package com.athena.group.Fragment.HsQuestion;


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
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.athena.group.API.ApiInterface;
import com.athena.group.API.ApiServiceCreator;
import com.athena.group.Model.CommanModel;
import com.athena.group.Model.DemoModel;
import com.athena.group.Model.Example;
import com.athena.group.Model.HSQuestionModel;
import com.athena.group.Model.Model;
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
public class PlantFragment extends Fragment {

    Example.questiondata questiondata;
    private SessionManager sessionManager;
    private ApiInterface apiservice;
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
    @BindView(R.id.toolbar_Title)
    TextView toolbar_Title;
    @BindView(R.id.back_icon)
    ImageView back_icon;
    ArrayList<HSQuestionModel.Data> arrayList;
    String txtdate = "";
    final Calendar myCalendar = Calendar.getInstance();
    Example e;
    String validation = "No";
    String val, val1;
    List<Example.questiondata> questiondataList1 = new ArrayList<>();

    //List<CommanModel> commanModelslist;


    public PlantFragment() {
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

        arrayList = Utility.getAppcon().getSession().arrayListQuestion;

        toolbar_Title.setText(arrayList.get(0).getPlant().get(0).getHsLabel());
        txt_date.setText(Utility.date);
        txtdate = Utility.date;

        List<Example> arrayListQuestion = Utility.getAppcon().getSession().questiondataList16;
        if (arrayListQuestion.size() == 0) {

        } else {
            edt_cmtHSEQ.setText(arrayListQuestion.get(0).getAns_comment());
            txt_date.setText(arrayListQuestion.get(0).getAns_needdate());

        }


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

        List<CommanModel> commanModelslist = new ArrayList<>();
        for (int i = 0; i < arrayList.get(0).getPlant().size(); i++) {
            CommanModel commanModel = new CommanModel();
            commanModel.setHsId(arrayList.get(0).getPlant().get(i).getHsId());
            commanModel.setHsLabel(arrayList.get(0).getPlant().get(i).getHsLabel());
            commanModel.setHsQues(arrayList.get(0).getPlant().get(i).getHsQues());
            commanModelslist.add(commanModel);
        }

        recyclerview.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerview.setLayoutManager(layoutManager);
        adapter = new QuestionAdapter(getActivity(), commanModelslist, "plant", new QuestionAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(String validation1) {
                validation = validation1;
            }
        });
        recyclerview.setAdapter(adapter);
        back_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callNextApi1();
                /*PrtctngPblcSiteScrtyFragment fragment = new PrtctngPblcSiteScrtyFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.frm, fragment);
                fragmentTransaction.addToBackStack("first");
                Utility.getAppcon().getSession().arrayListQuestion = arrayList;
                fragmentTransaction.commit();*/

            }
        });

        questiondataList = new ArrayList<>();

        btn_previous.setOnClickListener(view -> {
            callNextApi1();

           /* PrtctngPblcSiteScrtyFragment fragment = new PrtctngPblcSiteScrtyFragment();
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.frm, fragment);
            fragmentTransaction.addToBackStack("first");
            Utility.getAppcon().getSession().arrayListQuestion = arrayList;
            fragmentTransaction.commit();*/
        });
        questiondataList = new ArrayList<>();
        questiondataList1 = questiondataList;
        btn_next.setOnClickListener(view -> {
            e = new Example();
            e.setAns_user_id(sessionManager.getKeyId());
            e.setAns_comment(edt_cmtHSEQ.getText().toString());
            e.setAns_needdate(txtdate);
            e.setLast_step(Utility.getAppcon().getSession().data_value);
            e.setQuestions_hs(questiondataList);

            List<Example> examples1 = new ArrayList<>();
            examples1.add(e);
            Utility.getAppcon().getSession().questiondataList16 = new ArrayList<>();
            Utility.getAppcon().getSession().questiondataList16 = examples1;

            for (int i = 0; i < questiondataList.size(); i++) {
                Log.e("Label", questiondataList.get(i).getAns_ques());
                Log.e("Value", questiondataList.get(i).getAns_ques_value());
                if (!questiondataList.get(i).getAns_ques_value().equals("")) {
                    Utility.getAppcon().getSession().check_valid16 = "yes";
                    //callNextApi();
                } else {
                    Utility.getAppcon().getSession().check_valid16 = "no";
                    //callNextApi();
                    break;
                }
            }

            if (Utility.getAppcon().getSession().check_valid16.equals("no")) {
                callNextApi();
            } else {
                callNextApi();
            }

            // callNextApi();
            /*if (questiondataList.get(0).getAns_ques_value().isEmpty()) {
                if (validation.equals("No")) {
                    Toast.makeText(getActivity(), "Please Select Yes/No", Toast.LENGTH_LONG).show();
                } else {
                    callNextApi();
                }
            } else {
                questiondataList1 = questiondataList;
                callNextApi();
            }*/
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
                            MaterialsFragment fragment = new MaterialsFragment();
                            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.add(R.id.frm, fragment);
                            fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();

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

    private void handleBackPress(View view, FragmentActivity activity) {
//        view.setFocusableInTouchMode(true);
//        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                   callNextApi1();
                    return true;
                }
                return false;
            }
        });
    }

    private void callNextApi1() {

        questiondataList.clear();
        Observable<DemoModel> responseObservable = apiservice.back_hsquestion_topic_data(Utility.getAppcon().getSession().data_value,arrayList.get(0).getProtectingThePublicAndSiteSecurity().get(0).getHsLabel());
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
                .subscribe(new Observer<DemoModel>() {
                    @Override
                    public void onCompleted() {
                        // avi.hide();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("error", "" + e.getMessage());
                    }

                    @Override
                    public void onNext(DemoModel model) {
                        avi.hide();
                        statusCode = model.getStatusCode();
                        if (statusCode == 200) {
                           /* Log.e("Response is1 ",""+statusCode);
                            Log.e("size is a",""+model.getData().size());
*/
                            // questiondataList=arrayListQuestion1.get(0).getQuestions_hs();
                            Log.e("questiondataList", String.valueOf(questiondataList.size()));
                            try {
                                for (int j=0;j<model.getData().size();j++)
                                {
                                   /* Log.e("J is  a",""+j);
                                    Log.e("Question is ",model.getData().get(j).getAnsQues());
                                    Log.e("Question is Ans ",model.getData().get(j).getAnsQuesValue());*/
                                    questiondata=new Example.questiondata();
                                    questiondata.setAns_ques(model.getData().get(j).getAnsQues());
                                    questiondata.setAns_label(model.getData().get(j).getAnsLabel());
                                    questiondata.setAns_ques_value(model.getData().get(j).getAnsQuesValue());
                                    questiondata.setAns_ques_value_no(model.getData().get(j).getAnsQuesValueNo());
                                    questiondataList.add(questiondata);
                                   /* questiondataList.get(j).setAns_label(""+model.getData().get(j).getAnsLabel());
                                    questiondataList.get(j).setAns_ques_value(""+model.getData().get(j).getAnsQuesValue());
                                    questiondataList.get(j).setAns_ques(""+model.getData().get(j).getAnsQues());
                                    questiondataList.get(j).setAns_ques_value_no(""+model.getData().get(j).getAnsQuesValueNo());*/

                                }
                            }
                            catch (Exception e)
                            {
                            }

                            e = new Example();
                            e.setAns_user_id(sessionManager.getKeyId());
                            e.setAns_comment(model.getData().get(0).getAnsComment());
                            e.setAns_needdate(model.getData().get(0).getAnsNeeddate());
                            e.setLast_step(Utility.getAppcon().getSession().data_value);
                            e.setQuestions_hs(questiondataList);
                            List<Example> examples = new ArrayList<>();
                            examples.add(e);
                            Utility.getAppcon().getSession().questiondataList15 = new ArrayList<>();
                            Utility.getAppcon().getSession().questiondataList15 = examples;

                            PrtctngPblcSiteScrtyFragment fragment = new PrtctngPblcSiteScrtyFragment();
                            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.add(R.id.frm, fragment);
                            fragmentTransaction.addToBackStack("first");
                            Utility.getAppcon().getSession().arrayListQuestion = arrayList;
                            fragmentTransaction.commit();
                        }
                        else {
                            PrtctngPblcSiteScrtyFragment fragment = new PrtctngPblcSiteScrtyFragment();
                            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.add(R.id.frm, fragment);
                            fragmentTransaction.addToBackStack("first");
                            Utility.getAppcon().getSession().arrayListQuestion = arrayList;
                            fragmentTransaction.commit();
                        }
                    }
                });
    }
}
