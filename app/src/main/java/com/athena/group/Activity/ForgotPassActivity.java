package com.athena.group.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.athena.group.API.ApiInterface;
import com.athena.group.API.ApiServiceCreator;
import com.athena.group.Model.LoginResponse;
import com.athena.group.R;
import com.athena.group.application.Utility;
import com.wang.avi.AVLoadingIndicatorView;

import java.net.SocketTimeoutException;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class
ForgotPassActivity extends AppCompatActivity {

    @BindView(R.id.btn_submit)
    Button btn_submit;
    @BindView(R.id.edt_email)
    EditText edt_email;
    @BindView(R.id.avi)
    AVLoadingIndicatorView avi;
    ApiInterface apiservice;
    int statusCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_forgot_pass);
        ButterKnife.bind(this);

        apiservice = ApiServiceCreator.createService("latest");

        btn_submit.setOnClickListener(view -> {
            if (edt_email.getText().toString().equals("")) {
                Utility.displayToast(getApplicationContext(), "Please Enter Your Register Email !");
            } else {
                callForgotPassApi(edt_email.getText().toString());
            }
        });

    }

    private void callForgotPassApi(String email) {

        try {
            avi.show();
            Observable<LoginResponse> responseObservable = apiservice.forgotPass(email);
            responseObservable.subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
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
                    .subscribe(new Observer<LoginResponse>() {
                        @Override
                        public void onCompleted() {
                            avi.hide();
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.e("error", "" + e.getMessage());
                        }

                        @Override
                        public void onNext(LoginResponse loginResponse) {
                            statusCode = loginResponse.getStatusCode();
                            if (statusCode == 200) {
                                avi.hide();
                                Utility.displayToast(getApplicationContext(), loginResponse.getMessage());
                                finish();
                            } else {
                                Utility.displayToast(getApplicationContext(), loginResponse.getMessage());
                            }
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
