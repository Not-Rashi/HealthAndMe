package com.rashi.healthapp;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class StepTracker extends AppCompatActivity {
    TextView steps;
    double prev_mag = 0;
    int stepCount = 0;
    TextView maximum, average;

    boolean send_notifs = false;
    boolean track_steps = false;
    int goal = 0;
    @Override
    protected void onCreate(Bundle SavedInstanceState){
        super.onCreate(SavedInstanceState);
        setContentView(R.layout.step_trakcer);
        maximum = findViewById(R.id.highest);
        average = findViewById(R.id.average);
        DatabaseAdapter dbHandler = new DatabaseAdapter(StepTracker.this);
        Cursor max_steps = dbHandler.getMax();
        if (max_steps.getCount()!=0){
            max_steps.moveToFirst();
            int max = max_steps.getInt(0);
            maximum.setText("Maximum daily steps: "+String.valueOf(max));
        }
        Cursor avg_step = dbHandler.getAvg();
        if (avg_step.getCount()!=0){
            avg_step.moveToFirst();
            int avg = avg_step.getInt(0);
            average.setText("Average daily steps: "+String.valueOf(avg));
        }
        steps=findViewById(R.id.step_display);
        steps.setOnClickListener( r-> {
            //dbHandler.createTable();
            Toast.makeText(getApplicationContext(), "Clicked", Toast.LENGTH_LONG).show();
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            String date = format.format(calendar.getTime());
            dbHandler.recordDay(date, stepCount);
            Cursor max_step = dbHandler.getMax();
            if (max_step.getCount()!=0){
                max_step.moveToFirst();
                int max = max_step.getInt(0);
                maximum.setText("Maximum daily steps: "+String.valueOf(max));
            }
            SharedPreferences prefs = getSharedPreferences("UserPreferences", Context.MODE_PRIVATE);
            boolean check = prefs.getBoolean("send_notifications",false);
            TextView tv = findViewById(R.id.test);
            tv.setText(String.valueOf(check));
        });
        SensorManager sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        SensorEventListener step_counter = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                if (sensorEvent!=null){
                    float x = sensorEvent.values[0];
                    float y = sensorEvent.values[1];
                    float z = sensorEvent.values[2];
                    double magnitude = Math.sqrt(x*x+y*y+z*z);
                    double delta = magnitude - prev_mag;
                    prev_mag = magnitude;
                    if (delta > 6){
                        stepCount++;
                    }
                    steps.setText(String.valueOf(stepCount)+" steps");
                    if (send_notifs){
                        Intent intent = new Intent();
                        intent.setAction("GOAL_REACHED");
                        sendBroadcast(intent);
//                        if (stepCount>=goal){
//                            Intent intent = new Intent(getApplicationContext(), StepTracker.class);
//                            PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_IMMUTABLE);
//
//                            NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), "123")
//                                    .setSmallIcon(R.drawable.circle)
//                                    .setContentTitle("Goal Complete!")
//                                    .setContentText("Congratulations! You reached your daily step goal")
//                                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
//                                    .setContentIntent(pendingIntent)
//                                    .setAutoCancel(true);
//                            NotificationManagerCompat.notify(1, builder.build());
//                        }
                    }
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {

            }
        };
        sensorManager.registerListener(step_counter, sensor, SensorManager.SENSOR_DELAY_NORMAL);


    }
    protected void onStart(){
        super.onStart();
        SharedPreferences prefs = getSharedPreferences("UserPreferences", Context.MODE_PRIVATE);
        send_notifs = prefs.getBoolean("send_notifications",false);
        goal = prefs.getInt("set_goal", 0);
        //track_steps = prefs.getBoolean("track_steps", false);

    }
    private void createNotificationChannel() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Goal Complete";
            String description = "Congratulations, you have reached your daily goal!";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("123", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this.
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
