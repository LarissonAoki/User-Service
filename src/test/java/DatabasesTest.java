import access.Validation;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.eq;
import model.Database;
import model.User;
import org.bson.Document;
import org.json.JSONException;
import org.json.JSONObject;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterAll;


public class DatabasesTest {

    public Database db = new Database();
    
    static MongoCollection<Document> collection;
    static Document document;
    FindIterable<Document> list;
    
    @BeforeAll
    public static void insertUserDatabase(){
        MongoClient cliente = new MongoClient("localhost", 27017);
        MongoDatabase database = cliente.getDatabase("User");
        collection = database.getCollection("userData");
        
        document = new Document("userName", "larissa soares")
                .append("password", "0123456")
                .append("email", "larissa.aoki07@inpe.br")
                .append("country", "Brazil")
                .append("phoneNumber", "1299117235600")
                .append("organization", "INPE");
        
        collection.insertOne(document);
    }
    
    @Test
    public void searchUserNull() throws JSONException{
        JSONObject objJson = new JSONObject();
        
        objJson.put("id", "null");
        objJson.put("auth", "null");
        objJson.put("userName", "null");
        objJson.put("password", "null");
        objJson.put("email", "null");
        objJson.put("country", "null");
        objJson.put("phone number", "null");
        objJson.put("organization", "null");
        
        assertEquals(objJson.toString(), db.searchUser("larissa2", "0123456").toString());
    }
    @Test
    public void searchUserPassewordInvalid() throws JSONException{
        JSONObject objJson = new JSONObject();
        
        FindIterable<Document> list = collection.find(eq("userName", "larissa soares"));
        
        User user = db.convertUser(list);
        
        objJson.put("id", user.getId());
        objJson.put("auth", "false");
        objJson.put("userName", user.getUserName());
        objJson.put("password", "invalid");
        objJson.put("email", "null");
        objJson.put("country", "null");
        objJson.put("phone number", "null");
        objJson.put("organization", "null");
        
        assertEquals(objJson.toString(), db.searchUser("larissa soares", "111111111").toString());
    }
    @Test
    public void searchUserApproved() throws JSONException{
        JSONObject objJson = new JSONObject();
        
        FindIterable<Document> list = collection.find(eq("userName", "larissa soares"));
        
        User user = db.convertUser(list);
        
        objJson.put("id", user.getId());
        objJson.put("auth", "true");
        objJson.put("userName", user.getUserName());
        objJson.put("password", user.getPassword());
        objJson.put("email", user.getEmail());
        objJson.put("country", user.getCountry());
        objJson.put("phone number", user.getPhoneNumber());
        objJson.put("organization", user.getOrganization());
        
        assertEquals(objJson.toString(), db.searchUser("larissa soares", "0123456").toString());
    }
    
    @Test
    public void convertUserNull(){
        FindIterable<Document> list = collection.find(eq("userName", "larissa2"));

        assertNull(db.convertUser(list));
    }
    @Test
    public void convertUserAproved(){
        
        FindIterable<Document> list = collection.find(eq("userName", "larissa soares"));
        User user = null;
        for(Document d: list){
           String id = (String) d.get("_id").toString();
           String passwd = (String) d.get("password");
           String name = (String) d.get("userName");
           String email = (String) d.get("email");
           String country = (String) d.get("country");
           String phone = (String) d.get("phoneNumber");
           String org = (String) d.get("organization");
           
           user = new User(id, name, passwd, email, country, phone, org);   
        }
        
        assertEquals(user, db.convertUser(list));
    }
    @Test
    public void convertUserDisapproved(){
        User user = new User("", "larissa soares", "1234567890", "aoki.larissa07@gmail.com", "Brazil", "12345678900", "INPE");
        FindIterable<Document> list = collection.find(eq("userName", "larissa soares"));
        
        assertNotEquals(user, db.convertUser(list));
    }
    
