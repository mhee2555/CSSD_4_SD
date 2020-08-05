package com.phc.cssd;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.phc.core.connect.HTTPConnect;
import com.phc.core.data.AsonData;
import com.phc.core.string.Cons;

import com.phc.cssd.adapter.ImportWashDetailAdapter;
import com.phc.cssd.adapter.ImportWashDetailBigSizeAdapter;
import com.phc.cssd.adapter.ImportWashDetailGridViewAdapter;
import com.phc.cssd.adapter.ListEdit_L_Adapter;
import com.phc.cssd.adapter.ListEdit_R_Adapter;
import com.phc.cssd.data.Master;
import com.phc.cssd.model.Model;
import com.phc.cssd.model.ModelImportWashDetail;
import com.phc.cssd.model.ModelItemStock;

import com.phc.cssd.model.ModelWashDetail;
import com.phc.cssd.url.Url;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class CssdEditSterile extends Activity {

    private String TAG_RESULTS = "result";
    private JSONArray rs = null;
    private HTTPConnect httpConnect = new HTTPConnect();

    //------------------------------------------------
    // Session Variable
    private String userid = null;
    //------------------------------------------------

    ArrayList<String> doc_no_1 = new ArrayList<String>();
    ArrayList<String> doc_no_2 = new ArrayList<String>();
    ArrayList<String> doc_no_2_data = new ArrayList<String>();

    private List<ModelImportWashDetail> MODEL_IMPORT_WASH_DETAIL = null;

    private HashMap<String, String> ImportID = new HashMap<String,String>();

    private ImageView imageBack;
    private Button btn_qr;
    private EditText txt_qr;

    private TextView txt_machine_1;
    private TextView txt_round_1;
    private TextView txt_usr_prepare_1;
    private TextView txt_usr_approve_1;
    private TextView txt_label_wash_type_1;
    private TextView txt_wash_type_1;
    private TextView txt_label_usr_prepare_1;
    private TextView txt_label_usr_approve_1;
    private TextView txt_test_program_1;
    private TextView txt_program_1;


    private TextView txt_caption;
    private TextView txt_date_1;
    private Button btn_date_1;
    private Spinner spinner_document_1;
    private ListView list_document_1;

    private TextView txt_machine_2;
    private TextView txt_round_2;
    private TextView txt_usr_prepare_2;
    private TextView txt_usr_approve_2;
    private TextView txt_label_wash_type_2;
    private TextView txt_wash_type_2;
    private TextView txt_label_usr_prepare_2;
    private TextView txt_label_usr_approve_2;
    private TextView txt_test_program_2;
    private TextView txt_program_2;

    private TextView txt_date_2;
    private Button btn_date_2;
    private Spinner spinner_document_2;
    private ListView list_document_2;

    private Switch bt_switch;


    static final int DATE_PICKER_ID = 1111;
    private int year;
    private int month;
    private int day;


    String DocNo_1 = "";
    String DocNo_2 = "";
    String ProgramID = "";

    boolean IsWash = true;
    private Switch switch_sterile_doc;

    String B_ID = "1";


    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_PICKER_ID:
                return new DatePickerDialog(this, pickerListener, year, month, day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener pickerListener = new DatePickerDialog.OnDateSetListener() {

        // when dialog box is closed, below method will be called.
        @Override
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {

            year = selectedYear;
            month = selectedMonth;
            day = selectedDay;

            // Show selected date
            txt_date_2.setText(year + "-" + TwoZero((month + 1) + "") + "-" + TwoZero(day + ""));

            clearForm2();

            addDocument(txt_date_2.getText().toString());
        }
    };

    private String TwoZero(String data) {
        String dd = data;
        if (data.length() < 2) dd = "0" + data;
        return dd;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cssd_edit_sterile);

        //getSupportActionBar().hide();

        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);

        byIntent();

        byWidget();

        hideFrom();

        addDocument(txt_date_2.getText().toString());

    }

    public void onDestroy() {
        super.onDestroy();
    }

    private void byIntent() {
        // Argument
        Intent intent = getIntent();
        userid = intent.getStringExtra("userid");
    }


    private void byWidget() {
        imageBack = (ImageView) findViewById(R.id.imageBack);
        txt_caption = (TextView) findViewById(R.id.txt_caption);

        bt_switch = (Switch) findViewById(R.id.bt_switch);
        bt_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                IsWash = !bt_switch.isChecked();

                bt_switch.setText(IsWash ? "แก้ไขข้อมูลการล้าง" : "แก้ไขข้อมูลการฆ่าเชื้อ");
                txt_caption.setText(IsWash ? "แก้ไขข้อมูลการล้าง" : "แก้ไขข้อมูลการฆ่าเชื้อ");

                clearForm1();

                clearForm2();

                hideFrom();

                addDocument(txt_date_2.getText().toString());


            }
        });

        switch_sterile_doc = (Switch) findViewById(R.id.switch_sterile_doc);
        switch_sterile_doc.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(!isChecked){
                    switch_sterile_doc.setText("แก้ไขเอกสาร");
                }else{
                    switch_sterile_doc.setText("แก้ไขข้อมูลล้าง-ฆ่าเชื้อ");
                }

                switch_sterile_doc(isChecked);
            }
        });

        // 1
        txt_machine_1 = (TextView) findViewById(R.id.txt_machine_1);
        txt_round_1 = (TextView) findViewById(R.id.txt_round_1);
        txt_usr_prepare_1 = (TextView) findViewById(R.id.txt_usr_prepare_1);
        txt_usr_approve_1 = (TextView) findViewById(R.id.txt_usr_approve_1);
        txt_label_wash_type_1 = (TextView) findViewById(R.id.txt_label_wash_type_1);
        txt_wash_type_1 = (TextView) findViewById(R.id.txt_wash_type_1);
        txt_label_usr_prepare_1 = (TextView) findViewById(R.id.txt_label_usr_prepare_1);
        txt_label_usr_approve_1 = (TextView) findViewById(R.id.txt_label_usr_approve_1);
        txt_test_program_1 = (TextView) findViewById(R.id.txt_test_program_1);
        txt_program_1 = (TextView) findViewById(R.id.txt_program_1);

        txt_date_1 = (TextView) findViewById(R.id.txt_date_1);
        btn_date_1 = (Button) findViewById(R.id.btn_date_1);
        spinner_document_1 = (Spinner) findViewById(R.id.spinner_document_1);
        list_document_1 = (ListView) findViewById(R.id.list_document_1);


        // 2
        txt_machine_2 = (TextView) findViewById(R.id.txt_machine_2);
        txt_round_2 = (TextView) findViewById(R.id.txt_round_2);
        txt_usr_prepare_2 = (TextView) findViewById(R.id.txt_usr_prepare_2);
        txt_usr_approve_2 = (TextView) findViewById(R.id.txt_usr_approve_2);
        txt_label_wash_type_2 = (TextView) findViewById(R.id.txt_label_wash_type_2);
        txt_wash_type_2 = (TextView) findViewById(R.id.txt_wash_type_2);
        txt_label_usr_prepare_2 = (TextView) findViewById(R.id.txt_label_usr_prepare_2);
        txt_label_usr_approve_2 = (TextView) findViewById(R.id.txt_label_usr_approve_2);
        txt_test_program_2 = (TextView) findViewById(R.id.txt_test_program_2);
        txt_program_2 = (TextView) findViewById(R.id.txt_program_2);


        txt_date_2 = (TextView) findViewById(R.id.txt_date_2);
        btn_date_2 = (Button) findViewById(R.id.btn_date_2);
        spinner_document_2 = (Spinner) findViewById(R.id.spinner_document_2);
        list_document_2 = (ListView) findViewById(R.id.list_document_2);

        txt_qr = (EditText) findViewById(R.id.txt_qr);

        txt_test_program_2.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){

                if(!DocNo_2.equals("") && !ProgramID.equals("")) {

                    String data = IsWash ? Master.s_wash_test_program : Master.s_sterile_test_program ;

                    openDropDown(data, ProgramID);
                }else{
                    Toast.makeText(CssdEditSterile.this, "ไม่สามารถเลือกข้อมูลได้ !!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        txt_date_2.setText(year + "-" + TwoZero((month + 1) + "") + "-" + TwoZero(day + ""));

        btn_date_2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // On button click show datepicker dialog
                showDialog(DATE_PICKER_ID);
            }
        });

        txt_qr.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    Log.d("ttest_qr","txt_qr--"+(switch_sterile_doc.isChecked()&&bt_switch.isChecked()));
                    if(switch_sterile_doc.isChecked()&&bt_switch.isChecked()){
                        Log.d("ttest_qr","txt_qr--"+txt_qr.getText().toString().substring(0,5));
                        getItemToMac(txt_qr.getText().toString());
                    }else{
                        clearForm1();
                        checkQR(txt_qr.getText().toString());
                    }

                    return true;
                }
                return false;
            }
        });

        /*
        txt_qr.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    switch (keyCode) {
                        case KeyEvent.KEYCODE_DPAD_CENTER:
                        case KeyEvent.KEYCODE_ENTER:



                            checkQR(txt_qr.getText().toString());

                            clearForm1();

                            return true;
                        default:
                            break;
                    }
                }

                return false;

            }


        });
        */

        spinner_document_2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                list_document_2.setAdapter(null);

                if (position == 0) {
                    DocNo_2 = "";
                    ProgramID = "";
                    return;
                }


                int size = 12;
                int index = position*size;

                //System.out.println(size +"-"+position+"="+(position*size));

                DocNo_2 = doc_no_2_data.get(index);
                ProgramID = doc_no_2_data.get(index + 11);

                txt_machine_2.setText(doc_no_2_data.get(index + 2));
                txt_round_2.setText(doc_no_2_data.get(index + 1));
                txt_usr_prepare_2.setText(doc_no_2_data.get(index + 6));
                txt_usr_approve_2.setText(doc_no_2_data.get(index + 7));
                txt_wash_type_2.setText(doc_no_2_data.get(index + 9));
                txt_test_program_2.setText(doc_no_2_data.get(index + 4));
                txt_program_2.setText(doc_no_2_data.get(index + 10));

                // Display Detail
                onDisplayDetail(DocNo_2, "R", null);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    public void switch_sterile_doc(boolean c){

        clearForm1();

        int vis = View.VISIBLE;
        if(c){
            vis = View.GONE;
            displayWashDetail();
        }

        findViewById(R.id.M).setVisibility(vis);



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(data == null)
            return;

        try {


            String RETURN_DATA = data.getStringExtra("RETURN_DATA");
            String RETURN_VALUE = data.getStringExtra("RETURN_VALUE");

            if (resultCode == Master.sterile_test_program) {
                txt_test_program_2.setText(RETURN_DATA);
                txt_test_program_2.setContentDescription(RETURN_VALUE);

                updateTestProgram(DocNo_2, RETURN_VALUE );

            }else if (resultCode == Master.wash_test_program) {
                txt_test_program_2.setText(RETURN_DATA);
                txt_test_program_2.setContentDescription(RETURN_VALUE);

                updateTestProgram(DocNo_2, RETURN_VALUE);

            }

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    // Dropdown list
    private void openDropDown(String data, String filter){
        Intent i = new Intent(CssdEditSterile.this, MasterDropdown.class);
        i.putExtra("data", data);
        i.putExtra("filter", filter);
        startActivityForResult(i, Master.getResult(data));
    }

    private void hideFrom(){
        txt_label_wash_type_1.setVisibility(IsWash ? View.VISIBLE : View.GONE);
        txt_wash_type_1.setVisibility(IsWash ? View.VISIBLE : View.GONE);
        txt_label_usr_prepare_1.setVisibility(!IsWash ? View.VISIBLE : View.GONE);
        txt_usr_prepare_1.setVisibility(!IsWash ? View.VISIBLE : View.GONE);
        txt_label_usr_approve_1.setVisibility(!IsWash ? View.VISIBLE : View.GONE);
        txt_usr_approve_1.setVisibility(!IsWash ? View.VISIBLE : View.GONE);

        txt_label_wash_type_2.setVisibility(IsWash ? View.VISIBLE : View.GONE);
        txt_wash_type_2.setVisibility(IsWash ? View.VISIBLE : View.GONE);
        txt_label_usr_prepare_2.setVisibility(!IsWash ? View.VISIBLE : View.GONE);
        txt_usr_prepare_2.setVisibility(!IsWash ? View.VISIBLE : View.GONE);
        txt_label_usr_approve_2.setVisibility(!IsWash ? View.VISIBLE : View.GONE);
        txt_usr_approve_2.setVisibility(!IsWash ? View.VISIBLE : View.GONE);
        switch_sterile_doc.setVisibility(!IsWash ? View.VISIBLE : View.GONE);
    }

    public void focus() {
        txt_qr.setText("");
        txt_qr.requestFocus();
    }

    public void clearForm1(){
        DocNo_1 = "";
        txt_date_1.setText("");
        txt_machine_1.setText("");
        txt_round_1.setText("");
        txt_usr_prepare_1.setText("");
        txt_usr_approve_1.setText("");
        txt_wash_type_1.setText("");
        txt_program_1.setText("");
        txt_test_program_1.setText("");
        spinner_document_1.setAdapter(null);

        doc_no_1.clear();
        list_document_1.setAdapter(null);

        focus();
    }

    public void clearForm2(){
        DocNo_2 = "";
        ProgramID = "";
        txt_machine_2.setText("");
        txt_round_2.setText("");
        txt_usr_prepare_2.setText("");
        txt_usr_approve_2.setText("");
        txt_wash_type_2.setText("");
        txt_program_2.setText("");
        txt_test_program_2.setText("");
        spinner_document_2.setAdapter(null);

        txt_test_program_2.setText("");
        doc_no_2.clear();
        list_document_2.setAdapter(null);

        focus();
    }

    // =============================================================================================
    // Check Sterile Log
    // =============================================================================================

    public void checkQR(final String p_qr) {

        class Add extends AsyncTask<String, Void, String> {

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                DocNo_1 = "";

                try {
                    JSONObject jsonObj = new JSONObject(s);
                    rs = jsonObj.getJSONArray(TAG_RESULTS);

                    for (int i = 0; i < rs.length(); i++) {
                        JSONObject c = rs.getJSONObject(i);

                        if (c.getString("result").equals("A")) {

                            DocNo_1 = c.getString("DocNo");

                            txt_machine_1.setText(c.getString("MachineName"));
                            txt_round_1.setText(c.getString("RoundNumber"));
                            txt_usr_prepare_1.setText(c.getString("usr_prepare"));
                            txt_usr_approve_1.setText(c.getString("usr_approve"));
                            txt_wash_type_1.setText(c.getString("WashTypeName"));
                            txt_test_program_1.setText(c.getString("TestProgramName"));
                            txt_program_1.setText(c.getString("ProgramName"));
                            txt_date_1.setText(c.getString("DocDate"));

                            doc_no_1.clear();
                            doc_no_1.add(c.getString("DocNo"));
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(CssdEditSterile.this, android.R.layout.simple_spinner_dropdown_item, doc_no_1);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinner_document_1.setAdapter(adapter);
                            spinner_document_1.setSelection(0);


                            // Display Detail
                            onDisplayDetail(c.getString("DocNo"), "L", p_qr);

                        }else{
                            DocNo_1 = "";
                            Toast.makeText(CssdEditSterile.this, Cons.WARNING_NOT_FOUND, Toast.LENGTH_SHORT).show();
                        }
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String, String>();
                data.put("p_qr", p_qr);

                if(IsWash){
                    data.put("p_is_wash", "1");
                }

                String result = httpConnect.sendPostRequest(Url.URL + "cssd_find_qr_from_edit.php", data);

                return result;
            }
        }

        Add ru = new Add();

        ru.execute();
    }
    // =============================================================================================

    public void addDocument(final String p_date) {
        class Add extends AsyncTask<String, Void, String> {

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                try {
                    JSONObject jsonObj = new JSONObject(s);
                    rs = jsonObj.getJSONArray(TAG_RESULTS);

                    doc_no_2.clear();
                    doc_no_2_data.clear();

                    doc_no_2.add("-");

                    for (int i = 0; i < 12; i++) {
                        doc_no_2_data.add("");
                    }

                    for (int i = 0; i < rs.length(); i++) {
                        JSONObject c = rs.getJSONObject(i);

                        if (c.getString("result").equals("A")) {
                            //DocNo
                            doc_no_2.add(c.getString("Label"));

                            doc_no_2_data.add(c.getString("DocNo"));
                            doc_no_2_data.add(c.getString("RoundNumber"));
                            doc_no_2_data.add(c.getString("MachineName"));
                            doc_no_2_data.add(c.getString("TestProgramID"));
                            doc_no_2_data.add(c.getString("TestProgramName"));

                            //Data
                            doc_no_2_data.add(c.getString("usr_sterile"));
                            doc_no_2_data.add(c.getString("usr_prepare"));
                            doc_no_2_data.add(c.getString("usr_approve"));
                            doc_no_2_data.add(c.getString("WashTypeID"));
                            doc_no_2_data.add(c.getString("WashTypeName"));

                            doc_no_2_data.add(c.getString("Program"));
                            doc_no_2_data.add(c.getString("ProgramID"));
                        }
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(CssdEditSterile.this, android.R.layout.simple_spinner_dropdown_item, doc_no_2);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner_document_2.setAdapter(adapter);
                    spinner_document_2.setSelection(0);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String, String>();
                data.put("p_date", p_date);

                String file = IsWash ? "cssd_select_wash_doc_no.php" : "cssd_select_sterile_doc_no.php";
                String result = httpConnect.sendPostRequest(Url.URL + file, data);

                return result;
            }
        }

        Add ru = new Add();

        ru.execute();
    }

    // =============================================================================================
    // Display Detail
    // =============================================================================================
    private void onDisplayDetail(final String DocNo, final String lv, final String p_qr){
        class DisplayPay extends AsyncTask<String, Void, String> {

            private ProgressDialog dialog = new ProgressDialog(CssdEditSterile.this);

            // variable
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                this.dialog.setTitle(Cons.TITLE);
                this.dialog.setIcon(R.drawable.pose_favicon_2x);
                this.dialog.setMessage(Cons.WAIT_FOR_PROCESS);
                this.dialog.setCanceledOnTouchOutside(false);
                this.dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0xEFFFFFFF));
                this.dialog.setIndeterminate(true);
                this.dialog.show();
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);

                List<ModelItemStock> list = new ArrayList<>();

                try {
                    JSONObject jsonObj = new JSONObject(result);
                    rs = jsonObj.getJSONArray(TAG_RESULTS);
                    ImportID.clear();
                    for(int i=0;i<rs.length();i++){
                        JSONObject c = rs.getJSONObject(i);

                        if(c.getString("result").equals("A")) {
                            list.add(
                                    new ModelItemStock(
                                            c.getString("ID"),
                                            c.getString("DocNo"),
                                            c.getString("itemname"),
                                            c.getString("itemcode"),
                                            c.getString("UsageCode"),
                                            c.getString("RowID"),
                                            i
                                    )

                            );

                            if(!IsWash){
                                ImportID.put(c.getString("ID"),c.getString("ImportID"));
                            }
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if(lv.equals("L")) {
                    ArrayAdapter<ModelItemStock> adapter;
                    adapter = new ListEdit_L_Adapter(CssdEditSterile.this, list);
                    list_document_1.setAdapter(adapter);
                }else if(lv.equals("R")){
                    ArrayAdapter<ModelItemStock> adapter;
                    adapter = new ListEdit_R_Adapter(CssdEditSterile.this, list,switch_sterile_doc.isChecked());
                    list_document_2.setAdapter(adapter);
                }

                if (dialog.isShowing()) {
                    dialog.dismiss();
                }

            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("p_docno", DocNo);

                if(lv.equals("L")) {

                }else if(lv.equals("R")){

                }

                String result = null;

                String file = IsWash ? "cssd_display_edit_wash_detail.php": "cssd_display_edit_sterile_detail.php";

                try {
                    result = httpConnect.sendPostRequest(Url.URL + file, data);
                }catch(Exception e){
                    e.printStackTrace();
                }

                return result;
            }



            // =========================================================================================
        }

        DisplayPay obj = new DisplayPay();
        obj.execute();
    }

    // =============================================================================================
    // L
    // =============================================================================================
    public void toL(String ID){
        // Check DocNo
        if(checkDocNo()){
            onChangeDocNo(DocNo_2, DocNo_1, ID);
        }else{
            Toast.makeText(CssdEditSterile.this, Cons.WARNING_DUPLICATE_DOC, Toast.LENGTH_SHORT).show();
        }
    }

    // =============================================================================================
    // R
    // =============================================================================================
    public void toR(String ID){
        // Check DocNo
        if(checkDocNo()){
            onChangeDocNo(DocNo_1, DocNo_2, ID);
        }else{
            Toast.makeText(CssdEditSterile.this, Cons.WARNING_DUPLICATE_DOC, Toast.LENGTH_SHORT).show();
        }
    }


    private boolean checkDocNo(){

        if(DocNo_1.equals("") || DocNo_2.equals("")){
            Toast.makeText(CssdEditSterile.this, Cons.WARNING_SELECT_DOC, Toast.LENGTH_SHORT).show();
            return false;
        }

        try {
            return !DocNo_1.equals(DocNo_2);

        }catch(Exception e){
            return false;
        }
    }

    // =============================================================================================
    // Change Doc No
    // =============================================================================================

    public void onChangeDocNo(final String DocNo_Send, final String DocNo, final String ID) {

        class Add extends AsyncTask<String, Void, String> {

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                try {
                    JSONObject jsonObj = new JSONObject(s);
                    rs = jsonObj.getJSONArray(TAG_RESULTS);

                    for (int i = 0; i < rs.length(); i++) {
                        JSONObject c = rs.getJSONObject(i);

                        if (c.getString("result").equals("A")) {

                            // Display Detail L
                            onDisplayDetail(DocNo_1, "L", null);

                            // Display Detail R
                            onDisplayDetail(DocNo_2, "R", null);

                        }else{
                            Toast.makeText(CssdEditSterile.this, c.getString("Message"), Toast.LENGTH_SHORT).show();
                        }
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String, String>();
                data.put("p_docno", DocNo);
                data.put("p_docno_send", DocNo_Send);
                data.put("p_userid", userid);
                data.put("p_id", ID);

                if(IsWash){
                    data.put("p_is_wash", "1");
                }

                String result;

                result = httpConnect.sendPostRequest(Url.URL + "cssd_edit_data.php", data);

                return result;
            }
        }

        Add ru = new Add();

        ru.execute();
    }

    // =============================================================================================
    // Update Test Program
    // =============================================================================================

    public void updateTestProgram(final String DocNo, final String ID) {

        class Update extends AsyncTask<String, Void, String> {

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                try {
                    JSONObject jsonObj = new JSONObject(s);
                    rs = jsonObj.getJSONArray(TAG_RESULTS);

                    for (int i = 0; i < rs.length(); i++) {
                        JSONObject c = rs.getJSONObject(i);

                        Toast.makeText(CssdEditSterile.this, c.getString("Message"), Toast.LENGTH_SHORT).show();

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String, String>();
                data.put("p_docno", DocNo);
                data.put("p_id", ID);
                data.put("p_userid", userid);

                if(IsWash){
                    data.put("p_is_wash", "1");
                }

                String result;

                result = httpConnect.sendPostRequest(Url.URL + "cssd_update_test_program.php", data);

                return result;
            }
        }

        Update ru = new Update();

        ru.execute();
    }

    public void getItemToMac(String usagecode) {
        txt_qr.setText("");
        String itemcode = usagecode.substring(0,5).toUpperCase();

        Log.d("ttest_getToMac","itemcode - "+itemcode);
        for(int i =0;i<MODEL_IMPORT_WASH_DETAIL.size();i++){

            Log.d("ttest_getToMac","M-itemcode - "+MODEL_IMPORT_WASH_DETAIL.get(i).getI_code());
            if(MODEL_IMPORT_WASH_DETAIL.get(i).getI_code().equals(itemcode)){
                importWashDetail(
                        MODEL_IMPORT_WASH_DETAIL.get(i).getI_code(),
                        MODEL_IMPORT_WASH_DETAIL.get(i).getI_program_id() ,
                        MODEL_IMPORT_WASH_DETAIL.get(i).getI_program(),
                        MODEL_IMPORT_WASH_DETAIL.get(i).getPackingMatID(),
                        "0",
                        usagecode
                );
                return;
            }
        }
        Toast.makeText(CssdEditSterile.this, "สถานะรหัสใช้งำนไม่ตรงตำมเงื่อนไข", Toast.LENGTH_SHORT).show();
    }

    public void displayWashDetail() {

        class DisplayWashDetail extends AsyncTask<String, Void, String> {

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

                        MODEL_IMPORT_WASH_DETAIL = getImportWashDetail();

                        for(int i=0;i<MODEL_IMPORT_WASH_DETAIL.size();i++){

                        }

                        try {
                            ArrayAdapter<ModelImportWashDetail> adapter;

                            adapter = new ImportWashDetailAdapter(CssdEditSterile.this, MODEL_IMPORT_WASH_DETAIL,2);
                            list_document_1.setAdapter(adapter);


                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        return;
                    }

                }else{
                    list_document_1.setAdapter(null);

                    MODEL_IMPORT_WASH_DETAIL = null;
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("p_Mode", "1");
                String result = null;

                try {
                    result = httpConnect.sendPostRequest(Url.URL + "cssd_display_import_wash_detail_all.php", data);
                    Log.d("BANKFH",data+"");
                    Log.d("BANKFH",result+"");
                }catch(Exception e){
                    e.printStackTrace();
                }

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
                                        data.get(i + 12),
                                        data.get(i + 13)
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

        DisplayWashDetail obj = new DisplayWashDetail();
        obj.execute();
    }

    public void importWashDetail(String Id, String SterileProgramID, String SterileProgramName, String PackingMatID,String gQty,String usagecode){
        Log.d("ttest_DocNo",DocNo_1+"--1");
        Log.d("ttest_DocNo",DocNo_2+"--2");
        onImport(Id, SterileProgramID, SterileProgramName, PackingMatID, gQty,usagecode);
    }

    private void onImport(String Id, String SterileProgramID, String SterileProgramName, String PackingMatID, String gQty,String usagecode){

        // Check Machine Active
        if (DocNo_2==""||DocNo_2==null){
            Toast.makeText(CssdEditSterile.this, "ยังไม่ได้เลือกเครื่องฆ่าเชื้อ!", Toast.LENGTH_SHORT).show();
            return;
        }

        addSterileDetail(DocNo_2, Id, PackingMatID,gQty,userid,usagecode);

    }

    public void addSterileDetail(final String p_docno, final String p_data, final String p_PackingMatID,final String p_Qty,final String usercode,final String usagecode) {

        class AddSterileDetail extends AsyncTask<String, Void, String> {


            private ProgressDialog dialog = new ProgressDialog(CssdEditSterile.this);

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
                this.dialog.setMessage(Cons.WAIT_FOR_PROCESS);
                this.dialog.setCancelable(false);
                this.dialog.show();
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);

                try {
                    JSONObject jsonObj = new JSONObject(result);
                    rs = jsonObj.getJSONArray(TAG_RESULTS);

                    for(int i=0;i<rs.length();i++){
                        JSONObject c = rs.getJSONObject(i);
                        Log.d("OOOO","result :: "+c.getString("result"));
                        if(c.getString("result").equals("A")) {
                            // Display Sterile Detail
                            onDisplayDetail(DocNo_2, "R", null);
                            // Display Import Wash Detail
                            displayWashDetail();
                        }else{
                            Toast.makeText(CssdEditSterile.this, "ไม่พบรายการ !!", Toast.LENGTH_SHORT).show();
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }finally{
                    if (dialog.isShowing()) {
                        dialog.dismiss();
                    }
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();

                data.put("p_data", p_data);
                data.put("p_docno", p_docno);
                data.put("p_Qty", p_Qty);

                if(p_PackingMatID != null) {
                    data.put("p_PackingMatID", p_PackingMatID);
                }

                if(usagecode != null) {
                    data.put("p_UsageCode", usagecode);
                }
                data.put("Admincode", usercode);
                if(B_ID != null){
                    data.put("p_bid", B_ID);
                }
                data.put("p_is_status", "1");

                Log.d("OOOO","data :: "+data);
                String result = httpConnect.sendPostRequest(Url.URL + "cssd_add_sterile_detail_json.php", data);
                Log.d("OOOO","result :: "+result);
                return result;
            }

            // =========================================================================================
        }

        AddSterileDetail obj = new AddSterileDetail();
        obj.execute();
    }

    public void removeSterileDetail(final String ID,String LIST_ITEM_STOCK_ID) {

        final String p_data = ID + "@" + ImportID.get(ID) + "@" + LIST_ITEM_STOCK_ID + "@";

        class RemoveSterileDetail extends AsyncTask<String, Void, String> {

            private ProgressDialog dialog = new ProgressDialog(CssdEditSterile.this);

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

                this.dialog.setMessage(Cons.WAIT_FOR_PROCESS);
                this.dialog.show();
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);

                AsonData ason = new AsonData(result);

                Success = ason.isSuccess();
                size = ason.getSize();
                data = ason.getASONData();

                if(Success && data != null) {
                    // Display Sterile Detail
                    onDisplayDetail(DocNo_2, "R", null);
                    // Display Import Wash Detail
                    displayWashDetail();

                    if (dialog.isShowing()) {
                        dialog.dismiss();
                    }

                }else{
                    if (dialog.isShowing()) {
                        dialog.dismiss();
                    }
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();

                data.put("p_data", p_data);

                if(B_ID != null){
                    data.put("p_bid", B_ID);
                }

                String result = httpConnect.sendPostRequest(Url.URL + "cssd_remove_sterile_detail.php", data);
                Log.d("LJDLJDL",data+"");
                return result;
            }

            // =========================================================================================
        }

        RemoveSterileDetail obj = new RemoveSterileDetail();
        obj.execute();
    }

}

