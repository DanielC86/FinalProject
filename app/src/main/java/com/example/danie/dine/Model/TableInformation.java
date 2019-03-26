package com.example.danie.dine.Model;

public class TableInformation {

    private String userName;
    private String userPhone;
    private String reservationDate;
    private String reservationTime;
    private int guestNumber;

    public TableInformation(){
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(String reservationDate) {
        this.reservationDate = reservationDate;
    }

    public String getReservationTime() {
        return reservationTime;
    }

    public void setReservationTime(String reservationTime) {
        this.reservationTime = reservationTime;
    }

    public int getGuestNumber() {
        return guestNumber;
    }

    public void setGuestNumber(int guestNumber) {
        this.guestNumber = guestNumber;
    }

    public TableInformation(String userName, String userPhone, String reservationDate, String reservationTime, int guestNumber){
        this.userName = userName;
        this.userPhone = userPhone;
        this.reservationDate = reservationDate;
        this.reservationTime = reservationTime;
        this.guestNumber = guestNumber;
    }
}
