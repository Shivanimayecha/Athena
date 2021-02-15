package com.athena.group.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.athena.group.API.ApiInterface;
import com.athena.group.API.ApiServiceCreator;
import com.athena.group.MainActivity;
import com.athena.group.Model.LoginResponse;
import com.athena.group.Model.UserDataResponse;
import com.athena.group.R;
import com.athena.group.Truck__Driver_Panel.TruckDriverActivity;
import com.athena.group.application.SessionManager;
import com.athena.group.application.Utility;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;
import com.wang.avi.AVLoadingIndicatorView;

import java.net.SocketTimeoutException;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.edt_username)
    EditText edt_username;
    @BindView(R.id.edt_password)
    EditText edt_password;
    @BindView(R.id.btn_login)
    Button btn_login;
    @BindView(R.id.show_pass_btn)
    ImageView show_pass_btn;
    @BindView(R.id.txt_frgtpass)
    TextView txt_frgtpass;
    @BindView(R.id.avi)
    AVLoadingIndicatorView avi;

    ProgressDialog pDialog;
    ApiInterface apiservice;
    int statusCode;
    SessionManager sessionManager;
    private String Token;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_loginscreen);
        ButterKnife.bind(this);

        apiservice = ApiServiceCreator.createService("latest");
        sessionManager = new SessionManager(LoginActivity.this);

        String recent_token = FirebaseInstanceId.getInstance().getToken();
        Log.e("fcm_id", recent_token);
        sessionManager.setFcmId(recent_token);
        // Utility.displayToast(this, "FCM_KEY--" + recent_token);
    /*    if (sessionManager.isLoggedIn()) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }*/

        txt_frgtpass.setOnClickListener(view -> startActivity(new Intent(LoginActivity.this, ForgotPassActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)));
        btn_login.setOnClickListener(view -> {
            if (edt_username.getText().toString().equals("")) {
                Utility.displayToast(getApplicationContext(), "Please enter Mobileno");
            } else if (edt_password.getText().toString().equals("")) {
                Utility.displayToast(getApplicationContext(), "Please enter Password");
            } else {
                checkLogin(edt_username.getText().toString(), edt_password.getText().toString());
            }
        });
    }

    public void ShowHidePass(View view) {

        if (view.getId() == R.id.show_pass_btn) {

            if (edt_password.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())) {
                ((ImageView) (view)).setImageResource(R.drawable.show);

                //Show Password
                edt_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            } else {
                ((ImageView) (view)).setImageResource(R.drawable.hide);

                //Hide Password
                edt_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
        }
    }


    private void checkLogin(String email, String pwd) {

        avi.show();
        Log.e("data", email + pwd);
        Observable<LoginResponse> responseObservable = apiservice.loginUser(email, pwd, sessionManager.getFcmId());
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

                            Token = loginResponse.getData().get(0).getToken();
                            sessionManager.setKeyEmail(email);
                            sessionManager.setLoginType("normal");

                            Log.e("token---->", "onNext: " + Token);
                            getUserDetail();
                        } else {
                            Utility.displayToast(getApplicationContext(), loginResponse.getMessage());
                        }
                    }
                });
    }

    private void getUserDetail() {

        avi.show();
        Observable<UserDataResponse> responseObservable = apiservice.getUserDetails(sessionManager.getKeyEmail());
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
                .subscribe(new Observer<UserDataResponse>() {
                    @Override
                    public void onCompleted() {
                        avi.hide();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("error", e.getLocalizedMessage());
                    }

                    @Override
                    public void onNext(UserDataResponse userDataResponse) {
                        statusCode = userDataResponse.getStatusCode();
                        if (statusCode == 200) {
                            sessionManager.setKeyUserDetails(new Gson().toJson(userDataResponse.getData()));
                            sessionManager.setKeyToken(Token);
                            Log.e("user_type", userDataResponse.getData().get(0).getUserType());
                            Log.e("user_id", userDataResponse.getData().get(0).getUserId());
                            sessionManager.setKeyRoll(userDataResponse.getData().get(0).getUserType());
                            sessionManager.setKeyId(userDataResponse.getData().get(0).getUserId());
                            sessionManager.setKeyPassword(edt_password.getText().toString());
                            sessionManager.setKeyName(userDataResponse.getData().get(0).getName());
                            sessionManager.setKeyLname(userDataResponse.getData().get(0).getLname());
                            sessionManager.createLoginSession();
                            if (sessionManager.getKeyRoll().equals("Supervisor")) {
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                Intent intent = new Intent(LoginActivity.this, TruckDriverActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }
                    }
                });
    }


}
