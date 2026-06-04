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
    private ArrayList<PasswordEntry> allPasswords;
    CipherEngine CP = new CipherEngine();

    public void refreshTable() {
        this.allPasswords = PasswordsDao.getPassword(this.userId);
        tableModel.setRowCount(0);
        for (PasswordEntry p : allPasswords) {
            tableModel.addRow(new Object[] {
                    p.getSiteName(),
                    p.getSiteUsername(),
                    "*********",
                    p.getCategory(),
                    p.getPasswordId()
            });
        }
    }

    public void filterTable(String searchTerm, String searchType) {
        tableModel.setRowCount(0);
        if (searchType.equals("Site Name")) {
            for (PasswordEntry p : allPasswords) {
                if (p.getSiteName().toLowerCase().contains(searchTerm.toLowerCase())) {
                    tableModel.addRow(new Object[] {
                            p.getSiteName(),
                            p.getSiteUsername(),
                            "*********",
                            p.getCategory(),
                            p.getPasswordId()
                    });
                }
            }
        }

        if (searchType.equals("Username")) {
            for (PasswordEntry p : allPasswords) {
                if (p.getSiteUsername().toLowerCase().contains(searchTerm.toLowerCase())) {
                    tableModel.addRow(new Object[] {
                            p.getSiteName(),
                            p.getSiteUsername(),
                            "*********",
                            p.getCategory(),
                            p.getPasswordId()
                    });
                }
            }
        }

        if (searchType.equals("Category")) {
            for (PasswordEntry p : allPasswords) {
                if (p.getCategory().toLowerCase().contains(searchTerm.toLowerCase())) {
                    tableModel.addRow(new Object[] {
                            p.getSiteName(),
                            p.getSiteUsername(),
                            "*********",
                            p.getCategory(),
                            p.getPasswordId()
                    });
                }
            }
        }
    }

    public MainPage(int userId, String masterPassword) {
        this.userId = userId;
        this.shift = CP.calculateShift(masterPassword);

        setTitle("Main");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JLabel searchByLabel = new JLabel("Search by: ");
        JComboBox<String> searchType = new JComboBox<>(new String[]{"Site Name", "Username", "Category"});
        JTextField searchField = new JTextField(10);
        JButton searchButton = new JButton("Search");
        JButton refreshButton = new JButton("Refresh");
        searchPanel.add(searchByLabel);
        searchPanel.add(searchType);
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        searchPanel.add(refreshButton);
        add(searchPanel, BorderLayout.NORTH);

        searchButton.addActionListener(e -> {
            String searchValue = searchField.getText();
            String selectedSearchType = (String) searchType.getSelectedItem();
            if (searchValue.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter something to search.");
            }
            filterTable(searchValue, selectedSearchType);
        });

        refreshButton.addActionListener(e -> {
            refreshTable();
        });

        String[] columns = {"Site name", "Username", "Password", "Category", "password_id"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(tableModel);
        table.setAutoCreateRowSorter(true);
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
                new ShowPasswordDialog(decryptedPassword);

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
