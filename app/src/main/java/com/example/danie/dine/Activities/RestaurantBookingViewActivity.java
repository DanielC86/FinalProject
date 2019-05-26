package com.example.danie.dine.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.danie.dine.Model.DinnerEvent;
import com.example.danie.dine.Model.TableInformation;
import com.example.danie.dine.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class RestaurantBookingViewActivity extends AppCompatActivity {

    private String rBookingInfo;
    public String key;

    private Boolean itemSelected = false;
    private int selectedPosition = 0;

    private String tableRequest;
    String requestDeclined;
    String requestAccepted;

    private Button btnRAccept;
    private Button btnRDecline;

    private ListView restaurantListView;

    private ArrayList<String> restaurantArrayList = new ArrayList<>();
    private ArrayAdapter<String> restaurantAdapter;
    ArrayList<String> listKeys = new ArrayList<String>();

    //firebase for table
    private FirebaseAuth tableAuth;
    private DatabaseReference tableRef;
    private FirebaseDatabase tableDatabase;
    private FirebaseAuth.AuthStateListener tableAuthListener;
    private String tableID;

    //firebase for dinner
    private FirebaseAuth dinnerAuth;
    private DatabaseReference dinnerRef;
    private FirebaseDatabase dinnerDatabase;
    private FirebaseAuth.AuthStateListener dinnerAuthListener;
    private String dinnerID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_booking_view);

        btnRAccept = findViewById(R.id.btnRAccept);
        btnRDecline = findViewById(R.id.btnRDecline);

        btnRAccept.setVisibility(View.INVISIBLE);
        btnRDecline.setVisibility(View.INVISIBLE);

        //Firebase for dinner initialization
        dinnerAuth = FirebaseAuth.getInstance();
        FirebaseUser currentDinner = dinnerAuth.getCurrentUser();
        dinnerID = currentDinner.getUid();
        dinnerDatabase = FirebaseDatabase.getInstance();
        dinnerRef = dinnerDatabase.getReference().child("DinnerEvent");


        //Firebase for table initialization
        tableAuth = FirebaseAuth.getInstance();
        FirebaseUser currentTable = tableAuth.getCurrentUser();
        tableID = currentTable.getUid();
        tableDatabase = FirebaseDatabase.getInstance();
        tableRef = tableDatabase.getReference().child("Tables");

        restaurantListView = findViewById(R.id.restaurantListView);
        restaurantAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, restaurantArrayList);
        restaurantListView.setAdapter(restaurantAdapter);




        //display booking requests elements in listView
        tableRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                TableInformation rTableInfo = dataSnapshot.getValue(TableInformation.class);
                rTableInfo.setRequestKey(dataSnapshot.getKey());
                key = rTableInfo.getRequestKey().toString();
                rBookingInfo = ("Request for " + rTableInfo.getBookingName() + " date: " + rTableInfo.getBookingDate() + ", time: " + rTableInfo.getBookingTime() + ", guest number: " + rTableInfo.getBookingGuestNumber());
                restaurantArrayList.add(rBookingInfo);

                listKeys.add(dataSnapshot.getKey());
                restaurantAdapter.notifyDataSetChanged();

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                String key = dataSnapshot.getKey();
                int index = listKeys.indexOf(key);
                if (index != -1) {
                    restaurantArrayList.remove(index);
                    listKeys.remove(index);
                    restaurantAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                String key = dataSnapshot.getKey();
                int index = listKeys.indexOf(key);
                if (index != -1) {
                    restaurantArrayList.remove(index);
                    listKeys.remove(index);
                    restaurantAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //trying to get list item value
        restaurantListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tableRequest = restaurantArrayList.get(position);
                showMessage(listKeys.get(selectedPosition));
                selectedPosition = position;
                itemSelected = true;

                btnRAccept.setVisibility(View.VISIBLE);
                btnRDecline.setVisibility(View.VISIBLE);
                /*
                restaurantArrayList.get(position);
                tableRef.child("Tables").getKey();
                tableRef.removeValue();
                restaurantAdapter.notifyDataSetChanged();
                */

            }
        });

        btnRDecline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tableRequest = ("DECLINED " + tableRequest);
                dineEvent();
                deleteItem(restaurantListView);


            }
        });

        btnRAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tableRequest = ("ACCEPTED " + tableRequest);
                dineEvent();
                deleteItem(restaurantListView);
            }
        });


    }
    //messaging method
    private void showMessage(String s) {
        Toast.makeText(getApplicationContext(), s,Toast.LENGTH_SHORT).show();
    }


    public void deleteItem(View view) {
        restaurantListView.setItemChecked(selectedPosition, false);
        tableRef.child(listKeys.get(selectedPosition)).removeValue();
    }

    public void dineEvent(){
        String dinnerRequest = tableRequest;
        DinnerEvent currentDinner = new DinnerEvent(dinnerRequest);
        FirebaseUser dinnerTable = dinnerAuth.getCurrentUser();
        dinnerRef.setValue(currentDinner);

    }

    private void logoff() {
        tableAuth.signOut();
        Intent welcomeActivity = new Intent(getApplicationContext(), WelcomeActivity.class);
        startActivity(welcomeActivity);
        finish();
    }

    private void uploadMenu(){
        Intent menuUploaderActivity = new Intent(getApplicationContext(), UploadMenuActivity.class);
        startActivity(menuUploaderActivity);
    }

    private void dinnerEvents(){
        Intent allBookingsActivity = new Intent(getApplicationContext(), AllBookingsActivity.class);
        startActivity(allBookingsActivity);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.restaurant_settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.dinnerEvents:
                dinnerEvents();
                return true;
            case R.id.uploadMenu:
                uploadMenu();
                return true;
            case R.id.logOut:
                logoff();
                return true;
            default:return super.onOptionsItemSelected(item);
        }

    }
}
