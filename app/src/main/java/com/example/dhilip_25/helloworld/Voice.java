package com.example.dhilip_25.helloworld;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

public class Voice extends AppCompatActivity {
    TimePicker tp;
    Button bt;
    EditText et;
    SharedPreferences hr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice);
        bt = (Button) findViewById(R.id.bt);
        tp = (TimePicker) findViewById(R.id.timePicker1);
        et=(EditText)findViewById(R.id.et);
        hr=getSharedPreferences("Mypref",Context.MODE_PRIVATE);

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int hour = tp.getCurrentHour();
                int min = tp.getCurrentMinute();
                String repeat=et.getText().toString();
               SharedPreferences.Editor editor=hr.edit();
                editor.putInt("Hour",hour);
                editor.putInt("Min",min);
                editor.putInt("cnt",0);
                editor.putString("Repeat",repeat);
                editor.commit();

                Intent i = new Intent(Voice.this, Example.class);
                startActivity(i);
            }
        });

    }

}
