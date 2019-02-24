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

public class RegisterActivity extends AppCompatActivity {


    private EditText regLogin;
    private EditText regFirstName;
    private EditText regEmail;
    private EditText regPhone;
    private EditText regPassword1;
    private EditText regPassword2;
    private ProgressBar progressBarRegister;
    private Button btnRegConfirm;


    //firebase
    private FirebaseAuth firebaseAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        firebaseAuth = FirebaseAuth.getInstance();

        regFirstName = findViewById(R.id.regFirstName);
        regLogin = findViewById(R.id.regLogin);
        regEmail = findViewById(R.id.regEmail);
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





                        }







        });

    }

    private void registerNewUser(){


        //get input
        regLogin.getText().toString().trim();
        regEmail.getText().toString().trim();
        regPhone.getText().toString().trim();
        regPassword1.getText().toString().trim();
        regPassword2.getText().toString().trim();

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


        firebaseAuth.createUserWithEmailAndPassword(regEmail, regPassword1)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

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
