package com.Mehmet.ui;

import com.Mehmet.dao.PasswordsDao;
import com.Mehmet.model.PasswordEntry;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;


public class MainPage extends JFrame {
    private int userId;
    private JTable table;
    private DefaultTableModel tableModel;

    public MainPage(int userId, String masterPassword) {
        this.userId = userId;
        ArrayList<PasswordEntry> passwords = PasswordsDao.getPassword(userId);
        setTitle("Main");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        String[] columns = {"Site name", "Username", "Password", "Category"};
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane);

        JButton addPasswordButton = new JButton("Add Password");
        add(addPasswordButton, BorderLayout.SOUTH);

        addPasswordButton.addActionListener(e -> {
            new AddPasswordDialog(userId, this);
        });
        refreshTable();
    }

    public void refreshTable() {
        ArrayList<PasswordEntry> passwords = PasswordsDao.getPassword(this.userId);
        tableModel.setRowCount(0);
        for (PasswordEntry p : passwords) {
            tableModel.addRow(new Object[] {
                    p.getSiteName(),
                    p.getSiteUsername(),
                    p.getSitePassword(),
                    p.getCategory()
            });
        }
    }
}
