package tools;

import java.security.SecureRandom;

/**
 * Created by sissoko on 21/04/2016.
 */
public class HashPlain implements Hash {

    private static final int GENSALT_DEFAULT_LOG2_ROUNDS = 10;
    private static final int BCRYPT_SALT_LEN = 16;

    /**
     * Generate a salt for use with the BCrypt.hashpw() method,
     * selecting a reasonable default for the number of hashing
     * rounds to apply
     *
     * @return an encoded salt value
     */
    private static String gensalt() {
        return gensalt(GENSALT_DEFAULT_LOG2_ROUNDS);
    }

    /**
     * Generate a salt for use with the BCrypt.hashpw() method
     *
     * @param log_rounds the log2 of the number of rounds of
     *                   hashing to apply - the work factor therefore increases as
     *                   2**log_rounds.
     * @return an encoded salt value
     */
    private static String gensalt(int log_rounds) {
        return gensalt(log_rounds, new SecureRandom());
    }

    /**
     * Generate a salt for use with the BCrypt.hashpw() method
     *
     * @param log_rounds the log2 of the number of rounds of
     *                   hashing to apply - the work factor therefore increases as
     *                   2**log_rounds.
     * @param random     an instance of SecureRandom to use
     * @return an encoded salt value
     */
    private static String gensalt(int log_rounds, SecureRandom random) {
        StringBuffer rs = new StringBuffer();
        byte rnd[] = new byte[BCRYPT_SALT_LEN];

        random.nextBytes(rnd);

        rs.append("$2a$");
        if (log_rounds < 10)
            rs.append("0");
        if (log_rounds > 30) {
            throw new IllegalArgumentException(
                    "log_rounds exceeds maximum (30)");
        }
        rs.append(Integer.toString(log_rounds));
        return rs.toString();
    }

    @Override
    public String hashPassword(String password) {
        return hashPassword(password, gensalt());
    }

    @Override
    public String hashPassword(String password, String salt) {
        return password + salt;
    }

    @Override
    public boolean checkPassword(String clearPassword, String hashedPassword) {
        if (clearPassword == null || hashedPassword == null) {
            return false;
        }
        String clearPart = hashedPassword.substring(0, clearPassword.length());
        return clearPassword.compareTo(clearPart) == 0;
    }
}
