package com.athena.group.Truck__Driver_Panel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import com.athena.group.API.ApiInterface;
import com.athena.group.API.ApiServiceCreator;
import com.athena.group.Model.OrderDetailsTruckModel;
import com.athena.group.Model.OrderStatusModel;
import com.athena.group.Model.SpinnerModel;
import com.athena.group.R;
import com.athena.group.adapter.AssignOrderAdapter;
import com.athena.group.application.SessionManager;
import com.athena.group.application.Utility;
import com.wang.avi.AVLoadingIndicatorView;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Observer;
import rx.schedulers.Schedulers;

public class AssignOrderActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.toolbar_Title)
    TextView toolbar_Title;
    @BindView(R.id.txt_dummy)
    TextView txt_dummy;
    @BindView(R.id.back_icon)
    ImageView back_icon;
    @BindView(R.id.avi)
    AVLoadingIndicatorView avi;
    /*@BindView(R.id.spn_site)
    Spinner spn_site;*/
    /*@BindView(R.id.spn_orders)
    Spinner spn_orders;*/
    @BindView(R.id.rv_orderlist)
    RecyclerView rv_orderlist;
    @BindView(R.id.txt_date)
    TextView txt_date;
    /*@BindView(R.id.btn_add)
    Button btn_add;*/
    int statusCode;
    List<SpinnerModel.Data> data;
    List<OrderStatusModel.Data> data1;
    String order = "", o_id = "";
    //    site = "", s_id = "",
    ArrayList<String> sitearray = new ArrayList<>();
    ArrayList<String> orderarray = new ArrayList<>();
    SessionManager sessionManager;
    ApiInterface apiservice;
    AssignOrderAdapter adapter;
    RecyclerView.LayoutManager layoutManager;
    public static Handler handler = null;
    private String txtdate = "";
    final Calendar myCalendar = Calendar.getInstance();
    private List<OrderDetailsTruckModel.Data> currentSelectedItems = new ArrayList<>();
    DatePickerDialog datePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_assign_order);
        ButterKnife.bind(this);
        sessionManager = new SessionManager(AssignOrderActivity.this);
        apiservice = ApiServiceCreator.createService("latest");
        toolbar_Title.setText("Assign Orders");
        Utility.getAppcon().getSession().screen_name = "AssignOrder";
        txt_date.setText(Utility.date);
        txtdate = Utility.date;
        checkHandller();
        callAssignOrderApi();

        back_icon.setOnClickListener(this);
        txt_date.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.back_icon:
                finish();
                break;
            case R.id.txt_date:
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                datePickerDialog = new DatePickerDialog(AssignOrderActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                txt_date.setText(dayOfMonth + "-"
                                        + (monthOfYear + 1) + "-" + year);
                                txtdate = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;    //for pass in api
                                callAssignOrderApi();
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
                break;
        }
    }

    @SuppressLint("HandlerLeak")
    public void checkHandller() {
        handler = new Handler() {
            @Override
            public void handleMessage(Message message) {
                if (message == null) {

                }
                if (message.what == 100) {
                    callAssignOrderApi();
                }
            }
        };
    }

    private void callAssignOrderApi() {

        rv_orderlist.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        rv_orderlist.setLayoutManager(layoutManager);
        avi.show();
        Observable<OrderDetailsTruckModel> responseObservable = apiservice.get_TruckassignOrder(sessionManager.getKeyId(), txtdate);
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
                .subscribe(new Observer<OrderDetailsTruckModel>() {
                    @Override
                    public void onCompleted() {
                        avi.hide();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("error", "" + e.getMessage());
                    }

                    @Override
                    public void onNext(OrderDetailsTruckModel model) {
                        avi.hide();

                        //if (model.getData().size() > 0) {
                        statusCode = model.getStatusCode();
                        if (statusCode == 200) {
                            rv_orderlist.setVisibility(View.VISIBLE);
                            //btn_add.setVisibility(View.VISIBLE);
                            Utility.displayToast(getApplicationContext(), model.getMessage());
                            adapter = new AssignOrderAdapter(AssignOrderActivity.this, model.getData());
                            rv_orderlist.setAdapter(adapter);
                        } else if (statusCode == 404) {
                            rv_orderlist.setVisibility(View.GONE);
                            //btn_add.setVisibility(View.GONE);
                            Utility.displayToast(getApplicationContext(), model.getMessage());
                        }
                        /*} else {
                            txt_dummy.setText(View.VISIBLE);
                        }*/
                    }
                });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

}