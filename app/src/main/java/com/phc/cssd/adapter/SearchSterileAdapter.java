package com.phc.cssd.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.phc.cssd.R;
import com.phc.cssd.model.ModelSterile;

import java.util.List;

public class SearchSterileAdapter extends ArrayAdapter<ModelSterile> {

    private final List<ModelSterile> DATA_MODEL;
    private final Activity context;

    public SearchSterileAdapter(Activity context, List<ModelSterile> DATA_MODEL) {
        super(context, R.layout.activity_list_search_sterile, DATA_MODEL);
        this.context = context;
        this.DATA_MODEL = DATA_MODEL;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = null;

        if (convertView == null) {
            LayoutInflater inflator = context.getLayoutInflater();
            view = inflator.inflate(R.layout.activity_list_search_sterile, null);

            final ViewHolder viewHolder = new ViewHolder();

            viewHolder.txt_id = (TextView) view.findViewById(R.id.txt_id);
            viewHolder.txt_doc_date = (TextView) view.findViewById(R.id.txt_doc_date);
            viewHolder.txt_doc_no = (TextView) view.findViewById(R.id.txt_doc_no);
            viewHolder.txt_program = (TextView) view.findViewById(R.id.txt_program);
            viewHolder.txt_round = (TextView) view.findViewById(R.id.txt_round);
            viewHolder.txt_machine = (TextView) view.findViewById(R.id.txt_machine);
            viewHolder.txt_start = (TextView) view.findViewById(R.id.txt_start);
            viewHolder.txt_finish = (TextView) view.findViewById(R.id.txt_finish);
            viewHolder.batch = (TextView) view.findViewById(R.id.batch);
            viewHolder.txt_usr_sterile = (TextView) view.findViewById(R.id.txt_usr_sterile);

            view.setTag(viewHolder);

        } else {
            view = convertView;
        }

        // =========================================================================================
        final ViewHolder holder = (ViewHolder) view.getTag();
        holder.txt_id.setText(DATA_MODEL.get(position).getID());
        holder.txt_doc_date.setText(DATA_MODEL.get(position).getDocDate());
        holder.txt_doc_no.setText(DATA_MODEL.get(position).getDocNo());

        if (!DATA_MODEL.get(position).getSterileName().equals("TEST")){
            holder.txt_program.setText(DATA_MODEL.get(position).getSterileName());
        }else {
            holder.txt_program.setText(DATA_MODEL.get(position).getSterileName()+ "( "+DATA_MODEL.get(position).getTestProgramName()+" )");
        }
        holder.txt_round.setText(DATA_MODEL.get(position).getSterileRoundNumber());
        holder.txt_machine.setText(DATA_MODEL.get(position).getMachineName());
        holder.txt_start.setText(DATA_MODEL.get(position).getS_time());
        holder.txt_finish.setText(DATA_MODEL.get(position).getF_time());
        holder.batch.setText(DATA_MODEL.get(position).getBatch());
        holder.txt_usr_sterile.setText(DATA_MODEL.get(position).getUsr_approve());
        holder.index = (DATA_MODEL.get(position).getIndex());


        return view;
    }

    static class ViewHolder {
        int index;
        TextView txt_id;
        TextView txt_doc_date;
        TextView txt_doc_no;
        TextView txt_program;
        TextView txt_round;
        TextView txt_machine;
        TextView txt_start;
        TextView txt_finish;
        TextView batch;
        TextView txt_usr_sterile;
    }

}