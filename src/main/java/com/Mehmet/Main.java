package com.Mehmet;

import com.Mehmet.ui.LoginPage;
import com.Mehmet.dao.*;
import com.Mehmet.ui.MainPage;

public class Main {
    public static void main(String[] args) {
        DatabaseManager.initializeDatabase();
        LoginPage Pencere = new LoginPage();
    }

}
