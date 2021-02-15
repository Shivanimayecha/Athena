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
import android.widget.Spinner;
import android.widget.TextView;

import com.athena.group.API.ApiInterface;
import com.athena.group.API.ApiServiceCreator;
import com.athena.group.Model.AttributeModel;
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

public class MaterialScreenNew extends Activity implements View.OnClickListener {

    int statusCode;
    ApiInterface apiservice;
    SessionManager sessionManager;
    private int count = 0;

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
    @BindView(R.id.edtQty)
    EditText edtQty;
    @BindView(R.id.edtNotes)
    EditText edtNotes;
    @BindView(R.id.ln_cart_icon)
    LinearLayout ln_cart_icon;

    //spineerrr
/*    @BindView(R.id.spn_site)
    Spinner spn_site;*/
    @BindView(R.id.spn_product)
    Spinner spn_product;
    @BindView(R.id.spn_cd1)
    Spinner spn_cd1;
    @BindView(R.id.spn_cd2)
    Spinner spn_cd2;
    @BindView(R.id.spn_mesh)
    Spinner spn_mesh;
    @BindView(R.id.spn_gbm)
    Spinner spn_gbm;
    @BindView(R.id.spn_ppe)
    Spinner spn_ppe;
    @BindView(R.id.spn_st)
    Spinner spn_st;
    @BindView(R.id.spn_pdp)
    Spinner spn_pdp;
    @BindView(R.id.spn_pr)
    Spinner spn_pr;
    @BindView(R.id.spn_pdw)
    Spinner spn_pdw;
    @BindView(R.id.spn_claydrinage)
    Spinner spn_claydrinage;
    @BindView(R.id.spn_kerbs)
    Spinner spn_kerbs;
    @BindView(R.id.spn_flags)
    Spinner spn_flags;
    @BindView(R.id.spn_blockpaves)
    Spinner spn_blockpaves;
    //cardviewss

    @BindView(R.id.card_concredringe)
    CardView card_concredringe;
    @BindView(R.id.card_concredringe2)
    CardView card_concredringe2;
    @BindView(R.id.card_gbm)
    CardView card_gbm;
    @BindView(R.id.card_mesh)
    CardView card_mesh;
    @BindView(R.id.card_st)
    CardView card_st;
    @BindView(R.id.card_ppe)
    CardView card_ppe;
    @BindView(R.id.card_pdp)
    CardView card_pdp;
    @BindView(R.id.card_pr)
    CardView card_pr;
    @BindView(R.id.card_pdw)
    CardView card_pdw;
    @BindView(R.id.card_kerbs)
    CardView card_kerbs;
    @BindView(R.id.card_claydrinage)
    CardView card_claydrinage;
    @BindView(R.id.card_flags)
    CardView card_flags;
    @BindView(R.id.card_blockpaves)
    CardView card_blockpaves;

