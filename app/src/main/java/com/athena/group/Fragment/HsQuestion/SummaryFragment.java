package com.athena.group.Fragment.HsQuestion;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.ContentValues;
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
import android.provider.MediaStore;
import android.util.AttributeSet;
import android.util.Log;
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

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.athena.group.API.ApiInterface;
import com.athena.group.API.ApiServiceCreator;
import com.athena.group.FileUtils;
import com.athena.group.MainActivity;
import com.athena.group.Model.CommanModel;
import com.athena.group.Model.DemoModel;
import com.athena.group.Model.Example;
import com.athena.group.Model.HSQuestionModel;
import com.athena.group.Model.Model;
import com.athena.group.R;
import com.athena.group.adapter.MyAdapter;
import com.athena.group.application.SessionManager;
import com.athena.group.application.Utility;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;
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

import static android.app.Activity.RESULT_OK;
import static com.athena.group.adapter.QuestionAdapter.questiondataList;

/**
 * A simple {@link Fragment} subclass.
 */
public class SummaryFragment extends Fragment {
    Example e;
    Example.questiondata questiondata;
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
    @BindView(R.id.txt_date)
    TextView txt_date;
    @BindView(R.id.avi)
    AVLoadingIndicatorView avi;
    @BindView(R.id.toolbar_Title)
    TextView toolbar_Title;
    @BindView(R.id.back_icon)
    ImageView back_icon;

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
    private ArrayList<Uri> imagesPathList;
    List<MultipartBody.Part> parts;
    ///Fragment Return
    private static final int REQUEST_CODE = 6384;
    @BindView(R.id.listView)
    public GridView listView;
    @BindView(R.id.img_picibutton)
    LinearLayout img_picibutton;
    String fargment1 = "No";
    String fargment2 = "No";
    String fargment3 = "No";
    String fargment4 = "No";
    private static final int CAPTURE_IMAGE = 3;
    Uri mCapturedImageURI;

    public SummaryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view1 = inflater.inflate(R.layout.fragment_summary, container, false);
        ButterKnife.bind(this, view1);
        sessionManager = new SessionManager(getActivity());
        apiservice = ApiServiceCreator.createService("latest");

        arrayList = Utility.getAppcon().getSession().arrayListQuestion;
        toolbar_Title.setText("Summary Management");

        imagesPathList = new ArrayList<>();

        txt_date.setText(Utility.date);
        txtdate = Utility.date;
        questiondataList = new ArrayList<>();

