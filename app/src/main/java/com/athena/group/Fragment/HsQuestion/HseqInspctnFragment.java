package com.athena.group.Fragment.HsQuestion;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.Bundle;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.athena.group.API.ApiInterface;
import com.athena.group.API.ApiServiceCreator;
import com.athena.group.Model.CommanModel;
import com.athena.group.Model.CommanModel1;
import com.athena.group.Model.Example;
import com.athena.group.Model.Fstmodel;
import com.athena.group.Model.HSQuestionModel;
import com.athena.group.Model.Model;
import com.athena.group.Model.QuestionStartModel;
import com.athena.group.R;
import com.athena.group.adapter.QuestionAdapter;
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

import static com.athena.group.adapter.QuestionAdapter.questiondataList;

/**
 * A simple {@link Fragment} subclass.
 */
public class HseqInspctnFragment extends Fragment {
    CommanModel commanModel;
    CommanModel1 commanModel12;
    List<CommanModel1> commanModelslist1;
    CommanModel1.Data commanModel123;
    private SessionManager sessionManager;
    private ApiInterface apiservice;
    List<CommanModel1.Data> datalist;
    private int statusCode;
    private QuestionAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    /* @BindView(R.id.avi)
     AVLoadingIndicatorView avi;*/
    @BindView(R.id.recyerview)
    RecyclerView recyclerview;
    @BindView(R.id.btn_next)
    LinearLayout btn_next;
    @BindView(R.id.avi)
    AVLoadingIndicatorView avi;
    @BindView(R.id.btn_previous)
    LinearLayout btn_previous;
    @BindView(R.id.edt_cmtHSEQ)
    EditText edt_cmtHSEQ;

    @BindView(R.id.txt_date)
    TextView txt_date;
    String txtdate = "";
    final Calendar myCalendar = Calendar.getInstance();
    ArrayList<HSQuestionModel.Data> arrayList;
    Example e;
    @BindView(R.id.toolbar_Title)
    TextView toolbar_Title;
    @BindView(R.id.back_icon)
    ImageView back_icon;
    String validation = "No";

    QuestionStartModel qmodel;

    public HseqInspctnFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view1 = inflater.inflate(R.layout.fragment_hseqinspctn, container, false);
        ButterKnife.bind(this, view1);
        sessionManager = new SessionManager(getActivity());
        apiservice = ApiServiceCreator.createService("latest");
        toolbar_Title.setText("HSEQ Inspection");
        // callNextApi1();
        callApi();
        List<Example> arrayListQuestion = Utility.getAppcon().getSession().questiondataList1;
        if (arrayListQuestion.size() == 0) {
        } else {
            edt_cmtHSEQ.setText(arrayListQuestion.get(0).getAns_comment());
            txt_date.setText(arrayListQuestion.get(0).getAns_needdate());
        }

        back_icon.setOnClickListener(view -> {
            callNextApi1();
           /* QuestionStartFragment fragment = new QuestionStartFragment();
            //SummaryFragment fragment = new SummaryFragment();
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frm, fragment);
            fragmentTransaction.addToBackStack("first");
            Utility.getAppcon().getSession().arrayListQuestion = arrayList;
            fragmentTransaction.commit();*/
        });

        btn_previous.setOnClickListener(view -> {
            callNextApi1();
           /* QuestionStartFragment fragment = new QuestionStartFragment();
            //SummaryFragment fragment = new SummaryFragment();
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frm, fragment);
            fragmentTransaction.addToBackStack("first");
            Utility.getAppcon().getSession().arrayListQuestion = arrayList;
            fragmentTransaction.commit();*/
        });

        txt_date.setText(Utility.date);
        txtdate = Utility.date;

