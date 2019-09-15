package com.example.costs.database.model;


import java.text.DecimalFormat;

public class Category {
    public static final String TABLE_NAME = "categories";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_COLOUR = "colour";

    private int id;
    private String name;
    private String description;
    private int colour;
    private float price = 0f;

    // Create table SQL query
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_NAME + " TEXT,"
                    + COLUMN_DESCRIPTION + " TEXT,"
                    + COLUMN_COLOUR + " INTEGER"
                    + ")";

    public Category() {
    }

    public Category(int id, String name, String description, int color) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.colour = color;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getColour() {
        return colour;
    }

    public float getPrice() {
        return price;
    }

    public String getPrice(boolean xy) {
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        return decimalFormat.format(price);
    }

    public void setPrice(float price){ this.price = price; }

    public void setChange(int id, String name, String description, int colour){
        this.id = id;
        this.name = name;
        this.description = description;
        this.colour = colour;
    }
}