        edt_summary.setOnTouchListener(new View.OnTouchListener() {
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

        back_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callNextApi1();
            }
        });

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

        img_picibutton.setOnClickListener(view -> {
            //  showChooser();
            selectImage();
        });

        btn_submit.setOnClickListener(view -> {

            if (Utility.getAppcon().getSession().check_valid1.equals("no")) {
                HseqInspctnFragment fragment = new HseqInspctnFragment();
                //SummaryFragment fragment = new SummaryFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frm, fragment);
                fragmentTransaction.addToBackStack("first");
                fragmentTransaction.commit();
            } else if (Utility.getAppcon().getSession().check_valid2.equals("no")) {
                InspctnandReprtngFragment fragment = new InspctnandReprtngFragment();
                //SummaryFragment fragment = new SummaryFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frm, fragment);
                fragmentTransaction.addToBackStack("first");
                fragmentTransaction.commit();
            } else if (Utility.getAppcon().getSession().check_valid3.equals("no")) {
                DocumentationFragment fragment = new DocumentationFragment();
                //SummaryFragment fragment = new SummaryFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frm, fragment);
                fragmentTransaction.addToBackStack("first");
                fragmentTransaction.commit();
            } else if (Utility.getAppcon().getSession().check_valid4.equals("no")) {
                CompetenceTraningFragment fragment = new CompetenceTraningFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frm, fragment);
                fragmentTransaction.addToBackStack("first");
                fragmentTransaction.commit();
            } else if (Utility.getAppcon().getSession().check_valid5.equals("no")) {
                WelfareFragment fragment = new WelfareFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frm, fragment);
                fragmentTransaction.addToBackStack("first");
                fragmentTransaction.commit();
            } else if (Utility.getAppcon().getSession().check_valid6.equals("no")) {
                EmergencyProFragment fragment = new EmergencyProFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frm, fragment);
                fragmentTransaction.addToBackStack("first");
                fragmentTransaction.commit();
            } else if (Utility.getAppcon().getSession().check_valid7.equals("no")) {
                RaMsFragment fragment = new RaMsFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frm, fragment);
                fragmentTransaction.addToBackStack("first");
                fragmentTransaction.commit();
            } else if (Utility.getAppcon().getSession().check_valid8.equals("no")) {
                NBFragment fragment = new NBFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frm, fragment);
                fragmentTransaction.addToBackStack("first");
                fragmentTransaction.commit();
            } else if (Utility.getAppcon().getSession().check_valid9.equals("no")) {
                PTWFragment fragment = new PTWFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frm, fragment);
                fragmentTransaction.addToBackStack("first");
                fragmentTransaction.commit();
            } else if (Utility.getAppcon().getSession().check_valid10.equals("no")) {
                TraficMngmntFragment fragment = new TraficMngmntFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frm, fragment);
                fragmentTransaction.addToBackStack("first");
                fragmentTransaction.commit();
            } else if (Utility.getAppcon().getSession().check_valid11.equals("no")) {
                VehicalVMPMFragment fragment = new VehicalVMPMFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frm, fragment);
                fragmentTransaction.addToBackStack("first");
                fragmentTransaction.commit();
            } else if (Utility.getAppcon().getSession().check_valid12.equals("no")) {
                PortableToolsFragment fragment = new PortableToolsFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frm, fragment);
                fragmentTransaction.addToBackStack("first");
                fragmentTransaction.commit();
            } else if (Utility.getAppcon().getSession().check_valid13.equals("no")) {
                NoisVibrtnDustFragment fragment = new NoisVibrtnDustFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frm, fragment);
                fragmentTransaction.addToBackStack("first");
                fragmentTransaction.commit();
            } else if (Utility.getAppcon().getSession().check_valid14.equals("no")) {
                ExcavationFragment fragment = new ExcavationFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frm, fragment);
                fragmentTransaction.addToBackStack("first");
                fragmentTransaction.commit();
            } else if (Utility.getAppcon().getSession().check_valid15.equals("no")) {
                PrtctngPblcSiteScrtyFragment fragment = new PrtctngPblcSiteScrtyFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frm, fragment);
                fragmentTransaction.addToBackStack("first");
                fragmentTransaction.commit();
            } else if (Utility.getAppcon().getSession().check_valid16.equals("no")) {
                PlantFragment fragment = new PlantFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frm, fragment);
                fragmentTransaction.addToBackStack("first");
                fragmentTransaction.commit();
            } else if (Utility.getAppcon().getSession().check_valid17.equals("no")) {
                MaterialsFragment fragment = new MaterialsFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frm, fragment);
                fragmentTransaction.addToBackStack("first");
                fragmentTransaction.commit();
            } else if (Utility.getAppcon().getSession().check_valid18.equals("no")) {
                VisualChecksFragment fragment = new VisualChecksFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frm, fragment);
                fragmentTransaction.addToBackStack("first");
                fragmentTransaction.commit();
            } else {
                callSubmitApi();
            }
        });

        return view1;
    }

    private void callSubmitApi() {

        if (edt_summary.getText().toString().isEmpty()) {
            Utility.displayToast(getActivity(), "Please Enter Summary !");
        } else if (ConvertedBitmap.equals("")) {
            Utility.displayToast(getActivity(), "Please Upload Signature !");
        } else if (ConvertedBitmap1.equals("")) {
            Utility.displayToast(getActivity(), "Please Upload Signature !");
        } else {

            fargment1 = "Yes";
            btn_submit.setClickable(false);
            avi.show();

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

                RequestBody userid = RequestBody.create(MediaType.parse("text/plain"), sessionManager.getKeyId());
                RequestBody laststep = RequestBody.create(MediaType.parse("text/plain"), Utility.getAppcon().getSession().data_value);
                RequestBody sumofmang = RequestBody.create(MediaType.parse("text/plain"), edt_summary.getText().toString());
                RequestBody compltn = RequestBody.create(MediaType.parse("text/plain"), txtdate);

                Observable<Model> responseObservable = apiservice.insertHSImage(
                        userid,
                        sumofmang,
                        laststep,
                        compltn,
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
                                Log.e("erroronErrorResumeNext", ex.getLocalizedMessage());
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
                                Log.e("error---", e.getMessage());
                            }

                            @Override
                            public void onNext(Model model) {
                                statusCode = model.getStatusCode();
                                Log.e("TAG", "onNext: " + model.getStatusCode());

                                if (statusCode == 200) {
                                    btn_submit.setClickable(false);
                                    Utility.displayToast(getActivity(), model.getMessage());
                                    Utility.getAppcon().getSession().questionstart.clear();
                                    Utility.getAppcon().getSession().questiondataList1.clear();
                                    Utility.getAppcon().getSession().questiondataList2.clear();
                                    Utility.getAppcon().getSession().questiondataList3.clear();
                                    Utility.getAppcon().getSession().questiondataList4.clear();
                                    Utility.getAppcon().getSession().questiondataList5.clear();
                                    Utility.getAppcon().getSession().questiondataList6.clear();
                                    Utility.getAppcon().getSession().questiondataList7.clear();
                                    Utility.getAppcon().getSession().questiondataList8.clear();
                                    Utility.getAppcon().getSession().questiondataList9.clear();
                                    Utility.getAppcon().getSession().questiondataList10.clear();
                                    Utility.getAppcon().getSession().questiondataList11.clear();
                                    Utility.getAppcon().getSession().questiondataList12.clear();
                                    Utility.getAppcon().getSession().questiondataList13.clear();
                                    Utility.getAppcon().getSession().questiondataList14.clear();
                                    Utility.getAppcon().getSession().questiondataList15.clear();
                                    Utility.getAppcon().getSession().questiondataList16.clear();
                                    Utility.getAppcon().getSession().questiondataList17.clear();
                                    Utility.getAppcon().getSession().questiondataList18.clear();
                                    Utility.getAppcon().getSession().data_value = "";
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
    }

    private void selectImage() {

        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (options[item].equals("Take Photo")) {

                    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                        String fileName = "temp.jpg";
                        ContentValues values = new ContentValues();
                        values.put(MediaStore.Images.Media.TITLE, fileName);
                        mCapturedImageURI = getActivity().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case CAPTURE_IMAGE:
                ImageCropFunctionCustom(mCapturedImageURI);
                break;
            case CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE:

                CropImage.ActivityResult result = CropImage.getActivityResult(data);
                Compressor compressedImageFile = new Compressor(getActivity());
                compressedImageFile.setQuality(60);

                try {                    // Get the file path from the URI
                    File file = compressedImageFile.compressToFile(new File(result.getUri().getPath()));
                    Uri uri = Uri.fromFile(file);

                    Log.e("TAG", "capture uri:-- " + uri);
                    final String path = FileUtils.getPath(getActivity(), uri);
                    Log.e("Single File Selected", path);

                    imagesPathList.add(uri);
                    Log.e("Path is ", path);

                    MyAdapter mAdapter = new MyAdapter(getActivity(), imagesPathList);
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

    public void ImageCropFunctionCustom(Uri uri) {
        Intent intent = CropImage.activity(uri)
                .setGuidelines(CropImageView.Guidelines.ON)
                .getIntent(getActivity());
        startActivityForResult(intent, CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE);
    }

    /* @RequiresApi(api = Build.VERSION_CODES.KITKAT)
     @NonNull
     private MultipartBody.Part prepareFilePart(String partName, Uri fileUri) {
         Log.e("All File detail is ", String.valueOf(fileUri));
         File file = FileUtils.getFile(getActivity(), fileUri);
         RequestBody requestFile = RequestBody.create(MediaType.parse(Objects.requireNonNull(getActivity().getContentResolver().getType(fileUri))), file);
         return MultipartBody.Part.createFormData(partName, file.getName(), requestFile);
     }*/
    @NonNull
    private MultipartBody.Part prepareFilePart(String partName, Uri fileUri) {
        RequestBody requestFile;
        File file = FileUtils.getFile(getActivity(), fileUri);
        requestFile = RequestBody.create(MediaType.parse("image/*"), file);
        return MultipartBody.Part.createFormData(partName, file.getName(), requestFile);
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
                bitmap.compress(Bitmap.CompressFormat.PNG, 40, mFileOutStream);
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
                bitmap1.compress(Bitmap.CompressFormat.PNG, 40, mFileOutStream);
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
    private void callNextApi1() {

        questiondataList.clear();
        Observable<DemoModel> responseObservable = apiservice.back_hsquestion_topic_data(Utility.getAppcon().getSession().data_value,arrayList.get(0).getVisualChecks().get(0).getHsLabel());
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
                .subscribe(new Observer<DemoModel>() {
                    @Override
                    public void onCompleted() {
                        // avi.hide();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("error", "" + e.getMessage());
                    }

                    @Override
                    public void onNext(DemoModel model) {
                        avi.hide();
                        statusCode = model.getStatusCode();
                        if (statusCode == 200) {
                           /* Log.e("Response is1 ",""+statusCode);
                            Log.e("size is a",""+model.getData().size());
*/
                            // questiondataList=arrayListQuestion1.get(0).getQuestions_hs();
                            Log.e("questiondataList", String.valueOf(questiondataList.size()));
                            try {
                                for (int j=0;j<model.getData().size();j++)
                                {
                                   /* Log.e("J is  a",""+j);
                                    Log.e("Question is ",model.getData().get(j).getAnsQues());
                                    Log.e("Question is Ans ",model.getData().get(j).getAnsQuesValue());*/
                                    questiondata=new Example.questiondata();
                                    questiondata.setAns_ques(model.getData().get(j).getAnsQues());
                                    questiondata.setAns_label(model.getData().get(j).getAnsLabel());
                                    questiondata.setAns_ques_value(model.getData().get(j).getAnsQuesValue());
                                    questiondata.setAns_ques_value_no(model.getData().get(j).getAnsQuesValueNo());
                                    questiondataList.add(questiondata);
                                   /* questiondataList.get(j).setAns_label(""+model.getData().get(j).getAnsLabel());
                                    questiondataList.get(j).setAns_ques_value(""+model.getData().get(j).getAnsQuesValue());
                                    questiondataList.get(j).setAns_ques(""+model.getData().get(j).getAnsQues());
                                    questiondataList.get(j).setAns_ques_value_no(""+model.getData().get(j).getAnsQuesValueNo());*/

                                }
                            }
                            catch (Exception e)
                            {
                            }

                            e = new Example();
                            e.setAns_user_id(sessionManager.getKeyId());
                            e.setAns_comment("");
                            e.setAns_needdate(txtdate);
                            e.setLast_step(Utility.getAppcon().getSession().data_value);
                            e.setQuestions_hs(questiondataList);
                            List<Example> examples = new ArrayList<>();
                            examples.add(e);
                            Utility.getAppcon().getSession().questiondataList18 = new ArrayList<>();
                            Utility.getAppcon().getSession().questiondataList18 = examples;

                            VisualChecksFragment fragment = new VisualChecksFragment();
                            //SummaryFragment fragment = new SummaryFragment();
                            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.add(R.id.frm, fragment);
                            fragmentTransaction.addToBackStack("first");
                            Utility.getAppcon().getSession().arrayListQuestion = arrayList;
                            fragmentTransaction.commit();
                        }
                        else {
                            VisualChecksFragment fragment = new VisualChecksFragment();
                            //SummaryFragment fragment = new SummaryFragment();
                            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.add(R.id.frm, fragment);
                            fragmentTransaction.addToBackStack("first");
                            Utility.getAppcon().getSession().arrayListQuestion = arrayList;
                            fragmentTransaction.commit();
                        }
                    }
                });
    }
}
