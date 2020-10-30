package com.phc.cssd;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.phc.core.connect.HTTPConnect;
import com.phc.core.data.AsonData;
import com.phc.core.string.Cons;
import com.phc.cssd.adapter.WashDetailManagementGridViewAdapter;
import com.phc.cssd.data.Master;
import com.phc.cssd.model.ModelWashDetail;
import com.phc.cssd.url.Url;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class CssdWashDetailManagement extends AppCompatActivity {

    private HTTPConnect httpConnect = new HTTPConnect();

    private GridView grid_wash_detail;
    private TextView txt_packingmat;
    private TextView txt_item_code;
    private TextView txt_item_name;
    private EditText edt_qty;
    private Button btn_plus;
    private Button btn_minus;

    private ImageView image_save;
    private ImageView imageBack;

    String itemcode;
    String itemname;
    private String B_ID = null;

    int DAY = 0;
    int MAX_WASH_DETAIL = 0;

    private List<ModelWashDetail> MODEL_WASH_DETAIL = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        by Itemcode
        setContentView(R.layout.activity_cssd_wash_detail_management);

//        by w_id
//        setContentView(R.layout.activity_cssd_wash_detail_management_pack);

        // Permission StrictMode
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        // =========================================================================================

        byIntent();

        getSupportActionBar().hide();

        byWidget();

//         Display
        displayWashDetail(itemcode);
    }

    private void byIntent(){
        // Argument
        Intent intent = getIntent();
        itemcode = intent.getStringExtra("itemcode");
        itemname = intent.getStringExtra("itemname");
        B_ID = intent.getStringExtra("B_ID");
    }

    private void byWidget() {
        txt_item_code = (TextView) findViewById(R.id.txt_item_code);
        txt_item_name = (TextView) findViewById(R.id.txt_item_name);
        txt_packingmat = (TextView) findViewById(R.id.txt_packingmat);
        image_save = (ImageView) findViewById(R.id.image_save);
        imageBack = (ImageView) findViewById(R.id.imageBack);

        grid_wash_detail = (GridView) findViewById(R.id.grid_wash_detail);
        edt_qty = (EditText) findViewById(R.id.edt_qty);
        btn_plus = (Button) findViewById(R.id.btn_plus);
        btn_minus = (Button) findViewById(R.id.btn_minus);

        txt_item_code.setText("รหัส : " + itemcode);
        txt_item_name.setText("รายการ : " + itemname);

        imageBack.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                finish();
            }
        });

        image_save.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if(txt_packingmat.getContentDescription() == null){
                    Toast.makeText(CssdWashDetailManagement.this, "ยังไม่ได้เลือกรูปแบบการห่อ" , Toast.LENGTH_SHORT).show();
                    return;
                }

                if(!getCheckCount()){
                    Toast.makeText(CssdWashDetailManagement.this, "ยังไม่ได้เลือกรายการ" , Toast.LENGTH_SHORT).show();
                   return;
                }

                updatePacking();

