package com.athena.group;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import butterknife.ButterKnife;
import rx.Observable;
import rx.Observer;
import rx.schedulers.Schedulers;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

import com.athena.group.API.ApiInterface;
import com.athena.group.API.ApiServiceCreator;
import com.athena.group.Fragment.HsQuestion.CompetenceTraningFragment;
import com.athena.group.Fragment.HsQuestion.DocumentationFragment;
import com.athena.group.Fragment.HsQuestion.EmergencyProFragment;
import com.athena.group.Fragment.HsQuestion.ExcavationFragment;
import com.athena.group.Fragment.HsQuestion.HseqInspctnFragment;
import com.athena.group.Fragment.HsQuestion.InspctnandReprtngFragment;
import com.athena.group.Fragment.HsQuestion.MaterialsFragment;
import com.athena.group.Fragment.HsQuestion.NBFragment;
import com.athena.group.Fragment.HsQuestion.NoisVibrtnDustFragment;
import com.athena.group.Fragment.HsQuestion.PTWFragment;
import com.athena.group.Fragment.HsQuestion.PlantFragment;
import com.athena.group.Fragment.HsQuestion.PortableToolsFragment;
import com.athena.group.Fragment.HsQuestion.PrtctngPblcSiteScrtyFragment;
import com.athena.group.Fragment.HsQuestion.RaMsFragment;
import com.athena.group.Fragment.HsQuestion.SummaryFragment;
import com.athena.group.Fragment.HsQuestion.TraficMngmntFragment;
import com.athena.group.Fragment.HsQuestion.VehicalVMPMFragment;
import com.athena.group.Fragment.HsQuestion.VisualChecksFragment;
import com.athena.group.Fragment.HsQuestion.WelfareFragment;
import com.athena.group.Model.CommanModel;
import com.athena.group.Model.CommanModel1;
import com.athena.group.Model.Example;
import com.athena.group.Model.HSQuestionModel;
import com.athena.group.adapter.QuestionAdapter;
import com.athena.group.application.SessionManager;
import com.athena.group.application.Utility;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import static com.athena.group.adapter.QuestionAdapter.questiondataList;

