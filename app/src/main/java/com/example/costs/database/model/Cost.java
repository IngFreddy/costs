package com.example.costs.database.model;


public class Cost {
    public static final String TABLE_NAME = "costs";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_PRICE = "price";
    public static final String COLUMN_TIMESTAMP = "timestamp";
    public static final String COLUMN_CATEGORYID = "category_id";


    private int id;
    private String name;
    private String timestamp;
    private String description;
    private float price;
    private int categoryID;

    private int color = 0;

    // Create table SQL query
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_NAME + " TEXT,"
                    + COLUMN_DESCRIPTION + " TEXT,"
                    + COLUMN_PRICE + " FLOAT NOT NULL,"
                    + COLUMN_TIMESTAMP + " DATETIME,"
                    + COLUMN_CATEGORYID + " INTEGER"
                    + ")";

    public Cost() {
    }

    public Cost(int id, String name, String description, float price, String timestamp, int categoryID) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.timestamp = timestamp;
        this.categoryID = categoryID;
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

    public int getCategoryID() {
        return categoryID;
    }

    public int getColor() { return  color; }
    public void setColor(int color) {this.color = color;}

    public void setChange(int id, String name, String description, float price, String timestamp, int categoryID){
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.timestamp = timestamp;
        this.categoryID = categoryID;
    }
}
