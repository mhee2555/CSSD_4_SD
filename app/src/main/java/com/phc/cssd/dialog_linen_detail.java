package com.phc.cssd;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;

import com.phc.core.connect.HTTPConnect;
import com.phc.core.string.Cons;
import com.phc.cssd.R;
import com.phc.cssd.adapter.ListStockLinenDetailAdapter;
import com.phc.cssd.model.ModelLinenDetail;
import com.phc.cssd.url.Url;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class dialog_linen_detail extends Activity {

    Button back,save;
    EditText search_name,search_scan;
    CheckBox chk_all;
    ListView item;

    private JSONArray rs = null;
    private String TAG_RESULTS="result";
    private HTTPConnect httpConnect = new HTTPConnect();
    private List<ModelLinenDetail> Model_Linen = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_linen_detail);

        initialize();

    }

    private void initialize(){
        back = (Button) findViewById(R.id.back);
        save = (Button) findViewById(R.id.save);
        search_name = (EditText) findViewById(R.id.search_name);
        search_scan = (EditText) findViewById(R.id.search_scan);
        chk_all = (CheckBox) findViewById(R.id.chk_all);
        item = (ListView) findViewById(R.id.item);
    }

    public void getlistdata() {
        class getlistdata extends AsyncTask<String, Void, String> {
            private ProgressDialog dialog = new ProgressDialog(dialog_linen_detail.this);
            // variable
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                this.dialog.setTitle(Cons.WAIT_FOR_PROCESS);
                this.dialog.show();
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);

                try {
                    JSONObject jsonObj = new JSONObject(result);
                    rs = jsonObj.getJSONArray(TAG_RESULTS);
                    List<ModelLinenDetail> list = new ArrayList<>();
                    for(int i=0;i<rs.length();i++){
                        JSONObject c = rs.getJSONObject(i);
                        list.add(
                                get(
                                        c.getString("Itemname"),
                                        c.getString("UsageCode"),
                                        c.getString("PackDate"),
                                        c.getString("ExpireDate"),
                                        c.getString("Date")
                                )
                        );
                    }
                    Model_Linen = list;
                    ArrayAdapter<ModelLinenDetail> adapter;
                    adapter = new ListStockLinenDetailAdapter(dialog_linen_detail.this, Model_Linen);
                    item.setAdapter(adapter);
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
                String result = null;
                try {
                    result = httpConnect.sendPostRequest(Url.URL + "cssd_display_spore_doc.php", data);
                    Log.d("BFGDH",result);
                    Log.d("BFGDH",data+"");
                }catch(Exception e){
                    e.printStackTrace();
                }
                return result;
            }
            private ModelLinenDetail get(String Itemname, String UsageCode, String PackDate, String ExpireDate, String Date) {
                return new ModelLinenDetail(
                        Itemname,
                        UsageCode,
                        PackDate,
                        ExpireDate,
                        Date);
            }
            // =========================================================================================
        }

        getlistdata obj = new getlistdata();
        obj.execute();
    }
}