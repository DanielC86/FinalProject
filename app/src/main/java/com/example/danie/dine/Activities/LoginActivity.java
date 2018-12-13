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

public class LoginActivity extends AppCompatActivity {


    private EditText loginUsername;
    private EditText loginPassword;
    private Button btnLogin;
    private ProgressBar progressBarLogin;
    private Button getBtnLoginExit;

    //private FirebaseAuth userAuth;

    private Intent HomeActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //initialise firebase realtime database
        FirebaseDatabase dineDB = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = dineDB.getReference("User");


        loginUsername = findViewById(R.id.loginUsername);
        loginPassword = findViewById(R.id.loginPassword);
        btnLogin = findViewById(R.id.btnLogin);
        getBtnLoginExit = findViewById(R.id.btnLoginExit);
        progressBarLogin = findViewById(R.id.progressBarLogin);
       // userAuth = FirebaseAuth.getInstance();
        HomeActivity = new Intent(this,com.example.danie.dine.Activities.HomeActivity.class);

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

                table_user.addValueEventListener(new ValueEventListener() {



                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        //checking if username is empty
                        if (loginUsername.getText().toString().isEmpty()) {
                            showMessage("fill in username!");
                            progressBarLogin.setVisibility(View.INVISIBLE);
                            btnLogin.setVisibility(View.VISIBLE);
                        }
                        else{

                        //checking if user in database
                        if(dataSnapshot.child(loginUsername.getText().toString()).exists()) {

                        //get user information
                        User user = dataSnapshot.child(loginUsername.getText().toString()).getValue(User.class);
                        if(user.getPassword().equals(loginPassword.getText().toString())) {
                            showMessage("Sign in successfully!");
                            updateUI();
                        }
                        else {
                            showMessage("Wrong Password!");
                            //Toast.makeText(LoginActivity.this, "Could not lg in!", Toast.LENGTH_LONG).show();
                            progressBarLogin.setVisibility(View.INVISIBLE);
                            btnLogin.setVisibility(View.VISIBLE);
                        }

                        }
                        //user not in database
                        else {
                            showMessage("Cannot find user!");
                            progressBarLogin.setVisibility(View.INVISIBLE);
                            btnLogin.setVisibility(View.VISIBLE);
                        }
                }}

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                /*
                final String loginemail = loginEmail.getText().toString();
                final String loginpassword = loginPassword.getText().toString();

                if (loginemail.isEmpty() || loginpassword.isEmpty()) {
                    showMessage("cannot login, please check your input!");
                }
                else {
                    signIn(loginemail, loginpassword);
                }
                */
            }
        });


    }
    /*
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
    */


    private void updateUI() {

        startActivity(HomeActivity);
        finish();

    }
    //messaging method
    private void showMessage(String s) {
        Toast.makeText(getApplicationContext(), s,Toast.LENGTH_LONG).show();
    }
    /*
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
    */
}
