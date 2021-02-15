package com.athena.group.PlaceOrder;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Observer;
import rx.schedulers.Schedulers;

public class MaterialScreen extends AppCompatActivity {

    @BindView(R.id.spn_site)
    Spinner spn_site;
    @BindView(R.id.back_icon)
    ImageView back_icon;
    @BindView(R.id.toolbar_Title)
    TextView toolbar_Title;
    @BindView(R.id.badge_cart)
    TextView badge_cart;
    @BindView(R.id.btnDatePicker)
    RelativeLayout btnDatePicker;
    @BindView(R.id.calender)
    TextView calender;
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
    String site = "", s_id = "", txtdate = "";
    final Calendar myCalendar = Calendar.getInstance();
    List<SpinnerModel.Data> data;
    int statusCode;
    ApiInterface apiservice;
    SessionManager sessionManager;
    private Dialog dialog;
    Spinner spinner_attr, spinner_value;
    String concreteAttribute = "", concreteAttributevalue = "";
    ArrayList<String> productarray, AttributeArraydata, AttributeArrayValue;
    List<AttributeModel.Data> data3;
    List<AttributeModel.Data> data2;
    @BindView(R.id.spn_attribute1)
    Spinner spn_attribute1;
    @BindView(R.id.spn_attribute2)
    Spinner spn_attribute2;
    @BindView(R.id.spn_attribute3)
    Spinner spn_attribute3;
    @BindView(R.id.spn_attribute4)
    Spinner spn_attribute4;
    @BindView(R.id.txt_concrete_dringe)
    TextView txt_concrete_dringe;
    @BindView(R.id.ln_cart_icon)
    LinearLayout ln_cart_icon;
    String product = "", attributeNAme1 = "", attributeNAme2 = "", attributeNAme3 = "", attributeNAme4 = "", attributeType = "";
    private int count = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material_screen);

        ButterKnife.bind(this);
        sessionManager = new SessionManager(MaterialScreen.this);
        apiservice = ApiServiceCreator.createService("latest");
        toolbar_Title.setText("Material - Place Order");
        ln_cart_icon.setVisibility(View.VISIBLE);


        initViews();

    }

    private void initViews() {
        back_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        ln_cart_icon.setOnClickListener(view -> {
            Intent intent = new Intent(MaterialScreen.this, CartActivity.class);
            startActivity(intent);
        });
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
        };

        btnDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(MaterialScreen.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

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


        getSiteName();

        getAttributename1();
        txt_concrete_dringe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openDialog();
            }
        });

        spn_attribute1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (spn_attribute1.getSelectedItem() == "Select Mesh and accessories") {
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

        spn_attribute2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (spn_attribute2.getSelectedItem() == "Select General Building Material") {
                    product = "";
                    //Do nothing.
                } else {
                    attributeNAme2 = spn_attribute2.getSelectedItem().toString();

                    //  getAttributeValue(p_id, attributeNAme);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spn_attribute3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (spn_attribute3.getSelectedItem() == "Select PPE") {
                    product = "";
                    //Do nothing.
                } else {
                    attributeNAme3 = spn_attribute3.getSelectedItem().toString();

                    //  getAttributeValue(p_id, attributeNAme);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spn_attribute4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (spn_attribute4.getSelectedItem() == "Select Small Tools") {
                    product = "";
                    //Do nothing.
                } else {
                    attributeNAme4 = spn_attribute4.getSelectedItem().toString();

                    //  getAttributeValue(p_id, attributeNAme);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btn_placeorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (s_id.equals("")) {
                    Utility.displayToast(getApplicationContext(), "Please Select Site");
                } else {
                    // callPlaceOrderApi();
                }
            }
        });

        btn_addcart.setOnClickListener(view -> {

            // callAddToCartApi();
        });
    }

    private void updateLabel() {
        String myFormat = "dd-MM-yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        calender.setText(sdf.format(myCalendar.getTime()));
        txtdate = sdf.format(myCalendar.getTime());
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
        spn_site.setAdapter(dataAdapter);
    }


    private void openDialog() {

        dialog = new Dialog(MaterialScreen.this);
        dialog.getWindow();
        dialog.setTitle("Address");
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.concrete_layout);
        dialog.setCancelable(true);

        spinner_attr = dialog.findViewById(R.id.spinner_attr);
        spinner_value = dialog.findViewById(R.id.spinner_value);
        Button txt_ok = dialog.findViewById(R.id.btn_ok);
        ImageView txt_cancel = dialog.findViewById(R.id.img_close);

        txt_ok.setOnClickListener(view -> {
//            concreteAttribute + "   " +
            if (!concreteAttribute.equals("") && !concreteAttributevalue.equals("")) {
                txt_concrete_dringe.setText(concreteAttributevalue);
                dialog.cancel();
                dialog.dismiss();
            } else {
                Toast.makeText(this, "Please Select First", Toast.LENGTH_SHORT).show();
            }

        });

        txt_cancel.setOnClickListener(view -> {
            dialog.cancel();
            dialog.dismiss();
        });

        getAttributeData();
        spinner_attr.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (spinner_attr.getSelectedItem() == "Select Category") {
                    concreteAttribute = "";
                    //Do nothing.
                } else {
                    concreteAttribute = spinner_attr.getSelectedItem().toString();
                    getAttributeValue();
//                    SpinnerModel.Data datalist = data.get(i - 1);
//                    s_id = datalist.getSiteId();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinner_value.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (spinner_value.getSelectedItem() == "Select Category Value") {
                    concreteAttributevalue = "";
                    //Do nothing.
                } else {
                    concreteAttributevalue = spinner_value.getSelectedItem().toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        dialog.show();
        Window window = dialog.getWindow();
        assert window != null;
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

    }

    public void getAttributeValue() {
        AttributeArrayValue = new ArrayList<>();
        avi.show();

        Observable<AttributeModel> responseObservable = apiservice.getAttributevalue("8", concreteAttribute);

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
                        //getAttributename1(p_id);
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
                            data3 = model.getData();
                            for (int i = 0; i < model.getData().size(); i++) {
                                AttributeArrayValue.add(model.getData().get(i).getCa_value());
                            }
                        }
                    }
                });

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, AttributeArrayValue);
        dataAdapter.add("Select Category Value");
        dataAdapter.setDropDownViewResource(R.layout.spinner_item);
        spinner_value.setAdapter(dataAdapter);
    }

    public void getAttributeData() {
        AttributeArraydata = new ArrayList<>();
        avi.show();

        Observable<AttributeModel> responseObservable = apiservice.getsubAttribute("8", "Concrete Drainage");

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
                        //getAttributename1(p_id);
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
                            data2 = model.getData();
                            for (int i = 0; i < model.getData().size(); i++) {
                                AttributeArraydata.add(model.getData().get(i).getCa_sub_label());
                            }
                        }
                    }
                });

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, AttributeArraydata);
        dataAdapter.add("Select Category");
        dataAdapter.setDropDownViewResource(R.layout.spinner_item);
        spinner_attr.setAdapter(dataAdapter);
    }

    public void getAttributename1() {
        ArrayList<String> AttributeArray = new ArrayList<>();

       /* pDialog = new ProgressDialog(this);
        pDialog.setTitle("Checking Data");
        pDialog.setMessage("Please Wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();*/
        avi.show();

        Observable<AttributeValueModel> responseObservable = apiservice.getAttributeValues("8", "Mesh and accessories");

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
        dataAdapter.add("Select Mesh and accessories");
        dataAdapter.setDropDownViewResource(R.layout.spinner_item);
        spn_attribute1.setAdapter(dataAdapter);

    }

    public void getAttributename2() {
        ArrayList<String> AttributeArray = new ArrayList<>();

        /*pDialog = new ProgressDialog(this);
        pDialog.setTitle("Checking Data");
        pDialog.setMessage("Please Wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();*/
        avi.show();

        Observable<AttributeValueModel> responseObservable = apiservice.getAttributeValues("8", "General building materials");

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
                            //List<AttributeModel.Data> data = model.getData();
                            for (int i = 0; i < model.getData().size(); i++) {
                                AttributeArray.add(model.getData().get(i).getCa_value());
                            }
                        }
                    }
                });

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, AttributeArray);
        dataAdapter.add("Select General Building Material");
        dataAdapter.setDropDownViewResource(R.layout.spinner_item);
        spn_attribute2.setAdapter(dataAdapter);

    }

    public void getAttributenam3() {
        ArrayList<String> AttributeArray = new ArrayList<>();

        /*pDialog = new ProgressDialog(this);
        pDialog.setTitle("Checking Data");
        pDialog.setMessage("Please Wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();*/
        avi.show();

        Observable<AttributeValueModel> responseObservable = apiservice.getAttributeValues("8", "PPE");

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
                            //List<AttributeModel.Data> data = model.getData();
                            for (int i = 0; i < model.getData().size(); i++) {
                                AttributeArray.add(model.getData().get(i).getCa_value());
                            }
                        }
                    }
                });

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, AttributeArray);
        dataAdapter.add("Select PPE");
        dataAdapter.setDropDownViewResource(R.layout.spinner_item);
        spn_attribute3.setAdapter(dataAdapter);

    }

    public void getAttributenam4() {
        ArrayList<String> AttributeArray = new ArrayList<>();

       /* pDialog = new ProgressDialog(this);
        pDialog.setTitle("Checking Data");
        pDialog.setMessage("Please Wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();*/
        avi.show();

        Observable<AttributeValueModel> responseObservable = apiservice.getAttributeValues("8", "Small tools");

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
                            //List<AttributeModel.Data> data = model.getData();
                            for (int i = 0; i < model.getData().size(); i++) {
                                AttributeArray.add(model.getData().get(i).getCa_value());
                            }
                        }
                    }
                });

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, AttributeArray);
        dataAdapter.add("Select Small Tools");
        dataAdapter.setDropDownViewResource(R.layout.spinner_item);
        spn_attribute4.setAdapter(dataAdapter);

    }

    public void IncreaseCartCount() {
        int co = count + 1;
        String c = String.valueOf(co);
        sessionManager.setCartCount(c);
        badge_cart.setText(c);
    }

