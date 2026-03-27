package com.Mehmet.dao;

import com.Mehmet.model.PasswordEntry;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PasswordsDao {
    public static ArrayList<PasswordEntry> getPassword(int user_id) {
        String sql = "SELECT * FROM passwords WHERE user_id = ?";
        ArrayList<PasswordEntry> userthigs = new ArrayList<PasswordEntry>();

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, user_id);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                PasswordEntry entry = new PasswordEntry(
                        rs.getString("site_name"),
                        rs.getString("site_username"),
                        rs.getString("site_password"),
                        rs.getString("category"),
                        rs.getInt("user_id"),
                        rs.getInt("password_id")
                        );
                userthigs.add(entry);
            }
            return userthigs;

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}
