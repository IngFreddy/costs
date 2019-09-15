package com.example.costs.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.costs.database.model.Category;
import com.example.costs.database.model.Cost;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 9;
    private static final String DATABASE_NAME = "costs_db";

    private Context context;
    private SQLiteDatabase _db;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Cost.CREATE_TABLE);
        db.execSQL(Category.CREATE_TABLE);

        preFillDatabase(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Cost.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Category.TABLE_NAME);

        onCreate(db);

    }

    public void preFillDatabase(SQLiteDatabase db){
        db.insert(Category.TABLE_NAME, null, insertCategory("Food", "Necessary food", 65280));
        db.insert(Category.TABLE_NAME, null, insertCategory("Electronic", "Not so necessary", 255));
        db.insert(Category.TABLE_NAME, null, insertCategory("Drugstore", "Cleaning stuff", 65535));
        db.insert(Category.TABLE_NAME, null, insertCategory("Medicine", "Pills, vitamins", -43230));
        db.insert(Category.TABLE_NAME, null, insertCategory("Other", "Others, not categorized", -65281));
    }

    public ContentValues insertCategory(String name, String description, int colour) {
        ContentValues values = new ContentValues();
        values.put(Category.COLUMN_NAME, name);
        values.put(Category.COLUMN_DESCRIPTION, description);
        values.put(Category.COLUMN_COLOUR, colour);

        return values;
    }

    public Cursor selectDB(String querry){
        this._db = this.getReadableDatabase();
        Cursor cursor = _db.rawQuery(querry, null);

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
        long id = db.delete(tableName, "id = ?", new String[] {Integer.toString(idDelete)});

        db.close();

        return id;
    }


    public void closeDB(){
        this._db.close();
    }
}
