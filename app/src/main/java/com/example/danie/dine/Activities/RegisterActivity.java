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

import com.example.danie.dine.Model.User;
import com.example.danie.dine.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegisterActivity extends AppCompatActivity {


    private EditText regLogin;
    private EditText regFirstName;
    private EditText regEmail;
    private EditText regPhone;
    private EditText regPassword1;
    private EditText regPassword2;
    private ProgressBar progressBarRegister;
    private Button btnRegConfirm;

    /*
    firebase authentication
    private FirebaseAuth userAuth;
    */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        regFirstName = findViewById(R.id.regFirstName);
        regLogin = findViewById(R.id.regLogin);
        regEmail = findViewById(R.id.regEmail);
        regPhone = findViewById(R.id.regPhone);
        regPassword1 = findViewById(R.id.regPassword1);
        regPassword2 = findViewById(R.id.regPassword2);
        progressBarRegister = findViewById(R.id.progressBarRegister);

        //initialise firebase realtime database
        FirebaseDatabase dineDB = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = dineDB.getReference("User");

        btnRegConfirm = findViewById(R.id.btnRegConfirm);
        progressBarRegister.setVisibility(View.INVISIBLE);

        //userAuth = FirebaseAuth.getInstance();

        ///*** register button***///
        btnRegConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btnRegConfirm.setVisibility(View.INVISIBLE);
                progressBarRegister.setVisibility(View.VISIBLE);

                /*
                final String email = regEmail.getText().toString();
                final String password1 = regPassword1.getText().toString();
                final String password2 = regPassword2.getText().toString();
                final String username = regName.getText().toString();
                final String userPhone = regPhone.getText().toString();
                */


                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        //check if user name is empty
                        if (regLogin.getText().toString().isEmpty()) {
                            showMessage("please fill in username!");
                            btnRegConfirm.setVisibility(View.VISIBLE);
                            progressBarRegister.setVisibility(View.INVISIBLE);
                        }
                        //checking if email.is empty
                        else if (regEmail.getText().toString().isEmpty()) {
                            showMessage("please fill in email address!");
                            btnRegConfirm.setVisibility(View.VISIBLE);
                            progressBarRegister.setVisibility(View.INVISIBLE);
                        }
                        //checking if phone is empty
                        else if (regPhone.getText().toString().isEmpty()) {
                            showMessage("please fill in phone number!");
                            btnRegConfirm.setVisibility(View.VISIBLE);
                            progressBarRegister.setVisibility(View.INVISIBLE);
                        }
                        //checking if password is empty
                        else if (regPassword1.getText().toString().isEmpty()) {
                            showMessage("please fill in password!");
                            btnRegConfirm.setVisibility(View.VISIBLE);
                            progressBarRegister.setVisibility(View.INVISIBLE);
                        }
                        //check if password1 match password2
                        else if (!regPassword1.getText().toString().equals(regPassword2.getText().toString())) {
                            showMessage("password does not match!");
                            btnRegConfirm.setVisibility(View.VISIBLE);
                            progressBarRegister.setVisibility(View.INVISIBLE);
                        }
                        else
                        {



                        //check if user already exists
                        if (dataSnapshot.child(regLogin.getText().toString()).exists()) {
                            showMessage("user name already taken!");
                            btnRegConfirm.setVisibility(View.VISIBLE);
                            progressBarRegister.setVisibility(View.INVISIBLE);
                        }
                        else{
                            User newUser = new User(regEmail.getText().toString(), regPassword1.getText().toString(), regPhone.getText().toString(), regFirstName.getText().toString());
                            table_user.child(regLogin.getText().toString()).setValue(newUser);
                            showMessage("Registration Completed!");
                            finish();
                            //updateUI();
                        }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                /*
                if (email.isEmpty() || username.isEmpty() || userphone.isEmpty() || password1.isEmpty() || !password1.equals(password2)) {
                    //user cannot be registered, something wrong
                    showMessage("Registration Failed, pelase check your details!");
                    btnRegConfirm.setVisibility(View.VISIBLE);
                    progressBarRegister.setVisibility(View.INVISIBLE);

                }
                else {
                    //registration successful
                    CreateUserAccount(email, username, password1);
                }
                */

            }
        });

    }
    /*
    private void CreateUserAccount(String email, final String username, String password1) {

        //register new user method

        userAuth.createUserWithEmailAndPassword(email, password1)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            //user account is created successfully
                            showMessage("Account Created!");
                            //once user account created succesfully, user details needs to be updated
                            updateUserInfo(username,userAuth.getCurrentUser());

                        }
                        else {
                            //account not created, something wrong
                            showMessage("cannot create account, check your details" + task.getException().getMessage().toString());
                            btnRegConfirm.setVisibility(View.VISIBLE);
                            progressBarRegister.setVisibility(View.INVISIBLE);
                        }
                    }
                });


    }
    */

    /*
    private void updateUserInfo(String username, FirebaseUser currentUser) {

        //update user name method
        //StorageReference userStorage = FirebaseStorage.getInstance().getReference().child("")
        UserProfileChangeRequest profileUpdate = new UserProfileChangeRequest.Builder()
                .setDisplayName(username)
                .build();
        currentUser.updateProfile(profileUpdate)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if(task.isSuccessful()) {
                            //user info updated successfully
                            showMessage("Registration Complete!");
                            updateUI();
                        }

                    }
                });
    }
    */



    private void updateUI() {
        Intent homeActivity = new Intent(getApplicationContext(), HomeActivity.class);
        startActivity(homeActivity);
        finish();

    }

    private void showMessage(String message) {

        //showing toast message method
        Toast.makeText(getApplicationContext(), message,Toast.LENGTH_LONG).show();

    }
}
