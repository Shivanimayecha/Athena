package com.athena.group.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.athena.group.Model.NotificationModel;
import com.athena.group.R;
import com.athena.group.Truck__Driver_Panel.SingleAssignOrderActivity;
import com.athena.group.orderHistory.SingleOpenOrderActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.MyViewHolder> {

    Context context;
    List<NotificationModel.Data> arrayList;

    public NotificationAdapter(Context context, List<NotificationModel.Data> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public NotificationAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_layout, parent, false);
        return new NotificationAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationAdapter.MyViewHolder holder, int position) {

        holder.txt_description.setText(arrayList.get(position).getDescription());
        holder.txt_date.setText(arrayList.get(position).getCreatedDate());
        holder.card_noti.setOnClickListener(view -> {
            if (arrayList.get(position).getRedirectStatus().equals("2")) {
                Intent intent = new Intent(context, SingleAssignOrderActivity.class);
                intent.putExtra("data_assign_ord_id", arrayList.get(position).getAssignOrdId());
                context.startActivity(intent);
            } else if (arrayList.get(position).getRedirectStatus().equals("4")) {
                Intent intent = new Intent(context, SingleOpenOrderActivity.class);
                intent.putExtra("data_delivered_ord_id", arrayList.get(position).getOrderId());
                context.startActivity(intent);
            } else if (arrayList.get(position).getRedirectStatus().equals("1")) {
                Intent intent = new Intent(context, SingleOpenOrderActivity.class);
                intent.putExtra("data_delivered_ord_id", arrayList.get(position).getOrderId());
                context.startActivity(intent);
            } else if (arrayList.get(position).getRedirectStatus().equals("5")) {
                Intent intent = new Intent(context, SingleOpenOrderActivity.class);
                intent.putExtra("data_delivered_ord_id", arrayList.get(position).getOrderId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.txt_description)
        TextView txt_description;
        @BindView(R.id.txt_date)
        TextView txt_date;
        @BindView(R.id.card_noti)
        CardView card_noti;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
