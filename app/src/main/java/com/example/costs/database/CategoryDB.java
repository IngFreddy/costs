package com.example.costs.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.costs.database.model.Category;
import com.example.costs.database.model.Cost;

import java.util.ArrayList;
import java.util.List;

public class CategoryDB {

    private DatabaseHelper db;

    public CategoryDB(Context context){
        this.db = new DatabaseHelper(context);
    }

    public long insertCategory(String name, String description, String colour) {

        ContentValues values = new ContentValues();
        values.put(Category.COLUMN_NAME, name);
        values.put(Category.COLUMN_DESCRIPTION, description);
        values.put(Category.COLUMN_COLOUR, colour);

        return db.insertDB(Category.TABLE_NAME, values);

    }

    public long updateCategory(Category category) {

        ContentValues values = new ContentValues();
        values.put(Category.COLUMN_NAME, category.getName());
        values.put(Category.COLUMN_DESCRIPTION, category.getDescription());
        values.put(Category.COLUMN_COLOUR, category.getColour());

        return db.updateDB(Cost.TABLE_NAME, category.getId(), values);

    }

    public long deleteCategory(Category category) {
        return db.deleteDB(Cost.TABLE_NAME,category.getId());
    }

    public long deleteCategory(int id) {
        return db.deleteDB(Category.TABLE_NAME, id);
    }


    public List<Category> getAllCategories() {
        List<Category> cats = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + Category.TABLE_NAME +";";

        SQLiteDatabase sqldb = db.getReadableDatabase();
        Cursor cursor = sqldb.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Category cat = new Category(
                        cursor.getInt(cursor.getColumnIndex(Category.COLUMN_ID)),
                        cursor.getString(cursor.getColumnIndex(Category.COLUMN_NAME)),
                        cursor.getString(cursor.getColumnIndex(Category.COLUMN_DESCRIPTION)),
                        cursor.getString(cursor.getColumnIndex(Category.COLUMN_COLOUR)));

                cats.add(cat);
            } while (cursor.moveToNext());
        }

        sqldb.close();

        return cats;
    }

}
