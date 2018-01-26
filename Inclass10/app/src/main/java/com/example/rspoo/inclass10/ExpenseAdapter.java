package com.example.rspoo.inclass10;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rspoo on 11/8/2016.
 */

public class ExpenseAdapter extends ArrayAdapter<Expense> {
    List<Expense> mWeatherList;
    Context mContext;
    int mResource;




    public ExpenseAdapter(Context context, int resource, List<Expense> objects) {
        super(context, resource, objects);
        this.mWeatherList = objects;
        this.mContext = context;
        this.mResource = resource;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        Expense singleWeather = this.mWeatherList.get(position);
        ViewHolder holder;
        if(convertView == null)
        {
            LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(mResource,parent,false);
            holder = new ViewHolder();

            holder.textView1 = (TextView) convertView.findViewById(R.id.textViewOne);
            holder.textView2 = (TextView) convertView.findViewById(R.id.textViewTwo);

            convertView.setTag(holder);
        }
        holder = (ViewHolder) convertView.getTag();
        TextView textViewOne = holder.textView1;
        TextView textViewTwo = holder.textView2;



        //Set details
        //  SimpleDateFormat format = new SimpleDateFormat("hh:mm a", Locale.US);
        // String dateString = format.format(singleWeather.time.getPreetyTime());
        textViewOne.setText(singleWeather.expenseName);
        textViewTwo.setText( "$ " +singleWeather.amount+"");


        return convertView;
    }


    static class ViewHolder
    {

        TextView textView1;

        TextView textView2;

    }
}


