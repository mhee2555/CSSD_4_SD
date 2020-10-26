package com.phc.cssd.master_data;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.phc.core.connect.HTTPConnect;
import com.phc.cssd.R;
import com.phc.cssd.adapter.AuxSterileProgramItem;
import com.phc.cssd.adapter.AuxSterileProgramItem_Sterile;
import com.phc.cssd.properties.Response_Aux;
import com.phc.cssd.properties.Response_Item_sterileprogram;
import com.phc.cssd.url.getUrl;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MachineAndWash extends Activity {
    ArrayList<Response_Item_sterileprogram> ArrayItem = new ArrayList<Response_Item_sterileprogram>();
    ArrayList<Response_Aux> ArrayProgram = new ArrayList<Response_Aux>();
    ArrayList<Response_Aux> ArrayProgram_Sterile = new ArrayList<Response_Aux>();
    Button btnsave;
    ImageView backbtn;
    CheckBox cball;
    CheckBox chx_all_1;

    ListView list_item;
    ListView list_program;
    ListView list_program_1;

    String TAG_RESULTS="result";
    String Item_Code="";
    JSONArray setRs = null;
    getUrl iFt = new getUrl();
    HTTPConnect ruc = new HTTPConnect();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_machine_and_wash);
        Intent intent = getIntent();
        Item_Code = intent.getStringExtra("Item_Code");
        //Log.d("Item_Code: ", Item_Code);
        setTitle("เครื่องมือและโปรแกรมฆ่าเชื้อ");
        //getSupportActionBar().hide();
        btnsave = (Button) findViewById(R.id.button_save);
        backbtn = (ImageView) findViewById(R.id.imageBack);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getCountselected().equals("0")){
                    LinkProcess("",getCountselected(),Item_Code);
                    LinkProcess_Sterile("",getCountselected_Sterile(),Item_Code);
                }else{
                    LinkProcess(getSelectedcheckbox(),getCountselected(),Item_Code);
                    LinkProcess_Sterile(getSelectedcheckbox_Sterile(),getCountselected_Sterile(),Item_Code);
                }
                ListData_Process(Item_Code);
                ListData_Process_sterile(Item_Code);
                cball.setChecked(false);
                finish_close();
            }
        });


        cball = (CheckBox) findViewById(R.id.chx_all);
        chx_all_1 = (CheckBox) findViewById(R.id.chx_all_1);
        cball.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                toggleCheckboxAll_link(cball.isChecked());
            }
        });
        chx_all_1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                toggleCheckboxAll_link_Sterile(chx_all_1.isChecked());
            }
        });
        ListData_Process(Item_Code);
        ListData_Process_sterile(Item_Code);
    }

    private void finish_close(){
        finish();
    }

    private void toggleCheckboxAll_link(Boolean temp) {
        for (int i=0;i<ArrayProgram.size();i++){
            ArrayProgram.get(i).setIs_Check(temp);
        }
        final ListView lv1 = (ListView) findViewById(R.id.list_program);
        lv1.setAdapter(new AuxSterileProgramItem( MachineAndWash.this, ArrayProgram));
    }

    private void toggleCheckboxAll_link_Sterile(Boolean temp) {
        for (int i=0;i<ArrayProgram_Sterile.size();i++){
            ArrayProgram_Sterile.get(i).setIs_Check(temp);
        }
        final ListView lv1 = (ListView) findViewById(R.id.list_program_1);
        lv1.setAdapter(new AuxSterileProgramItem_Sterile( MachineAndWash.this, ArrayProgram_Sterile));
    }

    public String getSelectedcheckbox(){
        String Arraysel = "";
        for (int i=0;i<ArrayProgram.size();i++){
            if(i<ArrayProgram.size()) {
                if(ArrayProgram.get(i).isIs_Check()) {
                    Arraysel += ArrayProgram.get(i).getFields2() + ",";
                }
            }
        }
        Arraysel = Arraysel.substring(0, Arraysel.length() - 1);
        Log.d("Sel", Arraysel);
        return Arraysel;
    }

    public String getSelectedcheckbox_Sterile(){
        String Arraysel = "";
        for (int i=0;i<ArrayProgram_Sterile.size();i++){
            if(i<ArrayProgram_Sterile.size()) {
                if(ArrayProgram_Sterile.get(i).isIs_Check()) {
                    Arraysel += ArrayProgram_Sterile.get(i).getFields2() + ",";
                }
            }
        }
        Arraysel = Arraysel.substring(0, Arraysel.length() - 1);
        Log.d("Sel", Arraysel);
        return Arraysel;
    }

    public String getCountselected(){
        int count = 0;
        for (int i=0;i<ArrayProgram.size();i++){
            if(i<ArrayProgram.size()) {
                if(ArrayProgram.get(i).isIs_Check()) {
                    count++;
                }
            }
        }
        Log.d("SEL", count+"");
        return String.valueOf(count);
    }

    public String getCountselected_Sterile(){
        int count = 0;
        for (int i=0;i<ArrayProgram_Sterile.size();i++){
            if(i<ArrayProgram_Sterile.size()) {
                if(ArrayProgram_Sterile.get(i).isIs_Check()) {
                    count++;
                }
            }
        }
        Log.d("SEL", count+"");
        return String.valueOf(count);
    }

    public void LinkProcess(String SelectedCB,String Countn,String itemcode) {
        class LinkProcess extends AsyncTask<String, Void, String> {

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                try {
                    JSONObject jsonObj = new JSONObject(s);
                    setRs = jsonObj.getJSONArray(TAG_RESULTS);
                    String Depsel = "";
                    for(int i=0;i<setRs.length();i++){
                        JSONObject c = setRs.getJSONObject(i);
                        if(c.getString("bool").equals("true")){
                            Toast.makeText(MachineAndWash.this, "ผูกสำเร็จ", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(MachineAndWash.this, "ผูกล้มเหลว", Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("Selected",params[0]);
                data.put("Count",params[1]);
                data.put("Itemcode",params[2]);
                String result = ruc.sendPostRequest(iFt.reCreateWashProgram_Item(),data);
                return  result;
            }
        }

        LinkProcess ru = new LinkProcess();
        ru.execute(SelectedCB,Countn,itemcode);
    }

    public void LinkProcess_Sterile(String SelectedCB,String Countn,String itemcode) {
        class LinkProcess_Sterile extends AsyncTask<String, Void, String> {

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                try {
                    JSONObject jsonObj = new JSONObject(s);
                    setRs = jsonObj.getJSONArray(TAG_RESULTS);
                    String Depsel = "";
                    for(int i=0;i<setRs.length();i++){
                        JSONObject c = setRs.getJSONObject(i);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("Selected",params[0]);
                data.put("Count",params[1]);
                data.put("Itemcode",params[2]);
                String result = ruc.sendPostRequest(iFt.reCreateSterleProgram_Item(),data);
                return  result;
            }
        }

        LinkProcess_Sterile ru = new LinkProcess_Sterile();
        ru.execute(SelectedCB,Countn,itemcode);
    }

   /* public void ListData(String Search) {
        class ListData extends AsyncTask<String, Void, String> {

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                try {
                    Response_Item_sterileprogram newsData;
                    JSONObject jsonObj = new JSONObject(s);
                    setRs = jsonObj.getJSONArray(TAG_RESULTS);
                    ArrayItem.clear();
                    for(int i=0;i<setRs.length();i++){
                        newsData = new Response_Item_sterileprogram();
                        JSONObject c = setRs.getJSONObject(i);
                        newsData.settItemCode(c.getString("tItemCode"));
                        newsData.settName(c.getString("tName"));
                        newsData.settLife(c.getString("tLife"));
                        ArrayItem.add( newsData );
                    }
                    ListData_Process(Item_Code);
                    final ListView lv1 = (ListView) findViewById(R.id.list_item);
                    lv1.setAdapter(new Adapter_SterileProgram_Item( MachineAndSterile.this, ArrayItem));
                    lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            ListData_Process(ArrayItem.get(position).gettItemCode());
                            cball.setChecked(false);
                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("Search",params[0]);
                String result = ruc.sendPostRequest(iFt.searchItem_Sterileprogram(),data);
                return  result;
            }
        }

        ListData ru = new ListData();
        ru.execute( Search );
    }*/

    public void ListData_Process(String Itemcode) {
        class ListData_Process extends AsyncTask<String, Void, String> {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                //loading = ProgressDialog.show(MachineAndSterile.this, "Please Wait",null, true, true);
            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                //loading.dismiss();
                try {
                    Response_Aux newsData;
                    JSONObject jsonObj = new JSONObject(s);
                    setRs = jsonObj.getJSONArray(TAG_RESULTS);
                    ArrayProgram.clear();
                    for(int i=0;i<setRs.length();i++){
                        newsData = new Response_Aux();
                        JSONObject c = setRs.getJSONObject(i);
                        newsData.setFields2(c.getString("Id"));
                        newsData.setFields1(c.getString("WashProcessID"));
                        if(c.getString("checked").equals("true")){
                            newsData.setIs_Check(true);
                        }
                        ArrayProgram.add( newsData );
                    }
                    final ListView lv1 = (ListView) findViewById(R.id.list_program);
                    lv1.setAdapter(new AuxSterileProgramItem( MachineAndWash.this, ArrayProgram));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("Itemcode",params[0]);
                Log.d("itemcode", data + "");
                String result = ruc.sendPostRequest(iFt.getListProgram_Item_Wash(),data);
                Log.d("itemcode", result + "");
                return  result;
            }
        }

        ListData_Process ru = new ListData_Process();
        ru.execute( Itemcode );
    }

    public void ListData_Process_sterile(String Itemcode) {
        class ListData_Process_sterile extends AsyncTask<String, Void, String> {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                //loading = ProgressDialog.show(MachineAndSterile.this, "Please Wait",null, true, true);
            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                //loading.dismiss();
                try {
                    Response_Aux newsData;
                    JSONObject jsonObj = new JSONObject(s);
                    setRs = jsonObj.getJSONArray(TAG_RESULTS);
                    ArrayProgram_Sterile.clear();
                    for(int i=0;i<setRs.length();i++){
                        newsData = new Response_Aux();
                        JSONObject c = setRs.getJSONObject(i);
                        newsData.setFields2(c.getString("Id"));
                        newsData.setFields1(c.getString("SterileProgram"));
                        if(c.getString("checked").equals("true")){
                            newsData.setIs_Check(true);
                        }
                        ArrayProgram_Sterile.add( newsData );
                    }
                    final ListView lv1 = (ListView) findViewById(R.id.list_program_1);
                    lv1.setAdapter(new AuxSterileProgramItem_Sterile( MachineAndWash.this, ArrayProgram_Sterile));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("Itemcode",params[0]);
                String result = ruc.sendPostRequest(iFt.getListProgram_Item(),data);
                Log.d("DKHVKCHD", data + "");
                Log.d("DKHVKCHD", result + "");
                return  result;
            }
        }

        ListData_Process_sterile ru = new ListData_Process_sterile();
        ru.execute( Itemcode );
    }

    @Override
    public void onBackPressed() {
        this.finish();
    }
}