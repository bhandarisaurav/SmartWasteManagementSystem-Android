package com.deercoders.smartwastemanagementsystem;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class UserDashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dashboard);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.dashboard_menu, menu);
        return true;
    }

    public void logout(MenuItem item) {
        Intent myIntent = new Intent(UserDashboard.this, MainActivity.class);
        Toast.makeText(UserDashboard.this, "Logout Successful", Toast.LENGTH_LONG).show();
        startActivity(myIntent);
    }

    public void about(MenuItem item) {
        Intent myIntent = new Intent(UserDashboard.this, AboutUs.class);
        startActivity(myIntent);
    }

    public void showToast(String title) {
        Toast toast = Toast.makeText(this, title, Toast.LENGTH_LONG);
        View view = toast.getView();

        view.setBackgroundColor(Color.GRAY);

        TextView text = view.findViewById(android.R.id.message);
        text.setTextSize(18);

        text.setShadowLayer(10, 0, 0, Color.TRANSPARENT);
        text.setTextColor(Color.WHITE);
        toast.show();
    }


}
