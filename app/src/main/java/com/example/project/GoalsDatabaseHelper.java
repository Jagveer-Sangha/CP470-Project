package com.example.project;

import android.content.ClipData;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabaseLockedException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class GoalsDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "goalsDatabase.db";
    private static final int VERSION_NUM = 1;
    public static final String TABLE_OF_GOALS_ITEMS = "goalsTable";
    public static final String KEY_ID = "id";
    public static final String KEY_MESSAGE = "itemName";
    private static final String DATABASE_CREATE = "create table "
            +TABLE_OF_GOALS_ITEMS+"("+KEY_ID
            +" integer primary key autoincrement, " + KEY_MESSAGE
            +" text not null);";
    String ACTIVITY_NAME = "DatabaseHelper";

    public GoalsDatabaseHelper(Context ctx){
        super(ctx, DATABASE_NAME, null, VERSION_NUM);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(DATABASE_CREATE);
        Log.i("GoalsDatabaseHelper", "Calling on Create");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVer, int newVer) {
        Log.i("GoalsDatabaseHelper", "Calling on Upgrade, oldVersion = "+ oldVer +" newVersion = "+newVer);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_OF_GOALS_ITEMS);
        onCreate(sqLiteDatabase);
    }
    public void remove(String entry){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE from " + TABLE_OF_GOALS_ITEMS + "WHERE " + KEY_MESSAGE + " = '" + entry +"';");

    }
    public Cursor getItemId(String item){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT "+ KEY_ID + " FROM " + TABLE_OF_GOALS_ITEMS
                + " WHERE " + KEY_MESSAGE + " = '" + item + "';";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public void updateItem(String newItem, int id, String oldItem){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE" + TABLE_OF_GOALS_ITEMS + " SET " + KEY_MESSAGE
                + " = '" + newItem + "' WHERE " + KEY_ID + " = '" + id + "'"
                + " AND " + KEY_MESSAGE + " = '" + oldItem + "';";
        Log.d(ACTIVITY_NAME, "UpdateName: query: " + query);
        Log.d(ACTIVITY_NAME, "updateItem:  Setting Item to" + newItem);
        db.execSQL(query);
    }
    public void deleteItem(int id, String item){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM "+TABLE_OF_GOALS_ITEMS + " WHERE "
                + KEY_ID + " = '" + id + "'" + "AND " + KEY_MESSAGE + " = '" + item + "';";
        Log.d(ACTIVITY_NAME, "UpdateName: query: " + query);
        Log.d(ACTIVITY_NAME, "updateItem:  Deleting Item" + item);
    }


}
