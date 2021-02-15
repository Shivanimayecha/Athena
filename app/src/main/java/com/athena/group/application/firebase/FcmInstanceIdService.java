package com.athena.group.application.firebase;

import android.util.Log;

import com.athena.group.application.SessionManager;
import com.athena.group.application.Utility;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

public class FcmInstanceIdService extends FirebaseInstanceIdService {

    SessionManager sessionManager = null;

    @Override
    public void onTokenRefresh() {
        String recent_token = FirebaseInstanceId.getInstance().getToken();
        sessionManager = new SessionManager(this);
        sessionManager.setFcmId(recent_token);
        Log.e("fcm_id", recent_token);
        Utility.displayToast(this, "FCM_KEY--" + recent_token);
    }
}
