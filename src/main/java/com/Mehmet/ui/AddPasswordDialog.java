package com.Mehmet.ui;
import com.Mehmet.dao.PasswordsDao;

import javax.swing.*;
import java.awt.*;

public class AddPasswordDialog extends JDialog {
    public AddPasswordDialog(int userId, MainPage mainPage) {

        setTitle("Add Password");
        setSize(350, 250);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JTextField sitenameField = new JTextField(20);
        JTextField siteusernameField = new JTextField(20);
        JPasswordField sitepasswordField = new JPasswordField(20);
        JTextField sitecategoryField = new JTextField(20);

        JLabel sitenameLabel = new JLabel("Site name");
        JLabel siteusernameLabel = new JLabel("Username");
        JLabel sitepasswordLabel = new JLabel("Password");
        JLabel sitecategoryLabel = new JLabel("Category");

        JButton saveButton = new JButton("Save");

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(4, 2, 5, 5));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 10, 30, 10));
        mainPanel.add(sitenameLabel);
        mainPanel.add(sitenameField);

        mainPanel.add(siteusernameLabel);
        mainPanel.add(siteusernameField);

        mainPanel.add(sitepasswordLabel);
        mainPanel.add(sitepasswordField);

        mainPanel.add(sitecategoryLabel);
        mainPanel.add(sitecategoryField);
        add(mainPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(saveButton);
        add(buttonPanel, BorderLayout.SOUTH);

        saveButton.addActionListener(e -> {
            String sitename = sitenameField.getText();
            String siteusername = siteusernameField.getText();
            char[] passwordChars = sitepasswordField.getPassword();
            String sitepassword = new String(passwordChars);
            String sitecategory = sitecategoryField.getText();
            if (sitename.isEmpty() || siteusername.isEmpty() || sitepassword.isEmpty() || sitecategory.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill all the spaces.");
                return;
            }
            if (PasswordsDao.addPassword(sitename, siteusername, sitepassword, sitecategory, userId)) {
                JOptionPane.showMessageDialog(this, "Added Successfully!");
                mainPage.refreshTable();
                dispose();
            }
        });
        setVisible(true);
    }
}
