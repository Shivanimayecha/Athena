package com.athena.group.application;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.athena.group.Activity.LoginActivity;
import com.athena.group.Model.Example;
import com.athena.group.Model.HSInspectionModel;
import com.athena.group.Model.HSQuestionModel;
import com.athena.group.Model.OrderDetailsModel;
import com.athena.group.Model.OrderDetailsTruckModel;
import com.athena.group.Model.QuestionStartModel;
import com.athena.group.Model.labourmodel;
import com.athena.group.Model.planetmodel;

import java.util.ArrayList;
import java.util.List;

public class SessionManager {


    private SharedPreferences pref, pref2;
    private Editor editor, editor2;
    private static final String PREF_NAME = "skzlogin";
    private static final String IS_LOGIN = "IsLoggedIn";
    private static final String IS_NEW = "IsNew";
    private static String KEY_EMAIL = "id";
    private static String KEY_ID = "user_id";
    private static String KEY_NAME = "user_name";
    private static String KEY_LNAME = "user_lname";
    private static String KEY_TRUCKNO = "truck_no";
    private static String KEY_TRUCKID = "truck_id";
    private static String CART_COUNT_ID = "cart_count_id";
    private static String KEY_TOKEN = "token";
    private static String KEY_ROLL = "roll";
    private static String STATUS = "status";
    private static String KEY_PASSWORD = "password";
    private static String TOTAL_POINT = "total_point";
    private static String KEY_USER_DETAILS = "userdetails";
    private static String KEY_IMAGE = "image_list";
    private static String LOGIN_TYPE = "login_type";
    private static String FCM_ID = "fcm_id";
    private static final String PREF_DEVICE = "device";

    private static Boolean KEY_STATUS;

    public ArrayList<OrderDetailsModel.Data> arrayListOrderResponse = new ArrayList<>();
    public ArrayList<OrderDetailsTruckModel.Data> arrayListTruckOrderResponse = new ArrayList<>();
    public ArrayList<HSQuestionModel.Data> arrayListQuestion = new ArrayList<>();
    public ArrayList<HSQuestionModel.Data> arrayListHSInsctnQuestion = new ArrayList<>();
    public List<labourmodel> filled_labourlist = new ArrayList<>();
    public List<planetmodel> filled_plantlist = new ArrayList<>();

    public List<QuestionStartModel> questionstart = new ArrayList<>();
    public List<Example> questiondataList1 = new ArrayList<>();
    public List<Example> questiondataList2 = new ArrayList<>();
    public List<Example> questiondataList3 = new ArrayList<>();
    public List<Example> questiondataList4 = new ArrayList<>();
    public List<Example> questiondataList5 = new ArrayList<>();
    public List<Example> questiondataList6 = new ArrayList<>();
    public List<Example> questiondataList7 = new ArrayList<>();
    public List<Example> questiondataList8 = new ArrayList<>();
    public List<Example> questiondataList9 = new ArrayList<>();
    public List<Example> questiondataList10 = new ArrayList<>();
    public List<Example> questiondataList11 = new ArrayList<>();
    public List<Example> questiondataList12 = new ArrayList<>();
    public List<Example> questiondataList13 = new ArrayList<>();
    public List<Example> questiondataList14 = new ArrayList<>();
    public List<Example> questiondataList15 = new ArrayList<>();
    public List<Example> questiondataList16 = new ArrayList<>();
    public List<Example> questiondataList17 = new ArrayList<>();
    public List<Example> questiondataList18 = new ArrayList<>();
    public List<Example> questiondataList19 = new ArrayList<>();


    //hS Question

    public List<HSInspectionModel> hs = new ArrayList<>();

    public List<planetmodel> hs1 = new ArrayList<>();

    public String screen_name = "";
    public String jobreview_image = "";
    public String jobreview_operation = "";
    public String data_value = "";
    public String hs_data_value = "";
    public String mbl_last_step_val = "";
    public String check_valid1 = "";
    public String check_valid2 = "";
    public String check_valid3 = "";
    public String check_valid4 = "";
    public String check_valid5 = "";
    public String check_valid6 = "";
    public String check_valid7 = "";
    public String check_valid8 = "";
    public String check_valid9 = "";
    public String check_valid10 = "";
    public String check_valid11 = "";
    public String check_valid12 = "";
    public String check_valid13 = "";
    public String check_valid14 = "";
    public String check_valid15 = "";
    public String check_valid16 = "";
    public String check_valid17 = "";
    public String check_valid18 = "";
    public boolean status_value;

    public String notification_count = "";
    private Context _context;


    public SessionManager(Context context) {
        int PRIVATE_MODE = 0;
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
        pref2 = context.getSharedPreferences(PREF_DEVICE, PRIVATE_MODE);
        editor2 = pref2.edit();
    }


