package com.Mehmet.dao;

import com.Mehmet.model.PasswordEntry;
import com.Mehmet.ui.MainPage;

import java.lang.reflect.UndeclaredThrowableException;
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
                        rs.getInt("password_id"),
                        rs.getString("site_name"),
                        rs.getString("site_username"),
                        rs.getString("site_password"),
                        rs.getString("category"),
                        rs.getInt("user_id")
                        );
                userthigs.add(entry);
            }
            return userthigs;

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public static boolean addPassword(String site_name, String site_username, String site_password, String category, int user_id) {
        String sql = "INSERT INTO passwords (site_name, site_username, site_password, category, user_id) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseManager.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, site_name);
            pstmt.setString(2, site_username);
            pstmt.setString(3, site_password);
            pstmt.setString(4, category);
            pstmt.setInt(5, user_id);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Password Add error: " + e.getMessage());
            return false;
        }
    }

    public static boolean editPassword(String site_name, String site_username, String site_password, String category, int passwordId) {
        String sql = "UPDATE passwords SET site_name = ?, site_username = ?, site_password = ?, category = ? WHERE password_id = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, site_name);
            pstmt.setString(2, site_username);
            pstmt.setString(3, site_password);
            pstmt.setString(4, category);
            pstmt.setInt(5, passwordId);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Password edit error: " + e.getMessage());
            return false;
        }
    }

    public static boolean deletePassword(int passwordId) {
        String sql = "DELETE FROM passwords WHERE password_id = ?";

        try (Connection conn = DatabaseManager.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, passwordId);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Password delete error: " + e.getMessage());
            return false;
        }

    }

    public static String showOnlyPassword(int passwordId) {
        String sql = "SELECT site_password FROM passwords WHERE password_id = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, passwordId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getString("site_password");
            } else {
                return "";
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            return "";
        }
    }
}
