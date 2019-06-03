package com.example.danie.dine.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.danie.dine.Model.DinnerEvent;
import com.example.danie.dine.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserBookingView extends AppCompatActivity {


    private Button btnUBookingViewBack;
    private TextView userBookingView;

    private String dinner;

    //Firebase for table view
    private FirebaseAuth dinnerAuth;
    private DatabaseReference dinnerRef;
    private FirebaseDatabase dinnerDatabase;
    private FirebaseAuth.AuthStateListener dinnerAuthListener;
    private String dinnerID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_booking_view);

        //requestKey = getIntent().getExtras().getString("Content");

        userBookingView = findViewById(R.id.userBookingView);
        btnUBookingViewBack = findViewById(R.id.btnUBookingViewBack);

        //userBookingView.setText("");

        //Firebase for dinner initialization
        dinnerAuth = FirebaseAuth.getInstance();
        FirebaseUser currentDinner = dinnerAuth.getCurrentUser();
        dinnerID = currentDinner.getUid();
        dinnerDatabase = FirebaseDatabase.getInstance();
        dinnerRef = dinnerDatabase.getReference().child("DinnerEvent");




        dinnerRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                DinnerEvent currentDinner = dataSnapshot.getValue(DinnerEvent.class);
                if (dataSnapshot.hasChildren()){
                    currentDinner.getDinnerRequest();

                    dinner = currentDinner.getDinnerRequest();
                    userBookingView.setText(dinner);
                }
                else {
                    userBookingView.setText("No Data");
                }

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

    private void showMessage(String message) {
        //showing toast message method
        Toast.makeText(getApplicationContext(), message,Toast.LENGTH_SHORT).show();
    }
}
