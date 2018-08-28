package com.example.eugen.sgc;

public class Life {
    public String planetLifeRandom;
    public int lifeform;
    public int civilizationLvl;

    Life (int plf, int si, int h2o, int hzd, int star){
        planetLifeRandom=String.valueOf(plf);

        if(h2o>=80){
            lifeform=1;
        }else{
            lifeform=0;
        }
        civilizationLvl=Integer.valueOf(planetLifeRandom.substring((6),(8)));
    }

    public String getInfo(){

        String info="Жизнь: рандом жизни: "+planetLifeRandom+", жизнеформа: "+lifeform+", уровень цивилизации: "+civilizationLvl;

        return info;
    };

}
