package com.example.danie.dine.Activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
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

public class RestaurantViewActivity extends AppCompatActivity {


    //firebase for table
    private FirebaseAuth tableAuth;
    private DatabaseReference tableRef;
    private FirebaseDatabase tableDatabase;
    private FirebaseAuth.AuthStateListener tableAuthListener;

    private String rBookingInfo;
    private String rBookingName;
    private String rBookingDate;
    private String rBookingTime;
    private String rBookingGuests;

    private String tableID;


    //UI elements
    private TextView rBookingView;
    private Button rAccept;
    private Button rDecline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_view);

        //Firebase for table initialization
        tableAuth = FirebaseAuth.getInstance();
        FirebaseUser currentTable = tableAuth.getCurrentUser();
        tableID = currentTable.getUid();
        tableDatabase = FirebaseDatabase.getInstance();
        tableRef = tableDatabase.getReference().child("Tables");


        rBookingView = (TextView)findViewById(R.id.rBookingView);
        rAccept = (Button)findViewById(R.id.rAccept);
        rDecline = (Button)findViewById(R.id.rDecline);


        //display booking details - this one work!
        tableRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                TableInformation rTableInfo = dataSnapshot.getValue(TableInformation.class);
                rBookingInfo = ("Request for " + rTableInfo.getBookingName() + " date: " + rTableInfo.getBookingDate() + ", time: " + rTableInfo.getBookingTime() + ", guest number: " + rTableInfo.getBookingGuestNumber());
                rBookingView.setText(rBookingInfo);
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


    }
}
