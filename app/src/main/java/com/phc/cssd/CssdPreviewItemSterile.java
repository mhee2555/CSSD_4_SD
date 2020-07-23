package com.phc.cssd;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.phc.core.connect.HTTPConnect;
import com.phc.core.data.AsonData;
import com.phc.cssd.adapter.CssdPreviewItemSterile_List_ItemSet_Adapter;
import com.phc.cssd.adapter.CssdPreviewItemSterile_List_ItemSterile_Adapter;
import com.phc.cssd.model.ModelItemDetail;
import com.phc.cssd.model.ModelPreviewItemSterile;
import com.phc.cssd.url.Url;
import com.phc.cssd.url.getUrl;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CssdPreviewItemSterile extends AppCompatActivity {

    private ImageView imageBack;
    private Switch Sw_Scan;
    private Button bt_report_print,bt_clear;
    private ListView list_item_sterile;
    private ListView list_set_item;
    private EditText txt_search;
    private TextView txt_caption_21;
    private TextView txt_caption_22;
    private ImageView img_item;
    private ImageView img_item_all;
    private List<ModelPreviewItemSterile>  MODEL_ITEM_STERILE;
    private HTTPConnect httpConnect = new HTTPConnect();
    private List<ModelItemDetail>  ItemDetail;

    private String TAG_RESULTS = "result";
    private JSONArray rs = null;
    private int CountScan = 0;
    private Object view;
    private String chk = "0";
    private boolean end = false;
    final Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cssd_preview_item_sterile);

        // Permission StrictMode
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        getSupportActionBar().hide();

        byWidget();

        byIntent();

        displayItemSterile("");
    }

    private void byIntent(){
        // Argument
        Intent intent = getIntent();
    }

    private void GetReport(String Itemcode) {
        String url = "";
        url = getUrl.xUrl+"report/Report_checklist_sterile.php?Itemcode="+Itemcode;
        Log.d("FKHKDHD",url);
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
        for (int a = 0 ; a < ItemDetail.size() ; a ++){
            ItemDetail.get(a).setIsChk(1);
            ArrayAdapter<ModelItemDetail> adapter = new CssdPreviewItemSterile_List_ItemSet_Adapter(CssdPreviewItemSterile.this, ItemDetail);
            list_set_item.setAdapter(adapter);
        }
    }

    private void byWidget() {
        Sw_Scan = ( Switch ) findViewById(R.id.Sw_Scan);
        Sw_Scan.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (Sw_Scan.isChecked()) {
                    Toast.makeText(getApplicationContext(),
                            "MkCode", Toast.LENGTH_SHORT).show();
                    ScanQr();
                }else {
                    Toast.makeText(getApplicationContext(),
                            "QrCode", Toast.LENGTH_SHORT).show();
                    ScanMK();
                }
            }
        });

        bt_clear= ( Button ) findViewById(R.id.bt_clear);
        bt_clear.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                for (int a = 0 ; a < ItemDetail.size() ; a ++){
                    ItemDetail.get(a).setIsChk(1);
                    ArrayAdapter<ModelItemDetail> adapter = new CssdPreviewItemSterile_List_ItemSet_Adapter(CssdPreviewItemSterile.this, ItemDetail);
                    list_set_item.setAdapter(adapter);
                }
            }
        });
        bt_report_print= ( Button ) findViewById(R.id.bt_report_print);
        bt_report_print.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
