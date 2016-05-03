import models.User;
import org.junit.Test;

import javax.persistence.PersistenceException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by sissoko on 03/05/2016.
 */
public class UserUnitTest extends UnitTest {

    private static final String EMAIL = "test@test.com";
    private static final String PASSWORD = "secret";

    @Test
    public void shouldCreateUserWhenEmailAndPasswordAreGiven() {
        User user = createUser(EMAIL, PASSWORD);
        User.find.db().save(user);
        assertEquals(1, User.find.findRowCount());
    }

    @Test
    public void shouldReturnUserWhenUserExistsWithEmail() {
        User user = createUser(EMAIL, PASSWORD);
        User.find.db().save(user);
        User fromDatabase = User.findByEmail(EMAIL);
        assertNotNull(fromDatabase);
    }

    @Test(expected = PersistenceException.class)
    public void shouldThrowPersistentExceptionWhenUserEmailAlreadyExists() throws PersistenceException {
        User user = createUser(EMAIL, PASSWORD);
        User.find.db().save(user);
        user = createUser(EMAIL, PASSWORD);
        User.find.db().save(user);
    }

    private User createUser(String email, String password) {
        User user = new User();
        user.email = email;
        user.password = password;
        return user;
    }
}
