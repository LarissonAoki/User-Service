

package access;

import auth.AuthServlet;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Database;
import model.User;
import org.json.JSONException;
import org.json.JSONObject;

@WebServlet(name = "access", urlPatterns = {"/access"}, loadOnStartup = 1)


public class AccessServlet extends HttpServlet {
    
    Database db = new Database();
    Validation validade = new Validation();
   
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String userName = request.getParameter("userName");
            String password = request.getParameter("password");
            String email = request.getParameter("email");
            String country = request.getParameter("country");
            String phone = request.getParameter("phoneNumber");
            String org = request.getParameter("organization");
            
            User origin = new User("", userName, password, email, country, phone, org);
            
            User verify = varifyUser(userName, password, email, country, phone, org);
            
            JSONObject objJson = db.insertUser(origin, verify);
            
            PrintWriter pw = response.getWriter();
            response.setContentType("application/json");
            pw.write(objJson.toString());
            
        } catch (JSONException ex) {
            Logger.getLogger(AuthServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }
    
    public User varifyUser (String userName, String password, String email, 
            String coutry, String phone, String org) throws JSONException{
        
        String checkName = validade.verifyName(userName);
        String checkPassword = validade.verifyPassword(password);
        String checkEmail = validade.verifyEmail(email);
        String checkCountry = validade.verifySpace(coutry);
        String checkPhone = validade.verifyPhone(phone);
        String checkOrg = validade.verifySpace(org);
        
        if(checkCountry.length() <= 0){
            checkCountry = "invalid: blanck space";
        }
        if(checkOrg.length() <= 0){
            checkOrg = "invalid: blanck space";
        }
        
        
        User user = new User("", checkName, checkPassword, checkEmail, checkCountry, checkPhone, checkOrg);
        
        return user;
    }
}
