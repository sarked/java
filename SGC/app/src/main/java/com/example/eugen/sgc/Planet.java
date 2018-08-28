package com.example.eugen.sgc;


import java.util.Random;

public class Planet {
    public long adress;
    public String id;
    public int si;
    public int h2o;
    public int hzd;
    int star;
    boolean life;
    Life lifeform;

    Planet (long inputAdress, String randomValue){
        adress=inputAdress;
        // здесь к нам приходит 123 456 789
        id=randomValue;


        //определяем индекс подобия по последнему символу
        si=Integer.valueOf(id.substring(8));

        //удаленность от обитаемой зоны:
        hzd=Integer.valueOf(id.substring((5),(6)));

        //вода:
        if (si>=1){
            h2o=Integer.valueOf(id.substring((6),(8)));
            //если очень жарко или очень холодно - количество жидкой воды снижаем на 25%
            if(hzd<2&&h2o>25||hzd>7&&h2o>25){
                h2o=h2o-25;
            }
        }else{
            h2o=0;
        }

        // тип звезды:
        star=Integer.valueOf(id.substring((3),(5)));

        //Возможность жизни на планете

        if (si>=1&&h2o>20&&hzd>=2&&hzd<=7&&star<=70){
            //+перманентный шанс%? Растянуть рамки?
            life=true;
            //Запуск генератора жизни.
            //если false +% и ещё раз проверка

            Random random=new Random(adress);
            int planetLifeRandom=Math.abs(random.nextInt());
            if(String.valueOf(planetLifeRandom).length()>=9) {
                System.out.println("Генератор жизни > Вводимый адрес "+adress+". Нарандомленный ID жизни: "+planetLifeRandom);
                lifeform = new Life(planetLifeRandom, si, h2o, hzd, star);
            }
            else {
                System.out.println("Ошибка рандома жизни > длина ввода: "+String.valueOf(planetLifeRandom).length());
                life=false;
            }
        }else{
            life=false;
        }




    }

    public String getParams(){

        String strParams="Ввод: "+adress+", id: "+id+"; Индекс подобия: "+si+" , hzd: "+hzd+", вода: "+h2o+"%, звезда: "+star+". Жизнь на планете "+life;

        return strParams;
    };
}
