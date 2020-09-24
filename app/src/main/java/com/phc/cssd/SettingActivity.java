package com.phc.cssd;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.phc.core.string.Cons;
import com.phc.cssd.master_data.DepartmentActivity;
import com.phc.cssd.master_data.EmployeeActivity;
import com.phc.cssd.master_data.ItemTypeActivity;
import com.phc.cssd.master_data.OccurenceTypeActivity;
import com.phc.cssd.master_data.OrganizationActivity;
import com.phc.cssd.master_data.PackingmapActivity;
import com.phc.cssd.master_data.RegisterActivity;
import com.phc.cssd.master_data.SpecialActivity;
import com.phc.cssd.master_data.SterilemachineActivity;
import com.phc.cssd.master_data.SterileprocessActivity;
import com.phc.cssd.master_data.UnitsActivity;
import com.phc.cssd.master_data.WashMachineActivity;
import com.phc.cssd.master_data.WashProcessActivity;


public class SettingActivity extends AppCompatActivity implements View.OnClickListener {

    private String B_ID = null;

    RelativeLayout m_setting;
    RelativeLayout RL;
    RelativeLayout RL1;
    RelativeLayout RL2;
    RelativeLayout RL3;
    ImageView ic_hospital;
    ImageView ic_inst;
    ImageView ic_sterile;

    ImageView back;

    ImageView ic_hospital_001;
    ImageView ic_hospital_002;
    ImageView ic_hospital_003;
    ImageView ic_hospital_004;
    ImageView ic_hospital_005;
    ImageView ic_hospital_006;

    ImageView ic_inst_021;
    ImageView ic_inst_022;
    ImageView ic_inst_023;
    ImageView ic_inst_024;
    ImageView ic_inst_025;
    ImageView ic_inst_026;

    ImageView ic_sterile_031;
    ImageView ic_sterile_032;
    ImageView ic_sterile_033;
    ImageView ic_sterile_034;
    ImageView ic_sterile_035;

    ImageView ic_hospital_02;
    ImageView ic_sterile_02;
    ImageView ic_inst_01;
    ImageView ic_sterile_01;
    ImageView ic_inst_03;
    ImageView ic_hospital_03;

    ImageView imv_logout2;
    TextView txt_username;
    String userid="";
    String user_name="";
    String page="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        getSupportActionBar().hide();

        byIntent();

        Log.d("page", ""+page);
        try {
            Bundle bd = getIntent().getExtras();
            if (bd != null) {
                userid = bd.getString("userid");
                user_name = bd.getString("user_name");
                txt_username = (TextView)findViewById(R.id.txt_username2);
                Log.d("user_name: ", user_name);
                txt_username.setText("ผู้ใช้งาน : " + user_name);
                if(bd.getString("page")!=null) {
                    page = bd.getString("page");
                }
            } else {
                page = "blank";
            }
        }catch(Throwable e){
            page = "blank";
        }

        imv_logout2 = (ImageView) findViewById(R.id.imv_logout2);

        imv_logout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder quitDialog = new AlertDialog.Builder(SettingActivity.this);

                quitDialog.setTitle(Cons.TITLE);
                quitDialog.setIcon(R.drawable.pose_favicon_2x);
                quitDialog.setMessage("ยืนยันการออกจากโปรแกรม ?");

