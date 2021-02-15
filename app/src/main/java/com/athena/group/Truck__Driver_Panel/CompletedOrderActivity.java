package com.athena.group.Truck__Driver_Panel;

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
import android.widget.ImageView;
import android.widget.TextView;

import com.athena.group.API.ApiInterface;
import com.athena.group.API.ApiServiceCreator;
import com.athena.group.Model.OrderDetailsTruckModel;
import com.athena.group.R;
import com.athena.group.adapter.CompletOrderAdapter;
import com.athena.group.application.SessionManager;
import com.athena.group.application.Utility;
import com.wang.avi.AVLoadingIndicatorView;

import java.net.SocketTimeoutException;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Observer;
import rx.schedulers.Schedulers;

public class CompletedOrderActivity extends AppCompatActivity {

    @BindView(R.id.toolbar_Title)
    TextView toolbar_Title;
    @BindView(R.id.txt_dummy)
    TextView txt_dummy;
    @BindView(R.id.back_icon)
    ImageView back_icon;
    @BindView(R.id.avi)
    AVLoadingIndicatorView avi;
    @BindView(R.id.rv_orderlist)
    RecyclerView rv_orderlist;
    int statusCode;
    SessionManager sessionManager;
    ApiInterface apiservice;
    //AssignOrderAdapter adapter;
    CompletOrderAdapter adapter;
    RecyclerView.LayoutManager layoutManager;
    public static Handler handler = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_completed_order);
        ButterKnife.bind(this);

        sessionManager = new SessionManager(CompletedOrderActivity.this);
        apiservice = ApiServiceCreator.createService("latest");

        toolbar_Title.setText(getString(R.string.completeorder));

        Utility.getAppcon().getSession().screen_name = "CompletedOrder";

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
            }
        };
    }

    private void callOrderHistoryApi() {

        avi.show();
        Observable<OrderDetailsTruckModel> responseObservable = apiservice.get_cmpltdorder(
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

                        statusCode = model.getStatusCode();
                        if (model.getData().size() > 0) {
                            if (statusCode == 200) {
                                rv_orderlist.setVisibility(View.VISIBLE);
                                Utility.displayToast(getApplicationContext(), model.getMessage());
                                adapter = new CompletOrderAdapter(CompletedOrderActivity.this, model.getData());
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
}
