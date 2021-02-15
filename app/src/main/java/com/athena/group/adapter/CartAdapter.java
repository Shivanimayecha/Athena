package com.athena.group.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.athena.group.API.ApiInterface;
import com.athena.group.API.ApiServiceCreator;
import com.athena.group.Model.OrderDetailsModel;
import com.athena.group.Model.SpinnerModel;
import com.athena.group.PlaceOrder.CartActivity;
import com.athena.group.R;
import com.athena.group.application.SessionManager;
import com.athena.group.application.Utility;

import java.net.SocketTimeoutException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.HttpException;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder> {

    Context mContext;
    List<OrderDetailsModel.Data> arraylist;
    SessionManager sessionManager;
    ApiInterface apiservice;
    int statusCode;


    public CartAdapter(Context mContext, List<OrderDetailsModel.Data> arraylist) {
        this.mContext = mContext;
        this.arraylist = arraylist;
        sessionManager = new SessionManager(mContext);
        apiservice = ApiServiceCreator.createService("latest");
    }

    @NonNull
    @Override
    public CartAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_order_details, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.MyViewHolder holder, int position) {

        //holder.txt_date.setText(arraylist.get(position).getOrdDate());
        DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy");
        String inputDateStr = arraylist.get(position).getOrdDate();
        Date date = null;
        try {
            date = inputFormat.parse(inputDateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String outputDateStr = outputFormat.format(date);
        holder.txt_date.setText(outputDateStr);

        holder.txt_productname.setText(arraylist.get(position).getProductName());
        holder.txt_qty.setText(" Qty : " + arraylist.get(position).getOrdQty());

        if (arraylist.get(position).getProductName().equals("Aggregates")) {
            holder.txt_productvalue.setText("Type :- " + arraylist.get(position).getOrdAttributes().getType());
        } else if (arraylist.get(position).getProductName().equals("Concrete")) {
            holder.txt_productvalue.setText("Mix :- " + arraylist.get(position).getOrdAttributes().getMix() + "\n"
                    + "Slump :- " + arraylist.get(position).getOrdAttributes().getSlump() + "\n" +
                    "Aggregate Size :- " + arraylist.get(position).getOrdAttributes().getAggregateSize());
        } else if (arraylist.get(position).getProductName().equals("Materials")) {

            if (arraylist.get(position).getOrdAttributes().getOrdProductLabel().equals("Concrete drainage")) {
                holder.txt_productvalue.setText("Concrete Drainage :- " + arraylist.get(position).getOrdAttributes().getOrdLabel() + " : " + arraylist.get(position).getOrdAttributes().getOrdConcrete());
            } else if (arraylist.get(position).getOrdAttributes().getOrdProductLabel().equals("Mesh and accessories")) {
                holder.txt_productvalue.setText("Mesh :- " + arraylist.get(position).getOrdAttributes().getOrdMesh());
            } else if (arraylist.get(position).getOrdAttributes().getOrdProductLabel().equals("General building materials")) {
                holder.txt_productvalue.setText("GeneralBuilding Material :- " + arraylist.get(position).getOrdAttributes().getGeneralbuldingMaterial());
            } else if (arraylist.get(position).getOrdAttributes().getOrdProductLabel().equals("PPE")) {
                holder.txt_productvalue.setText("PPE :- " + arraylist.get(position).getOrdAttributes().getOrdPpe());
            } else if (arraylist.get(position).getOrdAttributes().getOrdProductLabel().equals("Small tools")) {
                holder.txt_productvalue.setText("Small tools :- " + arraylist.get(position).getOrdAttributes().getOrdSmalltools());
            } else if (arraylist.get(position).getOrdAttributes().getOrdProductLabel().equals("Plastic Drainage Polypipe")) {
                holder.txt_productvalue.setText("Plastic Drainage Polypipe :- " + arraylist.get(position).getOrdAttributes().getOrdPlasticDrainagePolypipe());
            } else if (arraylist.get(position).getOrdAttributes().getOrdProductLabel().equals("Polysewer / Ridgidrain")) {
                holder.txt_productvalue.setText("Polysewer / Ridgidrain :- " + arraylist.get(position).getOrdAttributes().getOrdPolysewer());
            } else if (arraylist.get(position).getOrdAttributes().getOrdProductLabel().equals("Plastic Drainage Wavin")) {
                holder.txt_productvalue.setText("Plastic Drainage Wavin :- " + arraylist.get(position).getOrdAttributes().getOrdPlasticDrainageWavin());
            } else if (arraylist.get(position).getOrdAttributes().getOrdProductLabel().equals("Clay Drainage")) {
                holder.txt_productvalue.setText("Clay Drainage :- " + arraylist.get(position).getOrdAttributes().getOrdClayDrainage());
            } else if (arraylist.get(position).getOrdAttributes().getOrdProductLabel().equals("Kerbs")) {
                holder.txt_productvalue.setText("Kerbs :- " + arraylist.get(position).getOrdAttributes().getOrdKerbs());
            } else if (arraylist.get(position).getOrdAttributes().getOrdProductLabel().equals("Flags")) {
                holder.txt_productvalue.setText("Flags :- " + arraylist.get(position).getOrdAttributes().getOrdFlags());
            } else if (arraylist.get(position).getOrdAttributes().getOrdProductLabel().equals("Block Paves")) {
                holder.txt_productvalue.setText("Block Paves :- " + arraylist.get(position).getOrdAttributes().getOrdBlockpaves());
            }

          /*  holder.txt_productvalue.setText("Concrete Drainage :- " + arraylist.get(position).getOrdAttributes().getOrdLabel() + " : " + arraylist.get(position).getOrdAttributes().getOrdConcrete() + "\n"
                    + "Mesh :- " + arraylist.get(position).getOrdAttributes().getOrdMesh() + "\n" +
                    "GeneralBuilding Material :- " + arraylist.get(position).getOrdAttributes().getGeneralbuldingMaterial() + "\n" +
                    "PPE :- " + arraylist.get(position).getOrdAttributes().getOrdPpe() + "\n" +
                    "Small tools :- " + arraylist.get(position).getOrdAttributes().getOrdSmalltools());*/
        }

        holder.img_delete.setOnClickListener(view -> {

            RemoveProduct(arraylist.get(position).getCartId(), position);
        });

    }

    private void RemoveProduct(String cartId, int position) {

        Observable<SpinnerModel> responseObservable = apiservice.removeProduct(cartId, sessionManager.getKeyId());

        Log.e("TAG", "RemoveProduct: " + cartId);
        responseObservable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext(throwable -> {
                    if (throwable instanceof HttpException) {
                        HttpException ex = (HttpException) throwable;
                        statusCode = ex.code();
                        Log.e("error", ex.getLocalizedMessage());
                    } else if (throwable instanceof SocketTimeoutException) {
                        statusCode = 1000;
                    }
                    return Observable.empty();
                })
                .subscribe(new Observer<SpinnerModel>() {
                    @Override
                    public void onCompleted() {
//                        pDialog.dismiss();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("error", "" + e.getMessage());
                    }

                    @Override
                    public void onNext(SpinnerModel model) {
                        statusCode = model.getStatusCode();
                        if (statusCode == 200) {
                            Utility.displayToast(mContext, "Product Remove succesfully");
                            arraylist.remove(position);
                            notifyItemRemoved(position);
                            notifyItemRangeChanged(position, getItemCount());
                            if (mContext instanceof CartActivity) {
                                ((CartActivity) mContext).DecreaseCartCountdata();
                            }
                        }
                    }
                });
    }

    @Override
    public int getItemCount() {
        return arraylist.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.txt_productname)
        TextView txt_productname;
        @BindView(R.id.txt_productvalue)
        TextView txt_productvalue;
        @BindView(R.id.txt_date)
        TextView txt_date;
        @BindView(R.id.txt_qty)
        TextView txt_qty;
        @BindView(R.id.img_delete)
        ImageView img_delete;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);


        }
    }
}
