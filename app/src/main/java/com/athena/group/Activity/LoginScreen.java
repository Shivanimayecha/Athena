/*
package com.group.group.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.group.group.API.APIClient;
import com.group.group.API.ApiInterface;
import com.group.group.AppCons;
import com.group.group.Constant;
import com.group.group.MainActivity;
import com.group.group.R;
import com.group.group.SharedObjects;
import com.group.group.Utilities;
import com.google.gson.JsonElement;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.group.group.Utilities.*;

public class LoginScreen extends Activity {
    public static ApiInterface apiInterface;
    EditText user_email, user_password;
    SharedObjects sharedPrefManager;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_loginscreen);
        apiInterface = APIClient.getAPIClient().create(ApiInterface.class);

        sharedPrefManager = new SharedObjects(this);
        user_email = findViewById(R.id.emailid);
        user_password = findViewById(R.id.password);
        Button login_btn = findViewById(R.id.login_btn);

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate();
                loginUser(user_email.getText().toString().trim(), user_password.getText().toString().trim());

            }
        });

    }

    private boolean validate() {
        // check the lenght of the enter data in EditText and give error if its empty


        boolean valid = true;

        String email = user_email.getText().toString();
        String password = user_password.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            user_email.setError("enter a valid email address");
            valid = false;
        } else {
            user_email.setError(null);
        }


        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            user_password.setError("enter a valid password");
            valid = false;
        } else {
            user_password.setError(null);
        }

        return valid;

    }

    private void loginUser(final String user_email, String user_password) {

        //showProgressDialog();
        Call<JsonElement> call = (Call<JsonElement>) apiInterface.loginUser(user_email, user_password);
        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                //  dismissProgressDialog();
                try {

                    Log.e("Login res ", response.body().toString());
                    JSONObject object = new JSONObject(response.body().toString());
                    String status = object.getString("statusCode");
                    String msg = object.getString("message");

                    if (status.equals("200")) {
                        sharedPrefManager.preferencesEditor.setPreference(AppCons.STATUS, AppCons.STATUS_LOGIN);
                        sharedPrefManager.SaveUserData(user_email);
                        setSharedPreference(getApplicationContext(), Constant.LOGIN_STATUS, user_email);
                        //    setSharedPreference(getApplicationContext(), Constant.LOGIN_ID, id);
                        startActivity(new Intent(LoginScreen.this, MainActivity.class));
                        finish();
                    } else {
                        Toast.makeText(LoginScreen.this, msg, Toast.LENGTH_SHORT).show();

                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                //  dismissProgressDialog();
            }
        });
    }

    public void showProgressDialog() {
        progressDialog = new ProgressDialog(LoginScreen.this);
        progressDialog.setMax(100);
        progressDialog.setIcon(R.drawable.loadingbar);
        progressDialog.setCancelable(true);
        progressDialog.setMessage("Loading");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
    }

    public void dismissProgressDialog() {
        if (progressDialog.isShowing())
            progressDialog.dismiss();
    }

}
*/
