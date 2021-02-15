package com.athena.group.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.athena.group.Model.OrderDetailsModel;
import com.athena.group.R;
import com.athena.group.application.Utility;
import com.athena.group.orderHistory.SubOrderHistory;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderDetialsAdapter extends RecyclerView.Adapter<OrderDetialsAdapter.MyViewHolder> {

    Context mContext;
    List<OrderDetailsModel.Data> arraylist;

    public OrderDetialsAdapter(Context mContext, List<OrderDetailsModel.Data> arraylist) {
        this.mContext = mContext;
        this.arraylist = arraylist;
    }

    @NonNull
    @Override
    public OrderDetialsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_details, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderDetialsAdapter.MyViewHolder holder, int position) {


        /*DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy");
        //String inputDateStr = arraylist.get(position).getOrdDate();
        String inputDateStr = arraylist.get(position).getOrdDeliveryDate();
        Date date = null;
        try {
            date = inputFormat.parse(inputDateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String outputDateStr = outputFormat.format(date);
        holder.txt_date.setText(outputDateStr);*/

        holder.txt_date.setText(arraylist.get(position).getOrdDeliveryDate());
        holder.txt_ordrid.setText("Order #" + arraylist.get(position).getMs_ord_id() + " | ");
        Log.e("Order type", arraylist.get(position).getOrdStatus());
        Log.e("OrderStatusName", arraylist.get(position).getOrderStatusName());
        if (arraylist.get(position).getOrderStatusName().equals("Delivered")) {
            // holder.txt_ordrstatus.setText("Received");
            holder.txt_ordrstatus.setText("Delivered");
        } else {
            holder.txt_ordrstatus.setText(arraylist.get(position).getOrderStatusName());
        }

        holder.cardView.setOnClickListener(view -> {
            Utility.getAppcon().getSession().arrayListOrderResponse = new ArrayList<>();
            Utility.getAppcon().getSession().arrayListOrderResponse.add(arraylist.get(position));
            Intent intent = new Intent(mContext, SubOrderHistory.class);
            mContext.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return arraylist.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.txt_ordrid)
        TextView txt_ordrid;
        @BindView(R.id.txt_date)
        TextView txt_date;
        @BindView(R.id.txt_ordrstatus)
        TextView txt_ordrstatus;
        @BindView(R.id.cardView)
        CardView cardView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
