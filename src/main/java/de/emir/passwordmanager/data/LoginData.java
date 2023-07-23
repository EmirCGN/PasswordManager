package de.emir.passwordmanager.data;


public class LoginData {
    private String website;
    private String username;
    private String password;
    private String type;

    public LoginData(String website, String username, String password, String type) {
        this.website = website;
        this.username = username;
        this.password = password;
        this.type = type;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Website: " + website + ", Username: " + username + ", Password: " + password + ", Type: " + type;
    }
}