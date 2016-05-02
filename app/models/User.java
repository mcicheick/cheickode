package models;

import com.fasterxml.jackson.databind.node.ObjectNode;
import play.data.validation.Constraints;
import play.data.validation.ValidationError;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Djamma Dev
 * @date 2015-11-26 20:11:56
 */

@Entity
@Table(name = "T_USERS")
public class User extends ModelBase {

    /**
     * Static finder for communication with users table.
     */
    public static Finder<Long, User> find = new Finder<>(User.class);

    /**
     * Unique identifier for a model in a models table.
     */
    @Id
    public Long id;

    @Constraints.Required(message = "required.user.first_name")
    @Column(name = "FIRST_NAME")
    public String first_name;

    @Constraints.Required(message = "required.user.last_name")
    @Column(name = "LAST_NAME")
    public String last_name;

    @Constraints.Email
    @Constraints.Required(message = "required.user.email")
    @Column(name = "EMAIL", nullable = false, unique = true)
    public String email;

    @Constraints.Required(message = "required.user.password")
    @Column(name = "PASSWORD")
    public String password;

    public User() {
        super();
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    /**
     *
     * @param email
     * @return
     */
    public static User findByEmail(String email) {
        return find.where().eq("email", email).findUnique();
    }
    /**
     * When posting new User
     *
     * @return error
     */
    public List<ValidationError> validate() {
        List<ValidationError> errors = new ArrayList<>();
        if (id == null) {
            if (findByEmail(email) != null) {
                errors.add(new ValidationError("email", "Email exists (" + email + ")"));
            }
        }
        return errors.isEmpty() ? null : errors;
    }

    @Override
    public ObjectNode toJson() {
        ObjectNode result = super.toJson();
        result.put("first_name", first_name);
        result.put("last_name", last_name);
        return result;
    }

    @Override
    public String toString() {
        String out = "";
        if(first_name != null) {
            out += first_name;
        }
        if(last_name != null) {
            if(!out.isEmpty()) {
                out += " " +last_name;
            }
        }
        if(!out.isEmpty()) {
            return out;
        }
        return email;
    }

}
