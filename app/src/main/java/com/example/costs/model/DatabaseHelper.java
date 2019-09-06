package com.example.costs.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import com.example.costs.model.CostDB;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "notes_db";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CostDB.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + CostDB.TABLE_NAME);
        onCreate(db);
    }

    public long insertCost(String name, String description, float price, String date) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(CostDB.COLUMN_NAME, name);
        values.put(CostDB.COLUMN_DESCRIPTION, description);
        values.put(CostDB.COLUMN_PRICE, price);
        values.put(CostDB.COLUMN_TIMESTAMP, date);

        long id = db.insert(CostDB.TABLE_NAME, null, values);

        db.close();

        return id;
    }

    public long insertCost(CostDB cost) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(CostDB.COLUMN_NAME, cost.getName());
        values.put(CostDB.COLUMN_DESCRIPTION, cost.getDescription());
        values.put(CostDB.COLUMN_PRICE, cost.getPrice());
        values.put(CostDB.COLUMN_TIMESTAMP, cost.getTimestamp());

        long id = db.insert(CostDB.TABLE_NAME, null, values);

        db.close();

        return id;
    }

    public long updateCost(CostDB cost) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(CostDB.COLUMN_NAME, cost.getName());
        values.put(CostDB.COLUMN_DESCRIPTION, cost.getDescription());
        values.put(CostDB.COLUMN_PRICE, cost.getPrice());
        values.put(CostDB.COLUMN_TIMESTAMP, cost.getTimestamp());

        long id = db.insert(CostDB.TABLE_NAME, null, values);

        db.close();

        return id;
    }


    public List<CostDB> getAllDates() {
        List<CostDB> costs = new ArrayList<>();

        String selectQuery = "SELECT SUM(" + CostDB.COLUMN_PRICE + ")," + CostDB.COLUMN_TIMESTAMP +
                    " FROM " + CostDB.TABLE_NAME + " GROUP BY " + CostDB.COLUMN_TIMESTAMP + ";";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                CostDB note = new CostDB(
                        0,
                        "", "",
                        cursor.getFloat(cursor.getColumnIndex("SUM(price)")),
                        cursor.getString(cursor.getColumnIndex(CostDB.COLUMN_TIMESTAMP)));

                costs.add(note);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return notes list
        return costs;
    }

    public List<CostDB> getCostsForDate(String date) {
        List<CostDB> costs = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + CostDB.TABLE_NAME
                + " WHERE " + CostDB.COLUMN_TIMESTAMP + "=\"" + date
                +"\" ORDER BY " + CostDB.COLUMN_TIMESTAMP + " ASC";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                CostDB note = new CostDB(
                        cursor.getInt(cursor.getColumnIndex(CostDB.COLUMN_ID)),
                        cursor.getString(cursor.getColumnIndex(CostDB.COLUMN_NAME)),
                        cursor.getString(cursor.getColumnIndex(CostDB.COLUMN_DESCRIPTION)),
                        cursor.getFloat(cursor.getColumnIndex(CostDB.COLUMN_PRICE)),
                        cursor.getString(cursor.getColumnIndex(CostDB.COLUMN_TIMESTAMP)));

                costs.add(note);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return notes list
        return costs;
    }

    public List<CostDB> getAllCosts() {
        List<CostDB> notes = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + CostDB.TABLE_NAME + " ORDER BY " +
                CostDB.COLUMN_TIMESTAMP + " ASC";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                CostDB note = new CostDB(
                        cursor.getInt(cursor.getColumnIndex(CostDB.COLUMN_ID)),
                        cursor.getString(cursor.getColumnIndex(CostDB.COLUMN_NAME)),
                        cursor.getString(cursor.getColumnIndex(CostDB.COLUMN_DESCRIPTION)),
                        cursor.getFloat(cursor.getColumnIndex(CostDB.COLUMN_PRICE)),
                        cursor.getString(cursor.getColumnIndex(CostDB.COLUMN_TIMESTAMP)));

                notes.add(note);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return notes list
        return notes;
    }

}
