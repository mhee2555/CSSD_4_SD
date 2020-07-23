package com.phc.cssd.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.phc.cssd.CssdEditSterile;
import com.phc.cssd.CssdSterile;
import com.phc.cssd.CssdWash;
import com.phc.cssd.R;
import com.phc.cssd.model.ModelImportWashDetail;

import java.util.HashMap;
import java.util.List;

public class ImportWashDetailAdapter extends ArrayAdapter<ModelImportWashDetail> {

    private final List<ModelImportWashDetail> DATA_MODEL;
    private final Activity context;
    public int mode = 1 ;
    public HashMap <String ,List<ModelImportWashDetail>>MODEL_IMPORT_WASH_DETAIL_SUB;

    public ImportWashDetailAdapter(Activity context, List<ModelImportWashDetail> DATA_MODEL) {
        super(context, R.layout.activity_list_import_wash_detail, DATA_MODEL);
        this.context = context;
        this.DATA_MODEL = DATA_MODEL;

        for(int i=0;i<DATA_MODEL.size();i++){

            Log.d("ttest_DATA_MODEL","DATA_MODEL = "+DATA_MODEL.get(i).getBasketCode()+"--"+i+"--"+DATA_MODEL.get(i).getI_name());

        }
    }

    public ImportWashDetailAdapter(Activity context, List<ModelImportWashDetail> DATA_MODEL,int mode) {
        super(context, R.layout.activity_list_import_wash_detail, DATA_MODEL);
        this.context = context;
        this.DATA_MODEL = DATA_MODEL;
        this.mode=mode;
    }

