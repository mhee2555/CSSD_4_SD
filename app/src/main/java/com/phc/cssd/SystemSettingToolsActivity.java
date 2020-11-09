package com.phc.cssd;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.phc.core.connect.HTTPConnect;
import com.phc.core.string.Cons;
import com.phc.cssd.url.Url;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SystemSettingToolsActivity extends AppCompatActivity {

    ImageView imageBack;
    LinearLayout Li1,Li2;

    //-------------------------------------
    // Type1
    //-------------------------------------
    LinearLayout L1,L2,L3,L4,L5,L6,L7,L8;
    TextView no1,no2,no3,no4,no5,no6,no7,no8;
    TextView text1,text2,text3,text4,text5,text6,text7,text8;
    TextView text1_1,text2_2,text3_3,text4_4,text5_5,text6_6,text7_7,text8_8;
    Button button_system_flase,button_system_true,button_user_true,button_user_flase;
    Switch sw1,sw2,sw3,sw4,sw5,sw6,sw7,sw8;
    //-------------------------------------
    // Type2
    //-------------------------------------
    TextView text1__1,text2_1,text3_1,text4_1,text5_1,text6_1,text7_1,text8_1,text9_1,text10_1,text11_1,text12_1,text13_1,text14_1,text15_1;
    CheckBox ch1_1,ch1_2,ch1_3;
    CheckBox ch2_1,ch2_2,ch2_3;
    CheckBox ch3_1,ch3_2,ch3_3;
    CheckBox ch4_1,ch4_2,ch4_3;
    CheckBox ch5_1,ch5_2,ch5_3;
    CheckBox ch6_1,ch6_2,ch6_3;
    CheckBox ch7_1,ch7_2,ch7_3;
    CheckBox ch8_1,ch8_2,ch8_3;
    CheckBox ch9_1,ch9_2,ch9_3;
    CheckBox ch10_1,ch10_2,ch10_3;
    CheckBox ch11_1,ch11_2,ch11_3;
    CheckBox ch12_1,ch12_2,ch12_3;
    CheckBox ch13_1,ch13_2,ch13_3;
    CheckBox ch14_1,ch14_2,ch14_3;
    CheckBox ch15_1,ch15_2,ch15_3;


    private JSONArray rs = null;
    private HTTPConnect httpConnect = new HTTPConnect();
    private String TAG_RESULTS = "result";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system_setting_tools);
        getSupportActionBar().hide();
        initialize();
        PutDataCheck1();
        PutDataCheck2();
        PutDataCheck3();
        PutDataCheck4();
        PutDataCheck5();
        PutDataCheck6();
        PutDataCheck7();
        PutDataCheck8();
    }

    public void initialize() {
        text1__1 = (TextView) findViewById(R.id.text1__1);
        text2_1 = (TextView) findViewById(R.id.text2_1);
        text3_1 = (TextView) findViewById(R.id.text3_1);
        text4_1 = (TextView) findViewById(R.id.text4_1);
        text5_1 = (TextView) findViewById(R.id.text5_1);
        text6_1 = (TextView) findViewById(R.id.text6_1);
        text7_1 = (TextView) findViewById(R.id.text7_1);
        text8_1 = (TextView) findViewById(R.id.text8_1);
        text9_1 = (TextView) findViewById(R.id.text9_1);
        text10_1 = (TextView) findViewById(R.id.text10_1);
        text11_1 = (TextView) findViewById(R.id.text11_1);
        text12_1 = (TextView) findViewById(R.id.text12_1);
        text13_1 = (TextView) findViewById(R.id.text13_1);
        text14_1 = (TextView) findViewById(R.id.text14_1);
        text15_1 = (TextView) findViewById(R.id.text15_1);

        ch1_1 = (CheckBox) findViewById(R.id.ch1_1);
        ch1_2 = (CheckBox) findViewById(R.id.ch1_2);
        ch1_3 = (CheckBox) findViewById(R.id.ch1_3);

        ch2_1 = (CheckBox) findViewById(R.id.ch2_1);
        ch2_2 = (CheckBox) findViewById(R.id.ch2_2);
        ch2_3 = (CheckBox) findViewById(R.id.ch2_3);

        ch3_1 = (CheckBox) findViewById(R.id.ch3_1);
        ch3_2 = (CheckBox) findViewById(R.id.ch3_2);
        ch3_3 = (CheckBox) findViewById(R.id.ch3_3);

        ch4_1 = (CheckBox) findViewById(R.id.ch4_1);
        ch4_2 = (CheckBox) findViewById(R.id.ch4_2);
        ch4_3 = (CheckBox) findViewById(R.id.ch4_3);

        ch5_1 = (CheckBox) findViewById(R.id.ch5_1);
        ch5_2 = (CheckBox) findViewById(R.id.ch5_2);
        ch5_3 = (CheckBox) findViewById(R.id.ch5_3);

        ch6_1 = (CheckBox) findViewById(R.id.ch6_1);
        ch6_2 = (CheckBox) findViewById(R.id.ch6_2);
        ch6_3 = (CheckBox) findViewById(R.id.ch6_3);

        ch7_1 = (CheckBox) findViewById(R.id.ch7_1);
        ch7_2 = (CheckBox) findViewById(R.id.ch7_2);
        ch7_3 = (CheckBox) findViewById(R.id.ch7_3);

        ch8_1 = (CheckBox) findViewById(R.id.ch8_1);
        ch8_2 = (CheckBox) findViewById(R.id.ch8_2);
        ch8_3 = (CheckBox) findViewById(R.id.ch8_3);

        ch8_1 = (CheckBox) findViewById(R.id.ch8_1);
        ch8_2 = (CheckBox) findViewById(R.id.ch8_2);
        ch8_3 = (CheckBox) findViewById(R.id.ch8_3);

        ch9_1 = (CheckBox) findViewById(R.id.ch9_1);
        ch9_2 = (CheckBox) findViewById(R.id.ch9_2);
        ch9_3 = (CheckBox) findViewById(R.id.ch9_3);

        ch10_1 = (CheckBox) findViewById(R.id.ch10_1);
        ch10_2 = (CheckBox) findViewById(R.id.ch10_2);
        ch10_3 = (CheckBox) findViewById(R.id.ch10_3);

        ch11_1 = (CheckBox) findViewById(R.id.ch11_1);
        ch11_2 = (CheckBox) findViewById(R.id.ch11_2);
        ch11_3 = (CheckBox) findViewById(R.id.ch11_3);

        ch12_1 = (CheckBox) findViewById(R.id.ch12_1);
        ch12_2 = (CheckBox) findViewById(R.id.ch12_2);
        ch12_3 = (CheckBox) findViewById(R.id.ch12_3);

        ch13_1 = (CheckBox) findViewById(R.id.ch13_1);
        ch13_2 = (CheckBox) findViewById(R.id.ch13_2);
        ch13_3 = (CheckBox) findViewById(R.id.ch13_3);

        ch14_1 = (CheckBox) findViewById(R.id.ch14_1);
        ch14_2 = (CheckBox) findViewById(R.id.ch14_2);
        ch14_3 = (CheckBox) findViewById(R.id.ch14_3);

        ch15_1 = (CheckBox) findViewById(R.id.ch15_1);
        ch15_2 = (CheckBox) findViewById(R.id.ch15_2);
        ch15_3 = (CheckBox) findViewById(R.id.ch15_3);

        ch1_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ch1_1.isChecked()){
                    setbooleanUser("4","1");
                }else{
                    setbooleanUser("4","0");
                }
            }
        });

        ch2_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ch2_1.isChecked()){
                    setbooleanUser("5","1");
                }else{
                    setbooleanUser("5","0");
                }
            }
        });

        ch3_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ch3_1.isChecked()){
                    setbooleanUser("6","1");
                }else{
                    setbooleanUser("6","0");
                }
            }
        });

        ch4_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ch4_1.isChecked()){
                    setbooleanUser("7","1");
                }else{
                    setbooleanUser("7","0");
                }
            }
        });

        ch5_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ch5_1.isChecked()){
                    setbooleanUser("8","1");
                }else{
                    setbooleanUser("8","0");
                }
            }
        });

        ch6_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ch6_1.isChecked()){
                    setbooleanUser("9","1");
                }else{
                    setbooleanUser("9","0");
                }
            }
        });

        ch7_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ch7_1.isChecked()){
                    setbooleanUser("10","1");
                }else{
                    setbooleanUser("10","0");
                }
            }
        });

        ch8_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ch8_1.isChecked()){
                    setbooleanUser("11","1");
                }else{
                    setbooleanUser("11","0");
                }
            }
        });

        ch9_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ch9_1.isChecked()){
                    setbooleanUser("12","1");
                }else{
                    setbooleanUser("12","0");
                }
            }
        });

        ch10_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ch10_1.isChecked()){
                    setbooleanUser("13","1");
                }else{
                    setbooleanUser("13","0");
                }
            }
        });

        ch11_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ch11_1.isChecked()){
                    setbooleanUser("14","1");
                }else{
                    setbooleanUser("14","0");
                }
            }
        });

        ch12_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ch12_1.isChecked()){
                    setbooleanUser("15","1");
                }else{
                    setbooleanUser("15","0");
                }
            }
        });

        ch13_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ch13_1.isChecked()){
                    setbooleanUser("16","1");
                }else{
                    setbooleanUser("16","0");
                }
            }
        });

        ch14_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ch14_1.isChecked()){
                    setbooleanUser("17","1");
                }else{
                    setbooleanUser("17","0");
                }
            }
        });

        ch15_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ch15_1.isChecked()){
                    setbooleanUser("18","1");
                }else{
                    setbooleanUser("18","0");
                }
            }
        });

        ch1_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ch1_2.isChecked()){
                    setbooleanInCharg("4","1");
                }else{
                    setbooleanInCharg("4","0");
                }
            }
        });

        ch2_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ch2_2.isChecked()){
                    setbooleanInCharg("5","1");
                }else{
                    setbooleanInCharg("5","0");
                }
            }
        });

        ch3_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ch3_2.isChecked()){
                    setbooleanInCharg("6","1");
                }else{
                    setbooleanInCharg("6","0");
                }
            }
        });

        ch4_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ch4_2.isChecked()){
                    setbooleanInCharg("7","1");
                }else{
                    setbooleanInCharg("7","0");
                }
            }
        });

        ch5_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ch5_2.isChecked()){
                    setbooleanInCharg("8","1");
                }else{
                    setbooleanInCharg("8","0");
                }
            }
        });

        ch6_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ch6_2.isChecked()){
                    setbooleanInCharg("9","1");
                }else{
                    setbooleanInCharg("9","0");
                }
            }
        });

        ch7_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ch7_2.isChecked()){
                    setbooleanInCharg("10","1");
                }else{
                    setbooleanInCharg("10","0");
                }
            }
        });

        ch8_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ch8_2.isChecked()){
                    setbooleanInCharg("11","1");
                }else{
                    setbooleanInCharg("11","0");
                }
            }
        });

        ch9_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ch9_2.isChecked()){
                    setbooleanInCharg("12","1");
                }else{
                    setbooleanInCharg("12","0");
                }
            }
        });

        ch10_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ch10_2.isChecked()){
                    setbooleanInCharg("13","1");
                }else{
                    setbooleanInCharg("13","0");
                }
            }
        });

        ch11_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ch11_2.isChecked()){
                    setbooleanInCharg("14","1");
                }else{
                    setbooleanInCharg("14","0");
                }
            }
        });

        ch12_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ch12_2.isChecked()){
                    setbooleanInCharg("15","1");
                }else{
                    setbooleanInCharg("15","0");
                }
            }
        });

        ch13_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ch13_2.isChecked()){
                    setbooleanInCharg("16","1");
                }else{
                    setbooleanInCharg("16","0");
                }
            }
        });

        ch14_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ch14_2.isChecked()){
                    setbooleanInCharg("17","1");
                }else{
                    setbooleanInCharg("17","0");
                }
            }
        });

        ch15_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ch15_2.isChecked()){
                    setbooleanInCharg("18","1");
                }else{
                    setbooleanInCharg("18","0");
                }
            }
        });

        ch1_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ch1_3.isChecked()){
                    setbooleanAdmin("4","1");
                }else{
                    setbooleanAdmin("4","0");
                }
            }
        });

        ch2_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ch2_3.isChecked()){
                    setbooleanAdmin("5","1");
                }else{
                    setbooleanAdmin("5","0");
                }
            }
        });

        ch3_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ch3_3.isChecked()){
                    setbooleanAdmin("6","1");
                }else{
                    setbooleanAdmin("6","0");
                }
            }
        });

        ch4_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ch4_3.isChecked()){
                    setbooleanAdmin("7","1");
                }else{
                    setbooleanAdmin("7","0");
                }
            }
        });

        ch5_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ch5_3.isChecked()){
                    setbooleanAdmin("8","1");
                }else{
                    setbooleanAdmin("8","0");
                }
            }
        });

        ch6_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ch6_3.isChecked()){
                    setbooleanAdmin("9","1");
                }else{
                    setbooleanAdmin("9","0");
                }
            }
        });

        ch7_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ch7_3.isChecked()){
                    setbooleanAdmin("10","1");
                }else{
                    setbooleanAdmin("10","0");
                }
            }
        });

        ch8_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ch8_3.isChecked()){
                    setbooleanAdmin("11","1");
                }else{
                    setbooleanAdmin("11","0");
                }
            }
        });

        ch9_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ch9_3.isChecked()){
                    setbooleanAdmin("12","1");
                }else{
                    setbooleanAdmin("12","0");
                }
            }
        });

        ch10_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ch10_3.isChecked()){
                    setbooleanAdmin("13","1");
                }else{
                    setbooleanAdmin("13","0");
                }
            }
        });

        ch11_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ch11_3.isChecked()){
                    setbooleanAdmin("14","1");
                }else{
                    setbooleanAdmin("14","0");
                }
            }
        });

        ch12_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ch12_3.isChecked()){
                    setbooleanAdmin("15","1");
                }else{
                    setbooleanAdmin("15","0");
                }
            }
        });

        ch13_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ch13_3.isChecked()){
                    setbooleanAdmin("16","1");
                }else{
                    setbooleanAdmin("16","0");
                }
            }
        });

        ch14_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ch14_3.isChecked()){
                    setbooleanAdmin("17","1");
                }else{
                    setbooleanAdmin("17","0");
                }
            }
        });

        ch15_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ch15_3.isChecked()){
                    setbooleanAdmin("18","1");
                }else{
                    setbooleanAdmin("18","0");
                }
            }
        });

        imageBack = (ImageView) findViewById(R.id.imageBack);
        Li1 = (LinearLayout) findViewById(R.id.Li1);
        Li2 = (LinearLayout) findViewById(R.id.Li2);
        Li2.setVisibility(View.GONE);
        button_system_flase = (Button) findViewById(R.id.button_system_flase);
        button_system_true = (Button) findViewById(R.id.button_system_true);
        button_user_true = (Button) findViewById(R.id.button_user_true);
        button_user_flase = (Button) findViewById(R.id.button_user_flase);

        button_system_flase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (button_system_true.getVisibility() == View.GONE){
                    button_system_flase.setVisibility(View.GONE);
                    button_system_true.setVisibility(View.VISIBLE);
                    button_user_true.setVisibility(View.GONE);
                    button_user_flase.setVisibility(View.VISIBLE);
                    Li1.setVisibility(View.VISIBLE);
                    Li2.setVisibility(View.GONE);
                }
            }
        });

        button_user_flase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (button_user_flase.getVisibility() == View.VISIBLE){
                    button_user_flase.setVisibility(View.GONE);
                    button_user_true.setVisibility(View.VISIBLE);
                    button_system_true.setVisibility(View.GONE);
                    button_system_flase.setVisibility(View.VISIBLE);
                    Li1.setVisibility(View.GONE);
                    Li2.setVisibility(View.VISIBLE);
                    PutDataCheck1_1();
                    PutDataCheck2_1();
                    PutDataCheck3_1();
                    PutDataCheck4_1();
                    PutDataCheck5_1();
                    PutDataCheck6_1();
                    PutDataCheck7_1();
                    PutDataCheck8_1();
                    PutDataCheck9_1();
                    PutDataCheck10_1();
                    PutDataCheck11_1();
                    PutDataCheck12_1();
                    PutDataCheck13_1();
                    PutDataCheck14_1();
                    PutDataCheck15_1();
                }
            }
        });

        L1 = (LinearLayout) findViewById(R.id.L1);
        L2 = (LinearLayout) findViewById(R.id.L2);
        L3 = (LinearLayout) findViewById(R.id.L3);
        L4 = (LinearLayout) findViewById(R.id.L4);
        L5 = (LinearLayout) findViewById(R.id.L5);
        L6 = (LinearLayout) findViewById(R.id.L6);
        L7 = (LinearLayout) findViewById(R.id.L7);
        L8 = (LinearLayout) findViewById(R.id.L8);

        no1 = (TextView) findViewById(R.id.no1);
        no2 = (TextView) findViewById(R.id.no2);
        no3 = (TextView) findViewById(R.id.no3);
        no4 = (TextView) findViewById(R.id.no4);
        no5 = (TextView) findViewById(R.id.no5);
        no6 = (TextView) findViewById(R.id.no6);
        no7 = (TextView) findViewById(R.id.no7);
        no8 = (TextView) findViewById(R.id.no8);

        text1 = (TextView) findViewById(R.id.text1);
        text2 = (TextView) findViewById(R.id.text2);
        text3 = (TextView) findViewById(R.id.text3);
        text4 = (TextView) findViewById(R.id.text4);
        text5 = (TextView) findViewById(R.id.text5);
        text6 = (TextView) findViewById(R.id.text6);
        text7 = (TextView) findViewById(R.id.text7);
        text8 = (TextView) findViewById(R.id.text8);

        text1_1 = (TextView) findViewById(R.id.text1_1);
        text2_2 = (TextView) findViewById(R.id.text2_2);
        text3_3 = (TextView) findViewById(R.id.text3_3);
        text4_4 = (TextView) findViewById(R.id.text4_4);
        text5_5 = (TextView) findViewById(R.id.text5_5);
        text6_6 = (TextView) findViewById(R.id.text6_6);
        text7_7 = (TextView) findViewById(R.id.text7_7);
        text8_8 = (TextView) findViewById(R.id.text8_8);

        sw1 = (Switch) findViewById(R.id.sw1);
        sw1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sw1.isChecked()){
                    setboolean("1","1");
                }else{
                    setboolean("1","0");
                }
            }
        });
        sw2 = (Switch) findViewById(R.id.sw2);
        sw2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sw2.isChecked()){
                    setboolean("2","1");
                }else{
                    setboolean("2","0");
                }
            }
        });
        sw3 = (Switch) findViewById(R.id.sw3);
        sw3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sw3.isChecked()){
                    setboolean("3","1");
                }else{
                    setboolean("3","0");
                }
            }
        });
        sw4 = (Switch) findViewById(R.id.sw4);
        sw4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sw4.isChecked()){
                    setboolean("4","1");
                }else{
                    setboolean("4","0");
                }
            }
        });
        sw5 = (Switch) findViewById(R.id.sw5);
        sw5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sw5.isChecked()){
                    setboolean("5","1");
                }else{
                    setboolean("5","0");
                }
            }
        });
        sw6 = (Switch) findViewById(R.id.sw6);
        sw6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sw6.isChecked()){
                    setboolean("6","1");
                }else{
                    setboolean("6","0");
                }
            }
        });
        sw7 = (Switch) findViewById(R.id.sw7);
        sw7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sw7.isChecked()){
                    setboolean("7","1");
                }else{
                    setboolean("7","0");
                }
            }
        });
        sw8 = (Switch) findViewById(R.id.sw8);
        sw8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sw8.isChecked()){
                    setboolean("8","1");
                }else{
                    setboolean("8","0");
                }
            }
        });

        sw1.setVisibility(View.GONE);
        sw2.setVisibility(View.GONE);
        sw3.setVisibility(View.GONE);
        sw4.setVisibility(View.GONE);
        sw5.setVisibility(View.GONE);
        sw6.setVisibility(View.GONE);
        sw7.setVisibility(View.GONE);
        sw8.setVisibility(View.GONE);

        text1_1.setVisibility(View.GONE);
        text2_2.setVisibility(View.GONE);
        text3_3.setVisibility(View.GONE);
        text4_4.setVisibility(View.GONE);
        text5_5.setVisibility(View.GONE);
        text6_6.setVisibility(View.GONE);
        text7_7.setVisibility(View.GONE);
        text8_8.setVisibility(View.GONE);

        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void setboolean(final String type,final String Set){
        class setboolean extends AsyncTask<String, Void, String> {
            private ProgressDialog dialog = new ProgressDialog(SystemSettingToolsActivity.this);
            // variable
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                this.dialog.setMessage(Cons.WAIT_FOR_PROCESS);
                this.dialog.show();
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);

                try {
                    JSONObject jsonObj = new JSONObject(result);
                    rs = jsonObj.getJSONArray(TAG_RESULTS);
                    for(int i=0;i<rs.length();i++){
                        JSONObject c = rs.getJSONObject(i);
                        if(c.getString("finish").equals("true")){
                            Toast.makeText(SystemSettingToolsActivity.this, "บันทึกสำเร็จ", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(SystemSettingToolsActivity.this, "โหลดข้อมูลไม่สำเร็จ", Toast.LENGTH_SHORT).show();
                            finish();
                        }

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }finally {
                    if (dialog.isShowing()) {
                        dialog.dismiss();
                    }
                }
            }
            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("type", type);
                data.put("Set", Set);
                String result = null;
                try {
                    result = httpConnect.sendPostRequest(Url.URL + "setting_setsetting.php", data);
                    Log.d("FKJDHJKDH",data+"");
                    Log.d("FKJDHJKDH",result+"");
                }catch(Exception e){
                    e.printStackTrace();
                }
                return result;
            }
        }
        setboolean obj = new setboolean();
        obj.execute();
    }

    public void setbooleanUser(final String ID,final String Set){
        class setbooleanUser extends AsyncTask<String, Void, String> {
            private ProgressDialog dialog = new ProgressDialog(SystemSettingToolsActivity.this);
            // variable
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                this.dialog.setMessage(Cons.WAIT_FOR_PROCESS);
                this.dialog.show();
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);

                try {
                    JSONObject jsonObj = new JSONObject(result);
                    rs = jsonObj.getJSONArray(TAG_RESULTS);
                    for(int i=0;i<rs.length();i++){
                        JSONObject c = rs.getJSONObject(i);
                        if(c.getString("finish").equals("true")){
                            Toast.makeText(SystemSettingToolsActivity.this, "บันทึกสำเร็จ", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(SystemSettingToolsActivity.this, "โหลดข้อมูลไม่สำเร็จ", Toast.LENGTH_SHORT).show();
                            finish();
                        }

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }finally {
                    if (dialog.isShowing()) {
                        dialog.dismiss();
                    }
                }
            }
            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("ID", ID);
                data.put("Set", Set);
                String result = null;
                try {
                    result = httpConnect.sendPostRequest(Url.URL + "setting_setsetting2.php", data);
                    Log.d("FKJDHJKDH",data+"");
                    Log.d("FKJDHJKDH",result+"");
                }catch(Exception e){
                    e.printStackTrace();
                }
                return result;
            }
        }
        setbooleanUser obj = new setbooleanUser();
        obj.execute();
    }

    public void setbooleanInCharg(final String ID,final String Set){
        class setbooleanUser extends AsyncTask<String, Void, String> {
            private ProgressDialog dialog = new ProgressDialog(SystemSettingToolsActivity.this);
            // variable
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                this.dialog.setMessage(Cons.WAIT_FOR_PROCESS);
                this.dialog.show();
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);

                try {
                    JSONObject jsonObj = new JSONObject(result);
                    rs = jsonObj.getJSONArray(TAG_RESULTS);
                    for(int i=0;i<rs.length();i++){
                        JSONObject c = rs.getJSONObject(i);
                        if(c.getString("finish").equals("true")){
                            Toast.makeText(SystemSettingToolsActivity.this, "บันทึกสำเร็จ", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(SystemSettingToolsActivity.this, "โหลดข้อมูลไม่สำเร็จ", Toast.LENGTH_SHORT).show();
                            finish();
                        }

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }finally {
                    if (dialog.isShowing()) {
                        dialog.dismiss();
                    }
                }
            }
            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("ID", ID);
                data.put("Set", Set);
                String result = null;
                try {
                    result = httpConnect.sendPostRequest(Url.URL + "setting_setsetting3.php", data);
                    Log.d("FKJDHJKDH",data+"");
                    Log.d("FKJDHJKDH",result+"");
                }catch(Exception e){
                    e.printStackTrace();
                }
                return result;
            }
        }
        setbooleanUser obj = new setbooleanUser();
        obj.execute();
    }

    public void setbooleanAdmin(final String ID,final String Set){
        class setbooleanUser extends AsyncTask<String, Void, String> {
            private ProgressDialog dialog = new ProgressDialog(SystemSettingToolsActivity.this);
            // variable
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                this.dialog.setMessage(Cons.WAIT_FOR_PROCESS);
                this.dialog.show();
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);

                try {
                    JSONObject jsonObj = new JSONObject(result);
                    rs = jsonObj.getJSONArray(TAG_RESULTS);
                    for(int i=0;i<rs.length();i++){
                        JSONObject c = rs.getJSONObject(i);
                        if(c.getString("finish").equals("true")){
                            Toast.makeText(SystemSettingToolsActivity.this, "บันทึกสำเร็จ", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(SystemSettingToolsActivity.this, "โหลดข้อมูลไม่สำเร็จ", Toast.LENGTH_SHORT).show();
                            finish();
                        }

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }finally {
                    if (dialog.isShowing()) {
                        dialog.dismiss();
                    }
                }
            }
            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("ID", ID);
                data.put("Set", Set);
                String result = null;
                try {
                    result = httpConnect.sendPostRequest(Url.URL + "setting_setsetting4.php", data);
                    Log.d("FKJDHJKDH",data+"");
                    Log.d("FKJDHJKDH",result+"");
                }catch(Exception e){
                    e.printStackTrace();
                }
                return result;
            }
        }
        setbooleanUser obj = new setbooleanUser();
        obj.execute();
    }

    public void PutDataCheck1() {
        class PutDataCheck1 extends AsyncTask<String, Void, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);

                try {
                    JSONObject jsonObj = new JSONObject(result);
                    rs = jsonObj.getJSONArray(TAG_RESULTS);
                    for(int i=0;i<rs.length();i++) {
                        JSONObject c = rs.getJSONObject(i);
                        if (!c.getString("Name").equals("")){
                            no1.setText("1.");
                            text1_1.setVisibility(View.VISIBLE);
                            sw1.setVisibility(View.VISIBLE);
                            text1.setText(c.getString("Name"));
                            if (c.getString("IsStatus").equals("0")){
                                sw1.setChecked(false);
                            }else {
                                sw1.setChecked(true);
                            }
                        }else {
                            L1.setVisibility(View.GONE);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @SuppressLint("WrongThread")
            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("data","1");
                String result = null;
                try {
                    result = httpConnect.sendPostRequest(Url.URL + "cssd_display_config_type.php", data);
                    Log.d("FKJDHJKDH",data+"");
                    Log.d("FKJDHJKDH",result+"");
                }catch(Exception e){
                    e.printStackTrace();
                }
                return result;
            }
            // =========================================================================================
        }
        PutDataCheck1 obj = new PutDataCheck1();
        obj.execute();
    }

    public void PutDataCheck2() {
        class PutDataCheck2 extends AsyncTask<String, Void, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);

                try {
                    JSONObject jsonObj = new JSONObject(result);
                    rs = jsonObj.getJSONArray(TAG_RESULTS);
                    for(int i=0;i<rs.length();i++) {
                        JSONObject c = rs.getJSONObject(i);
                        if (!c.getString("Name").equals("")){
                            if (L1.getVisibility() == View.GONE){
                                no2.setText("1.");
                            }else {
                                no2.setText("2.");
                            }
                            text2_2.setVisibility(View.VISIBLE);
                            sw2.setVisibility(View.VISIBLE);
                            text2.setText(c.getString("Name"));
                            if (c.getString("IsStatus").equals("0")){
                                sw2.setChecked(false);
                            }else {
                                sw2.setChecked(true);
                            }
                        }else {
                            L2.setVisibility(View.GONE);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @SuppressLint("WrongThread")
            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("data","2");
                String result = null;
                try {
                    result = httpConnect.sendPostRequest(Url.URL + "cssd_display_config_type.php", data);
                    Log.d("FKJDHJKDH",data+"");
                    Log.d("FKJDHJKDH",result+"");
                }catch(Exception e){
                    e.printStackTrace();
                }
                return result;
            }
            // =========================================================================================
        }
        PutDataCheck2 obj = new PutDataCheck2();
        obj.execute();
    }

    public void PutDataCheck3() {
        class PutDataCheck3 extends AsyncTask<String, Void, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);

                try {
                    JSONObject jsonObj = new JSONObject(result);
                    rs = jsonObj.getJSONArray(TAG_RESULTS);
                    for(int i=0;i<rs.length();i++) {
                        JSONObject c = rs.getJSONObject(i);
                        if (!c.getString("Name").equals("")){
                            if (L1.getVisibility() == View.GONE){
                                if (L2.getVisibility() == View.GONE){
                                    no3.setText("1.");
                                }else {
                                    no3.setText("2.");
                                }
                            }else {
                                if (L2.getVisibility() == View.GONE){
                                    no3.setText("2.");
                                }else {
                                    no3.setText("3.");
                                }
                            }
                            text3_3.setVisibility(View.VISIBLE);
                            sw3.setVisibility(View.VISIBLE);
                            text3.setText(c.getString("Name"));
                            if (c.getString("IsStatus").equals("0")){
                                sw3.setChecked(false);
                            }else {
                                sw3.setChecked(true);
                            }
                        }else {
                            L3.setVisibility(View.GONE);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @SuppressLint("WrongThread")
            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("data","3");
                String result = null;
                try {
                    result = httpConnect.sendPostRequest(Url.URL + "cssd_display_config_type.php", data);
                    Log.d("FKJDHJKDH",data+"");
                    Log.d("FKJDHJKDH",result+"");
                }catch(Exception e){
                    e.printStackTrace();
                }
                return result;
            }
            // =========================================================================================
        }
        PutDataCheck3 obj = new PutDataCheck3();
        obj.execute();
    }

    public void PutDataCheck4() {
        class PutDataCheck4 extends AsyncTask<String, Void, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);

                try {
                    JSONObject jsonObj = new JSONObject(result);
                    rs = jsonObj.getJSONArray(TAG_RESULTS);
                    for(int i=0;i<rs.length();i++) {
                        JSONObject c = rs.getJSONObject(i);
                        if (!c.getString("Name").equals("")){
                            if (L1.getVisibility() == View.GONE){
                                if (L2.getVisibility() == View.GONE){
                                    if (L3.getVisibility() == View.GONE){
                                        no4.setText("1.");
                                    }else {
                                        no4.setText("2.");
                                    }
                                }else {
                                    if (L1.getVisibility() == View.GONE){
                                        no4.setText("2.");
                                    }else {
                                        no4.setText("3.");
                                    }
                                }
                            }else {
                                if (L2.getVisibility() == View.GONE){
                                    if (L3.getVisibility() == View.GONE){
                                        no4.setText("2.");
                                    }else {
                                        no4.setText("3.");
                                    }
                                }else {
                                    no4.setText("4.");
                                }
                            }
                            text4_4.setVisibility(View.VISIBLE);
                            sw4.setVisibility(View.VISIBLE);
                            text4.setText(c.getString("Name"));
                            if (c.getString("IsStatus").equals("0")){
                                sw4.setChecked(false);
                            }else {
                                sw4.setChecked(true);
                            }
                        }else {
                            L4.setVisibility(View.GONE);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @SuppressLint("WrongThread")
            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("data","4");
                String result = null;
                try {
                    result = httpConnect.sendPostRequest(Url.URL + "cssd_display_config_type.php", data);
                    Log.d("FKJDHJKDH",data+"");
                    Log.d("FKJDHJKDH",result+"");
                }catch(Exception e){
                    e.printStackTrace();
                }
                return result;
            }
            // =========================================================================================
        }
        PutDataCheck4 obj = new PutDataCheck4();
        obj.execute();
    }

    public void PutDataCheck5() {
        class PutDataCheck5 extends AsyncTask<String, Void, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);

                try {
                    JSONObject jsonObj = new JSONObject(result);
                    rs = jsonObj.getJSONArray(TAG_RESULTS);
                    for(int i=0;i<rs.length();i++) {
                        JSONObject c = rs.getJSONObject(i);
                        if (!c.getString("Name").equals("")){
                            if (L1.getVisibility() == View.GONE){
                                if (L2.getVisibility() == View.GONE){
                                    if (L3.getVisibility() == View.GONE){
                                        if (L4.getVisibility() == View.GONE){
                                            no5.setText("1.");
                                        }else {
                                            no5.setText("2.");
                                        }
                                    }else {
                                        no5.setText("3.");
                                    }
                                }else {
                                    no5.setText("4.");
                                }
                            }else {
                                if (L2.getVisibility() == View.GONE){
                                    if (L3.getVisibility() == View.GONE){
                                        if (L4.getVisibility() == View.GONE){
                                            no5.setText("2.");
                                        }else {
                                            no5.setText("3.");
                                        }
                                    }else {
                                        if (L4.getVisibility() == View.GONE){
                                            no5.setText("3.");
                                        }else {
                                            no5.setText("4.");
                                        }
                                    }
                                }else {
                                    if (L4.getVisibility() == View.GONE){
                                        no5.setText("4.");
                                    }else {
                                        no5.setText("5.");
                                    }
                                }
                            }
                            text5_5.setVisibility(View.VISIBLE);
                            sw5.setVisibility(View.VISIBLE);
                            text5.setText(c.getString("Name"));
                            if (c.getString("IsStatus").equals("0")){
                                sw5.setChecked(false);
                            }else {
                                sw5.setChecked(true);
                            }
                        }else {
                            L5.setVisibility(View.GONE);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @SuppressLint("WrongThread")
            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("data","5");
                String result = null;
                try {
                    result = httpConnect.sendPostRequest(Url.URL + "cssd_display_config_type.php", data);
                    Log.d("FKJDHJKDH",data+"");
                    Log.d("FKJDHJKDH",result+"");
                }catch(Exception e){
                    e.printStackTrace();
                }
                return result;
            }
            // =========================================================================================
        }
        PutDataCheck5 obj = new PutDataCheck5();
        obj.execute();
    }

    public void PutDataCheck6() {
        class PutDataCheck6 extends AsyncTask<String, Void, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);

                try {
                    JSONObject jsonObj = new JSONObject(result);
                    rs = jsonObj.getJSONArray(TAG_RESULTS);
                    for(int i=0;i<rs.length();i++) {
                        JSONObject c = rs.getJSONObject(i);
                        if (!c.getString("Name").equals("")){
                            if (L1.getVisibility() == View.GONE){
                                if (L2.getVisibility() == View.GONE){
                                    if (L3.getVisibility() == View.GONE){
                                        if (L4.getVisibility() == View.GONE){
                                            if (L5.getVisibility() == View.GONE){
                                                no6.setText("1.");
                                            }else {
                                                no6.setText("2.");
                                            }
                                        }else {
                                            no6.setText("3.");
                                        }
                                    }else {
                                        no6.setText("4.");
                                    }
                                }else {
                                    if (L5.getVisibility() == View.GONE){
                                        no6.setText("3.");
                                    }else {
                                        no6.setText("5.");
                                    }
                                }
                            }else {
                                if (L2.getVisibility() == View.GONE){
                                    if (L3.getVisibility() == View.GONE){
                                        if (L4.getVisibility() == View.GONE){
                                            if (L5.getVisibility() == View.GONE){
                                                no6.setText("2.");
                                            }else {
                                                no6.setText("3.");
                                            }
                                        }else {
                                            no6.setText("4.");
                                        }
                                    }else {
                                        no6.setText("5.");
                                    }
                                }else {
                                    no6.setText("6.");
                                }
                            }
                            text6_6.setVisibility(View.VISIBLE);
                            sw6.setVisibility(View.VISIBLE);
                            text6.setText(c.getString("Name"));
                            if (c.getString("IsStatus").equals("0")){
                                sw6.setChecked(false);
                            }else {
                                sw6.setChecked(true);
                            }
                        }else {
                            L6.setVisibility(View.GONE);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @SuppressLint("WrongThread")
            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("data","6");
                String result = null;
                try {
                    result = httpConnect.sendPostRequest(Url.URL + "cssd_display_config_type.php", data);
                    Log.d("FKJDHJKDH",data+"");
                    Log.d("FKJDHJKDH",result+"");
                }catch(Exception e){
                    e.printStackTrace();
                }
                return result;
            }
            // =========================================================================================
        }
        PutDataCheck6 obj = new PutDataCheck6();
        obj.execute();
    }

    public void PutDataCheck7() {
        class PutDataCheck7 extends AsyncTask<String, Void, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);

                try {
                    JSONObject jsonObj = new JSONObject(result);
                    rs = jsonObj.getJSONArray(TAG_RESULTS);
                    for(int i=0;i<rs.length();i++) {
                        JSONObject c = rs.getJSONObject(i);
                        if (!c.getString("Name").equals("")){
                            if (L1.getVisibility() == View.GONE){
                                if (L2.getVisibility() == View.GONE){
                                    if (L3.getVisibility() == View.GONE){
                                        if (L4.getVisibility() == View.GONE){
                                            if (L5.getVisibility() == View.GONE){
                                               if (L6.getVisibility() == View.GONE){
                                                   no7.setText("1.");
                                               }else {
                                                   no7.setText("2.");
                                               }
                                            }else {
                                                no7.setText("3.");
                                            }
                                        }else {
                                            no7.setText("4.");
                                        }
                                    }else {
                                        no7.setText("5.");
                                    }
                                }else {
                                    no7.setText("6.");
                                }
                            }else {
                                if (L2.getVisibility() == View.GONE){
                                    if (L3.getVisibility() == View.GONE){
                                        if (L4.getVisibility() == View.GONE){
                                            if (L5.getVisibility() == View.GONE){
                                               if (L6.getVisibility() == View.GONE){
                                                   no7.setText("2.");
                                               }else {
                                                   no7.setText("3.");
                                               }
                                            }else {
                                                no7.setText("4.");
                                            }
                                        }else {
                                            no7.setText("5.");
                                        }
                                    }else {
                                        if (L6.getVisibility() == View.GONE){
                                            no7.setText("4.");
                                        }else {
                                            no7.setText("6.");
                                        }
                                    }
                                }else {
                                    no7.setText("7.");
                                }
                            }
                            text7_7.setVisibility(View.VISIBLE);
                            sw7.setVisibility(View.VISIBLE);
                            text7.setText(c.getString("Name"));
                            if (c.getString("IsStatus").equals("0")){
                                sw7.setChecked(false);
                            }else {
                                sw7.setChecked(true);
                            }
                        }else {
                            L7.setVisibility(View.GONE);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @SuppressLint("WrongThread")
            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("data","7");
                String result = null;
                try {
                    result = httpConnect.sendPostRequest(Url.URL + "cssd_display_config_type.php", data);
                    Log.d("FKJDHJKDH",data+"");
                    Log.d("FKJDHJKDH",result+"");
                }catch(Exception e){
                    e.printStackTrace();
                }
                return result;
            }
            // =========================================================================================
        }
        PutDataCheck7 obj = new PutDataCheck7();
        obj.execute();
    }

    public void PutDataCheck8() {
        class PutDataCheck8 extends AsyncTask<String, Void, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);

                try {
                    JSONObject jsonObj = new JSONObject(result);
                    rs = jsonObj.getJSONArray(TAG_RESULTS);
                    for(int i=0;i<rs.length();i++) {
                        JSONObject c = rs.getJSONObject(i);
                        if (!c.getString("Name").equals("")){
                            if (L1.getVisibility() == View.GONE){
                                if (L2.getVisibility() == View.GONE){
                                    if (L3.getVisibility() == View.GONE){
                                        if (L4.getVisibility() == View.GONE){
                                            if (L5.getVisibility() == View.GONE){
                                                if (L6.getVisibility() == View.GONE){
                                                    if (L7.getVisibility() == View.GONE){
                                                        no8.setText("1.");
                                                    }else {
                                                        no8.setText("2.");
                                                    }
                                                }else {
                                                    no8.setText("3.");
                                                }
                                            }else {
                                                no8.setText("4.");
                                            }
                                        }else {
                                            no8.setText("5.");
                                        }
                                    }else {
                                        no8.setText("6.");
                                    }
                                }else {
                                    if (L7.getVisibility() == View.GONE){
                                        no8.setText("4.");
                                    }else {
                                        no8.setText("7.");
                                    }
                                }
                            }else {
                                if (L2.getVisibility() == View.GONE){
                                    if (L3.getVisibility() == View.GONE){
                                        if (L4.getVisibility() == View.GONE){
                                            if (L5.getVisibility() == View.GONE){
                                                if (L6.getVisibility() == View.GONE){
                                                    if (L7.getVisibility() == View.GONE){
                                                        no8.setText("2.");
                                                    }else {
                                                        no8.setText("3.");
                                                    }
                                                }else {
                                                    no8.setText("4.");
                                                }
                                            }else {
                                                if (L7.getVisibility() == View.GONE){
                                                    no8.setText("4.");
                                                }else {
                                                    no8.setText("5.");
                                                }
                                            }
                                        }else {
                                            no8.setText("6.");
                                        }
                                    }else {
                                        no8.setText("7.");
                                    }
                                }else {
                                    no8.setText("8.");
                                }
                            }
                            text8_8.setVisibility(View.VISIBLE);
                            sw8.setVisibility(View.VISIBLE);
                            text8.setText(c.getString("Name"));
                            if (c.getString("IsStatus").equals("0")){
                                sw8.setChecked(false);
                            }else {
                                sw8.setChecked(true);
                            }
                        }else {
                            L8.setVisibility(View.GONE);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @SuppressLint("WrongThread")
            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("data","8");
                String result = null;
                try {
                    result = httpConnect.sendPostRequest(Url.URL + "cssd_display_config_type.php", data);
                    Log.d("FKJDHJKDH",data+"");
                    Log.d("FKJDHJKDH",result+"");
                }catch(Exception e){
                    e.printStackTrace();
                }
                return result;
            }
            // =========================================================================================
        }
        PutDataCheck8 obj = new PutDataCheck8();
        obj.execute();
    }

    public void PutDataCheck1_1() {
        class PutDataCheck1_1 extends AsyncTask<String, Void, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);

                try {
                    JSONObject jsonObj = new JSONObject(result);
                    rs = jsonObj.getJSONArray(TAG_RESULTS);
                    for(int i=0;i<rs.length();i++) {
                        JSONObject c = rs.getJSONObject(i);
                        text1__1.setText(c.getString("Name"));
                        if (c.getString("IsUser").equals("0")){
                            ch1_1.setChecked(false);
                        }else {
                            ch1_1.setChecked(true);
                        }

                        if (c.getString("IsInCharg").equals("0")){
                            ch1_2.setChecked(false);
                        }else {
                            ch1_2.setChecked(true);
                        }

                        if (c.getString("IsAdmin").equals("0")){
                            ch1_3.setChecked(false);
                        }else {
                            ch1_3.setChecked(true);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @SuppressLint("WrongThread")
            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("data","4");
                String result = null;
                try {
                    result = httpConnect.sendPostRequest(Url.URL + "cssd_display_config_type2.php", data);
                    Log.d("FKJDHJKDH",data+"");
                    Log.d("FKJDHJKDH",result+"");
                }catch(Exception e){
                    e.printStackTrace();
                }
                return result;
            }
            // =========================================================================================
        }
        PutDataCheck1_1 obj = new PutDataCheck1_1();
        obj.execute();
    }

    public void PutDataCheck2_1() {
        class PutDataCheck2_1 extends AsyncTask<String, Void, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);

                try {
                    JSONObject jsonObj = new JSONObject(result);
                    rs = jsonObj.getJSONArray(TAG_RESULTS);
                    for(int i=0;i<rs.length();i++) {
                        JSONObject c = rs.getJSONObject(i);
                        text2_1.setText(c.getString("Name"));
                        if (c.getString("IsUser").equals("0")){
                            ch2_1.setChecked(false);
                        }else {
                            ch2_1.setChecked(true);
                        }

                        if (c.getString("IsInCharg").equals("0")){
                            ch2_2.setChecked(false);
                        }else {
                            ch2_2.setChecked(true);
                        }

                        if (c.getString("IsAdmin").equals("0")){
                            ch2_3.setChecked(false);
                        }else {
                            ch2_3.setChecked(true);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @SuppressLint("WrongThread")
            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("data","5");
                String result = null;
                try {
                    result = httpConnect.sendPostRequest(Url.URL + "cssd_display_config_type2.php", data);
                    Log.d("FKJDHJKDH",data+"");
                    Log.d("FKJDHJKDH",result+"");
                }catch(Exception e){
                    e.printStackTrace();
                }
                return result;
            }
            // =========================================================================================
        }
        PutDataCheck2_1 obj = new PutDataCheck2_1();
        obj.execute();
    }

    public void PutDataCheck3_1() {
        class PutDataCheck3_1 extends AsyncTask<String, Void, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);

                try {
                    JSONObject jsonObj = new JSONObject(result);
                    rs = jsonObj.getJSONArray(TAG_RESULTS);
                    for(int i=0;i<rs.length();i++) {
                        JSONObject c = rs.getJSONObject(i);
                        text3_1.setText(c.getString("Name"));
                        if (c.getString("IsUser").equals("0")){
                            ch3_1.setChecked(false);
                        }else {
                            ch3_1.setChecked(true);
                        }

                        if (c.getString("IsInCharg").equals("0")){
                            ch3_2.setChecked(false);
                        }else {
                            ch3_2.setChecked(true);
                        }

                        if (c.getString("IsAdmin").equals("0")){
                            ch3_3.setChecked(false);
                        }else {
                            ch3_3.setChecked(true);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @SuppressLint("WrongThread")
            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("data","6");
                String result = null;
                try {
                    result = httpConnect.sendPostRequest(Url.URL + "cssd_display_config_type2.php", data);
                    Log.d("FKJDHJKDH",data+"");
                    Log.d("FKJDHJKDH",result+"");
                }catch(Exception e){
                    e.printStackTrace();
                }
                return result;
            }
            // =========================================================================================
        }
        PutDataCheck3_1 obj = new PutDataCheck3_1();
        obj.execute();
    }

    public void PutDataCheck4_1() {
        class PutDataCheck4_1 extends AsyncTask<String, Void, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);

                try {
                    JSONObject jsonObj = new JSONObject(result);
                    rs = jsonObj.getJSONArray(TAG_RESULTS);
                    for(int i=0;i<rs.length();i++) {
                        JSONObject c = rs.getJSONObject(i);
                        text4_1.setText(c.getString("Name"));
                        if (c.getString("IsUser").equals("0")){
                            ch4_1.setChecked(false);
                        }else {
                            ch4_1.setChecked(true);
                        }

                        if (c.getString("IsInCharg").equals("0")){
                            ch4_2.setChecked(false);
                        }else {
                            ch4_2.setChecked(true);
                        }

                        if (c.getString("IsAdmin").equals("0")){
                            ch4_3.setChecked(false);
                        }else {
                            ch4_3.setChecked(true);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @SuppressLint("WrongThread")
            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("data","7");
                String result = null;
                try {
                    result = httpConnect.sendPostRequest(Url.URL + "cssd_display_config_type2.php", data);
                    Log.d("FKJDHJKDH",data+"");
                    Log.d("FKJDHJKDH",result+"");
                }catch(Exception e){
                    e.printStackTrace();
                }
                return result;
            }
            // =========================================================================================
        }
        PutDataCheck4_1 obj = new PutDataCheck4_1();
        obj.execute();
    }

    public void PutDataCheck5_1() {
        class PutDataCheck5_1 extends AsyncTask<String, Void, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);

                try {
                    JSONObject jsonObj = new JSONObject(result);
                    rs = jsonObj.getJSONArray(TAG_RESULTS);
                    for(int i=0;i<rs.length();i++) {
                        JSONObject c = rs.getJSONObject(i);
                        text5_1.setText(c.getString("Name"));
                        if (c.getString("IsUser").equals("0")){
                            ch5_1.setChecked(false);
                        }else {
                            ch5_1.setChecked(true);
                        }

                        if (c.getString("IsInCharg").equals("0")){
                            ch5_2.setChecked(false);
                        }else {
                            ch5_2.setChecked(true);
                        }

                        if (c.getString("IsAdmin").equals("0")){
                            ch5_3.setChecked(false);
                        }else {
                            ch5_3.setChecked(true);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @SuppressLint("WrongThread")
            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("data","8");
                String result = null;
                try {
                    result = httpConnect.sendPostRequest(Url.URL + "cssd_display_config_type2.php", data);
                    Log.d("FKJDHJKDH",data+"");
                    Log.d("FKJDHJKDH",result+"");
                }catch(Exception e){
                    e.printStackTrace();
                }
                return result;
            }
            // =========================================================================================
        }
        PutDataCheck5_1 obj = new PutDataCheck5_1();
        obj.execute();
    }

    public void PutDataCheck6_1() {
        class PutDataCheck6_1 extends AsyncTask<String, Void, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);

                try {
                    JSONObject jsonObj = new JSONObject(result);
                    rs = jsonObj.getJSONArray(TAG_RESULTS);
                    for(int i=0;i<rs.length();i++) {
                        JSONObject c = rs.getJSONObject(i);
                        text6_1.setText(c.getString("Name"));
                        if (c.getString("IsUser").equals("0")){
                            ch6_1.setChecked(false);
                        }else {
                            ch6_1.setChecked(true);
                        }

                        if (c.getString("IsInCharg").equals("0")){
                            ch6_2.setChecked(false);
                        }else {
                            ch6_2.setChecked(true);
                        }

                        if (c.getString("IsAdmin").equals("0")){
                            ch6_3.setChecked(false);
                        }else {
                            ch6_3.setChecked(true);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @SuppressLint("WrongThread")
            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("data","9");
                String result = null;
                try {
                    result = httpConnect.sendPostRequest(Url.URL + "cssd_display_config_type2.php", data);
                    Log.d("FKJDHJKDH",data+"");
                    Log.d("FKJDHJKDH",result+"");
                }catch(Exception e){
                    e.printStackTrace();
                }
                return result;
            }
            // =========================================================================================
        }
        PutDataCheck6_1 obj = new PutDataCheck6_1();
        obj.execute();
    }

    public void PutDataCheck7_1() {
        class PutDataCheck7_1 extends AsyncTask<String, Void, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);

                try {
                    JSONObject jsonObj = new JSONObject(result);
                    rs = jsonObj.getJSONArray(TAG_RESULTS);
                    for(int i=0;i<rs.length();i++) {
                        JSONObject c = rs.getJSONObject(i);
                        text7_1.setText(c.getString("Name"));
                        if (c.getString("IsUser").equals("0")){
                            ch7_1.setChecked(false);
                        }else {
                            ch7_1.setChecked(true);
                        }

                        if (c.getString("IsInCharg").equals("0")){
                            ch7_2.setChecked(false);
                        }else {
                            ch7_2.setChecked(true);
                        }

                        if (c.getString("IsAdmin").equals("0")){
                            ch7_3.setChecked(false);
                        }else {
                            ch7_3.setChecked(true);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @SuppressLint("WrongThread")
            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("data","10");
                String result = null;
                try {
                    result = httpConnect.sendPostRequest(Url.URL + "cssd_display_config_type2.php", data);
                    Log.d("FKJDHJKDH",data+"");
                    Log.d("FKJDHJKDH",result+"");
                }catch(Exception e){
                    e.printStackTrace();
                }
                return result;
            }
            // =========================================================================================
        }
        PutDataCheck7_1 obj = new PutDataCheck7_1();
        obj.execute();
    }

    public void PutDataCheck8_1() {
        class PutDataCheck8_1 extends AsyncTask<String, Void, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);

                try {
                    JSONObject jsonObj = new JSONObject(result);
                    rs = jsonObj.getJSONArray(TAG_RESULTS);
                    for(int i=0;i<rs.length();i++) {
                        JSONObject c = rs.getJSONObject(i);
                        text8_1.setText(c.getString("Name"));
                        if (c.getString("IsUser").equals("0")){
                            ch8_1.setChecked(false);
                        }else {
                            ch8_1.setChecked(true);
                        }

                        if (c.getString("IsInCharg").equals("0")){
                            ch8_2.setChecked(false);
                        }else {
                            ch8_2.setChecked(true);
                        }

                        if (c.getString("IsAdmin").equals("0")){
                            ch8_3.setChecked(false);
                        }else {
                            ch8_3.setChecked(true);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @SuppressLint("WrongThread")
            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("data","11");
                String result = null;
                try {
                    result = httpConnect.sendPostRequest(Url.URL + "cssd_display_config_type2.php", data);
                    Log.d("FKJDHJKDH",data+"");
                    Log.d("FKJDHJKDH",result+"");
                }catch(Exception e){
                    e.printStackTrace();
                }
                return result;
            }
            // =========================================================================================
        }
        PutDataCheck8_1 obj = new PutDataCheck8_1();
        obj.execute();
    }

    public void PutDataCheck9_1() {
        class PutDataCheck9_1 extends AsyncTask<String, Void, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);

                try {
                    JSONObject jsonObj = new JSONObject(result);
                    rs = jsonObj.getJSONArray(TAG_RESULTS);
                    for(int i=0;i<rs.length();i++) {
                        JSONObject c = rs.getJSONObject(i);
                        text9_1.setText(c.getString("Name"));
                        if (c.getString("IsUser").equals("0")){
                            ch9_1.setChecked(false);
                        }else {
                            ch9_1.setChecked(true);
                        }

                        if (c.getString("IsInCharg").equals("0")){
                            ch9_2.setChecked(false);
                        }else {
                            ch9_2.setChecked(true);
                        }

                        if (c.getString("IsAdmin").equals("0")){
                            ch9_3.setChecked(false);
                        }else {
                            ch9_3.setChecked(true);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @SuppressLint("WrongThread")
            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("data","12");
                String result = null;
                try {
                    result = httpConnect.sendPostRequest(Url.URL + "cssd_display_config_type2.php", data);
                    Log.d("FKJDHJKDH",data+"");
                    Log.d("FKJDHJKDH",result+"");
                }catch(Exception e){
                    e.printStackTrace();
                }
                return result;
            }
            // =========================================================================================
        }
        PutDataCheck9_1 obj = new PutDataCheck9_1();
        obj.execute();
    }

    public void PutDataCheck10_1() {
        class PutDataCheck10_1 extends AsyncTask<String, Void, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);

                try {
                    JSONObject jsonObj = new JSONObject(result);
                    rs = jsonObj.getJSONArray(TAG_RESULTS);
                    for(int i=0;i<rs.length();i++) {
                        JSONObject c = rs.getJSONObject(i);
                        text10_1.setText(c.getString("Name"));
                        if (c.getString("IsUser").equals("0")){
                            ch10_1.setChecked(false);
                        }else {
                            ch10_1.setChecked(true);
                        }

                        if (c.getString("IsInCharg").equals("0")){
                            ch10_2.setChecked(false);
                        }else {
                            ch10_2.setChecked(true);
                        }

                        if (c.getString("IsAdmin").equals("0")){
                            ch10_3.setChecked(false);
                        }else {
                            ch10_3.setChecked(true);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @SuppressLint("WrongThread")
            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("data","13");
                String result = null;
                try {
                    result = httpConnect.sendPostRequest(Url.URL + "cssd_display_config_type2.php", data);
                    Log.d("FKJDHJKDH",data+"");
                    Log.d("FKJDHJKDH",result+"");
                }catch(Exception e){
                    e.printStackTrace();
                }
                return result;
            }
            // =========================================================================================
        }
        PutDataCheck10_1 obj = new PutDataCheck10_1();
        obj.execute();
    }

    public void PutDataCheck11_1() {
        class PutDataCheck11_1 extends AsyncTask<String, Void, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);

                try {
                    JSONObject jsonObj = new JSONObject(result);
                    rs = jsonObj.getJSONArray(TAG_RESULTS);
                    for(int i=0;i<rs.length();i++) {
                        JSONObject c = rs.getJSONObject(i);
                        text11_1.setText(c.getString("Name"));
                        if (c.getString("IsUser").equals("0")){
                            ch11_1.setChecked(false);
                        }else {
                            ch11_1.setChecked(true);
                        }

                        if (c.getString("IsInCharg").equals("0")){
                            ch11_2.setChecked(false);
                        }else {
                            ch11_2.setChecked(true);
                        }

                        if (c.getString("IsAdmin").equals("0")){
                            ch11_3.setChecked(false);
                        }else {
                            ch11_3.setChecked(true);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @SuppressLint("WrongThread")
            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("data","14");
                String result = null;
                try {
                    result = httpConnect.sendPostRequest(Url.URL + "cssd_display_config_type2.php", data);
                    Log.d("FKJDHJKDH",data+"");
                    Log.d("FKJDHJKDH",result+"");
                }catch(Exception e){
                    e.printStackTrace();
                }
                return result;
            }
            // =========================================================================================
        }
        PutDataCheck11_1 obj = new PutDataCheck11_1();
        obj.execute();
    }

    public void PutDataCheck12_1() {
        class PutDataCheck12_1 extends AsyncTask<String, Void, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);

                try {
                    JSONObject jsonObj = new JSONObject(result);
                    rs = jsonObj.getJSONArray(TAG_RESULTS);
                    for(int i=0;i<rs.length();i++) {
                        JSONObject c = rs.getJSONObject(i);
                        text12_1.setText(c.getString("Name"));
                        if (c.getString("IsUser").equals("0")){
                            ch12_1.setChecked(false);
                        }else {
                            ch12_1.setChecked(true);
                        }

                        if (c.getString("IsInCharg").equals("0")){
                            ch12_2.setChecked(false);
                        }else {
                            ch12_2.setChecked(true);
                        }

                        if (c.getString("IsAdmin").equals("0")){
                            ch12_3.setChecked(false);
                        }else {
                            ch12_3.setChecked(true);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @SuppressLint("WrongThread")
            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("data","15");
                String result = null;
                try {
                    result = httpConnect.sendPostRequest(Url.URL + "cssd_display_config_type2.php", data);
                    Log.d("FKJDHJKDH",data+"");
                    Log.d("FKJDHJKDH",result+"");
                }catch(Exception e){
                    e.printStackTrace();
                }
                return result;
            }
            // =========================================================================================
        }
        PutDataCheck12_1 obj = new PutDataCheck12_1();
        obj.execute();
    }

    public void PutDataCheck13_1() {
        class PutDataCheck13_1 extends AsyncTask<String, Void, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);

                try {
                    JSONObject jsonObj = new JSONObject(result);
                    rs = jsonObj.getJSONArray(TAG_RESULTS);
                    for(int i=0;i<rs.length();i++) {
                        JSONObject c = rs.getJSONObject(i);
                        text13_1.setText(c.getString("Name"));
                        if (c.getString("IsUser").equals("0")){
                            ch13_1.setChecked(false);
                        }else {
                            ch13_1.setChecked(true);
                        }

                        if (c.getString("IsInCharg").equals("0")){
                            ch13_2.setChecked(false);
                        }else {
                            ch13_2.setChecked(true);
                        }

                        if (c.getString("IsAdmin").equals("0")){
                            ch13_3.setChecked(false);
                        }else {
                            ch13_3.setChecked(true);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @SuppressLint("WrongThread")
            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("data","16");
                String result = null;
                try {
                    result = httpConnect.sendPostRequest(Url.URL + "cssd_display_config_type2.php", data);
                    Log.d("FKJDHJKDH",data+"");
                    Log.d("FKJDHJKDH",result+"");
                }catch(Exception e){
                    e.printStackTrace();
                }
                return result;
            }
            // =========================================================================================
        }
        PutDataCheck13_1 obj = new PutDataCheck13_1();
        obj.execute();
    }

    public void PutDataCheck14_1() {
        class PutDataCheck14_1 extends AsyncTask<String, Void, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);

                try {
                    JSONObject jsonObj = new JSONObject(result);
                    rs = jsonObj.getJSONArray(TAG_RESULTS);
                    for(int i=0;i<rs.length();i++) {
                        JSONObject c = rs.getJSONObject(i);
                        text14_1.setText(c.getString("Name"));
                        if (c.getString("IsUser").equals("0")){
                            ch14_1.setChecked(false);
                        }else {
                            ch14_1.setChecked(true);
                        }

                        if (c.getString("IsInCharg").equals("0")){
                            ch14_2.setChecked(false);
                        }else {
                            ch14_2.setChecked(true);
                        }

                        if (c.getString("IsAdmin").equals("0")){
                            ch14_3.setChecked(false);
                        }else {
                            ch14_3.setChecked(true);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @SuppressLint("WrongThread")
            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("data","17");
                String result = null;
                try {
                    result = httpConnect.sendPostRequest(Url.URL + "cssd_display_config_type2.php", data);
                    Log.d("FKJDHJKDH",data+"");
                    Log.d("FKJDHJKDH",result+"");
                }catch(Exception e){
                    e.printStackTrace();
                }
                return result;
            }
            // =========================================================================================
        }
        PutDataCheck14_1 obj = new PutDataCheck14_1();
        obj.execute();
    }

    public void PutDataCheck15_1() {
        class PutDataCheck15_1 extends AsyncTask<String, Void, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);

                try {
                    JSONObject jsonObj = new JSONObject(result);
                    rs = jsonObj.getJSONArray(TAG_RESULTS);
                    for(int i=0;i<rs.length();i++) {
                        JSONObject c = rs.getJSONObject(i);
                        text15_1.setText(c.getString("Name"));
                        if (c.getString("IsUser").equals("0")){
                            ch15_1.setChecked(false);
                        }else {
                            ch15_1.setChecked(true);
                        }

                        if (c.getString("IsInCharg").equals("0")){
                            ch15_2.setChecked(false);
                        }else {
                            ch15_2.setChecked(true);
                        }

                        if (c.getString("IsAdmin").equals("0")){
                            ch15_3.setChecked(false);
                        }else {
                            ch15_3.setChecked(true);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @SuppressLint("WrongThread")
            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("data","18");
                String result = null;
                try {
                    result = httpConnect.sendPostRequest(Url.URL + "cssd_display_config_type2.php", data);
                    Log.d("FKJDHJKDH",data+"");
                    Log.d("FKJDHJKDH",result+"");
                }catch(Exception e){
                    e.printStackTrace();
                }
                return result;
            }
            // =========================================================================================
        }
        PutDataCheck15_1 obj = new PutDataCheck15_1();
        obj.execute();
    }
}