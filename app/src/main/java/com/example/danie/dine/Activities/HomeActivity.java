package com.example.danie.dine.Activities;

import android.content.Intent;
import android.os.Bundle;
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
import android.widget.ListView;
import android.widget.TextView;

import com.example.danie.dine.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private Intent WelcomeActivity;
    private Intent BookingManagementActivity;
    private Button btnBookTable;

    private TextView lblUserName;
    private TextView lblUserEmail;
    private TextView lblUserPhone;

    String userFirstName;
    String userEmail;
    String userPhone;


    ListView lsUserInfo;


    FirebaseDatabase dineDB;
    DatabaseReference userInfo;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WelcomeActivity = new Intent(this,com.example.danie.dine.Activities.WelcomeActivity.class);
        BookingManagementActivity = new Intent(this,com.example.danie.dine.Activities.BookingManagementActivity.class);
        setContentView(R.layout.activity_home);


        lblUserName = (TextView)findViewById(R.id.lblUsername);
        lblUserEmail = (TextView)findViewById(R.id.lblUserEmail);
        lblUserPhone = (TextView)findViewById(R.id.lblUserPhone);







        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Menu");
        setSupportActionBar(toolbar);






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

        //Set logged user name
        //View headerView = navigationView.getHeaderView(0);
        //txtFirstName = (TextView)findViewById(R.id.txtFirstName);
        //txtFirstName.setText(Common.loggedUser.getName());
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
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_logout) {
            // Handle log off
            logoff();
            finish();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //here method to go to booking management activity
    private void BookingManagement() {
        startActivity(BookingManagementActivity);
    }

    private void logoff() {

        startActivity(WelcomeActivity);
        finish();

    }



}
