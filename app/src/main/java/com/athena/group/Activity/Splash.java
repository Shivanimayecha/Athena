package com.athena.group.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import com.athena.group.R;
import com.athena.group.application.SessionManager;

public class Splash extends Activity {

    Handler handler;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
//                String strDeviceId = Settings.Secure.getString(getApplicationContext().getContentResolver(),
//                        Settings.Secure.ANDROID_ID);
                // Toast.makeText(Splash.this, "android_id= " + strDeviceId, Toast.LENGTH_LONG).show();

                Intent intent = new Intent(Splash.this, LoginSignup.class);
                startActivity(intent);
                finish();
            }
        }, 3000);

    }
}
