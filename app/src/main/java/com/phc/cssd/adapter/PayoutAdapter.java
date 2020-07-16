package com.phc.cssd.adapter;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.phc.cssd.R;
import com.phc.cssd.properties.Response_Aux;

import java.util.ArrayList;

public class PayoutAdapter extends ArrayAdapter {
    private ArrayList<Response_Aux> listData ;
    private Context context;

    public PayoutAdapter(AppCompatActivity aActivity, ArrayList<Response_Aux> listData) {
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
        final View v = inflater.inflate(R.layout.list_payout, parent, false);

        TextView tFields1 = (TextView) v.findViewById(R.id.tFields1);
        TextView tFields2 = (TextView) v.findViewById(R.id.tFields2);
        TextView tFields3 = (TextView) v.findViewById(R.id.tFields3);
        TextView tFields4 = (TextView) v.findViewById(R.id.tFields4);
        TextView tFields5 = (TextView) v.findViewById(R.id.tFields5);
        TextView tFields6 = (TextView) v.findViewById(R.id.tFields6);
        ImageView imageView1 = (ImageView) v.findViewById(R.id.imageView1);

        tFields1.setText( listData.get(position).getFields1() );
        tFields2.setText( listData.get(position).getFields2() );
        tFields3.setText( listData.get(position).getFields3() );
        tFields4.setText( listData.get(position).getFields4() );
        tFields5.setText( listData.get(position).getFields5() );
        tFields6.setText( listData.get(position).getFields6() );


        if( listData.get(position).getFields7().equals("2") ){
            imageView1.setImageResource(R.drawable.ic_radiobox_fill);
        }else if( listData.get(position).getFields7().equals("1") ){
            imageView1.setImageResource(R.drawable.uncheck02_1_32);
        }else{
            imageView1.setImageResource(R.drawable.ic_radiobox_unfill);
        }
        return v;
    }

}
