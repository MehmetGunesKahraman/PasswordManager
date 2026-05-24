package com.Mehmet.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.StringSelection;

public class ShowPasswordDialog extends JDialog {
    public ShowPasswordDialog(String passwordShow) {
        setTitle("Show Password");
        setSize(300, 150);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel passwordLabel = new JLabel("Password: ");
        JTextField showPasswordField = new JTextField(passwordShow);
        showPasswordField.setEditable(false);
        showPasswordField.setBorder(null);
        showPasswordField.setBackground(null);

        showPasswordField.setToolTipText(passwordShow);

        mainPanel.add(passwordLabel, BorderLayout.WEST);
        mainPanel.add(showPasswordField, BorderLayout.CENTER);
        add(mainPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton okButton = new JButton("OK");
        JButton copyButton = new JButton("Copy");

        buttonPanel.add(okButton);
        buttonPanel.add(copyButton);
        add(buttonPanel, BorderLayout.SOUTH);

        copyButton.addActionListener(e -> {
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(passwordShow), null);
            copyButton.setText("Copied!");
            Timer timer = new Timer(1500, evt -> copyButton.setText("Copy"));
            timer.setRepeats(false);
            timer.start();
        });

        okButton.addActionListener(e -> dispose());

        setLocationRelativeTo(null);
        setVisible(true);
    }
}