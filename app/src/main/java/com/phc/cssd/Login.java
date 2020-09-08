package com.phc.cssd;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.phc.core.connect.HTTPConnect;
import com.phc.core.string.Cons;
import com.phc.cssd.url.Url;
import com.phc.cssd.url.getUrl;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

public class Login extends AppCompatActivity {

    private EditText uname;
    private EditText pword;
    private Button submit;
    private Button down;
    private Spinner spinner_building;
    private ImageView imageView3;
    private TextView textView3;
    private TextView textView1;
    private TextView btn_test;
    private TextView btn_test1;

    DownloadManager downloadManager;
    String NewVersion;
    String VersionApp = "";
    String IsConfirmVersion = "0";
    String TAG_RESULTS = "result";
    String serialNumber;
    String nameapp;
    JSONArray setRs = null;
    getUrl iFt = new getUrl();
    private HTTPConnect httpConnect = new HTTPConnect();

    ArrayList<String> building_ar_text = new ArrayList<>();
    ArrayList<String> building_ar_value = new ArrayList<String>();

    DownloadManager dm;
    long downloadId;
    String  pendingDownloadUrl ;
    final int storagePermissionRequestCode = 101;
    private String downloadUrl = "", downloadFileName = "";
    //private const val PROVIDER_PATH = ".provider"

    //DeviceMode
    int T2Lite = 1;
    int L2 = 2;
    int DeviceMode = 1;
    private RelativeLayout page;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        getSupportActionBar().hide();

        getSerialNumber();

//        VersionApp();

//        FireBaseCheckVersionApp();

//        FireBaseDownloadApk();

        byWidget();

        byEvent();

        addBuilding();

        DeviceMode = setDeviceMode();

        if("TL01184200273".equals(getSerialNumber())||"L203P85U01743".equals(getSerialNumber())){
            get_login("admin", "123");
        }

    }

    public int setDeviceMode() {
        int re = 1;
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        if(width>720){//T2lite
            re = T2Lite;
        }else{//L2
            re = L2;
            imageView3.setVisibility(View.GONE);
            page.getLayoutParams().width = 550;
            page.getLayoutParams().height = 370;
            int x = (int) (20 * Resources.getSystem().getDisplayMetrics().density);
            page.setPadding(0,0,x,0);
        }
        return re;
    }

    public String getSerialNumber() {
        try {
            Class<?> c = Class.forName("android.os.SystemProperties");
            Method get = c.getMethod("get", String.class);
            serialNumber = (String) get.invoke(c, "gsm.sn1");
            if (serialNumber.equals(""))
                serialNumber = (String) get.invoke(c, "ril.serialnumber");
            if (serialNumber.equals(""))
                serialNumber = (String) get.invoke(c, "ro.serialno");
            if (serialNumber.equals(""))
                serialNumber = (String) get.invoke(c, "sys.serialnumber");
            if (serialNumber.equals(""))
                serialNumber = Build.SERIAL;
            if (serialNumber.equals(""))
                serialNumber = null;
        } catch (Exception e) {
            e.printStackTrace();
            serialNumber = null;
        }
        Log.d("ttest_serialNumber","serialNumber--"+serialNumber);
        return serialNumber;
    }

    private void byWidget() {
        page = (RelativeLayout) findViewById(R.id.page);
        textView3 = (TextView) findViewById(R.id.textView3);
        textView1 = (TextView) findViewById(R.id.textView1);
        spinner_building = (Spinner) findViewById(R.id.spinner_building);
        uname = (EditText) findViewById(R.id.txt_username);
        pword = (EditText) findViewById(R.id.txt_password);
        submit = (Button) findViewById(R.id.button_login);
        imageView3 = (ImageView) findViewById(R.id.QrCode);
        btn_test = (TextView) findViewById(R.id.btn_test);
    }

    private void byEvent(){
        // Test
        imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, Menu.class);
                intent.putExtra("userid", "1");
                intent.putExtra("user_name", "aaa");
                startActivity(intent);
                finish();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!uname.getText().toString().equals("") && !pword.getText().toString().equals("")) {
                    get_login(uname.getText().toString(), pword.getText().toString());
                    //openFile("apk"+"Version_1.0.1"+".apk");
                }else {
                    Toast.makeText(Login.this, "กรุณากรอกรายการให้ครบถ้วน", Toast.LENGTH_SHORT).show();
                }
