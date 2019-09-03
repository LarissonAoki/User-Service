/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author larissa
 */
public class User {
    
    public String id;
    public String userName;
    public String password;
    public String email;
    public String country;
    public String phoneNumber;
    public String organization;

    public User(String id, String userName, String password, String email, String country, String phoneNumber, String organization) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.country = country;
        this.phoneNumber = phoneNumber;
        this.organization = organization;
    }
    
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }
    
     @Override
    public boolean equals(Object o){
        if (o instanceof User){
            User u = (User) o;
            if (u.getId().equals(this.id)){
                if (u.getUserName().equals(this.userName)){
                    if (u.getEmail().equals(this.email)){
                        if (u.getCountry().equals(this.country)){
                            if (u.getPhoneNumber().equals(this.phoneNumber)){
                                if (u.getOrganization().equals(this.organization)){
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
    
}
