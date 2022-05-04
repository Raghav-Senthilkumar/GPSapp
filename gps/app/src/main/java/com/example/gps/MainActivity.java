package com.example.gps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements LocationListener{
    TextView latitude, longitude,address, dit,timer,recentlocation1,recentlocation2,recentlocation3, longestlocation;
    LocationManager locationManager;
    Button reset;
    List<Address> adr;
    ArrayList<Location> savedLocations = new ArrayList<Location>();
    Location prev;
    private boolean run;
    Float d = 0.0f;
    long time = 0;
    long elaspedtime = 0;
    long temper = SystemClock.elapsedRealtime()/1000;
    ArrayList<Location> loc = new ArrayList<Location>();
    ArrayList<Long> times = new ArrayList<Long>();
    Double latt,longg;
    String adre;
    long tempe = 0;
    int counter =0;
    long currentt;
    long g;
    String r1,r2,r3,l1;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        latitude = findViewById(R.id.lat);
        longitude = findViewById(R.id.logn);
        address = findViewById(R.id.addd);
        dit = findViewById(R.id.distance);
        reset= findViewById(R.id.button);
        timer = findViewById(R.id.times);
        recentlocation1 =findViewById(R.id.recent);
        recentlocation2 =findViewById(R.id.recent2);
        recentlocation3 =findViewById(R.id.recent3);
        longestlocation= findViewById(R.id.longest);

        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 0);

        }
        else{
           try{
               locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
           }catch (Exception e){
               e.printStackTrace();
           }

        }
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToallgpslocations();
            }
        });



    }

    private void goToallgpslocations() {
        Intent intent = new Intent(this,allgpslocations.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("time",times);
        bundle.putSerializable("loca",loc);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putDouble("lat_value",latt);
        outState.putDouble("log_value",longg);
        outState.putFloat("distance_value",d);
        outState.putString("address_value",adre);
        outState.putString("r1_value",r1);
        outState.putString("r2_value",r2);
        outState.putString("r3_value",r3);
        outState.putString("l1_value",l1);
        outState.putLong("time_value",g);
        outState.putLong("elasped_value",elaspedtime);
        outState.putLong("timer_value",temper);
        outState.putSerializable("array_value",loc);
        outState.putSerializable("arrayd_value",times);
        outState.putInt("int_value", counter);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        latt= savedInstanceState.getDouble("lat_value");
        longg= savedInstanceState.getDouble("log_value");
        d= savedInstanceState.getFloat("distance_value");
        adre = savedInstanceState.getString("address_value");
        l1 = savedInstanceState.getString("l1_value");
        r1 = savedInstanceState.getString("r1_value");
        //recentlocation1.setText(r1);
        r2 = savedInstanceState.getString("r2_value");
        //recentlocation2.setText(r2);
        r3 = savedInstanceState.getString("r3_value");
        //recentlocation3.setText(r3);
        g = savedInstanceState.getLong("time_value");
        temper = savedInstanceState.getLong("timer_value");
        loc = (ArrayList<Location>) savedInstanceState.getSerializable("array_value");
        times = (ArrayList<Long>) savedInstanceState.getSerializable("arrayd_value");
        counter = savedInstanceState.getInt("int_value");
        elaspedtime = savedInstanceState.getLong("elasped_value");

    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        //.d("dd", String.valueOf(temper));
        currentt =SystemClock.elapsedRealtime()/1000-temper;
        latt =  location.getLatitude();
        longg = location.getLongitude();
        latitude.setText("Latitude: " +String.valueOf(latt));
        longitude.setText("Longitude: " +String.valueOf(longg));
        if(prev==null){
            prev=location;
            loc.add(location);
            address.setText(getAdress(location));
        }
        d+= location.distanceTo(prev);
        if(location.distanceTo(prev)==0){
            g = currentt-elaspedtime;
            Log.d("ff",String.valueOf(g));
            timer.setText(g+ "s");
        }
        else{
            loc.add(location);
            counter++;
            times.add((currentt- elaspedtime));
             elaspedtime = currentt;
            g = currentt-elaspedtime;
            timer.setText(g+"s");
            address.setText(getAdress(location));
        }
        dit.setText("Distance Traveled: " + Float.toString(d));
        try {
            for (int i = 0; i < loc.size(); i++) {
                if (times.get(i) > tempe) {
                    l1="Longest Location:" + times.get(i) + "s at " + getAdress(loc.get(i));
                    longestlocation.setText(l1);
                    tempe = times.get(i);
                }
            }
        }catch(Exception e){

        }

       if(counter>0) {
           try {
               r1=" Most recent: " +getAdress(loc.get(counter-1));
               recentlocation1.setText(r1);
           }catch (Exception e){

           }
       }
        if(counter>1) {
            try {
                r2=" Second most recent: " + getAdress(loc.get(counter-2));
                recentlocation2.setText(r2);
            }catch (Exception e){

            }
        }
        if(counter>2) {
            try {
                r3=" Third most recent: " + getAdress(loc.get(counter-3));
                recentlocation3.setText(r3);
            }catch (Exception e){

            }
        }

        prev=location;


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults.length>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED && grantResults[1]==PackageManager.PERMISSION_GRANTED){
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                try{
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
                }catch (Exception e){
                    e.printStackTrace();

                }

            }
        }
        else{

        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        locationManager.removeUpdates(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        }
    }
    public String getAdress(Location locations){
        Geocoder geocoder = new Geocoder(MainActivity.this, Locale.US);
        List<Address> adred;
        try {
            adred = geocoder.getFromLocation(locations.getLatitude(), locations.getLongitude(), 1);
            if(adred!=null) {
                adre= adred.get(0).getAddressLine(0);
                return adre;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}

