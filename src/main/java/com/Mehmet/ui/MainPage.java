package com.Mehmet.ui;

import com.Mehmet.dao.PasswordsDao;
import com.Mehmet.model.PasswordEntry;
import com.Mehmet.service.CipherEngine;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;


public class MainPage extends JFrame {
    private int userId;
    private JTable table;
    private DefaultTableModel tableModel;
    private int shift;
    CipherEngine CP = new CipherEngine();

    public void refreshTable() {
        ArrayList<PasswordEntry> passwords = PasswordsDao.getPassword(this.userId);
        tableModel.setRowCount(0);
        for (PasswordEntry p : passwords) {
            tableModel.addRow(new Object[] {
                    p.getSiteName(),
                    p.getSiteUsername(),
                    "*********",
                    p.getCategory(),
                    p.getPasswordId()
            });
        }
    }

    public MainPage(int userId, String masterPassword) {
        this.userId = userId;
        this.shift = CP.calculateShift(masterPassword);

        setTitle("Main");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        String[] columns = {"Site name", "Username", "Password", "Category", "password_id"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(tableModel);
        table.getColumnModel().getColumn(4).setWidth(0);
        table.getColumnModel().getColumn(4).setMinWidth(0);
        table.getColumnModel().getColumn(4).setMaxWidth(0);

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();

        JButton addPasswordButton = new JButton("Add");
        buttonPanel.add(addPasswordButton);

        JButton editPasswordButton = new JButton("Edit");
        buttonPanel.add(editPasswordButton);

        JButton showPasswordButton = new JButton("Show Password");
        buttonPanel.add(showPasswordButton);

        JButton deletePasswordButton = new JButton("Delete");
        buttonPanel.add(deletePasswordButton);

        add(buttonPanel, BorderLayout.SOUTH);

        addPasswordButton.addActionListener(e -> {
            new AddPasswordDialog(userId, this, masterPassword, shift);
        });


        editPasswordButton.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Please select a row first.");
                return;
            }
            String siteName = (String)tableModel.getValueAt(row, 0);
            String siteUserName = (String)tableModel.getValueAt(row, 1);
            String sitePassword = (String)tableModel.getValueAt(row, 2);
            String siteCategory = (String)tableModel.getValueAt(row, 3);
            int passwordId = (int)tableModel.getValueAt(row, 4);

            new EditPasswordDialog(this, siteName, siteUserName, sitePassword, siteCategory, passwordId, shift);
        });

        showPasswordButton.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Please select a row first.");
                return;
            }
            int passwordId = (int)tableModel.getValueAt(row, 4);
            String realPassword = PasswordsDao.showOnlyPassword(passwordId);
            if (realPassword.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Password couldn't found");
                return;
            } else {
                String decryptedPassword = CP.decrypt(realPassword, shift);
                JOptionPane.showMessageDialog(this, "Password: " + decryptedPassword);

            }
        });

        deletePasswordButton.addActionListener( e -> {
            int row = table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Please select a row first.");
                return;
            }
            int passwordId = (int)tableModel.getValueAt(row, 4);
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this password?", "WARNING!", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                if (PasswordsDao.deletePassword(passwordId)) {
                    JOptionPane.showMessageDialog(this, "Password Deleted!");
                    refreshTable();
                }
            }
        });

        refreshTable();

        setVisible(true);
    }
}
