package com.example.eugen.groshi;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;

public class ValuteAdapter extends ArrayAdapter<Valute>{

    private ArrayList<Valute> list;
    public ValuteAdapter(@NonNull Context context, ArrayList<Valute> valutes) {
        super(context,R.layout.list_item,valutes);
        list=valutes;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent){
        if(convertView==null){
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.list_item,null);
        }

        TextView spoiler = (TextView) convertView.findViewById(R.id.list_item_spoiler);
        TextView a = (TextView) convertView.findViewById(R.id.list_item_txt);
        TextView b = (TextView) convertView.findViewById(R.id.list_item_country);
        Button up = (Button) convertView.findViewById(R.id.up_btn);
        Button down = (Button) convertView.findViewById(R.id.down_btn);
        ImageView iv =(ImageView) convertView.findViewById(R.id.list_item_img);

        final Valute target = list.get(position);

        a.setText(target.code);
        b.setText(target.country);
        iv.setImageResource(target.image);

        switch (target.order){
            case 1:
                spoiler.setVisibility(View.VISIBLE);
                spoiler.setText("Что конвертируем:");
                break;
            case 2:
                spoiler.setVisibility(View.VISIBLE);
                spoiler.setText("Видимые варианты конвертации:");
                break;
            case 4:
                spoiler.setVisibility(View.VISIBLE);
                spoiler.setText("Скрытые варианты конвертации:");
                break;
            default:
                spoiler.setVisibility(View.GONE);
                break;
        }

        up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SettingsActivity.setOrder(target.order,true);
                SettingsActivity.update_adapter();
            }
        });
        down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SettingsActivity.setOrder(target.order,false);
                SettingsActivity.update_adapter();
            }
        });


        return convertView;


    }
}
