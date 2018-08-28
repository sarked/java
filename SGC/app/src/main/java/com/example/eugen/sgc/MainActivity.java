package com.example.eugen.sgc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    long inputValue;
    Random random;
    ArrayList list;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //элементы верстки
        final EditText editText=(EditText)findViewById(R.id.test_input);
        Button btn =(Button)findViewById(R.id.start_btn);
        Button dhdbtn =(Button)findViewById(R.id.dhd_button);
        final ListView listView =(ListView)findViewById(R.id.test_list);

        list = new ArrayList<>();
        //final ArrayAdapter <String> adapter = new ArrayAdapter<String>(this, R.layout.list_item ,list);
        //listView.setAdapter(adapter);
        final PlanetAdapter adapter = new PlanetAdapter(this, list);

        //активность с наборным устройством с ожиданием ответа
        dhdbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(),DhdActivity.class);
                //startActivity(intent);
                startActivityForResult(intent, 1);
            }
        });

        //тестовый генератор
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inputValue=Long.valueOf(String.valueOf(editText.getText()));

                //используем введенное зерно, получаем id планеты.
                random=new Random(inputValue);
                String randomValue=String.valueOf(Math.abs(random.nextInt()));
                //Toast.makeText(getApplicationContext(),"Ввод: "+inputValue+". Длина rand_id : "+randomValue.length(),Toast.LENGTH_LONG).show();
                System.out.println("Вводимый адрес "+inputValue+". Нарандомленный ID: "+randomValue);
                //блокер ошибок
                if(randomValue.length()>=9){
                    //если randomValue есть в базе - достаем, если нет - генерим новую планету следующим конструктором:
                    randomValue=randomValue.substring(randomValue.length()-9);
                    Planet a=new Planet(inputValue,randomValue);
                    list.add(a);
                    listView.setAdapter(adapter);
                    editText.setText("");
                }else{
                    Toast.makeText(getApplicationContext(),"Ошибка вводимого адреса",Toast.LENGTH_LONG).show();
                }
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext(),"открытие новой активности, подробности планеты",Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent){
        Bundle data=intent.getExtras();
        String txt = data.getString("key");
        editText=(EditText)findViewById(R.id.test_input);
        editText.setText(txt);
    }


}
