package com.athena.group.MobilePlant;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.athena.group.API.ApiInterface;
import com.athena.group.API.ApiServiceCreator;
import com.athena.group.Model.Model;
import com.athena.group.Model.SiteContractorModel;
import com.athena.group.Model.SpinnerModel;
import com.athena.group.R;
import com.athena.group.application.SessionManager;
import com.athena.group.application.Utility;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;
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

public class MblPlntChekActivity extends AppCompatActivity {

    @BindView(R.id.toolbar_Title)
    TextView toolbar_Title;
    @BindView(R.id.back_icon)
    ImageView back_icon;
    @BindView(R.id.avi)
    AVLoadingIndicatorView avi;

    //    @BindView(R.id.edt_contrctname)
//    EditText edt_contrctname;
//    @BindView(R.id.edt_plnttype)
//    EditText edt_plnttype;
    @BindView(R.id.spn_plant)
    Spinner spn_plant;
    @BindView(R.id.spn_contractor)
    Spinner spn_contractor;
    @BindView(R.id.spn_contractor_no)
    Spinner spn_contractor_no;
    @BindView(R.id.edt_plntmodl)
    EditText edt_plntmodl;
    @BindView(R.id.spn_operatr)
    SearchableSpinner spn_operatr;
    @BindView(R.id.edt_cmpnynme)
    EditText edt_cmpnynme;
   /* @BindView(R.id.edt_wc)
    EditText edt_wc;
    @BindView(R.id.edt_we)
    EditText edt_we;*/

    @BindView(R.id.img_clndr)
    ImageView img_clndr;
    @BindView(R.id.img_clndr1)
    ImageView img_clndr1;
    @BindView(R.id.img_clndr2)
    ImageView img_clndr2;
    @BindView(R.id.img_clndrwc)
    ImageView img_clndrwc;
    @BindView(R.id.img_clndrwe)
    ImageView img_clndrwe;
    @BindView(R.id.txt_date_wc)
    TextView txt_date_wc;
    @BindView(R.id.txt_date_we)
    TextView txt_date_we;
    @BindView(R.id.txt_date)
    TextView txt_date;
    @BindView(R.id.txt_date1)
    TextView txt_date1;
    @BindView(R.id.txt_date2)
    TextView txt_date2;