//                if (!spinner_building.getSelectedItem().equals("-")) {
//                    get_login(uname.getText().toString(), pword.getText().toString());
//                }else {
//                    Toast.makeText(Login.this, "กรุณากรอกรายการให้ครบถ้วน", Toast.LENGTH_SHORT).show();
//                }
            }
        });
    }

    public void get_login(final String uname, String pword) {

        class get_login extends AsyncTask<String, Void, String> {

            private ProgressDialog dialog = new ProgressDialog(Login.this);

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                dialog.setTitle(Cons.TITLE);
                dialog.setIcon(R.drawable.pose_favicon_2x);
                dialog.setMessage(Cons.WAIT_FOR_AUTHENTICATION);
                dialog.setCanceledOnTouchOutside(false);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0xEFFFFFFF));
                dialog.setIndeterminate(true);

                dialog.show();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                try {
                    JSONObject jsonObj = new JSONObject(s);
                    setRs = jsonObj.getJSONArray(TAG_RESULTS);

                    for (int i = 0; i < setRs.length(); i++) {
                        JSONObject c = setRs.getJSONObject(i);
                        if (!c.getString("id").equals("false")) {
                            Intent intent;
                            if(DeviceMode==T2Lite){
                                intent = new Intent(Login.this,Menu.class);
                            }else{
                                intent = new Intent(Login.this,PayoutActivity.class);
                            }
//                            intent = new Intent(Login.this,test.class);
                            intent.putExtra("userid", c.getString("id"));
                            intent.putExtra("user_name", c.getString("username"));
                            intent.putExtra("Lang", c.getString("Lang"));
                            intent.putExtra("IsAdmin", c.getString("IsAdmin"));
                            intent.putExtra("EmpCode",c.getString("EmpCode"));
                            intent.putExtra("B_ID", "1");
                            intent.putExtra("B_Name", building_ar_text.get(spinner_building.getSelectedItemPosition()));
                            startActivity(intent);
                            finish();
                            Toast.makeText(Login.this,"สวัสดีคุณ"+" "+c.getString("FirstName")+" "+c.getString("LastName")+"",Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(Login.this, "ไม่พบผู้ใช้นี้ในระบบ กรุณาตรวจสอบชื่อผู้ใช้และรหัสผ่านอีกครั้ง", Toast.LENGTH_SHORT).show();
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (dialog.isShowing()) {
                    dialog.dismiss();
                }

            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String, String>();
                data.put("uname", params[0]);
                data.put("pword", params[1]);

                String result = httpConnect.sendPostRequest(iFt.get_Login(), data);
                Log.d("FKJDHJKDH",data+"");
                Log.d("FKJDHJKDH",result+"");
                return result;
            }
        }

        get_login ru = new get_login();
        ru.execute(uname, pword);
    }

    public void addBuilding() {
        class Add extends AsyncTask<String, Void, String> {

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                try {
                    JSONObject jsonObj = new JSONObject(s);
                    setRs = jsonObj.getJSONArray(TAG_RESULTS);
                    building_ar_text.clear();
                    building_ar_value.clear();
                    int count = 0;
                    building_ar_text.add("-");
                    building_ar_value.add("0");
                    for (int i = 0; i < setRs.length(); i++) {

                        JSONObject c = setRs.getJSONObject(i);

                        if (c.getString("result").equals("A")) {
                            building_ar_text.add(c.getString("BuildingName"));
                            building_ar_value.add(c.getString("ID"));
                        }

                        count++;
                    }

                    if(count == 1){
                        textView3.setVisibility(View.GONE);
                        spinner_building.setVisibility(View.GONE);
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(Login.this, android.R.layout.simple_spinner_dropdown_item, building_ar_text);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner_building.setAdapter(adapter);
                    spinner_building.setSelection(0);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String, String>();
                String result = httpConnect.sendPostRequest(Url.URL + "cssd_select_building.php", data);

                return result;
            }
        }

        Add ru = new Add();

        ru.execute();
    }

//    public void VersionApp() {
//        class VersionApp extends AsyncTask<String, Void, String> {
//            @Override
//            protected void onPreExecute() {
//                super.onPreExecute();
//            }
//
//            @Override
//            protected void onPostExecute(String result) {
//                super.onPostExecute(result);
//
//                try {
//                    JSONObject jsonObj = new JSONObject(result);
//                    setRs = jsonObj.getJSONArray(TAG_RESULTS);
//                    for(int i=0;i<setRs.length();i++) {
//                        JSONObject c = setRs.getJSONObject(i);
//                        btn_test.setText(c.getString("version"));
//                        if (c.getString("Isreturn").equals("0") || c.getString("Isreturn").equals("null")) {
//                            if (c.getString("Isconfirm").equals("0") || c.getString("Isconfirm").equals("")) {
//                                IsConfirmVersion = "1";
//                                down.setVisibility(View.INVISIBLE);
//                                textView1.setVisibility(View.VISIBLE);
//                                uname.setVisibility(View.VISIBLE);
//                                pword.setVisibility(View.VISIBLE);
//                                submit.setVisibility(View.VISIBLE);
//                                imageView3.setVisibility(View.VISIBLE);
//                            } else {
//                                IsConfirmVersion = "0";
//                                down.setVisibility(View.VISIBLE);
//                                textView1.setVisibility(View.INVISIBLE);
//                                uname.setVisibility(View.INVISIBLE);
//                                pword.setVisibility(View.INVISIBLE);
//                                submit.setVisibility(View.INVISIBLE);
//                                imageView3.setVisibility(View.INVISIBLE);
//                                VersionAppNew();
//                            }
//                        }else {
//                            IsConfirmVersion = "0";
//                            down.setVisibility(View.VISIBLE);
//                            down.setText("RETRUN VERSION");
//                            VersionAppNew();
//                        }
//                    }
//                    if (IsConfirmVersion.equals("0")){
//                        uname.setEnabled(false);
//                        pword.setEnabled(false);
//                        //spinner_building.setEnabled(false);
//                        submit.setEnabled(false);
////                        Alerter.create(Login.this)
////                                .setTitle("กรุณาอัพเดตแอพพลิเคชั่น")
////                                .setIcon(R.drawable.ic_warning_black_24dp)
////                                .setBackgroundColorRes(R.color.colorAccent)
////                                .setDuration(3000)
////                                .enableSwipeToDismiss()
////                                .enableProgress(true)
////                                .setProgressColorRes(R.color.colorPrimaryDark)
////                                .show();
//                        Toast.makeText(Login.this,"กรุณาอัพเดตแอพพลิเคชั่น",Toast.LENGTH_SHORT).show();
//                    }else {
//                        uname.setEnabled(true);
//                        pword.setEnabled(true);
//                        //spinner_building.setEnabled(true);
//                        submit.setEnabled(true);
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            protected String doInBackground(String... params) {
//                HashMap<String, String> data = new HashMap<String,String>();
//                data.put("serialNumber",serialNumber);
//                String result = null;
//                try {
//                    result = httpConnect.sendPostRequest(Url.URL + "cssd_select_version.php", data);
//                    Log.d("DDSDAD",data+"");
//                    Log.d("DDSDAD",result);
//                }catch(Exception e){
//                    e.printStackTrace();
//                }
//                return result;
//            }
//            // =========================================================================================
//        }
//        VersionApp obj = new VersionApp();
//        obj.execute();
//    }

//    public void VersionAppNew() {
//        class VersionAppNew extends AsyncTask<String, Void, String> {
//            @Override
//            protected void onPreExecute() {
//                super.onPreExecute();
//            }
//
//            @Override
//            protected void onPostExecute(String result) {
//                super.onPostExecute(result);
//
//                try {
//                    JSONObject jsonObj = new JSONObject(result);
//                    setRs = jsonObj.getJSONArray(TAG_RESULTS);
//                    for(int i=0;i<setRs.length();i++) {
//                        JSONObject c = setRs.getJSONObject(i);
//                        NewVersion = c.getString("version");
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            protected String doInBackground(String... params) {
//                HashMap<String, String> data = new HashMap<String,String>();
//                data.put("serialNumber",serialNumber);
//                String result = null;
//                try {
//                    result = httpConnect.sendPostRequest(Url.URL + "cssd_select_version_new.php", data);
//                    Log.d("LJDHL",data+"");
//                    Log.d("LJDHL",result);
//                }catch(Exception e){
//                    e.printStackTrace();
//                }
//                return result;
//            }
//            // =========================================================================================
//        }
//        VersionAppNew obj = new VersionAppNew();
//        obj.execute();
//    }
//
//    public void FireBaseDownloadApk(){
//        down = (Button) findViewById(R.id.down);
//        down.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                UpdateVersionApp(VersionApp);
//                dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
//                downloadManager = ( DownloadManager )getSystemService(Context.DOWNLOAD_SERVICE);
//                //Uri uri = Uri.parse("http://192.168.14.48/cssd_php_m1_paydep_vch/APK_Version/apk"+NewVersion+".apk");
//                //Uri uri = Uri.parse("http://poseintelligence.dyndns.biz:8181/cssd_php_m1_paydep_2usage/APK_Version/apk"+NewVersion+".apk");
//                //Uri uri = Uri.parse("http://poseintelligence.dyndns.biz:8181/cssd_php_m1_paydep_vcy_test/APK_Version/apk"+NewVersion+".apk");
//                Uri uri = Uri.parse("http://poseintelligence.dyndns.biz:8181/cssd_siriraj/APK_Version/apk"+NewVersion+".apk");
//                nameapp = "/apk"+NewVersion+".apk";
//                //Uri uri = Uri.parse("http://poseintelligence.dyndns.biz:8181/cssd_php_m1_paydep_test_itemcode/APK_Version/apk"+NewVersion+".apk");
//                onDownloadStart(String.valueOf(uri));
//                Log.d("KJDKJD",BuildConfig.APPLICATION_ID+"");
////                DownloadManager.Request request = new DownloadManager.Request(uri);
////                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
////                Long reference = downloadManager.enqueue(request);
////                down.setVisibility(View.GONE);
////                Alerter.create(Login.this)
////                        .setTitle("สามารถติดตั้งโปรแกรมใหม่ได้ที่การดาวน์โหลดของคุณ")
////                        .setIcon(R.drawable.ic_check_box_black_24dp)
////                        .setBackgroundColorRes(R.color.colorGreen)
////                        .setDuration(3000)
////                        .enableSwipeToDismiss()
////                        .enableProgress(true)
////                        .setProgressColorRes(R.color.colorPrimaryDark)
////                        .show();
//                //Toasty.warning(Login.this,"สามารถติดตั้งโปรแกรมใหม่ได้ที่การดาวน์โหลดของคุณ",Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//
//    private  void onDownloadStart(String url) {
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
//            downlaodFile(url);
//        } else {
//            pendingDownloadUrl = url;
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, storagePermissionRequestCode);
//        }
//    }
//
//    private void downlaodFile(String url) {
//        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
//        String filename = URLUtil.guessFileName(url, null, null);
//        request.allowScanningByMediaScanner();
//        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
//        request.setDestinationInExternalPublicDir(String.valueOf(Environment.getExternalStorageDirectory()), filename);
//        downloadId = dm.enqueue(request);//save download id for later reference
//        registerReceiver(downloadCompleteReceiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
//        // }
//    }
//
//    @Override public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        if (requestCode == storagePermissionRequestCode) {
//            boolean canDownload = true;
//            for (int grantResult : grantResults) {
//                if (grantResult == PackageManager.PERMISSION_DENIED) {
//                    canDownload = false;
//                    break;
//                }
//            }
//            if(canDownload){
//                downlaodFile(pendingDownloadUrl);
//            }
//        }
//    }
//
//    BroadcastReceiver downloadCompleteReceiver = new BroadcastReceiver() {
//        @Override
//        public void onReceive(final Context context, final Intent intent) {
//            Cursor c = dm.query(new DownloadManager.Query().setFilterById(downloadId));
//            if (c != null) {
//                c.moveToFirst();
//                try {
//                    String fileUri = c.getString(c.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI));
//                    File mFile = new File(Uri.parse(fileUri).getPath());
//                    String fileName = mFile.getAbsolutePath();
//                    openFile(fileName);
//                }catch (Exception e){
//                    Log.e("error", "Could not open the downloaded file");
//                }
//            }
//        }
//    };
//
//    private void openFile(String file) {
//        try {
//            File saveFile = new File(this.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).toString()+"/"+nameapp);
//            Intent intent = new Intent(Intent.ACTION_VIEW);
////            Uri contentUri = FileProvider.getUriForFile(
////                    this,
////                    BuildConfig.APPLICATION_ID,
////                    saveFile
////            );
//            Uri contentUri = Uri.parse("content://com.phc.cssd.provider/external_files/Download"+nameapp);
//            intent.setDataAndType(contentUri,"application/vnd.android.package-archive");
//            startActivity(intent);
//            Log.d("KJDKJD",contentUri+"");
//        }catch (Exception e){
//            Log.d("KJDKJD",e+"");
//            e.printStackTrace();
//        }
//    }
//
//    public void UpdateVersionApp(String VersionApp) {
//        class UpdateVersionApp extends AsyncTask<String, Void, String> {
//            @Override
//            protected void onPreExecute() {
//                super.onPreExecute();
//            }
//
//            @Override
//            protected void onPostExecute(String result) {
//                super.onPostExecute(result);
//
//                try {
//                    JSONObject jsonObj = new JSONObject(result);
//                    setRs = jsonObj.getJSONArray(TAG_RESULTS);
//                    for(int i=0;i<setRs.length();i++) {
//                        JSONObject c = setRs.getJSONObject(i);
//                        IsConfirmVersion = "1";
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @SuppressLint("WrongThread")
//            @Override
//            protected String doInBackground(String... params) {
//                HashMap<String, String> data = new HashMap<String,String>();
//                data.put("VersionApp",btn_test.getText().toString());
//                data.put("serialNumber",serialNumber);
//                String result = null;
//                try {
//                    result = httpConnect.sendPostRequest(Url.URL + "cssd_update_version_app.php", data);
//                    Log.d("KLSJLKD",data+"");
//                    Log.d("KLSJLKD",result);
//                }catch(Exception e){
//                    e.printStackTrace();
//                }
//                return result;
//            }
//            // =========================================================================================
//        }
//        UpdateVersionApp obj = new UpdateVersionApp();
//        obj.execute();
//    }

}