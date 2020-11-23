package com.phc.cssd;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.phc.core.connect.HTTPConnect;
import com.phc.core.data.AsonData;
import com.phc.cssd.adapter.CheckListAdapter;
import com.phc.cssd.adapter.ItemStockDepartmentAdapter;
import com.phc.cssd.model.ModelMasterData;
import com.phc.cssd.url.Url;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class CssdCreateItemStockDepartment extends Activity {
    //------------------------------------------------
    private boolean Success = false;
    private ArrayList<String> data = null;
    private int size = 0;
    //------------------------------------------------
    // Session Variable
    private String userid;
    private String B_ID = null;
    private String Item_Code;
    private String Item_Name;
    private String Item_Stock;
    private int check_true;
    //------------------------------------------------
    private ListView ListData;
    private ImageView image_save;
    private CheckBox Check_all;
    private RadioButton use_1,use_2;
    EditText etxt_searchsendsterile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cssd_create_item_stock_department);
        byIntent();
        byWidget();
        displayItemStockDepartment(Item_Code,"");
    }

    private void byIntent(){
        // Argument
        Intent intent = getIntent();
        Item_Code = intent.getStringExtra("Item_Code");
        Item_Name = intent.getStringExtra("Item_Name");
        Item_Stock = intent.getStringExtra("Item_Stock");
        userid = intent.getStringExtra("userid");
        B_ID = intent.getStringExtra("B_ID");
    }

    public void Check_All(boolean Check){
        if (Check == true){
            Check_all.setChecked(true);
        }else {
            Check_all.setChecked(false);
        }
    }

    private void byWidget(){
        use_1 = (RadioButton) findViewById(R.id.use_1);
        use_2 = (RadioButton) findViewById(R.id.use_2);

//        for (int i = 0 ; i < MODEL_ITEM_STOCK_DEPARTMENT.size() ; i ++){
//            if (MODEL_ITEM_STOCK_DEPARTMENT.get(i).isCheck() == true){
//                check_true ++;
//            }
//        }

        Check_all = (CheckBox) findViewById(R.id.Check_all);
        Check_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Check_all.isChecked() == true){
                    for (int i = 0 ; i < MODEL_ITEM_STOCK_DEPARTMENT.size() ; i ++){
                        MODEL_ITEM_STOCK_DEPARTMENT.get(i).setCheck(true);
                        ArrayAdapter<ModelMasterData> adapter = new ItemStockDepartmentAdapter(CssdCreateItemStockDepartment.this, MODEL_ITEM_STOCK_DEPARTMENT);
                        ListData.setAdapter(adapter);
                    }
                }else {
                    for (int i = 0 ; i < MODEL_ITEM_STOCK_DEPARTMENT.size() ; i ++){
                        MODEL_ITEM_STOCK_DEPARTMENT.get(i).setCheck(false);
                        ArrayAdapter<ModelMasterData> adapter = new ItemStockDepartmentAdapter(CssdCreateItemStockDepartment.this, MODEL_ITEM_STOCK_DEPARTMENT);
                        ListData.setAdapter(adapter);
                    }
                }
            }
        });
        ListData = (ListView) findViewById(R.id.list);
        image_save = (ImageView) findViewById(R.id.image_save);
        etxt_searchsendsterile = (EditText) findViewById(R.id.etxt_searchsendsterile);
        etxt_searchsendsterile.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                displayItemStockDepartment(Item_Code,etxt_searchsendsterile.getText().toString().replace(' ','%'));
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        image_save.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                String LIST_ID = "";
                String DATA = "";
                List<ModelMasterData> DATA_MODEL = MODEL_ITEM_STOCK_DEPARTMENT;
                Iterator li = DATA_MODEL.iterator();
                while(li.hasNext()) {
                    try {
                        ModelMasterData m = (ModelMasterData) li.next();
                        LIST_ID = m.getCode();
                        if (m.isCheck()) {
                            DATA += LIST_ID + "@";
                        }
                    }catch(Exception e){
                        continue;
                    }
                }
                if(!DATA.equals("")) {
                    onSave(DATA);
                }else{
                    Toast.makeText(CssdCreateItemStockDepartment.this, "ยังไม่ได้เลือกรายการ !!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onDestroy();
        finish();
    }

    public void onSave(final String p_data) {
        class AddItemStockDepartment extends AsyncTask<String, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                AsonData ason = new AsonData(result);
                Success = ason.isSuccess();
                size = ason.getSize();
                data = ason.getASONData();
                if(Success && data != null) {
                    Toast.makeText(CssdCreateItemStockDepartment.this, "เพิ่มรายการสำเร็จ " + data.get(0) + " รายการ.", Toast.LENGTH_SHORT).show();
                    finish();
                }else{
                    Toast.makeText(CssdCreateItemStockDepartment.this, "ไม่สามารถเพิ่มรายการได้ !!", Toast.LENGTH_SHORT).show();
                }
            }

            @SuppressLint("WrongThread")
            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("p_item_code", Item_Code);
                data.put("p_data", p_data);
                data.put("key", etxt_searchsendsterile.getText().toString());
                data.put("userid", userid);
                if(B_ID != null){
                    data.put("p_bid", B_ID);
                }
                String result = httpConnect.sendPostRequest(Url.URL + "cssd_create_item_stock_department.php", data);
                Log.d("DKJJDK",data+"");
                Log.d("DKJJDK",result+"");
                return result;
            }
        }
        AddItemStockDepartment obj = new AddItemStockDepartment();
        obj.execute();
    }

    private HTTPConnect httpConnect = new HTTPConnect();
    private List<ModelMasterData> MODEL_ITEM_STOCK_DEPARTMENT = null;
    public void displayItemStockDepartment(final String p_itemcode,final String key) {
        if(p_itemcode == null || p_itemcode.equals("")){
            return ;
        }
        class DisplayItemStockDepartment extends AsyncTask<String, Void, String> {
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
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                AsonData ason = new AsonData(result);
                Success = ason.isSuccess();
                size = ason.getSize();
                data = ason.getASONData();
                if(Success && data != null) {
                    try {
                        MODEL_ITEM_STOCK_DEPARTMENT = getDepartmentItemStock();
                        try {
                            ArrayAdapter<ModelMasterData> adapter = new ItemStockDepartmentAdapter(CssdCreateItemStockDepartment.this, MODEL_ITEM_STOCK_DEPARTMENT);
                            ListData.setAdapter(adapter);
                        } catch (Exception e) {
                            ListData.setAdapter(null);
                            e.printStackTrace();
                        }

                        int chk = 0;
                        int unchk = 0;

                        for (int i = 0 ; i < MODEL_ITEM_STOCK_DEPARTMENT.size() ; i ++){
                            if (MODEL_ITEM_STOCK_DEPARTMENT.get(i).isCheck() == true){
                                chk ++;
                            }else {
                                unchk ++;
                            }
                        }

                        if (chk == 1){
                            use_1.setChecked(true);
                            use_2.setChecked(false);
                        }else {
                            use_1.setChecked(false);
                            use_2.setChecked(true);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        return;
                    }
                }else{
                    ListData.setAdapter(null);
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("p_itemcode", p_itemcode);
                data.put("key", key);
                if(B_ID != null){
                    data.put("p_bid", B_ID);
                }
                String result = httpConnect.sendPostRequest(Url.URL + "cssd_display_item_stock_department.php", data);
                return result;
            }
            private List<ModelMasterData> getDepartmentItemStock() throws Exception{
                List<ModelMasterData> list = new ArrayList<>();
                try {
                    int index = 0;
                    for(int i=0;i<data.size();i+=size){
                        list.add(
                                get(
                                        data.get(i),
                                        data.get(i + 1),
                                        data.get(i + 2).equals("1"),
                                        index
                                )
                        );
                        index++;
                    }
                    // System.out.println("list = " + list.size());
                }catch(Exception e){
                    e.printStackTrace();
                }
                return list;
            }
            private ModelMasterData get(
                    String code, 
                    String name, 
                    boolean isCheck, 
                    int index
            ){
                return new ModelMasterData(
                        code,
                        name,
                        isCheck,
                        index
                );
            }
            // =========================================================================================
        }
        DisplayItemStockDepartment obj = new DisplayItemStockDepartment();
        obj.execute();
    }
}