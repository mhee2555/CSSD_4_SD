package com.phc.cssd;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.phc.core.connect.HTTPConnect;
import com.phc.cssd.url.Url;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.HashMap;

public class dialog_Load_Img_Remark extends Activity {

    String itemcode,sel,usagecode,itemname,type;
    TextView itemname_set,usagecode_set,sub_itemname_set,unit_set,text_remark;
    ImageView img;
    LinearLayout L1;
    LinearLayout P1;
    String codeData_pic1;
    String codeData_pic2;

    private HTTPConnect httpConnect = new HTTPConnect();
    private String TAG_RESULTS = "result";
    private JSONArray rs = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_load_img_remark);
        byIntent();
        initialize();
        ShowDetail();
    }

    private void byIntent() {
        // Argument
        Intent intent = getIntent();
        itemcode = intent.getStringExtra("itemcode");
        usagecode = intent.getStringExtra("usagecode");
        itemname = intent.getStringExtra("itemname");
        sel = intent.getStringExtra("sel");
        type = intent.getStringExtra("type");
    }

    @SuppressLint("WrongViewCast")
    public void initialize() {
        itemname_set = (TextView) findViewById(R.id.itemname_set);
        usagecode_set = (TextView) findViewById(R.id.usagecode_set);
        sub_itemname_set = (TextView) findViewById(R.id.sub_itemname_set);
        text_remark = (TextView) findViewById(R.id.text_remark);
        unit_set = (TextView) findViewById(R.id.unit_set);
        img = (ImageView) findViewById(R.id.img);
        L1 = (LinearLayout) findViewById(R.id.L1);
        P1 = (LinearLayout) findViewById(R.id.P1);
        P1.setVisibility(View.GONE);
    }

    public void ShowDetail() {
        class ShowDetail extends AsyncTask<String, Void, String> {
            @Override
            protected void onPreExecute() { super.onPreExecute(); }

            @Override
            protected void onPostExecute(String result) {
                try {
                    JSONObject jsonObj = new JSONObject(result);
                    rs = jsonObj.getJSONArray(TAG_RESULTS);
                    for(int i=0;i<rs.length();i++) {
                        JSONObject c = rs.getJSONObject(i);
                        try {
                            if (!type.equals("remark")){
                                if (sel.equals("2")){
                                    itemname_set.setText("ชื่อรายการเซ็ท : " + c.getString("itemnamesel2"));
                                }else {
                                    itemname_set.setText("ชื่อรายการเซ็ท : " + itemname);
                                }
                                usagecode_set.setText("รหัสใช้งาน : " + usagecode);
                                if (sel.equals("1")){
                                    sub_itemname_set.setVisibility(View.GONE);
                                }else {
                                    sub_itemname_set.setText("รายละเอียดเซ็ท : " + itemname);
                                }
                                if (c.getString("ID").equals("null")){
                                    unit_set.setText("Sterile Detail (SD) : ไม่มีข้อมูล");
                                }else {
                                    unit_set.setText("Sterile Detail (SD) : "+ c.getString("ID"));
                                }
                            }else {
                                L1.setVisibility(View.GONE);
                                if (c.getString("Note").equals("null") || c.getString("Note").equals("")){
                                    P1.setVisibility(View.VISIBLE);
                                    text_remark.setText("("+c.getString("NameType")+")");
                                }else {
                                    P1.setVisibility(View.VISIBLE);
                                    text_remark.setText(c.getString("Note")+"("+c.getString("NameType")+")");
                                }

                            }
                            if (!type.equals("remark")){
                                URL imageUrl = new URL(Url.URL + "cssd_image/"+c.getString("Picture"));
                                System.out.println(Url.URL + "cssd_image/"+c.getString("Picture"));
                                if (!c.getString("Picture").equals("")){
                                    img.setBackgroundResource(R.color.colorWhite);
                                    codeData_pic1 = "data:image/jpeg;base64,"+c.getString("Picture");
                                    codeData_pic1 = codeData_pic1.replace("data:image/jpeg;base64,","");
                                    byte[] code_pic1 = Base64.decode(codeData_pic1,Base64.DEFAULT);
                                    Bitmap bitmap_pic1 = BitmapFactory.decodeByteArray(code_pic1,0,code_pic1.length);
                                    img.setImageBitmap(bitmap_pic1);
                                }
                            }else {
                                URL imageUrl = new URL("http://poseintelligence.dyndns.biz:8181/cssd_php_m1_paydep_siriraj/images/"+c.getString("Picture"));
                                System.out.println(Url.URL + "cssd_image/"+c.getString("Picture"));
                                if (!c.getString("Picture").equals("")){
                                    img.setBackgroundResource(R.color.colorWhite);
                                    codeData_pic1 = "data:image/jpeg;base64,"+c.getString("Picture");
                                    codeData_pic1 = codeData_pic1.replace("data:image/jpeg;base64,","");
                                    byte[] code_pic1 = Base64.decode(codeData_pic1,Base64.DEFAULT);
                                    Bitmap bitmap_pic1 = BitmapFactory.decodeByteArray(code_pic1,0,code_pic1.length);
                                    img.setImageBitmap(bitmap_pic1);
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
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
                if(type != null){
                    data.put("type",type);
                }else {
                    data.put("type","");
                }

                if(usagecode != null){
                    data.put("usagecode",usagecode);
                }else {
                    data.put("usagecode","");
                }

                if(itemcode != null){
                    data.put("itemcode",itemcode);
                }else {
                    data.put("itemcode","");
                }

                if(itemname != null){
                    data.put("itemname",itemname);
                }else {
                    data.put("itemname","");
                }

                data.put("sel",sel);
                String result = null;
                try {
                    if (type.equals("remark")){
                        result = httpConnect.sendPostRequest(Url.URL + "cssd_show_detail_item_send.php", data);
                    }else {
                        result = httpConnect.sendPostRequest(Url.URL + "cssd_show_detail_item_send.php", data);
                    }
                    Log.d("FKJDHJKDH",data+"");
                    Log.d("FKJDHJKDH",result+"");
                }catch(Exception e){
                    e.printStackTrace();
                }
                return result;
            }
            // =========================================================================================
        }
        ShowDetail obj = new ShowDetail();
        obj.execute();
    }
}
