package com.example.project;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
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
}
