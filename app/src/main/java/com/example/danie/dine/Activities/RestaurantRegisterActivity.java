package com.example.danie.dine.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.danie.dine.Model.RestaurantInformation;
import com.example.danie.dine.Model.UserInformation;
import com.example.danie.dine.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RestaurantRegisterActivity extends AppCompatActivity {


    private EditText regRestName;
    private EditText regRestEmail;
    private EditText regRestPhone;
    private EditText regRestPassword1;
    private EditText regRestPassword2;
    private Button btnRegRestSubmit;
    private ProgressBar regRestProgressBar;

    //firebase stuff for restaurant
    private FirebaseAuth rAuth;
    private DatabaseReference rRef;

    //firebase stuff for user
    private FirebaseAuth uAuth;
    private DatabaseReference uRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_register);

        //initialize firebase for restaurant
        rAuth = FirebaseAuth.getInstance();
        rRef = FirebaseDatabase.getInstance().getReference().child("Restaurants");

        //initialize firebase for user
        uAuth = FirebaseAuth.getInstance();
        uRef = FirebaseDatabase.getInstance().getReference().child("Users");

        //UI elements
        regRestName = findViewById(R.id.regRestName);
        regRestEmail = findViewById(R.id.regRestEmail);
        regRestPhone = findViewById(R.id.regRestPhone);
        regRestPassword1 = findViewById(R.id.regRestPassword1);
        regRestPassword2 = findViewById(R.id.regRestPassword2);
        btnRegRestSubmit = findViewById(R.id.btnRegRestSubmit);
        regRestProgressBar = findViewById(R.id.regRestProgressBar);

        btnRegRestSubmit.setVisibility(View.VISIBLE);
        regRestProgressBar.setVisibility(View.INVISIBLE);

        //submitting registration
        btnRegRestSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnRegRestSubmit.setVisibility(View.INVISIBLE);
                regRestProgressBar.setVisibility(View.VISIBLE);
                registerNewRestaurant();
            }
        });
    }

    private void registerNewRestaurant(){
        String restName = regRestName.getText().toString().trim();
        String restEmail = regRestEmail.getText().toString().trim();
        String restPhone = regRestPhone.getText().toString().trim();
        String restPassword1 = regRestPassword1.getText().toString().trim();
        String restPassword2 = regRestPassword2.getText().toString().trim();

        //basic error trapping
        if (restName.isEmpty()) {
            showMessage("Please fill in Restaurant name!");
            btnRegRestSubmit.setVisibility(View.VISIBLE);
            regRestProgressBar.setVisibility(View.INVISIBLE);
        }
        else if (restEmail.isEmpty()) {
            showMessage("Please fill in Email Address!");
            btnRegRestSubmit.setVisibility(View.VISIBLE);
            regRestProgressBar.setVisibility(View.INVISIBLE);
        }
        else if (restPhone.isEmpty()) {
            showMessage("Please enter your phone number!");
            btnRegRestSubmit.setVisibility(View.VISIBLE);
            regRestProgressBar.setVisibility(View.INVISIBLE);
        }
        else if (restPassword1.isEmpty()) {
            showMessage("please fill in your password");
            btnRegRestSubmit.setVisibility(View.VISIBLE);
            regRestProgressBar.setVisibility(View.INVISIBLE);
        }
        else if (!restPassword1.equals(restPassword2)) {
            showMessage("Password does not match!");
            btnRegRestSubmit.setVisibility(View.VISIBLE);
            regRestProgressBar.setVisibility(View.INVISIBLE);
        }
        else {
            rAuth.createUserWithEmailAndPassword(restEmail, restPassword1)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {
                                showMessage("Restaurant Registered!");
                                restaurantLogin();
                                storeRestaurantInfo();
                                storeUserInfo();
                            }
                        }
                    });

        }
    }


    private void showMessage(String message) {
        //showing toast message method
        Toast.makeText(getApplicationContext(), message,Toast.LENGTH_SHORT).show();
    }
    private void restaurantLogin(){
        Intent RestaurantViewActivity = new Intent(getApplicationContext(), RestaurantViewActivity.class);
        startActivity(RestaurantViewActivity);
        finish();
    }

    //storing restaurant info into database
    private void storeRestaurantInfo(){
        //method to store restaurant info in restaurant table
        String restaurantName = regRestName.getText().toString().trim();
        String restaurantPhone = regRestPhone.getText().toString().trim();
        String restaurantEmail = regRestEmail.getText().toString().trim();

        RestaurantInformation currentRestaurant = new RestaurantInformation(restaurantName, restaurantPhone, restaurantEmail);
        FirebaseUser firebaseResaurant = rAuth.getCurrentUser();
        rRef.child(firebaseResaurant.getUid()).setValue(currentRestaurant);
    }

    //storing restaurant user info
    private void storeUserInfo(){
        //method to store restaurant user info in user table with restaurant user type
        String userName = regRestName.getText().toString().trim();
        String phoneNumber = regRestPhone.getText().toString().trim();
        String userEmail = regRestEmail.getText().toString().trim();
        String userType = "Restaurant";

        UserInformation currentUser = new UserInformation(userName, phoneNumber, userEmail, userType);
        FirebaseUser firebaseUser = uAuth.getCurrentUser();
        uRef.child(firebaseUser.getUid()).setValue(currentUser);
    }

    @Override
    protected void onStart() {
        super.onStart();

    }
}
