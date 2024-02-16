package com.example.contactsapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.ImageView;
import android.widget.TextView;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class ContactDetails extends AppCompatActivity {

    private TextView nameTv, phoneTv, emailTv, addedTimeTv,updatedTimeTv,noteTv;
    private ImageView profileIv;

    private String id;
    private DbHelper dbHelper;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_details);

        //init db
        dbHelper = new DbHelper(this);

        //get data from intent
        Intent intent = getIntent();
        id = intent.getStringExtra("contactId");

        //init view
        nameTv = findViewById(R.id.nameTv);
        phoneTv = findViewById(R.id.phoneTv);
        emailTv = findViewById(R.id.emailTv);
        addedTimeTv = findViewById(R.id.addedTimeTv);
        updatedTimeTv = findViewById(R.id.updatedTimeTv);
        noteTv = findViewById(R.id.noteTv);

        profileIv = findViewById(R.id.profileIv);

        loadDataById();
    }

    private void loadDataById() {
        //get data from db
        String selectQuery = "SELECT * FROM "+Constants.TABLE_NAME + " WHERE " + Constants.C_ID + " =\"" +
                id + "\"";

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if(cursor.moveToFirst()){
            do {
                String name =  ""+cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_NAME));
                String phone =""+cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_PHONE));
                String email =""+cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_EMAIL));
                String note =""+cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_NOTE));
                String addTime = ""+cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_ADDED_TIME));
                String updateTime =""+cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_UPDATE_TIME));

                Calendar calendar = Calendar.getInstance(Locale.getDefault());
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                calendar.setTimeInMillis(Long.parseLong(addTime));

                String timeAdd = "" + dateFormat.format(calendar.getTime());

                calendar.setTimeInMillis(Long.parseLong(updateTime));
                String timeUpdate = "" + dateFormat.format(calendar.getTime());


                //set data
                nameTv.setText(name);
                phoneTv.setText(phone);
                emailTv.setText(email);
                noteTv.setText(note);
                addedTimeTv.setText(timeAdd);
                updatedTimeTv.setText(timeUpdate);



            }while (cursor.moveToNext());
        }
        db.close();
    }

}