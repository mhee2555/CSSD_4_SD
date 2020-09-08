package com.phc.cssd.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.phc.core.connect.HTTPConnect;
import com.phc.cssd.PayoutActivity;
import com.phc.cssd.R;
import com.phc.cssd.SearchItem_Payout;
import com.phc.cssd.properties.Response_Aux_itemstock;
import com.phc.cssd.url.getUrl;
import com.phc.cssd.url.xControl;

import org.json.JSONArray;

import java.util.ArrayList;

public class search_PayoutItemAdapter extends ArrayAdapter {
    JSONArray setRs = null;
    String SELECT_URL;
    getUrl iFt = new getUrl();
    HTTPConnect ruc = new HTTPConnect();
    xControl xCtl = new xControl();
    private ArrayList<Response_Aux_itemstock> listData ;
    //private ArrayList<Response_Aux_itemstock> listData2 ;
    private Activity context;
    ListView Lv2;
    String Usage_code;
    boolean IsAdmin = false;
    int devicemode = 0;

    public search_PayoutItemAdapter(Activity aActivity, ArrayList<Response_Aux_itemstock> listData,int devicemode) {
        super(aActivity, 0, listData);
        this.context = aActivity;
        this.listData = listData;
        //this.listData2 = listData2;
        this.Lv2 = Lv2;
        this.Usage_code = Usage_code;
        this.devicemode = devicemode;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View v = inflater.inflate(R.layout.list_search_sendsterile, parent, false);
        TextView tFields1 = (TextView) v.findViewById(R.id.txt_itemcode);
        TextView txt_qty = (TextView) v.findViewById(R.id.txt_qty);
        final EditText EditNum1 = (EditText) v.findViewById(R.id.etxt_qty);
        Button bt_import = (Button) v.findViewById(R.id.bt_import);

        if (listData.get(position).getxFields16().equals("1")) {
            tFields1.setText(listData.get(position).getFields2() + ": " + "[ SET ] " + listData.get(position).getFields9() + " " + "(" + listData.get(position).getxFields15() + " วัน " + ")");
        }else {
            tFields1.setText(listData.get(position).getFields2() + ":" + listData.get(position).getFields9() + " " + "(" + listData.get(position).getxFields15() + " วัน " + ")");
        }
        EditNum1.setText( listData.get(position).getFields6() );


        if(devicemode == PayoutActivity.IsT2){
            EditNum1.setOnFocusChangeListener(new View.OnFocusChangeListener(){
                public void onFocusChange(View v, boolean hasFocus){
                    if (hasFocus) ((EditText)v).selectAll();
                }
            });

            EditNum1.addTextChangedListener(new TextWatcher() {

                public void afterTextChanged(Editable s) {
                    listData.get(position).setFields6( EditNum1.getText()+"" );
                }

                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                public void onTextChanged(CharSequence s, int start, int before, int count) {}
            });

            bt_import.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    ((SearchItem_Payout)context).senditemandqty( listData.get(position).getFields2(),EditNum1.getText()+"",listData.get(position).getxFields10());

                }
            });
        }else{
            EditNum1.setVisibility(View.GONE);
            bt_import.setVisibility(View.GONE);
            LinearLayout l = (LinearLayout) v.findViewById(R.id.f);
            l.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Dialog dialog = new Dialog(context, R.style.DialogCustomTheme);

                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

                    dialog.setContentView(R.layout.dialog_set_item_number);

                    dialog.setCancelable(false);

                    dialog.setTitle("");

                    TextView itemcode = (TextView) dialog.findViewById(R.id.itemcode);
                    TextView itemnum = (TextView) dialog.findViewById(R.id.textView187);
                    final EditText number = (EditText) dialog.findViewById(R.id.number);

                    Button bt_cancel = (Button) dialog.findViewById(R.id.bt_cancel);
                    Button bt_save = (Button) dialog.findViewById(R.id.bt_save);

                    itemcode.setText("รายการ : "+listData.get(position).getFields2());
                    itemnum.setText("จำนวน");
                    number.setText("1");

                    number.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ((EditText)v).selectAll();
                        }
                    });

                    bt_save.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int qty = 0;
                            if(!number.getText().toString().equals("")){
                                if(Integer.parseInt(number.getText().toString())>0){
                                    ((SearchItem_Payout)context).senditemandqty( listData.get(position).getFields2(),number.getText()+"",listData.get(position).getxFields10());
                                }
                            }
                            dialog.dismiss();
                        }
                    });

                    bt_cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });

                    dialog.show();

                }
            });
        }


        return v;
    }

}
