package com.phc.cssd.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.phc.cssd.R;
import com.phc.cssd.properties.Response_Aux_itemstock;

import java.util.ArrayList;

public class AuxAdapter_itemstock_search_Only extends ArrayAdapter {

    private Context Mainac;
    private ArrayList<Response_Aux_itemstock> listData ;
    private Context context;
    private CheckBox chx_change;

    public AuxAdapter_itemstock_search_Only(Activity aActivity, ArrayList<Response_Aux_itemstock> listData) {
        super(aActivity, 0, listData);
        this.context = aActivity;
        this.listData = listData;
        this.Mainac = context;
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
        final View v = inflater.inflate(R.layout.list_data_search_only, parent, false);
        //chx_change = (CheckBox) v.findViewById(R.id.checkBox);
        TextView tFields1 = (TextView) v.findViewById(R.id.tFields1);
        //TextView tFields2 = (TextView) v.findViewById(R.id.tFields2);
        TextView tFields3 = (TextView) v.findViewById(R.id.tFields3);
        TextView tFields4 = (TextView) v.findViewById(R.id.tFields4);
        TextView tFields5 = (TextView) v.findViewById(R.id.tFields5);
        TextView tFields7 = (TextView) v.findViewById(R.id.tFields7);
        TextView tFields7_1 = (TextView) v.findViewById(R.id.tFields7_1);
        TextView tFields8 = (TextView) v.findViewById(R.id.tFields8);

        tFields1.setText( listData.get(position).getFields8() );//getFields8
        //tFields2.setText( listData.get(position).getFields2() );
        tFields3.setText( listData.get(position).getFields1() );//getFields1
        tFields4.setText( listData.get(position).getFields3() );
        tFields5.setText( listData.get(position).getFields4() );
        tFields7.setText( listData.get(position).getFields5() );
        tFields7_1.setText( listData.get(position).getxFields17() );
        tFields8.setText( listData.get(position).getFields7() );

//        chx_change.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                boolean newState = !listData.get(position).isIs_Check();
//                listData.get(position).setIs_Check(newState);
//                if(context instanceof Itemstock_manage) {
//                    if (getCountselected() > 0) {
//                        ((Itemstock_manage) Mainac).toggleButtonAll(true);
//                    } else if (getCountselected() == 0) {
//                        ((Itemstock_manage) Mainac).toggleButtonAll(false);
//                    }
//                }
//            }
//        });
//        chx_change.setChecked(listData.get(position).isIs_Check());

        return v;
    }

    public int getCountselected(){
        int count = 0;
        for (int i=0;i<listData.size();i++){
            if(listData.get(i).isIs_Check()) {
                count++;
            }
        }
        return count;
    }



}
