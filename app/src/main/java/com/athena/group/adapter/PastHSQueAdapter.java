package com.athena.group.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.athena.group.API.ApiConstants;
import com.athena.group.Model.PastHsModel;
import com.athena.group.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PastHSQueAdapter extends RecyclerView.Adapter<PastHSQueAdapter.MyviewHolder> {

    Context mContext;
    List<PastHsModel.Data> arraylist;

    public PastHSQueAdapter(Context mContext, List<PastHsModel.Data> arraylist) {
        this.mContext = mContext;
        this.arraylist = arraylist;
    }

    @NonNull
    @Override
    public MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.past_hs_qus_details, parent, false);
        return new MyviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyviewHolder holder, int position) {

       /* DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy");
        String inputDateStr = arraylist.get(position).getAns_needdate();
        Date date = null;
        try {
            date = inputFormat.parse(inputDateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String outputDateStr = outputFormat.format(date);*/
        holder.txt_date.setText("Date :- " + arraylist.get(position).getAns_completion());
        holder.txt_name.setText("Site Name :- " + arraylist.get(position).getSiteName());

        holder.img_pdf.setOnClickListener(view ->
        {
            String pdf_url = arraylist.get(position).getAnsPdf();
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
        @BindView(R.id.img_pdf)
        ImageView img_pdf;

        public MyviewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
