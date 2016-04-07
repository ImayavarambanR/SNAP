package com.example.dhilip_25.helloworld;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaRecorder;
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
import java.sql.Time;
import java.util.Calendar;
import java.util.Date;

public class Example extends AppCompatActivity {
    Button b1,b2,b3,b4,b5,b6,b7,b8,done;
    SharedPreferences prefs;
    int hr2,hr3,hr4;
    int min2,min3,min4;
    private MediaRecorder myAudioRecorder,myAudioRecorder2,myAudioRecorder3,myAudioRecorder4;
    private String outputFile = null,outputFile2 = null,outputFile3 = null,outputFile4 = null;
    int r=0,r2=0,r3=0,r4=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example);

        b1=(Button)findViewById(R.id.button2);
        b2=(Button)findViewById(R.id.button5);
        b3=(Button)findViewById(R.id.button3);
        b4=(Button)findViewById(R.id.button7);

        outputFile = Environment.getExternalStorageDirectory().getAbsolutePath() + "/recording.3gp";;
        myAudioRecorder=new MediaRecorder();
        myAudioRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        myAudioRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        myAudioRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        myAudioRecorder.setOutputFile(outputFile);

        outputFile2 = Environment.getExternalStorageDirectory().getAbsolutePath() + "/recording1.3gp";;
        myAudioRecorder2=new MediaRecorder();
        myAudioRecorder2.setAudioSource(MediaRecorder.AudioSource.MIC);
        myAudioRecorder2.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        myAudioRecorder2.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        myAudioRecorder2.setOutputFile(outputFile2);

        outputFile3 = Environment.getExternalStorageDirectory().getAbsolutePath() + "/recording3.3gp";;
        myAudioRecorder3=new MediaRecorder();
        myAudioRecorder3.setAudioSource(MediaRecorder.AudioSource.MIC);
        myAudioRecorder3.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        myAudioRecorder3.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        myAudioRecorder3.setOutputFile(outputFile3);

        outputFile4 = Environment.getExternalStorageDirectory().getAbsolutePath() + "/recording4.3gp";;
        myAudioRecorder4=new MediaRecorder();
        myAudioRecorder4.setAudioSource(MediaRecorder.AudioSource.MIC);
        myAudioRecorder4.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        myAudioRecorder4.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        myAudioRecorder4.setOutputFile(outputFile4);

        prefs=getSharedPreferences("Mypref", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor=prefs.edit();

        b5=(Button)findViewById(R.id.button4);
        b6=(Button)findViewById(R.id.button6);
        b7=(Button)findViewById(R.id.button8);
        b8=(Button)findViewById(R.id.bt3);
       done =(Button)findViewById(R.id.done);

        /*prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        hour =prefs.getInt("hours", 0);
        min = timer.getInt("uponBreak",0);*/
         int hr=prefs.getInt("Hour",0);
         int min=prefs.getInt("Min",0);
        int cnt=prefs.getInt("cnt",0);
        String rep=prefs.getString("Repeat", "No name defined");
        int repeat=Integer.parseInt(rep);
        if(cnt==0) {
            cnt++;
            b1.setText(hr + ":" + min);
            hr += repeat;
            if (hr < 24) {
                hr2 = hr;
                b2.setText(hr2 + ":" + min);
            } else {
                hr = (hr - 23) - 1;
                hr2 = hr;
                b2.setText(hr2 + ":" + min);
            }
            hr += repeat;
            if (hr < 24) {
                hr3 = hr;
                b3.setText(hr3 + ":" + min);
            } else {
                hr = (hr - 23) - 1;
                hr3 = hr;
                b3.setText(hr3 + ":" + min);
            }
            hr += repeat;
            if (hr < 24) {
                hr4 = hr;
                b4.setText(hr4 + ":" + min);
            } else {
                hr = (hr - 23) - 1;
                hr4 = hr;
                b4.setText(hr4 + ":" + min);
            }
            editor.putInt("Hour2",hr2);
            editor.putInt("Min2", min);
            editor.putInt("Hour3",hr3);
            editor.putInt("Min3", min);
            editor.putInt("Hour4",hr4);
            editor.putInt("Min4",min);
            editor.commit();
        }
         else{
            int hour2=prefs.getInt("Hour2",0);
            int min2=prefs.getInt("Min2", 0);
            int hour3=prefs.getInt("Hour3",0);
            int min3=prefs.getInt("Min3",0);
            int hour4=prefs.getInt("Hour4",0);
            int min4=prefs.getInt("Min4",0);
            b1.setText(hr + ":" + min);
            b2.setText(hour2 + ":" + min2);
            b3.setText(hour3 + ":" + min3);
            b4.setText(hour4 + ":" + min4);
        }




        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putInt("Button1",1);
                editor.commit();
                Intent i = new Intent(Example.this, NumberPick.class);
                startActivity(i);
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putInt("Button1",2);
                editor.commit();
                Intent i = new Intent(Example.this, NumberPick.class);
                startActivity(i);
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putInt("Button1",3);
                editor.commit();
                Intent i = new Intent(Example.this, NumberPick.class);
                startActivity(i);
            }
        });
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putInt("Button1",4);
                editor.commit();
                Intent i = new Intent(Example.this, NumberPick.class);
                startActivity(i);
            }
        });

        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                r++;
                if (r % 2 != 0) {
                    b5.setBackgroundResource(R.drawable.recored);
                    try {
                        myAudioRecorder.prepare();
                        myAudioRecorder.start();
                        Toast.makeText(getApplicationContext(), "AudioRecording", Toast.LENGTH_LONG).show();
                    } catch (IllegalStateException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                else
                {
                    b5.setBackgroundResource(R.drawable.record);
                    myAudioRecorder.stop();
                    myAudioRecorder.release();
                    myAudioRecorder  = null;
                    Toast.makeText(getApplicationContext(), "Audio recorded successfully",Toast.LENGTH_LONG).show();
                }
            }

        });

        b6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                r2++;
                if (r2 % 2 != 0) {
                    b6.setBackgroundResource(R.drawable.recored);
                    try {
                        myAudioRecorder2.prepare();
                        myAudioRecorder2.start();
                        Toast.makeText(getApplicationContext(), "AudioRecording", Toast.LENGTH_LONG).show();
                    } catch (IllegalStateException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                else
                {
                    b6.setBackgroundResource(R.drawable.record);
                    myAudioRecorder2.stop();
                    myAudioRecorder2.release();
                    myAudioRecorder2  = null;
                    Toast.makeText(getApplicationContext(), "Audio recorded successfully",Toast.LENGTH_LONG).show();
                }

            }
        });

        b7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                r3++;
                if (r3 % 2 != 0) {
                    b7.setBackgroundResource(R.drawable.recored);
                    try {
                        myAudioRecorder3.prepare();
                        myAudioRecorder3.start();
                        Toast.makeText(getApplicationContext(), "AudioRecording", Toast.LENGTH_LONG).show();
                    } catch (IllegalStateException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                else
                {
                    b7.setBackgroundResource(R.drawable.record);
                    myAudioRecorder3.stop();
                    myAudioRecorder3.release();
                    myAudioRecorder3  = null;
                    Toast.makeText(getApplicationContext(), "Audio recorded successfully",Toast.LENGTH_LONG).show();
                }
            }
        });

        b8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                r4++;
                if (r4 % 2 != 0) {
                    b8.setBackgroundResource(R.drawable.recored);
                    try {
                        myAudioRecorder4.prepare();
                        myAudioRecorder4.start();
                        Toast.makeText(getApplicationContext(), "AudioRecording", Toast.LENGTH_LONG).show();
                    } catch (IllegalStateException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                else
                {
                    b8.setBackgroundResource(R.drawable.record);
                    myAudioRecorder4.stop();
                    myAudioRecorder4.release();
                    myAudioRecorder4  = null;
                    Toast.makeText(getApplicationContext(), "Audio recorded successfully",Toast.LENGTH_LONG).show();
                }
            }
        });

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Example.this, Notify.class);
                startService(i);
                Intent ii=new Intent(Example.this,End.class);
                startActivity(ii);

            }
        });

    }

}
