package com.athena.group.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.athena.group.Model.SiteContractorModel;
import com.athena.group.Model.labourmodel;
import com.athena.group.R;
import com.athena.group.application.Utility;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MblQuestick_labourAdapter extends RecyclerView.Adapter<MblQuestick_labourAdapter.myViewHolder> {

    Context mContext;
    List<SiteContractorModel.Data> arraylist;

    public interface OnItemClickListener {
        //void onItemClick(Float item, int item1, String validation);
        void onItemClick(List<labourmodel> labourmodels_list);
    }

    List<labourmodel> labourmodels_list;
    List<labourmodel> new_labourmodels_list;
    labourmodel l_model;


    OnItemClickListener listener;
    String laboure_list = "";
    List<labourmodel> arrayListQuestion3;

    public MblQuestick_labourAdapter(Context jobReviewActivity, List<SiteContractorModel.Data> data1, OnItemClickListener listener) {
        this.mContext = jobReviewActivity;
        this.arraylist = data1;
        this.listener = listener;
        // this.new_labourmodels_list = new_labourmodels_list;

    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.questntick_list1, parent, false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {

        //l_model=new labourmodel();
        labourmodels_list = new ArrayList<>();
        new_labourmodels_list = new ArrayList<>();

        holder.txt_quechktitle.setText(arraylist.get(position).getName() + " " + arraylist.get(position).getLname());

        Log.d("TAG", "new_labourmodels_list: " + new Gson().toJson(Utility.getAppcon().getSession().filled_labourlist));

        arrayListQuestion3 = new ArrayList<>();
        arrayListQuestion3 = Utility.getAppcon().getSession().filled_labourlist;
        if (arrayListQuestion3.size() == 0) {

            Log.e("Size is", "Null");
        } else {

            Log.e("inside else","inside else");
            labourmodels_list = arrayListQuestion3;
            for (int i = 0; i < labourmodels_list.size(); i++) {
                Log.e("inside for","inside for");

                if (labourmodels_list.get(i).getNames().equals(arraylist.get(position).getName() + " " + arraylist.get(position).getLname())) {
                    holder.chk_yn.setChecked(true);
                    Log.e("True Condition", "Ok " + position);
                }

            }

        }
            /*for (int i = 0; i < arraylist.size(); i++) {
                if (arraylist.get(i).getName().equals(Utility.getAppcon().getSession().filled_labourlist.get(i).getNames())) {
                    holder.chk_yn.setChecked(true);
                } else {
                    holder.chk_yn.setChecked(false);
                }
            }*/

        holder.chk_yn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {

                    if (laboure_list.equals("")) {
                        // l_model.setId(String.valueOf(position));
                        // l_model.setNames(arraylist.get(position).getName());
                        // labourmodels_list.add(l_model);
                        laboure_list = arraylist.get(position).getName();
                        labourmodels_list.add(new labourmodel(String.valueOf(position), arraylist.get(position).getName() + " " + arraylist.get(position).getLname()));
                        listener.onItemClick(labourmodels_list);
                    } else {
                        //  l_model.setId(String.valueOf(position));
                        //l_model.setNames(arraylist.get(position).getName());
                        // labourmodels_list.add(l_model);
                        labourmodels_list.add(new labourmodel(String.valueOf(position), arraylist.get(position).getName() + " " + arraylist.get(position).getLname()));
                        laboure_list = laboure_list + "," + arraylist.get(position).getName();
                        listener.onItemClick(labourmodels_list);

                    }
                    //questiondata_mbls.get(position + top_question_size).setAns_ques_status("Yes");
                } else {
                    // try {
                    if (labourmodels_list.size() == 0) {

                    } else {
                        Iterator<labourmodel> iter = labourmodels_list.iterator();
                        while (iter.hasNext()) {
                            labourmodel s = iter.next();

                            if (s.getNames().equals(arraylist.get(position).getName() + " " + arraylist.get(position).getLname())) {
                                iter.remove();
                                listener.onItemClick(labourmodels_list);
                            }
                        }

                       /* for (int p = 0; p < labourmodels_list.size(); p++) {
                            if (arrayListQuestion3 != null && arrayListQuestion3.size() > 0) {
                                if (arrayListQuestion3.get(p).getNames().equals(arraylist.get(position).getName())) {
                                    labourmodels_list.remove(p);
                                    listener.onItemClick(labourmodels_list);
                                }
                            }
                        }*/
                    }
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }

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

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
