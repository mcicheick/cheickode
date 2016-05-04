package forms;

import play.data.validation.Constraints;
import play.libs.Json;

public class LoginForm {

    @Constraints.Required(message = "Username is required")
    public String username;

    @Constraints.Required(message = "Password is required")
    public String password;

    public String fromUrl;

    public Boolean remember = false;

    public LoginForm(){
    }

    @Override
    public String toString() {
        return Json.toJson(this).toString();
    }
}