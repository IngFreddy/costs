package com.example.costs.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.costs.database.model.Cost;

import java.util.ArrayList;
import java.util.List;

public class CostDB {

    private DatabaseHelper db;

    public CostDB(Context context){
        this.db = new DatabaseHelper(context);
    }

    public long insertCost(String name, String description, float price, String date, int categoryID) {

        ContentValues values = new ContentValues();
        values.put(Cost.COLUMN_NAME, name);
        values.put(Cost.COLUMN_DESCRIPTION, description);
        values.put(Cost.COLUMN_PRICE, price);
        values.put(Cost.COLUMN_TIMESTAMP, date);
        values.put(Cost.COLUMN_CATEGORYID, categoryID);

        return db.insertDB(Cost.TABLE_NAME, values);

    }

    public long insertCost(Cost cost) {

        ContentValues values = new ContentValues();
        values.put(Cost.COLUMN_NAME, cost.getName());
        values.put(Cost.COLUMN_DESCRIPTION, cost.getDescription());
        values.put(Cost.COLUMN_PRICE, cost.getPrice());
        values.put(Cost.COLUMN_TIMESTAMP, cost.getTimestamp());
        values.put(Cost.COLUMN_CATEGORYID, cost.getCategoryID());

        return db.insertDB(Cost.TABLE_NAME, values);

    }

    public long updateCost(Cost cost) {

        ContentValues values = new ContentValues();
        values.put(Cost.COLUMN_NAME, cost.getName());
        values.put(Cost.COLUMN_DESCRIPTION, cost.getDescription());
        values.put(Cost.COLUMN_PRICE, cost.getPrice());
        values.put(Cost.COLUMN_TIMESTAMP, cost.getTimestamp());
        values.put(Cost.COLUMN_CATEGORYID, cost.getCategoryID());

        //return mDb.update(TABLENAME, contentValues,"ID=?",new String[] {id});
        //long id = db.update(Cost.TABLE_NAME, values, "ID=?", new String[] {cost.getId() +""});

        return db.updateDB(Cost.TABLE_NAME, cost.getId(), values);

    }

    public long deleteCost(Cost cost) {
        return db.deleteDB(Cost.TABLE_NAME,cost.getId());
    }

    public long deleteCost(int id) {
        return db.deleteDB(Cost.TABLE_NAME, id);
    }


    public List<Cost> getAllDates() {
        List<Cost> costs = new ArrayList<>();

        String selectQuery = "SELECT SUM(" + Cost.COLUMN_PRICE + ")," + Cost.COLUMN_TIMESTAMP +
                " FROM " + Cost.TABLE_NAME + " GROUP BY " + Cost.COLUMN_TIMESTAMP + ";";

        SQLiteDatabase sqldb = db.getReadableDatabase();
        Cursor cursor = sqldb.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Cost note = new Cost(
                        0,
                        "", "",
                        cursor.getFloat(cursor.getColumnIndex("SUM(price)")),
                        cursor.getString(cursor.getColumnIndex(Cost.COLUMN_TIMESTAMP)),0);

                costs.add(note);
            } while (cursor.moveToNext());
        }

        sqldb.close();

        return costs;
    }

    public List<Cost> getCostsForDate(String date) {
        List<Cost> costs = new ArrayList<>();

//        String selectQuery = "SELECT  * FROM " + Cost.TABLE_NAME
//                + " WHERE " + Cost.COLUMN_TIMESTAMP + "=\"" + date
//                +"\" ORDER BY " + Cost.COLUMN_TIMESTAMP + " ASC";

        String selectQuery = "SELECT c.id, c.name, c.description, c.price, c.timestamp, c.category_id, categories.colour \n" +
                "\tFROM costs AS c LEFT JOIN categories ON c.category_id = categories.id \n" +
                "\tORDER BY c.timestamp ASC;";

        SQLiteDatabase sqldb = db.getReadableDatabase();
        Cursor cursor = sqldb.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Cost cos = new Cost(
                        cursor.getInt(cursor.getColumnIndex(Cost.COLUMN_ID)),
                        cursor.getString(cursor.getColumnIndex(Cost.COLUMN_NAME)),
                        cursor.getString(cursor.getColumnIndex(Cost.COLUMN_DESCRIPTION)),
                        cursor.getFloat(cursor.getColumnIndex(Cost.COLUMN_PRICE)),
                        cursor.getString(cursor.getColumnIndex(Cost.COLUMN_TIMESTAMP)),
                        cursor.getInt(cursor.getColumnIndex(Cost.COLUMN_CATEGORYID)));
                cos.setColor(cursor.getInt(cursor.getColumnIndex("colour")));

                costs.add(cos);
            } while (cursor.moveToNext());
        }
        sqldb.close();

        return costs;
    }

    public List<Cost> getAllCosts() {
        List<Cost> notes = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + Cost.TABLE_NAME + " ORDER BY " +
                Cost.COLUMN_TIMESTAMP + " ASC";

        SQLiteDatabase sqldb = db.getReadableDatabase();
        Cursor cursor = sqldb.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Cost note = new Cost(
                        cursor.getInt(cursor.getColumnIndex(Cost.COLUMN_ID)),
                        cursor.getString(cursor.getColumnIndex(Cost.COLUMN_NAME)),
                        cursor.getString(cursor.getColumnIndex(Cost.COLUMN_DESCRIPTION)),
                        cursor.getFloat(cursor.getColumnIndex(Cost.COLUMN_PRICE)),
                        cursor.getString(cursor.getColumnIndex(Cost.COLUMN_TIMESTAMP)),
                        cursor.getInt(cursor.getColumnIndex(Cost.COLUMN_CATEGORYID)));

                notes.add(note);
            } while (cursor.moveToNext());
        }

        sqldb.close();

        return notes;
    }
}
