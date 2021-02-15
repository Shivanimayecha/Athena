package com.athena.group.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.athena.group.API.ApiConstants;
import com.athena.group.API.ApiInterface;
import com.athena.group.API.ApiServiceCreator;
import com.athena.group.Activity.JobReviewActivity;
import com.athena.group.Model.PastHsModel;
import com.athena.group.Model.UserDataResponse;
import com.athena.group.R;
import com.athena.group.application.SessionManager;
import com.athena.group.application.Utility;

import java.net.SocketTimeoutException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.HttpException;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class JobReportAdapter extends RecyclerView.Adapter<JobReportAdapter.MyviewHolder> {

    Context mContext;
    List<PastHsModel.Data> arraylist;
    private SessionManager sessionManager;
    private ApiInterface apiservice;
    private int statusCode;

    public JobReportAdapter(Context mContext, List<PastHsModel.Data> arraylist) {
        this.mContext = mContext;
        this.arraylist = arraylist;
        sessionManager = new SessionManager(mContext);
        apiservice = ApiServiceCreator.createService("latest");
    }

    @NonNull
    @Override
    public JobReportAdapter.MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.jobreprt_list, parent, false);
        return new MyviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull JobReportAdapter.MyviewHolder holder, int position) {

        holder.txt_sitename.setText("SiteName :- " + arraylist.get(position).getJb_site_name());
       /* holder.txt_contractorname.setText(arraylist.get(position).getJb_laber_name());
        holder.txt_plntnme.setText(arraylist.get(position).getJb_plant_name());
        holder.txt_qty.setText(arraylist.get(position).getJb_quantity());
        holder.txt_wrkonsite.setText(arraylist.get(position).getJb_work_on_site_notes());*/

        holder.txt_date.setText("Date :- " + arraylist.get(position).getJb_date());

        holder.img_edit.setOnClickListener(view -> {
            Utility.getAppcon().getSession().jobreview_operation = "update";
            Intent intent = new Intent(mContext, JobReviewActivity.class);
            intent.putExtra("jb_id", arraylist.get(position).getJb_id());
            mContext.startActivity(intent);
        });

        holder.img_delete.setOnClickListener(view -> {
            deleteApi(sessionManager.getKeyId(), arraylist.get(position).getJb_id(), position);
        });

        holder.img_pdf.setOnClickListener(view ->
        {
            String pdf_url = arraylist.get(position).getJb_new_pdf();
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(ApiConstants.PDF_URL + Uri.parse(pdf_url)));
            mContext.startActivity(browserIntent);
        });
    }

    private void deleteApi(String keyId, String jb_id, int position) {

        try {
            Observable<PastHsModel> responseObservable = apiservice.delate_jobReview_report(keyId, jb_id);
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
                    .subscribe(new Observer<PastHsModel>() {
                        @Override
                        public void onCompleted() {
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.e("error", "" + e.getMessage());
                        }

                        @Override
                        public void onNext(PastHsModel model) {
                            statusCode = model.getStatusCode();
                            if (statusCode == 200) {
                                Utility.displayToast(mContext, model.getMessage());
                                arraylist.remove(position);
                                notifyItemRemoved(position);
                            } else {
                                Utility.displayToast(mContext, model.getMessage());
                            }
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public int getItemCount() {
        return arraylist.size();
    }

    public class MyviewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.txt_sitename)
        TextView txt_sitename;
        /*@BindView(R.id.txt_contractorname)
        TextView txt_contractorname;
        @BindView(R.id.txt_plntnme)
        TextView txt_plntnme;
        @BindView(R.id.txt_qty)
        TextView txt_qty;*/

        @BindView(R.id.txt_date)
        TextView txt_date;
        @BindView(R.id.img_edit)
        ImageView img_edit;
        @BindView(R.id.img_pdf)
        ImageView img_pdf;
        @BindView(R.id.img_delete)
        ImageView img_delete;

        public MyviewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
