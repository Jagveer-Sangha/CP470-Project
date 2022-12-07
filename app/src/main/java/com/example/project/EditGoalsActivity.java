package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class EditGoalsActivity extends AppCompatActivity {
    EditText editItemText;
    Button delete, edit;

    private String selectedName;
    private int selectedID;
    private ArrayList<String>itemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_goals);

        editItemText = findViewById(R.id.editItemText);
        delete = findViewById(R.id.deleteGoal);
        edit = findViewById(R.id.editGoal);

        GoalsDatabaseHelper goalsDB = new GoalsDatabaseHelper(this);

        Intent receivedIntent = getIntent();

        selectedName = receivedIntent.getStringExtra("item");
        selectedID = receivedIntent.getIntExtra("id", -1);
        itemList = receivedIntent.getStringArrayListExtra("itemList");

        editItemText.setText(selectedName);

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newItem = editItemText.getText().toString();

                if(!newItem.equals("")){
                    goalsDB.updateItem(newItem, selectedID, selectedName);
                }
                else{
                    toastMessage("Please Enter an Entry");
                }
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goalsDB.deleteItem(selectedID, selectedName);
                editItemText.setText("");
                itemList.remove(selectedName);
                toastMessage("Item Removed");
                Intent returnToEntryPage = new Intent(EditGoalsActivity.this, GoalActivity.class);
                startActivity(returnToEntryPage);
            }
        });
    }

    public void toastMessage(String Message){
        Toast.makeText(this, Message, Toast.LENGTH_SHORT).show();

    }}