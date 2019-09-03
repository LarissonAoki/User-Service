

import access.AccessServlet;
import model.User;
import model.Database;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;

public class VerifyUserTest {
    
    AccessServlet accessServlet = new AccessServlet();
    Database db = new Database();
    User user;
    
    @BeforeEach
    public void before(){
        user = new User("", "larissa soares", "0123456", "larissa.aoki07@inpe.br", "Brazil", "1299117235600", "INPE");
    }
    
    @Test
    public void verifyUserAproved() throws JSONException{
        
        assertEquals(user, accessServlet.varifyUser("larissa soares", "0123456", "larissa.aoki07@inpe.br", "Brazil", "1299117235600", "INPE"));
    }
    @Test
    public void verifyUserNameErro1() throws JSONException{
        user.setUserName("character impromper");
        assertEquals(user, accessServlet.varifyUser("l@rissa_so@res", "0123456", "larissa.aoki07@inpe.br", "Brazil", "1299117235600", "INPE"));
    }
    @Test
    public void verifyUserNameErro2() throws JSONException{
        user.setUserName("existent");
        assertEquals(user, accessServlet.varifyUser("larissa", "0123456", "larissa.aoki07@inpe.br", "Brazil", "1299117235600", "INPE"));
    }
    @Test
    public void verifyUserNameErro3() throws JSONException{
        user.setUserName("invalid: blanck space");
        assertEquals(user, accessServlet.varifyUser("", "0123456", "larissa.aoki07@inpe.br", "Brazil", "1299117235600", "INPE"));
    }
    @Test
    public void verifyUserPasswordErro1() throws JSONException{
        user.setPassword("invalid size");
        assertEquals(user, accessServlet.varifyUser("larissa soares", "1234", "larissa.aoki07@inpe.br", "Brazil", "1299117235600", "INPE"));
    }
    @Test
    public void verifyUserPasswordErro2() throws JSONException{
        user.setPassword("invalid: blanck space");
        assertEquals(user, accessServlet.varifyUser("larissa soares", "", "larissa.aoki07@inpe.br", "Brazil", "1299117235600", "INPE"));
    }
    @Test
    public void verifyUserEmailErro1() throws JSONException{
        user.setEmail("email invalid");
        assertEquals(user, accessServlet.varifyUser("larissa soares", "0123456", "larissa@aoki07@inpe.br", "Brazil", "1299117235600", "INPE"));
    }
    @Test
    public void verifyUserEmailErro2() throws JSONException{
        user.setEmail("invalid: have space");
        assertEquals(user, accessServlet.varifyUser("larissa soares", "0123456", "larissa aoki07@inpe.br", "Brazil", "1299117235600", "INPE"));
    }
    @Test
    public void verifyUserEmailErro3() throws JSONException{
        user.setEmail("invalid: blanck space");
        assertEquals(user, accessServlet.varifyUser("larissa soares", "0123456", "", "Brazil", "1299117235600", "INPE"));
    }
    @Test
    public void verifyUserCountryErro() throws JSONException{
        user.setCountry("invalid: blanck space");
        System.out.println("country -> "+user.getCountry());
        assertEquals(user, accessServlet.varifyUser("larissa soares", "0123456", "larissa.aoki07@inpe.br", " ", "1299117235600", "INPE"));
    }
    @Test
    public void verifyUserPhoneErro1() throws JSONException{
        user.setPhoneNumber("invalid: only number");
        assertEquals(user, accessServlet.varifyUser("larissa soares", "0123456", "larissa.aoki07@inpe.br", "Brazil", "12991172356aa", "INPE"));
    }
    @Test
    public void verifyUserPhoneErro2() throws JSONException{
        user.setPhoneNumber("invalid: blanck space");
        assertEquals(user, accessServlet.varifyUser("larissa soares", "0123456", "larissa.aoki07@inpe.br", "Brazil", "", "INPE"));
    }
    @Test
    public void verifyUserOrganizationErro() throws JSONException{
        User u = new User("", "larissa soares", "0123456", "larissa.aoki07@inpe.br", "Brazil", "1299117235600", "invalid: blanck space");
        user.setOrganization("invalid: blanck space");
        System.out.println("org -> "+user.getOrganization());
        assertEquals(u, accessServlet.varifyUser("larissa soares", "0123456", "larissa.aoki07@inpe.br", "Brazil", "1299117235600", " "));
    }
}
