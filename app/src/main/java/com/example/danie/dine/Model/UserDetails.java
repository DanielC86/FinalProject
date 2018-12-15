package com.example.danie.dine.Model;

public class UserDetails {

    private String email;
    private String firstName;
    private String phone;

    //public userDetails(){}

    public UserDetails(String firstName, String email, String phone){

        firstName = firstName;

        email = email;

        phone = phone;


    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}


