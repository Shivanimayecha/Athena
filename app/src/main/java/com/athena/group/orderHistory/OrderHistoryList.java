package com.athena.group.orderHistory;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.athena.group.API.ApiInterface;
import com.athena.group.API.ApiServiceCreator;
import com.athena.group.Model.OrderDetailsModel;
import com.athena.group.Model.OrderStatusModel;
import com.athena.group.Model.SpinnerModel;
import com.athena.group.R;
import com.athena.group.adapter.OrderDetialsAdapter;
import com.athena.group.application.SessionManager;
import com.athena.group.application.Utility;
import com.wang.avi.AVLoadingIndicatorView;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Observer;
import rx.schedulers.Schedulers;

public class OrderHistoryList extends Activity {

    @BindView(R.id.toolbar_Title)
    TextView toolbar_Title;
    @BindView(R.id.back_icon)
    ImageView back_icon;
    @BindView(R.id.avi)
    AVLoadingIndicatorView avi;
    @BindView(R.id.spn_site)
    Spinner spn_site;
    @BindView(R.id.rv_orderlist)
    RecyclerView rv_orderlist;
    int statusCode;
    List<SpinnerModel.Data> data;
    List<OrderStatusModel.Data> data1;
    String site = "", s_id = "", order = "", o_id = "";
    ArrayList<String> sitearray = new ArrayList<>();
    ArrayList<String> orderarray = new ArrayList<>();
    SessionManager sessionManager;
    ApiInterface apiservice;
    OrderDetialsAdapter adapter;
    RecyclerView.LayoutManager layoutManager;
    public static Handler handler = null;
    ArrayAdapter<String> siteAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_order_historylist);
        ButterKnife.bind(this);
        sessionManager = new SessionManager(OrderHistoryList.this);
        apiservice = ApiServiceCreator.createService("latest");
        toolbar_Title.setText("Completed Orders");
        Utility.getAppcon().getSession().screen_name = "OderHistoryList";

        ArrayList<Integer> integerList = new ArrayList<Integer>();
        integerList.add(1);

        checkHandller();
        initViews();
    }

    private void initViews() {
        back_icon.setOnClickListener(view -> finish());

        rv_orderlist.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        rv_orderlist.setLayoutManager(layoutManager);

        s_id = getIntent().getStringExtra("site");
        Log.e("TAG", "initViews: site--" + s_id);

        getSiteName();
        spn_site.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (spn_site.getSelectedItem() == "Select site") {
                    site = "";
                } else {
                    site = spn_site.getSelectedItem().toString();
                    SpinnerModel.Data datalist = data.get(i - 1);
                    s_id = datalist.getSiteId();
                    String[] separated = site.split(",");
                    //contract_no=separated[0];
                    site = separated[1];
                    Log.e("Sitename is ", site);
                    callOrderHistoryApi();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    @SuppressLint("HandlerLeak")
    public void checkHandller() {
        handler = new Handler() {
            @Override
            public void handleMessage(Message message) {
                if (message == null) {

                }
                if (message.what == 100) {
                    callOrderHistoryApi();
                }
                //return false;
            }
        };
    }


    private void getStatusOrderHistoryApi() {

        avi.show();
        Observable<OrderDetailsModel> responseObservable = apiservice.get_statusOrderHistory(s_id, o_id);
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
                        avi.hide();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("error", "" + e.getMessage());
                    }

                    @Override
                    public void onNext(OrderDetailsModel model) {
                        avi.hide();

                        statusCode = model.getStatusCode();
                        if (statusCode == 200) {
                            rv_orderlist.setVisibility(View.VISIBLE);
                            Utility.displayToast(getApplicationContext(), model.getMessage());
                            adapter = new OrderDetialsAdapter(OrderHistoryList.this, model.getData());
                            rv_orderlist.setAdapter(adapter);
                        } else if (statusCode == 404) {
                            rv_orderlist.setVisibility(View.GONE);
                            Utility.displayToast(getApplicationContext(), model.getMessage());
                        }
                    }
                });
    }

    private void callOrderHistoryApi() {

        avi.show();
        Observable<OrderDetailsModel> responseObservable = apiservice.get_allOrderHistory(s_id);
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
                        avi.hide();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("error", "" + e.getMessage());
                    }

                    @Override
                    public void onNext(OrderDetailsModel model) {
                        avi.hide();

                        statusCode = model.getStatusCode();
                        if (statusCode == 200) {
                            rv_orderlist.setVisibility(View.VISIBLE);
                            Utility.displayToast(getApplicationContext(), model.getMessage());
                            adapter = new OrderDetialsAdapter(OrderHistoryList.this, model.getData());
                            rv_orderlist.setAdapter(adapter);
                        } else if (statusCode == 404) {
                            rv_orderlist.setVisibility(View.GONE);
                            Utility.displayToast(getApplicationContext(), model.getMessage());
                        }
                    }
                });
    }

    private void getSiteName() {

        avi.show();
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
                        statusCode = model.getStatusCode();
                        if (statusCode == 200) {
                            data = model.getData();
                            for (int i = 0; i < model.getData().size(); i++) {
                                sitearray.add(model.getData().get(i).getSite_number() + " , " + model.getData().get(i).getSiteName());
                            }

                            siteAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item, sitearray);
                            //siteAdapter.add("Select site");
                            siteAdapter.insert("Select site", 0);
                            siteAdapter.setDropDownViewResource(R.layout.spinner_item);
                            spn_site.setAdapter(siteAdapter);

                            String compareValue = s_id;
                            Log.e("s_id", s_id);

                            if (compareValue != null) {
                                int spinnerPosition = siteAdapter.getPosition(compareValue);
                                Log.e("spinnerPosition", String.valueOf(spinnerPosition));
                                spn_site.setSelection(spinnerPosition);
                                Log.e("spinnerPosition", "" + spinnerPosition);
                            }
                        }
                    }
                });
    }
}