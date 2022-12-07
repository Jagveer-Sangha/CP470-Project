package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;

public class ExpenseActivity extends AppCompatActivity {

    //Initialize variables
    private static final String ACTIVITY_NAME = "ExpenseActivity";
    Button expenseConfirm;
    Button expenseCancel;
    Switch expenseSwitch;
    EditText expenseEditText;
    Spinner expenseSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);
        expenseConfirm = findViewById(R.id.expense_button1);
        expenseCancel = findViewById(R.id.expense_button2);
        expenseSwitch = findViewById(R.id.expense_switch);
        expenseEditText = findViewById(R.id.expense_edittext1);
        expenseSpinner = findViewById(R.id.expense_spinner);

        //Expense Confirm button
        expenseConfirm.setOnClickListener(view -> {
            setContentView(R.layout.activity_monthly_budget);
            Log.i(ACTIVITY_NAME, "Expense Confirm Button Clicked");
        });

        //Expense Cancel button
        expenseCancel.setOnClickListener(view -> {
            setContentView(R.layout.activity_monthly_budget);
            Log.i(ACTIVITY_NAME, "Expense Cancel Button Clicked");
        });
    }
}