package com.athena.group.Fragment.HsInspection;


import android.Manifest;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.athena.group.API.ApiInterface;
import com.athena.group.API.ApiServiceCreator;
import com.athena.group.FileUtils;
import com.athena.group.Model.CommanModel;
import com.athena.group.Model.HSInspectionModel;
import com.athena.group.Model.HSQuestionModel;
import com.athena.group.Model.Model;
import com.athena.group.R;
import com.athena.group.adapter.HSInspectnQuesAdapter;
import com.athena.group.adapter.MyAdapter;
import com.athena.group.application.SessionManager;
import com.athena.group.application.Utility;
import com.wang.avi.AVLoadingIndicatorView;


import java.io.File;
import java.net.SocketTimeoutException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
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
import static com.athena.group.adapter.HSInspectnQuesAdapter.hsquestiondataList;

/**
 * A simple {@link Fragment} subclass.
 */
public class HSCccTrngFragment extends Fragment {


    //Add Shirish
    @BindView(R.id.listView)
    public GridView listView;
    private List<Uri> imagesPathList = new ArrayList<>();
    private static final int REQUEST_CODE = 6384;
    private static final int REQUEST_CODE_ASK_PERMISSIONS = 124;
    RelativeLayout nestred_scroll;


    Uri mCapturedImageURI;
    @BindView(R.id.toolbar_Title)
    TextView toolbar_Title;
    @BindView(R.id.back_icon)
    ImageView back_icon;
    @BindView(R.id.btn_next)
    LinearLayout btn_next;
    @BindView(R.id.avi)
    AVLoadingIndicatorView avi;
    @BindView(R.id.btn_previous)
    LinearLayout btn_previous;
    @BindView(R.id.recyerview)
    RecyclerView recyerview;

    private Bitmap yourbitmap;

    private LinearLayout lnrImages;
    private Bitmap resized;

    private final int PICK_IMAGE_MULTIPLE = 1;
    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;

    @BindView(R.id.img_picibutton)
    LinearLayout img_picibutton;
    List<MultipartBody.Part> parts = new ArrayList<>();

    HorizontalScrollView horizontalScrollView;
    public static final String IMAGE_EXTENSION = "jpg";
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;

    Integer REQUEST_IMAGE = 1;
    Integer CAPTURE_IMAGE = 3;
    Integer REQUEST_VIDEO = 2;
    String path;
    @BindView(R.id.btn_submit)
    Button btn_submit;

    List<MultipartBody.Part> list;
    @BindView(R.id.txt_failed)
    public TextView txt_failed;
    @BindView(R.id.txt_percntg)
    public TextView txt_percntg;
    @BindView(R.id.txt_fld)
    public TextView txt_fld;
    @BindView(R.id.txt_per)
    public TextView txt_per;
    private SessionManager sessionManager;
    private ApiInterface apiservice;
    private int statusCode;
    ArrayList<HSQuestionModel.Data> arrayList;
    private RecyclerView.LayoutManager layoutManager;
    HSInspectnQuesAdapter adapter;
    List<CommanModel> commanModelslist;
    HSInspectionModel hs;
    String validation = "No";
    DecimalFormat format = new DecimalFormat("0.00");


    public HSCccTrngFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view1 = inflater.inflate(R.layout.fragment_hs_img, container, false);
        ButterKnife.bind(this, view1);

        sessionManager = new SessionManager(getActivity());
        apiservice = ApiServiceCreator.createService("latest");

        arrayList = Utility.getAppcon().getSession().arrayListHSInsctnQuestion;

        toolbar_Title.setText(arrayList.get(0).getCommunicationCoOrdinationCompetenceAndTraining().get(0).getHsLabel());

        back_icon.setOnClickListener(view -> {
            getActivity().onBackPressed();
        });

        //add Shirish
        /*imagesPathList = new ArrayList<>();
        parts = new ArrayList<>();*/

        hsquestiondataList = new ArrayList<>();
        hsquestiondataList.clear();

        btn_previous.setOnClickListener(view -> getActivity().onBackPressed());
        btn_next.setOnClickListener(view -> {
            hs = new HSInspectionModel();
            hs.setAns_user_id(sessionManager.getKeyId());
            hs.setLast_step(Utility.getAppcon().getSession().hs_data_value);
            hs.setAns_failed(txt_failed.getText().toString());
            hs.setAns_score(txt_percntg.getText().toString());
            hs.setQuestions_hs(hsquestiondataList);

            // for (int i = 0; i < hsquestiondataList.size(); i++) {
//            if (hsquestiondataList.get(0).getAns_ques_status().equals("")) {
//                Utility.displayToast(getActivity(), "Please Select Inspection Status");
//            } else {
            //callNextApi();
            //}
            //}
            if (validation.equals("No")) {
                Toast.makeText(getActivity(), "Please Select Inspection Status", Toast.LENGTH_LONG).show();
            } else {
                callNextApi();
            }
        });

