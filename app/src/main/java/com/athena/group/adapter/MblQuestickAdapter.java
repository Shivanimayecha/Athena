package com.athena.group.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.athena.group.Model.MobilePlantModel;
import com.athena.group.Model.QuestickModel;
import com.athena.group.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.athena.group.adapter.MblRecmndedAdapter.questiondata_mbls;

public class MblQuestickAdapter extends RecyclerView.Adapter<MblQuestickAdapter.myViewHolder> {

    Context mContext;
    List<QuestickModel.Data> arraylist;
    MobilePlantModel.questiondata_mbl mquesmdl;
    int top_question_size;

    public MblQuestickAdapter(Context mContext, List<QuestickModel.Data> arraylist, int top_question_size) {
        this.mContext = mContext;
        this.arraylist = arraylist;
        this.top_question_size = top_question_size;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.questntick_list, parent, false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {

        mquesmdl = new MobilePlantModel.questiondata_mbl();
        holder.txt_quechktitle.setText(arraylist.get(position).getMbQsLabel());

        mquesmdl.setAns_label(arraylist.get(position).getMbQsLabel());
        mquesmdl.setAns_recommanded_presure("");
        mquesmdl.setAns_actual_presure("");
        mquesmdl.setAns_ques_status("");
        questiondata_mbls.add(mquesmdl);

        try {
            Log.e("top Number is ", String.valueOf(top_question_size));
            for (int i = top_question_size; i < questiondata_mbls.size(); i++) {
                //   Log.e("Position is ", String.valueOf(i));
                questiondata_mbls.get(i).setAns_ques_status("");
            }
        } catch (Exception e) {

        }


        holder.rd1.setOnClickListener(view ->
        {
            questiondata_mbls.get(position + top_question_size).setAns_ques_status("Yes");
           /* for (int i = 0; i < questiondataList.size(); i++) {
                if (questiondataList.get(i).getAns_ques_value().equals("")) {
                    lok = "No";
                    //  Log.e("Validataion is1 ", lok);
                } else {
                    lok = "Yes";
                    /// Log.e("Validataion is1 ", lok);
                }
            }
            listener.onItemClick(lok);*/
        });
        holder.rd2.setOnClickListener(view ->
        {
            // Log.e("No","Ok");
            questiondata_mbls.get(position + top_question_size).setAns_ques_status("No");
            // questiondataList.get(position).setAns_ques_value_no("No");
            /*for (int i = 0; i < questiondataList.size(); i++) {
                if (questiondataList.get(i).getAns_ques_value().equals("")) {
                    lok = "No";
                    //  Log.e("Validataion is2 ", lok);
                } else {
                    lok = "Yes";
                    //  Log.e("Validataion is 2", lok);
                }

            }
            listener.onItemClick(lok);*/
        });
        holder.rd3.setOnClickListener(view ->
        {
            // Log.e("No","Ok");
            questiondata_mbls.get(position + top_question_size).setAns_ques_status("N/A");
            // questiondataList.get(position).setAns_ques_value_no("No");
            /*for (int i = 0; i < questiondataList.size(); i++) {
                if (questiondataList.get(i).getAns_ques_value().equals("")) {
                    lok = "No";
                    //  Log.e("Validataion is2 ", lok);
                } else {
                    lok = "Yes";
                    //  Log.e("Validataion is 2", lok);
                }

            }
            listener.onItemClick(lok);*/
        });


/*
        holder.chk_yn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    questiondata_mbls.get(position + top_question_size).setAns_ques_status("Yes");
                } else {
                    questiondata_mbls.get(position + top_question_size).setAns_ques_status("No");
                }
            }
        });
*/
    }

    @Override
    public int getItemCount() {
        return arraylist.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.txt_quechktitle)
        TextView txt_quechktitle;
        @BindView(R.id.rd1)
        RadioButton rd1;
        @BindView(R.id.rd2)
        RadioButton rd2;
        @BindView(R.id.rd3)
        RadioButton rd3;
        /*@BindView(R.id.chk_yn)
        CheckBox chk_yn;*/

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
