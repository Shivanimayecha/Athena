package com.athena.group.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.athena.group.MainActivity;
import com.athena.group.R;
import com.athena.group.Truck__Driver_Panel.TruckDriverActivity;
import com.athena.group.application.SessionManager;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginSignup extends Activity {

    /*@BindView(R.id.Signup)
    Button Signup;*/
    @BindView(R.id.login)
    Button login;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login_signup);
        ButterKnife.bind(this);
        sessionManager = new SessionManager(LoginSignup.this);


        if (sessionManager.isLoggedIn()) {

            if (sessionManager.getKeyRoll().equals("Supervisor")) {
                Intent intent = new Intent(LoginSignup.this, MainActivity.class);
                startActivity(intent);
                finish();
            } else {
                Intent intent = new Intent(LoginSignup.this, TruckDriverActivity.class);
                startActivity(intent);
                finish();
            }
        }

        initview();

    }

    private void initview() {

/*
        Signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(LoginSignup.this, com.group.group.Activity.Signup.class);
                startActivity(intent);
            }
        });
*/
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(LoginSignup.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }


}
