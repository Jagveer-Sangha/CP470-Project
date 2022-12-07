package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Switch;
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
    private static final String ACTIVITY_NAME = "MonthlyBudget";
    Button budgetButton;
    Button expenseButton;
    ProgressBar progBar;

    //Database/Date Variables
    MonthlyBudgetDatabaseHelper BudgetDBH;
    SQLiteDatabase BudgetDB;
    //int Year = Calendar.getInstance().get(Calendar.YEAR);
    //int Month = Calendar.getInstance().get(Calendar.MONTH) + 1;//Month count starts at 0
    //String CurrentDate = Integer.toString(Month)+"/"+Integer.toString(Year);

    //Pie Chart Variables
    private PieChart pieChart;
    public static String FOOD = "Food", EDUCATE = "Education", ENTERTAIN = "Entertainment", HOUSE = "Housing", MEDS = "Medical", UTIL = "Utilities", ETC = "Other", AVAILABLE = "Remaining"; //Various Expense (subject to change)
    public static String[] CATEGORIES = {FOOD, EDUCATE, ENTERTAIN, HOUSE, MEDS, UTIL, ETC, AVAILABLE};//Available holds budget
    private float[] UpdatedPercents = {0,0,0,0,0,0,0,0};
    private float[] CurrentValues = {0,0,0,0,0,0,0,0};
    private float OverUnderBudget = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monthly_budget);

        //Find buttons
        budgetButton = findViewById(R.id.button1);
        expenseButton = findViewById(R.id.button2);


        progBar = findViewById(R.id.progressBar);

        //Update Progress Bar
        progBar.setVisibility(View.VISIBLE);
        progBar.setProgress(0);

        //Make Database
        BudgetDBH= new MonthlyBudgetDatabaseHelper(this);
        BudgetDB = BudgetDBH.getWritableDatabase();

        progBar.setProgress(25);

        //Get Database items from current month's database
        Cursor c = BudgetDB.rawQuery("select * from "+ MonthlyBudgetDatabaseHelper.TABLE_OF_BUDGET_ITEMS + ";", null);
        c.moveToFirst();

        //Get all current expense and budget values
        int count = 0;
        while(!c.isAfterLast()){
            CurrentValues[count] = c.getFloat((c.getColumnIndexOrThrow(MonthlyBudgetDatabaseHelper.KEY_VALUE)));
            Log.i(ACTIVITY_NAME, "SQL Category & Value: " + c.getString(c.getColumnIndexOrThrow(MonthlyBudgetDatabaseHelper.KEY_CATEGORY)) + ", " + c.getFloat((c.getColumnIndexOrThrow(MonthlyBudgetDatabaseHelper.KEY_VALUE))));
            c.moveToNext();
            count += 1;
        }
        c.close();
        progBar.setProgress(50);

        //Calculate Percents
        calculateBudget();

        progBar.setProgress(75);

        //Set up Piechart
        pieChart = findViewById(R.id.pieChart1);
        setupPieChart();

        //Load Piechart
        progBar.setProgress(100);
        progBar.setVisibility(View.INVISIBLE);
        loadPieChartData();

        /*
         String goalVal = ;
        BudgetList.add(goalVal);

        ContentValues val = new ContentValues();
        val.put(MonthlyBudgetDatabaseHelper.KEY_CATEGORY, inputBox.getText().toString());
        long insertId = BudgetDB.insert(MonthlyBudgetDatabaseHelper.TABLE_OF_BUDGET_ITEMS, null, val);
        Cursor c2 = BudgetDB.query(MonthlyBudgetDatabaseHelper.TABLE_OF_BUDGET_ITEMS, null, GoalsDatabaseHelper.KEY_ID + "=" + insertId, null, null, null, null);
        c2.moveToFirst();
        c2.close();
*/


        //If click budget button bring up budget
        budgetButton.setOnClickListener(view -> {
            setContentView(R.layout.activity_add_budget);
            Log.i(ACTIVITY_NAME, "Budget Button Clicked");
        });

        //If click expense button bring up expense
        expenseButton.setOnClickListener(view -> {
            setContentView(R.layout.activity_add_expense);
            Log.i(ACTIVITY_NAME, "Expense Button Clicked");
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
    private void calculateBudget(){
        float TotalExpenses = 0;
        for (int i = 0; i < 7; i++){
            TotalExpenses += CurrentValues[i];
        }
        OverUnderBudget = CurrentValues[7] - TotalExpenses;

        //Calculate New Percents when under budget
        if (OverUnderBudget > 0){
            UpdatedPercents[7] = (CurrentValues[7] - TotalExpenses)/CurrentValues[7];
            for (int i = 0; i < 7; i++){
                UpdatedPercents[i] = CurrentValues[i] / CurrentValues[7];
            }
        }

        //Calculate New Percents when over budget
        else{
            for (int i = 0; i < 8; i++){
                UpdatedPercents[i] = CurrentValues[i]/TotalExpenses;
            }
        }

    }

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
        //Add category entries to pie chart
        ArrayList<PieEntry> entries = new ArrayList<>();
        int count = 0;
        boolean emptyChart = true;
        for (float var: UpdatedPercents){
            if(var > 0){
                emptyChart = false;
                entries.add(new PieEntry(var, CATEGORIES[count]));
            }
            count += 1;
        }

        //If all values are zero (i.e. empty chart)
        if(emptyChart == true){
            entries.add(new PieEntry(100, "Empty"));
        }

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