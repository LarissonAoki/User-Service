import model.User;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

public class UserTest {
    
    static User user1;
    
    @BeforeEach
    public void criateUser(){
        user1 = new User("", "larissa", "6543210", "larissa.aoki@inpe.br", "Brazil", "1298765432100", "INPWE");
    }
    
    @Test
    public void userEquals (){
        User user2 = user1;
        assertEquals(user2, user1);
    }
    
    @Test
    public void userNotEquals(){
        User user2 = new User("", "larissa2", "0123456", "aoki.larissa07@gmail.com", "Japan", "1298765432100", "Fatec");
        assertNotEquals(user2, user1);
    }
}
