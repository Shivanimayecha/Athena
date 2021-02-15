package com.athena.group.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.athena.group.API.ApiInterface;
import com.athena.group.API.ApiServiceCreator;
import com.athena.group.FileUtilisDoc;
import com.athena.group.FileUtils;
import com.athena.group.MainActivity;
import com.athena.group.Model.JobReviewModel;
import com.athena.group.Model.Model;
import com.athena.group.Model.PastHsModel;
import com.athena.group.Model.SiteContractorModel;
import com.athena.group.Model.SpinnerModel;
import com.athena.group.Model.Uri_Model;
import com.athena.group.Model.labourmodel;
import com.athena.group.Model.planetmodel;
import com.athena.group.R;
import com.athena.group.adapter.JobReportAdapter;
import com.athena.group.adapter.MblQuestick_Plant_Adapter;
import com.athena.group.adapter.MblQuestick_labourAdapter;
import com.athena.group.adapter.MblQuestick_labourAdapter1;
import com.athena.group.adapter.MblQuestick_plantAdapter1;
import com.athena.group.adapter.MyAdapter;
import com.athena.group.adapter.MyAdapter1;
import com.athena.group.application.SessionManager;
import com.athena.group.application.Utility;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;
import com.wang.avi.AVLoadingIndicatorView;

import java.io.File;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.zelory.compressor.Compressor;
import io.reactivex.annotations.NonNull;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Observable;
import rx.Observer;
import rx.schedulers.Schedulers;

import static android.content.Intent.ACTION_GET_CONTENT;

public class JobReviewActivity extends AppCompatActivity implements View.OnClickListener {

    /*@BindView(R.id.spn_plant)
    Spinner spn_plant;*/
    /*@BindView(R.id.spn_labour)
    Spinner spn_labour;*/
    /*@BindView(R.id.spn_qty)
    Spinner spn_qty;*/
     /*@BindView(R.id.liner)
    LinearLayout liner;*/
    /*@BindView(R.id.spn_qty1)
    Button spn_qty1;*/
        /*@BindView(R.id.spn_plant1)
    Button spn_plant1;*/

    @BindView(R.id.toolbar_Title)
    TextView toolbar_Title;
    @BindView(R.id.back_icon)
    ImageView back_icon;
    @BindView(R.id.avi)
    AVLoadingIndicatorView avi;
    @BindView(R.id.btn_submit)
    Button btn_submit;
    @BindView(R.id.spn_site)
    Spinner spn_site;
    @BindView(R.id.rd1)
    RadioButton rd1;
    @BindView(R.id.rd2)
    RadioButton rd2;
    @BindView(R.id.rd3)
    RadioButton rd3;
    @BindView(R.id.rd4)
    RadioButton rd4;
    @BindView(R.id.rd5)
    RadioButton rd5;
    @BindView(R.id.rd6)
    RadioButton rd6;
    @BindView(R.id.edt_notes)
    EditText edt_notes;
    @BindView(R.id.edt_prgrms)
    EditText edt_prgrms;
    @BindView(R.id.edt_commnt)
    EditText edt_commnt;
    @BindView(R.id.edt_commnt1)
    EditText edt_commnt1;
    @BindView(R.id.edt_commnt2)
    EditText edt_commnt2;
    @BindView(R.id.listView)
    public GridView listView;
    @BindView(R.id.img_picibutton)
    LinearLayout img_picibutton;
    @BindView(R.id.spn_labour1)
    LinearLayout spn_labour1;
    @BindView(R.id.txt_labour1)
    TextView txt_labour1;
    @BindView(R.id.ll_plant)
    LinearLayout ll_plant;
    @BindView(R.id.txt_plant)
    TextView txt_plant;
    @BindView(R.id.txt_pdfpath)
    TextView txt_pdfpath;
    @BindView(R.id.ll_pdf)
    LinearLayout ll_pdf;
    @BindView(R.id.labouer_list)
    RecyclerView labouer_list;
    @BindView(R.id.card_labour)
    CardView card_labour;
    @BindView(R.id.card_plant)
    CardView card_plant;
    @BindView(R.id.img_clndr)
    ImageView img_clndr;
    @BindView(R.id.txt_date)
    TextView txt_date;
    @BindView(R.id.plant_list_rec)
    RecyclerView plant_list_rec1;

