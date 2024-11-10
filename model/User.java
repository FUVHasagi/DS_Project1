package model;

public class User {
    private String username;
    private String role;
    private String DisplayName;
    private String password;

    public User(String username, String role) {
        this.username = username;
        this.role = role;
    }

    public User(String username, String role, String DisplayName, String password) {
        this.username = username;
        this.role = role;
        this.DisplayName = DisplayName;
        this.password = password;
    }

    public String getPassword(){
        return password;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public User(String DisplayName) {
        this.DisplayName = DisplayName;
    }

    public void setUsername(String username) {this.username = username;}

    public String getUsername() {return this.username;}


    public void setRole(String priviledgeID) {this.role = priviledgeID;}

    public String getRole() {return role;}

    public void setDisplayName(String displayName) {this.DisplayName = displayName;}
    public String getDisplayName() {return DisplayName;}

}