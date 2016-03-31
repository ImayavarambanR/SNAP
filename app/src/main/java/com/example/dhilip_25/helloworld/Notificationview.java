package com.example.dhilip_25.helloworld;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;

public class Notificationview extends AppCompatActivity {
    private String outputFile = null,outputFile2=null,outputFile3=null,outputFile4=null;
    MediaPlayer m = new MediaPlayer();
    Button bt;
    SharedPreferences prefs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notificationview);
        prefs=getSharedPreferences("Mypref", Context.MODE_PRIVATE);
        bt=(Button)findViewById(R.id.bt);
        Toast.makeText(getApplicationContext(), "Click the button to play the audio", Toast.LENGTH_LONG).show();
        outputFile = Environment.getExternalStorageDirectory().getAbsolutePath() + "/recording.3gp";;
        outputFile2 = Environment.getExternalStorageDirectory().getAbsolutePath() + "/recording1.3gp";;
        outputFile3 = Environment.getExternalStorageDirectory().getAbsolutePath() + "/recording3.3gp";;
        outputFile4 = Environment.getExternalStorageDirectory().getAbsolutePath() + "/recording4.3gp";;
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int play=prefs.getInt("Play",0);
                Toast.makeText(getApplicationContext(), "Your Audio is playing", Toast.LENGTH_LONG).show();
               switch(play) {
                   case 1:    try {
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
                   break;

                   case 2: try {
                       m.setDataSource(outputFile2);
                   } catch (IOException e) {
                       e.printStackTrace();
                   }

                       try {
                           m.prepare();
                       } catch (IOException e) {
                           e.printStackTrace();
                       }
                       m.start();
                       break;

                   case 3:try {
                       m.setDataSource(outputFile3);
                   } catch (IOException e) {
                       e.printStackTrace();
                   }

                       try {
                           m.prepare();
                       } catch (IOException e) {
                           e.printStackTrace();
                       }
                       m.start();
                       break;

                   case 4:try {
                       m.setDataSource(outputFile4);
                   } catch (IOException e) {
                       e.printStackTrace();
                   }

                       try {
                           m.prepare();
                       } catch (IOException e) {
                           e.printStackTrace();
                       }
                       m.start();
                       break;

               }
            }

        });

    }

}
