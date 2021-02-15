package com.athena.group.LogHistory;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.athena.group.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LogHistory extends Activity implements View.OnClickListener {

    @BindView(R.id.ll_subcontractor)
    LinearLayout ll_subcontractor;
    @BindView(R.id.ll_site)
    LinearLayout ll_site;
    @BindView(R.id.ll_time)
    LinearLayout ll_time;
    @BindView(R.id.back_icon)
    ImageView back_icon;
    @BindView(R.id.toolbar_Title)
    TextView toolbar_Title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_log_history);
        ButterKnife.bind(this);
        toolbar_Title.setText(getString(R.string.loghistory));
        back_icon.setOnClickListener(this);
        ll_subcontractor.setOnClickListener(this);
        ll_site.setOnClickListener(this);
        ll_time.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {

        Intent intent;
        switch (view.getId()) {
            case R.id.back_icon:
                finish();
                break;
            case R.id.ll_subcontractor:
                intent = new Intent(getApplicationContext(), SubContractorLogHistory.class);
                startActivity(intent);
                break;
            case R.id.ll_site:
                intent = new Intent(getApplicationContext(), SiteLogHistory.class);
                startActivity(intent);
                break;
            case R.id.ll_time:
                intent = new Intent(getApplicationContext(), DateLogHistory.class);
                startActivity(intent);
                break;
        }
    }
}
