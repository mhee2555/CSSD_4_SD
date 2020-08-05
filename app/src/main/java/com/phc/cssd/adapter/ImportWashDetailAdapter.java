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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
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
    final float scale = getContext().getResources().getDisplayMetrics().density;
    int pixels = (int) (44 * scale + 0.5f);
    int x= 1;

    public ImportWashDetailAdapter(Activity context, List<ModelImportWashDetail> DATA_MODEL) {
        super(context, R.layout.activity_list_import_wash_detail, DATA_MODEL);
        this.context = context;
        this.DATA_MODEL = DATA_MODEL;
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

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View view = inflater.inflate(R.layout.activity_list_import_wash_detail, parent, false);

        final String basket;
        final int index;
        final TextView txt_item_code;
        final TextView txt_no;
        final TextView txt_item_program_id;
        final TextView txt_packingmat;
        final TextView txt_item_name;
        final TextView txt_qty;
        final TextView txt_basket;
        final ImageView imv_add;
        final ImageView txt_IsRemarkems;
        final RelativeLayout relativeLayout;
        final String item_program;
        final String PackingMatID;
        final ListView sub_item;
        final String usageCode;

        relativeLayout = (RelativeLayout) view.findViewById(R.id.relativeLayout);
        txt_item_code = (TextView) view.findViewById(R.id.txt_item_code);
        txt_item_program_id = (TextView) view.findViewById(R.id.txt_item_program_id);
        txt_no = (TextView) view.findViewById(R.id.txt_no);
        txt_item_name = (TextView) view.findViewById(R.id.txt_item_name);
        txt_qty = (TextView) view.findViewById(R.id.txt_qty);
        imv_add = (ImageView) view.findViewById(R.id.imv_add);
        txt_packingmat = (TextView) view.findViewById(R.id.txt_packingmat);
        txt_basket = (TextView) view.findViewById(R.id.txt_basket);
        txt_IsRemarkems = (ImageView) view.findViewById(R.id.txt_IsRemarkems);

        sub_item = (ListView) view.findViewById(R.id.sub_item);
        sub_item.setVisibility(View.GONE);

        // =========================================================================================
        txt_item_code.setText( DATA_MODEL.get(position).getI_code());
        txt_packingmat.setText( "( " + DATA_MODEL.get(position).getPackingMat() + " : " + DATA_MODEL.get(position).getShelflife() + " วัน )");
//        txt_no.setText( DATA_MODEL.get(position).getI_no() + ".");
        txt_no.setText(position+1 + ".");
        txt_item_program_id.setText( DATA_MODEL.get(position).getI_program_id());
//        txt_basket.setText(DATA_MODEL.get(position).getBasketName());
        txt_item_name.setText(DATA_MODEL.get(position).getI_name());
        txt_qty.setText(DATA_MODEL.get(position).getI_qty());

//        index = (DATA_MODEL.get(position).getIndex());
        PackingMatID = DATA_MODEL.get(position).getPackingMatID();
        item_program = DATA_MODEL.get(position).getI_program();
        basket = DATA_MODEL.get(position).getBasketCode();

        usageCode =DATA_MODEL.get(position).getI_UsageCode();

        txt_basket.setText(usageCode);

        if (!DATA_MODEL.get(position).getIsRemarkExpress().equals("0")){
            txt_IsRemarkems.setVisibility(View.VISIBLE);
        }else {
            txt_IsRemarkems.setVisibility(View.GONE);
        }
        //chk.setImageResource(DATA_MODEL.get(position).getCheck() );
        //chk.setChecked(DATA_MODEL.get(position).isCheck());
        // =========================================================================================

        if(mode==1){// show nomal
            txt_qty.setVisibility(View.GONE);

            relativeLayout.setOnLongClickListener(new View.OnLongClickListener() {
                public boolean onLongClick(View v) {
                    ((CssdSterile)context).openDialogWashManagement(
                            txt_item_code.getText().toString(),
                            txt_item_name.getText().toString() );
                    return false;
                }
            });

            txt_qty.setOnLongClickListener(new View.OnLongClickListener() {
                public boolean onLongClick(View v) {
                    final Dialog dialog = new Dialog(context);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.dialog_qty);
                    dialog.setCancelable(true);

                    final TextView getQty = (TextView)dialog.findViewById(R.id.getQty);
                    getQty.setText(txt_qty.getText());

                    final EditText editQty = (EditText)dialog.findViewById(R.id.editQty);
                    Button button1 = (Button)dialog.findViewById(R.id.button1);

                    button1.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            String xQty = txt_qty.getText().toString();
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
                                                    txt_item_code.getText().toString(),
                                                    txt_item_program_id.getText().toString(),
                                                    item_program,
                                                    PackingMatID,
                                                    gQty,
                                                    basket,
                                                    usageCode
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

            imv_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((CssdSterile)context).importWashDetail(
                            txt_item_code.getText().toString(),
                            txt_item_program_id.getText().toString() ,
                            item_program,
                            PackingMatID,
                            txt_qty.getText().toString(),
                            basket,
                            usageCode

                    );
                }
            });
        }
        else if(mode==2){// show in CssdEditSterile
            imv_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((CssdEditSterile)context).importWashDetail(
                            txt_item_code.getText().toString(),
                            txt_item_program_id.getText().toString() ,
                            item_program,
                            PackingMatID,
                            "0",
                            null
                    );
                }
            });
        }
        else if(mode == 3){// show main and basket
            if(DATA_MODEL.get(position).isCheck()) {
                Log.d("ttest_DATA_MODEL","DATA_MODEL main = "+DATA_MODEL.get(position).getBasketCode()+"--"+position+"--"+DATA_MODEL.get(position).getI_name());

                final List<ModelImportWashDetail> DETAIL_SUB = MODEL_IMPORT_WASH_DETAIL_SUB.get(basket);

                sub_item.setAdapter(new ImportWashDetailAdapter((CssdSterile) context, DETAIL_SUB));

                sub_item.getLayoutParams().height = pixels * DETAIL_SUB.size();

                txt_item_name.setTypeface(null, Typeface.BOLD);

                txt_qty.setText(DETAIL_SUB.size()+"");
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

                imv_add.setOnClickListener(new View.OnClickListener() {
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
                                    x.getBasketCode(),
                                    x.getI_UsageCode()
                            );
                        }
                    }
                });
            }else {
                txt_qty.setVisibility(View.GONE);

                relativeLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ((CssdSterile) context).callCheckList(DATA_MODEL.get(position).getI_id());
                    }
                });

                imv_add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ((CssdSterile)context).importWashDetail(
                                txt_item_code.getText().toString(),
                                txt_item_program_id.getText().toString() ,
                                item_program,
                                PackingMatID,
                                txt_qty.getText().toString(),
                                basket,
                                usageCode

                        );
                    }
                });
            }
        }
        else if(mode == 4){// pair basket
            Log.d("ttest_Xoad","x = "+x+"--"+DATA_MODEL.size());
            x=x+1;
            Log.d("ttest_Load","Load = "+position+"--"+DATA_MODEL.get(position).isCheck());
            if(DATA_MODEL.get(position).isCheck()) {
                final List<ModelImportWashDetail> DETAIL_SUB = MODEL_IMPORT_WASH_DETAIL_SUB.get(basket);

//                    Log.d("ttest_DETAIL_SUB","DETAIL_SUB = "+basket+"--"+position+"--");

                sub_item.setAdapter(new ImportWashDetailAdapter((CssdSterile) context, DETAIL_SUB,4));

                sub_item.getLayoutParams().height = pixels * DETAIL_SUB.size();

                txt_qty.setText(DETAIL_SUB.size()+"");
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

                imv_add.setOnClickListener(new View.OnClickListener() {
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
                                    x.getBasketCode(),
                                    x.getI_UsageCode()
                            );
                        }
                    }
                });
            }else {
                txt_qty.setVisibility(View.GONE);

                imv_add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ((CssdSterile)context).importWashDetailToBasket(
                                txt_item_code.getText().toString(),
                                txt_item_program_id.getText().toString() ,
                                item_program,
                                PackingMatID,
                                txt_qty.getText().toString(),
                                basket,
                                usageCode

                        );
                    }
                });
            }
        }
        else if(mode==5){ //In Basket
            txt_qty.setVisibility(View.GONE);
            imv_add.setVisibility(View.GONE);
            ImageView imv_remove = (ImageView) view.findViewById(R.id.imv_remove);
            RadioButton isPrint = (RadioButton) view.findViewById(R.id.isPrint);
            imv_remove.setVisibility(View.VISIBLE);
            isPrint.setVisibility(View.VISIBLE);
            imv_remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    ((CssdSterile)context).onRemoveBasket(
                            DATA_MODEL.get(position).getBasketDetailId()
                    );

                }
            });

            isPrint.setChecked(DATA_MODEL.get(position).getPrint_count()>0);
        }

        return view;
    }

}