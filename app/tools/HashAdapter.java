package tools;

import com.google.inject.Inject;
import play.Application;

/**
 * Created by sissoko on 21/04/2016.
 */
public class HashAdapter implements Hash {

    private final static String PLAIN = "plain";
    private final static String BCRYPT = "bcrypt";

    private Hash hash;

    @Inject
    public HashAdapter(Application application) {
        String hashKey = application.configuration().getString("tools.hash", PLAIN);
        if (hashKey.equals(BCRYPT)) {
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