    String txtdate = "", product = "", p_id = "";
    //site = "", s_id = "",
    String sitedata = "";
    final Calendar myCalendar = Calendar.getInstance();
    List<SpinnerModel.Data> data;
    List<AttributeModel.Data> productdata;
    List<AttributeValueModel.Data> valueData;
    private String cd1 = "", cd2 = "", mesh = "", gbm = "", ppe = "", pdp = "", pr = "", st = "", pdw = "", claydrinage = "", kerbs = "", flags = "", blockpaves = "";
    DatePickerDialog datePickerDialog;
    String time1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_material_screen_new);

        ButterKnife.bind(this);
        sessionManager = new SessionManager(MaterialScreenNew.this);
        apiservice = ApiServiceCreator.createService("latest");
        toolbar_Title.setText(getString(R.string.materialplaceorder));
        ln_cart_icon.setVisibility(View.VISIBLE);

        initViews();
    }

    private void initViews() {

        txt_date.setText(Utility.date);
        txt_day.setText(Utility.day);
        txtdate = Utility.date;

        time1 = new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());

        back_icon.setOnClickListener(this);
        ln_cart_icon.setOnClickListener(this);
        ll_dateday.setOnClickListener(this);
        btn_addcart.setOnClickListener(this);
        btn_placeorder.setOnClickListener(this);

        sitedata = getIntent().getStringExtra("site_name");
        Log.e("TAG", "sitedata--: " + sitedata);

        getMaterialProduct();
        spn_product.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (spn_product.getSelectedItem() == "Select Product") {
                    product = "";
                } else {
                    product = spn_product.getSelectedItem().toString();
                    /*AttributeModel.Data datalist = productdata.get(i - 1);
                    product = datalist.getCa_label();*/

                    if (product.equals("Concrete drainage")) {

                        getSubAttribute();
                        card_concredringe.setVisibility(View.VISIBLE);
                        card_concredringe2.setVisibility(View.VISIBLE);
                        card_mesh.setVisibility(View.GONE);
                        card_gbm.setVisibility(View.GONE);
                        card_st.setVisibility(View.GONE);
                        card_ppe.setVisibility(View.GONE);
                        card_pdw.setVisibility(View.GONE);
                        card_pr.setVisibility(View.GONE);
                        card_pdp.setVisibility(View.GONE);
                        card_claydrinage.setVisibility(View.GONE);
                        card_kerbs.setVisibility(View.GONE);
                        card_flags.setVisibility(View.GONE);
                        card_blockpaves.setVisibility(View.GONE);

                    } else if (product.equals("Mesh and accessories")) {

                        getAttribute();
                        card_concredringe.setVisibility(View.GONE);
                        card_concredringe2.setVisibility(View.GONE);
                        card_mesh.setVisibility(View.VISIBLE);
                        card_gbm.setVisibility(View.GONE);
                        card_st.setVisibility(View.GONE);
                        card_ppe.setVisibility(View.GONE);
                        card_pdw.setVisibility(View.GONE);
                        card_pr.setVisibility(View.GONE);
                        card_pdp.setVisibility(View.GONE);
                        card_claydrinage.setVisibility(View.GONE);
                        card_kerbs.setVisibility(View.GONE);
                        card_flags.setVisibility(View.GONE);
                        card_blockpaves.setVisibility(View.GONE);

                    } else if (product.equals("General building materials")) {

                        getgbm();
                        card_concredringe.setVisibility(View.GONE);
                        card_concredringe2.setVisibility(View.GONE);
                        card_gbm.setVisibility(View.VISIBLE);
                        card_mesh.setVisibility(View.GONE);
                        card_st.setVisibility(View.GONE);
                        card_ppe.setVisibility(View.GONE);
                        card_pdw.setVisibility(View.GONE);
                        card_pr.setVisibility(View.GONE);
                        card_pdp.setVisibility(View.GONE);
                        card_claydrinage.setVisibility(View.GONE);
                        card_kerbs.setVisibility(View.GONE);
                        card_flags.setVisibility(View.GONE);
                        card_blockpaves.setVisibility(View.GONE);
                    } else if (product.equals("PPE")) {

                        getPPE();
                        card_concredringe.setVisibility(View.GONE);
                        card_concredringe2.setVisibility(View.GONE);
                        card_ppe.setVisibility(View.VISIBLE);
                        card_mesh.setVisibility(View.GONE);
                        card_gbm.setVisibility(View.GONE);
                        card_st.setVisibility(View.GONE);
                        card_pdw.setVisibility(View.GONE);
                        card_pr.setVisibility(View.GONE);
                        card_pdp.setVisibility(View.GONE);
                        card_claydrinage.setVisibility(View.GONE);
                        card_kerbs.setVisibility(View.GONE);
                        card_flags.setVisibility(View.GONE);
                        card_blockpaves.setVisibility(View.GONE);
                    } else if (product.equals("Small tools")) {
                        getst();
                        card_st.setVisibility(View.VISIBLE);
                        card_concredringe.setVisibility(View.GONE);
                        card_concredringe2.setVisibility(View.GONE);
                        card_ppe.setVisibility(View.GONE);
                        card_mesh.setVisibility(View.GONE);
                        card_gbm.setVisibility(View.GONE);
                        card_pdw.setVisibility(View.GONE);
                        card_pr.setVisibility(View.GONE);
                        card_pdp.setVisibility(View.GONE);
                        card_claydrinage.setVisibility(View.GONE);
                        card_kerbs.setVisibility(View.GONE);
                        card_flags.setVisibility(View.GONE);
                        card_blockpaves.setVisibility(View.GONE);
                    } else if (product.equals("Plastic Drainage Polypipe")) {
                        getpdp();
                        card_pdp.setVisibility(View.VISIBLE);
                        card_ppe.setVisibility(View.GONE);
                        card_mesh.setVisibility(View.GONE);
                        card_gbm.setVisibility(View.GONE);
                        card_st.setVisibility(View.GONE);
                        card_pdw.setVisibility(View.GONE);
                        card_pr.setVisibility(View.GONE);
                        card_claydrinage.setVisibility(View.GONE);
                        card_kerbs.setVisibility(View.GONE);
                        card_concredringe.setVisibility(View.GONE);
                        card_concredringe2.setVisibility(View.GONE);
                        card_flags.setVisibility(View.GONE);
                        card_blockpaves.setVisibility(View.GONE);
                    } else if (product.equals("Polysewer / Ridgidrain")) {
                        getpr();
                        card_pr.setVisibility(View.VISIBLE);
                        card_concredringe.setVisibility(View.GONE);
                        card_concredringe2.setVisibility(View.GONE);
                        card_pdp.setVisibility(View.GONE);
                        card_ppe.setVisibility(View.GONE);
                        card_mesh.setVisibility(View.GONE);
                        card_gbm.setVisibility(View.GONE);
                        card_st.setVisibility(View.GONE);
                        card_pdw.setVisibility(View.GONE);
                        card_claydrinage.setVisibility(View.GONE);
                        card_kerbs.setVisibility(View.GONE);
                        card_flags.setVisibility(View.GONE);
                        card_blockpaves.setVisibility(View.GONE);
                    } else if (product.equals("Plastic Drainage Wavin")) {
                        getpdw();
                        card_concredringe.setVisibility(View.GONE);
                        card_concredringe2.setVisibility(View.GONE);
                        card_pdp.setVisibility(View.GONE);
                        card_ppe.setVisibility(View.GONE);
                        card_mesh.setVisibility(View.GONE);
                        card_gbm.setVisibility(View.GONE);
                        card_st.setVisibility(View.GONE);
                        card_pr.setVisibility(View.GONE);
                        card_claydrinage.setVisibility(View.GONE);
                        card_kerbs.setVisibility(View.GONE);
                        card_pdw.setVisibility(View.VISIBLE);
                        card_flags.setVisibility(View.GONE);
                        card_blockpaves.setVisibility(View.GONE);
                    } else if (product.equals("Clay Drainage")) {
                        getcd();
                        card_concredringe.setVisibility(View.GONE);
                        card_concredringe2.setVisibility(View.GONE);
                        card_pdp.setVisibility(View.GONE);
                        card_ppe.setVisibility(View.GONE);
                        card_mesh.setVisibility(View.GONE);
                        card_gbm.setVisibility(View.GONE);
                        card_st.setVisibility(View.GONE);
                        card_pr.setVisibility(View.GONE);
                        card_claydrinage.setVisibility(View.VISIBLE);
                        card_kerbs.setVisibility(View.GONE);
                        card_pdw.setVisibility(View.GONE);
                        card_flags.setVisibility(View.GONE);
                        card_blockpaves.setVisibility(View.GONE);
                    } else if (product.equals("Kerbs")) {
                        getkerbs();
                        card_kerbs.setVisibility(View.VISIBLE);
                        card_pdp.setVisibility(View.GONE);
                        card_ppe.setVisibility(View.GONE);
                        card_mesh.setVisibility(View.GONE);
                        card_gbm.setVisibility(View.GONE);
                        card_st.setVisibility(View.GONE);
                        card_pr.setVisibility(View.GONE);
                        card_claydrinage.setVisibility(View.GONE);
                        card_pdw.setVisibility(View.GONE);
                        card_concredringe.setVisibility(View.GONE);
                        card_concredringe2.setVisibility(View.GONE);
                        card_flags.setVisibility(View.GONE);
                        card_blockpaves.setVisibility(View.GONE);
                    } else if (product.equals("Flags")) {
                        getflags();
                        card_kerbs.setVisibility(View.GONE);
                        card_pdp.setVisibility(View.GONE);
                        card_ppe.setVisibility(View.GONE);
                        card_mesh.setVisibility(View.GONE);
                        card_gbm.setVisibility(View.GONE);
                        card_st.setVisibility(View.GONE);
                        card_pr.setVisibility(View.GONE);
                        card_claydrinage.setVisibility(View.GONE);
                        card_pdw.setVisibility(View.GONE);
                        card_concredringe.setVisibility(View.GONE);
                        card_concredringe2.setVisibility(View.GONE);
                        card_flags.setVisibility(View.VISIBLE);
                        card_blockpaves.setVisibility(View.GONE);
                    } else if (product.equals("Block Paves")) {
                        getblockpaves();
                        card_kerbs.setVisibility(View.GONE);
                        card_pdp.setVisibility(View.GONE);
                        card_ppe.setVisibility(View.GONE);
                        card_mesh.setVisibility(View.GONE);
                        card_gbm.setVisibility(View.GONE);
                        card_st.setVisibility(View.GONE);
                        card_pr.setVisibility(View.GONE);
                        card_claydrinage.setVisibility(View.GONE);
                        card_pdw.setVisibility(View.GONE);
                        card_concredringe.setVisibility(View.GONE);
                        card_concredringe2.setVisibility(View.GONE);
                        card_flags.setVisibility(View.GONE);
                        card_blockpaves.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spn_cd1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (spn_cd1.getSelectedItem() == "Select Concrete drainage") {
                    cd1 = "";
                    //Do nothing.
                } else {
                    cd1 = spn_cd1.getSelectedItem().toString();
                    getsubAttributeValue();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spn_cd2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (spn_cd2.getSelectedItem() == "Select Concrete drainage product") {
                    cd2 = "";
                } else {
                    cd2 = spn_cd2.getSelectedItem().toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spn_mesh.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (spn_mesh.getSelectedItem() == "Select Mesh") {
                    mesh = "";
                } else {
                    mesh = spn_mesh.getSelectedItem().toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spn_ppe.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (spn_ppe.getSelectedItem() == "Select PPE") {
                    ppe = "";
                } else {
                    ppe = spn_ppe.getSelectedItem().toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spn_st.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (spn_st.getSelectedItem() == "Select Small tools") {
                    st = "";
                } else {
                    st = spn_st.getSelectedItem().toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spn_gbm.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (spn_gbm.getSelectedItem() == "Select Gerenal building material") {
                    gbm = "";
                } else {
                    gbm = spn_gbm.getSelectedItem().toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spn_pdp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (spn_pdp.getSelectedItem() == "Select Plastic Drainage Polypipe") {
                    pdp = "";
                } else {
                    pdp = spn_pdp.getSelectedItem().toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spn_pr.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (spn_pr.getSelectedItem() == "Select Polysewer / Ridgidrain") {
                    pr = "";
                } else {
                    pr = spn_pr.getSelectedItem().toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spn_pdw.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (spn_pdw.getSelectedItem() == "Select Plastic Drainage Wavin") {
                    pdw = "";
                } else {
                    pdw = spn_pdw.getSelectedItem().toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spn_claydrinage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (spn_claydrinage.getSelectedItem() == "Select Clay Drainage") {
                    claydrinage = "";
                } else {
                    claydrinage = spn_claydrinage.getSelectedItem().toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spn_kerbs.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (spn_kerbs.getSelectedItem() == "Select Kerbs") {
                    kerbs = "";
                } else {
                    kerbs = spn_kerbs.getSelectedItem().toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spn_flags.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (spn_flags.getSelectedItem() == "Select Flags") {
                    flags = "";
                } else {
                    flags = spn_flags.getSelectedItem().toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spn_blockpaves.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (spn_blockpaves.getSelectedItem() == "Select Block paves") {
                    blockpaves = "";
                } else {
                    blockpaves = spn_blockpaves.getSelectedItem().toString();
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
                Intent intent = new Intent(MaterialScreenNew.this, CartActivity.class);
                startActivity(intent);
                break;

            case R.id.ll_dateday:
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                datePickerDialog = new DatePickerDialog(MaterialScreenNew.this, new DatePickerDialog.OnDateSetListener() {
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

            case R.id.btn_addcart:
                if (product.equals("")) {
                    Utility.displayToast(getApplicationContext(), "Please Select Product");
                } else if (edtQty.getText().toString().equals("")) {
                    Utility.displayToast(this, "Please Enter Quantity");
                } else if (txtdate.equals("")) {
                    Utility.displayToast(this, "Please Select Date");
                } else {
                    callAddToCartApi();
                }
                break;

            case R.id.btn_placeorder:
                if (product.equals("")) {
                    Utility.displayToast(getApplicationContext(), "Please Select Product");
                } else if (edtQty.getText().toString().equals("")) {
                    Utility.displayToast(this, "Please Enter Quantity");
                } else if (txtdate.equals("")) {
                    Utility.displayToast(this, "Please Select Date");
                } else {
                    callPlaceOrderApi();
                }
                break;
        }
    }

    private void callAddToCartApi() {

        try {
            avi.show();
            Observable<SpinnerModel> responseObservable = apiservice.addCart_material(
                    sitedata,
                    product,
                    "8",
                    cd1,
                    cd2,
                    mesh,
                    gbm,
                    ppe,
                    st,
                    pdp,
                    pr,
                    pdw,
                    claydrinage,
                    kerbs,
                    flags,
                    blockpaves,
                    edtQty.getText().toString(),
                    time1,
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
                                Utility.displayToast(MaterialScreenNew.this, model.getMessage());
                                IncreaseCartCount();
                            } else {
                                Utility.displayToast(MaterialScreenNew.this, model.getMessage());
                            }
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void IncreaseCartCount() {
        int co = Integer.parseInt(sessionManager.getCartCount()) + 1;
        String c = String.valueOf(co);
        sessionManager.setCartCount(c);
        badge_cart.setText(c);
    }

    private void callPlaceOrderApi() {

        try {
            avi.show();
            btn_placeorder.setEnabled(false);
            Observable<SpinnerModel> responseObservable = apiservice.placeOrder_material(
                    sitedata,
                    product,
                    "8",
                    cd1,
                    cd2,
                    mesh,
                    gbm,
                    ppe,
                    st,
                    pdp,
                    pr,
                    pdw,
                    claydrinage,
                    kerbs,
                    flags,
                    blockpaves,
                    edtQty.getText().toString(),
                    time1,
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
                                Utility.displayToast(MaterialScreenNew.this, model.getMessage());
                                Intent intent = new Intent(MaterialScreenNew.this, PlaceOrder.class);
                                startActivity(intent);
                                finish();
                            } else {
                                Utility.displayToast(MaterialScreenNew.this, model.getMessage());
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

    private void getflags() {
        avi.show();
        ArrayList<String> flagarray = new ArrayList<>();
        Observable<AttributeValueModel> responseObservable = apiservice.getAttributeValues("8", product);
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
                            valueData = model.getData();
                            for (int i = 0; i < model.getData().size(); i++) {
                                flagarray.add(model.getData().get(i).getCa_value());
                            }
                        } else {
                            Utility.displayToast(MaterialScreenNew.this, model.getMessage());
                        }
                    }
                });

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, flagarray);
        dataAdapter.add("Select Flags");
        dataAdapter.setDropDownViewResource(R.layout.spinner_item);
        spn_flags.setAdapter(dataAdapter);
    }

    private void getblockpaves() {
        avi.show();
        ArrayList<String> bparray = new ArrayList<>();
        Observable<AttributeValueModel> responseObservable = apiservice.getAttributeValues("8", product);
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
                            valueData = model.getData();
                            for (int i = 0; i < model.getData().size(); i++) {
                                bparray.add(model.getData().get(i).getCa_value());
                            }
                        } else {
                            Utility.displayToast(MaterialScreenNew.this, model.getMessage());
                        }
                    }
                });

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, bparray);
        dataAdapter.add("Select Block paves");
        dataAdapter.setDropDownViewResource(R.layout.spinner_item);
        spn_blockpaves.setAdapter(dataAdapter);
    }

    private void getkerbs() {
        avi.show();
        ArrayList<String> kerbsarray = new ArrayList<>();
        Observable<AttributeValueModel> responseObservable = apiservice.getAttributeValues("8", product);
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
                            valueData = model.getData();
                            for (int i = 0; i < model.getData().size(); i++) {
                                kerbsarray.add(model.getData().get(i).getCa_value());
                            }
                        } else {
                            Utility.displayToast(MaterialScreenNew.this, model.getMessage());
                        }
                    }
                });

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, kerbsarray);
        dataAdapter.add("Select Kerbs");
        dataAdapter.setDropDownViewResource(R.layout.spinner_item);
        spn_kerbs.setAdapter(dataAdapter);
    }

    private void getcd() {
        avi.show();
        ArrayList<String> cdarray = new ArrayList<>();
        Observable<AttributeValueModel> responseObservable = apiservice.getAttributeValues("8", product);
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
                            valueData = model.getData();
                            for (int i = 0; i < model.getData().size(); i++) {
                                cdarray.add(model.getData().get(i).getCa_value());
                            }
                        } else {
                            Utility.displayToast(MaterialScreenNew.this, model.getMessage());
                        }
                    }
                });

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, cdarray);
        dataAdapter.add("Select Clay Drainage");
        dataAdapter.setDropDownViewResource(R.layout.spinner_item);
        spn_claydrinage.setAdapter(dataAdapter);
    }

    private void getpdw() {
        avi.show();
        ArrayList<String> pdwarray = new ArrayList<>();
        Observable<AttributeValueModel> responseObservable = apiservice.getAttributeValues("8", product);
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
                            valueData = model.getData();
                            for (int i = 0; i < model.getData().size(); i++) {
                                pdwarray.add(model.getData().get(i).getCa_value());
                            }
                        } else {
                            Utility.displayToast(MaterialScreenNew.this, model.getMessage());
                        }
                    }
                });

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, pdwarray);
        dataAdapter.add("Select Plastic Drainage Wavin");
        dataAdapter.setDropDownViewResource(R.layout.spinner_item);
        spn_pdw.setAdapter(dataAdapter);
    }

    private void getpr() {
        avi.show();
        ArrayList<String> prarray = new ArrayList<>();
        Observable<AttributeValueModel> responseObservable = apiservice.getAttributeValues("8", product);
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
                            valueData = model.getData();
                            for (int i = 0; i < model.getData().size(); i++) {
                                prarray.add(model.getData().get(i).getCa_value());
                            }
                        } else {
                            Utility.displayToast(MaterialScreenNew.this, model.getMessage());
                        }
                    }
                });

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, prarray);
        dataAdapter.add("Select Polysewer / Ridgidrain");
        dataAdapter.setDropDownViewResource(R.layout.spinner_item);
        spn_pr.setAdapter(dataAdapter);
    }

    private void getpdp() {
        avi.show();
        ArrayList<String> pdparray = new ArrayList<>();
        Observable<AttributeValueModel> responseObservable = apiservice.getAttributeValues("8", product);
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
                            valueData = model.getData();
                            for (int i = 0; i < model.getData().size(); i++) {
                                pdparray.add(model.getData().get(i).getCa_value());
                            }
                        } else {
                            Utility.displayToast(MaterialScreenNew.this, model.getMessage());
                        }
                    }
                });

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, pdparray);
        dataAdapter.add("Select Plastic Drainage Polypipe");
        dataAdapter.setDropDownViewResource(R.layout.spinner_item);
        spn_pdp.setAdapter(dataAdapter);
    }

    private void getst() {
        avi.show();
        ArrayList<String> starray = new ArrayList<>();
        Observable<AttributeValueModel> responseObservable = apiservice.getAttributeValues("8", product);
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
                            valueData = model.getData();
                            for (int i = 0; i < model.getData().size(); i++) {
                                starray.add(model.getData().get(i).getCa_value());
                            }
                        } else {
                            Utility.displayToast(MaterialScreenNew.this, model.getMessage());
                        }
                    }
                });

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, starray);
        dataAdapter.add("Select Small tools");
        dataAdapter.setDropDownViewResource(R.layout.spinner_item);
        spn_st.setAdapter(dataAdapter);
    }

    private void getPPE() {
        avi.show();
        ArrayList<String> ppearray = new ArrayList<>();
        Observable<AttributeValueModel> responseObservable = apiservice.getAttributeValues("8", product);
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
                            valueData = model.getData();
                            for (int i = 0; i < model.getData().size(); i++) {
                                ppearray.add(model.getData().get(i).getCa_value());
                            }
                        } else {
                            Utility.displayToast(MaterialScreenNew.this, model.getMessage());
                        }
                    }
                });

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, ppearray);
        dataAdapter.add("Select PPE");
        dataAdapter.setDropDownViewResource(R.layout.spinner_item);
        spn_ppe.setAdapter(dataAdapter);
    }

    private void getgbm() {
        avi.show();
        ArrayList<String> gbmarray = new ArrayList<>();
        Observable<AttributeValueModel> responseObservable = apiservice.getAttributeValues("8", product);
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
                            valueData = model.getData();
                            for (int i = 0; i < model.getData().size(); i++) {
                                gbmarray.add(model.getData().get(i).getCa_value());
                            }
                        } else {
                            Utility.displayToast(MaterialScreenNew.this, model.getMessage());
                        }
                    }
                });

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, gbmarray);
        dataAdapter.add("Select General building material");
        dataAdapter.setDropDownViewResource(R.layout.spinner_item);
        spn_gbm.setAdapter(dataAdapter);
    }

    private void getAttribute() {
        avi.show();
        ArrayList<String> attriValueArray = new ArrayList<>();
        Observable<AttributeValueModel> responseObservable = apiservice.getAttributeValues("8", product);
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
                            valueData = model.getData();
                            for (int i = 0; i < model.getData().size(); i++) {
                                attriValueArray.add(model.getData().get(i).getCa_value());
                            }
                        } else {
                            Utility.displayToast(MaterialScreenNew.this, model.getMessage());
                        }
                    }
                });

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, attriValueArray);
        dataAdapter.add("Select Mesh");
        dataAdapter.setDropDownViewResource(R.layout.spinner_item);
        spn_mesh.setAdapter(dataAdapter);
    }

    private void getsubAttributeValue() {

        avi.show();
        ArrayList<String> subattrivalueArray = new ArrayList<>();
        Observable<AttributeModel> responseObservable = apiservice.getAttributevalue("8", cd1);
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
                .subscribe(new Observer<AttributeModel>() {
                    @Override
                    public void onCompleted() {
                        avi.hide();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("error", "" + e.getMessage());
                    }

                    @Override
                    public void onNext(AttributeModel model) {
                        avi.hide();

                        statusCode = model.getStatusCode();
                        if (statusCode == 200) {
                            productdata = model.getData();
                            for (int i = 0; i < model.getData().size(); i++) {
                                subattrivalueArray.add(model.getData().get(i).getCa_value());
                            }
                        }
                    }
                });

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, subattrivalueArray);
        dataAdapter.add("Select Concrete drainage product");
        dataAdapter.setDropDownViewResource(R.layout.spinner_item);
        spn_cd2.setAdapter(dataAdapter);
    }

    private void getSubAttribute() {

        avi.show();
        ArrayList<String> subattriArray = new ArrayList<>();
        Observable<AttributeModel> responseObservable = apiservice.getsubAttribute("8", product);
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
                .subscribe(new Observer<AttributeModel>() {
                    @Override
                    public void onCompleted() {
                        avi.hide();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("error", "" + e.getMessage());
                    }

                    @Override
                    public void onNext(AttributeModel model) {
                        avi.hide();

                        statusCode = model.getStatusCode();
                        if (statusCode == 200) {
                            productdata = model.getData();
                            for (int i = 0; i < model.getData().size(); i++) {
                                subattriArray.add(model.getData().get(i).getCa_sub_label());
                            }
                        } else {
                            Utility.displayToast(MaterialScreenNew.this, model.getMessage());
                        }
                    }
                });

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, subattriArray);
        dataAdapter.add("Select Concrete drainage");
        dataAdapter.setDropDownViewResource(R.layout.spinner_item);
        spn_cd1.setAdapter(dataAdapter);
    }

    private void getMaterialProduct() {
        avi.show();
        ArrayList<String> productarray = new ArrayList<>();
        Observable<AttributeModel> responseObservable = apiservice.getAttribute("8");
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
                .subscribe(new Observer<AttributeModel>() {
                    @Override
                    public void onCompleted() {
                        avi.hide();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("error", "" + e.getMessage());
                    }

                    @Override
                    public void onNext(AttributeModel model) {
                        avi.hide();

                        statusCode = model.getStatusCode();
                        if (statusCode == 200) {
                            productdata = model.getData();
                            for (int i = 0; i < model.getData().size(); i++) {
                                productarray.add(model.getData().get(i).getCa_label());
                            }
                        }
                    }
                });

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, productarray);
        dataAdapter.add("Select Product");
        dataAdapter.setDropDownViewResource(R.layout.spinner_item);
        spn_product.setAdapter(dataAdapter);

    }
}