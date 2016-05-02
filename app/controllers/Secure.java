package controllers;

import com.google.inject.Inject;
import com.google.inject.Provider;
import forms.LoginForm;
import models.User;
import play.api.Play;
import play.data.Form;
import play.data.validation.ValidationError;
import play.libs.Crypto;
import play.libs.Time;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import play.twirl.api.Content;
import tools.Hash;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Secure extends Controller {

    @Inject
    Crypto crypto;

    @Inject
    Provider<Hash> hashProvider;

    public static User connected() {
        return User.find.where().eq("email", session("username")).findUnique();
    }

    public Result login() throws Throwable {
        if (session("username") != null) {
            return redirect("/");
        }
        Http.Cookie remember = Http.Context.current().request().cookie("rememberme");
        if (remember != null) {
            int firstIndex = remember.value().indexOf("-");
            int lastIndex = remember.value().lastIndexOf("-");
            if (lastIndex > firstIndex) {
                String sign = remember.value().substring(0, firstIndex);
                String restOfCookie = remember.value().substring(firstIndex + 1);
                String username = remember.value().substring(firstIndex + 1, lastIndex);
                String time = remember.value().substring(lastIndex + 1);
                Date expirationDate = new Date(Long.parseLong(time)); // surround with try/catch?
                Date now = new Date();
                if (expirationDate == null || expirationDate.before(now)) {
                    return logout();
                }
                if (crypto.sign(restOfCookie).equals(sign)) {
                    session("username", username);
                    String from = flash("fromUrl");
                    if (from == null) {
                        from = Http.Context.current().request().getHeader("referer");
                    }
                    if (from != null) {
                        return redirect(from);
                    } else {
                        return redirect("/");
                    }
                }
            }
        }

        String fromUrl = flash("fromUrl");
        if (fromUrl == null) {
            fromUrl = Http.Context.current().request().getHeader("referer");
        }
        LoginForm loginForm = new LoginForm();
        loginForm.fromUrl = fromUrl;
        Form<LoginForm> form = Form.form(LoginForm.class).fill(loginForm);
        return ok(content(form, "login"));
    }

    public Result logout() throws Throwable {
        session().clear();
        Http.Context.current().response().discardCookie("rememberme");
        return redirect("/");
    }

    public Result logon() throws Throwable {
        Form<LoginForm> bindedForm = Form.form(LoginForm.class).bindFromRequest();
        if (bindedForm.hasErrors()) {
            return badRequest(content(bindedForm, "login"));
        }
        LoginForm userForm = bindedForm.get();
        User user = User.findByEmail(userForm.username);
        if (user != null) {
            String hashPassword = user.password;
            if (!hashProvider.get().checkPassword(userForm.password, hashPassword)) {
                user = null;
            }
        }
        if (user == null) {
            List<ValidationError> errorList = new ArrayList<>();
            errorList.add(new ValidationError("username", "Login et/ou mot de passe incorrect(s)"));
            bindedForm.errors().put("", errorList);
            return badRequest(content(bindedForm, "login"));
        }
        session().put("username", userForm.username);

        // Mark user as connected
        // Remember if needed
        if (userForm.remember) {
            Date expiration = new Date();
            String duration = "7d";
            Integer maxAge = Time.parseDuration(duration);
            expiration.setTime(expiration.getTime() + ((long) maxAge * 1000));
            Http.Context.current().response()
                    .setCookie("rememberme", crypto.sign(userForm.username + "-" + expiration.getTime())
                            + "-" + userForm.username + "-" + expiration.getTime(), maxAge);

        }
        String redirect = "/";
        if (userForm.fromUrl != null && !userForm.fromUrl.isEmpty()) {
            redirect = userForm.fromUrl;
        }
        return redirect(redirect);
    }

    /**
     * @param bindedForm
     * @param action
     * @return
     * @throws Throwable
     */
    private static Content content(Form<LoginForm> bindedForm, String action) throws Throwable {
        Content content;
        try {
            Method method = getViewClass(action).getMethod("render", Form.class);
            content = (Content) method.invoke(null, bindedForm);
        } catch (ClassNotFoundException
                | IllegalAccessException
                | IllegalArgumentException
                | NoSuchMethodException
                | SecurityException
                | InvocationTargetException e) {
            content = views.html.login.render(bindedForm);
        }
        return content;
    }

    public static Class getViewClass(String view) throws Throwable {
        String classFullName = "views.html." + view;
        Class clazz = Play.classloader(Play.current()).loadClass(classFullName);
        return clazz;
    }
}
