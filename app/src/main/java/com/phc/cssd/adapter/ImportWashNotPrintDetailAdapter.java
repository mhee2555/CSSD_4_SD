package com.phc.cssd.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.phc.cssd.CssdEditSterile;
import com.phc.cssd.CssdSterile;
import com.phc.cssd.R;
import com.phc.cssd.config.ConfigProgram;
import com.phc.cssd.model.ModelImportWashDetail;

import java.util.HashMap;
import java.util.List;

public class ImportWashNotPrintDetailAdapter extends ArrayAdapter {

    private final List<ModelImportWashDetail> DATA_MODEL_MASTER;
    private final List<ModelImportWashDetail> DATA_MODEL;
    private final Activity context;
    public HashMap<String ,List<ModelImportWashDetail>> MODEL_IMPORT_WASH_DETAIL_SUB;
    final float scale = getContext().getResources().getDisplayMetrics().density;
    int pixels = (int) (44 * scale + 0.5f);
    CheckBox chk_basket = null;

    public ImportWashNotPrintDetailAdapter(Activity context,List<ModelImportWashDetail> DATA_MODEL_MASTER, List<ModelImportWashDetail> DATA_MODEL,HashMap<String ,List<ModelImportWashDetail>> MODEL_IMPORT_WASH_DETAIL_SUB) {
        super(context, 0, DATA_MODEL);
        this.context = context;
        this.DATA_MODEL = DATA_MODEL;
        this.DATA_MODEL_MASTER = DATA_MODEL_MASTER;
        this.MODEL_IMPORT_WASH_DETAIL_SUB = MODEL_IMPORT_WASH_DETAIL_SUB;
        if(!ConfigProgram.pair_basket_2){
            set_num_btn_print_bk();
        }
    }

    public ImportWashNotPrintDetailAdapter(Activity context,List<ModelImportWashDetail> DATA_MODEL_MASTER, List<ModelImportWashDetail> DATA_MODEL,CheckBox chk_basket) {
        super(context, 0, DATA_MODEL);
        this.context = context;
        this.DATA_MODEL = DATA_MODEL;
        this.DATA_MODEL_MASTER = DATA_MODEL_MASTER;
        this.chk_basket = chk_basket;
    }

    @Override
    public int getCount() {
        return DATA_MODEL.size();
    }

    @Override
    public Object getItem(int position) {
        return DATA_MODEL.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View view = inflater.inflate(R.layout.list_import_not_print_wash_detail, parent, false);

        final String basket;
        final TextView txt_packingmat;
        final TextView txt_item_name;
        final TextView txt_basket;
        final ImageView txt_IsRemarkems;
        final ListView sub_item;
        final String usageCode;
        final RelativeLayout relativeLayout;
        final String w_id = DATA_MODEL.get(position).getI_id();

        relativeLayout = (RelativeLayout) view.findViewById(R.id.relativeLayout);
        txt_item_name = (TextView) view.findViewById(R.id.txt_item_name);
        txt_packingmat = (TextView) view.findViewById(R.id.txt_packingmat);
        txt_basket = (TextView) view.findViewById(R.id.txt_basket);
        txt_IsRemarkems = (ImageView) view.findViewById(R.id.txt_IsRemarkems);
        CheckBox check_no = (CheckBox) view.findViewById(R.id.check_no);

        sub_item = (ListView) view.findViewById(R.id.sub_item);
        sub_item.setVisibility(View.GONE);

        basket = DATA_MODEL.get(position).getBasketCode();

        // =========================================================================================
        txt_packingmat.setText( "( " + DATA_MODEL.get(position).getPackingMat() + " : " + DATA_MODEL.get(position).getShelflife() + " วัน )");
        usageCode =DATA_MODEL.get(position).getI_UsageCode();

        txt_item_name.setText(DATA_MODEL.get(position).getI_name());
        txt_basket.setText(usageCode);

        if (!DATA_MODEL.get(position).getIsRemarkExpress().equals("0")){
            txt_IsRemarkems.setVisibility(View.VISIBLE);
        }else {
            txt_IsRemarkems.setVisibility(View.GONE);
        }

        Log.d("ttest_for_print","isCheck = "+DATA_MODEL.get(position).getI_name()+"----"+DATA_MODEL.get(position).isCheck());

        if(DATA_MODEL.get(position).isBasket()) {
            final List<ModelImportWashDetail> DETAIL_SUB = MODEL_IMPORT_WASH_DETAIL_SUB.get(basket);

            sub_item.setAdapter(new ImportWashNotPrintDetailAdapter((CssdSterile) context,DATA_MODEL_MASTER, DETAIL_SUB,check_no));

            sub_item.getLayoutParams().height = pixels * DETAIL_SUB.size();

            txt_packingmat.setVisibility(View.GONE);
            txt_basket.setVisibility(View.GONE);

            relativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (sub_item.getVisibility() == View.GONE) {
                        sub_item.setVisibility(View.VISIBLE);
                    } else {
                        sub_item.setVisibility(View.GONE);
                    }

                }
            });

            relativeLayout.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Log.d("tog_onLongClick","getI_name = "+DATA_MODEL.get(position).getI_name()+"----"+DATA_MODEL.get(position).isCheck());
                    return true;
                }
            });

            boolean chk = false;

            for(int i =0;i<DETAIL_SUB.size();i++){
                if(DETAIL_SUB.get(i).isCheck()){
                    chk = true;
                }else{
                    chk = false;
                    break;
                }

            }

            check_no.setChecked(chk);
            check_no.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    for(int i =0;i<DETAIL_SUB.size();i++){
                        int index = DETAIL_SUB.get(i).getIndex();
                        DATA_MODEL_MASTER.get(index).setCheck(((CheckBox) v).isChecked());
                        DETAIL_SUB.get(i).setCheck(((CheckBox) v).isChecked());

                    }

                    sub_item.setAdapter(new ImportWashNotPrintDetailAdapter((CssdSterile) context,DATA_MODEL_MASTER, DETAIL_SUB,(CheckBox) v));
                    sub_item.setVisibility(View.VISIBLE);
                    set_num_btn_print_bk();
                }
            });
        }
        else {
            check_no.setChecked(DATA_MODEL.get(position).isCheck());

            relativeLayout.setOnLongClickListener(new View.OnLongClickListener() {
                public boolean onLongClick(View v) {
                    ((CssdSterile)context).wash_detail_management_pack(
                            w_id,
                            txt_item_name.getText().toString(),
                            usageCode);
                    return false;
                }
            });

            check_no.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int index = DATA_MODEL.get(position).getIndex();
                    DATA_MODEL_MASTER.get(index).setCheck(((CheckBox) v).isChecked());
                    DATA_MODEL.get(position).setCheck(((CheckBox) v).isChecked());

                    if(chk_basket!=null){
                        boolean chk = false;

                        for(int i =0;i<DATA_MODEL.size();i++){

                            if(DATA_MODEL.get(i).isCheck()){
                                chk = true;
                            }else{
                                chk = false;
                                break;
                            }
                        }

                        chk_basket.setChecked(chk);
                    }
                    set_num_btn_print_bk();
                }
            });
        }

        // =========================================================================================

        return view;
    }

    public void set_num_btn_print_bk(){
        int n=0;
        for(int i =0;i<DATA_MODEL_MASTER.size();i++){
            if(DATA_MODEL_MASTER.get(i).isCheck()){
                n++;
            }

        }

        ((CssdSterile)context).set_num_btn_print_bk(n);
    }

}
