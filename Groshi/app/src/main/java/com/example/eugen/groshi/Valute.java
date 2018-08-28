package com.example.eugen.groshi;

import android.graphics.drawable.Drawable;

import java.util.ArrayList;

public class Valute {
    public String code;
    public String name;
    public String country;
    public float value;
    int image;
    int order;
    String upDate;
    int visible;

    Valute(String cd, String n, String ct, float val, int i, int o, String ud, int vis){

        code=cd;
        name=n;
        country=ct;
        value=val;
        image=i;
        //image=R.drawable.dollar;
        order=o;
        upDate=ud;
        visible=vis;
    }
}
