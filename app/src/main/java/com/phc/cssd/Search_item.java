package com.phc.cssd;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.phc.core.connect.HTTPConnect;
import com.phc.cssd.adapter.AuxAdapter_itemstock_search_Only;
import com.phc.cssd.properties.Response_Aux_itemstock;
import com.phc.cssd.url.getUrl;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Search_item extends AppCompatActivity {

    ArrayList<Response_Aux_itemstock> ArrayData = new ArrayList<Response_Aux_itemstock>();
    ArrayList<String> liststatus = new ArrayList<String>();
    ArrayList<String> listdept = new ArrayList<String>();
    ListView list_search;
    Button button_search;
    Button button_import;
    Button bt_linen;
    Button button_edit;
    EditText txtsearch;
    EditText txtcode;
    EditText txtname;
    CheckBox allbox;
    CheckBox somebox;
    Spinner spn_status;
    Spinner spn_dept;
    String DeptID;
    String UserID;
    String B_ID = null;
    TextView labeldept;
    ImageView backbtn;

    String TAG_RESULTS="result";
    JSONArray setRs = null;
    getUrl iFt = new getUrl();
    HTTPConnect ruc = new HTTPConnect();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_item);

        getSupportActionBar().hide();

        byIntent();

        initialize();

        DeptID = "21";

        ListData("","1","","");
    }

    private void byIntent(){
        Intent intent = getIntent();
        B_ID = intent.getStringExtra("B_ID");
    }

    private void initialize(){
        bt_linen = (Button) findViewById(R.id.bt_linen);
        bt_linen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Search_item.this,dialog_linen_detail.class);
                startActivity(intent);
            }
        });
        getBundleuser();
        allbox = (CheckBox) findViewById(R.id.allchkbox);
        allbox.setChecked(true);
        somebox = (CheckBox) findViewById(R.id.somechkbox);
        somebox.setChecked(false);

        spn_status = (Spinner) findViewById(R.id.spn_status);
        spn_status.setVisibility(View.INVISIBLE);
        spn_dept = (Spinner) findViewById(R.id.spn_dept);
        spn_dept.setVisibility(View.INVISIBLE);

        labeldept = (TextView) findViewById(R.id.labeldept);

        allbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spn_status.setSelection(0);
                if(allbox.isChecked()){
                    spn_status.setVisibility(View.INVISIBLE);
                    spn_dept.setVisibility(View.INVISIBLE);
                    somebox.setChecked(false);
                    labeldept.setVisibility(View.INVISIBLE);
                }else{
                    spn_status.setVisibility(View.VISIBLE);
                    somebox.setChecked(true);
                }
            }
        });

        somebox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spn_status.setSelection(0);
                if(somebox.isChecked()){
                    spn_status.setVisibility(View.VISIBLE);
                    allbox.setChecked(false);
                }else{
                    spn_status.setVisibility(View.INVISIBLE);
                    allbox.setChecked(true);
                }
            }
        });
        txtsearch = (EditText) findViewById(R.id.item_search);
        txtsearch.setOnKeyListener(new View.OnKeyListener()
        {
            public boolean onKey(View v, int keyCode, KeyEvent event)
            {
                if (event.getAction() == KeyEvent.ACTION_DOWN)
                {
                    switch (keyCode)
                    {
                        case KeyEvent.KEYCODE_DPAD_CENTER:
                        case KeyEvent.KEYCODE_ENTER:
                            ListData(txtsearch.getText().toString(),"1","","");
                            return true;
                        default:
                            break;
                    }
                }
                return false;
            }
        });

        txtcode = (EditText) findViewById(R.id.txtcode);
        txtname = (EditText) findViewById(R.id.txtname);
        txtname.setEnabled(false);
        txtname.setTextColor(Color.BLACK);
        button_search = (Button) findViewById(R.id.button_search);
        button_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txts = txtsearch.getText().toString();
                String chkx = "";
                Log.d("ttest", spn_status+"");
                if(allbox.isChecked()){
                    chkx = "1";
                }else{
                    chkx = "2";
                }
                String statusstr = "";
                String deptstr = "";
                if(chkx.equals("1")){
                    statusstr = "";
                    deptstr = "";
                }else{
                    statusstr = spn_status.getSelectedItemPosition()+"";
                    if(statusstr.equals("5")){
                        deptstr = spn_dept.getSelectedItem().toString();;
                    }else{
                        deptstr = "";
                    }
                }
                Log.d("ttest", "txts:"+txts+"/chks:"+chkx+"/status:"+statusstr+"/dept:"+deptstr);
                if (spn_dept.getSelectedItem().equals("-")){
                    Log.d("JFGDJD",spn_status.getSelectedItem()+"");
                    if (!spn_status.getSelectedItem().equals("แผนกรับของ")){
                        ListData(txts,chkx,statusstr,String.valueOf(spn_dept.getSelectedItemPosition()));
                    }else {
                        Toast.makeText(Search_item.this,"กรุณาเลือกแผนก",Toast.LENGTH_LONG).show();
                    }
                }else {
                    ListData(txts,chkx,statusstr,String.valueOf(spn_dept.getSelectedItemPosition()));
                }


            }
        });

        button_import = (Button) findViewById(R.id.button_import) ;
        button_import.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!getCountselected().equals("0")) {
                    Log.d("test", "Selectlist: "+getSelectedcheckbox()+" count: "+getCountselected());
                }else{
                    Toast.makeText(Search_item.this,"กรุณาเลือกข้อมูลอย่างน้อย 1 รายการ", Toast.LENGTH_LONG).show();
                }
            }
        });

        button_edit = (Button) findViewById(R.id.button_edit);
        button_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }

        });

        list_search = (ListView) findViewById(R.id.list_search);

        backbtn = (ImageView) findViewById(R.id.imageBack);

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });
        setSpinnerStatus();
        ListDepartment();
        spn_status.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position==5){
                    labeldept.setVisibility(View.VISIBLE);
                    spn_dept.setVisibility(View.VISIBLE);
                }else{
                    labeldept.setVisibility(View.INVISIBLE);
                    spn_dept.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    public void setSpinnerStatus(){
        liststatus.add("ส่งล้าง");//0
        liststatus.add("ล้าง");//1
        liststatus.add("ห้องแพ็ค");//2
        liststatus.add("ห้องปลอดเชื้อ");//3
        liststatus.add("จ่ายของ");//4
        liststatus.add("แผนกรับของ");//5

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, liststatus);
        spn_status.setAdapter(adapter);
    }

    public void ListDepartment() {
        class ListDepartment extends AsyncTask<String, Void, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                List<String> list = new ArrayList<String>();
                try {
                    JSONObject jsonObj = new JSONObject(s);
                    setRs = jsonObj.getJSONArray(TAG_RESULTS);
                    listdept.clear();
                    list.add("-");
                    for(int i=0;i<setRs.length();i++){
                        JSONObject c = setRs.getJSONObject(i);
                        listdept.add(c.getString("xId"));
                        list.add(c.getString("xName"));
                    }
                    ArrayAdapter<String> SpinnerList = new ArrayAdapter<String>(Search_item.this,
                            android.R.layout.simple_dropdown_item_1line, list);
                    spn_dept.setAdapter( SpinnerList );
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("B_ID",B_ID);
                data.put("sel","1");
                String result = ruc.sendPostRequest(iFt.getdepartment(),data);
                Log.d("ttest",data+"");
                Log.d("ttest",result);
                return  result;
            }
        }
        ListDepartment ru = new ListDepartment();
        ru.execute();
    }

    private void getBundleuser() {
        Intent intent = getIntent();
        Bundle bd = getIntent().getExtras();

        try{
            if(intent.hasExtra("UserID")||intent.hasExtra("Dept")) {
                UserID = bd.getString("UserID");
                DeptID = bd.getString("Dept");
            }
        }catch (NullPointerException e){
            if(!intent.hasExtra("UserID") || !intent.hasExtra("Dept")) {
                UserID = "";
                DeptID = "";
            }
            Log.d("Error", "Error-bundle");
        }
    }

    public String getSelectedcheckbox(){
        String Arraysel = "";
        for (int i=0;i<ArrayData.size();i++){
            if(i<ArrayData.size()) {
                if(ArrayData.get(i).isIs_Check()) {
                    Arraysel += ArrayData.get(i).getFields1() + ",";
                }
            }
        }
        Arraysel = Arraysel.substring(0, Arraysel.length() - 1);
        return Arraysel;
    }

    public String getCountselected(){
        int count = 0;
        for (int i=0;i<ArrayData.size();i++){
            if(i<ArrayData.size()) {
                if(ArrayData.get(i).isIs_Check()) {
                    count++;
                }
            }
        }
        return String.valueOf(count);
    }
    ProgressDialog loadingDialog;
    public void ListData(final String Keyword,String chkx,String statusstr,String deptstr) {
        class ListData extends AsyncTask<String, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loadingDialog = ProgressDialog.show(Search_item.this, "", "Loading...", true, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                try {
                    Response_Aux_itemstock newsData;
                    JSONObject jsonObj = new JSONObject(s);
                    setRs = jsonObj.getJSONArray(TAG_RESULTS);
                    ArrayData.clear();
                    for(int i=0;i<setRs.length();i++){
                        newsData = new Response_Aux_itemstock();
                        JSONObject c = setRs.getJSONObject(i);

                        newsData.setFields1(c.getString("xItem_Name"));
                        newsData.setFields2(c.getString("xItem_Code"));
                        newsData.setFields3(c.getString("xPackDate"));
                        newsData.setFields4(c.getString("xExpDate"));
                        newsData.setFields5(c.getString("xDept"));
                        newsData.setFields6(c.getString("xQty"));

//                        newsData.setFields7(c.getString("xStatus"));

                        String xStatus = "";
                        switch (c.getString("xStatus")){
                            case "0": xStatus = "ส่งล้าง"; break;
                            case "1": xStatus = "ล้าง"; break;
                            case "2": xStatus = "ห้องแพ็ค"; break;
                            case "3": xStatus = "ห้องปลอดเชื้อ"; break;
                            case "4": xStatus = "จ่ายของ"; break;
                            case "5": xStatus = "แผนกรับของ";
                        }

                        newsData.setFields7(xStatus);

                        if(c.getString("xStatus").equals("3") && c.getString("xIsDis").equals("1")){
                            newsData.setFields8(c.getString("xUsageID")+" ถูกจอง");
                        }else{
                            newsData.setFields8(c.getString("xUsageID"));
                        }

                        newsData.setFields9(c.getString("xItem_Name"));
                        ArrayData.add( newsData );
                        if(i==0 && !Keyword.equals("")){
                            if (c.getString("xItem_Name").equals("")){
                                txtcode.setText("");
                                txtname.setText("");
                            }else {
                                txtcode.setText(c.getString("xItem_Code"));
                                txtname.setText(c.getString("xItem_Name")+" [ "+c.getString("xItem_Code")+" ]");
                            }

                        }
                    }
                    list_search.setAdapter(new AuxAdapter_itemstock_search_Only( Search_item.this, ArrayData));
                    list_search.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                            Object o = list_search.getItemAtPosition(position);
                            Response_Aux_itemstock newsData = (Response_Aux_itemstock) o;
                            if (newsData.getFields9().equals("")){
                                txtname.setText("");
                                txtcode.setText("");
                            }else {
                                txtname.setText(newsData.getFields9()+" [ "+newsData.getFields8()+" ]");
                                txtcode.setText(newsData.getFields8());
                            }

                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                loadingDialog.dismiss();
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("Keyword",params[0]);
                data.put("chkx",params[1]);
                data.put("statusstr",params[2]);
                data.put("Dept",params[3]);
                data.put("sel","1");
                data.put("B_ID",B_ID);
                String result = ruc.sendPostRequest(iFt.getSearch_Itemstock(),data);
                Log.d("ttest",data+"");
                Log.d("ttest",result);
                return  result;
            }
        }

        ListData ru = new ListData();
        ru.execute( Keyword,chkx,statusstr,deptstr );
    }

    @Override
    public void onBackPressed() {

        this.finish();
    }
}
