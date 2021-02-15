package com.athena.group.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Observer;
import rx.schedulers.Schedulers;

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

public class SignOrderActivity extends AppCompatActivity implements View.OnClickListener {
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
        setContentView(R.layout.activity_sign_order);
        ButterKnife.bind(this);
        sessionManager = new SessionManager(SignOrderActivity.this);
        apiservice = ApiServiceCreator.createService("latest");
        toolbar_Title.setText("All Assigned Orders");
        Utility.getAppcon().getSession().screen_name = "All_AssignOrder";
        txt_date.setText(Utility.date);
        txtdate = Utility.date;
        callAssignOrderApi();
        checkHandller();


        back_icon.setOnClickListener(this);
        txt_date.setOnClickListener(this);
    }


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
                datePickerDialog = new DatePickerDialog(SignOrderActivity.this,
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
        layoutManager = new LinearLayoutManager(SignOrderActivity.this);
        rv_orderlist.setLayoutManager(layoutManager);
        avi.show();
        Log.e("Method is ", "Call");
        Log.e("Key id", "" + sessionManager.getKeyId());
       /* msmpApi api= msmpApiclient.getApiClient().create(msmpApi.class);
        Call<OrderDetailsTruckModel> orderDetailsTruckModel1Call=api.all_assign_order("23");
        orderDetailsTruckModel1Call.enqueue(new Callback<OrderDetailsTruckModel>() {
            @Override
            public void onResponse(Call<OrderDetailsTruckModel> call, Response<OrderDetailsTruckModel> response) {

                Log.e("Message is ",response.body().getMessage());
                Log.e("Data ", String.valueOf(response.body().getStatusCode()));
            }

            @Override
            public void onFailure(Call<OrderDetailsTruckModel> call, Throwable t) {

                Log.e("error is ",t.getMessage());
            }
        });*/

        Observable<OrderDetailsTruckModel> responseObservable = apiservice.all_assign_order(sessionManager.getKeyId());
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
                        Log.e("On Complte Method", "Ok000");
                        avi.hide();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("On Error Method", "Ok000");
                        Log.e("error", "" + e.getMessage());
                    }

                    @Override
                    public void onNext(OrderDetailsTruckModel model) {

                        Log.e("Message is", model.getMessage());
                        Log.e("statuds", String.valueOf(model.getStatusCode()));
                        avi.hide();
                        //if (model.getData().size() > 0) {
                        statusCode = model.getStatusCode();
                        if (statusCode == 200) {
                            rv_orderlist.setVisibility(View.VISIBLE);
                            //btn_add.setVisibility(View.VISIBLE);
                            Utility.displayToast(getApplicationContext(), model.getMessage());
                            adapter = new AssignOrderAdapter(SignOrderActivity.this, model.getData());
                            rv_orderlist.setAdapter(adapter);
                        } else if (statusCode == 404) {
                            rv_orderlist.setVisibility(View.GONE);
                            //btn_add.setVisibility(View.GONE);
                            Utility.displayToast(getApplicationContext(), model.getMessage());
                        }
                        // }
                    }
                });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

}