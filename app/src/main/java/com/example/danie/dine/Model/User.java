package com.example.danie.dine.Model;

public class User {

    private String email;
    private String name;
    private String password;
    private String phone;

    public User() {

    }

    public User(String email, String password, String phone, String name) {

        email = email;
        password = password;
        name = name;
        phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String regEmail) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
