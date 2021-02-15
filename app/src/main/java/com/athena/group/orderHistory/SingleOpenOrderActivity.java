package com.athena.group.orderHistory;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.athena.group.API.ApiConstants;
import com.athena.group.API.ApiInterface;
import com.athena.group.API.ApiServiceCreator;
import com.athena.group.Activity.NotificationActivity;
import com.athena.group.FileUtils;
import com.athena.group.Model.OrderDetailsTruckModel;
import com.athena.group.Model.OrderStatusModel;
import com.athena.group.Model.SpinnerModel;
import com.athena.group.Model.TimeLogModel;
import com.athena.group.R;
import com.athena.group.adapter.MyAdapter;
import com.athena.group.adapter.OrderDisplayAdapter;
import com.athena.group.application.SessionManager;
import com.athena.group.application.Utility;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;
import com.wang.avi.AVLoadingIndicatorView;

import java.io.File;
import java.net.SocketTimeoutException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.zelory.compressor.Compressor;
import io.reactivex.annotations.NonNull;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Observable;
import rx.Observer;
import rx.schedulers.Schedulers;

public class SingleOpenOrderActivity extends AppCompatActivity {

    @BindView(R.id.toolbar_Title)
    TextView toolbar_Title;
    @BindView(R.id.back_icon)
    ImageView back_icon;
    @BindView(R.id.avi)
    AVLoadingIndicatorView avi;
    AVLoadingIndicatorView avi1;
    @BindView(R.id.txt_date)
    TextView txt_date;
    @BindView(R.id.txt_dl_date)
    TextView txt_dl_date;
    /*@BindView(R.id.txt_qty)
    TextView txt_qty;*/
    @BindView(R.id.txt_odr_no)
    TextView txt_odr_no;
    @BindView(R.id.txt_odrno)
    TextView txt_odrno;
    @BindView(R.id.txt_ordstatus)
    TextView txt_ordstatus;
    @BindView(R.id.btn_conform)
    Button btn_conform;
    @BindView(R.id.btn_pdf)
    Button btn_pdf;
    @BindView(R.id.relative)
    RelativeLayout relative;
    EditText edt_cmnt;
    GridView listView;
    SessionManager sessionManager;
    ApiInterface apiservice;
    int statusCode;
    List<SpinnerModel.Data> data;
    List<OrderStatusModel.Data> data1;
    String site = "", s_id = "", order = "", o_id = "";
    ArrayList<String> sitearray = new ArrayList<>();
    ArrayList<String> orderarray = new ArrayList<>();
    private Dialog dialog;
    private static final int REQUEST_CODE = 6384;
    List<MultipartBody.Part> parts;
    private ArrayList<Uri> imagesPathList;
    private static final int CAPTURE_IMAGE = 3;
    Uri mCapturedImageURI;
    @BindView(R.id.rv_orderlist)
    RecyclerView rv_orderlist;
    RecyclerView.LayoutManager layoutManager;
    OrderDisplayAdapter adapter;
    List<String> image_list;
    String ord_id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_single_open_order);
        ButterKnife.bind(this);
        sessionManager = new SessionManager(SingleOpenOrderActivity.this);
        apiservice = ApiServiceCreator.createService("latest");
        toolbar_Title.setText("Order Details");
        back_icon.setOnClickListener(view -> finish());
        imagesPathList = new ArrayList<>();

        ord_id = getIntent().getStringExtra("data_delivered_ord_id");
        relative.setVisibility(View.GONE);
        callApi();
    }

    private void callApi() {

        try {
            avi.show();
            Observable<OrderDetailsTruckModel> responseObservable = apiservice.get_single_open_order(ord_id);
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
                    .subscribe(new Observer<OrderDetailsTruckModel>() {
                        @Override
                        public void onCompleted() {
                            avi.hide();
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.e("error", "" + e.getMessage());
                        }

                        @Override
                        public void onNext(OrderDetailsTruckModel model) {

                            avi.hide();
                            statusCode = model.getStatusCode();
                            if (statusCode == 200) {
                                relative.setVisibility(View.VISIBLE);
                                rv_orderlist.setHasFixedSize(true);
                                layoutManager = new LinearLayoutManager(getApplicationContext());
                                rv_orderlist.setLayoutManager(layoutManager);
                                adapter = new OrderDisplayAdapter(getApplicationContext(), model.getData().get(0).getOrderDetails());
                                rv_orderlist.setAdapter(adapter);

                                if (model.getData().get(0).getOrderStatusName().equals("Delivered")) {
                                    btn_conform.setEnabled(true);
                                } else if (model.getData().get(0).getOrderStatusName().equals("Order assigned to third party")) {
                                    btn_conform.setEnabled(true);
                                } else if (model.getData().get(0).getOrderStatusName().equals("Completed")) {
                                    btn_conform.setVisibility(View.GONE);
                                } else {
                                    btn_conform.setBackground(getDrawable(R.drawable.grayback));
                                    btn_conform.setTextColor(Color.parseColor("#ffffff"));
                                }

                                DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
                                DateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy");
                                String inputDateStr = model.getData().get(0).getOrderDetails().get(0).getOrdDate();
                                Date date = null;
                                try {
                                    date = inputFormat.parse(inputDateStr);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                String outputDateStr = outputFormat.format(date);
                                txt_date.setText(outputDateStr);

                                txt_dl_date.setText(model.getData().get(0).getOrderDetails().get(0).getOrdDeliveryDate());


                                txt_odrno.setText("#" + model.getData().get(0).getOrderDetails().get(0).getMs_ord_id());
                                txt_odr_no.setText("Order #" + model.getData().get(0).getOrderDetails().get(0).getMs_ord_id() + " Invoice ");
                                // txt_qty.setText(model.getData().get(0).getOrderDetails().get(0).getOrdQty() + " Qty");

                                if (model.getData().get(0).getOrderStatusName().equals("Delivered")) {
                                    // txt_ordstatus.setText("Received");
                                    txt_ordstatus.setText("Delivered");

                                } else {
                                    txt_ordstatus.setText(model.getData().get(0).getOrderStatusName());
                                }

                                btn_pdf.setOnClickListener(view -> {
                                    String pdf_url = model.getData().get(0).getOrderDetails().get(0).getOrdPdfLink();
                                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(ApiConstants.PDF_URL + Uri.parse(pdf_url)));
                                    startActivity(browserIntent);
                                });

                                btn_conform.setOnClickListener(view -> {
                                    if (model.getData().get(0).getOrderStatusName().equals("Delivered")) {
                                        openDialog(model.getData().get(0).getOrderDetails().get(0).getMs_ord_id());
                                    } else if (model.getData().get(0).getOrderStatusName().equals("Order assigned to third party")) {
                                        openDialog(model.getData().get(0).getOrderDetails().get(0).getMs_ord_id());
                                    } else {
                                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getApplicationContext());
                                        alertDialogBuilder.setMessage("Order delivery yet not confrim by assigned truck driver.");
                                        alertDialogBuilder.setPositiveButton("Ok",
                                                new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface arg0, int arg1) {
                                                    }
                                                });
                                        AlertDialog alertDialog = alertDialogBuilder.create();
                                        alertDialog.show();
                                    }
                                });

                            } else {
                                relative.setVisibility(View.GONE);
                                Utility.displayToast(getApplicationContext(), model.getMessage());
                            }
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void openDialog(String ms_ord_id) {

        dialog = new Dialog(SingleOpenOrderActivity.this);
        dialog.getWindow();
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_ordrconfrm);
        dialog.setCancelable(true);

        Button btn_submt = dialog.findViewById(R.id.btn_submit);
        ImageView txt_cancel = dialog.findViewById(R.id.img_close);
        edt_cmnt = dialog.findViewById(R.id.edt_cmmnt);
        listView = dialog.findViewById(R.id.listView);
        avi1 = dialog.findViewById(R.id.avi);
        LinearLayout imgpic = dialog.findViewById(R.id.img_picibutton);

        txt_cancel.setOnClickListener(view -> dialog.dismiss());
        imgpic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
                //showChooser();
            }
        });

        edt_cmnt.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent event) {
                // TODO Auto-generated method stub
                if (view.getId() == R.id.edt_cmtHSEQ) {
                    view.getParent().requestDisallowInterceptTouchEvent(true);
                    switch (event.getAction() & MotionEvent.ACTION_MASK) {
                        case MotionEvent.ACTION_UP:
                            view.getParent().requestDisallowInterceptTouchEvent(false);
                            break;
                    }
                }
                return false;
            }
        });

        btn_submt.setOnClickListener(view -> {
            callSubmitApi(ms_ord_id);
        });


        dialog.show();
        Window window = dialog.getWindow();
        assert window != null;
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    private void callSubmitApi(String ms_ord_id) {

        try {
            parts = new ArrayList<>();

            if (imagesPathList != null) {
                for (int i = 0; i < imagesPathList.size(); i++) {
                    parts.add(prepareFilePart("coc_images[]", imagesPathList.get(i)));
                }
            }
            RequestBody u_id = RequestBody.create(MediaType.parse("text/plain"), sessionManager.getKeyId());
            RequestBody msid = RequestBody.create(MediaType.parse("text/plain"), ms_ord_id);
            RequestBody cmnt = RequestBody.create(MediaType.parse("text/plain"), edt_cmnt.getText().toString());

            avi1.show();
            Observable<TimeLogModel> responseObservable = apiservice.confirmOrder(u_id, msid, cmnt, parts);

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
                    .subscribe(new Observer<TimeLogModel>() {
                        @Override
                        public void onCompleted() {
                            avi1.hide();
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.e("error", "" + e.getMessage());
                        }

                        @Override
                        public void onNext(TimeLogModel model) {
                            statusCode = model.getStatusCode();
                            if (statusCode == 200) {
                                Utility.displayToast(getApplicationContext(), model.getMessage());
                                txt_ordstatus.setText("Order Delivered");
                                btn_conform.setText("Material Received");
                                btn_conform.setEnabled(false);
                                btn_conform.setBackground(getDrawable(R.drawable.grayback));
                                btn_conform.setTextColor(Color.parseColor("#ffffff"));
                                dialog.dismiss();
                            } else {
                                Utility.displayToast(getApplicationContext(), model.getMessage());
                            }
                        }
                    });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private void selectImage() {

        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(SingleOpenOrderActivity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (options[item].equals("Take Photo")) {

                    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                        String fileName = "temp.jpg";
                        ContentValues values = new ContentValues();
                        values.put(MediaStore.Images.Media.TITLE, fileName);
                        mCapturedImageURI = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, mCapturedImageURI);
                        startActivityForResult(takePictureIntent, CAPTURE_IMAGE);
                    }
                } else if (options[item].equals("Choose from Gallery")) {

                    showChooser();
//                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                    startActivityForResult(intent, 1);
                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void showChooser() {
        // Use the GET_CONTENT intent from the utility class
        Intent target = FileUtils.createGetContentIntent();
        // Create the chooser Intent
        Intent intent = Intent.createChooser(
                target, "Select Image");
        try {
            startActivityForResult(intent, REQUEST_CODE);
        } catch (ActivityNotFoundException e) {
            // The reason for the existence of aFileChooser
        }
    }

    @NonNull
    private MultipartBody.Part prepareFilePart(String partName, Uri fileUri) {
        RequestBody requestFile;
        File file = FileUtils.getFile(this, fileUri);
        requestFile = RequestBody.create(MediaType.parse("image/*"), file);
        return MultipartBody.Part.createFormData(partName, file.getName(), requestFile);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case CAPTURE_IMAGE:
                ImageCropFunctionCustom(mCapturedImageURI);

                break;
            case CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE:

                CropImage.ActivityResult result = CropImage.getActivityResult(data);
                Compressor compressedImageFile = new Compressor(this);
                compressedImageFile.setQuality(60);

                try {
                    // Get the file path from the URI
                    File file = compressedImageFile.compressToFile(new File(result.getUri().getPath()));
                    Uri uri = Uri.fromFile(file);

                    Log.e("TAG", "capture uri:-- " + uri);
                    final String path = FileUtils.getPath(getApplicationContext(), uri);
                    Log.e("Single File Selected", path);

                    imagesPathList.add(uri);
                    Log.e("Path is ", path);

                    MyAdapter mAdapter = new MyAdapter(getApplicationContext(), imagesPathList);
                    listView.setAdapter(mAdapter);
                } catch (Exception e) {
                }
                break;
            case REQUEST_CODE:
                // If the file selection was successful
                if (resultCode == RESULT_OK) {
                    if (data.getClipData() != null) {
                        int count = data.getClipData().getItemCount();
                        int currentItem = 0;
                        while (currentItem < count) {
                            Uri imageUri = data.getClipData().getItemAt(currentItem).getUri();
                            //do something with the image (save it to some directory or whatever you need to do with it here)
                            currentItem = currentItem + 1;
                            try {
                                imagesPathList.add(imageUri);
                                MyAdapter mAdapter = new MyAdapter(getApplicationContext(), imagesPathList);
                                listView.setAdapter(mAdapter);
                            } catch (Exception e) {

                            }

                            Log.e("Uri Selected", imageUri.toString());

                        }
                    } else if (data.getData() != null) {
                        //do something with the image (save it to some directory or whatever you need to do with it here)
                        final Uri uri = data.getData();

                        try {
                            // Get the file path from the URI
                            final String path = FileUtils.getPath(getApplicationContext(), uri);
                            Log.e("Single File Selected", path);

                            imagesPathList.add(uri);
                            Log.e("Path is ", path);
                            MyAdapter mAdapter = new MyAdapter(getApplicationContext(), imagesPathList);
                            listView.setAdapter(mAdapter);


                        } catch (Exception e) {

                        }
                    }
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void ImageCropFunctionCustom(Uri uri) {
        Intent intent = CropImage.activity(uri)
                .setGuidelines(CropImageView.Guidelines.ON)
                .getIntent(this);
        startActivityForResult(intent, CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        callHandler();
    }

    private void callHandler() {
        if (NotificationActivity.handler != null) {
            Message message = Message.obtain();
            message.what = 100;
            if (NotificationActivity.handler != null) {
                NotificationActivity.handler.sendMessage(message);
            }
        }
    }
}
