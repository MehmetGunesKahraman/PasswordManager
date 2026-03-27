package com.Mehmet.ui;

import com.Mehmet.dao.PasswordsDao;
import com.Mehmet.model.PasswordEntry;

import javax.swing.*;
import java.util.ArrayList;


public class MainPage extends JFrame {
    private int userId;

    public MainPage(int userId) {
        this.userId = userId;
        ArrayList<PasswordEntry> passwords = PasswordsDao.getPassword(userId);
        setTitle("Main");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
