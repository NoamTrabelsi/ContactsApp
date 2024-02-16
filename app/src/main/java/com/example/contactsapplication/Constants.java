package com.example.contactsapplication;

public class Constants {

    public static final String DATABASE_NAME = "CONTACT_DB";
    //db version
    public static final int DATABASE_VERSION = 1;


    //table name
    public static final String TABLE_NAME = "CONTACT_TABLE";

    //table column or field name
    public static final String C_ID="ID";
    public static final String C_NAME="NAME";
    public static final String C_PHONE="PHONE";
    public static final String C_EMAIL="EMAIL";
    public static final String C_NOTE="NOTE";
    public static final String C_ADDED_TIME="ADDED_TIME";
    public static final String C_UPDATE_TIME="UPDATE_TIME";


    public static final String
            CREATE_TABLE = "CREATE TABLE "
            + TABLE_NAME + "( "
            + C_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + C_NAME + " TEXT, "
            + C_PHONE + " TEXT, "
            + C_EMAIL + " TEXT, "
            + C_NOTE + " TEXT, "
            + C_ADDED_TIME + " TEXT, "
            + C_UPDATE_TIME + " TEXT"
            +" );"
            ;

}
