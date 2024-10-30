package com.rashi.healthapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Videos extends AppCompatActivity {
    
    @Override
    protected void onCreate(Bundle state){
        super.onCreate(state);
        setContentView(R.layout.videos);
        TextView v1 = findViewById(R.id.video1);
        TextView call = findViewById(R.id.call);
        v1.setOnClickListener(v -> {
            Uri page = Uri.parse("https://youtube.com");
            Intent web_intent = new Intent(Intent.ACTION_VIEW, page);
            startActivity(web_intent);
        });
        call.setOnClickListener(c -> {
            Uri phone = Uri.parse("tel:+917338467114");
            Intent make_call = new Intent(Intent.ACTION_DIAL, phone);
            startActivity(make_call);
        });
    }
}
