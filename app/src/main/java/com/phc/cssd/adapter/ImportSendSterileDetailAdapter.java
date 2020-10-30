package com.phc.cssd.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
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

import com.phc.cssd.CssdSterile;
import com.phc.cssd.CssdWash;
import com.phc.cssd.R;
import com.phc.cssd.model.ModelSendSterileDetail;
import com.phc.cssd.model.ModelSterileDetail;

import java.util.HashMap;
import java.util.List;

public class ImportSendSterileDetailAdapter extends ArrayAdapter<ModelSendSterileDetail> {

    private final List<ModelSendSterileDetail> DATA_MODEL;
    private final Activity context;
    private final boolean IsActive;

    HashMap<String,List<ModelSendSterileDetail>> MAP_MODEL_SEND_STERILE_DETAIL_GROUP_BASKET;

    public ImportSendSterileDetailAdapter(Activity context, List<ModelSendSterileDetail> DATA_MODEL, boolean IsActive,HashMap<String,List<ModelSendSterileDetail>> MAP_MODEL_SEND_STERILE_DETAIL_GROUP_BASKET) {
        super(context, R.layout.activity_list_import_send_sterile_detail, DATA_MODEL);
        this.context = context;
        this.DATA_MODEL = DATA_MODEL;
        this.IsActive = IsActive;
        this.MAP_MODEL_SEND_STERILE_DETAIL_GROUP_BASKET =MAP_MODEL_SEND_STERILE_DETAIL_GROUP_BASKET;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View view = inflater.inflate(R.layout.activity_list_import_send_sterile_detail, parent, false);

        int index;
        final TextView txt_item_code;
        TextView txt_no;
        final TextView txt_item_program_id;
        TextView txt_packingmat;
        final TextView txt_item_name;
        final TextView txt_qty;
        ImageView txt_IsRemarkems;

        ImageView imv_add;
        RelativeLayout relativeLayout;

        final String id;
        final String program_id;
        final String program_name;

        final String item_program;
        final String PackingMatID;


        relativeLayout = (RelativeLayout) view.findViewById(R.id.relativeLayout);
        txt_item_code = (TextView) view.findViewById(R.id.txt_item_code);
        txt_item_program_id = (TextView) view.findViewById(R.id.txt_item_program_id);
        txt_no = (TextView) view.findViewById(R.id.txt_no);
        txt_item_name = (TextView) view.findViewById(R.id.txt_item_name);
        txt_qty = (TextView) view.findViewById(R.id.txt_qty);
        imv_add = (ImageView) view.findViewById(R.id.imv_add);
        txt_packingmat = (TextView) view.findViewById(R.id.txt_packingmat);
        txt_IsRemarkems = (ImageView) view.findViewById(R.id.txt_IsRemarkems);
        final ListView list_sub = (ListView) view.findViewById(R.id.basket_detail);

        txt_item_code.setText( DATA_MODEL.get(position).getUsageCode());
//        txt_no.setText(  DATA_MODEL.get(position).getIsWashDept() + (DATA_MODEL.get(position).getIndex()+1) + ".");
        txt_no.setText(position+1+"");
        txt_item_program_id.setText( DATA_MODEL.get(position).getI_program_id());
        txt_item_name.setText(DATA_MODEL.get(position).getI_name());
        txt_qty.setText(DATA_MODEL.get(position).getI_qty());
        index = (DATA_MODEL.get(position).getIndex());
        program_id = DATA_MODEL.get(position).getI_program_id();
        program_name = DATA_MODEL.get(position).getI_program();
        id = DATA_MODEL.get(position).getI_id();
        PackingMatID = DATA_MODEL.get(position).getPackingMatID();
        item_program = DATA_MODEL.get(position).getI_program();

        relativeLayout.setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View v) {
                (( CssdWash )context).openDialogWashManagement(
                        txt_item_code.getText().toString(),
                        txt_item_name.getText().toString() );
                return false;
            }
        });

        if(DATA_MODEL.get(position).isBasket()){
            ModelSendSterileDetail x =  DATA_MODEL.get(position);
            final List<ModelSendSterileDetail> MODEL_SUB = MAP_MODEL_SEND_STERILE_DETAIL_GROUP_BASKET.get(x.getBasketCode());

            list_sub.setAdapter(new ImportSendSterileDetailAdapter( (CssdWash)context, MODEL_SUB, IsActive,null));
            list_sub.setVisibility(View.VISIBLE);
            txt_qty.setVisibility(View.VISIBLE);
            txt_qty.setText(MODEL_SUB.size()+"");
            txt_item_code.setVisibility(View.GONE);

            final float scale = getContext().getResources().getDisplayMetrics().density;
            int pixels = (int) (45 * scale + 0.5f);

            list_sub.getLayoutParams().height = (pixels * MODEL_SUB.size());

            relativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (list_sub.getVisibility() == View.GONE) {
                        list_sub.setVisibility(View.VISIBLE);
                    } else {
                        list_sub.setVisibility(View.GONE);
                    }

                }
            });

            txt_packingmat.setText("");

            String UsageCode = MODEL_SUB.get(0).getUsageCode();

            if(MODEL_SUB.size()>1){
                for(int i=1;i<MODEL_SUB.size();i++){
                    UsageCode += "@"+MODEL_SUB.get(i).getUsageCode();

                    if (!MODEL_SUB.get(i).getIsRemarkExpress().equals("0")){
                        DATA_MODEL.get(position).setIsRemarkExpress("1");
                    }
                }
            }

            final String in_basket_UsageCode = UsageCode;

            imv_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    ((CssdWash)context).importSendSterileDetail( id.toString(), program_id.toString(), program_name.toString());

                    ((CssdWash)context).importSendSterileDetail( in_basket_UsageCode, program_id, program_name);
                }
            });

        }else{
            txt_qty.setVisibility(View.INVISIBLE);
            txt_packingmat.setText( "( " + DATA_MODEL.get(position).getPackingMat() + " : " + DATA_MODEL.get(position).getShelflife() + " วัน )");

            imv_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    ((CssdWash)context).importSendSterileDetail( id.toString(), program_id.toString(), program_name.toString());
                    String UsageCode = DATA_MODEL.get(position).getUsageCode();
                    ((CssdWash)context).importSendSterileDetail( UsageCode, program_id, program_name);
                }
            });
        }

        if(MAP_MODEL_SEND_STERILE_DETAIL_GROUP_BASKET==null){
            imv_add.setVisibility(View.INVISIBLE);
        }