    @Test
    public void insertUserAproved() throws JSONException{
            Validation validate = new Validation();
            User origin = new User("", "fabricio", "1234567", "prof.fabricio@gmail.com", "Brazil", "1298765432100", "INPE");
            User verify = new User("", validate.verifyName(origin.getUserName()),
                    validate.verifyPassword(origin.getPassword()), 
                    validate.verifyEmail(origin.getEmail()), 
                    validate.verifySpace(origin.getCountry()), 
                    validate.verifyPhone(origin.getPhoneNumber()),
                    validate.verifySpace(origin.getOrganization()));
          JSONObject objJson = new JSONObject();
          
          objJson.put("id", "5c36223a04727f10c123af50"); 
          objJson.put("subscribe", "true");
          objJson.put("userName", verify.getUserName());
          objJson.put("password", verify.getPassword());
          objJson.put("email", verify.getEmail());
          objJson.put("country", verify.getCountry());
          objJson.put("phone number", verify.getPhoneNumber());
          objJson.put("organization", verify.getOrganization());
          
          JSONObject resultJson = db.insertUser(origin, verify);
          resultJson.put("id", "5c36223a04727f10c123af50");
          
          assertEquals(objJson.toString(), resultJson.toString());
    }
    @Test
    public void insertUserExist() throws JSONException{
            Validation validate = new Validation();
            User origin = new User("", "larissa", "1234567", "prof.fabricio@gmail.com", "Brazil", "1298765432100", "INPE");
            User verify = new User("", validate.verifyName(origin.getUserName()),
                    validate.verifyPassword(origin.getPassword()), 
                    validate.verifyEmail(origin.getEmail()), 
                    validate.verifySpace(origin.getCountry()), 
                    validate.verifyPhone(origin.getPhoneNumber()),
                    validate.verifySpace(origin.getOrganization()));
          JSONObject objJson = new JSONObject();
          
          objJson.put("id", ""); 
          objJson.put("subscribe", "false");
          objJson.put("userName", verify.getUserName());
          objJson.put("password", verify.getPassword());
          objJson.put("email", verify.getEmail());
          objJson.put("country", verify.getCountry());
          objJson.put("phone number", verify.getPhoneNumber());
          objJson.put("organization", verify.getOrganization());
          
          assertEquals(objJson.toString(), db.insertUser(origin, verify).toString());
    }
    
