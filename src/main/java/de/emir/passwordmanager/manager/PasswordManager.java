package de.emir.passwordmanager.manager;

import de.emir.passwordmanager.data.LoginData;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class PasswordManager implements Serializable {
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

    public void saveLoginData() {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("logins.dat"))) {
            outputStream.writeObject(logins);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadLoginData() {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("logins.dat"))) {
            logins = (List<LoginData>) inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public List<LoginData> searchLoginData(String keyword) {
        List<LoginData> filteredLogins = new ArrayList<>();
        for (LoginData login : logins) {
            if (login.getWebsite().contains(keyword)
                    || login.getUsername().contains(keyword)
                    || login.getType().contains(keyword)) {
                filteredLogins.add(login);
            }
        }
        return filteredLogins;
    }

    public void sortLoginData(Comparator<LoginData> comparator) {
        logins.sort(comparator);
    }
}
