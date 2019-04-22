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

public class LoginActivity extends AppCompatActivity {

    private EditText loginEmail;
    private EditText loginPassword;
    private Button btnLogin;
    private ProgressBar progressBarLogin;
    private Button getBtnLoginExit;


    private FirebaseAuth mAuth;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();


        //check if user already logged in
        if (mAuth.getCurrentUser() != null){
            //start home activity
            updateUI();
        }

        loginEmail = findViewById(R.id.loginEmail);
        loginPassword = findViewById(R.id.loginPassword);
        btnLogin = findViewById(R.id.btnLogin);
        getBtnLoginExit = findViewById(R.id.btnLoginExit);
        progressBarLogin = findViewById(R.id.progressBarLogin);


        //exit
        getBtnLoginExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                System.exit(0);
            }
        });


        //login button to log in already existing users
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                progressBarLogin.setVisibility(View.VISIBLE);
                btnLogin.setVisibility(View.INVISIBLE);
                userLogin();
            }
        });

    }

    private void userLogin() {

        //get input
        String userEmail = loginEmail.getText().toString().trim();
        String userPassword = loginPassword.getText().toString().trim();

        //check if the fields are empty
        if (userEmail.isEmpty()) {
            showMessage("Please fill in email address!");
            btnLogin.setVisibility(View.VISIBLE);
            progressBarLogin.setVisibility(View.INVISIBLE);
        }
        else if (userPassword.isEmpty()) {
            showMessage("Please fill in password!");
            btnLogin.setVisibility(View.VISIBLE);
            progressBarLogin.setVisibility(View.INVISIBLE);
        }
        else {
            //user login
            mAuth.signInWithEmailAndPassword(userEmail, userPassword)
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

    private void updateUI() {

        Intent homeActivity = new Intent(getApplicationContext(), HomeActivity.class);
        startActivity(homeActivity);
        finish();
    }

    //messaging method
    private void showMessage(String s) {
        Toast.makeText(getApplicationContext(), s,Toast.LENGTH_SHORT).show();
    }

}
