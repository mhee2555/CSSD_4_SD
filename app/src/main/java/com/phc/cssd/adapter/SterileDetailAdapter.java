package com.phc.cssd.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.phc.cssd.CssdPrintSterile;
import com.phc.cssd.CssdSterile;
import com.phc.cssd.R;
import com.phc.cssd.config.ConfigProgram;
import com.phc.cssd.model.ModelSterileDetail;

import java.util.HashMap;
import java.util.List;

public class SterileDetailAdapter extends ArrayAdapter<ModelSterileDetail> {

    private final List<ModelSterileDetail> DATA_MODEL;
    private final Activity context;
    private String B_ID = null;
    int Heigth = 0;
    int Width = 0;
    private final boolean IsActive;
    int mode = 1;
    HashMap<String,List<ModelSterileDetail>> MAP_MODEL_STERILE_DETAIL_GROUP_BASKET;

    public SterileDetailAdapter(Activity context, List<ModelSterileDetail> DATA_MODEL, boolean IsActive,String B_ID,int Width,int Heigth) {
        super(context, R.layout.activity_list_sterile_detail_data, DATA_MODEL);
        this.context = context;
        this.DATA_MODEL = DATA_MODEL;
        this.IsActive = IsActive;
        this.B_ID = B_ID;
        this.Width = Width;
        this.Heigth = Heigth;
        mode = 1;
    }

    public SterileDetailAdapter(Activity context, List<ModelSterileDetail> DATA_MODEL, boolean IsActive,String B_ID,int Width,int Heigth,int mode) {
        super(context, R.layout.activity_list_sterile_detail_data, DATA_MODEL);
        this.context = context;
        this.DATA_MODEL = DATA_MODEL;
        this.IsActive = IsActive;
        this.B_ID = B_ID;
        this.Width = Width;
        this.Heigth = Heigth;
        this.mode = mode;
    }

