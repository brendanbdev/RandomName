package com.example.randomname;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String NAME_TABLE = "CUSTOMER_TABLE";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_NAME = "NAME";

    public DatabaseHelper(@Nullable Context context) {
        super(context, "name.sqLiteDatabase", null, 1);
    }

    //this is called the first time a database is accessed. There should be code in here to create a new database.
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTableStatement = "CREATE TABLE " + NAME_TABLE + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_NAME + " TEXT)";

        sqLiteDatabase.execSQL(createTableStatement);
    }

    //this is called if the database version changes. It prevents previous users apps from breaking when you change the database design.
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public boolean addOne(SavedNameModel savedNameModel) {

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_NAME, savedNameModel.getName());

        long insert = sqLiteDatabase.insert(NAME_TABLE, null, contentValues);
        if (insert == -1) {
            return false;
        } else {
            return true;
        }
    }

    public List<SavedNameModel> getAllNames() {
        List<SavedNameModel> returnList = new ArrayList<>();
        //get data from the database
        String queryString = "SELECT * FROM " + NAME_TABLE;
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(queryString, null);

        if (cursor.moveToFirst()) {
            // loop through the cursor (result set) and create new customer objects, then put them into the return list.
            do {
                int nameID = cursor.getInt(0);
                String name = cursor.getString(1);

                SavedNameModel newSavedName = new SavedNameModel(nameID, name);
                returnList.add(newSavedName);
            } while (cursor.moveToNext());
        }
        else {
            //Failure. Do nothing.
        }

        cursor.close();
        sqLiteDatabase.close();

        return returnList;
    }
}
