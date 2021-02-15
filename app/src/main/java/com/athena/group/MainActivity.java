package com.athena.group;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.athena.group.Activity.JobFrmLstActivity;
import com.athena.group.Activity.JobReviewActivity;
import com.athena.group.Activity.NotificationActivity;
import com.athena.group.Activity.TimeLogNew;
import com.athena.group.HSQuesInsctn.HSQuestionActivity;
import com.athena.group.LogHistory.LogHistory;
import com.athena.group.Activity.LoginActivity;
import com.athena.group.HSQuesInsctn.PastHSQuesActivity;
import com.athena.group.MobilePlant.MblFrmLstActivity;
import com.athena.group.MobilePlant.MblPlntChekActivity;
import com.athena.group.application.Utility;
import com.athena.group.orderHistory.OrderHistory;
import com.athena.group.PlaceOrder.PlaceOrder;
import com.athena.group.Activity.Profile;
import com.athena.group.report.Report;
import com.athena.group.application.SessionManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends Activity implements View.OnClickListener {

    SessionManager sessionManager;
    private static long back_pressed;
    @BindView(R.id.txt_sprvsrname)
    TextView txt_sprvsrname;
    @BindView(R.id.ll_profile)
    LinearLayout ll_profile;
    @BindView(R.id.ll_timelog)
    LinearLayout ll_timelog;
    @BindView(R.id.ll_placeordr)
    LinearLayout ll_placeordr;
    @BindView(R.id.ll_loghistory)
    LinearLayout ll_loghistory;
    @BindView(R.id.ll_ordrhistory)
    LinearLayout ll_ordrhistory;
    @BindView(R.id.ll_report)
    LinearLayout ll_report;
    @BindView(R.id.ll_queans)
    LinearLayout ll_queans;
    /*@BindView(R.id.ll_hsinspectn)
    LinearLayout ll_hsinspectn;*/
    @BindView(R.id.ll_past_queans)
    LinearLayout ll_past_queans;
    /*@BindView(R.id.ll_past_hsinspectn)
    LinearLayout ll_past_hsinspectn;*/
    @BindView(R.id.ll_mobileplant)
    LinearLayout ll_mobileplant;
    @BindView(R.id.ll_jobrvw)
    LinearLayout ll_jobrvw;
    @BindView(R.id.ll_mblfrmlist)
    LinearLayout ll_mblfrmlist;
    @BindView(R.id.ll_jobfrmlist)
    LinearLayout ll_jobfrmlist;
    @BindView(R.id.ll_logout)
    LinearLayout ll_logout;
    @BindView(R.id.img_notification)
    ImageView img_notification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        sessionManager = new SessionManager(MainActivity.this);
        Log.e("User is", sessionManager.getKeyId());
        checkAndRequestPermissions();
        txt_sprvsrname.setText(sessionManager.getKeyName() + " " + sessionManager.getKeyLname());
        ll_profile.setOnClickListener(this);
        ll_timelog.setOnClickListener(this);
        ll_placeordr.setOnClickListener(this);
        ll_loghistory.setOnClickListener(this);
        ll_ordrhistory.setOnClickListener(this);
        ll_report.setOnClickListener(this);
        ll_queans.setOnClickListener(this);
//        ll_hsinspectn.setOnClickListener(this);
//        ll_past_hsinspectn.setOnClickListener(this);
        ll_past_queans.setOnClickListener(this);
        ll_mobileplant.setOnClickListener(this);
        ll_jobrvw.setOnClickListener(this);
        ll_mblfrmlist.setOnClickListener(this);
        ll_jobfrmlist.setOnClickListener(this);
        ll_logout.setOnClickListener(this);
        img_notification.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {

        Intent intent;

        switch (view.getId()) {

            case R.id.img_notification:
                intent = new Intent(this, NotificationActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_profile:
                intent = new Intent(this, Profile.class);
                startActivity(intent);
                break;
            case R.id.ll_timelog:
                intent = new Intent(this, TimeLogNew.class);
                startActivity(intent);
                break;
            case R.id.ll_placeordr:
                intent = new Intent(this, PlaceOrder.class);
                startActivity(intent);
                break;
            case R.id.ll_loghistory:
                intent = new Intent(this, LogHistory.class);
                startActivity(intent);
                break;
            case R.id.ll_ordrhistory:
                intent = new Intent(this, OrderHistory.class);
                startActivity(intent);
                break;
            case R.id.ll_report:
                intent = new Intent(this, Report.class);
                startActivity(intent);
                break;
            case R.id.ll_queans:
                intent = new Intent(this, HSQuestionActivity.class);
                startActivity(intent);
                cleradata();
                break;
//            case R.id.ll_hsinspectn:
//                intent = new Intent(this, HSInspctnActivity.class);
//                startActivity(intent);
//                break;
            case R.id.ll_past_queans:
                intent = new Intent(this, PastHSQuesActivity.class);
                startActivity(intent);
                break;
//            case R.id.ll_past_hsinspectn:
//                intent = new Intent(this, PastHSInspcActivity.class);
//                startActivity(intent);
//                break;
            case R.id.ll_mobileplant:
                intent = new Intent(this, MblPlntChekActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_jobrvw:
                intent = new Intent(this, JobReviewActivity.class);
                Utility.getAppcon().getSession().jobreview_operation = "insert";
                startActivity(intent);
                break;
            case R.id.ll_mblfrmlist:
                intent = new Intent(this, MblFrmLstActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_jobfrmlist:
                intent = new Intent(this, JobFrmLstActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_logout:
                sessionManager.logoutUser();
                intent = new Intent(this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                break;

            default:
        }

    }

    private void cleradata() {
        Utility.getAppcon().getSession().data_value = "";
        Utility.getAppcon().getSession().questionstart.clear();
        Utility.getAppcon().getSession().questiondataList1.clear();
        Utility.getAppcon().getSession().questiondataList2.clear();
        Utility.getAppcon().getSession().questiondataList3.clear();
        Utility.getAppcon().getSession().questiondataList4.clear();
        Utility.getAppcon().getSession().questiondataList5.clear();
        Utility.getAppcon().getSession().questiondataList6.clear();
        Utility.getAppcon().getSession().questiondataList7.clear();
        Utility.getAppcon().getSession().questiondataList8.clear();
        Utility.getAppcon().getSession().questiondataList9.clear();
        Utility.getAppcon().getSession().questiondataList10.clear();
        Utility.getAppcon().getSession().questiondataList11.clear();
        Utility.getAppcon().getSession().questiondataList12.clear();
        Utility.getAppcon().getSession().questiondataList13.clear();
        Utility.getAppcon().getSession().questiondataList14.clear();
        Utility.getAppcon().getSession().questiondataList15.clear();
        Utility.getAppcon().getSession().questiondataList16.clear();
        Utility.getAppcon().getSession().questiondataList17.clear();
        Utility.getAppcon().getSession().questiondataList18.clear();
    }

    private boolean checkAndRequestPermissions() {

        int camera = ContextCompat.checkSelfPermission(this, "android.permission.CAMERA");
        int writeStorage = ContextCompat.checkSelfPermission(this, "android.permission.WRITE_EXTERNAL_STORAGE");
        int readStorage = ContextCompat.checkSelfPermission(this, "android.permission.READ_EXTERNAL_STORAGE");

        List<String> listPermissionNeeded = new ArrayList<>();

        if (writeStorage != 0) {
            listPermissionNeeded.add("android.permission.WRITE_EXTERNAL_STORAGE");
        }
        if (readStorage != 0) {
            listPermissionNeeded.add("android.permission.READ_EXTERNAL_STORAGE");
        }
        if (camera != 0) {

            listPermissionNeeded.add("android.permission.CAMERA");
        }
        if (listPermissionNeeded.isEmpty()) {
            return true;
        }
        ActivityCompat.requestPermissions(this, listPermissionNeeded.toArray(new String[listPermissionNeeded.size()]), 100);
        return false;
    }

    @Override
    public void onBackPressed() {
        if (back_pressed + 2000 > System.currentTimeMillis()) super.onBackPressed();
        else
            Toast.makeText(getBaseContext(), "Press click Back again to exit !", Toast.LENGTH_SHORT).show();
        back_pressed = System.currentTimeMillis();
    }

}
