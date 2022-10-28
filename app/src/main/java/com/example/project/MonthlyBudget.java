package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class MonthlyBudget extends AppCompatActivity {

    //Initalize variables
    private PieChart pieChart;
    private float sampleData1, sampleData2, sampleData3, sampleData4, sampleData5, sampleData6, sampleData7;
    private static String FOOD = "Food & Dining", EDUCATE = "Education", ENTERTAIN = "Entertainment", HOUSE = "Housing", MEDS = "Medical", UTIL = "Utilities", AVAILABLE = "Remaining"; //Various Expense (subject to change)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monthly_budget);

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
        PieDataSet dataSet = new PieDataSet(entries, "Monthly Budget");
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