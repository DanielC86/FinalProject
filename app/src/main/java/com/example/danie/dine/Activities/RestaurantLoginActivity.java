package com.example.danie.dine.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.danie.dine.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RestaurantLoginActivity extends AppCompatActivity {


    private EditText rEmail;
    private EditText rPassword;
    private Button btnRLogin;
    private Button btnRExit;
    private Button btnRestaurantRegister;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_login);

        mAuth = FirebaseAuth.getInstance();

        //check if user already logged in
        if (mAuth.getCurrentUser() != null){
            //start home activity
            RestaurantLogin();
        }

        rEmail = findViewById(R.id.rEmail);
        rPassword = findViewById(R.id.rPassword);
        btnRLogin =  findViewById(R.id.btnRLogin);
        btnRExit = findViewById(R.id.btnRExit);
        btnRestaurantRegister = findViewById(R.id.btnRestaurantRegister);


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

        btnRestaurantRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                restaurantRegister();
            }
        });
    }

    private void Exit(){
        finish();
        System.exit(0);
    }

    private void RestaurantLogin(){
        //method for restaurant login
        //get user input
        String restaurantEmail = rEmail.getText().toString().trim();
        String restaurantPassword = rPassword.getText().toString().trim();

        //check if the fields are empty
        if (restaurantEmail.isEmpty()) {
            showMessage("Please fill in email address!");
            btnRLogin.setVisibility(View.VISIBLE);
            //progressBarLogin.setVisibility(View.INVISIBLE);
        }
        else if (restaurantPassword.isEmpty()) {
            showMessage("Please fill in password!");
            btnRLogin.setVisibility(View.VISIBLE);
            //progressBarLogin.setVisibility(View.INVISIBLE);
        }
        else {
            //user login
            mAuth.signInWithEmailAndPassword(restaurantEmail, restaurantPassword)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                updateUI();
                            }
                        }
                    });
        }

    }

    //messaging method
    private void showMessage(String s) {
        Toast.makeText(getApplicationContext(), s,Toast.LENGTH_SHORT).show();
    }

    private void updateUI(){
        Intent RestaurantViewActivity = new Intent (getApplicationContext(), RestaurantViewActivity.class);
        startActivity(RestaurantViewActivity);
    }

    private void restaurantRegister(){
        Intent RestaurantRegisterActivity = new Intent(getApplicationContext(), com.example.danie.dine.Activities.RestaurantRegisterActivity.class);
        startActivity(RestaurantRegisterActivity);
    }
}
