package com.example.danie.dine.Activities;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.danie.dine.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class UploadMenuActivity extends AppCompatActivity {


    private Button btnPickFile;
    private Button btnUpload;
    private Button btnUploadBack;


    Uri menuUri; //URL for local store - path for local file

    private TextView menuName;

    FirebaseStorage rStorage;
    FirebaseDatabase rDatabase;

    ProgressDialog progressDialog;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_menu);


        //firebase storage initialization
        rStorage = FirebaseStorage.getInstance();
        rDatabase = FirebaseDatabase.getInstance();

        menuName = findViewById(R.id.menuName);


        btnPickFile = findViewById(R.id.btnPickFile);
        btnUpload = findViewById(R.id.btnUpload);
        btnUploadBack = findViewById(R.id.btnUploadBack);


        btnUploadBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack();

            }
        });

        btnPickFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ContextCompat.checkSelfPermission(UploadMenuActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

                    chooseFile();

                }
                else{
                    ActivityCompat.requestPermissions(UploadMenuActivity.this,  new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 9);
                }

            }
        });

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(menuUri!=null){
                    uploadFile(menuUri);
                }
                else {
                    showMessage("please select a file!");
                }
            }
        });

    }

    private void uploadFile(Uri menuUri){

        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setTitle("Uploading Menu... ");
        progressDialog.show();
        progressDialog.setProgress(0);

        StorageReference storageReference = rStorage.getReference();

        final String filename = "menu";

        storageReference.child("Menus").child(filename).putFile(menuUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        String url = taskSnapshot.getStorage().getDownloadUrl().toString();
                        DatabaseReference reference = rDatabase.getReference();

                        reference.child(filename).setValue(url).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                      showMessage(("Menu Uploaded Successfully!"));
                                }
                                else {
                                    showMessage("Menu not uploaded, something went wrong...");
                                }
                            }
                        });

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                showMessage("Menu not uploaded, something went wrong...");

            }
        })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                int currentProgress = (int) (100 * taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                progressDialog.setProgress(currentProgress) ;
            }
        });


    }

    private void goBack(){
        Intent restaurantBookingViewActivity = new Intent(getApplicationContext(), RestaurantBookingViewActivity.class);
        startActivity(restaurantBookingViewActivity);
        finish();
    }

    private void chooseFile(){

        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 86);

    }

    private void showMessage(String s) {
        Toast.makeText(getApplicationContext(), s,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == 9 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            uploadFile(menuUri);
        }
        else {
            showMessage("Please allow app to browse your files");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if(requestCode == 86 && resultCode == RESULT_OK && data!=null) {

            menuUri=data.getData();
            menuName.setText(data.getData().getLastPathSegment());

        }
        else {
            showMessage("Please select a file...");
        }
    }
}
