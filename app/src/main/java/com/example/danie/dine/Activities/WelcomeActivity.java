package com.example.danie.dine.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.danie.dine.Model.UserInformation;
import com.example.danie.dine.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class WelcomeActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference mRef;
    private FirebaseDatabase mDatabase;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private String userRole;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance();
        mRef = mDatabase.getReference().child("Users");

        userRole = "";


        //check if user already logged in
        if (mAuth.getCurrentUser() != null){
            userID = currentUser.getUid();
            updateUI();
        }
        else {
            showMessage("Please login");
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

        //get user type
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserInformation userDetails = dataSnapshot.getValue(UserInformation.class);
                userDetails.getUserType();

                userDetails.setUserType(dataSnapshot.child(userID).getValue(UserInformation.class).getUserType()); //get user type
                String userType = userDetails.getUserType();
                if (userType.equals("User")) {
                    Intent homeActivity = new Intent(getApplicationContext(), HomeActivity.class);
                    startActivity(homeActivity);
                    finish();
                }
                else if (userType.equals("Restaurant")) {
                    Intent restaurantViewActivity = new Intent(getApplicationContext(), RestaurantViewActivity.class);
                    startActivity(restaurantViewActivity);
                    finish();
                }
                else {
                    Intent loginActivity = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(loginActivity);
                    finish();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    //restaurant menu screen
    private void BusinessLogin(){
        Intent restaurantLoginActivity = new Intent(getApplicationContext(), RestaurantLoginActivity.class);
        startActivity(restaurantLoginActivity);
    }

    private void showMessage(String message) {

        //showing toast message method
        Toast.makeText(getApplicationContext(), message,Toast.LENGTH_SHORT).show();
    }
}
