package com.athena.group.HSQuesInsctn;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.athena.group.API.ApiInterface;
import com.athena.group.API.ApiServiceCreator;
import com.athena.group.Fragment.CompleteFragment;
import com.athena.group.Fragment.PendingFragment;
import com.athena.group.Model.PastHsModel;
import com.athena.group.R;
import com.athena.group.adapter.FragmentAdapter;
import com.athena.group.adapter.PastHSQueAdapter;
import com.athena.group.application.SessionManager;
import com.athena.group.application.Utility;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;
import com.wang.avi.AVLoadingIndicatorView;

import java.net.SocketTimeoutException;

import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Observer;
import rx.schedulers.Schedulers;

public class PastHSQuesActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener {

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
    PastHSQueAdapter adapter;

    ViewPager viewPager;
    TabLayout tabs;
    @BindView(R.id.app_bar)
    AppBarLayout app_bar;
    ImageView branding_icon;
    View firstTab,secondTab,thirdTab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_past_hsques);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ButterKnife.bind(this);
        sessionManager = new SessionManager(PastHSQuesActivity.this);
        apiservice = ApiServiceCreator.createService("latest");
        toolbar_Title.setText("H&S Reports Form List");
        back_icon.setOnClickListener(view -> finish());
        tabs = (TabLayout)findViewById(R.id.result_tabs);
        viewPager = (ViewPager)findViewById(R.id.viewpager);
        setupViewPager(viewPager);


        tabs.setupWithViewPager(viewPager);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        firstTab = ((ViewGroup)    tabs.getChildAt(0)).getChildAt(0);
        secondTab = ((ViewGroup) tabs.getChildAt(0)).getChildAt(1);
        tabs.setOnTabSelectedListener(PastHSQuesActivity.this);
        firstTab.setBackgroundColor(Color.parseColor("#BC0072"));
        secondTab.setBackgroundColor(Color.parseColor("#FFFFFF"));
       // initViews();
    }
    private void setupViewPager(ViewPager viewPager) {
        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager());
        adapter.addFragment(new PendingFragment(), "Pending");
        adapter.addFragment(new CompleteFragment(), "Complete");
        viewPager.setAdapter(adapter);
    }

    private void initViews() {
        rv_list.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        rv_list.setLayoutManager(layoutManager);

        avi.show();

        Observable<PastHsModel> responseObservable = apiservice.get_hsQuestionReport(
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
                            adapter = new PastHSQueAdapter(PastHSQuesActivity.this, model.getData());
                            rv_list.setAdapter(adapter);
                        } else if (statusCode == 404) {
                            rv_list.setVisibility(View.GONE);
                            Utility.displayToast(getApplicationContext(), model.getMessage());
                        }
                    }
                });
    }


    public void onTabSelected(TabLayout.Tab tab) {

        viewPager.setCurrentItem(tab.getPosition());

        int selectedTabPosition = tab.getPosition();

        if (selectedTabPosition == 0) { // that means first tab
            firstTab.setBackgroundColor(Color.parseColor("#BC0072"));
            secondTab.setBackgroundColor(Color.parseColor("#FFFFFF"));


        } else if (selectedTabPosition == 1) { // that means it's a last tab

            firstTab.setBackgroundColor(Color.parseColor("#FFFFFF"));
            secondTab.setBackgroundColor(Color.parseColor("#BC0072"));

        }
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
