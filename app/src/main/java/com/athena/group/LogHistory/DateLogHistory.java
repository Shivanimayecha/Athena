package com.athena.group.LogHistory;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.athena.group.API.ApiInterface;
import com.athena.group.API.ApiServiceCreator;
import com.athena.group.Model.SiteContractorModel;
import com.athena.group.Model.sitelogModel;
import com.athena.group.R;
import com.athena.group.adapter.SubConDetailsAdapter;
import com.athena.group.application.SessionManager;
import com.athena.group.application.Utility;
import com.wang.avi.AVLoadingIndicatorView;

import java.net.SocketTimeoutException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Observer;
import rx.schedulers.Schedulers;

public class DateLogHistory extends Activity implements View.OnClickListener {

    @BindView(R.id.rv_contctrdetails)
    RecyclerView rv_contctrdetails;
    @BindView(R.id.back_icon)
    ImageView back_icon;
    @BindView(R.id.toolbar_Title)
    TextView toolbar_Title;
    @BindView(R.id.avi)
    AVLoadingIndicatorView avi;
    @BindView(R.id.rl_fromdaydate)
    RelativeLayout rl_fromdaydate;
    @BindView(R.id.rl_todaydate)
    RelativeLayout rl_todaydate;
    @BindView(R.id.txt_fromdate)
    TextView txt_fromdate;
    @BindView(R.id.txt_enddate)
    TextView txt_enddate;
    @BindView(R.id.txt_day)
    TextView txt_day;
    @BindView(R.id.txt_day1)
    TextView txt_day1;
    @BindView(R.id.btn_submit)
    Button btn_submit;
    @BindView(R.id.txt_daystotal)
    TextView txt_daystotal;
    @BindView(R.id.txt_totalhrs)
    TextView txt_totalhrs;
    @BindView(R.id.ll_reports)
    LinearLayout ll_reports;
    @BindView(R.id.card_reports)
    CardView card_reports;
    @BindView(R.id.card_totalhrs)
    CardView card_totalhrs;
    @BindView(R.id.btn_csv)
    Button btn_csv;
    int statusCode;
    final Calendar myCalendar = Calendar.getInstance();
    private String fromdate = "", todate = "";
    String contractor = "", c_id = "";
    List<SiteContractorModel.Data> data;
    ApiInterface apiservice;
    SessionManager sessionManager;
    RecyclerView.LayoutManager layoutManager;
    SubConDetailsAdapter adapter;
    DatePickerDialog datePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_date_log_history);

        ButterKnife.bind(this);
        sessionManager = new SessionManager(DateLogHistory.this);
        apiservice = ApiServiceCreator.createService("latest");
        toolbar_Title.setText("Date Log History");
        initView();
    }

    private void initView() {

        rv_contctrdetails.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        rv_contctrdetails.setLayoutManager(layoutManager);

        card_reports.setVisibility(View.GONE);
        card_totalhrs.setVisibility(View.GONE);

        txt_fromdate.setText(Utility.date);
        fromdate = Utility.date;
        txt_enddate.setText(Utility.date);
        todate = Utility.date;
        txt_day.setText(Utility.day);
        txt_day1.setText(Utility.day);

        back_icon.setOnClickListener(this);
        rl_fromdaydate.setOnClickListener(this);
        rl_todaydate.setOnClickListener(this);
        btn_submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR); // current year
        int mMonth = c.get(Calendar.MONTH); // current month
        int mDay = c.get(Calendar.DAY_OF_MONTH); // current day

        switch (view.getId()) {
            case R.id.back_icon:
                finish();
                break;
            case R.id.rl_fromdaydate:
                datePickerDialog = new DatePickerDialog(DateLogHistory.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        txt_fromdate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                        fromdate = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year; //for pass in api
                        SimpleDateFormat simpledateformat = new SimpleDateFormat("EEEE");  //for day
                        Date date = new Date(year, monthOfYear, dayOfMonth - 1);
                        txt_day.setText(simpledateformat.format(date));
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
                break;
            case R.id.rl_todaydate:
                datePickerDialog = new DatePickerDialog(DateLogHistory.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        txt_enddate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                        todate = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;  //for pass in api
                        SimpleDateFormat simpledateformat = new SimpleDateFormat("EEEE"); //for day
                        Date date = new Date(year, monthOfYear, dayOfMonth - 1);
                        txt_day1.setText(simpledateformat.format(date));
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
                break;
            case R.id.btn_submit:
                if (fromdate.equals("")) {
                    Utility.displayToast(getApplicationContext(), "Please Select Fromdate");
                } else if (todate.equals("")) {
                    Utility.displayToast(getApplicationContext(), "Please Select Todate");
                } else {
                    callDateLogApi();
                }
                break;
        }
    }

    private void callDateLogApi() {

        avi.show();
        Observable<sitelogModel> responseObservable = apiservice.get_dateLogHistory(sessionManager.getKeyId(), fromdate, todate);
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
                .subscribe(new Observer<sitelogModel>() {
                    @Override
                    public void onCompleted() {
                        avi.hide();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("error", "" + e.getMessage());
                    }

                    @Override
                    public void onNext(sitelogModel model) {
                        avi.hide();
                        statusCode = model.getStatusCode();
                        if (statusCode == 200) {

                            card_reports.setVisibility(View.VISIBLE);
                            card_totalhrs.setVisibility(View.VISIBLE);
                            txt_daystotal.setText(model.getTotal_days());
                            txt_totalhrs.setText(model.getTotal_work_hrs());

                            if (!model.getTime_log_detailed_csv().equals("")) {
                                btn_csv.setVisibility(View.VISIBLE);
                                btn_csv.setOnClickListener(view -> {
                                    String csv_url = model.getTime_log_detailed_csv();
                                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(csv_url));
                                    startActivity(browserIntent);
                                });
                            } else {
                                btn_csv.setVisibility(View.GONE);
                            }
                            adapter = new SubConDetailsAdapter(DateLogHistory.this, model.getData());
                            rv_contctrdetails.setAdapter(adapter);
                        } else if (statusCode == 404) {
                            card_reports.setVisibility(View.GONE);
                            card_totalhrs.setVisibility(View.GONE);
                            btn_csv.setVisibility(View.GONE);
                            Utility.displayToast(getApplicationContext(), model.getMessage());
                        }
                    }
                });
    }
}