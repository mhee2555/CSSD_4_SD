package com.phc.cssd.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.phc.cssd.CssdCreateItemStockDepartment;
import com.phc.cssd.R;
import com.phc.cssd.model.ModelMasterData;

import java.util.List;

public class ItemStockDepartmentAdapter extends ArrayAdapter<ModelMasterData> {

    private final List<ModelMasterData> DATA_MODEL;
    private final Activity context;

    public ItemStockDepartmentAdapter(Activity context, List<ModelMasterData> DATA_MODEL) {
        super(context, R.layout.activity_list_item_stock_department_data, DATA_MODEL);
        this.context = context;
        this.DATA_MODEL = DATA_MODEL;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = null;

        if (convertView == null) {
            LayoutInflater inflator = context.getLayoutInflater();
            view = inflator.inflate(R.layout.activity_list_item_stock_department_data, null);

            final ViewHolder viewHolder = new ViewHolder();

            viewHolder.chk = (CheckBox) view.findViewById(R.id.chk);
            viewHolder.txt_code = (TextView) view.findViewById(R.id.txt_code);
            viewHolder.txt_name = (TextView) view.findViewById(R.id.txt_name);


            viewHolder.chk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        DATA_MODEL.get(viewHolder.index).setCheck( ! DATA_MODEL.get(viewHolder.index).isCheck() );
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
            });


            view.setTag(viewHolder);

        } else {
            view = convertView;
        }

        // =========================================================================================
        final ItemStockDepartmentAdapter.ViewHolder holder = (ItemStockDepartmentAdapter.ViewHolder) view.getTag();
        holder.txt_name.setText(DATA_MODEL.get(position).getName());
        holder.txt_code.setText(DATA_MODEL.get(position).getCode());
        holder.chk.setChecked(DATA_MODEL.get(position).isCheck());
        holder.index = (DATA_MODEL.get(position).getIndex());

        if (DATA_MODEL.get(position).isCheck() == false){
            ((CssdCreateItemStockDepartment)context).Check_All(false);
        }else {
            ((CssdCreateItemStockDepartment)context).Check_All(true);
        }
        // =========================================================================================

        return view;
    }

    static class ViewHolder {
        CheckBox chk;
        int index;
        TextView txt_code;
        TextView txt_name;
    }

}