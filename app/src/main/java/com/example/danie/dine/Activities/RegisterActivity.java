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

import com.example.danie.dine.Model.UserInformation;
import com.example.danie.dine.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {



    private EditText regFirstName;
    private EditText regEmail;
    private EditText regPhone;
    private EditText regPassword1;
    private EditText regPassword2;
    private ProgressBar progressBarRegister;
    private Button btnRegConfirm;

    private FirebaseAuth mAuth;

    private DatabaseReference mRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //initialize firebase
        mAuth = FirebaseAuth.getInstance();

        mRef = FirebaseDatabase.getInstance().getReference().child("Users");

        regFirstName = findViewById(R.id.regFirstName);
        regEmail = findViewById(R.id.loginEmail);
        regPhone = findViewById(R.id.regPhone);
        regPassword1 = findViewById(R.id.regPassword1);
        regPassword2 = findViewById(R.id.regPassword2);
        progressBarRegister = findViewById(R.id.progressBarRegister);

        btnRegConfirm = findViewById(R.id.btnRegConfirm);
        progressBarRegister.setVisibility(View.INVISIBLE);


        ///*** register button***///
        btnRegConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btnRegConfirm.setVisibility(View.INVISIBLE);
                progressBarRegister.setVisibility(View.VISIBLE);

                registerNewUser();
                        }

        });
    }

    private void registerNewUser(){

        String firstName = regFirstName.getText().toString().trim();
        String userEmail = regEmail.getText().toString().trim();
        String userPhone = regPhone.getText().toString().trim();
        String userPassword1 = regPassword1.getText().toString().trim();
        String userPassword2 = regPassword2.getText().toString().trim();

        //check if user name is empty
        if (firstName.isEmpty()) {
            showMessage("please fill in username!");
            btnRegConfirm.setVisibility(View.VISIBLE);
            progressBarRegister.setVisibility(View.INVISIBLE);
        }
        //checking if email.is empty
        else if (userEmail.isEmpty()) {
            showMessage("please fill in email address!");
            btnRegConfirm.setVisibility(View.VISIBLE);
            progressBarRegister.setVisibility(View.INVISIBLE);
        }
        //checking if phone is empty
        else if (userPhone.isEmpty()) {
            showMessage("please fill in phone number!");
            btnRegConfirm.setVisibility(View.VISIBLE);
            progressBarRegister.setVisibility(View.INVISIBLE);
        }
        //checking if password is empty
        else if (userPassword1.isEmpty()) {
            showMessage("please fill in password!");
            btnRegConfirm.setVisibility(View.VISIBLE);
            progressBarRegister.setVisibility(View.INVISIBLE);
        }
        //check if password1 match password2
        else if (!userPassword1.equals(userPassword2)) {
            showMessage("password does not match!");
            btnRegConfirm.setVisibility(View.VISIBLE);
            progressBarRegister.setVisibility(View.INVISIBLE);
        }
        else{

        mAuth.createUserWithEmailAndPassword(userEmail, userPassword1)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            showMessage("User Registered!");
                            updateUI();
                            storeUserInfo();
                        }

                    }
                });
    }}



    private void updateUI() {
        Intent homeActivity = new Intent(getApplicationContext(), HomeActivity.class);
        startActivity(homeActivity);
        finish();

    }

    private void showMessage(String message) {

        //showing toast message method
        Toast.makeText(getApplicationContext(), message,Toast.LENGTH_SHORT).show();
    }

    private void storeUserInfo() {
        //this method is to store user details into database
        String userName = regFirstName.getText().toString().trim();
        String userPhone = regPhone.getText().toString().trim();
        String userEmail = regEmail.getText().toString().trim();

        UserInformation currentUser = new UserInformation(userName, userPhone, userEmail);

        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        mRef.child(firebaseUser.getUid()).setValue(currentUser);
    }

    @Override
    protected void onStart() {
        super.onStart();

    }
}
