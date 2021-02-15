package com.athena.group.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.athena.group.API.ApiInterface;
import com.athena.group.API.ApiServiceCreator;
import com.athena.group.Model.NotificationModel;
import com.athena.group.R;
import com.athena.group.adapter.NotificationAdapter;
import com.athena.group.application.SessionManager;
import com.wang.avi.AVLoadingIndicatorView;

import java.net.SocketTimeoutException;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Observer;
import rx.schedulers.Schedulers;

public class NotificationActivity extends AppCompatActivity {


    @BindView(R.id.toolbar_Title)
    TextView toolbar_Title;
    @BindView(R.id.back_icon)
    ImageView back_icon;
    @BindView(R.id.rv_notilist)
    RecyclerView rv_notilist;
    @BindView(R.id.avi)
    AVLoadingIndicatorView avi;
    SessionManager sessionManager;
    ApiInterface apiInterface;
    RecyclerView.LayoutManager layoutManager;
    int statusCode;
    NotificationAdapter notificationAdapter;
    public static Handler handler = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_notification);
        ButterKnife.bind(this);
        sessionManager = new SessionManager(NotificationActivity.this);
        apiInterface = ApiServiceCreator.createService("latest");
        toolbar_Title.setText("All Notification");
        back_icon.setOnClickListener(view -> finish());
        checkHandller();
        getNotification();
    }

    @SuppressLint("HandlerLeak")
    public void checkHandller() {
        handler = new Handler() {
            @Override
            public void handleMessage(Message message) {
                if (message == null) {

                }
                if (message.what == 100) {
                    getNotification();
                }
            }
        };
    }

    public void getNotification() {

        rv_notilist.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        rv_notilist.setLayoutManager(layoutManager);

        avi.show();
        Observable<NotificationModel> responseObservable = apiInterface.show_notification(sessionManager.getKeyId());
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
                .subscribe(new Observer<NotificationModel>() {
                    @Override
                    public void onCompleted() {
                        avi.hide();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("error", "" + e.getMessage());
                    }

                    @Override
                    public void onNext(NotificationModel notificationModel) {
                        statusCode = notificationModel.getStatusCode();
                        if (statusCode == 200) {
                            notificationAdapter = new NotificationAdapter(NotificationActivity.this, notificationModel.getData());
                            rv_notilist.setAdapter(notificationAdapter);
                        }
                    }
                });
    }
}