package com.athena.group.PlaceOrder;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.athena.group.API.ApiInterface;
import com.athena.group.API.ApiServiceCreator;
import com.athena.group.Model.SpinnerModel;
import com.athena.group.R;
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

public class PlaceOrder extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.spn_site)
    Spinner spn_site;
    @BindView(R.id.back_icon)
    ImageView back_icon;
    @BindView(R.id.toolbar_Title)
    TextView toolbar_Title;
    @BindView(R.id.badge_cart)
    TextView badge_cart;
    @BindView(R.id.ll_concrete)
    LinearLayout ll_concrete;
    @BindView(R.id.ll_material)
    LinearLayout ll_material;
    @BindView(R.id.ll_aggregates)
    LinearLayout ll_aggregates;
    /*  @BindView(R.id.ll_haulage)
      LinearLayout ll_haulage;*/
    @BindView(R.id.ln_cart_icon)
    LinearLayout ln_cart_icon;

    @BindView(R.id.avi)
    AVLoadingIndicatorView avi;

    ApiInterface apiservice;
    SessionManager sessionManager;
    ProgressDialog pDialog;
    int statusCode;
    List<SpinnerModel.Data> data;
    String site, s_id = "";
    ArrayList<String> sitearray = new ArrayList<>();
    String p_id = "", p_name = "";
    int Cartcount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_place_order);
        ButterKnife.bind(this);
        sessionManager = new SessionManager(PlaceOrder.this);
        apiservice = ApiServiceCreator.createService("latest");
        toolbar_Title.setText("Place Order");
        ln_cart_icon.setVisibility(View.VISIBLE);

        initView();
//        getProduct();

    }

    private void initView() {

        back_icon.setOnClickListener(this);
        ln_cart_icon.setOnClickListener(this);
        ll_concrete.setOnClickListener(this);
        ll_aggregates.setOnClickListener(this);
        ll_material.setOnClickListener(this);

        getCartCount();
        getSiteName();

        spn_site.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (spn_site.getSelectedItem() == "Select site") {
                    site = "";
                    //Do nothing.
                } else {
                    site = spn_site.getSelectedItem().toString();
                    SpinnerModel.Data datalist = data.get(i - 1);
                    s_id = datalist.getSiteId();
                    String[] separated = site.split(",");
                    //contract_no=separated[0];
                    site = separated[1];
                    Log.e("Sitename is ", site);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public void onClick(View view) {

        Intent intent;
        switch (view.getId()) {
            case R.id.back_icon:
                finish();
                break;
            case R.id.ln_cart_icon:
                intent = new Intent(PlaceOrder.this, CartActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_concrete:
                if (s_id.equals("")) {
                    Utility.displayToast(getApplicationContext(), "Please Select Site");
                } else {
                    intent = new Intent(PlaceOrder.this, ConcreteScreen.class);
                    intent.putExtra("site_name", s_id);
                    startActivity(intent);
                }
                break;
            case R.id.ll_aggregates:
                if (s_id.equals("")) {
                    Utility.displayToast(getApplicationContext(), "Please Select Site");
                } else {
                    intent = new Intent(PlaceOrder.this, AggregateScreen.class);
                    intent.putExtra("site_name", s_id);
                    startActivity(intent);
                }
                break;
            case R.id.ll_material:
                if (s_id.equals("")) {
                    Utility.displayToast(getApplicationContext(), "Please Select Site");
                } else {
                    intent = new Intent(PlaceOrder.this, MaterialScreenNew.class);
                    intent.putExtra("site_name", s_id);
                    startActivity(intent);
                }
                break;
        }
    }

    public void getCartCount() {

        Observable<SpinnerModel> responseObservable = apiservice.getCartCont(sessionManager.getKeyId());
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
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("error", "" + e.getMessage());
                    }

                    @Override
                    public void onNext(SpinnerModel model) {
                        statusCode = model.getStatusCode();
                        if (statusCode == 200) {
                            if (model.getMessage() != null && !model.getMessage().equals(null)) {
                                Cartcount = Integer.parseInt(model.getMessage());
                                sessionManager.setCartCount(model.getMessage());
                                badge_cart.setText(sessionManager.getCartCount());
                            }
                        }
                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
        badge_cart.setText(sessionManager.getCartCount());
    }

    private void getSiteName() {

        avi.show();
        //Chnage Shirish
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
                        }
                    }
                });

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, sitearray);
        dataAdapter.add("Select site");
        dataAdapter.setDropDownViewResource(R.layout.spinner_item);
        spn_site.setAdapter(dataAdapter);

    }
}

/*
    private void getProduct() {

        avi.show();

        Observable<ProductModel> responseObservable = apiservice.get_allproducts();

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
                .subscribe(new Observer<ProductModel>() {
                    @Override
                    public void onCompleted() {
                        avi.hide();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("error", "" + e.getMessage());
                    }

                    @Override
                    public void onNext(ProductModel model) {
                        avi.hide();

                        statusCode = model.getStatusCode();
                        if (statusCode == 200) {

                            for (int i = 0; i < model.getData().size(); i++) {
                                p_id = model.getData().get(i).getCatId();
                            }




                        }
                    }
                });

    }
*/

