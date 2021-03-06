package com.athena.group.Truck__Driver_Panel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.athena.group.API.ApiConstants;
import com.athena.group.API.ApiInterface;
import com.athena.group.API.ApiServiceCreator;
import com.athena.group.Activity.SignOrderActivity;
import com.athena.group.Model.OrderDetailsModel;
import com.athena.group.Model.OrderDetailsTruckModel;
import com.athena.group.Model.OrderStatusModel;
import com.athena.group.Model.SpinnerModel;
import com.athena.group.R;
import com.athena.group.adapter.OrderDisplayAdapter;
import com.athena.group.application.SessionManager;
import com.athena.group.application.Utility;
import com.google.gson.Gson;
import com.wang.avi.AVLoadingIndicatorView;

import java.io.File;
import java.io.FileOutputStream;
import java.net.SocketTimeoutException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Observable;
import rx.Observer;
import rx.schedulers.Schedulers;

public class OrderDisplayActivity extends AppCompatActivity {

    @BindView(R.id.toolbar_Title)
    TextView toolbar_Title;
    @BindView(R.id.txt_custname)
    TextView txt_custname;
    @BindView(R.id.txt_driver_name)
    TextView txt_driver_name;
    @BindView(R.id.txt_wagonno)
    TextView txt_wagonno;
    @BindView(R.id.txt_loadingadd)
    TextView txt_loadingadd;
    @BindView(R.id.txt_delivadd)
    TextView txt_delivadd;
    @BindView(R.id.avi)
    AVLoadingIndicatorView avi;
    @BindView(R.id.txt_odrno)
    TextView txt_odrno;
    @BindView(R.id.txt_date)
    TextView txt_date;
    @BindView(R.id.txt_ordstatus)
    TextView txt_ordstatus;

    @BindView(R.id.edt_weight)
    EditText edt_weight;
    @BindView(R.id.edt_printname)
    EditText edt_printname;
    @BindView(R.id.edt_job)
    EditText edt_job;

    @BindView(R.id.back_icon)
    ImageView back_icon;
    @BindView(R.id.img_pic)
    ImageView img_pic;
    @BindView(R.id.img_picimage)
    ImageView img_picimage;
    @BindView(R.id.btn_status)
    Button btn_status;
    @BindView(R.id.btn_submit)
    Button btn_submit;
    @BindView(R.id.btn_pdf)
    Button btn_pdf;
    @BindView(R.id.btn_pdf1)
    Button btn_pdf1;
    @BindView(R.id.ll_fillup)
    LinearLayout ll_fillup;

