package com.example.eugen.sgc;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;


public class PlanetAdapter extends ArrayAdapter<Planet> {

    private ArrayList<Planet> list;
    public PlanetAdapter (Context ctx, ArrayList<Planet> planets){
        super(ctx,R.layout.list_planet,planets);
        list=planets;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView= LayoutInflater.from(getContext()).inflate(R.layout.list_planet,null);

        TextView a=(TextView) convertView.findViewById(R.id.list_planet_adress);
        TextView b=(TextView) convertView.findViewById(R.id.list_planet_si);
        TextView c=(TextView) convertView.findViewById(R.id.list_planet_hzd_h2o_star);
        LinearLayout lifeLay=(LinearLayout) convertView.findViewById(R.id.life_layout);
        TextView lifeTxt=(TextView) convertView.findViewById(R.id.life_txt);

        a.setText("Ввод: "+list.get(position).adress+" , id: "+list.get(position).id);
        b.setText("индекс подобия: "+list.get(position).si);
        c.setText("hzd: "+list.get(position).hzd+", h2o: "+list.get(position).h2o+"%, звезда: "+list.get(position).star);
        if (list.get(position).life==true){
            lifeTxt.setText(list.get(position).lifeform.getInfo());
            lifeLay.setVisibility(View.VISIBLE);
        }

        return convertView;
    }
}
