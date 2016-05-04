package tools;

import com.google.inject.Inject;
import play.Application;

/**
 * Password hashing class.<br/>
 * This class will centralize all method to hash passwords.
 * <p>
 * Created by sissoko on 23/09/15.
 *
 * @since DjammaDev 1.0
 */
public interface Hash {

    String hashPassword(String password);

    String hashPassword(String password, String salt);

    boolean checkPassword(String plaintext, String hashed);
}
