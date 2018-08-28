package com.example.eugen.groshi;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBConnection extends SQLiteOpenHelper {
    public static String VALUTE_TABLE_NAME="valutes";
    public static String VALUTE_CODE="valute_code";
    public static String VALUTE_NAME="valute_name";
    public static String VALUTE_COUNTRY="country";
    public static String VALUTE_VALUE="value";
    public static String VALUTE_IMAGE="image";
    public static String VALUTE_ORDER="val_order";
    public static String VALUTE_UP_DATE="date";
    public static String VALUTE_VISIBLE="visible";


    public DBConnection(Context context) {
        super (context, "db_name",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // запускается 1 раз при запуске или создании после удаления БД
        //создание таблицы:
        db.execSQL("CREATE TABLE "+VALUTE_TABLE_NAME+" (" +
                "_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
                +VALUTE_CODE+" TEXT,"
                +VALUTE_NAME+" TEXT,"
                +VALUTE_COUNTRY+" TEXT,"
                +VALUTE_VALUE+" REAL,"
                +VALUTE_IMAGE+" INTEGER,"
                +VALUTE_ORDER+" INTEGER,"
                +VALUTE_UP_DATE+" TEXT,"
                +VALUTE_VISIBLE+" INTEGER)");


        ContentValues cv =new ContentValues();
        //кладем один объект
        cv.put(VALUTE_CODE,"AUD");
        cv.put(VALUTE_NAME,"доллар");
        cv.put(VALUTE_COUNTRY,"Австралия");
        cv.put(VALUTE_VALUE,46.7178);
        cv.put(VALUTE_IMAGE,R.drawable.aud);
        cv.put(VALUTE_ORDER,26);
        cv.put(VALUTE_UP_DATE,"1533283817");
        cv.put(VALUTE_VISIBLE,1);
        db.insert(VALUTE_TABLE_NAME,null,cv);

        cv.put(VALUTE_CODE,"AZN");
        cv.put(VALUTE_NAME,"манат");
        cv.put(VALUTE_COUNTRY,"Азербайджан");
        cv.put(VALUTE_VALUE,37.1792);
        cv.put(VALUTE_IMAGE,R.drawable.azn);
        cv.put(VALUTE_ORDER,33);
        cv.put(VALUTE_UP_DATE,"1533283817");
        cv.put(VALUTE_VISIBLE,1);
        db.insert(VALUTE_TABLE_NAME,null,cv);

        cv.put(VALUTE_CODE,"GBP");
        cv.put(VALUTE_NAME,"фунт");
        cv.put(VALUTE_COUNTRY,"Великобритания");
        cv.put(VALUTE_VALUE,83.7125);
        cv.put(VALUTE_IMAGE,R.drawable.gbp);
        cv.put(VALUTE_ORDER,4);
        cv.put(VALUTE_UP_DATE,"1533283817");
        cv.put(VALUTE_VISIBLE,1);
        db.insert(VALUTE_TABLE_NAME,null,cv);

        cv.put(VALUTE_CODE,"AMD");
        cv.put(VALUTE_NAME,"драм");
        cv.put(VALUTE_COUNTRY,"Армения");
        cv.put(VALUTE_VALUE,0.130904);
        cv.put(VALUTE_IMAGE,R.drawable.amd);
        cv.put(VALUTE_ORDER,10);
        cv.put(VALUTE_UP_DATE,"1533283817");
        cv.put(VALUTE_VISIBLE,1);
        db.insert(VALUTE_TABLE_NAME,null,cv);

        cv.put(VALUTE_CODE,"BYN");
        cv.put(VALUTE_NAME,"рубль");
        cv.put(VALUTE_COUNTRY,"Беларусь");
        cv.put(VALUTE_VALUE,31.8163);
        cv.put(VALUTE_IMAGE,R.drawable.byn);
        cv.put(VALUTE_ORDER,2);
        cv.put(VALUTE_UP_DATE,"1533283817");
        cv.put(VALUTE_VISIBLE,1);
        db.insert(VALUTE_TABLE_NAME,null,cv);

        cv.put(VALUTE_CODE,"BGN");
        cv.put(VALUTE_NAME,"лев");
        cv.put(VALUTE_COUNTRY,"Болгария");
        cv.put(VALUTE_VALUE,37.79);
        cv.put(VALUTE_IMAGE,R.drawable.bgn);
        cv.put(VALUTE_ORDER,5);
        cv.put(VALUTE_UP_DATE,"1533283817");
        cv.put(VALUTE_VISIBLE,1);
        db.insert(VALUTE_TABLE_NAME,null,cv);

        cv.put(VALUTE_CODE,"BRL");
        cv.put(VALUTE_NAME,"реал");
        cv.put(VALUTE_COUNTRY,"Бразилия");
        cv.put(VALUTE_VALUE,16.1667);
        cv.put(VALUTE_IMAGE,R.drawable.byn2);
        cv.put(VALUTE_ORDER,6);
        cv.put(VALUTE_UP_DATE,"1533283817");
        cv.put(VALUTE_VISIBLE,1);
        db.insert(VALUTE_TABLE_NAME,null,cv);

        cv.put(VALUTE_CODE,"HUF");
        cv.put(VALUTE_NAME,"форинт");
        cv.put(VALUTE_COUNTRY,"Венгрия");
        cv.put(VALUTE_VALUE,0.225427);
        cv.put(VALUTE_IMAGE,R.drawable.gbp2);
        cv.put(VALUTE_ORDER,7);
        cv.put(VALUTE_UP_DATE,"1533283817");
        cv.put(VALUTE_VISIBLE,1);
        db.insert(VALUTE_TABLE_NAME,null,cv);

        cv.put(VALUTE_CODE,"HKD");
        cv.put(VALUTE_NAME,"доллар");
        cv.put(VALUTE_COUNTRY,"Гонконг");
        cv.put(VALUTE_VALUE,8.06155);
        cv.put(VALUTE_IMAGE,R.drawable.aud2);
        cv.put(VALUTE_ORDER,8);
        cv.put(VALUTE_UP_DATE,"1533283817");
        cv.put(VALUTE_VISIBLE,1);
        db.insert(VALUTE_TABLE_NAME,null,cv);

        cv.put(VALUTE_CODE,"DKK");
        cv.put(VALUTE_NAME,"крона");
        cv.put(VALUTE_COUNTRY,"Дания");
        cv.put(VALUTE_VALUE,9.881491);
        cv.put(VALUTE_IMAGE,R.drawable.dollar);
        cv.put(VALUTE_ORDER,9);
        cv.put(VALUTE_UP_DATE,"1533283817");
        cv.put(VALUTE_VISIBLE,1);
        db.insert(VALUTE_TABLE_NAME,null,cv);

        cv.put(VALUTE_CODE,"USD");
        cv.put(VALUTE_NAME,"доллар");
        cv.put(VALUTE_COUNTRY,"США");
        cv.put(VALUTE_VALUE,63.2604);
        cv.put(VALUTE_IMAGE,R.drawable.dollar);
        cv.put(VALUTE_ORDER,3);
        cv.put(VALUTE_UP_DATE,"1533283817");
        cv.put(VALUTE_VISIBLE,1);
        db.insert(VALUTE_TABLE_NAME,null,cv);

        cv.put(VALUTE_CODE,"EUR");
        cv.put(VALUTE_NAME,"евро");
        cv.put(VALUTE_COUNTRY,"Евросоюз");
        cv.put(VALUTE_VALUE,73.9641);
        cv.put(VALUTE_IMAGE,R.drawable.dollar);
        cv.put(VALUTE_ORDER,11);
        cv.put(VALUTE_UP_DATE,"1533283817");
        cv.put(VALUTE_VISIBLE,1);
        db.insert(VALUTE_TABLE_NAME,null,cv);

        cv.put(VALUTE_CODE,"INR");
        cv.put(VALUTE_NAME,"рупия");
        cv.put(VALUTE_COUNTRY,"Индия");
        cv.put(VALUTE_VALUE,0.920833);
        cv.put(VALUTE_IMAGE,R.drawable.dollar);
        cv.put(VALUTE_ORDER,12);
        cv.put(VALUTE_UP_DATE,"1533283817");
        cv.put(VALUTE_VISIBLE,1);
        db.insert(VALUTE_TABLE_NAME,null,cv);

        cv.put(VALUTE_CODE,"KZT");
        cv.put(VALUTE_NAME,"тенге");
        cv.put(VALUTE_COUNTRY,"Казахстан");
        cv.put(VALUTE_VALUE,0.18424802);
        cv.put(VALUTE_IMAGE,R.drawable.dollar);
        cv.put(VALUTE_ORDER,13);
        cv.put(VALUTE_UP_DATE,"1533283817");
        cv.put(VALUTE_VISIBLE,1);
        db.insert(VALUTE_TABLE_NAME,null,cv);

        cv.put(VALUTE_CODE,"CAD");
        cv.put(VALUTE_NAME,"доллар");
        cv.put(VALUTE_COUNTRY,"Канада");
        cv.put(VALUTE_VALUE,48.1287);
        cv.put(VALUTE_IMAGE,R.drawable.dollar);
        cv.put(VALUTE_ORDER,14);
        cv.put(VALUTE_UP_DATE,"1533283817");
        cv.put(VALUTE_VISIBLE,1);
        db.insert(VALUTE_TABLE_NAME,null,cv);

        cv.put(VALUTE_CODE,"KGS");
        cv.put(VALUTE_NAME,"сом");
        cv.put(VALUTE_COUNTRY,"Киргизия");
        cv.put(VALUTE_VALUE,0.92748594);
        cv.put(VALUTE_IMAGE,R.drawable.dollar);
        cv.put(VALUTE_ORDER,15);
        cv.put(VALUTE_UP_DATE,"1533283817");
        cv.put(VALUTE_VISIBLE,1);
        db.insert(VALUTE_TABLE_NAME,null,cv);

        cv.put(VALUTE_CODE,"CNY");
        cv.put(VALUTE_NAME,"юань");
        cv.put(VALUTE_COUNTRY,"Китай");
        cv.put(VALUTE_VALUE,9.54956);
        cv.put(VALUTE_IMAGE,R.drawable.dollar);
        cv.put(VALUTE_ORDER,16);
        cv.put(VALUTE_UP_DATE,"1533283817");
        cv.put(VALUTE_VISIBLE,1);
        db.insert(VALUTE_TABLE_NAME,null,cv);

        cv.put(VALUTE_CODE,"MDL");
        cv.put(VALUTE_NAME,"лей");
        cv.put(VALUTE_COUNTRY,"Молдавия");
        cv.put(VALUTE_VALUE,3.76349);
        cv.put(VALUTE_IMAGE,R.drawable.dollar);
        cv.put(VALUTE_ORDER,17);
        cv.put(VALUTE_UP_DATE,"1533283817");
        cv.put(VALUTE_VISIBLE,1);
        db.insert(VALUTE_TABLE_NAME,null,cv);

        cv.put(VALUTE_CODE,"NOK");
        cv.put(VALUTE_NAME,"крона");
        cv.put(VALUTE_COUNTRY,"Норвегия");
        cv.put(VALUTE_VALUE,7.78376);
        cv.put(VALUTE_IMAGE,R.drawable.dollar);
        cv.put(VALUTE_ORDER,18);
        cv.put(VALUTE_UP_DATE,"1533283817");
        cv.put(VALUTE_VISIBLE,1);
        db.insert(VALUTE_TABLE_NAME,null,cv);

        cv.put(VALUTE_CODE,"PLN");
        cv.put(VALUTE_NAME,"злотый");
        cv.put(VALUTE_COUNTRY,"Польша");
        cv.put(VALUTE_VALUE,16.7532);
        cv.put(VALUTE_IMAGE,R.drawable.dollar);
        cv.put(VALUTE_ORDER,19);
        cv.put(VALUTE_UP_DATE,"1533283817");
        cv.put(VALUTE_VISIBLE,1);
        db.insert(VALUTE_TABLE_NAME,null,cv);

        cv.put(VALUTE_CODE,"RON");
        cv.put(VALUTE_NAME,"лей");
        cv.put(VALUTE_COUNTRY,"Румыния");
        cv.put(VALUTE_VALUE,15.8039);
        cv.put(VALUTE_IMAGE,R.drawable.dollar);
        cv.put(VALUTE_ORDER,20);
        cv.put(VALUTE_UP_DATE,"1533283817");
        cv.put(VALUTE_VISIBLE,1);
        db.insert(VALUTE_TABLE_NAME,null,cv);

        cv.put(VALUTE_CODE,"SGD");
        cv.put(VALUTE_NAME,"доллар");
        cv.put(VALUTE_COUNTRY,"Сингапур");
        cv.put(VALUTE_VALUE,46.3573);
        cv.put(VALUTE_IMAGE,R.drawable.dollar);
        cv.put(VALUTE_ORDER,21);
        cv.put(VALUTE_UP_DATE,"1533283817");
        cv.put(VALUTE_VISIBLE,1);
        db.insert(VALUTE_TABLE_NAME,null,cv);

        cv.put(VALUTE_CODE,"TJS");
        cv.put(VALUTE_NAME,"сомони");
        cv.put(VALUTE_COUNTRY,"Таджикистан");
        cv.put(VALUTE_VALUE,6.8850403);
        cv.put(VALUTE_IMAGE,R.drawable.dollar);
        cv.put(VALUTE_ORDER,22);
        cv.put(VALUTE_UP_DATE,"1533283817");
        cv.put(VALUTE_VISIBLE,1);
        db.insert(VALUTE_TABLE_NAME,null,cv);

        cv.put(VALUTE_CODE,"TRY");
        cv.put(VALUTE_NAME,"лира");
        cv.put(VALUTE_COUNTRY,"Турция");
        cv.put(VALUTE_VALUE,13.4248);
        cv.put(VALUTE_IMAGE,R.drawable.dollar);
        cv.put(VALUTE_ORDER,23);
        cv.put(VALUTE_UP_DATE,"1533283817");
        cv.put(VALUTE_VISIBLE,1);
        db.insert(VALUTE_TABLE_NAME,null,cv);

        cv.put(VALUTE_CODE,"TMT");
        cv.put(VALUTE_NAME,"манат");
        cv.put(VALUTE_COUNTRY,"Туркмения");
        cv.put(VALUTE_VALUE,18.0906);
        cv.put(VALUTE_IMAGE,R.drawable.dollar);
        cv.put(VALUTE_ORDER,24);
        cv.put(VALUTE_UP_DATE,"1533283817");
        cv.put(VALUTE_VISIBLE,1);
        db.insert(VALUTE_TABLE_NAME,null,cv);

        cv.put(VALUTE_CODE,"UZS");
        cv.put(VALUTE_NAME,"сум");
        cv.put(VALUTE_COUNTRY,"Узбекистан");
        cv.put(VALUTE_VALUE,0.00805819);
        cv.put(VALUTE_IMAGE,R.drawable.dollar);
        cv.put(VALUTE_ORDER,25);
        cv.put(VALUTE_UP_DATE,"1533283817");
        cv.put(VALUTE_VISIBLE,1);
        db.insert(VALUTE_TABLE_NAME,null,cv);

        cv.put(VALUTE_CODE,"UAH");
        cv.put(VALUTE_NAME,"гривна");
        cv.put(VALUTE_COUNTRY,"Украина");
        cv.put(VALUTE_VALUE,2.3902001);
        cv.put(VALUTE_IMAGE,R.drawable.uah);
        cv.put(VALUTE_ORDER,1);
        cv.put(VALUTE_UP_DATE,"1533283817");
        cv.put(VALUTE_VISIBLE,1);
        db.insert(VALUTE_TABLE_NAME,null,cv);

        cv.put(VALUTE_CODE,"CZK");
        cv.put(VALUTE_NAME,"крона");
        cv.put(VALUTE_COUNTRY,"Чехия");
        cv.put(VALUTE_VALUE,2.82262);
        cv.put(VALUTE_IMAGE,R.drawable.dollar);
        cv.put(VALUTE_ORDER,27);
        cv.put(VALUTE_UP_DATE,"1533283817");
        cv.put(VALUTE_VISIBLE,1);
        db.insert(VALUTE_TABLE_NAME,null,cv);

        cv.put(VALUTE_CODE,"SEK");
        cv.put(VALUTE_NAME,"крона");
        cv.put(VALUTE_COUNTRY,"Швеция");
        cv.put(VALUTE_VALUE,7.17019);
        cv.put(VALUTE_IMAGE,R.drawable.dollar);
        cv.put(VALUTE_ORDER,28);
        cv.put(VALUTE_UP_DATE,"1533283817");
        cv.put(VALUTE_VISIBLE,1);
        db.insert(VALUTE_TABLE_NAME,null,cv);

        cv.put(VALUTE_CODE,"CHF");
        cv.put(VALUTE_NAME,"франк");
        cv.put(VALUTE_COUNTRY,"Швейцария");
        cv.put(VALUTE_VALUE,63.6788);
        cv.put(VALUTE_IMAGE,R.drawable.dollar);
        cv.put(VALUTE_ORDER,29);
        cv.put(VALUTE_UP_DATE,"1533283817");
        cv.put(VALUTE_VISIBLE,1);
        db.insert(VALUTE_TABLE_NAME,null,cv);

        cv.put(VALUTE_CODE,"ZAR");
        cv.put(VALUTE_NAME,"рэнд");
        cv.put(VALUTE_COUNTRY,"ЮАР");
        cv.put(VALUTE_VALUE,4.61108);
        cv.put(VALUTE_IMAGE,R.drawable.dollar);
        cv.put(VALUTE_ORDER,30);
        cv.put(VALUTE_UP_DATE,"1533283817");
        cv.put(VALUTE_VISIBLE,1);
        db.insert(VALUTE_TABLE_NAME,null,cv);

        cv.put(VALUTE_CODE,"KRW");
        cv.put(VALUTE_NAME,"вона");
        cv.put(VALUTE_COUNTRY,"Южная Корея");
        cv.put(VALUTE_VALUE,0.0566096);
        cv.put(VALUTE_IMAGE,R.drawable.dollar);
        cv.put(VALUTE_ORDER,31);
        cv.put(VALUTE_UP_DATE,"1533283817");
        cv.put(VALUTE_VISIBLE,1);
        db.insert(VALUTE_TABLE_NAME,null,cv);


        cv.put(VALUTE_CODE,"JPY");
        cv.put(VALUTE_NAME,"иена");
        cv.put(VALUTE_COUNTRY,"Япония");
        cv.put(VALUTE_VALUE,0.57216096);
        cv.put(VALUTE_IMAGE,R.drawable.dollar);
        cv.put(VALUTE_ORDER,32);
        cv.put(VALUTE_UP_DATE,"1533283817");
        cv.put(VALUTE_VISIBLE,1);
        db.insert(VALUTE_TABLE_NAME,null,cv);


        cv.put(VALUTE_CODE,"RUB");
        cv.put(VALUTE_NAME,"рубль");
        cv.put(VALUTE_COUNTRY,"Россия");
        cv.put(VALUTE_VALUE,1.0);
        cv.put(VALUTE_IMAGE,R.drawable.dollar);
        cv.put(VALUTE_ORDER,34);
        cv.put(VALUTE_UP_DATE,"1533283817");
        cv.put(VALUTE_VISIBLE,1);
        db.insert(VALUTE_TABLE_NAME,null,cv);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldV, int newV) {
        //при переходе от одной версии приложения к другой
        db.execSQL("DROP TABLE contacts");
        //создание заново:
        this.onCreate(db);
    }
}
