package com.example.danie.dine.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import com.example.danie.dine.R;

public class HomeActivity extends AppCompatActivity {


    private EditText userName;
    private EditText userPhone;
    private EditText userEmail;
    private EditText userPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

       // userPassword = (MaterialEditText)
    }
}
