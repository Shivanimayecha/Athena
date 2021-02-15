package com.athena.group.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.athena.group.API.ApiConstants;
import com.athena.group.API.ApiInterface;
import com.athena.group.API.ApiServiceCreator;
import com.athena.group.Model.PositionModel;
import com.athena.group.Model.UserDataResponse;
import com.athena.group.R;
import com.athena.group.application.SessionManager;
import com.athena.group.application.Utility;
import com.bumptech.glide.Glide;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;
import com.wang.avi.AVLoadingIndicatorView;

import java.io.File;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import id.zelory.compressor.Compressor;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class EditUserProfile extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.edt_fname)
    EditText edt_fname;
    @BindView(R.id.edt_lname)
    EditText edt_lname;
    @BindView(R.id.edt_mobile)
    EditText edt_mobile;
    @BindView(R.id.edt_address)
    EditText edt_address;
    @BindView(R.id.edt_password)
    EditText edt_password;
    @BindView(R.id.spn_position)
    Spinner spn_position;
    @BindView(R.id.img_close)
    ImageView img_close;
    @BindView(R.id.img_save)
    ImageView img_save;
    @BindView(R.id.avi)
    AVLoadingIndicatorView avi;
    @BindView(R.id.profile_image)
    CircleImageView profile_image;
    @BindView(R.id.ll_changeimg)
    LinearLayout ll_changeimg;

    SessionManager sessionManager;
    ApiInterface apiservice;
    int statusCode;
    String site = "", p_id = "";
    List<PositionModel.Data> positiondata;
    Uri uri, selectedImage;
    String ImageType = "";
    String mediaPath = "";
    ArrayAdapter<String> dataAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.editprofile);
        ButterKnife.bind(this);
        sessionManager = new SessionManager(EditUserProfile.this);
        apiservice = ApiServiceCreator.createService("latest");

        initViews();

    }

    private void initViews() {

        img_close.setOnClickListener(this);
        ll_changeimg.setOnClickListener(this);
        img_save.setOnClickListener(this);

        if (sessionManager.getKeyRoll().equals("Supervisor")) {
            spn_position.setVisibility(View.VISIBLE);
        } else {
            spn_position.setVisibility(View.GONE);
        }

        getUserDetail();
        getPosition();
        spn_position.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (spn_position.getSelectedItem() == "Select Position") {
                    site = "";
                    //Do nothing.
                } else {
                    site = spn_position.getSelectedItem().toString();
                    PositionModel.Data datalist = positiondata.get(i - 1);
                    p_id = datalist.getUpId();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_close:
                finish();
                break;
            case R.id.ll_changeimg:
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 0);
                break;
            case R.id.img_save:
                updateUserData();
                break;
        }
    }

    private void getPosition() {

        ArrayList<String> positionarray = new ArrayList<>();
        avi.show();

        Observable<PositionModel> responseObservable = apiservice.getPosition();
        responseObservable.subscribeOn(Schedulers.newThread())
                .observeOn(rx.android.schedulers.AndroidSchedulers.mainThread())
                .onErrorResumeNext(throwable -> {
                    if (throwable instanceof retrofit2.HttpException) {
                        retrofit2.HttpException ex = (retrofit2.HttpException) throwable;
                        statusCode = ex.code();
                        Log.e("error", ex.getLocalizedMessage());
                    } else if (throwable instanceof SocketTimeoutException) {
                        statusCode = 1000;
                    }
                    return Observable.empty();
                })
                .subscribe(new Observer<PositionModel>() {
                    @Override
                    public void onCompleted() {
                        avi.hide();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("error", "" + e.getMessage());
                    }

                    @Override
                    public void onNext(PositionModel model) {
                        statusCode = model.getStatusCode();
                        if (statusCode == 200) {
                            positiondata = model.getData();
                            for (int i = 0; i < model.getData().size(); i++) {
                                positionarray.add(model.getData().get(i).getUpName());
                            }
                        }
                    }
                });

        dataAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, positionarray);
        dataAdapter.add("Select Position");
        dataAdapter.setDropDownViewResource(R.layout.spinner_item);
        spn_position.setAdapter(dataAdapter);
    }


    private void getUserDetail() {

        try {
            avi.show();
            Observable<UserDataResponse> responseObservable = apiservice.getUserDetails(sessionManager.getKeyEmail());
            responseObservable.subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .onErrorResumeNext(throwable -> {
                        if (throwable instanceof retrofit2.HttpException) {
                            retrofit2.HttpException ex = (retrofit2.HttpException) throwable;
                            statusCode = ex.code();
                            Log.e("error", ex.getLocalizedMessage());
                        } else if (throwable instanceof SocketTimeoutException) {
                            statusCode = 1000;
                        }
                        return Observable.empty();
                    })
                    .subscribe(new Observer<UserDataResponse>() {
                        @Override
                        public void onCompleted() {
                            avi.hide();
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.e("error", e.getLocalizedMessage());
                        }

                        @Override
                        public void onNext(UserDataResponse userDataResponse) {
                            statusCode = userDataResponse.getStatusCode();
                            if (statusCode == 200) {

                                edt_fname.setText(userDataResponse.getData().get(0).getName());
                                edt_lname.setText(userDataResponse.getData().get(0).getLname());
                                edt_mobile.setText(userDataResponse.getData().get(0).getMobile());
                                edt_address.setText(userDataResponse.getData().get(0).getAddressLine1());

                                String compareValue = userDataResponse.getData().get(0).getUserPosition();
                                if (compareValue != null) {
                                    int spinnerPosition = dataAdapter.getPosition(compareValue);
                                    spn_position.setSelection(spinnerPosition);
                                }
                                Glide.with(EditUserProfile.this)
                                        .load(ApiConstants.IMAGE_URL + userDataResponse.getData().get(0).getProfileImage())
                                        .placeholder(R.color.gray)
                                        .into(profile_image);
                            } else {
                                Utility.displayToast(EditUserProfile.this, userDataResponse.getMessage());
                            }
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateUserData() {

        avi.show();

        Observable<UserDataResponse> responseObservable = apiservice.updatePersonalDetail(
                sessionManager.getKeyId(),
                edt_fname.getText().toString(),
                edt_lname.getText().toString(),
                p_id,
//                edt_acc_no.getText().toString(),
//                edt_ifsc.getText().toString(),
//                edt_bankname.getText().toString(),
//                edt_charge.getText().toString(),
//                edt_holdername.getText().toString(),
//                edt_brachcode.getText().toString()
                edt_mobile.getText().toString(),
                edt_address.getText().toString(),
                edt_password.getText().toString()
        );

        responseObservable.subscribeOn(Schedulers.newThread())
                .observeOn(rx.android.schedulers.AndroidSchedulers.mainThread())
                .onErrorResumeNext(throwable -> {
                    if (throwable instanceof retrofit2.HttpException) {
                        retrofit2.HttpException ex = (retrofit2.HttpException) throwable;
                        statusCode = ex.code();
                        Log.e("error", ex.getLocalizedMessage());
                    } else if (throwable instanceof SocketTimeoutException) {
                        statusCode = 1000;
                    }
                    return Observable.empty();
                })
                .subscribe(new Observer<UserDataResponse>() {
                    @Override
                    public void onCompleted() {
                        avi.hide();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("error", "" + e.getMessage());
                    }

                    @Override
                    public void onNext(UserDataResponse model) {
                        avi.hide();

                        statusCode = model.getStatusCode();
                        if (statusCode == 200) {
                            Utility.displayToast(EditUserProfile.this, model.getMessage());
                            Intent intent = new Intent(EditUserProfile.this, Profile.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            sessionManager.setKeyImage(model.getData().get(0).getProfileImage());
                            sessionManager.setKeyId(model.getData().get(0).getUserId());
                            finish();
                        }

                    }
                });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
/*
        try {
*/
        if (requestCode == 0 && resultCode == RESULT_OK && null != data) {
            selectedImage = data.getData();
            // ImageCropFunction();
            ImageCropFunctionCustom();
        } else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            if (data != null) {
                CropImage.ActivityResult result = CropImage.getActivityResult(data);
                uri = result.getUri();
                ImageType = "profile";
                //uri = Uri.fromFile(new File(StoredPath));
                Bundle bundle = data.getExtras();
                assert bundle != null;
                Bitmap bitmap = bundle.getParcelable("data");
                // uri = Uri.fromFile(new File(StoredPath));
                mediaPath = uri.getPath();
                   /* FileOutputStream mFileOutStream = new FileOutputStream(StoredPath);
                    assert bitmap != null;
                    //  bitmap.compress(Bitmap.CompressFormat.PNG, 90, mFileOutStream);
                    mFileOutStream.flush();
                    mFileOutStream.close();*/

                if (mediaPath != null) {
                    profile_image.setImageBitmap(BitmapFactory.decodeFile(mediaPath));
                    //imageView.setImageResource(R.drawable.no_image);
                    uploadFile();
                } else {
                    if (uri != null) {
                        mediaPath = uri.getPath();
                        profile_image.setImageBitmap(BitmapFactory.decodeFile(mediaPath));
                        //imageView.setImageResource(R.drawable.no_image);
                        uploadFile();
                    }
                }
                uploadFile();
            }
        }

        /*} catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();
        }*/
    }

    public void ImageCropFunctionCustom() {
        Intent intent = CropImage.activity(selectedImage)
                .setGuidelines(CropImageView.Guidelines.ON)
                .getIntent(this);
        startActivityForResult(intent, CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE);
    }


    private void uploadFile() {

        /*final ProgressDialog pDialog;
        pDialog = new ProgressDialog(this);
        pDialog.setTitle("Uploading profile image");
        pDialog.setMessage("Please Wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);
        pDialog.show();*/
        avi.show();

        // Map is used to multipart the file using okhttp3.RequestBody
        Map<String, RequestBody> map = new HashMap<>();
        if (mediaPath != null) {

            Compressor compressedImageFile = new Compressor(this);
            compressedImageFile.setQuality(60);
            try {
                File file = new File(mediaPath);
                File compressfile = compressedImageFile.compressToFile(file);
                RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), compressfile);
                RequestBody user_id = RequestBody.create(MediaType.parse("text/plain"), sessionManager.getKeyId());
                map.put("file\"; filename=\"" + file.getName() + "\"", requestBody);
                map.put("userId", user_id);
                Observable<UserDataResponse> responseObservable = apiservice.upload_profile_image(map);

                responseObservable.subscribeOn(Schedulers.newThread())
                        .observeOn(rx.android.schedulers.AndroidSchedulers.mainThread())
                        .onErrorResumeNext(throwable -> {
                            if (throwable instanceof retrofit2.HttpException) {
                                retrofit2.HttpException ex = (retrofit2.HttpException) throwable;
                                statusCode = ex.code();
                                Log.e("error", ex.getLocalizedMessage());
                            } else if (throwable instanceof SocketTimeoutException) {
                                statusCode = 1000;
                            }
                            return Observable.empty();
                        })
                        .subscribe(new Observer<UserDataResponse>() {
                            @Override
                            public void onCompleted() {
                                avi.hide();
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.e("error", "" + e.getMessage());
                            }

                            @Override
                            public void onNext(UserDataResponse model) {
                                avi.hide();
                                statusCode = model.getStatusCode();
                                if (statusCode == 200) {

                                    Picasso.get().load(ApiConstants.IMAGE_URL + model.getData().get(0).getProfileImage())
                                            .memoryPolicy(MemoryPolicy.NO_CACHE)
                                            .networkPolicy(NetworkPolicy.NO_CACHE)
                                            .placeholder(R.color.gray)
                                            .into(profile_image);

                                    //getPost();
                                    Toast.makeText(getApplicationContext(), "Your profile image successfully updated", Toast.LENGTH_SHORT).show();
                                    if (!model.getData().get(0).getProfileImage().equals("")) {
                                        sessionManager.setKeyImage(model.getData().get(0).getProfileImage());
                                    }
                                }

                            }
                        });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
