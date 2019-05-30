package com.example.danie.dine.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

    private String newName;
    private String newEmail;
    private String newPhone;



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


        btnEditBack = findViewById(R.id.btnEditBack);
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
                goBack();
                finish();

            }
        });

        btnEditName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lblEditName.setVisibility(View.INVISIBLE);
                lblEditEmail.setVisibility(View.INVISIBLE);
                lblEditPhone.setVisibility(View.INVISIBLE);
                btnEditName.setVisibility(View.INVISIBLE);
                btnEditEmail.setVisibility(View.INVISIBLE);
                btnEditPhone.setVisibility(View.INVISIBLE);
                btnEditBack.setVisibility(View.INVISIBLE);

                editName();
            }
        });

        btnEditEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo
                editEmail();
            }
        });

        btnEditPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo
                editPhone();

            }
        });

        btnSaveName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editTextName.getText().toString().isEmpty()) {
                    showMessage("Please provide new user name!!!");
                }
                else {
                    saveName();
                    restartActivity();
                }
            }
        });

        btnSaveEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editTextEmail.getText().toString().isEmpty()) {
                    showMessage("Please provide new user email!!!");
                }
                else {
                    saveEmail();
                    restartActivity();
                }
            }
        });

        btnSavePhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editTextPhone.getText().toString().isEmpty()) {
                    showMessage("Please provide new user phone!!!");
                }
                else {
                    savePhone();
                    restartActivity();
                }
            }
        });
    }

    private void editName(){
        editTextName.setVisibility(View.VISIBLE);
        btnSaveName.setVisibility(View.VISIBLE);

    }

    private void editEmail(){
        editTextName.setVisibility(View.INVISIBLE);
        btnSaveName.setVisibility(View.INVISIBLE);
        lblEditName.setVisibility(View.INVISIBLE);
        lblEditEmail.setVisibility(View.INVISIBLE);
        lblEditPhone.setVisibility(View.INVISIBLE);
        btnEditName.setVisibility(View.INVISIBLE);
        btnEditEmail.setVisibility(View.INVISIBLE);
        btnEditPhone.setVisibility(View.INVISIBLE);
        btnEditBack.setVisibility(View.INVISIBLE);

        editTextEmail.setVisibility(View.VISIBLE);
        btnSaveEmail.setVisibility(View.VISIBLE);
    }

    private void editPhone(){
        editTextName.setVisibility(View.INVISIBLE);
        btnSaveName.setVisibility(View.INVISIBLE);
        lblEditName.setVisibility(View.INVISIBLE);
        lblEditEmail.setVisibility(View.INVISIBLE);
        lblEditPhone.setVisibility(View.INVISIBLE);
        btnEditName.setVisibility(View.INVISIBLE);
        btnEditEmail.setVisibility(View.INVISIBLE);
        btnEditPhone.setVisibility(View.INVISIBLE);
        btnEditBack.setVisibility(View.INVISIBLE);

        editTextPhone.setVisibility(View.VISIBLE);
        btnSavePhone.setVisibility(View.VISIBLE);
    }

    private void saveName(){
        editTextName.setVisibility(View.INVISIBLE);
        btnSaveName.setVisibility(View.INVISIBLE);
        lblEditName.setVisibility(View.VISIBLE);
        lblEditEmail.setVisibility(View.VISIBLE);
        lblEditPhone.setVisibility(View.VISIBLE);
        btnEditName.setVisibility(View.VISIBLE);
        btnEditEmail.setVisibility(View.VISIBLE);
        btnEditPhone.setVisibility(View.VISIBLE);
        btnEditBack.setVisibility(View.VISIBLE);
        String userEmail = lblEditEmail.getText().toString();
        String phoneNumber = lblEditPhone.getText().toString();
        String userType = "User";

        String newName = editTextName.getText().toString();
        UserInformation currentUser = new UserInformation(newName, phoneNumber, userEmail, userType);
        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        mRef.child(firebaseUser.getUid()).setValue(currentUser);



    }

    private void saveEmail(){
        editTextName.setVisibility(View.INVISIBLE);
        btnSaveName.setVisibility(View.INVISIBLE);
        lblEditName.setVisibility(View.VISIBLE);
        lblEditEmail.setVisibility(View.VISIBLE);
        lblEditPhone.setVisibility(View.VISIBLE);
        btnEditName.setVisibility(View.VISIBLE);
        btnEditEmail.setVisibility(View.VISIBLE);
        btnEditPhone.setVisibility(View.VISIBLE);
        btnEditBack.setVisibility(View.VISIBLE);

        String userName = lblEditName.getText().toString();
        String phoneNumber = lblEditPhone.getText().toString();
        String userType = "User";

        String newEmail = editTextEmail.getText().toString();
        UserInformation currentUser = new UserInformation(userName, phoneNumber, newEmail, userType);
        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        mRef.child(firebaseUser.getUid()).setValue(currentUser);
    }
    private void savePhone(){
        editTextName.setVisibility(View.INVISIBLE);
        btnSaveName.setVisibility(View.INVISIBLE);
        lblEditName.setVisibility(View.VISIBLE);
        lblEditEmail.setVisibility(View.VISIBLE);
        lblEditPhone.setVisibility(View.VISIBLE);
        btnEditName.setVisibility(View.VISIBLE);
        btnEditEmail.setVisibility(View.VISIBLE);
        btnEditPhone.setVisibility(View.VISIBLE);
        btnEditBack.setVisibility(View.VISIBLE);

        String userName = lblEditName.getText().toString();
        String userEmail = lblEditEmail.getText().toString();
        String userType = "User";

        String newPhone = editTextPhone.getText().toString();
        UserInformation currentUser = new UserInformation(userName, newPhone, userEmail, userType);
        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        mRef.child(firebaseUser.getUid()).setValue(currentUser);
    }




    private void showMessage(String message) {

        //showing toast message method
        Toast.makeText(getApplicationContext(), message,Toast.LENGTH_SHORT).show();
    }

    //restart activity
    private void restartActivity(){
        Intent editDetailsActivity = new Intent(getApplicationContext(), EditDetailsActivity.class);
        startActivity(editDetailsActivity);
    }

    private void goBack(){
        Intent homeActivity = new Intent(getApplicationContext(), HomeActivity.class);
        startActivity(homeActivity);
    }
}
