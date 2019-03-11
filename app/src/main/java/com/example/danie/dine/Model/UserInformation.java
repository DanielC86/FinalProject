package com.example.danie.dine.Model;

public class UserInformation {

    public String userName;
    public String phoneNumber;
    public String userEmail;


    public UserInformation(){
    }

    public UserInformation(String userName, String phoneNumber, String userEmail) {
        this.userName = userName;
        this.phoneNumber = phoneNumber;
        this.userEmail = userEmail;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
}
