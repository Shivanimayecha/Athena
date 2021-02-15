package com.athena.group.Fragment.HsInspection;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
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
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.athena.group.API.ApiInterface;
import com.athena.group.API.ApiServiceCreator;
import com.athena.group.FileUtils;
import com.athena.group.MainActivity;
import com.athena.group.Model.CommanModel;
import com.athena.group.Model.HSQuestionModel;
import com.athena.group.Model.Model;
import com.athena.group.R;
import com.athena.group.adapter.MyAdapter;
import com.athena.group.application.SessionManager;
import com.athena.group.application.Utility;
import com.wang.avi.AVLoadingIndicatorView;

import java.io.File;
import java.io.FileOutputStream;
import java.net.SocketTimeoutException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.annotations.NonNull;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Observable;
import rx.Observer;
import rx.schedulers.Schedulers;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class CompletionFragment extends Fragment {


    private SessionManager sessionManager;
    private ApiInterface apiservice;

    /* @BindView(R.id.avi)
     AVLoadingIndicatorView avi;*/
    /*@BindView(R.id.recyerview)
    RecyclerView recyclerview;
    @BindView(R.id.btn_next)
    LinearLayout btn_next;*/
    @BindView(R.id.btn_submit)
    Button btn_submit;
    @BindView(R.id.img_picimage)
    ImageView img_picimage;
    @BindView(R.id.img_picimage1)
    ImageView img_picimage1;
    @BindView(R.id.img_pic)
    ImageView img_pic;
    @BindView(R.id.img_pic1)
    ImageView img_pic1;
    @BindView(R.id.edt_summary)
    EditText edt_summary;
    @BindView(R.id.edt_nmofinspctr)
    EditText edt_nmofinspctr;
    @BindView(R.id.txt_date)
    TextView txt_date;
    @BindView(R.id.txt_time)
    TextView txt_time;
    @BindView(R.id.txt_sprvsrname)
    TextView txt_sprvsrname;
    @BindView(R.id.avi)
    AVLoadingIndicatorView avi;
    @BindView(R.id.toolbar_Title)
    TextView toolbar_Title;
    @BindView(R.id.back_icon)
    ImageView back_icon;
    @BindView(R.id.listView)
    public GridView listView;
    @BindView(R.id.img_picibutton)
    LinearLayout img_picibutton;

    List<MultipartBody.Part> parts;
    private ArrayList<Uri> imagesPathList;
    private static final int REQUEST_CODE = 6384;

    Button mClear, mGetSign, mCancel;
    ArrayList<HSQuestionModel.Data> arrayList;
    List<CommanModel> commanModelslist;
    Dialog dialog;
    LinearLayout mContent;
    View view;
    signature mSignature;
    signaturesecond mSignaturesecond;
    Bitmap bitmap, bitmap1;
    List<HSQuestionModel> hsQuestionModels;
    String ConvertedBitmap = "", ConvertedBitmap1 = "";

    AlertDialog.Builder builder;
    File imgFile, imgFile1;
    String txtdate = "";
    final Calendar myCalendar = Calendar.getInstance();
    private int statusCode;
    String name = "";

    public CompletionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view1 = inflater.inflate(R.layout.fragment_hs_summary, container, false);
        ButterKnife.bind(this, view1);
        sessionManager = new SessionManager(getActivity());
        apiservice = ApiServiceCreator.createService("latest");

        arrayList = Utility.getAppcon().getSession().arrayListQuestion;
        toolbar_Title.setText("Completion");

        imagesPathList = new ArrayList<>();
        handleBackPress(view1);

        initView();


        return view1;
    }

    private void initView() {

        edt_summary.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent event) {
                // TODO Auto-generated method stub
                if (view.getId() == R.id.edt_summary) {
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

        txt_time.setText(Utility.time);
        txt_date.setText(Utility.date);
        txtdate = Utility.date + " " + Utility.time;
        txt_sprvsrname.setText(sessionManager.getKeyName() + " " + sessionManager.getKeyLname());
        name = sessionManager.getKeyName() + " " + sessionManager.getKeyLname();

        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

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
        txt_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(getActivity(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        back_icon.setOnClickListener(view -> getActivity().onBackPressed());

        dialog = new Dialog(getActivity());
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
        img_picibutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showChooser();
            }
        });

        btn_submit.setOnClickListener(view -> {

            if (edt_summary.getText().toString().equals("")) {
                Utility.displayToast(getActivity(), "Please Enter Summary !");
            } else if (ConvertedBitmap.equals("")) {
                Utility.displayToast(getActivity(), "Please Upload Signature !");
            } else if (ConvertedBitmap1.equals("")) {
                Utility.displayToast(getActivity(), "Please Upload Signature !");
            } else {
                callSubmitApi();
            }
        });
    }

    private void callSubmitApi() {

//        Map<String, RequestBody> map = new HashMap<>();
//
//        if (!ConvertedBitmap.equals("")) {
//            File file = new File(ConvertedBitmap);
//            RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
//            map.put("ans_ins_sign_off\"; filename=\"" + file.getName() + "\"", requestBody);
//        }
//        if (!ConvertedBitmap1.equals("")) {
//            File file = new File(ConvertedBitmap1);
//            RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
//            map.put("ans_supervisor_sign\"; filename=\"" + file.getName() + "\"", requestBody);
//        }
//        File file = new File(String.valueOf(imagesPathList));
//        RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
//        map.put("img_path[]\"; filename=\"" + file.getName() + "\"", requestBody);

        try {
            parts = new ArrayList<>();


            if (imagesPathList != null) {
                for (int i = 0; i < imagesPathList.size(); i++) {
                    parts.add(prepareFilePart("img_path[]", imagesPathList.get(i)));
                }
            }
            //pass it like this
            File file = new File(ConvertedBitmap);
            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            MultipartBody.Part inssign = MultipartBody.Part.createFormData("ans_ins_sign_off", file.getName(), requestFile);

            File file1 = new File(ConvertedBitmap1);
            RequestBody requestFile1 = RequestBody.create(MediaType.parse("multipart/form-data"), file1);
            MultipartBody.Part sprvssrsign = MultipartBody.Part.createFormData("ans_supervisor_sign", file1.getName(), requestFile1);

            //service.updateProfile(id, fullName, body, other);

//            File file = new File(ConvertedBitmap);
//            RequestBody requestBody1 = RequestBody.create(MediaType.parse("*/*"), file);
//            File file1 = new File(ConvertedBitmap1);
//            RequestBody requestBody2 = RequestBody.create(MediaType.parse("*/*"), file1);
            RequestBody userid = RequestBody.create(MediaType.parse("text/plain"), sessionManager.getKeyId());
            RequestBody laststep = RequestBody.create(MediaType.parse("text/plain"), Utility.getAppcon().getSession().hs_data_value);
            RequestBody sumofmang = RequestBody.create(MediaType.parse("text/plain"), edt_summary.getText().toString());
            RequestBody compltn = RequestBody.create(MediaType.parse("text/plain"), txtdate);
            RequestBody ispctrname = RequestBody.create(MediaType.parse("text/plain"), edt_nmofinspctr.getText().toString());
            RequestBody sprvsrname = RequestBody.create(MediaType.parse("text/plain"), name);
            RequestBody datetime = RequestBody.create(MediaType.parse("text/plain"), txtdate);


           /* map.put("ans_user_id", userid);
            map.put("ans_sumofmang", sumofmang);
            map.put("last_step", laststep);
            map.put("last_ins_name", ispctrname);
            map.put("last_ins_date", datetime);
            map.put("last_site_manager_name", sprvsrname);*/

            avi.show();
            Observable<Model> responseObservable = apiservice.insert_hsLastStep(
                    userid,
                    sumofmang,
                    laststep,
                    ispctrname,
                    datetime,
                    sprvsrname,
                    inssign,
                    sprvssrsign,
                    parts
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
                    .subscribe(new Observer<Model>() {
                        @Override
                        public void onCompleted() {

                           avi.hide();
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.e("error", "" + e.getMessage());
                        }

                        @Override
                        public void onNext(Model model) {
                            statusCode = model.getStatusCode();
                            if (statusCode == 200) {
                                Utility.displayToast(getActivity(), model.getMessage());
                                Intent intent = new Intent(getActivity(), MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                            } else if (statusCode == 201) {
                                Utility.displayToast(getActivity(), model.getMessage());
                            } else {
                                Utility.displayToast(getActivity(), model.getMessage());
                            }
                        }
                    });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @NonNull
    private MultipartBody.Part prepareFilePart(String partName, Uri fileUri) {
        Log.e("All File detail is ", String.valueOf(fileUri));
        File file = FileUtils.getFile(getActivity(), fileUri);
        RequestBody requestFile = RequestBody.create(MediaType.parse(Objects.requireNonNull(getActivity().getContentResolver().getType(fileUri))), file);
        return MultipartBody.Part.createFormData(partName, file.getName(), requestFile);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
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
                                MyAdapter mAdapter = new MyAdapter(getActivity(), imagesPathList);
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
                            final String path = FileUtils.getPath(getActivity(), uri);
                            Log.e("Single File Selected", path);

                            imagesPathList.add(uri);
                            Log.e("Path is ", path);
                            MyAdapter mAdapter = new MyAdapter(getActivity(), imagesPathList);
                            listView.setAdapter(mAdapter);


                        } catch (Exception e) {

                        }
                    }
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void updateLabel() {
        String myFormat = "dd-MM-yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        txt_date.setText(sdf.format(myCalendar.getTime()));
        txtdate = sdf.format(myCalendar.getTime());
    }

    public void dialog_action() {

        mContent = (LinearLayout) dialog.findViewById(R.id.linearLayout);
        mSignature = new signature(getActivity(), null);
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
                    Toast.makeText(getActivity(), "Folder created", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(getActivity(), "Signature save ", Toast.LENGTH_SHORT).show();
                    //selectImage1();
                    imgFile = new File(ConvertedBitmap);

                    if (imgFile.exists()) {

                        bitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

                        //ImageView myImage = (ImageView) findViewById(R.id.img_pic);

                        img_pic.setImageBitmap(bitmap);
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
        mSignaturesecond = new signaturesecond(getActivity(), null);
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
                    Toast.makeText(getActivity(), "Folder created", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(getActivity(), "Signature save ", Toast.LENGTH_SHORT).show();
                    //selectImage1();
                    File imgFile = new File(ConvertedBitmap1);

                    if (imgFile.exists()) {

                        bitmap1 = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                        img_pic1.setImageBitmap(bitmap1);
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
            mSignature = new signature(getActivity(), null);
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
            mSignaturesecond = new signaturesecond(getActivity(), null);
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

    private void handleBackPress(View view) {
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {

                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    return true;
                }
                return false;
            }
        });
    }


}
