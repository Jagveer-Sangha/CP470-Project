package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Map;

public class SpendingCategory extends AppCompatActivity {

    //idk where to store or something, double because money and works well with percentages, can format into money too

    DecimalFormat df = new DecimalFormat("##.##%");
    //SharedPreferences sp;
    Toast toast;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spending_category);
        String text = "Showing percentages...";
        button = findViewById(R.id.toastButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toast = Toast.makeText(getApplicationContext(),text, Toast.LENGTH_SHORT );
                toast.show();
            }
        });
    }

    //gets total per category in sp, adds percentage to list
    private ArrayList<Double> totalAsPercentage(double total) {
        //need name of sp, private because only reading
        SharedPreferences sp = getSharedPreferences("shared-pref", Context.MODE_PRIVATE);
        //double [] percentages = new double[sp.getAll().size()];
        Map<String, ?> allTotals = sp.getAll();
        ArrayList<Double> totals = new ArrayList<>();

        for(Map.Entry<String,?> entry: allTotals.entrySet()){
            Log.d("values", entry.getKey()+ ": "+entry.getValue().toString());
            String str = entry.getValue().toString();
            try {
                double l = Double.parseDouble(str);
                totals.add(Double.valueOf(df.format(l/total)));
            }
            catch(Exception e){
                e.printStackTrace();
            }
            }
        return totals;
        }

}
