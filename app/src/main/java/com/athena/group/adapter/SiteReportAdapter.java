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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SiteReportAdapter extends RecyclerView.Adapter<SiteReportAdapter.MyViewHolder> {

    Context context;
    List<sitelogModel.Data> arrayList;

    public SiteReportAdapter(Context context, List<sitelogModel.Data> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public SiteReportAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.site_details_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SiteReportAdapter.MyViewHolder holder, int position) {

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
        //holder.txt_date.setText(arrayList.get(position).getSiteDate());
        holder.txt_hrs.setText(arrayList.get(position).getTotalWork());
        holder.txt_notes.setText(arrayList.get(position).getNote());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.txt_date)
        TextView txt_date;

        @BindView(R.id.txt_hrs)
        TextView txt_hrs;

        @BindView(R.id.txt_notes)
        TextView txt_notes;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
}