    ApiInterface apiservice;
    int statusCode;
    SessionManager sessionManager;
    private String site = "", s_id = "", p_id = "", contractor = "", c_id = "", qty = "", plant = "", daywrk_ans = "", issusite_ans = "", oustndng_ans = "";
    List<SpinnerModel.Data> data;
    List<SiteContractorModel.Data> data1;
    List<SiteContractorModel.Data> data2;
    ArrayList<String> sitearray = new ArrayList<>();
    ArrayList<String> arrayList = new ArrayList<>();
    private static final int REQUEST_CODE = 6384;
    //private ArrayList<Uri> imagesPathList;
    List<MultipartBody.Part> parts;
    List<MultipartBody.Part> parts1;
    MblQuestick_Plant_Adapter mblQuestick_plant_adapter;
    String check1 = "Yes", validataion, site_name = "";
    ViewGroup dropPreview;
    private static final int PICK_PDF_REQUEST = 1;
    Uri pdf_filePath;
    List<labourmodel> new_labourmodels_list = new ArrayList<>();
    List<labourmodel> combinedList = new ArrayList<>();
    List<planetmodel> combinedList1 = new ArrayList<>();
    List<labourmodel> labourmodels_list;
    labourmodel l_model;
    String labour_join = "", plant_join = "", qty_join = "", flag_operation = "insert", jb_id = "", plant_item = "", label_item = "", quntity_item = "", txtdate = "";
    final Calendar myCalendar = Calendar.getInstance();
    List<planetmodel> arrayListQuestion123;
    DatePickerDialog datePickerDialog;
    private static final int CAPTURE_IMAGE = 3;
    Uri mCapturedImageURI;
    ArrayAdapter<String> siteAdapter;
    String[] labourname;
    List<labourmodel> labourmodels;
    List<planetmodel> listp;
    String old_image = "";
    private ArrayList<Uri_Model> imagesPathList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_job_review_new);
        ButterKnife.bind(this);
        sessionManager = new SessionManager(JobReviewActivity.this);
        apiservice = ApiServiceCreator.createService("latest");
        toolbar_Title.setText(getString(R.string.jobreview));

        initView();
    }

    private void initView() {

        if (jb_id != null) {
            jb_id = getIntent().getStringExtra("jb_id");
        } else {
            jb_id = "";
        }
        txt_date.setText(Utility.date);
        txtdate = Utility.date;
        imagesPathList = new ArrayList<>();
        labourmodels_list = new ArrayList<>();
        dropPreview = getWindow().getDecorView().findViewById(android.R.id.content);
        Utility.getAppcon().getSession().filled_labourlist.clear();
        Utility.getAppcon().getSession().filled_plantlist.clear();
        editTouchListeners();
        clickListeners();
        getSiteName();
        spn_site.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (spn_site.getSelectedItem() == "Select site") {
                    site = "";
                    //Do nothing.
                } else {
                    try {
                        site = spn_site.getSelectedItem().toString();
                        SpinnerModel.Data datalist = data.get(i - 1);
                        s_id = datalist.getSiteId();
                        // site = datalist.getSite_number();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                   /* String[] separated = site.split(",");
                    //contract_no=separated[0];
                    site = separated[1];
                    Log.e("Sitename is ", site);*/
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        if (Utility.getAppcon().getSession().jobreview_operation.equals("update")) {
            btn_submit.setText("Update");
            flag_operation = "update";
            getJobReviewData();
        } else {
            btn_submit.setText("Submit");
        }
    }

    private void clickListeners() {
        back_icon.setOnClickListener(this);
        img_picibutton.setOnClickListener(this);
        rd1.setOnClickListener(this);
        rd2.setOnClickListener(this);
        rd3.setOnClickListener(this);
        rd4.setOnClickListener(this);
        rd5.setOnClickListener(this);
        rd6.setOnClickListener(this);
        ll_plant.setOnClickListener(this);
        ll_pdf.setOnClickListener(this);
        spn_labour1.setOnClickListener(this);
        img_clndr.setOnClickListener(this);
        btn_submit.setOnClickListener(this);
    }

    private void editTouchListeners() {
        edt_notes.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent event) {
                // TODO Auto-generated method stub
                if (view.getId() == R.id.edt_notes) {
                    view.getParent().requestDisallowInterceptTouchEvent(true);
                    switch (event.getAction() & MotionEvent.ACTION_MASK) {
                        case MotionEvent.ACTION_UP:
                            view.getParent().requestDisallowInterceptTouchEvent(false);
                            break;
                    }
                }
                return false;
            }
        });

        edt_prgrms.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent event) {
                // TODO Auto-generated method stub
                if (view.getId() == R.id.edt_prgrms) {
                    view.getParent().requestDisallowInterceptTouchEvent(true);
                    switch (event.getAction() & MotionEvent.ACTION_MASK) {
                        case MotionEvent.ACTION_UP:
                            view.getParent().requestDisallowInterceptTouchEvent(false);
                            break;
                    }
                }
                return false;
            }
        });

        edt_commnt.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent event) {
                // TODO Auto-generated method stub
                if (view.getId() == R.id.edt_commnt) {
                    view.getParent().requestDisallowInterceptTouchEvent(true);
                    switch (event.getAction() & MotionEvent.ACTION_MASK) {
                        case MotionEvent.ACTION_UP:
                            view.getParent().requestDisallowInterceptTouchEvent(false);
                            break;
                    }
                }
                return false;
            }
        });

        edt_commnt1.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent event) {
                // TODO Auto-generated method stub
                if (view.getId() == R.id.edt_commnt1) {
                    view.getParent().requestDisallowInterceptTouchEvent(true);
                    switch (event.getAction() & MotionEvent.ACTION_MASK) {
                        case MotionEvent.ACTION_UP:
                            view.getParent().requestDisallowInterceptTouchEvent(false);
                            break;
                    }
                }
                return false;
            }
        });

        edt_commnt2.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent event) {
                // TODO Auto-generated method stub
                if (view.getId() == R.id.edt_commnt2) {
                    view.getParent().requestDisallowInterceptTouchEvent(true);
                    switch (event.getAction() & MotionEvent.ACTION_MASK) {
                        case MotionEvent.ACTION_UP:
                            view.getParent().requestDisallowInterceptTouchEvent(false);
                            break;
                    }
                }
                return false;
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_icon:
                finish();
                break;
            case R.id.img_picibutton:
                selectImage();
                //showChooser();
                break;
            case R.id.rd1:
                daywrk_ans = "Yes";
                break;
            case R.id.rd2:
                daywrk_ans = "No";
                break;
            case R.id.rd3:
                issusite_ans = "Yes";
                break;
            case R.id.rd4:
                issusite_ans = "No";
                break;
            case R.id.rd5:
                oustndng_ans = "Yes";
                break;
            case R.id.rd6:
                oustndng_ans = "No";
                break;
            case R.id.ll_plant:
                openplant();
                break;
            case R.id.spn_labour1:
                openlaboure();
                break;
            case R.id.ll_pdf:
                showFileChooser();
                break;
            case R.id.img_clndr:
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                datePickerDialog = new DatePickerDialog(JobReviewActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                txt_date.setText(dayOfMonth + "-"
                                        + (monthOfYear + 1) + "-" + year);
                                //for pass in api
                                txtdate = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
                break;
            case R.id.btn_submit:
                if (s_id.equals("")) {
                    Utility.displayToast(getApplicationContext(), "Please Select Site");
                } else if (txt_labour1.getText().toString().equals("Select Contractor")) {
                    Utility.displayToast(getApplicationContext(), "Please Select Contractor");
                } /*else if (label_item.equals("")) {
                    Utility.displayToast(getApplicationContext(), "Please Select Contractor");
                }*/ else if (txt_plant.getText().toString().equals("Select Plant")) {
                    Utility.displayToast(getApplicationContext(), "Please Select Plant");
                } /*else if (plant_item.equals("")) {
                    Utility.displayToast(getApplicationContext(), "Please Select Plant");
                } else if (quntity_item.equals("")) {
                    Utility.displayToast(getApplicationContext(), "Please Select Quantity");
                }*/ else {
                    if (flag_operation.equals("insert")) {
                        callSubmitApi();
                    } else {
                        callUpdateApi();
                    }
                }
                break;
        }
    }

    private void getSiteName() {

        avi.show();
        //Chnage Shirish
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
                                sitearray.add(model.getData().get(i).getSite_number() + " , " + model.getData().get(i).getSiteName());
                            }
                            siteAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item, sitearray);
                            //siteAdapter.add("Select site");
                            siteAdapter.insert("Select site", 0);
                            siteAdapter.setDropDownViewResource(R.layout.spinner_item);
                            spn_site.setAdapter(siteAdapter);
                        } else {
                            Utility.displayToast(getApplicationContext(), model.getMessage());
                        }
                    }
                });
    }

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Pdf"), PICK_PDF_REQUEST);
    }

    private void openplant() {
        plant_item = "";
        quntity_item = "";
        AlertDialog.Builder builder = new AlertDialog.Builder(JobReviewActivity.this);
        View dialogView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.alert_laboure, dropPreview, false);
        RecyclerView rcv_member = dialogView.findViewById(R.id.rcv_member);
        ImageView img_close = dialogView.findViewById(R.id.img_close);
        TextView txt_title = dialogView.findViewById(R.id.txt_title);
        txt_title.setText("Select Plant");
        Button done = dialogView.findViewById(R.id.btn_done);
        RecyclerView.LayoutManager rcv_layoutManager = new LinearLayoutManager(JobReviewActivity.this);
        rcv_member.setLayoutManager(rcv_layoutManager);
        builder.setView(dialogView);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        RecyclerView.LayoutManager plant_list_rec = new LinearLayoutManager(this);
        plant_list_rec1.setLayoutManager(plant_list_rec);

        img_close.setOnClickListener(view -> alertDialog.dismiss());
        done.setOnClickListener(view -> {

            List<planetmodel> arrayListQuestion123 = Utility.getAppcon().getSession().hs1;
            Utility.getAppcon().getSession().filled_plantlist = new ArrayList<>();
            Utility.getAppcon().getSession().filled_plantlist = arrayListQuestion123;
            //  Log.e("combinedList1", new Gson().toJson(combinedList1));
            for (int i = 0; i < arrayListQuestion123.size(); i++) {
                if (arrayListQuestion123.get(i).getQuntity().equals("0")) {
                    check1 = "No";
                    validataion = check1;
                } else {
                    check1 = "No";
                    validataion = check1;
                    Log.e("int is ", String.valueOf(i));
                    if (String.valueOf(arrayListQuestion123.get(i).getId()).equals("null")) {

                    } else {
                        if (plant_item.equals("")) {
                            plant_item = arrayListQuestion123.get(i).getPlant();
                        } else {
                            plant_item = plant_item + "," + arrayListQuestion123.get(i).getPlant();
                        }

                    }
                    if (String.valueOf(arrayListQuestion123.get(i).getId()).equals("null")) {

                    } else {
                        if (quntity_item.equals("")) {
                            quntity_item = arrayListQuestion123.get(i).getQuntity();
                        } else {
                            quntity_item = quntity_item + "," + arrayListQuestion123.get(i).getQuntity();
                        }
                    }

                }
            }
            Log.e("plant_item--qty_item", plant_item + "---" + quntity_item);


            //Log.d("TAG", "filled_plantlist list--- " + new Gson().toJson(Utility.getAppcon().getSession().filled_plantlist));

            String[] separated = plant_item.split(",");
            String[] separated1 = quntity_item.split(",");
            if (separated.length != separated.length) {
                Toast.makeText(getApplicationContext(), "Select quntity", Toast.LENGTH_LONG).show();
            } else {

                alertDialog.dismiss();
                txt_plant.setText("Plant");
                MblQuestick_plantAdapter1 mblQuestick_plantAdapter1 = new MblQuestick_plantAdapter1(JobReviewActivity.this, arrayListQuestion123);
                plant_list_rec1.setAdapter(mblQuestick_plantAdapter1);
                //plant_list_rec1.setVisibility(View.VISIBLE);
                card_plant.setVisibility(View.VISIBLE);
            }
        });

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
                            List<planetmodel> editModelArrayList = populateList();

                            mblQuestick_plant_adapter = new MblQuestick_Plant_Adapter(JobReviewActivity.this, data2, editModelArrayList, new MblQuestick_Plant_Adapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(String plant, String check, List<planetmodel> planetmodels) {
                                    arrayListQuestion123 = planetmodels;
                                   /* Set<planetmodel> set = new LinkedHashSet<>(arrayListQuestion123);
                                    if (listp != null) {
                                        set.addAll(listp);
                                    }
                                    combinedList1 = new ArrayList<>(set);*/
                                }

                                @Override
                                public void onItemClick1(String quntity, String check) {

                                }
                            });
                            rcv_member.setAdapter(mblQuestick_plant_adapter);
                        }
                    }
                });


    }

    private void openlaboure() {
        AlertDialog.Builder builder = new AlertDialog.Builder(JobReviewActivity.this);
        View dialogView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.alert_laboure, null, false);
        RecyclerView rcv_member = dialogView.findViewById(R.id.rcv_member);
        ImageView img_close = dialogView.findViewById(R.id.img_close);
        TextView toolbar_Title = dialogView.findViewById(R.id.toolbar_Title);
        Button done = dialogView.findViewById(R.id.btn_done);
        RecyclerView.LayoutManager rcv_layoutManager = new LinearLayoutManager(JobReviewActivity.this);
        rcv_member.setLayoutManager(rcv_layoutManager);
        builder.setView(dialogView);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        labouer_list.setLayoutManager(layoutManager);
        img_close.setOnClickListener(view -> alertDialog.dismiss());
        done.setOnClickListener(view -> {
            try {
                if (combinedList.size() == 0) {
                    //labouer_list.setVisibility(View.GONE);
                    card_labour.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(), "Selcet Contractor", Toast.LENGTH_LONG).show();
                } else {
                    label_item = "";
                    for (int i = 0; i < combinedList.size(); i++) {
                        if (label_item.equals("")) {
                            label_item = combinedList.get(i).getNames();
                        } else {
                            label_item = label_item + "," + combinedList.get(i).getNames();
                        }
                    }

                    Log.e("label_item--", label_item);
                    txt_labour1.setText("Contractors");
                    //labouer_list.setVisibility(View.VISIBLE);
                    card_labour.setVisibility(View.VISIBLE);
                    Utility.getAppcon().getSession().filled_labourlist = new ArrayList<>();
                    Utility.getAppcon().getSession().filled_labourlist = combinedList;

                    Log.d("TAG", " filled_labourlist list--- " + new Gson().toJson(Utility.getAppcon().getSession().filled_labourlist));

                    MblQuestick_labourAdapter1 mblQuestick_labourAdapter1 = new MblQuestick_labourAdapter1(JobReviewActivity.this, combinedList);
                    labouer_list.setAdapter(mblQuestick_labourAdapter1);
                }
                alertDialog.dismiss();
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "Select Contractor", Toast.LENGTH_LONG).show();
            }
        });
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
                            MblQuestick_labourAdapter mAdapter = new MblQuestick_labourAdapter(getApplicationContext(), data1, new MblQuestick_labourAdapter.OnItemClickListener() {
                                public void onItemClick(List<labourmodel> labourmodels_list1) {
                                    new_labourmodels_list = labourmodels_list1;
                                    Set<labourmodel> set = new LinkedHashSet<>(new_labourmodels_list);
                                    if (labourmodels != null) {
                                        set.addAll(labourmodels);
                                    }
                                    combinedList = new ArrayList<>(set);
                                   /*
                                    if (labourmodels != null) {
                                        new_labourmodels_list.addAll(labourmodels);
                                    }*/
                                }
                            });
                            rcv_member.setAdapter(mAdapter);
                        }
                    }
                });
    }

    private void getJobReviewData() {

        try {
            avi.show();
            Observable<JobReviewModel> responseObservable = apiservice.get_single_job_reviews_report(jb_id, sessionManager.getKeyId());
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
                    .subscribe(new Observer<JobReviewModel>() {
                        @Override
                        public void onCompleted() {
                            avi.hide();
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.e("error", "" + e.getMessage());
                        }

                        @Override
                        public void onNext(JobReviewModel model) {
                            statusCode = model.getStatusCode();
                            if (statusCode == 200) {

                                //labour
                                if (!model.getData().get(0).getJbLaber().equals("")) {
                                    labourmodels = new ArrayList<>();
                                    labourname = model.getData().get(0).getJbLaber().split(",");
                                    List<String> arr = new ArrayList<String>();
                                    arr = Arrays.asList(labourname);

                                    for (int i = 0; i < arr.size(); i++) {
                                        labourmodel labourmodel = new labourmodel();
                                        labourmodel.setNames(arr.get(i));
                                        labourmodels.add(labourmodel);
                                    }
                                    Log.e("labourData", new Gson().toJson(labourmodels));
                                    card_labour.setVisibility(View.VISIBLE);
                                    txt_labour1.setText("Contractors");
                                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(JobReviewActivity.this);
                                    labouer_list.setLayoutManager(layoutManager);
                                    MblQuestick_labourAdapter1 mblQuestick_labourAdapter1 = new MblQuestick_labourAdapter1(JobReviewActivity.this, labourmodels);
                                    labouer_list.setAdapter(mblQuestick_labourAdapter1);
                                    Utility.getAppcon().getSession().filled_labourlist = new ArrayList<>();
                                    Utility.getAppcon().getSession().filled_labourlist = labourmodels;
                                    List<String> lbjoin = new ArrayList<>();
                                    for (int i = 0; i < labourmodels.size(); i++) {
                                        lbjoin.add(labourmodels.get(i).getNames());
                                    }

                                    label_item = android.text.TextUtils.join(",", lbjoin);
                                    Log.e("labour_join", label_item);
                                    // String con_name = String.join(",", );
                                } else {
                                }

                                //plant
                                if (!model.getData().get(0).getJbPlant().equals("") && !model.getData().get(0).getJbQuantity().equals("")) {
                                    listp = new ArrayList<>();
                                    String[] plantname = model.getData().get(0).getJbPlant().split(",");
                                    String[] qtyname = model.getData().get(0).getJbQuantity().split(",");
                                    List<String> arr1 = new ArrayList<String>();
                                    List<String> arr2 = new ArrayList<String>();
                                    arr1 = Arrays.asList(plantname);
                                    arr2 = Arrays.asList(qtyname);
                                    for (int i = 0; i < arr1.size(); i++) {
                                        planetmodel p = new planetmodel();
                                        p.setPlant(arr1.get(i));
                                        p.setQuntity(arr2.get(i));
                                        listp.add(p);
                                    }
                                    Log.e("plantData", new Gson().toJson(listp));

                                    card_plant.setVisibility(View.VISIBLE);
                                    txt_plant.setText("Plant");
                                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(JobReviewActivity.this);
                                    plant_list_rec1.setLayoutManager(layoutManager);
                                    MblQuestick_plantAdapter1 mblQuestick_plantAdapter1 = new MblQuestick_plantAdapter1(JobReviewActivity.this, listp);
                                    plant_list_rec1.setAdapter(mblQuestick_plantAdapter1);
                                    Utility.getAppcon().getSession().filled_plantlist = new ArrayList<>();
                                    Utility.getAppcon().getSession().filled_plantlist = listp;

                                    List<String> plantlist = new ArrayList<>();
                                    for (int i = 0; i < listp.size(); i++) {
                                        plantlist.add(listp.get(i).getPlant());
                                    }
                                    List<String> qtylist = new ArrayList<>();
                                    for (int i = 0; i < listp.size(); i++) {
                                        qtylist.add(listp.get(i).getQuntity());
                                    }
                                    plant_item = android.text.TextUtils.join(",", plantlist);
                                    quntity_item = android.text.TextUtils.join(",", qtylist);
                                    Log.e("plat_join--qty_join", plant_item + "--" + quntity_item);

                                } else {
                                }

                                ///image
                                if (!model.getData().get(0).getJbWorkOnSiteImages().equals("")) {
                                    String[] images = model.getData().get(0).getJbWorkOnSiteImages().split(",");
                                    List<String> imglist = new ArrayList<String>();
                                    imglist = Arrays.asList(images);
                                    // List<Uri> listimage = new ArrayList<>();
                                    for (int i = 0; i < imglist.size(); i++) {
                                        Uri_Model uri_model = new Uri_Model();
                                        uri_model.setStatus("no new");
                                        uri_model.setUri(Uri.parse(imglist.get(i)));
                                        imagesPathList.add(uri_model);
                                    }
                                    Log.e("imagelist--", String.valueOf(imagesPathList));
                                    Utility.getAppcon().getSession().jobreview_image = "";
                                    MyAdapter1 mAdapter = new MyAdapter1(getApplicationContext(), imagesPathList);
                                    listView.setAdapter(mAdapter);
                                } else {
                                }

                                /// site
                                site_name = model.getData().get(0).getJb_site_number() + " , " + model.getData().get(0).getJbSiteName();
                                if (site_name != null) {
                                    int spinnerPosition = siteAdapter.getPosition(site_name);
                                    spn_site.setSelection(spinnerPosition);
                                    Log.e("sitename", "" + spinnerPosition);
                                }

                                edt_notes.setText(model.getData().get(0).getJbWorkOnSiteNotes());
                                edt_prgrms.setText(model.getData().get(0).getJbPrograms());
                                edt_commnt.setText(model.getData().get(0).getJbDayWorkNotes());
                                edt_commnt1.setText(model.getData().get(0).getJbIssueSiteNotes());
                                edt_commnt2.setText(model.getData().get(0).getJbOutstandingNotes());

                                if (model.getData().get(0).getJbDayWorkAns().equals("Yes")) {
                                    rd1.setChecked(true);
                                } else if (model.getData().get(0).getJbDayWorkAns().equals("No")) {
                                    rd2.setChecked(true);
                                } else {
                                    rd1.setChecked(false);
                                    rd2.setChecked(false);
                                }
                                if (model.getData().get(0).getJbIssueSiteAns().equals("Yes")) {
                                    rd3.setChecked(true);
                                } else if (model.getData().get(0).getJbIssueSiteAns().equals("No")) {
                                    rd4.setChecked(true);
                                } else {
                                    rd3.setChecked(false);
                                    rd4.setChecked(false);
                                }
                                if (model.getData().get(0).getJbOutstandingAns().equals("Yes")) {
                                    rd5.setChecked(true);
                                } else if (model.getData().get(0).getJbOutstandingAns().equals("No")) {
                                    rd6.setChecked(true);
                                } else {
                                    rd5.setChecked(false);
                                    rd6.setChecked(false);
                                }

                                if (model.getData().get(0).getJbPdf() != null) {
                                    txt_pdfpath.setText((CharSequence) model.getData().get(0).getJbPdf().toString());
                                }

                            }
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void callSubmitApi() {

        try {
            avi.show();
            btn_submit.setClickable(false);

            parts = new ArrayList<>();
            if (imagesPathList != null) {
                for (int i = 0; i < imagesPathList.size(); i++) {
                    parts.add(prepareFilePart("jb_work_on_site_images[]", imagesPathList.get(i).getUri()));
                }
            }

            MultipartBody.Part body = null;
            if (pdf_filePath != null) {
                File file = FileUtilisDoc.getFile(JobReviewActivity.this, pdf_filePath);
                RequestBody requestFile = RequestBody.create(MediaType.parse(getContentResolver().getType(pdf_filePath)), file);
                body = MultipartBody.Part.createFormData("jb_pdf", file.getName(), requestFile);
            }
            RequestBody userid = RequestBody.create(MediaType.parse("text/plain"), sessionManager.getKeyId());
            RequestBody sitee = RequestBody.create(MediaType.parse("text/plain"), s_id);
            RequestBody labour = RequestBody.create(MediaType.parse("text/plain"), label_item);
            RequestBody plant = RequestBody.create(MediaType.parse("text/plain"), plant_item);
            RequestBody quantity = RequestBody.create(MediaType.parse("text/plain"), quntity_item);
           /* RequestBody labour1 = RequestBody.create(MediaType.parse("text/plain"), labour_join);
            RequestBody plant1 = RequestBody.create(MediaType.parse("text/plain"), plant_join);
            RequestBody quantity1 = RequestBody.create(MediaType.parse("text/plain"), qty_join);*/
            RequestBody wrkondsite = RequestBody.create(MediaType.parse("text/plain"), edt_notes.getText().toString());
            RequestBody program = RequestBody.create(MediaType.parse("text/plain"), edt_prgrms.getText().toString());
            RequestBody dy_ans = RequestBody.create(MediaType.parse("text/plain"), daywrk_ans);
            RequestBody dy_notes = RequestBody.create(MediaType.parse("text/plain"), edt_commnt.getText().toString());
            RequestBody isuue_ans = RequestBody.create(MediaType.parse("text/plain"), issusite_ans);
            RequestBody isuue_notes = RequestBody.create(MediaType.parse("text/plain"), edt_commnt1.getText().toString());
            RequestBody date = RequestBody.create(MediaType.parse("text/plain"), txtdate);
            RequestBody outstndng_ans = RequestBody.create(MediaType.parse("text/plain"), oustndng_ans);
            RequestBody outstndng_notes = RequestBody.create(MediaType.parse("text/plain"), edt_commnt2.getText().toString());
            RequestBody operation_flag = RequestBody.create(MediaType.parse("text/plain"), flag_operation);
            Observable<Model> responseObservable = responseObservable = apiservice.insert_jobReview(
                    userid,
                    sitee,
                    labour,
                    plant,
                    quantity,
                    wrkondsite,
                    program,
                    dy_ans,
                    dy_notes,
                    isuue_ans,
                    isuue_notes,
                    outstndng_ans,
                    outstndng_notes,
                    date,
                    parts,
                    body,
                    //j_id,
                    operation_flag
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
                            statusCode = model.getStatusCode();
                            if (statusCode == 200) {
                                btn_submit.setClickable(false);
                                Utility.displayToast(getApplicationContext(), model.getMessage());
                                Utility.getAppcon().getSession().filled_labourlist.clear();
                                Utility.getAppcon().getSession().filled_plantlist.clear();
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                            } else if (statusCode == 201) {
                                Utility.displayToast(getApplicationContext(), model.getMessage());
                            } else {
                                Utility.displayToast(getApplicationContext(), model.getMessage());
                            }
                        }
                    });
        } catch (
                Exception e) {
            e.printStackTrace();
        }

    }

    private void callUpdateApi() {
        avi.show();
        btn_submit.setClickable(false);
        parts = new ArrayList<>();
        if (imagesPathList != null) {
            for (int i = 0; i < imagesPathList.size(); i++) {
                Log.e("Image list is ", String.valueOf(imagesPathList.get(i)));
                if (imagesPathList.get(i).getStatus().equals("no new")) {

                    if (old_image.equals("")) {
                        old_image = String.valueOf(imagesPathList.get(i).getUri());
                    } else {
                        old_image = old_image + "," + String.valueOf(imagesPathList.get(i).getUri());
                    }
                    Log.e("Old Image", old_image);
                    Log.e("No New Item add", "Yes");
                } else {
                    parts.add(prepareFilePart("jb_work_on_site_images[]", imagesPathList.get(i).getUri()));
                }

            }
        }

        MultipartBody.Part body = null;
        if (pdf_filePath != null) {
            File file = FileUtilisDoc.getFile(JobReviewActivity.this, pdf_filePath);
            RequestBody requestFile = RequestBody.create(MediaType.parse(getContentResolver().getType(pdf_filePath)), file);
            body = MultipartBody.Part.createFormData("jb_pdf", file.getName(), requestFile);
        }
        RequestBody userid = RequestBody.create(MediaType.parse("text/plain"), sessionManager.getKeyId());
        RequestBody sitee = RequestBody.create(MediaType.parse("text/plain"), s_id);
        RequestBody labour = RequestBody.create(MediaType.parse("text/plain"), label_item);
        RequestBody plant = RequestBody.create(MediaType.parse("text/plain"), plant_item);
        RequestBody quantity = RequestBody.create(MediaType.parse("text/plain"), quntity_item);
           /* RequestBody labour1 = RequestBody.create(MediaType.parse("text/plain"), labour_join);
            RequestBody plant1 = RequestBody.create(MediaType.parse("text/plain"), plant_join);
            RequestBody quantity1 = RequestBody.create(MediaType.parse("text/plain"), qty_join);*/
        RequestBody wrkondsite = RequestBody.create(MediaType.parse("text/plain"), edt_notes.getText().toString());
        RequestBody program = RequestBody.create(MediaType.parse("text/plain"), edt_prgrms.getText().toString());
        RequestBody dy_ans = RequestBody.create(MediaType.parse("text/plain"), daywrk_ans);
        RequestBody dy_notes = RequestBody.create(MediaType.parse("text/plain"), edt_commnt.getText().toString());
        RequestBody isuue_ans = RequestBody.create(MediaType.parse("text/plain"), issusite_ans);
        RequestBody isuue_notes = RequestBody.create(MediaType.parse("text/plain"), edt_commnt1.getText().toString());
        RequestBody date = RequestBody.create(MediaType.parse("text/plain"), txtdate);
        RequestBody outstndng_ans = RequestBody.create(MediaType.parse("text/plain"), oustndng_ans);
        RequestBody outstndng_notes = RequestBody.create(MediaType.parse("text/plain"), edt_commnt2.getText().toString());
        RequestBody j_id = RequestBody.create(MediaType.parse("text/plain"), jb_id);
        RequestBody operation_flag = RequestBody.create(MediaType.parse("text/plain"), flag_operation);
        RequestBody jb_old_images = RequestBody.create(MediaType.parse("text/plain"), old_image);
        Observable<Model> responseObservable = responseObservable = apiservice.update_jobReview(
                userid,
                sitee,
                labour,
                plant,
                quantity,
                wrkondsite,
                program,
                dy_ans,
                dy_notes,
                isuue_ans,
                isuue_notes,
                outstndng_ans,
                outstndng_notes,
                date,
                parts,
                body,
                j_id,
                operation_flag,
                jb_old_images
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
                        statusCode = model.getStatusCode();
                        if (statusCode == 200) {
                            btn_submit.setClickable(false);
                            Utility.displayToast(getApplicationContext(), model.getMessage());
                            Utility.getAppcon().getSession().filled_labourlist.clear();
                            Utility.getAppcon().getSession().filled_plantlist.clear();
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        } else if (statusCode == 201) {
                            Utility.displayToast(getApplicationContext(), model.getMessage());
                        } else {
                            Utility.displayToast(getApplicationContext(), model.getMessage());
                        }
                    }
                });


    }

    @NonNull
    private MultipartBody.Part prepareFilePart(String partName, Uri fileUri) {
        RequestBody requestFile;
        File file = FileUtils.getFile(this, fileUri);
        requestFile = RequestBody.create(MediaType.parse("image/*"), file);
        return MultipartBody.Part.createFormData(partName, file.getName(), requestFile);
    }

    private void showChooser() {
        Intent target = FileUtils.createGetContentIntent();
        Intent intent = Intent.createChooser(target, "Select Image");
        try {
            startActivityForResult(intent, REQUEST_CODE);
        } catch (ActivityNotFoundException e) {
        }
    }

    private void selectImage() {

        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(JobReviewActivity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (options[item].equals("Take Photo")) {

                    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                        String fileName = "temp.jpg";
                        ContentValues values = new ContentValues();
                        values.put(MediaStore.Images.Media.TITLE, fileName);
                        mCapturedImageURI = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, mCapturedImageURI);
                        startActivityForResult(takePictureIntent, CAPTURE_IMAGE);
                    }
                } else if (options[item].equals("Choose from Gallery")) {

                    showChooser();
//                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                    startActivityForResult(intent, 1);
                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {

            case CAPTURE_IMAGE:
                ImageCropFunctionCustom(mCapturedImageURI);

                break;
            case CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE:

                CropImage.ActivityResult result = CropImage.getActivityResult(data);
                Compressor compressedImageFile = new Compressor(this);
                compressedImageFile.setQuality(60);

                try {
                    // Get the file path from the URI
                    File file = compressedImageFile.compressToFile(new File(result.getUri().getPath()));
                    Uri uri = Uri.fromFile(file);

                    Log.e("TAG", "capture uri:-- " + uri);
                    final String path = FileUtils.getPath(getApplicationContext(), uri);
                    Log.e("Single File Selected", path);
                    Uri_Model uri_model = new Uri_Model();
                    uri_model.setStatus("new");
                    uri_model.setUri(uri);
                    imagesPathList.add(uri_model);
                    Log.e("Path is ", path);

                    MyAdapter1 mAdapter = new MyAdapter1(getApplicationContext(), imagesPathList);
                    listView.setAdapter(mAdapter);
                } catch (Exception e) {
                }
                break;
            case REQUEST_CODE: // Do your stuff here...
                if (resultCode == RESULT_OK) {
                    if (data.getClipData() != null) {
                        Log.e("multiple", "view");
                        int count = data.getClipData().getItemCount();
                        int currentItem = 0;
                        while (currentItem < count) {
                            Uri imageUri = data.getClipData().getItemAt(currentItem).getUri();
                            //do something with the image (save it to some directory or whatever you need to do with it here)
                            currentItem = currentItem + 1;
                            try {
                                Uri_Model uri_model = new Uri_Model();
                                uri_model.setStatus("new");
                                uri_model.setUri(imageUri);
                                imagesPathList.add(uri_model);
                                MyAdapter1 mAdapter = new MyAdapter1(getApplicationContext(), imagesPathList);
                                listView.setAdapter(mAdapter);
                            } catch (Exception e) {

                            }
                            Log.e("Uri Selected", imageUri.toString());
                        }
                    } else if (data.getData() != null) {

                        final Uri uri = data.getData();
                        try {
                            // Get the file path from the URI
                            final String path = FileUtils.getPath(getApplicationContext(), uri);
                            Log.e("Single File Selected", path);
                            Uri_Model uri_model = new Uri_Model();
                            uri_model.setStatus("new");
                            uri_model.setUri(uri);
                            imagesPathList.add(uri_model);
                            Log.e("Path is ", path);
                            MyAdapter1 mAdapter = new MyAdapter1(getApplicationContext(), imagesPathList);
                            listView.setAdapter(mAdapter);
                        } catch (Exception e) {
                        }
                    }
                }
                break;
            case PICK_PDF_REQUEST:
                try {
                    if (data.getData() != null) {
                        pdf_filePath = data.getData();
                        // String path = data.getData();
                        //SelectedPDF = getPDFPath(pdf_filePath);
//                        Log.e("TAG", "pdf:--- " + pdf_filePath);
                        // txt_pdfpath.setText(String.valueOf(pdf_filePath));

                        String uriString = pdf_filePath.toString();
                        File myFile = new File(uriString);
                        String path = myFile.getAbsolutePath();
                        String displayName = null;

                        if (uriString.startsWith("content://")) {
                            Cursor cursor = null;
                            try {
                                cursor = getApplicationContext().getContentResolver().query(pdf_filePath, null, null, null, null);
                                if (cursor != null && cursor.moveToFirst()) {
                                    displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                                    txt_pdfpath.setText(displayName);
                                }
                            } finally {
                                cursor.close();
                            }
                        } else if (uriString.startsWith("file://")) {
                            displayName = myFile.getName();
                            txt_pdfpath.setText(displayName);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void ImageCropFunctionCustom(Uri uri) {
        Intent intent = CropImage.activity(uri)
                .setGuidelines(CropImageView.Guidelines.ON)
                .getIntent(this);
        startActivityForResult(intent, CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE);
    }

    private ArrayList<planetmodel> populateList() {

        ArrayList<planetmodel> list = new ArrayList<>();
        for (int i = 0; i < data2.size(); i++) {
            planetmodel editModel = new planetmodel();
            editModel.setId(String.valueOf(i));
            editModel.setQuntity("0");
            list.add(editModel);
        }

        return list;
    }
}
 /*public String getPDFPath(Uri uri) {
        String[] projection = {MediaStore.MediaColumns.DATA};
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }*/
/*
    public String getPDFPath(Uri uri) {
        String path = null;
        String[] proj = {MediaStore.MediaColumns.DATA};
        Cursor cursor = getContentResolver().query(uri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
            path = cursor.getString(column_index);
        }
        cursor.close();
        return path;
    }
*/