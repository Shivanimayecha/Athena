package com.athena.group.report;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.athena.group.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Report extends Activity {

    @BindView(R.id.back_icon)
    ImageView back_icon;
    @BindView(R.id.toolbar_Title)
    TextView toolbar_Title;
    @BindView(R.id.ll_contractor)
    LinearLayout ll_contractor;
    @BindView(R.id.ll_site)
    LinearLayout ll_site;
   /* @BindView(R.id.ll_payout)
    LinearLayout ll_payout;*/
    /*@BindView(R.id.ll_sitemanual)
    LinearLayout ll_sitemanual;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_report);
        ButterKnife.bind(this);

        toolbar_Title.setText(getString(R.string.reports));

        initViews();
    }

    private void initViews() {

        back_icon.setOnClickListener(view -> finish());

        ll_contractor.setOnClickListener(view -> {
            startActivity(new Intent(Report.this, SubContractorReport.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        });
        ll_site.setOnClickListener(view -> {
            startActivity(new Intent(Report.this, SiteReport.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        });
       /* ll_payout.setOnClickListener(view -> {
            startActivity(new Intent(Report.this, Payout.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        });*/

    }
}
