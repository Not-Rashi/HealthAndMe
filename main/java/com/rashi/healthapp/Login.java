package com.rashi.healthapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.rashi.healthapp.models.User;

public class Login extends AppCompatActivity {
    FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        EditText user_email = findViewById(R.id.user_email);
        EditText user_password = findViewById(R.id.user_password);
        db = FirebaseFirestore.getInstance();
        Button btnLogin = findViewById(R.id.BtnLogin);
        btnLogin.setOnClickListener(v ->{
//            Intent goHome = new Intent(this, HomePage.class);
//            startActivity(goHome);
            String email = user_email.getText().toString();
            String password = user_password.getText().toString();
            db.collection("Users").whereEqualTo("email", email)
                    .get()
                    .addOnCompleteListener(q ->{
                        if (q.isSuccessful()) {
                            for (QueryDocumentSnapshot doc : q.getResult()) {
                                User user = doc.toObject(User.class);
                                String stored_pass = user.getPassword();
                                Toast.makeText(getApplicationContext(), "Password: "+stored_pass, Toast.LENGTH_LONG).show();
                                if (stored_pass.equals(password)) {
                                    Toast.makeText(getApplicationContext(), "Successful login", Toast.LENGTH_LONG).show();
                                    Intent goHome = new Intent(this, HomePage.class);
                                    startActivity(goHome);
                                } else {
                                    Toast.makeText(getApplicationContext(), "Incorrect password", Toast.LENGTH_LONG).show();
                                }
                                break;
                            }
                        }
                        else{
                            Toast.makeText(getApplicationContext(), "Incorrect email", Toast.LENGTH_LONG).show();
                        }

                    });

//            String email = user_email.getText().toString();
//            String password = user_password.getText().toString();
//            DatabaseAdapter dbHandler = new DatabaseAdapter(Login.this);
//            Cursor records = dbHandler.getRecord(email);
//            String stored_password = "";
//            if (records.getCount()==0){
//                Toast.makeText(getApplicationContext(), "Invalid email", Toast.LENGTH_LONG).show();
//            }
//            else{
//                while (records.moveToNext()){
//                    stored_password = records.getString(1);
//                    break;
//                }
//                if (!password.equals(stored_password)){
//                    Toast.makeText(getApplicationContext(), "Invalid password", Toast.LENGTH_LONG).show();
//                }
//                else{
//                    Intent goHome = new Intent(this, HomePage.class);
////                  startActivity(goHome);
//                }
//            }
        });

    }

}
