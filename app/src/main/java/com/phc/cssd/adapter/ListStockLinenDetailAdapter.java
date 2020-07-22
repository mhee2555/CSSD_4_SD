package com.phc.cssd.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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

        TextView tFields1 = (TextView) v.findViewById(R.id.tItemCode);
        TextView tFields2 = (TextView) v.findViewById(R.id.tName);
        TextView tFields3 = (TextView) v.findViewById(R.id.tLife);

        return v;
    }
}