    public SterileDetailAdapter(Activity context, List<ModelSterileDetail> DATA_MODEL, boolean IsActive,String B_ID,int Width,int Heigth,int mode,HashMap<String,List<ModelSterileDetail>> MAP_MODEL_STERILE_DETAIL_GROUP_BASKET) {
        super(context, R.layout.activity_list_sterile_detail_data, DATA_MODEL);
        this.context = context;
        this.DATA_MODEL = DATA_MODEL;
        this.IsActive = IsActive;
        this.B_ID = B_ID;
        this.Width = Width;
        this.Heigth = Heigth;
        this.mode = mode;
        this.MAP_MODEL_STERILE_DETAIL_GROUP_BASKET = MAP_MODEL_STERILE_DETAIL_GROUP_BASKET;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View view = inflater.inflate(R.layout.activity_list_sterile_detail_data, parent, false);

        final int index;
        final TextView txt_id;
        final TextView txt_item_name;
        final TextView txt_usage_code;
        final TextView txt_qty;
        final TextView txt_age;
        final ImageView imv_remove;
        final ImageView imv_print;
        final TextView txt_basket;
        final CheckBox chk;
        final RelativeLayout relativeLayout;

        final String ID;
        final String DocNo;
        final String ItemStockID;
        final String Qty;
        final String itemname;

        final String itemcode;
        final String UsageCode;
        final String age;
        final String ImportID;
        final String SterileDate;

        final String ExpireDate;
        final String IsStatus;
        final String OccuranceQty;
        final String DepName;
        final String DepName2;

        final String LabelID;
        final String usr_sterile;
        final String usr_prepare;
        final String usr_approve;
        final String SterileRoundNumber;

        final String MachineName;
        final String Price;
        final String Time;

        final String Qty_Print;
        final String ItemSetData;
        final ImageView txt_IsRemarkems;

        final ListView list_sub;

        txt_id = (TextView) view.findViewById(R.id.txt_id);
        //txt_department = (TextView) view.findViewById(R.id.txt_department);
        txt_item_name = (TextView) view.findViewById(R.id.txt_item_name);
        txt_usage_code = (TextView) view.findViewById(R.id.txt_usage_code);
        txt_qty = (TextView) view.findViewById(R.id.txt_qty);
        txt_age = (TextView) view.findViewById(R.id.txt_age);
        //chk = (ImageView) view.findViewById(R.id.chk);
        imv_remove = (ImageView) view.findViewById(R.id.imv_remove);
        imv_print = (ImageView) view.findViewById(R.id.imv_print);
        chk = (CheckBox) view.findViewById(R.id.chk);
        txt_basket = (TextView) view.findViewById(R.id.txt_basket);
        relativeLayout = (RelativeLayout) view.findViewById(R.id.relativeLayout);
        txt_IsRemarkems = (ImageView) view.findViewById(R.id.txt_IsRemarkems);
        list_sub = (ListView) view.findViewById(R.id.list_sub);

        list_sub.setVisibility(View.GONE);

        // =========================================================================================
        txt_id.setText(DATA_MODEL.get(position).getID());
        //txt_department.setText(DATA_MODEL.get(position).getDepName2());
        txt_item_name.setText(DATA_MODEL.get(position).getItemname()); // + " - " + DATA_MODEL.get(position).getUsageCount());
        txt_usage_code.setText(DATA_MODEL.get(position).getUsageCode());
        txt_qty.setText(DATA_MODEL.get(position).getQty());
        txt_age.setText(DATA_MODEL.get(position).getAge());

        if(mode==3){
            txt_basket.setText(DATA_MODEL.get(position).getUsageCode());
        }else{
            txt_basket.setText(DATA_MODEL.get(position).getBasketName());
        }
        // chk.setImageResource(DATA_MODEL.get(position).getCheck() );
        chk.setChecked(DATA_MODEL.get(position).isCheck());
        index = position;
        imv_print.setImageResource(DATA_MODEL.get(position).getPrintStatus() );
        imv_remove.setImageResource( IsActive ? R.drawable.ic_left_grey : R.drawable.ic_left_blue );

        ID = DATA_MODEL.get(position).getID();
        DocNo = DATA_MODEL.get(position).getDocNo();
        ItemStockID = DATA_MODEL.get(position).getItemStockID();
        Qty = DATA_MODEL.get(position).getQty();
        itemname = DATA_MODEL.get(position).getItemname();

        itemcode = DATA_MODEL.get(position).getItemcode();
        UsageCode = DATA_MODEL.get(position).getUsageCode();
        age = DATA_MODEL.get(position).getAge();
        ImportID = DATA_MODEL.get(position).getImportID();
        SterileDate = DATA_MODEL.get(position).getSterileDate();

        ExpireDate = DATA_MODEL.get(position).getExpireDate();
        IsStatus = DATA_MODEL.get(position).getIsStatus();
        OccuranceQty = DATA_MODEL.get(position).getOccuranceQty();
        DepName = DATA_MODEL.get(position).getDepName();
        DepName2 = DATA_MODEL.get(position).getDepName2();

        LabelID = DATA_MODEL.get(position).getLabelID();
        usr_sterile = DATA_MODEL.get(position).getUsr_sterile();
        usr_prepare = DATA_MODEL.get(position).getUsr_prepare();
        usr_approve = DATA_MODEL.get(position).getUsr_approve();
        SterileRoundNumber = DATA_MODEL.get(position).getSterileRoundNumber();

        MachineName = DATA_MODEL.get(position).getMachineName();
        Price = DATA_MODEL.get(position).getPrice();
        Time = DATA_MODEL.get(position).getTime();

        Qty_Print = DATA_MODEL.get(position).getQty_Print();
        ItemSetData = DATA_MODEL.get(position).getItemSetData();

        if (!DATA_MODEL.get(position).getIsRemarkExpress().equals("0")){
            txt_IsRemarkems.setVisibility(View.VISIBLE);
        }else {
            txt_IsRemarkems.setVisibility(View.GONE);
        }
        // =========================================================================================

        if(ConfigProgram.basket_tag){
            imv_print.setImageResource(0);
        }

        if(mode==1){// show sub
            chk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        DATA_MODEL.get(index).setCheck( ! DATA_MODEL.get(index).IsCheck );
                        //chk.setImageResource( DATA_MODEL.get(position).getCheck() );
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
            });