//        txt_qty.setOnLongClickListener(new View.OnLongClickListener() {
//            public boolean onLongClick(View v) {
//                final Dialog dialog = new Dialog(context);
//                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//                dialog.setContentView(R.layout.dialog_qty);
//                dialog.setCancelable(true);
//
//                TextView getQty = (TextView)dialog.findViewById(R.id.getQty);
//                getQty.setText(txt_qty.getText());
//
//                final EditText editQty = (EditText)dialog.findViewById(R.id.editQty);
//                Button button1 = (Button)dialog.findViewById(R.id.button1);
//
//                button1.setOnClickListener(new View.OnClickListener() {
//                    public void onClick(View v) {
//                        String xQty = txt_qty.getText().toString();
//                        String gQty = editQty.getText().toString();
//                        if (gQty.equals("")){
//                            Toast.makeText(context, "กรุณากรอกจำนวนตามที่มี !!", Toast.LENGTH_SHORT).show();
//                        }else {
//                            if (Integer.parseInt(gQty) <= Integer.parseInt(xQty)) {
//                                if (Integer.parseInt(gQty) == 0){
//                                    Toast.makeText(context, "กรุณากรอกจำนวนตามที่มี !!", Toast.LENGTH_SHORT).show();
//                                    editQty.setText("");
//                                }else {
//                                    if (Integer.parseInt(gQty) > Integer.parseInt(xQty)) {
//                                        Toast toast = Toast.makeText(context, (Integer.parseInt(gQty) - Integer.parseInt(xQty)), Toast.LENGTH_SHORT);
//                                        toast.setMargin(50, 50);
//                                        toast.show();
//                                    } else {
//                                        (( CssdWash ) context).importWashDetail(
//                                                txt_item_code.getText().toString(),
//                                                txt_item_program_id.getText().toString(),
//                                                item_program,
//                                                PackingMatID,
//                                                gQty
//                                        );
//                                    }
//                                    dialog.cancel();
//                                }
//                            }else if ((Integer.parseInt(gQty) > Integer.parseInt(xQty))){
//                                Toast.makeText(context, "กรุณากรอกจำนวนตามที่มี !!", Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    }
//                });
//                dialog.show();
//                return false;
//            }
//        });

    // =========================================================================================

        if (!DATA_MODEL.get(position).getIsRemarkExpress().equals("0")){
            txt_IsRemarkems.setVisibility(View.VISIBLE);
        }else {
            txt_IsRemarkems.setVisibility(View.INVISIBLE);
        }
        return view;
    }

}