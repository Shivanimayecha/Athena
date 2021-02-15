package com.athena.group.application;

import android.annotation.TargetApi;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.Build;

import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.multidex.MultiDex;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


/**
 * Created by harsh
 * on 3/10/2018.
 */

public class Utility extends Application {

    static appContext appcon = null;
    private static Utility mInstance;
    SessionManager sessionManager = null;

    @Override
    public void onCreate() {

        super.onCreate();
        mInstance = this;
        if (appcon == null) {
            appcon = new appContext();
            appcon.init(mInstance);
            sessionManager = new SessionManager(this);
        }

        printHashKey();

        // TypefaceUtil.setDefaultFont(this, "DEFAULT", "lato_font.ttf");
        //TypefaceUtil.overrideFont(getApplicationContext(), "SERIF", "fonts/lato_mediaum.ttf");
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static boolean checkPermission(final Context context, String... permissions) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    public static String day = new SimpleDateFormat("EEEE", Locale.getDefault()).format(new Date());
    public static String date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
    public static String date1 = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
    public static String time = new SimpleDateFormat("hh:mm a").format(Calendar.getInstance().getTime());
    public static String time1 = new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());


    public static boolean validateContactNo(String text) {
        return !TextUtils.isEmpty(text) && text.matches("^(?:(?:\\+|0{0,2})91(\\s*[\\-]\\s*)?|[0]?)?[789]\\d{9}$");
    }

    public static boolean validateEmail(String target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    public static Bitmap getResizedBitmap(Bitmap bm) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) 1200) / width;
        float scaleHeight = ((float) 1200) / height;
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap resizedBitmap = Bitmap.createBitmap(
                bm, 0, 0, width, height, matrix, false);
        bm.recycle();
        return resizedBitmap;
    }

    public static void displayToast(Context context, String toastMsg) {
        Toast.makeText(context, toastMsg, Toast.LENGTH_SHORT).show();
    }

/*
    public static void displaySnack(View snack_view, String Msg, Context context) {
        Snackbar snackbar = Snackbar.make(snack_view, Msg, Toast.LENGTH_SHORT);
        View view = snackbar.getView();
        view.setBackgroundColor(Color.GRAY);
        TextView tv = view.findViewById(android.support.design.R.id.snackbar_text);
        tv.setTextColor(context.getResources().getColor(R.color.colorPrimary));
        snackbar.show();
    }
*/

    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public static appContext getAppcon() {
        if (appcon == null) {
            appcon = new appContext();
            appcon.init(mInstance);
        }
        return appcon;
    }

    public void printHashKey() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.group.petsforever.application",
                    PackageManager.GET_SIGNATURES);

            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
        } catch (NoSuchAlgorithmException e) {
        }
    }

}