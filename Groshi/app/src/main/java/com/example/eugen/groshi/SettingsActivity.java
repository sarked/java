package com.example.eugen.groshi;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static android.view.View.GONE;
import static android.widget.ImageView.ScaleType.FIT_CENTER;

public class SettingsActivity extends AppCompatActivity {
    public static SettingsActivity self;
    ListView listView;
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    static ConnectivityManager manager;
    static boolean mobile;
    static boolean wifi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        self=this;
        //запретили смену ориентации для главной активности
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //виджеты и строим список
        Button btn = findViewById(R.id.add_valute_btn);
        listView = findViewById(R.id.setting_list);
        update_adapter();

        //реклама в проверке на интернет
        manager = (ConnectivityManager)getSystemService(MainActivity.self.CONNECTIVITY_SERVICE);
        AdView mAdView = findViewById(R.id.adSecondView);
        mobile = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnected();
        wifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnected();
        if (!mobile && !wifi) {
            //инета нет
            mAdView.setVisibility(GONE);
        } else {
            //инет есть, грузим рекламу
            MobileAds.initialize(this, "ca-app-pub-1926638597037129~5070311083");
            AdRequest adRequest = new AdRequest.Builder().build();
            mAdView.loadAd(adRequest);
        }

        //----------
        //ИТЕМ-КЛИК, РЕДАКТИРОВАНИЕ ВАЛЮТЫ
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
                AlertDialog.Builder dialog = new AlertDialog.Builder (self);
                //тайтл и содержимые виджеты
                dialog.setTitle(getResources().getString(R.string.valute_snackbar_txt)+MainActivity.self.valutes.get(i).code);
                LayoutInflater inflater = getLayoutInflater();
                final LinearLayout layout = findViewById(R.id.dialog_settings_layout);
                final View dialogview = inflater.inflate(R.layout.dialog_settings, layout);
                //спойлеры
                LinearLayout spoiler1 = dialogview.findViewById(R.id.dialog_settings_spoiler_1);
                LinearLayout spoiler2 = dialogview.findViewById(R.id.dialog_settings_spoiler_2);
                LinearLayout spoiler3 = dialogview.findViewById(R.id.dialog_settings_spoiler_3);
                //кнопки "сохранить","в начало списка", "в конец списка"
                Button btn1 = dialogview.findViewById(R.id.dialog_settings_first_btn);
                Button btn3 = dialogview.findViewById(R.id.dialog_settings_up_btn);
                Button btn4 = dialogview.findViewById(R.id.dialog_settings_down_btn);
                //Эдиттексты первой строки (нужны здесь для кнопки "сохранить")
                final EditText et1 = dialogview.findViewById(R.id.dialog_settings_input_code);
                final EditText et2 = dialogview.findViewById(R.id.dialog_settings_input_value);
                final Spinner valuteSpinner = dialogview.findViewById(R.id.dialog_settings_spinner);
                //кнопочка для смены пикч
                ImageButton ib = dialogview.findViewById(R.id.dialog_settings_img_btn);
                ib.setImageResource(MainActivity.self.valutes.get(i).image);

