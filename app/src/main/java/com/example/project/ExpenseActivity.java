package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class ExpenseActivity extends AppCompatActivity {

    //Initialize variables
    private static final String ACTIVITY_NAME = "ExpenseActivity";
    Button expenseConfirm;
    Button expenseCancel;
    Switch expenseSwitch;
    TextView expenseTextView;
    EditText expenseEditText;
    Spinner expenseSpinner;
    String currentCategory;

    String text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);
        expenseConfirm = findViewById(R.id.expense_button1);
        expenseCancel = findViewById(R.id.expense_button2);
        expenseSwitch = findViewById(R.id.expense_switch);
        expenseEditText = findViewById(R.id.expense_edittext1);
        expenseSpinner = findViewById(R.id.expense_spinner);
        expenseTextView = findViewById(R.id.expense_textView2);
        //Limit values to 9 digits before decimal and 2 digits after decimal
        expenseEditText.setFilters(new InputFilter[] {new MonthlyBudget.DecimalDigitsInputFilter(9,2)});


        //Display current value
        float[] CurrentValues = (float[]) getIntent().getSerializableExtra("Current");
        text = expenseTextView.getText().toString() + " " + Float.toString(CurrentValues[0]);
        expenseTextView.setText(text);

        //Holds values of the expense {Switchstate, value, category}
        float[] expenseParameters = {0,0,0};

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.categories, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        expenseSpinner.setAdapter(adapter);
        currentCategory = expenseSpinner.getSelectedItem().toString();

        expenseSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(!currentCategory.equals(expenseSpinner.getSelectedItem().toString())){
                    //Update current value
                    text = getString(R.string.current_expense_text) + " " + Float.toString(CurrentValues[expenseSpinner.getSelectedItemPosition()]);
                    expenseTextView.setText(text);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //Expense Confirm button
        expenseConfirm.setOnClickListener(view -> {
            //No value in edittext field
            if(expenseEditText.getText().toString().equals("") ||  Float.parseFloat(expenseEditText.getText().toString()) == 0){
                Toast.makeText(getBaseContext(), R.string.toast_empty , Toast.LENGTH_SHORT ).show();
            }
            //Value in edittext field
            else{
                //Check which state switch is in
                Boolean switchState = expenseSwitch.isChecked();
                //1 for true, meaning ADD
                if(switchState){
                    expenseParameters[0] = 1;
                }
                //0 for false, meaning SUBTRACT
                else{
                    expenseParameters[0] = 0;
                }
                if(switchState || (switchState == false && Float.parseFloat(expenseEditText.getText().toString()) < CurrentValues[expenseSpinner.getSelectedItemPosition()])){
                    //Grab expense value
                    expenseParameters[1] = Float.parseFloat(expenseEditText.getText().toString());

                    //Check which category is selected
                    expenseParameters[2] = expenseSpinner.getSelectedItemPosition();
                        Log.i(ACTIVITY_NAME, "Expense Confirm Button Clicked");
                        Intent resultIntent = new Intent(  );
                        resultIntent.putExtra("Values", expenseParameters);
                        setResult(3, resultIntent);
                        Toast.makeText(getBaseContext(), R.string.toast_confirm , Toast.LENGTH_SHORT ).show();
                        finish();
                }
                else{
                    Toast.makeText(getBaseContext(), R.string.toast_subtract, Toast.LENGTH_SHORT ).show();
                }
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