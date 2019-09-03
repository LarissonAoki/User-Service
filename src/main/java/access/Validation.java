
package access;

import model.Database;
import org.json.JSONException;
import org.json.JSONObject;

public class Validation {
    
    public String verifyName (String userName) throws JSONException{
        
        String stop = "";
        String listCharacters = "!@#$%&*(){}/?<>+.,:;";
        char[] character = listCharacters.toCharArray();
        
        if(verifySpace(userName).length() > 0){
            for(int i=0; i<userName.length(); i++){
                for (char c: character){
                   if(c == userName.charAt(i)){
                       stop = "character";
                       break;
                   }
                }
                if(stop.equals("character")){
                    break;
                }
            }
            if(!stop.equals("character")){
                JSONObject obj = new Database().searchUser(userName, "");
                if(!obj.get("userName").equals("null")){
                    userName = "existent";
                }
            }else{
                userName = "character impromper";
            }
        }else{
            userName = "invalid: blanck space";
        }
        return userName;
    }
    
    public String verifyPassword(String password){
        if(verifySpace(password).length() > 0){
            if(password.length() < 6){
                password = "invalid size";
            }
        }else{
            password = "invalid: blanck space";
        }
        return password;
    }
    
    public String verifyPhone(String phone){
        
        if(verifySpace(phone).length() == phone.length() && verifySpace(phone).length() > 0){
            if(!phone.matches("\\d{"+phone.length()+"}")){
                phone = "invalid: only number";
            }
        }else{
            phone = "invalid: blanck space";
        }
        return phone;
    }
    
    public String verifyEmail(String email){
        
        String spaceEmail = verifySpace(email);
        
        if(spaceEmail.length() >  0){
            if(spaceEmail.length() == email.length()){
                String[] part1 = email.split("@");
                if (part1.length == 2){
                    String[] part2 = part1[1].split("\\.");
                
                    if(part2.length >= 2){
                        boolean numeros2 = part2[0].matches("\\d{"+part2[0].length()+"}");
                        if(numeros2 != false){
                            email = "email invalid";
                        }
                    }else{
                        email = "email invalid";
                    }
                }else{
                    email = "email invalid";
                }
            
            }else{
                email = "invalid: have space";
            }
        }else{
            email = "invalid: blanck space";
        }
        
        return email;
    }
    
    public String verifySpace(String text){
        text = text.replace(" ", "");
        return text;
    }
    
}
