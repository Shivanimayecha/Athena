package com.athena.group.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.transition.AutoTransition;
import androidx.transition.TransitionManager;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
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
import com.athena.group.MainActivity;
import com.athena.group.Model.SiteContractorModel;
import com.athena.group.Model.SpinnerModel;
import com.athena.group.Model.TimeLogModel;
import com.athena.group.R;
import com.athena.group.application.SessionManager;
import com.athena.group.application.Utility;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;
import com.wang.avi.AVLoadingIndicatorView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.zelory.compressor.Compressor;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Observable;
import rx.Observer;
import rx.schedulers.Schedulers;


public class TimeLog extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener, View.OnClickListener {

    private String TAG = "TAG";
    @BindView(R.id.spn_site)
    Spinner spn_site;
    @BindView(R.id.spn_contractor)
    Spinner spn_contractor;
    @BindView(R.id.spn_stime)
    Spinner spn_stime;
    @BindView(R.id.spn_etime)
    Spinner spn_etime;
    @BindView(R.id.spn_ampm)
    Spinner spn_ampm;
    @BindView(R.id.spn_ampm1)
    Spinner spn_ampm1;
    @BindView(R.id.calendertxt)
    TextView calendertxt;
    @BindView(R.id.txt_day)
    TextView txt_day;
    @BindView(R.id.ll_dateday)
    LinearLayout ll_dateday;
    @BindView(R.id.txt_totalhrs)
    TextView txt_totalhrs;
    @BindView(R.id.timecard)
    CardView timecard;
    @BindView(R.id.card_totalhrs)
    CardView card_totalhrs;
    @BindView(R.id.img_remark)
    ImageView img_remark;
    @BindView(R.id.img_pic)
    ImageView img_pic;
    @BindView(R.id.edt_note)
    EditText edt_note;
    @BindView(R.id.btn_endday)
    Button btn_endday;
    @BindView(R.id.toolbar_Title)
    TextView toolbar_Title;
    @BindView(R.id.back_icon)
    ImageView back_icon;
    @BindView(R.id.avi)
    AVLoadingIndicatorView avi;
    @BindView(R.id.ll_expandableview)
    LinearLayout ll_expandableview;
    @BindView(R.id.img_downup)
    ImageView img_downup;
    @BindView(R.id.rg1)
    RadioGroup rg1;
    @BindView(R.id.rg2)
    RadioGroup rg2;
    @BindView(R.id.rb1)
    RadioButton rb1;
    @BindView(R.id.rb2)
    RadioButton rb2;
    @BindView(R.id.rb3)
    RadioButton rb3;
    @BindView(R.id.rb8)
    RadioButton rbt8;
    @BindView(R.id.rb4)
    RadioButton rbt4;
    @BindView(R.id.rb5)
    RadioButton rbt5;
    @BindView(R.id.rb6)
    RadioButton rbt6;
    @BindView(R.id.rb7)
    RadioButton rbt7;
    ApiInterface apiservice;
    ProgressDialog pDialog;
    int statusCode;
    List<SpinnerModel.Data> data;
    List<SiteContractorModel.Data> data1;
    String site = "", contractor = "", c_id = "", s_id = "";
    ArrayList<String> sitearray = new ArrayList<>();
    ArrayList<String> contractrArray;
    SessionManager sessionManager;
    private int mYear, mMonth, mDay, mHour, mMinute;
    String wstime = "", wetime = "", bstime = "", betime = "";
    Uri mCapturedImageURI;
    String mediaPath = "";
    Integer CAPTURE_IMAGE = 3;
    private ArrayList<Uri> arrayListuri;
    String filePath1;
    final Calendar myCalendar = Calendar.getInstance();
    String workTimehr = "", breakTimehr = "", totalWorkTime = "";
    private static final int Time_id = 1;
    private String brk_time = "00:30", trvl_time = "00:00", trvl_time1 = "00:00";
    private Dialog dialog;
    String[] arrayList1;
    String[] arrayList2;
    ArrayList<String> arrayList3 = new ArrayList();
    ArrayList<String> arrayList4 = new ArrayList();

    private String spn_starttime = "07:30";
    private String spn_endtime = "17:00";
    private String site_date = "";
    String wrkhr1 = "";
    DatePickerDialog datePickerDialog;

