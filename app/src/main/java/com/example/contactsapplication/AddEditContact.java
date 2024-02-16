package com.example.contactsapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import androidx.appcompat.app.ActionBar;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class AddEditContact extends AppCompatActivity {

    private ImageView profileIv;
    private EditText nameEt,phoneEt, emailEt,noteEt;
    private FloatingActionButton fab;

    private Boolean isEditeMode;
    private String id,name,phone, email,note, addedTime,updateTime;


    private ActionBar actionBar;

    //DB
    private DbHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_contact);

        //init db
        dbHelper = new DbHelper(this);


        //init actionBar
        actionBar = getSupportActionBar();

        //set title action bar
        if(actionBar != null) {
            actionBar.setTitle("add Contact");
        }
        //back button
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }


        //init view
        profileIv = findViewById(R.id.profileIv);
        nameEt = findViewById(R.id.nameEt);
        phoneEt = findViewById(R.id.phoneEt);
        emailEt = findViewById(R.id.emailEt);
        noteEt = findViewById(R.id.noteEt);
        fab = findViewById(R.id.fab);

        Intent intent = getIntent();
        isEditeMode = intent.getBooleanExtra("isEditMode",false);
        if(isEditeMode){
            actionBar.setTitle("Update Contact");

            //get the other val from intent
            id = intent.getStringExtra("ID");
            name = intent.getStringExtra("NAME");
            phone = intent.getStringExtra("PHONE");
            email = intent.getStringExtra("EMAIL");
            note = intent.getStringExtra("NOTE");
            addedTime = intent.getStringExtra("ADDEDTIME");
            updateTime = intent.getStringExtra("UPDATETIME");


            //set val in editText field
            nameEt.setText(name);
            phoneEt.setText(phone);
            emailEt.setText(email);
            noteEt.setText(note);

        }

        //add even handler
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
            }
        });
    }
//


    private void saveData() {
        //take user giver data IN VARIABLE
        name = nameEt.getText().toString();
        phone = phoneEt.getText().toString();
        email = emailEt.getText().toString();
        note = noteEt.getText().toString();

        //current time to added time
        String timeStamp = ""+System.currentTimeMillis();



        if(!name.isEmpty() || !phone.isEmpty() || !email.isEmpty() || !note.isEmpty()){
            //check edit or add mode to save data in sql
            if (isEditeMode){
                //edit mode
                dbHelper.updateContact(""+id,
                        ""+name,""+phone,""+email,""+note,""+timeStamp,""+timeStamp
                );
                Toast.makeText(getApplicationContext(),"Updated", Toast.LENGTH_SHORT).show();
            }else{
                //add mode
                long id = dbHelper.insertContact(
                        ""+name,""+phone,""+email,""+note,""+timeStamp,""+timeStamp
                );

                //check insert data
                Toast.makeText(getApplicationContext(),"Inserted "+id, Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(getApplicationContext(),"Nothing to save!!",Toast.LENGTH_SHORT).show();
        }
    }

    //back button click
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

}