package com.example.danie.dine.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.danie.dine.R;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {


    private EditText userName,userEmail,userPhone,userPassword1,userPassword2;
    private ProgressBar progressBarRegister;
    private Button btnRegConfirm;

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

        btnRegConfirm = findViewById(R.id.btnRegConfirm);
        progressBarRegister.setVisibility(View.INVISIBLE);

        userAuth = FirebaseAuth.getInstance();

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


            }
        });



    }



    //showing toast message
    private void showMessage(String message) {

        Toast.makeText(getApplicationContext(), message,Toast.LENGTH_LONG).show();

    }
}
