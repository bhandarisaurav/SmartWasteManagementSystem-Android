package com.deercoders.smartwastemanagementsystem;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Smart Waste Management System");
        setSupportActionBar(toolbar);

        Button button = findViewById(R.id.btn_login);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                EditText email = findViewById(R.id.input_email);

                EditText password = findViewById(R.id.input_password);

                if (email.getText().toString().equals("admin") && password.getText().toString().equals("password")) {
                    Intent myIntent = new Intent(MainActivity.this, UserDashboard.class);
                    startActivity(myIntent);
                } else {
                    Toast.makeText(MainActivity.this, "Enter Valid Username/Password", Toast.LENGTH_LONG).show();

                }
            }
        });
    }


}
