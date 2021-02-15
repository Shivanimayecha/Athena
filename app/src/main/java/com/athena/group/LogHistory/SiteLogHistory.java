package com.athena.group.LogHistory;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.DatePickerDialog;
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
import android.widget.Spinner;
import android.widget.TextView;

import com.athena.group.API.ApiInterface;
import com.athena.group.API.ApiServiceCreator;
import com.athena.group.Model.HoursModel;
import com.athena.group.Model.SpinnerModel;
import com.athena.group.Model.sitelogModel;
import com.athena.group.R;
import com.athena.group.adapter.SiteTimeLogAdapter;
import com.athena.group.application.SessionManager;
import com.athena.group.application.Utility;
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

public class SiteLogHistory extends Activity implements View.OnClickListener {

    @BindView(R.id.card_totalhrs)
    CardView card_totalhrs;
    @BindView(R.id.card_contractorhrs)
    CardView card_contractorhrs;
    @BindView(R.id.cardweek)
    CardView cardweek;
    @BindView(R.id.spn_site)
    Spinner spn_site;
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
    @BindView(R.id.txt_totalhours)
    TextView txt_totalhours;
    @BindView(R.id.txt_weekbtn)
    TextView txt_weekbtn;
    @BindView(R.id.txt_mnthbtn)
    TextView txt_mnthbtn;
    @BindView(R.id.weekly)
    TextView weekly;
    @BindView(R.id.txt_wkmnth_hrs)
    TextView txt_wkmnth_hrs;
    @BindView(R.id.btn_submit)
    Button btn_submit;
    @BindView(R.id.rv_contrctrhrs)
    RecyclerView rv_contrctrhrs;
    ApiInterface apiservice;
    SessionManager sessionManager;
    String site = "", s_id = "";
    List<SpinnerModel.Data> data;
    int statusCode;
    final Calendar myCalendar = Calendar.getInstance();
    private String fromdate = "", todate = "";
    List<sitelogModel> arraylist;
    SiteTimeLogAdapter adapter;
    RecyclerView.LayoutManager layoutManager;
    DatePickerDialog datePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_site_loghistory);
        ButterKnife.bind(this);
        sessionManager = new SessionManager(SiteLogHistory.this);
        apiservice = ApiServiceCreator.createService("latest");
        toolbar_Title.setText("Site Log History ");
        back_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        initView();
    }

    private void initView() {

        rv_contrctrhrs.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        rv_contrctrhrs.setLayoutManager(layoutManager);
        card_totalhrs.setVisibility(View.GONE);
        card_contractorhrs.setVisibility(View.GONE);
        cardweek.setVisibility(View.GONE);

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
        txt_mnthbtn.setOnClickListener(this);
        txt_weekbtn.setOnClickListener(this);

        getSiteName();
        spn_site.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (spn_site.getSelectedItem() == "Select site") {
                    site = "";
                    //Do nothing.
                } else {
                    site = spn_site.getSelectedItem().toString();
                    String[] separated = site.split(",");
                    //contract_no=separated[0];
                    site = separated[1];
                    Log.e("Sitename is ", site);
                    SpinnerModel.Data datalist = data.get(i - 1);
                    s_id = datalist.getSiteId();
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
                datePickerDialog = new DatePickerDialog(SiteLogHistory.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        txt_fromdate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                        fromdate = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;  //for pass in api
                        SimpleDateFormat simpledateformat = new SimpleDateFormat("EEEE");   //for day
                        Date date = new Date(year, monthOfYear, dayOfMonth - 1);
                        txt_day.setText(simpledateformat.format(date));
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
                break;
            case R.id.rl_todaydate:
                datePickerDialog = new DatePickerDialog(SiteLogHistory.this, new DatePickerDialog.OnDateSetListener() {
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
            case R.id.txt_weekbtn:
                weekHoursApi();
                break;
            case R.id.txt_mnthbtn:
                mnthHoursApi();
                break;
            case R.id.btn_submit:
                if (s_id.equals("")) {
                    Utility.displayToast(getApplicationContext(), "Please Select Site");
                } else if (fromdate.equals("")) {
                    Utility.displayToast(getApplicationContext(), "Please Select Fromdate");
                } else if (todate.equals("")) {
                    Utility.displayToast(getApplicationContext(), "Please Select Todate");
                } else {
                    callSitelogApi();
                }
                break;
        }
    }

    private void getSiteName() {

        ArrayList<String> sitearray = new ArrayList<>();
        avi.show();
        //Change Shirish
        //Observable<SpinnerModel> responseObservable = apiservice.get_site();
        Observable<SpinnerModel> responseObservable = apiservice.get_userSite(sessionManager.getKeyId());
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
                .subscribe(new Observer<SpinnerModel>() {
                    @Override
                    public void onCompleted() {
                        avi.hide();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("error", "" + e.getMessage());
                    }

                    @Override
                    public void onNext(SpinnerModel model) {
                        avi.hide();
                        statusCode = model.getStatusCode();
                        if (statusCode == 200) {
                            data = model.getData();
                            for (int i = 0; i < model.getData().size(); i++) {
                                sitearray.add(model.getData().get(i).getSite_number() + " , " + model.getData().get(i).getSiteName());
                            }
                        }
                    }
                });

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, sitearray);
        dataAdapter.add("Select site");
        dataAdapter.setDropDownViewResource(R.layout.spinner_item);
        spn_site.setAdapter(dataAdapter);
    }

    private void callSitelogApi() {
        avi.show();
        Observable<sitelogModel> responseObservable = apiservice.get_siteloghistory(sessionManager.getKeyId(), fromdate, todate, s_id);
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
                            card_totalhrs.setVisibility(View.VISIBLE);
                            card_contractorhrs.setVisibility(View.VISIBLE);
                            txt_totalhours.setText(model.getSiteTotalHrs());

                            adapter = new SiteTimeLogAdapter(SiteLogHistory.this, model.getData());
                            rv_contrctrhrs.setAdapter(adapter);
                        } else if (statusCode == 404) {
                            card_totalhrs.setVisibility(View.GONE);
                            card_contractorhrs.setVisibility(View.GONE);
                            Utility.displayToast(getApplicationContext(), model.getMessage());
                        }
                    }
                });
    }

    private void weekHoursApi() {

        cardweek.setVisibility(View.VISIBLE);
        avi.show();
        Observable<HoursModel> responseObservable = apiservice.get_weekHours(s_id);
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
                            txt_wkmnth_hrs.setText("Total Week Hrs : " + model.getData() + " hrs.");
                            weekly.setText("Weekly");
                            txt_weekbtn.setBackground(getDrawable(R.drawable.weekbtn));
                            txt_mnthbtn.setBackground(getDrawable(R.drawable.monthgraybtn));
                        } else if (statusCode == 404) {
                            txt_wkmnth_hrs.setText("Total Week Hrs :- 00:00 hrs.");
                            weekly.setText("Weekly");
                            txt_weekbtn.setBackground(getDrawable(R.drawable.weekbtn));
                            txt_mnthbtn.setBackground(getDrawable(R.drawable.monthgraybtn));
                            Utility.displayToast(getApplicationContext(), model.getMessage());
                        }
                    }
                });
    }

    private void mnthHoursApi() {

        cardweek.setVisibility(View.VISIBLE);
        avi.show();
        Observable<HoursModel> responseObservable = apiservice.get_monthHours(s_id);
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
                            txt_wkmnth_hrs.setText("Total Month Hrs : " + model.getData() + " hrs.");
                            weekly.setText("Monthly");
                            txt_weekbtn.setBackground(getDrawable(R.drawable.weekgraybtn));
                            txt_mnthbtn.setBackground(getDrawable(R.drawable.monthbtn));
                        } else if (statusCode == 404) {
                            txt_wkmnth_hrs.setText("Total Month Hrs :- 00:00 hrs.");
                            weekly.setText("Monthly");
                            txt_weekbtn.setBackground(getDrawable(R.drawable.weekgraybtn));
                            txt_mnthbtn.setBackground(getDrawable(R.drawable.monthbtn));
                            Utility.displayToast(getApplicationContext(), model.getMessage());
                        }
                    }
                });
    }
}