        edt_cmtHSEQ.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent event) {
                // TODO Auto-generated method stub
                if (view.getId() == R.id.edt_cmtHSEQ) {
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
        txt_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(getActivity(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        questiondataList = new ArrayList<>();

        btn_next.setOnClickListener(view -> {
            e = new Example();
            e.setAns_user_id(sessionManager.getKeyId());
            e.setAns_comment(edt_cmtHSEQ.getText().toString());
            e.setAns_needdate(txtdate);
            //e.setLast_step("");
            e.setLast_step(Utility.getAppcon().getSession().data_value);
            e.setQuestions_hs(questiondataList);

            List<Example> examples = new ArrayList<>();
            examples.add(e);
            Utility.getAppcon().getSession().questiondataList1 = new ArrayList<>();
            Utility.getAppcon().getSession().questiondataList1 = examples;

            //callNextApi();
            Log.e("TAG", "questiondataList 1: " + questiondataList.size());

            for (int i = 0; i < questiondataList.size(); i++) {
                Log.e("Label", questiondataList.get(i).getAns_ques());
                Log.e("Value", questiondataList.get(i).getAns_ques_value());
                if (!questiondataList.get(i).getAns_ques_value().equals("")) {
                    Utility.getAppcon().getSession().check_valid1 = "yes";
                    //callNextApi();
                } else {
                    Utility.getAppcon().getSession().check_valid1 = "no";
                    //callNextApi();
                    break;
                }
            }

            if (Utility.getAppcon().getSession().check_valid1.equals("no")) {
                callNextApi();
            } else {
                callNextApi();
            }

//            if (validation.equals("No")) {
//                Utility.getAppcon().getSession().check_valid1 = "no";
//                //callNextApi();
//                Toast.makeText(getActivity(), "Please Select Yes/No", Toast.LENGTH_LONG).show();
//            } else {
//                Utility.getAppcon().getSession().check_valid1 = "yes";
//                callNextApi();
//            }

//            } else {
//                Utility.getAppcon().getSession().check_valid1 = "yes";
//                callNextApi();
//            }
            //  }

        });

        handleBackPress(view1, getActivity());
        return view1;
    }

    private void callNextApi() {
        avi.show();
        Observable<Model> responseObservable = apiservice.insertHsquestionDetails(e);
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
                            Utility.displayToast(getActivity(), model.getMessage());
                            //Log.e("TAG", "onNext: " + model.getData());
                            // Utility.getAppcon().getSession().data_value = model.getData();

                            InspctnandReprtngFragment fragment = new InspctnandReprtngFragment();
                            //SummaryFragment fragment = new SummaryFragment();
                            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.frm, fragment);
                            fragmentTransaction.addToBackStack("first");
                            Utility.getAppcon().getSession().arrayListQuestion = arrayList;
                            fragmentTransaction.commit();

                            // questiondataList.clear();
                        }
                    }
                });
    }


    private void callNextApi1() {
        // avi.show();
        Observable<Fstmodel> responseObservable = apiservice.first_hsquestion_topic_data(Utility.getAppcon().getSession().data_value);
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
                .subscribe(new Observer<Fstmodel>() {
                    @Override
                    public void onCompleted() {
                        // avi.hide();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("error", "" + e.getMessage());
                    }

                    @Override
                    public void onNext(Fstmodel model) {
                        avi.hide();
                        statusCode = model.getStatusCode();
                        if (statusCode == 200) {

                            // model.getData().get(i).getSite_number() + " , " + model.getData().get(i).getSiteName()

                            qmodel = new QuestionStartModel();
                            qmodel.setSite(model.getData().get(0).getSite_number() + " , " + model.getData().get(0).getSite_name());
                            qmodel.setSupervisor(model.getData().get(0).getAnsNameofsup());
                            qmodel.setContractor(model.getData().get(0).getContractor_name());
                            qmodel.setInspectedby(model.getData().get(0).getAnsInspectedBy());
                            qmodel.setReportref(model.getData().get(0).getAnsReportReference());

                            //sessionManager.setQueStartDetails(new Gson().toJson(qmodel));
                            List<QuestionStartModel> qslist = new ArrayList<>();
                            qslist.add(qmodel);
                            Utility.getAppcon().getSession().questionstart = new ArrayList<>();
                            Utility.getAppcon().getSession().questionstart = qslist;
                            QuestionStartFragment fragment = new QuestionStartFragment();
                            //SummaryFragment fragment = new SummaryFragment();
                            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.frm, fragment);
                            fragmentTransaction.addToBackStack("first");
                            Utility.getAppcon().getSession().arrayListQuestion = arrayList;
                            fragmentTransaction.commit();
                        }
                    }

                });
    }

    private void callApi() {
        recyclerview.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerview.setLayoutManager(layoutManager);
        avi.show();
        Observable<HSQuestionModel> responseObservable = apiservice.get_allQuestion();
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
                .subscribe(new Observer<HSQuestionModel>() {
                    @Override
                    public void onCompleted() {
                        avi.hide();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("error", "" + e.getMessage());
                    }

                    @Override
                    public void onNext(HSQuestionModel model) {
                        avi.hide();
                        statusCode = model.getStatusCode();
                        Log.e("Response is ", String.valueOf(statusCode));
                        if (statusCode == 200) {

                            Utility.getAppcon().getSession().arrayListQuestion = new ArrayList<>();
                            Utility.getAppcon().getSession().arrayListQuestion = model.getData();
                            arrayList = model.getData();
                            List<CommanModel> commanModelslist = new ArrayList<>();


                            commanModelslist1 = new ArrayList<>();
                            datalist = new ArrayList<>();
                            for (int i = 0; i < model.getData().get(0).getHSEQInspections().size(); i++) {
                                commanModel = new CommanModel();
                                commanModel.setHsId(model.getData().get(0).getHSEQInspections().get(i).getHsId());
                                commanModel.setHsLabel(model.getData().get(0).getHSEQInspections().get(i).getHsLabel());
                                commanModel.setHsQues(model.getData().get(0).getHSEQInspections().get(i).getHsQues());
                                commanModelslist.add(commanModel);
                                commanModel12 = new CommanModel1();
                                commanModel12.setPosition("0");
                                commanModelslist1.add(commanModel12);
                                commanModel123 = new CommanModel1.Data();
                                commanModel123.setHsId(model.getData().get(0).getHSEQInspections().get(i).getHsId());
                                commanModel123.setHsLabel(model.getData().get(0).getHSEQInspections().get(i).getHsLabel());
                                commanModel123.setHsQues(model.getData().get(0).getHSEQInspections().get(i).getHsQues());
                                datalist.add(commanModel123);
                            }

                            Utility.displayToast(getActivity(), model.getMessage());
                            adapter = new QuestionAdapter(getActivity(), commanModelslist, "hseqinspection", new QuestionAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(String validation1) {
                                    validation = validation1;
                                    Log.e("TAG", "validation --: " + validation);
                                }
                            });
                            recyclerview.setAdapter(adapter);

                        }

                    }
                });
    }

    private void updateLabel() {
        String myFormat = "dd-MM-yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        txt_date.setText(sdf.format(myCalendar.getTime()));
        txtdate = sdf.format(myCalendar.getTime());
    }

    private void handleBackPress(View view, Activity activity) {
        // view.setFocusableInTouchMode(true);
        //  view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    callNextApi1();
                    /*QuestionStartFragment fragment = new QuestionStartFragment();
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frm, fragment);
                    fragmentTransaction.addToBackStack("first");
                    fragmentTransaction.commit();*/
                    return true;
                }
                return false;
            }
        });
    }
}