package com.example.eugen.sgc;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DhdActivity extends AppCompatActivity {

    int push=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dhd);

        final TextView input_txt = (TextView)findViewById(R.id.input_text);


        //массив: пикча-значение
        //массив с кнопками

        //проходимся по массиву с кнопками, чтобы раскидать значения, одну кнопку удаляем, добавляем домашнюю
        //механика удаления одной кнопки if (i==PlanetID) {i=+1} else (visible block)

        //glyph_iv
        ImageButton[] buttons_list = new ImageButton[38];
        buttons_list[0] = (ImageButton)findViewById(R.id.btn1);
        buttons_list[1] = (ImageButton)findViewById(R.id.btn2);
        buttons_list[2] = (ImageButton)findViewById(R.id.btn3);
        buttons_list[3] = (ImageButton)findViewById(R.id.btn4);
        buttons_list[4] = (ImageButton)findViewById(R.id.btn5);
        buttons_list[5] = (ImageButton)findViewById(R.id.btn6);
        buttons_list[6] = (ImageButton)findViewById(R.id.btn7);
        buttons_list[7] = (ImageButton)findViewById(R.id.btn8);
        buttons_list[8] = (ImageButton)findViewById(R.id.btn9);
        buttons_list[9] = (ImageButton)findViewById(R.id.btn10);
        buttons_list[10] = (ImageButton)findViewById(R.id.btn11);
        buttons_list[11] = (ImageButton)findViewById(R.id.btn12);
        buttons_list[12] = (ImageButton)findViewById(R.id.btn13);
        buttons_list[13] = (ImageButton)findViewById(R.id.btn14);
        buttons_list[14] = (ImageButton)findViewById(R.id.btn15);
        buttons_list[15] = (ImageButton)findViewById(R.id.btn16);
        buttons_list[16] = (ImageButton)findViewById(R.id.btn17);
        buttons_list[17] = (ImageButton)findViewById(R.id.btn18);
        buttons_list[18] = (ImageButton)findViewById(R.id.btn19);
        buttons_list[19] = (ImageButton)findViewById(R.id.btn20);
        buttons_list[20] = (ImageButton)findViewById(R.id.btn21);
        buttons_list[21] = (ImageButton)findViewById(R.id.btn22);
        buttons_list[22] = (ImageButton)findViewById(R.id.btn23);
        buttons_list[23] = (ImageButton)findViewById(R.id.btn24);
        buttons_list[24] = (ImageButton)findViewById(R.id.btn25);
        buttons_list[25] = (ImageButton)findViewById(R.id.btn26);
        buttons_list[26] = (ImageButton)findViewById(R.id.btn27);
        buttons_list[27] = (ImageButton)findViewById(R.id.btn28);
        buttons_list[28] = (ImageButton)findViewById(R.id.btn29);
        buttons_list[29] = (ImageButton)findViewById(R.id.btn30);
        buttons_list[30] = (ImageButton)findViewById(R.id.btn31);
        buttons_list[31] = (ImageButton)findViewById(R.id.btn32);
        buttons_list[32] = (ImageButton)findViewById(R.id.btn33);
        buttons_list[33] = (ImageButton)findViewById(R.id.btn34);
        buttons_list[34] = (ImageButton)findViewById(R.id.btn35);
        buttons_list[35] = (ImageButton)findViewById(R.id.btn36);
        buttons_list[36] = (ImageButton)findViewById(R.id.btn37);
        buttons_list[37] = (ImageButton)findViewById(R.id.btn38);

        Map <Integer, Integer> glyphs = new HashMap<Integer, Integer>();
        glyphs.put(38,R.drawable.symbol_01);
        glyphs.put(37,R.drawable.symbol_38);
        glyphs.put(36,R.drawable.symbol_37);
        glyphs.put(35,R.drawable.symbol_36);
        glyphs.put(34,R.drawable.symbol_35);
        glyphs.put(33,R.drawable.symbol_34);
        glyphs.put(32,R.drawable.symbol_33);
        glyphs.put(31,R.drawable.symbol_32);
        glyphs.put(30,R.drawable.symbol_31);
        glyphs.put(29,R.drawable.symbol_30);
        glyphs.put(28,R.drawable.symbol_29);
        glyphs.put(27,R.drawable.symbol_28);
        glyphs.put(26,R.drawable.symbol_27);
        glyphs.put(25,R.drawable.symbol_26);
        glyphs.put(24,R.drawable.symbol_25);
        glyphs.put(23,R.drawable.symbol_24);
        glyphs.put(22,R.drawable.symbol_23);
        glyphs.put(21,R.drawable.symbol_22);
        glyphs.put(20,R.drawable.symbol_21);
        glyphs.put(19,R.drawable.symbol_20);
        glyphs.put(18,R.drawable.symbol_19);
        glyphs.put(17,R.drawable.symbol_18);
        glyphs.put(16,R.drawable.symbol_17);
        glyphs.put(15,R.drawable.symbol_16);
        glyphs.put(14,R.drawable.symbol_15);
        glyphs.put(13,R.drawable.symbol_14);
        glyphs.put(12,R.drawable.symbol_13);
        glyphs.put(11,R.drawable.symbol_12);
        glyphs.put(10,R.drawable.symbol_11);
        glyphs.put(9,R.drawable.symbol_10);
        glyphs.put(8,R.drawable.symbol_09);
        glyphs.put(7,R.drawable.symbol_08);
        glyphs.put(6,R.drawable.symbol_07);
        glyphs.put(5,R.drawable.symbol_06);
        glyphs.put(4,R.drawable.symbol_05);
        glyphs.put(3,R.drawable.symbol_04);
        glyphs.put(2,R.drawable.symbol_03);
        glyphs.put(1,R.drawable.symbol_02);
        glyphs.put(0,R.drawable.symbol_39);

        int i=0;
        while (i<buttons_list.length){
            buttons_list[i].setImageResource(glyphs.get(i));

            //запись глифа в виде уникального двухзначного символа
            final int glyph_value = i+1;
            String str_glyph_value = String.valueOf(glyph_value);
            if (glyph_value<10){
                str_glyph_value="0"+str_glyph_value;
            }
            final String finalStr_glyph_value = str_glyph_value;
            buttons_list[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    view.setEnabled(false);
                    view.animate().alpha(0.3f).setDuration(350);
                    input_txt.setText(input_txt.getText()+""+ finalStr_glyph_value);


                    //после шестого глифа закрывает активность и отправляет результат в главную.
                    push+=1;
                    if (push==6){
                        Intent intent = new Intent();
                        intent.putExtra("key",input_txt.getText().toString());
                        setResult(0,intent);
                        finish();
                    }

                }
            });

            i+=1;
        }




    }

}