    @BindView(R.id.rv_orderlist)
    RecyclerView rv_orderlist;
    SessionManager sessionManager;
    ApiInterface apiservice;
    List<OrderDetailsTruckModel.Data> arraylist;
    int statusCode;
    List<SpinnerModel.Data> data;
    List<OrderStatusModel.Data> data1;
    String order = "", o_id = "";
    //site = "", s_id = "",
    ArrayList<String> sitearray = new ArrayList<>();
    ArrayList<String> orderarray = new ArrayList<>();
    String truck_driver_status = "", ConvertedBitmap = "";
    Dialog dialog;
    LinearLayout mContent;
    View view;
    signature mSignature;
    Bitmap bitmap;
    AlertDialog.Builder builder;
    Button mClear, mGetSign, mCancel;
    OrderDisplayAdapter adapter;
    RecyclerView.LayoutManager layoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_order_display);
        ButterKnife.bind(this);
        sessionManager = new SessionManager(OrderDisplayActivity.this);
        apiservice = ApiServiceCreator.createService("latest");
        toolbar_Title.setText("Order Details");


        back_icon.setOnClickListener(view1 -> finish());
        arraylist = Utility.getAppcon().getSession().arrayListTruckOrderResponse;
        Log.e("arrayList", new Gson().toJson(arraylist));

        getData();
        checkOrderStatus();

        if (Utility.getAppcon().getSession().screen_name.equals("CompletedOrder")) {
            ll_fillup.setVisibility(View.GONE);
        } else {
            if (arraylist.get(0).getDriver_order_pdf_link().equals("")) {
                btn_pdf.setVisibility(View.GONE);
                ll_fillup.setVisibility(View.VISIBLE);

            } else {
                btn_pdf.setVisibility(View.VISIBLE);
                ll_fillup.setVisibility(View.GONE);
            }
        }

        if (!arraylist.get(0).getDriver_order_pdf_link().equals("")) {
            btn_pdf.setVisibility(View.VISIBLE);

            btn_pdf.setOnClickListener(view1 ->
            {
                String pdf_url = arraylist.get(0).getDriver_order_pdf_link();
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(ApiConstants.PDF_URL + Uri.parse(pdf_url)));
                startActivity(browserIntent);
            });
        } else {
            btn_pdf.setVisibility(View.GONE);
        }

        btn_status.setOnClickListener(view -> {

            // btn_status.setText("Goods Pickup");
            if (btn_status.getText().toString().equals("Order Assign")) {
                /*btn_status.setText("Goods Delivered");
                txt_ordstatus.setText("Goods Delivered");
                truck_driver_status = "6";*/
                /*if (btn_status.getText().toString().equals("Goods Delivered") && txt_ordstatus.getText().toString().equals("Goods Delivered")) {

                    ll_fillup.setVisibility(View.VISIBLE);
                } else {
                    ll_fillup.setVisibility(View.GONE);
                }*/

                /*if (edt_weight.getText().toString().equals("") && edt_printname.getText().toString().equals("") && edt_job.getText().toString().equals("") && ConvertedBitmap.equals("")) {
                    Utility.displayToast(getApplicationContext(), "Please Fill Deliveryslip Form");
                }*/

                if (ll_fillup.getVisibility() == View.VISIBLE) {
                    Utility.displayToast(getApplicationContext(), "Please Fill Order Deliveryslip Form");
                } else {
                    btn_status.setText("Goods Delivered");
                    txt_ordstatus.setText("Goods Delivered");
                    truck_driver_status = "6";
                    callStatusManagementApi();
                }


            }/* else if (btn_status.getText().toString().equals("Goods Pickup")) {
             *//*truck_driver_status = "5";
                btn_status.setText("In Transit");
                txt_ordstatus.setText("In Transit");*//*
                ll_fillup.setVisibility(View.GONE);

                truck_driver_status = "6";
                btn_status.setText("Goods Delivered");
                txt_ordstatus.setText("Goods Delivered");
                callStatusManagementApi();

            }*//* else if (btn_status.getText().toString().equals("In Transit")) {
                truck_driver_status = "6";
                btn_status.setText("Goods Delivered");
                txt_ordstatus.setText("Goods Delivered");
                callStatusManagementApi();

            }*/ else if (btn_status.getText().toString().equals("Goods Delivered")) {
                //truck_driver_status = "6";
                //ll_fillup.setVisibility(View.GONE);

                btn_status.setText("Goods Delivered");
                txt_ordstatus.setText("Goods Delivered");
            }
        });

        dialog = new Dialog(OrderDisplayActivity.this);
        // Removing the features of Normal Dialogs
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_signature);
        dialog.setCancelable(true);

        img_picimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ConvertedBitmap = "";
                dialog_action();
            }
        });

        btn_submit.setOnClickListener(view ->
        {
            if (ConvertedBitmap.equals("")) {
                Utility.displayToast(this, "Please draw signature");
            } else {
                callFormFillupApi();
            }
        });
    }

    private void getData() {

        rv_orderlist.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        rv_orderlist.setLayoutManager(layoutManager);
        adapter = new OrderDisplayAdapter(OrderDisplayActivity.this, arraylist.get(0).getOrderDetails());
        rv_orderlist.setAdapter(adapter);

        txt_wagonno.setText(arraylist.get(0).getVehicle_no());
        DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy");
        String inputDateStr = arraylist.get(0).getAoDate();
        Date date = null;
        try {
            date = inputFormat.parse(inputDateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String outputDateStr = outputFormat.format(date);
        txt_date.setText(outputDateStr);
        txt_odrno.setText("Order #" + arraylist.get(0).getAoId());
        txt_ordstatus.setText(arraylist.get(0).getOrderStatusName());
        txt_custname.setText(arraylist.get(0).getAo_cust_name());
        txt_driver_name.setText(arraylist.get(0).getAo_driver_name());
        if (arraylist.get(0).getPickupLocation().equals("")) {
            txt_loadingadd.setText("No Pickup Location");
        } else {
            txt_loadingadd.setText(arraylist.get(0).getPickupLocation());
        }
        txt_delivadd.setText(arraylist.get(0).getDropLocation());
    }

    private void checkOrderStatus() {

        String stts = getIntent().getStringExtra("status");
        Log.e("TAG", "checkOrderStatus: --" + stts);
        if (stts.equals("Order Assign")) {
            btn_status.setText(stts);
            txt_ordstatus.setText(stts);
        } else if (stts.equals("Goods Delivered")) {
            btn_status.setText(stts);
            txt_ordstatus.setText(stts);
        } else if (stts.equals("Completed")) {
            btn_status.setVisibility(View.GONE);
        } else if (stts.equals("Delivered")) {
            btn_status.setVisibility(View.GONE);
        }
        /*if (arraylist.get(0).getOrderStatusName().equals("Order Assign")) {
            btn_status.setText("Order Assign");
        } else if (arraylist.get(0).getOrderStatusName().equals("Delivered")) {
            btn_status.setText("Delivered");
        } else if (arraylist.get(0).getOrderStatusName().equals("Completed")) {
            btn_status.setVisibility(View.GONE);
        }*/
    }


    private void callFormFillupApi() {

        avi.show();
        Map<String, RequestBody> map = new HashMap<>();

        if (!ConvertedBitmap.equals("")) {
            File file = new File(ConvertedBitmap);
            RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
            map.put("do_signature\"; filename=\"" + file.getName() + "\"", requestBody);
        }

        RequestBody ao_id = RequestBody.create(MediaType.parse("text/plain"), arraylist.get(0).getAoId());
        RequestBody ord_id = RequestBody.create(MediaType.parse("text/plain"), arraylist.get(0).getOrderDetails().get(0).getMs_ord_id());
        RequestBody user_id = RequestBody.create(MediaType.parse("text/plain"), sessionManager.getKeyId());
        RequestBody truck_id = RequestBody.create(MediaType.parse("text/plain"), sessionManager.getKeyTruckid());
        RequestBody date = RequestBody.create(MediaType.parse("text/plain"), arraylist.get(0).getAoDate());
        RequestBody weight = RequestBody.create(MediaType.parse("text/plain"), edt_weight.getText().toString());
        RequestBody pname = RequestBody.create(MediaType.parse("text/plain"), edt_printname.getText().toString());
        RequestBody jobttl = RequestBody.create(MediaType.parse("text/plain"), edt_job.getText().toString());

        map.put("do_ao_id", ao_id);
        map.put("do_ord_id", ord_id);
        map.put("do_trusr_id", user_id);
        map.put("do_truck_id", truck_id);
        map.put("do_date", date);
        map.put("do_weight", weight);
        map.put("do_print_name", pname);
        map.put("do_job_title", jobttl);

        Observable<OrderDetailsModel> responseObservable = apiservice.truck_addOrderDetails(map);

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
                .subscribe(new Observer<OrderDetailsModel>() {
                    @Override
                    public void onCompleted() {
                        avi.hide();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("error", "" + e.getMessage());
                    }

                    @Override
                    public void onNext(OrderDetailsModel model) {
                        avi.hide();
                        statusCode = model.getStatusCode();
                        if (statusCode == 200) {

                            Utility.displayToast(getApplicationContext(), model.getMessage());
                            ll_fillup.setVisibility(View.GONE);
                            btn_pdf1.setVisibility(View.VISIBLE);
                            btn_pdf1.setOnClickListener(view1 -> {
                                String pdf_url = model.getData().get(0).getDo_order_pdf_link();
                                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(ApiConstants.PDF_URL + Uri.parse(pdf_url)));
                                startActivity(browserIntent);
                            });
                        } else {
                            Utility.displayToast(getApplicationContext(), model.getMessage());
                        }
                    }
                });
    }

    public void dialog_action() {

        mContent = (LinearLayout) dialog.findViewById(R.id.linearLayout);
        mSignature = new signature(getApplicationContext(), null);
        mSignature.setBackgroundColor(Color.WHITE);
        // Dynamically generating Layout through java code
        mContent.addView(mSignature, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        mClear = (Button) dialog.findViewById(R.id.clear);
        mGetSign = (Button) dialog.findViewById(R.id.getsign);
        mGetSign.setEnabled(false);
        mCancel = (Button) dialog.findViewById(R.id.cancel);
        view = mContent;

        mClear.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Log.v("log_tag", "Panel Cleared");

                mSignature.clear();
                bitmap = null;
                img_pic.setImageDrawable(null);
            }
        });

        mGetSign.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                // Creating Separate Directory for saving Generated Images
                String DIRECTORY = Environment.getExternalStorageDirectory().getPath() + "/DCIM/CREAMLINE/";
                String pic_name = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
                String StoredPath = DIRECTORY + pic_name + ".png";
                //save to static string
                ConvertedBitmap = StoredPath;
                Log.e("TAG", "ConvertedBitmap: " + ConvertedBitmap);

                // Method to create Directory, if the Directory doesn't exists
                File file = new File(DIRECTORY);
                if (!file.exists()) {
                    file.mkdir();
                    Toast.makeText(getApplicationContext(), "Folder created", Toast.LENGTH_SHORT).show();
                    /*Snackbar snackbar = Snackbar.make(layout, "Folder created successfully!", Snackbar.LENGTH_LONG);
                    snackbar.show();*/

                }

                Log.v("log_tag", "Panel Saved");
                view.setDrawingCacheEnabled(true);

                mSignature.save(view, StoredPath);
                dialog.dismiss();


                if (img_pic.equals("")) {

                    builder.setTitle("Reminder!");
                    builder.setMessage("Please make sure all required fields are not empty. Before getting the driver's Signature");
                    //builder.setIcon(R.drawable.ic_priority_high_black_24dp);
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    builder.show();

                } else {
                    Toast.makeText(OrderDisplayActivity.this, "Signature save ", Toast.LENGTH_SHORT).show();
                    //selectImage1();
                    File imgFile = new File(ConvertedBitmap);

                    if (imgFile.exists()) {

                        bitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

                        ImageView myImage = (ImageView) findViewById(R.id.img_pic);

                        myImage.setImageBitmap(bitmap);
                        Log.e("TAG", "signature: " + bitmap);
                    }
                }
            }
        });

        mCancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.v("log_tag", "Panel Canceled");
                if (img_pic.equals(null)) {
                    mSignature.clear();
                    dialog.dismiss();
                } else {
                    dialog.dismiss();
                }
            }
        });
        dialog.show();
    }

    static Canvas canvas;

    public class signature extends View {

        private static final float STROKE_WIDTH = 5f;
        private static final float HALF_STROKE_WIDTH = STROKE_WIDTH / 2;
        private Paint paint = new Paint();
        private Path path = new Path();


        private float lastTouchX;
        private float lastTouchY;
        private final RectF dirtyRect = new RectF();

        public signature(Context context, AttributeSet attrs) {
            super(context, attrs);
            paint.setAntiAlias(true);
            paint.setColor(Color.BLACK);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeJoin(Paint.Join.ROUND);
            paint.setStrokeWidth(STROKE_WIDTH);
        }

        @SuppressLint("WrongThread")
        public void save(View v, String StoredPath) {
            Log.v("log_tag", "Width: " + v.getWidth());
            Log.v("log_tag", "Height: " + v.getHeight());
            if (bitmap == null) {
                bitmap = Bitmap.createBitmap(mContent.getWidth(), mContent.getHeight(), Bitmap.Config.RGB_565);
            }
            canvas = new Canvas(bitmap);
            try {
                // Output the file
                FileOutputStream mFileOutStream = new FileOutputStream(StoredPath);
                v.draw(canvas);

                // Convert the output file to Image such as .png
                bitmap.compress(Bitmap.CompressFormat.PNG, 90, mFileOutStream);
                mFileOutStream.flush();
                mFileOutStream.close();

            } catch (Exception e) {
                Log.v("log_tag", e.toString());
            }

        }

        public void clear() {
            path.reset();
            invalidate();
            mContent.removeAllViews();
            mContent = (LinearLayout) dialog.findViewById(R.id.linearLayout);
            mSignature = new signature(getApplicationContext(), null);
            mSignature.setBackgroundColor(Color.WHITE);
            // Dynamically generating Layout through java code
            mContent.addView(mSignature, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            canvas.drawPath(path, paint);
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            float eventX = event.getX();
            float eventY = event.getY();
            mGetSign.setEnabled(true);

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    path.moveTo(eventX, eventY);
                    lastTouchX = eventX;
                    lastTouchY = eventY;
                    return true;

                case MotionEvent.ACTION_MOVE:

                case MotionEvent.ACTION_UP:

                    resetDirtyRect(eventX, eventY);
                    int historySize = event.getHistorySize();
                    for (int i = 0; i < historySize; i++) {
                        float historicalX = event.getHistoricalX(i);
                        float historicalY = event.getHistoricalY(i);
                        expandDirtyRect(historicalX, historicalY);
                        path.lineTo(historicalX, historicalY);
                    }
                    path.lineTo(eventX, eventY);
                    break;

                default:
                    //debug("Ignored touch event: " + event.toString());
                    return false;
            }

            invalidate((int) (dirtyRect.left - HALF_STROKE_WIDTH),
                    (int) (dirtyRect.top - HALF_STROKE_WIDTH),
                    (int) (dirtyRect.right + HALF_STROKE_WIDTH),
                    (int) (dirtyRect.bottom + HALF_STROKE_WIDTH));

            lastTouchX = eventX;
            lastTouchY = eventY;

            return true;
        }

        private void expandDirtyRect(float historicalX, float historicalY) {
            if (historicalX < dirtyRect.left) {
                dirtyRect.left = historicalX;
            } else if (historicalX > dirtyRect.right) {
                dirtyRect.right = historicalX;
            }

            if (historicalY < dirtyRect.top) {
                dirtyRect.top = historicalY;
            } else if (historicalY > dirtyRect.bottom) {
                dirtyRect.bottom = historicalY;
            }
        }

        private void resetDirtyRect(float eventX, float eventY) {
            dirtyRect.left = Math.min(lastTouchX, eventX);
            dirtyRect.right = Math.max(lastTouchX, eventX);
            dirtyRect.top = Math.min(lastTouchY, eventY);
            dirtyRect.bottom = Math.max(lastTouchY, eventY);
        }
    }

    private void callStatusManagementApi() {

        avi.show();
        Observable<OrderDetailsModel> responseObservable = apiservice.truck_OrderStatusmanagement(
                arraylist.get(0).getAoId(),
                arraylist.get(0).getAoOrdId(),
                arraylist.get(0).getAoSiteId(),
                sessionManager.getKeyId(),
                sessionManager.getKeyTruckid(),
                //arraylist.get(0).getAoTruckId(),
                truck_driver_status,
                arraylist.get(0).getAoPickupLocation()
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
                .subscribe(new Observer<OrderDetailsModel>() {
                    @Override
                    public void onCompleted() {
                        avi.hide();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("error", "" + e.getMessage());
                    }

                    @Override
                    public void onNext(OrderDetailsModel model) {
                        avi.hide();
                        statusCode = model.getStatusCode();
                        if (statusCode == 200) {
                            Utility.displayToast(getApplicationContext(), model.getMessage());
                        } else {
                            Utility.displayToast(getApplicationContext(), model.getMessage());
                        }
                    }
                });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (Utility.getAppcon().getSession().screen_name.equals("AssignOrder")) {
            callHandler();
        } else if (Utility.getAppcon().getSession().screen_name.equals("CompletedOrder")) {
            callHandler3();
        } else if (Utility.getAppcon().getSession().screen_name.equals("All_AssignOrder")) {
            callHandler2();
        }
    }

    private void callHandler() {
        if (AssignOrderActivity.handler != null) {
            Message message = Message.obtain();
            message.what = 100;
            if (AssignOrderActivity.handler != null) {
                AssignOrderActivity.handler.sendMessage(message);
            }
        }
    }

    private void callHandler2() {
        if (SignOrderActivity.handler != null) {
            Message message = Message.obtain();
            message.what = 100;
            if (SignOrderActivity.handler != null) {
                SignOrderActivity.handler.sendMessage(message);
            }
        }
    }

    private void callHandler3() {
        if (CompletedOrderActivity.handler != null) {
            Message message = Message.obtain();
            message.what = 100;
            if (CompletedOrderActivity.handler != null) {
                CompletedOrderActivity.handler.sendMessage(message);
            }
        }
    }
}