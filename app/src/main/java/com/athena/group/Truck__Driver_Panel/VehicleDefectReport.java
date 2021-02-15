package com.athena.group.Truck__Driver_Panel;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
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
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.athena.group.API.ApiInterface;
import com.athena.group.API.ApiServiceCreator;
import com.athena.group.Model.LoginResponse;
import com.athena.group.R;
import com.athena.group.application.SessionManager;
import com.athena.group.application.Utility;
import com.wang.avi.AVLoadingIndicatorView;

import java.io.File;
import java.io.FileOutputStream;
import java.net.SocketTimeoutException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Observable;
import rx.Observer;
import rx.schedulers.Schedulers;

public class VehicleDefectReport extends AppCompatActivity {

    SessionManager sessionManager;
    ApiInterface apiservice;


    @BindView(R.id.toolbar_Title)
    TextView toolbar_Title;
    @BindView(R.id.back_icon)
    ImageView back_icon;
    @BindView(R.id.avi)
    AVLoadingIndicatorView avi;

    @BindView(R.id.txt_date)
    TextView txt_date;
    /*   @BindView(R.id.txt_date2)
       TextView txt_date2;
       @BindView(R.id.txt_date3)
       TextView txt_date3;*/
    @BindView(R.id.txt_time)
    TextView txt_time;
    /*@BindView(R.id.report_no)
    TextView report_no;*/
    @BindView(R.id.txt_driver_name)
    TextView txt_driver_name;
    @BindView(R.id.txt_vehicleno)
    TextView txt_vehicleno;

    @BindView(R.id.edt_odoreading)
    EditText edt_odoreading;
    @BindView(R.id.edt_name)
    EditText edt_name;
    @BindView(R.id.edt_defct_assesmnt_rect)
    EditText edt_defct_assesmnt_rect;
    @BindView(R.id.edt_rprt_defc_here)
    EditText edt_rprt_defc_here;
    @BindView(R.id.edt_defc_rprtto)
    /*EditText edt_defc_rprtto;
    @BindView(R.id.edt_nodefcfound)*/
            EditText edt_nodefcfound;

    @BindView(R.id.btn_next)
    Button btn_next;
    @BindView(R.id.img_picimage)
    ImageView img_picimage;
    @BindView(R.id.img_picimage1)
    ImageView img_picimage1;
    @BindView(R.id.img_pic)
    ImageView img_pic;
    @BindView(R.id.img_pic1)
    ImageView img_pic1;

    @BindView(R.id.check_abl)
    CheckBox check_abl;
    @BindView(R.id.check_air)
    CheckBox check_air;
    @BindView(R.id.check_biae)
    CheckBox check_biae;
    @BindView(R.id.check_bl)
    CheckBox check_bl;
    @BindView(R.id.check_bttrycon)
    CheckBox check_bttrycon;
    @BindView(R.id.check_cis)
    CheckBox check_cis;
    @BindView(R.id.check_ec)
    CheckBox check_ec;
    @BindView(R.id.check_cs)
    CheckBox check_cs;
    @BindView(R.id.check_eees)
    CheckBox check_eees;
    @BindView(R.id.check_fo)
    CheckBox check_fo;
    @BindView(R.id.check_horn)
    CheckBox check_horn;
    @BindView(R.id.check_isr)
    CheckBox check_isr;
    @BindView(R.id.check_lights)
    CheckBox check_lights;
    @BindView(R.id.check_wlm)
    CheckBox check_wlm;
    @BindView(R.id.check_wiper)
    CheckBox check_wiper;
    @BindView(R.id.check_tyres)
    CheckBox check_tyres;
    @BindView(R.id.check_steering)
    CheckBox check_steering;
    @BindView(R.id.check_ss)
    CheckBox check_ss;
    @BindView(R.id.check_scw)
    CheckBox check_scw;
    @BindView(R.id.check_rm)
    CheckBox check_rm;
    @BindView(R.id.check_rp)
    CheckBox check_rp;
    @BindView(R.id.check_mgv)
    CheckBox check_mgv;
    @BindView(R.id.check_checkbox)
    CheckBox check_checkbox;
    @BindView(R.id.check_slvh)
    CheckBox check_slvh;
    @BindView(R.id.check_washers)
    CheckBox check_washers;
    Button mClear, mGetSign, mCancel;

