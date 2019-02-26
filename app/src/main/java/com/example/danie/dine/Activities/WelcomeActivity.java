package com.example.danie.dine.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.danie.dine.R;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        Button btnWelcomeLogin = (Button)findViewById(R.id.btnWelcomeLogin);
        btnWelcomeLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent welcomeLoginIntent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(welcomeLoginIntent);
            }
        });

        Button btnWelcomeRegister = (Button)findViewById(R.id.btnWelcomeRegister);
        btnWelcomeRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent welcomeRegisterIntent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(welcomeRegisterIntent);
            }
        });

        Button btnWelcomeExit = (Button) findViewById(R.id.btnWelcomeExit);
        btnWelcomeExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                System.exit(0);
            }
        });
    }
}
