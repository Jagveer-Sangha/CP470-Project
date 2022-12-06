package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.Calendar;

import java.util.ArrayList;

public class MonthlyBudget extends AppCompatActivity {

    //Initialize variables
    ArrayList <String> BudgetList = new ArrayList<>();
    private static final String ACTIVITY_NAME = "MonthlyBudget";
    Button budgetButton;
    Button expenseButton;

    //Database/Date Variables
    MonthlyBudgetDatabaseHelper BudgetDBH;
    SQLiteDatabase BudgetDB;
    int Year = Calendar.getInstance().get(Calendar.YEAR);
    int Month = Calendar.getInstance().get(Calendar.MONTH) + 1;//Month count starts at 0
    String CurrentDate = Integer.toString(Month)+"/"+Integer.toString(Year);

    //Pie Chart Variables
    private PieChart pieChart;
    private float sampleData1, sampleData2, sampleData3, sampleData4, sampleData5, sampleData6, sampleData7, sampleData8;
    public static String FOOD = "Food", EDUCATE = "Education", ENTERTAIN = "Entertainment", HOUSE = "Housing", MEDS = "Medical", UTIL = "Utilities", ETC = "Other", AVAILABLE = "Remaining"; //Various Expense (subject to change)
    public static String[] CATEGORIES = {FOOD, EDUCATE, ENTERTAIN, HOUSE, MEDS, UTIL, ETC, AVAILABLE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monthly_budget);

        //Find buttons
        budgetButton = findViewById(R.id.button1);
        expenseButton = findViewById(R.id.button2);

        //Make Database
        BudgetDBH= new MonthlyBudgetDatabaseHelper(this);
        BudgetDB = BudgetDBH.getWritableDatabase();

        //Get Database items from current month's database
        Cursor c = BudgetDB.rawQuery("select * from "+ MonthlyBudgetDatabaseHelper.TABLE_OF_BUDGET_ITEMS + ";", null);
        c.moveToFirst();

        //
        /*while(!c.isAfterLast()){
            String str = c.getString((c.getColumnIndexOrThrow(MonthlyBudgetDatabaseHelper.KEY_CATEGORY)));
            //BudgetList.add(str);
            Log.i(ACTIVITY_NAME, "SQL MESSAGE: " + c.getString(c.getColumnIndexOrThrow(MonthlyBudgetDatabaseHelper.KEY_CATEGORY)));
            Log.i(ACTIVITY_NAME, "Cursor Column Count =" + c.getColumnCount());
            c.moveToNext();
        }


         String goalVal = ;
        BudgetList.add(goalVal);

        ContentValues val = new ContentValues();
        val.put(MonthlyBudgetDatabaseHelper.KEY_CATEGORY, inputBox.getText().toString());
        long insertId = BudgetDB.insert(MonthlyBudgetDatabaseHelper.TABLE_OF_BUDGET_ITEMS, null, val);
        Cursor c2 = BudgetDB.query(MonthlyBudgetDatabaseHelper.TABLE_OF_BUDGET_ITEMS, null, GoalsDatabaseHelper.KEY_ID + "=" + insertId, null, null, null, null);
        c2.moveToFirst();
        c2.close();
*/


        //Set up Piechart
        pieChart = findViewById(R.id.pieChart1);
        setupPieChart();
        loadPieChartData();

        //If click budget button bring up budget fragment
        budgetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(ACTIVITY_NAME, "Budget Button Clicked");

            }
        });

        //If click expense button bring up expense fragment
        expenseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(ACTIVITY_NAME, "Expense Button Clicked");

            }
        });

    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        MonthlyBudgetDatabaseHelper databaseHelper = new MonthlyBudgetDatabaseHelper(MonthlyBudget.this);
        SQLiteDatabase database = databaseHelper.getWritableDatabase();
        databaseHelper.onUpgrade(database,1, 2);
        database.close();
        Log.i(ACTIVITY_NAME, "In onDestroy()");
    }

    //Calculate budget data distribution

    //Set up pie chart
    private void setupPieChart(){
        pieChart.setDrawHoleEnabled(true);
        pieChart.setUsePercentValues(true);
        pieChart.setEntryLabelTextSize(12f);
        pieChart.setEntryLabelColor(Color.BLACK);
        pieChart.setCenterText("Spending by Category");
        pieChart.setCenterTextSize(22f);
        pieChart.getDescription().setEnabled(false);

        Legend lege = pieChart.getLegend();
        lege.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        lege.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        lege.setOrientation(Legend.LegendOrientation.VERTICAL);
        lege.setDrawInside(false);
        lege.setEnabled(true);
    }

    //Load budget data into pie chart to display
    private void loadPieChartData(){
        //Set random data values
        sampleData1 = 0.2f;
        sampleData2 = 0.1f;
        sampleData3 = 0.05f;
        sampleData4 = 0.15f;
        sampleData5 = 0.35f;
        sampleData6 = 0.07f;
        sampleData7 = 0.00f;
        sampleData8 = 0.08f;

        //Add category entries to pie chart
        ArrayList<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(sampleData1, CATEGORIES[0]));
        entries.add(new PieEntry(sampleData2, CATEGORIES[1]));
        entries.add(new PieEntry(sampleData3, CATEGORIES[2]));
        entries.add(new PieEntry(sampleData4, CATEGORIES[3]));
        entries.add(new PieEntry(sampleData5, CATEGORIES[4]));
        entries.add(new PieEntry(sampleData6, CATEGORIES[5]));
        entries.add(new PieEntry(sampleData7, CATEGORIES[6]));
        entries.add(new PieEntry(sampleData8, CATEGORIES[7]));

        //Assign colours to array
        ArrayList<Integer> colours = new ArrayList<>();
        for (int colour: ColorTemplate.MATERIAL_COLORS){
            colours.add(colour);
        }

        for (int colour: ColorTemplate.VORDIPLOM_COLORS){
            colours.add(colour);
        }

        //Assign colours to entries
        PieDataSet dataSet = new PieDataSet(entries, "");
        dataSet.setColors(colours);

        //Settings for pie chart (format, text colours, etc...)
        PieData data = new PieData(dataSet);
        data.setDrawValues(true);
        data.setValueFormatter(new PercentFormatter(pieChart));
        data.setValueTextSize(12f);
        data.setValueTextColor(Color.BLACK);

        pieChart.setData(data);
        pieChart.invalidate();

       pieChart.animateY(2000, Easing.EaseInOutQuad);
    }



}