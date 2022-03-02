package com.example.ra.Utils;

public class users {
    public String username,email,role,password,profileuri,userid,priority;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getProfileuri() {
        return profileuri;
    }

    public void setProfileuri(String profileuri) {
        this.profileuri = profileuri;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public users(String username, String email, String role, String password, String profileuri, String userid, String priority) {
        this.username = username;
        this.email = email;
        this.role = role;
        this.password = password;
        this.profileuri = profileuri;
        this.userid = userid;
        this.priority = priority;
    }

    public users() {
    }
}
