package com.example.gps;

import androidx.appcompat.app.AppCompatActivity;

import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

public class allgpslocations extends AppCompatActivity {
    TextView textView;
ListView listView;
ArrayList<Loc> loclist;
locAdapter adapter;
ArrayList<Long> ti;
ArrayList<Location> locationss;
String temp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allgpslocations);
        listView = findViewById(R.id.listview);
        textView = findViewById(R.id.textView);
        Geocoder geocoder = new Geocoder(allgpslocations.this, Locale.US);
        Bundle bundleObject = getIntent().getExtras();
        ti= (ArrayList<Long>)bundleObject.getSerializable("time");
        locationss= (ArrayList<Location>)bundleObject.getSerializable("loca");
        loclist = new ArrayList<>();
        for(int i=0;i<ti.size();i++){
            try {
                temp= geocoder.getFromLocation(locationss.get(i).getLatitude(), locationss.get(i).getLongitude(), 1).get(0).getAddressLine(0);
            }catch (Exception e){

            }
            loclist.add(new Loc(ti.get(i),temp));
        }
        adapter = new locAdapter(this, R.layout.costantlocations,loclist);
        listView.setAdapter(adapter);

    }
}