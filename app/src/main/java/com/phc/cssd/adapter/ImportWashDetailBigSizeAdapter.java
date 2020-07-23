package com.phc.cssd.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.phc.cssd.CssdSterile;
import com.phc.cssd.R;
import com.phc.cssd.model.ModelImportWashDetail;

import java.util.List;

public class ImportWashDetailBigSizeAdapter extends ArrayAdapter<ModelImportWashDetail> {

    private final List<ModelImportWashDetail> DATA_MODEL;
    private final Activity context;

    public ImportWashDetailBigSizeAdapter(Activity context, List<ModelImportWashDetail> DATA_MODEL) {
        super(context, R.layout.activity_list_big_size_import_wash_detail, DATA_MODEL);
        this.context = context;
        this.DATA_MODEL = DATA_MODEL;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = null;

        if (convertView == null) {
            LayoutInflater inflator = context.getLayoutInflater();
            view = inflator.inflate(R.layout.activity_list_big_size_import_wash_detail, null);

            final ViewHolder viewHolder = new ViewHolder();
            viewHolder.relativeLayout = (RelativeLayout) view.findViewById(R.id.relativeLayout);
            viewHolder.txt_item_code = (TextView) view.findViewById(R.id.txt_item_code);
            viewHolder.txt_item_program_id = (TextView) view.findViewById(R.id.txt_item_program_id);
            viewHolder.txt_no = (TextView) view.findViewById(R.id.txt_no);
            //viewHolder.txt_item_program = (TextView) view.findViewById(R.id.txt_item_program);
            viewHolder.txt_item_name = (TextView) view.findViewById(R.id.txt_item_name);
            viewHolder.txt_qty = (TextView) view.findViewById(R.id.txt_qty);
            viewHolder.txt_packingmat = (TextView) view.findViewById(R.id.txt_packingmat);
            //viewHolder.chk = (CheckBox) view.findViewById(R.id.chk);
            viewHolder.imv_add = (ImageView) view.findViewById(R.id.imv_add);


            viewHolder.imv_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((CssdSterile)context).importWashDetail( viewHolder.txt_item_code.getText().toString(), viewHolder.txt_item_program_id.getText().toString() , viewHolder.item_program, viewHolder.PackingMatID,"0",
                            viewHolder.basket);
                }
            });

            viewHolder.relativeLayout.setOnLongClickListener(new View.OnLongClickListener() {
                public boolean onLongClick(View v) {
                    ((CssdSterile)context).openDialogWashManagement( viewHolder.txt_item_code.getText().toString(), viewHolder.txt_item_name.getText().toString());
                    return false;
                }
            });

            view.setTag(viewHolder);

        } else {
            view = convertView;
        }

        // =========================================================================================
        final ImportWashDetailBigSizeAdapter.ViewHolder holder = (ImportWashDetailBigSizeAdapter.ViewHolder) view.getTag();
        holder.txt_item_code.setText( DATA_MODEL.get(position).getI_id());
        holder.txt_no.setText( DATA_MODEL.get(position).getI_no() + ".");
        holder.txt_item_program_id.setText( DATA_MODEL.get(position).getI_program_id());
        holder.item_program = DATA_MODEL.get(position).getI_program();
        holder.txt_item_name.setText(DATA_MODEL.get(position).getI_name());
        holder.txt_qty.setText(DATA_MODEL.get(position).getI_qty());
        holder.txt_packingmat.setText( "( " + DATA_MODEL.get(position).getPackingMat() + " : " + DATA_MODEL.get(position).getShelflife() + " วัน )");
        //holder.chk.setImageResource(DATA_MODEL.get(position).getCheck() );
        //holder.chk.setChecked(DATA_MODEL.get(position).isCheck());
        holder.index = (DATA_MODEL.get(position).getIndex());
        holder.PackingMatID = DATA_MODEL.get(position).getPackingMatID();
        // =========================================================================================

        return view;
    }

    static class ViewHolder {
        public String basket;
        int index;
        TextView txt_item_code;
        TextView txt_no;
        TextView txt_item_program_id;
        //TextView txt_item_program;
        TextView txt_item_name;
        TextView txt_qty;
        //CheckBox chk;
        ImageView imv_add;
        RelativeLayout relativeLayout;
        String item_program;
        TextView txt_packingmat;
        String PackingMatID;
    }

}