//                String Itemcode = MODEL_ITEM_STERILE.get(0).getItemcode();
//                GetReport(Itemcode);
            }
        });
        list_item_sterile = (ListView)findViewById(R.id.list_item_sterile);
        list_set_item = (ListView)findViewById(R.id.list_set_item);
        img_item = (ImageView)findViewById(R.id.img_item);
        img_item_all = (ImageView)findViewById(R.id.img_item_all);
        txt_search = (EditText) findViewById(R.id.txt_search);

        txt_caption_21 = (TextView) findViewById(R.id.txt_caption_21);
        txt_caption_22 = (TextView)findViewById(R.id.txt_caption_22);

        txt_caption_21.setText("ชื่อเซ็ท : ");
        txt_caption_22.setText("รายการในเซ็ท " + 0 + " รายการ   จำนวนทั้งหมด "+ 0 + " ชิ้น");

        txt_search.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                try {
                    txt_search.requestFocus();
                    if (event.getAction() == KeyEvent.ACTION_DOWN) {
                        switch (keyCode) {
                            case KeyEvent.KEYCODE_DPAD_CENTER:
                            case KeyEvent.KEYCODE_ENTER:
                                if (CountScan == 0){
                                    displayItemSterile(txt_search.getText().toString());
                                }else {
                                    CheckItemIsSet(txt_search.getText().toString());
                                    for (int i = 0 ; i < ItemDetail.size() ; i ++){
                                        ItemDetail.get(i).getItemcode();
                                        String Itemcode;
                                        Itemcode = txt_search.getText().toString().toUpperCase().substring(0,5);
                                        if (Itemcode.equals(ItemDetail.get(i).getItemcode())){
                                            if (ItemDetail.get(i).getIsChk() == 2){
                                                chk = "2";
                                            }else {
                                                ItemDetail.get(i).setIsChk(2);
                                                ArrayAdapter<ModelItemDetail> adapter = new CssdPreviewItemSterile_List_ItemSet_Adapter(CssdPreviewItemSterile.this, ItemDetail);
                                                list_set_item.setAdapter(adapter);
                                                try {
                                                    URL url = new URL(Url.getImageURL() + Itemcode+"_pic1.PNG");
                                                    Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                                                    img_item_all.setImageBitmap(bmp);
                                                }catch(Exception e){
                                                    e.printStackTrace();
                                                    img_item_all.setImageResource(R.drawable.ic_preview);
                                                }
                                                chk = "1";
                                            }
                                        }
                                    }
                                    if (chk.equals("1")){
                                        Toast.makeText(CssdPreviewItemSterile.this, "นำเข้าสำเร็จ", Toast.LENGTH_SHORT).show();
                                        chk.equals("0");
                                        txt_search.setText("");
                                        txt_search.requestFocus();
                                    }else if (chk.equals("0")){
                                        Toast.makeText(CssdPreviewItemSterile.this, "นำเข้าไม่สำเร็จ", Toast.LENGTH_SHORT).show();
                                        txt_search.setText("");
                                        txt_search.requestFocus();
                                    }else if (chk.equals("2")){
                                        Toast.makeText(CssdPreviewItemSterile.this, "รายการซ้ำ", Toast.LENGTH_SHORT).show();
                                        txt_search.setText("");
                                        txt_search.requestFocus();
                                    }
                                    CountScan ++;
                                }
                                return true;
                            default:
                                txt_search.requestFocus();
                                break;
                        }
                    }else {
                        txt_search.requestFocus();
                        return true;
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
                return false;
            }
        });

//        list_item_sterile.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//                String code = ((TextView)((RelativeLayout)((LinearLayout) view).getChildAt(0)).getChildAt(0)).getText().toString();
//                String name = ((TextView)((RelativeLayout)((LinearLayout) view).getChildAt(0)).getChildAt(1)).getText().toString();
//                String set_count = ((TextView)((RelativeLayout)((LinearLayout) view).getChildAt(0)).getChildAt(2)).getText().toString();
//                String set_qty = ((TextView)((RelativeLayout)((LinearLayout) view).getChildAt(0)).getChildAt(3)).getText().toString();
//                String pic_1 = ((TextView)((RelativeLayout)((LinearLayout) view).getChildAt(0)).getChildAt(4)).getText().toString();
//                String pic_2 = ((TextView)((RelativeLayout)((LinearLayout) view).getChildAt(0)).getChildAt(5)).getText().toString();
//
//                // Display
//                txt_caption_21.setText("ชื่อเซ็ท : " + name);
//                txt_caption_22.setText("รายการในเซ็ท " + set_count + " รายการ   จำนวนทั้งหมด "+ set_qty + " ชิ้น");
//
//                //Image 1
//                try {
//                    //System.out.println(Url.getImageURL() + pic_1);
//                    URL url = new URL(Url.getImageURL() + pic_1);
//                    Log.d("FIFPFIF",url+"");
//                    Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
//                    img_item.setImageBitmap(bmp);
//                }catch(Exception e){
//                    e.printStackTrace();
//                    img_item.setImageResource(R.drawable.ic_preview);
//                }
//
//                //Image 2
//                try {
//                    //System.out.println(Url.getImageURL() + pic_2);
//                    URL url = new URL(Url.getImageURL() + pic_2);
//                    Log.d("FIFPFIF",url+"");
//                    Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
//                    img_item_all.setImageBitmap(bmp);
//                }catch(Exception e){
//                    e.printStackTrace();
//                    img_item_all.setImageResource(R.drawable.ic_preview);
//                }
//
//                // Display Item Set
//                displayItemSet(code);
//
//            }
//        });

