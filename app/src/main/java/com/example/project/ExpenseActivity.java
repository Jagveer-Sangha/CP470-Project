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

        //Holds values of the expense
        float[] expenseParameters = {0,0,0};

        //Expense Confirm button
        expenseConfirm.setOnClickListener(view -> {
            //No value in edittext field
            if(expenseEditText.getText().toString().equals("")){
                Toast.makeText(getBaseContext(), R.string.toast_empty , Toast.LENGTH_SHORT ).show();
            }
            //Value in edittext field
            else{
                    expenseEditText.setFilters(new InputFilter[] {new MonthlyBudget.DecimalDigitsInputFilter(9,2)});
                    Log.i(ACTIVITY_NAME, "Expense Confirm Button Clicked");
                    Intent resultIntent = new Intent(  );
                    resultIntent.putExtra("Values", "Here is my response");
                    setResult(Activity.RESULT_OK, resultIntent);
                    Toast.makeText(getBaseContext(), R.string.toast_confirm , Toast.LENGTH_SHORT ).show();
                    finish();
            }
        });

        //Expense Cancel button
        expenseCancel.setOnClickListener(view -> {
            Log.i(ACTIVITY_NAME, "Expense Cancel Button Clicked");
            Toast.makeText(getBaseContext(), R.string.toast_cancel, Toast.LENGTH_SHORT ).show();
            finish();
        });
    }
}