package com.phc.cssd;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.phc.core.string.Cons;
import com.phc.cssd.adapter.ListApproveAdapter;
import com.phc.cssd.adapter.SpinnerListAdapter;
import com.phc.core.connect.HTTPConnect;
import com.phc.cssd.properties.Response_Aux;
import com.phc.cssd.properties.Response_Machine_ApStock;
import com.phc.cssd.url.DateThai;
import com.phc.cssd.url.Url;
import com.phc.cssd.url.getUrl;
import com.phc.cssd.adapter.SterileDetailForSearchAdapter;
import com.phc.cssd.url.xControl;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class SterileSeachActivity extends AppCompatActivity implements View.OnClickListener {

    String condition1 = "";
    String condition2 = "";
    String condition3 = "";
    String condition4 = "";
    ArrayList<Response_Aux> results = new ArrayList<Response_Aux>();
    ArrayList<Response_Aux> resultssterileprocess = new ArrayList<Response_Aux>();
    ArrayList<Response_Aux> resultssterilemachine = new ArrayList<Response_Aux>();
    ArrayList<Response_Aux> resultssterileround = new ArrayList<Response_Aux>();
    ArrayList<Response_Aux> resultssteriledocument = new ArrayList<Response_Aux>();
    ArrayList<Response_Aux> resultssteriledetail = new ArrayList<Response_Aux>();
    ArrayList<Response_Aux> resultsOccurance;
    ArrayList<Response_Aux> xListOccuranceType = new ArrayList<Response_Aux>();
    ArrayList<Response_Machine_ApStock> listMachine_ApStock = new ArrayList<Response_Machine_ApStock>();
    private static final String TAG_RESULTS="result";
    JSONArray setRs = null;
    Calendar myCalendar = Calendar.getInstance();
    String SELECT_URL;
    getUrl iFt = new getUrl();
    HTTPConnect ruc = new HTTPConnect();
    xControl xCtl = new xControl();
    private int year;
    private int month;
    private int day;
    static final int DATE_PICKER_ID = 1111;
    static final int xWh = 160;
    private int MachineCount = 0;
    private int McSel = 0;
    CheckBox checkBox;
    Spinner Spinner01;
    Spinner Spinner02;
    Spinner Spinner03;
    GridView lv1;
    String gDate;
    TextView textViewDate2;
    TextView tImport;
    TextView tOcc;
    TextView tDate;
    EditText eSearch;
    Button bSearch;
    Button b_Back;
    Button b_Refresh;
    ImageView bImport;
    ImageView imageBg;
    ImageView imageBg2;
    ImageView b_Occurance;
    ImageView b_OccuranceReturn;
    ImageView bDate;
    int ChkSwitch = 0;
    String DocNo="";
    String DocDate="";
    String xQty="0";
    int xIsStatus = 0;
    int xIsOccurance = 0;
    String ListRowID = "";
    String userid;
    DateThai Dthai;
    RelativeLayout rl;
    int mCnt=0;
    ImageView[] IMGS= new ImageView[100];
    TextView[] tView= new TextView[100];
    TextView textViewProgram;
    TextView textViewTime;
    TextView textViewDocNo;
    TextView textViewRoundNumber;
    Object DateTime;
    ProgressDialog loadingDialog;
    String B_ID ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sterile_seach);
        getSupportActionBar().hide();
        setTitle("บันทึกรับเข้าห้องปลอดเชื้อ");

        Bundle bd = getIntent().getExtras();
        if (bd != null){
            userid =  bd.getString("userid");
            B_ID = bd.getString("B_ID");
        }
        rl = (RelativeLayout) findViewById(R.id.rl);
        imageBg = (ImageView) findViewById(R.id.imageBg);
        imageBg2 = (ImageView) findViewById(R.id.imageBg2);
        b_Refresh = (Button) findViewById(R.id.b_Reresh);
        Dthai = new DateThai();
        textViewProgram = (TextView) findViewById(R.id.textView01);
        textViewTime = (TextView) findViewById(R.id.textView03);
        textViewDocNo = (TextView) findViewById(R.id.textView04);
        textViewRoundNumber = (TextView) findViewById(R.id.textView02);
        tImport = (TextView) findViewById(R.id.tImport);
        tOcc = (TextView) findViewById(R.id.tOcc);
        bDate = (ImageView) findViewById(R.id.bDate);
        tDate = (TextView) findViewById(R.id.tDate);
        Date currentDate = Calendar.getInstance().getTime();
        java.text.SimpleDateFormat simpleDateFormat = new java.text.SimpleDateFormat("EEE,d,M,yyyy");
        String formattedCurrentDate = simpleDateFormat.format(currentDate);
        final Calendar c = Calendar.getInstance();
        year  = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day   = c.get(Calendar.DAY_OF_MONTH);
        gDate =  year + "-" + TwoZero((month + 1)+"") + "-" + TwoZero(day+"" );
        tDate.setText(gDate);
        ListMachineCount( gDate );
        String[] partsCollArr;
        String delimiter = ",";
        partsCollArr = formattedCurrentDate.split(delimiter);
        eSearch = (EditText) findViewById(R.id.item_search);
        bSearch = (Button) findViewById(R.id.button_search);
        b_Back = (Button) findViewById(R.id.b_Back);
        bImport = (ImageView) findViewById(R.id.b_Import);
        b_Occurance = (ImageView) findViewById(R.id.b_Occurance);
        b_OccuranceReturn  = (ImageView) findViewById(R.id.b_OccuranceReturn);
        bDate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // On button click show datepicker dialog
                showDialog(DATE_PICKER_ID);
                ListMachineCount( gDate );
                textViewProgram.setText( "" );
                textViewTime.setText( "" );
                textViewDocNo.setText( "" );
                textViewRoundNumber.setText( "" );
                DocNo = "";
                xIsStatus = 0;
                tImport.setText( "0" );
                tOcc.setText( "0" );
                ListSterileDocDetail(DocNo);
                lv1.setAdapter(null);
            }
        });

        b_OccuranceReturn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!DocNo.equals("")) ReturnOccurance(DocNo);
            }
        });

        bImport.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                CheckPayDep();
            }
        });

        b_Refresh.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ListSterileDocDetail(textViewDocNo.getText().toString());
            }
        });

        b_Back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ClosePage();
            }
        });
        initialize();
    }

    public void initialize(){
        b_Occurance.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                xIsOccurance = 0;
                resultsOccurance = new ArrayList<Response_Aux>();
                for(int n=0;n<resultssteriledetail.size();n++){
                    if( resultssteriledetail.get(n).getFields5().equals("1") ){
                        Response_Aux newData = new Response_Aux();
                        newData.setFields1( resultssteriledetail.get(n).getFields1() );
                        newData.setFields2( resultssteriledetail.get(n).getFields2() );
                        newData.setFields3( resultssteriledetail.get(n).getFields3() );
                        newData.setFields4( resultssteriledetail.get(n).getFields4() );
                        newData.setFields5( resultssteriledetail.get(n).getFields5() );
                        newData.setFields6( resultssteriledetail.get(n).getFields6() );
                        resultsOccurance.add( newData );
                        xIsOccurance++;
                    }
                }
                if (!DocNo.equals("")) {
                    final Dialog dialog = new Dialog(SterileSeachActivity.this, R.style.DialogCustomTheme);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.spinner_dialog);
                    dialog.setCancelable(false);
                    dialog.setTitle("บันทึกความเสี่ยง...");
                    final Switch switch1 = (Switch) dialog.findViewById(R.id.switch1);
                    if(xIsOccurance==0) resultsOccurance = resultssteriledetail;
                    final Spinner spnOccuranceType = (Spinner) dialog.findViewById(R.id.spn_list);
                    final Spinner spn_Step = (Spinner) dialog.findViewById(R.id.spn_Step);
                    String[] StepList = getResources().getStringArray(R.array.StpeAdapter);
                    ArrayAdapter<String> StepAdapter = new ArrayAdapter<String>(SterileSeachActivity.this,
                            android.R.layout.simple_dropdown_item_1line, StepList);
                    spn_Step.setAdapter( StepAdapter );
                    spn_Step.setVisibility(View.GONE);
                    xCtl.ListOccuranceType(spnOccuranceType, SterileSeachActivity.this);
                    final ArrayList<Response_Aux> xData = xCtl.getListOccuranceType();
                    Button button1 = (Button) dialog.findViewById(R.id.button1);
                    button1.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            String OccuranceTypeID = xData.get(spnOccuranceType.getSelectedItemPosition()).getFields1();
                            String xSel = "0";
                            String xFullOcc = "0";
                            if(xIsOccurance==0)
                                xSel = "1";
                            else
                                xSel = "3";
                            if( resultssteriledetail.size() == resultsOccurance.size() )
                                xFullOcc = "1";
                            else
                                xFullOcc = "0";
                            ListRowID="";
                            for (int i=0;i<resultsOccurance.size();i++){
                                ListRowID += resultsOccurance.get(i).getFields1()+",";
                            }
                            ListRowID = ListRowID.substring(0,ListRowID.length()-1);
                            if (switch1.isChecked())
                                ChkSwitch = 1;
                            else
                                ChkSwitch = 0;
                            CreateOccuranceDocNo(xSel, DocNo, OccuranceTypeID, userid,"0",xQty,xFullOcc,ListRowID,resultssteriledetail.size()+"",ChkSwitch+"");
                            if( xFullOcc.equals("1") ) ListMachineCount( gDate );
                            if(xIsOccurance!=0){
                                ClearDocHeader();
                            }else{
                                lv1.setAdapter(null);
                            }
                            dialog.dismiss();
                        }
                    });

                    Button button2 = (Button) dialog.findViewById(R.id.button2);
                    button2.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            ListSterileDocDetail(DocNo);
                            ReturnOccurance(DocNo);
                            dialog.dismiss();
                        }
                    });
                    TextView tCount = (TextView) dialog.findViewById(R.id.tCount);
                    tCount.setText( "จำนวน   "+ resultsOccurance.size() +"   รายการ" );
                    ListView lOcc = (ListView) dialog.findViewById(R.id.lOcc);
                    lOcc.setAdapter(new ListApproveAdapter( SterileSeachActivity.this, resultsOccurance));
                    dialog.show();
                }
            }
        });
    }

    private void CImg(){
        ImageView iv = new ImageView(this);
        iv.setImageDrawable(getDrawable(R.drawable.ic_sterile_green));
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        iv.setLayoutParams(lp);
        rl.addView(iv);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_PICKER_ID:
                return new DatePickerDialog(this, pickerListener, year, month,day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener pickerListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
            year  = selectedYear;
            month = selectedMonth;
            day   = selectedDay;
            gDate = year + "-" + TwoZero((month + 1)+"") + "-" + TwoZero(day+"" ) ;
            tDate.setText(gDate);
            ListMachineCount( gDate );
        }
    };
    //==========================================
    public void CreateApproveStock( String xRefDocNo,String xUserId ) {
        class CreateApproveStock extends AsyncTask<String, Void, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                String xFinish="";
                try {
                    JSONObject jsonObj = new JSONObject(s);
                    setRs = jsonObj.getJSONArray(TAG_RESULTS);
                    results.clear();
                    for(int i=0;i<setRs.length();i++){
                        JSONObject c = setRs.getJSONObject(i);
                        xFinish = c.getString("Finish");
                        if(xFinish.equals("true")) {
                            ListMachineCount( gDate );
                            textViewProgram.setText( "" );
                            textViewTime.setText( "" );
                            textViewDocNo.setText( "" );
                            textViewRoundNumber.setText( "" );
                            DocNo = "";
                            xIsStatus = 0;
                            tImport.setText( "0" );
                            tOcc.setText( "0" );
                            lv1.setAdapter(null);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("RefDocNo",params[0]);
                data.put("userid",params[1]);
                if(B_ID!=null){data.put("B_ID",B_ID);}
                String result = ruc.sendPostRequest(iFt.createapprovestockdocno(),data);
                Log.d("SDFDSF", data+"");
                Log.d("SDFDSF", result);
                return  result;
            }
        }
        CreateApproveStock ru = new CreateApproveStock();
        ru.execute( xRefDocNo,xUserId );
    }

    public void ListSterile() {
        class ListSterile extends AsyncTask<String, Void, String> {
            // ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                // loading = ProgressDialog.show(ApproveStockActivity.this, "Please Wait",null, true, true);
            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                // loading.dismiss();
                ArrayList<String> sterileprocessAdapter = new ArrayList<>();
                try {
                    Response_Aux newsData;
                    JSONObject jsonObj = new JSONObject(s);
                    setRs = jsonObj.getJSONArray(TAG_RESULTS);
                    resultssterileprocess.clear();
                    for(int i=0;i<setRs.length();i++){
                        newsData = new Response_Aux();
                        JSONObject c = setRs.getJSONObject(i);
                        newsData.setFields1(c.getString("xId"));
                        newsData.setFields2(c.getString("xName1"));
                        newsData.setFields3(c.getString("xName2"));
                        resultssterileprocess.add( newsData );
                        sterileprocessAdapter.add(c.getString("xName2"));
                    }
                    SpinnerListAdapter SpinnerList = new SpinnerListAdapter(SterileSeachActivity.this,sterileprocessAdapter);
                    Spinner01.setAdapter( SpinnerList );
                    Spinner01.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            ListSterileMachine( resultssterileprocess.get( position ).getFields1() );
                            //eSearch.setText( resultssterileprocess.get( position ).getFields1() );
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                            //Another interface callback
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                //data.put("Search",params[0]);

                String result = ruc.sendPostRequest(iFt.getsterileprocess(),data);
                return  result;
            }
        }

        ListSterile ru = new ListSterile();
        ru.execute();
    }

    public void ListSterileMachine(String McCode) {
        class ListSterileMachine extends AsyncTask<String, Void, String> {
            // ProgressDialog loading;
            ArrayList<String> sterileMachineAdapter = new ArrayList<>();
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                // loading = ProgressDialog.show(ApproveStockActivity.this, "Please Wait",null, true, true);
            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                // loading.dismiss();
                try {
                    Response_Aux newsData;
                    JSONObject jsonObj = new JSONObject(s);
                    setRs = jsonObj.getJSONArray(TAG_RESULTS);
                    resultssterilemachine.clear();
                    sterileMachineAdapter.clear();
                    for(int i=0;i<setRs.length();i++){
                        newsData = new Response_Aux();
                        JSONObject c = setRs.getJSONObject(i);
                        newsData.setFields1(c.getString("xId"));
                        newsData.setFields2(c.getString("xName"));
                        resultssterilemachine.add( newsData );
                        sterileMachineAdapter.add(c.getString("xName"));
                    }
                    //eSearch.setText( sterileMachineAdapter.size() );
                    SpinnerListAdapter SpinnerList = new SpinnerListAdapter(SterileSeachActivity.this,sterileMachineAdapter);
                    Spinner02.setAdapter( SpinnerList );
                    Spinner02.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            //eSearch.setText( resultssterilemachine.get( position ).getFields1() );
                            //eSearch.append(resultssterilemachine.get( position ).getFields2() );
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                            //Another interface callback
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("McCode",params[0]);
                String result = ruc.sendPostRequest(iFt.getsterileprocessmachine(),data);
                Log.d("BANK",result);
                return  result;
            }
        }

        ListSterileMachine ru = new ListSterileMachine();
        ru.execute(McCode);
    }

    public void ListSterileRound(String xDate) {
        class ListSterileRound extends AsyncTask<String, Void, String> {
            // ProgressDialog loading;
            ArrayList<String> sterileRoundAdapter = new ArrayList<>();
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                // loading = ProgressDialog.show(ApproveStockActivity.this, "Please Wait",null, true, true);
            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                // loading.dismiss();
                try {
                    Response_Aux newsData;
                    JSONObject jsonObj = new JSONObject(s);
                    setRs = jsonObj.getJSONArray(TAG_RESULTS);
                    resultssterileround.clear();
                    sterileRoundAdapter.clear();
                    for(int i=0;i<setRs.length();i++){
                        newsData = new Response_Aux();
                        JSONObject c = setRs.getJSONObject(i);
                        newsData.setFields1(c.getString("xRound"));
                        resultssterileround.add( newsData );
                        sterileRoundAdapter.add(c.getString("xRound"));
                    }

                    SpinnerListAdapter SpinnerList = new SpinnerListAdapter(SterileSeachActivity.this,sterileRoundAdapter);
                    Spinner03.setAdapter( SpinnerList );
                    Spinner03.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            //.setText( resultssterilemachine.get( position ).getFields1() );
                            //eSearch.append(resultssterilemachine.get( position ).getFields2() );
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                            //Another interface callback
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("xDate",params[0]);
                if(B_ID!=null){data.put("B_ID",B_ID);}
                String result = ruc.sendPostRequest(iFt.getsterileprocessround(),data);
                Log.d("BANK",data+"");
                Log.d("BANK",result);
                return  result;
            }
        }

        ListSterileRound ru = new ListSterileRound();
        ru.execute(xDate);
    }

    public void ListSterileDocDetail(final String xDocNo) {
        class ListSterileDocDetail extends AsyncTask<String, Void, String> {
            private ProgressDialog dialog = new ProgressDialog(SterileSeachActivity.this);
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                this.dialog.setMessage(Cons.WAIT_FOR_PROCESS);
                this.dialog.setCancelable(false);
                this.dialog.show();
            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                try {
                    Response_Aux newsData;
                    JSONObject jsonObj = new JSONObject(s);
                    setRs = jsonObj.getJSONArray(TAG_RESULTS);
                    resultssteriledetail.clear();
                    for(int i=0;i<setRs.length();i++){
                        newsData = new Response_Aux();
                        JSONObject c = setRs.getJSONObject(i);
                        newsData.setFields1(c.getString("xId"));
                        newsData.setFields2(c.getString("xItemName"));
                        newsData.setFields3(c.getString("xQty"));
                        newsData.setFields4(c.getString("xIsStatus"));
                        newsData.setFields5(c.getString("xIsOccurance"));
                        newsData.setFields6(c.getString("xUsageCode"));
                        newsData.setFields7(c.getString("xIsSterile"));
                        newsData.setFields8(c.getString("OccuranceDocNo"));
                        newsData.setFields15(c.getString("IsRemarkExpress"));
                        resultssteriledetail.add( newsData );
                    }
                    lv1 = (GridView) findViewById(R.id.ListView02);
                    lv1.setAdapter(new SterileDetailForSearchAdapter( SterileSeachActivity.this, resultssteriledetail,xIsStatus,userid,DocNo,xQty));
                    int IsOcc = 0;
                    int IsImport = 0;
                    for(int n=0;n<resultssteriledetail.size();n++){
                        if( resultssteriledetail.get(n).getFields5().equals("1") ){
                            IsOcc++;
                        }else{
                            IsImport++;
                        }
                    }
                    if (resultssteriledetail.get(0).getFields1().equals("")){
                        tImport.setText(" 0 รายการ");
                        tOcc.setText(" 0 รายการ");
                    }else {
                        tImport.setText(IsImport+" รายการ");
                        tOcc.setText(IsOcc+" รายการ");
                    }
                    Log.d("ttest","in ListSterileDocDetail : "+xDocNo+"-"+resultssteriledetail.size());

                } catch (JSONException e) {
                    e.printStackTrace();
                }finally {
                    if (dialog.isShowing()){
                        Handler handler = new Handler();
                        Runnable runnable = new Runnable() {
                            @Override
                            public void run() {
                                dialog.dismiss();
                            }
                        };
                        handler.postDelayed(runnable, 500);

                    }
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("xDocNo",params[0]);
                String result = ruc.sendPostRequest(iFt.getsteriledocdetail(),data);
                Log.d("DJKHDK",data+"");
                Log.d("DJKHDK",result+"");
                return  result;
            }
        }
        ListSterileDocDetail ru = new ListSterileDocDetail();
        ru.execute(xDocNo);
    }

    public void CreateOccuranceDocNo(String Sel,String RefDocNo,String OccuranceId,String UserID,String StepNo,String Qty,String FullOcc,String ListRowID,String Cnt,String ChkSwitch) {
        class CreateOccuranceDocNo extends AsyncTask<String, Void, String> {
            // ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                // loading.dismiss();
                try {
                    JSONObject jsonObj = new JSONObject(s);
                    setRs = jsonObj.getJSONArray(TAG_RESULTS);
                    results.clear();
                    for(int i=0;i<setRs.length();i++){
                        JSONObject c = setRs.getJSONObject(i);
                        if(!c.getString("DocNo").equals("Null")) {
//                            textViewProgram.setText(null);
//                            textViewRoundNumber.setText(null);
//                            textViewTime.setText(null);
//                            textViewDocNo.setText(null);
                            rl.clearFocus();

                            Toast.makeText(getApplicationContext(), "บันทึกสำเร็จ...", Toast.LENGTH_LONG).show();
                        }else {
                            Toast.makeText(getApplicationContext(), "ไม่สามารถสร้างเอกสารได้...", Toast.LENGTH_LONG).show();
                            Log.d("ttest","in func CreateOccuranceDocNo");
                        }

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("xSel",params[0]);
                data.put("xRefDocNo",params[1]);
                data.put("xOccuranceId",params[2]);
                data.put("xUserID",params[3]);
                data.put("xStepNo",params[4]);
                data.put("xQty",params[5]);
                data.put("xFullOcc",params[6]);
                data.put("xListRowID",params[7]);
                data.put("xCnt",params[8]);
                data.put("xChkSwitch",params[9]);
                if(B_ID!=null){data.put("B_ID",B_ID);}

                Log.d("OOOO",data+"");

                String result = ruc.sendPostRequest(iFt.createoccurancedocno(),data);
                return  result;
            }
        }
        CreateOccuranceDocNo ru = new CreateOccuranceDocNo();
        ru.execute(Sel,RefDocNo,OccuranceId,UserID,StepNo,Qty,FullOcc,ListRowID,Cnt,ChkSwitch);
    }

    public void ReturnOccurance(String RefDocNo) {
        class ReturnOccurance extends AsyncTask<String, Void, String> {
            // ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                // loading.dismiss();
                try {
                    JSONObject jsonObj = new JSONObject(s);
                    setRs = jsonObj.getJSONArray(TAG_RESULTS);
                    resultssteriledetail.clear();
                    Log.d("ttest","in ReturnOccurance resultssteriledetail.clear");
                    for(int i=0;i<setRs.length();i++){
                        JSONObject c = setRs.getJSONObject(i);
                        if(c.getString("Finish").equals("True")) {
                            ListSterileDocDetail(DocNo);
                            Toast.makeText(getApplicationContext(), "คืนค่าสำเร็จ...", Toast.LENGTH_LONG).show();
                        }else {
                            Toast.makeText(getApplicationContext(), "ไม่สามารถคืนค่าได้...", Toast.LENGTH_LONG).show();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("xRefDocNo",params[0]);
                String result = ruc.sendPostRequest(iFt.retrunoccurance(),data);
                Log.d("BANK",params[0]);
                Log.d("BANK",result);
                return  result;
            }
        }
        ReturnOccurance ru = new ReturnOccurance();
        ru.execute(RefDocNo);
    }

    private String TwoZero(String data){
        String dd = data;
        if(data.length() < 2 ) dd = "0"+data;
        return dd;
    }

    private void ListMachineCount(String xDate) {
        class ListMachineCount extends AsyncTask<String, Void, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loadingDialog = ProgressDialog.show(SterileSeachActivity.this, "", "Loading...", true, false);

            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                try {
                    listMachine_ApStock.clear();
                    Response_Machine_ApStock newsData;
                    JSONObject jsonObj = new JSONObject(s);
                    setRs = jsonObj.getJSONArray(TAG_RESULTS);

                    for(int i=0;i<setRs.length();i++){
                        JSONObject c = setRs.getJSONObject(i);
                        newsData = new Response_Machine_ApStock();
                        newsData.setDocNo( c.getString("DocNo") );
                        newsData.setMachineName(c.getString("MachineName2"));
                        newsData.setSterileName(c.getString("SterileName2"));
                        newsData.setFinishTime(c.getString("FinishTime"));
                        newsData.setMachineCount( Integer.parseInt(c.getString("McCnt")) );
                        newsData.setMcID( Integer.parseInt(c.getString("McID")) );
                        newsData.setIsStatus( Integer.parseInt(c.getString("IsStatus")) );
                        newsData.setSterileRoundNumber( Integer.parseInt(c.getString("SterileRoundNumber")) );
                        listMachine_ApStock.add(newsData);
                        MachineCount = Integer.parseInt(c.getString("McCnt"));
                    }
//                    ListSterileDocDetail( "" );
                    imgMc(MachineCount);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                loadingDialog.dismiss();
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("xDate",params[0]);
                data.put("B_ID",B_ID);
                String result = ruc.sendPostRequest(iFt.getmachinecount(),data);
                Log.d("BVBV",params[0]);
                return  result;
            }
        }

        ListMachineCount ru = new ListMachineCount();
        ru.execute(xDate);
    }

    private void imgMc(int CntMc){
        ImageView iv;
        TextView tv;
        rl.removeAllViews();
        for(int i=0;i<CntMc;i++){
            mCnt = i;
            iv = new ImageView(this);
            tv = new TextView(this);
            // Set an image for ImageView
            switch(i) {
                case 0:iv.setId(R.id.mcButton01);break;
                case 1:iv.setId(R.id.mcButton02);break;
                case 2:iv.setId(R.id.mcButton03);break;
                case 3:iv.setId(R.id.mcButton04);break;
                case 4:iv.setId(R.id.mcButton05);break;
                case 5:iv.setId(R.id.mcButton06);break;
                case 6:iv.setId(R.id.mcButton07);break;
                case 7:iv.setId(R.id.mcButton08);break;
                case 8:iv.setId(R.id.mcButton09);break;
                case 9:iv.setId(R.id.mcButton10);break;
                case 10:iv.setId(R.id.mcButton11);break;
                case 11:iv.setId(R.id.mcButton12);break;
                case 12:iv.setId(R.id.mcButton13);break;
                case 13:iv.setId(R.id.mcButton14);break;
                case 14:iv.setId(R.id.mcButton15);break;
                case 15:iv.setId(R.id.mcButton16);break;
                case 16:iv.setId(R.id.mcButton17);break;
                case 17:iv.setId(R.id.mcButton18);break;
                case 18:iv.setId(R.id.mcButton19);break;
                case 19:iv.setId(R.id.mcButton20);break;
            }
            IMGS[i] = iv;
            tView[i] = tv;
            //DisEnable Button
            if((listMachine_ApStock.size()-1) < i) {
                iv.setEnabled(false);
                iv.setImageDrawable(getDrawable(R.drawable.ic_sterile_mc_grey));
            }else{
                iv.setEnabled(true);
                iv.setImageDrawable(getDrawable(R.drawable.ic_sterile_green));
            }
            // Create layout parameters for ImageView
            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);
            lp.leftMargin = (xWh*i)+5;
            lp.topMargin = 5;
            lp.width = xWh;
            iv.setLayoutParams(lp);
            rl.addView(iv);
            RelativeLayout.LayoutParams lpt = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);
            lpt.leftMargin = (xWh*i)+80;
            lpt.topMargin = 80;
            tv.setLayoutParams(lpt);
            try{tv.setText(listMachine_ApStock.get(i).getMachineName());}catch (Exception e){e.getMessage();};
            tv.setHeight(25);
            tv.setWidth(80);
            tv.setRotation(90);
            tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP,12);
            rl.addView(tv);
            iv.setOnClickListener(this);
        }
//            iv = new ImageView(this);
//            iv.setImageDrawable(getDrawable(R.drawable.bg_sterile_03));

        RelativeLayout.LayoutParams lpi = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);

