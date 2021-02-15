package com.athena.group.Fragment;

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
import com.athena.group.Model.PastHsModel;
import com.athena.group.R;
import com.athena.group.adapter.PastHSQueAdapter;
import com.athena.group.application.SessionManager;
import com.athena.group.application.Utility;
import com.wang.avi.AVLoadingIndicatorView;

import java.net.SocketTimeoutException;

public class CompleteFragment extends Fragment {
    SessionManager sessionManager;
    ApiInterface apiservice;
    @BindView(R.id.rv_list)
    RecyclerView rv_list;
    RecyclerView.LayoutManager layoutManager;
    int statusCode;
    PastHSQueAdapter adapter;
    @BindView(R.id.avi)
    AVLoadingIndicatorView avi;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view1 = inflater.inflate(R.layout.fragment_complete, container, false);
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
            Observable<PastHsModel> responseObservable = apiservice.get_hsQuestionReport(
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
                                Utility.displayToast(getContext(), model.getMessage());
                                adapter = new PastHSQueAdapter(getActivity(), model.getData());
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


}