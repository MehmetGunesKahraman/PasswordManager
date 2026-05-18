package com.Mehmet.ui;

import com.Mehmet.dao.UserDao;
import com.Mehmet.service.CipherEngine;
import javax.swing.*;
import java.awt.*;

public class LoginPage extends JFrame {
    public LoginPage() {
        CipherEngine CP = new CipherEngine();

        // Setting the window settings
        setTitle("Password Manager");
        setSize(350, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel settings
        JPanel mainPanel = new JPanel();
        JTextField usernameField = new JTextField(20);
        JPasswordField passwordField = new JPasswordField(20);
        JButton loginButton = new JButton("Login");
        JButton registerButton = new JButton("Register");

        SwingUtilities.invokeLater(() -> {
            JRootPane root = getRootPane();
            if (root != null) {
                root.setDefaultButton(loginButton);
            }
        });

        // Layout Settings
        mainPanel.setLayout(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Username Label
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.3;
        JLabel usernameLabel = new JLabel("Username");
        mainPanel.add(usernameLabel, gbc);

        // Username Field
        gbc.gridx = 1;
        gbc.weightx = 0.7;
        mainPanel.add(usernameField, gbc);

        // Password Label
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.3;
        JLabel passwordLabel = new JLabel("Password");
        mainPanel.add(passwordLabel, gbc);

        // Password Field
        gbc.gridx = 1;
        gbc.weightx = 0.7;
        mainPanel.add(passwordField, gbc);

        // Buttons row
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);
        mainPanel.add(buttonPanel, gbc);


        loginButton.addActionListener(  e -> {
            String username = usernameField.getText();
            if (username.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter a username.");
                return;
            }
            char[] passwordChar = passwordField.getPassword();
            String password = new String(passwordChar);
            if (password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter a password.");
                return;
            }

            int shift = CP.calculateShift(password);
            String encryptedPassword = CP.encrypt(password, shift);

            int userId = UserDao.loginUser(username, encryptedPassword);
            if (userId >= 1) {
                new MainPage(userId, password);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Username or password is wrong!");
            }
        });


        registerButton.addActionListener(e -> {
            String username = usernameField.getText();
            if (username.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter a username.");
                return;
            }
            char[] passwordChars = passwordField.getPassword();
            String password = new String(passwordChars);
            if (password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter a password.");
                return;
            }

            int shift = CP.calculateShift(password);
            String encryptedPassword = CP.encrypt(password, shift);

            if (UserDao.registerUser(username, encryptedPassword)) {
                JOptionPane.showMessageDialog(this, "User registered successfully.");
            } else {
                JOptionPane.showMessageDialog(this, "User already exists!");
                return;
            }
        });

        add(mainPanel);

        setVisible(true);
    }
}