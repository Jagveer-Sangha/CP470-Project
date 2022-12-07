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
import android.widget.TextView;
import android.widget.Toast;

public class BudgetActivity extends AppCompatActivity {

    //Initialize variables
    private static final String ACTIVITY_NAME = "BudgetActivity";
    Button budgetConfirm;
    Button budgetCancel;
    Switch budgetSwitch;
    TextView budgetTextView;
    EditText budgetEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_budget);
        budgetConfirm = findViewById(R.id.budget_button1);
        budgetCancel = findViewById(R.id.budget_button2);
        budgetSwitch = findViewById(R.id.budget_switch);
        budgetEditText = findViewById(R.id.budget_edittext1);
        budgetTextView = findViewById(R.id.budget_textView1);
        //Limit values to 9 digits before decimal and 2 digits after decimal
        budgetEditText.setFilters(new InputFilter[] {new MonthlyBudget.DecimalDigitsInputFilter(9,2)});

        //Display current value
        float[] CurrentValues = (float[]) getIntent().getSerializableExtra("Current");
        String text = getString(R.string.current_budget_text) + " " + Float.toString(CurrentValues[7]);
        budgetTextView.setText(text);

        //Holds values of the budget {Switchstate, value}
        float[] budgetParameters = {0,0};


        //Budget Confirm button
        budgetConfirm.setOnClickListener(view -> {
            //No value in edittext field
            if(budgetEditText.getText().toString().equals("") || Float.parseFloat(budgetEditText.getText().toString()) == 0){
                Toast.makeText(getBaseContext(), R.string.toast_empty , Toast.LENGTH_SHORT ).show();
            }
            //Value in edittext field
            else{
                    //Check which state switch is in
                    Boolean switchState = budgetSwitch.isChecked();
                    //1 for true, meaning ADD
                    if(switchState){
                        budgetParameters[0] = 1;
                    }
                    //0 for false, meaning SUBTRACT
                    else{
                        budgetParameters[0] = 0;
                    }
                if(switchState || (switchState == false && Float.parseFloat(budgetEditText.getText().toString()) < CurrentValues[7])){
                    //Grab budget value
                    budgetParameters[1] = Float.parseFloat(budgetEditText.getText().toString());
                    Log.i(ACTIVITY_NAME, "Budget Confirm Button Clicked");
                    Intent resultIntent = new Intent(  );
                    resultIntent.putExtra("Values", budgetParameters);
                    setResult(2, resultIntent);
                    Toast.makeText(BudgetActivity.this, R.string.toast_confirm, Toast.LENGTH_LONG).show();
                    finish();
                }
                else{
                    Toast.makeText(BudgetActivity.this, R.string.toast_subtract, Toast.LENGTH_LONG).show();
                }
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