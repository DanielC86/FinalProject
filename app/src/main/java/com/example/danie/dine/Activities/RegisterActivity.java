package com.example.danie.dine.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.example.danie.dine.R;

public class RegisterActivity extends AppCompatActivity {


    private EditText userName,userEmail,userPhone,userPassword1,userPassword2;
    private ProgressBar loadingProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        userName = findViewById(R.id.regName);
        userEmail = findViewById(R.id.regEmail);
        userPhone = findViewById(R.id.regPhone);
        userPassword1 = findViewById(R.id.regPassword1);
        userPassword2 = findViewById(R.id.regPassword2);

    }
}
