package com.example.dhilip_25.helloworld;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Dhilip_25 on 3/28/2016.
 */
public class SimpleNotify extends Service {
    private String outputFile = null;
    MediaPlayer m = new MediaPlayer();
    Button bt;
    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }
    public void onCreate()
    {
        super.onCreate();
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(getApplicationContext(), "SERVICE STARTED", Toast.LENGTH_LONG).show();
        outputFile = Environment.getExternalStorageDirectory().getAbsolutePath() + "/recording.3gp";
        ;
        stopSelf();
        try {

            m.setDataSource(outputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            m.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        m.start();
        return START_NOT_STICKY;
    }

}
