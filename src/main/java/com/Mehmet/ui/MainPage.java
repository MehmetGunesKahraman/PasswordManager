package com.Mehmet.ui;

import com.Mehmet.dao.PasswordsDao;
import com.Mehmet.model.PasswordEntry;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Objects;


public class MainPage extends JFrame {
    private int userId;

    public MainPage(int userId, String masterPassword) {
        this.userId = userId;
        ArrayList<PasswordEntry> passwords = PasswordsDao.getPassword(userId);
        setTitle("Main");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        Object[][] data = new Object[passwords.size()][4];
        for (int i = 0; i < passwords.size(); i++) {
            data[i][0] = passwords.get(i).getSiteName();
            data[i][1] = passwords.get(i).getSiteUsername();
            data[i][2] = passwords.get(i).getSitePassword();
            data[i][3] = passwords.get(i).getCategory();
        }

        String[] columns = {"Site name", "Username", "Password", "Category"};
        JTable table = new JTable(data, columns);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane);
    }
}