                //для дефолтной (базовой из списка) валюты скрываем спойлер с ET-тами //спойлер 2 (сохранить, сброс) по умолчанию gone
                if (MainActivity.self.valutes.get(i).visible==1){
                    spoiler1.setVisibility(GONE);
                    //пишем дату последнего обновления
                    final TextView tv = dialogview.findViewById(R.id.dialog_settings_last_update);
                    Date date=new Date(Long.valueOf(MainActivity.self.valutes.get(i).upDate));
                    tv.setText(getResources().getString(R.string.last_update)+dateFormat.format(date));

//ДОРАБОТАТЬ!!!!!!!!!! //диалог для кастомной (которую сами вносили) валюты активируем ET-ы (их нужно вынести в асинктаск наверн, чтоб не зависало на слабых устройствах).
                    //тут вообще куча проблем с оптимизацией должно быть
                }else{
                    //спойлер 1 с ЕТ уже визибл по умолчанию
                    spoiler2.setVisibility(View.VISIBLE);
                    spoiler3.setVisibility(GONE);
                    //кнопка "сброс"

                    Button btn2 = dialogview.findViewById(R.id.dialog_settings_last_btn);

//ДОРАБОТАТЬ!!!!!!!!!!                    //на спиннер нужен блок для несовпадения валют?
                    SpinnerAdapter spinnerAdapter = new SpinnerAdapter(SettingsActivity.self, MainActivity.self.valutes);
                    valuteSpinner.setAdapter(spinnerAdapter);
                    valuteSpinner.setSelection(1);
                    et1.setText(MainActivity.self.valutes.get(i).code);
                    et1.setLongClickable(false);
                    et2.setLongClickable(false);
                    et1.setFilters(new InputFilter[] {new InputFilter.AllCaps(), new InputFilter.LengthFilter(6)});
                    et2.setFilters(new InputFilter[] {new InputFilter.LengthFilter(7)});
                    //далее идут обработчики кнопок первой строки - спиннера и двух кнопок "сохранить" и "сброс"
                    //спиннер валют
                    valuteSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int in, long l) {
                            et2.setText(String.valueOf(MainActivity.self.valutes.get(i).value/MainActivity.self.valutes.get(in).value));
                        }
                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {
                        }
                    });

                    //кнопка "Сброс" возвращает значения, которые были до редактирования ET-ов. Она не используется в дефолтных базовых валютах, потому находится здесь.
                    btn2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            et1.setText(MainActivity.self.valutes.get(i).code);
                            valuteSpinner.setSelection(1);
                            et2.setText(String.valueOf(MainActivity.self.valutes.get(i).value/MainActivity.self.valutes.get(1).value));
                        }
                    });
                }

                //Кнопка диалога "Удалить", показывается, если списов валют >3
                if (MainActivity.self.valutes.size()>3){
                    dialog.setNeutralButton(R.string.delete, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int deli) {
                            //главный диалог закрывается, открывается диалог "вы уверены?"
                            AlertDialog.Builder delBuilder = new AlertDialog.Builder(self);
                            delBuilder.setTitle(R.string.are_you_shure);
                            delBuilder.setMessage(R.string.del_text_part1+MainActivity.self.valutes.get(i).code+R.string.del_text_part2+"«"+R.string.add_valute_btn+"».")
                                    .setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            //метод смещения в конец перед удалением и само удаление
                                            setAbsoluteOrder(MainActivity.self.valutes.get(i).order, false);
                                            final DBConnection connection = new DBConnection(MainActivity.self);
                                            final SQLiteDatabase db=connection.getWritableDatabase();
                                            Toast.makeText(getApplicationContext(),MainActivity.self.valutes.get(i).code+getResources().getString(R.string.was_deleted),Toast.LENGTH_LONG).show();
                                            db.delete(DBConnection.VALUTE_TABLE_NAME,DBConnection.VALUTE_CODE +" = ?", new String[]{String.valueOf(MainActivity.self.valutes.get(i).code)});
                                            db.close();
                                            update_adapter();
                                        }
                                    })
                                    .setNeutralButton(R.string.cancel, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                        }
                                    });
                            delBuilder.show();
                        }
                    });
                }
                //Кнопка диалога "Закрыть"
                dialog.setPositiveButton(R.string.close, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //просто закрытие диалогового окна, все сохранения происходят в диалоге
                    }
                });

                //Сформировали окончательный вид диалога редактирования валюты
                dialog.setView(dialogview);
                final AlertDialog builder = dialog.create();
                builder.show();
                //дальше пойдут обработчики нажатий на кнопочки диалога

                //Кнопка "Сохранить"
                btn1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (et1.getText().length()==0||et2.getText().length()==0){
                            Toast.makeText(getApplicationContext(),getResources().getString(R.string.input_err),Toast.LENGTH_SHORT).show();
                        }else{
                            if (et2.getText().toString().substring(0,1).contains(".")){
                                Toast.makeText(getApplicationContext(),getResources().getString(R.string.input_err),Toast.LENGTH_SHORT).show();
                            }else{
//может возникнуть проблема повторения валюты (нет блока при редактировании), но важно ли это?
                                final DBConnection connection = new DBConnection(MainActivity.self);
                                final SQLiteDatabase db=connection.getWritableDatabase();
                                final ContentValues cv =new ContentValues();
                                //обновление данных
                                cv.put(DBConnection.VALUTE_CODE,(et1.getText()).toString());
                                cv.put(DBConnection.VALUTE_VALUE,Float.valueOf(et2.getText().toString())*MainActivity.self.valutes.get(valuteSpinner.getSelectedItemPosition()).value);
                                cv.put(DBConnection.VALUTE_UP_DATE,new Date().getTime());
                                db.update(DBConnection.VALUTE_TABLE_NAME,cv,DBConnection.VALUTE_ORDER +" = ?", new String[]{String.valueOf(MainActivity.self.valutes.get(i).order)});
                                //закрываем БД
                                db.close();
                                update_adapter();
                                //оповещение и закрытие всего диалога
                                Toast.makeText(getApplicationContext(),R.string.saved,Toast.LENGTH_LONG).show();
                                builder.dismiss();
                            }
                        }
                    }
                });
                //внопки абсолютного смещения по списку
                btn3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        setAbsoluteOrder(MainActivity.self.valutes.get(i).order, true);
                        update_adapter();
                        builder.dismiss();
                    }
                });
                btn4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        setAbsoluteOrder(MainActivity.self.valutes.get(i).order, false);
                        update_adapter();
                        builder.dismiss();
                    }
                });
                //кнопка выбора пикчи
                ib.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //дисмисс предыдущего. Записываем тут при кликах пикчу в основную, при кнопке "сохранить" перезаписываем в бд
                        final AlertDialog.Builder imgBuilder = new AlertDialog.Builder(self);
                        //переменная, в которую записываем выбор пользователя
                        final int[] choisenImg = new int[1];

                        //наш список с картинками
                        //можно вынести в класс валют или в корень активности
                        final ArrayList<Integer> reslist= new ArrayList<Integer>();
                        reslist.add(R.drawable.amd);
                        reslist.add(R.drawable.aud);
                        reslist.add(R.drawable.aud2);
                        reslist.add(R.drawable.azn);
                        reslist.add(R.drawable.bgn);
                        reslist.add(R.drawable.byn);
                        reslist.add(R.drawable.byn2);
                        reslist.add(R.drawable.gbp);
                        reslist.add(R.drawable.gbp2);
                        reslist.add(R.drawable.uah);
                        reslist.add(R.drawable.usd);

                        //динамически строим верстку
                        LayoutInflater inflater = getLayoutInflater();
                        final View dialogImgView = inflater.inflate(R.layout.dialog_img, layout);
                        //виджеты определяем, заполняем
                        TextView img_txt=dialogImgView.findViewById(R.id.dialog_img_txt);
                        final ImageView img_pic=dialogImgView.findViewById(R.id.dialog_img_pic);
                        choisenImg[0]=MainActivity.self.valutes.get(i).image;
                        img_txt.setText(MainActivity.self.valutes.get(i).code);
                        img_pic.setImageResource(choisenImg[0]);
                        //главный лайаут MP,MP
                        final LinearLayout layout = dialogImgView.findViewById(R.id.root);
                        //создаем строки
                        for (int iter = 0; iter < reslist.size();) {
                            LinearLayout lay=new LinearLayout(self);
                            lay.setOrientation(LinearLayout.HORIZONTAL);
                            lay.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                            for (int a = 0; a < 4 && iter < reslist.size(); iter++) {
                                //создание одного юнита и запихивание в строку
                                LinearLayout itemLayout=new LinearLayout(self);
                                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                LinearLayout.LayoutParams imgParams = new LinearLayout.LayoutParams(140, 140);
                                itemLayout.setLayoutParams(params);
                                itemLayout.setOrientation(LinearLayout.HORIZONTAL);
                                ImageButton ib = new ImageButton(self);
                                ib.setImageResource(reslist.get(iter));
                                final int saveImg=iter;
                                ib.setScaleType(FIT_CENTER);
                                ib.setLayoutParams(imgParams);
                                //запихиваем кнопку в итем
                                itemLayout.addView(ib);
                                //обработчик нажатий на кнопки-картинки
                                ib.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        choisenImg[0] =reslist.get(saveImg);
                                        img_pic.setImageResource(choisenImg[0]);
                                    }
                                });
                                //запихиваем итем в строку
                                lay.addView(itemLayout);
                                a++;
                            }
                            //когда строка заканчивается, запихиваем строку в столбик
                            layout.addView(lay);
                        }
                        //заканчиваем строить диалог с картинками на выбор
                        imgBuilder.setView(dialogImgView);
                        imgBuilder.setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int in) {
                                //окно закрытия, //смена пикчи в DB, закрытие всех окон, обновление списка
                                final DBConnection update_connection = new DBConnection(MainActivity.self);
                                final SQLiteDatabase update_db=update_connection.getWritableDatabase();
                                final ContentValues cv =new ContentValues();
                                cv.put(DBConnection.VALUTE_IMAGE,choisenImg[0]);
                                update_db.update(DBConnection.VALUTE_TABLE_NAME,cv,DBConnection.VALUTE_ORDER +" = ? ", new String[]{String.valueOf(i+1)});
                                update_db.close();
                                update_adapter();
                                builder.dismiss();
                            }
                        });
                        imgBuilder.setNeutralButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                            }
                        });
                        imgBuilder.show();
                    }
                });
            }
        });


        //----------
        //ДОБАВЛЕНИЕ НОВОЙ ВАЛЮТЫ
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //лайаут с диалогом
                AlertDialog.Builder dialog = new AlertDialog.Builder (self);
                dialog.setTitle(R.string.add_valute_btn);
                LayoutInflater inflater = getLayoutInflater();
                final LinearLayout layout = findViewById(R.id.dialog_settings_layout);
                final View dialogview = inflater.inflate(R.layout.dialog_settings, layout);
                //элементы
                final EditText et1=dialogview.findViewById(R.id.dialog_settings_input_code);
                final EditText et2=dialogview.findViewById(R.id.dialog_settings_input_value);
                et1.setLongClickable(false);
                et2.setLongClickable(false);
                et1.setFilters(new InputFilter[] {new InputFilter.AllCaps(), new InputFilter.LengthFilter(6)});
                et2.setFilters(new InputFilter[] {new InputFilter.LengthFilter(7)});
                Button btn1 = dialogview.findViewById(R.id.dialog_settings_first_btn);
                Button btn2 = dialogview.findViewById(R.id.dialog_settings_last_btn);
                final Spinner valuteSpinner=dialogview.findViewById(R.id.dialog_settings_spinner);
                final ImageButton ib = dialogview.findViewById(R.id.dialog_settings_img_btn);
                //spoiler2 по умолчанию gone
                LinearLayout spoiler2 = dialogview.findViewById(R.id.dialog_settings_spoiler_2);
                LinearLayout spoiler3 = dialogview.findViewById(R.id.dialog_settings_spoiler_3);
                LinearLayout spoiler4 = dialogview.findViewById(R.id.dialog_settings_spoiler_4);
                spoiler2.setVisibility(View.VISIBLE);
                spoiler3.setVisibility(GONE);
                spoiler4.setVisibility(GONE);
                btn1.setVisibility(GONE);

                //кнопка "сброс" эдитов первой строки
                btn2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        et1.setText("");
                        valuteSpinner.setSelection(1);
                        et2.setText("");
                    }
                });

                //переменная для хранения картинки до слушателя кнопки
                //Нужна картинка дефолтная для любой вносимой валюты - вопросительный знак [?]
                final int[] choisenImg = new int[1];
                choisenImg[0]=R.drawable.usd;

                //смена картинок
                ib.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //код
                        final AlertDialog.Builder imgBuilder = new AlertDialog.Builder(self);

                        //наш список с картинками вынести куда-то, может в класс валют
                        final ArrayList<Integer> reslist= new ArrayList<Integer>();
                        reslist.add(R.drawable.amd);
                        reslist.add(R.drawable.aud);
                        reslist.add(R.drawable.aud2);
                        reslist.add(R.drawable.azn);
                        reslist.add(R.drawable.bgn);
                        reslist.add(R.drawable.byn);
                        reslist.add(R.drawable.byn2);
                        reslist.add(R.drawable.gbp);
                        reslist.add(R.drawable.gbp2);
                        reslist.add(R.drawable.uah);
                        reslist.add(R.drawable.usd);

                        //динамически строим верстку
                        LayoutInflater inflater = getLayoutInflater();
                        final View dialogImgView = inflater.inflate(R.layout.dialog_img, layout);
                        //виджеты определяем, заполняем
                        TextView img_txt=dialogImgView.findViewById(R.id.dialog_img_txt);
                        final ImageView img_pic=dialogImgView.findViewById(R.id.dialog_img_pic);

                        img_txt.setVisibility(GONE);
                        img_pic.setVisibility(GONE);
                        //главный лайаут MP,MP
                        final LinearLayout layout = dialogImgView.findViewById(R.id.root);
                        //создаем строки
                        final AlertDialog dialog = imgBuilder.create();
                        for (int iter = 0; iter < reslist.size();) {
                            LinearLayout lay=new LinearLayout(self);
                            lay.setOrientation(LinearLayout.HORIZONTAL);
                            lay.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                            for (int a = 0; a < 4 && iter < reslist.size(); iter++) {
                                //создание одного юнита и запихивание в строку
                                LinearLayout itemLayout=new LinearLayout(self);
                                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                LinearLayout.LayoutParams imgParams = new LinearLayout.LayoutParams(140, 140);
                                itemLayout.setLayoutParams(params);
                                itemLayout.setOrientation(LinearLayout.HORIZONTAL);
                                final ImageButton iib = new ImageButton(self);
                                iib.setImageResource(reslist.get(iter));
                                final int saveImg=iter;
                                iib.setScaleType(FIT_CENTER);
                                iib.setLayoutParams(imgParams);
                                //запихиваем кнопку в итем
                                itemLayout.addView(iib);
                                //обработчик нажатий на кнопки-картинки
                                iib.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        //одно нажатие, возвращаемся в предыдущий диалог, пикча в переменной
                                        choisenImg[0] =reslist.get(saveImg);
                                        ib.setImageResource(choisenImg[0]);
                                        dialog.dismiss();
                                    }
                                });
                                //запихиваем итем в строку
                                lay.addView(itemLayout);
                                a++;
                            }
                            //когда строка заканчивается, запихиваем строку в столбик
                            layout.addView(lay);
                        }
                        //заканчиваем строить диалог с картинками на выбор
                        dialog.setView(dialogImgView);
                        dialog.show();
                    }
                });

                final SpinnerAdapter spinnerAdapter = new SpinnerAdapter(SettingsActivity.self, MainActivity.self.valutes);
                valuteSpinner.setAdapter(spinnerAdapter);
                valuteSpinner.setSelection(1);

                dialog.setNeutralButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                }).setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //итератор, нет ли такой валюты в списке
                        boolean nextStep=true;
                        for(int a=0;a<MainActivity.self.valutes.size();a+=1) {
                            //чек существующих валют на схожее имя
                            if (et1.getText().toString().equals(MainActivity.self.valutes.get(a).code)) {
                                Toast.makeText(getApplicationContext(), R.string.double_err, Toast.LENGTH_LONG).show();
                                nextStep = false;
                            }
                        }
                        if (nextStep==true){
                            //пройдено, дальше валидация
                            if (et1.getText().length()==0||et2.getText().length()==0){
                                Toast.makeText(getApplicationContext(),getResources().getString(R.string.input_err),Toast.LENGTH_LONG).show();
                            }else{
                                if (et2.getText().toString().substring(0,1).contains(".")){
                                    Toast.makeText(getApplicationContext(),getResources().getString(R.string.input_err),Toast.LENGTH_LONG).show();
                                }else{
                                    //пройдено всё, подтягиваем ET-ы и сохраняем в БД что получилось
                                    final DBConnection connection = new DBConnection(MainActivity.self);
                                    final SQLiteDatabase db=connection.getWritableDatabase();
                                    final ContentValues cv =new ContentValues();
                                    cv.put(DBConnection.VALUTE_CODE,(et1.getText()).toString());
                                    cv.put(DBConnection.VALUTE_NAME,"");
                                    cv.put(DBConnection.VALUTE_COUNTRY,"");
                                    //тут идет пересчет в рубли в зависимости от выбранной страны
                                    cv.put(DBConnection.VALUTE_VALUE,Float.valueOf(et2.getText().toString())*MainActivity.self.valutes.get(valuteSpinner.getSelectedItemPosition()).value);
                                    cv.put(DBConnection.VALUTE_IMAGE,choisenImg[0]);
                                    cv.put(DBConnection.VALUTE_ORDER,MainActivity.self.valutes.size()+1);
                                    cv.put(DBConnection.VALUTE_UP_DATE,new Date().getTime());
                                    cv.put(DBConnection.VALUTE_VISIBLE,0);
                                    db.insert(DBConnection.VALUTE_TABLE_NAME,null,cv);
                                    db.close();

                                    setAbsoluteOrder(MainActivity.self.valutes.size()+1,true);
                                    update_adapter();
                                    //оповещение и закрытие всего диалога
                                    Toast.makeText(getApplicationContext(),R.string.saved,Toast.LENGTH_LONG).show();
                                }
                            }
                        }
                    }
                });

                dialog.setView(dialogview);
                dialog.create().show();
            }
        });
        //<<<OnCreate END>>>
    }

    //метод получения обновленного списка и построения страницы заново
    public static void update_adapter(){
        MainActivity.self.valutes.clear();
        MainActivity.getList();

        ValuteAdapter adapter=new ValuteAdapter(SettingsActivity.self,MainActivity.self.valutes);
        SettingsActivity.self.listView.setAdapter(adapter);
    }

    public static  void setOrder(int order, boolean whichBtn){
        //true- вверх, false- вниз
        if (whichBtn==true){
            if (order>1){
                final DBConnection update_connection = new DBConnection(MainActivity.self);
                final SQLiteDatabase update_db=update_connection.getWritableDatabase();
                final ContentValues cvOrder =new ContentValues();
                //смещаемое число загоняем в буфер (на позицию 0)
                cvOrder.put(DBConnection.VALUTE_ORDER,0);
                update_db.update(DBConnection.VALUTE_TABLE_NAME,cvOrder,DBConnection.VALUTE_ORDER +" = ? ", new String[]{String.valueOf(order-1)});
                //целевое число двигаем на 1 позицию вверх
                cvOrder.put(DBConnection.VALUTE_ORDER,order-1);
                update_db.update(DBConnection.VALUTE_TABLE_NAME,cvOrder,DBConnection.VALUTE_ORDER +" = ?", new String[]{String.valueOf(order)});
                //возвращаем смещаемое число на первоначальную позицию
                cvOrder.put(DBConnection.VALUTE_ORDER,order);
                update_db.update(DBConnection.VALUTE_TABLE_NAME,cvOrder,DBConnection.VALUTE_ORDER +" = ?", new String[]{String.valueOf(0)});
                update_db.close();
            }
        }else{
            if (order<MainActivity.self.valutes.size()){
                final DBConnection update_connection = new DBConnection(MainActivity.self);
                final SQLiteDatabase update_db=update_connection.getWritableDatabase();
                final ContentValues cvOrder =new ContentValues();
                //смещаемое число загоняем в буфер (на позицию 0)
                cvOrder.put(DBConnection.VALUTE_ORDER,0);
                update_db.update(DBConnection.VALUTE_TABLE_NAME,cvOrder,DBConnection.VALUTE_ORDER +" = ?", new String[]{String.valueOf(order+1)});
                //целевое число двигаем на 1 позицию вниз
                cvOrder.put(DBConnection.VALUTE_ORDER,order+1);
                update_db.update(DBConnection.VALUTE_TABLE_NAME,cvOrder,DBConnection.VALUTE_ORDER +" = ?", new String[]{String.valueOf(order)});
                //возвращаем смещаемое число на первоначальную позицию
                cvOrder.put(DBConnection.VALUTE_ORDER,order);
                update_db.update(DBConnection.VALUTE_TABLE_NAME,cvOrder,DBConnection.VALUTE_ORDER +" = ?", new String[]{String.valueOf(0)});
                update_db.close();
            }
        }
    }

    public static void setAbsoluteOrder(int order, boolean whichBtn){
        //true- вверх, false- вниз
        if (whichBtn==true){
            if (order>1){
                final DBConnection update_connection = new DBConnection(MainActivity.self);
                final SQLiteDatabase update_db=update_connection.getWritableDatabase();
                final ContentValues cvOrder =new ContentValues();
                //смещаемое число загоняем в буфер (на позицию 0)
                cvOrder.put(DBConnection.VALUTE_ORDER,0);
                update_db.update(DBConnection.VALUTE_TABLE_NAME,cvOrder,DBConnection.VALUTE_ORDER +" = ? ", new String[]{String.valueOf(order)});
                //предыдущие нашему значению массив строк сдвигаем на 1 позицию вниз (+1)
                for (int c=order; c!=0; c--){
                    cvOrder.put(DBConnection.VALUTE_ORDER,c+1);
                    update_db.update(DBConnection.VALUTE_TABLE_NAME,cvOrder,DBConnection.VALUTE_ORDER +" = ?", new String[]{String.valueOf(c)});
                }
                //позицию с 0 буферного места смещаем на позицию 1
                cvOrder.put(DBConnection.VALUTE_ORDER,1);
                update_db.update(DBConnection.VALUTE_TABLE_NAME,cvOrder,DBConnection.VALUTE_ORDER +" = ?", new String[]{String.valueOf(0)});
                update_db.close();
            }
        }else{
            if (order<MainActivity.self.valutes.size()){
                final DBConnection update_connection = new DBConnection(MainActivity.self);
                final SQLiteDatabase update_db=update_connection.getWritableDatabase();
                final ContentValues cvOrder =new ContentValues();
                //смещаемое число загоняем в буфер (на позицию size+1)
                cvOrder.put(DBConnection.VALUTE_ORDER,MainActivity.self.valutes.size()+1);
                update_db.update(DBConnection.VALUTE_TABLE_NAME,cvOrder,DBConnection.VALUTE_ORDER +" = ? ", new String[]{String.valueOf(order)});
                //массив строк от order+1 до текущего положения смещаем на позицию вниз
                for (int c=order; c<MainActivity.self.valutes.size()+1; c++){
                    cvOrder.put(DBConnection.VALUTE_ORDER,c-1);
                    update_db.update(DBConnection.VALUTE_TABLE_NAME,cvOrder,DBConnection.VALUTE_ORDER +" = ?", new String[]{String.valueOf(c)});
                }
                cvOrder.put(DBConnection.VALUTE_ORDER,MainActivity.self.valutes.size());
                update_db.update(DBConnection.VALUTE_TABLE_NAME,cvOrder,DBConnection.VALUTE_ORDER +" = ? ", new String[]{String.valueOf(MainActivity.self.valutes.size()+1)});
                update_db.close();
            }
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.setClass(getApplicationContext(),MainActivity.class);
        startActivity(intent);
        finish();
    }
}