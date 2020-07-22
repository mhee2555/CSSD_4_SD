package com.phc.cssd.adapter;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.phc.cssd.R;
import com.phc.cssd.properties.Response_Item_sterileprogram;

import java.util.ArrayList;

public class ListStockLinenDetailAdapter extends ArrayAdapter {

    private ArrayList<Response_Item_sterileprogram> listData ;
    private Context context;

    public ListStockLinenDetailAdapter(AppCompatActivity aActivity, ArrayList<Response_Item_sterileprogram> listData) {
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
        final View v = inflater.inflate(R.layout.list_sterileprogramitem_item, parent, false);

        TextView tFields1 = (TextView) v.findViewById(R.id.tItemCode);
        TextView tFields2 = (TextView) v.findViewById(R.id.tName);
        TextView tFields3 = (TextView) v.findViewById(R.id.tLife);

        tFields1.setText( listData.get(position).gettItemCode() );
        tFields2.setText( listData.get(position).gettName() );
        tFields3.setText( listData.get(position).gettLife() );

        return v;
    }
}