            imv_remove.setVisibility(View.GONE);
//            imv_remove.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Log.d("ttest_data","ImportID - "+ImportID);
//                    Log.d("ttest_data","ItemStockID - "+ItemStockID);
//                    Log.d("ttest_data","ID - "+ID);
//
//                    ((CssdSterile)context).removeSterileDetail(
//                            DATA_MODEL.get(position).getImportID(),
//                            ID,
//                            ItemStockID
//                    );
//
//                    ((CssdSterile)context).Update_S_id_IN_Baskettag("("+DATA_MODEL.get(position).getImportID()+")",2);
//
//                }
//            });

        }
        else if(mode==2){// show main
            ModelSterileDetail x = DATA_MODEL.get(position);
            if(x.getIsBasket()){
                final List<ModelSterileDetail> MODEL_SUB = MAP_MODEL_STERILE_DETAIL_GROUP_BASKET.get(x.getBasketCode());
                list_sub.setAdapter(new SterileDetailAdapter((CssdSterile)context, MODEL_SUB, IsActive,B_ID,Width,Heigth));

                txt_qty.setText(MODEL_SUB.size()+"");
                txt_age.setVisibility(View.GONE);
                txt_basket.setVisibility(View.GONE);

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

                imv_remove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String W_id = "("+MODEL_SUB.get(0).getImportID();

                        for(int i=0;i<MODEL_SUB.size();i++){
                            W_id += ","+MODEL_SUB.get(i).getImportID();
                            ((CssdSterile)context).removeSterileDetail(
                                    MODEL_SUB.get(i).getImportID(),
                                    MODEL_SUB.get(i).getID(),
                                    MODEL_SUB.get(i).getItemStockID()
                            );
                        }
                        W_id += ")";
                        Log.d("tog_W_id_imv_remove",W_id);

                        ((CssdSterile)context).Update_S_id_IN_Baskettag(W_id,2);


                    }
                });

            }else{
                chk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            DATA_MODEL.get(index).setCheck( ! DATA_MODEL.get(index).IsCheck );
                            //chk.setImageResource( DATA_MODEL.get(position).getCheck() );
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                });

                imv_remove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d("ttest_data","ImportID - "+ImportID);
                        Log.d("ttest_data","ItemStockID - "+ItemStockID);
                        Log.d("ttest_data","ID - "+ID);

                        ((CssdSterile)context).removeSterileDetail(
                                ImportID,
                                ID,
                                ItemStockID
                        );

                        ((CssdSterile)context).Update_S_id_IN_Baskettag("("+ImportID+")",2);
                    }
                });

                if(ConfigProgram.basket_tag){
                    relativeLayout.setOnLongClickListener(new View.OnLongClickListener() {
                        public boolean onLongClick(View v) {
                            Intent intent = new Intent(context, CssdPrintSterile.class);
                            intent.putExtra("ID", ID);
                            intent.putExtra("DocNo",  DocNo);
                            intent.putExtra("ItemStockID",  ItemStockID);
                            intent.putExtra("Qty",  Qty);
                            intent.putExtra("itemname",  itemname);

                            intent.putExtra("itemcode",  itemcode);
                            intent.putExtra("UsageCode",  UsageCode);
                            intent.putExtra("age",  age);
                            intent.putExtra("ImportID",  ImportID);
                            intent.putExtra("SterileDate",  SterileDate);

                            intent.putExtra("ExpireDate",  ExpireDate);
                            intent.putExtra("IsStatus",  IsStatus);
                            intent.putExtra("OccuranceQty",  OccuranceQty);
                            intent.putExtra("DepName",  DepName);
                            intent.putExtra("DepName2",  DepName2);

                            intent.putExtra("LabelID",  LabelID);
                            intent.putExtra("usr_sterile",  usr_sterile);
                            intent.putExtra("usr_prepare",  usr_prepare);
                            intent.putExtra("usr_approve",  usr_approve);
                            intent.putExtra("SterileRoundNumber",  SterileRoundNumber);

                            intent.putExtra("MachineName",  MachineName);
                            intent.putExtra("Price",  Price);
                            intent.putExtra("Time",  Time);

                            intent.putExtra("Qty_Print",  Qty_Print);
                            intent.putExtra("ItemSetData",  ItemSetData);

                            intent.putExtra("B_ID",B_ID);
                            intent.putExtra("Width",Width);
                            intent.putExtra("Heigth",Heigth);

                            context.startActivity(intent);

                            return false;
                        }
                    });

                    relativeLayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            try {
                                DATA_MODEL.get(index).setCheck( ! DATA_MODEL.get(index).IsCheck );
                                //chk.setImageResource( DATA_MODEL.get(position).getCheck() );
                            }catch(Exception e){
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }
        }

        return view;
    }
    
}