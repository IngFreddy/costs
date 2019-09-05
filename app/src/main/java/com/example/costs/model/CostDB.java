package com.example.costs.model;


public class CostDB {
    public static final String TABLE_NAME = "costs";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_PRICE = "price";
    public static final String COLUMN_TIMESTAMP = "timestamp";


    private int id;
    private String name;
    private String timestamp;
    private float price;

    // Create table SQL query
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_NAME + " TEXT,"
                    + COLUMN_PRICE + " FLOAT,"
                    + COLUMN_TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP"
                    + ")";

    public CostDB() {
    }

    public CostDB(int id, String name, float price, String timestamp) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.timestamp = timestamp;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public float getprice() {
        return price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setprice(float price) {
        this.price = price;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
