package com.phc.cssd;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.phc.core.connect.HTTPConnect;
import com.phc.core.data.AsonData;
import com.phc.cssd.adapter.ImportNewItemStockAdapter;
import com.phc.cssd.adapter.ImportNoWashAdapter;
import com.phc.cssd.data.Master;
import com.phc.cssd.model.ModelImportWashDetail;
import com.phc.cssd.url.Url;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class CssdNewItemStockToWash extends Activity {

    //------------------------------------------------
    // Session Variable
    private String B_ID = null;
    //------------------------------------------------

    private ListView ListData;
    private ImageView image_save;

    private RadioButton radio_is_new;
    private RadioButton radio_no_wash;
    private EditText txt_search;
    private Button btn_search_item;

    private TextView txt;
    private String userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cssd_new_item_stock_to_wash);

        byIntent();

        byWidget();

        displayItemStock();
    }

    private void byIntent(){
        // Argument
        Intent intent = getIntent();
        B_ID = intent.getStringExtra("B_ID");
        userid = intent.getStringExtra("userid");
    }

    private void byWidget(){
        ListData = (ListView) findViewById(R.id.list);
        image_save = (ImageView) findViewById(R.id.image_save);
        radio_is_new = (RadioButton) findViewById(R.id.radio_is_new);
        radio_no_wash = (RadioButton) findViewById(R.id.radio_no_wash);
        txt_search = (EditText) findViewById(R.id.txt_search);
        txt = (TextView) findViewById(R.id.txt);
        btn_search_item = (Button) findViewById(R.id.btn_search_item);

        btn_search_item.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                displayItemStock();
            }
        });

        txt_search.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                displayItemStock();
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

        radio_is_new.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                txt.setText( radio_is_new.isChecked() ? "วันที่นำเข้า" : "จำนวน" );
                displayItemStock();
            }
        });

        radio_no_wash.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                txt.setText( radio_no_wash.isChecked() ? "จำนวน" : "วันที่นำเข้า" );
                displayItemStock();
            }
        });

        image_save.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){

                String LIST_ID = "";
                String itemcode = "";
                String itemqty = "";
                String QTY = "";

                List<ModelImportWashDetail> DATA_MODEL = MODEL_NEW_ITEM_STOCK;

                Iterator li = DATA_MODEL.iterator();

                if(radio_is_new.isChecked()) {
                    while (li.hasNext()) {
                        try {
                            ModelImportWashDetail m = (ModelImportWashDetail) li.next();

                            LIST_ID = m.getI_id();

                            if (m.isCheck()) {
                                itemcode += LIST_ID + "@";
                                itemqty +="1@";
                            }

                        } catch (Exception e) {
                            continue;
                        }
                    }
                }else{
                    while (li.hasNext()) {
                        try {
                            ModelImportWashDetail m = (ModelImportWashDetail) li.next();

                            LIST_ID = m.getI_id();
                            QTY = m.getI_qty();

                            if (m.isCheck()) {
                                itemcode += LIST_ID + "@";
                                itemqty +=QTY + "@";
                            }

                        } catch (Exception e) {
                            continue;
                        }
                    }
                }

                if(!itemcode.equals("")) {

                    if(radio_is_new.isChecked()) {
                        cssd_import_new_item_or_nowash(itemcode, itemqty,1);
                    }else{
                        cssd_import_new_item_or_nowash(itemcode, itemqty,2);
                    }

                }else{
                    Toast.makeText(CssdNewItemStockToWash.this, "ยังไม่ได้เลือกรายการ !!", Toast.LENGTH_SHORT).show();
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

    private HTTPConnect httpConnect = new HTTPConnect();
    private List<ModelImportWashDetail> MODEL_NEW_ITEM_STOCK = null;

    public void displayItemStock() {

        final String txt = txt_search.getText().toString();
        final boolean IsNewItemStock = radio_is_new.isChecked();

        class displayItemStock extends AsyncTask<String, Void, String> {

            //------------------------------------------------
            // Background Worker Process Variable
            private boolean Success = false;
            private ArrayList<String> data = null;
            private int size = 0;
            //------------------------------------------------

            // variable
            @Override
            protected void onPreExecute() {
                ListData.setAdapter(null);
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

                        MODEL_NEW_ITEM_STOCK = getImportWashDetail();

                        try {

                            ArrayAdapter<ModelImportWashDetail> adapter;

                            Log.d("KDJDKJ",radio_is_new.isChecked()+"");
                            if(radio_is_new.isChecked()) {
                                adapter = new ImportNewItemStockAdapter(CssdNewItemStockToWash.this, MODEL_NEW_ITEM_STOCK);
                            }else{
                                adapter = new ImportNoWashAdapter(CssdNewItemStockToWash.this, MODEL_NEW_ITEM_STOCK);
                            }

                            ListData.setAdapter(adapter);

                        } catch (Exception e) {
                            ListData.setAdapter(null);
                            e.printStackTrace();
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

                data.put("p_SterileProcessID", "-");

                if(!txt.equals("")){
                    data.put("p_filter", txt);
                }

                if(B_ID != null){
                    data.put("p_bid", B_ID);
                }

                String result = httpConnect.sendPostRequest(Url.URL + ( IsNewItemStock ? "cssd_display_import_new_item_stock.php" : "cssd_display_import_no_wash.php"), data);

                Log.d("tog_new_item","url = "+Url.URL + "cssd_display_import_new_item_stock.php");
                Log.d("KDJDKJ",data+"");
                Log.d("KDJDKJ",result+"");

                return result;
            }

            public List<ModelImportWashDetail> getImportWashDetail() throws Exception{

                List<ModelImportWashDetail> list = new ArrayList<>();

                try {
                    int index = 0;

                    for(int i=0;i<data.size();i+=size){

                        list.add(
                                new ModelImportWashDetail(
                                        index,
                                        false,
                                        data.get(i),
                                        data.get(i + 1),
                                        data.get(i + 2),
                                        data.get(i + 3),
                                        data.get(i + 4),
                                        data.get(i + 5),
                                        data.get(i + 6),
                                        data.get(i + 7),
                                        data.get(i + 8),
                                        data.get(i + 9),
                                        data.get(i + 10),
                                        data.get(i + 11),
                                        "-",
                                        ""
                                )
                        );
                        index++;
                    }

                }catch(Exception e){
                    e.printStackTrace();
                }

                return list;
            }

            // =========================================================================================
        }

        displayItemStock obj = new displayItemStock();
        obj.execute();
    }


    public void  cssd_import_new_item_or_nowash(final String itemcode, final String qty,final int type){

        Log.d("tog_new_item","itemcode = "+itemcode+" ---- qty ="+qty);

        class cssd_import_new_item_or_nowash extends AsyncTask<String, Void, String> {

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
                    JSONArray rs = jsonObj.getJSONArray("result");

                    for (int i = 0; i < rs.length(); i++) {

                        JSONObject c = rs.getJSONObject(i);

                        if (c.getString("flag").equals("true")) {
                            Toast.makeText(CssdNewItemStockToWash.this, "นำเข้าอุปกรณ์สำเร็จ !!", Toast.LENGTH_SHORT).show();
//                            Intent intent = new Intent();
//                            intent.putExtra("RETURN_VALUE", DATA);
//
//                            setResult(Master.CssdNewItemStockToWash, intent);

                            finish();
                        }else{
                            Toast.makeText(CssdNewItemStockToWash.this, "นำเข้าอุปกรณ์ไม่สำเร็จ !!", Toast.LENGTH_SHORT).show();
                        }

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();

                data.put("itemcode", itemcode);
                data.put("qty", qty);
                data.put("IsType", type+"");
                data.put("userid", userid);

                String result = null;

                try {
                    result = httpConnect.sendPostRequest(Url.URL + "cssd_import_new_item_to_wash.php", data);

                }catch(Exception e){
                    e.printStackTrace();
                }

                Log.d("tog_new_item","url = "+Url.URL + "cssd_import_new_item_to_wash.php");
                Log.d("tog_new_item","data = "+data);
                Log.d("tog_new_item","result = "+result);
                return result;
            }

            // =========================================================================================
        }

        cssd_import_new_item_or_nowash obj = new cssd_import_new_item_or_nowash();
        obj.execute();
    }
}