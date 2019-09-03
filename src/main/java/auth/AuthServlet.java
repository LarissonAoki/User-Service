
package auth;

import model.Database;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONException;
import org.json.JSONObject;

@WebServlet(name = "auth", urlPatterns = {"/auth"}, loadOnStartup = 1)
public class AuthServlet extends HttpServlet{
    
    Database db = new Database();
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException{
        
        try {
            String userName = request.getParameter("userName");
            String password = request.getParameter("password");
            
            JSONObject objJson = db.searchUser(userName, password);
            
            PrintWriter pw = response.getWriter();
            response.setContentType("application/json");
            pw.write(objJson.toString());
            
        } catch (JSONException ex) {
            Logger.getLogger(AuthServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    
    public void doPost(HttpServletRequest request, HttpServletResponse response){
    
    }
}
