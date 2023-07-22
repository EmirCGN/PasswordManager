package de.emir.passwordmanager.data;


public class LoginData {
    private String website;
    private String username;
    private String password;

    public LoginData(String website, String username, String password) {
        this.website = website;
        this.username = username;
        this.password = password;
    }

    // Getter und Setter (falls benötigt)

    @Override
    public String toString() {
        return "Website: " + website + ", Benutzername: " + username + ", Passwort: " + password;
    }
}