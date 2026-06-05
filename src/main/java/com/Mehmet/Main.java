package com.Mehmet;

import com.Mehmet.ui.LoginPage;
import com.Mehmet.dao.*;
import com.Mehmet.ui.MainPage;
import com.formdev.flatlaf.FlatDarkLaf;

public class Main {
    public static void main(String[] args) {
        FlatDarkLaf.setup();
        DatabaseManager.initializeDatabase();
        LoginPage window = new LoginPage();
    }

}
