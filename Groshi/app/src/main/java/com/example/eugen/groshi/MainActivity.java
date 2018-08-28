package com.example.eugen.groshi;

import android.content.ContentValues;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.HapticFeedbackConstants;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    public static MainActivity self;
    static ConnectivityManager manager;
    static boolean mobile;
    static boolean wifi;

    MediaPlayer mp;
    ImageView switch_sounds,iv1,iv2,iv3,settings_btn,info_btn,dropping_object;
    Button button_0, button_1, button_2, button_3, button_4, button_5, button_6, button_7, button_8, button_9, button_point, button_backspace;

    final DecimalFormat df = new DecimalFormat("###,###,##0.00");
    TextView input,tv1,tv2;

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    Date currentDate;

    boolean doubleBackToExitPressedOnce = false;
    SharedPreferences prefs;
    int soundStatus;

    //этот лист мы используем как основной во всех активностях
    ArrayList<Valute> valutes = new ArrayList<>();
    final ContentValues cv =new ContentValues();
    //https://www.cbr-xml-daily.ru/daily_json.js  Юзаем этот API

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //запретили смену ориентации для главной активности
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        self=this;

        manager = (ConnectivityManager)getSystemService(MainActivity.self.CONNECTIVITY_SERVICE);

        button_0 = findViewById(R.id.button0);
        button_1 = findViewById(R.id.button1);
        button_2 = findViewById(R.id.button2);
        button_3 = findViewById(R.id.button3);
        button_4 = findViewById(R.id.button4);
        button_5 = findViewById(R.id.button5);
        button_6 = findViewById(R.id.button6);
        button_7 = findViewById(R.id.button7);
        button_8 = findViewById(R.id.button8);
        button_9 = findViewById(R.id.button9);
        button_point = findViewById(R.id.button_point);
        button_backspace = findViewById(R.id.button_backspace);

        input = findViewById(R.id.edit_field);
        tv1 = findViewById(R.id.tv1);
        tv2 = findViewById(R.id.tv2);
        iv1 = findViewById(R.id.main_first_pic);
        iv2 = findViewById(R.id.main_second_pic);
        iv3 = findViewById(R.id.main_third_pic);
        settings_btn = findViewById(R.id.settings_btn);
        info_btn = findViewById(R.id.info_btn);
        dropping_object = findViewById(R.id.dropping_object);
        switch_sounds = findViewById(R.id.switch_sounds);
        SeekBar seekbar = findViewById(R.id.seekbar);

        prefs=getSharedPreferences("MainActivity",MODE_PRIVATE);
        //данные по умолчанию:
        soundStatus = prefs.getInt("sound",1);

        //РЕКЛАМА, загрузка рекламы и виджетов занимает много времени, попробуй позже это оптимизировать
        MobileAds.initialize(this, "ca-app-pub-1926638597037129~5070311083");
        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        getList();

        //автообновление валют
        auto_update_course(valutes.get(0));
        //чек звука, смена пикчи
        if (soundStatus==0){
            switch_sounds.setImageResource(R.drawable.apple);
        }

        //картинки-жмякалки
        iv1.setImageResource(valutes.get(0).image);
        iv2.setImageResource(valutes.get(1).image);
        iv3.setImageResource(valutes.get(2).image);
        iv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                valuteInfo(iv1,0);
            }
        });
        iv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                valuteInfo(iv2,1);
            }
        });
        iv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                valuteInfo(iv3,2);
            }
        });

        settings_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //звук черепа
                sound(R.raw.skullsoundshort);

                //анимация черепа
                settings_btn.setImageResource(R.drawable.byn);
                //код
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(),SettingsActivity.class);
                startActivity(intent);
                finish();
            }
        });
        info_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //звук
                multiSound(R.raw.birdsong1, R.raw.birdsong2, R.raw.birdsong4);
                //анимация птички
                Animation bird = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.animationclickbird);
                info_btn.startAnimation(bird);
                //код
                AlertDialog.Builder dialog = new AlertDialog.Builder (self);
                dialog.setTitle(R.string.about_app);
                dialog.setMessage("Разработано Sighs of Decay в практических и обучающих целях в 2018 году. Используются курсы валют из открытого API НБ РФ. Курсы других банков могут и будут незначительно отличаться. Изображения используют сложившиеся стереотипы, носят исключительно юмористический характер и не несут цели кого-либо обидеть или оскорбить. Используя приложение вы подтверждаете, что вам нравится стиль, юмор или функционал.");
                dialog.setPositiveButton(R.string.rate, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                dialog.create().show();
            }
        });
        switch_sounds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mute();
            }
        });



        //клавиатура
        button_0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                input.setText(input.getText()+"0");
            }
        });
        button_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                input.setText(input.getText()+"1");
            }
        });
        button_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                input.setText(input.getText()+"2");
            }
        });
        button_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                input.setText(input.getText()+"3");
            }
        });
        button_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                input.setText(input.getText()+"4");
            }
        });
        button_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                input.setText(input.getText()+"5");
            }
        });
        button_6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                input.setText(input.getText()+"6");
            }
        });
        button_7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                input.setText(input.getText()+"7");
            }
        });
        button_8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                input.setText(input.getText()+"8");
            }
        });
        button_9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                input.setText(input.getText()+"9");
            }
        });
        button_point.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int points=0;
                String result = new String("");
                String inputString=input.getText().toString();
                //есть ли уже точка в этом числе?
                for (int i = 0; i < inputString.length(); i++) {
                    if (!inputString.substring(i,i+1).contains(".")) {
                        //если символ не точка - записываем
                        result=result+inputString.substring(i,i+1);
                    }else{
                        //точка есть, запоминаем
                        points=+1;
                    }
                }
                //если во входящем числе не было точек - ставим одну
                if (points==0){
                    input.setText(result+".");
                }
            }
        });
        button_backspace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (input.getText().length()>1){
                    input.setText(input.getText().subSequence(0,input.getText().length()-1));
                }else{
                    input.setText("0");
                }

            }
        });

        //слушатель ввода текста, реагирует на изменение TV input
        input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String a=s.toString();

                if (a.matches("")){
                    a = "0";
                }else{
                    //проверка первого символа одиночного числа, сначала выясняем длину числа
                    if (a.length()<=1){
                        //проверяем на 0 или ".", меняем а на 0, дописываем точку
                        if (a.equals("0")){
                            a="0";
                        }
                        if (a.substring(0,1).equals(".")){
                            a="0";
                            input.setText("0.");
                        }
                    }else{
                        //проверка длинного числа (2+ символа)
                        //если первый символ 0, то
                        if (a.substring(0,1).equals("0")){
                            //если два первых символа "0.", то ничего не делаем
                            if (!a.substring(0,2).equals("0.")){
                                a=a.substring(1);
                                input.setText(a);
                            }
                        }
                        //если первый символ точка - дописываем "0."
                        if (a.substring(0,1).equals(".")){
                            a="0."+a.substring(1);
                            input.setText(a);
                        }
                    }
                }
                //смена вьюшек с новыми введенными данными
                tv1.setText(df.format((Double.parseDouble(a)*valutes.get(0).value)/valutes.get(1).value));
                tv2.setText(df.format((Double.parseDouble(a)*valutes.get(0).value)/valutes.get(2).value));

                //value.get(0) - это целевая валюта
                //целевую валюту берем запросом, тоже в формуле заменить переменную
                //переменная uah_rc - это курс первой валюты в списке
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        //сикбар
        seekbar.setDrawingCacheBackgroundColor(Color.WHITE);
        seekbar.setPadding(45, 0, 45, 0);
        final int[] whichSound = {1};
        final int[] sound={R.raw.coins1};
        seekbar.setMax(199);
        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                int a=(i+1)*5;
                input.setText(String.valueOf(a));
                whichSound [0]=i;
                switch (i/40){
                    case(0):
                        seekBar.setThumb(getResources().getDrawable(R.drawable.apple));
                        break;
                    case(1):
                        seekBar.setThumb(getResources().getDrawable(R.drawable.apple1));
                        break;
                    case(2):
                        seekBar.setThumb(getResources().getDrawable(R.drawable.apple2));
                        break;
                    case(3):
                        seekBar.setThumb(getResources().getDrawable(R.drawable.apple3));
                        break;
                    case(4):
                        seekBar.setThumb(getResources().getDrawable(R.drawable.apple4));
                        if (dropping_object.getVisibility()==View.VISIBLE){
                            //звук
                            sound(R.raw.dropsound);
                            //анимация падения и скрытие виджета
                            Animation drop = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.animationdrop);
                            dropping_object.startAnimation(drop);
                            dropping_object.setVisibility(View.GONE);
                        }
                        break;
                }
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                if (mp!=null){
                    mp.release();
                }
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //получаем число от 0 до 5 и применяем звук
                seekbarSound(whichSound[0], sound[0]);


            }
        });
        //<<<OnCreate END>>>
    }



    //метод обновления курса, вызываемый из LoadTask
    public void response(JSONObject object) throws JSONException {
        //открываем БД в writable
        final DBConnection connection = new DBConnection(this);
        final SQLiteDatabase db=connection.getWritableDatabase();
        //
        //дату надо записывать и выводить где-нибудь, мб в шеред-префс
        //Toast.makeText(getApplicationContext(),"Курс валют обновлен "+object.getString("Date").substring(0,10)+".",Toast.LENGTH_SHORT).show();
        Toast.makeText(getApplicationContext(),R.string.course_update,Toast.LENGTH_SHORT).show();
        //получаем JSON объект и проходимся по всему в цикле

        currentDate = new Date();
        long milliseconds = currentDate.getTime();
        JSONObject subMain = object.getJSONObject("Valute");
        for (int wi=0; wi < valutes.size(); wi++) {
            JSONObject subAud = subMain.getJSONObject(valutes.get(wi).code);
            Float newCourse =Float.valueOf(subAud.getString("Value"))/subAud.getInt("Nominal");
            //System.out.println("тыц -"+valutes.get(wi).code+" --- "+" за "+subAud.getInt("Nominal")+" --- "+Float.valueOf(subAud.getString("Value"))+" = "+newCourse);
            //запишем (обновим) полученное значение в БД на соответствующую ячейку
            cv.put(DBConnection.VALUTE_VALUE,newCourse);
            cv.put(DBConnection.VALUTE_UP_DATE,String.valueOf(milliseconds));
            db.update(DBConnection.VALUTE_TABLE_NAME,cv,DBConnection.VALUTE_CODE +" = ?", new String[]{valutes.get(wi).code});
        }
        //адаптеры построения главного списка
        //мб нужно перестроить лист с валютами valutes
        //final ContactsAdapter adapter=new ContactsAdapter(this,contacts);
        //ListView.setAdapter(adapter);

        //закрываем db-wridable
        db.close();
    }



    public static void getList(){
        //первым делом открываем БД для наполнения списка values и построения порядка верстки
        final DBConnection connection = new DBConnection(MainActivity.self);
        final SQLiteDatabase db=connection.getReadableDatabase();
        //для этой будет создан кеш, она будет продублирована и все операции будут выполняться над копией, пока БД не будет закрыта.
        //или .getReadableDatabase()  //если нужно только чтение

        //курсор - это итератор, который позволяет обходить результаты получения данных
        //Cursor cursor = db.query(DBConnection.VALUTE_TABLE_NAME, new String[]{"_id", DBConnection.VALUTE_CODE, DBConnection.VALUTE_NAME, DBConnection.VALUTE_COUNTRY, DBConnection.VALUTE_VALUE, DBConnection.VALUTE_IMAGE, DBConnection.VALUTE_ORDER},
        //        null,null,null,null,null);;

        Cursor cursor = db.query(DBConnection.VALUTE_TABLE_NAME, new String[]{DBConnection.VALUTE_CODE,
                        DBConnection.VALUTE_NAME,
                        DBConnection.VALUTE_COUNTRY,
                        DBConnection.VALUTE_VALUE,
                        DBConnection.VALUTE_IMAGE,
                        DBConnection.VALUTE_ORDER,
                        DBConnection.VALUTE_UP_DATE,
                        DBConnection.VALUTE_VISIBLE},
                null,null,null,null, DBConnection.VALUTE_ORDER+" ASC");;

        String code, name, upDate, country; float value; int image, order, visible;
        //cursor.getCount()>0 - проверка есть ли что-нибудь
        if((cursor!=null)&& cursor.getCount()>0){
            while (cursor.moveToNext()){
                //порядок в query
                code=cursor.getString(0);
                name=cursor.getString(1);
                country=cursor.getString(2);
                value=cursor.getFloat(3);
                image=cursor.getInt(4);
                order=cursor.getInt(5);
                upDate=cursor.getString(6);
                visible=cursor.getInt(7);

                Valute valute = new Valute(code, name, country, value, image, order, upDate, visible);
                MainActivity.self.valutes.add(valute);
                //System.out.println(order+" "+code+" "+name+" "+country+" "+value+" "+image+" "+visible+" "+upDate+" id: "+id+". Всего: "+ MainActivity.self.valutes.size());
                //Toast.makeText(this,id+" "+code+" "+name+" "+country+" "+value+" "+image+" "+order+". Всего: "+valutes.size(),Toast.LENGTH_SHORT).show();
            }
        }
        //рекомендуется закрывать везде, где до этого открывалось
        db.close();
    }


    public void auto_update_course(Valute val){
        Date lastUpDate = new Date(Long.valueOf(val.upDate));
        currentDate = new Date();
        //тут дата апдейта прыгает, показывает завтрашний день, надо бы чекнуть
        //Toast.makeText(MainActivity.self,dateFormat.format(lastUpDate)+" и "+dateFormat.format(currentDate),Toast.LENGTH_LONG).show();
        //проверка на интернет: реклама / проверка обновлений
        //текущая дата до даты апдейта - всё ок,
        if (dateFormat.format(lastUpDate).contains(dateFormat.format(currentDate))){
            //Toast.makeText(MainActivity.self,"Всё ок",Toast.LENGTH_SHORT).show();
        }else{
            //дата апдейта перед текущей датой- устарело, надо обновить
            if (val.visible==0){
                //валюта кастомная, пусть человек обновляет
                Toast.makeText(MainActivity.self,"Рекомендуется обновить курс основной валюты.",Toast.LENGTH_SHORT).show();
            }else{
                //проверка на интернет
                mobile = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnected();
                wifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnected();
                if (!mobile && !wifi) {
                    //инета нет
                    //Toast.makeText(MainActivity.self,"Требуется подключение к интернету", Toast.LENGTH_LONG).show();
                } else {
                    //инет есть, обновляем
                    upload_course();
                }
            }
        }
    }

    public static void upload_course(){
        LoadTask task1=new LoadTask();
        task1.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,"https://www.cbr-xml-daily.ru/daily_json.js");
    }

