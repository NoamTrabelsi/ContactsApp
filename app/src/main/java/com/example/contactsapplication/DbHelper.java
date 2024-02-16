package com.example.contactsapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;
import java.util.ArrayList;


public class DbHelper extends SQLiteOpenHelper {
    public DbHelper(@Nullable Context context) {
        super(context, Constants.DATABASE_NAME, null,Constants.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //create table on database
        db.execSQL(Constants.CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //upgrade table
        db.execSQL("DROP TABLE IF EXISTS "+ Constants.TABLE_NAME);
        //create table again
        onCreate(db);
    }

    public long insertContact(String name,String phone,String email,String note,String addedTime,String updatedTime){

        //get writable db
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Constants.C_NAME,name);
        contentValues.put(Constants.C_PHONE,phone);
        contentValues.put(Constants.C_EMAIL,email);
        contentValues.put(Constants.C_NOTE,note);
        contentValues.put(Constants.C_ADDED_TIME,addedTime);
        contentValues.put(Constants.C_UPDATE_TIME,updatedTime);

        //insert data in row
        long id = db.insert(Constants.TABLE_NAME,null,contentValues);

        db.close();
        return id ;
    }

    //Update function to update data in db
    public void updateContact(String id, String name,String phone,String email,String note,String addedTime,String updatedTime){

        //get writable db
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Constants.C_NAME,name);
        contentValues.put(Constants.C_PHONE,phone);
        contentValues.put(Constants.C_EMAIL,email);
        contentValues.put(Constants.C_NOTE,note);
        contentValues.put(Constants.C_ADDED_TIME,addedTime);
        contentValues.put(Constants.C_UPDATE_TIME,updatedTime);

        //update data in row
        db.update(Constants.TABLE_NAME,contentValues,Constants.C_ID + " =? ", new String[]{id} );

        db.close();
    }


    //delete data by id
    public void deleteContact(String id){
        //get writable db
        SQLiteDatabase db = getWritableDatabase();

        db.delete(Constants.TABLE_NAME,"WHERE"+" =? ", new String[]{id});
        db.close();
    }
    //delete all data
    public void deleteAllContact(){
        //get writable db
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " +Constants.TABLE_NAME);
        db.close();
    }

    //get data
    public ArrayList<ModelContact> getAllData(String orderBy){
        //create arrayList
        ArrayList<ModelContact> arrayList = new ArrayList<>();
        //sql command query
        String selectQuery = "SELECT * FROM "+ Constants.TABLE_NAME +" ORDER BY " + orderBy;

        //get readable db
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,null);


        //looping though all record and add to list
        if(cursor.moveToFirst()){
            do{
                ModelContact modelContact = new ModelContact(
                        ""+cursor.getInt(cursor.getColumnIndexOrThrow(Constants.C_ID)),
                        ""+cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_NAME)),
                        ""+cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_PHONE)),
                        ""+cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_EMAIL)),
                        ""+cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_NOTE)),
                        ""+cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_ADDED_TIME)),
                        ""+cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_UPDATE_TIME))
                );
                arrayList.add(modelContact);
            }while (cursor.moveToNext());
        }
        db.close();

        return arrayList;
    }
}
