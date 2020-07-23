package com.phc.cssd.adapter;

import android.app.Activity;
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
        View view = null;

        if (convertView == null) {
            LayoutInflater inflator = context.getLayoutInflater();
            view = inflator.inflate(R.layout.activity_list_sterile_detail_data, null);

            final ViewHolder viewHolder = new ViewHolder();

            viewHolder.txt_id = (TextView) view.findViewById(R.id.txt_id);
            //viewHolder.txt_department = (TextView) view.findViewById(R.id.txt_department);
            viewHolder.txt_item_name = (TextView) view.findViewById(R.id.txt_item_name);
            viewHolder.txt_usage_code = (TextView) view.findViewById(R.id.txt_usage_code);
            viewHolder.txt_qty = (TextView) view.findViewById(R.id.txt_qty);
            viewHolder.txt_age = (TextView) view.findViewById(R.id.txt_age);
            //viewHolder.chk = (ImageView) view.findViewById(R.id.chk);
            viewHolder.imv_remove = (ImageView) view.findViewById(R.id.imv_remove);
            viewHolder.imv_print = (ImageView) view.findViewById(R.id.imv_print);
            viewHolder.chk = (CheckBox) view.findViewById(R.id.chk);
            viewHolder.txt_basket = (TextView) view.findViewById(R.id.txt_basket);
            viewHolder.relativeLayout = (RelativeLayout) view.findViewById(R.id.relativeLayout);
            viewHolder.txt_IsRemarkems = (ImageView) view.findViewById(R.id.txt_IsRemarkems);
            viewHolder.list_sub = (ListView) view.findViewById(R.id.list_sub);

            viewHolder.list_sub.setVisibility(View.GONE);

            if(mode==1){
                viewHolder.chk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            DATA_MODEL.get(viewHolder.index).setCheck( ! DATA_MODEL.get(viewHolder.index).IsCheck );
                            //viewHolder.chk.setImageResource( DATA_MODEL.get(position).getCheck() );
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                });

                viewHolder.imv_remove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d("ttest_data","ImportID - "+viewHolder.ImportID);
                        Log.d("ttest_data","ItemStockID - "+viewHolder.ItemStockID);
                        Log.d("ttest_data","ID - "+viewHolder.ID);

                        ((CssdSterile)context).onRemoveBasket(
                                DATA_MODEL.get(position).getImportID()
                        );

                        ((CssdSterile)context).removeSterileDetail(
                                viewHolder.ImportID,
                                viewHolder.ID,
                                viewHolder.ItemStockID
                        );
                    }
                });

                viewHolder.relativeLayout.setOnLongClickListener(new View.OnLongClickListener() {
                    public boolean onLongClick(View v) {
                        Intent intent = new Intent(context, CssdPrintSterile.class);
                        intent.putExtra("ID", viewHolder.ID);
                        intent.putExtra("DocNo",  viewHolder.DocNo);
                        intent.putExtra("ItemStockID",  viewHolder.ItemStockID);
                        intent.putExtra("Qty",  viewHolder.Qty);
                        intent.putExtra("itemname",  viewHolder.itemname);

                        intent.putExtra("itemcode",  viewHolder.itemcode);
                        intent.putExtra("UsageCode",  viewHolder.UsageCode);
                        intent.putExtra("age",  viewHolder.age);
                        intent.putExtra("ImportID",  viewHolder.ImportID);
                        intent.putExtra("SterileDate",  viewHolder.SterileDate);

                        intent.putExtra("ExpireDate",  viewHolder.ExpireDate);
                        intent.putExtra("IsStatus",  viewHolder.IsStatus);
                        intent.putExtra("OccuranceQty",  viewHolder.OccuranceQty);
                        intent.putExtra("DepName",  viewHolder.DepName);
                        intent.putExtra("DepName2",  viewHolder.DepName2);

                        intent.putExtra("LabelID",  viewHolder.LabelID);
                        intent.putExtra("usr_sterile",  viewHolder.usr_sterile);
                        intent.putExtra("usr_prepare",  viewHolder.usr_prepare);
                        intent.putExtra("usr_approve",  viewHolder.usr_approve);
                        intent.putExtra("SterileRoundNumber",  viewHolder.SterileRoundNumber);

                        intent.putExtra("MachineName",  viewHolder.MachineName);
                        intent.putExtra("Price",  viewHolder.Price);
                        intent.putExtra("Time",  viewHolder.Time);

                        intent.putExtra("Qty_Print",  viewHolder.Qty_Print);
                        intent.putExtra("ItemSetData",  viewHolder.ItemSetData);

                        intent.putExtra("B_ID",B_ID);
                        intent.putExtra("Width",Width);
                        intent.putExtra("Heigth",Heigth);

                        context.startActivity(intent);

                        return false;
                    }
                });

                viewHolder.relativeLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            DATA_MODEL.get(viewHolder.index).setCheck( ! DATA_MODEL.get(viewHolder.index).IsCheck );
                            //viewHolder.chk.setImageResource( DATA_MODEL.get(position).getCheck() );
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                });
            }
            else if(mode==2){
                ModelSterileDetail x = DATA_MODEL.get(position);
                if(x.getIsBasket()){
                    final List<ModelSterileDetail> MODEL_SUB = MAP_MODEL_STERILE_DETAIL_GROUP_BASKET.get(x.getBasketCode());
                    viewHolder.list_sub.setAdapter(new SterileDetailAdapter((CssdSterile)context, MODEL_SUB, IsActive,B_ID,Width,Heigth));

                    x.setQty(MODEL_SUB.size()+"");
                    viewHolder.txt_age.setVisibility(View.GONE);
                    viewHolder.txt_basket.setVisibility(View.GONE);

                    final float scale = getContext().getResources().getDisplayMetrics().density;
                    int pixels = (int) (45 * scale + 0.5f);

                    viewHolder.list_sub.getLayoutParams().height = (pixels * MODEL_SUB.size());

                    viewHolder.relativeLayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (viewHolder.list_sub.getVisibility() == View.GONE) {
                                viewHolder.list_sub.setVisibility(View.VISIBLE);
                            } else {
                                viewHolder.list_sub.setVisibility(View.GONE);
                            }

                        }
                    });

                    viewHolder.imv_remove.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            for(int i=0;i<MODEL_SUB.size();i++){

                                ((CssdSterile)context).onRemoveBasket(
                                        MODEL_SUB.get(position).getImportID()
                                );

                                ((CssdSterile)context).removeSterileDetail(
                                        MODEL_SUB.get(i).getImportID(),
                                        MODEL_SUB.get(i).getID(),
                                        MODEL_SUB.get(i).getItemStockID()
                                );
                            }
                        }
                    });

                }else{
                    viewHolder.chk.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            try {
                                DATA_MODEL.get(viewHolder.index).setCheck( ! DATA_MODEL.get(viewHolder.index).IsCheck );
                                //viewHolder.chk.setImageResource( DATA_MODEL.get(position).getCheck() );
                            }catch(Exception e){
                                e.printStackTrace();
                            }
                        }
                    });

                    viewHolder.imv_remove.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Log.d("ttest_data","ImportID - "+viewHolder.ImportID);
                            Log.d("ttest_data","ItemStockID - "+viewHolder.ItemStockID);
                            Log.d("ttest_data","ID - "+viewHolder.ID);

                            ((CssdSterile)context).removeSterileDetail(
                                    viewHolder.ImportID,
                                    viewHolder.ID,
                                    viewHolder.ItemStockID
                            );
                        }
                    });

                    viewHolder.relativeLayout.setOnLongClickListener(new View.OnLongClickListener() {
                        public boolean onLongClick(View v) {
                            Intent intent = new Intent(context, CssdPrintSterile.class);
                            intent.putExtra("ID", viewHolder.ID);
                            intent.putExtra("DocNo",  viewHolder.DocNo);
                            intent.putExtra("ItemStockID",  viewHolder.ItemStockID);
                            intent.putExtra("Qty",  viewHolder.Qty);
                            intent.putExtra("itemname",  viewHolder.itemname);

                            intent.putExtra("itemcode",  viewHolder.itemcode);
                            intent.putExtra("UsageCode",  viewHolder.UsageCode);
                            intent.putExtra("age",  viewHolder.age);
                            intent.putExtra("ImportID",  viewHolder.ImportID);
                            intent.putExtra("SterileDate",  viewHolder.SterileDate);

                            intent.putExtra("ExpireDate",  viewHolder.ExpireDate);
                            intent.putExtra("IsStatus",  viewHolder.IsStatus);
                            intent.putExtra("OccuranceQty",  viewHolder.OccuranceQty);
                            intent.putExtra("DepName",  viewHolder.DepName);
                            intent.putExtra("DepName2",  viewHolder.DepName2);

                            intent.putExtra("LabelID",  viewHolder.LabelID);
                            intent.putExtra("usr_sterile",  viewHolder.usr_sterile);
                            intent.putExtra("usr_prepare",  viewHolder.usr_prepare);
                            intent.putExtra("usr_approve",  viewHolder.usr_approve);
                            intent.putExtra("SterileRoundNumber",  viewHolder.SterileRoundNumber);

                            intent.putExtra("MachineName",  viewHolder.MachineName);
                            intent.putExtra("Price",  viewHolder.Price);
                            intent.putExtra("Time",  viewHolder.Time);

                            intent.putExtra("Qty_Print",  viewHolder.Qty_Print);
                            intent.putExtra("ItemSetData",  viewHolder.ItemSetData);

                            intent.putExtra("B_ID",B_ID);
                            intent.putExtra("Width",Width);
                            intent.putExtra("Heigth",Heigth);

                            context.startActivity(intent);

                            return false;
                        }
                    });

                    viewHolder.relativeLayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            try {
                                DATA_MODEL.get(viewHolder.index).setCheck( ! DATA_MODEL.get(viewHolder.index).IsCheck );
                                //viewHolder.chk.setImageResource( DATA_MODEL.get(position).getCheck() );
                            }catch(Exception e){
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }
            else if(mode==3){
                viewHolder.txt_usage_code.setVisibility(View.GONE);
                viewHolder.txt_age.setVisibility(View.GONE);
                viewHolder.txt_qty.setVisibility(View.GONE);
                viewHolder.imv_print.setVisibility(View.GONE);

                viewHolder.imv_remove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        ((CssdSterile)context).onRemoveBasket(
                                DATA_MODEL.get(position).getImportID()
                        );

                        ((CssdSterile)context).removeSterileDetail(
                                viewHolder.ImportID,
                                viewHolder.ID,
                                viewHolder.ItemStockID
                        );

                    }
                });
            }

            view.setTag(viewHolder);

        } else {
            view = convertView;
        }

        // =========================================================================================
        final ViewHolder holder = (ViewHolder) view.getTag();
        holder.txt_id.setText(DATA_MODEL.get(position).getID());
        //holder.txt_department.setText(DATA_MODEL.get(position).getDepName2());
        holder.txt_item_name.setText(DATA_MODEL.get(position).getItemname()); // + " - " + DATA_MODEL.get(position).getUsageCount());
        holder.txt_usage_code.setText(DATA_MODEL.get(position).getUsageCode());
        holder.txt_qty.setText(DATA_MODEL.get(position).getQty());
        holder.txt_age.setText(DATA_MODEL.get(position).getAge());
        if(mode==3){
            holder.txt_basket.setText(DATA_MODEL.get(position).getUsageCode());
        }else{
            holder.txt_basket.setText(DATA_MODEL.get(position).getBasketName());
        }
        // holder.chk.setImageResource(DATA_MODEL.get(position).getCheck() );
        holder.chk.setChecked(DATA_MODEL.get(position).isCheck());
        holder.index = position;
        holder.imv_print.setImageResource(DATA_MODEL.get(position).getPrintStatus() );
        holder.imv_remove.setImageResource( IsActive ? R.drawable.ic_left_grey : R.drawable.ic_left_blue );

        holder.ID = DATA_MODEL.get(position).getID();
        holder.DocNo = DATA_MODEL.get(position).getDocNo();
        holder.ItemStockID = DATA_MODEL.get(position).getItemStockID();
        holder.Qty = DATA_MODEL.get(position).getQty();
        holder.itemname = DATA_MODEL.get(position).getItemname();

        holder.itemcode = DATA_MODEL.get(position).getItemcode();
        holder.UsageCode = DATA_MODEL.get(position).getUsageCode();
        holder.age = DATA_MODEL.get(position).getAge();
        holder.ImportID = DATA_MODEL.get(position).getImportID();
        holder.SterileDate = DATA_MODEL.get(position).getSterileDate();

        holder.ExpireDate = DATA_MODEL.get(position).getExpireDate();
        holder.IsStatus = DATA_MODEL.get(position).getIsStatus();
        holder.OccuranceQty = DATA_MODEL.get(position).getOccuranceQty();
        holder.DepName = DATA_MODEL.get(position).getDepName();
        holder.DepName2 = DATA_MODEL.get(position).getDepName2();

        holder.LabelID = DATA_MODEL.get(position).getLabelID();
        holder.usr_sterile = DATA_MODEL.get(position).getUsr_sterile();
        holder.usr_prepare = DATA_MODEL.get(position).getUsr_prepare();
        holder.usr_approve = DATA_MODEL.get(position).getUsr_approve();
        holder.SterileRoundNumber = DATA_MODEL.get(position).getSterileRoundNumber();

        holder.MachineName = DATA_MODEL.get(position).getMachineName();
        holder.Price = DATA_MODEL.get(position).getPrice();
        holder.Time = DATA_MODEL.get(position).getTime();

        holder.Qty_Print = DATA_MODEL.get(position).getQty_Print();
        holder.ItemSetData = DATA_MODEL.get(position).getItemSetData();

        if (!DATA_MODEL.get(position).getIsRemarkExpress().equals("0")){
            holder.txt_IsRemarkems.setVisibility(View.VISIBLE);
        }else {
            holder.txt_IsRemarkems.setVisibility(View.GONE);
        }
        // =========================================================================================
        /*
        holder.txt_department.setBackgroundResource( holder.index%2 == 0 ? R.color.even : R.color.odd);
        holder.txt_item_name.setBackgroundResource( holder.index%2 == 0 ? R.color.even : R.color.odd);
        holder.txt_usage_code.setBackgroundResource( holder.index%2 == 0 ? R.color.even : R.color.odd);
        holder.txt_qty.setBackgroundResource( holder.index%2 == 0 ? R.color.even : R.color.odd);
        holder.txt_age.setBackgroundResource( holder.index%2 == 0 ? R.color.even : R.color.odd);
        holder.chk.setBackgroundResource( holder.index%2 == 0 ? R.color.even : R.color.odd);
        */
        /*
        if ((DATA_MODEL.get(position).getAge().equals("1"))) {
            holder.txt_age.setText("90");
        } else if (DATA_MODEL.get(position).getAge().equals("2")) {
            holder.txt_age.setText("365");
        } else if (DATA_MODEL.get(position).getAge().equals("3")) {
            holder.txt_age.setText("14");
        } else if (DATA_MODEL.get(position).getAge().equals("4")) {
            holder.txt_age.setText("14");
        } else if (DATA_MODEL.get(position).getAge().equals("5")) {
            holder.txt_age.setText("60");
        } else if (DATA_MODEL.get(position).getAge().equals("6")) {
            holder.txt_age.setText("90");
        } else if (DATA_MODEL.get(position).getAge().equals("7")) {
            holder.txt_age.setText("14");
        } else if (DATA_MODEL.get(position).getAge().equals("8")) {
            holder.txt_age.setText("14");
        } else if (DATA_MODEL.get(position).getAge().equals("9")) {
            holder.txt_age.setText("0");
        } else if (DATA_MODEL.get(position).getAge().equals("10")) {
            holder.txt_age.setText("90");
        } else if (DATA_MODEL.get(position).getAge().equals("11")) {
            holder.txt_age.setText("365");
        }
        */



        return view;
    }

    static class ViewHolder {
        int index;
        TextView txt_id;
        //TextView txt_department;
        TextView txt_item_name;
        TextView txt_usage_code;
        TextView txt_qty;
        TextView txt_age;
        ImageView imv_remove;
        ImageView imv_print;
        TextView txt_basket;
        CheckBox chk;
        RelativeLayout relativeLayout;

        String ID;
        String DocNo;
        String ItemStockID;
        String Qty;
        String itemname;

        String itemcode;
        String UsageCode;
        String age;
        String ImportID;
        String SterileDate;

        String ExpireDate;
        String IsStatus;
        String OccuranceQty;
        String DepName;
        String DepName2;

        String LabelID;
        String usr_sterile;
        String usr_prepare;
        String usr_approve;
        String SterileRoundNumber;

        String MachineName;
        String Price;
        String Time;

        String Qty_Print;
        String ItemSetData;
        ImageView txt_IsRemarkems;

        ListView list_sub;

    }

}