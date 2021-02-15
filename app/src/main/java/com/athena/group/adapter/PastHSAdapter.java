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
import com.athena.group.Model.PastHsModel;
import com.athena.group.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PastHSAdapter extends RecyclerView.Adapter<PastHSAdapter.MyviewHolder> {

    Context mContext;
    List<PastHsModel.Data> arraylist;

    public PastHSAdapter(Context mContext, List<PastHsModel.Data> arraylist) {
        this.mContext = mContext;
        this.arraylist = arraylist;
    }

    @NonNull
    @Override
    public PastHSAdapter.MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.past_hs_details, parent, false);
        return new MyviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PastHSAdapter.MyviewHolder holder, int position) {

        DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy");
        String inputDateStr = arraylist.get(position).getCreatedDate();
        Date date = null;
        try {
            date = inputFormat.parse(inputDateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String outputDateStr = outputFormat.format(date);
        holder.txt_date.setText("Date :- " + outputDateStr);
        holder.txt_name.setText("Inspected by :- " + arraylist.get(position).getAnsInspectedBy());

        holder.btn_pdf.setOnClickListener(view ->
        {
            String pdf_url = arraylist.get(0).getAnsPdf();
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(ApiConstants.PDF_URL + Uri.parse(pdf_url)));
            mContext.startActivity(browserIntent);
        });
    }

    @Override
    public int getItemCount() {
        return arraylist.size();
    }

    public class MyviewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.txt_date)
        TextView txt_date;
        @BindView(R.id.txt_name)
        TextView txt_name;
        @BindView(R.id.btn_pdf)
        Button btn_pdf;

        public MyviewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
