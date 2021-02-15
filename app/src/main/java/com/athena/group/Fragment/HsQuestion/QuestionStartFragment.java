package com.athena.group.Fragment.HsQuestion;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.athena.group.API.ApiInterface;
import com.athena.group.API.ApiServiceCreator;
import com.athena.group.MainActivity;
import com.athena.group.Model.Model;
import com.athena.group.Model.QuestionStartModel;
import com.athena.group.Model.SiteContractorModel;
import com.athena.group.Model.SpinnerModel;
import com.athena.group.R;
import com.athena.group.application.SessionManager;
import com.athena.group.application.Utility;
import com.google.gson.Gson;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class QuestionStartFragment extends Fragment {

    @BindView(R.id.toolbar_Title)
    TextView toolbar_Title;
    @BindView(R.id.back_icon)
    ImageView back_icon;
    @BindView(R.id.btn_next)
    LinearLayout btn_next;
    @BindView(R.id.avi)
    AVLoadingIndicatorView avi;
    @BindView(R.id.spn_site)
    Spinner spn_site;
    @BindView(R.id.spn_sprvsr)
    SearchableSpinner spn_sprvsr;
    @BindView(R.id.txt_start)
    TextView txt_start;
    @BindView(R.id.txt_date)
    TextView txt_date;
    @BindView(R.id.txt_time)
    TextView txt_time;
    @BindView(R.id.img_clndr)
    ImageView img_clndr;
    @BindView(R.id.img_time)
    ImageView img_time;
    /*@BindView(R.id.txt_sprvsrname)
    TextView txt_sprvsrname;*/
    /*   @BindView(R.id.edt_inspcn)
       EditText edt_inspcn;*/
    @BindView(R.id.spn_contractor)
    SearchableSpinner spn_contractor;
    @BindView(R.id.edt_inspctby)
    EditText edt_inspctby;
    @BindView(R.id.edt_rprtref)
    EditText edt_rprtref;
    private SessionManager sessionManager;
    private ApiInterface apiservice;
    private int statusCode;
    String site = "", s_id = "";
    List<SpinnerModel.Data> data = new ArrayList<>();
    List<SpinnerModel.Data> data2;
    ArrayList<String> sitearray = new ArrayList<>();
    ArrayList<String> suprvsrarray = new ArrayList<>();
    String name = "", txtdate = "", txttime = "", sprvsr = "";
    final Calendar myCalendar = Calendar.getInstance();
    List<SiteContractorModel.Data> data1;
    String contractor = "", c_id = "", suprvsrname = "";
    ArrayList<String> contractrArray;
    private int mYear, mMonth, mDay, mHour, mMinute;
    String datetime = "";
    QuestionStartModel qmodel;
    List<QuestionStartModel> arrayListQuestion;
    ArrayAdapter<String> conAdapter;
    ArrayAdapter<String> siteAdapter;
    ArrayAdapter<String> supAdapter;

    public QuestionStartFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view1 = inflater.inflate(R.layout.fragment_inspectnstart, container, false);
        ButterKnife.bind(this, view1);
        sessionManager = new SessionManager(getActivity());
        apiservice = ApiServiceCreator.createService("latest");
        toolbar_Title.setText("H & S Report Form");

        initView();

        handleBackPress(view1);
        spn_sprvsr.setTitle("Select Supervisor");
        spn_contractor.setTitle("Select Contractor");
        return view1;
    }

    private void initView() {

        String time = new SimpleDateFormat("hh:mm a").format(Calendar.getInstance().getTime());

        /*txt_sprvsrname.setText(sessionManager.getKeyName() + " " + sessionManager.getKeyLname());
        suprvsrname = sessionManager.getKeyName() + " " + sessionManager.getKeyLname();*/
        txt_time.setText(time);
        txt_date.setText(Utility.date);
        txtdate = Utility.date;
        txttime = time;

       /* if (sessionManager.getQuestionstart().size() == 0)
        {

        }else */
        arrayListQuestion = Utility.getAppcon().getSession().questionstart;
        Log.e("arrayListQuestion--", new Gson().toJson(arrayListQuestion));

        if (arrayListQuestion.size() == 0) {
        } else {
            edt_inspctby.setText(arrayListQuestion.get(0).getInspectedby());
            edt_rprtref.setText(arrayListQuestion.get(0).getReportref());
        }

        /*Utility.getAppcon().getSession().questiondataList1.clear();
        Utility.getAppcon().getSession().questiondataList2.clear();
        Utility.getAppcon().getSession().questiondataList3.clear();
        Utility.getAppcon().getSession().questiondataList4.clear();
        Utility.getAppcon().getSession().questiondataList5.clear();
        Utility.getAppcon().getSession().questiondataList6.clear();
        Utility.getAppcon().getSession().questiondataList7.clear();
        Utility.getAppcon().getSession().questiondataList8.clear();
        Utility.getAppcon().getSession().questiondataList9.clear();
        Utility.getAppcon().getSession().questiondataList10.clear();
        Utility.getAppcon().getSession().questiondataList11.clear();
        Utility.getAppcon().getSession().questiondataList12.clear();
        Utility.getAppcon().getSession().questiondataList13.clear();
        Utility.getAppcon().getSession().questiondataList14.clear();
        Utility.getAppcon().getSession().questiondataList15.clear();
        Utility.getAppcon().getSession()
        .questiondataList16.clear();
        Utility.getAppcon().getSession().questiondataList17.clear();
        Utility.getAppcon().getSession().questiondataList18.clear();*/

        edt_inspctby.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent event) {
                // TODO Auto-generated method stub
                if (view.getId() == R.id.edt_inspctby) {
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
        edt_rprtref.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent event) {
                // TODO Auto-generated method stub
                if (view.getId() == R.id.edt_rprtref) {
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

        back_icon.setOnClickListener(view -> {
            Intent intent = new Intent(getActivity(), MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });

        getSiteName();
        spn_site.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (spn_site.getSelectedItem() == "Select site") {
                    site = "";
                    //Do nothing.
                } else {
                    //try {
                    site = spn_site.getSelectedItem().toString();
                    SpinnerModel.Data datalist = data.get(i - 1);
                    s_id = datalist.getSiteId();
                    getSiteContractor(s_id);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        getSuprvsrName();
        spn_sprvsr.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (spn_sprvsr.getSelectedItem() == "Select Supervisor") {
                    sprvsr = "";
                    //Do nothing.
                } else {
                    sprvsr = spn_sprvsr.getSelectedItem().toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        //getContractorName();
        spn_contractor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (spn_contractor.getSelectedItem() == "Select Contractor") {
                    contractor = "";
                    //Do nothing.
                } else {
                    contractor = spn_contractor.getSelectedItem().toString();
                    SiteContractorModel.Data datalist1 = data1.get(i - 1);
                    c_id = datalist1.getUserId();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btn_next.setOnClickListener(view -> {

            qmodel = new QuestionStartModel();
            qmodel.setSite(site);
            qmodel.setSupervisor(sprvsr);
            qmodel.setContractor(contractor);
            qmodel.setInspectedby(edt_inspctby.getText().toString());
            qmodel.setReportref(edt_rprtref.getText().toString());

            //sessionManager.setQueStartDetails(new Gson().toJson(qmodel));
            List<QuestionStartModel> qslist = new ArrayList<>();
            qslist.add(qmodel);
            Utility.getAppcon().getSession().questionstart = new ArrayList<>();
            Utility.getAppcon().getSession().questionstart = qslist;

            Log.e("questionstart--", new Gson().toJson(Utility.getAppcon().getSession().questionstart));

            if (s_id.equals("")) {
                Utility.displayToast(getActivity(), "Please Select Site");
            } else if (sprvsr.equals("")) {
                Utility.displayToast(getActivity(), "Please Select Supervisor");
            } else if (c_id.equals("")) {
                Utility.displayToast(getActivity(), "Please Select Contract manager");
            } else if (edt_inspctby.getText().toString().equals("")) {
                Utility.displayToast(getActivity(), "Please Enter Inspector name");
            } else {
                callFormApi();
            }
            //callFormApi();
        });

        img_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(),
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {

                                String AM_PM = " AM";
                                String mm_precede = "";
                                if (hourOfDay >= 12) {
                                    AM_PM = " PM";
                                    if (hourOfDay >= 13 && hourOfDay < 24) {
                                        hourOfDay -= 12;
                                    } else {
                                        hourOfDay = 12;
                                    }
                                } else if (hourOfDay == 0) {
                                    hourOfDay = 12;
                                }
                                if (minute < 10) {
                                    mm_precede = "0";
                                }
                                txt_time.setText(hourOfDay + ":" + mm_precede + minute + AM_PM);
                                txttime = hourOfDay + ":" + mm_precede + minute + AM_PM;
                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();
            }
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
        img_clndr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(getActivity(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private void updateLabel() {
        String myFormat = "dd-MM-yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        txt_date.setText(sdf.format(myCalendar.getTime()));
        txtdate = sdf.format(myCalendar.getTime());
    }

    private void callFormApi() {
        avi.show();

        if (Utility.getAppcon().getSession().data_value.equals("")) {
            Observable<Model> responseObservable = apiservice.insert_question_FirstDetails(
                    sessionManager.getKeyId(),
                    sprvsr,
                    "",
                    "",
                    "",
                    s_id,
                    //txtdate,
                    //datetime,
                    txtdate + " " + txttime,
                    c_id,
                    //edt_nmofcontrct.getText().toString(),
                    edt_inspctby.getText().toString(),
                    //edt_sprvsr.getText().toString(),
                    sprvsr,
                    edt_rprtref.getText().toString(),
                    Utility.getAppcon().getSession().data_value);

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
                            //avi.hide();
                            statusCode = model.getStatusCode();
                            if (statusCode == 200) {

                                Utility.displayToast(getActivity(), model.getMessage());
                                Log.e("TAG", "onNext: " + model.getData());
                                Utility.getAppcon().getSession().data_value = model.getData();

                                //InstructionFragment fragment = new InstructionFragment();
                                HseqInspctnFragment fragment = new HseqInspctnFragment();
                                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                fragmentTransaction.add(R.id.frm, fragment);
                                fragmentTransaction.addToBackStack(null);
                                fragmentTransaction.commit();
                            }
                        }
                    });
        } else {
            Observable<Model> responseObservable = apiservice.insert_question_FirstDetails(
                    sessionManager.getKeyId(),
                    sprvsr,
                    "",
                    "",
                    "",
                    s_id,
                    //txtdate,
                    //datetime,
                    txtdate + " " + txttime,
                    c_id,
                    //edt_nmofcontrct.getText().toString(),
                    edt_inspctby.getText().toString(),
                    //edt_sprvsr.getText().toString(),
                    sprvsr,
                    edt_rprtref.getText().toString(),
                    Utility.getAppcon().getSession().data_value);

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
                            //avi.hide();
                            statusCode = model.getStatusCode();
                            if (statusCode == 200) {

                                Utility.displayToast(getActivity(), model.getMessage());
                                Log.e("TAG", "onNext: " + model.getData());
                                //Utility.getAppcon().getSession().data_value = model.getData();

                                //InstructionFragment fragment = new InstructionFragment();
                                HseqInspctnFragment fragment = new HseqInspctnFragment();
                                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                fragmentTransaction.add(R.id.frm, fragment);
                                fragmentTransaction.addToBackStack(null);
                                fragmentTransaction.commit();
                            }
                        }
                    });
        }

    }

    private void handleBackPress(View view) {
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {

                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    return true;
                }
                return false;
            }
        });
    }

    private void getSiteName() {

        try {
            // avi.show();
            //Change Shirish
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

                            //avi.hide();
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

                                siteAdapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, sitearray);
                                //dataAdapter.add("Select site");
                                siteAdapter.insert("Select site", 0);
                                siteAdapter.setDropDownViewResource(R.layout.spinner_item);
                                spn_site.setAdapter(siteAdapter);

                                String compareValue = arrayListQuestion.get(0).getSite();
                                if (compareValue != null) {
                                    int spinnerPosition = siteAdapter.getPosition(compareValue);
                                    spn_site.setSelection(spinnerPosition);

                                    Log.e("spinnerPosition", "" + spinnerPosition);
                                }

                            } else {
                                Utility.displayToast(getActivity(), model.getMessage());
                            }
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getSuprvsrName() {

        try {
            //avi.show();
            Observable<SpinnerModel> responseObservable = apiservice.get_all_supervisor();

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

                            // avi.hide();
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.e("error", "" + e.getMessage());
                        }

                        @Override
                        public void onNext(SpinnerModel model) {
                            statusCode = model.getStatusCode();
                            if (statusCode == 200) {
                                data2 = model.getData();
                                for (int i = 0; i < model.getData().size(); i++) {
                                    suprvsrarray.add(model.getData().get(i).getName() + " " + model.getData().get(i).getLname());
                                }

                                supAdapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, suprvsrarray);
                                //supAdapter.add("Select Supervisor");
                                supAdapter.insert("Select Supervisor", 0);
                                supAdapter.setDropDownViewResource(R.layout.spinner_item);
                                spn_sprvsr.setAdapter(supAdapter);

                                String compareValue = arrayListQuestion.get(0).getSupervisor();
                                if (compareValue != null) {
                                    int spinnerPosition = supAdapter.getPosition(compareValue);
                                    spn_sprvsr.setSelection(spinnerPosition);
                                    Log.e("spinnerPosition sup", "" + spinnerPosition);
                                }
                            } else {
                                Utility.displayToast(getActivity(), model.getMessage());
                            }
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getSiteContractor(String s_id) {
        try {
            // avi.show();
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
                            // avi.hide();
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.e("error", "" + e.getMessage());
                        }

                        @Override
                        public void onNext(SiteContractorModel model) {
                            statusCode = model.getStatusCode();
                            if (statusCode == 200) {
                                // avi.hide();
                                data1 = model.getData();
                                for (int i = 0; i < model.getData().size(); i++) {
                                    contractrArray.add(model.getData().get(i).getName() + " " + model.getData().get(i).getLname());
                                }

                                conAdapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, contractrArray);
                                //dataAdapter.add("Select Contractor");
                                conAdapter.insert("Select Contractor", 0);
                                conAdapter.setDropDownViewResource(R.layout.spinner_item);
                                spn_contractor.setAdapter(conAdapter);

                                String compareValue = arrayListQuestion.get(0).getContractor();
                                if (compareValue != null) {
                                    int spinnerPosition = conAdapter.getPosition(compareValue);
                                    spn_contractor.setSelection(spinnerPosition);

                                    Log.e("spinnerPosition con--", "" + spinnerPosition);
                                }
                            } else {
                                Utility.displayToast(getActivity(), model.getMessage());
                            }
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}


