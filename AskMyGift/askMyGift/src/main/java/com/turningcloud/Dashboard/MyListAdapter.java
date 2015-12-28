package com.turningcloud.Dashboard;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.turningcloud.askmygift.R;

import java.util.ArrayList;

/**
 * Created by sumeet on 27/7/15.
 */
public class MyListAdapter extends BaseAdapter {

    Context context;
    LayoutInflater inflater;
    ArrayList<String> Data;
    public MyListAdapter(Context context, ArrayList<String> data) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        Data = data;
    }
    @Override
    public int getCount() {
        return Data.size();
    }

    @Override
    public Object getItem(int position) {
        return Data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = inflater.inflate(R.layout.custom_list_row,null);
        TextView listText  = (TextView)v.findViewById(R.id.list_text);
        listText.setText(Data.get(position));
        Log.i("ListData",Data.get(position));
        return v;
    }
}
