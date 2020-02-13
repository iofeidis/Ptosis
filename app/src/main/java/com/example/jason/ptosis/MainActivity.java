package com.example.jason.ptosis;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.LinkedList;

import static android.content.Intent.ACTION_SCREEN_OFF;


public class MainActivity extends AppCompatActivity implements SensorEventListener {


    private static final String TAG = "MainActivity";
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    //TextView acceleration;

    float x, y, z;
    float t;
    boolean flag = false, flagfall = false, flaghit = false;
    public int i = 1, k = 0, p = 0;



    public static String username, phone;

    public static LinkedList<String> q = new LinkedList<>();

    public static LinkedList<String> getQ() {
        return q;
    }


    public static String getUsername() {
        return username;
    }

    public static String getPhone() {
        return phone;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username= getIntent().getStringExtra("username");
        phone= getIntent().getStringExtra("phone");


        //Toast.makeText(this, phone , Toast.LENGTH_SHORT).show();

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        if (mAccelerometer != null) {
            // Success! There's an accelerometer.
            mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL); //boolean
        }

        Button historybutton = (Button) findViewById(R.id.historybutton);
        historybutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(MainActivity.this, History.class);

                startActivity(intent1);

            }
        });



    }


    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        x = sensorEvent.values[0];
        y = sensorEvent.values[1];
        z = sensorEvent.values[2];

        t = (float) Math.sqrt(x * x + y * y + z * z);
        t = Math.round(t * 1000) / 1000;

        if (t < 5) {
            flagfall = true;                  //supposedly person starts free fall
        }

        if ((t > 19) && (flagfall == true)) { // supposedly person hits the ground
            flaghit = true;
        }
        if ((k < 40) && (flaghit == true)) { //~4sec
            if ((t > 8) && (t < 11)) {       //supposedly person remains to the ground
                //FALL DETECTED
                flagfall = false;
                flaghit = false;
                k = 0;
                p++;//Fall History

                long date = System.currentTimeMillis();
                SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy  \nhh:mm:ss a");
                String dateString = sdf.format(date);

                if (p<20) {
                    q.add(dateString);
                } else {
                    q.remove();
                    q.add(dateString);
                }
                //ALARM
                Intent intentAlarm = new Intent(MainActivity.this, Alarm.class);
                startActivity(intentAlarm);
            }

        } else k++;

        if (k>30) {
            flagfall = false;
            flaghit = false;
            k = 0;
        }


    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}

