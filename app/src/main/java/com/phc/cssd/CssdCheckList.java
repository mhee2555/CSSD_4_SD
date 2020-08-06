package com.phc.cssd;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.StrictMode;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.phc.core.connect.HTTPConnect;
import com.phc.cssd.adapter.CheckListAdapter;
import com.phc.cssd.model.ModelCheckList;
import com.phc.cssd.model.ModelWashDetailForPrint;
import com.phc.cssd.print_sticker.PrintWash;
import com.phc.cssd.url.Url;
import com.phc.cssd.url.getUrl;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CssdCheckList extends Activity {

    // Http
    private String TAG_RESULTS="result";
    private JSONArray rs = null;
    private HTTPConnect httpConnect = new HTTPConnect();


    // Intent
    private String userid = null;
    private String B_ID = null;
    private boolean IsAdmin = false;
    private String ID = null;

    // Widget
    private ListView list_check;
    private ImageView imv_print;
    private ImageView imageBack;
    private EditText edt_usage_code;
    private TextView txt_packer;

    private List<ModelCheckList> MODEL_CHECK_LIST = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cssd_check_list);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        byIntent();

        byWidget();

        byEvent();

        displayCheckList();

    }

    private void byIntent(){
        // Argument
        Intent intent = getIntent();
        userid = intent.getStringExtra("userid");
        B_ID = intent.getStringExtra("B_ID");
        IsAdmin = intent.getBooleanExtra("IsAdmin", false);
        ID = intent.getStringExtra("ID");
    }

    private void byWidget(){

        list_check = (ListView) findViewById(R.id.list_check);

        imv_print = (ImageView) findViewById(R.id.imv_print);

        imageBack = (ImageView) findViewById(R.id.imageBack);
        txt_packer = ( TextView ) findViewById(R.id.txt_packer);
        edt_usage_code = ( EditText ) findViewById(R.id.edt_usage_code);
        edt_usage_code.setShowSoftInputOnFocus(false);
    }

    private void byEvent(){

        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        edt_usage_code.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    switch (keyCode) {
                        case KeyEvent.KEYCODE_DPAD_CENTER:
                        case KeyEvent.KEYCODE_ENTER:

                            checkInput(edt_usage_code.getText().toString());

                            break;
                        default:
                            break;
                    }
                }
                return false;
            }
        });


        imv_print.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onPrint();
            }
        });

        txt_packer.setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View v) {

                checkPacker("EM00002");

                return true;
            }
        });

    }

    private void onPrint(){

        boolean IsCheck = true;
        String MsgCheck = "";

        try {

            // Check Packer
            if (txt_packer.getContentDescription() == null || txt_packer.getContentDescription().toString().equals("")) {
                Toast.makeText(CssdCheckList.this, "ยังไม่ได้เลือกผู้ห่อ !!", Toast.LENGTH_SHORT).show();
                return;
            }

            // Check List
            for(int i=0;i<MODEL_CHECK_LIST.size();i++){

                if (!MODEL_CHECK_LIST.get(i).isCheck()) {
                    MsgCheck = "มีบางรายการยังไม่ได้ถูกเช็ค !!";
                    IsCheck = false;
                    break;
                }

                if (!MODEL_CHECK_LIST.get(i).getNameType().equals("")) {
                    MsgCheck = "มีบางรายการถูก Remark ไว้ !!";
                    IsCheck = false;
                    break;
                }
            }

            if(!IsCheck){
                Toast.makeText(CssdCheckList.this, MsgCheck, Toast.LENGTH_SHORT).show();
                return;
            }

            // Print
            class Print extends AsyncTask<String, Void, String> {

                // variable
                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                }

                @Override
                protected void onPostExecute(String s) {
                    super.onPostExecute(s);

                    List<ModelWashDetailForPrint> list = new ArrayList<>();

                    try {
                        JSONObject jsonObj = new JSONObject(s);
                        rs = jsonObj.getJSONArray(TAG_RESULTS);

                        for (int i = 0; i < rs.length(); i++) {

                            JSONObject c = rs.getJSONObject(i);

                            if (c.getString("result").equals("A")) {
                                list.add(
                                        new ModelWashDetailForPrint(
                                                c.getString("ID"),
                                                c.getString("itemcode"),
                                                c.getString("itemname"),
                                                c.getString("UsageCode"),
                                                c.getString("Packer"),
                                                c.getString("Age"),
                                                c.getString("MFG"),
                                                c.getString("EXP"),
                                                c.getString("CaseLabel"),
                                                c.getString("PrinterIP"),
                                                c.getString("UsageCount"),
                                                c.getString("IsCheckList")
                                        )
                                );

                                // Print
                                PrintWash p = new PrintWash();
                                String p_data = p.print(CssdCheckList.this, c.getInt("CaseLabel"), c.getString("PrinterIP"), list);

                                // Update Print Status
                                updatePrintStatus(p_data);

                                if(c.getString("IsCheckList").equals("1")){
                                    callCheckListPaper(ID);
                                }

                                finish();
                            }
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        return;
                    }finally {
                        focus();
                    }

                    // -----------------------------------
                    // Print Multiple Id
                    // -----------------------------------
                    /*
                    List<ModelWashDetailForPrint> list_2 ;
                    List<ModelWashDetailForPrint> list_3 ;

                    list_2 = getWashDetailLabelForPrint(list, "2");
                    list_3 = getWashDetailLabelForPrint(list, "3");

                    // Print
                    final PrintWash p = new PrintWash();
                    String p_data = "";

                    if(list_2 != null && list_2.size() > 0) {
                        String ip = list_2.get(0).getPrinterIP();
                        p_data += p.print(CssdCheckList.this, 2, ip, list_2);
                    }

                    if(list_3 != null && list_3.size() > 0) {
                        String ip = list_2.get(0).getPrinterIP();
                        p_data += p.print(CssdCheckList.this, 3, ip, list_3);
                    }

                    // Update Print Status
                    if(p_data != null && !p_data.equals(""))
                        updatePrintStatus(p_data);
                    */
                    // -----------------------------------

                }

                @Override
                protected String doInBackground(String... params) {
                    HashMap<String, String> data = new HashMap<String,String>();
                    data.put("ID", ID);
                    String result = null;

                    try {
                        result = httpConnect.sendPostRequest(Url.URL + "cssd_select_wash_detail_for_print.php", data);

                    }catch(Exception e){
                        e.printStackTrace();
                    }

                    return result;
                }

                // =========================================================================================
            }

            Print obj = new Print();
            obj.execute();


        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(!IsCheck)
                focus();
        }
    }

    private List<ModelWashDetailForPrint> getWashDetailLabelForPrint(List<ModelWashDetailForPrint> list, String Label){

        List<ModelWashDetailForPrint> list_label = new ArrayList<>();

        // List Label portion
        for(int ix=0;ix<list.size();ix++){
            if(list.get(ix).getCaseLabel().equals(Label)) {
                list_label.add(
                        new ModelWashDetailForPrint(
                                list.get(ix).getID(),
                                list.get(ix).getItemcode(),
                                list.get(ix).getItemname(),
                                list.get(ix).getUsageCode(),
                                list.get(ix).getPacker(),
                                list.get(ix).getAge(),
                                list.get(ix).getMFG(),
                                list.get(ix).getEXP(),
                                list.get(ix).getCaseLabel(),
                                list.get(ix).getPrinterIP(),
                                list.get(ix).getUsageCount(),
                                list.get(ix).getIsCheckList()
                        )
                );
            }
        }

        return list_label;
    }

    public void updatePrintStatus(final String p_data) {

        class UpdatePrintStatus extends AsyncTask<String, Void, String> {

            // variable
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();

                data.put("p_data", p_data.substring(0, p_data.length()-1));

                String result = httpConnect.sendPostRequest(Url.URL + "cssd_update_wash_detail_print_status.php", data);

                return result;
            }

            // =========================================================================================
        }

        UpdatePrintStatus obj = new UpdatePrintStatus();
        obj.execute();
    }

    private void callCheckListPaper(String WashID) {
        String url = Url.URL + "report/Report_checklist_sterile.php?WashID=" + WashID;
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }

    public void displayCheckList() {

        class DisplayWashDetail extends AsyncTask<String, Void, String> {

            // variable
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                try {
                    JSONObject jsonObj = new JSONObject(s);
                    rs = jsonObj.getJSONArray(TAG_RESULTS);

                    List<ModelCheckList> list = new ArrayList<>();

                    for (int i = 0; i < rs.length(); i++) {

                        JSONObject c = rs.getJSONObject(i);

                        if (c.getString("result").equals("A")) {
                            list.add(
                                    new ModelCheckList(
                                            c.getString("ID"),
                                            c.getString("Send_ID"),
                                            c.getString("itemcode"),
                                            c.getString("itemname"),
                                            c.getString("Qty"),
                                            c.getString("AdminRemark"),
                                            c.getString("DateRemark"),
                                            c.getString("Remark"),
                                            c.getString("NameType"),
                                            false
                                    )
                            );
                        }
                    }

                    // Model
                    MODEL_CHECK_LIST = list;

                    try {
                        ArrayAdapter<ModelCheckList> adapter;

                        adapter = new CheckListAdapter(CssdCheckList.this, MODEL_CHECK_LIST);
                        list_check.setAdapter(adapter);

                    } catch (Exception e) {
                        list_check.setAdapter(null);
                        e.printStackTrace();
                    }

                } catch (Exception e) {
                    e.printStackTrace();

                    list_check.setAdapter(null);

                    return;
                }

            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();

                data.put("ID", ID);

                if(B_ID != null){
                    data.put("p_bid", B_ID);
                }

                String result = null;

                try {
                    result = httpConnect.sendPostRequest(Url.URL + "cssd_select_check_list.php", data);

                }catch(Exception e){
                    e.printStackTrace();
                }

                return result;
            }

            // =========================================================================================
        }

        DisplayWashDetail obj = new DisplayWashDetail();
        obj.execute();
    }

    public void checkPacker(final String packer) {

        class Check extends AsyncTask<String, Void, String> {

            // variable
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                try {
                    JSONObject jsonObj = new JSONObject(s);
                    rs = jsonObj.getJSONArray(TAG_RESULTS);

                    for (int i = 0; i < rs.length(); i++) {

                        JSONObject c = rs.getJSONObject(i);

                        if (c.getString("result").equals("A")) {
                            txt_packer.setText("ผู้ห่อ : " + c.getString("packer"));
                            txt_packer.setContentDescription(c.getString("ID"));
                        }else{
                            Toast.makeText(CssdCheckList.this, "ไม่พบรายการ !!", Toast.LENGTH_SHORT).show();
                        }

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }finally {
                    focus();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();

                data.put("ID", ID);
                data.put("packer", packer);

                if(B_ID != null){
                    data.put("p_bid", B_ID);
                }

                String result = null;

                try {
                    result = httpConnect.sendPostRequest(Url.URL + "cssd_check_packer.php", data);

                }catch(Exception e){
                    e.printStackTrace();
                }

                return result;
            }

            // =========================================================================================
        }

        Check obj = new Check();
        obj.execute();
    }

    private void checkInput(final String Input){

        boolean IsCheck = false;

        try {

            String code = Input.length() > 6 ? Input.substring(0, 5) : Input;

            for(int i=0;i<MODEL_CHECK_LIST.size();i++){

                //System.out.println(code + " = " + MODEL_CHECK_LIST.get(i).getItemcode());

                if (code.equals(MODEL_CHECK_LIST.get(i).getItemcode())) {

                    IsCheck = true;

                    if(MODEL_CHECK_LIST.get(i).isCheck()){
                        Toast.makeText(CssdCheckList.this, "รายการนี้ได้ทำการยิงเช็คแล้ว !!", Toast.LENGTH_SHORT).show();
                        break;
                    }

                    // Set
                    MODEL_CHECK_LIST.get(i).setCheck(true);

                    // Display
                    ArrayAdapter<ModelCheckList> adapter;
                    adapter = new CheckListAdapter(CssdCheckList.this, MODEL_CHECK_LIST);
                    list_check.setAdapter(adapter);

                    break;
                }
            }

            if(!IsCheck){
                checkPacker(Input);
            }


        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(IsCheck) {
                focus();
            }
        }
    }

    private void focus(){
        final Handler h = new Handler();
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                edt_usage_code.setText("");
                edt_usage_code.requestFocus();
            }
        }, 500);

    }
}