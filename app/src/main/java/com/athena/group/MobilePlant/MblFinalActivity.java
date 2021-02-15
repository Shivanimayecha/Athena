package com.athena.group.MobilePlant;

import androidx.appcompat.app.AppCompatActivity;

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
import android.os.Bundle;
import android.os.Environment;
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
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.athena.group.API.ApiInterface;
import com.athena.group.API.ApiServiceCreator;
import com.athena.group.MainActivity;
import com.athena.group.Model.Model;
import com.athena.group.R;
import com.athena.group.application.SessionManager;
import com.athena.group.application.Utility;
import com.wang.avi.AVLoadingIndicatorView;

import java.io.File;
import java.io.FileOutputStream;
import java.net.SocketTimeoutException;
import java.text.SimpleDateFormat;
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

public class MblFinalActivity extends AppCompatActivity {

    @BindView(R.id.toolbar_Title)
    TextView toolbar_Title;
    @BindView(R.id.back_icon)
    ImageView back_icon;
    @BindView(R.id.avi)
    AVLoadingIndicatorView avi;
    @BindView(R.id.btn_submit)
    Button btn_submit;
    @BindView(R.id.img_pic)
    ImageView img_pic;
    @BindView(R.id.img_picimage)
    ImageView img_picimage;

    @BindView(R.id.edt_commnt)
    EditText edt_commnt;
    @BindView(R.id.edt_commnt1)
    EditText edt_commnt1;

    @BindView(R.id.rd1)
    RadioButton rd1;
    @BindView(R.id.rd2)
    RadioButton rd2;
    @BindView(R.id.rd3)
    RadioButton rd3;
    @BindView(R.id.rd4)
    RadioButton rd4;

    ApiInterface apiservice;
    int statusCode;
    SessionManager sessionManager;
    Dialog dialog;
    LinearLayout mContent;
    View view;
    signature mSignature;
    Bitmap bitmap;
    AlertDialog.Builder builder;
    Button mClear, mGetSign, mCancel;
    String ConvertedBitmap = "", firstrd = "", secndrd = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_mbl_final);
        ButterKnife.bind(this);
        sessionManager = new SessionManager(MblFinalActivity.this);
        apiservice = ApiServiceCreator.createService("latest");
        toolbar_Title.setText(getString(R.string.mblplntchecklist));
        back_icon.setOnClickListener(view -> finish());
        initView();
    }

    private void initView() {

        dialog = new Dialog(MblFinalActivity.this);
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

        rd1.setOnClickListener(view1 -> firstrd = "Yes");
        rd2.setOnClickListener(view1 -> firstrd = "No");
        rd3.setOnClickListener(view1 -> secndrd = "Yes");
        rd4.setOnClickListener(view1 -> secndrd = "No");

        btn_submit.setOnClickListener(view1 -> {

            if (firstrd.equals("")) {
                Utility.displayToast(MblFinalActivity.this, "Please Select Yes/No");
            } else if (secndrd.equals("")) {
                Utility.displayToast(MblFinalActivity.this, "Please Select Yes/No");
            } else if (ConvertedBitmap.equals("")) {
                Utility.displayToast(MblFinalActivity.this, "Signature Required !");
            } else {
                callSubmitApi();
            }
        });
    }

    private void callSubmitApi() {

        avi.show();
        Map<String, RequestBody> map = new HashMap<>();

        if (!ConvertedBitmap.equals("")) {
            File file = new File(ConvertedBitmap);
            RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
            map.put("ans_secplant_ins_sign_off\"; filename=\"" + file.getName() + "\"", requestBody);
        }

        RequestBody user_id = RequestBody.create(MediaType.parse("text/plain"), sessionManager.getKeyId());
        RequestBody cmmnt1 = RequestBody.create(MediaType.parse("text/plain"), edt_commnt.getText().toString());
        RequestBody lststep = RequestBody.create(MediaType.parse("text/plain"), Utility.getAppcon().getSession().mbl_last_step_val);
        RequestBody plntstts = RequestBody.create(MediaType.parse("text/plain"), firstrd);
        RequestBody cmmnt2 = RequestBody.create(MediaType.parse("text/plain"), edt_commnt1.getText().toString());
        RequestBody plntstts2 = RequestBody.create(MediaType.parse("text/plain"), secndrd);


        map.put("ans_user_id", user_id);
        map.put("ans_plant_comment", cmmnt1);
        map.put("last_step", lststep);
        map.put("ans_plant_status", plntstts);
        map.put("ans_secplant_comment", cmmnt2);
        map.put("ans_secplant_status", plntstts2);

        Observable<Model> responseObservable = apiservice.insert_mblLastStep(map);

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
                .subscribe(new Observer<Model>() {
                    @Override
                    public void onCompleted() {
                       avi.hide();
                        //getAttributename1(p_id);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("error", "" + e.getMessage());
                    }

                    @Override
                    public void onNext(Model model) {
                        avi.hide();

                        statusCode = model.getStatusCode();
                        if (statusCode == 200) {

                            Utility.displayToast(getApplicationContext(), model.getMessage());
                            Intent intent = new Intent(MblFinalActivity.this, MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
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
                    Toast.makeText(MblFinalActivity.this, "Signature save ", Toast.LENGTH_SHORT).show();
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

}