        commanModelslist = new ArrayList<>();
        for (int i = 0; i < arrayList.get(0).getCommunicationCoOrdinationCompetenceAndTraining().size(); i++) {
            CommanModel commanModel = new CommanModel();
            commanModel.setHsId(arrayList.get(0).getCommunicationCoOrdinationCompetenceAndTraining().get(i).getHsId());
            commanModel.setHsLabel(arrayList.get(0).getCommunicationCoOrdinationCompetenceAndTraining().get(i).getHsLabel());
            commanModel.setHsQues(arrayList.get(0).getCommunicationCoOrdinationCompetenceAndTraining().get(i).getHsQues());
            commanModelslist.add(commanModel);
        }

        recyerview.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        recyerview.setLayoutManager(layoutManager);
        adapter = new HSInspectnQuesAdapter(getActivity(), commanModelslist, "hsccct", new HSInspectnQuesAdapter.OnItemClickListener() {
            public void onItemClick(Double item, int item1, String validation1) {

                if (item == 100.0) {
                    validation = validation1;
                    txt_percntg.setTextColor(Color.parseColor("#4CAF50"));
                    txt_per.setTextColor(Color.parseColor("#4CAF50"));
                    txt_percntg.setText(String.valueOf(format.format((item))));
                    txt_failed.setVisibility(View.GONE);
                    txt_fld.setVisibility(View.GONE);
                    //txt_failed.setText(String.valueOf(item1) + "failed");

                    Log.e("TAG", "perctng-- " + item);
                } else {
                    txt_failed.setVisibility(View.VISIBLE);
                    txt_fld.setVisibility(View.VISIBLE);
                    validation = validation1;
                    txt_percntg.setTextColor(Color.parseColor("#FD9832"));
                    txt_per.setTextColor(Color.parseColor("#FD9832"));
                    txt_percntg.setText(String.valueOf(format.format((item))));
                    txt_failed.setText(String.valueOf(item1));
                    Log.e("TAG", "perctng-- " + item);
                    Log.e("TAG", "faild-- " + item1);

                }
            }
        });
        recyerview.setAdapter(adapter);

