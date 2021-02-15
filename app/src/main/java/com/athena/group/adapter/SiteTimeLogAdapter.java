package com.athena.group.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.athena.group.Model.sitelogModel;
import com.athena.group.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SiteTimeLogAdapter extends RecyclerView.Adapter<SiteTimeLogAdapter.MyViewHolder> {


    Context context;
    List<sitelogModel.Data> arrayList;

    public SiteTimeLogAdapter(Context context, List<sitelogModel.Data> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }


    @NonNull
    @Override
    public SiteTimeLogAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contrctrhours_details, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SiteTimeLogAdapter.MyViewHolder holder, int position) {
        holder.txt_contractorname.setText(arrayList.get(position).getContractorName() + " :- ");
        holder.txt_contractorhr.setText(arrayList.get(position).getContractorHrs() + "  hrs");
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.txt_contractorname)
        TextView txt_contractorname;

        @BindView(R.id.txt_contractorhr)
        TextView txt_contractorhr;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
}
