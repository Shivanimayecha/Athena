package com.athena.group.Activity;

import androidx.cardview.widget.CardView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.athena.group.API.ApiConstants;
import com.athena.group.API.ApiInterface;
import com.athena.group.API.ApiServiceCreator;
import com.athena.group.Model.SpinnerModel;
import com.athena.group.Model.UserDataResponse;
import com.athena.group.R;
import com.athena.group.application.SessionManager;
import com.athena.group.application.Utility;
import com.bumptech.glide.Glide;
import com.wang.avi.AVLoadingIndicatorView;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class Profile extends Activity {

    @BindView(R.id.toolbar_Title)
    TextView toolbar_Title;
    @BindView(R.id.back_icon)
    ImageView back_icon;
    @BindView(R.id.avi)
    AVLoadingIndicatorView avi;
    @BindView(R.id.txt_phone)
    TextView txt_phone;
    @BindView(R.id.txt_email)
    TextView txt_email;
    @BindView(R.id.txt_address)
    TextView txt_address;
    @BindView(R.id.txt_position)
    TextView txt_position;
    @BindView(R.id.txt_usertype)
    TextView txt_usertype;
    @BindView(R.id.txt_fname)
    TextView txt_fname;
    @BindView(R.id.txt_lname)
    TextView txt_lname;
    @BindView(R.id.txt_edit)
    TextView txt_edit;
    @BindView(R.id.txt_sites)
    TextView txt_sites;
    @BindView(R.id.card_address)
    CardView card_address;
    @BindView(R.id.rl_site)
    RelativeLayout rl_site;
    @BindView(R.id.profile_image)
    CircleImageView profile_image;
    SessionManager sessionManager;
    ApiInterface apiservice;
    int statusCode;
    String site = "", s_id = "";
    List<SpinnerModel.Data> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);
        sessionManager = new SessionManager(Profile.this);
        apiservice = ApiServiceCreator.createService("latest");
        toolbar_Title.setText("Profile");
        initviews();
    }

    private void initviews() {

        card_address.setVisibility(View.GONE);
        back_icon.setOnClickListener(view -> finish());
        txt_edit.setOnClickListener(view -> startActivity(new Intent(Profile.this, EditUserProfile.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)));
        getUserDetail();
        getSiteName();
    }

    private void getUserDetail() {

        try {
            avi.show();
            List<String> truckno = new ArrayList<>();
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

                                txt_phone.setText(userDataResponse.getData().get(0).getMobile());
                                txt_email.setText(userDataResponse.getData().get(0).getEmail());
                                if (!userDataResponse.getData().get(0).getAddressLine1().equals("")) {
                                    card_address.setVisibility(View.VISIBLE);
                                    txt_address.setText(userDataResponse.getData().get(0).getAddressLine1());
                                } else {
                                    card_address.setVisibility(View.GONE);
                                }
                                txt_position.setText(userDataResponse.getData().get(0).getUserPosition());
                                txt_usertype.setText(userDataResponse.getData().get(0).getUserType());
                                txt_fname.setText(userDataResponse.getData().get(0).getName());
                                txt_lname.setText("  " + userDataResponse.getData().get(0).getLname());

                                Glide.with(Profile.this)
                                        .load(ApiConstants.IMAGE_URL + userDataResponse.getData().get(0).getProfileImage())
                                        .placeholder(R.color.gray)
                                        .into(profile_image);

                                if (sessionManager.getKeyRoll().equals("Supervisor")) {
                                    rl_site.setVisibility(View.VISIBLE);
                                } else {
                                    rl_site.setVisibility(View.GONE);
                                }
                            } else {
                                Utility.displayToast(Profile.this, userDataResponse.getMessage());
                            }
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getSiteName() {

        List<String> sitename = new ArrayList<>();
        List<String> siteid = new ArrayList<>();

        avi.show();
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
                                siteid.add(model.getData().get(i).getSiteId());
                                sitename.add(model.getData().get(i).getSiteName());
                            }

                            site = TextUtils.join(",", siteid);
                            String zonenamee = TextUtils.join(", ", sitename);
                            txt_sites.setText(zonenamee);
                        }
                    }
                });
    }

}
