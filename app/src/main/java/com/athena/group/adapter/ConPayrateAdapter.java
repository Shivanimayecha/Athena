package com.athena.group.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.athena.group.Model.HoursModel;
import com.athena.group.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ConPayrateAdapter extends RecyclerView.Adapter<ConPayrateAdapter.MyViewHolder> {

    Context mContext;
    List<HoursModel.ContimelogDetail> arrayList;

    public ConPayrateAdapter(Context mContext, List<HoursModel.ContimelogDetail> arrayList) {
        this.mContext = mContext;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ConPayrateAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.payrate_details, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ConPayrateAdapter.MyViewHolder holder, int position) {

        DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy");
        String inputDateStr = arrayList.get(position).getSiteDate();
        Date date = null;
        try {
            date = inputFormat.parse(inputDateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String outputDateStr = outputFormat.format(date);
        holder.txt_date.setText(outputDateStr);
        holder.txt_rate.setText(arrayList.get(position).getRate());
        holder.txt_payment.setText(arrayList.get(position).getPaymnet());
        holder.txt_totalwrkhr.setText(arrayList.get(position).getTotalWork() + " hr.");
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txt_date)
        TextView txt_date;
        @BindView(R.id.txt_rate)
        TextView txt_rate;
        @BindView(R.id.txt_payment)
        TextView txt_payment;
        @BindView(R.id.txt_totalwrkhr)
        TextView txt_totalwrkhr;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
