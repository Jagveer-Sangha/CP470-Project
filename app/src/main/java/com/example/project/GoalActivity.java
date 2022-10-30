package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class GoalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goal);


        EditText inputBox = findViewById(R.id.inputBox);
        Button goalEnter = findViewById(R.id.goalsenter);
        ListView goalsList = findViewById(R.id.goalsList);

        ArrayList goalsListStrg = new ArrayList<>();

        goalEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String goalVal = inputBox.getText().toString();
                goalsListStrg.add(goalVal);

            }
        });
    }
}