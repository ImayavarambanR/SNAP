package com.example.dhilip_25.helloworld;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class Edittime extends AppCompatActivity {
 Button b1,b2,b3,b4,b5,b6,b7,b8;
    SharedPreferences timer;
    int hour,min;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edittime);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

       b1=(Button)findViewById(R.id.button2);
        b2=(Button)findViewById(R.id.button5);
        b3=(Button)findViewById(R.id.button3);
        b4=(Button)findViewById(R.id.button7);
        b5=(Button)findViewById(R.id.button4);
        b6=(Button)findViewById(R.id.button6);
        b7=(Button)findViewById(R.id.button8);
        b8=(Button)findViewById(R.id.bt3);

        timer = getSharedPreferences("Time", MODE_PRIVATE);
        hour =timer.getInt("TimerValue", 0);
        min = timer.getInt("uponBreak",0);

        b1.setText(hour);
        b2.setText(hour);
        b3.setText(hour);
        b4.setText(hour);
        b5.setText(hour);
        b6.setText(hour);
        b7.setText(hour);
        b8.setText(hour);

        /*b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
            }
        });*/
    }

}
