package com.athena.group.HSQuesInsctn;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.WindowManager;

import com.athena.group.Fragment.HsQuestion.QuestionStartFragment;
import com.athena.group.R;
import com.athena.group.application.Utility;

import butterknife.ButterKnife;

public class HSQuestionActivity extends AppCompatActivity {


    /*@BindView(R.id.toolbar_Title)
    TextView toolbar_Title;
    @BindView(R.id.back_icon)
    ImageView back_icon;*/

    /*@BindView(R.id.recyclerview)
    RecyclerView recyclerview;*/
    //HseqInspctnFragment fragment;
    QuestionStartFragment fragment;
    FragmentTransaction transaction;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_hsquestion);
        ButterKnife.bind(this);

        //toolbar_Title.setText("HSQuestion Form");
        //back_icon.setOnClickListener(view -> finish());

        //callApi();
        Utility.getAppcon().getSession().screen_name = "new_QueStart";


        //fragment = new HseqInspctnFragment();
        fragment = new QuestionStartFragment();
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.frm, fragment);
        transaction.addToBackStack(null);
        transaction.commit();

    }
}
