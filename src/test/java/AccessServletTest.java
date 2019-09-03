
import access.AccessServlet;
import access.Validation;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.eq;
import model.Database;
import org.bson.Document;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import javax.servlet.ServletException;
import model.User;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

public class AccessServletTest {
    
    AccessServlet accessServlet = new AccessServlet();
    Database db = new Database();
    static MongoCollection collection;
    static Document document;
    Validation validate = new Validation();
    User user;
    
    @Mock
    HttpServletRequest request;
    
    @Mock
    HttpServletResponse response;
    
    @BeforeAll
    public static void insertDatabase(){
        
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
   
    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);    
        user = new User("", "larissa aoki", "0123456", "larissa.aoki07@inpe.br", "Brazil", "1298765432100", "INPE");
    }
    
    @Test
    public void verifyUserAproved() throws JSONException, IOException, ServletException{
        
        User verify = userVerify(user);
        JSONObject objJson = db.insertUser(user, verify);
        String id = objJson.getString("id");
        collection.deleteOne(eq("userName", "larissa aoki"));
        
        when(request.getParameter("userName")).thenReturn("larissa aoki");
        when(request.getParameter("password")).thenReturn("0123456");
        when(request.getParameter("email")).thenReturn("larissa.aoki07@inpe.br");
        when(request.getParameter("country")).thenReturn("Brazil");
        when(request.getParameter("phoneNumber")).thenReturn("1298765432100");
        when(request.getParameter("organization")).thenReturn("INPE");
        
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        when(response.getWriter()).thenReturn(pw);
        accessServlet.doGet(request, response);
        
        
        sw.getBuffer().replace(88, 112, id);
        String result = sw.getBuffer().toString().trim();
        assertEquals(objJson.toString(), result);
    }
    @Test
    public void verifyUserNameErro1() throws JSONException, IOException, ServletException{
        user.setUserName("lariss@oki");
        User verify = userVerify(user);
        
        JSONObject objJson = db.insertUser(user, verify);
        
        when(request.getParameter("userName")).thenReturn("lariss@oki");
        when(request.getParameter("password")).thenReturn("0123456");
        when(request.getParameter("email")).thenReturn("larissa.aoki07@inpe.br");
        when(request.getParameter("country")).thenReturn("Brazil");
        when(request.getParameter("phoneNumber")).thenReturn("1298765432100");
        when(request.getParameter("organization")).thenReturn("INPE");
        
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        when(response.getWriter()).thenReturn(pw);
        accessServlet.doGet(request, response);
        String result = sw.getBuffer().toString().trim();
        assertEquals(objJson.toString(), result);
    }
    @Test
    public void verifyUserNameErro2() throws JSONException, ServletException, IOException{
        user.setUserName("larissa soares");
        User verify = userVerify(user);
        
        JSONObject objJson = db.insertUser(user, verify);
        
        when(request.getParameter("userName")).thenReturn("larissa soares");
        when(request.getParameter("password")).thenReturn("0123456");
        when(request.getParameter("email")).thenReturn("larissa.aoki07@inpe.br");
        when(request.getParameter("country")).thenReturn("Brazil");
        when(request.getParameter("phoneNumber")).thenReturn("1298765432100");
        when(request.getParameter("organization")).thenReturn("INPE");
        
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        when(response.getWriter()).thenReturn(pw);
        accessServlet.doGet(request, response);
        String result = sw.getBuffer().toString().trim();
        assertEquals(objJson.toString(), result);
    }
    @Test
    public void verifyUserNameErro3() throws JSONException, IOException, ServletException{
        user.setUserName(" ");
        User verify = userVerify(user);
        
        JSONObject objJson = db.insertUser(user, verify);
        
        when(request.getParameter("userName")).thenReturn(" ");
        when(request.getParameter("password")).thenReturn("0123456");
        when(request.getParameter("email")).thenReturn("larissa.aoki07@inpe.br");
        when(request.getParameter("country")).thenReturn("Brazil");
        when(request.getParameter("phoneNumber")).thenReturn("1298765432100");
        when(request.getParameter("organization")).thenReturn("INPE");
        
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        when(response.getWriter()).thenReturn(pw);
        accessServlet.doGet(request, response);
        String result = sw.getBuffer().toString().trim();
        assertEquals(objJson.toString(), result);
    }
    
    @Test
    public void verifyUserPasswordErro1() throws JSONException, IOException, ServletException{
        user.setPassword("1234");
        User verify = userVerify(user);
        
        JSONObject objJson = db.insertUser(user, verify);
        
        when(request.getParameter("userName")).thenReturn("larissa aoki");
        when(request.getParameter("password")).thenReturn("1234");
        when(request.getParameter("email")).thenReturn("larissa.aoki07@inpe.br");
        when(request.getParameter("country")).thenReturn("Brazil");
        when(request.getParameter("phoneNumber")).thenReturn("1298765432100");
        when(request.getParameter("organization")).thenReturn("INPE");
        
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        when(response.getWriter()).thenReturn(pw);
        accessServlet.doGet(request, response);
        String result = sw.getBuffer().toString().trim();
        assertEquals(objJson.toString(), result);
    }
    @Test
    public void verifyUserPasswordErro2() throws JSONException, IOException, ServletException{
        user.setPassword("");
        User verify = userVerify(user);
        
        JSONObject objJson = db.insertUser(user, verify);
        
        when(request.getParameter("userName")).thenReturn("larissa aoki");
        when(request.getParameter("password")).thenReturn("");
        when(request.getParameter("email")).thenReturn("larissa.aoki07@inpe.br");
        when(request.getParameter("country")).thenReturn("Brazil");
        when(request.getParameter("phoneNumber")).thenReturn("1298765432100");
        when(request.getParameter("organization")).thenReturn("INPE");
        
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        when(response.getWriter()).thenReturn(pw);
        accessServlet.doGet(request, response);
        String result = sw.getBuffer().toString().trim();
        assertEquals(objJson.toString(), result);
    }
    @Test
    public void verifyUserEmailErro1() throws JSONException, IOException, ServletException{
        user.setEmail("lariss@oki@inpe.br");
        User verify = userVerify(user);
        
        JSONObject objJson = db.insertUser(user, verify);
        
        when(request.getParameter("userName")).thenReturn("larissa aoki");
        when(request.getParameter("password")).thenReturn("0123456");
        when(request.getParameter("email")).thenReturn("lariss@oki@inpe.br");
        when(request.getParameter("country")).thenReturn("Brazil");
        when(request.getParameter("phoneNumber")).thenReturn("1298765432100");
        when(request.getParameter("organization")).thenReturn("INPE");
        
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        when(response.getWriter()).thenReturn(pw);
        accessServlet.doGet(request, response);
        String result = sw.getBuffer().toString().trim();
        assertEquals(objJson.toString(), result);
    }
    @Test
    public void verifyUserEmailErro2() throws JSONException, IOException, ServletException{
        user.setEmail("larissa aoki @inpe.br");
        User verify = userVerify(user);
        
        JSONObject objJson = db.insertUser(user, verify);
        
        when(request.getParameter("userName")).thenReturn("larissa aoki");
        when(request.getParameter("password")).thenReturn("0123456");
        when(request.getParameter("email")).thenReturn("larissa aoki @inpe.br");
        when(request.getParameter("country")).thenReturn("Brazil");
        when(request.getParameter("phoneNumber")).thenReturn("1298765432100");
        when(request.getParameter("organization")).thenReturn("INPE");
        
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        when(response.getWriter()).thenReturn(pw);
        accessServlet.doGet(request, response);
        String result = sw.getBuffer().toString().trim();
        assertEquals(objJson.toString(), result);
    }
    @Test
    public void verifyUserEmailErro3() throws JSONException, ServletException, IOException{
        user.setEmail(" ");
        User verify = userVerify(user);
        
        JSONObject objJson = db.insertUser(user, verify);
        
        when(request.getParameter("userName")).thenReturn("larissa aoki");
        when(request.getParameter("password")).thenReturn("0123456");
        when(request.getParameter("email")).thenReturn(" ");
        when(request.getParameter("country")).thenReturn("Brazil");
        when(request.getParameter("phoneNumber")).thenReturn("1298765432100");
        when(request.getParameter("organization")).thenReturn("INPE");
        
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        when(response.getWriter()).thenReturn(pw);
        accessServlet.doGet(request, response);
        String result = sw.getBuffer().toString().trim();
        assertEquals(objJson.toString(), result);
    }
    @Test
    public void verifyUserCountryErro() throws JSONException, IOException, ServletException{
        user.setCountry(" ");
        User verify = userVerify(user);
        
        JSONObject objJson = db.insertUser(user, verify);
        
        when(request.getParameter("userName")).thenReturn("larissa aoki");
        when(request.getParameter("password")).thenReturn("0123456");
        when(request.getParameter("email")).thenReturn("larissa.aoki07@inpe.br");
        when(request.getParameter("country")).thenReturn(" ");
        when(request.getParameter("phoneNumber")).thenReturn("1298765432100");
        when(request.getParameter("organization")).thenReturn("INPE");
        
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        when(response.getWriter()).thenReturn(pw);
        accessServlet.doGet(request, response);
        String result = sw.getBuffer().toString().trim();
        
        assertEquals(objJson.toString(), result);
    }
    @Test
    public void verifyUserPhoneErro1() throws JSONException, IOException, ServletException{
        user.setPhoneNumber("12987654321aa");
        User verify = userVerify(user);
        
        JSONObject objJson = db.insertUser(user, verify);
        
        when(request.getParameter("userName")).thenReturn("larissa aoki");
        when(request.getParameter("password")).thenReturn("0123456");
        when(request.getParameter("email")).thenReturn("larissa.aoki07@inpe.br");
        when(request.getParameter("country")).thenReturn("Brazil");
        when(request.getParameter("phoneNumber")).thenReturn("12987654321aa");
        when(request.getParameter("organization")).thenReturn("INPE");
        
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        when(response.getWriter()).thenReturn(pw);
        accessServlet.doGet(request, response);
        String result = sw.getBuffer().toString().trim();
        assertEquals(objJson.toString(), result);
    }
    @Test
    public void verifyUserPhoneErro2() throws JSONException, IOException, ServletException{
        user.setPhoneNumber(" ");
        User verify = userVerify(user);
        
        JSONObject objJson = db.insertUser(user, verify);
        
        when(request.getParameter("userName")).thenReturn("larissa aoki");
        when(request.getParameter("password")).thenReturn("0123456");
        when(request.getParameter("email")).thenReturn("larissa.aoki07@inpe.br");
        when(request.getParameter("country")).thenReturn("Brazil");
        when(request.getParameter("phoneNumber")).thenReturn(" ");
        when(request.getParameter("organization")).thenReturn("INPE");
        
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        when(response.getWriter()).thenReturn(pw);
        accessServlet.doGet(request, response);
        String result = sw.getBuffer().toString().trim();
        assertEquals(objJson.toString(), result);
    }
    @Test
    public void verifyUserOrganizationErro() throws JSONException, IOException, ServletException{
        user.setOrganization(" ");
        User verify = userVerify(user);
        
        JSONObject objJson = db.insertUser(user, verify);
        
        when(request.getParameter("userName")).thenReturn("larissa aoki");
        when(request.getParameter("password")).thenReturn("0123456");
        when(request.getParameter("email")).thenReturn("larissa.aoki07@inpe.br");
        when(request.getParameter("country")).thenReturn("Brazil");
        when(request.getParameter("phoneNumber")).thenReturn("1298765432100");
        when(request.getParameter("organization")).thenReturn(" ");
        
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        when(response.getWriter()).thenReturn(pw);
        accessServlet.doGet(request, response);
        String result = sw.getBuffer().toString().trim();
        assertEquals(objJson.toString(), result);
    }  
    
    @AfterAll
    public static void removeDatabase(){
        collection.deleteOne(document);
        collection.deleteOne(eq("userName", "larissa aoki"));
    }
    
    public User userVerify(User u) throws JSONException{
        
        User new_user = accessServlet.varifyUser(u.getUserName(), 
                u.getPassword(), u.getEmail(), u.getCountry(), 
                u.getPhoneNumber(), u.getOrganization());
        return new_user;
    }
}
