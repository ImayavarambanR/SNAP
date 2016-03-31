package com.example.dhilip_25.helloworld;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.NumberPicker;

import java.lang.reflect.Field;

public class TimerPart extends AppCompatActivity {
    NumberPicker num,num1,num2;
    int hr=0,min=0,sec=1;

    int sumTime;


    SharedPreferences TimerKindOff,TimerOnce,onceIntro;
    SharedPreferences.Editor editTimerKindOff,editOnce,editOnceTimer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer_part);

        onceIntro = getSharedPreferences("onceIntro",MODE_PRIVATE);
        num=(NumberPicker) findViewById(R.id.num);
        num.setMinValue(0);
        num.setMaxValue(11);
        num.setBackgroundColor(ContextCompat.getColor(this, R.color.num));
        num.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {

            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                // TODO Auto-generated method stub

                hr = newVal;

            }
        });
        setNumberPickerTextColor(num, Color.WHITE);
        setDividerColor(num, R.color.num2);

        num1=(NumberPicker) findViewById(R.id.num2);
        num1.setMinValue(0);
        num1.setMaxValue(59);
        num1.setBackgroundColor(ContextCompat.getColor(this, R.color.num));
        num1.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {

            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                // TODO Auto-generated method stub
                min = newVal;

            }
        });
        setNumberPickerTextColor(num1, Color.WHITE);
        setDividerColor(num1, R.color.num2);

        num2=(NumberPicker) findViewById(R.id.num3);
        num2.setMinValue(1);
        num2.setMaxValue(59);
        num2.setBackgroundColor(ContextCompat.getColor(this, R.color.num));
        num2.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {

            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                // TODO Auto-generated method stub
                sec = newVal;

            }
        });
        setNumberPickerTextColor(num2, Color.WHITE);
        setDividerColor(num2, R.color.num2);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editOnceTimer = onceIntro.edit();
                editOnceTimer.putBoolean("timerStruck",true);
                editOnceTimer.commit();

                sumTime = (hr*60*60) +(min*60) + (sec);

                TimerKindOff = getSharedPreferences("Timer", MODE_PRIVATE);
                editTimerKindOff = TimerKindOff.edit();
                editTimerKindOff.putInt("TimerValue", sumTime);
                editTimerKindOff.putInt("TimerValueTemp",sumTime);
                editTimerKindOff.commit();
                TimerOnce = getSharedPreferences("TimerOnce", MODE_PRIVATE);

                    editOnce = TimerOnce.edit();
                    editOnce.putBoolean("OnceMain",true);
                    editOnce.commit();
                    Intent i = new Intent(TimerPart.this, End.class);
                    startActivity(i);
                  Intent ii = new Intent(TimerPart.this, MyService.class);
                   startService(ii);
            }
        });
    }
    public static boolean setNumberPickerTextColor(NumberPicker numberPicker, int color)
    {
        final int count = numberPicker.getChildCount();
        for(int i = 0; i < count; i++){
            View child = numberPicker.getChildAt(i);
            if(child instanceof EditText){
                try{
                    Field selectorWheelPaintField = numberPicker.getClass()
                            .getDeclaredField("mSelectorWheelPaint");
                    selectorWheelPaintField.setAccessible(true);
                    ((Paint)selectorWheelPaintField.get(numberPicker)).setColor(color);
                    ((EditText)child).setTextColor(color);
                    numberPicker.invalidate();
                    return true;
                }
                catch(NoSuchFieldException e){
                    e.printStackTrace();
                }
                catch(IllegalAccessException e){
                    e.printStackTrace();
                }
                catch(IllegalArgumentException e){
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    private void setDividerColor(NumberPicker picker, int color) {

        java.lang.reflect.Field[] pickerFields = NumberPicker.class.getDeclaredFields();
        for (java.lang.reflect.Field pf : pickerFields) {
            if (pf.getName().equals("mSelectionDivider")) {
                pf.setAccessible(true);
                try {
                    ColorDrawable colorDrawable = new ColorDrawable(color);
                    pf.set(picker, colorDrawable);
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (Resources.NotFoundException e) {
                    e.printStackTrace();
                }
                catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}