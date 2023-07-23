package de.emir.passwordmanager;

import de.emir.passwordmanager.ui.PasswordManagerGUI;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                PasswordManagerGUI passwordManagerGUI = new PasswordManagerGUI();
                passwordManagerGUI.showGUI();
            }
        });
    }
}