    public String getFcmId() {
        return pref2.getString(FCM_ID, null);
    }

    public void setFcmId(String fcmId) {
        editor2 = pref2.edit();
        editor2.putString(FCM_ID, fcmId);
        editor2.apply();
    }


    public boolean getStatus() {
        return pref.getBoolean(STATUS, true);
    }

    public void setStatus(boolean status) {
        editor.putBoolean(STATUS, status);
        editor.commit();
    }

    public String getKeyRoll() {
        return pref.getString(KEY_ROLL, null);
    }

    public void setKeyRoll(String roll) {
        editor.putString(KEY_ROLL, roll);
        editor.commit();
    }


    public String getKeyToken() {
        return pref.getString(KEY_TOKEN, null);
    }

    public void setKeyToken(String token) {
        editor.putString(KEY_TOKEN, token);
        editor.commit();
    }

    public String getKeyPassword() {
        return pref.getString(KEY_PASSWORD, null);
    }

    public void setKeyPassword(String keyPassword) {
        editor.putString(KEY_PASSWORD, keyPassword);
        editor.commit();
    }

    public void setTotalPoint(String point) {
        editor.putString(TOTAL_POINT, point);
        editor.commit();
    }

    public String getTotalPoint() {
        return pref.getString(TOTAL_POINT, "");
    }

    public String getKeyMobile() {
        return pref.getString(KEY_EMAIL, "");
    }

    public void setKeyMobile(String mobile) {
        editor.putString(KEY_EMAIL, mobile);
        editor.commit();
    }

    public String getKeyName() {
        return pref.getString(KEY_NAME, "");
    }

    public void setKeyName(String name) {
        editor.putString(KEY_NAME, name);
        editor.commit();
    }

    public String getKeyTruckno() {
        return pref.getString(KEY_TRUCKNO, "");
    }

    public void setKeyTruckno(String trno) {
        editor.putString(KEY_TRUCKNO, trno);
        editor.commit();
    }

    public String getKeyTruckid() {
        return pref.getString(KEY_TRUCKID, "");
    }

    public void setKeyTruckid(String trno) {
        editor.putString(KEY_TRUCKID, trno);
        editor.commit();
    }

    public String getKeyLname() {
        return pref.getString(KEY_LNAME, "");
    }

    public void setKeyLname(String name) {
        editor.putString(KEY_LNAME, name);
        editor.commit();
    }

    public String getKeyEmail() {
        return pref.getString(KEY_EMAIL, "");
    }

    public void setKeyEmail(String email) {
        editor.putString(KEY_EMAIL, email);
        editor.commit();
    }

    public String getKeyId() {
        return pref.getString(KEY_ID, null);
    }


    public void setKeyId(String keyId) {
        editor.putString(KEY_ID, keyId);
        editor.apply();
    }


    public void setCartCount(String keyId) {
        editor.putString(CART_COUNT_ID, keyId);
        editor.apply();
    }

    public String getCartCount() {
        return pref.getString(CART_COUNT_ID, null);
    }

    public String getLoginType() {
        return pref.getString(LOGIN_TYPE, "");
    }

    public void setLoginType(String loginType) {
        editor.putString(LOGIN_TYPE, loginType);
        editor.commit();
    }

    public void createLoginSession() {
        editor.putBoolean(IS_LOGIN, true);
        editor.commit();
    }

    public void checkInstall() {
        editor.putBoolean(IS_NEW, true);
        editor.commit();
    }

    public boolean isInstalled() {
        return pref.getBoolean(IS_NEW, false);
    }

    public void setKeyUserDetails(String userData) {
        editor.putString(KEY_USER_DETAILS, userData);
        editor.commit();
    }

   /* public List<UserDataResponse.Data> getUserData() {
        Type type = new TypeToken<List<UserDataResponse.Data>>() {
        }.getType();
        return new Gson().fromJson(pref.getString(KEY_USER_DETAILS, null), type);
    }*/

    public void setKeyImage(String keyImage) {
        editor.putString(KEY_IMAGE, keyImage);
        editor.commit();
    }

    public String getKeyImage() {
        return pref.getString(KEY_IMAGE, "");
    }

//    public List<ImagePathResponse.Data> getImageData() {
//        Type type = new TypeToken<List<ImagePathResponse.Data>>() {
//        }.getType();
//        return new Gson().fromJson(pref.getString(KEY_IMAGE, null), type);
//    }

    public void logoutUser() {
        editor.clear();
        editor.commit();

        Intent i = new Intent(_context, LoginActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY);
        _context.startActivity(i);
    }

    public boolean isLoggedIn() {
        return pref.getBoolean(IS_LOGIN, false);
    }
}