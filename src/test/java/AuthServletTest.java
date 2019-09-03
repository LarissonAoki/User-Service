
import auth.AuthServlet;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
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
import java.io.PrintWriter;
import java.io.StringWriter;
import javax.servlet.ServletException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterAll;

public class AuthServletTest {
    
    AuthServlet authServlet = new AuthServlet();
    Database db = new Database();
    static MongoCollection collection;
    static Document document;
    
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
    }
    
    @Test
    public void AuthTrue() throws JSONException, IOException, ServletException{
        
        JSONObject objJson = db.searchUser("larissa soares", "0123456");
        
        when(request.getParameter("userName")).thenReturn("larissa soares");
        when(request.getParameter("password")).thenReturn("0123456");
        
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        when(response.getWriter()).thenReturn(pw);
        
        authServlet.doGet(request, response);
        String result = sw.getBuffer().toString().trim();
        
        assertEquals(objJson.toString(), result);
    }
    @Test
    public void AuthFalse() throws JSONException, IOException, ServletException{
        
        JSONObject objJson = db.searchUser("larissa soares", "6543210");
        
        when(request.getParameter("userName")).thenReturn("larissa soares");
        when(request.getParameter("password")).thenReturn("6543210");
        
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        when(response.getWriter()).thenReturn(pw);
        
        authServlet.doGet(request, response);
        String result = sw.getBuffer().toString().trim();
        
        assertEquals(objJson.toString(), result);
    }
    @Test
    public void AuthNull() throws JSONException, IOException, ServletException{
        
        JSONObject objJson = db.searchUser("Larissa Soares Aoki", "0123456");
        
        when(request.getParameter("userName")).thenReturn("Larissa Soares Aoki");
        when(request.getParameter("password")).thenReturn("0123456");
        
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        when(response.getWriter()).thenReturn(pw);
        
        authServlet.doGet(request, response);
        String result = sw.getBuffer().toString().trim();
        
        assertEquals(objJson.toString(), result);
    }
    
    @AfterAll
    public static void removeDatabase(){
        collection.deleteOne(document);
    }
}
