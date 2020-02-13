package com.example.jason.ptosis;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.CountDownTimer;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

import static com.example.jason.ptosis.MainActivity.getPhone;
import static com.example.jason.ptosis.MainActivity.getUsername;

//this class is triggered when a fall is detected

public class Alarm extends AppCompatActivity {

    public static String username;
    public static String phone;
    private static final long START_TIME_IN_MILLIS = 15000; //15sec
    private TextView mTextViewCountdown;
    private CountDownTimer mCountDownTimer;
    private boolean mTimerRunning;
    private long mTimeLeftInMillis = START_TIME_IN_MILLIS;
    boolean flagCall = false;
    private Ringtone r;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        username = getUsername();
        phone = getPhone();
        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        r = RingtoneManager.getRingtone(getApplicationContext(), notification);
        r.play();

        TextView alarmMessage = findViewById(R.id.alarmMessage);
        alarmMessage.setText(username + ", όλα εντάξει;");

        mTextViewCountdown = findViewById(R.id.text_view_countdown);

        startTimer();

        Button buttonProblem = findViewById(R.id.buttonProblem);
        buttonProblem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                phone = getPhone();
                Intent phoneIntent = new Intent(Intent.ACTION_CALL);
                phoneIntent.setData(Uri.parse("tel:"+phone));

                if (ActivityCompat.checkSelfPermission(Alarm.this,
                        Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                flagCall = true;

                r.stop();
                finish();
                startActivity(phoneIntent);
            }
        });

        Button buttonOk = findViewById(R.id.buttonOk);
        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                flagCall=true;//to prevent from calling
                r.stop();
                finish();

            }
        });


    }


    private void startTimer() {

        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 500) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                updateCountDownText();

            }

            @Override
            public void onFinish() {

            }
        }.start();

        mTimerRunning = true;
    }

    @SuppressLint("MissingPermission")
    private void updateCountDownText() {
        int seconds = (int) (mTimeLeftInMillis / 1000);

        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d", seconds);
        mTextViewCountdown.setText(timeLeftFormatted);

        if ((seconds == 0)&&(flagCall==false)) {
            phone = getPhone();
            Intent phoneIntent = new Intent(Intent.ACTION_CALL);
            phoneIntent.setData(Uri.parse("tel:"+phone));

            if (ActivityCompat.checkSelfPermission(Alarm.this,
                    Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
            }

            r.stop();
            finish();
            startActivity(phoneIntent);
        }
    }



}



