package de.emir.passwordmanager.ui;




import de.emir.passwordmanager.data.LoginData;
import de.emir.passwordmanager.manager.PasswordManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class PasswordManagerGUI extends JFrame {

    private PasswordManager passwordManager;
    private DefaultListModel<LoginData> listModel;
    private JList<LoginData> loginList;

    public PasswordManagerGUI() {
        super("Passwort Manager");
        passwordManager = new PasswordManager();

        // GUI-Komponenten
        listModel = new DefaultListModel<>();
        loginList = new JList<>(listModel);
        JButton addButton = new JButton("Hinzufügen");
        JButton deleteButton = new JButton("Löschen");

        // Layout
        setLayout(new BorderLayout());
        add(new JScrollPane(loginList), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Button-Listener
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAddDialog();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteSelectedLogin();
            }
        });

        // Fenster-Optionen
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void showGUI() {
        setVisible(true);
        // Beispiel: Vorab-Daten hinzufügen
        passwordManager.addLoginData(new LoginData("example.com", "benutzername123", "geheim123"));
        passwordManager.addLoginData(new LoginData("app.xyz", "user567", "password789"));

        // Beispiel: Logins in der GUI anzeigen
        updateLoginList();
    }

    private void showAddDialog() {
        String website = JOptionPane.showInputDialog(this, "Website:");
        String username = JOptionPane.showInputDialog(this, "Benutzername:");
        String password = JOptionPane.showInputDialog(this, "Passwort:");

        if (website != null && username != null && password != null) {
            LoginData newLogin = new LoginData(website, username, password);
            passwordManager.addLoginData(newLogin);
            updateLoginList();
        }
    }

    private void deleteSelectedLogin() {
        int selectedIndex = loginList.getSelectedIndex();
        if (selectedIndex != -1) {
            passwordManager.removeLoginData(selectedIndex);
            updateLoginList();
        }
    }

    private void updateLoginList() {
        listModel.clear();
        List<LoginData> logins = passwordManager.getLogins();
        for (LoginData login : logins) {
            listModel.addElement(login);
        }
    }
}