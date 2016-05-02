package controllers;

import com.google.inject.Inject;
import com.google.inject.Provider;
import models.Post;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.*;

import tools.Hash;
import views.html.*;

import java.util.List;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class HomeController extends Secure {

    @Inject
    FormFactory factory;

    /**
     * An action that renders an HTML page with a welcome message.
     * The configuration in the <code>routes</code> file means that
     * this method will be called when the application receives a
     * <code>GET</code> request with a path of <code>/</code>.
     */
    public Result index() {
        List<Post> posts = Post.find.order().desc("modify_date").findList();
        return ok(index.render(posts));
    }

    public Result create() {
        return ok(create.render(factory.form(Post.class)));
    }

    public Result edit(Long id) {
        if (connected() == null) {
            return unauthorized(unauthorized.render("Please connect to edit this post"));
        }
        Post post = Post.find.byId(id);
        if (post == null) {
            return notFound("Page not found");
        }
        Form<Post> form = factory.form(Post.class);
        form = form.fill(post);
        return ok(edit.render(form));
    }

    public Result post() {
        Form<Post> form = factory.form(Post.class).bindFromRequest();
        if (form.hasErrors()) {
            return badRequest(create.render(form));
        }
        Post post = form.get();
        post.save();
        return redirect(routes.HomeController.index());
    }

    public Result show(Long id) {
        Post post = Post.find.byId(id);
        if (post == null) {
            return notFound("Page not found");
        }
        return ok(views.html.post.render(post));
    }

    public Result update(Long id) {
        if (connected() == null) {
            return unauthorized(unauthorized.render("Please connect to edit this post"));
        }
        Post fromDatabase = Post.find.byId(id);
        if (fromDatabase == null) {
            return notFound("Page not found");
        }
        Form<Post> form = factory.form(Post.class).bindFromRequest();
        if (form.hasErrors()) {
            return badRequest(edit.render(form));
        }
        Post post = form.get();
        fromDatabase.setTitle(post.title);
        fromDatabase.setProblem(post.problem);
        fromDatabase.setSolution(post.solution);
        fromDatabase.save();
        return redirect(routes.HomeController.show(id));
    }

    public Result delete() {
        return Results.TODO;
    }

}
