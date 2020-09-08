package com.phc.cssd;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.phc.core.connect.HTTPConnect;
import com.phc.core.data.AsonData;
import com.phc.core.string.Cons;
import com.phc.cssd.url.Url;
import com.phc.cssd.url.getUrl;

import java.util.ArrayList;
import java.util.HashMap;

public class Menu extends AppCompatActivity {

    private HTTPConnect httpConnect = new HTTPConnect();
    final Handler h = new Handler();
    private Runnable r;
    private String userid, user_name = null;
    private String B_ID = "1";
    private String B_Name = null;
    private String EmpCode = null;
    private boolean IsAdmin = false;
    private TextView txt_username;
    private TextView txt_wash_remain;
    private TextView txt_version;
    private TextView txt_b;
    private ImageView imv_logout;
    private ImageView img_take_back;
    private ImageView img_edit_sterile;
    private ImageView key_hack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        getSupportActionBar().hide();
        byWidget();
        byIntent();
        stockDaily();
        txt_version.setText(getUrl.PROJECT + " " + getUrl.VERSION);
    }

    public void onDestroy() {
        super.onDestroy();
        clearHandler();
    }

    @Override
    public void onBackPressed() {
        Menu.super.onBackPressed();
    }

    private void byIntent(){
        // Argument
        Intent intent = getIntent();
        userid = intent.getStringExtra("userid");
        user_name = intent.getStringExtra("user_name");
        B_ID = intent.getStringExtra("B_ID");
        B_Name = intent.getStringExtra("B_Name");
        EmpCode = intent.getStringExtra("EmpCode");
        String admin = intent.getStringExtra("IsAdmin");
        IsAdmin = admin != null && admin.equals("1") ;
        img_edit_sterile.setVisibility(IsAdmin ? View.VISIBLE : View.GONE);
        txt_username.setText("ผู้ใช้งาน : " + user_name );
        txt_b.setText("ตึก : "+B_Name);
        if (!EmpCode.equals("EM00000")){
            key_hack.setVisibility(View.INVISIBLE);
        }else {
            key_hack.setVisibility(View.VISIBLE);
            key_hack.setFocusable(true);
        }
        r = new Runnable() {
            @Override
            public void run() {
                try {
                    h.postDelayed(this, 15000);

                    displayWashRemain();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        h.postDelayed(r, 1000);
    }

    private void clearHandler() {
        h.removeCallbacks(r);
    }

    private void byWidget() {
        txt_version = (TextView) findViewById(R.id.txt_version);
        txt_username = (TextView) findViewById(R.id.txt_username);
        txt_wash_remain = (TextView) findViewById(R.id.txt_wash_remain);
        txt_b = (TextView) findViewById(R.id.txt_b);
        imv_logout = (ImageView) findViewById(R.id.imv_logout);
        img_take_back = (ImageView) findViewById(R.id.img_take_back);
        img_edit_sterile = (ImageView) findViewById(R.id.img_edit_sterile);
        key_hack = (ImageView) findViewById(R.id.key_hack);
        key_hack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Menu.this,Export_database.class);
                startActivity(intent);
                finish();
            }
        });
        img_take_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoActivity(CssdTakeBack.class);
            }
        });
        img_edit_sterile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoActivity(CssdEditSterile.class);
            }
        });
        imv_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder quitDialog = new AlertDialog.Builder(Menu.this);
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
        ImageView imv_m1 = (ImageView) findViewById(R.id.imv_m1);
        ImageView imv_m2 = (ImageView) findViewById(R.id.imv_m2);
        ImageView imv_m3 = (ImageView) findViewById(R.id.imv_m3);
        ImageView imv_m4 = (ImageView) findViewById(R.id.imv_m4);
        ImageView imv_1 = (ImageView) findViewById(R.id.imv_1);
        ImageView imv_2 = (ImageView) findViewById(R.id.imv_2);
        ImageView imv_3 = (ImageView) findViewById(R.id.imv_3);
        ImageView imv_4 = (ImageView) findViewById(R.id.imv_4);
        ImageView ic_inst_ = (ImageView) findViewById(R.id.ic_inst_);
        ImageView img_preview_item_sterile = (ImageView) findViewById(R.id.img_preview_item_sterile);
        ImageView imv_setting = (ImageView) findViewById(R.id.imv_setting);
        Bundle bd = getIntent().getExtras();
        if (bd != null){
            userid =  bd.getString("userid");
        }

        ic_inst_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { gotoActivity(PreviewSticker_main.class);
            }
        });
        img_preview_item_sterile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { gotoActivity(CssdPreviewItemSterile.class);
            }
        });
        imv_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { gotoActivity(CssdWash.class);
            }
        });
        imv_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { gotoActivity(CssdSterile.class);
            }
        });
        imv_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { gotoActivity(PayoutActivity.class);
            }
        });
        imv_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { gotoActivity(SterileSeachActivity.class);
            }
        });
        imv_m1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { gotoActivity(ReportActivity.class);
            }
        });
        imv_m2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { gotoActivity(Search_item.class);
            }
        });
        imv_m3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { gotoActivity(Occurance_MainActivity.class);
            }
        });
        imv_m4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { gotoActivity(Recall.class);
            }
        });
        imv_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { gotoActivity(SettingActivity.class);
            }
        });
    }

    public void onClickBtn(View v){
        //Toast.makeText(this, "Clicked on Button : "+ v.getResources().getResourceName(0), Toast.LENGTH_LONG).show();
    }

    private void gotoActivity(Class c){
        Intent intent = new Intent(Menu.this, c);
        intent.putExtra("userid", userid);
        intent.putExtra("user_name", user_name);
        intent.putExtra("IsAdmin", IsAdmin);
        intent.putExtra("B_ID", B_ID);
        intent.putExtra("EmpCode", EmpCode);
        startActivity(intent);
    }

    private Service s = new Service() {
        @Nullable
        @Override
        public IBinder onBind(Intent intent) {
            return null;
        }
    };

    public void showNotification(String MESSAGE) {
        Intent intent = new Intent(this, Item.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(Item.class);
        stackBuilder.addNextIntent(intent);
        PendingIntent contentIntent = PendingIntent.getActivity(
                this,
                0,
                new Intent(), // add this
                PendingIntent.FLAG_UPDATE_CURRENT);
        Notification notification = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(Cons.TITLE)
                .setContentText(MESSAGE)
                .setAutoCancel(true)
                .setContentIntent(contentIntent)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(MESSAGE))
                .build();
        NotificationManager notificationManager = (NotificationManager) this.getSystemService(s.NOTIFICATION_SERVICE);
        notificationManager.notify(1000, notification);
    }
    // =============================================================================================
    // -- wash remain
    // =============================================================================================
    public void displayWashRemain() {
        class DisplayWashRemain extends AsyncTask<String, Void, String> {
            private ProgressDialog dialog = new ProgressDialog(Menu.this);
            //------------------------------------------------
            // Background Worker Process Variable
            private boolean Success = false;
            private ArrayList<String> data = null;
            private int size = 0;
            //------------------------------------------------
            // variable
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                //this.dialog.setMessage(Cons.WAIT_FOR_PROCESS);
                //this.dialog.show();
            }
            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                AsonData ason = new AsonData(result);
                Success = ason.isSuccess();
                size = ason.getSize();
                data = ason.getASONData();
                if(Success && data != null) {
                    try{
                        txt_wash_remain.setText(data.get(0));
                    }catch(Exception e){
                        e.printStackTrace();
                        return;
                    }
                }else{
                }
            }
            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("p_userid", userid);
                String result = httpConnect.sendPostRequest(Url.URL + "cssd_select_sendsterile_wash_remain.php", data);
                return result;
            }
            // =========================================================================================
        }
        DisplayWashRemain obj = new DisplayWashRemain();
        obj.execute();
    }

    public void stockDaily() {
        class Daily extends AsyncTask<String, Void, String> {
            // variable
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);

            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();

                String result = httpConnect.sendPostRequest(Url.URL + "cssd_display_itemstock_daily.php", data);

                return result;
            }

            // =========================================================================================
        }

        Daily obj = new Daily();
        obj.execute();
    }

}