                quitDialog.setPositiveButton("ตกลง", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        gotoActivity(Login.class);
                    }
                });

                quitDialog.setNegativeButton("ยกเลิก", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                quitDialog.show();


            }
        });
        m_setting = (RelativeLayout)findViewById(R.id.m_setting);
        RL = (RelativeLayout)findViewById(R.id.Rl);
        RL1 = (RelativeLayout)findViewById(R.id.Rl1);
        RL2 = (RelativeLayout)findViewById(R.id.Rl2);
        RL3 = (RelativeLayout)findViewById(R.id.Rl3);

        RL1.setVisibility( View.INVISIBLE );
        RL2.setVisibility( View.INVISIBLE );
        RL3.setVisibility( View.INVISIBLE );

        ic_hospital = (ImageView)findViewById(R.id.ic_hospital);
        ic_inst = (ImageView)findViewById(R.id.ic_inst);
        ic_sterile = (ImageView)findViewById(R.id.ic_sterile);
        back = (ImageView)findViewById(R.id.img_back);

        m_setting.setBackgroundResource(R.drawable.bg_setting_1);
        switch (page){
            case "blank":
                m_setting.setBackgroundResource(R.drawable.bg_setting_1);
                RL.setVisibility( View.VISIBLE );
                RL1.setVisibility( View.INVISIBLE );
                RL2.setVisibility( View.INVISIBLE );
                RL3.setVisibility( View.INVISIBLE );
                break;
            case "top":
                m_setting.setBackgroundResource(R.drawable.bg_setting_2);
                RL.setVisibility( View.INVISIBLE );
                RL1.setVisibility( View.INVISIBLE );
                RL2.setVisibility( View.VISIBLE );
                RL3.setVisibility( View.INVISIBLE );
                break;
            case "middle":
                m_setting.setBackgroundResource(R.drawable.bg_setting_3);
                RL.setVisibility( View.INVISIBLE );
                RL1.setVisibility( View.VISIBLE );
                RL2.setVisibility( View.INVISIBLE );
                RL3.setVisibility( View.INVISIBLE );
                break;
            case "bottom":
                m_setting.setBackgroundResource(R.drawable.bg_setting_4);
                RL.setVisibility( View.INVISIBLE );
                RL1.setVisibility( View.INVISIBLE );
                RL2.setVisibility( View.INVISIBLE );
                RL3.setVisibility( View.VISIBLE );
                break;
        }

        back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //Intent intent = new Intent(SettingActivity.this, Menu.class);
                //startActivity(intent);
                finish();
