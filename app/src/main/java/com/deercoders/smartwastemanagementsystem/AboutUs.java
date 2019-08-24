package com.deercoders.smartwastemanagementsystem;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import java.util.Calendar;

import mehdi.sakout.aboutpage.AboutPage;
import mehdi.sakout.aboutpage.Element;

public class AboutUs extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View aboutPage = new AboutPage(this)

                .isRTL(false)
                .setImage(R.drawable.ic_logo)
                .setDescription("Smart Waste Management System is focused on solving the issue of waste management in the valley. There are a few dumping sites and containers spread across the valley but the efficient pickup of the wastes is not done. This system helps to solve this problem by timely notifying the users on when a container is filled, after which an assigned person can go and pick up the waste. This designed system also helps to overcome the problem of a person going to a site where there is no waste. Also, the vehicles that are sent to pick up the wastes are tracked in real time to make sure they are heading towards the right destination.\n")
                .addItem(new Element().setTitle("Version 0.1"))
                .addGroup("Connect with us")
                .addEmail("saurav.bhandari@deerwalk.edu.np")
                .addEmail("raman.pandey@deerwalk.edu.np")
                .addEmail("saras.karanjit@deerwalk.edu.np")
                .addEmail("kushal.bista@deerwalk.edu.np")
                .addWebsite("http://wastemanagement.com")
                .addFacebook("")
                .addYoutube("")
                .addInstagram("")
                .addGitHub("saurav529")
                .addItem(getCopyRightsElement())
                .create();

        setContentView(aboutPage);
    }


    Element getCopyRightsElement() {
        Element copyRightsElement = new Element();
        final String copyrights = String.format(getString(R.string.copy_right), Calendar.getInstance().get(Calendar.YEAR));
        copyRightsElement.setTitle(copyrights);
        copyRightsElement.setIconDrawable(R.drawable.about_icon_copy_right);
        copyRightsElement.setIconTint(mehdi.sakout.aboutpage.R.color.about_item_icon_color);
        copyRightsElement.setIconNightTint(android.R.color.white);
        copyRightsElement.setGravity(Gravity.CENTER);
        copyRightsElement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AboutUs.this, copyrights, Toast.LENGTH_SHORT).show();
            }
        });
        return copyRightsElement;
    }

}
