package com.example.danie.dine.Model;

public class User {

    private String email;
    private String firstName;
    private String login;
    private String password1;
    private String phone;

    public User() {

    }

    public User(String email, String password1, String phone, String name) {

        email = email;
        password1 = password1;
        name = firstName;
        phone = phone;
        login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String regEmail) {
        this.email = email;
    }

    public String getName() {
        return firstName;
    }

    public void setName(String name) {
        this.firstName = firstName;
    }

    public String getPassword() {
        return password1;
    }

    public void setPassword(String password) {
        this.password1 = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}
