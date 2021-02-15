package com.athena.group.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.athena.group.Model.planetmodel;
import com.athena.group.R;

import java.util.List;

public class MblQuestick_plantAdapter1 extends RecyclerView.Adapter<MblQuestick_plantAdapter1.myViewHolder> {

    Context mContext;
    List<planetmodel> labourmodels_list;

    public MblQuestick_plantAdapter1(Context jobReviewActivity, List<planetmodel> data1) {
        this.mContext = jobReviewActivity;
        this.labourmodels_list = data1;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.questntick_list2, parent, false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {

        holder.id.setText(Integer.toString(position + 1));

        if (labourmodels_list.get(position).getQuntity().equals("0")) {
            holder.l_id.setVisibility(View.GONE);
            holder.l_id1.setVisibility(View.GONE);
        } else {
            holder.l_id.setVisibility(View.VISIBLE);
            holder.l_id1.setVisibility(View.VISIBLE);
            holder.quntity.setText(labourmodels_list.get(position).getQuntity());
        }
        // holder.id.setText(String.valueOf(Integer.parseInt(labourmodels_list.get(position).getId())+1));
        holder.txt_quechktitle.setText(labourmodels_list.get(position).getPlant());
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
        TextView quntity;
        LinearLayout l_id;
        LinearLayout l_id1;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            //ButterKnife.bind(this, itemView);
            txt_quechktitle = itemView.findViewById(R.id.edt_AC_holder_name);
            id = itemView.findViewById(R.id.id);
            l_id = itemView.findViewById(R.id.l_id);
            l_id1 = itemView.findViewById(R.id.l_id1);
            quntity = itemView.findViewById(R.id.quntity);

        }
    }
}
