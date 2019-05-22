package com.example.danie.dine.Model;

import com.google.firebase.database.Exclude;

public class DinnerEvent {

    String dinnerRequest;

    String requestKey;

    public DinnerEvent(){

    }

    public String getDinnerRequest() {
        return dinnerRequest;
    }

    public void setDinnerRequest(String dinnerRequest) {
        this.dinnerRequest = dinnerRequest;
    }

    @Exclude
    public String getRequestKey() {
        return requestKey;
    }

    @Exclude
    public void setRequestKey(String requestKey) {
        this.requestKey = requestKey;
    }

    public DinnerEvent (String dinnerRequest){
        this.dinnerRequest = dinnerRequest;
    }
}