    public ImportWashDetailAdapter(Activity context, List<ModelImportWashDetail> DATA_MODEL,HashMap<String ,List<ModelImportWashDetail>> MODEL_IMPORT_WASH_DETAIL_SUB,int mode) {
        super(context, R.layout.activity_list_import_wash_detail, DATA_MODEL);
        this.context = context;
        this.DATA_MODEL = DATA_MODEL;
        this.MODEL_IMPORT_WASH_DETAIL_SUB = MODEL_IMPORT_WASH_DETAIL_SUB;
        this.mode=mode;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = null;

        if (convertView == null) {
            LayoutInflater inflator = context.getLayoutInflater();
            view = inflator.inflate(R.layout.activity_list_import_wash_detail, null);

            final ViewHolder viewHolder = new ViewHolder();
            viewHolder.relativeLayout = (RelativeLayout) view.findViewById(R.id.relativeLayout);
            viewHolder.txt_item_code = (TextView) view.findViewById(R.id.txt_item_code);
            viewHolder.txt_item_program_id = (TextView) view.findViewById(R.id.txt_item_program_id);
            viewHolder.txt_no = (TextView) view.findViewById(R.id.txt_no);
            //viewHolder.txt_item_program = (TextView) view.findViewById(R.id.txt_item_program);
            viewHolder.txt_item_name = (TextView) view.findViewById(R.id.txt_item_name);
            viewHolder.txt_qty = (TextView) view.findViewById(R.id.txt_qty);
            //viewHolder.chk = (CheckBox) view.findViewById(R.id.chk);
            viewHolder.imv_add = (ImageView) view.findViewById(R.id.imv_add);
            viewHolder.txt_packingmat = (TextView) view.findViewById(R.id.txt_packingmat);
            viewHolder.txt_basket = (TextView) view.findViewById(R.id.txt_basket);
            viewHolder.txt_IsRemarkems = (ImageView) view.findViewById(R.id.txt_IsRemarkems);

            viewHolder.sub_item = (ListView) view.findViewById(R.id.sub_item);
            viewHolder.sub_item.setVisibility(View.GONE);

            if(mode==1){
                viewHolder.relativeLayout.setOnLongClickListener(new View.OnLongClickListener() {
                    public boolean onLongClick(View v) {
                        ((CssdSterile)context).openDialogWashManagement(
                                viewHolder.txt_item_code.getText().toString(),
                                viewHolder.txt_item_name.getText().toString() );
                        return false;
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
                                String  gQty = editQty.getText().toString();
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
                                                (( CssdSterile ) context).importWashDetail(
                                                        viewHolder.txt_item_code.getText().toString(),
                                                        viewHolder.txt_item_program_id.getText().toString(),
                                                        viewHolder.item_program,
                                                        viewHolder.PackingMatID,
                                                        gQty,
                                                        viewHolder.basket
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

                viewHolder.imv_add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ((CssdSterile)context).importWashDetail(
                                viewHolder.txt_item_code.getText().toString(),
                                viewHolder.txt_item_program_id.getText().toString() ,
                                viewHolder.item_program,
                                viewHolder.PackingMatID,
                                viewHolder.txt_qty.getText().toString(),
                                viewHolder.basket

                        );
                    }
                });
            }
            else if(mode==2){
                viewHolder.imv_add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ((CssdEditSterile)context).importWashDetail(
                                viewHolder.txt_item_code.getText().toString(),
                                viewHolder.txt_item_program_id.getText().toString() ,
                                viewHolder.item_program,
                                viewHolder.PackingMatID,
                                "0",
                                null
                        );
                    }
                });
            }
            else if(mode == 3){
                if(DATA_MODEL.get(position).isCheck()) {
                    String basket = DATA_MODEL.get(position).getBasketCode();
                    final List<ModelImportWashDetail> DETAIL_SUB = MODEL_IMPORT_WASH_DETAIL_SUB.get(basket);

//                    Log.d("ttest","DETAIL_SUB = "+basket+"--"+x+"--");

                    viewHolder.sub_item.setAdapter(new ImportWashDetailAdapter((CssdSterile) context, DETAIL_SUB));

                    final float scale = getContext().getResources().getDisplayMetrics().density;
                    int pixels = (int) (44 * scale + 0.5f);

                    viewHolder.sub_item.getLayoutParams().height = pixels * DETAIL_SUB.size();

                    viewHolder.txt_packingmat.setVisibility(View.GONE);
                    viewHolder.txt_basket.setVisibility(View.GONE);

                    viewHolder.relativeLayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (viewHolder.sub_item.getVisibility() == View.GONE) {
                                viewHolder.sub_item.setVisibility(View.VISIBLE);
                            } else {
                                viewHolder.sub_item.setVisibility(View.GONE);
                            }

                        }
                    });

                    viewHolder.imv_add.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            for (int i = 0; i < DETAIL_SUB.size(); i++) {

                                ModelImportWashDetail x = DETAIL_SUB.get(i);

                                ((CssdSterile) context).importWashDetail(
                                        x.getI_code(),
                                        x.getI_program_id(),
                                        x.getI_program(),
                                        x.getPackingMatID(),
                                        x.getI_qty(),
                                        x.getBasketCode()
                                );
                            }
                        }
                    });
                }else {
                    viewHolder.imv_add.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ((CssdSterile)context).importWashDetail(
                                    viewHolder.txt_item_code.getText().toString(),
                                    viewHolder.txt_item_program_id.getText().toString() ,
                                    viewHolder.item_program,
                                    viewHolder.PackingMatID,
                                    viewHolder.txt_qty.getText().toString(),
                                    viewHolder.basket

                                );
                            }
                        });
                    }
            }
            else if(mode == 4){
                if(DATA_MODEL.get(position).isCheck()) {
                    String basket = DATA_MODEL.get(position).getBasketCode();
                    final List<ModelImportWashDetail> DETAIL_SUB = MODEL_IMPORT_WASH_DETAIL_SUB.get(basket);

//                    Log.d("ttest","DETAIL_SUB = "+basket+"--"+x+"--");

                    viewHolder.sub_item.setAdapter(new ImportWashDetailAdapter((CssdSterile) context, DETAIL_SUB));

                    final float scale = getContext().getResources().getDisplayMetrics().density;
                    int pixels = (int) (44 * scale + 0.5f);

                    viewHolder.sub_item.getLayoutParams().height = pixels * DETAIL_SUB.size();

                    viewHolder.txt_packingmat.setVisibility(View.GONE);
                    viewHolder.txt_basket.setVisibility(View.GONE);

                    viewHolder.relativeLayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (viewHolder.sub_item.getVisibility() == View.GONE) {
                                viewHolder.sub_item.setVisibility(View.VISIBLE);
                            } else {
                                viewHolder.sub_item.setVisibility(View.GONE);
                            }

                        }
                    });

                    viewHolder.imv_add.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            for (int i = 0; i < DETAIL_SUB.size(); i++) {

                                ModelImportWashDetail x = DETAIL_SUB.get(i);

                                ((CssdSterile) context).importWashDetailToBasket(
                                        x.getI_code(),
                                        x.getI_program_id(),
                                        x.getI_program(),
                                        x.getPackingMatID(),
                                        x.getI_qty(),
                                        x.getBasketCode()
                                );
                            }
                        }
                    });
                }else {
                    viewHolder.imv_add.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ((CssdSterile)context).importWashDetailToBasket(
                                    viewHolder.txt_item_code.getText().toString(),
                                    viewHolder.txt_item_program_id.getText().toString() ,
                                    viewHolder.item_program,
                                    viewHolder.PackingMatID,
                                    viewHolder.txt_qty.getText().toString(),
                                    viewHolder.basket

                            );
                        }
                    });
                }
            }

            view.setTag(viewHolder);

        } else {
            view = convertView;
        }

        // =========================================================================================
        final ImportWashDetailAdapter.ViewHolder holder = (ImportWashDetailAdapter.ViewHolder) view.getTag();
        holder.txt_item_code.setText( DATA_MODEL.get(position).getI_id());
        holder.txt_packingmat.setText( "( " + DATA_MODEL.get(position).getPackingMat() + " : " + DATA_MODEL.get(position).getShelflife() + " วัน )");
