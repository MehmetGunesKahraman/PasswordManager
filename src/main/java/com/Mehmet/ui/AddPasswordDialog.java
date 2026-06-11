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
        setSize(400, 250);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        ImageIcon showPasswordIcon = new ImageIcon(getClass().getResource("/show.png"));
        Image scaledShow = showPasswordIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        final ImageIcon showIcon = new ImageIcon(scaledShow);

        ImageIcon hidePasswordIcon = new ImageIcon(getClass().getResource("/hide.png"));
        Image scaledHide = hidePasswordIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        final ImageIcon hideIcon = new ImageIcon(scaledHide);

        JLabel siteNameLabel = new JLabel("Site name");
        JLabel siteUsernameLabel = new JLabel("Username");
        JLabel sitePasswordLabel = new JLabel("Password");
        JLabel strongPasswordToolkit = new JLabel("?");
        JLabel showStrengthLabel = new JLabel("Password Strength");
        JLabel siteCategoryLabel = new JLabel("Category");

        JTextField siteNameField = new JTextField(20);
        JTextField siteUsernameField = new JTextField(20);
        JPasswordField sitePasswordField = new JPasswordField(20);
        JTextField siteCategoryField = new JTextField(20);

        JButton showPasswordIconButton = new JButton(showIcon);
        showPasswordIconButton.setMargin(new Insets(0, 0, 0, 0));
        showPasswordIconButton.setBorderPainted(false);
        showPasswordIconButton.setContentAreaFilled(false);
        showPasswordIconButton.setFocusPainted(false);
        showPasswordIconButton.setOpaque(false);

        JButton saveButton = new JButton("Save");

        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(siteNameLabel , gbc);
        gbc.gridx = 1;
        gbc.gridy = 0;
        mainPanel.add(siteNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        mainPanel.add(siteUsernameLabel , gbc);
        gbc.gridx = 1;
        gbc.gridy = 1;
        mainPanel.add(siteUsernameField , gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        mainPanel.add(sitePasswordLabel , gbc);
        gbc.gridx = 1;
        gbc.gridy = 2;
        mainPanel.add(sitePasswordField , gbc);
        gbc.gridx = 2;
        gbc.gridy = 2;
        mainPanel.add(showPasswordIconButton, gbc);
        gbc.gridx = 3;
        gbc.gridy = 2;
        mainPanel.add(strongPasswordToolkit , gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        mainPanel.add(showStrengthLabel , gbc);
        gbc.gridx = 1;
        gbc.gridy = 3;
        mainPanel.add(passwordstrengthLabel , gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        mainPanel.add(siteCategoryLabel , gbc);
        gbc.gridx = 1;
        gbc.gridy = 4;
        mainPanel.add(siteCategoryField , gbc);

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

        showPasswordIconButton.addActionListener(e -> {
            if (sitePasswordField.getEchoChar() == 0) {
                sitePasswordField.setEchoChar('•');
                showPasswordIconButton.setIcon(showIcon);
            } else {
                sitePasswordField.setEchoChar((char) 0);
                showPasswordIconButton.setIcon(hideIcon);
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
