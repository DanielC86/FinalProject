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

import com.example.danie.dine.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class RegisterActivity extends AppCompatActivity {


    private EditText userName;
    private EditText userEmail;
    private EditText userPhone;
    private EditText userPassword1;
    private EditText userPassword2;
    private ProgressBar progressBarRegister;
    private Button btnRegConfirm;

    //firebase authentication
    private FirebaseAuth userAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        userName = findViewById(R.id.regName);
        userEmail = findViewById(R.id.regEmail);
        userPhone = findViewById(R.id.regPhone);
        userPassword1 = findViewById(R.id.regPassword1);
        userPassword2 = findViewById(R.id.regPassword2);
        progressBarRegister = findViewById(R.id.progressBarRegister);

        btnRegConfirm = findViewById(R.id.btnRegConfirm);
        progressBarRegister.setVisibility(View.INVISIBLE);

        userAuth = FirebaseAuth.getInstance();

        ///*** register button***///
        btnRegConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btnRegConfirm.setVisibility(View.INVISIBLE);
                progressBarRegister.setVisibility(View.VISIBLE);
                final String email = userEmail.getText().toString();
                final String password1 = userPassword1.getText().toString();
                final String password2 = userPassword2.getText().toString();
                final String username = userName.getText().toString();
                final String userphone = userPhone.getText().toString();


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


            }
        });



    }

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
