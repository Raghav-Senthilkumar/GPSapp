package com.example.gps;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class locAdapter extends ArrayAdapter<Loc>{
    ArrayList<Loc> loclist;

    Context context;
    int xmlResource;

    public locAdapter(@NonNull Context context, int resource, ArrayList<Loc> loclist){
        super(context,resource,loclist);
        this.loclist = loclist;
        xmlResource = resource;
        this.context= context;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View adapterLayout = layoutInflater.inflate(xmlResource, null);
        TextView Times= adapterLayout.findViewById(R.id.alltimes);
        TextView locss = adapterLayout.findViewById(R.id.allloc);
        Times.setText(String.valueOf(loclist.get(position).getTimes())+"s");
        locss.setText(loclist.get(position).getLoc());
        return adapterLayout;
    }

}