//                onUpdatePacking(txt_item_code.getText().toString(), txt_packingmat.getContentDescription().toString());

            }
        });

        txt_packingmat.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                openDropDown("packingmat");
            }
        });

        edt_qty.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {

                //System.out.println("CCCCCCCCC");


                if(!edt_qty.getText().toString().trim().equals("")){
                    try {
                        int Qty = Integer.valueOf(edt_qty.getText().toString()).intValue();

                        if(Qty > MAX_WASH_DETAIL){
                            edt_qty.setText("");
                        }else{
                            updateIsCheck( Integer.valueOf(edt_qty.getText().toString()).intValue() );
                        }

                    }catch (Exception e){

                    }
                }

            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });


        btn_plus.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                try {
                    if(edt_qty.getText().toString().trim().equals("")) {
                        edt_qty.setText("1");
                        //updateIsCheck(1);
                    }else{
                        int Qty = Integer.valueOf(edt_qty.getText().toString()).intValue();

                        edt_qty.setText( Integer.toString( ( (Qty+1) > MAX_WASH_DETAIL) ? MAX_WASH_DETAIL : (Qty + 1) ) );

                        //updateIsCheck( Integer.valueOf(edt_qty.getText().toString()).intValue() );
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

                        //updateIsCheck( Integer.valueOf(edt_qty.getText().toString()).intValue() );
                    }
                }catch (Exception e){
                    edt_qty.setText("0");
                }
            }
        });
    }


    // =============================================================================================
    // -- on Update Packing
    // =============================================================================================

    public void onUpdatePacking(final String p_data, final String p_PackingMatID) {

        class RemoveSterileDetail extends AsyncTask<String, Void, String> {

            private ProgressDialog dialog = new ProgressDialog(CssdWashDetailManagement.this);

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

                    terminate();

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
                data.put("p_PackingMatID", p_PackingMatID);


                String result = httpConnect.sendPostRequest(Url.URL + "cssd_update_wash_detail_packing.php", data);

                System.out.println("URL = " + result);

                return result;
            }

            // =========================================================================================
        }

        RemoveSterileDetail obj = new RemoveSterileDetail();
        obj.execute();
    }

    // Dropdown list
    private void openDropDown(String data){
        Intent i = new Intent(CssdWashDetailManagement.this, MasterDropdown.class);
        i.putExtra("data", data);
        startActivityForResult(i, Master.getResult(data));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        try {
            if(data != null) {
                String RETURN_DATA = data.getStringExtra("RETURN_DATA");
                String RETURN_VALUE = data.getStringExtra("RETURN_VALUE");

                if (resultCode == Master.packingmat) {
                    txt_packingmat.setText(RETURN_DATA);
                    txt_packingmat.setContentDescription(RETURN_VALUE);
                    DAY = Integer.valueOf( RETURN_DATA.substring(RETURN_DATA.lastIndexOf("[") + 1, RETURN_DATA.length() - 1)).intValue();
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private void terminate(){
        Intent intent = new Intent();
        intent.putExtra("RETURN_DATA", "OK");
        intent.putExtra("RETURN_VALUE", "OK");
        setResult(Master.CssdWashDetailManagement, intent);
        finish();
    }

    private void updatePacking(){
        AlertDialog.Builder quitDialog = new AlertDialog.Builder(CssdWashDetailManagement.this);
        quitDialog.setTitle(Cons.TITLE);
        quitDialog.setMessage("ยืนยันการเปลี่ยนรูปแบบการห่อ");

        quitDialog.setPositiveButton("ตกลง", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                onUpdatePacking();
            }
        });

        quitDialog.setNegativeButton("ยกเลิก", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        quitDialog.show();
    }

    private void onUpdatePacking(){
        if(MODEL_WASH_DETAIL != null){

            String DATA = "";

            // Set
            Iterator li = MODEL_WASH_DETAIL.iterator();
            while(li.hasNext()) {
                try {
                    ModelWashDetail m = (ModelWashDetail) li.next();

                    if(m.isCheck()) {
                        DATA += m.getID() + "@";
                    }

                }catch(Exception e){
                    e.printStackTrace();
                    continue;
                }
            }

            onUpdatePacking(DATA, txt_packingmat.getContentDescription().toString());

        }
    }

    private void updateIsCheck(int select){

        //System.out.println("select = " + select);

        if(MODEL_WASH_DETAIL != null){
            Iterator li = MODEL_WASH_DETAIL.iterator();
            int i = 0;

            // Clear
            while(li.hasNext()) {
                try {
                    ModelWashDetail m = (ModelWashDetail) li.next();
                    m.setCheck(false);
                }catch(Exception e){

                }
            }

            // Set
            Iterator li2 = MODEL_WASH_DETAIL.iterator();
            while(li2.hasNext()) {
                try {
                    ModelWashDetail m = (ModelWashDetail) li2.next();
                    m.setCheck(true);
                    i++;

                    if(i >= select){
                        break;
                    }
                }catch(Exception e){
                    e.printStackTrace();
                    continue;
                }
            }

            //System.out.println("i = " + i);

            if(i > 0){
                try {
                    ArrayAdapter<ModelWashDetail> adapter;
                    adapter = new WashDetailManagementGridViewAdapter(CssdWashDetailManagement.this, MODEL_WASH_DETAIL);
                    grid_wash_detail.setAdapter(adapter);
                } catch (Exception e) {
                    grid_wash_detail.setAdapter(null);
                    e.printStackTrace();
                }
            }
        }
    }


    private boolean getCheckCount(){

        if(MODEL_WASH_DETAIL != null){
            int i = 0;

            // Set
            Iterator li = MODEL_WASH_DETAIL.iterator();
            while(li.hasNext()) {
                try {
                    ModelWashDetail m = (ModelWashDetail) li.next();

                    if(m.isCheck()){
                        return true;
                    }
                }catch(Exception e){
                    e.printStackTrace();
                    continue;
                }
            }

            return false;
        }else{
            return false;
        }
    }


//     =============================================================================================
//     -- Display Wash Detail ...
//     =============================================================================================

    public void displayWashDetail(final String p_itemcode) {

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

                        MODEL_WASH_DETAIL = getModelWashDetail();

                        try {
                            ArrayAdapter<ModelWashDetail> adapter;

                            adapter = new WashDetailManagementGridViewAdapter(CssdWashDetailManagement.this, MODEL_WASH_DETAIL);
                            grid_wash_detail.setAdapter(adapter);


                        } catch (Exception e) {
                            grid_wash_detail.setAdapter(null);
                            e.printStackTrace();
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        return;
                    }

                }else{
                    grid_wash_detail.setAdapter(null);
                    MODEL_WASH_DETAIL = null;
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("p_itemcode", p_itemcode);

                if(B_ID != null){
                    data.put("p_bid", B_ID);
                }

                String result = httpConnect.sendPostRequest(Url.URL + "cssd_display_wash_detail.php", data);

                return result;
            }

            private List<ModelWashDetail> getModelWashDetail() throws Exception{

                List<ModelWashDetail> list = new ArrayList<>();

                try {
                    int index = 0;

                    for(int i=0;i<data.size();i+=size){

                        list.add(
                                get(

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
                                        index
                                )
                        );

                        index++;
                    }

                    MAX_WASH_DETAIL = index;

                    // System.out.println("list = " + list.size());

                }catch(Exception e){
                    e.printStackTrace();
                }

                return list;
            }

            private ModelWashDetail get(
                    String ID,
                    String WashDocNo,
                    String ItemStockID,
                    String Qty,
                    String DepName,
                    String DepName2,
                    String itemname,
                    String itemcode,
                    String UsageCode,
                    String age,
                    String ImportID,
                    String packingMatID,
                    String packingMat,
                    String shelflife,
                    String BasketName,
                    String IsRemarkExpress,
                    int index
            ){
                return new ModelWashDetail(
                        ID,
                        WashDocNo,
                        ItemStockID,
                        Qty,
                        DepName,
                        DepName2,
                        itemname,
                        itemcode,
                        UsageCode,
                        age,
                        ImportID,
                        packingMatID,
                        packingMat,
                        shelflife,
                        BasketName,
                        IsRemarkExpress,
                        index
                );
            }

            // =========================================================================================
        }

        DisplayWashDetail obj = new DisplayWashDetail();
        obj.execute();
    }


}
