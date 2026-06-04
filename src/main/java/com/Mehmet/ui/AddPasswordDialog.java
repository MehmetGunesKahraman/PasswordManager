package com.Mehmet.ui;
import com.Mehmet.dao.PasswordsDao;
import com.Mehmet.service.CipherEngine;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;

public class AddPasswordDialog extends JDialog {
    private JLabel passwordstrengthLabel = new JLabel();
    void checkStrength(String sField) {
        int criteria = 0;

        /* if (sField.length() < 8) {
            passwordstrengthLabel.setText("Min 8 letters are recommended");
            return;
        } */

        if (sField.length() >= 12 ) {
            criteria++;

        }
        boolean containsDigit = sField.matches(".*\\d.*");
        if (containsDigit) {
            criteria++;
        }

        boolean hasUppercase = sField.matches(".*[A-Z].*");
        if (hasUppercase) {
            criteria++;
        }

        boolean hasLowercase = sField.matches(".*[a-z].*");
        if (hasLowercase) {
            criteria++;
        }

        boolean hasSpecial = sField.matches(".*[!@#$%^&*(),.?\":{}|<>].*");
        if (hasSpecial) {
            criteria++;
        }

        if (criteria <= 2) {
            passwordstrengthLabel.setText("Weak");
            passwordstrengthLabel.setForeground(Color.RED);
        }
        else if (criteria <= 4) {
            passwordstrengthLabel.setText("Middle");
            passwordstrengthLabel.setForeground(Color.ORANGE);
        } else {
            passwordstrengthLabel.setText("Strong");
            passwordstrengthLabel.setForeground(Color.GREEN);
        }
    }

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
        JLabel showStrengthLabel = new JLabel("Password Strength");
        JLabel siteCategoryLabel = new JLabel("Category");

        JButton saveButton = new JButton("Save");

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(5, 2, 5, 5));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 10, 30, 10));
        mainPanel.add(siteNameLabel);
        mainPanel.add(siteNameField);

        mainPanel.add(siteUsernameLabel);
        mainPanel.add(siteUsernameField);

        mainPanel.add(sitePasswordLabel);
        mainPanel.add(sitePasswordField);
        mainPanel.add(showStrengthLabel);
        mainPanel.add(passwordstrengthLabel);

        mainPanel.add(siteCategoryLabel);
        mainPanel.add(siteCategoryField);
        add(mainPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(saveButton);
        add(buttonPanel, BorderLayout.SOUTH);

        sitePasswordField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                char[] charsPassword = sitePasswordField.getPassword();
                String changingPassword = new String(charsPassword);
                checkStrength(changingPassword);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                char[] charsPassword = sitePasswordField.getPassword();
                String changingPassword = new String(charsPassword);
                checkStrength(changingPassword);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                char[] charsPassword = sitePasswordField.getPassword();
                String changingPassword = new String(charsPassword);
                checkStrength(changingPassword);
            }
        });

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
