package com.phc.cssd.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.phc.cssd.R;
import com.phc.cssd.properties.Response_Aux;

import java.util.ArrayList;

public class ListApproveAdapter extends ArrayAdapter {
    private ArrayList<Response_Aux> listData ;
    private Activity context;

    public ListApproveAdapter(Activity aActivity, ArrayList<Response_Aux> listData) {
        super(aActivity, 0, listData);
        this.context = aActivity;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View v = inflater.inflate(R.layout.o_sterile_detail, parent, false);
        final int nPosition=position;
        final TextView tFields1 = (TextView) v.findViewById(R.id.tFields1);
        final TextView tFields2 = (TextView) v.findViewById(R.id.tFields2);
        final ImageView imageView01 = (ImageView) v.findViewById(R.id.imageView1);
        final ImageView ems = (ImageView) v.findViewById(R.id.ems);
        final RelativeLayout Re1 = (RelativeLayout) v.findViewById(R.id.Re1);


        imageView01.setImageResource(R.drawable.ic_file);

        tFields1.setText( listData.get(position).getFields2() );
        tFields2.setText( listData.get(position).getFields6() );
        Re1.setBackgroundResource(R.drawable.rectangle_red);

        if (listData.get(position).getFields15().equals("0")){
            ems.setVisibility(View.GONE);
        }else {
            ems.setVisibility(View.VISIBLE);
        }

        return v;
    }

}
