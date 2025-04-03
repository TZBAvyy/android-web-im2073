import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;

public class UserTest {
    private User user;

    @Before
    public void setUp() {
        user = User.findUser("gibraltar.av@gmail.com");
    }

    @Test
    public void testFindUser() {
        assertNotNull("User should not be null", user);
        assertNull("User should be null", User.findUser("wrong@email.com"));
    }

    @Test
    public void testPasswordCheck() {
        assertTrue("Password check should return true", user.checkPassword("xxxx"));
        assertFalse("Password check should return false", user.checkPassword("wrongpassword"));
        assertFalse("Password check should return false", user.checkPassword(""));
    }
}
