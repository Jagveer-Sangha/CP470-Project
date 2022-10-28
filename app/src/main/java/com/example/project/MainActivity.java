package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
    //Variable for readability purposes
    protected static final String ACTIVITY_NAME = "MainActivity";

    //    private Button one ;
//    private Button two ;
//    private Button three ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i(ACTIVITY_NAME, "In onCreate()");

        // one = findViewByID(R.id.);
        // two = findViewByID(R.id.);
        // three = findViewByID(R.id.);

        //one.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View view) {
//              //In _ InsertActivityWeAreGoingTo
//            Intent intent = new Intent(MainActivity.this, _.class);
//
//            startActivityForResult(intent,10);
////                startActivity(intent);
//        }
//    });
        //repeat for diff buttons and pgs
    }



    @Override
    protected void onResume() {
        super.onResume();
        Log.i(ACTIVITY_NAME, "In onResume()");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(ACTIVITY_NAME, "In onStart()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(ACTIVITY_NAME, "In onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(ACTIVITY_NAME, "In onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(ACTIVITY_NAME, "In onDestroy()");
    }
}