public class HSQuestionActivity1 extends AppCompatActivity {
    Example.questiondata questiondata;
    Example e;
    FragmentTransaction transaction;
    private SessionManager sessionManager;
    private ApiInterface apiservice;
    private int statusCode;
    List<CommanModel1.Data> datalist;
    ArrayList<HSQuestionModel.Data> arrayList;
    CommanModel commanModel;
    CommanModel1 commanModel12;
    List<CommanModel1> commanModelslist1;
    CommanModel1.Data commanModel123;
    private QuestionAdapter adapter;
    String old_label;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_h_s_question1);
        ButterKnife.bind(this);
        sessionManager = new SessionManager(this);
        apiservice = ApiServiceCreator.createService("latest");

        //toolbar_Title.setText("HSQuestion Form");
        //back_icon.setOnClickListener(view -> finish());
        questiondataList = new ArrayList<>();
        callApi();
        // getResultData();
        //fragment = new HseqInspctnFragment();
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        old_label = bundle.getString("label");
        String last_data = Utility.getAppcon().getSession().data_value;
        Log.e("Last ", Utility.getAppcon().getSession().data_value);

    }


    private void callApi() {

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
                        /*avi.hide();*/
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("error", "" + e.getMessage());
                    }

                    @Override
                    public void onNext(HSQuestionModel model) {
                        /*       avi.hide();*/
                        statusCode = model.getStatusCode();
                        Log.e("Response is ", String.valueOf(statusCode));
                        if (statusCode == 200) {

                            Utility.getAppcon().getSession().arrayListQuestion = new ArrayList<>();
                            Utility.getAppcon().getSession().arrayListQuestion = model.getData();
                            arrayList = model.getData();
                            Log.e("Data is", String.valueOf(arrayList));
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

                            Utility.displayToast(getApplicationContext(), model.getMessage());
                            adapter = new QuestionAdapter(getApplicationContext(), commanModelslist, "hseqinspection", new QuestionAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(String validation1) {
                                    // validation = validation1;
                                    //Log.e("TAG", "validation --: " + validation);
                                }
                            });
                            //recyclerview.setAdapter(adapter);

//                            getResultData();

//1
                            if (old_label.equals("HSEQ Inspections")) {
                                HseqInspctnFragment fragment = new HseqInspctnFragment();
                                //SummaryFragment fragment = new SummaryFragment();
                                FragmentManager fragmentManager = getSupportFragmentManager();
                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                fragmentTransaction.replace(R.id.frm, fragment);
                                fragmentTransaction.addToBackStack("first");
                                //Utility.getAppcon().getSession().arrayListQuestion = arrayList;
                                fragmentTransaction.commit();
                            }
                            //2
                            else if (old_label.equals("Inspection and Reporting")) {
                                InspctnandReprtngFragment fragment = new InspctnandReprtngFragment();
                                //SummaryFragment fragment = new SummaryFragment();
                                FragmentManager fragmentManager = getSupportFragmentManager();
                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                fragmentTransaction.replace(R.id.frm, fragment);
                                fragmentTransaction.addToBackStack("first");
                                //Utility.getAppcon().getSession().arrayListQuestion = arrayList;
                                fragmentTransaction.commit();
                            }
                            //3
                            else if (old_label.equals("Documentation")) {
                                DocumentationFragment fragment = new DocumentationFragment();
                                //SummaryFragment fragment = new SummaryFragment();
                                FragmentManager fragmentManager = getSupportFragmentManager();
                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                fragmentTransaction.replace(R.id.frm, fragment);
                                fragmentTransaction.addToBackStack("first");
                                //Utility.getAppcon().getSession().arrayListQuestion = arrayList;
                                fragmentTransaction.commit();
                            }
                            //4
                            else if (old_label.equals("Competence & Training")) {
                                CompetenceTraningFragment fragment = new CompetenceTraningFragment();
                                //SummaryFragment fragment = new SummaryFragment();
                                FragmentManager fragmentManager = getSupportFragmentManager();
                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                fragmentTransaction.replace(R.id.frm, fragment);
                                fragmentTransaction.addToBackStack("first");
                                //Utility.getAppcon().getSession().arrayListQuestion = arrayList;
                                fragmentTransaction.commit();
                            }
//5
                            else if (old_label.equals("Welfare")) {
                                WelfareFragment fragment = new WelfareFragment();
                                //SummaryFragment fragment = new SummaryFragment();
                                FragmentManager fragmentManager = getSupportFragmentManager();
                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                fragmentTransaction.replace(R.id.frm, fragment);
                                fragmentTransaction.addToBackStack("first");
                                //Utility.getAppcon().getSession().arrayListQuestion = arrayList;
                                fragmentTransaction.commit();
                            }
                            //6
                            else if (old_label.equals("Emergency Procedures")) {
                                EmergencyProFragment fragment = new EmergencyProFragment();
                                //SummaryFragment fragment = new SummaryFragment();
                                FragmentManager fragmentManager = getSupportFragmentManager();
                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                fragmentTransaction.replace(R.id.frm, fragment);
                                fragmentTransaction.addToBackStack("first");
                                //Utility.getAppcon().getSession().arrayListQuestion = arrayList;
                                fragmentTransaction.commit();
                            }
                            //7
                            else if (old_label.equals("Risk Assessment/Method Statements(RAMS)")) {
                                RaMsFragment fragment = new RaMsFragment();
                                //SummaryFragment fragment = new SummaryFragment();
                                FragmentManager fragmentManager = getSupportFragmentManager();
                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                fragmentTransaction.replace(R.id.frm, fragment);
                                fragmentTransaction.addToBackStack("first");
                                //Utility.getAppcon().getSession().arrayListQuestion = arrayList;
                                fragmentTransaction.commit();
                            }
                            //8
                            else if (old_label.equals("Existing Services")) {
                                NBFragment fragment = new NBFragment();
                                //SummaryFragment fragment = new SummaryFragment();
                                FragmentManager fragmentManager = getSupportFragmentManager();
                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                fragmentTransaction.replace(R.id.frm, fragment);
                                fragmentTransaction.addToBackStack("first");
                                //Utility.getAppcon().getSession().arrayListQuestion = arrayList;
                                fragmentTransaction.commit();
                            }
                            //9
                            else if (old_label.equals("Permits to Work")) {
                                PTWFragment fragment = new PTWFragment();
                                //SummaryFragment fragment = new SummaryFragment();
                                FragmentManager fragmentManager = getSupportFragmentManager();
                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                fragmentTransaction.replace(R.id.frm, fragment);
                                fragmentTransaction.addToBackStack("first");
                                //Utility.getAppcon().getSession().arrayListQuestion = arrayList;
                                fragmentTransaction.commit();
                            }
                            //10
                            else if (old_label.equals("Traffic Management")) {
                                TraficMngmntFragment fragment = new TraficMngmntFragment();
                                //SummaryFragment fragment = new SummaryFragment();
                                FragmentManager fragmentManager = getSupportFragmentManager();
                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                fragmentTransaction.replace(R.id.frm, fragment);
                                fragmentTransaction.addToBackStack("first");
                                //Utility.getAppcon().getSession().arrayListQuestion = arrayList;
                                fragmentTransaction.commit();
                            }
                            //11
                            else if (old_label.equals("Vehicles, Vehicle Movements, Plant and Machinery")) {
                                VehicalVMPMFragment fragment = new VehicalVMPMFragment();
                                //SummaryFragment fragment = new SummaryFragment();
                                FragmentManager fragmentManager = getSupportFragmentManager();
                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                fragmentTransaction.replace(R.id.frm, fragment);
                                fragmentTransaction.addToBackStack("first");
                                //Utility.getAppcon().getSession().arrayListQuestion = arrayList;
                                fragmentTransaction.commit();
                            }
                            //12
                            else if (old_label.equals("Portable Tools")) {
                                PortableToolsFragment fragment = new PortableToolsFragment();
                                //SummaryFragment fragment = new SummaryFragment();
                                FragmentManager fragmentManager = getSupportFragmentManager();
                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                fragmentTransaction.replace(R.id.frm, fragment);
                                fragmentTransaction.addToBackStack("first");
                                //Utility.getAppcon().getSession().arrayListQuestion = arrayList;
                                fragmentTransaction.commit();
                            }
                            //13
                            else if (old_label.equals("Noise, Vibration and Dust")) {
                                NoisVibrtnDustFragment fragment = new NoisVibrtnDustFragment();
                                //SummaryFragment fragment = new SummaryFragment();
                                FragmentManager fragmentManager = getSupportFragmentManager();
                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                fragmentTransaction.replace(R.id.frm, fragment);
                                fragmentTransaction.addToBackStack("first");
                                //Utility.getAppcon().getSession().arrayListQuestion = arrayList;
                                fragmentTransaction.commit();
                            }
                            //14
                            else if (old_label.equals("Excavations")) {
                                ExcavationFragment fragment = new ExcavationFragment();
                                //SummaryFragment fragment = new SummaryFragment();
                                FragmentManager fragmentManager = getSupportFragmentManager();
                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                fragmentTransaction.replace(R.id.frm, fragment);
                                fragmentTransaction.addToBackStack("first");
                                //Utility.getAppcon().getSession().arrayListQuestion = arrayList;
                                fragmentTransaction.commit();
                            }
//15
                            else if (old_label.equals("Protecting the Public and Site Security")) {
                                PrtctngPblcSiteScrtyFragment fragment = new PrtctngPblcSiteScrtyFragment();
                                //SummaryFragment fragment = new SummaryFragment();
                                FragmentManager fragmentManager = getSupportFragmentManager();
                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                fragmentTransaction.replace(R.id.frm, fragment);
                                fragmentTransaction.addToBackStack("first");
                                //Utility.getAppcon().getSession().arrayListQuestion = arrayList;
                                fragmentTransaction.commit();
                            }
                            //16
                            else if (old_label.equals("Plant")) {
                                PlantFragment fragment = new PlantFragment();
                                //SummaryFragment fragment = new SummaryFragment();
                                FragmentManager fragmentManager = getSupportFragmentManager();
                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                fragmentTransaction.replace(R.id.frm, fragment);
                                fragmentTransaction.addToBackStack("first");
                                //Utility.getAppcon().getSession().arrayListQuestion = arrayList;
                                fragmentTransaction.commit();
                            }
                            //17
                            else if (old_label.equals("Materials")) {
                                MaterialsFragment fragment = new MaterialsFragment();
                                //SummaryFragment fragment = new SummaryFragment();
                                FragmentManager fragmentManager = getSupportFragmentManager();
                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                fragmentTransaction.replace(R.id.frm, fragment);
                                fragmentTransaction.addToBackStack("first");
                                //Utility.getAppcon().getSession().arrayListQuestion = arrayList;
                                fragmentTransaction.commit();
                            }
                            //18
                            else if (old_label.equals("Visual checks")) {
                                VisualChecksFragment fragment = new VisualChecksFragment();
                                //SummaryFragment fragment = new SummaryFragment();
                                FragmentManager fragmentManager = getSupportFragmentManager();
                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                fragmentTransaction.replace(R.id.frm, fragment);
                                fragmentTransaction.addToBackStack("first");
                                //Utility.getAppcon().getSession().arrayListQuestion = arrayList;
                                fragmentTransaction.commit();
                            } else if (old_label.equals("last_step_remaning")) {
                                SummaryFragment fragment = new SummaryFragment();
                                //SummaryFragment fragment = new SummaryFragment();
                                FragmentManager fragmentManager = getSupportFragmentManager();
                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                fragmentTransaction.replace(R.id.frm, fragment);
                                fragmentTransaction.addToBackStack("first");
                                //Utility.getAppcon().getSession().arrayListQuestion = arrayList;
                                fragmentTransaction.commit();
                            }

                        }
                    }
                });
    }


   /* public void getResultData()
    {
        questiondataList=new ArrayList<>();
       String last_data= Utility.getAppcon().getSession().data_value;
       Log.e("Last Stape",last_data);
        Observable<ResultModel> responseObservable = apiservice.get_allQuestion_data(last_data);
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
                .subscribe(new Observer<ResultModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("error", "" + e.getMessage());
                    }

                    @Override
                    public void onNext(ResultModel model) {
                        statusCode = model.getStatusCode();
                        Log.e("Response is ", String.valueOf(statusCode));
                        if (statusCode == 200) {

                            for (int i=1;i<model.getData().size();i++)
                            {
                                Log.e("i is ", String.valueOf(i));
                                Log.e("J size is", String.valueOf(model.getData().get(i).getQuestiondata().size()));
                                //Log.e("Question is 123", String.valueOf(model.getData().get(i).getQuestiondata()));

                                for (int j=0;j<model.getData().get(i).getQuestiondata().size();j++)
                                {
                                    Log.e("j is ", String.valueOf(j));
                                    Log.e("Question is ",""+model.getData().get(i).getQuestiondata().get(j).getAnsQues());

                                    questiondataList.get(j).setAns_label(""+model.getData().get(i).getQuestiondata().get(j).getAnsLabel());
                                    questiondataList.get(j).setAns_ques_value(""+model.getData().get(i).getQuestiondata().get(j).getAnsQuesValue());
                                    questiondataList.get(j).setAns_ques(""+model.getData().get(i).getQuestiondata().get(j).getAnsQues());
                                    questiondataList.get(j).setAns_ques_value_no(""+model.getData().get(i).getQuestiondata().get(j).getAnsQuesValueNo());
                                    questiondata=new Example.questiondata();
                                    questiondata.setAns_ques(model.getData().get(i).getQuestiondata().get(j).getAnsQues());
                                    questiondata.setAns_label(model.getData().get(i).getQuestiondata().get(j).getAnsLabel());
                                    questiondata.setAns_ques_value(model.getData().get(i).getQuestiondata().get(j).getAnsQuesValue());
                                    questiondata.setAns_ques_value_no(model.getData().get(i).getQuestiondata().get(j).getAnsQuesValueNo());
                                    questiondataList.add(questiondata);
                                }
                                try {
                                    if (i==2)
                                    {

                                        e = new Example();
                                        e.setAns_user_id(sessionManager.getKeyId());
                                        e.setAns_comment(model.getData().get(i).getQuestiondata().get(0).getAnsComment());
                                        e.setAns_needdate(model.getData().get(i).getQuestiondata().get(0).getAnsNeeddate());
                                        e.setLast_step(Utility.getAppcon().getSession().data_value);
                                        e.setQuestions_hs(questiondataList);

                                        List<Example> examples = new ArrayList<>();
                                        examples.add(e);
                                        Utility.getAppcon().getSession().questiondataList1 = new ArrayList<>();
                                        Utility.getAppcon().getSession().questiondataList1 = examples;
                                    }
                                    //2
                                    else if (i==2)
                                    {
                                        e = new Example();
                                        e.setAns_user_id(sessionManager.getKeyId());
                                        e.setAns_comment(""+model.getData().get(i).getQuestiondata().get(0).getAnsComment());
                                        e.setAns_needdate(""+model.getData().get(i).getQuestiondata().get(0).getAnsNeeddate());
                                        e.setLast_step(""+Utility.getAppcon().getSession().data_value);
                                        e.setQuestions_hs(questiondataList);

                                        List<Example> examples = new ArrayList<>();
                                        examples.add(e);
                                        Utility.getAppcon().getSession().questiondataList2 = new ArrayList<>();
                                        Utility.getAppcon().getSession().questiondataList2 = examples;
                                    }
                                    //3
                                    else if (i==3)
                                    {

                                        e = new Example();
                                        e.setAns_user_id(sessionManager.getKeyId());
                                        e.setAns_comment(""+model.getData().get(i).getQuestiondata().get(0).getAnsComment());
                                        e.setAns_needdate(""+model.getData().get(i).getQuestiondata().get(0).getAnsNeeddate());
                                        e.setLast_step(""+Utility.getAppcon().getSession().data_value);
                                        e.setQuestions_hs(questiondataList);

                                        List<Example> examples = new ArrayList<>();
                                        examples.add(e);
                                        Utility.getAppcon().getSession().questiondataList3 = new ArrayList<>();
                                        Utility.getAppcon().getSession().questiondataList2 = examples;

                                    }
                                    //4
                                    else if (i==4)
                                    {
                                        Log.e("i is","4");
                                        Log.e("questiondataList4", String.valueOf(questiondataList));

                                        e = new Example();
                                        e.setAns_user_id(sessionManager.getKeyId());
                                        e.setAns_comment(model.getData().get(i).getQuestiondata().get(0).getAnsComment());
                                        e.setAns_needdate(model.getData().get(i).getQuestiondata().get(0).getAnsNeeddate());
                                        e.setLast_step(Utility.getAppcon().getSession().data_value);
                                        e.setQuestions_hs(questiondataList);

                                        List<Example> examples = new ArrayList<>();
                                        examples.add(e);
                                        Utility.getAppcon().getSession().questiondataList4 = new ArrayList<>();
                                        Utility.getAppcon().getSession().questiondataList4 = examples;
                                    }
//5
                                    else if (i==5)
                                    {
                                        Log.e("i is","5");
                                        e = new Example();
                                        e.setAns_user_id(sessionManager.getKeyId());
                                        e.setAns_comment(model.getData().get(i).getQuestiondata().get(0).getAnsComment());
                                        e.setAns_needdate(model.getData().get(i).getQuestiondata().get(0).getAnsNeeddate());
                                        e.setLast_step(Utility.getAppcon().getSession().data_value);
                                        e.setQuestions_hs(questiondataList);

                                        List<Example> examples = new ArrayList<>();
                                        examples.add(e);
                                        Utility.getAppcon().getSession().questiondataList5 = new ArrayList<>();
                                        Utility.getAppcon().getSession().questiondataList5 = examples;
                                    }
                                    //6
                                    else if (i==6)
                                    {
                                        Log.e("i is","6");
                                        e = new Example();
                                        e.setAns_user_id(sessionManager.getKeyId());
                                        e.setAns_comment(model.getData().get(i).getQuestiondata().get(0).getAnsComment());
                                        e.setAns_needdate(model.getData().get(i).getQuestiondata().get(0).getAnsNeeddate());
                                        e.setLast_step(Utility.getAppcon().getSession().data_value);
                                        e.setQuestions_hs(questiondataList);

                                        List<Example> examples = new ArrayList<>();
                                        examples.add(e);
                                        Utility.getAppcon().getSession().questiondataList6 = new ArrayList<>();
                                        Utility.getAppcon().getSession().questiondataList6 = examples;
                                    }
                                    //7
                                    else if (i==7)
                                    {
                                        Log.e("i is","7");
                                        e = new Example();
                                        e.setAns_user_id(sessionManager.getKeyId());
                                        e.setAns_comment(model.getData().get(i).getQuestiondata().get(0).getAnsComment());
                                        e.setAns_needdate(model.getData().get(i).getQuestiondata().get(0).getAnsNeeddate());
                                        e.setLast_step(Utility.getAppcon().getSession().data_value);
                                        e.setQuestions_hs(questiondataList);

                                        List<Example> examples = new ArrayList<>();
                                        examples.add(e);
                                        Utility.getAppcon().getSession().questiondataList7 = new ArrayList<>();
                                        Utility.getAppcon().getSession().questiondataList7 = examples;

                                    }
                                    //8
                                    else if (i==8)
                                    {
                                        Log.e("i is","8");
                                        e = new Example();
                                        e.setAns_user_id(sessionManager.getKeyId());
                                        e.setAns_comment(model.getData().get(i).getQuestiondata().get(0).getAnsComment());
                                        e.setAns_needdate(model.getData().get(i).getQuestiondata().get(0).getAnsNeeddate());
                                        e.setLast_step(Utility.getAppcon().getSession().data_value);
                                        e.setQuestions_hs(questiondataList);

                                        List<Example> examples = new ArrayList<>();
                                        examples.add(e);
                                        Utility.getAppcon().getSession().questiondataList8 = new ArrayList<>();
                                        Utility.getAppcon().getSession().questiondataList8 = examples;

                                    }
                                    //9
                                    else if (i==9)
                                    {
                                        Log.e("i is","9");
                                        e = new Example();
                                        e.setAns_user_id(sessionManager.getKeyId());
                                        e.setAns_comment(model.getData().get(i).getQuestiondata().get(0).getAnsComment());
                                        e.setAns_needdate(model.getData().get(i).getQuestiondata().get(0).getAnsNeeddate());
                                        e.setLast_step(Utility.getAppcon().getSession().data_value);
                                        e.setQuestions_hs(questiondataList);

                                        List<Example> examples = new ArrayList<>();
                                        examples.add(e);
                                        Utility.getAppcon().getSession().questiondataList9 = new ArrayList<>();
                                        Utility.getAppcon().getSession().questiondataList9 = examples;
                                    }
                                    //10
                                    else if (i==10)
                                    {
                                        Log.e("i is","10");
                                        e = new Example();
                                        e.setAns_user_id(sessionManager.getKeyId());
                                        e.setAns_comment(model.getData().get(i).getQuestiondata().get(0).getAnsComment());
                                        e.setAns_needdate(model.getData().get(i).getQuestiondata().get(0).getAnsNeeddate());
                                        e.setLast_step(Utility.getAppcon().getSession().data_value);
                                        e.setQuestions_hs(questiondataList);

                                        List<Example> examples = new ArrayList<>();
                                        examples.add(e);
                                        Utility.getAppcon().getSession().questiondataList10 = new ArrayList<>();
                                        Utility.getAppcon().getSession().questiondataList10 = examples;
                                    }
                                    //11
                                    else if (i==11)
                                    {
                                        Log.e("i is","11");
                                        e = new Example();
                                        e.setAns_user_id(sessionManager.getKeyId());
                                        e.setAns_comment(model.getData().get(i).getQuestiondata().get(0).getAnsComment());
                                        e.setAns_needdate(model.getData().get(i).getQuestiondata().get(0).getAnsNeeddate());
                                        e.setLast_step(Utility.getAppcon().getSession().data_value);
                                        e.setQuestions_hs(questiondataList);

                                        List<Example> examples = new ArrayList<>();
                                        examples.add(e);
                                        Utility.getAppcon().getSession().questiondataList11 = new ArrayList<>();
                                        Utility.getAppcon().getSession().questiondataList11 = examples;
                                    }
                                    //12
                                    else if (i==12)
                                    {
                                        Log.e("i is","12");
                                        e = new Example();
                                        e.setAns_user_id(sessionManager.getKeyId());
                                        e.setAns_comment(model.getData().get(i).getQuestiondata().get(0).getAnsComment());
                                        e.setAns_needdate(model.getData().get(i).getQuestiondata().get(0).getAnsNeeddate());
                                        e.setLast_step(Utility.getAppcon().getSession().data_value);
                                        e.setQuestions_hs(questiondataList);

                                        List<Example> examples = new ArrayList<>();
                                        examples.add(e);
                                        Utility.getAppcon().getSession().questiondataList12 = new ArrayList<>();
                                        Utility.getAppcon().getSession().questiondataList12 = examples;
                                    }
                                    //13
                                    else if (i==13)
                                    {
                                        Log.e("i is","13");
                                        e = new Example();
                                        e.setAns_user_id(sessionManager.getKeyId());
                                        e.setAns_comment(model.getData().get(i).getQuestiondata().get(0).getAnsComment());
                                        e.setAns_needdate(model.getData().get(i).getQuestiondata().get(0).getAnsNeeddate());
                                        e.setLast_step(Utility.getAppcon().getSession().data_value);
                                        e.setQuestions_hs(questiondataList);

                                        List<Example> examples = new ArrayList<>();
                                        examples.add(e);
                                        Utility.getAppcon().getSession().questiondataList13 = new ArrayList<>();
                                        Utility.getAppcon().getSession().questiondataList13 = examples;
                                    }
                                    //14
                                    else if (i==14)
                                    {
                                        Log.e("i is","14");
                                        e = new Example();
                                        e.setAns_user_id(sessionManager.getKeyId());
                                        e.setAns_comment(model.getData().get(i).getQuestiondata().get(0).getAnsComment());
                                        e.setAns_needdate(model.getData().get(i).getQuestiondata().get(0).getAnsNeeddate());
                                        e.setLast_step(Utility.getAppcon().getSession().data_value);
                                        e.setQuestions_hs(questiondataList);

                                        List<Example> examples = new ArrayList<>();
                                        examples.add(e);
                                        Utility.getAppcon().getSession().questiondataList14 = new ArrayList<>();
                                        Utility.getAppcon().getSession().questiondataList14 = examples;

                                    }
//15
                                    else if (i==15)
                                    {
                                        Log.e("i is","15");
                                        e = new Example();
                                        e.setAns_user_id(sessionManager.getKeyId());
                                        e.setAns_comment(model.getData().get(i).getQuestiondata().get(0).getAnsComment());
                                        e.setAns_needdate(model.getData().get(i).getQuestiondata().get(0).getAnsNeeddate());
                                        e.setLast_step(Utility.getAppcon().getSession().data_value);
                                        e.setQuestions_hs(questiondataList);

                                        List<Example> examples = new ArrayList<>();
                                        examples.add(e);
                                        Utility.getAppcon().getSession().questiondataList15 = new ArrayList<>();
                                        Utility.getAppcon().getSession().questiondataList15 = examples;
                                    }
                                    //16
                                    else if (i==16)
                                    {
                                        Log.e("i is","16");
                                        e = new Example();
                                        e.setAns_user_id(sessionManager.getKeyId());
                                        e.setAns_comment(model.getData().get(i).getQuestiondata().get(0).getAnsComment());
                                        e.setAns_needdate(model.getData().get(i).getQuestiondata().get(0).getAnsNeeddate());
                                        e.setLast_step(Utility.getAppcon().getSession().data_value);
                                        e.setQuestions_hs(questiondataList);

                                        List<Example> examples = new ArrayList<>();
                                        examples.add(e);
                                        Utility.getAppcon().getSession().questiondataList16 = new ArrayList<>();
                                        Utility.getAppcon().getSession().questiondataList16 = examples;
                                    }
                                    //17
                                    else if (i==17)
                                    {
                                        Log.e("i is","17");
                                        e = new Example();
                                        e.setAns_user_id(sessionManager.getKeyId());
                                        e.setAns_comment(model.getData().get(i).getQuestiondata().get(0).getAnsComment());
                                        e.setAns_needdate(model.getData().get(i).getQuestiondata().get(0).getAnsNeeddate());
                                        e.setLast_step(Utility.getAppcon().getSession().data_value);
                                        e.setQuestions_hs(questiondataList);

                                        List<Example> examples = new ArrayList<>();
                                        examples.add(e);
                                        Utility.getAppcon().getSession().questiondataList17 = new ArrayList<>();
                                        Utility.getAppcon().getSession().questiondataList17 = examples;
                                    }
                                    //18
                                    else if (i==18)
                                    {
                                        Log.e("i is","18");
                                        e = new Example();
                                        e.setAns_user_id(sessionManager.getKeyId());
                                        e.setAns_comment(model.getData().get(i).getQuestiondata().get(0).getAnsComment());
                                        e.setAns_needdate(model.getData().get(i).getQuestiondata().get(0).getAnsNeeddate());
                                        e.setLast_step(Utility.getAppcon().getSession().data_value);
                                        e.setQuestions_hs(questiondataList);

                                        List<Example> examples = new ArrayList<>();
                                        examples.add(e);
                                        Utility.getAppcon().getSession().questiondataList18= new ArrayList<>();
                                        Utility.getAppcon().getSession().questiondataList18 = examples;
                                    }
                                }catch (Exception e)
                                {

                                }


                                }
                                //questiondataList.clear();


                            }






                    }
                });
    }*/
}