//        holder.txt_no.setText( DATA_MODEL.get(position).getI_no() + ".");
        holder.txt_no.setText(position+1 + ".");
        holder.txt_item_program_id.setText( DATA_MODEL.get(position).getI_program_id());
        holder.txt_basket.setText(DATA_MODEL.get(position).getBasketName());
        holder.txt_item_name.setText(DATA_MODEL.get(position).getI_name());
        holder.txt_qty.setText(DATA_MODEL.get(position).getI_qty());

        holder.index = (DATA_MODEL.get(position).getIndex());
        holder.PackingMatID = DATA_MODEL.get(position).getPackingMatID();
        holder.item_program = DATA_MODEL.get(position).getI_program();
        holder.basket = DATA_MODEL.get(position).getBasketCode();

        if (!DATA_MODEL.get(position).getIsRemarkExpress().equals("0")){
            holder.txt_IsRemarkems.setVisibility(View.VISIBLE);
        }else {
            holder.txt_IsRemarkems.setVisibility(View.GONE);
        }
        //holder.chk.setImageResource(DATA_MODEL.get(position).getCheck() );
        //holder.chk.setChecked(DATA_MODEL.get(position).isCheck());
        // =========================================================================================

        return view;
    }

    static class ViewHolder {
        public String basket;
        int index;
        TextView txt_item_code;
        TextView txt_no;
        TextView txt_item_program_id;
        TextView txt_packingmat;
        TextView txt_item_name;
        TextView txt_qty;
        TextView txt_basket;
        //CheckBox chk;
        ImageView imv_add;
        ImageView txt_IsRemarkems;
        RelativeLayout relativeLayout;
        String item_program;
        String PackingMatID;
        ListView sub_item;

    }

}