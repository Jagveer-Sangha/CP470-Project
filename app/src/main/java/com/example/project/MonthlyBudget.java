package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
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

    //Initalize variables
    ArrayList <String> BudgetList = new ArrayList<>();
    private static final String ACTIVITY_NAME = "MonthlyBudget";

    MonthlyBudgetDatabaseHelper BudgetDBH;
    SQLiteDatabase BudgetDB;
    int Year = Calendar.getInstance().get(Calendar.YEAR);
    int Month = Calendar.getInstance().get(Calendar.MONTH) + 1;
    String CurrentDate = Integer.toString(Month)+"/"+Integer.toString(Year);


    private PieChart pieChart;
    private float sampleData1, sampleData2, sampleData3, sampleData4, sampleData5, sampleData6, sampleData7;
    private static String FOOD = "Food & Dining", EDUCATE = "Education", ENTERTAIN = "Entertainment", HOUSE = "Housing", MEDS = "Medical", UTIL = "Utilities", AVAILABLE = "Remaining"; //Various Expense (subject to change)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monthly_budget);

        //Database

        BudgetDBH= new MonthlyBudgetDatabaseHelper(this);
        BudgetDB = BudgetDBH.getWritableDatabase();
       /* String goalVal = ;
        BudgetList.add(goalVal);

        ContentValues val = new ContentValues();
        val.put(MonthlyBudgetDatabaseHelper.KEY_CATEGORY, inputBox.getText().toString());
        long insertId = BudgetDB.insert(MonthlyBudgetDatabaseHelper.TABLE_OF_BUDGET_ITEMS, null, val);
        Cursor c2 = BudgetDB.query(MonthlyBudgetDatabaseHelper.TABLE_OF_BUDGET_ITEMS, null, GoalsDatabaseHelper.KEY_ID + "=" + insertId, null, null, null, null);
        c2.moveToFirst();
        c2.close();
*/

        pieChart = findViewById(R.id.pieChart1);
        setupPieChart();
        loadPieChartData();

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
        sampleData7 = 0.08f;

        //Add category entries to pie chart
        ArrayList<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(sampleData1, FOOD));
        entries.add(new PieEntry(sampleData2, EDUCATE));
        entries.add(new PieEntry(sampleData3, ENTERTAIN));
        entries.add(new PieEntry(sampleData4, HOUSE));
        entries.add(new PieEntry(sampleData5, MEDS));
        entries.add(new PieEntry(sampleData6, UTIL));
        entries.add(new PieEntry(sampleData7, AVAILABLE));

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

        //Settings for piechart (format, text colours, etc...)
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