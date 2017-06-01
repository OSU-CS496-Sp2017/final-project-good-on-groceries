package com.group7.goodongroceries.data;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class GroceryListDBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "goodOnGroceries.db";
    private static final int DB_VERSION = 1;

    public GroceryListDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_LIST_TABLE =
                "CREATE TABLE " + GroceryListContract.ListItems.TABLE_NAME + " (" +
                        GroceryListContract.ListItems._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        GroceryListContract.ListItems.COLUMN_ENTRY + " TEXT NOT NULL, " +
                        GroceryListContract.ListItems.COLUMN_CHECKED + " INTEGER DEFAULT NOT NULL, " +
                        GroceryListContract.ListItems.COLUMN_FOOD_ID + " INTEGER " +
                        ");";

        final String SQL_CREATE_FOOD_TABLE =
                "CREATE TABLE " + GroceryListContract.Food.TABLE_NAME + " (" +
                        GroceryListContract.Food._ID + " INTEGER PRIMARY KEY, " +
                        GroceryListContract.Food.COLUMN_DESCRIPTION + " TEXT NOT NULL" +
                        ");";

        final String SQL_CREATE_NUTRIENT_TABLE =
                "CREATE TABLE " + GroceryListContract.Nutrients.TABLE_NAME + " (" +
                        GroceryListContract.Nutrients._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        GroceryListContract.Nutrients.COLUMN_GROUP + " TEXT NOT NULL, " +
                        GroceryListContract.Nutrients.COLUMN_UNIT + " TEXT NOT NULL, " +
                        GroceryListContract.Nutrients.COLUMN_VALUE + " TEXT NOT NULL, " +
                        GroceryListContract.Nutrients.COLUMN_MEASUREMENT + " TEXT NOT NULL, " +
                        GroceryListContract.Nutrients.COLUMN_FOOD_ID + " INTEGER REFERENCES " +
                            GroceryListContract.Food.TABLE_NAME +
                            " (" + GroceryListContract.Food._ID + ") " +
                            " ON DELETE CASCADE" +
                        ");";
        
        db.execSQL(SQL_CREATE_LIST_TABLE);
        db.execSQL(SQL_CREATE_FOOD_TABLE);
        db.execSQL(SQL_CREATE_NUTRIENT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Just rebuild the DB
        db.execSQL("DROP TABLE IF EXISTS " + GroceryListContract.ListItems.TABLE_NAME + ";");
        db.execSQL("DROP TABLE IF EXISTS " + GroceryListContract.Food.TABLE_NAME + ";");
        db.execSQL("DROP TABLE IF EXISTS " + GroceryListContract.Nutrients.TABLE_NAME + ";");
        onCreate(db);
    }
}