    @BindView(R.id.btn_next)
    LinearLayout btn_next;
    List<SiteContractorModel.Data> data1;
    List<SiteContractorModel.Data> data2;
    ApiInterface apiservice;
    int statusCode;
    SessionManager sessionManager;
    final Calendar myCalendar = Calendar.getInstance();
    String contract_name = "", contract_no = "", txtdate = "", txtdate1 = "", txtdate2 = "", txtdate_wc = "", txtdate_we = "", contractor = "", c_id = "", plant = "", p_id = "", site_id = "";
    List<SpinnerModel.Data> data;
    ArrayList<String> sitearray = new ArrayList<>();
    ArrayList<String> sitenoarray = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_mbl_plnt_chek);
        ButterKnife.bind(this);
        sessionManager = new SessionManager(MblPlntChekActivity.this);
        apiservice = ApiServiceCreator.createService("latest");
        toolbar_Title.setText(getString(R.string.mblplntchecklist));
        back_icon.setOnClickListener(view -> finish());
        spn_operatr.setTitle("Select Operator Name");
        initView();
    }

    private void initView() {

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
        DatePickerDialog.OnDateSetListener date1 = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel1();
            }
        };
        DatePickerDialog.OnDateSetListener date2 = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel2();
            }
        };
        DatePickerDialog.OnDateSetListener datewc = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelwc();
            }
        };
        DatePickerDialog.OnDateSetListener datewe = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelwe();
            }
        };
        txt_date.setText(Utility.date);
        txt_date1.setText(Utility.date);
        txt_date2.setText(Utility.date);
        txt_date_wc.setText(Utility.date);
        txt_date_we.setText(Utility.date);
        txtdate = Utility.date;
        txtdate1 = Utility.date;
        txtdate2 = Utility.date;
        txtdate_wc = Utility.date;
        txtdate_we = Utility.date;

        img_clndr.setOnClickListener(view -> {
            new DatePickerDialog(MblPlntChekActivity.this, date, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)).show();
        });
        img_clndr1.setOnClickListener(view -> {
            new DatePickerDialog(MblPlntChekActivity.this, date1, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)).show();
        });
        img_clndr2.setOnClickListener(view -> {
            new DatePickerDialog(MblPlntChekActivity.this, date2, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)).show();
        });
        img_clndrwc.setOnClickListener(view -> {
            new DatePickerDialog(MblPlntChekActivity.this, datewc, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)).show();
        });
        img_clndrwe.setOnClickListener(view -> {
            new DatePickerDialog(MblPlntChekActivity.this, datewe, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)).show();
        });
        getContractorName();
        spn_operatr.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (spn_operatr.getSelectedItem() == "Select Operator Name") {
                    contractor = "";
                    c_id = "";
                    //Do nothing.
                } else {
                    contractor = spn_operatr.getSelectedItem().toString();
                    Log.e("Co", contract_name);
                    SiteContractorModel.Data datalist1 = data1.get(i - 1);
                    c_id = datalist1.getUserId();
                    Log.e("Co", c_id);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        getSiteNumber();
        //getSiteName();
        spn_contractor_no.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (spn_contractor_no.getSelectedItem() == "Select Site") {
                    contract_no = "";
                    //Do nothing.
                } else {
                    contract_no = spn_contractor_no.getSelectedItem().toString();
                    Log.e("contract_no", contract_no);
                    String[] separated = contract_no.split(",");
                    try {
                        contract_no = separated[0];
                        contract_name = separated[1];
                    } catch (Exception e) {

                    }

                    // Log.e("contract_no",contract_no);
                    //Log.e("contract_name",contract_name);
                    //Log.e("Number is",contract_no);
//                    /*for (int j=1;j<data.size();j++)
//                    {
//                        if (contract_no.equals(data.get(j).getSite_number()))
//                        {
//                            spn_contractor.setSelection(j);
//                        }
//                    }*/
                    //New Api Call Shirish
                    // getSiteName1();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        spn_contractor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (spn_contractor.getSelectedItem() == "Select Site") {
                    contract_name = "";
                    //Do nothing.
                } else {
                    //   contract_name = spn_contractor.getSelectedItem().toString();

                    //New Api Call Shirish
                    Log.e("Site name is ", contract_name);
                   /* for (int j=1;j<data.size();j++)
                    {
                      *//*  if (contract_name.equals(data.get(j).getSiteName()))
                        {
                            spn_contractor_no.setSelection(j);
                        }*//*

                     if (contract_name.equals(data.get(j).getSiteName()))
                        {
                            Log.e("Site id is",data.get(j).getSiteId());
                            if (site_id.equals(""))
                            {
                                site_id=data.get(j).getSiteId();
                                getSiteNumber1();
                            }
                           else if (site_id.equals(data.get(j).getSiteId()))
                            {

                            }
                            else {
                                site_id=data.get(j).getSiteId();
                                getSiteNumber1();
                            }

                        }
                        else {

                        }
                    }*/
                    //getSiteNumber();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        getPlant();
        spn_plant.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (spn_plant.getSelectedItem() == "Select Plant") {
                    plant = "";
                    //Do nothing.
                } else {
                    plant = spn_plant.getSelectedItem().toString();
                    SiteContractorModel.Data datalist2 = data2.get(i - 1);
                    p_id = datalist2.getFa_id();
                    Log.e("TAG", "onItemSelected: " + p_id);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btn_next.setOnClickListener(view -> {

            /*if (contract_no.equals("")) {
                Utility.displayToast(getApplicationContext(), "Please Select Contract No");
            } else */
            if (contract_no.equals("")) {
                Utility.displayToast(getApplicationContext(), "Please Select Site");
            } else if (p_id.equals("")) {
                Utility.displayToast(getApplicationContext(), "Please Select Plant");
            } else if (edt_plntmodl.getText().toString().equals("")) {
                Utility.displayToast(getApplicationContext(), "Please Enter Plant Model");
            } else if (c_id.equals("")) {
                Utility.displayToast(getApplicationContext(), "Please Select Operator Name");
            } else if (edt_cmpnynme.getText().toString().equals("")) {
                Utility.displayToast(getApplicationContext(), "Please Enter Company Name");
            } else {
                callNextApi();
            }
        });
    }

    private void callNextApi() {
        Log.e("c_id", c_id);
        // Log.e(" edt_plntmodl.getText().toString()", edt_plntmodl.getText().toString());

        avi.show();
        Observable<Model> responseObservable = apiservice.insert_mblFirstData(
                sessionManager.getKeyId(),
                //edt_contrctname.getText().toString(),
                contract_name,
                contract_no,
                //edt_contcrctno.getText().toString(),
                //edt_plnttype.getText().toString(),
                p_id,
                txtdate,
                edt_plntmodl.getText().toString(),
                //edt_oprtrname.getText().toString(),
                c_id,
                edt_cmpnynme.getText().toString(),
//                edt_wc.getText().toString(),
//                edt_we.getText().toString(),
                txtdate_wc,
                txtdate_we,
                txtdate1,
                txtdate2);

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
                .subscribe(new Observer<Model>() {
                    @Override
                    public void onCompleted() {
                        avi.hide();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("error", "" + e.getMessage());
                    }

                    @Override
                    public void onNext(Model model) {
                        avi.hide();
                        statusCode = model.getStatusCode();
                        if (statusCode == 200) {

                            Utility.displayToast(getApplicationContext(), model.getMessage());
                            Log.e("TAG", "onNext: " + model.getData());
                            Utility.getAppcon().getSession().mbl_last_step_val = model.getData();

                            Intent intent = new Intent(getApplicationContext(), MblChckNxtActivity.class);
                            startActivity(intent);
                        } else {
                            Utility.displayToast(getApplicationContext(), model.getMessage());
                        }
                    }
                });

    }

    private void getSiteNumber() {

        avi.show();
        // Observable<SpinnerModel> responseObservable = apiservice.get_site();
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
                                sitenoarray.add(model.getData().get(i).getSite_number() + " , " + model.getData().get(i).getSiteName());
                            }
                        } else {
                            Utility.displayToast(getApplicationContext(), model.getMessage());
                        }
                    }
                });

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item, sitenoarray);
        dataAdapter.add("Select Site");
        dataAdapter.setDropDownViewResource(R.layout.spinner_item);
        spn_contractor_no.setAdapter(dataAdapter);
    }


    //New Api Call Shirish
    private void getSiteNumber1() {

        avi.show();
        Observable<SpinnerModel> responseObservable = apiservice.get_site2(site_id);

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
                            spn_contractor_no.setSelection(1);
                            for (int i = 0; i < model.getData().size(); i++) {
                                sitenoarray.add(model.getData().get(i).getSite_number());
                            }
                        } else {
                            Utility.displayToast(getApplicationContext(), model.getMessage());
                        }
                    }
                });

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item, sitenoarray);
        dataAdapter.clear();
        dataAdapter.add("Select Contract No.");
        dataAdapter.setDropDownViewResource(R.layout.spinner_item);
        spn_contractor_no.setAdapter(dataAdapter);
    }

    private void getSiteName() {
        avi.show();
        Observable<SpinnerModel> responseObservable = apiservice.get_site();

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
                                sitearray.add(model.getData().get(i).getSiteName());
                            }
                        } else {
                            Utility.displayToast(getApplicationContext(), model.getMessage());
                        }
                    }
                });

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item, sitearray);

        dataAdapter.add("Select Site");
        dataAdapter.setDropDownViewResource(R.layout.spinner_item);
        spn_contractor.setAdapter(dataAdapter);
    }

    //New Api Call Shirish
    private void getSiteName1() {

        avi.show();
        Observable<SpinnerModel> responseObservable = apiservice.get_site1(contract_no);

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

                        Log.e("status code ", String.valueOf(statusCode));
                        if (statusCode == 200) {
                            data = model.getData();
                            spn_contractor.setSelection(1);
                            for (int i = 0; i < model.getData().size(); i++) {
                                sitearray.add(model.getData().get(i).getSiteName());
                            }

                        } else {
                            Utility.displayToast(getApplicationContext(), model.getMessage());
                        }
                    }
                });

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item, sitearray);
        dataAdapter.clear();
        dataAdapter.add("Select Site");
        dataAdapter.setDropDownViewResource(R.layout.spinner_item);
        spn_contractor.setAdapter(dataAdapter);
    }


    private void getContractorName() {

        ArrayList<String> contractorarray = new ArrayList<>();
        avi.show();

        Observable<SiteContractorModel> responseObservable = apiservice.get_allSub_contractor();
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
                .subscribe(new Observer<SiteContractorModel>() {
                    @Override
                    public void onCompleted() {
                        avi.hide();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("error", "" + e.getMessage());
                    }

                    @Override
                    public void onNext(SiteContractorModel model) {
                        avi.hide();

                        statusCode = model.getStatusCode();
                        if (statusCode == 200) {
                            data1 = model.getData();
                            for (int i = 0; i < model.getData().size(); i++) {
                                contractorarray.add(model.getData().get(i).getName() + " " + model.getData().get(i).getLname());
                            }
                        } else {
                            Utility.displayToast(getApplicationContext(), model.getMessage());
                        }
                    }
                });

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, contractorarray);
        dataAdapter.add("Select Operator Name");
        dataAdapter.setDropDownViewResource(R.layout.spinner_item);
        spn_operatr.setAdapter(dataAdapter);
    }

    private void getPlant() {

        ArrayList<String> plantarray = new ArrayList<>();
        avi.show();

        Observable<SiteContractorModel> responseObservable = apiservice.get_allPlant();
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
                .subscribe(new Observer<SiteContractorModel>() {
                    @Override
                    public void onCompleted() {
                        avi.hide();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("error", "" + e.getMessage());
                    }

                    @Override
                    public void onNext(SiteContractorModel model) {
                        avi.hide();

                        statusCode = model.getStatusCode();
                        if (statusCode == 200) {
                            data2 = model.getData();
                            for (int i = 0; i < model.getData().size(); i++) {
                                plantarray.add(model.getData().get(i).getFa_label());
                            }
                        } else {
                            Utility.displayToast(getApplicationContext(), model.getMessage());
                        }
                    }
                });

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, plantarray);
        dataAdapter.add("Select Plant");
        dataAdapter.setDropDownViewResource(R.layout.spinner_item);
        spn_plant.setAdapter(dataAdapter);
    }

    private void updateLabel() {
        String myFormat = "dd-MM-yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        txt_date.setText(sdf.format(myCalendar.getTime()));   // for display
        txtdate = sdf.format(myCalendar.getTime());
    }

    private void updateLabel1() {
        String myFormat = "dd-MM-yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        txt_date1.setText(sdf.format(myCalendar.getTime()));   // for display
        txtdate1 = sdf.format(myCalendar.getTime());
    }

    private void updateLabel2() {
        String myFormat = "dd-MM-yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        txt_date2.setText(sdf.format(myCalendar.getTime()));   // for display
        txtdate2 = sdf.format(myCalendar.getTime());
    }

    private void updateLabelwc() {
        String myFormat = "dd-MM-yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        txt_date_wc.setText(sdf.format(myCalendar.getTime()));   // for display
        txtdate_wc = sdf.format(myCalendar.getTime());
    }

    private void updateLabelwe() {
        String myFormat = "dd-MM-yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        txt_date_we.setText(sdf.format(myCalendar.getTime()));   // for display
        txtdate_we = sdf.format(myCalendar.getTime());
    }
}
