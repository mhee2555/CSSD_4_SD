package com.phc.cssd;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.phc.core.connect.HTTPConnect;
import com.phc.core.data.AsonData;
import com.phc.cssd.adapter.CssdPreviewItemSterile_List_ItemSet_Adapter;
import com.phc.cssd.adapter.CssdPreviewItemSterile_List_ItemSterile_Adapter;
import com.phc.cssd.adapter.ItemDetailAdapter;
import com.phc.cssd.adapter.SterileDetailAdapter;
import com.phc.cssd.adapter.SterileDetailBigSizeAdapter;
import com.phc.cssd.adapter.SterileDetailGridViewAdapter;
import com.phc.cssd.model.ModelItemDetail;
import com.phc.cssd.model.ModelItems;
import com.phc.cssd.model.ModelItems;
import com.phc.cssd.model.ModelMasterData;
import com.phc.cssd.model.ModelPreviewItemSterile;
import com.phc.cssd.model.ModelSterileProgram;
import com.phc.cssd.url.Url;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CssdPreviewItemSterile extends AppCompatActivity {

    private ImageView imageBack;
    private ListView list_item_sterile;
    private ListView list_set_item;
    private EditText txt_search;
    private TextView txt_caption_21;
    private TextView txt_caption_22;

    private ImageView img_item;
    private ImageView img_item_all;


    private List<ModelPreviewItemSterile>  MODEL_ITEM_STERILE;

    private HTTPConnect httpConnect = new HTTPConnect();

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

        displayItemSterile();
    }

    private void byIntent(){
        // Argument
        Intent intent = getIntent();
    }

    private void byWidget() {
        list_item_sterile = (ListView)findViewById(R.id.list_item_sterile);
        list_set_item = (ListView)findViewById(R.id.list_set_item);
        img_item = (ImageView)findViewById(R.id.img_item);
        img_item_all = (ImageView)findViewById(R.id.img_item_all);
        txt_search = (EditText) findViewById(R.id.txt_search);

        txt_caption_21 = (TextView) findViewById(R.id.txt_caption_21);
        txt_caption_22 = (TextView)findViewById(R.id.txt_caption_22);

        txt_caption_21.setText("ชื่อเซ็ท : ");
        txt_caption_22.setText("รายการในเซ็ท " + 0 + " รายการ   จำนวนทั้งหมด "+ 0 + " ชิ้น");

        txt_search.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                displayItemSterile();
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

        list_item_sterile.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String code = ((TextView)((RelativeLayout)((LinearLayout) view).getChildAt(0)).getChildAt(0)).getText().toString();
                String name = ((TextView)((RelativeLayout)((LinearLayout) view).getChildAt(0)).getChildAt(1)).getText().toString();
                String set_count = ((TextView)((RelativeLayout)((LinearLayout) view).getChildAt(0)).getChildAt(2)).getText().toString();
                String set_qty = ((TextView)((RelativeLayout)((LinearLayout) view).getChildAt(0)).getChildAt(3)).getText().toString();
                String pic_1 = ((TextView)((RelativeLayout)((LinearLayout) view).getChildAt(0)).getChildAt(4)).getText().toString();
                String pic_2 = ((TextView)((RelativeLayout)((LinearLayout) view).getChildAt(0)).getChildAt(5)).getText().toString();

                // Display
                txt_caption_21.setText("ชื่อเซ็ท : " + name);
                txt_caption_22.setText("รายการในเซ็ท " + set_count + " รายการ   จำนวนทั้งหมด "+ set_qty + " ชิ้น");

                //Image 1
                try {
                    //System.out.println(Url.getImageURL() + pic_1);
                    URL url = new URL(Url.getImageURL() + pic_1);
                    Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                    img_item.setImageBitmap(bmp);
                }catch(Exception e){
                    e.printStackTrace();
                    img_item.setImageResource(R.drawable.ic_preview);
                }

                //Image 2
                try {
                    //System.out.println(Url.getImageURL() + pic_2);
                    URL url = new URL(Url.getImageURL() + pic_2);
                    Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                    img_item_all.setImageBitmap(bmp);
                }catch(Exception e){
                    e.printStackTrace();
                    img_item_all.setImageResource(R.drawable.ic_preview);
                }

                // Display Item Set
                displayItemSet(code);

            }
        });

        list_set_item.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String pic_2 = ((TextView)((LinearLayout) view).getChildAt(4)).getText().toString();

                System.out.println(pic_2);

                //Image 2
                try {
                    //System.out.println(Url.getImageURL() + pic_2);
                    URL url = new URL(Url.getImageURL() + pic_2);
                    Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                    img_item_all.setImageBitmap(bmp);
                }catch(Exception e){
                    //e.printStackTrace();
                    img_item_all.setImageResource(R.drawable.ic_preview);
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
    }

    public  void clearForm(){
        img_item.setImageResource(R.drawable.ic_preview);
        img_item_all.setImageResource(R.drawable.ic_preview);

        list_set_item.setAdapter(null);

        txt_caption_21.setText("ชื่อเซ็ท : ");
        txt_caption_22.setText("รายการในเซ็ท " + 0 + " รายการ   จำนวนทั้งหมด "+ 0 + " ชิ้น");
    }

    // =============================================================================================
    // -- Display Item Sterile
    // =============================================================================================

    public void displayItemSterile() {

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
                data.put("p_filter", txt_search.getText().toString());
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
                                        "0"
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
                        ArrayAdapter<ModelItemDetail> adapter = new CssdPreviewItemSterile_List_ItemSet_Adapter(CssdPreviewItemSterile.this, getItemDetailModel());
                        list_set_item.setAdapter(adapter);
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

                System.out.println("URL = " + result);

                return result;
            }

            private List<ModelItemDetail> getItemDetailModel() throws Exception{

                List<ModelItemDetail> list = new ArrayList<>();

                try {
                    int index = 0;

                    //System.out.println("data.size() = " + data.size());
                    //System.out.println("size = " + size);

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
                                        data.get(i + 15)
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

            private ModelItemDetail getItemSet(int index, String ID, String itemcode, String itemname, String alternatename, String barcode, String setCount, String unitName, String ID_set, String itemDetailID, String qty, String itemcode_set, String itemname_set, String alternatename_set, String barcode_set, String picture_set, String picture_detail) {
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
                        picture_detail
                );
            }

            // =========================================================================================
        }

        DisplayItemSet obj = new DisplayItemSet();
        obj.execute();
    }


}
