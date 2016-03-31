package com.example.dhilip_25.helloworld;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Mahesh on 13-Dec-15.
 */
public class TimerService extends Service {
    int time,i;
    int count;
    Timer t;
    SharedPreferences timer;
    SharedPreferences.Editor edit;
    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }
    public void onCreate()
    {

        super.onCreate();

    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
         Toast.makeText(getApplicationContext(),"Service Started",Toast.LENGTH_LONG).show();
        timer = getSharedPreferences("Timer", MODE_PRIVATE);
        count=timer.getInt("TimerValue", 0);
        time = timer.getInt("uponBreak",0);
        //Log.e("adapter","TimerValue "+count);
        t = new Timer();
        t.scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {

                time += 1;
                Log.e("adapter","Timer "+time);
                if (time == count) {
                    t.cancel();
                    t.purge();
                    time=0;
                    stopSelf();
                    Intent intent = new Intent(TimerService.this, Plain.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    startActivity(intent);
                    edit = timer.edit();
                    edit.putInt("TimerValue",2);
                    edit.commit();
                }

            }

        }, 0, 1000);

        return START_NOT_STICKY;

    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        edit = timer.edit();
        edit.putInt("uponBreak",time);
        edit.commit();
        time = timer.getInt("uponBreak",0);
        Log.e("adapter","timer destroyed"+time);
        t.cancel();
        t.purge();
        //Toast.makeText(this, "Service Destroyed Timer", Toast.LENGTH_LONG).show();
    }

}

