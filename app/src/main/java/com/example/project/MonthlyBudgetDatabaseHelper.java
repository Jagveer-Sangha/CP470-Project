package com.example.project;

import static com.example.project.MonthlyBudget.CATEGORIES;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import java.util.Calendar;
import java.util.Date;

public class MonthlyBudgetDatabaseHelper extends SQLiteOpenHelper{
    private static final String DATABASE_NAME = "BudgetDatabase.db";
    private static final int VERSION_NUM = 1;
    private static final int Year = Calendar.getInstance().get(Calendar.YEAR);
    private static final int Month = Calendar.getInstance().get(Calendar.MONTH) + 1;

    public static final String TABLE_OF_BUDGET_ITEMS =  "M"+Integer.toString(Month)+"Y"+Integer.toString(Year);//Set to month and year
    public static final String KEY_ID = "id";
    public static final String KEY_CATEGORY = "CategoryName";
    public static final String KEY_VALUE = "TotalValue";
    public static final String KEY_PERCENT = "Percentage";
    public static final String KEY_TARGET = "TargetValue";
    public static final String[] COLUMNS = {KEY_ID, KEY_CATEGORY, KEY_VALUE, KEY_PERCENT, KEY_TARGET};
    private static final String DATABASE_CREATE = "create table " + TABLE_OF_BUDGET_ITEMS + "(" + KEY_ID + " integer primary key autoincrement, " + KEY_CATEGORY + " text not null, " + KEY_VALUE + " float not null, " + KEY_PERCENT + " float not null, " + KEY_TARGET + " float not null);";
    private static final String CATEGORIES_CREATE = "insert into " + TABLE_OF_BUDGET_ITEMS + "(" + KEY_CATEGORY + "," + KEY_VALUE + "," + KEY_PERCENT + "," + KEY_TARGET + ") VALUES (";

    public MonthlyBudgetDatabaseHelper(Context ctx){
        super(ctx, DATABASE_NAME, null, VERSION_NUM);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(DATABASE_CREATE);
        //Add categories
        /*for (int i = 0; i < 8; i++){
         sqLiteDatabase.execSQL(CATEGORIES_CREATE + "'" + CATEGORIES[i] + "'" + ", 0, 0, 0);");
        }*/
        Log.i("MonthlyBudgetDatabaseHelper", "Calling on Create");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVer, int newVer) {
        Log.i("MonthlyBudgetDatabaseHelper", "Calling on Upgrade, oldVersion = "+ oldVer +" newVersion = "+newVer);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_OF_BUDGET_ITEMS);
        onCreate(sqLiteDatabase);
    }
}
