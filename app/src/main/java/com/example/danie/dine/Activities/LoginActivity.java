package com.example.danie.dine.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.danie.dine.R;

public class LoginActivity extends AppCompatActivity {


    private EditText loginUsername;
    private EditText loginPassword;
    private Button btnLogin;
    private ProgressBar progressBarLogin;
    private Button getBtnLoginExit;



    private Intent HomeMenuDrawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);




        loginUsername = findViewById(R.id.loginUsername);
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
            }
        });


    }

    private void updateUI() {

        startActivity(HomeMenuDrawer);
        finish();

    }
    //messaging method
    private void showMessage(String s) {
        Toast.makeText(getApplicationContext(), s,Toast.LENGTH_LONG).show();
    }

}
