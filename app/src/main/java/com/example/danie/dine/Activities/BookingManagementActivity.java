package com.example.danie.dine.Activities;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.danie.dine.Fragments.DatePickerFragment;
import com.example.danie.dine.Fragments.TimePickerFragment;
import com.example.danie.dine.Model.TableInformation;
import com.example.danie.dine.Model.UserInformation;
import com.example.danie.dine.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.Calendar;

public class BookingManagementActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    private int guestNumber = 1;
    String status = "Awaiting";
    private String bookingDate;
    private String bookingTime;
    String currentDate;
    String currentTime;
    private String guests;
    private TextView lblDate;
    private TextView lblTime;
    private TextView lblGuests;
    private TextView bookingUserName;
    private TextView bookingUserEmail;
    private TextView bookingUserPhone;

    public String key;

    //firebase elements for user
    private FirebaseAuth mAuth;
    private DatabaseReference mRef;
    private FirebaseDatabase mDatabase;
    private FirebaseAuth.AuthStateListener mAuthListener;

    //firebase elements for table
    private FirebaseAuth tableAuth;
    private DatabaseReference tableRef;
    private FirebaseDatabase tableDatabase;
    private FirebaseAuth.AuthStateListener tableAuthlistener;
    private String tableID;

    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_management);

        lblDate = findViewById(R.id.lblDate);
        lblDate.setVisibility(View.INVISIBLE);
        lblTime = findViewById(R.id.lblTime);
        lblTime.setVisibility(View.INVISIBLE);
        lblGuests = findViewById(R.id.lblGuests);
        lblGuests.setVisibility(View.INVISIBLE);


        //firebase for user
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        userID = currentUser.getUid();
        mDatabase = FirebaseDatabase.getInstance();
        mRef = mDatabase.getReference().child("Users");

        //firebase for table
        tableAuth = FirebaseAuth.getInstance();
        FirebaseUser currentTable = tableAuth.getCurrentUser();
        tableID = currentTable.getUid();
        tableDatabase = FirebaseDatabase.getInstance();
        tableRef = tableDatabase.getReference().child("Tables");

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in

                    showMessage("Successfully signed in with: " + user.getEmail());
                } else {
                    // User is signed out
                    showMessage("Successfully signed out.");
                }
                // ...
            }
        };

        Button btnDate = (Button) findViewById(R.id.btnDate);
        Button btnTime = (Button) findViewById(R.id.btnTime);
        Button btnPlus = (Button) findViewById(R.id.btnPlus);
        Button btnMinus = (Button) findViewById(R.id.btnMinus);
        Button btnTableSubmit = (Button) findViewById(R.id.btnTableSubmit);

        bookingUserEmail = (TextView) findViewById(R.id.bookingUserEmail);
        bookingUserPhone = (TextView) findViewById(R.id.bookingUserPhone);
        bookingUserName = (TextView) findViewById(R.id.bookingUserName);


        btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lblGuests.setVisibility(View.VISIBLE);
                //code to add guest number and display
                guestNumber++;
                lblGuests.setText("Guests: " + guestNumber);
                if (guestNumber > 8) {
                    guestNumber = guestNumber - 9;
                }
            }
        });

        //guest picker
        btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lblGuests.setVisibility(View.VISIBLE);
                //code to decrease guest number
                TextView lblGuests = (TextView) findViewById(R.id.lblGuests);
                lblGuests.setText("Guests: " + guestNumber);
                if (guestNumber == 1 ) {
                    guestNumber = 1;
                }
                else {
                    guestNumber--;
                }
                guests = "number of guests: " + guestNumber;
            }
        });


        //date picker
        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "Date");
            }
        });

        //time picker
        btnTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(), "Time");
            }
        });

        //submit reservation
        btnTableSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookTable();
                System.out.println("******************************    Reservation successful with date of " + currentDate + " and time " + currentTime + ", number of guests: " + guestNumber);
            }
        });

        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserInformation userDetails = dataSnapshot.getValue(UserInformation.class);
                userDetails.getPhoneNumber();
                userDetails.getUserEmail();
                userDetails.getUserName();
                userDetails.setUserName(dataSnapshot.child(userID).getValue(UserInformation.class).getUserName()); //get user email
                userDetails.setUserEmail(dataSnapshot.child(userID).getValue(UserInformation.class).getUserEmail()); //get user email
                userDetails.setPhoneNumber(dataSnapshot.child(userID).getValue(UserInformation.class).getPhoneNumber()); //get user phone number
                bookingUserName.setText(userDetails.getUserName());
                bookingUserEmail.setText(userDetails.getUserEmail());
                bookingUserPhone.setText(userDetails.getPhoneNumber());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        currentDate = DateFormat.getDateInstance(DateFormat.MEDIUM).format(calendar.getTime());
        lblDate.setVisibility(View.VISIBLE);
        lblDate.setText(currentDate);

    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);
        currentTime = DateFormat.getTimeInstance(DateFormat.SHORT).format(calendar.getTime());
        lblTime.setVisibility(View.VISIBLE);
        lblTime.setText(currentTime);
    }

    private void showMessage(String message) {
        //showing toast message method
        Toast.makeText(getApplicationContext(), message,Toast.LENGTH_SHORT).show();
    }

    private void storeBookingInfo(){
        //this method is to store user details into database
        String bookingName = bookingUserName.getText().toString().trim();
        String bookingEmail = bookingUserEmail.getText().toString().trim();
        String bookingPhone = bookingUserPhone.getText().toString().trim();
        String requestKey = tableID;
        String bookingDate = currentDate;
        String bookingTime = currentTime;
        String bookingGuestNumber = lblGuests.getText().toString();
        String bookingStatus = status;

        TableInformation currentBooking = new TableInformation(bookingName, bookingEmail, bookingPhone, bookingDate, bookingTime, bookingGuestNumber, bookingStatus);
        FirebaseUser firebaseTable = tableAuth.getCurrentUser();
        tableRef.child(firebaseTable.getUid()).setValue(currentBooking);

        tableRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                key = dataSnapshot.getKey();
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

    private void bookTable(){

        String bookingDate = lblDate.getText().toString().trim();
        String bookingTime = lblTime.getText().toString().trim();
        String checkGuests = lblGuests.getText().toString().trim();

        if (bookingDate.equals("Date")) {
            showMessage("Please pick the date for booking!");
        }
        else if (bookingTime.equals("Time")) {
            showMessage("Please pick the time for booking!");
        }
        else if (checkGuests.equals("0")) {
            showMessage("Please pick guests number!");
        }
        else
            userBookings();
            storeBookingInfo();
        //updateUI();
        showMessage("Request sent!");
        showMessage((key));
    }




    private void userBookings() {
        Intent homeActivity = new Intent(getApplicationContext(), HomeActivity.class);
        homeActivity.putExtra("Content", key);
        startActivity(homeActivity);
        finish();
    }



    @Override
    protected void onStart() {
        super.onStart();

    }

}
