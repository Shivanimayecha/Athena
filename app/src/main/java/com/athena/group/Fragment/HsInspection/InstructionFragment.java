package com.athena.group.Fragment.HsInspection;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.athena.group.API.ApiInterface;
import com.athena.group.API.ApiServiceCreator;
import com.athena.group.R;
import com.athena.group.application.SessionManager;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class InstructionFragment extends Fragment {

    @BindView(R.id.toolbar_Title)
    TextView toolbar_Title;
    @BindView(R.id.back_icon)
    ImageView back_icon;
    @BindView(R.id.btn_next)
    LinearLayout btn_next;

    @BindView(R.id.btn_previous)
    LinearLayout btn_previous;

    private SessionManager sessionManager;
    private ApiInterface apiservice;
    private int statusCode;


    public InstructionFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view1 = inflater.inflate(R.layout.fragment_instructn, container, false);
        ButterKnife.bind(this, view1);
        sessionManager = new SessionManager(getActivity());
        apiservice = ApiServiceCreator.createService("latest");
        toolbar_Title.setText("Instruction");

        back_icon.setOnClickListener(view -> {
            getActivity().onBackPressed();
        });

        btn_previous.setOnClickListener(view -> getActivity().onBackPressed());
        btn_next.setOnClickListener(view -> {
            HSInspectnReporFragment fragment = new HSInspectnReporFragment();
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.frm, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        });
        return view1;
    }

}


