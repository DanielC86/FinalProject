package com.example.danie.dine.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.danie.dine.R;

public class RestaurantLoginActivity extends AppCompatActivity {


    private TextView rEmail;
    private TextView rPassword;
    private Button btnRLogin;
    private Button btnRExit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_login);

        rEmail = findViewById(R.id.rEmail);
        rPassword = findViewById(R.id.rPassword);
        btnRLogin =  findViewById(R.id.btnRLogin);
        btnRExit = findViewById(R.id.btnRExit);


        btnRExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Exit();
            }
        });

        btnRLogin.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RestaurantLogin();
            }
        }));
    }

    private void Exit(){
        finish();
        System.exit(0);
    }

    private void RestaurantLogin(){
        //method for restaurant login
    }
}
