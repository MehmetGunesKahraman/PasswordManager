package com.Mehmet.ui;

import com.Mehmet.dao.PasswordsDao;
import com.Mehmet.service.CipherEngine;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;

public class EditPasswordDialog extends JDialog {
    private JLabel passwordstrengthLabel = new JLabel();

    CipherEngine CP = new CipherEngine();

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

    public EditPasswordDialog(MainPage mainPage, String siteName, String siteUserName, String sitePassword, String siteCategory, int passwordId, int shift) {
        setTitle("Edit");
        setSize(350, 250);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        //DS
        String encryptedRealPassword = PasswordsDao.showOnlyPassword(passwordId);
        String decryptedRealPassword = CP.decrypt(encryptedRealPassword, shift);

        JTextField siteNameField = new JTextField(20);
        siteNameField.setText(siteName);
        JTextField siteUsernameField = new JTextField(20);
        siteUsernameField.setText(siteUserName);

        //DS
        JPasswordField sitePasswordField = new JPasswordField(20);
        sitePasswordField.setText(decryptedRealPassword);
        checkStrength(decryptedRealPassword);

        JTextField siteCategoryField = new JTextField(20);
        siteCategoryField.setText(siteCategory);

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
            String save_siteName = siteNameField.getText();
            String save_siteUsername = siteUsernameField.getText();
            char[] passwordChars = sitePasswordField.getPassword();
            String save_sitePassword = new String(passwordChars);
            String save_siteCategory = siteCategoryField.getText();

            if (save_siteName.isEmpty() || save_siteUsername.isEmpty() || save_sitePassword.isEmpty() || save_siteCategory.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill all the spaces.");
                return;
            }

            String encryptedPassword = CP.encrypt(save_sitePassword, shift);
            if (PasswordsDao.editPassword(save_siteName, save_siteUsername, encryptedPassword, save_siteCategory, passwordId)) {
                JOptionPane.showMessageDialog(this, "Edited Successfully!");
                mainPage.refreshTable();
                dispose();
            }
        });
        setVisible(true);
    }
}