package com.Mehmet.ui;

import com.Mehmet.Main;
import com.Mehmet.dao.PasswordsDao;

import javax.swing.*;
import java.awt.*;

public class EditPasswordDialog extends JDialog {
    public EditPasswordDialog(MainPage mainPage, String siteName, String siteUserName, String sitePassword, String siteCategory, int passwordId) {
        setTitle("Edit");
        setSize(350, 250);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JTextField siteNameField = new JTextField(20);
        siteNameField.setText(siteName);
        JTextField siteUsernameField = new JTextField(20);
        siteUsernameField.setText(siteUserName);
        JPasswordField sitePasswordField = new JPasswordField(20);
        sitePasswordField.setText(sitePassword);
        JTextField siteCategoryField = new JTextField(20);
        siteCategoryField.setText(siteCategory);

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
            String save_siteName = siteNameField.getText();
            String save_siteUsername = siteUsernameField.getText();
            char[] passwordChars = sitePasswordField.getPassword();
            String save_sitePassword = new String(passwordChars);
            String save_siteCategory = siteCategoryField.getText();
            if (save_siteName.isEmpty() || save_siteUsername.isEmpty() || save_sitePassword.isEmpty() || save_siteCategory.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill all the spaces.");
                return;
            }
            if (PasswordsDao.editPassword(save_siteName, save_siteUsername, save_sitePassword, save_siteCategory, passwordId)) {
                JOptionPane.showMessageDialog(this, "Edited Successfully!");
                mainPage.refreshTable();
                dispose();
            }
        });
        setVisible(true);
    }
}
