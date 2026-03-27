package com.Mehmet.model;

public class PasswordEntry {
    private String siteName;
    private String siteUsername;
    private String sitePassword;
    private String category;
    private int userId;
    private int passwordId;


    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getSiteUsername() {
        return siteUsername;
    }

    public void setSiteUsername(String siteUsername) {
        this.siteUsername = siteUsername;
    }

    public int getUserId() {
        return userId;
    }

    public int getPasswordId() {
        return passwordId;
    }

    public String getSitePassword() {
        return sitePassword;
    }

    public void setSitePassword(String sitePassword) {
        this.sitePassword = sitePassword;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public PasswordEntry(String siteName, String siteUsername, String sitePassword, String category, int userId, int passwordId) {
        this.siteName = siteName;
        this.siteUsername = siteUsername;
        this.sitePassword = sitePassword;
        this.category = category;
        this.userId = userId;
        this.passwordId = passwordId;
    }
}