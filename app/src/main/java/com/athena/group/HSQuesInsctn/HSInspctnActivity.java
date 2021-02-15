package com.athena.group.HSQuesInsctn;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.WindowManager;

import com.athena.group.Fragment.HsInspection.InspectnstartFragment;
import com.athena.group.R;

public class HSInspctnActivity extends AppCompatActivity {

    InspectnstartFragment fragment;
    FragmentTransaction transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_hsinspctn);

        fragment = new InspectnstartFragment();
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.frm, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
