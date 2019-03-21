package com.example.danie.dine.Activities;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.example.danie.dine.Model.DatePickerFragment;
import com.example.danie.dine.R;

import java.text.DateFormat;
import java.util.Calendar;

public class BookingManagementActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_booking_management);

        Button btnDate = (Button) findViewById(R.id.btnDate);
        Button btnTime = (Button) findViewById(R.id.btnTime);
        TextView lblTime = (TextView) findViewById(R.id.lblTime);

        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "Date");
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
}
