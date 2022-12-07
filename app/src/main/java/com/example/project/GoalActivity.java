package com.example.project;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
    private static final String ACTIVITY_NAME = "GoalsWindow";

    GoalsDatabaseHelper goalsDB;
    SQLiteDatabase database;
    int itemId;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goal);




        EditText inputBox = findViewById(R.id.inputBox);
        Button goalEnter = findViewById(R.id.goalsenter);
        ListView goalsList = findViewById(R.id.goalsList);
        Button goalDelete = findViewById(R.id.deleteGoal);
        goalsDB = new GoalsDatabaseHelper(this);
        database = goalsDB.getWritableDatabase();


        GoalAdapter goalAdapter = new GoalAdapter(this);
        goalsList.setAdapter(goalAdapter);

        goalAdapter.notifyDataSetChanged();

        Cursor c = database.rawQuery("select * from "+ GoalsDatabaseHelper.TABLE_OF_GOALS_ITEMS + ";", null);
        c.moveToFirst();

        while(!c.isAfterLast()){
            String str = c.getString((c.getColumnIndexOrThrow(GoalsDatabaseHelper.KEY_MESSAGE)));
            goalsListStrg.add(str);
            Log.i(ACTIVITY_NAME, "SQL MESSAGE: " + c.getString(c.getColumnIndexOrThrow(GoalsDatabaseHelper.KEY_MESSAGE)));
            Log.i(ACTIVITY_NAME, "Cursor Column Count =" + c.getColumnCount());
            c.moveToNext();
        }

        for(int i = 0; i < c.getColumnCount(); i++){
            c.getColumnName(i);
            System.out.println(c.getColumnName(i));
        }

        goalEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String goalVal = inputBox.getText().toString();
                goalsListStrg.add(goalVal);

                ContentValues val = new ContentValues();
                val.put(GoalsDatabaseHelper.KEY_MESSAGE, inputBox.getText().toString());
                long insertId = database.insert(GoalsDatabaseHelper.TABLE_OF_GOALS_ITEMS, null, val);
                Cursor c2 = database.query(GoalsDatabaseHelper.TABLE_OF_GOALS_ITEMS, null, GoalsDatabaseHelper.KEY_ID + "=" + insertId, null, null, null, null);
                c2.moveToFirst();
                c2.close();

                goalAdapter.notifyDataSetChanged();
                inputBox.setText("");

            }


        });
//        goalsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long id) {
//
//                String val = goalsList.getItemAtPosition(pos).toString();
////                goalsListStrg.remove(val);
//
//                Cursor data = goalsDB.getItemId(val);
//                int itemId = -1;
//                while(data.moveToNext()) {
//                    itemId = data.getInt(0);
//
//                }
//                if(itemId > -1){
//                    goalsListStrg.remove(val);
////                    Log.d(ACTIVITY_NAME, "onItemClick: The ID is " + itemId);
////                    Intent editScreenIntent = new Intent(GoalActivity.this, EditGoalsActivity.class);
////                    editScreenIntent.putExtra("id", itemId);
////                    editScreenIntent.putExtra("item", val);
////                    editScreenIntent.putExtra("itemList", goalsListStrg);
////
////                    startActivity(editScreenIntent);
//                    database.delete(GoalsDatabaseHelper.TABLE_OF_GOALS_ITEMS, GoalsDatabaseHelper.KEY_ID + "=?", new String[]{""+itemId});
//                    goalAdapter.notifyDataSetChanged();
//
//                }
//
//
//
//
//            }
//        });

        goalsList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int pos, long id) {
                String val = goalsList.getItemAtPosition(pos).toString();
//                goalsListStrg.remove(val);

                Cursor data = goalsDB.getItemId(val);
                itemId = -1;
                while(data.moveToNext()) {
                    itemId = data.getInt(0);

                }
                if(itemId > -1) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(GoalActivity.this);
                    builder.setMessage("Do You Wan't to Delete this goal?")
                    .setTitle("Delete Goal")
                    .setCancelable(false)
                    .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            goalsListStrg.remove(val);
    //                    Log.d(ACTIVITY_NAME, "onItemClick: The ID is " + itemId);
    //                    Intent editScreenIntent = new Intent(GoalActivity.this, EditGoalsActivity.class);
    //                    editScreenIntent.putExtra("id", itemId);
    //                    editScreenIntent.putExtra("item", val);
    //                    editScreenIntent.putExtra("itemList", goalsListStrg);
    //
    //                    startActivity(editScreenIntent);
                            database.delete(GoalsDatabaseHelper.TABLE_OF_GOALS_ITEMS, GoalsDatabaseHelper.KEY_ID + "=?", new String[]{"" + itemId});
                            goalAdapter.notifyDataSetChanged();
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    }).create().show();

                }


                    return false;
            }
        });

    }

    @Override
    protected void onDestroy() {
        goalsDB.close();
        super.onDestroy();
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

        @Override
        public long getItemId(int position) {
            return (position);
        }

        @Override
        public void remove(@Nullable String object) {
            goalsListStrg.remove(object);
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