package com.athena.group.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.athena.group.Model.labourmodel;
import com.athena.group.R;

import java.util.List;

public class MblQuestick_labourAdapter1 extends RecyclerView.Adapter<MblQuestick_labourAdapter1.myViewHolder> {

    Context mContext;
    List<labourmodel> labourmodels_list;

    public MblQuestick_labourAdapter1(Context jobReviewActivity, List<labourmodel> data1) {
        this.mContext = jobReviewActivity;
        this.labourmodels_list = data1;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.questntick_list3, parent, false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        // holder.id.setText(String.valueOf(Integer.parseInt(labourmodels_list.get(position).getId()) + 1));
        holder.id.setText(Integer.toString(position + 1));
        holder.txt_quechktitle.setText(labourmodels_list.get(position).getNames());
    }

    @Override
    public int getItemCount() {
        return labourmodels_list.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder {

        //  @BindView(R.id.edt_AC_holder_name)
        TextView txt_quechktitle;
        //@BindView(R.id.id)
        TextView id;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            //ButterKnife.bind(this, itemView);
            txt_quechktitle = itemView.findViewById(R.id.edt_AC_holder_name);
            id = itemView.findViewById(R.id.id);

        }
    }
}