/*
    private void callAddToCartApi() {

        String pattern = "HH:mm:ss";
        DateFormat df = new SimpleDateFormat(pattern);
        Date today = Calendar.getInstance().getTime();
        String todayAsString = df.format(today);

        try {
            avi.show();

            Observable<SpinnerModel> responseObservable = apiservice.addCart_material(
                    s_id,
                    "8",
                    concreteAttribute,
                    txt_concrete_dringe.getText().toString(),
                    attributeNAme1,
                    attributeNAme2,
                    attributeNAme3,
                    attributeNAme4,
                    edtQty.getText().toString(),
                    todayAsString,
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
                                Utility.displayToast(MaterialScreen.this, model.getMessage());
                                IncreaseCartCount();
                            } else {
                                Utility.displayToast(MaterialScreen.this, model.getMessage());
                            }
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
*/

/*
    private void callPlaceOrderApi() {

        String pattern = "HH:mm:ss";
        DateFormat df = new SimpleDateFormat(pattern);
        Date today = Calendar.getInstance().getTime();
        String todayAsString = df.format(today);

        if (s_id.equals("")) {
            Utility.displayToast(this, "Please Select Site");
        } else if (txt_concrete_dringe.getText().toString().equals("")) {
            Utility.displayToast(this, "Please Select Concrete Drainage");
        } else if (attributeNAme1.equals("")) {
            Utility.displayToast(this, "Please Select Mesh and accessories");
        } else if (attributeNAme2.equals("")) {
            Utility.displayToast(this, "Please Select general building material");
        } else if (attributeNAme3.equals("")) {
            Utility.displayToast(this, "Please Select ppe");
        } else if (attributeNAme4.equals("")) {
            Utility.displayToast(this, "Please Select small tools");
        } else if (edtQty.getText().toString().equals("")) {
            Utility.displayToast(this, "Please Enter Quantity");
        } else if (edtNotes.getText().toString().equals("")) {
            Utility.displayToast(this, "Please Enter Notes");
        } else if (txtdate.equals("")) {
            Utility.displayToast(this, "Please Select Date");
        } else {
            try {
                   */
/* pDialog = new ProgressDialog(this);
                    pDialog.setTitle("Checking Data");
                    pDialog.setMessage("Please Wait...");
                    pDialog.setIndeterminate(false);
                    pDialog.setCancelable(false);
                    pDialog.show();*//*

                avi.show();
                btn_placeorder.setEnabled(false);


                Observable<SpinnerModel> responseObservable = apiservice.placeOrder_material(
                        s_id,
                        "8",
                        concreteAttribute,
                        txt_concrete_dringe.getText().toString(),
                        attributeNAme1,
                        attributeNAme2,
                        attributeNAme3,
                        attributeNAme4,
                        edtQty.getText().toString(),
                        todayAsString,
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
                                // btn_placeorder.setEnabled(false);

                                statusCode = model.getStatusCode();
                                if (statusCode == 200) {
                                    Utility.displayToast(MaterialScreen.this, model.getMessage());
                                    Intent intent = new Intent(MaterialScreen.this, PlaceOrder.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Utility.displayToast(MaterialScreen.this, model.getMessage());
                                }
                            }
                        });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
*/

    @Override
    protected void onResume() {
        super.onResume();
        badge_cart.setText(sessionManager.getCartCount());
    }

}