//            lpi.width = 200;//(xWh*CntMc)+10;
//            lpi.leftMargin = 0;
//            iv.setLayoutParams(lpi);
//            iv.bringToFront();
//            rl.addView(iv);
//            imageBg.setLayoutParams(lpi);
        imageBg.bringToFront();

        RelativeLayout.LayoutParams lpi2 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        lpi2.leftMargin = 0;
        lpi2.topMargin = 5;
        imageBg2.setLayoutParams(lpi2);
        imageBg2.bringToFront();
    }

    public void onClick(View v){
        switch(v.getId()) {
            case R.id.mcButton01:setXY(0);setDocHeader(0);break;
            case R.id.mcButton02:setXY(1);setDocHeader(1);break;
            case R.id.mcButton03:setXY(2);setDocHeader(2);break;
            case R.id.mcButton04:setXY(3);setDocHeader(3);break;
            case R.id.mcButton05:setXY(4);setDocHeader(4);break;
            case R.id.mcButton06:setXY(5);setDocHeader(5);break;
            case R.id.mcButton07:setXY(6);setDocHeader(6);break;
            case R.id.mcButton08:setXY(7);setDocHeader(7);break;
            case R.id.mcButton09:setXY(8);setDocHeader(8);break;
            case R.id.mcButton10:setXY(9);setDocHeader(9);break;
            case R.id.mcButton11:setXY(10);setDocHeader(10);break;
            case R.id.mcButton12:setXY(11);setDocHeader(11);break;
            case R.id.mcButton13:setXY(12);setDocHeader(12);break;
            case R.id.mcButton14:setXY(13);setDocHeader(13);break;
            case R.id.mcButton15:setXY(14);setDocHeader(14);break;
            case R.id.mcButton16:setXY(15);setDocHeader(15);break;
            case R.id.mcButton17:setXY(16);setDocHeader(16);break;
            case R.id.mcButton18:setXY(17);setDocHeader(17);break;
            case R.id.mcButton19:setXY(18);setDocHeader(18);break;
            case R.id.mcButton20:setXY(19);setDocHeader(19);break;
        }
    }

    private void setDocHeader(int index){
        textViewProgram.setText( listMachine_ApStock.get(index).getSterileName() );
        textViewTime.setText( listMachine_ApStock.get(index).getFinishTime() );
        textViewDocNo.setText( listMachine_ApStock.get(index).getDocNo() );
        textViewRoundNumber.setText( listMachine_ApStock.get(index).getSterileRoundNumber()+"" );
        DocNo = listMachine_ApStock.get(index).getDocNo();
        xIsStatus = listMachine_ApStock.get(index).getIsStatus();
        McSel = index;
        ListSterileDocDetail( listMachine_ApStock.get(index).getDocNo() );
        ShowAlertRemart( listMachine_ApStock.get(index).getDocNo() );
    }

    private void ClearDocHeader() {
        textViewProgram.setText("");
        textViewTime.setText("");
        textViewDocNo.setText("");
        textViewRoundNumber.setText("");
        DocNo = "";
        xIsStatus = 0;
        tImport.setText("0");
        tOcc.setText("0");
        lv1.setAdapter(null);
    }

    private void setXY(int index){
        for(int i=0;i<MachineCount;i++) {
            RelativeLayout.LayoutParams lp1 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);
            RelativeLayout.LayoutParams lpt1 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);

            lp1.width = xWh;
            if( i != index) {
                lp1.leftMargin = (xWh*i) + 5;
                lp1.topMargin = 5;
                IMGS[i].setLayoutParams(lp1);
                IMGS[i].bringToFront();
                lpt1.topMargin = 80;
                lpt1.leftMargin = (xWh*i)+80;
                tView[i].setLayoutParams(lpt1);
                tView[i].bringToFront();
            }
        }

        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        lp.leftMargin = (xWh*index) + 5;
        lp.topMargin = -18;
        lp.width = xWh;
        IMGS[index].setLayoutParams(lp);
        IMGS[index].bringToFront();

        imageBg.bringToFront();

        RelativeLayout.LayoutParams lpt = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        lpt.leftMargin = (xWh*index)+80;
        lpt.topMargin = 55;
        tView[index].setLayoutParams(lpt);
        tView[index].bringToFront();
    }

    public void CheckPayDep() {
        class CheckPayDep extends AsyncTask<String, Void, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                try {
                    JSONObject jsonObj = new JSONObject(result);
                    setRs = jsonObj.getJSONArray(TAG_RESULTS);
                    String UsageCode = "";
                    String Cnt = "";
                    for(int i=0;i<setRs.length();i++) {
                        JSONObject c = setRs.getJSONObject(i);
                        UsageCode = c.getString("UsageCode");
                        Cnt = c.getString("Cnt");
                    }
                    if (!UsageCode.equals("flase")) {
                        if( xIsStatus == 1 ) CreateApproveStock(DocNo,userid);
                        Intent intent = new Intent(SterileSeachActivity.this, dialog_approve_sterile.class);
                        intent.putExtra("userid", userid);
                        intent.putExtra("DocNo",DocNo);
                        intent.putExtra("Cnt",Cnt);
                        intent.putExtra("B_ID",B_ID);
                        startActivity(intent);
                    }else {
                        if( xIsStatus == 1 ) CreateApproveStock(DocNo,userid);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("DocNo",DocNo);
                data.put("B_ID",B_ID);
                String result = null;
                try {
                    result = ruc.sendPostRequest(Url.URL + "cssd_display_check_approve_paydep.php", data);
                    Log.d("DJKHDK",data+"");
                    Log.d("DJKHDK",result+"");
                    Log.d("DJKHDK",xIsStatus+"");
                }catch(Exception e){
                    e.printStackTrace();
                }
                return result;
            }
            // =========================================================================================
        }
        CheckPayDep obj = new CheckPayDep();
        obj.execute();
    }

    public void DisplayRemarkItem(final String usagecode) {
        class DisplayRemarkItem extends AsyncTask<String, Void, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);

                try {
                    JSONObject jsonObj = new JSONObject(result);
                    setRs = jsonObj.getJSONArray(TAG_RESULTS);
                    for(int i=0;i<setRs.length();i++) {
                        JSONObject c = setRs.getJSONObject(i);
                        if (!c.getString("IsRemarkExpress").equals("0")){
                            final Dialog dialog = new Dialog(SterileSeachActivity.this);
                            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            dialog.setContentView(R.layout.ems_show_remart);
                            dialog.setCancelable(true);
                            dialog.setTitle("หมายเหตุ...");
                            final TextView note1 = (TextView) dialog.findViewById(R.id.note1);
                            note1.setText(c.getString("RemarkExpress"));
                            Button button1 = (Button) dialog.findViewById(R.id.button1);
                            button1.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    dialog.dismiss();
                                }
                            });
                            dialog.show();
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("usagecode",usagecode);
                String result = null;
                try {
                    result = ruc.sendPostRequest(Url.URL + "cssd_display_remart_usage.php", data);
                    Log.d("DJKHDK",data+"");
                    Log.d("DJKHDK",result+"");
                }catch(Exception e){
                    e.printStackTrace();
                }
                return result;
            }
            // =========================================================================================
        }
        DisplayRemarkItem obj = new DisplayRemarkItem();
        obj.execute();
    }

    public void ShowAlertRemart(final String DocNo) {
        class ShowAlertRemart extends AsyncTask<String, Void, String> {
            private ProgressDialog dialog = new ProgressDialog(SterileSeachActivity.this);
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                this.dialog.setMessage(Cons.WAIT_FOR_PROCESS);
                this.dialog.show();
            }
            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                try {
                    JSONObject jsonObj = new JSONObject(result);
                    setRs = jsonObj.getJSONArray(TAG_RESULTS);
                    String IsRemarkExpress = "";
                    String itemname = "";
                    String Shelflife = "";
                    String UsageCode = "";
                    String Cnt = "";
                    for(int i=0;i<setRs.length();i++) {
                        JSONObject c = setRs.getJSONObject(i);
                        UsageCode = c.getString("UsageCode");
                        Cnt = c.getString("cnt");
                        condition1 = c.getString("condition1");
                        condition2 = c.getString("condition2");
                        condition3 = c.getString("condition3");
                        condition4 = c.getString("condition4");
                    }
                    if (!condition1.equals("0") || !condition2.equals("0") || !condition3.equals("0") || !condition4.equals("0")){
                        Intent intent = new Intent(SterileSeachActivity.this, dialog_check_usage_count.class);
                        intent.putExtra("UsageCode", UsageCode);
                        intent.putExtra("cnt", Cnt);
                        intent.putExtra("DocNo",DocNo);
                        intent.putExtra("B_ID",B_ID);
                        intent.putExtra("sel","1");
                        intent.putExtra("page","1");
                        intent.putExtra("condition1",condition1);
                        intent.putExtra("condition2",condition2);
                        intent.putExtra("condition3",condition3);
                        intent.putExtra("condition4",condition4);
                        startActivity(intent);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }finally {
                    if (dialog.isShowing()) {
                        dialog.dismiss();
                    }
                }
            }
            @SuppressLint("WrongThread")
            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("DOC_NO",DocNo);
                data.put("B_ID",B_ID);
                String result = null;
                try {
                    result = ruc.sendPostRequest(Url.URL + "cssd_check_usage_ems_Approve.php", data);
                    Log.d("DJKHDK",data+"");
                    Log.d("DJKHDK",result+"");
                }catch(Exception e){
                    e.printStackTrace();
                }
                return result;
            }
            // =========================================================================================
        }
        ShowAlertRemart obj = new ShowAlertRemart();
        obj.execute();
    }

    @Override
    public void onBackPressed() {
        ClosePage();
    }

    private void ClosePage(){
        this.finish();
    }
}