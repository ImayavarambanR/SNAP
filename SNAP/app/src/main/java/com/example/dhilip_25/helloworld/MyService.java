package com.example.dhilip_25.helloworld;

import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.os.SystemClock;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import java.util.List;
import java.util.SortedMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeMap;

public class MyService extends Service {
    int i;
    Timer t;
    String AppName;
    String tempName;
    Boolean tempBoolean=true;
    int count;
    SharedPreferences sharedpref,forBroadcastInstalled,appNamesOfSelected;
    SharedPreferences.Editor edit;
    String PackageName;
    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }
    public void onCreate()
    {
        super.onCreate();

        forBroadcastInstalled=getSharedPreferences("forBroadcastInstalled", MODE_PRIVATE);
        Integer i = forBroadcastInstalled.getInt("InstalledCount",0);
        if(i==0){
            edit=forBroadcastInstalled.edit();
            edit.putInt("InstalledCount",0);
            edit.commit();

        }else{
            int value= forBroadcastInstalled.getInt("InstalledCount",0);
            //Log.e("adapter", "value "+value);
        }

    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "MyService", Toast.LENGTH_LONG).show();
        sharedpref = getSharedPreferences("sharedpref", MODE_PRIVATE);

        appNamesOfSelected = getSharedPreferences("appNamesOfSelected",MODE_PRIVATE);
        count = appNamesOfSelected.getInt("countOfSelected",-1);

        t = new Timer();
        t.scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {
                i = 0;

                AppName = printForegroundTask();

                //Log.e("adapter","foreground: " +AppName);
                for (i = 0; i <= count; i++) {

                    PackageName = appNamesOfSelected.getString("nameOfSelected"+i,"");

                    Log.e("adapter", " "+PackageName+ " " + i);

                    if (tempBoolean && AppName.equals(PackageName) ) {
                        tempName=PackageName;
                        tempBoolean=false;

                        Intent intent = new Intent(MyService.this,TimerService.class);
                        startService(intent);

                        Log.e("adapter", "STARTING TIMER SERVICE");

                    }else if(tempName!=null) {
                        if (!(tempName.equals(AppName))) {
                            //Log.e("adapter","STOP TIMER");
                            tempBoolean = true;
                            tempName = null;
                            Intent ii = new Intent(MyService.this, TimerService.class);
                            stopService(ii);
                        }
                    }
                }


            }

        }, 0, 1000);



        return START_STICKY;

    }




    private String printForegroundTask() {
        String currentApp = "NULL";
        if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            UsageStatsManager usm = (UsageStatsManager)this.getSystemService(Context.USAGE_STATS_SERVICE);
            long time = System.currentTimeMillis();
            List<UsageStats> appList = usm.queryUsageStats(UsageStatsManager.INTERVAL_DAILY,  time - 1000*1000, time);
            if (appList != null && appList.size() > 0) {
                SortedMap<Long, UsageStats> mySortedMap = new TreeMap<Long, UsageStats>();
                for (UsageStats usageStats : appList) {
                    mySortedMap.put(usageStats.getLastTimeUsed(), usageStats);
                }
                if (mySortedMap != null && !mySortedMap.isEmpty()) {
                    currentApp = mySortedMap.get(mySortedMap.lastKey()).getPackageName();
                }
            }
        } else {

            ActivityManager am = (ActivityManager) this.getSystemService(Context.ACTIVITY_SERVICE);
            ActivityManager.RunningTaskInfo foregroundTaskInfo = am.getRunningTasks(1).get(0);
            String foregroundTaskPackageName = foregroundTaskInfo.topActivity.getPackageName();
            currentApp = foregroundTaskPackageName;

            //Log.e("adapter","NotLollipop");
        }


        return currentApp;

    }





    @Override
    public void onTaskRemoved(Intent rootIntent) {
        Intent restartService = new Intent(getApplicationContext(), this.getClass());
        restartService.setPackage(getPackageName());
        PendingIntent restartServicePI = PendingIntent.getService(
                getApplicationContext(), 1, restartService,
                PendingIntent.FLAG_ONE_SHOT);
        AlarmManager alarmService = (AlarmManager)getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        alarmService.set(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime() + 1000, restartServicePI);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        //Toast.makeText(this, "Service Destroyed", Toast.LENGTH_LONG).show();
    }

}

