package com.Mehmet.ui;

import javax.swing.*;


public class MainPage extends JFrame {
    private int userId;

    public MainPage(int userId) {
        this.userId = userId;
        setTitle("Main");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
