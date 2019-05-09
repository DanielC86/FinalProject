package com.example.danie.dine.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.danie.dine.Model.TableInformation;
import com.example.danie.dine.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserBookingView extends AppCompatActivity {


    private Button btnUBookingViewBack;
    private TextView userBookingView;
    private String uBookingDate;
    private String uBookingTime;
    private String uBookingGuests;
    private String uBookingInfo;

    //Firebase for table view
    private FirebaseAuth tableAuth;
    private DatabaseReference tableRef;
    private FirebaseDatabase tableDatabase;
    private FirebaseAuth.AuthStateListener tableAuthListener;
    private String tableID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_booking_view);

        userBookingView = findViewById(R.id.userBookingView);
        btnUBookingViewBack = findViewById(R.id.btnUBookingViewBack);

        //Firebase for table initialization
        tableAuth = FirebaseAuth.getInstance();
        FirebaseUser currentTable = tableAuth.getCurrentUser();
        tableID = currentTable.getUid();
        tableDatabase = FirebaseDatabase.getInstance();
        tableRef = tableDatabase.getReference().child("Tables");


        tableRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                TableInformation uTableInfo = dataSnapshot.getValue(TableInformation.class);
                uBookingInfo = ("Request for " + uTableInfo.getBookingName() + " date: " + uTableInfo.getBookingDate() + ", time: " + uTableInfo.getBookingTime() + ", guest number: " + uTableInfo.getBookingGuestNumber() + ", Request status: " + uTableInfo.getBookingStatus());
                userBookingView.setText(uBookingInfo);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btnUBookingViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack();
            }
        });
    }


    private void goBack() {
        Intent homeActivity = new Intent(getApplicationContext(), HomeActivity.class);
        startActivity(homeActivity);
        finish();
    }
}
