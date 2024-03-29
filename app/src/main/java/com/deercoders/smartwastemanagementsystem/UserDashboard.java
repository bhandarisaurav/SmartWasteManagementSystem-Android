package com.deercoders.smartwastemanagementsystem;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Locale;

import fr.quentinklein.slt.LocationTracker;
import fr.quentinklein.slt.TrackerSettings;

public class UserDashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dashboard);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final TextView locationDisplay = findViewById(R.id.location_value);
        final TextView speedDisplay = findViewById(R.id.speed);
        final TextView longitudeDisplay = findViewById(R.id.longitude);
        final TextView latitudeDisplay = findViewById(R.id.latitude);
        TrackerSettings settings =
                new TrackerSettings()
                        .setUseGPS(true)
                        .setUseNetwork(true)
                        .setUsePassive(true)
                        .setTimeBetweenUpdates(60 * 60)
                        .setMetersBetweenUpdates(2);

        LocationTracker tracker = new LocationTracker(UserDashboard.this, settings) {

            @Override
            public void onLocationFound(Location location) {
                float bearing = location.getBearing();
                String data = "" + bearing;
//                        locationDisplay.setText(data);
//
//                        float speed = location.getSpeed();
//                        data = "" + speed;
//                        speedDisplay.setText(data);

                double longitude = location.getLongitude();
                data = "" + longitude;
                longitudeDisplay.setText(data);

                double latitude = location.getLatitude();
                data = "" + latitude;
                latitudeDisplay.setText(data);

                Geocoder geocoder;
                List<Address> addresses;
                geocoder = new Geocoder(UserDashboard.this, Locale.getDefault());

                try {
                    double lat = location.getLatitude();
                    double lon = location.getLongitude();
                    addresses = geocoder.getFromLocation(lat, lon, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                    String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                    String city = addresses.get(0).getLocality();
                    String state = addresses.get(0).getAdminArea();
                    String country = addresses.get(0).getCountryName();
                    String postalCode = addresses.get(0).getPostalCode();
                    String knownName = addresses.get(0).getFeatureName();
                    System.out.println("\n============================================================================================================================================");
                    System.out.println("address = " + address);
                    System.out.println("city = " + city);
                    System.out.println("state = " + state);
                    System.out.println("country = " + country);
                    System.out.println("postalCode = " + postalCode);
                    System.out.println("knownName = " + knownName);
                    System.out.println("============================================================================================================================================\n");
                    locationDisplay.setText(address);
                    speedDisplay.setText(city);

                    if (address != null) {
                        String addr = address.split(",")[0].replace(" ", "%20");
//                                URL url = new URL("http://192.168.137.188:8080/location/save?user=1&address=" + addr + "&latitude=" + latitude + "&longitude=" + longitude);
//                                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
//                                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
//                                urlConnection.disconnect();

                        // Instantiate the RequestQueue.
                        RequestQueue queue = Volley.newRequestQueue(UserDashboard.this);
                        String url = "http://192.168.137.58:8080/location/save?user=1&address=" + encodeValue(addr) + "&latitude=" + latitude + "&longitude=" + longitude;
                        System.out.println(url);

                        JsonObjectRequest request = new JsonObjectRequest(url, null,
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        if (null != response) {
                                            Toast.makeText(UserDashboard.this, "ADDRESS SENT", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                }, new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        });
                        queue.add(request);
                    }


                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onTimeout() {

            }
        };
        if (ActivityCompat.checkSelfPermission(UserDashboard.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(UserDashboard.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        tracker.startListening();


    }


    public static String encodeValue(String value) {
        try {
            return URLEncoder.encode(value, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException ex) {
            throw new RuntimeException(ex.getCause());
        }
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
