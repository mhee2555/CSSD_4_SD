package com.phc.cssd.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.phc.cssd.CssdWash;
import com.phc.cssd.R;
import com.phc.cssd.model.ModelSendSterileDetail;
import com.phc.cssd.model.ModelWashDetail;

import java.util.HashMap;
import java.util.List;

public class WashDetailAdapter extends ArrayAdapter<ModelWashDetail> {

    private final List<ModelWashDetail> DATA_MODEL;
    private final Activity context;
    private final boolean IsActive;

    HashMap<String,List<ModelWashDetail>> MAP_MODEL_WASH_DETAIL_GROUP_BASKET;

    public WashDetailAdapter(Activity context, List<ModelWashDetail> DATA_MODEL, boolean IsActive,HashMap<String,List<ModelWashDetail>> MAP_MODEL_WASH_DETAIL_GROUP_BASKET) {
        super(context, R.layout.activity_list_wash_detail_data, DATA_MODEL);
        this.context = context;
        this.DATA_MODEL = DATA_MODEL;
        this.IsActive = IsActive;
        this.MAP_MODEL_WASH_DETAIL_GROUP_BASKET = MAP_MODEL_WASH_DETAIL_GROUP_BASKET;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View view = inflater.inflate(R.layout.activity_list_wash_detail_data, parent, false);

        RelativeLayout relativeLayout;

        int index;
        TextView txt_id;
        TextView txt_item_name;
        TextView txt_usage_code;
        TextView txt_qty;
        TextView txt_age;
        TextView txt_basket;
        ImageView imv_remove;
        ImageView isRemark;

        final String ID;
        final String ImportID;
        final String ItemStockID;
        
        relativeLayout = ( RelativeLayout ) view.findViewById(R.id.relativeLayout);
        txt_id = (TextView) view.findViewById(R.id.txt_id);
        txt_item_name = (TextView) view.findViewById(R.id.txt_item_name);
        txt_usage_code = (TextView) view.findViewById(R.id.txt_usage_code);
        txt_qty = (TextView) view.findViewById(R.id.txt_qty);
        txt_age = (TextView) view.findViewById(R.id.txt_age);
        imv_remove = (ImageView) view.findViewById(R.id.imv_remove);
        txt_basket = (TextView) view.findViewById(R.id.txt_basket);
        isRemark = (ImageView) view.findViewById(R.id.isRemark);

        final ListView list_sub = (ListView) view.findViewById(R.id.list_sub);

        txt_id.setText(DATA_MODEL.get(position).getID());
        txt_item_name.setText(DATA_MODEL.get(position).getItemname());
        txt_usage_code.setText(DATA_MODEL.get(position).getUsageCode());
        txt_qty.setText(DATA_MODEL.get(position).getQty());
        txt_age.setText(DATA_MODEL.get(position).getAge());
        txt_basket.setText(DATA_MODEL.get(position).getBasketName());
        imv_remove.setImageResource( IsActive ? R.drawable.ic_left_grey : R.drawable.ic_left_blue );
        index = (DATA_MODEL.get(position).getIndex());
        ID = DATA_MODEL.get(position).getID();
        ImportID = DATA_MODEL.get(position).getImportID();
        ItemStockID = DATA_MODEL.get(position).getItemStockID();

        if(DATA_MODEL.get(position).isBasket()){
            txt_basket.setVisibility(View.GONE);
            ModelWashDetail x =  DATA_MODEL.get(position);
            final List<ModelWashDetail> MODEL_SUB = MAP_MODEL_WASH_DETAIL_GROUP_BASKET.get(x.getBasketCode());

            list_sub.setAdapter(new WashDetailAdapter( (CssdWash)context, MODEL_SUB, IsActive,null));
            list_sub.setVisibility(View.VISIBLE);
            txt_qty.setVisibility(View.VISIBLE);
            txt_qty.setText(MODEL_SUB.size()+"");

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

            String sub_ID = MODEL_SUB.get(0).getID();
            String sub_ImportID = MODEL_SUB.get(0).getImportID();
            String sub_ItemStockID = MODEL_SUB.get(0).getItemStockID();

            String DATA = sub_ID + "@" + sub_ImportID + "@" + sub_ItemStockID + "@";

            if(MODEL_SUB.size()>1){
                for(int i=1;i<MODEL_SUB.size();i++){

                    sub_ID = MODEL_SUB.get(i).getID();
                    sub_ImportID = MODEL_SUB.get(i).getImportID();
                    sub_ItemStockID = MODEL_SUB.get(i).getItemStockID();

                    DATA += sub_ID + "@" + sub_ImportID + "@" + sub_ItemStockID + "@";

                    if (!MODEL_SUB.get(i).getIsRemarkExpress().equals("0")){
                        DATA_MODEL.get(position).setIsRemarkExpress("1");
                    }
                }
            }

            final String ALLDATA = DATA;

            imv_remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((CssdWash)context).removeWashtagInWashDetail(ALLDATA);
                }
            });

        }else{
            if(MAP_MODEL_WASH_DETAIL_GROUP_BASKET==null){
                imv_remove.setVisibility(View.INVISIBLE);
            }
            imv_remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((CssdWash)context).removeWashDetail( ImportID, ID, ItemStockID);
                }
            });
        }

        if (DATA_MODEL.get(position).getIsRemarkExpress().equals("1")){
            isRemark.setVisibility(View.VISIBLE);
        }else {
            isRemark.setVisibility(View.GONE);
        }

            /*
            relativeLayout.setOnLongClickListener(new View.OnLongClickListener() {
                public boolean onLongClick(View v) {
                    ((CssdWash)context).openDialogBasketManagement( DATA_MODEL );
                    return false;
                }
            });
            */


        return view;
    }

}