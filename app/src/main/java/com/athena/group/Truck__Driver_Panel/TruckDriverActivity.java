package com.athena.group.Truck__Driver_Panel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.athena.group.Activity.LoginActivity;
import com.athena.group.Activity.NotificationActivity;
import com.athena.group.Activity.Profile;
import com.athena.group.Activity.SignOrderActivity;
import com.athena.group.R;
import com.athena.group.application.SessionManager;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TruckDriverActivity extends AppCompatActivity {


    @BindView(R.id.profile)
    LinearLayout profile;
    /* @BindView(R.id.ll_timelog)
     LinearLayout ll_timelog;*/
    @BindView(R.id.ll_assignedordr)
    LinearLayout ll_assignedordr;
    /*@BindView(R.id.ll_todayordr)
    LinearLayout ll_todayordr;*/
    @BindView(R.id.ll_todaytrip)
    LinearLayout ll_todaytrip;
    @BindView(R.id.ll_cmpltdordr)
    LinearLayout ll_cmpltdordr;

    @BindView(R.id.ll_allassignordr)
    LinearLayout ll_allassignordr;
    /*
       @BindView(R.id.ll_ordrstausmngmnt)
       LinearLayout ll_ordrstausmngmnt;*/
    @BindView(R.id.ll_logout)
    LinearLayout ll_logout;
    @BindView(R.id.txt_user)
    TextView txt_user;
    @BindView(R.id.img_notification)
    ImageView img_notification;
    SessionManager sessionManager;
    private static long back_pressed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_truck_driver);
        ButterKnife.bind(this);
        sessionManager = new SessionManager(this);

        txt_user.setText(sessionManager.getKeyName() + " " + sessionManager.getKeyLname());

        img_notification.setOnClickListener(view -> {
            Intent intent = new Intent(TruckDriverActivity.this, NotificationActivity.class);
            startActivity(intent);
        });
        profile.setOnClickListener(view -> {
            Intent intent = new Intent(TruckDriverActivity.this, Profile.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });
        ll_todaytrip.setOnClickListener(view -> {
            Intent intent = new Intent(TruckDriverActivity.this, TodayTripActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });

        ll_assignedordr.setOnClickListener(view -> {
            /*if (sessionManager.getKeyTruckid().equals("")) {
                Utility.displayToast(this, "Please Select Truck From Vehicle Defect Report");
            } else {*/
            Intent intent = new Intent(TruckDriverActivity.this, AssignOrderActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            //}
        });


        ll_allassignordr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TruckDriverActivity.this, SignOrderActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        /*ll_todayordr.setOnClickListener(view -> {
            if (sessionManager.getKeyTruckid().equals("")) {
                Utility.displayToast(this, "Please Select Truck From Vehicle Defect Report");
            } else {
                Intent intent = new Intent(TruckDriverActivity.this, TodaysOrderActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });*/

        ll_cmpltdordr.setOnClickListener(view -> {
            Intent intent = new Intent(TruckDriverActivity.this, CompletedOrderActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });

        ll_logout.setOnClickListener(view -> {
            sessionManager.logoutUser();
            Intent i = new Intent(getApplicationContext(), LoginActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
            finish();
        });
    }

    @Override
    public void onBackPressed() {
        if (back_pressed + 2000 > System.currentTimeMillis()) super.onBackPressed();
        else
            Toast.makeText(getBaseContext(), "Press click Back again to exit !", Toast.LENGTH_SHORT).show();
        back_pressed = System.currentTimeMillis();
    }

}
