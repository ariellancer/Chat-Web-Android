package com.example.exe3.infoToDB;
public class User {
    private String username;
    private String password;
    private String displayName;
    private String profilePic;

    public User(String username, String password, String profilePic, String displayName) {
        this.username = username;
        this.password = password;
        this.profilePic = profilePic;
        this.displayName = displayName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
}
