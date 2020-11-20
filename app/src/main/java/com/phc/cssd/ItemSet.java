package com.phc.cssd;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.phc.core.connect.HTTPConnect;
import com.phc.cssd.adapter.ItemDetailEnterQtyAdapter;
import com.phc.cssd.url.Url;
import com.phc.core.data.AsonData;
import com.phc.core.string.Cons;
import com.phc.cssd.adapter.ItemAdapter;
import com.phc.cssd.data.Master;
import com.phc.cssd.model.ModelItemDetail;
import com.phc.cssd.model.ModelItems;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class ItemSet extends Activity {

    //------------------------------------------------
    // Background Worker Process Variable
    private boolean Success = false;
    private ArrayList<String> data = null;
    private int size = 0;
    //------------------------------------------------

    //------------------------------------------------
    // Session Variable
    private String ITEM_ID = null;
    private String ITEM_CODE = null;
    private String ITEM_NAME = null;
    //------------------------------------------------

    //------------------------------------------------
    // Widget Variable
    private ImageView imageBack;
    private EditText txt_search;
    private TextView txt_item_name;
    private Button btn_search;

    private Button btn_add;
    private Button btn_remove;

    private ListView list_item;
    private ListView list_set;
    private ImageView image_remove_all;
    //------------------------------------------------

    //------------------------------------------------
    // Local Variable
    private ItemAdapter itemAdapter;
    private ItemDetailEnterQtyAdapter itemSetAdapter;

    private List<com.phc.cssd.model.ModelItemDetail> ModelItemDetail = null;
    private List<com.phc.cssd.model.ModelItems> ModelItems = null;

    ArrayList<String> item_set;
    //------------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_set);

        // Permission StrictMode
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        bySession();

        byWidget();

        onDisplayItemSet();

    }

    private void bySession(){
        // Argument
        Intent intent = getIntent();
        //UserName = intent.getStringExtra("UserName");
        //EmpCode = intent.getStringExtra("EmpCode");
        ITEM_ID = intent.getStringExtra("ITEM_ID");
        ITEM_CODE = intent.getStringExtra("ITEM_CODE");
        ITEM_NAME = intent.getStringExtra("ITEM_NAME");
    }

    private void byWidget(){
        txt_search = (EditText) findViewById(R.id.txt_search);
        btn_search = (Button) findViewById(R.id.btn_search);

        btn_add = (Button) findViewById(R.id.btn_add);
        btn_remove = (Button) findViewById(R.id.btn_remove);

        txt_item_name = (TextView) findViewById(R.id.txt_item_name);

        txt_item_name.setText("รหัส-ชื่อ รายการ (เซ็ท) : " + ITEM_CODE + " - " + ITEM_NAME );

        // ------------------- Event --------------------

        txt_search.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                onDisplay();

            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

        btn_add.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                onAddSet();
            }
        });

        btn_remove.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                /*
                AlertDialog.Builder quitDialog = new AlertDialog.Builder(ItemSet.this);
                quitDialog.setTitle(Cons.TITLE);
                quitDialog.setMessage(Cons.CONFIRM_DELETE);

                quitDialog.setPositiveButton("ตกลง", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        onDeleteSet();
                    }
                });

                quitDialog.setNegativeButton("ยกเลิก", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                quitDialog.show();
                */

            }
        });

        image_remove_all = (ImageView) findViewById(R.id.image_remove_all);
        image_remove_all.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){

                AlertDialog.Builder quitDialog = new AlertDialog.Builder(ItemSet.this);
                quitDialog.setTitle(Cons.TITLE);
                quitDialog.setMessage(Cons.CONFIRM_DELETE);

                quitDialog.setPositiveButton("ตกลง", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        onDeleteSet();
                    }
                });

                quitDialog.setNegativeButton("ยกเลิก", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                quitDialog.show();

            }
        });


        list_item = (ListView) findViewById(R.id.list_item);
        list_set = (ListView) findViewById(R.id.list_set);

        list_set.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // selected item
                String item_code = ((TextView)((LinearLayout) view).getChildAt(1)).getText().toString();
                String item_qty = ((TextView)((LinearLayout) view).getChildAt(3)).getText().toString();

                //System.out.println("DATA = " + item_code + "-" + item_qty);

                openFormEnterQty(item_code, item_qty);


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

    }

    private void openFormEnterQty(String item_code, String item_qty){
        Intent i = new Intent(ItemSet.this, EnterNumber.class);
        i.putExtra("data", data);
        i.putExtra("item_code", item_code);
        i.putExtra("item_qty", item_qty);
        startActivityForResult(i, Master.enter_qty);
    }

    private void onAddSet(){
        try {
            String LIST_ID = null;
            String DATA = ITEM_ID + "@";
            int List_Count = 0;

            // =========================================================================================

            List<ModelItems> DATA_MODEL = ModelItems;

            Iterator li = DATA_MODEL.iterator();

            while(li.hasNext()) {
                try {
                    ModelItems md = (ModelItems) li.next();

                    LIST_ID = md.getID();

                    if (md.isChecked()) {
                        DATA += LIST_ID + "@";
                        List_Count++;
                    }

                }catch(Exception e){
                    continue;
                }
            }

            //System.out.println("DATA = " + DATA);

            add(DATA);

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void onAddSet(String itemcode){
        try {

            String DATA = ITEM_ID + "@" + itemcode + "@";

            add(DATA);

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private void onDeleteSet(){
        try {
            String LIST_ID = null;
            String DATA = "";
            int List_Count = 0;

            // =========================================================================================

            List<ModelItemDetail> DATA_MODEL = ModelItemDetail;

            Iterator li = DATA_MODEL.iterator();

            while(li.hasNext()) {
                try {
                    ModelItemDetail md = (ModelItemDetail) li.next();

                    LIST_ID = md.getID_set();

                    //if (md.isChecked()) {
                    DATA += LIST_ID + "@";
                    List_Count++;
                    //}

                }catch(Exception e){
                    continue;
                }
            }

            //System.out.println("DATA = " + DATA);

            remove(DATA);

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void onDeleteSet(String id){
        try {
            String DATA = id + "@";

            remove(DATA);

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    // =============================================================================================
    private HTTPConnect httpConnect = new HTTPConnect();
    // =============================================================================================
    // -- Remove WashDetail
    // =============================================================================================

    public void onDisplayItemSet() {

        class DisplayItemSet extends AsyncTask<String, Void, String> {

            //------------------------------------------------
            // Background Worker Process Variable
            private ProgressDialog dialog = new ProgressDialog(ItemSet.this);
            private boolean Success = false;
            private ArrayList<String> data = null;
            private int size = 0;
            //------------------------------------------------

            // variable
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                /*ProgressDialog dialog = new ProgressDialog(ItemSet.this);
                dialog.setMessage("......");
                dialog.setTitle(".....");
                dialog.setCanceledOnTouchOutside(false);
                dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);*/
                this.dialog.setMessage(Cons.WAIT_FOR_PROCESS);
                this.dialog.setCanceledOnTouchOutside(false);
                this.dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);
                this.dialog.show();
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);

                AsonData ason = new AsonData(result);

                Success = ason.isSuccess();
                size = ason.getSize();
                data = ason.getASONData();

                if (Success && data != null) {

                    try {

                        try {
                            ModelItemDetail = getItemDetailModel();
                        } catch (Exception e) {
                            e.printStackTrace();
                            return;
                        }

                        itemSetAdapter = new ItemDetailEnterQtyAdapter(ItemSet.this, ModelItemDetail);
                        list_set.setAdapter(itemSetAdapter);
                        onDisplay();
                    } catch (Exception e) {
                        e.printStackTrace();
                        onDisplay();
                        list_set.setAdapter(null);
                    }
                    if (dialog.isShowing()) {
                        dialog.dismiss();
                    }
                }else{
                    if (dialog.isShowing()) {
                        dialog.dismiss();
                    }
                    //item_set.clear();
                    list_set.setAdapter(null);
                    try {
                        ModelItemDetail = getItemDetailModel();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    onDisplay();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String, String>();
                data.put("p_itemcode", ITEM_ID);
                Log.d("DisplayItemSet data: ", data+"");
                String result = httpConnect.sendPostRequest(Url.URL + "cssd_display_item_set.php", data);

                System.out.println("URL = " + result);

                return result;
            }

            private ModelItemDetail getItemDegtail(int index,
                                                   String ID,
                                                   String itemcode,
                                                   String itemname,
                                                   String alternatename,
                                                   String barcode,
                                                   String setCount,
                                                   String unitName,
                                                   String ID_set,
                                                   String itemDetailID,
                                                   String qty,
                                                   String itemcode_set,
                                                   String itemname_set,
                                                   String alternatename_set,
                                                   String barcode_set,
                                                   String Picture_set,
                                                   String Picture_detail,
                                                   String QtyAll) {
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
                        Picture_set,
                        Picture_detail,
                        QtyAll
                );
            }

            private List<ModelItemDetail> getItemDetailModel() throws Exception{

                List<ModelItemDetail> list = new ArrayList<>();
                item_set = new ArrayList<String>();

                try {
                    int index = 0;
                    if(data!=null){
                        for(int i=0;i<data.size();i+=size){
                            list.add(
                                    getItemDegtail(
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
                                            data.get(i + 16)
                                    )
                            );

                            // item (SET)
                            item_set.add(data.get(i + 8));

                            //System.out.println("data.get = " + data.get(i + 8));

                            index++;
                        }
                    }/*else{

                    }*/


                    // //System.out.println("list = " + list.size());

                }catch(Exception e){
                    e.printStackTrace();
                }

                return list;
            }
        }

        DisplayItemSet obj = new DisplayItemSet();
        obj.execute();
    }


    // =============================================================================================
    // -- Display item ...
    // =============================================================================================

    public void onDisplay() {

        // get Item SET
        String SET = "";

        if(item_set != null) {
            if (item_set.size() > 0) {
                for (int i = 0; i < item_set.size(); i++) {
                    SET += item_set.get(i) + "@";
                }
            }
        }


        //System.out.println(SET);


        final String s = SET;

        class DisplayItem extends AsyncTask<String, Void, String> {

            //------------------------------------------------
            // Background Worker Process Variable
            private boolean Success = false;
            private ArrayList<String> data = null;
            private int size = 0;
            private ProgressDialog dialog = new ProgressDialog(ItemSet.this);
            //------------------------------------------------

            // variable
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                /*ProgressDialog dialog = new ProgressDialog(ItemSet.this);
                dialog.setMessage("......");
                dialog.setTitle(".....");
                dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);*/
                this.dialog.setMessage(Cons.WAIT_FOR_PROCESS);
                this.dialog.setCanceledOnTouchOutside(false);
                this.dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);
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
                    try {
                        //ArrayAdapter<ModelMasterData> adapter = new ItemAdapter(ItemSet.this, getModel());

                        try {
                            ModelItems = getModel();
                        } catch (Exception e) {
                            e.printStackTrace();
                            return;
                        }
                        Log.d("Display: ", "Display Success!!!");
                        itemAdapter = new ItemAdapter(ItemSet.this, ModelItems);
                        list_item.setAdapter(itemAdapter);

                    } catch (Exception e) {
                        e.printStackTrace();
                        list_item.setAdapter(null);
                    }
                    if (dialog.isShowing()) {
                        dialog.dismiss();
                    }
                }else{
                    list_item.setAdapter(null);
                    if (dialog.isShowing()) {
                        dialog.dismiss();
                    }
                }
            }

            @SuppressLint("WrongThread")
            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("p_is_set", "0");
                data.put("p_set", s);
                data.put("p_filter", txt_search.getText().toString());
                Log.d("DisplayItem data: ",data+"" );
                String result = httpConnect.sendPostRequest(Url.URL + "cssd_display_items.php", data);
                Log.d("DisplayItem data: ",result+"" );
                System.out.println("URL = " + result);

                return result;
            }

            private List<ModelItems> getModel() throws Exception{

                List<ModelItems> list = new ArrayList<>();

                try {
                    int index = 0;

                    ////System.out.println("size() = " + size);
                    ////System.out.println("data.size() = " + data.size());

                    for(int i=0;i<data.size();i+=size){

                        list.add(
                                get(
                                        index,
                                        data.get(i),
                                        data.get(i + 1),
                                        data.get(i + 2)
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

            private ModelItems get(int index, String id, String code, String name) {
                return new ModelItems(index, id, code, name);

            }
        }

        DisplayItem obj = new DisplayItem();
        obj.execute();
    }

    public void add(final String p_data) {

        class AddItem extends AsyncTask<String, Void, String> {
            private ProgressDialog dialog = new ProgressDialog(ItemSet.this);
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
                /*ProgressDialog dialog = new ProgressDialog(ItemSet.this);
                dialog.setMessage("......");
                dialog.setTitle(".....");*/

                this.dialog.setMessage(Cons.WAIT_FOR_PROCESS);
                this.dialog.setCanceledOnTouchOutside(false);
                this.dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);
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
                    onDisplayItemSet();
                    //onDisplay();

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

                String result = httpConnect.sendPostRequest(Url.URL + "cssd_add_item_detail.php", data);

                System.out.println("URL = " + result);

                return result;
            }
        }

        AddItem obj = new AddItem();
        obj.execute();
    }

    public void remove(final String p_data) {

        class AddItem extends AsyncTask<String, Void, String> {
            private ProgressDialog dialog = new ProgressDialog(ItemSet.this);
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

                /*this.dialog.setMessage(Cons.WAIT_FOR_PROCESS);
                this.dialog.show();*/
                /*ProgressDialog dialog = new ProgressDialog(ItemSet.this);
                dialog.setMessage("......");
                dialog.setTitle(".....");
                dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);*/
                this.dialog.setMessage(Cons.WAIT_FOR_PROCESS);
                this.dialog.setCanceledOnTouchOutside(false);
                this.dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);
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
                    Log.d("onPostExecute: ","success!!" );
                    onDisplayItemSet();
                    //onDisplay();

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
                Log.d("remove: ", data+"");

                String result = httpConnect.sendPostRequest(Url.URL + "cssd_remove_item_detail.php", data);

                System.out.println("URL = " + result);

                return result;
            }
        }

        AddItem obj = new AddItem();
        obj.execute();
    }
}
