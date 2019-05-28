package com.example.danie.dine.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.danie.dine.Model.UserInformation;
import com.example.danie.dine.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditDetailsActivity extends AppCompatActivity {


    private Button btnEditBack;
    private Button btnEditName;
    private Button btnEditEmail;
    private Button btnEditPhone;

    private Button btnSaveName;
    private Button btnSaveEmail;
    private Button btnSavePhone;


    private EditText editTextName;
    private EditText editTextEmail;
    private EditText editTextPhone;

    private TextView lblEditName;
    private TextView lblEditEmail;
    private TextView lblEditPhone;




    //firebase elements
    private FirebaseAuth mAuth;
    private DatabaseReference mRef;
    private FirebaseDatabase mDatabase;
    private FirebaseAuth.AuthStateListener mAuthListener;




    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_details);


        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        userID = currentUser.getUid();
        mDatabase = FirebaseDatabase.getInstance();
        mRef = mDatabase.getReference().child("Users");


        btnEditBack = findViewById(R.id.btnSaveEmail);
        btnEditName = findViewById(R.id.btnEditName);
        btnEditEmail = findViewById(R.id.btnEditEmail);
        btnEditPhone = findViewById(R.id.btnEditPhone);

        btnSaveName = findViewById(R.id.btnSaveName);
        btnSaveEmail = findViewById(R.id.btnSaveEmail);
        btnSavePhone = findViewById(R.id.btnSavePhone);

        btnSaveName.setVisibility(View.INVISIBLE);
        btnSaveEmail.setVisibility(View.INVISIBLE);
        btnSavePhone.setVisibility(View.INVISIBLE);


        lblEditName = findViewById(R.id.lblEditName);
        lblEditEmail = findViewById(R.id.lblEditEmail);
        lblEditPhone = findViewById(R.id.lblEditPhone);

        lblEditName.setVisibility(View.VISIBLE);
        lblEditEmail.setVisibility(View.VISIBLE);
        lblEditPhone.setVisibility(View.VISIBLE);


        editTextName = findViewById(R.id.editTextName);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPhone = findViewById(R.id.editTextPhone);

        editTextEmail.setVisibility(View.INVISIBLE);
        editTextName.setVisibility(View.INVISIBLE);
        editTextPhone.setVisibility(View.INVISIBLE);


        //view user details
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserInformation userDetails = dataSnapshot.getValue(UserInformation.class);
                userDetails.getUserName();
                userDetails.getPhoneNumber();
                userDetails.getUserEmail();

                userDetails.setUserName(dataSnapshot.child(userID).getValue(UserInformation.class).getUserName()); //get username
                userDetails.setUserEmail(dataSnapshot.child(userID).getValue(UserInformation.class).getUserEmail()); //get user email
                userDetails.setPhoneNumber(dataSnapshot.child(userID).getValue(UserInformation.class).getPhoneNumber()); //get user phone number
                lblEditName.setText(userDetails.getUserName());
                lblEditEmail.setText(userDetails.getUserEmail());
                lblEditPhone.setText(userDetails.getPhoneNumber());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        btnEditBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editBack();
                finish();

            }
        });

        btnEditName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo
                editName();
            }
        });

        btnEditEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo
            }
        });

        btnEditPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnSaveName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo
            }
        });

        btnSaveEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo
            }
        });

        btnSavePhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo
            }
        });
    }

    private void editName(){
        editTextName.setVisibility(View.VISIBLE);
        btnSaveName.setVisibility(View.VISIBLE);
        lblEditName.setVisibility(View.INVISIBLE);
        lblEditEmail.setVisibility(View.INVISIBLE);
        lblEditPhone.setVisibility(View.INVISIBLE);
        btnEditName.setVisibility(View.INVISIBLE);
        btnEditEmail.setVisibility(View.INVISIBLE);
        btnEditPhone.setVisibility(View.INVISIBLE);

    }

    private void editEmail(){
        //todo
    }

    private void editPhone(){
        //todo
    }

    private void saveName(){
        //todo
    }

    private void saveEmail(){
        //todo
    }
    private void savePhone(){
        //todo
    }




    private void editBack(){
        Intent homeActivity = new Intent(getApplicationContext(), HomeActivity.class);
        startActivity(homeActivity);
    }
}
