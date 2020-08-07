package com.phc.cssd;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.usb.UsbAccessory;
import android.hardware.usb.UsbConstants;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbInterface;
import android.hardware.usb.UsbManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.phc.core.connect.HTTPConnect;
import com.phc.core.data.AsonData;
import com.phc.core.date.DateTime;
import com.phc.core.string.FormatDate;
import com.phc.core.string.Text2Digit;
import com.phc.core.string.TextAsBitmap;
import com.phc.core.string.TextRotation;
import com.phc.core.string.TextSplitName;
import com.phc.core.string.TextTwoLine;
import com.phc.cssd.adapter.SearchSterileAdapter;
import com.phc.cssd.adapter.SearchSterileDetailAdapter;
import com.phc.cssd.adapter.SterileDetailAdapter;
import com.phc.cssd.data.PreviewSticker;
import com.phc.cssd.data.SterileDetailItemSet;
import com.phc.cssd.model.ModelSterile;
import com.phc.cssd.model.ModelSterileDetail;
import com.phc.cssd.model.ModelWashDetailForPrint;
import com.phc.cssd.print_sticker.PrintSticker;
import com.phc.cssd.print_sticker.PrintWash;
import com.phc.cssd.properties.pCustomer;
import com.phc.cssd.url.Url;
import com.phc.cssd.url.getUrl;
import com.phc.cssd.usb.TscWifi;

//import com.phc.cssd.usb.TSCUSBActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import static com.phc.cssd.CssdTscPrintLabel.updatePrintStatus;

public class CssdSearchSterile extends AppCompatActivity {

    //------------------------------------------------
    private String TAG_RESULTS="result";
    private JSONArray rs = null;
    private HTTPConnect httpConnect = new HTTPConnect();
    getUrl iFt = new getUrl();
    HTTPConnect ruc = new HTTPConnect();
    // Background Worker Process Variable
    private boolean Success = false;
    private ArrayList<String> data = null;
    private int size = 0;
    private String ID = null;
    //------------------------------------------------

    private List<ModelSterile> MODEL_STERILE = null;
    private List<ModelSterileDetail> MODEL_STERILE_DETAIL = null;

    PrintSticker PSK = new PrintSticker();

    boolean Print = true;

    //------------------------------------------------
    // Session Variable
    private String userid = null;
    private String B_ID = null;
    //------------------------------------------------

    // Widget Variable
    private LinearLayout main;
    private EditText txt_search;
    private TextView txt_caption;
    TextView gDate;
    Button bDate;

    private ListView list_sterile;
    private ListView list_sterile_detail;

    private ImageView imv_print;

    private ImageView imageBack;
    private Button btn_search_item;

    static final int DATE_PICKER_ID = 1111;
    private int year;
    private int month;
    private int day;
    int xItemname1 = 0;
    int yItemname1 = 0;
    int xUsagecode1 = 0;
    int yUasgecode1 = 0;
    int xPrice = 0;
    int yPrice = 0;
    int xDept1 = 0;
    int yDept1 = 0;
    int xMachine = 0;
    int yMahcine = 0;
    int xRound = 0;
    int yRound = 0;
    int xPackdate = 0;
    int yPackdate = 0;
    int xExp = 0;
    int yExp = 0;
    int xEmp1 = 0;
    int yEmp1 = 0;
    int xEmp2 = 0;
    int yEmp2 = 0;
    int xItemname2 = 0;
    int yItemname2 = 0;
    int xDept2 = 0;
    int yDept2 = 0;
    int xUsagecode2 = 0;
    int yUsagecode2 = 0;
    int xQrcode1 = 0;
    int yQrcode1 = 0;
    int xQrcode2 = 0;
    int yQrcode2 = 0;
    int xPackdate2 = 0;
    int yPackdate2 = 0;
    int xExp2 = 0;
    int yExp2 = 0;
    int xEmp3 = 0;
    int yEmp3 = 0;
    // =============================================================================================

    @Override
    protected Dialog onCreateDialog(int id) {

        Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);


        switch (id) {
            case DATE_PICKER_ID:return new DatePickerDialog(this, pickerListener, mYear, mMonth, mDay);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener pickerListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
            year  = selectedYear;
            month = selectedMonth;
            day   = selectedDay;

            gDate.setText( Text2Digit.twoDigit(Integer.toString(day)) + "-" +  Text2Digit.twoDigit(Integer.toString(month + 1)) + "-" + year );

            onDisplay();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cssd_search_sterile);

        // Permission StrictMode
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        getSupportActionBar().hide();

        byIntent();

        byWidget();

        declarePrinter();

        onDisplay();

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void byIntent(){
        // Argument
        Intent intent = getIntent();
        userid = intent.getStringExtra("userid");
        B_ID = intent.getStringExtra("B_ID");
    }

