package com.rashi.healthapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class BmiCalc extends AppCompatActivity {
    EditText height;
    EditText weight;
    Button btnCalc;
    TextView bmi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bmi_calc);
        height = findViewById(R.id.height);
        weight = findViewById(R.id.weight);
        btnCalc = findViewById(R.id.calcBMI);
        bmi = findViewById(R.id.bmi);
        btnCalc.setOnClickListener(v -> {
            try{
            float h = Float.parseFloat(height.getText().toString());
            float w= Float.parseFloat(weight.getText().toString());
            float result = w/(h*h);
            bmi.setText(String.valueOf(result));
            }
            catch (Exception e){
                Toast.makeText(getApplicationContext(), "Please enter numeric values only", Toast.LENGTH_LONG).show();
            }
        });
    }
}