//                m_setting.setBackgroundResource(R.drawable.bg_setting_1);
//                RL.setVisibility( View.VISIBLE );
//                RL1.setVisibility( View.INVISIBLE );
//                RL2.setVisibility( View.INVISIBLE );
//                RL3.setVisibility( View.INVISIBLE );
            }
        });

        ic_hospital.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                m_setting.setBackgroundResource(R.drawable.bg_setting_3);
                RL.setVisibility( View.INVISIBLE );
                RL1.setVisibility( View.VISIBLE );
                RL2.setVisibility( View.INVISIBLE );
                RL3.setVisibility( View.INVISIBLE );
            }
        });

        ic_inst.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                m_setting.setBackgroundResource(R.drawable.bg_setting_2);
                RL.setVisibility( View.INVISIBLE );
                RL1.setVisibility( View.INVISIBLE );
                RL2.setVisibility( View.VISIBLE );
                RL3.setVisibility( View.INVISIBLE );
            }
        });

        ic_sterile.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                m_setting.setBackgroundResource(R.drawable.bg_setting_4);
                RL.setVisibility( View.INVISIBLE );
                RL1.setVisibility( View.INVISIBLE );
                RL2.setVisibility( View.INVISIBLE );
                RL3.setVisibility( View.VISIBLE );
            }
        });

        setButton();
        setButtonClick(this);
    }

    private void byIntent(){
        // Argument
        Intent intent = getIntent();
        B_ID = intent.getStringExtra("B_ID");
    }

    private void setButton() {
        ic_hospital_001 = (ImageView)findViewById(R.id.ic_hospital_001);
        ic_hospital_002 = (ImageView)findViewById(R.id.ic_hospital_002);
        ic_hospital_003 = (ImageView)findViewById(R.id.ic_hospital_003);
        ic_hospital_004 = (ImageView)findViewById(R.id.ic_hospital_004);
        ic_hospital_005 = (ImageView)findViewById(R.id.ic_hospital_005);
        ic_hospital_006 = (ImageView)findViewById(R.id.ic_hospital_006);

        ic_inst_021 = (ImageView)findViewById(R.id.ic_inst_021);
        ic_inst_022 = (ImageView)findViewById(R.id.ic_inst_022);
        ic_inst_023 = (ImageView)findViewById(R.id.ic_inst_023);
        ic_inst_024 = (ImageView)findViewById(R.id.ic_inst_024);
        ic_inst_025 = (ImageView)findViewById(R.id.ic_inst_025);
        ic_inst_026 = (ImageView)findViewById(R.id.ic_inst_026);

        ic_sterile_031 = (ImageView)findViewById(R.id.ic_sterile_031);
        ic_sterile_032 = (ImageView)findViewById(R.id.ic_sterile_032);
        ic_sterile_033 = (ImageView)findViewById(R.id.ic_sterile_033);
        ic_sterile_034 = (ImageView)findViewById(R.id.ic_sterile_034);
        ic_sterile_035 = (ImageView)findViewById(R.id.ic_sterile_035);

        ic_hospital_02 = (ImageView) findViewById(R.id.ic_hospital_02);
        ic_sterile_02 = (ImageView) findViewById(R.id.ic_sterile_02);

        ic_inst_01 = (ImageView) findViewById(R.id.ic_inst_01);
        ic_sterile_01 = (ImageView) findViewById(R.id.ic_sterile_01);

        ic_inst_03 = (ImageView) findViewById(R.id.ic_inst_03);
        ic_hospital_03 = (ImageView) findViewById(R.id.ic_hospital_03);
        ic_hospital_005.setImageResource(R.drawable.ic_expire);
        ic_hospital_006.setImageResource(R.drawable.ic_hospital_006_grey);
    }

    private void setButtonClick( final View.OnClickListener listener) {
        ic_hospital_001.setOnClickListener(listener);
        ic_hospital_002.setOnClickListener(listener);
        ic_hospital_003.setOnClickListener(listener);
        ic_hospital_004.setOnClickListener(listener);
        ic_hospital_005.setOnClickListener(listener);
        ic_hospital_006.setOnClickListener(listener);

        ic_inst_021.setOnClickListener(listener);
        ic_inst_022.setOnClickListener(listener);
        ic_inst_023.setOnClickListener(listener);
        ic_inst_024.setOnClickListener(listener);
        ic_inst_025.setOnClickListener(listener);
        ic_inst_026.setOnClickListener(listener);

        ic_sterile_031.setOnClickListener(listener);
        ic_sterile_032.setOnClickListener(listener);
        ic_sterile_033.setOnClickListener(listener);
        ic_sterile_034.setOnClickListener(listener);
        ic_sterile_035.setOnClickListener(listener);

        ic_hospital_02.setOnClickListener(listener);
        ic_sterile_02.setOnClickListener(listener);

        ic_inst_01.setOnClickListener(listener);
        ic_sterile_01.setOnClickListener(listener);

        ic_inst_03.setOnClickListener(listener);
        ic_hospital_03.setOnClickListener(listener);
    }

    private void gotoActivity(Class c){
        Intent intent = new Intent(SettingActivity.this, c);
        intent.putExtra("userid", userid);
        intent.putExtra("B_ID",B_ID);
        startActivity(intent);
    }

    public void onClick(View v){
        // TODO Auto-generated method stub
//        if (v.getId() == R.id.ic_hospital_001 )        {
//            Log.d("test", "hey!");
//            // gotoActivity(Se);
//        }

        switch(v.getId()){

            // Main object//
            case R.id.ic_hospital_02:
                m_setting.setBackgroundResource(R.drawable.bg_setting_3);
                RL.setVisibility( View.INVISIBLE );
                RL1.setVisibility( View.VISIBLE );
                RL2.setVisibility( View.INVISIBLE );
                RL3.setVisibility( View.INVISIBLE );
                break;
            case R.id.ic_sterile_02:
                m_setting.setBackgroundResource(R.drawable.bg_setting_4);
                RL.setVisibility( View.INVISIBLE );
                RL1.setVisibility( View.INVISIBLE );
                RL2.setVisibility( View.INVISIBLE );
                RL3.setVisibility( View.VISIBLE );
                break;
            case R.id.ic_inst_01:
                m_setting.setBackgroundResource(R.drawable.bg_setting_2);
                RL.setVisibility( View.INVISIBLE );
                RL1.setVisibility( View.INVISIBLE );
                RL2.setVisibility( View.VISIBLE );
                RL3.setVisibility( View.INVISIBLE );
                break;
            case R.id.ic_sterile_01:
                m_setting.setBackgroundResource(R.drawable.bg_setting_4);
                RL.setVisibility( View.INVISIBLE );
                RL1.setVisibility( View.INVISIBLE );
                RL2.setVisibility( View.INVISIBLE );
                RL3.setVisibility( View.VISIBLE );
                break;
            case R.id.ic_inst_03:
                m_setting.setBackgroundResource(R.drawable.bg_setting_2);
                RL.setVisibility( View.INVISIBLE );
                RL1.setVisibility( View.INVISIBLE );
                RL2.setVisibility( View.VISIBLE );
                RL3.setVisibility( View.INVISIBLE );
                break;
            case R.id.ic_hospital_03:
                m_setting.setBackgroundResource(R.drawable.bg_setting_3);
                RL.setVisibility( View.INVISIBLE );
                RL1.setVisibility( View.VISIBLE );
                RL2.setVisibility( View.INVISIBLE );
                RL3.setVisibility( View.INVISIBLE );
                break;
            ///////////////
            case R.id.ic_hospital_001:
                Log.d("test", "ic_hospital_001!");
                gotoActivity(OrganizationActivity.class);
                break;
            case R.id.ic_hospital_002:
                Log.d("test", "ic_hospital_002!");
                gotoActivity(DepartmentActivity.class);

                break;
            case R.id.ic_hospital_003:
                Log.d("test", "ic_hospital_003!");
                gotoActivity(EmployeeActivity.class);
                break;
            case R.id.ic_hospital_004:
                Log.d("test", "ic_hospital_004!");
                gotoActivity(RegisterActivity.class);
                break;
            case R.id.ic_hospital_005:
                Log.d("test", "ic_hospital_005!");
                ic_hospital_005.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(SettingActivity.this, SettingExprieActivity.class);
                        startActivity(i);
                    }
                });
                break;
            case R.id.ic_hospital_006:
                Log.d("test", "ic_hospital_006!");
                //gotoActivity(ItemTypeActivity.class);
                break;

            case R.id.ic_inst_021:
                gotoActivity(Item_newlayout.class);
                Log.d("test", "ic_inst_021!");
                break;
            case R.id.ic_inst_022:
                Log.d("test", "ic_inst_022!");
                gotoActivity(ItemTypeActivity.class);
                break;
            case R.id.ic_inst_023:
                Log.d("test", "ic_inst_023!");
                gotoActivity(PackingmapActivity.class);
                break;
            case R.id.ic_inst_024:
                Log.d("test", "ic_inst_024!");
                gotoActivity(PreviewSticker_main.class);
                break;
            case R.id.ic_inst_025:
                Log.d("test", "ic_inst_025!");
                gotoActivity(SpecialActivity.class);
                break;
            case R.id.ic_inst_026:
                Log.d("test", "ic_inst_026!");
                gotoActivity(UnitsActivity.class);
                break;

            case R.id.ic_sterile_031:
                Log.d("test", "ic_sterile_031!");
                gotoActivity(OccurenceTypeActivity.class);
                break;
            case R.id.ic_sterile_032:
                Log.d("test", "ic_sterile_032!");
                gotoActivity(SterileprocessActivity.class);
                break;
            case R.id.ic_sterile_033:
                Log.d("test", "ic_sterile_033!");
                gotoActivity(SterilemachineActivity.class);
                break;
            case R.id.ic_sterile_034:
                Log.d("test", "ic_sterile_034!");
                gotoActivity(WashProcessActivity.class);
                break;
            case R.id.ic_sterile_035:
                Log.d("test", "ic_sterile_035!");
                gotoActivity(WashMachineActivity.class);
                break;

        }
    }

    @Override
    public void onBackPressed() {
        SettingActivity.super.onBackPressed();
    }

}
