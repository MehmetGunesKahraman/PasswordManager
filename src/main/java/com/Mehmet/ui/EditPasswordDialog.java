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
        setSize(370, 250);
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
        JLabel strongPasswordToolkit = new JLabel("?");
        JLabel siteCategoryLabel = new JLabel("Category");

        JButton saveButton = new JButton("Save");

        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(siteNameLabel ,gbc);
        gbc.gridx = 1;
        gbc.gridy = 0;
        mainPanel.add(siteNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        mainPanel.add(siteUsernameLabel ,gbc);
        gbc.gridx = 1;
        gbc.gridy = 1;
        mainPanel.add(siteUsernameField ,gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        mainPanel.add(sitePasswordLabel ,gbc);
        gbc.gridx = 1;
        gbc.gridy = 2;
        mainPanel.add(sitePasswordField ,gbc);
        gbc.gridx = 2;
        gbc.gridy = 2;
        mainPanel.add(strongPasswordToolkit ,gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        mainPanel.add(showStrengthLabel ,gbc);
        gbc.gridx = 1;
        gbc.gridy = 3;
        mainPanel.add(passwordstrengthLabel ,gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        mainPanel.add(siteCategoryLabel ,gbc);
        gbc.gridx = 1;
        gbc.gridy = 4;
        mainPanel.add(siteCategoryField ,gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        mainPanel.add(saveButton ,gbc);

        add(mainPanel, BorderLayout.CENTER);

        strongPasswordToolkit.setToolTipText(
                "For a strong password:\n" +
                        "Length: minimum 8 characters, ideal 12+\n" +
                        "Uppercase: minimum 1\n" +
                        "Lowercase: minimum 1\n" +
                        "Number: minimum 1\n" +
                        "Special character: minimum 1 (!@#$%^&*)"
        );

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