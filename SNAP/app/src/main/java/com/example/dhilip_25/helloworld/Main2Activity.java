package com.example.dhilip_25.helloworld;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Main2Activity extends ListActivity {
    int count;
    private PackageManager packageManager = null;
    private List<ApplicationInfo> applist = null;
    private ApplicationAdapter listadaptor = null;
    ListView list;
    SharedPreferences sharedPrefs,shared,Installed,forBroadcastInstalled,MainActivityOnce,onceIntro,packageShared
            ,enable;
    SharedPreferences.Editor editor,editorInstalled,edit,editOnce;

    int i;
    int CountCheck;
    Boolean valueOnce;
    String PackageName;
    ArrayList<String> appName;
    boolean bool;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent C = new Intent(Main2Activity.this, TimerPart.class);
                startActivity(C);
            }

        });
        onceIntro = getSharedPreferences("onceIntro",MODE_PRIVATE);

        list=(ListView) findViewById(android.R.id.list);

        enable =  getSharedPreferences("enable",MODE_PRIVATE);

        packageShared = getSharedPreferences("packageShared",MODE_PRIVATE);
        sharedPrefs = getSharedPreferences("sharedpref", MODE_PRIVATE);
        shared=getSharedPreferences("shared", MODE_PRIVATE);
        Installed=getSharedPreferences("Installed", MODE_PRIVATE);
        forBroadcastInstalled=getSharedPreferences("forBroadcastInstalled", MODE_PRIVATE);
        MainActivityOnce = getSharedPreferences("MainActivityOnce", MODE_PRIVATE);
        editor = sharedPrefs.edit();
        appName=new ArrayList<String>();
        packageManager = getPackageManager();
        new LoadApplications().execute();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();


        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        ApplicationInfo app = applist.get(position);

        try {
            Intent intent = packageManager
                    .getLaunchIntentForPackage(app.packageName);

            if (null != intent) {
                startActivity(intent);
            }
        } catch (ActivityNotFoundException e) {
            Toast.makeText(Main2Activity.this, e.getMessage(),
                    Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(Main2Activity.this, e.getMessage(),
                    Toast.LENGTH_LONG).show();
        }
    }

    private List<ApplicationInfo> checkForLaunchIntent(List<ApplicationInfo> list) {
        ArrayList<ApplicationInfo> applist = new ArrayList<ApplicationInfo>();
        i=0;
        for (ApplicationInfo info : list) {

            try {

                if (null != packageManager.getLaunchIntentForPackage(info.packageName)) {


                    applist.add(info);

                    editor.putString("name" + i, info.processName.toString());

                    editor.commit();
                    i++;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        Boolean checkInstalled = Installed.getBoolean("InstalledCheck", false);
        int InstalledCount = forBroadcastInstalled.getInt("InstalledCount", 0);
        CountCheck=0;
        if(checkInstalled) {
            editorInstalled = Installed.edit();
            editorInstalled.putBoolean("InstalledCheck", false);
            editorInstalled.commit();
            SharedPreferences appNamesOfSelected;
            appNamesOfSelected = getSharedPreferences("appNamesOfSelected",MODE_PRIVATE);

            count = (shared.getInt("count", 0));
            int countOfSelected = appNamesOfSelected.getInt("countOfSelected",-1);
            for (i = 0; i < (count + InstalledCount); i++) {
                Boolean j=true;
                PackageName = (sharedPrefs.getString("name" + i, ""));
                for(int k=0;k<=countOfSelected;k++){
                    String sample = appNamesOfSelected.getString("nameOfSelected" + k, "");
                    if(PackageName.equals(sample)){
                        j=false;
                        editor = shared.edit();
                        editor.putBoolean("CheckValue"+i,true);
                        editor.commit();
                    }
                    if(j){
                        editor = shared.edit();
                        editor.putBoolean("CheckValue"+i,false);
                        editor.commit();
                    }
                }
                if(i==(count + InstalledCount)){
                    SharedPreferences.Editor editBroadcast;
                    editBroadcast = forBroadcastInstalled.edit();
                    editBroadcast.putInt("InstalledCount", 0);
                    editBroadcast.commit();
                }

            }
        }


        return applist;
    }
    private class LoadApplications extends AsyncTask<Void, Void, Void> {
        private ProgressDialog progress = null;

        @Override
        protected Void doInBackground(Void... params) {
            applist = checkForLaunchIntent(packageManager.getInstalledApplications(PackageManager.GET_META_DATA));
            listadaptor = new ApplicationAdapter(Main2Activity.this,R.layout.snippet_list_row, applist);
            return null;
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

        @Override
        protected void onPostExecute(Void result) {
            setListAdapter(listadaptor);
            progress.dismiss();
            super.onPostExecute(result);
        }

        @Override
        protected void onPreExecute() {
            progress = ProgressDialog.show(Main2Activity.this, null,
                    "Loading application info...");
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }
}
