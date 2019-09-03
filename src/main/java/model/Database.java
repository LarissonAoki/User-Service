
package model;

import access.Validation;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.eq;

import org.json.JSONException;
import org.json.JSONObject;

import org.bson.Document;

public class Database {
    
    MongoClient cliente;
    MongoDatabase database; 
    MongoCollection<Document> collection;
    
    public Database() {
        cliente = new MongoClient("localhost", 27017);
        database = cliente.getDatabase("User");
        collection = database.getCollection("userData");       
        
        /*        
        Document documento = new Document("userName", "larissa")
                .append("password", "12345")
                .append("email", "larissa.aoki@inpe.br")
                .append("country", "Brazil")
                .append("phoneNumber", "12991172356")
                .append("organization", "INPE");
        
        collection.insertOne(documento);*/
        
    }
    
    public JSONObject searchUser (String userName, String password) throws JSONException{
        
        JSONObject objJson = new JSONObject();
        
        FindIterable<Document> list = collection.find(eq("userName", userName));
        
        User user = convertUser(list);
        
        if(user == null){
            objJson.put("id", "null");
            objJson.put("auth", "null");
            objJson.put("userName", "null");
            objJson.put("password", "null");
            objJson.put("email", "null");
            objJson.put("country", "null");            
            objJson.put("phone number", "null");
            objJson.put("organization", "null");
        }else {
            if(user.getPassword().equals(password)){
                objJson.put("id", user.getId());
                objJson.put("auth", "true");
                objJson.put("userName", user.getUserName());
                objJson.put("password", user.getPassword());
                objJson.put("email", user.getEmail());
                objJson.put("country", user.getCountry());            
                objJson.put("phone number", user.getPhoneNumber());
                objJson.put("organization", user.getOrganization());
                
            }else {
                objJson.put("id", user.getId());
                objJson.put("auth", "false");
                objJson.put("userName", user.getUserName());
                objJson.put("password", "invalid");
                objJson.put("email", "null");
                objJson.put("country", "null");            
                objJson.put("phone number", "null");
                objJson.put("organization", "null");
            }
        }
        return objJson;
    }
    
    public User convertUser (FindIterable<Document> list){        
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
        
        return user;
    }
    
    public JSONObject insertUser(User origin, User verify) throws JSONException{
        JSONObject objJson = new JSONObject();
        
        boolean name = origin.getUserName().equals(verify.getUserName());
        boolean pass = origin.getPassword().equals(verify.getPassword());
        boolean email = origin.getEmail().equals(verify.getEmail());
        boolean country = origin.getCountry().equals(verify.getCountry());
        boolean phone = origin.getPhoneNumber().equals(verify.getPhoneNumber());
        boolean org = origin.getOrganization().equals(verify.getOrganization());
        
        if(name && pass && email && country && phone && org){
          Document documento = new Document("userName", verify.getUserName())
                .append("password", verify.getPassword())
                .append("email", verify.getEmail())
                .append("country", verify.getCountry())
                .append("phoneNumber", verify.getPhoneNumber())
                .append("organization", verify.getOrganization());
          
          collection.insertOne(documento);  
          
          FindIterable<Document> list = collection.find(eq("userName", verify.getUserName()));
          
          User user = convertUser(list);
          
          objJson.put("id", user.getId());
          objJson.put("subscribe", "true");
          objJson.put("userName", user.getUserName());
          objJson.put("password", user.getPassword());
          objJson.put("email", user.getEmail());
          objJson.put("country", user.getCountry());
          objJson.put("phone number", user.getPhoneNumber());
          objJson.put("organization", user.getOrganization());
          
        }else{
            objJson.put("id", "");
            objJson.put("subscribe", "false");
            objJson.put("userName", verify.getUserName());
            objJson.put("password", verify.getPassword());
            objJson.put("email", verify.getEmail());
            objJson.put("country", verify.getCountry());
            objJson.put("phone number", verify.getPhoneNumber());
            objJson.put("organization", verify.getOrganization());
        }
        return objJson;
    }
    
    
    public void listar(){
        FindIterable<Document> list = collection.find();
        
        for(Document d: list){
            System.out.println(d);
        }
    }   
}
