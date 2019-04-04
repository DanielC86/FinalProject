package com.example.danie.dine.Activities;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.danie.dine.Fragments.DatePickerFragment;
import com.example.danie.dine.Fragments.TimePickerFragment;
import com.example.danie.dine.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.Calendar;

public class BookingManagementActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    private int guestNumber = 1;
    private TextView lblGuests;

    //database stuff
    private FirebaseAuth mAuth;
    private DatabaseReference mRef;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_management);

        //initialize firebase
        mAuth = FirebaseAuth.getInstance();
        mRef = FirebaseDatabase.getInstance().getReference().child("Reservations");

        Button btnDate = (Button) findViewById(R.id.btnDate);
        Button btnTime = (Button) findViewById(R.id.btnTime);
        Button btnPlus = (Button) findViewById(R.id.btnPlus);
        Button btnMinus = (Button) findViewById(R.id.btnMinus);

        btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //code to add guest number and display
                guestNumber++;
                TextView lblGuests = (TextView) findViewById(R.id.lblGuests);
                lblGuests.setText("Guests: " + guestNumber);
                if (guestNumber > 8) {
                    guestNumber = guestNumber - 9;
                }
            }
        });

        btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //code to decrease guest number
                TextView lblGuests = (TextView) findViewById(R.id.lblGuests);
                lblGuests.setText("Guests: " + guestNumber);
                if (guestNumber == 1 ) {
                    guestNumber = 1;
                }
                else {
                    guestNumber--;
                }
            }
        });


        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "Date");
            }
        });

        btnTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(), "Time");
            }
        });
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String currentDate = DateFormat.getDateInstance(DateFormat.MEDIUM).format(calendar.getTime());
        TextView lblDate = (TextView) findViewById(R.id.lblDate);
        //lblDate.setVisibility(View.VISIBLE);
        lblDate.setText(currentDate);



    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);
        String currentTime = DateFormat.getTimeInstance(DateFormat.SHORT).format(calendar.getTime());
        TextView lblTime = (TextView) findViewById(R.id.lblTime);
        lblTime.setText(currentTime);


    }

    public void makeReservation(){

    }
}
