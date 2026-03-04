package ui;
import service.CipherEngine;
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
        JTextField usernameField = new JTextField(12);
        JPasswordField passwordField = new JPasswordField(12);
        JButton loginButton = new JButton("Login");
        JButton registerButton = new JButton("Register");

        // Layout Settings
        mainPanel.setLayout(new GridLayout(3, 2, 8, 8));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel usernameLabel = new JLabel("Username");
        mainPanel.add(usernameLabel);
        mainPanel.add(usernameField);

        JLabel passwordLabel = new JLabel("Master Password");
        mainPanel.add(passwordLabel);
        mainPanel.add(passwordField);

        loginButton.setPreferredSize(new Dimension(80, 25));
        registerButton.setPreferredSize(new Dimension(80, 25));
        loginButton.setMargin(new Insets(2, 5, 2, 5));
        registerButton.setMargin(new Insets(2 ,5 ,2 ,5));
        mainPanel.add(loginButton);
        mainPanel.add(registerButton);

        add(mainPanel, BorderLayout.CENTER);

        setVisible(true);
    }
}