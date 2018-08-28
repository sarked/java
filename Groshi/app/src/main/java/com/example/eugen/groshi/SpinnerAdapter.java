package com.example.eugen.groshi;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class SpinnerAdapter extends ArrayAdapter<Valute> {
    private ArrayList<Valute> list;

    public SpinnerAdapter(Context ctx, ArrayList<Valute> valutes) {
        super(ctx, R.layout.spinner_item, valutes);
        list = valutes;
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        if(convertView==null){
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.spinner_item,null);
        }
        TextView city =  convertView.findViewById(R.id.spinner_item_valute);
        city.setText(list.get(position).code);
//это нужно оптимизировать
        return convertView;
    }
    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        if(convertView==null){
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.spinner_item,null);
        }
        TextView city = (TextView) convertView.findViewById(R.id.spinner_item_valute);
        city.setText(" "+list.get(position).code+" ("+list.get(position).name+") "+list.get(position).country);
        return convertView;
    }
}
