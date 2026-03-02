package model;

public class PasswordEntry {
    private String name;
    private String password;
    private String category;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String  getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public PasswordEntry(String name, String password, String category) {
        this.name = name;
        this.password = password;
        this.category = category;
    }

}