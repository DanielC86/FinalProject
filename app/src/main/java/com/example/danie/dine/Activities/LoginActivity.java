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

public class LoginActivity extends AppCompatActivity {


    private EditText loginEmail;
    private EditText loginPassword;
    private Button btnLogin;
    private ProgressBar progressBarLogin;
    private Button getBtnLoginExit;

    private FirebaseAuth userAuth;

    private Intent HomeActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        loginEmail = findViewById(R.id.loginEmail);
        loginPassword = findViewById(R.id.loginPassword);
        btnLogin = findViewById(R.id.btnLogin);
        getBtnLoginExit = findViewById(R.id.btnLoginExit);
        progressBarLogin = findViewById(R.id.progressBarLogin);
        userAuth = FirebaseAuth.getInstance();
        HomeActivity = new Intent(this,com.example.danie.dine.Activities.HomeActivity.class);

        getBtnLoginExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                System.exit(0);
            }
        });



        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBarLogin.setVisibility(View.VISIBLE);
                btnLogin.setVisibility(View.INVISIBLE);

                final String loginemail = loginEmail.getText().toString();
                final String loginpassword = loginPassword.getText().toString();

                if (loginemail.isEmpty() || loginpassword.isEmpty()) {
                    showMessage("cannot login, please check your input!");
                }
                else {
                    signIn(loginemail, loginpassword);
                }
            }
        });

    }

    private void signIn(String loginemail, String loginpassword) {

        userAuth.signInWithEmailAndPassword(loginemail, loginpassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {

                    progressBarLogin.setVisibility(View.INVISIBLE);
                    btnLogin.setVisibility(View.VISIBLE);
                    updateUI();
                }
                else {

                    showMessage(task.getException().getMessage());
                    progressBarLogin.setVisibility(View.INVISIBLE);
                    btnLogin.setVisibility(View.VISIBLE);

                }

            }
        });

    }


    private void updateUI() {

        startActivity(HomeActivity);
        finish();

    }

    private void showMessage(String s) {
        Toast.makeText(getApplicationContext(), s,Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onStart() {
        super.onStart();

        //check if any user is already logged in
        FirebaseUser user = userAuth.getCurrentUser();

        if (user != null) {
            //if user is already logged in, we need to redirect to home page
            updateUI();

        }
    }
}