    private void byWidget() {
        main = (LinearLayout) findViewById(R.id.main);

        txt_search = (EditText) findViewById(R.id.txt_search);
        txt_caption = (TextView) findViewById(R.id.txt_caption);

        gDate = (TextView) findViewById(R.id.gDate);
        bDate = (Button) findViewById(R.id.bDate);
        gDate.setText(DateTime.getDate());
        gDate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDialog(DATE_PICKER_ID);
            }
        });

        bDate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDialog(DATE_PICKER_ID);
            }
        });

        btn_search_item = (Button) findViewById(R.id.btn_search_item);

        btn_search_item.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                onDisplay();
            }
        });

        txt_search.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                onDisplay();
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

        list_sterile = (ListView) findViewById(R.id.list_sterile);
        list_sterile_detail = (ListView) findViewById(R.id.list_sterile_detail);

        // listening to single list item on click
        list_sterile.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // selected item
                String DocNo = ((TextView)((LinearLayout) view).getChildAt(0)).getText().toString();

                //System.out.println(DocNo);

                onDisplayDetail(DocNo);

            }
        });

        imv_print = (ImageView) findViewById(R.id.imv_print);

        imv_print.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                try {
                    onPrint();
                }catch (Exception e){

                }
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

    private List<ModelSterileDetail> getItemLabel(String Label){
        try {
            List<ModelSterileDetail> list = new ArrayList<>();
            List<ModelSterileDetail> DATA_MODEL = MODEL_STERILE_DETAIL;

            Iterator li = DATA_MODEL.iterator();

            String LABEL_ID = null;

            while(li.hasNext()) {
                ModelSterileDetail m = (ModelSterileDetail) li.next();
                LABEL_ID = m.getLabelID();

                if (LABEL_ID.equals(Label) && m.isCheck()) {
                    list.add(
                            getSterileDetail(
                                    m.getID(),
                                    m.getDocNo(),
                                    m.getItemStockID(),
                                    m.getQty(),
                                    m.getItemname(),

                                    m.getItemcode(),
                                    m.getUsageCode(),
                                    m.getAge(),
                                    m.getImportID(),
                                    m.getSterileDate(),

                                    m.getExpireDate(),
                                    m.getIsStatus(),
                                    m.getOccuranceQty(),
                                    m.getDepName(),
                                    m.getDepName2(),

                                    m.getLabelID(),
                                    m.getUsr_sterile(),
                                    m.getUsr_prepare(),
                                    m.getUsr_approve(),
                                    m.getSterileRoundNumber(),

                                    m.getMachineName(),
                                    m.getPrice(),
                                    m.getTime(),
                                    m.getSterileProcessID(),
                                    m.getQty_Print(),

                                    m.getPrinter(),
                                    m.getUsageCount(),
                                    m.getItemSetData(),
                                    m.getIndex(),
                                    m.getIsRemarkExpress()
                            )
                    );
                }
            }

            return list;

        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public void onDisplay() {
        class DisplaySterile extends AsyncTask<String, Void, String> {

            final String str_search = txt_search.getText().toString();
            final String str_search_date = gDate.getText().toString();

            // variable
            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                list_sterile.setAdapter(null);
                list_sterile_detail.setAdapter(null);
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
                    list_sterile_detail.setAdapter(null);

                    try {
                        ArrayAdapter<ModelSterile> adapter = new SearchSterileAdapter(CssdSearchSterile.this, MODEL_STERILE);
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

                if(B_ID != null){
                    data.put("p_bid", B_ID);
                }

                //if(!str_search.trim().equals("")) {
                data.put("p_search_docno", str_search);
                //}

                if(!str_search_date.trim().equals("")) {
                    data.put("p_search_doc_date", DateTime.convertDate(str_search_date));
                }

                String result = httpConnect.sendPostRequest(Url.URL + "cssd_display_sterile.php", data);

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
                        e.printStackTrace();
                        return;
                    }

                    try {
                        ArrayAdapter<ModelSterileDetail> adapter = new SearchSterileDetailAdapter(CssdSearchSterile.this, MODEL_STERILE_DETAIL);
                        list_sterile_detail.setAdapter(adapter);
                    } catch (Exception e) {
                        list_sterile_detail.setAdapter(null);
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

                String result = httpConnect.sendPostRequest(Url.URL + "cssd_display_sterile_detail_seaech.php", data);

                return result;
            }

            private List<ModelSterileDetail> getModelSterileDetail() {

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
                                        data.get(i + 27),
                                        index,
                                        data.get(i + 28)
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
                    String SterileProcessID,
                    String PrintCount,

                    String Printer,
                    String UsageCount,
                    String ItemSetData,
                    int index,
                    String IsRemarkExpress
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
                        index,
                        IsRemarkExpress
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
            String SterileProcessID,
            String PrintCount,

            String Printer,
            String UsageCount,
            String ItemSetData,
            int index,
            String IsRemarkExpress
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
                index,
                IsRemarkExpress
        );
    }

    // =============================================================================================
    // -- Printing...
    // =============================================================================================
    PreviewSticker p = new PreviewSticker("");
    // =============================================================================================
    // -- Get Printer
    // =============================================================================================
    ArrayList<Integer> PRINTER = new ArrayList<>();
    ArrayList<String> PRINTER_IP = new ArrayList<>();
    int Width = 0;
    int Heigth = 0;

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
                Log.d("BAKF",result);
                //System.out.println("URL = " + result);

                return result;
            }

            // =========================================================================================
        }

        DeclarePrinter obj = new DeclarePrinter();
        obj.execute();
    }

    private String getPrinterIP(int Label){
        if(PRINTER_IP == null || PRINTER_IP.size() == 0) {
            return null;
        }else {
            return PRINTER_IP.get(Label - 1);
        }
    }

    final int DELAY_TIME = 0;

