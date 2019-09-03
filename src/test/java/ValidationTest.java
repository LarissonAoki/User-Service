import access.Validation;
import org.json.JSONException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class ValidationTest {
    
    Validation v = new Validation();
    
    @Test
    public void validateName() throws JSONException{
        assertEquals("fabricio", v.verifyName("fabricio"));
    }
    @Test
    public void validateNameSpace() throws JSONException{
        assertEquals("invalid: blanck space", v.verifyName(""));
    }
    @Test
    public void validateNameExist() throws JSONException{
        assertEquals("existent", v.verifyName("larissa"));
    }
    @Test
    public void validateNameCharacter() throws JSONException{
        assertEquals("character impromper", v.verifyName("l@rissa"));
    }
    
    @Test
    public void validatePassword(){
        assertEquals("senha12345", v.verifyPassword("senha12345"));
    }
    @Test
    public void validatePasswordSize(){
        assertEquals("invalid size", v.verifyPassword("12345"));
    }
    @Test
    public void validatePasswordSpace(){
        assertEquals("invalid: blanck space", v.verifyPassword("     "));
    }
    
    @Test
    public void validatePhone(){
        assertEquals("1645121654", v.verifyPhone("1645121654"));
    }
    @Test
    public void validatePhoneOnlyNumber(){
        assertEquals("invalid: only number", v.verifyPhone("123456789abc"));
    }
    @Test
    public void validatePhoneSpace(){
        assertEquals("invalid: blanck space", v.verifyPhone("    "));
    }
    
    @Test
    public void validateEmail(){
        assertEquals("larissa.aoki@inpe.br", v.verifyEmail("larissa.aoki@inpe.br"));
    }
    @Test
    public void validateEmailBlackSpace(){
        assertEquals("invalid: blanck space", v.verifyEmail("     "));
    }
    @Test
    public void validateEmailSpace(){
        assertEquals("invalid: have space", v.verifyEmail("larissa aoki @inpe.br"));
    }
    @Test
    public void validateEmailTwo(){
        assertEquals("email invalid", v.verifyEmail("lariss@oki@gmail.com"));
    }
    @Test
    public void validateEmailNoDot(){
        assertEquals("email invalid", v.verifyEmail("larissa.aoki@inpe"));
    }
    @Test
    public void validateEmailHaveNumber(){
        assertEquals("email invalid", v.verifyEmail("larissa.aoki@1234.br"));
    }
    
    @Test
    public void validateSpace(){
        assertEquals("spacetest", v.verifySpace("space test"));
    }
    @Test
    public void validateSpace2(){
        assertEquals("", v.verifySpace("        "));
    }
}
