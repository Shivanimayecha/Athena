package com.athena.group.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.athena.group.API.ApiInterface;
import com.athena.group.API.ApiServiceCreator;
import com.athena.group.Model.OrderDetailsModel;
import com.athena.group.Model.OrderDetailsTruckModel;
import com.athena.group.R;
import com.athena.group.Truck__Driver_Panel.OrderDisplayActivity;
import com.athena.group.application.SessionManager;
import com.athena.group.application.Utility;

import java.net.SocketTimeoutException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Observer;
import rx.schedulers.Schedulers;

public class AssignOrderAdapter extends RecyclerView.Adapter<AssignOrderAdapter.MyViewHolder> {

    Context mContext;
    List<OrderDetailsTruckModel.Data> arraylist;
    private String truck_driver_status = "";
    SessionManager sessionManager;
    ApiInterface apiservice;
    int statusCode;
    AssignOrderDisplayAdapter adapter;
    RecyclerView.LayoutManager layoutManager;

    public AssignOrderAdapter(Context mContext, List<OrderDetailsTruckModel.Data> arraylist/*, OnItemCheckListener onItemCheckListener*/) {
        this.mContext = mContext;
        this.arraylist = arraylist;
        sessionManager = new SessionManager(mContext);
        apiservice = ApiServiceCreator.createService("latest");
        //this.onItemClick = onItemCheckListener;
    }

    @NonNull
    @Override
    public AssignOrderAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_details_truck, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AssignOrderAdapter.MyViewHolder holder, int position) {
        holder.rv_orderlist.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(mContext);

        holder.rv_orderlist.setLayoutManager(layoutManager);

        adapter = new AssignOrderDisplayAdapter(mContext, arraylist.get(position).getOrderDetails());
        holder.rv_orderlist.setAdapter(adapter);

        DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy");
        String inputDateStr = arraylist.get(position).getAoDate();
        Date date = null;
        try {
            date = inputFormat.parse(inputDateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String outputDateStr = outputFormat.format(date);
        holder.txt_date.setText(outputDateStr);
        //holder.txt_date.setText(arraylist.get(position).getAoDate());
        holder.txt_ordrid.setText("Order " + arraylist.get(position).getAoId() + " | ");
        holder.txt_ordrstatus.setText(arraylist.get(position).getOrderStatusName());
        //holder.txt_sitename.setText("Order to " + arraylist.get(position).getSiteName());
        if (arraylist.get(position).getPickupLocation().equals("")) {
            holder.txt_pickupname.setText("No Pickup Location");
        } else {
            holder.txt_pickupname.setText(arraylist.get(position).getPickupLocation());
        }
        holder.txt_dropname.setText(arraylist.get(position).getDropLocation());
        holder.txt_qty.setText(arraylist.get(position).getTotal_qty());
        holder.txt_ttlitems.setText(arraylist.get(position).getTotal_order());

        //qty items calculation
        /*int list = arraylist.get(0).getOrderDetails().size();
        holder.txt_ttlitems.setText(Integer.toString(list));

        Double total = 0.0;
        String tot = "";

        for (int i = 0; i < arraylist.get(0).getOrderDetails().size(); i++) {

            String price1 = arraylist.get(0).getOrderDetails().get(i).getOrdQty();
            total = total + Double.valueOf(price1);
            tot = String.valueOf(total);
        }
        holder.txt_qty.setText(tot);*/

        holder.txt_ordrstatus.setOnClickListener(view ->
        {
            if (holder.txt_ordrstatus.getText().toString().equals("Order Assign")) {
                holder.txt_ordrstatus.setText("Goods Delivered");
                truck_driver_status = "6";
                callStatusManagementApi(position);
            } else if (holder.txt_ordrstatus.getText().toString().equals("Goods Delivered")) {
                holder.txt_ordrstatus.setText("Goods Delivered");
            }
        });
        holder.linearLayout.setOnClickListener(view -> {

            Utility.getAppcon().getSession().arrayListTruckOrderResponse = new ArrayList<>();
            Utility.getAppcon().getSession().arrayListTruckOrderResponse.add(arraylist.get(position));
            Intent intent = new Intent(mContext, OrderDisplayActivity.class);
            intent.putExtra("status", holder.txt_ordrstatus.getText().toString());
            mContext.startActivity(intent);
        });

        //holder.check.setClickable(false);
        /*final OrderDetailsTruckModel.Data currentItem = arraylist.get(position);
        holder.check.setOnClickListener(view -> {
            //  holder.check.setChecked(holder.check.isChecked());
            if (holder.check.isChecked()) {
                onItemClick.onItemCheck(currentItem);
            } else {
                onItemClick.onItemUncheck(currentItem);
            }
        });*/
    }

    private void callStatusManagementApi(int pos) {

        Observable<OrderDetailsModel> responseObservable = apiservice.truck_OrderStatusmanagement(
                arraylist.get(pos).getAoId(),
                arraylist.get(pos).getAoOrdId(),
                arraylist.get(pos).getAoSiteId(),
                sessionManager.getKeyId(),
                sessionManager.getKeyTruckid(),
                //arraylist.get(0).getAoTruckId(),
                truck_driver_status,
                arraylist.get(pos).getAoPickupLocation());

        responseObservable.subscribeOn(Schedulers.newThread())
                .observeOn(rx.android.schedulers.AndroidSchedulers.mainThread())
                .onErrorResumeNext(throwable -> {
                    if (throwable instanceof retrofit2.HttpException) {
                        retrofit2.HttpException ex = (retrofit2.HttpException) throwable;
                        statusCode = ex.code();
                        Log.e("error", ex.getLocalizedMessage());
                    } else if (throwable instanceof SocketTimeoutException) {
                        statusCode = 1000;
                    }
                    return Observable.empty();
                })
                .subscribe(new Observer<OrderDetailsModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("error", "" + e.getMessage());
                    }

                    @Override
                    public void onNext(OrderDetailsModel model) {
                        statusCode = model.getStatusCode();
                        if (statusCode == 200) {
                            Utility.displayToast(mContext, model.getMessage());
                        } else {
                            Utility.displayToast(mContext, model.getMessage());
                        }
                    }
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
        /*@BindView(R.id.txt_sitename)
        TextView txt_sitename;*/
        @BindView(R.id.txt_pickupname)
        TextView txt_pickupname;
        @BindView(R.id.txt_dropname)
        TextView txt_dropname;
        @BindView(R.id.txt_ttlitems)
        TextView txt_ttlitems;
        @BindView(R.id.txt_qty)
        TextView txt_qty;
        @BindView(R.id.cardView)
        CardView cardView;
        @BindView(R.id.linearLayout)
        LinearLayout linearLayout;
        @BindView(R.id.rv_orderlist)
        RecyclerView rv_orderlist;
      /*  @BindView(R.id.chk_ordr)
        CheckBox check;*/

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }

/*
        public void setOnClickListener(View.OnClickListener onClickListener) {
            itemView.setOnClickListener(onClickListener);
        }
*/
    }


}
