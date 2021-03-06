package com.example.danie.dine.Activities;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.danie.dine.Model.UserInformation;
import com.example.danie.dine.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import static android.os.Environment.DIRECTORY_DOWNLOADS;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private Intent WelcomeActivity;
    private Intent BookingManagementActivity;
    private Button btnBookTable;
    private Button btnUserViewBooking;


    //firebase elements
    private FirebaseAuth mAuth;
    private DatabaseReference mRef;
    private FirebaseDatabase mDatabase;
    private FirebaseAuth.AuthStateListener mAuthListener;

    //firebase Storage
    FirebaseStorage menuStorage;
    StorageReference menuReference;
    StorageReference fileRef;



    //firebase for table
    private FirebaseAuth tableAuth;
    private DatabaseReference tableRef;
    private FirebaseDatabase tableDatabase;
    private FirebaseAuth.AuthStateListener tableAuthListener;

    //ui elements
    private TextView lblUserName;
    private TextView lblUserEmail;
    private TextView lblUserPhone;
    private TextView tableUserView;
    //private TextView emailView;
    // variable to store id of currently logged in user
    private String userID;
    private String tableID;

    private Button btnViewMenu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WelcomeActivity = new Intent(this,com.example.danie.dine.Activities.WelcomeActivity.class);
        BookingManagementActivity = new Intent(this,com.example.danie.dine.Activities.BookingManagementActivity.class);
        setContentView(R.layout.activity_home);

        btnViewMenu = findViewById(R.id.btnViewMenu);
        btnUserViewBooking = findViewById(R.id.btnUserViewBooking);

        //tableUserView.setText("you have no recent bookings");

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        userID = currentUser.getUid();
        mDatabase = FirebaseDatabase.getInstance();
        mRef = mDatabase.getReference().child("Users");

        //not in use
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


        lblUserName = (TextView)findViewById(R.id.lblUsername);
        lblUserEmail = (TextView)findViewById(R.id.lblUserEmail);
        lblUserPhone = (TextView)findViewById(R.id.lblUserPhone);
        //tableUserView = (TextView)findViewById(R.id.tableUserView);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Menu");
        setSupportActionBar(toolbar);


        //display user info
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
                lblUserName.setText(userDetails.getUserName());
                lblUserEmail.setText(userDetails.getUserEmail());
                lblUserPhone.setText(userDetails.getPhoneNumber());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btnUserViewBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                viewUserBooking();
            }
        });


        btnViewMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewMenu();
            }
        });



        //floating button
        FloatingActionButton btnBookTable = (FloatingActionButton) findViewById(R.id.btnBookTable);
        btnBookTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Lets go for a dinner!", Snackbar.LENGTH_LONG);
                BookingManagement();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    //drawer elements
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_logout) {
            // Handle log off
            logoff();
            finish();
        }
        else if (id == R.id.nav_edit_details) {
            //open edit user details activity, for testing purposes set to log off
            editDetails();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //here method to go to booking management activity
    private void BookingManagement() {
        startActivity(BookingManagementActivity);
    }
        //this method to log off
    private void logoff() {
        mAuth.signOut();
        startActivity(WelcomeActivity);
        finish();

    }


    private void showMessage(String message) {

        //showing toast message method
        Toast.makeText(getApplicationContext(), message,Toast.LENGTH_SHORT).show();
    }

    private void viewMenu(){
        menuReference = menuStorage.getInstance().getReference();
        fileRef = menuReference.child("Menus/menu.pdf");
        fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                String url = uri.toString();
                downloadMenu(HomeActivity.this, "menu", ".pdf", DIRECTORY_DOWNLOADS, url);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

    private void viewUserBooking(){
        Intent userBookingView = new Intent(getApplicationContext(), UserBookingView.class);
        startActivity(userBookingView);
    }

    private void downloadMenu(Context context, String filename, String fileExtension, String destinationDirectory, String Url){

        DownloadManager downloadManager = (DownloadManager) context.
                getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(Url);
        DownloadManager.Request request = new DownloadManager.Request(uri);

        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalFilesDir(context, destinationDirectory, filename + fileExtension);
        downloadManager.enqueue(request);

    }

    private void editDetails(){
        Intent editDetailsActivity = new Intent(getApplicationContext(), EditDetailsActivity.class);
        startActivity(editDetailsActivity);
    }


    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

}
