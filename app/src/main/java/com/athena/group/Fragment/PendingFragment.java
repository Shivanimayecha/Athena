package com.athena.group.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Observer;
import rx.schedulers.Schedulers;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.athena.group.API.ApiInterface;
import com.athena.group.API.ApiServiceCreator;
import com.athena.group.HSQuestionActivity1;
import com.athena.group.Model.PastHsModel;
import com.athena.group.Model.QuestionStartModel;
import com.athena.group.R;
import com.athena.group.adapter.PastHSQueAdapter1;
import com.athena.group.application.SessionManager;
import com.athena.group.application.Utility;
import com.wang.avi.AVLoadingIndicatorView;

import java.net.SocketTimeoutException;
import java.util.List;

public class PendingFragment extends Fragment {
    SessionManager sessionManager;
    ApiInterface apiservice;
    @BindView(R.id.rv_list)
    RecyclerView rv_list;
    RecyclerView.LayoutManager layoutManager;
    int statusCode;
    PastHSQueAdapter1 adapter;
    QuestionStartModel qmodel;
    @BindView(R.id.avi)
    AVLoadingIndicatorView avi;

    public PendingFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view1 = inflater.inflate(R.layout.fragment_pending, container, false);
        ButterKnife.bind(this, view1);
        sessionManager = new SessionManager(getContext());
        apiservice = ApiServiceCreator.createService("latest");
        initViews();
        return view1;
    }

    private void initViews() {
        rv_list.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        rv_list.setLayoutManager(layoutManager);

        try {
            avi.show();

            Observable<PastHsModel> responseObservable = apiservice.get_hsQuestionPending(
                    sessionManager.getKeyId()
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
                    .subscribe(new Observer<PastHsModel>() {
                        @Override
                        public void onCompleted() {
                            avi.hide();
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.e("error", "" + e.getMessage());
                        }

                        @Override
                        public void onNext(PastHsModel model) {
                            //avi.hide();

                            //if (model.getData().size() > 0) {
                            statusCode = model.getStatusCode();
                            if (statusCode == 200) {
                                rv_list.setVisibility(View.VISIBLE);
                                List<PastHsModel.Data> model_data = model.getData();
                                Utility.displayToast(getContext(), model.getMessage());
                                adapter = new PastHSQueAdapter1(getActivity(), model.getData(), new PastHSQueAdapter1.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(String label) {
                                        Intent intent = new Intent(getContext(), HSQuestionActivity1.class);
                                        intent.putExtra("label", model_data.get(Integer.parseInt(label)).getAnsLabel());
                                        startActivity(intent);


                                       /* qmo  del = n+
                                       ew QuestionStartModel();
                                        qmodel.setSite(model_data.get(Integer.parseInt(label)).getSiteName());
                                        qmodel.setSupervisor(model_data.get(Integer.parseInt(label)).getAnsNameofsup());
                                        qmodel.setContractor(model_data.get(Integer.parseInt(label)).getAnsNameContactManager());
                                        qmodel.setInspectedby(model_data.get(Integer.parseInt(label)).getAnsInspectedBy());
                                        qmodel.setReportref(model_data.get(Integer.parseInt(label)).getAnsReportReference());

                                        //sessionManager.setQueStartDetails(new Gson().toJson(qmodel));
                                        List<QuestionStartModel> qslist = new ArrayList<>();
                                        qslist.add(qmodel);
                                        Utility.getAppcon().getSession().questionstart = new ArrayList<>();
                                        Utility.getAppcon().getSession().questionstart = qslist;

    */

                                        Utility.getAppcon().getSession().data_value = model_data.get(Integer.parseInt(label)).getLastStep();

                                       /* InspctnandReprtngFragment fragment = new InspctnandReprtngFragment();
                                        //SummaryFragment fragment = new SummaryFragment();
                                        FragmentManager fragmentManager =getActivity().getSupportFragmentManager();
                                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                        fragmentTransaction.replace(R.id.frm, fragment);
                                        fragmentTransaction.addToBackStack("first");
                                        //Utility.getAppcon().getSession().arrayListQuestion = arrayList;
                                        fragmentTransaction.commit();*/
                                    }
                                });
                                rv_list.setAdapter(adapter);
                            } else if (statusCode == 404) {
                                rv_list.setVisibility(View.GONE);
                                Utility.displayToast(getContext(), model.getMessage());
                            }
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        initViews();
    }
}