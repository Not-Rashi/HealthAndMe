package com.rashi.healthapp;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;

public class HomePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView tv_vid = findViewById(R.id.video);
        TextView tv_bmi = findViewById(R.id.bmi);
        TextView tv_stepTracker = findViewById(R.id.steps);
        tv_stepTracker.setOnClickListener(s -> {
            Intent steps_page = new Intent(this, StepTracker.class);
            startActivity(steps_page);
        });
        tv_bmi.setOnClickListener(v ->{
            Intent bmi_page = new Intent(this, BmiCalc.class);
            startActivity(bmi_page);
        });
        tv_vid.setOnClickListener(t -> {
            Intent vid_page = new Intent(this, Videos.class);
            startActivity(vid_page);
        });
        sendNotification();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
//        Toast.makeText(getApplicationContext(), String.valueOf(R.id.settings), Toast.LENGTH_LONG).show();
//        return true;

        int id = item.getItemId();
        if (id==R.id.settings){
            Intent goSettings = new Intent(this, SettingsActivity.class);
            startActivity(goSettings);
            return true;

        }
        else if (id==R.id.history){
            return true;
        }
        else if (id==R.id.logout){
            Intent returnStart = new Intent(this, MainActivity.class);
            startActivity(returnStart);
            return true;
        }
        else{
            return super.onOptionsItemSelected(item);
        }
    }
    void sendNotification() {

        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        NotificationManager notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    "Reminder",
                    "Reminder_name",
                    NotificationManager.IMPORTANCE_HIGH
            );
            notificationManager.createNotificationChannel(channel);
        }

        Log.d("NotifManager", "Sending notification");

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "Reminder")
                .setSmallIcon(R.drawable.circle)
                .setContentTitle("Hi there!")
                .setContentText("Welcome to HealthAndMe")
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setDefaults(NotificationCompat.DEFAULT_ALL);

        notificationManager.notify(0, builder.build());

        Log.d("NotifManager", "Notification sent");
    }

}
