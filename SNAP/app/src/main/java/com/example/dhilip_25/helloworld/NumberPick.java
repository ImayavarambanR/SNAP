package com.example.dhilip_25.helloworld;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.Toast;

public class NumberPick extends AppCompatActivity {
    NumberPicker num,num1;
    int hr=0,min=0;
    Button bt;
    int button;
    SharedPreferences pref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number_pick);
        pref=getSharedPreferences("Mypref", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = pref.edit();
        num=(NumberPicker) findViewById(R.id.num);
        bt=(Button)findViewById(R.id.bt);
        num.setMinValue(0);
        num.setMaxValue(23);
        num.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {

            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                // TODO Auto-generated method stub

                hr = newVal;

            }
        });

        num1=(NumberPicker) findViewById(R.id.num2);
        num1.setMinValue(0);
        num1.setMaxValue(59);
        num1.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {

            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                // TODO Auto-generated method stub
                min = newVal;

            }
        });

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Toast.makeText(getApplicationContext(),+hr+":"+min,Toast.LENGTH_LONG).show();
                button=pref.getInt("Button1",0);
                switch(button) {
                    case 1:   editor.putInt("Hour", hr);
                               editor.putInt("Min", min);
                               editor.putInt("cnt", 1);
                               editor.commit();
                               Intent i = new Intent(NumberPick.this, Example.class);
                               startActivity(i);
                               break;

                    case 2:   editor.putInt("Hour2", hr);
                              editor.putInt("Min2", min);
                              editor.putInt("cnt", 1);
                               editor.commit();
                               Intent i2 = new Intent(NumberPick.this, Example.class);
                               startActivity(i2);
                                break;
                    case 3:    editor.putInt("Hour3", hr);
                              editor.putInt("Min3", min);
                              editor.putInt("cnt", 1);
                               editor.commit();
                             Intent i3 = new Intent(NumberPick.this, Example.class);
                              startActivity(i3);
                               break;
                    case 4:   editor.putInt("Hour4", hr);
                        editor.putInt("Min4", min);
                        editor.putInt("cnt", 1);
                        editor.commit();
                        Intent ii = new Intent(NumberPick.this, Example.class);
                        startActivity(ii);
                        break;
                }
            }
        });
    }

}
