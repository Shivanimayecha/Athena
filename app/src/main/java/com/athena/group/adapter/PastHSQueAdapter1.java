package com.athena.group.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.athena.group.API.ApiInterface;
import com.athena.group.API.ApiServiceCreator;
import com.athena.group.Model.PastHsModel;
import com.athena.group.Model.UserDataResponse;
import com.athena.group.R;
import com.athena.group.application.SessionManager;
import com.athena.group.application.Utility;

import java.net.SocketTimeoutException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.HttpException;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class PastHSQueAdapter1 extends RecyclerView.Adapter<PastHSQueAdapter1.MyviewHolder> {

    Context mContext;
    List<PastHsModel.Data> arraylist;
    private SessionManager sessionManager;
    private ApiInterface apiservice;
    private int statusCode;

    public interface OnItemClickListener {
        void onItemClick(String label);
    }

    OnItemClickListener listener;

    public PastHSQueAdapter1(Context mContext, List<PastHsModel.Data> arraylist, OnItemClickListener listener) {
        this.mContext = mContext;
        this.arraylist = arraylist;
        this.listener = listener;
        sessionManager = new SessionManager(mContext);
        apiservice = ApiServiceCreator.createService("latest");
    }

    @NonNull
    @Override
    public MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.past_hs_qus_details1, parent, false);
        return new MyviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyviewHolder holder, int position) {

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
        holder.txt_name.setText("Site Name :- " + arraylist.get(position).getSiteName());

        holder.img_edit.setOnClickListener(view ->
        {

            Log.e("label is", arraylist.get(position).getAnsLabel());
            String label = arraylist.get(position).getAnsLabel();
            cleradata();
            listener.onItemClick("" + position);
        });
        holder.img_delete.setOnClickListener(view -> {
            removeFromList(sessionManager.getKeyId(), arraylist.get(position).getLastStep(), position);
        });
    }

    private void cleradata() {
        Utility.getAppcon().getSession().data_value = "";
        Utility.getAppcon().getSession().questionstart.clear();
        Utility.getAppcon().getSession().questiondataList1.clear();
        Utility.getAppcon().getSession().questiondataList2.clear();
        Utility.getAppcon().getSession().questiondataList3.clear();
        Utility.getAppcon().getSession().questiondataList4.clear();
        Utility.getAppcon().getSession().questiondataList5.clear();
        Utility.getAppcon().getSession().questiondataList6.clear();
        Utility.getAppcon().getSession().questiondataList7.clear();
        Utility.getAppcon().getSession().questiondataList8.clear();
        Utility.getAppcon().getSession().questiondataList9.clear();
        Utility.getAppcon().getSession().questiondataList10.clear();
        Utility.getAppcon().getSession().questiondataList11.clear();
        Utility.getAppcon().getSession().questiondataList12.clear();
        Utility.getAppcon().getSession().questiondataList13.clear();
        Utility.getAppcon().getSession().questiondataList14.clear();
        Utility.getAppcon().getSession().questiondataList15.clear();
        Utility.getAppcon().getSession().questiondataList16.clear();
        Utility.getAppcon().getSession().questiondataList17.clear();
        Utility.getAppcon().getSession().questiondataList18.clear();
    }

    private void removeFromList(String keyId, String lastStep, int position) {

        Observable<UserDataResponse> responseObservable = apiservice.delate_panding_hs_question_report(keyId, lastStep);
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
                .subscribe(new Observer<UserDataResponse>() {
                    @Override
                    public void onCompleted() {
//                        pDialog.dismiss();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("error", "" + e.getMessage());
                    }

                    @Override
                    public void onNext(UserDataResponse model) {
                        statusCode = model.getStatusCode();
                        if (statusCode == 200) {
                            Utility.displayToast(mContext, model.getMessage());
                            arraylist.remove(position);
                            notifyItemRemoved(position);
                            //notifyItemRangeChanged(position, getItemCount());
                        }
                    }
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
        @BindView(R.id.img_edit)
        ImageView img_edit;
        @BindView(R.id.img_delete)
        ImageView img_delete;
        /*@BindView(R.id.btn_pdf)
        Button btn_pdf;
        @BindView(R.id.btn_delete)
        Button btn_delete;*/

        public MyviewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
