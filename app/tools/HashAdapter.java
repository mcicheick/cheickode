package tools;

import com.google.inject.Inject;
import play.Application;

/**
 * Created by sissoko on 21/04/2016.
 */
public class HashAdapter implements Hash {

    private Hash hash;

    @Inject
    public HashAdapter(Application application) {
        String hashKey = application.configuration().getString("tools.hash", "plain");
        if (hashKey.equals("bcrypt")) {
            this.hash = new HashBCrypt();
        } else {
            this.hash = new HashPlain();
        }
    }

    @Override
    public String hashPassword(String password) {
        return hash.hashPassword(password);
    }

    @Override
    public String hashPassword(String password, String salt) {
        return hash.hashPassword(password, salt);
    }

    @Override
    public boolean checkPassword(String plaintext, String hashed) {
        return hash.checkPassword(plaintext, hashed);
    }
}
