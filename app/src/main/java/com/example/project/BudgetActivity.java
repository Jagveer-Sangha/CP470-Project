package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

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

        //Holds values of the budget
        float[] budgetParameters = {0,0};

        //Budget Confirm button
        budgetConfirm.setOnClickListener(view -> {
            //No value in edittext field
            if(budgetEditText.getText().toString().equals("")){
                Toast.makeText(getBaseContext(), R.string.toast_empty , Toast.LENGTH_SHORT ).show();
            }
            //Value in edittext field
            else{
                    budgetEditText.setFilters(new InputFilter[] {new MonthlyBudget.DecimalDigitsInputFilter(9,2)});
                    Log.i(ACTIVITY_NAME, "Budget Confirm Button Clicked");
                    Intent resultIntent = new Intent(  );
                    resultIntent.putExtra("Values", "Here is my response");
                    setResult(Activity.RESULT_OK, resultIntent);
                    Toast.makeText(BudgetActivity.this, R.string.toast_confirm, Toast.LENGTH_LONG).show();
                    finish();
            }
        });

        //Budget Cancel button
        budgetCancel.setOnClickListener(view -> {
            Log.i(ACTIVITY_NAME, "Budget Cancel Button Clicked");
            Toast.makeText(BudgetActivity.this, R.string.toast_cancel, Toast.LENGTH_LONG).show();
            finish();
        });


    }
}