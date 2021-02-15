package com.athena.group.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.athena.group.Model.SiteContractorModel;
import com.athena.group.Model.planetmodel;
import com.athena.group.R;
import com.athena.group.application.Utility;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MblQuestick_Plant_Adapter extends RecyclerView.Adapter<MblQuestick_Plant_Adapter.myViewHolder> {

    Context mContext;
    List<planetmodel> planetmodels;
    List<SiteContractorModel.Data> arraylist;

    public interface OnItemClickListener {
        //void onItemClick(Float item, int item1, String validation);
        void onItemClick(String validation, String check, List<planetmodel> planetmodels);

        void onItemClick1(String quntity, String check);
    }

    OnItemClickListener listener;
    String laboure_list = "";
    String quntity_list = "";
    String check = "Yes";
    List<planetmodel> editModelArrayList;

    public MblQuestick_Plant_Adapter(Context jobReviewActivity, List<SiteContractorModel.Data> data1, List<planetmodel> editModelArrayList, OnItemClickListener listener) {
        this.mContext = jobReviewActivity;
        this.arraylist = data1;
        this.listener = listener;
        this.editModelArrayList = editModelArrayList;

    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.quest_plant_list, parent, false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {

        planetmodels = new ArrayList<>();
        listener.onItemClick1(quntity_list, "Yes");
        holder.txt_quechktitle.setText(arraylist.get(position).getFa_label());

        Log.d("TAG", "filled_plantlist--- " + new Gson().toJson(Utility.getAppcon().getSession().filled_plantlist));

        List<planetmodel> plantlist1 = Utility.getAppcon().getSession().filled_plantlist;
        if (plantlist1.size() == 0) {
            //   Log.e("Size is","Null");
        } else {

            for (int i = 0; i < plantlist1.size(); i++) {

                try {
                    if (plantlist1.get(i).getPlant().equals(arraylist.get(position).getFa_label()) && !plantlist1.get(i).getQuntity().equals("0")) {
                        holder.editText.setVisibility(View.VISIBLE);
                        holder.chk_yn.setChecked(true);
                        editModelArrayList.get(position).setPlant(arraylist.get(position).getFa_label());
                        holder.editText.setText(plantlist1.get(i).getQuntity());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }


        holder.chk_yn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    holder.editText.setVisibility(View.VISIBLE);
                    holder.editText.requestFocus();
                    check = "No";
                    listener.onItemClick1(quntity_list, "Yes");
                    if (laboure_list.equals("")) {
                        editModelArrayList.get(position).setPlant(arraylist.get(position).getFa_label());
                        laboure_list = arraylist.get(position).getFa_label();
                        listener.onItemClick(laboure_list, check, editModelArrayList);
                    } else {
                        editModelArrayList.get(position).setPlant(arraylist.get(position).getFa_label());
                        laboure_list = laboure_list + "," + arraylist.get(position).getFa_label();
                        listener.onItemClick(laboure_list, check, editModelArrayList);
                    }
                } else {
                    editModelArrayList.get(position).setQuntity("0");
                    listener.onItemClick1(quntity_list, "No");
                    holder.editText.setVisibility(View.GONE);
                    holder.editText.clearFocus();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return arraylist.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.txt_quechktitle)
        TextView txt_quechktitle;
        @BindView(R.id.chk_yn)
        CheckBox chk_yn;

        @BindView(R.id.quantity)
        EditText editText;
        @BindView(R.id.liner)
        LinearLayout linearLayout;

        @BindView(R.id.quantity_layout)
        LinearLayout quantity_layout;

        @BindView(R.id.done1)
        TextView done1;


        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    editModelArrayList.get(getAdapterPosition()).setQuntity(editText.getText().toString());
                    Utility.getAppcon().getSession().hs1 = new ArrayList<>();
                    Utility.getAppcon().getSession().hs1 = editModelArrayList;
                    // planetmodels.get(getAdapterPosition()).setQuntity(editText.getText().toString());
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
        }
    }

}