    @Test
    public void insertUserCharacterName() throws JSONException{
            Validation validate = new Validation();
            User origin = new User("", "l@rissa", "1234567", "prof.fabricio@gmail.com", "Brazil", "1298765432100", "INPE");
            User verify = new User("", validate.verifyName(origin.getUserName()),
                    validate.verifyPassword(origin.getPassword()), 
                    validate.verifyEmail(origin.getEmail()), 
                    validate.verifySpace(origin.getCountry()), 
                    validate.verifyPhone(origin.getPhoneNumber()),
                    validate.verifySpace(origin.getOrganization()));
          JSONObject objJson = new JSONObject();
          
          objJson.put("id", ""); 
          objJson.put("subscribe", "false");
          objJson.put("userName", verify.getUserName());
          objJson.put("password", verify.getPassword());
          objJson.put("email", verify.getEmail());
          objJson.put("country", verify.getCountry());
          objJson.put("phone number", verify.getPhoneNumber());
          objJson.put("organization", verify.getOrganization());
          
          assertEquals(objJson.toString(), db.insertUser(origin, verify).toString());
    }
    
    
    @Test
    public void insertUserPasswordSmall() throws JSONException{
            Validation validate = new Validation();
            User origin = new User("", "larissa2", "1234", "prof.fabricio@gmail.com", "Brazil", "1298765432100", "INPE");
            User verify = new User("", validate.verifyName(origin.getUserName()),
                    validate.verifyPassword(origin.getPassword()), 
                    validate.verifyEmail(origin.getEmail()), 
                    validate.verifySpace(origin.getCountry()), 
                    validate.verifyPhone(origin.getPhoneNumber()),
                    validate.verifySpace(origin.getOrganization()));
          JSONObject objJson = new JSONObject();
          
          objJson.put("id", ""); 
          objJson.put("subscribe", "false");
          objJson.put("userName", verify.getUserName());
          objJson.put("password", verify.getPassword());
          objJson.put("email", verify.getEmail());
          objJson.put("country", verify.getCountry());
          objJson.put("phone number", verify.getPhoneNumber());
          objJson.put("organization", verify.getOrganization());
          
          assertEquals(objJson.toString(), db.insertUser(origin, verify).toString());
    }
    @Test
    public void insertUserEmailTwo() throws JSONException{
            Validation validate = new Validation();
            User origin = new User("", "fabricio", "1234567", "prof@fabricio@gmail.com", "Brazil", "1298765432100", "INPE");
            User verify = new User("", validate.verifyName(origin.getUserName()),
                    validate.verifyPassword(origin.getPassword()), 
                    validate.verifyEmail(origin.getEmail()), 
                    validate.verifySpace(origin.getCountry()), 
                    validate.verifyPhone(origin.getPhoneNumber()),
                    validate.verifySpace(origin.getOrganization()));
          JSONObject objJson = new JSONObject();
          
          objJson.put("id", ""); 
          objJson.put("subscribe", "false");
          objJson.put("userName", verify.getUserName());
          objJson.put("password", verify.getPassword());
          objJson.put("email", verify.getEmail());
          objJson.put("country", verify.getCountry());
          objJson.put("phone number", verify.getPhoneNumber());
          objJson.put("organization", verify.getOrganization());
          
          assertEquals(objJson.toString(), db.insertUser(origin, verify).toString());
    }
    @Test
    public void insertUserEmailSpace() throws JSONException{
            Validation validate = new Validation();
            User origin = new User("", "fabricio", "1234567", "prof fabricio@gmail.com", "Brazil", "1298765432100", "INPE");
            User verify = new User("", validate.verifyName(origin.getUserName()),
                    validate.verifyPassword(origin.getPassword()), 
                    validate.verifyEmail(origin.getEmail()), 
                    validate.verifySpace(origin.getCountry()), 
                    validate.verifyPhone(origin.getPhoneNumber()),
                    validate.verifySpace(origin.getOrganization()));
          JSONObject objJson = new JSONObject();
          
          objJson.put("id", ""); 
          objJson.put("subscribe", "false");
          objJson.put("userName", verify.getUserName());
          objJson.put("password", verify.getPassword());
          objJson.put("email", verify.getEmail());
          objJson.put("country", verify.getCountry());
          objJson.put("phone number", verify.getPhoneNumber());
          objJson.put("organization", verify.getOrganization());
          
          assertEquals(objJson.toString(), db.insertUser(origin, verify).toString());
    }
    @Test
    public void insertUserEmailDoto() throws JSONException{
            Validation validate = new Validation();
            User origin = new User("", "fabricio", "1234567", "prof.fabricio@gmailcom", "Brazil", "1298765432100", "INPE");
            User verify = new User("", validate.verifyName(origin.getUserName()),
                    validate.verifyPassword(origin.getPassword()), 
                    validate.verifyEmail(origin.getEmail()), 
                    validate.verifySpace(origin.getCountry()), 
                    validate.verifyPhone(origin.getPhoneNumber()),
                    validate.verifySpace(origin.getOrganization()));
          JSONObject objJson = new JSONObject();
          
          objJson.put("id", ""); 
          objJson.put("subscribe", "false");
          objJson.put("userName", verify.getUserName());
          objJson.put("password", verify.getPassword());
          objJson.put("email", verify.getEmail());
          objJson.put("country", verify.getCountry());
          objJson.put("phone number", verify.getPhoneNumber());
          objJson.put("organization", verify.getOrganization());
          
          assertEquals(objJson.toString(), db.insertUser(origin, verify).toString());
    }
    @Test
    public void insertUserEmailNumber() throws JSONException{
            Validation validate = new Validation();
            User origin = new User("", "fabricio", "1234567", "prof.fabricio@12345.com", "Brazil", "1298765432100", "INPE");
            User verify = new User("", validate.verifyName(origin.getUserName()),
                    validate.verifyPassword(origin.getPassword()), 
                    validate.verifyEmail(origin.getEmail()), 
                    validate.verifySpace(origin.getCountry()), 
                    validate.verifyPhone(origin.getPhoneNumber()),
                    validate.verifySpace(origin.getOrganization()));
          JSONObject objJson = new JSONObject();
          
          objJson.put("id", ""); 
          objJson.put("subscribe", "false");
          objJson.put("userName", verify.getUserName());
          objJson.put("password", verify.getPassword());
          objJson.put("email", verify.getEmail());
          objJson.put("country", verify.getCountry());
          objJson.put("phone number", verify.getPhoneNumber());
          objJson.put("organization", verify.getOrganization());
          
          assertEquals(objJson.toString(), db.insertUser(origin, verify).toString());
    }
    @Test
    public void insertUserCountrySpace() throws JSONException{
            Validation validate = new Validation();
            User origin = new User("", "fabricio", "1234567", "prof.fabricio@gmail.com", "          ", "1298765432100", "INPE");
            User verify = new User("", validate.verifyName(origin.getUserName()),
                    validate.verifyPassword(origin.getPassword()), 
                    validate.verifyEmail(origin.getEmail()), 
                    validate.verifySpace(origin.getCountry()), 
                    validate.verifyPhone(origin.getPhoneNumber()),
                    validate.verifySpace(origin.getOrganization()));
          JSONObject objJson = new JSONObject();
          
          objJson.put("id", ""); 
          objJson.put("subscribe", "false");
          objJson.put("userName", verify.getUserName());
          objJson.put("password", verify.getPassword());
          objJson.put("email", verify.getEmail());
          objJson.put("country", verify.getCountry());
          objJson.put("phone number", verify.getPhoneNumber());
          objJson.put("organization", verify.getOrganization());
          
          assertEquals(objJson.toString(), db.insertUser(origin, verify).toString());
    }
    @Test
    public void insertUserPhoneCharacter() throws JSONException{
            Validation validate = new Validation();
            User origin = new User("", "fabricio", "1234567", "prof.fabricio@gmail.com", "Brazil", "12987654321aa", "INPE");
            User verify = new User("", validate.verifyName(origin.getUserName()),
                    validate.verifyPassword(origin.getPassword()), 
                    validate.verifyEmail(origin.getEmail()), 
                    validate.verifySpace(origin.getCountry()), 
                    validate.verifyPhone(origin.getPhoneNumber()),
                    validate.verifySpace(origin.getOrganization()));
          JSONObject objJson = new JSONObject();
          
          objJson.put("id", ""); 
          objJson.put("subscribe", "false");
          objJson.put("userName", verify.getUserName());
          objJson.put("password", verify.getPassword());
          objJson.put("email", verify.getEmail());
          objJson.put("country", verify.getCountry());
          objJson.put("phone number", verify.getPhoneNumber());
          objJson.put("organization", verify.getOrganization());
          
          assertEquals(objJson.toString(), db.insertUser(origin, verify).toString());
    }
    @Test
    public void insertUserPhoneSpace() throws JSONException{
            Validation validate = new Validation();
            User origin = new User("", "fabricio", "1234567", "prof.fabricio@gmail.com", "Brazil", "12 987654321", "INPE");
            User verify = new User("", validate.verifyName(origin.getUserName()),
                    validate.verifyPassword(origin.getPassword()), 
                    validate.verifyEmail(origin.getEmail()), 
                    validate.verifySpace(origin.getCountry()), 
                    validate.verifyPhone(origin.getPhoneNumber()),
                    validate.verifySpace(origin.getOrganization()));
          JSONObject objJson = new JSONObject();
          
          objJson.put("id", ""); 
          objJson.put("subscribe", "false");
          objJson.put("userName", verify.getUserName());
          objJson.put("password", verify.getPassword());
          objJson.put("email", verify.getEmail());
          objJson.put("country", verify.getCountry());
          objJson.put("phone number", verify.getPhoneNumber());
          objJson.put("organization", verify.getOrganization());
          
          assertEquals(objJson.toString(), db.insertUser(origin, verify).toString());
    }
    @Test
    public void insertUserOrganizationBlank() throws JSONException{
            Validation validate = new Validation();
            User origin = new User("", "fabricio", "1234567", "prof.fabricio@gmail.com", "Brazil", "1298765432100", "       ");
            User verify = new User("", validate.verifyName(origin.getUserName()),
                    validate.verifyPassword(origin.getPassword()), 
                    validate.verifyEmail(origin.getEmail()), 
                    validate.verifySpace(origin.getCountry()), 
                    validate.verifyPhone(origin.getPhoneNumber()),
                    validate.verifySpace(origin.getOrganization()));
          JSONObject objJson = new JSONObject();
          
          objJson.put("id", ""); 
          objJson.put("subscribe", "false");
          objJson.put("userName", verify.getUserName());
          objJson.put("password", verify.getPassword());
          objJson.put("email", verify.getEmail());
          objJson.put("country", verify.getCountry());
          objJson.put("phone number", verify.getPhoneNumber());
          objJson.put("organization", verify.getOrganization());
          
          assertEquals(objJson.toString(), db.insertUser(origin, verify).toString());
    }
    @Test
    public void insertUserBlankSpace() throws JSONException{
            Validation validate = new Validation();
            User origin = new User("", "     ", "    ", "     ", "     ", "    ", "    ");
            User verify = new User("", validate.verifyName(origin.getUserName()),
                    validate.verifyPassword(origin.getPassword()), 
                    validate.verifyEmail(origin.getEmail()), 
                    validate.verifySpace(origin.getCountry()), 
                    validate.verifyPhone(origin.getPhoneNumber()),
                    validate.verifySpace(origin.getOrganization()));
          JSONObject objJson = new JSONObject();
          
          objJson.put("id", ""); 
          objJson.put("subscribe", "false");
          objJson.put("userName", verify.getUserName());
          objJson.put("password", verify.getPassword());
          objJson.put("email", verify.getEmail());
          objJson.put("country", verify.getCountry());
          objJson.put("phone number", verify.getPhoneNumber());
          objJson.put("organization", verify.getOrganization());
          
          assertEquals(objJson.toString(), db.insertUser(origin, verify).toString());
    }
    @Test
    public void insertUserDesaproved() throws JSONException{
            Validation validate = new Validation();
            User origin = new User("", "f@bricio", "1234", "prof@fabricio@gmail.com", "   ", "12abcd321", "    ");
            User verify = new User("", validate.verifyName(origin.getUserName()),
                    validate.verifyPassword(origin.getPassword()), 
                    validate.verifyEmail(origin.getEmail()), 
                    validate.verifySpace(origin.getCountry()), 
                    validate.verifyPhone(origin.getPhoneNumber()),
                    validate.verifySpace(origin.getOrganization()));
          JSONObject objJson = new JSONObject();
          
          objJson.put("id", ""); 
          objJson.put("subscribe", "false");
          objJson.put("userName", verify.getUserName());
          objJson.put("password", verify.getPassword());
          objJson.put("email", verify.getEmail());
          objJson.put("country", verify.getCountry());
          objJson.put("phone number", verify.getPhoneNumber());
          objJson.put("organization", verify.getOrganization());
          
          assertEquals(objJson.toString(), db.insertUser(origin, verify).toString());
    }
    
        
    @AfterAll
    public static void deleteUserDatabase(){
        collection.deleteOne(document);
        collection.deleteOne(eq("userName", "fabricio"));
    }
    
}