    int statusCode;
    String vehicle;
    private String fo, bettrycon, tyres, ss, sterring, slvh, mgv, abl, lights, rm, isr, wiper, washer;
    String horn, eees, air, bl, cs, ec, biae, scw, rp, cisb, warninglamps, checkfound, report_date;
    final Calendar myCalendar = Calendar.getInstance();
    private String vehicle_id;

    Dialog dialog;
    LinearLayout mContent;
    View view;
    signature mSignature;
    signaturesecond mSignaturesecond;
    Bitmap bitmap, bitmap1;
    String ConvertedBitmap = "", ConvertedBitmap1 = "";

    AlertDialog.Builder builder;

    LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_vehicle_defect_report);
        ButterKnife.bind(this);
        sessionManager = new SessionManager(VehicleDefectReport.this);
        apiservice = ApiServiceCreator.createService("latest");
        toolbar_Title.setText(getString(R.string.vehicledefectreport));

        back_icon.setOnClickListener(view -> finish());
        initViews();
    }

    private void initViews() {


        vehicle = getIntent().getStringExtra("vehicle");
        vehicle_id = getIntent().getStringExtra("vehicle_id");
        txt_driver_name.setText(sessionManager.getKeyName() + " " + sessionManager.getKeyLname());
        txt_vehicleno.setText(vehicle);
        txt_date.setText(Utility.date);
        report_date = Utility.date1;
        Log.e("tag", "report_date: " + report_date);

       /* txt_date2.setText(Utility.date);
        txt_date3.setText(Utility.date);*/
        String time = new SimpleDateFormat("hh:mm a").format(Calendar.getInstance().getTime());

        txt_time.setText(time);

        DatePickerDialog.OnDateSetListener datee1 = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
        };

        txt_date.setOnClickListener(view ->
        {
            new DatePickerDialog(VehicleDefectReport.this, datee1, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)).show();

        });

        edt_rprt_defc_here.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent event) {
                // TODO Auto-generated method stub
                if (view.getId() == R.id.edt_rprt_defc_here) {
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

        edt_defct_assesmnt_rect.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent event) {
                // TODO Auto-generated method stub
                if (view.getId() == R.id.edt_defct_assesmnt_rect) {
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

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (edt_odoreading.getText().toString().equals("")) {
                    Utility.displayToast(getApplicationContext(), "Enter Odometer Reading");
                } else if (ConvertedBitmap.equals("")) {
                    Utility.displayToast(getApplicationContext(), "Required Engineer Signature !");
                } else {
                    callReportApi();

                }
            }
        });

        dialog = new Dialog(VehicleDefectReport.this);
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

        img_picimage1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ConvertedBitmap1 = "";
                dialog_action2();
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
                    Toast.makeText(VehicleDefectReport.this, "Signature save ", Toast.LENGTH_SHORT).show();
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

    public void dialog_action2() {

        mContent = (LinearLayout) dialog.findViewById(R.id.linearLayout);
        mSignaturesecond = new signaturesecond(getApplicationContext(), null);
        mSignaturesecond.setBackgroundColor(Color.WHITE);
        // Dynamically generating Layout through java code
        mContent.addView(mSignaturesecond, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        mClear = (Button) dialog.findViewById(R.id.clear);
        mGetSign = (Button) dialog.findViewById(R.id.getsign);
        mGetSign.setEnabled(false);
        mCancel = (Button) dialog.findViewById(R.id.cancel);
        view = mContent;

        mSignature.clear();

        mClear.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Log.v("log_tag", "Panel Cleared");

                mSignaturesecond.clear();
                bitmap1 = null;
                img_pic1.setImageDrawable(null);
            }
        });

        mGetSign.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {


                // Creating Separate Directory for saving Generated Images
                String DIRECTORY = Environment.getExternalStorageDirectory().getPath() + "/DCIM/CREAMLINE/";
                String pic_name = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
                String StoredPath = DIRECTORY + pic_name + ".png";
                //save to static string
                ConvertedBitmap1 = StoredPath;
                Log.e("TAG", "ConvertedBitmap1: " + ConvertedBitmap1);

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

                mSignaturesecond.save(view, StoredPath);
                dialog.dismiss();


                if (img_pic1.equals("")) {

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
                    Toast.makeText(VehicleDefectReport.this, "Signature save ", Toast.LENGTH_SHORT).show();
                    //selectImage1();
                    File imgFile = new File(ConvertedBitmap1);

                    if (imgFile.exists()) {

                        bitmap1 = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

                        ImageView myImage = (ImageView) findViewById(R.id.img_pic1);

                        myImage.setImageBitmap(bitmap1);
                        Log.e("TAG", "signature: " + bitmap1);


                    }
                }


            }
        });

        mCancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.v("log_tag", "Panel Canceled");
                if (img_pic1.equals(null)) {
                    mSignaturesecond.clear();
                    dialog.dismiss();
                } else {
                    dialog.dismiss();

                }
            }
        });
        dialog.show();
    }


    static Canvas canvas, canvas2;


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

    public class signaturesecond extends View {

        private static final float STROKE_WIDTH = 5f;
        private static final float HALF_STROKE_WIDTH = STROKE_WIDTH / 2;
        private Paint paint = new Paint();
        private Path path = new Path();


        private float lastTouchX;
        private float lastTouchY;
        private final RectF dirtyRect = new RectF();

        public signaturesecond(Context context, AttributeSet attrs) {
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
            if (bitmap1 == null) {
                bitmap1 = Bitmap.createBitmap(mContent.getWidth(), mContent.getHeight(), Bitmap.Config.RGB_565);
            }
            canvas2 = new Canvas(bitmap1);
            try {
                // Output the file
                FileOutputStream mFileOutStream = new FileOutputStream(StoredPath);
                v.draw(canvas2);

                // Convert the output file to Image such as .png
                bitmap1.compress(Bitmap.CompressFormat.PNG, 90, mFileOutStream);
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
            mSignaturesecond = new signaturesecond(getApplicationContext(), null);
            mSignaturesecond.setBackgroundColor(Color.WHITE);
            // Dynamically generating Layout through java code
            mContent.addView(mSignaturesecond, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
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

    private void updateLabel() {
        String myFormat = "dd-MM-yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        txt_date.setText(sdf.format(myCalendar.getTime()));

        String myFormat1 = "yyyy-MM-dd";
        SimpleDateFormat sdf1 = new SimpleDateFormat(myFormat1, Locale.US);
        report_date = sdf1.format(myCalendar.getTime());   //pass in api
    }

    private void callReportApi() {


        if (check_fo.isChecked()) {
            fo = "Yes";
        } else {
            fo = "NO";
        }
        if (check_bttrycon.isChecked()) {
            bettrycon = "Yes";
        } else {
            bettrycon = "NO";
        }
        if (check_tyres.isChecked()) {
            tyres = "Yes";
        } else {
            tyres = "NO";
        }
        if (check_ss.isChecked()) {
            ss = "Yes";
        } else {
            ss = "NO";
        }
        if (check_steering.isChecked()) {
            sterring = "Yes";
        } else {
            sterring = "NO";
        }
        if (check_slvh.isChecked()) {
            slvh = "Yes";
        } else {
            slvh = "NO";
        }
        if (check_mgv.isChecked()) {
            mgv = "Yes";
        } else {
            mgv = "NO";
        }
        if (check_abl.isChecked()) {
            abl = "Yes";
        } else {
            abl = "NO";
        }
        if (check_lights.isChecked()) {
            lights = "Yes";
        } else {
            lights = "NO";
        }
        if (check_rm.isChecked()) {
            rm = "Yes";
        } else {
            rm = "NO";
        }
        if (check_isr.isChecked()) {
            isr = "Yes";
        } else {
            isr = "NO";
        }
        if (check_wiper.isChecked()) {
            wiper = "Yes";
        } else {
            wiper = "NO";
        }
        if (check_washers.isChecked()) {
            washer = "Yes";
        } else {
            washer = "NO";
        }
        if (check_horn.isChecked()) {
            horn = "Yes";
        } else {
            horn = "NO";
        }
        if (check_eees.isChecked()) {
            eees = "Yes";
        } else {
            eees = "NO";
        }
        if (check_air.isChecked()) {
            air = "Yes";
        } else {
            air = "NO";
        }
        if (check_bl.isChecked()) {
            bl = "Yes";
        } else {
            bl = "NO";
        }
        if (check_cs.isChecked()) {
            cs = "Yes";
        } else {
            cs = "NO";
        }
        if (check_ec.isChecked()) {
            ec = "Yes";
        } else {
            ec = "NO";
        }
        if (check_biae.isChecked()) {
            biae = "Yes";
        } else {
            biae = "NO";
        }
        if (check_scw.isChecked()) {
            scw = "Yes";
        } else {
            scw = "NO";
        }
        if (check_rp.isChecked()) {
            rp = "Yes";
        } else {
            rp = "NO";
        }
        if (check_cis.isChecked()) {
            cisb = "Yes";
        } else {
            cisb = "NO";
        }
        if (check_wlm.isChecked()) {
            warninglamps = "Yes";
        } else {
            warninglamps = "NO";
        }
        if (check_checkbox.isChecked()) {
            checkfound = "Yes";
        } else {
            checkfound = "NO";
        }
        Map<String, RequestBody> map = new HashMap<>();

     /*   Compressor compressedImageFile = new Compressor(this);
        compressedImageFile.setQuality(60);*/
        if (!ConvertedBitmap.equals("")) {
            File file = new File(ConvertedBitmap);
            //File compressfile = null;
            //compressfile = compressedImageFile.compressToFile(file);
            RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
            map.put("dd_drivers_signature\"; filename=\"" + file.getName() + "\"", requestBody);
        }
        if (!ConvertedBitmap1.equals("")) {
            File file = new File(ConvertedBitmap1);
            RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
            map.put("dd_signature\"; filename=\"" + file.getName() + "\"", requestBody);
        }
        try {
            RequestBody userid = RequestBody.create(MediaType.parse("text/plain"), sessionManager.getKeyId());
            RequestBody truckid = RequestBody.create(MediaType.parse("text/plain"), vehicle_id);
            RequestBody reportdate = RequestBody.create(MediaType.parse("text/plain"), report_date);
            RequestBody reportno = RequestBody.create(MediaType.parse("text/plain"), "123435");
            RequestBody drvrname = RequestBody.create(MediaType.parse("text/plain"), txt_driver_name.getText().toString());
            RequestBody vehicleno = RequestBody.create(MediaType.parse("text/plain"), vehicle);
            RequestBody odomtrreadng = RequestBody.create(MediaType.parse("text/plain"), edt_odoreading.getText().toString());
            RequestBody time = RequestBody.create(MediaType.parse("text/plain"), txt_time.getText().toString());
            RequestBody fol = RequestBody.create(MediaType.parse("text/plain"), fo);
            RequestBody light = RequestBody.create(MediaType.parse("text/plain"), lights);
            RequestBody breklins = RequestBody.create(MediaType.parse("text/plain"), bl);
            RequestBody battrysecurity = RequestBody.create(MediaType.parse("text/plain"), bettrycon);
            RequestBody reflectmaker = RequestBody.create(MediaType.parse("text/plain"), rm);
            RequestBody coplingsecurity = RequestBody.create(MediaType.parse("text/plain"), cs);
            RequestBody tyrswhhelfixing = RequestBody.create(MediaType.parse("text/plain"), tyres);
            RequestBody indicatorsiderep = RequestBody.create(MediaType.parse("text/plain"), isr);
            RequestBody elecconn = RequestBody.create(MediaType.parse("text/plain"), ec);
            RequestBody spray = RequestBody.create(MediaType.parse("text/plain"), ss);
            RequestBody wpr = RequestBody.create(MediaType.parse("text/plain"), wiper);
            RequestBody brakiae = RequestBody.create(MediaType.parse("text/plain"), biae);
            RequestBody steering = RequestBody.create(MediaType.parse("text/plain"), sterring);
            RequestBody wshr = RequestBody.create(MediaType.parse("text/plain"), washer);
            RequestBody scofbw = RequestBody.create(MediaType.parse("text/plain"), scw);
            RequestBody secloadvehicle = RequestBody.create(MediaType.parse("text/plain"), slvh);
            RequestBody hrn = RequestBody.create(MediaType.parse("text/plain"), horn);
            RequestBody regiplat = RequestBody.create(MediaType.parse("text/plain"), rp);
            RequestBody mrrglssvisi = RequestBody.create(MediaType.parse("text/plain"), mgv);
            RequestBody ece_engin_smok = RequestBody.create(MediaType.parse("text/plain"), eees);
            RequestBody cab_int_setbelts = RequestBody.create(MediaType.parse("text/plain"), cisb);
            RequestBody air_build_lks = RequestBody.create(MediaType.parse("text/plain"), abl);
            RequestBody adblue_requird = RequestBody.create(MediaType.parse("text/plain"), air);
            RequestBody warning_lamps = RequestBody.create(MediaType.parse("text/plain"), warninglamps);
            RequestBody rprtnofound = RequestBody.create(MediaType.parse("text/plain"), checkfound);
            RequestBody rprt_defc_here = RequestBody.create(MediaType.parse("text/plain"), edt_rprt_defc_here.getText().toString());
            RequestBody defc_assmnt_recti = RequestBody.create(MediaType.parse("text/plain"), edt_defct_assesmnt_rect.getText().toString());
            //RequestBody defc_rprtto = RequestBody.create(MediaType.parse("text/plain"), edt_defc_rprtto.getText().toString());
            RequestBody defc_rprtto = RequestBody.create(MediaType.parse("text/plain"), edt_nodefcfound.getText().toString());
            RequestBody defc_rcti_by = RequestBody.create(MediaType.parse("text/plain"), edt_name.getText().toString());


            map.put("dd_trusr_id", userid);
            map.put("dd_truck_id", truckid);
            map.put("dd_assign_date", reportdate);
            map.put("dd_report_no", reportno);
            map.put("dd_drivers_name", drvrname);
            map.put("dd_vehicle_no", vehicleno);
            map.put("dd_odometer_reading", odomtrreadng);
            map.put("dd_time", time);
            map.put("dd_fual_oil_leaks", fol);
            map.put("dd_lights", light);
            map.put("dd_brake_lines", breklins);
            map.put("dd_battery_security", battrysecurity);
            map.put("dd_reflectors_markers", reflectmaker);
            map.put("dd_coupling_security", coplingsecurity);
            map.put("dd_tyres_wheel_wheel_fixing", tyrswhhelfixing);
            map.put("dd_indicators_side_repeaters", indicatorsiderep);
            map.put("dd_electrical_connections", elecconn);
            map.put("dd_spray_suppression", spray);
            map.put("dd_wipers", wpr);
            map.put("dd_brakes_inc_abs_ebs", brakiae);
            map.put("dd_steering", steering);
            map.put("dd_washers", wshr);
            map.put("dd_security_condition_of_body_wings", scofbw);
            map.put("dd_security_of_load_vehicle_height", secloadvehicle);
            map.put("dd_horn", hrn);
            map.put("dd_registration_plates", regiplat);
            map.put("dd_mirrors_glass_visibility", mrrglssvisi);
            map.put("dd_excessive_engine_exhaust_smoke", ece_engin_smok);
            map.put("dd_cab_interior_seat_belts", cab_int_setbelts);
            map.put("dd_air_build_up_leaks", air_build_lks);
            map.put("dd_adblue_if_required", adblue_requird);
            map.put("dd_warning_lamps_mil", warning_lamps);
            map.put("dd_report_defects_here", rprt_defc_here);
            map.put("dd_defect_assessment_and_rectification", defc_assmnt_recti);
            map.put("dd_defect_reported_to", defc_rprtto);
            map.put("dd_write_nill_here", rprtnofound);
            map.put("dd_defects_rectified_by", defc_rcti_by);

            avi.show();
            Observable<LoginResponse> responseObservable = apiservice.add_driverDailyReport(map);

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
                    .subscribe(new Observer<LoginResponse>() {
                        @Override
                        public void onCompleted() {
                            avi.hide();
                        }

                        @Override
                        public void onError(Throwable e) {
                             Log.e("error", "" + e.getMessage());
                        }

                        @Override
                        public void onNext(LoginResponse model) {
                            statusCode = model.getStatusCode();
                            if (statusCode == 200) {
                                btn_next.setEnabled(false);
                                Utility.displayToast(VehicleDefectReport.this, model.getMessage());
                                Intent intent = new Intent(getApplicationContext(), TodayTripActivity.class);
                                startActivity(intent);

                                //onBackPressed();
                            } else if (statusCode == 201) {
                                Utility.displayToast(VehicleDefectReport.this, model.getMessage());
                            }
                        }
                    });

        } catch (Exception e) {
            Log.e("Exception is", e.getMessage());
            e.printStackTrace();
        }


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        callHandler();
    }

    private void callHandler() {
        if (TodayTripActivity.handler != null) {
            Message message = Message.obtain();
            message.what = 100;
            if (TodayTripActivity.handler != null) {
                TodayTripActivity.handler.sendMessage(message);
            }
        }
    }


}
