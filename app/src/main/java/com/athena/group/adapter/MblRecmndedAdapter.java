package com.athena.group.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.athena.group.Model.MobilePlantModel;
import com.athena.group.Model.RecmndedModel;
import com.athena.group.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MblRecmndedAdapter extends RecyclerView.Adapter<MblRecmndedAdapter.myViewHolder> {

    Context mContext;
    List<RecmndedModel.Data> arraylist;
    MobilePlantModel.questiondata_mbl mquesmdl;
    public static List<MobilePlantModel.questiondata_mbl> questiondata_mbls = new ArrayList<>();

    public MblRecmndedAdapter(Context mContext, List<RecmndedModel.Data> arraylist) {
        this.mContext = mContext;
        this.arraylist = arraylist;
    }

    @NonNull
    @Override
    public MblRecmndedAdapter.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.questnrecmded_list, parent, false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MblRecmndedAdapter.myViewHolder holder, int position) {

        mquesmdl = new MobilePlantModel.questiondata_mbl();
        holder.txt_rcmndque.setText(arraylist.get(position).getMbQsLabel());
        holder.txt_rcmndvalue.setText(arraylist.get(position).getMbReqPressure());

        mquesmdl.setAns_label(arraylist.get(position).getMbQsLabel());
        mquesmdl.setAns_ques_status("");
        mquesmdl.setAns_recommanded_presure(arraylist.get(position).getMbReqPressure());
        mquesmdl.setAns_actual_presure("");
        questiondata_mbls.add(mquesmdl);
    }

    @Override
    public int getItemCount() {
        return arraylist.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.txt_rcmndque)
        TextView txt_rcmndque;
        @BindView(R.id.txt_rcmndvalue)
        TextView txt_rcmndvalue;
        @BindView(R.id.edt_rcmd)
        EditText edt_rcmd;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            edt_rcmd.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    questiondata_mbls.get(getAdapterPosition()).setAns_actual_presure(edt_rcmd.getText().toString());
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
        }
    }
}