//    private void onPrint() throws Exception {
//
//        final List<ModelSterileDetail> Label_1 = getItemLabel("1");
//        final List<ModelSterileDetail> Label_2 = getItemLabel("2");
//        final List<ModelSterileDetail> Label_3 = getItemLabel("3");
//        final List<ModelSterileDetail> Label_4 = getItemLabel("4");
//        final List<ModelSterileDetail> Label_5 = getItemLabel("5");
//        final List<ModelSterileDetail> Label_6 = getItemLabel("6");
//        final List<ModelSterileDetail> Label_7 = getItemLabel("7");
//        final List<ModelSterileDetail> Label_8 = getItemLabel("8");
//        final List<ModelSterileDetail> Label_9 = getItemLabel("9");
//        final List<ModelSterileDetail> Label_10 = getItemLabel("10");
//
//        if(Label_1 != null && Label_1.size() > 0) {
//            final Handler handler0 = new Handler();
//            handler0.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        PSK.PrintSticker(CssdSearchSterile.this,1,Label_1,PRINTER_IP,B_ID,Print);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//
//                }
//            }, 0);
//        }
//
//        if(Label_2 != null && Label_2.size() > 0) {
//            final Handler handler = new Handler();
//            handler.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        PSK.PrintSticker(CssdSearchSterile.this,2,Label_2,PRINTER_IP,B_ID,Print);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//
//                }
//            }, DELAY_TIME);
//        }
//
//        if(Label_3 != null && Label_3.size() > 0) {
//            final Handler handler2 = new Handler();
//            handler2.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        PSK.PrintSticker(CssdSearchSterile.this,3,Label_3,PRINTER_IP,B_ID,Print);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//
//            }, DELAY_TIME);
//        }
//
//        if(Label_4 != null && Label_4.size() > 0) {
//            final Handler handler4 = new Handler();
//            handler4.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        PSK.PrintSticker(CssdSearchSterile.this,4,Label_4,PRINTER_IP,B_ID,Print);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//
//                }
//            }, DELAY_TIME);
//        }
//
//        if(Label_5 != null && Label_5.size() > 0) {
//            final Handler handler5 = new Handler();
//            handler5.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        PSK.PrintSticker(CssdSearchSterile.this,5,Label_5,PRINTER_IP,B_ID,Print);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//
//            }, DELAY_TIME);
//        }
//
//        if(Label_6 != null && Label_6.size() > 0) {
//            final Handler handler6 = new Handler();
//            handler6.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        PSK.PrintSticker(CssdSearchSterile.this,6,Label_6,PRINTER_IP,B_ID,Print);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//
//            }, DELAY_TIME);
//        }
//
//        if(Label_7 != null && Label_7.size() > 0) {
//            final Handler handler7 = new Handler();
//            handler7.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        PSK.PrintSticker(CssdSearchSterile.this,7,Label_7,PRINTER_IP,B_ID,Print);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//
//            }, DELAY_TIME);
//        }
//
//        if(Label_8 != null && Label_8.size() > 0) {
//            final Handler handler8 = new Handler();
//            handler8.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        PSK.PrintSticker(CssdSearchSterile.this,8,Label_8,PRINTER_IP,B_ID,Print);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//
//            }, DELAY_TIME);
//        }
//
//        if(Label_9 != null && Label_9.size() > 0) {
//            final Handler handler9 = new Handler();
//            handler9.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        PSK.PrintSticker(CssdSearchSterile.this,9,Label_9,PRINTER_IP,B_ID,Print);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//
//            }, DELAY_TIME);
//
//        }
//
//        if(Label_10 != null && Label_10.size() > 0) {
//            final Handler handler10 = new Handler();
//            handler10.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        PSK.PrintSticker(CssdSearchSterile.this,10,Label_10,PRINTER_IP,B_ID,Print);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//
//            }, DELAY_TIME);
//        }
//    }

    private void onPrint(){

        boolean IsCheck = true;
        String MsgCheck = "";

        try {

            // Check Packer
//            if (txt_packer.getContentDescription() == null || txt_packer.getContentDescription().toString().equals("")) {
//                Toast.makeText(CssdCheckList.this, "ยังไม่ได้เลือกผู้ห่อ !!", Toast.LENGTH_SHORT).show();
//                return;
//            }

            // Check List
//            for(int i=0;i<MODEL_CHECK_LIST.size();i++){
//
//                if (!MODEL_CHECK_LIST.get(i).isCheck()) {
//                    MsgCheck = "มีบางรายการยังไม่ได้ถูกเช็ค !!";
//                    IsCheck = false;
//                    break;
//                }
//
//                if (!MODEL_CHECK_LIST.get(i).getNameType().equals("")) {
//                    MsgCheck = "มีบางรายการถูก Remark ไว้ !!";
//                    IsCheck = false;
//                    break;
//                }
//            }

            if(!IsCheck){
                Toast.makeText(CssdSearchSterile.this, MsgCheck, Toast.LENGTH_SHORT).show();
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
                                String p_data = p.print(CssdSearchSterile.this, c.getInt("CaseLabel"), c.getString("PrinterIP"), list);

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

    private void callCheckListPaper(String WashID) {
        String url = Url.URL + "report/Report_checklist_sterile.php?WashID=" + WashID;
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }

    private void focus(){
        final Handler h = new Handler();
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
//                edt_usage_code.setText("");
//                edt_usage_code.requestFocus();
            }
        }, 500);

    }

    void xPrint(int Sel, List<ModelSterileDetail> sterile) throws Exception {

        if(sterile == null || sterile.size() == 0){
            Toast.makeText(CssdSearchSterile.this, "ไม่พบข้อมูล", Toast.LENGTH_SHORT).show();
        }

        boolean PMachine = p.isPMachine();
        boolean PPrice = p.isPPrice();
        boolean PRound = p.isPRound();
        boolean PDept = p.isPDept();
        boolean PEmp1 = p.isPEmp1();
        boolean PEmp2 = p.isPEmp2();
        boolean PEmp3 = p.isPEmp3();
        int PSpeed = p.getPSpeed();
        int PDensity = p.getPDensity();
        String POption = p.getPOption();

        String itemname = "";
        String Price = "";
        String DepName2 = "";
        String itemcode = "";
        String UsageCode = "";
        String MachineName = "";
        String SterileRoundNumber = "";
        String SterileDate = "";
        String ExpireDate = "";
        String usr_prepare = "";
        String usr_approve = "";
        String usr_sterile = "";
        String qty = "0";
        String age = "0";
        String qty_print = "1";
        //if (mUsbManager.hasPermission(TscDevice_1)) {
        TscWifi Tsc = new TscWifi();
        switch(Sel){
            case 0:
                Tsc.openport(getPrinterIP(Sel), 9100);
                Tsc.setup(50, 70, PSpeed, PDensity, 0, 2, 1);
                Tsc.sendcommand("DIRECTION 1,0\r\n");
                Tsc.clearbuffer();
                Tsc.printerfont(10, 10, "0", 0, 20, 20, ".");
                Tsc.printerfont(20, 10, "0", 0, 20, 20, ".");
                Tsc.printerfont(40, 10, "0", 0, 20, 20, ".");
                Tsc.printerfont(60, 10, "0", 0, 20, 20, ".");
                Tsc.printerfont(100, 10, "0", 0, 20, 20, ".");
                Tsc.printerfont(150, 10, "0", 0, 20, 20, ".");
                Tsc.printerfont(200, 10, "0", 0, 20, 20, ".");
                Tsc.printerfont(300, 10, "0", 0, 20, 20, ".");
                Tsc.printerfont(400, 10, "0", 0, 20, 20, ".");
                Tsc.printerfont(500, 10, "0", 0, 20, 20, ".");
                Tsc.printerfont(600, 10, "0", 0, 20, 20, ".");
                Tsc.printerfont(700, 10, "0", 0, 20, 20, ".");
                Tsc.printerfont(800, 10, "0", 0, 20, 20, ".");
                Tsc.printerfont(10, 20, "0", 0, 20, 20, ".");
                Tsc.printerfont(10, 30, "0", 0, 20, 20, ".");
                Tsc.printerfont(10, 60, "0", 0, 20, 20, ".");
                Tsc.printerfont(10, 100, "0", 0, 20, 20, ".");
                Tsc.printerfont(10, 150, "0", 0, 20, 20, ".");
                Tsc.printerfont(10, 200, "0", 0, 20, 20, ".");
                Tsc.printerfont(10, 300, "0", 0, 20, 20, ".");
                Tsc.printerfont(10, 400, "0", 0, 20, 20, ".");
                Tsc.printerfont(10, 500, "0", 0, 20, 20, ".");
                Tsc.printerfont(10, 600, "0", 0, 20, 20, ".");
                Tsc.printerfont(10, 700, "0", 0, 20, 20, ".");
                Tsc.printerfont(10, 800, "0", 0, 20, 20, ".");
                Tsc.sendcommand("PRINT 1," + 1 + "\r\n");
                break;

            case 1:
                List<ModelSterileDetail> DATA_MODEL_4 = sterile;

                Iterator li4 = DATA_MODEL_4.iterator();

                Tsc.openport(getPrinterIP(Sel), 9100);

                while (li4.hasNext()) {
                    try {
                        ModelSterileDetail m = (ModelSterileDetail) li4.next();

                        Tsc.setup(60, 76, PSpeed, PDensity, 0, 2, 1);
                        Tsc.sendcommand("DIRECTION 1,0\r\n");
                        Tsc.clearbuffer();

                        String Itemname[] = TextTwoLine.make2line(m.getItemname());

                        if( (Itemname[0].length() > 1) && (Itemname[1].length() == 0) ){
                            Tsc.sendpicture(50, 20, TextAsBitmap.getTextBitmap(Itemname[0], 26));
                        }else{
                            Tsc.sendpicture(50, 15, TextAsBitmap.getTextBitmap(Itemname[0], 20));
                            Tsc.sendpicture(50, 42, TextAsBitmap.getTextBitmap(Itemname[1], 20));
                        }
                        //ราคา
                        if (PPrice) {
                            Tsc.printerfont(300, 105, "1", 0, 1, 1, "Price: " + m.getPrice());
                        }
                        //แผนก
                        if (PDept) {
                            Tsc.sendpicture(50, 85, TextAsBitmap.getTextBitmap(m.getDepName2(), 20));
                        }
                        //ชื่อเซ็ท
                        Tsc.sendpicture(100, 117, TextAsBitmap.getTextBitmap(POption.equals("1") ? m.getItemcode() : m.getUsageCode(), 20));
                        //QR_Code
                        Tsc.qrcode(40, 165, "H", "6", "A", "0", "M2", "S1", m.getUsageCode());
                        //เครื่อง
                        if (PMachine) {
                            Tsc.sendpicture(210, 160, TextAsBitmap.getTextBitmap("เครื่อง:" + m.getMachineName(), 18));
                        }
                        //รอบ
                        if (PRound) {
                            Tsc.sendpicture(350, 160, TextAsBitmap.getTextBitmap("รอบ:" + m.getSterileRoundNumber(), 18));
                        }
                        //วันแพค
                        Tsc.printerfont(210, 204, "0", 0, 9, 9, m.getSterileDate());

                        //วันหมดอายุ
                        Tsc.sendpicture(210, 238, TextAsBitmap.getTextBitmap(m.getExpireDate() + " " + m.getAgeDay(), 25));

                        //ผู้เตรียม - ู้ฆ่าเชื้อ
                        if (PEmp1 || PEmp2) {
                            Tsc.sendpicture(220, 267, TextAsBitmap.getTextBitmap(m.getUsr_prepare()+"เตรียม"+" / "+m.getUsr_approve()+"ตรวจ", 18));
                        }

                        //ผู้ตรวจ
                        if (PEmp3) {
                            Tsc.sendpicture(220, 293, TextAsBitmap.getTextBitmap(m.getUsr_sterile()+"ฆ่าเชื้อ", 18));
                        }

                        //ส่วนล่างสติ๊กเกอร์
                        //ชื่ออุปกรณ์
                        if( (Itemname[0].length() > 1) && (Itemname[1].length() == 0) ){
                            Tsc.sendpicture(50, 450, TextAsBitmap.getTextBitmap(Itemname[0], 26));
                        }else{
                            Tsc.sendpicture(50, 440, TextAsBitmap.getTextBitmap(Itemname[0], 20));
                            Tsc.sendpicture(50, 470, TextAsBitmap.getTextBitmap(Itemname[1], 20));
                        }

                        //แผนก
                        Tsc.sendpicture(230, 500, TextAsBitmap.getTextBitmap(m.getDepName2(), 18));
                        //Tsc.sendpicture(50,465,TextAsBitmap.getTextBitmap(m.getDepName2(),18));
                        //ชื่อเซ็ท
                        Tsc.sendpicture(85, 500, TextAsBitmap.getTextBitmap(m.getUsageCode(), 18));
                        //QR_Code
                        Tsc.qrcode(340, 495, "H", "4", "A", "0", "M2", "S1", m.getUsageCode());
                        //วันหมดอายุ
                        Tsc.printerfont(40, 565, "0", 0, 9, 9, m.getSterileDate());
                        //วันหมดอายุ
                        Tsc.printerfont(178, 563, "0", 0, 12, 12, m.getExpireDate());


                        Tsc.sendcommand("PRINT 1," + m.getQty() + "\r\n");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

                Tsc.closeport();

                break;

            //==================================================================================================================================
            // Indicator blue 5x7cm
            //==================================================================================================================================
            case 2:
                List<ModelSterileDetail> DATA_MODEL_5 = sterile;

                Iterator li5 = DATA_MODEL_5.iterator();
                Tsc.openport(getPrinterIP(Sel), 9100);


                while (li5.hasNext()) {

                    try {
                        ModelSterileDetail m = (ModelSterileDetail) li5.next();

                        Tsc.setup(50, 70, PSpeed, PDensity, 0, 1, 1);
                        Tsc.sendcommand("DIRECTION 1,0\r\n");
                        Tsc.clearbuffer();
                        String Itemname[] = TextTwoLine.make2line(m.getItemname());

                        if( (Itemname[0].length() > 1) && (Itemname[1].length() == 0) ){
                            Tsc.sendpicture(30, 15, TextAsBitmap.getTextBitmap(Itemname[0], 26));
                        }else{
                            Tsc.sendpicture(30, 12, TextAsBitmap.getTextBitmap(Itemname[0], 22));
                            Tsc.sendpicture(30, 36, TextAsBitmap.getTextBitmap(Itemname[1], 22));
                        }
                          /*  //ราคา
                            if (PPrice) {
                                Tsc.printerfont(300, 85, "1", 0, 1, 1, "Price: " + m.getPrice());
                            }*/
                        //แผนก
                        if (PDept) {
                            Tsc.sendpicture(30, 85, TextAsBitmap.getTextBitmap(m.getDepName2(), 20));
                        }
                        //ชื่อเซ็ท
                        Tsc.sendpicture(30, 117, TextAsBitmap.getTextBitmap(POption.equals("1") ? "No." +m.getItemcode() : "No." +m.getUsageCode(), 20));
                        //QR_Code
                        Tsc.qrcode(20, 165, "H", "6", "A", "0", "M2", "S1", m.getUsageCode());
                        //เครื่อง
                        if (PMachine) {
                            Tsc.sendpicture(190, 150, TextAsBitmap.getTextBitmap("เครื่อง:" + m.getMachineName(), 18));
                        }
                        //รอบ
                        if (PRound) {
                            Tsc.sendpicture(320, 150, TextAsBitmap.getTextBitmap("รอบ:" + m.getSterileRoundNumber(), 18));
                        }
                        //วันแพค
                        Tsc.sendpicture(190, 175, TextAsBitmap.getTextBitmap("Packing Date:", 16));
                        Tsc.printerfont(210, 200, "0", 0, 9, 9, m.getSterileDate());

                        //วันหมดอายุ
                        Tsc.sendpicture(225, 230, TextAsBitmap.getTextBitmap(FormatDate.dateString(m.getExpireDate())+ " (" +m.getAgeDay()+"วัน)", 21));

                        //ผู้เตรียม - ู้ฆ่าเชื้อ
                        if (PEmp1 || PEmp2) {
                            Tsc.sendpicture(190, 260, TextAsBitmap.getTextBitmap(m.getUsr_prepare()+"เตรียม"+" / "+m.getUsr_approve()+"ตรวจ", 18));
                        }

                        //ผู้ตรวจ
                        if (PEmp3) {
                            Tsc.sendpicture(190, 290, TextAsBitmap.getTextBitmap(m.getUsr_sterile()+"ฆ่าเชื้อ", 18));
                        }

                        //ส่วนล่างสติ๊กเกอร์
                        //ชื่ออุปกรณ์
                        if( (Itemname[0].length() > 1) && (Itemname[1].length() == 0) ){
                            Tsc.sendpicture(30, 410, TextAsBitmap.getTextBitmap(Itemname[0], 26));
                        }else{
                            Tsc.sendpicture(30, 410, TextAsBitmap.getTextBitmap(Itemname[0], 20));
                            Tsc.sendpicture(30, 440, TextAsBitmap.getTextBitmap(Itemname[1], 20));
                        }

                        //แผนก
                        Tsc.sendpicture(190, 470, TextAsBitmap.getTextBitmap(m.getDepName2(), 18));
                        //Tsc.sendpicture(50,465,TextAsBitmap.getTextBitmap(m.getDepName2(),18));
                        //ชื่อเซ็ท
                        Tsc.sendpicture(20, 470, TextAsBitmap.getTextBitmap("No."+m.getUsageCode(), 18));
                        //QR_Code
                        Tsc.qrcode(295, 450, "H", "4", "A", "0", "M2", "S1", m.getUsageCode());
                        //วันหมดอายุ

                        Tsc.printerfont(40, 495, "0", 0, 9, 9, "Pakcing Date:"+m.getSterileDate());
                        //วันหมดอายุ
                        Tsc.printerfont(120, 525, "0", 0, 12, 12, m.getExpireDate());
                        Tsc.sendcommand("PRINT 1," + m.getQty() + "\r\n");



                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }


                Tsc.closeport();

                break;


            //==================================================================================================================================
            // Sticker Non Indicator Grey 5x2.5
            //==================================================================================================================================
            case 3:
                List<ModelSterileDetail> DATA_MODEL_1 = sterile;

                Iterator li1 = DATA_MODEL_1.iterator();

                Tsc.openport(getPrinterIP(Sel), 9100);

                while (li1.hasNext()) {
                    try {

                        ModelSterileDetail m = (ModelSterileDetail) li1.next();

                        Tsc.setup(55, 56, PSpeed, PDensity, 0, 2, 1);
                        //Tsc.setup(51, 26, PSpeed, PDensity, 0, 1, 1);
                        Tsc.sendcommand("DIRECTION 1,0\r\n");
                        Tsc.clearbuffer();

                        itemname = m.getItemname();
                        Price = m.getPrice();
                        DepName2 = m.getDepName2();
                        itemcode = m.getItemcode();
                        UsageCode = m.getUsageCode();
                        MachineName = m.getMachineName();
                        SterileRoundNumber = m.getSterileRoundNumber();
                        SterileDate = m.getSterileDate();
                        ExpireDate = m.getExpireDate();
                        usr_prepare = m.getUsr_prepare();
                        usr_approve = m.getUsr_approve();
                        usr_sterile = m.getUsr_sterile();
                        qty = m.getQty();
                        age = m.getExpDay();

                        String Itemname[] = TextTwoLine.make2line(itemname);
                        if( (Itemname[0].length() > 1) && (Itemname[1].length() == 0) ){
                            Tsc.sendpicture(40, 20, TextAsBitmap.getTextBitmap(Itemname[0], 26));
                        }else{
                            Tsc.sendpicture(40, 15, TextAsBitmap.getTextBitmap(Itemname[0], 20));
                            Tsc.sendpicture(40, 42, TextAsBitmap.getTextBitmap(Itemname[1], 20));
                        }
                        if (PPrice) {
                            Tsc.printerfont(300, 85, "1", 0, 1, 1, "Price:" + Price + ".-");
                        }

                        if (PDept) {
                            Tsc.sendpicture(30, 65, TextAsBitmap.getTextBitmap(DepName2, 20));
                        }
                        Tsc.sendpicture(80, 95, TextAsBitmap.getTextBitmap(POption.equals("1") ? itemcode : UsageCode, 20));
                        if (PMachine) {
                            Tsc.sendpicture(180, 120, TextAsBitmap.getTextBitmap("เครื่อง: " + MachineName, 20));
                        }
                        if (PRound) {
                            Tsc.sendpicture(320, 120, TextAsBitmap.getTextBitmap("รอบ: " + SterileRoundNumber, 20));
                        }

                        Tsc.qrcode(30, 125, "H", "5", "A", "0", "M2", "S1", UsageCode);
                        Tsc.printerfont(200, 165, "0", 0, 9, 9, SterileDate);
                        Tsc.sendpicture(200, 215, TextAsBitmap.getTextBitmap(m.getExpireDate() + " (" + m.getAgeDay() + ")", 26));

                        if (PEmp1 || PEmp2) {
                            Tsc.sendpicture(30, 252, TextAsBitmap.getTextBitmap(usr_prepare +"เตรียม"+ "/" + usr_approve +"ตรวจ"+ "/" + usr_sterile+"ฆ่าเชื้อ", 15));
                        }

                        //ส่วนล่างสติ๊กเกอร์
                        //ชื่ออุปกรณ์
                        if( (Itemname[0].length() > 1) && (Itemname[1].length() == 0) ){
                            Tsc.sendpicture(30, 285, TextAsBitmap.getTextBitmap(Itemname[0], 26));
                        }else{
                            Tsc.sendpicture(30, 285, TextAsBitmap.getTextBitmap(Itemname[0], 20));
                            Tsc.sendpicture(30, 305, TextAsBitmap.getTextBitmap(Itemname[1], 20));
                        }
                        //แผนก
                        if (PDept) {
                            Tsc.sendpicture(210, 350, TextAsBitmap.getTextBitmap(DepName2, 18));
                        }

                        //ชื่อเซต
                        Tsc.sendpicture(65, 350, TextAsBitmap.getTextBitmap(UsageCode, 18));
                        //QR_Code
                        Tsc.qrcode(310, 330, "H", "4", "A", "0", "M2", "S1", UsageCode);
                        //วันหมดอายุ
                        Tsc.printerfont(25, 405, "0", 0, 9, 9, SterileDate);
                        //วันหมดอายุ
                        Tsc.printerfont(150, 400, "0", 0, 12, 12, ExpireDate);


                        Tsc.sendcommand("PRINT 1," + qty + "\r\n");

                        System.gc();
                        System.runFinalization();
                        System.gc();


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                Tsc.closeport();

                break;

            //==================================================================================================================================
            // Sticker Indicator Grey 5x7cm
            //==================================================================================================================================
            case 4:

                System.out.println(" -----------------> 6");

                List<ModelSterileDetail> DATA_MODEL_41= sterile;

                Iterator li41 = DATA_MODEL_41.iterator();

                //TscLAN.openport(mUsbManager, getPrinter(6));
                Tsc.openport(getPrinterIP(Sel), 9100);
                int x = 15;
                int y = 100;
                int inc = 30;
                int x_ = 330;
                int s = 22;

                while (li41.hasNext()) {
                    try {
                        //======================================================================
                        // Model
                        //======================================================================
                        ModelSterileDetail m = (ModelSterileDetail) li41.next();
                        itemname = m.getItemname();
                        Price = m.getPrice();
                        DepName2 = m.getDepName2();
                        itemcode = m.getItemcode();
                        UsageCode = m.getUsageCode();
                        MachineName = m.getMachineName();
                        SterileRoundNumber = m.getSterileRoundNumber();
                        SterileDate = m.getSterileDate();
                        ExpireDate = m.getExpireDate();
                        usr_prepare = m.getUsr_prepare();
                        usr_approve = m.getUsr_approve();
                        usr_sterile = m.getUsr_sterile();
                        qty = m.getQty();
                        age = m.getExpDay();

                        String[] parts = m.getItemSetData().split("%");

                        //======================================================================
                        // Sticker
                        //======================================================================
                        for (int i = 0; i < parts.length; i = i + 10) {
                            // Setup
                            Tsc.setup(51, 80, PSpeed, PDensity, 0, 1, 1+1/2);
                            Tsc.sendcommand("DIRECTION 1,0\r\n");
                            Tsc.clearbuffer();

                            // Header
                            Tsc.sendpicture(60, 15, TextAsBitmap.getTextBitmap(DepName2, 26));

                            Tsc.sendpicture(20, 65, TextAsBitmap.getTextBitmap(itemname.substring(0,itemname.length()-4), 24));
                            //TscLAN.sendpicture(285, 10, TextAsBitmap.getTextBitmap("(" + ((i/10)+1) + "/" +  ( ( parts.length / 11) + 1 ) + ")", s) );
                            // TscLAN.printerfont(x, 45, "0", 0, 10, 10, "------------------------------------------------");

                            // Detail (Set)
                            try {
                                String[] set = parts[i].split("#");
                                Tsc.sendpicture(x, y, TextAsBitmap.getTextBitmap((i + 1) + ". " + set[0], s));
                                Tsc.sendpicture(x_, y, TextAsBitmap.getTextBitmap(set[1], s));

                                set = parts[i + 1].split("#");
                                Tsc.sendpicture(x, y + (inc * 1), TextAsBitmap.getTextBitmap((i + 2) + ". " + set[0], s));
                                Tsc.sendpicture(x_, y + (inc * 1), TextAsBitmap.getTextBitmap(set[1], s));

                                set = parts[i + 2].split("#");
                                Tsc.sendpicture(x, y + (inc * 2), TextAsBitmap.getTextBitmap((i + 3) + ". " + set[0], s));
                                Tsc.sendpicture(x_, y + (inc * 2), TextAsBitmap.getTextBitmap(set[1], s));

                                set = parts[i + 3].split("#");
                                Tsc.sendpicture(x, y + (inc * 3), TextAsBitmap.getTextBitmap((i + 4) + ". " + set[0], s));
                                Tsc.sendpicture(x_, y + (inc * 3), TextAsBitmap.getTextBitmap(set[1], s));

                                set = parts[i + 4].split("#");
                                Tsc.sendpicture(x, y + (inc * 4), TextAsBitmap.getTextBitmap((i + 5) + ". " + set[0], s));
                                Tsc.sendpicture(x_, y + (inc * 4), TextAsBitmap.getTextBitmap(set[1], s));

                                set = parts[i + 5].split("#");
                                Tsc.sendpicture(x, y + (inc * 5), TextAsBitmap.getTextBitmap((i + 6) + ". " + set[0], s));
                                Tsc.sendpicture(x_, y + (inc * 5), TextAsBitmap.getTextBitmap(set[1], s));

                                set = parts[i + 6].split("#");
                                Tsc.sendpicture(x, y + (inc * 6), TextAsBitmap.getTextBitmap((i + 7) + ". " + set[0], s));
                                Tsc.sendpicture(x_, y + (inc * 6), TextAsBitmap.getTextBitmap(set[1], s));

                                set = parts[i + 7].split("#");
                                Tsc.sendpicture(x, y + (inc * 7), TextAsBitmap.getTextBitmap((i + 8) + ". " + set[0], s));
                                Tsc.sendpicture(x_, y + (inc * 7), TextAsBitmap.getTextBitmap(set[1], s));

                                set = parts[i + 8].split("#");
                                Tsc.sendpicture(x, y + (inc * 8), TextAsBitmap.getTextBitmap((i + 9) + ". " + set[0], s));
                                Tsc.sendpicture(x_, y + (inc * 8), TextAsBitmap.getTextBitmap(set[1], s));

                                set = parts[i + 9].split("#");
                                Tsc.sendpicture(x, y + (inc * 9), TextAsBitmap.getTextBitmap((i + 10) + ". " + set[0], s));
                                Tsc.sendpicture(x_, y + (inc * 9), TextAsBitmap.getTextBitmap(set[1], s));
                            }catch(Exception e){

                            }

                            // Footer
                            Tsc.qrcode(290, 425, "H", "4", "A", "0", "M1", "S1", m.getUsageCode());
                            //Tsc.sendpicture(300, 445,  TextAsBitmap.getTextBitmap(POption.equals("1") ? m.getItemcode() : m.getUsageCode(), 20));


                            //Tsc.sendpicture(x, 490, TextAsBitmap.getTextBitmap((PEmp3 ? usr_sterile : ""), 20));
                            Tsc.sendpicture(x, 505, TextAsBitmap.getTextBitmap("Packing Date: "+FormatDate.dateString(SterileDate), 22));
                            Tsc.sendpicture(150, 535, TextAsBitmap.getTextBitmap(FormatDate.dateString(ExpireDate), 45));
                            Tsc.sendpicture(x, 588, TextAsBitmap.getTextBitmap((PEmp1 ? TextSplitName.SplitName(m.getUsr_prepare())+"-เตรียม"  : "-เตรียม") + "/" + (PEmp2 ? TextSplitName.SplitName(m.getUsr_approve())+"-ตรวจ"  : "-ตรวจ") + "/" + (PEmp3 ? TextSplitName.SplitName(m.getUsr_sterile())+"-ฆ่าเชื้อ"  : "-ฆ่าเชื้อ") , 15));
                            Tsc.sendpicture(x, 610, TextAsBitmap.getTextBitmap("เครื่อง:" + (PMachine ? MachineName : "") + "  รอบ:" + (PRound ? SterileRoundNumber : ""), 15));
                            Tsc.sendpicture(270, 605, TextAsBitmap.getTextBitmap("("+m.getAge()+"วัน)", 22));
                            //Tsc.sendpicture(260, 610, TextAsBitmap.getTextBitmap("No."+itemcode, 22));
                            //Tsc.sendpicture(250, 600, TextAsBitmap.getTextBitmap(age, s));


                            // Print
                            Tsc.sendcommand("PRINT 1," + m.getQty_Print() + "\r\n");
                        }



                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

                Tsc.closeport();
                break;

            //==================================================================================================================================
            // Sticker Yellow (Pasin)
            //==================================================================================================================================

            case 5:

                Tsc.openport(getPrinterIP(Sel), 9100);
                Tsc.setup(50, 49, PSpeed, PDensity, 0, 1, 1);
                Tsc.sendcommand("DIRECTION 1,0\r\n");

                System.out.println(" -----------------> 5");

                List<ModelSterileDetail> DATA_MODEL = sterile;

                Iterator li = DATA_MODEL.iterator();


                while (li.hasNext()) {
                    Tsc.clearbuffer();
                    try {
                        ModelSterileDetail m = (ModelSterileDetail) li.next();

                        String Itemname[] = TextTwoLine.make2line(m.getItemname());

                        Tsc.printerfont(30,20,"0",0,9,9, m.getDepName2());

                        //แผนก
                        if (PDept) {
                            Tsc.sendpicture(30, 20, TextAsBitmap.getTextBitmap(m.getDepName2(), 26));
                        }

                        Tsc.sendpicture(30, 70, TextAsBitmap.getTextBitmap(Itemname[0], 36));
                        Tsc.sendpicture(30, 110, TextAsBitmap.getTextBitmap(Itemname[1], 36));
                        //เครื่อง
                        if (PMachine) {
                            Tsc.sendpicture(30, 160, TextAsBitmap.getTextBitmap("เครื่อง:" + m.getMachineName(), 24));
                        }

                        //รอบ
                        if (PRound) {
                            Tsc.sendpicture(190, 160, TextAsBitmap.getTextBitmap("รอบ:" + m.getSterileRoundNumber(), 24));
                        }

                        //วันแพค
                        Tsc.sendpicture(30, 190, TextAsBitmap.getTextBitmap("ผลิต "+m.getSterileDate() + m.getExpDay(), 26));

                        //วันหมดอายุ
                        Tsc.sendpicture(30, 230, TextAsBitmap.getTextBitmap("หมด", 30));
                        Tsc.sendpicture(30, 260, TextAsBitmap.getTextBitmap("อายุ", 30));
                        Tsc.sendpicture(120, 240, TextAsBitmap.getTextBitmap(FormatDate.dateString(m.getExpireDate()), 45));

                        //คนเครียม ตรวจ
                        if (PEmp1||PEmp2){Tsc.sendpicture(30,310,TextAsBitmap.getTextBitmap(TextSplitName.SplitName(m.getUsr_prepare())+"-เตรียม",22));}
                        //ฆ่าเชื้อ
                        if (PEmp3){Tsc.sendpicture(130,340,TextAsBitmap.getTextBitmap( TextSplitName.SplitName(m.getUsr_approve())+"-ห่อ"+"/"+TextSplitName.SplitName(m.getUsr_sterile())+"-นึ่ง",22));}



                        Tsc.sendcommand("PRINT 1," + m.getQty_Print() + "\r\n");

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

                Tsc.closeport();

                break;

                /*//==================================================================================================================================
                // Sticker Red (Pasin)
                //==================================================================================================================================
                case 6:

                    System.out.println(" -----------------> 6");

                    List<ModelSterileDetail> DATA_MODEL_2 = sterile;

                    Iterator li2 = DATA_MODEL_2.iterator();

                    Tsc.openport(getPrinterIP(Sel), 9100);
                    int x = 15;
                    int y = 60;
                    final int inc = 40;
                    final int x_ = 300;
                    final int s = 26;

                    while (li2.hasNext()) {
                        try {
                            //======================================================================
                            // Model
                            //======================================================================
                            ModelSterileDetail m = (ModelSterileDetail) li2.next();

                            itemname = m.getItemname();
                            Price = m.getPrice();
                            DepName2 = m.getDepName2();
                            itemcode = m.getItemcode();
                            UsageCode = m.getUsageCode();
                            MachineName = m.getMachineName();
                            SterileRoundNumber = m.getSterileRoundNumber();
                            SterileDate = m.getSterileDate();
                            ExpireDate = m.getExpireDate();
                            usr_prepare = m.getUsr_prepare();
                            usr_approve = m.getUsr_approve();
                            usr_sterile = m.getUsr_sterile();
                            qty = m.getQty();
                            age = m.getExpDay();
                            qty_print = m.getQty_Print();

                            String[] parts = m.getItemSetData().split("%");

                            //======================================================================
                            // Sticker
                            //======================================================================
                            for (int i = 0; i < parts.length; i = i + 10) {
                                // Setup
                                Tsc.setup(47, 83, PSpeed, PDensity, 0, 1, 1);
                                Tsc.sendcommand("DIRECTION 0,0\r\n");
                                Tsc.clearbuffer();

                                // Header
                                Tsc.sendpicture(20, 10, TextAsBitmap.getTextBitmap(itemname, 30));
                                Tsc.sendpicture(285, 10, TextAsBitmap.getTextBitmap("(" + ((i / 10) + 1) + "/" + ((parts.length / 11) + 1) + ")", s));
                                Tsc.printerfont(x, 45, "0", 0, 10, 10, "------------------------------------------------");

                                // Detail (Set)
                                try {
                                    String[] set = parts[i].split("#");
                                    Tsc.sendpicture(x, y, TextAsBitmap.getTextBitmap((i + 1) + ". " + set[0], s));
                                    Tsc.sendpicture(x_, y, TextAsBitmap.getTextBitmap(set[1], s));

                                    set = parts[i + 1].split("#");
                                    Tsc.sendpicture(x, y + (inc * 1), TextAsBitmap.getTextBitmap((i + 2) + ". " + set[0], s));
                                    Tsc.sendpicture(x_, y + (inc * 1), TextAsBitmap.getTextBitmap(set[1], s));

                                    set = parts[i + 2].split("#");
                                    Tsc.sendpicture(x, y + (inc * 2), TextAsBitmap.getTextBitmap((i + 3) + ". " + set[0], s));
                                    Tsc.sendpicture(x_, y + (inc * 2), TextAsBitmap.getTextBitmap(set[1], s));

                                    set = parts[i + 3].split("#");
                                    Tsc.sendpicture(x, y + (inc * 3), TextAsBitmap.getTextBitmap((i + 4) + ". " + set[0], s));
                                    Tsc.sendpicture(x_, y + (inc * 3), TextAsBitmap.getTextBitmap(set[1], s));

                                    set = parts[i + 4].split("#");
                                    Tsc.sendpicture(x, y + (inc * 4), TextAsBitmap.getTextBitmap((i + 5) + ". " + set[0], s));
                                    Tsc.sendpicture(x_, y + (inc * 4), TextAsBitmap.getTextBitmap(set[1], s));

                                    set = parts[i + 5].split("#");
                                    Tsc.sendpicture(x, y + (inc * 5), TextAsBitmap.getTextBitmap((i + 6) + ". " + set[0], s));
                                    Tsc.sendpicture(x_, y + (inc * 5), TextAsBitmap.getTextBitmap(set[1], s));

                                    set = parts[i + 6].split("#");
                                    Tsc.sendpicture(x, y + (inc * 6), TextAsBitmap.getTextBitmap((i + 7) + ". " + set[0], s));
                                    Tsc.sendpicture(x_, y + (inc * 6), TextAsBitmap.getTextBitmap(set[1], s));

                                    set = parts[i + 7].split("#");
                                    Tsc.sendpicture(x, y + (inc * 7), TextAsBitmap.getTextBitmap((i + 8) + ". " + set[0], s));
                                    Tsc.sendpicture(x_, y + (inc * 7), TextAsBitmap.getTextBitmap(set[1], s));

                                    set = parts[i + 8].split("#");
                                    Tsc.sendpicture(x, y + (inc * 8), TextAsBitmap.getTextBitmap((i + 9) + ". " + set[0], s));
                                    Tsc.sendpicture(x_, y + (inc * 8), TextAsBitmap.getTextBitmap(set[1], s));

                                    set = parts[i + 9].split("#");
                                    Tsc.sendpicture(x, y + (inc * 9), TextAsBitmap.getTextBitmap((i + 10) + ". " + set[0], s));
                                    Tsc.sendpicture(x_, y + (inc * 9), TextAsBitmap.getTextBitmap(set[1], s));
                                } catch (Exception e) {

                                }

                                // Footer
                                Tsc.sendpicture(x, 465, TextAsBitmap.getTextBitmap("หมดอายุ", 30));
                                Tsc.sendpicture(150, 465, TextAsBitmap.getTextBitmap(FormatDate.dateString(ExpireDate), 45));

                                Tsc.sendpicture(x, 515, TextAsBitmap.getTextBitmap(SterileDate + "  (ค-" + (PMachine ? MachineName : "") + "/ร-" + (PRound ? SterileRoundNumber : "") + ")", s));
                                Tsc.sendpicture(x, 565, TextAsBitmap.getTextBitmap((PEmp1 ? usr_prepare : "") + "," + (PEmp2 ? usr_approve : "") + "," + (PEmp3 ? usr_sterile : ""), 20));

                                Tsc.sendpicture(250, 600, TextAsBitmap.getTextBitmap(age, s));

                                // Print
                                Tsc.sendcommand("PRINT 1," + qty_print + "\r\n");
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                    Tsc.closeport();

                    break;*/

            //==================================================================================================================================
            // Sticker Blue (Pasin)
            //==================================================================================================================================
            case 7:

                System.out.println(" -----------------> 7");

                Tsc.openport(getPrinterIP(Sel), 9100);
                Tsc.setup(50, 25, PSpeed, PDensity, 0, 2, 1);
                Tsc.sendcommand("DIRECTION 1,0\r\n");



                List<ModelSterileDetail> DATA_MODEL_3 = sterile;

                Iterator li3 = DATA_MODEL_3.iterator();

                //System.out.println(" Printer -----> " + getPrinter(7).getDeviceName() );

                while (li3.hasNext()) {
                    Tsc.clearbuffer();
                    try {
                        ModelSterileDetail m = (ModelSterileDetail) li3.next();

                        Tsc.sendpicture(60, 40, TextAsBitmap.getTextBitmap(m.getDepName2()+" " + m.getItemname(), 30));
                        Tsc.sendpicture(20, 70, TextAsBitmap.getTextBitmap("ผลิต "+m.getSterileDate() + "(" + (PMachine ? m.getMachineName() : "") +"/"+ (PRound ? m.getSterileRoundNumber() : "") + ")", 25));
                        Tsc.sendpicture(25, 100, TextAsBitmap.getTextBitmap("หมด", 25));
                        Tsc.sendpicture(25, 130, TextAsBitmap.getTextBitmap("อายุ", 25));
                        Tsc.sendpicture(350, 90, TextRotation.RotateBitmap(TextAsBitmap.getTextBitmap(m.getExpDay(), 25), 90));
                        Tsc.sendpicture(110, 110, TextAsBitmap.getTextBitmap(FormatDate.dateString(m.getExpireDate()), 40));
                        Tsc.sendpicture(25, 170, TextAsBitmap.getTextBitmap((PEmp1 ? TextSplitName.SplitName(m.getUsr_prepare())+"-เตรียม"  : "-เตรียม") + "," + (PEmp2 ? TextSplitName.SplitName(m.getUsr_approve())+"-ห่อ"  : "-ห่อ") + "," + (PEmp3 ? TextSplitName.SplitName(m.getUsr_sterile())+"-นึ่ง"  : "-นึ่ง"), 20));


                        Tsc.sendcommand("PRINT 1," + m.getQty_Print() + "\r\n");


                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

                Tsc.closeport();

                break;
        }

        //}

    }


    public void Get_label_sticker(String xID) {
        class Get_label_sticker extends AsyncTask<String, Void, String> {
            // variable
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                try{
                    JSONObject jsonObj = new JSONObject(s);
                    JSONArray setRs = jsonObj.getJSONArray(iFt.getTAG_RESULTS());
                    for(int i=0;i<setRs.length();i++) {
                        JSONObject c = setRs.getJSONObject(i);
                        xItemname1=c.getInt("xItemname1");
                        yItemname1=c.getInt("yItemname1");
                        xUsagecode1=c.getInt("xUsagecode1");
                        yUasgecode1=c.getInt("yUasgecode1");
                        xPrice=c.getInt("xPrice");
                        yPrice=c.getInt("yPrice");
                        xDept1=c.getInt("xDept1");
                        yDept1=c.getInt("yDept1");
                        xMachine=c.getInt("xMachine");
                        yMahcine=c.getInt("yMahcine");
                        xRound=c.getInt("xRound");
                        yRound=c.getInt("yRound");
                        xPackdate=c.getInt("xPackdate");
                        yPackdate=c.getInt("yPackdate");
                        xExp=c.getInt("xExp");
                        yExp=c.getInt("yExp");
                        xEmp1=c.getInt("xEmp1");
                        yEmp1=c.getInt("yEmp1");
                        xEmp2=c.getInt("xEmp2");
                        yEmp2=c.getInt("yEmp2");
                        xItemname2=c.getInt("xItemname2");
                        yItemname2=c.getInt("yItemname2");
                        xDept2=c.getInt("xDept2");
                        yDept2=c.getInt("yDept2=");
                        xUsagecode2=c.getInt("xUsagecode2");
                        yUsagecode2=c.getInt("yUsagecode2");
                        xQrcode1=c.getInt("xQrcode1");
                        yQrcode1=c.getInt("yQrcode1");
                        xQrcode2=c.getInt("xQrcode2");
                        yQrcode2=c.getInt("yQrcode2");
                        xPackdate2=c.getInt("xPackdate2");
                        yPackdate2=c.getInt("yPackdate2");
                        xExp2=c.getInt("xExp2");
                        yExp2=c.getInt("yExp2");
                        xEmp3=c.getInt("xEmp3");
                        yEmp3=c.getInt("yEmp3");
                        Log.d("ED_DEPTNAME",c.getString("ED_DepName") );
                    }
                    //etxt_dept.setText(ED_DeptName);
                    //getlistdata(deptsp_id,edittext.getText().toString(),"");
                }catch (JSONException e){
                    Log.d("XXX", "Error on get dept" );
                }
            }
            //class connect php RegisterUserClass important !!!!!!!
            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("xID",params[0]);
                String result = ruc.sendPostRequest(getUrl.xUrl+"p/sticker/get_label_stickerXY.php",data);
                return result;
            }
        }
        Get_label_sticker ru = new Get_label_sticker();
        ru.execute(xID);
    }


}
