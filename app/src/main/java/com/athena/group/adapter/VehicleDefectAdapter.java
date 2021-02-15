package com.athena.group.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.athena.group.API.ApiConstants;
import com.athena.group.Model.VDRLModel;
import com.athena.group.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VehicleDefectAdapter extends RecyclerView.Adapter<VehicleDefectAdapter.MyViewHolder> {

    Context mContext;
    List<VDRLModel.Data> arrayList;

    public VehicleDefectAdapter(Context mContext, List<VDRLModel.Data> arrayList) {
        this.mContext = mContext;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public VehicleDefectAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.vehicle_report_details, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VehicleDefectAdapter.MyViewHolder holder, int position) {

        DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy");
        String inputDateStr = arrayList.get(position).getDdAssignDate();
        Date date = null;
        try {
            date = inputFormat.parse(inputDateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String outputDateStr = outputFormat.format(date);
        holder.txt_date.setText(outputDateStr);

        //holder.txt_date.setText(arraylist.get(position).getOrdDate());
        holder.txt_ordrid.setText("Report " + arrayList.get(position).getDdId() + " | ");
        holder.txt_truckname.setText(arrayList.get(position).getDdVehicleNo());

        holder.btn_pdf.setOnClickListener(view ->
        {
            String pdf_url = arrayList.get(position).getDdPdf();
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(ApiConstants.PDF_URL + Uri.parse(pdf_url)));
            mContext.startActivity(browserIntent);
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txt_ordrid)
        TextView txt_ordrid;
        @BindView(R.id.txt_date)
        TextView txt_date;
        @BindView(R.id.txt_truckname)
        TextView txt_truckname;
        @BindView(R.id.btn_pdf)
        Button btn_pdf;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
