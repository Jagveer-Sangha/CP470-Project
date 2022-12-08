package com.example.project;

import android.content.ClipData;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabaseLockedException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
/*
*
* GoalDataBaseHelper:
*
* This class is used to setup the database and all it's functions that will be used to
* manipulate the database when cerrtain activities take place within the goals page, such as
* insertion of an item into the list.
*
*
* */
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
    //Creates the Database
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(DATABASE_CREATE);
        Log.i("GoalsDatabaseHelper", "Calling on Create");

    }
    //Updates the database when changes are made to the datebase, such as when the item is deleted from the list
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVer, int newVer) {
        Log.i("GoalsDatabaseHelper", "Calling on Upgrade, oldVersion = "+ oldVer +" newVersion = "+newVer);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_OF_GOALS_ITEMS);
        onCreate(sqLiteDatabase);
    }
    //Gets the id of the selected goal item from the database in order for it to be manipulated.
    public Cursor getItemId(String item){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT "+ KEY_ID + " FROM " + TABLE_OF_GOALS_ITEMS
                + " WHERE " + KEY_MESSAGE + " = '" + item + "';";
        Cursor data = db.rawQuery(query, null);
        return data;
    }




}
