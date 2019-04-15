package com.example.danie.dine.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.danie.dine.R;
import com.google.firebase.auth.FirebaseAuth;

public class WelcomeActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        mAuth = FirebaseAuth.getInstance();


        //check if user already logged in
        if (mAuth.getCurrentUser() != null){
            //start home activity
            updateUI();
        }

        Button btnRestaurant = (Button)findViewById(R.id.btnRestaurant);

        Button btnWelcomeLogin = (Button)findViewById(R.id.btnWelcomeLogin);
        btnWelcomeLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent welcomeLoginIntent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(welcomeLoginIntent);
            }
        });

        Button btnWelcomeRegister = (Button)findViewById(R.id.btnWelcomeRegister);
        btnWelcomeRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent welcomeRegisterIntent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(welcomeRegisterIntent);
            }
        });

        Button btnWelcomeExit = (Button) findViewById(R.id.btnWelcomeExit);
        btnWelcomeExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                System.exit(0);
            }
        });

        //go for restaurant login
        btnRestaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BusinessLogin();
            }
        });
    }

    //normal user menu screen
    private void updateUI() {
        Intent homeActivity = new Intent(getApplicationContext(), HomeActivity.class);
        startActivity(homeActivity);
        finish();
    }
    //restaurant menu screen
    private void BusinessLogin(){
        Intent restaurantLoginActivity = new Intent(getApplicationContext(), RestaurantLoginActivity.class);
        startActivity(restaurantLoginActivity);
    }
}
