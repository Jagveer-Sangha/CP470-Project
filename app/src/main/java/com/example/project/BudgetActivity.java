package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;

public class BudgetActivity extends AppCompatActivity {

    //Initialize variables
    private static final String ACTIVITY_NAME = "BudgetActivity";
    Button budgetConfirm;
    Button budgetCancel;
    Switch budgetSwitch;
    EditText budgetEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_budget);
        budgetConfirm = findViewById(R.id.budget_button1);
        budgetCancel = findViewById(R.id.budget_button2);
        budgetSwitch = findViewById(R.id.budget_switch);
        budgetEditText = findViewById(R.id.budget_edittext1);


        //Budget Confirm button
        budgetConfirm.setOnClickListener(view -> {
            setContentView(R.layout.activity_monthly_budget);
            Log.i(ACTIVITY_NAME, "Budget Confirm Button Clicked");
        });

        //Budget Cancel button
        budgetCancel.setOnClickListener(view -> {
            setContentView(R.layout.activity_monthly_budget);
            Log.i(ACTIVITY_NAME, "Budget Cancel Button Clicked");
        });


    }
}