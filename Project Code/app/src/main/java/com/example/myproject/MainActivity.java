package com.example.myproject;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {
    Button SignIn, SignUp;
    TextView heading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SignIn = findViewById(R.id.button1);
        heading = findViewById(R.id.textView1);
        SignUp = findViewById(R.id.button2);
        SignIn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View w) {
                Intent j = new Intent(MainActivity.this, Login.class);
                startActivity(j);
            }
        });

        SignUp.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, Registeration.class);
                startActivity(i);
            }
        });
    }
}