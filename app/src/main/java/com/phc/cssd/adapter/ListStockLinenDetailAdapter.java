package com.phc.cssd.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.phc.cssd.R;
import com.phc.cssd.dialog_linen_detail;
import com.phc.cssd.model.ModelLinenDetail;

import java.util.List;

public class ListStockLinenDetailAdapter extends ArrayAdapter {

    private List<ModelLinenDetail> listData ;
    private Context context;

    public ListStockLinenDetailAdapter(dialog_linen_detail aActivity, List<ModelLinenDetail> listData) {
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
        final View v = inflater.inflate(R.layout.list_stock_linen_detail, parent, false);

        TextView itemname = (TextView) v.findViewById(R.id.itemname);
        TextView usage = (TextView) v.findViewById(R.id.usage);
        TextView pack = (TextView) v.findViewById(R.id.pack);
        TextView exp = (TextView) v.findViewById(R.id.exp);
        TextView day_exp = (TextView) v.findViewById(R.id.day_exp);
        CheckBox chk = (CheckBox) v.findViewById(R.id.chk);

        itemname.setText(listData.get(position).getItemname());
        usage.setText(listData.get(position).getUsageCode());
        pack.setText(listData.get(position).getPackDate());
        exp.setText(listData.get(position).getExpireDate());
        day_exp.setText(listData.get(position).getDate()+" วัน");

        if (listData.get(position).getChk().equals("0")){
            chk.setChecked(false);
        }else {
            chk.setChecked(true);
        }

        return v;
    }
}
