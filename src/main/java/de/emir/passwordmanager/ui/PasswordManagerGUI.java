package de.emir.passwordmanager.ui;

import de.emir.passwordmanager.data.LoginData;
import de.emir.passwordmanager.manager.PasswordManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class PasswordManagerGUI extends JFrame {

        private PasswordManager passwordManager;
        private DefaultListModel<LoginData> listModel;
        private JList<LoginData> loginList;
        private JLabel dashboardLabel;

        public PasswordManagerGUI() {
                super("Password Manager");
                passwordManager = new PasswordManager();

                // Darkmode Look-and-Feel (Nimbus)
                try {
                        UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
                        UIManager.put("nimbusBase", new Color(18, 30, 49));
                        // ... (Other UI manager settings)
                } catch (Exception e) {
                        e.printStackTrace();
                }

                // GUI components
                listModel = new DefaultListModel<>();
                loginList = new JList<>(listModel);
                loginList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                JScrollPane scrollPane = new JScrollPane(loginList);
                JButton addButton = new JButton("Add Login");
                JButton deleteButton = new JButton("Delete Login");

                // Dashboard-Label
                dashboardLabel = new JLabel("Dashboard: All");

                // Layout
                setLayout(new BorderLayout());
                add(scrollPane, BorderLayout.CENTER);

                JPanel buttonPanel = new JPanel();
                buttonPanel.add(addButton);
                buttonPanel.add(deleteButton);
                add(buttonPanel, BorderLayout.SOUTH);

                add(dashboardLabel, BorderLayout.NORTH);

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

                // Start the background thread
                BackgroundThread backgroundThread = new BackgroundThread();
                backgroundThread.start();

                // Load login data from file
                passwordManager.loadLoginData();

                // Add search bar
                addSearchBar();
        }

        public void showGUI() {
                File file = new File("logins.dat");
                if (!file.exists()) {
                        try {
                                file.createNewFile();
                        } catch (IOException e) {
                                e.printStackTrace();
                        }
                }
                setVisible(true);
                // Example: Logins in the GUI display
                updateLoginList();
        }

        private String generateRandomPassword() {
                int passwordLength = 12; // Set the desired password length
                String allowedChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
                SecureRandom random = new SecureRandom();
                StringBuilder password = new StringBuilder(passwordLength);
                for (int i = 0; i < passwordLength; i++) {
                        int randomIndex = random.nextInt(allowedChars.length());
                        password.append(allowedChars.charAt(randomIndex));
                }
                return password.toString();
        }

        private void showAddDialog() {
                JPanel inputPanel = new JPanel(new GridLayout(0, 1));
                JTextField websiteField = new JTextField();
                JTextField usernameField = new JTextField();
                JPasswordField passwordField = new JPasswordField();
                JComboBox<String> typeComboBox = new JComboBox<>(new String[]{"Website", "Email", "Application"});
                inputPanel.add(new JLabel("Website:"));
                inputPanel.add(websiteField);
                inputPanel.add(new JLabel("Username:"));
                inputPanel.add(usernameField);
                inputPanel.add(new JLabel("Password:"));
                inputPanel.add(passwordField);
                inputPanel.add(new JLabel("Type:"));
                inputPanel.add(typeComboBox);

                JButton generatePasswordButton = new JButton("Generate Password");
                inputPanel.add(generatePasswordButton);

                generatePasswordButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                                String generatedPassword = generateRandomPassword();
                                passwordField.setText(generatedPassword);
                        }
                });

                int result = JOptionPane.showConfirmDialog(this, inputPanel,
                        "Add New Login", JOptionPane.OK_CANCEL_OPTION);
                if (result == JOptionPane.OK_OPTION) {
                        String website = websiteField.getText();
                        String username = usernameField.getText();
                        String password = new String(passwordField.getPassword());
                        String type = typeComboBox.getSelectedItem().toString();
                        if (!website.isEmpty() && !username.isEmpty() && !password.isEmpty()) {
                                LoginData newLogin = new LoginData(website, username, password, type);
                                passwordManager.addLoginData(newLogin);
                                saveLoginData(); // Save the updated list of logins
                                updateLoginList();
                        } else {
                                JOptionPane.showMessageDialog(this, "Please fill in all fields!", "Error", JOptionPane.ERROR_MESSAGE);
                                showAddDialog();
                        }
                }
        }

        private void deleteSelectedLogin() {
                int selectedIndex = loginList.getSelectedIndex();
                if (selectedIndex != -1) {
                        passwordManager.removeLoginData(selectedIndex);
                        saveLoginData(); // Save the updated list of logins
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

        private void saveLoginData() {
                // Save login data to file
                passwordManager.saveLoginData();
        }

        private void addSearchBar() {
                JTextField searchField = new JTextField();
                JButton searchButton = new JButton("Search");

                searchButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                                String searchKeyword = searchField.getText();
                                filterLoginList(searchKeyword);
                        }
                });

                JPanel searchPanel = new JPanel(new BorderLayout());
                searchPanel.add(searchField, BorderLayout.CENTER);
                searchPanel.add(searchButton, BorderLayout.EAST);

                add(searchPanel, BorderLayout.NORTH);
        }

        private void filterLoginList(String keyword) {
                listModel.clear();
                List<LoginData> filteredLogins = passwordManager.searchLoginData(keyword);
                for (LoginData login : filteredLogins) {
                        listModel.addElement(login);
                }
        }

}
