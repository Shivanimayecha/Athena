/*
package com.group.group.Truck__Driver_Panel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
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

import com.group.group.API.ApiInterface;
import com.group.group.API.ApiServiceCreator;
import com.group.group.Model.OrderDetailsModel;
import com.group.group.Model.OrderDetailsTruckModel;
import com.group.group.Model.OrderStatusModel;
import com.group.group.Model.SpinnerModel;
import com.group.group.R;
import com.group.group.adapter.AssignOrderAdapter;
import com.group.group.adapter.OrderDetialsAdapter;
import com.group.group.application.SessionManager;
import com.group.group.application.Utility;
import com.wang.avi.AVLoadingIndicatorView;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Observer;
import rx.schedulers.Schedulers;

public class TodaysOrderActivity extends AppCompatActivity {

    @BindView(R.id.toolbar_Title)
    TextView toolbar_Title;
    @BindView(R.id.txt_dummy)
    TextView txt_dummy;
    @BindView(R.id.back_icon)
    ImageView back_icon;
    @BindView(R.id.avi)
    AVLoadingIndicatorView avi;
   */
/* @BindView(R.id.spn_site)
    Spinner spn_site;*//*


    @BindView(R.id.rv_orderlist)
    RecyclerView rv_orderlist;
    int statusCode;
    List<SpinnerModel.Data> data;
    List<OrderStatusModel.Data> data1;
    //String site = "", s_id = "", order = "", o_id = "";
    ArrayList<String> sitearray = new ArrayList<>();
    ArrayList<String> orderarray = new ArrayList<>();
    SessionManager sessionManager;
    ApiInterface apiservice;
    AssignOrderAdapter adapter;
    RecyclerView.LayoutManager layoutManager;
    public static Handler handler = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_todays_order);
        ButterKnife.bind(this);

        sessionManager = new SessionManager(TodaysOrderActivity.this);
        apiservice = ApiServiceCreator.createService("latest");

        toolbar_Title.setText("Today Order");

        Utility.getAppcon().getSession().screen_name = "TodayOrder";

        rv_orderlist.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        rv_orderlist.setLayoutManager(layoutManager);

        checkHandller();
        back_icon.setOnClickListener(view ->
        {
            finish();
        });

        callOrderHistoryApi();

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


    private void callOrderHistoryApi() {

        avi.show();

        Observable<OrderDetailsTruckModel> responseObservable = apiservice.get_truckTodaysOrder(
                sessionManager.getKeyId());

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
                        //getAttributename1(p_id);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("error", "" + e.getMessage());
                    }

                    @Override
                    public void onNext(OrderDetailsTruckModel model) {
                        avi.hide();

                        if (model.getData().size() > 0) {
                            statusCode = model.getStatusCode();
                            if (statusCode == 200) {
                                rv_orderlist.setVisibility(View.VISIBLE);
                                Utility.displayToast(getApplicationContext(), model.getMessage());
                                adapter = new AssignOrderAdapter(TodaysOrderActivity.this, model.getData());
                                rv_orderlist.setAdapter(adapter);
                            } else if (statusCode == 404) {
                                rv_orderlist.setVisibility(View.GONE);
                                Utility.displayToast(getApplicationContext(), model.getMessage());
                            }
                        } else {
                            txt_dummy.setVisibility(View.VISIBLE);
                        }
                    }
                });


    }

    private void getSiteName() {

        avi.show();
        Observable<SpinnerModel> responseObservable = apiservice.get_truckDriver_site(sessionManager.getKeyId());

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
                                sitearray.add(model.getData().get(i).getSiteName());
                            }
                        }
                    }
                });

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, sitearray);
        dataAdapter.add("Select site");
        dataAdapter.setDropDownViewResource(R.layout.spinner_item);
        //  spn_site.setAdapter(dataAdapter);

    }

}
*/