    @BindView(R.id.text)
    SearchableSpinner text;
    private String ampm = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_timelog);
        ButterKnife.bind(this);
        sessionManager = new SessionManager(TimeLog.this);
        apiservice = ApiServiceCreator.createService("latest");
        toolbar_Title.setText(getString(R.string.timelog));

        Log.e("TimelogData_Data", "getIntentData: " + wstime + "--" + wetime + "--" + bstime + "--" + betime);
        initViews();

        text.setTitle("Select Contractor");

    }

    private void initViews() {

        card_totalhrs.setVisibility(View.GONE);
        calendertxt.setText(Utility.date);
        txt_day.setText(Utility.day);
        site_date = Utility.date1;

        onClickListener();
        radioCheckChanged();
        allSpinnersBind();
    }

    private void onClickListener() {
        back_icon.setOnClickListener(this);
        btn_endday.setOnClickListener(this);
        rb1.setOnClickListener(this);
        rb2.setOnClickListener(this);
        rb3.setOnClickListener(this);
        img_pic.setOnClickListener(this);
        img_downup.setOnClickListener(this);
        ll_dateday.setOnClickListener(this);
    }

    private void radioCheckChanged() {

        rbt8.setOnCheckedChangeListener(TimeLog.this);
        rbt4.setOnCheckedChangeListener(TimeLog.this);
        rbt5.setOnCheckedChangeListener(TimeLog.this);
        rbt6.setOnCheckedChangeListener(TimeLog.this);
        rbt7.setOnCheckedChangeListener(TimeLog.this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.back_icon:
                finish();
                break;
            case R.id.btn_endday:
                if (s_id.equals("")) {
                    Utility.displayToast(TimeLog.this, "Please Select Site");
                } else if (c_id.equals("")) {
                    Utility.displayToast(TimeLog.this, "Please Select Contractor");
                } else if (calendertxt.getText().toString().equals("")) {
                    Utility.displayToast(TimeLog.this, "Please Select date");
                } else if (spn_starttime.equals("")) {
                    Utility.displayToast(TimeLog.this, "Please select start time");
                } else if (spn_endtime.equals("")) {
                    Utility.displayToast(TimeLog.this, "Please select end time");
                } else if (rg1.getCheckedRadioButtonId() == -1) {
                    Utility.displayToast(TimeLog.this, "Please Select Break time");
                } else {
                    callEndDayApi();
                }
                break;
            case R.id.rb1:
                brk_time = "00:00";
                timeCalculation();
                break;
            case R.id.rb2:
                brk_time = "00:30";
                timeCalculation();
                break;
            case R.id.rb3:
                brk_time = "01:00";
                timeCalculation();
                break;
            case R.id.img_pic:
                selectImage();
                break;
            case R.id.img_downup:
                card_totalhrs.setVisibility(View.VISIBLE);

                if (ll_expandableview.getVisibility() == View.GONE) {
                    TransitionManager.beginDelayedTransition(timecard, new AutoTransition());
                    ll_expandableview.setVisibility(View.VISIBLE);
                    img_downup.setBackgroundResource(R.drawable.uparrow);
                } else {
                    TransitionManager.beginDelayedTransition(timecard, new AutoTransition());
                    ll_expandableview.setVisibility(View.GONE);
                    img_downup.setBackgroundResource(R.drawable.downarrow);
                }
                break;
            case R.id.ll_dateday:
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                datePickerDialog = new DatePickerDialog(TimeLog.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                calendertxt.setText(dayOfMonth + "-"
                                        + (monthOfYear + 1) + "-" + year);

                                site_date = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;    //for pass in api

                                //for day
                                SimpleDateFormat simpledateformat = new SimpleDateFormat("EEEE");
                                Date date = new Date(year, monthOfYear, dayOfMonth - 1);
                                txt_day.setText(simpledateformat.format(date));
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
                break;
        }
    }

    private void allSpinnersBind() {

        arrayList1 = getResources().getStringArray(R.array.start_time);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.timespinner_item, arrayList1);
        arrayAdapter.setDropDownViewResource(R.layout.timespinner_item);
        spn_stime.setAdapter(arrayAdapter);

        int posi = getIndex(spn_stime, "07:30");  //for default value
        spn_stime.setSelection(posi);
        spn_stime.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (spn_stime.getSelectedItem() == "Select Start time") {
                    spn_starttime = "";
                } else {
                    spn_starttime = spn_stime.getSelectedItem().toString();
                    timeCalculation();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        arrayList2 = getResources().getStringArray(R.array.end_time);
        ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<String>(this, R.layout.timespinner_item, arrayList2);
        arrayAdapter1.setDropDownViewResource(R.layout.timespinner_item);
        spn_etime.setAdapter(arrayAdapter1);

        // int posi1 = getIndex(spn_etime, "17:00"); //for default value
        int posi1 = getIndex(spn_etime, "05:00"); //for default value
        spn_etime.setSelection(posi1);
        spn_etime.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (spn_etime.getSelectedItem() == "Select End time") {
                    spn_endtime = "";
                } else {
                    spn_endtime = spn_etime.getSelectedItem().toString();

                    timeCalculation();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        arrayList3.add("AM");
        arrayList3.add("PM");
        ArrayAdapter<String> arrayAdapter3 = new ArrayAdapter<String>(this, R.layout.spinner_item, arrayList3);
        arrayAdapter3.setDropDownViewResource(R.layout.spinner_item);
        spn_ampm.setAdapter(arrayAdapter3);
        spn_ampm.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ampm = spn_ampm.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        arrayList4.add("PM");
        arrayList4.add("AM");
        ArrayAdapter<String> arrayAdapter4 = new ArrayAdapter<String>(this, R.layout.spinner_item, arrayList4);
        arrayAdapter4.setDropDownViewResource(R.layout.spinner_item);
        spn_ampm1.setAdapter(arrayAdapter4);
        spn_ampm1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ampm = spn_ampm1.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        getSiteName();
        spn_site.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (spn_site.getSelectedItem() == "Select site") {
                    site = "";
                } else {
                    site = spn_site.getSelectedItem().toString();
                    SpinnerModel.Data datalist = data.get(i - 1);
                    s_id = datalist.getSiteId();
                    getSiteContractor(s_id);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

       /* spn_contractor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (spn_contractor.getSelectedItem() == "Select Contractor") {
                    contractor = "";
                } else {
                    contractor = spn_contractor.getSelectedItem().toString();
                    SiteContractorModel.Data datalist = data1.get(i - 1);
                    c_id = datalist.getUserId();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });*/


        text.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (text.getSelectedItem() == "Select Contractor") {
                    c_id = "";
                } else {
                    contractor = text.getSelectedItem().toString();
                    SiteContractorModel.Data datalist = data1.get(i - 1);
                    c_id = datalist.getUserId();
                    Log.e("C_id", c_id);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    private void timeCalculation() {
        Date startDate = null, endDate = null, startBreaktime = null, endBreaktime = null, workHr = null, breakHr = null;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        try {
            if (spn_starttime != null) {
                startDate = simpleDateFormat.parse(spn_starttime);
            }
            if (spn_endtime != null) {
                endDate = simpleDateFormat.parse(spn_endtime);
            }
            workTimehr = TimeDiff(startDate, endDate, simpleDateFormat);
            Log.e(TAG, "timecal: " + workTimehr);

            //String wrkhr = timeCalculation2(workTimehr, brk_time);
            String wrkhr = timeCalculation2(brk_time, workTimehr);
            Log.e(TAG, "ahsjhasjhajsh: brk" + workTimehr + " \n " + brk_time);

            Log.e(TAG, "timeCalculation: brk" + wrkhr);

            String time1 = wrkhr;
            String time2 = trvl_time1;

            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
            timeFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

            Date date1 = timeFormat.parse(time1);
            Date date2 = timeFormat.parse(time2);

            long sum = date1.getTime() + date2.getTime();

            String date3 = timeFormat.format(new Date(sum));
            System.out.println("The sum is " + date3);
            txt_totalhrs.setText("Today: " + date3 + " Hours");
            //txt_totalhrs.setText("Today: " + wrkhr + " Hours");
        } catch (
                ParseException e) {
            e.printStackTrace();
        }

    }

    private String timeCalculation2(String wrh, String brk) {

        String wrkhr = "";
        Date startDate = null, endDate = null, startBreaktime = null, endBreaktime = null, workHr = null, breakHr = null;

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        try {
            if (wrh != null) {
                startDate = simpleDateFormat.parse(wrh);
            }
            if (brk != null) {
                endDate = simpleDateFormat.parse(brk);
            }
            wrkhr = TimeDiff(startDate, endDate, simpleDateFormat);
            Log.e(TAG, "timecal2: " + wrkhr);
            Log.e(TAG, "brk2: " + brk);
            // wrkhr = convertDate(String.valueOf(getMilliFromDate(workTimehr) - getMilliFromDate(brk_time)), "HH:mm");

        } catch (
                ParseException e) {
            e.printStackTrace();
        }

        return wrkhr;

    }

    private int getIndex(Spinner spinner, String myString) {
        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)) {
                return i;
            }
        }
        return 0;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean c) {
        switch (buttonView.getId()) {
            case R.id.rb4:
                if (rbt4.isChecked() && c) {
                    rbt8.setChecked(false);
                    rbt5.setChecked(false);
                    rbt6.setChecked(false);
                    rbt7.setChecked(false);
                    if (rbt4.getText().toString().equals("30 mins")) {
                        trvl_time = "00:30";
                        trvl_time1 = "00:30";
                        timeCalculation();
                    }
                    return;
                }
                rbt8.setEnabled(true);
                rbt5.setEnabled(true);
                rbt6.setEnabled(true);
                rbt7.setEnabled(true);
                break;
            case R.id.rb5:
                if (rbt5.isChecked() && c) {
                    rbt8.setChecked(false);
                    rbt4.setChecked(false);
                    rbt6.setChecked(false);
                    rbt7.setChecked(false);
                    if (rbt5.getText().toString().equals("60 mins")) {
                        trvl_time = "60:00";
                        trvl_time1 = "01:00";
                        timeCalculation();
                    }
                    return;
                }
                rbt8.setEnabled(true);
                rbt4.setEnabled(true);
                rbt6.setEnabled(true);
                rbt7.setEnabled(true);
                break;
            case R.id.rb6:
                if (rbt6.isChecked() && c) {
                    rbt8.setChecked(false);
                    rbt4.setChecked(false);
                    rbt5.setChecked(false);
                    rbt7.setChecked(false);
                    if (rbt6.getText().toString().equals("90 mins")) {
                        trvl_time = "90:00";
                        trvl_time1 = "01:30";
                        timeCalculation();
                    }
                    return;
                }
                rbt8.setEnabled(true);
                rbt4.setEnabled(true);
                rbt5.setEnabled(true);
                rbt7.setEnabled(true);
                break;
            case R.id.rb7:
                if (rbt7.isChecked() && c) {
                    rbt8.setChecked(false);
                    rbt4.setChecked(false);
                    rbt6.setChecked(false);
                    rbt5.setChecked(false);
                    if (rbt7.getText().toString().equals("120 mins")) {
                        trvl_time = "120:00";
                        trvl_time1 = "02:00";
                        timeCalculation();
                    }
                    return;
                }
                rbt8.setEnabled(true);
                rbt4.setEnabled(true);
                rbt6.setEnabled(true);
                rbt5.setEnabled(true);
                break;
            case R.id.rb8:
                if (rbt8.isChecked() && c) {
                    rbt4.setChecked(false);
                    rbt5.setChecked(false);
                    rbt6.setChecked(false);
                    rbt7.setChecked(false);
                    if (rbt8.getText().toString().equals("NO Travel")) {
                        trvl_time = "00:00";
                        trvl_time1 = "00:00";
                        timeCalculation();
                    }
                    return;
                }
                rbt4.setEnabled(true);
                rbt5.setEnabled(true);
                rbt6.setEnabled(true);
                rbt7.setEnabled(true);
                break;
            default:
                break;
        }
    }

    public void callEndDayApi() {

        if (mediaPath == null) {
            mediaPath = "";
        }

        Map<String, RequestBody> map = new HashMap<>();
        Compressor compressedImageFile = new Compressor(this);
        compressedImageFile.setQuality(60);
        if (!mediaPath.equals("")) {
            File file = new File(mediaPath);
            File compressfile = null;
            try {
                compressfile = compressedImageFile.compressToFile(file);
                RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), compressfile);
                map.put("file[]\"; filename=\"" + file.getName() + "\"", requestBody);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            RequestBody userid = RequestBody.create(MediaType.parse("text/plain"), sessionManager.getKeyId());
            RequestBody site = RequestBody.create(MediaType.parse("text/plain"), s_id);
            RequestBody contractor = RequestBody.create(MediaType.parse("text/plain"), c_id);
            RequestBody stime = RequestBody.create(MediaType.parse("text/plain"), spn_starttime);
            RequestBody etime = RequestBody.create(MediaType.parse("text/plain"), spn_endtime);
            RequestBody be_time = RequestBody.create(MediaType.parse("text/plain"), brk_time);
            RequestBody tv_time = RequestBody.create(MediaType.parse("text/plain"), trvl_time1);
            RequestBody work_hrs = RequestBody.create(MediaType.parse("text/plain"), "");
            RequestBody brk_hrs = RequestBody.create(MediaType.parse("text/plain"), brk_time);
            RequestBody sdate = RequestBody.create(MediaType.parse("text/plain"), site_date);
            RequestBody note = RequestBody.create(MediaType.parse("text/plain"), edt_note.getText().toString());

            map.put("userId", userid);
            map.put("site", site);
            map.put("contractor_id", contractor);
            map.put("start_time", stime);
            map.put("end_time", etime);
            map.put("br_end_time", be_time);
            map.put("travel_time", tv_time);
            map.put("work_hrs", work_hrs);
            map.put("break_hrs", brk_hrs);
            map.put("site_date", sdate);
            map.put("note", note);

            avi.show();
            Observable<TimeLogModel> responseObservable = apiservice.addWorkLog(map);

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
                    .subscribe(new Observer<TimeLogModel>() {
                        @Override
                        public void onCompleted() {
                            avi.hide();
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.e("error", "" + e.getMessage());
                        }

                        @Override
                        public void onNext(TimeLogModel model) {
                            statusCode = model.getStatusCode();
                            if (statusCode == 200) {
                                btn_endday.setEnabled(false);
                                Utility.displayToast(TimeLog.this, model.getMessage());
                                Intent intent = new Intent(TimeLog.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            } else if (statusCode == 201) {
                                Utility.displayToast(TimeLog.this, model.getMessage());
                                alertDialog(model.getData());
                            }
                        }
                    });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void alertDialog(String data) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Are you sure,You want Add Work Log ?");
        alertDialogBuilder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        callEditWorkLogApi(data);
                    }
                });

        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                dialog.cancel();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void callEditWorkLogApi(String data) {

        if (mediaPath == null) {
            mediaPath = "";
        }

        Map<String, RequestBody> map = new HashMap<>();
        Compressor compressedImageFile = new Compressor(this);
        compressedImageFile.setQuality(60);
        if (!mediaPath.equals("")) {
            File file = new File(mediaPath);
            File compressfile = null;
            try {
                compressfile = compressedImageFile.compressToFile(file);
                RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), compressfile);
                map.put("file[]\"; filename=\"" + file.getName() + "\"", requestBody);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            RequestBody userid = RequestBody.create(MediaType.parse("text/plain"), sessionManager.getKeyId());
            RequestBody site = RequestBody.create(MediaType.parse("text/plain"), s_id);
            RequestBody contractor = RequestBody.create(MediaType.parse("text/plain"), c_id);
            RequestBody stime = RequestBody.create(MediaType.parse("text/plain"), spn_starttime);
            RequestBody etime = RequestBody.create(MediaType.parse("text/plain"), spn_endtime);
            RequestBody be_time = RequestBody.create(MediaType.parse("text/plain"), brk_time);
            RequestBody tv_time = RequestBody.create(MediaType.parse("text/plain"), trvl_time1);
            RequestBody work_hrs = RequestBody.create(MediaType.parse("text/plain"), "");
            RequestBody brk_hrs = RequestBody.create(MediaType.parse("text/plain"), brk_time);
            RequestBody sdate = RequestBody.create(MediaType.parse("text/plain"), site_date);
            RequestBody note = RequestBody.create(MediaType.parse("text/plain"), edt_note.getText().toString());
            RequestBody dattaa = RequestBody.create(MediaType.parse("text/plain"), data);

            map.put("userId", userid);
            map.put("site", site);
            map.put("contractor_id", contractor);
            map.put("start_time", stime);
            map.put("end_time", etime);
            map.put("br_end_time", be_time);
            map.put("travel_time", tv_time);
            map.put("work_hrs", work_hrs);
            map.put("break_hrs", brk_hrs);
            map.put("site_date", sdate);
            map.put("note", note);
            map.put("log_id", dattaa);

            avi.show();
            Observable<TimeLogModel> responseObservable = apiservice.editWorkLog(map);

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
                    .subscribe(new Observer<TimeLogModel>() {
                        @Override
                        public void onCompleted() {
                            avi.hide();
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.e("error", "" + e.getMessage());
                        }

                        @Override
                        public void onNext(TimeLogModel model) {
                            statusCode = model.getStatusCode();
                            if (statusCode == 200) {
                                btn_endday.setEnabled(false);
                                Utility.displayToast(TimeLog.this, model.getMessage());
                                Intent intent = new Intent(TimeLog.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                Utility.displayToast(TimeLog.this, model.getMessage());
                            }
                        }
                    });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void getSiteName() {
        avi.show();
        //Chnage Shirish
//        Observable<SpinnerModel> responseObservable = apiservice.get_site();

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

    private void getSiteContractor(String s_id) {
        avi.show();
        contractrArray = new ArrayList<>();
        Observable<SiteContractorModel> responseObservable = apiservice.get_siteContractor(s_id);
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
                        statusCode = model.getStatusCode();
                        if (statusCode == 200) {
                            data1 = model.getData();

                            for (int i = 0; i < model.getData().size(); i++) {
                                contractrArray.add(model.getData().get(i).getName() + " " + model.getData().get(i).getLname());

                            }
                        }
                    }
                });

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, contractrArray);
        dataAdapter.add("Select Contractor");
        dataAdapter.setDropDownViewResource(R.layout.spinner_item);
        //spn_contractor.setAdapter(dataAdapter);
        text.setAdapter(dataAdapter);


    }

    private void selectImage() {

        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(TimeLog.this);
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
                        mCapturedImageURI = getContentResolver()
                                .insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                                        values);
                        takePictureIntent
                                .putExtra(MediaStore.EXTRA_OUTPUT, mCapturedImageURI);
                        startActivityForResult(takePictureIntent, CAPTURE_IMAGE);

                    }
                } else if (options[item].equals("Choose from Gallery")) {

                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 1);
                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        arrayListuri = new ArrayList<>();

        if (requestCode == 1 && resultCode == RESULT_OK && null != data) {

            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = null;
            if (selectedImage != null) {
                cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            }
            assert cursor != null;
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            //filePath1 = cursor.getString(column_index);
            mediaPath = cursor.getString(columnIndex);
            // mediaPath=compressImage(mediaPath1);
            Log.e(TAG, "onActivityResult: 1--" + mediaPath);
            img_remark.setImageBitmap(BitmapFactory.decodeFile(mediaPath));
            cursor.close();
        } else if (requestCode == CAPTURE_IMAGE) {
            ImageCropFunctionCustom(mCapturedImageURI);

        } else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            if (data != null) {
                CropImage.ActivityResult result = CropImage.getActivityResult(data);
                // Uri uri = result.getUri();
                Compressor compressedImageFile = new Compressor(this);
                compressedImageFile.setQuality(60);
                try {
                    File file = compressedImageFile.compressToFile(new File(result.getUri().getPath()));
                    Uri uri = Uri.fromFile(file);

                    arrayListuri.add(uri);
                    Bundle bundle = data.getExtras();
                    assert bundle != null;
                    filePath1 = uri.getPath();
                    mediaPath = compressImage(filePath1);
                    if (filePath1 != null) {
                        img_remark.setImageBitmap(BitmapFactory.decodeFile(filePath1));
                    } else {
                        if (uri != null) {
                            filePath1 = uri.getPath();
                            mediaPath = compressImage(filePath1);
                            img_remark.setImageResource(0);
                            img_remark.setImageBitmap(BitmapFactory.decodeFile(mediaPath));
                            Log.e(TAG, "onActivityResult: 2--" + mediaPath);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void ImageCropFunctionCustom(Uri uri) {
        Intent intent = CropImage.activity(uri)
                .setGuidelines(CropImageView.Guidelines.ON)
                .getIntent(this);
        startActivityForResult(intent, CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE);
    }

    public String compressImage(String imageUri) {

        String filePath = getRealPathFromURI(imageUri);
        Bitmap scaledBitmap = null;

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        Bitmap bmp = BitmapFactory.decodeFile(filePath, options);
        int actualHeight = options.outHeight;
        int actualWidth = options.outWidth;
        float maxHeight = 816.0f;
        float maxWidth = 612.0f;
        float imgRatio = actualWidth / actualHeight;
        float maxRatio = maxWidth / maxHeight;
        if (actualHeight > maxHeight || actualWidth > maxWidth) {
            if (imgRatio < maxRatio) {
                imgRatio = maxHeight / actualHeight;
                actualWidth = (int) (imgRatio * actualWidth);
                actualHeight = (int) maxHeight;
            } else if (imgRatio > maxRatio) {
                imgRatio = maxWidth / actualWidth;
                actualHeight = (int) (imgRatio * actualHeight);
                actualWidth = (int) maxWidth;
            } else {
                actualHeight = (int) maxHeight;
                actualWidth = (int) maxWidth;

            }
        }

        options.inSampleSize = calculateInSampleSize(options, actualWidth, actualHeight);
        options.inJustDecodeBounds = false;

//      this options allow android to claim the bitmap memory if it runs low on memory
        options.inPurgeable = true;
        options.inInputShareable = true;
        options.inTempStorage = new byte[16 * 1024];

        try {
//          load the bitmap from its path
            bmp = BitmapFactory.decodeFile(filePath, options);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();

        }
        try {
            scaledBitmap = Bitmap.createBitmap(actualWidth, actualHeight, Bitmap.Config.ARGB_8888);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();
        }

        float ratioX = actualWidth / (float) options.outWidth;
        float ratioY = actualHeight / (float) options.outHeight;
        float middleX = actualWidth / 2.0f;
        float middleY = actualHeight / 2.0f;

        Matrix scaleMatrix = new Matrix();
        scaleMatrix.setScale(ratioX, ratioY, middleX, middleY);

        Canvas canvas = new Canvas(scaledBitmap);
        canvas.setMatrix(scaleMatrix);
        canvas.drawBitmap(bmp, middleX - bmp.getWidth() / 2, middleY - bmp.getHeight() / 2, new Paint(Paint.FILTER_BITMAP_FLAG));

//      check the rotation of the image and display it properly
        ExifInterface exif;
        try {
            exif = new ExifInterface(filePath);

            int orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION, 0);
            Log.d("EXIF", "Exif: " + orientation);
            Matrix matrix = new Matrix();
            if (orientation == 6) {
                matrix.postRotate(90);
                Log.d("EXIF", "Exif: " + orientation);
            } else if (orientation == 3) {
                matrix.postRotate(180);
                Log.d("EXIF", "Exif: " + orientation);
            } else if (orientation == 8) {
                matrix.postRotate(270);
                Log.d("EXIF", "Exif: " + orientation);
            }
            scaledBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0,
                    scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix,
                    true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        FileOutputStream out = null;
        String filename = getFilename();
        try {
            out = new FileOutputStream(filename);

//          write the compressed bitmap at the destination specified by filename.
            scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 20, out);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return filename;

    }

    public String getFilename() {
        File file = new File(Environment.getExternalStorageDirectory().getPath(), "MyFolder/Images");
        if (!file.exists()) {
            file.mkdirs();
        }
        String uriSting = (file.getAbsolutePath() + "/" + System.currentTimeMillis() + ".jpg");
        return uriSting;

    }

    public int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        final float totalPixels = width * height;
        final float totalReqPixelsCap = reqWidth * reqHeight * 2;
        while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
            inSampleSize++;
        }

        return inSampleSize;
    }

    private String getRealPathFromURI(String contentURI) {
        Uri contentUri = Uri.parse(contentURI);
        Cursor cursor = getContentResolver().query(contentUri, null, null, null, null);
        if (cursor == null) {
            return contentUri.getPath();
        } else {
            cursor.moveToFirst();
            int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(index);
        }
    }

    public String TimeDiff3(Date startDate, Date endDate, SimpleDateFormat simpleDateFormat) {
        String timedata = "";
        if (endDate != null && startDate != null) {
            long difference = endDate.getTime() + startDate.getTime();
            if (difference < 0) {
                Date dateMax = null;
                Date dateMin = null;
                try {
                    dateMax = simpleDateFormat.parse("24:00");
                    dateMin = simpleDateFormat.parse("00:00");
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                difference = (dateMax.getTime() - startDate.getTime()) + (endDate.getTime() - dateMin.getTime());
            }
            int days = (int) (difference / (1000 * 60 * 60 * 24));
            int hours = (int) ((difference - (1000 * 60 * 60 * 24 * days)) / (1000 * 60 * 60));
            int min = (int) (difference - (1000 * 60 * 60 * 24 * days) - (1000 * 60 * 60 * hours)) / (1000 * 60);
            Log.i("log_tag", "Hours: " + hours + ", Mins: " + min);
            timedata = String.format("%02d:%02d", hours, min);
            Log.i("timeformat", timedata);
        }
        return timedata;
    }


    public String TimeDiff(Date startDate, Date endDate, SimpleDateFormat simpleDateFormat) {
        String timedata = "";
        if (endDate != null && startDate != null) {
            long difference = endDate.getTime() - startDate.getTime();
            if (difference < 0) {
                Date dateMax = null;
                Date dateMin = null;
                try {
                    dateMax = simpleDateFormat.parse("24:00");
                    dateMin = simpleDateFormat.parse("00:00");
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                difference = (dateMax.getTime() - startDate.getTime()) + (endDate.getTime() - dateMin.getTime());
            }
            int days = (int) (difference / (1000 * 60 * 60 * 24));
            int hours = (int) ((difference - (1000 * 60 * 60 * 24 * days)) / (1000 * 60 * 60));
            int min = (int) (difference - (1000 * 60 * 60 * 24 * days) - (1000 * 60 * 60 * hours)) / (1000 * 60);
            Log.i("log_tag", "Hours: " + hours + ", Mins: " + min);
            timedata = String.format("%02d:%02d", hours, min);
            Log.i("timeformat", timedata);
        }
        return timedata;
    }
}

//getIntentData();
/*
        timecard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TimeLog.this, Timecard.class));
            }
        });
*/

/*
        s_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openStartTimeDialog();
            }
        });
*/

/*
        e_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openEndTimeDialog();
            }
        });
*/


// in callend day api
/* if (rg2.getCheckedRadioButtonId() == R.id.rb4) {
                        if (rb4.getText().toString().equals("30 mins")) {
                            trvl_time = "00:30";
                        }
                    }
                    if (rg2.getCheckedRadioButtonId() == R.id.rb5) {
                        if (rb5.getText().toString().equals("60 mins")) {
                            trvl_time = "60:00";
                        }
                    }
                    if (rg2.getCheckedRadioButtonId() == R.id.rb6) {
                        if (rb6.getText().toString().equals("90 mins")) {
                            trvl_time = "90:00";
                        }
                    }
                    if (rg2.getCheckedRadioButtonId() == R.id.rb7) {
                        if (rb7.getText().toString().equals("120 mins")) {
                            trvl_time = "120:00";
                        }
                    }
*/
                 /*   if (selectedId1 == rg2.getCheckedRadioButtonId()) {
                        rb4 = (RadioButton) findViewById(selectedId1);
                        rb5 = (RadioButton) findViewById(selectedId1);
                        rb6 = (RadioButton) findViewById(selectedId1);
                        rb7 = (RadioButton) findViewById(selectedId1);
                        if (rb4.getText().toString().equals("30 mins")) {
                            trvl_time = "00:30";
                        } else if (rb5.getText().toString().equals("60 mins")) {
                            trvl_time = "60:00";
                        } else if (rb6.getText().toString().equals("90 mins")) {
                            trvl_time = "90:00";
                        } else if (rb7.getText().toString().equals("120 mins")) {
                            trvl_time = "120:00";
                        }
                    }*/

   /* private void openEndTimeDialog() {

        dialog = new Dialog(TimeLog.this);
        dialog.getWindow();
        dialog.setTitle("Address");
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_timepicker);
        dialog.setCancelable(true);

        TimePicker picker = dialog.findViewById(R.id.timePicker1);
        Button txt_ok = dialog.findViewById(R.id.btn_ok);
        ImageView txt_cancel = dialog.findViewById(R.id.img_close);
        picker.setIs24HourView(false);

        txt_ok.setOnClickListener(view -> {

            int hour, minute;
            String am_pm;
            if (Build.VERSION.SDK_INT >= 23) {
                hour = picker.getHour();
                minute = picker.getMinute();
            } else {
                hour = picker.getCurrentHour();
                minute = picker.getCurrentMinute();
            }
            if (hour > 12) {
                am_pm = "PM";
                hour = hour - 12;
            } else {
                am_pm = "AM";
            }
            //e_time.setText(hour + ":" + minute);
            dialog.cancel();
            dialog.dismiss();
        });

        txt_cancel.setOnClickListener(view -> {
            dialog.cancel();
            dialog.dismiss();
        });


        dialog.show();
        Window window = dialog.getWindow();
        assert window != null;
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    private void openStartTimeDialog() {

        dialog = new Dialog(TimeLog.this);
        dialog.getWindow();
        dialog.setTitle("Address");
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_timepicker);
        dialog.setCancelable(true);

        TimePicker picker = dialog.findViewById(R.id.timePicker1);
        Button txt_ok = dialog.findViewById(R.id.btn_ok);
        ImageView txt_cancel = dialog.findViewById(R.id.img_close);
        picker.setIs24HourView(true);

        txt_ok.setOnClickListener(view -> {

            int hour, minute;
            String am_pm;
            if (Build.VERSION.SDK_INT >= 23) {
                hour = picker.getHour();
                minute = picker.getMinute();
            } else {
                hour = picker.getCurrentHour();
                minute = picker.getCurrentMinute();
            }
            if (hour > 12) {
                am_pm = "PM";
                hour = hour - 12;
            } else {
                am_pm = "AM";
            }
            //s_time.setText(hour + ":" + minute);
            dialog.cancel();
            dialog.dismiss();
        });

        txt_cancel.setOnClickListener(view -> {
            dialog.cancel();
            dialog.dismiss();
        });

        dialog.show();
        Window window = dialog.getWindow();
        assert window != null;
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }*/

   /*   private void getIntentData() {

        wstime = getIntent().getStringExtra("startime");
        wetime = getIntent().getStringExtra("endtime");
        bstime = getIntent().getStringExtra("bstartime");
        betime = getIntent().getStringExtra("estartime");*/


//}