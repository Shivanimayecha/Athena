package com.athena.group.Fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.athena.group.API.ApiInterface;
import com.athena.group.API.ApiServiceCreator;
import com.athena.group.Model.CommanModel;
import com.athena.group.Model.HSQuestionModel;
import com.athena.group.R;
import com.athena.group.adapter.QuestionAdapter;
import com.athena.group.application.SessionManager;
import com.athena.group.application.Utility;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class CompetenceTraningFragment extends Fragment {


    private SessionManager sessionManager;
    private ApiInterface apiservice;
    private int statusCode;
    private QuestionAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    /* @BindView(R.id.avi)
     AVLoadingIndicatorView avi;*/
    @BindView(R.id.recyerview)
    RecyclerView recyclerview;
    LinearLayout btn_next;
    @BindView(R.id.avi)
    AVLoadingIndicatorView avi;
    @BindView(R.id.btn_previous)
    LinearLayout btn_previous;

    ArrayList<HSQuestionModel.Data> arrayList;
    List<CommanModel> commanModelslist;


    public CompetenceTraningFragment() {
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

        btn_next.setOnClickListener(view -> {

            WelfareFragment fragment = new WelfareFragment();
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frm, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();

        });
        commanModelslist = new ArrayList<>();
        for (int i = 0; i < arrayList.get(0).getCompetenceTraining().size(); i++) {
            CommanModel commanModel = new CommanModel();
            commanModel.setHsId(arrayList.get(0).getCompetenceTraining().get(i).getHsId());
            commanModel.setHsLabel(arrayList.get(0).getCompetenceTraining().get(i).getHsLabel());
            commanModel.setHsQues(arrayList.get(0).getCompetenceTraining().get(i).getHsQues());
            commanModelslist.add(commanModel);
        }

        recyclerview.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerview.setLayoutManager(layoutManager);

        adapter = new QuestionAdapter(getActivity(), commanModelslist, "competencetraning");
        recyclerview.setAdapter(adapter);
        //callApi();
        return view1;
    }

}
