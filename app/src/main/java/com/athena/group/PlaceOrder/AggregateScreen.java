package com.athena.group.PlaceOrder;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.athena.group.API.ApiInterface;
import com.athena.group.API.ApiServiceCreator;
import com.athena.group.Model.AttributeValueModel;
import com.athena.group.Model.SpinnerModel;
import com.athena.group.R;
import com.athena.group.application.SessionManager;
import com.athena.group.application.Utility;
import com.wang.avi.AVLoadingIndicatorView;

import java.net.SocketTimeoutException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Observer;
import rx.schedulers.Schedulers;

public class AggregateScreen extends Activity implements View.OnClickListener {

    @BindView(R.id.spn_type)
    Spinner spn_type;
    /* @BindView(R.id.spn_site)
     Spinner spn_site;*/
    @BindView(R.id.spn_qty)
    Spinner spn_qty;
    @BindView(R.id.back_icon)
    ImageView back_icon;
    @BindView(R.id.toolbar_Title)
    TextView toolbar_Title;
    @BindView(R.id.badge_cart)
    TextView badge_cart;
    @BindView(R.id.ll_dateday)
    LinearLayout ll_dateday;
    @BindView(R.id.txt_date)
    TextView txt_date;
    @BindView(R.id.txt_day)
    TextView txt_day;
    @BindView(R.id.avi)
    AVLoadingIndicatorView avi;
    @BindView(R.id.btn_placeorder)
    Button btn_placeorder;
    @BindView(R.id.btn_addcart)
    Button btn_addcart;
    /*@BindView(R.id.edtQty)
    EditText edtQty;*/
    @BindView(R.id.edtNotes)
    EditText edtNotes;
    @BindView(R.id.ln_cart_icon)
    LinearLayout ln_cart_icon;
    String txtdate = "", product = "", attributeType = "";
    //site = "", s_id = "",
    final Calendar myCalendar = Calendar.getInstance();
    List<SpinnerModel.Data> data;
    int statusCode;
    ApiInterface apiservice;
    SessionManager sessionManager;
    ArrayList<String> AttributeArray = new ArrayList<>();
    //String[] arrayList;
    ArrayList<String> arrayList = new ArrayList<>();
    private String qty;
    private int count = 0;
    String sitedata = "";
    DatePickerDialog datePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_aggregate_screen);
        ButterKnife.bind(this);
        sessionManager = new SessionManager(AggregateScreen.this);
        apiservice = ApiServiceCreator.createService("latest");
        toolbar_Title.setText("Aggregate - Place Order");
        ln_cart_icon.setVisibility(View.VISIBLE);

        initViews();
    }

    private void initViews() {

        txt_date.setText(Utility.date);
        txt_day.setText(Utility.day);
        txtdate = Utility.date;

        back_icon.setOnClickListener(this);
        ln_cart_icon.setOnClickListener(this);
        ll_dateday.setOnClickListener(this);
        btn_placeorder.setOnClickListener(this);
        btn_addcart.setOnClickListener(this);

        sitedata = getIntent().getStringExtra("site_name");
        Log.e("TAG", "sitedata--: " + sitedata);

        arrayList.add("Select Quantity");
        arrayList.add("20");
        arrayList.add("40");
        arrayList.add("60");
        arrayList.add("80");
        arrayList.add("100");
        arrayList.add("120");
        arrayList.add("140");
        arrayList.add("160");
        arrayList.add("180");
        arrayList.add("200");

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, arrayList);
        arrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        spn_qty.setAdapter(arrayAdapter);

        spn_qty.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (spn_qty.getSelectedItem() == "Select Quantity") {
                    qty = "";
                } else {
                    qty = spn_qty.getSelectedItem().toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        getAttributeType();
        spn_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (spn_type.getSelectedItem() == "Select Type") {
                    product = "";
                } else {
                    attributeType = spn_type.getSelectedItem().toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.back_icon:
                finish();
                break;
            case R.id.ln_cart_icon:
                Intent intent = new Intent(AggregateScreen.this, CartActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_dateday:
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                datePickerDialog = new DatePickerDialog(AggregateScreen.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        txt_date.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                        txtdate = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;  //for pass in api
                        SimpleDateFormat simpledateformat = new SimpleDateFormat("EEEE"); //for day
                        Date date = new Date(year, monthOfYear, dayOfMonth - 1);
                        txt_day.setText(simpledateformat.format(date));
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
                break;
            case R.id.btn_placeorder:
                if (attributeType.equals("")) {
                    Utility.displayToast(this, "Please Select Type");
                } else if (qty.equals("")) {
                    Utility.displayToast(this, "Please Select Quantity");
                } else if (txtdate.equals("")) {
                    Utility.displayToast(this, "Please Select Date");
                } else {
                    callPlaceOrderApi();
                }
                break;
            case R.id.btn_addcart:
                if (attributeType.equals("")) {
                    Utility.displayToast(this, "Please Select Type");
                } else if (qty.equals("")) {
                    Utility.displayToast(this, "Please Select Quantity");
                } else if (txtdate.equals("")) {
                    Utility.displayToast(this, "Please Select Date");
                } else {
                    callAddtoCartApi();
                }
                break;
        }
    }

    private void getSiteName() {

        avi.show();
        ArrayList<String> sitearray = new ArrayList<>();

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
                        avi.hide();

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
        //spn_site.setAdapter(dataAdapter);
    }

    public void getAttributeType() {

        avi.show();
        Observable<AttributeValueModel> responseObservable = apiservice.getAttributeValues("7", "Type");
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
                .subscribe(new Observer<AttributeValueModel>() {
                    @Override
                    public void onCompleted() {
                        avi.hide();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("error", "" + e.getMessage());
                    }

                    @Override
                    public void onNext(AttributeValueModel model) {
                        avi.hide();
                        statusCode = model.getStatusCode();
                        if (statusCode == 200) {
                            for (int i = 0; i < model.getData().size(); i++) {
                                AttributeArray.add(model.getData().get(i).getCa_value());
                            }
                        }
                    }
                });

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, AttributeArray);
        dataAdapter.add("Select Type");
        dataAdapter.setDropDownViewResource(R.layout.spinner_item);
        spn_type.setAdapter(dataAdapter);

    }

    public void IncreaseCartCount() {
        //int co = count + 1;
        int co = Integer.parseInt(sessionManager.getCartCount()) + 1;
        String c = String.valueOf(co);
        sessionManager.setCartCount(c);
        badge_cart.setText(c);
    }

    private void callAddtoCartApi() {
        try {
            avi.show();
            Observable<SpinnerModel> responseObservable = apiservice.addCart_aggregate(
                    sitedata,
                    "7",
                    attributeType,
                    qty,
                    Utility.time1,
                    edtNotes.getText().toString(),
                    txtdate,
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
                            avi.hide();
                            statusCode = model.getStatusCode();
                            if (statusCode == 200) {
                                Utility.displayToast(AggregateScreen.this, model.getMessage());
                                IncreaseCartCount();
                            } else {
                                Utility.displayToast(AggregateScreen.this, model.getMessage());
                            }
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void callPlaceOrderApi() {

        try {
            avi.show();
            btn_placeorder.setEnabled(false);
            Observable<SpinnerModel> responseObservable = apiservice.placeOrder_aggregate(
                    sitedata,
                    "7",
                    attributeType,
                    qty,
                    Utility.time1,
                    edtNotes.getText().toString(),
                    txtdate,
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
                            avi.hide();
                            statusCode = model.getStatusCode();
                            if (statusCode == 200) {
                                Utility.displayToast(AggregateScreen.this, model.getMessage());
                                Intent intent = new Intent(AggregateScreen.this, PlaceOrder.class);
                                startActivity(intent);
                                finish();
                            } else {
                                Utility.displayToast(AggregateScreen.this, model.getMessage());
                            }
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        badge_cart.setText(sessionManager.getCartCount());
    }

}

/*
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
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
*/
