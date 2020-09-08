package com.phc.cssd.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.phc.cssd.CssdWash;
import com.phc.cssd.R;
import com.phc.cssd.model.ModelSendSterileDetail;

import java.util.List;

public class ImportSendSterileDetailAdapter extends ArrayAdapter<ModelSendSterileDetail> {

    private final List<ModelSendSterileDetail> DATA_MODEL;
    private final Activity context;
    private final boolean IsActive;

    public ImportSendSterileDetailAdapter(Activity context, List<ModelSendSterileDetail> DATA_MODEL, boolean IsActive) {
        super(context, R.layout.activity_list_import_send_sterile_detail, DATA_MODEL);
        this.context = context;
        this.DATA_MODEL = DATA_MODEL;
        this.IsActive = IsActive;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = null;

        if (convertView == null) {
            LayoutInflater inflator = context.getLayoutInflater();
            view = inflator.inflate(R.layout.activity_list_import_send_sterile_detail, null);

            final ImportSendSterileDetailAdapter.ViewHolder viewHolder = new ImportSendSterileDetailAdapter.ViewHolder();
            viewHolder.relativeLayout = (RelativeLayout) view.findViewById(R.id.relativeLayout);
            viewHolder.txt_item_code = (TextView) view.findViewById(R.id.txt_item_code);
            viewHolder.txt_item_program_id = (TextView) view.findViewById(R.id.txt_item_program_id);
            viewHolder.txt_no = (TextView) view.findViewById(R.id.txt_no);
            viewHolder.txt_item_name = (TextView) view.findViewById(R.id.txt_item_name);
            viewHolder.txt_qty = (TextView) view.findViewById(R.id.txt_qty);
            viewHolder.imv_add = (ImageView) view.findViewById(R.id.imv_add);
            viewHolder.txt_packingmat = (TextView) view.findViewById(R.id.txt_packingmat);
            viewHolder.txt_IsRemarkems = (ImageView) view.findViewById(R.id.txt_IsRemarkems);

            viewHolder.relativeLayout.setOnLongClickListener(new View.OnLongClickListener() {
                public boolean onLongClick(View v) {
                    (( CssdWash )context).openDialogWashManagement(
                            viewHolder.txt_item_code.getText().toString(),
                            viewHolder.txt_item_name.getText().toString() );
                    return false;
                }
            });

            viewHolder.imv_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((CssdWash)context).importSendSterileDetail( viewHolder.id.toString(), viewHolder.program_id.toString(), viewHolder.program_name.toString());
                }
            });

            viewHolder.txt_qty.setOnLongClickListener(new View.OnLongClickListener() {
                public boolean onLongClick(View v) {
                    final Dialog dialog = new Dialog(context);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.dialog_qty);
                    dialog.setCancelable(true);

                    final TextView getQty = (TextView)dialog.findViewById(R.id.getQty);
                    getQty.setText(viewHolder.txt_qty.getText());

                    final EditText editQty = (EditText)dialog.findViewById(R.id.editQty);
                    Button button1 = (Button)dialog.findViewById(R.id.button1);

                    button1.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            String xQty = viewHolder.txt_qty.getText().toString();
                            String gQty = editQty.getText().toString();
                            if (gQty.equals("")){
                                Toast.makeText(context, "กรุณากรอกจำนวนตามที่มี !!", Toast.LENGTH_SHORT).show();
                            }else {
                                if (Integer.parseInt(gQty) <= Integer.parseInt(xQty)) {
                                    if (Integer.parseInt(gQty) == 0){
                                        Toast.makeText(context, "กรุณากรอกจำนวนตามที่มี !!", Toast.LENGTH_SHORT).show();
                                        editQty.setText("");
                                    }else {
                                        if (Integer.parseInt(gQty) > Integer.parseInt(xQty)) {
                                            Toast toast = Toast.makeText(context, (Integer.parseInt(gQty) - Integer.parseInt(xQty)), Toast.LENGTH_SHORT);
                                            toast.setMargin(50, 50);
                                            toast.show();
                                        } else {
                                            (( CssdWash ) context).importWashDetail(
                                                    viewHolder.txt_item_code.getText().toString(),
                                                    viewHolder.txt_item_program_id.getText().toString(),
                                                    viewHolder.item_program,
                                                    viewHolder.PackingMatID,
                                                    gQty
                                            );
                                        }
                                        dialog.cancel();
                                    }
                                }else if ((Integer.parseInt(gQty) > Integer.parseInt(xQty))){
                                    Toast.makeText(context, "กรุณากรอกจำนวนตามที่มี !!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    });
                    dialog.show();
                    return false;
                }
            });

            view.setTag(viewHolder);

        } else {
            view = convertView;
        }

        // =========================================================================================
        final ImportSendSterileDetailAdapter.ViewHolder holder = (ImportSendSterileDetailAdapter.ViewHolder) view.getTag();
        holder.txt_item_code.setText( DATA_MODEL.get(position).getI_id());
        holder.txt_packingmat.setText( "( " + DATA_MODEL.get(position).getPackingMat() + " : " + DATA_MODEL.get(position).getShelflife() + " วัน )");
        holder.txt_no.setText(  DATA_MODEL.get(position).getIsWashDept() + (DATA_MODEL.get(position).getIndex()+1) + ".");
        holder.txt_item_program_id.setText( DATA_MODEL.get(position).getI_program_id());
        holder.txt_item_name.setText(DATA_MODEL.get(position).getI_name());
        holder.txt_qty.setText(DATA_MODEL.get(position).getI_qty());
        holder.index = (DATA_MODEL.get(position).getIndex());
        holder.program_id = DATA_MODEL.get(position).getI_program_id();
        holder.program_name = DATA_MODEL.get(position).getI_program();
        holder.id = DATA_MODEL.get(position).getI_id();
        holder.PackingMatID = DATA_MODEL.get(position).getPackingMatID();
        holder.item_program = DATA_MODEL.get(position).getI_program();

        if (!DATA_MODEL.get(position).getIsRemarkExpress().equals("0")){
            holder.txt_IsRemarkems.setVisibility(View.VISIBLE);
        }else {
            holder.txt_IsRemarkems.setVisibility(View.INVISIBLE);
        }
        // =========================================================================================

        return view;
    }

    static class ViewHolder {
        int index;
        TextView txt_item_code;
        TextView txt_no;
        TextView txt_item_program_id;
        TextView txt_packingmat;
        TextView txt_item_name;
        TextView txt_qty;
        ImageView txt_IsRemarkems;

        ImageView imv_add;
        RelativeLayout relativeLayout;

        String id;
        String program_id;
        String program_name;

        String item_program;
        String PackingMatID;

    }

}