package com.athena.group.MobilePlant;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.athena.group.API.ApiInterface;
import com.athena.group.API.ApiServiceCreator;
import com.athena.group.Model.MobilePlantModel;
import com.athena.group.Model.Model;
import com.athena.group.Model.QuestickModel;
import com.athena.group.Model.RecmndedModel;
import com.athena.group.R;
import com.athena.group.adapter.MblQuestickAdapter;
import com.athena.group.adapter.MblRecmndedAdapter;
import com.athena.group.application.SessionManager;
import com.athena.group.application.Utility;
import com.wang.avi.AVLoadingIndicatorView;

import java.net.SocketTimeoutException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Observer;
import rx.schedulers.Schedulers;

import static com.athena.group.adapter.MblRecmndedAdapter.questiondata_mbls;


public class MblChckNxtActivity extends AppCompatActivity {

    @BindView(R.id.toolbar_Title)
    TextView toolbar_Title;
    @BindView(R.id.back_icon)
    ImageView back_icon;
    @BindView(R.id.avi)
    AVLoadingIndicatorView avi;
    @BindView(R.id.rv_questick_list)
    RecyclerView rv_questick_list;
    @BindView(R.id.rv_rcmnded_list)
    RecyclerView rv_rcmnded_list;
    @BindView(R.id.btn_next)
    LinearLayout btn_next;
    @BindView(R.id.img_clndr)
    ImageView img_clndr;
    @BindView(R.id.txt_date)
    TextView txt_date;
    @BindView(R.id.txt_day)
    TextView txt_day;
    String txtdate, txtday;
    ApiInterface apiservice;
    int statusCode;
    SessionManager sessionManager;
    RecyclerView.LayoutManager layoutManager;
    DatePickerDialog datePickerDialog;

    MblRecmndedAdapter adapter;
    MblQuestickAdapter mAdapter;
    MobilePlantModel mpm;

    int top_questionsize = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_mbl_chck_nxt);
        ButterKnife.bind(this);
        sessionManager = new SessionManager(MblChckNxtActivity.this);
        apiservice = ApiServiceCreator.createService("latest");
        toolbar_Title.setText(getString(R.string.mblplntchecklist));
        back_icon.setOnClickListener(view -> finish());

        initView();
    }

    private void initView() {

        questiondata_mbls = new ArrayList<>();
        questiondata_mbls.clear();

        getRecmndedListApi();
        getQuestickListApi();
        btn_next.setOnClickListener(view -> {

            mpm = new MobilePlantModel();
            mpm.setAns_user_id(sessionManager.getKeyId());
            mpm.setLast_step(Utility.getAppcon().getSession().mbl_last_step_val);
            mpm.setCreatedDate(txtdate);
            mpm.setAns_day(txtday);
            mpm.setQuestiondata_mbl(questiondata_mbls);

            callNextApi();
        });

        txt_date.setText(Utility.date);
        txtdate = Utility.date;
        txt_day.setText(Utility.day);

       /* SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        Date d = new Date();
        txt_day.setText(sdf.format(d));*/

        SimpleDateFormat sdf1 = new SimpleDateFormat("EEE"); // for pass in api
        Date d1 = new Date();
        txtday = sdf1.format(d1);

        img_clndr.setOnClickListener(view -> {

            // calender class's instance and get current date , month and year from calender
            final Calendar c = Calendar.getInstance();
            int mYear = c.get(Calendar.YEAR); // current year
            int mMonth = c.get(Calendar.MONTH); // current month
            int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
            // date picker dialog
            datePickerDialog = new DatePickerDialog(MblChckNxtActivity.this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {
                            // set day of month , month and year value in the edit text
                            txt_date.setText(dayOfMonth + "-"
                                    + (monthOfYear + 1) + "-" + year);
                            txtdate = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;

                            //for day
                            SimpleDateFormat simpledateformat = new SimpleDateFormat("EEEE");
                            Date date = new Date(year, monthOfYear, dayOfMonth - 1);
                            txt_day.setText(simpledateformat.format(date));

                            SimpleDateFormat simpledateformat1 = new SimpleDateFormat("EEE"); // for pass in api
                            Date date1 = new Date(year, monthOfYear, dayOfMonth - 1);
                            txtday = simpledateformat1.format(date1);
                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        });
    }

    private void callNextApi() {

        avi.show();

        Observable<Model> responseObservable = apiservice.insert_mblSecondStep(mpm);

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
                .subscribe(new Observer<Model>() {
                    @Override
                    public void onCompleted() {
                        avi.hide();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("error", "" + e.getMessage());
                    }

                    @Override
                    public void onNext(Model model) {
                        avi.hide();

                        //if (model.getData().size() > 0) {
                        statusCode = model.getStatusCode();
                        if (statusCode == 200) {
                            Utility.displayToast(getApplicationContext(), model.getMessage());
                            Intent intent = new Intent(MblChckNxtActivity.this, MblFinalActivity.class);
                            startActivity(intent);
                        } else if (statusCode == 404) {
                            Utility.displayToast(getApplicationContext(), model.getMessage());
                        }
                    }
                });
    }

    private void getRecmndedListApi() {

        rv_rcmnded_list.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        rv_rcmnded_list.setLayoutManager(layoutManager);
        avi.show();

        Observable<RecmndedModel> responseObservable = apiservice.recommended_mobilePlantQuestion();

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
                .subscribe(new Observer<RecmndedModel>() {
                    @Override
                    public void onCompleted() {
                        avi.hide();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("error", "" + e.getMessage());
                    }

                    @Override
                    public void onNext(RecmndedModel model) {
                        avi.hide();

                        //if (model.getData().size() > 0) {
                        statusCode = model.getStatusCode();
                        if (statusCode == 200) {

                            top_questionsize = model.getData().size();
                            Log.e("Size is ", String.valueOf(top_questionsize));
                            rv_rcmnded_list.setVisibility(View.VISIBLE);
                            Utility.displayToast(getApplicationContext(), model.getMessage());
                            adapter = new MblRecmndedAdapter(MblChckNxtActivity.this, model.getData());
                            rv_rcmnded_list.setAdapter(adapter);
                        } else if (statusCode == 404) {
                            rv_rcmnded_list.setVisibility(View.GONE);
                            Utility.displayToast(getApplicationContext(), model.getMessage());
                        }
                    }
                });
    }

    private void getQuestickListApi() {
        rv_questick_list.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        rv_questick_list.setLayoutManager(layoutManager);
        avi.show();

        Observable<QuestickModel> responseObservable = apiservice.get_mobilePlantQuestion();

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
                .subscribe(new Observer<QuestickModel>() {
                    @Override
                    public void onCompleted() {
                        avi.hide();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("error", "" + e.getMessage());
                    }

                    @Override
                    public void onNext(QuestickModel model) {
                        avi.hide();
                        //if (model.getData().size() > 0) {
                        statusCode = model.getStatusCode();
                        if (statusCode == 200) {
                            rv_questick_list.setVisibility(View.VISIBLE);
                            Utility.displayToast(getApplicationContext(), model.getMessage());
                            mAdapter = new MblQuestickAdapter(MblChckNxtActivity.this, model.getData(), top_questionsize);
                            rv_questick_list.setAdapter(mAdapter);
                        } else if (statusCode == 404) {
                            rv_questick_list.setVisibility(View.GONE);
                            Utility.displayToast(getApplicationContext(), model.getMessage());
                        }
                    }
                });
    }

}
