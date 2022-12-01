package com.example.project;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class GoalActivity extends AppCompatActivity {

    ArrayList <String> goalsListStrg = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goal);


        EditText inputBox = findViewById(R.id.inputBox);
        Button goalEnter = findViewById(R.id.goalsenter);
        ListView goalsList = findViewById(R.id.goalsList);

        GoalAdapter goalAdapter = new GoalAdapter(this);
        goalsList.setAdapter(goalAdapter);

        goalEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String goalVal = inputBox.getText().toString();
                goalsListStrg.add(goalVal);

                goalAdapter.notifyDataSetChanged();
                inputBox.setText("");

            }
        });
    }

    private class GoalAdapter extends ArrayAdapter<String>{
        public GoalAdapter(Context ctx){
            super(ctx, 0);
        }
        public int getCount(){
            return goalsListStrg.size();
        }

        @Nullable
        @Override
        public String getItem(int position) {
            return (goalsListStrg.get(position));
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater inflater = GoalActivity.this.getLayoutInflater();

            View result = null;
            result = inflater.inflate(R.layout.activity_goals_list_items, null);

            TextView goalStr = (TextView) result.findViewById(R.id.goalText);
            goalStr.setText(getItem(position));
            return result;
        }
    }
}