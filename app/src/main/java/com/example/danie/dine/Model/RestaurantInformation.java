package com.example.danie.dine.Model;

public class RestaurantInformation {

    private String restaurantName;
    private String restaurantPhone;
    private String restaurantEmail;

    public RestaurantInformation(){
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getRestaurantPhone() {
        return restaurantPhone;
    }

    public void setRestaurantPhone(String restaurantPhone) {
        this.restaurantPhone = restaurantPhone;
    }

    public String getRestaurantEmail() {
        return restaurantEmail;
    }

    public void setRestaurantEmail(String restaurantEmail) {
        this.restaurantEmail = restaurantEmail;
    }

    public RestaurantInformation(String restaurantName, String restaurantPhone, String restaurantEmail){
        this.restaurantName = restaurantName;
        this.restaurantPhone = restaurantPhone;
        this.restaurantEmail = restaurantEmail;
    }
}
