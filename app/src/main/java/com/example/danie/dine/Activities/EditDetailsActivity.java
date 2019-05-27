package com.example.danie.dine.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

import com.example.danie.dine.Model.UserInformation;
import com.example.danie.dine.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditDetailsActivity extends AppCompatActivity {

    private Button btnEditSubmit;
    private Button btnEditBack;


    private EditText editName;
    private EditText editEmail;
    private EditText editPhone;




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

        btnEditBack = findViewById(R.id.btnEditBack);
        btnEditSubmit = findViewById(R.id.btnEditSubmit);

        editName = findViewById(R.id.editName);
        editEmail = findViewById(R.id.editEmail);
        editPhone = findViewById(R.id.editPhone);


        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        userID = currentUser.getUid();
        mDatabase = FirebaseDatabase.getInstance();
        mRef = mDatabase.getReference().child("Users");
    }


    private void updateDetails(){

        String newName = "";
        String newPhone = "";
        String newEmail = "";
        String newUserType = "User";

        DatabaseReference editUserRef = FirebaseDatabase.getInstance().getReference("Users").child(userID);
        UserInformation editUser = new UserInformation(newName, newPhone, newEmail, newUserType);
        editUserRef.setValue(editUser);
    }
}
