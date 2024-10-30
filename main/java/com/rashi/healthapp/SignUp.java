package com.rashi.healthapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.firestore.FirebaseFirestore;
import com.rashi.healthapp.models.User;


public class SignUp extends AppCompatActivity {
    FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        EditText et_username = findViewById(R.id.username);
        EditText et_name = findViewById(R.id.name);
        EditText et_email = findViewById(R.id.email);
        EditText et_phone = findViewById(R.id.phone);
        EditText et_password = findViewById(R.id.password);
        TextView error = findViewById(R.id.error);
        db = FirebaseFirestore.getInstance();
        DatabaseAdapter dbHandler = new DatabaseAdapter(SignUp.this);
        Button signUp = findViewById(R.id.BtnSignup);
        signUp.setOnClickListener(v -> {
            String username = et_username.getText().toString();
            String name = et_name.getText().toString();
            String email = et_email.getText().toString();
            String phone = et_phone.getText().toString();
            String password= et_password.getText().toString();
            if (username.isEmpty()||name.isEmpty()||email.isEmpty()||phone.isEmpty()||password.isEmpty()){
                error.setText("Please fill all fields!");
            }
            else {
                dbHandler.newUser(username, name, email, phone, password);
                User user = new User(email, name, username, phone, password);
                db.collection("Users").add(user)
                        .addOnSuccessListener(s ->{
                            Toast.makeText(getApplicationContext(), "Successfully signed up", Toast.LENGTH_LONG).show();
                            error.setText("Successfully signed up");
                        })
                        .addOnFailureListener(f ->{
                                Toast.makeText(getApplicationContext(), "Could not sign up", Toast.LENGTH_LONG).show();
                                error.setText("Could not sign up");

                        });

                Intent goHome = new Intent(this, HomePage.class);
                startActivity(goHome);
            }

        });
    }

}