        img_picibutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (askForPermission())
                    showChooser();
            }
        });

        btn_submit.setOnClickListener(view -> {

            if (imagesPathList.isEmpty()) {
                Utility.displayToast(getActivity(), "Please Select Image");
            } else {
                callImgUploadApi();
            }
        });

        return view1;

    }

    private void callImgUploadApi() {

        if (imagesPathList != null) {
            for (int i = 0; i < imagesPathList.size(); i++) {
                parts.add(prepareFilePart("img_path[]", imagesPathList.get(i)));
            }
        }

        String u_id = sessionManager.getKeyId();
        String lasxt_step = Utility.getAppcon().getSession().hs_data_value;
        String label = arrayList.get(0).getCommunicationCoOrdinationCompetenceAndTraining().get(0).getHsLabel();

        Log.e("TAG", "u_id:-- " + u_id);
        Log.e("TAG", "last_step:-- " + lasxt_step);
        Log.e("TAG", "label:-- " + label);

        Log.e("Part is ", String.valueOf(parts));

        avi.show();

        RequestBody last_step = RequestBody.create(MediaType.parse("text/plain"), lasxt_step);
        RequestBody label1 = RequestBody.create(MediaType.parse("text/plain"), label);
        Observable<Model> responseObservable = apiservice.insert_inspector_imgdata(Integer.parseInt(u_id), label1, last_step, parts);

        //Observable<Model> responseObservable = apiservice.insert_inspector_imgdata(u_id, label, last_step, parts);
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
                        avi.hide();
                        statusCode = model.getStatusCode();
                        if (statusCode == 200) {
                            Log.e("status code ", "" + model.getMessage());
                            Utility.displayToast(getActivity(), "Image Upload Successfully !");
                        }
                    }
                });
    }


    //Add Shirish
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
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

                            Log.e("TAG", "Uri Selected" + imageUri.toString());

                        }
                    } else if (data.getData() != null) {
                        //do something with the image (save it to some directory or whatever you need to do with it here)
                        final Uri uri = data.getData();
                        //imagesPathList.add(uri);

                        try {
                            // Get the file path from the URI
                            final String path = FileUtils.getPath(getActivity(), uri);
                            Log.e("TAG", "Single File Selected" + path);

                            imagesPathList.add(uri);
                            Log.e("TAG", "Path is " + path);
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


    //Add Shirish
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @NonNull
    private MultipartBody.Part prepareFilePart(String partName, Uri fileUri) {
        Log.e("All File detail is ", String.valueOf(fileUri));
        File file = FileUtils.getFile(getActivity(), fileUri);
        RequestBody requestFile = RequestBody.create(MediaType.parse(Objects.requireNonNull(getActivity().getContentResolver().getType(fileUri))), file);
        return MultipartBody.Part.createFormData(partName, file.getName(), requestFile);
    }


    private void callNextApi() {

        //nestred_scroll.setVisibility(View.VISIBLE);
        avi.show();
        if (hs.getQuestions_hs().size() == 0) {
            Utility.displayToast(getActivity(), "Please fill-up details");
        } else {
            Observable<Model> responseObservable = apiservice.insert_inspectionHsDetails(hs);
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
                            avi.hide();
                            statusCode = model.getStatusCode();
                            if (statusCode == 200) {

                                Utility.displayToast(getActivity(), model.getMessage());
                                //CompletionFragment fragment = new CompletionFragment();
                                HSEmrgncywlfrFragment fragment = new HSEmrgncywlfrFragment();
                                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                fragmentTransaction.add(R.id.frm, fragment);
                                fragmentTransaction.addToBackStack(null);
                                fragmentTransaction.commit();
                            }
                        }
                    });
        }

    }//Add Shirish

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


    /* AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AppBaseTheme);
                                ViewGroup viewGroup = getActivity().findViewById(android.R.id.content);
                                View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_image_upload, viewGroup, false);
                                nestred_scroll = dialogView.findViewById(R.id.nestred_scroll);
                                lnrImages = (LinearLayout) dialogView.findViewById(R.id.lnrImages);
                                img_picibutton = dialogView.findViewById(R.id.img_picibutton);
                                btn_submit = dialogView.findViewById(R.id.btn_submit);

                                listView = dialogView.findViewById(R.id.listView);
                                builder.setView(dialogView);
                                AlertDialog alertDialog = builder.create();
                                alertDialog.show();
                                btn_submit.setOnClickListener(new View.OnClickListener() {
                                    @SuppressLint("LongLogTag")
                                    @Override
                                    public void onClick(View view) {

                                        parts = new ArrayList<>();


                                        if (imagesPathList != null) {
                                            for (int i = 0; i < imagesPathList.size(); i++) {
                                                parts.add(prepareFilePart("img_path[]", imagesPathList.get(i)));
                                            }
                                        }


                                        *//*
     *//*

                                        String u_id = sessionManager.getKeyId();
                                        String last_step = Utility.getAppcon().getSession().hs_data_value;
                                        String label = arrayList.get(0).getCommunicationCoOrdinationCompetenceAndTraining().get(0).getHsLabel();

                                        Log.e("TAG", "u_id:-- " + u_id);
                                        Log.e("TAG", "last_step:-- " + last_step);
                                        Log.e("TAG", "label:-- " + label);

                                        Log.e("Part is ", String.valueOf(parts));
                                        Observable<Model> responseObservable = apiservice.insert_inspector_imgdata(u_id, label, last_step, parts);
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

                                                    }

                                                    @Override
                                                    public void onError(Throwable e) {
                                                        Log.e("error", "" + e.getMessage());
                                                    }

                                                    @Override
                                                    public void onNext(Model model) {
                                                        //avi.hide();
                                                        statusCode = model.getStatusCode();


                                                        if (statusCode == 200) {
                                                            Log.e("status code ", "" + model.getMessage());
                                                            Utility.displayToast(getActivity(), model.getMessage());
                                                            HSEmrgncywlfrFragment fragment = new HSEmrgncywlfrFragment();
                                                            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                                            fragmentTransaction.replace(R.id.frm, fragment);
                                                            fragmentTransaction.addToBackStack(null);
                                                            fragmentTransaction.commit();
                                                            alertDialog.dismiss();
                                                        }
                                                    }
                                                });
                                    }
                                });
                                img_picibutton.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                        if (askForPermission())
                                            showChooser();
                                    }
                                });*/

    //Add Shirish
    private boolean askForPermission() {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if (currentAPIVersion >= Build.VERSION_CODES.M) {
            int hasCallPermission = ContextCompat.checkSelfPermission(getActivity(),
                    Manifest.permission.READ_EXTERNAL_STORAGE);
            if (hasCallPermission != PackageManager.PERMISSION_GRANTED) {
                // Ask for permission
                // need to request permission
                if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                        Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    // explain
                    showMessageOKCancel(
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    ActivityCompat.requestPermissions(getActivity(),
                                            new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
                                            REQUEST_CODE_ASK_PERMISSIONS);
                                }
                            });
                    // if denied then working here
                } else {
                    // Request for permission
                    ActivityCompat.requestPermissions(getActivity(),
                            new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
                            REQUEST_CODE_ASK_PERMISSIONS);
                }

                return false;
            } else {
                // permission granted and calling function working
                return true;
            }
        } else {
            return true;
        }
    }

    //Add Shirish
    private void showMessageOKCancel(DialogInterface.OnClickListener okListener) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final AlertDialog dialog = builder.setMessage("You need to grant access to Read External Storage")
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create();

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface arg0) {
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(
                        ContextCompat.getColor(getContext(), android.R.color.holo_blue_light));
                dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(
                        ContextCompat.getColor(getContext(), android.R.color.holo_red_light));
            }
        });

        dialog.show();

    }
}


