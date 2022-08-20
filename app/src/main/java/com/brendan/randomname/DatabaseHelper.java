package com.brendan.randomname;

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
    public static final int INSERTION_ERROR = -1;

    public DatabaseHelper(@Nullable Context context) {
        super(context, "name.sqLiteDatabase", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTableStatement = "CREATE TABLE " + NAME_TABLE + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_NAME + " TEXT)";
        sqLiteDatabase.execSQL(createTableStatement);
    }

    //This is called if the database version changes. It prevents previous users apps from breaking when you change the database design.
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public boolean addOne(SavedNameModel savedNameModel) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME, savedNameModel.getName());
        long rowID = sqLiteDatabase.insert(NAME_TABLE, null, contentValues);
        return (rowID != INSERTION_ERROR);
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

        cursor.close();
        sqLiteDatabase.close();

        return returnList;
    }

    public void delete(SavedNameModel savedNameModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        String queryString = "DELETE FROM " + NAME_TABLE + " WHERE " + COLUMN_ID + " = " + savedNameModel.getId();
        db.rawQuery(queryString, null).moveToFirst();
    }

    public void deleteAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        String queryString = "DELETE FROM " + "CUSTOMER_TABLE";
        db.rawQuery(queryString, null).moveToFirst();
    }
}
