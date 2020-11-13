package com.phc.cssd;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.phc.core.connect.HTTPConnect;
import com.phc.core.data.AsonData;
import com.phc.cssd.adapter.SterileDetailDraftAdapter;
import com.phc.cssd.adapter.SterileDraftAdapter;
import com.phc.cssd.data.Master;
import com.phc.cssd.model.ModelSterile;
import com.phc.cssd.model.ModelSterileDetail;
import com.phc.cssd.url.Url;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CssdSterileDraft extends Activity {

    //------------------------------------------------

    private HTTPConnect httpConnect = new HTTPConnect();

    // Background Worker Process Variable
    private boolean Success = false;
    private ArrayList<String> data = null;
    private int size = 0;
    //------------------------------------------------

    private List<ModelSterile> MODEL_STERILE = null;
    private List<ModelSterileDetail> MODEL_STERILE_DETAIL = null;

    //------------------------------------------------
    // Session Variable
    private String STERILE_MACHINE_ID = null;
    private String B_ID = null;
    //------------------------------------------------

    // Widget Variable
    private LinearLayout main;
    private EditText edt_search;
    private TextView txt_caption;

    private ListView list_sterile;
    private GridView grid_sterile_detail;

    private ImageView imv_print;

    private ImageView imageBack;
    private Button btn_search_item;

    private String DocNo = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cssd_sterile_draft);

        // Permission StrictMode
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        byIntent();

        byWidget();

        onDisplay();

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void byIntent(){
        // Argument
        Intent intent = getIntent();
        STERILE_MACHINE_ID = intent.getStringExtra("STERILE_MACHINE_ID");
        B_ID = intent.getStringExtra("B_ID");
    }

    private void byWidget() {

        main = (LinearLayout) findViewById(R.id.main);

        edt_search = (EditText) findViewById(R.id.edt_search);
        txt_caption = (TextView) findViewById(R.id.txt_caption);

        btn_search_item = (Button) findViewById(R.id.btn_search_item);

        btn_search_item.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                onDisplay();
            }
        });

        edt_search.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                onDisplay();
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

        list_sterile = (ListView) findViewById(R.id.list_sterile);
        grid_sterile_detail = (GridView) findViewById(R.id.grid_sterile_detail);

        list_sterile.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // Selected item
                //DocNo = ((TextView)((LinearLayout) view).getChildAt(0)).getText().toString();

                DocNo = ((TextView)((LinearLayout)((LinearLayout)((LinearLayout) view).getChildAt(0)).getChildAt(0)).getChildAt(3)).getText().toString();

                System.out.println("DocNo = " + DocNo);

                onDisplayDetail(DocNo);

            }
        });

        list_sterile.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                DocNo = ((TextView)((LinearLayout)((LinearLayout)((LinearLayout) view).getChildAt(0)).getChildAt(0)).getChildAt(3)).getText().toString();

                returnData();

                return true;

            }
        });

        imv_print = (ImageView) findViewById(R.id.imv_print);

        imv_print.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                returnData();
            }
        });

        imageBack = (ImageView) findViewById(R.id.imageBack);
        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        imageBack.bringToFront();
        txt_caption.bringToFront();
    }

    public void returnData() {
        Intent intent = new Intent();
        intent.putExtra("RETURN_DATA", DocNo);
        intent.putExtra("RETURN_VALUE", DocNo);
        setResult(Master.CssdSterileDraft, intent);
        finish();
    }

    public void onDisplay() {
        class DisplaySterile extends AsyncTask<String, Void, String> {

            final String str_search = edt_search.getText().toString();

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
                        MODEL_STERILE = getModelSterile();
                    } catch (Exception e) {
                        e.printStackTrace();
                        return;
                    }

                    //Clear
                    grid_sterile_detail.setAdapter(null);

                    try {
                        ArrayAdapter<ModelSterile> adapter = new SterileDraftAdapter(CssdSterileDraft.this, MODEL_STERILE);
                        list_sterile.setAdapter(adapter);
                    } catch (Exception e) {
                        list_sterile.setAdapter(null);
                        e.printStackTrace();
                    }
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();

                data.put("p_machine_id", STERILE_MACHINE_ID);

                if(!str_search.trim().equals("")) {
                    data.put("p_draft_docno", str_search);
                }

                if(B_ID != null){
                    data.put("p_bid", B_ID);
                }

                String result = httpConnect.sendPostRequest(Url.URL + "cssd_display_sterile.php", data);
                Log.d("FKHKDHI",data+"");

                return result;
            }

            private List<ModelSterile> getModelSterile() throws Exception{

                List<ModelSterile> list = new ArrayList<>();

                try {
                    int index = 0;

                    for(int i=0;i<data.size();i+=size){

                        list.add(
                                getSterile(
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
                                        data.get(i + 12),
                                        data.get(i + 13),
                                        data.get(i + 14),
                                        data.get(i + 15),
                                        data.get(i + 16),
                                        data.get(i + 17),
                                        data.get(i + 18),
                                        data.get(i + 19),
                                        data.get(i + 20),
                                        data.get(i + 21),
                                        data.get(i + 22),
                                        data.get(i + 23),
                                        data.get(i + 24),
                                        data.get(i + 25),
                                        data.get(i + 26),
                                        data.get(i + 27),
                                        data.get(i + 28),
                                        index
                                )
                        );

                        index++;
                    }

                }catch(Exception e){
                    e.printStackTrace();
                }

                return list;
            }

            private ModelSterile getSterile(
                    String ID,
                    String DocNo,
                    String DocDate,
                    String ModifyDate,
                    String UserCode,
                    String PrepareCode,
                    String ApproveCode,
                    String DeptID,
                    String Qty,
                    String IsStatus,
                    String sterileProgramID,
                    String sterileMachineID,
                    String sterileRoundNumber,
                    String StartTime,
                    String FinishTime,
                    String s_time,
                    String f_time,
                    String Remark,
                    String IsOccurance,
                    String SterileName,
                    String MachineName,
                    String usr_sterile,
                    String usr_prepare,
                    String usr_approve,
                    String PrintAll,
                    String PrintCount,
                    String TestProgramID,
                    String TestProgramName,
                    String Batch,
                    int index
            ){
                return new ModelSterile(
                        ID,
                        DocNo,
                        DocDate,
                        ModifyDate,
                        UserCode,
                        PrepareCode,
                        ApproveCode,
                        DeptID,
                        Qty,
                        IsStatus,
                        sterileProgramID,
                        sterileMachineID,
                        sterileRoundNumber,
                        StartTime,
                        FinishTime,
                        s_time,
                        f_time,
                        Remark,
                        IsOccurance,
                        SterileName,
                        MachineName,
                        usr_sterile,
                        usr_prepare,
                        usr_approve,
                        PrintAll,
                        PrintCount,
                        TestProgramID,
                        TestProgramName,
                        Batch,
                        index
                );
            }
        }

        DisplaySterile obj = new DisplaySterile();
        obj.execute();
    }

    public void onDisplayDetail(final String p_docno) {
        class DisplayDetail extends AsyncTask<String, Void, String> {

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
                        MODEL_STERILE_DETAIL = getModelSterileDetail();
                    } catch (Exception e) {
                        grid_sterile_detail.setAdapter(null);
                        MODEL_STERILE_DETAIL = null;
                        e.printStackTrace();
                        return;
                    }

                    try {
                        ArrayAdapter<ModelSterileDetail> adapter = new SterileDetailDraftAdapter(CssdSterileDraft.this, MODEL_STERILE_DETAIL);
                        grid_sterile_detail.setAdapter(adapter);
                    } catch (Exception e) {
                        grid_sterile_detail.setAdapter(null);
                        MODEL_STERILE_DETAIL = null;
                        e.printStackTrace();
                    }
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();

                data.put("p_docno", p_docno);

                if(B_ID != null){
                    data.put("p_bid", B_ID);
                }

                String result = httpConnect.sendPostRequest(Url.URL + "cssd_display_sterile_detail.php", data);

                return result;
            }

            private List<ModelSterileDetail> getModelSterileDetail() throws Exception{

                List<ModelSterileDetail> list = new ArrayList<>();

                try {
                    int index = 0;

                    for(int i=0;i<data.size();i+=size){

                        list.add(
                                getSterileDetail(
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
                                        data.get(i + 12),
                                        data.get(i + 13),
                                        data.get(i + 14),
                                        data.get(i + 15),
                                        data.get(i + 16),
                                        data.get(i + 17),
                                        data.get(i + 18),
                                        data.get(i + 19),
                                        data.get(i + 20),
                                        data.get(i + 21),
                                        data.get(i + 22),
                                        data.get(i + 23),
                                        data.get(i + 24),
                                        data.get(i + 25),
                                        data.get(i + 26),
                                        index
                                )
                        );

                        index++;
                    }

                    // //System.out.println("list = " + list.size());

                }catch(Exception e){
                    e.printStackTrace();
                }

                return list;
            }

            private ModelSterileDetail getSterileDetail(
                    String ID,
                    String DocNo,
                    String ItemStockID,
                    String Qty,
                    String itemname,

                    String itemcode,
                    String UsageCode,
                    String age,
                    String ImportID,
                    String SterileDate,

                    String ExpireDate,
                    String IsStatus,
                    String OccuranceQty,
                    String DepName,
                    String DepName2,

                    String LabelID,
                    String usr_sterile,
                    String usr_prepare,
                    String usr_approve,
                    String SterileRoundNumber,

                    String MachineName,
                    String Price,
                    String Time,
                    String SterileProgramID,
                    String qty_Print,

                    String printer,
                    String UsageCount,
                    int index
            ){
                return new ModelSterileDetail(
                        ID,
                        DocNo,
                        ItemStockID,
                        Qty,
                        itemname,

                        itemcode,
                        UsageCode,
                        age,
                        ImportID,
                        SterileDate,

                        ExpireDate,
                        IsStatus,
                        OccuranceQty,
                        DepName,
                        DepName2,

                        LabelID,
                        usr_sterile,
                        usr_prepare,
                        usr_approve,
                        SterileRoundNumber,

                        MachineName,
                        Price,
                        Time,
                        SterileProgramID,
                        qty_Print,

                        printer,
                        UsageCount,
                        index
                );
            }

            // =========================================================================================
        }

        DisplayDetail obj = new DisplayDetail();
        obj.execute();
    }


    private ModelSterileDetail getSterileDetail(
            String ID,
            String DocNo,
            String ItemStockID,
            String Qty,
            String itemname,

            String itemcode,
            String UsageCode,
            String age,
            String ImportID,
            String SterileDate,

            String ExpireDate,
            String IsStatus,
            String OccuranceQty,
            String DepName,
            String DepName2,

            String LabelID,
            String usr_sterile,
            String usr_prepare,
            String usr_approve,
            String SterileRoundNumber,

            String MachineName,
            String Price,
            String Time,
            String Qty_Print,
            int index
    ){
        return new ModelSterileDetail(
                ID,
                DocNo,
                ItemStockID,
                Qty,
                itemname,

                itemcode,
                UsageCode,
                age,
                ImportID,
                SterileDate,

                ExpireDate,
                IsStatus,
                OccuranceQty,
                DepName,
                DepName2,

                LabelID,
                usr_sterile,
                usr_prepare,
                usr_approve,
                SterileRoundNumber,

                MachineName,
                Price,
                Time,
                Qty_Print,
                index
        );
    }

}
