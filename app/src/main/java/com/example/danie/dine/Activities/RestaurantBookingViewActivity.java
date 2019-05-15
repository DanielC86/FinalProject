package com.example.danie.dine.Activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

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
    private String rBookingName;
    private String rBookingDate;
    private String rBookingTime;
    private String rBookingGuests;

    private ListView restaurantListView;

    private ArrayList<String> restaurantArrayList = new ArrayList<>();
    private ArrayAdapter<String> restaurantAdapter;

    //firebase for table
    private FirebaseAuth tableAuth;
    private DatabaseReference tableRef;
    private FirebaseDatabase tableDatabase;
    private FirebaseAuth.AuthStateListener tableAuthListener;

    private String tableID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_booking_view);

        //Firebase for table initialization
        tableAuth = FirebaseAuth.getInstance();
        FirebaseUser currentTable = tableAuth.getCurrentUser();
        tableID = currentTable.getUid();
        tableDatabase = FirebaseDatabase.getInstance();
        tableRef = tableDatabase.getReference().child("Tables");

        restaurantListView = findViewById(R.id.restaurantListView);
        restaurantAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, restaurantArrayList);
        restaurantListView.setAdapter(restaurantAdapter);




        tableRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                TableInformation rTableInfo = dataSnapshot.getValue(TableInformation.class);
                rBookingInfo = ("Request for " + rTableInfo.getBookingName() + " date: " + rTableInfo.getBookingDate() + ", time: " + rTableInfo.getBookingTime() + ", guest number: " + rTableInfo.getBookingGuestNumber());
                restaurantArrayList.add(rBookingInfo);

                restaurantAdapter.notifyDataSetChanged();

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

        restaurantListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                view.setSelected(true);
                //todo
                showMessage("item pressed");
            }
        });

    }
    //messaging method
    private void showMessage(String s) {
        Toast.makeText(getApplicationContext(), s,Toast.LENGTH_SHORT).show();
    }

}
