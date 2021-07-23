package com.example.storageinandroid.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.storageinandroid.model.CustomerModel;
import com.example.storageinandroid.utils.Util;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    //This is called when the database is accessed for the first time.
    //Here should be the code for creating new database
    public DatabaseHelper(Context context){
        super(context, Util.DATABASE_NAME, null, Util.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
       String create_table = "CREATE TABLE "+Util.TABLE_NAME+
                            "(" +Util.KEY_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "
                                +Util.KEY_NAME+" TEXT, "
                                +Util.KEY_AGE+" INTEGER, "
                                +Util.KEY_ACTIVE_CUSTOMER+" BOOL)";
       db.execSQL(create_table);
    }

    //This is called when the version of the database is changed,
    //It prevents user apps from breaking when you change the database design
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean addOne(CustomerModel customerModel){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(Util.KEY_NAME, customerModel.getName());
        cv.put(Util.KEY_AGE, customerModel.getAge());
        cv.put(Util.KEY_ACTIVE_CUSTOMER, customerModel.isActive());

        long insert = db.insert(Util.TABLE_NAME, null, cv);

        return insert != -1;    //insert -1 means insertion in the db fails
    }

    public boolean deleteOne(CustomerModel customerModel){
        //finds customerModel in the database. if it found, delete it and return true
        //if it not found return false

        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM "+Util.TABLE_NAME+" WHERE "+Util.KEY_ID+" = "+customerModel.getId();

        Cursor cursor = db.rawQuery(query, null);

        return cursor.moveToFirst();
    }

    public List<CustomerModel> getAll(){
        List<CustomerModel> list = new ArrayList<>();

        //get data from the database
        String qurey = "SELECT * FROM "+Util.TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(qurey, null);

        if(cursor.moveToFirst()){
            //loop through the results and
            //create a new customer object and put them in the list
            do{
                int customerID = cursor.getInt(0);
                String customerName = cursor.getString(1);
                int customerAge = cursor.getInt(2);
                boolean customerIsActive = cursor.getInt(3) == 1;

                CustomerModel customerModel = new CustomerModel(customerID, customerName, customerAge, customerIsActive);
                list.add(customerModel);
            }while(cursor.moveToNext());
        }else{
            //failure do not add anything to the list
        }
        //close both the cursor and the db

        return  list;
    }
}
