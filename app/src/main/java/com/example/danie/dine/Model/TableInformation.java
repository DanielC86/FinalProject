package com.example.danie.dine.Model;

public class TableInformation {

    private String bookingName;
    private String bookingEmail;
    private String bookingPhone;
    private String bookingDate;
    private String bookingTime;
    private String bookingGuestNumber;
    private String bookingStatus;
    private String requestKey;

    public TableInformation(){

    }

    public String getBookingName() {
        return bookingName;
    }

    public void setBookingName(String bookingName) {
        this.bookingName = bookingName;
    }

    public String getBookingEmail() {
        return bookingEmail;
    }

    public void setBookingEmail(String bookingEmail) {
        this.bookingEmail = bookingEmail;
    }

    public String getBookingPhone() {
        return bookingPhone;
    }

    public void setBookingPhone(String bookingPhone) {
        this.bookingPhone = bookingPhone;
    }

    public String getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(String bookingDate) {
        this.bookingDate = bookingDate;
    }

    public String getBookingTime() {
        return bookingTime;
    }

    public void setBookingTime(String bookingTime) {
        this.bookingTime = bookingTime;
    }

    public String getBookingGuestNumber() {
        return bookingGuestNumber;
    }

    public String getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(String bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    public void setBookingGuestNumber(String bookingGuestNumber) {
        this.bookingGuestNumber = bookingGuestNumber;
    }


    public String getRequestKey() {
        return requestKey;
    }


    public void setRequestKey(String requestKey) {
        this.requestKey = requestKey;
    }

    public TableInformation(String bookingName, String bookingEmail, String bookingPhone, String bookingDate, String bookingTime, String bookingGuestNumber, String bookingStatus){
        this.bookingName = bookingName;
        this.bookingEmail = bookingEmail;
        this.bookingPhone = bookingPhone;
        this.bookingDate = bookingDate;
        this.bookingTime = bookingTime;
        this.bookingGuestNumber = bookingGuestNumber;
        this.bookingStatus = bookingStatus;
        this.requestKey = requestKey;
    }
}
