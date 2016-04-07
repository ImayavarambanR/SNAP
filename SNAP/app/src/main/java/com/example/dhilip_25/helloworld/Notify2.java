package com.example.dhilip_25.helloworld;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.bluetooth.BluetoothClass;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Environment;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Dhilip_25 on 3/31/2016.
 */
public class Notify2 extends Service {
    int hours,hr;
    int minutes,min;
    int m2=10;
    int m1=0;
    Timer t;
    private String outputFile2 = null;
    MediaPlayer me1 = new MediaPlayer();
    SharedPreferences prefs;
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
       // Toast.makeText(getApplicationContext(), "SERVICE 2 STARTED", Toast.LENGTH_LONG).show();
        outputFile2 = Environment.getExternalStorageDirectory().getAbsolutePath() + "/recording1.3gp";;
        prefs=getSharedPreferences("Mypref", Context.MODE_PRIVATE);
        hr=prefs.getInt("Hour2", 0);
        min=prefs.getInt("Min2", 0);
        t = new Timer();
        t.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Date dt = new Date();
                hours = dt.getHours();
                minutes = dt.getMinutes();
                //m1 += 1;
                Log.e("adapter", "TimerValue " + minutes);
                if ((hours == hr) && (minutes == min)) {
                    t.cancel();
                    t.purge();
                    // m1=0;
                    stopSelf();
                    try {

                        me1.setDataSource(outputFile2);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    try {
                        me1.prepare();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    me1.start();
                    final SharedPreferences.Editor editor=prefs.edit();
                    editor.putInt("Play",2);
                    editor.commit();
                    Log.e("adapter", "TimerValue " + "done");
                    Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), R.drawable.play);
                    NotificationManager notif=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
                    NotificationCompat.Builder notify= new NotificationCompat.Builder(Notify2.this)
                            .setContentTitle("Voice Notification")
                            .setContentText("Please click this ")
                            .setAutoCancel(true)
                            .setSmallIcon(R.drawable.abc)
                            .setLargeIcon(largeIcon);
                    Intent i = new Intent(Notify2.this,Notificationview.class);
                    i.putExtra("i",true);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    PendingIntent pending= PendingIntent.getActivity(Notify2.this, (int) System.currentTimeMillis(), i, PendingIntent.FLAG_CANCEL_CURRENT);
                    notify.setContentIntent(pending);
                    notif.notify(0, notify.build());
                }else{
                }

            }

        }, 0, 1000);


        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Intent i=new Intent(Notify2.this,Notify3.class);
        startService(i);
        t.cancel();
        t.purge();
        stopSelf();
    }
}


