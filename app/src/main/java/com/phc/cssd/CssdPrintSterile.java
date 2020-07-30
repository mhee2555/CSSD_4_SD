package com.phc.cssd;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.StrictMode;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.phc.core.connect.HTTPConnect;
import com.phc.core.data.AsonData;
import com.phc.cssd.data.PreviewSticker;
import com.phc.cssd.model.ModelSterileDetail;
import com.phc.cssd.print_sticker.PrintSticker;
import com.phc.cssd.url.Url;
import com.phc.cssd.url.getUrl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CssdPrintSterile extends Activity {

    private List<ModelSterileDetail> MODEL_STERILE_DETAIL = null;

    getUrl iFt = new getUrl();
    HTTPConnect ruc = new HTTPConnect();
    // Widget Variable
    private TextView edt_qty;
    private Button btn_print;
    private Button btn_plus;
    private Button btn_minus;
    private TextView txt_item_name;

    private String B_ID = null;
    String Heigth = String.valueOf(0);
    String Width = String.valueOf(0);

    String ID;
    String DocNo;
    String ItemStockID;
    String Qty;
    String itemname;

    String itemcode;
    String UsageCode;
    String age;
    String ImportID;
    String SterileDate;

    String ExpireDate;
    String IsStatus;
    String OccuranceQty;
    String DepName;
    String DepName2;

    String LabelID;
    String usr_sterile;
    String usr_prepare;
    String usr_approve;
    String SterileRoundNumber;

    String MachineName;
    String Price;
    String Time;

    String Qty_Print = "1";
    String ItemSetData;

    boolean Print = true;


    // =============================================================================================
    // -- Printing...
    // =============================================================================================
    private HTTPConnect httpConnect = new HTTPConnect();
    PreviewSticker p = new PreviewSticker("");
    PrintSticker PSK = new PrintSticker();

    // =============================================================================================
    // -- Get Printer
    // =============================================================================================
    ArrayList<Integer> PRINTER = new ArrayList<>();
    ArrayList<String> PRINTER_IP = new ArrayList<>();

    public void declarePrinter() {

        class DeclarePrinter extends AsyncTask<String, Void, String> {

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
                    try{
                        for(int i=0;i<data.size();i+=size) {
                            PRINTER.add( Integer.valueOf(data.get(i)).intValue() );
                            PRINTER_IP.add(data.get(i+1));
                            PRINTER_IP.add(data.get(i+2));
                            PRINTER_IP.add(data.get(i+3));
                        }
                    }catch(Exception e){
                        e.printStackTrace();
                        return;
                    }
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();

                if(B_ID != null){
                    data.put("p_bid", B_ID);
                }

                String result = httpConnect.sendPostRequest(Url.URL + "cssd_select_label.php", data);

                System.out.println("URL = " + result);

                return result;
            }

            // =========================================================================================
        }

        DeclarePrinter obj = new DeclarePrinter();
        obj.execute();
    }

    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cssd_print_sterile);

        // Permission StrictMode
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        byIntent();

        byWidget();

        declarePrinter();

    }

    private void byIntent(){
        // Argument

        Intent intent = getIntent();
        B_ID = intent.getStringExtra("B_ID");
        Width = intent.getStringExtra("Width");
        Heigth = intent.getStringExtra("Heigth");
        ID = intent.getStringExtra("ID");
        DocNo = intent.getStringExtra("DocNo");
        ItemStockID = intent.getStringExtra("ItemStockID");
        Qty = intent.getStringExtra("Qty");
        itemname = intent.getStringExtra("itemname");

        itemcode = intent.getStringExtra("itemcode");
        UsageCode = intent.getStringExtra("UsageCode");
        age = intent.getStringExtra("age");
        ImportID = intent.getStringExtra("ImportID");
        SterileDate = intent.getStringExtra("SterileDate");

        ExpireDate = intent.getStringExtra("ExpireDate");
        IsStatus = intent.getStringExtra("IsStatus");
        OccuranceQty = intent.getStringExtra("OccuranceQty");
        DepName = intent.getStringExtra("DepName");
        DepName2 = intent.getStringExtra("DepName2");

        LabelID = intent.getStringExtra("LabelID");
        Log.d("BANKQQQ",LabelID);
        usr_sterile = intent.getStringExtra("usr_sterile");
        usr_prepare = intent.getStringExtra("usr_prepare");
        usr_approve = intent.getStringExtra("usr_approve");
        SterileRoundNumber = intent.getStringExtra("SterileRoundNumber");

        MachineName = intent.getStringExtra("MachineName");
        Price = intent.getStringExtra("Price");
        Time = intent.getStringExtra("Time");

        Qty_Print = intent.getStringExtra("Qty_Print");
        ItemSetData = intent.getStringExtra("ItemSetData");


        List<ModelSterileDetail> list = new ArrayList<>();
        list.add(
                getSterileDetail(
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
                        Time,"0",
                        Qty_Print,

                        "0",
                        "0",
                        ItemSetData,
                        "",
                        "",
                        0
                )
        );
        MODEL_STERILE_DETAIL = list;
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
            String SterileProcessID,
            String PrintCount,

            String Printer,
            String UsageCount,
            String ItemSetData,
            String BasketName,
            String IsRemarkExpress,
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
                SterileProcessID,
                PrintCount,

                Printer,
                UsageCount,
                ItemSetData,
                BasketName,
                IsRemarkExpress,
                index
        );
    }

    private void byWidget() {
        edt_qty = (TextView) findViewById(R.id.edt_qty);

        btn_print = (Button) findViewById(R.id.btn_print);
        btn_plus = (Button) findViewById(R.id.btn_plus);
        btn_minus = (Button) findViewById(R.id.btn_minus);
        txt_item_name = (TextView) findViewById(R.id.txt_item_name);

        btn_print.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                try {
                    if(!edt_qty.getText().toString().equals("")) {

                        btn_print.setBackgroundResource(R.drawable.ic_popup_print_grey);

                        onPrint();
                    }
                }catch (Exception e){

                }
            }
        });

        btn_plus.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                try {
                    if(edt_qty.getText().toString().trim().equals("")) {
                        edt_qty.setText("1");
                    }else{
                        int Qty = Integer.valueOf(edt_qty.getText().toString()).intValue();

                        edt_qty.setText(Integer.toString(Qty + 1));
                    }
                }catch (Exception e){
                    edt_qty.setText("0");
                }
            }
        });

        btn_minus.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                try {
                    if(edt_qty.getText().toString().equals("")) {
                        edt_qty.setText("1");
                    }else{
                        int Qty = Integer.valueOf(edt_qty.getText().toString()).intValue();

                        if(Qty > 1)
                            edt_qty.setText(Integer.toString(Qty - 1));
                    }
                }catch (Exception e){
                    edt_qty.setText("0");
                }
            }
        });

        txt_item_name.setText("รายการ " + UsageCode + " : " + itemname);

    }

    private void onPrint() throws Exception {
        final Handler handler0 = new Handler();
        handler0.postDelayed(new Runnable() {
            @Override
            public void run() {
                int Label = Integer.valueOf(LabelID).intValue();
                try {
                    PSK.PrintSticker(CssdPrintSterile.this,Label,MODEL_STERILE_DETAIL,PRINTER_IP,B_ID,Print);
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }, 0);

    }

}
