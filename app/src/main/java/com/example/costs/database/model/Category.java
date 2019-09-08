package com.example.costs.database.model;


public class Category {
    public static final String TABLE_NAME = "categories";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_COLOUR = "colour";

    private int id;
    private String name;
    private String description;
    private String colour;

    // Create table SQL query
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_NAME + " TEXT,"
                    + COLUMN_DESCRIPTION + " TEXT,"
                    + COLUMN_COLOUR + " TEXT,"
                    + ")";

    public Category() {
    }

    public Category(int id, String name, String description, String color) {
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

    public String getColour() {
        return colour;
    }

    public void setChange(int id, String name, String description, String colour){
        this.id = id;
        this.name = name;
        this.description = description;
        this.colour = colour;
    }
}
