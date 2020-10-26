package com.phc.cssd.adapter;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.phc.core.connect.HTTPConnect;
import com.phc.cssd.R;
import com.phc.cssd.SendSterile_MainActivity;
import com.phc.cssd.properties.pCustomer;
import com.phc.cssd.url.Url;
import com.phc.cssd.url.getUrl;
import com.phc.cssd.url.xControl;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class Adapter_Washtag_SS extends ArrayAdapter {

    private ArrayList<pCustomer> listData;
    private SendSterile_MainActivity aActivity;

    HTTPConnect ruc = new HTTPConnect();

    public Adapter_Washtag_SS(SendSterile_MainActivity aActivity, ArrayList<pCustomer> listData) {
        super(aActivity, 0, listData);
        this.aActivity= aActivity;
        this.listData = listData;
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {

        Log.d("ttest_wt_detail","washtag_detail p = "+position);
        LayoutInflater inflater = (LayoutInflater) aActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View v = inflater.inflate(R.layout.washtag_ss_washdocdetail_adapter, parent, false);

        final pCustomer pCus = listData.get(position);

        final TextView txtitemname = (TextView) v.findViewById(R.id.w_itemname);
        final TextView txtUcode = (TextView) v.findViewById(R.id.w_ucode);
        final TextView w_date = (TextView) v.findViewById(R.id.w_date);
        final TextView itemqty = (TextView) v.findViewById(R.id.itemqty);
        ImageView btdel = (ImageView) v.findViewById(R.id.w_btdel);

        final ImageView resterile_IV = (ImageView) v.findViewById(R.id.w_resterile);
        final TextView ucount = (TextView) v.findViewById(R.id.ucount);
        final TextView txtxremark_detail = (TextView) v.findViewById(R.id.w_remark_detail);
        final TextView textView53 = (TextView) v.findViewById(R.id.textView53);
        final TextView r_name = (TextView) v.findViewById(R.id.w_r_name);
        final TextView bt_note = (TextView) v.findViewById(R.id.w_bt_note);
        final TextView bt_risk = (TextView) v.findViewById(R.id.w_bt_risk);
        final CheckBox chk_box = (CheckBox) v.findViewById(R.id.chk_box);
        final CheckBox del_multi = (CheckBox) v.findViewById(R.id.del_multi);

        resterile_IV.setVisibility(View.INVISIBLE);
        ucount.setVisibility(View.INVISIBLE);
        txtxremark_detail.setVisibility(View.INVISIBLE);
        textView53.setVisibility(View.INVISIBLE);
        r_name.setVisibility(View.INVISIBLE);
        bt_note.setVisibility(View.INVISIBLE);
        bt_risk.setVisibility(View.INVISIBLE);
        chk_box.setVisibility(View.GONE);
        del_multi.setVisibility(View.GONE);

        txtitemname.setText(pCus.getItemname());
        txtUcode.setText("รหัสใช้งาน : " + pCus.getUsageCode());
        itemqty.setText("[ 1 ]");
        w_date.setText(" (" + pCus.getPackdate() + " วัน" + " )");

        btdel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                basket_detail_delete(pCus.getBasketID());
            }
        });

        return v;
    }

    public void basket_detail_delete(String BasketID){
        class washtag_detail extends AsyncTask<String, Void, String> {
            // variable

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                aActivity.SelectBasket();
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("BasketID",params[0]);
                String result = ruc.sendPostRequest(getUrl.xUrl+"washtag/remove_item_in_washtag.php",data);
                Log.d("ttest_dt_detail","delete๘washtag_detail result = "+result);
                return result;
            }
        }
        washtag_detail ru = new washtag_detail();
        ru.execute(BasketID);
        Log.d("ttest_dt_detail","delete๘washtag_detail BasketID = "+BasketID);
    }
}