//метод выключения/включения звука
    private void mute() {
        //записывать в шередпрефс 0/1, нет и есть звук
        //анимация от нажатия
        Animation monkey = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.animationclickbird);
        switch_sounds.startAnimation(monkey);
        //если саундСтатус 1 - звук есть, выключить
        if (soundStatus==1){
            prefs.edit().putInt("sound",0).apply();
            soundStatus=0;
            Toast.makeText(this, R.string.sound_off, Toast.LENGTH_SHORT).show();
            switch_sounds.setImageResource(R.drawable.apple);
        }else{
            //иначе включаем звук
            prefs.edit().putInt("sound",1).apply();
            soundStatus=1;
            Toast.makeText(this, R.string.sound_on, Toast.LENGTH_SHORT).show();
            switch_sounds.setImageResource(R.drawable.mute);
            //звук обезьянки
            multiSound(R.raw.monkey1, R.raw.monkey2, R.raw.monkey1);
        }
    }

//Методы вызова звуков
    public void sound(Integer wichSound){
        //в методах вызова звука смотреть что у нас в переменной, если переменная 1- воспроизводить звук
        if (soundStatus==1) {
            if (mp!=null){
                mp.release();
            }
            mp = MediaPlayer.create(getApplicationContext(),wichSound);
            mp.start();
        }

    }

    public void multiSound(Integer sound1, Integer sound2, Integer sound3){
        if (soundStatus==1) {
            if (mp!=null){
                mp.release();
            }
            //выбор песенки
            final Random random = new Random();
            int birdsong=sound1;
            switch (random.nextInt(3)){
                case 0:
                    birdsong=sound2;
                    break;
                case 1:
                    birdsong=sound1;
                    break;
                case 2:
                    birdsong=sound3;
                    break;
            }
            mp = MediaPlayer.create(getApplicationContext(),birdsong);
            mp.start();
        }
    }

    public void seekbarSound(int whichSound, int sound){
        if (soundStatus==1) {
            //mp.release проходит в сикбарченджед
            int intSound=whichSound/34;
            switch (intSound){
                case(0):
                    sound=R.raw.coins1;
                    break;
                case(1):
                    sound=R.raw.coins2;
                    break;
                case(2):
                    sound=R.raw.coins3;
                    break;
                case(3):
                    sound=R.raw.coins4;
                    break;
                case(4):
                    sound=R.raw.coins5;
                    break;
                case(5):
                    sound=R.raw.coins6;
                    break;
            }
            mp = MediaPlayer.create(getApplicationContext(),sound);
            mp.start();
        }
    }

    public void valuteInfo(ImageView iv, int valId){
        //звук
        sound(R.raw.clicksound);
        //анимация вращения
        iv.animate().rotationXBy(720f).setDuration(180).start();
        //снекбар
        CoordinatorLayout Clayout = (CoordinatorLayout)findViewById(R.id.snackbarlocation);
        Snackbar.make(Clayout, getResources().getString(R.string.valute_snackbar_txt)+valutes.get(valId).code, Snackbar.LENGTH_LONG)
                .setAction(R.string.valute_snackbar_btn, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent();
                        intent.setClass(getApplicationContext(),SettingsActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }).show();
    }


//кнопка НАЗАД
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            //звук
            sound(R.raw.doorclose1);
            //
            super.onBackPressed();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, R.string.how_to_exit, Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 1000);
    }

//вибрация обратной тактильной связи
    public void onClick(View v) {
        button_0.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
        button_1.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
        button_2.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
        button_3.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
        button_4.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
        button_5.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
        button_6.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
        button_7.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
        button_8.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
        button_9.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
        button_point.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
        button_backspace.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
    }
}
