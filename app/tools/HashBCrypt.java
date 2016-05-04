package tools;

/**
 * Created by sissoko on 21/04/2016.
 */
public class HashBCrypt implements Hash {

    @Override
    public String hashPassword(String password) {
        return hashPassword(password, BCrypt.gensalt());
    }

    @Override
    public String hashPassword(String password, String salt) {
        return BCrypt.hashpw(password, salt);
    }

    @Override
    public boolean checkPassword(String plaintext, String hashed) {
        return BCrypt.checkpw(plaintext, hashed);
    }
}
