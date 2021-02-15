package com.athena.group.PlaceOrder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.athena.group.API.ApiInterface;
import com.athena.group.API.ApiServiceCreator;
import com.athena.group.Model.OrderDetailsModel;
import com.athena.group.R;
import com.athena.group.adapter.CartAdapter;
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

public class CartActivity extends AppCompatActivity {

    SessionManager sessionManager;
    ApiInterface apiservice;
    int statusCode;
    RecyclerView.LayoutManager layoutManager;
    CartAdapter adapter;
    int count;
    List<String> cartid;
    List<OrderDetailsModel.Data> arrayList;
    private String res;


    @BindView(R.id.toolbar_Title)
    TextView toolbar_Title;
    @BindView(R.id.back_icon)
    ImageView back_icon;
    @BindView(R.id.avi)
    AVLoadingIndicatorView avi;
    @BindView(R.id.rv_cartlist)
    RecyclerView rv_cartlist;
    @BindView(R.id.btn_placeorder)
    Button btn_placeorder;
    @BindView(R.id.txt_dummy)
    TextView txt_dummy;
    @BindView(R.id.ll_placeorder)
    LinearLayout ll_placeorder;
    ProgressDialog pDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_cart);
        ButterKnife.bind(this);
        sessionManager = new SessionManager(CartActivity.this);
        apiservice = ApiServiceCreator.createService("latest");
        toolbar_Title.setText("Cart");
        arrayList = new ArrayList<>();

        initviews();
    }

    private void initviews() {

        back_icon.setOnClickListener(view -> finish());

        callCartListApi();

        btn_placeorder.setOnClickListener(view -> callCartPlaceOrderApi());
    }


    private void callCartListApi() {

        rv_cartlist.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        rv_cartlist.setLayoutManager(layoutManager);
        avi.show();

        Observable<OrderDetailsModel> responseObservable = apiservice.get_userCartdata(sessionManager.getKeyId());
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

                        if (model.getData().size() > 0) {
                            statusCode = model.getStatusCode();
                            arrayList = model.getData();
                            Log.e("tag", "onNext" + arrayList);
                            ll_placeorder.setVisibility(View.VISIBLE);
                            txt_dummy.setVisibility(View.GONE);

                            if (statusCode == 200) {
                                Utility.displayToast(getApplicationContext(), model.getMessage());
                                adapter = new CartAdapter(CartActivity.this, model.getData());
                                rv_cartlist.setAdapter(adapter);

                                cartid = new ArrayList<>();

                                for (int i = 0; i < arrayList.size(); i++) {
                                    cartid.add(String.valueOf(arrayList.get(i).getCartId()));
                                }
                                res = android.text.TextUtils.join(",", cartid);
                                Log.e("tag", "res:---- " + res);
                            }

                        } else {
                            txt_dummy.setVisibility(View.VISIBLE);
                            ll_placeorder.setVisibility(View.GONE);
                        }
                    }
                });
    }

    private void callCartPlaceOrderApi() {
        avi.show();

        Observable<OrderDetailsModel> responseObservable = apiservice.cart_placeOrder(res);
        Log.e("tag", "callCartPlaceOrderApi: " + res);
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

                        statusCode = model.getStatusCode();
                        if (statusCode == 200) {

                            Utility.displayToast(getApplicationContext(), model.getMessage());
                            Intent intent = new Intent(CartActivity.this, PlaceOrder.class);
                            startActivity(intent);
                            finish();

                        } else if (statusCode == 404) {
                            Utility.displayToast(getApplicationContext(), model.getMessage());
                        }
                    }
                });
    }

    public void DecreaseCartCountdata() {
        count = Integer.parseInt(sessionManager.getCartCount());
        String c = String.valueOf(count - 1);
        sessionManager.setCartCount(c);
        callCartListApi();
    }
}