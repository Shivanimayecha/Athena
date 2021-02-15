package com.athena.group.PlaceOrder;

import androidx.cardview.widget.CardView;

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
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

public class ConcreteScreen extends Activity implements View.OnClickListener {

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
    /* @BindView(R.id.day)
     TextView day;*/
  /*  @BindView(R.id.spn_site)
    Spinner spn_site;*/
    @BindView(R.id.spn_qty)
    Spinner spn_qty;
    /*    @BindView(R.id.spn_attribute1)
        Spinner spn_attribute1;*/
    @BindView(R.id.spn_attribute2)
    Spinner spn_attribute2;
    @BindView(R.id.spn_attribute3)
    Spinner spn_attribute3;
    @BindView(R.id.spn_attribute4)
    Spinner spn_attribute4;
    @BindView(R.id.btn_placeorder)
    Button btn_placeorder;
    @BindView(R.id.btn_addcart)
    Button btn_addcart;
    /* @BindView(R.id.edtQty)
     EditText edtQty;*/
    @BindView(R.id.edtNotes)
    EditText edtNotes;
    @BindView(R.id.mixCard)
    CardView mixCard;
    @BindView(R.id.aggrigateCard)
    CardView aggrigateCard;
    @BindView(R.id.slumcard)
    CardView slumcard;
    /*@BindView(R.id.quantCard)
    CardView quantCard;*/
    @BindView(R.id.ln_cart_icon)
    LinearLayout ln_cart_icon;
    @BindView(R.id.avi)
    AVLoadingIndicatorView avi;
    @BindView(R.id.rg1)
    RadioGroup rg1;
    @BindView(R.id.rb_yes)
    RadioButton rb_yes;
    @BindView(R.id.rb_no)
    RadioButton rb_no;
    RadioButton rb1, rb2;
    ApiInterface apiservice;
    SessionManager sessionManager;
    int statusCode;
    List<SpinnerModel.Data> data;
    ArrayList<String> sitearray = new ArrayList<>();
    String product = "", p_id = "", pname = "";
    String sitedata = "";
    String TAG = "TAG";
    final Calendar myCalendar = Calendar.getInstance();
    String attributeNAme1 = "", attributeNAme2 = "", attributeNAme3 = "", attributeNAme4 = "", attributeType = "";
    String txtdate = "";
    private String yn = "";
    // String[] arrayList;
    ArrayList<String> arrayList = new ArrayList();
    private String qty = "";
    DatePickerDialog datePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_concrete_screen);
        ButterKnife.bind(this);
        sessionManager = new SessionManager(ConcreteScreen.this);
        apiservice = ApiServiceCreator.createService("latest");
        toolbar_Title.setText("Concrete - Place Order");
        ln_cart_icon.setVisibility(View.VISIBLE);

        initView();
    }

    private void initView() {

        txt_date.setText(Utility.date);
        txt_day.setText(Utility.day);
        txtdate = Utility.date;

        back_icon.setOnClickListener(this);
        ln_cart_icon.setOnClickListener(this);
        ll_dateday.setOnClickListener(this);
        btn_placeorder.setOnClickListener(this);
        btn_addcart.setOnClickListener(this);

        sitedata = getIntent().getStringExtra("site_name");
        Log.e(TAG, "sitedata--: " + sitedata);

        arrayList.add("Select Quantity");
        arrayList.add("0.5");
        arrayList.add("1");
        arrayList.add("1.5");
        arrayList.add("2");
        arrayList.add("2.5");
        arrayList.add("3");
        arrayList.add("3.5");
        arrayList.add("4");
        arrayList.add("4.5");
        arrayList.add("5");
        arrayList.add("5.5");
        arrayList.add("6");
        arrayList.add("6.5");
        arrayList.add("7");
        arrayList.add("7.5");
        arrayList.add("8");
        arrayList.add("9");
        arrayList.add("10");
        arrayList.add("11");
        arrayList.add("12");
        arrayList.add("13");
        arrayList.add("14");
        arrayList.add("15");
        arrayList.add("16");
        arrayList.add("17");
        arrayList.add("18");
        arrayList.add("19");
        arrayList.add("20");
        arrayList.add("21");
        arrayList.add("22");
        arrayList.add("23");
        arrayList.add("24");
        arrayList.add("25");
        arrayList.add("26");
        arrayList.add("27");
        arrayList.add("28");
        arrayList.add("29");
        arrayList.add("30");

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

        getAttributename2();
        spn_attribute2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (spn_attribute2.getSelectedItem() == "Select Mix") {
                    product = "";
                } else {
                    attributeNAme2 = spn_attribute2.getSelectedItem().toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spn_attribute3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (spn_attribute3.getSelectedItem() == "Select Aggregate Size") {
                    product = "";
                } else {
                    attributeNAme3 = spn_attribute3.getSelectedItem().toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spn_attribute4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (spn_attribute4.getSelectedItem() == "Select Slump") {
                    product = "";
                } else {
                    attributeNAme4 = spn_attribute4.getSelectedItem().toString();
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
                startActivity(new Intent(this, CartActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                break;
            case R.id.ll_dateday:
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                datePickerDialog = new DatePickerDialog(ConcreteScreen.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        txt_date.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                        txtdate = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;  //for pass in api
                        SimpleDateFormat simpledateformat = new SimpleDateFormat("EEEE");  //for day
                        Date date = new Date(year, monthOfYear, dayOfMonth - 1);
                        txt_day.setText(simpledateformat.format(date));
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
                break;
            case R.id.btn_placeorder:
                if (qty.equals("")) {
                    Utility.displayToast(getApplicationContext(), "Please Select Quantity");
                } else if (attributeNAme2.equals("")) {
                    Utility.displayToast(getApplicationContext(), "Please Select Mix");
                } else if (attributeNAme3.equals("")) {
                    Utility.displayToast(getApplicationContext(), "Please Select Aggregate Size");
                } else if (attributeNAme4.equals("")) {
                    Utility.displayToast(getApplicationContext(), "Please Select Slump");
                } else if (rg1.getCheckedRadioButtonId() == -1) {
                    Utility.displayToast(getApplicationContext(), "Please Select Pump required or not ?");
                } else if (txtdate.equals("")) {
                    Utility.displayToast(getApplicationContext(), "Please Select Date");
                } else {
                    int selectedId = rg1.getCheckedRadioButtonId();

                    if (selectedId == rg1.getCheckedRadioButtonId()) {
                        rb1 = (RadioButton) findViewById(selectedId);
                        rb2 = (RadioButton) findViewById(selectedId);
                        if (rb1.getText().toString().equals("Yes")) {
                            yn = "yes";
                        } else if (rb2.getText().toString().equals("No")) {
                            yn = "no";
                        }
                    }
                    callPlaceOrderApi();
                }
                break;
            case R.id.btn_addcart:
                if (qty.equals("")) {
                    Utility.displayToast(this, "Please Select Quantity");
                } else if (attributeNAme2.equals("")) {
                    Utility.displayToast(this, "Please Select Mix");
                } else if (attributeNAme3.equals("")) {
                    Utility.displayToast(this, "Please Select Aggregate Size");
                } else if (attributeNAme4.equals("")) {
                    Utility.displayToast(this, "Please Select Slump");
                } else if (rg1.getCheckedRadioButtonId() == -1) {
                    Utility.displayToast(this, "Please Select Pump required or not ?");
                } else if (txtdate.equals("")) {
                    Utility.displayToast(this, "Please Select Date");
                } else {
                    int selectedId = rg1.getCheckedRadioButtonId();

                    if (selectedId == rg1.getCheckedRadioButtonId()) {
                        rb1 = (RadioButton) findViewById(selectedId);
                        rb2 = (RadioButton) findViewById(selectedId);
                        if (rb1.getText().toString().equals("Yes")) {
                            yn = "yes";
                        } else if (rb2.getText().toString().equals("No")) {
                            yn = "no";
                        }
                    }
                    callAddToCartApi();
                }
                break;
        }
    }

    private void callPlaceOrderApi() {

        try {
            avi.show();
            btn_placeorder.setEnabled(false);
            Observable<SpinnerModel> responseObservable = apiservice.placeOrder(
                    sitedata,
                    "4",
                    attributeNAme2,
                    attributeNAme3,
                    attributeNAme4,
                    qty,
                    Utility.time1,
                    yn,
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
                                Utility.displayToast(ConcreteScreen.this, model.getMessage());
                                Intent intent = new Intent(ConcreteScreen.this, PlaceOrder.class);
                                startActivity(intent);
                                finish();
                            } else {
                                Utility.displayToast(ConcreteScreen.this, model.getMessage());
                            }
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getAttributename2() {
        ArrayList<String> AttributeArray = new ArrayList<>();

        avi.show();
        Observable<AttributeValueModel> responseObservable = apiservice.getAttributeValues("4", "Mix");
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
                        getAttributenam3();
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
        dataAdapter.add("Select Mix");
        dataAdapter.setDropDownViewResource(R.layout.spinner_item);
        spn_attribute2.setAdapter(dataAdapter);
    }

    public void getAttributenam3() {
        ArrayList<String> AttributeArray = new ArrayList<>();
        avi.show();
        Observable<AttributeValueModel> responseObservable = apiservice.getAttributeValues("4", "Aggregate Size");
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
                        getAttributenam4();
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
        dataAdapter.add("Select Aggregate Size");
        dataAdapter.setDropDownViewResource(R.layout.spinner_item);
        spn_attribute3.setAdapter(dataAdapter);
    }

    public void getAttributenam4() {
        ArrayList<String> AttributeArray = new ArrayList<>();
        avi.show();
        Observable<AttributeValueModel> responseObservable = apiservice.getAttributeValues("4", "Slump");
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
        dataAdapter.add("Select Slump");
        dataAdapter.setDropDownViewResource(R.layout.spinner_item);
        spn_attribute4.setAdapter(dataAdapter);
    }


    public void IncreaseCartCount() {
        int co = Integer.parseInt(sessionManager.getCartCount()) + 1;
        String c = String.valueOf(co);
        sessionManager.setCartCount(c);
        badge_cart.setText(c);
    }

    private void callAddToCartApi() {
        try {
            avi.show();
            Observable<SpinnerModel> responseObservable = apiservice.addCart_concrete(
                    sitedata,
                    "4",
                    attributeNAme2,
                    attributeNAme3,
                    attributeNAme4,
                    qty,
                    Utility.time1,
                    yn,
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
                                Utility.displayToast(ConcreteScreen.this, model.getMessage());
                                IncreaseCartCount();
                            } else {
                                Utility.displayToast(ConcreteScreen.this, model.getMessage());
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
//    public void getAttributeValue(String p_id, String Attributename) {
//
//        ArrayList<String> AttributeValuesArray = new ArrayList<>();
//
//        pDialog = new ProgressDialog(this);
//        pDialog.setTitle("Checking Data");
//        pDialog.setMessage("Please Wait...");
//        pDialog.setIndeterminate(false);
//        pDialog.setCancelable(false);
//        pDialog.show();
//
//        Observable<AttributeValueModel> responseObservable = apiservice.getAttributeValues(p_id, Attributename);
//
//        responseObservable.subscribeOn(Schedulers.newThread())
//                .observeOn(rx.android.schedulers.AndroidSchedulers.mainThread())
//                .onErrorResumeNext(throwable -> {
//                    if (throwable instanceof retrofit2.HttpException) {
//                        retrofit2.HttpException ex = (retrofit2.HttpException) throwable;
//                        statusCode = ex.code();
//                        Log.e("error", ex.getLocalizedMessage());
//                    } else if (throwable instanceof SocketTimeoutException) {
//                        statusCode = 1000;
//                    }
//                    return Observable.empty();
//                })
//                .subscribe(new Observer<AttributeValueModel>() {
//                    @Override
//                    public void onCompleted() {
//                        pDialog.dismiss();
//                        // getProduct();
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Log.e("error", "" + e.getMessage());
//                    }
//
//                    @Override
//                    public void onNext(AttributeValueModel model) {
//                        statusCode = model.getStatusCode();
//                        if (statusCode == 200) {
//                            for (int i = 0; i < model.getData().size(); i++) {
//                                AttributeValuesArray.add(model.getData().get(i).getCa_value());
//                            }
//                        }
//                    }
//                });
//
//        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, AttributeValuesArray);
//        dataAdapter.add("Select AttributeValues");
//        dataAdapter.setDropDownViewResource(R.layout.spinner_item);
//        spn_atrribute_values.setAdapter(dataAdapter);
//    }

     /*   btnDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(ConcreteScreen.this, R.style.DialogTheme,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                // calender.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                                calender.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                                txtdate = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                            }
                        }, year, month, day);
                picker.show();
            }
        });*/

/*
    private void getProduct() {

        productarray = new ArrayList<>();
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
                        //getAttributename1(p_id);
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
                            data1 = model.getData();
                            for (int i = 0; i < model.getData().size(); i++) {
                                productarray.add(model.getData().get(i).getCatName());
                            }
                        }
                    }
                });

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, productarray);
        dataAdapter.add("Select Product");
        dataAdapter.setDropDownViewResource(R.layout.spinner_item);
        spn_product.setAdapter(dataAdapter);
    }
*/
/*
        spn_product.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (spn_product.getSelectedItem() == "Select Product") {
                    product = "";
                    //Do nothing.
                } else {
                    product = spn_product.getSelectedItem().toString();
                    ProductModel.Data datalist = data1.get(i - 1);
                    p_id = datalist.getCatId();
                    pname = datalist.getCatName();

                    if (pname.equals("Concrete")) {
                        getAttributename1(String.valueOf(p_id));
                        typeCard.setVisibility(View.GONE);
                        quantCard.setVisibility(View.VISIBLE);
                        mixCard.setVisibility(View.VISIBLE);
                        aggrigateCard.setVisibility(View.VISIBLE);
                        slumcard.setVisibility(View.VISIBLE);
                        ll_concretedringe.setVisibility(View.GONE);
                        ll_gnrlbuild.setVisibility(View.GONE);
                        ll_meshacces.setVisibility(View.GONE);
                        ll_ppe.setVisibility(View.GONE);
                        ll_smalltools.setVisibility(View.GONE);

                    } else if (pname.equals("Aggregates")) {
                        getAttributeType(p_id);
                        typeCard.setVisibility(View.VISIBLE);
                        quantCard.setVisibility(View.GONE);
                        mixCard.setVisibility(View.GONE);
                        aggrigateCard.setVisibility(View.GONE);
                        slumcard.setVisibility(View.GONE);
                        ll_concretedringe.setVisibility(View.GONE);
                        ll_gnrlbuild.setVisibility(View.GONE);
                        ll_meshacces.setVisibility(View.GONE);
                        ll_ppe.setVisibility(View.GONE);
                        ll_smalltools.setVisibility(View.GONE);
                    } else if (pname.equals("Materials")) {
                        //getAttributeType(p_id);
                        typeCard.setVisibility(View.GONE);
                        quantCard.setVisibility(View.GONE);
                        mixCard.setVisibility(View.GONE);
                        aggrigateCard.setVisibility(View.GONE);
                        slumcard.setVisibility(View.GONE);
                        ll_concretedringe.setVisibility(View.VISIBLE);
                        ll_gnrlbuild.setVisibility(View.VISIBLE);
                        ll_meshacces.setVisibility(View.VISIBLE);
                        ll_ppe.setVisibility(View.VISIBLE);
                        ll_smalltools.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
*/


    /*public void getAttributename1() {
        ArrayList<String> AttributeArray = new ArrayList<>();

       *//* pDialog = new ProgressDialog(this);
        pDialog.setTitle("Checking Data");
        pDialog.setMessage("Please Wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();*//*
        avi.show();

        Observable<AttributeValueModel> responseObservable = apiservice.getAttributeValues("4", "Quant(m3)");

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
                        getAttributename2();
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
                            //List<AttributeModel.Data> data = model.getData();
                            for (int i = 0; i < model.getData().size(); i++) {
                                AttributeArray.add(model.getData().get(i).getCa_value());
                            }
                        }
                    }
                });

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, AttributeArray);
        dataAdapter.add("Select Quant(m3)");
        dataAdapter.setDropDownViewResource(R.layout.spinner_item);
        //spn_attribute1.setAdapter(dataAdapter);

    }*/
//getSiteName();


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


//getAttributename1();


     /*   spn_attribute1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (spn_attribute1.getSelectedItem() == "Select Quant(m3)") {
                    product = "";
                    //Do nothing.
                } else {
                    attributeNAme1 = spn_attribute1.getSelectedItem().toString();

                    //  getAttributeValue(p_id, attributeNAme);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
*/
         /*
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);

            dataa = data.getStringExtra("data");

            if (requestCode == REQUEST_MESH && resultCode == RESULT_OK && data1 != null) {
                edt_mesh_acces.setText(dataa);
            } else if (requestCode == REQUEST_GNRLBUILDNG && resultCode == RESULT_OK && data1 != null) {
                edt_gnrlmaterial.setText(dataa);
            } else if (requestCode == REQUEST_PPE && resultCode == RESULT_OK && data1 != null) {
                edt_ppe.setText(dataa);
            } else if (requestCode == REQUEST_SMALLTOOLS && resultCode == RESULT_OK && data1 != null) {
                edt_small_tool.setText(dataa);
            }
        }
    */
