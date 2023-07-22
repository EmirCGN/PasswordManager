package de.emir.passwordmanager.manager;

import de.emir.passwordmanager.data.LoginData;

import java.util.ArrayList;
import java.util.List;

public class PasswordManager {
    private List<LoginData> logins;

    public PasswordManager() {
        logins = new ArrayList<>();
    }

    public void addLoginData(LoginData login) {
        logins.add(login);
    }

    public void removeLoginData(int index) {
        logins.remove(index);
    }

    public List<LoginData> getLogins() {
        return logins;
    }
}