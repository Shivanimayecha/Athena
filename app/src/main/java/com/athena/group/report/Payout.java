package com.athena.group.report;

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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.athena.group.API.ApiInterface;
import com.athena.group.API.ApiServiceCreator;
import com.athena.group.Model.HoursModel;
import com.athena.group.Model.SiteContractorModel;
import com.athena.group.Model.sitelogModel;
import com.athena.group.R;
import com.athena.group.adapter.ConPayrateAdapter;
import com.athena.group.application.SessionManager;
import com.athena.group.application.Utility;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;
import com.wang.avi.AVLoadingIndicatorView;

import java.net.SocketTimeoutException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Observer;
import rx.schedulers.Schedulers;

public class Payout extends Activity implements View.OnClickListener {

    @BindView(R.id.spn_contractor)
    SearchableSpinner spn_contractor;
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
    @BindView(R.id.txt_mny)
    TextView txt_mny;
    @BindView(R.id.txt_charge)
    TextView txt_charge;
    @BindView(R.id.cardweek)
    CardView cardweek;
    @BindView(R.id.card_hrsdaymny)
    CardView card_hrsdaymny;
    /*@BindView(R.id.txt_weekbtn)
    TextView txt_weekbtn;
    @BindView(R.id.txt_mnthbtn)
    TextView txt_mnthbtn;*/
    @BindView(R.id.weekly)
    TextView weekly;
    @BindView(R.id.txt_wkmnth_hrs)
    TextView txt_wkmnth_hrs;
    @BindView(R.id.rv_contctrdetails)
    RecyclerView rv_contctrdetails;
    @BindView(R.id.card_reports)
    CardView card_reports;
    @BindView(R.id.btn_csv)
    Button btn_csv;
    int statusCode;
    final Calendar myCalendar = Calendar.getInstance();
    private String fromdate = "", todate = "";
    String contractor = "", c_id = "";
    List<SiteContractorModel.Data> data;
    ApiInterface apiservice;
    SessionManager sessionManager;
    DatePickerDialog datePickerDialog;
    ConPayrateAdapter adapter;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_payout);
        ButterKnife.bind(this);
        sessionManager = new SessionManager(Payout.this);
        apiservice = ApiServiceCreator.createService("latest");
        toolbar_Title.setText(getString(R.string.payout));
        spn_contractor.setTitle("Select sub-contractor");
        initView();
    }

    private void initView() {

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

        getContractorName();
        spn_contractor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (spn_contractor.getSelectedItem() == "Select sub-contractor") {
                    contractor = "";
                } else {
                    contractor = spn_contractor.getSelectedItem().toString();
                    SiteContractorModel.Data datalist = data.get(i - 1);
                    c_id = datalist.getUserId();
                    weekHoursApi();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
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
                datePickerDialog = new DatePickerDialog(Payout.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        txt_fromdate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                        fromdate = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year; //for pass in api
                        SimpleDateFormat simpledateformat = new SimpleDateFormat("EEEE"); //for day
                        Date date = new Date(year, monthOfYear, dayOfMonth - 1);
                        txt_day.setText(simpledateformat.format(date));
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
                break;
            case R.id.rl_todaydate:
                datePickerDialog = new DatePickerDialog(Payout.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        txt_enddate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                        todate = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;   //for pass in api
                        SimpleDateFormat simpledateformat = new SimpleDateFormat("EEEE"); //for day
                        Date date = new Date(year, monthOfYear, dayOfMonth - 1);
                        txt_day1.setText(simpledateformat.format(date));
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
                break;
            case R.id.btn_submit:
                if (c_id.equals("")) {
                    Utility.displayToast(getApplicationContext(), "Please Select Contractor");
                } else if (fromdate.equals("")) {
                    Utility.displayToast(getApplicationContext(), "Please Select Fromdate");
                } else if (todate.equals("")) {
                    Utility.displayToast(getApplicationContext(), "Please Select Todate");
                } else {
                    callContractorlogApi();
                }
                break;
        }
    }

    private void getContractorName() {

        ArrayList<String> contractorarray = new ArrayList<>();
        avi.show();
        Observable<SiteContractorModel> responseObservable = apiservice.get_allSub_contractor();
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
                .subscribe(new Observer<SiteContractorModel>() {
                    @Override
                    public void onCompleted() {
                        avi.hide();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("error", "" + e.getMessage());
                    }

                    @Override
                    public void onNext(SiteContractorModel model) {
                        avi.hide();

                        statusCode = model.getStatusCode();
                        if (statusCode == 200) {
                            data = model.getData();
                            for (int i = 0; i < model.getData().size(); i++) {
                                contractorarray.add(model.getData().get(i).getName()+" "+model.getData().get(i).getLname());
                            }
                        } else {
                            Utility.displayToast(getApplicationContext(), model.getMessage());
                        }
                    }
                });

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, contractorarray);
        dataAdapter.add("Select sub-contractor");
        dataAdapter.setDropDownViewResource(R.layout.spinner_item);
        spn_contractor.setAdapter(dataAdapter);
    }

    private void callContractorlogApi() {

        avi.show();
        Observable<sitelogModel> responseObservable = apiservice.get_subcontactorloghistory(sessionManager.getKeyId(), fromdate, todate, c_id);
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
                            card_hrsdaymny.setVisibility(View.VISIBLE);

                            txt_daystotal.setText(model.getTotal_days());
                            txt_totalhrs.setText(model.getTotal_work_hrs());
                            txt_mny.setText(model.getTotal_payment());
                            txt_charge.setText(model.getContractor_charges() + "/Hr.");

                        } else if (statusCode == 404) {
                            card_hrsdaymny.setVisibility(View.GONE);
                            Utility.displayToast(getApplicationContext(), model.getMessage());
                        }
                    }
                });
    }

    private void weekHoursApi() {

        rv_contctrdetails.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        rv_contctrdetails.setLayoutManager(layoutManager);
        cardweek.setVisibility(View.VISIBLE);
        avi.show();
        Observable<HoursModel> responseObservable = apiservice.get_conWeekHours(c_id);
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
                .subscribe(new Observer<HoursModel>() {
                    @Override
                    public void onCompleted() {
                        avi.hide();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("error", "" + e.getMessage());
                    }

                    @Override
                    public void onNext(HoursModel model) {
                        avi.hide();
                        statusCode = model.getStatusCode();

                        if (statusCode == 200) {
                            txt_wkmnth_hrs.setText("Total Week Hrs : " + model.getData() + " Hrs.");
                            weekly.setText("Weekly");

                            if (!model.getCsv_path().equals("")) {
                                btn_csv.setVisibility(View.VISIBLE);
                                btn_csv.setOnClickListener(view -> {
                                    String csv_url = model.getCsv_path();
                                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(csv_url));
                                    startActivity(browserIntent);
                                });
                            } else {
                                btn_csv.setVisibility(View.GONE);
                            }

                            if (model.getContimelogDetail().size() != 0) {
                                card_reports.setVisibility(View.VISIBLE);
                                adapter = new ConPayrateAdapter(getApplicationContext(), model.getContimelogDetail());
                                rv_contctrdetails.setAdapter(adapter);
                            } else {
                                card_reports.setVisibility(View.GONE);
                            }
                            /*txt_weekbtn.setBackground(getDrawable(R.drawable.weekbtn));
                            txt_mnthbtn.setBackground(getDrawable(R.drawable.monthgraybtn));*/
                        } else if (statusCode == 404) {
                            txt_wkmnth_hrs.setText("Total Week Hrs :- 00:00 Hrs.");
                            weekly.setText("Weekly");
                            Utility.displayToast(getApplicationContext(), "Week Hrs not found");

                            if (!model.getCsv_path().equals("")) {
                                btn_csv.setVisibility(View.VISIBLE);
                                btn_csv.setOnClickListener(view -> {
                                    String csv_url = model.getCsv_path();
                                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(csv_url));
                                    startActivity(browserIntent);
                                });
                            } else {
                                btn_csv.setVisibility(View.GONE);
                            }

                            if (model.getContimelogDetail().size() != 0) {
                                card_reports.setVisibility(View.VISIBLE);
                                adapter = new ConPayrateAdapter(getApplicationContext(), model.getContimelogDetail());
                                rv_contctrdetails.setAdapter(adapter);
                            } else {
                                card_reports.setVisibility(View.GONE);
                            }
                        }
                    }
                });
    }
}