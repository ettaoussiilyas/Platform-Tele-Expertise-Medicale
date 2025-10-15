import org.junit.jupiter.api.Test;
import org.mindrot.jbcrypt.BCrypt;
import static org.junit.jupiter.api.Assertions.*;

public class AuthenticationTest {
    
    @Test
    public void testPasswordHashing() {
        String password = "test123";
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        
        // Verify the password can be checked
        assertTrue(BCrypt.checkpw(password, hashedPassword));
        assertFalse(BCrypt.checkpw("wrongpassword", hashedPassword));
    }
    
    @Test
    public void testKnownHash() {
        String password = "test123";
        String knownHash = "$2a$10$lU0eZqMbGT46n7cwPp4eveaNPnH0I5Cnw0OkShwTgQ1wW6z5ugylK";
        
        // This should work with our test data
        assertTrue(BCrypt.checkpw(password, knownHash));
    }
}