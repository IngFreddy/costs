package com.example.costs.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.costs.database.model.Cost;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 3;
    private static final String DATABASE_NAME = "costs_db";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Cost.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Cost.TABLE_NAME);
        onCreate(db);
    }

    public Cursor selectDB(String querry){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(querry, null);

        db.close();

        return cursor;
    }

    public long insertDB(String tableName, ContentValues values){
        SQLiteDatabase db = this.getWritableDatabase();

        long id = db.insert(tableName, null, values);

        db.close();

        return id;
    }

    //        long id = db.update(Cost.TABLE_NAME, values, "ID=?", new String[] {cost.getId() +""});
    public long updateDB(String tableName, int idUpdate, ContentValues values){
        SQLiteDatabase db = this.getWritableDatabase();

        long id = db.update(tableName, values, "ID=?", new String[] {Integer.toString(idUpdate)});

        db.close();

        return id;
    }

    public long deleteDB(String tableName, int idDelete){
        SQLiteDatabase db = this.getWritableDatabase();

        //long id = db.delete(Cost.TABLE_NAME,"ID=?",new String[]{cost.getId() +""});
        long id = db.delete(tableName, "ID=?", new String[] {Integer.toString(idDelete)});

        db.close();

        return id;
    }

}
