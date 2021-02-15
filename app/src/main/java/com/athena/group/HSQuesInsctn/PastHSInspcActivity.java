package com.athena.group.HSQuesInsctn;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.athena.group.API.ApiInterface;
import com.athena.group.API.ApiServiceCreator;
import com.athena.group.Model.PastHsModel;
import com.athena.group.R;
import com.athena.group.adapter.PastHSAdapter;
import com.athena.group.application.SessionManager;
import com.athena.group.application.Utility;
import com.wang.avi.AVLoadingIndicatorView;

import java.net.SocketTimeoutException;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Observer;
import rx.schedulers.Schedulers;

public class PastHSInspcActivity extends AppCompatActivity {

    SessionManager sessionManager;
    ApiInterface apiservice;
    @BindView(R.id.toolbar_Title)
    TextView toolbar_Title;
    @BindView(R.id.back_icon)
    ImageView back_icon;
    @BindView(R.id.avi)
    AVLoadingIndicatorView avi;
    @BindView(R.id.rv_list)
    RecyclerView rv_list;
    RecyclerView.LayoutManager layoutManager;
    int statusCode;
    PastHSAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_past_hsinspc);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ButterKnife.bind(this);
        sessionManager = new SessionManager(PastHSInspcActivity.this);
        apiservice = ApiServiceCreator.createService("latest");
        toolbar_Title.setText("H&S Inspection Reports");
        back_icon.setOnClickListener(view -> finish());
        initViews();
    }

    private void initViews() {
        rv_list.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        rv_list.setLayoutManager(layoutManager);

        avi.show();

        Observable<PastHsModel> responseObservable = apiservice.get_hsInspetionReport(
                sessionManager.getKeyId()
        );

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
                .subscribe(new Observer<PastHsModel>() {
                    @Override
                    public void onCompleted() {
                        avi.hide();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("error", "" + e.getMessage());
                    }

                    @Override
                    public void onNext(PastHsModel model) {
                        avi.hide();

                        //if (model.getData().size() > 0) {
                        statusCode = model.getStatusCode();
                        if (statusCode == 200) {
                            rv_list.setVisibility(View.VISIBLE);
                            Utility.displayToast(getApplicationContext(), model.getMessage());
                            adapter = new PastHSAdapter(PastHSInspcActivity.this, model.getData());
                            rv_list.setAdapter(adapter);
                        } else if (statusCode == 404) {
                            rv_list.setVisibility(View.GONE);
                            Utility.displayToast(getApplicationContext(), model.getMessage());
                        }
                    }
                });
    }

}
