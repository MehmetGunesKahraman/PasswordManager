package com.Mehmet.ui;
import com.Mehmet.dao.PasswordsDao;
import com.Mehmet.service.CipherEngine;

import javax.swing.*;
import java.awt.*;

public class AddPasswordDialog extends JDialog {
    public AddPasswordDialog(int userId, MainPage mainPage, String masterPassword, int shift) {
        CipherEngine CP = new CipherEngine();

        setTitle("Add Password");
        setSize(350, 250);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JTextField siteNameField = new JTextField(20);
        JTextField siteUsernameField = new JTextField(20);
        JPasswordField sitePasswordField = new JPasswordField(20);
        JTextField siteCategoryField = new JTextField(20);

        JLabel siteNameLabel = new JLabel("Site name");
        JLabel siteUsernameLabel = new JLabel("Username");
        JLabel sitePasswordLabel = new JLabel("Password");
        JLabel siteCategoryLabel = new JLabel("Category");

        JButton saveButton = new JButton("Save");

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(4, 2, 5, 5));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 10, 30, 10));
        mainPanel.add(siteNameLabel);
        mainPanel.add(siteNameField);

        mainPanel.add(siteUsernameLabel);
        mainPanel.add(siteUsernameField);

        mainPanel.add(sitePasswordLabel);
        mainPanel.add(sitePasswordField);

        mainPanel.add(siteCategoryLabel);
        mainPanel.add(siteCategoryField);
        add(mainPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(saveButton);
        add(buttonPanel, BorderLayout.SOUTH);

        saveButton.addActionListener(e -> {
            String siteName = siteNameField.getText();
            String siteUsername = siteUsernameField.getText();
            char[] passwordChars = sitePasswordField.getPassword();
            String sitePassword = new String(passwordChars);
            String siteCategory = siteCategoryField.getText();
            if (siteName.isEmpty() || siteUsername.isEmpty() || sitePassword.isEmpty() || siteCategory.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill all the spaces.");
                return;
            }

            String encryptedPassword = CP.encrypt(sitePassword, shift);
            if (PasswordsDao.addPassword(siteName, siteUsername, encryptedPassword, siteCategory, userId)) {
                JOptionPane.showMessageDialog(this, "Added Successfully!");
                mainPage.refreshTable();
                dispose();
            }
        });
        setVisible(true);
    }
}
