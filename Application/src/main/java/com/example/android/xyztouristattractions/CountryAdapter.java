package com.example.android.xyztouristattractions;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Alex on 26.03.2016.
 */
public class CountryAdapter extends BaseAdapter {
    Context context;
    ArrayList<Country> listData;

    public CountryAdapter(Context context,ArrayList<Country> listData){
        this.context = context;
        this.listData = listData;
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    class ViewHolder {
        private TextView textViewCountryName;
    }
    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if(view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.city_item,null);
            viewHolder = new ViewHolder();
            viewHolder.textViewCountryName = (TextView) view.findViewById(R.id.txtViewCountryName);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }
        Country country = listData.get(position);
        String countryName = country.getName();
        viewHolder.textViewCountryName.setText(countryName);
        return view;
    }
}
