package com.example.costs.model;


public class CostDB {
    public static final String TABLE_NAME = "costs";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_PRICE = "price";
    public static final String COLUMN_TIMESTAMP = "timestamp";


    private int id;
    private String name;
    private String timestamp;
    private String description;
    private float price;

    // Create table SQL query
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_NAME + " TEXT,"
                    + COLUMN_DESCRIPTION + " TEXT,"
                    + COLUMN_PRICE + " FLOAT,"
                    + COLUMN_TIMESTAMP + " DATETIME"
                    + ")";

    public CostDB() {
    }

    public CostDB(int id, String name, String description, float price, String timestamp) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.timestamp = timestamp;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public float getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setChange(int id, String name, String description, float price, String timestamp){
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.timestamp = timestamp;
    }
}