//        list_set_item.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                String pic_2 = ((TextView)((LinearLayout) view).getChildAt(4)).getText().toString();
//                System.out.println(pic_2);
//                //Image 2
//                try {
//                    //System.out.println(Url.getImageURL() + pic_2);
//                    URL url = new URL(Url.getImageURL() + pic_2);
//                    Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
//                    img_item_all.setImageBitmap(bmp);
//                }catch(Exception e){
//                    //e.printStackTrace();
//                    img_item_all.setImageResource(R.drawable.ic_preview);
//                }
//            }
//        });

        imageBack = (ImageView) findViewById(R.id.imageBack);
        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        imageBack.bringToFront();
    }

    private void ScanUsageCode(final String Usagecode){

    }

    private void ScanQr(){
        txt_search.setEnabled(false);
    }

    private void ScanMK(){
        txt_search.setEnabled(true);
        startServerSocket();
    }

    private void startServerSocket() {
        Thread thread = new Thread(new Runnable() {
            private String stringData = null;
            @Override
            public void run() {
                try {
                    ServerSocket ss = new ServerSocket(9002);
                    while (!end) {
                        //Server is waiting for client here, if needed
                        Socket s = ss.accept();
                        BufferedReader input = new BufferedReader(new InputStreamReader(s.getInputStream()));
                        PrintWriter output = new PrintWriter(s.getOutputStream());
                        stringData = input.readLine();
                        output.println("FROM SERVER - " + stringData.toUpperCase());
                        output.flush();
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        updateUI(stringData);
                        if (stringData.equalsIgnoreCase("STOP")) {
                            end = true;
                            output.close();
                            s.close();
                            break;
                        }
                        output.close();
                        s.close();
                    }
                    ss.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        });
        thread.start();
    }

    private void updateUI(final String stringData) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                txt_search.setText( stringData );
            }
        });
    }

    public  void clearForm(){
        img_item.setImageResource(R.drawable.ic_preview);
        img_item_all.setImageResource(R.drawable.ic_preview);

        list_set_item.setAdapter(null);

//        txt_caption_21.setText("ชื่อเซ็ท : ");
//        txt_caption_22.setText("รายการในเซ็ท " + 0 + " รายการ   จำนวนทั้งหมด "+ 0 + " ชิ้น");
    }

    // =============================================================================================
    // -- Display Item Sterile
    // =============================================================================================

    public void displayItemSterile(final String itemcode) {

        class DisplayItemSterile extends AsyncTask<String, Void, String> {

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
                        MODEL_ITEM_STERILE = getModelItems();
                        try {
                            ArrayAdapter<ModelPreviewItemSterile> adapter;
                            adapter = new CssdPreviewItemSterile_List_ItemSterile_Adapter(CssdPreviewItemSterile.this, MODEL_ITEM_STERILE);
                            list_item_sterile.setAdapter(adapter);
                            if (CountScan == 0){
                                if (MODEL_ITEM_STERILE.get(0).getIsSet().equals("1")){
                                    CountScan = 1;
                                    displayItemSet(itemcode);
                                    txt_search.setText("");
                                    txt_search.requestFocus();
                                    txt_caption_21.setText("ชื่อเซ็ท : " + MODEL_ITEM_STERILE.get(0).getItemname());
                                    txt_caption_22.setText("รายการในเซ็ท " + MODEL_ITEM_STERILE.get(0).getSet_count() + " รายการ   จำนวนทั้งหมด "+ MODEL_ITEM_STERILE.get(0).getSet_qty() + " ชิ้น");
                                }
                            }
                            clearForm();

                        } catch (Exception e) {
                            list_item_sterile.setAdapter(null);
                            e.printStackTrace();
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        return;
                    }
                }else{
                    list_item_sterile.setAdapter(null);
                }
            }

            @SuppressLint("WrongThread")
            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("p_filter", itemcode);
                String result = httpConnect.sendPostRequest(Url.URL + "cssd_display_item_sterile.php", data);
                Log.d("LJHDDL",data+"");
                Log.d("LJHDDL",result);
                System.out.println("URL = " + result);

                return result;
            }

            private List<ModelPreviewItemSterile> getModelItems() {

                List<ModelPreviewItemSterile> list = new ArrayList<>();

                try {
                    int index = 0;

                    for(int i=0;i<data.size();i+=size){

                        list.add(
                                getItems(
                                        data.get(i),
                                        data.get(i + 1),
                                        data.get(i + 2),
                                        data.get(i + 3),
                                        data.get(i + 4),
                                        data.get(i + 5),
                                        data.get(i + 6),
                                        data.get(i + 7)
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

            private ModelPreviewItemSterile getItems( String itemid, String itemcode, String itemname, String picture_1, String picture_2, String set_count, String set_qty, String IsSet) {
                return new ModelPreviewItemSterile(
                        itemid,
                        itemcode,
                        itemname,
                        picture_1,
                        picture_2,
                        set_count,
                        set_qty,
                        IsSet
                );
            }

            // =========================================================================================
        }

        DisplayItemSterile obj = new DisplayItemSterile();
        obj.execute();
    }

    // =============================================================================================
    // -- Display Item Set
    // =============================================================================================

    public void displayItemSet(final String p_itemcode) {
        class DisplayItemSet extends AsyncTask<String, Void, String> {
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
                        ItemDetail=getItemDetailModel();
                        ArrayAdapter<ModelItemDetail> adapter = new CssdPreviewItemSterile_List_ItemSet_Adapter(CssdPreviewItemSterile.this, ItemDetail);
                        list_set_item.setAdapter(adapter);
                        try {
                            URL url = new URL(Url.getImageURL() + p_itemcode.substring(0,5).toUpperCase()+"_pic1.PNG");
                            Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                            img_item.setImageBitmap(bmp);
                        }catch(Exception e){
                            e.printStackTrace();
                            img_item.setImageResource(R.drawable.ic_preview);
                        }
                    } catch (Exception e) {
                        list_set_item.setAdapter(null);
                        e.printStackTrace();
                    }
                }else{
                    list_set_item.setAdapter(null);
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();

                data.put("p_itemcode", p_itemcode);

                String result = httpConnect.sendPostRequest(Url.URL + "cssd_display_item_set.php", data);

                Log.d("VM:VM:",data+"");
                Log.d("VM:VM:",result);

                return result;
            }

            private List<ModelItemDetail> getItemDetailModel() throws Exception{

                List<ModelItemDetail> list = new ArrayList<>();

                try {
                    int index = 0;
                    for(int i=0;i<data.size();i+=size){
                        list.add(
                                getItemSet(
                                        index,
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
                                        1
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

            private ModelItemDetail getItemSet(int index, String ID, String itemcode, String itemname, String alternatename, String barcode, String setCount, String unitName, String ID_set, String itemDetailID, String qty, String itemcode_set, String itemname_set, String alternatename_set, String barcode_set, String picture_set, String picture_detail,int isChk) {
                return new ModelItemDetail(
                        index,
                        ID,
                        itemcode,
                        itemname,
                        alternatename,
                        barcode,
                        setCount,
                        unitName,
                        ID_set,
                        itemDetailID,
                        qty,
                        itemcode_set,
                        itemname_set,
                        alternatename_set,
                        barcode_set,
                        picture_set,
                        picture_detail,
                        isChk
                );
            }
            // =========================================================================================
        }
        DisplayItemSet obj = new DisplayItemSet();
        obj.execute();
    }

    public void CheckItemIsSet(final String Usagecode) {
        class CheckItemIsSet extends AsyncTask<String, Void, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);

                try {
                    JSONObject jsonObj = new JSONObject(result);
                    rs = jsonObj.getJSONArray(TAG_RESULTS);
                    for(int i=0;i<rs.length();i++) {
                        JSONObject c = rs.getJSONObject(i);
                        if (c.getString("finish").equals("1")){
                            AlertDialog.Builder builder = new AlertDialog.Builder(CssdPreviewItemSterile.this);
                            builder.setCancelable(true);
                            builder.setTitle("ยืนยัน");
                            builder.setMessage("ต้องการเปลี่ยนรายการจัดเซ็ตใช่หรือไม่ ?");
                            builder.setPositiveButton("ยืนยัน",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            CountScan = 0;
                                            displayItemSterile(txt_search.getText().toString());
                                        }
                                    });
                            builder.setNegativeButton("ไม่ยืนยัน", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                }
                            });
                            AlertDialog dialog = builder.create();
                            dialog.show();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @SuppressLint("WrongThread")
            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("Usagecode",Usagecode);

                String result = null;
                try {
                    result = httpConnect.sendPostRequest(Url.URL + "cssd_check_item_isset.php", data);
                    Log.d("FKJDHJKDH",data+"");
                    Log.d("FKJDHJKDH",result+"");
                }catch(Exception e){
                    e.printStackTrace();
                }
                return result;
            }
            // =========================================================================================
        }
        CheckItemIsSet obj = new CheckItemIsSet();
        obj.execute();
    }

//    public void ScanUsagecode(final String usagecode) {
//        class ScanUsagecode extends AsyncTask<String, Void, String> {
//            @Override
//            protected void onPreExecute() {
//                super.onPreExecute();
//            }
//
//            @Override
//            protected void onPostExecute(String result) {
//                super.onPostExecute(result);
//
//                try {
//                    JSONObject jsonObj = new JSONObject(result);
//                    rs = jsonObj.getJSONArray(TAG_RESULTS);
//                    String Itemcode = "";
//                    for(int i=0;i<rs.length();i++) {
//                        JSONObject c = rs.getJSONObject(i);
//                        Itemcode = c.getString("ItemCode");
//                    }
//                    for (int i = 0 ; i < ItemDetail.size() ; i ++){
//                        ItemDetail.get(i).getItemcode();
//                        if (Itemcode.equals(ItemDetail.get(i).getItemcode())){
//                            if (ItemDetail.get(i).getIsChk() == 2){
//                                chk = "2";
//                            }else {
//                                ItemDetail.get(i).setIsChk(2);
//                                ArrayAdapter<ModelItemDetail> adapter = new CssdPreviewItemSterile_List_ItemSet_Adapter(CssdPreviewItemSterile.this, ItemDetail);
//                                list_set_item.setAdapter(adapter);
//                                try {
//                                    URL url = new URL(Url.getImageURL() + Itemcode+"_pic1.PNG");
//                                    Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
//                                    img_item_all.setImageBitmap(bmp);
//                                }catch(Exception e){
//                                    e.printStackTrace();
//                                    img_item_all.setImageResource(R.drawable.ic_preview);
//                                }
//                                chk = "1";
//                            }
//                        }
//                    }
//                    if (chk.equals("1")){
//                        Toast.makeText(CssdPreviewItemSterile.this, "นำเข้าสำเร็จ", Toast.LENGTH_SHORT).show();
//                        chk.equals("0");
//                        txt_search.setText("");
//                        txt_search.requestFocus();
//                    }else if (chk.equals("0")){
//                        Toast.makeText(CssdPreviewItemSterile.this, "นำเข้าไม่สำเร็จ", Toast.LENGTH_SHORT).show();
//                        txt_search.setText("");
//                        txt_search.requestFocus();
//                    }else if (chk.equals("2")){
//                        Toast.makeText(CssdPreviewItemSterile.this, "รายการซ้ำ", Toast.LENGTH_SHORT).show();
//                        txt_search.setText("");
//                        txt_search.requestFocus();
//                    }
//                    CountScan ++;
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @SuppressLint("WrongThread")
//            @Override
//            protected String doInBackground(String... params) {
//                HashMap<String, String> data = new HashMap<String,String>();
//                data.put("usagecode",usagecode);
//                String result = null;
//                try {
//                    result = httpConnect.sendPostRequest(Url.URL + "cssd_scan_usagecode_checklist.php", data);
//                    Log.d("FKJDHJKDH",data+"");
//                    Log.d("FKJDHJKDH",result+"");
//                }catch(Exception e){
//                    e.printStackTrace();
//                }
//                return result;
//            }
//            // =========================================================================================
//        }
//        ScanUsagecode obj = new ScanUsagecode();
//        obj.execute();
//    }

}
