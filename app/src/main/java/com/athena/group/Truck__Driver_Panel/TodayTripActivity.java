package com.athena.group.Truck__Driver_Panel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.athena.group.API.ApiInterface;
import com.athena.group.API.ApiServiceCreator;
import com.athena.group.Model.SpinnerModel;
import com.athena.group.Model.VDRLModel;
import com.athena.group.R;
import com.athena.group.adapter.VehicleDefectAdapter;
import com.athena.group.application.SessionManager;
import com.athena.group.application.Utility;
import com.wang.avi.AVLoadingIndicatorView;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Observer;
import rx.schedulers.Schedulers;

public class TodayTripActivity extends AppCompatActivity {

    SessionManager sessionManager;
    ApiInterface apiservice;
    final Calendar myCalendar = Calendar.getInstance();
    @BindView(R.id.toolbar_Title)
    TextView toolbar_Title;
    @BindView(R.id.back_icon)
    ImageView back_icon;
    @BindView(R.id.avi)
    AVLoadingIndicatorView avi;
    @BindView(R.id.spn_vehicle)
    Spinner spn_vehicle;
    /*   @BindView(R.id.txt_date)
       TextView txt_date;*/
   /* @BindView(R.id.txt_date1)
    TextView txt_date1;*/
    @BindView(R.id.txt_driver_name)
    TextView txt_driver_name;
    @BindView(R.id.btn_next)
    Button btn_next;
    private String vehicle = "";
    int statusCode;
    List<SpinnerModel.Data> data;
    private String tr_id = "", trname = "", pass_date;

    @BindView(R.id.rv_reportlist)
    RecyclerView rv_reportlist;
    VehicleDefectAdapter adapter;
    RecyclerView.LayoutManager layoutManager;
    public static Handler handler = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_today_trip);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ButterKnife.bind(this);
        sessionManager = new SessionManager(TodayTripActivity.this);
        apiservice = ApiServiceCreator.createService("latest");
        toolbar_Title.setText(getString(R.string.vehicledefectreport));
        checkHandller();
        back_icon.setOnClickListener(view -> finish());
        initViews();
    }

    private void initViews() {
        rv_reportlist.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        rv_reportlist.setLayoutManager(layoutManager);

        txt_driver_name.setText(sessionManager.getKeyName() + " " + sessionManager.getKeyLname());

        //txt_date.setText(Utility.date);
        /*txt_date1.setText(Utility.date);
        pass_date = Utility.date;*/

/*
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
*/

/*
        txt_date1.setOnClickListener(view ->
        {
            new DatePickerDialog(TodayTripActivity.this, datee1, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)).show();

        });
*/
        btn_next.setOnClickListener(view ->
        {
            if (vehicle.equals("")) {
                Utility.displayToast(getApplicationContext(), "Select Vehicle First !");
            } else {
                Intent intent = new Intent(TodayTripActivity.this, VehicleDefectReport.class);
                intent.putExtra("vehicle", vehicle);
                intent.putExtra("vehicle_id", tr_id);
                //intent.putExtra("date", pass_date);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }

        });

        getVehicle();
        spn_vehicle.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (spn_vehicle.getSelectedItem() == "Select Vehicle") {
                    vehicle = "";
                    //Do nothing.
                } else {
                    vehicle = spn_vehicle.getSelectedItem().toString();
                    SpinnerModel.Data datalist = data.get(i - 1);
                    tr_id = datalist.getTrId();
                    trname = datalist.getTrNumber();
                    sessionManager.setKeyTruckno(trname);
                    sessionManager.setKeyTruckid(tr_id);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        getVehicleDefectListApi();
    }

    @SuppressLint("HandlerLeak")
    public void checkHandller() {
        handler = new Handler() {
            @Override
            public void handleMessage(Message message) {
                if (message == null) {
                }
                if (message.what == 100) {
                    getVehicleDefectListApi();
                }
            }
        };
    }

    private void getVehicleDefectListApi() {

        avi.show();

        Observable<VDRLModel> responseObservable = apiservice.get_driverDefectReport(
                sessionManager.getKeyId()
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
                .subscribe(new Observer<VDRLModel>() {
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
                    public void onNext(VDRLModel model) {
                        avi.hide();

                        //if (model.getData().size() > 0) {
                        statusCode = model.getStatusCode();
                        if (statusCode == 200) {
                            rv_reportlist.setVisibility(View.VISIBLE);
                            Utility.displayToast(getApplicationContext(), model.getMessage());
                            adapter = new VehicleDefectAdapter(TodayTripActivity.this, model.getData());
                            rv_reportlist.setAdapter(adapter);
                        } else if (statusCode == 404) {
                            rv_reportlist.setVisibility(View.GONE);
                            Utility.displayToast(getApplicationContext(), model.getMessage());
                        }
                        /*} else {
                            txt_dummy.setText(View.VISIBLE);
                        }*/
                    }
                });
    }

/*
    private void updateLabel() {
        String myFormat = "dd-MM-yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        txt_date1.setText(sdf.format(myCalendar.getTime()));   // for display

        String myFormat1 = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf1 = new SimpleDateFormat(myFormat1, Locale.US);
        pass_date = sdf1.format(myCalendar.getTime());   //pass in api
    }
*/


    private void getVehicle() {

        ArrayList<String> vehiclearray = new ArrayList<>();

        avi.show();
        Observable<SpinnerModel> responseObservable = apiservice.get_vehicle(sessionManager.getKeyId());

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
                .subscribe(new Observer<SpinnerModel>() {
                    @Override
                    public void onCompleted() {

                        avi.hide();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("error", "" + e.getMessage());
                    }

                    @Override
                    public void onNext(SpinnerModel model) {
                        statusCode = model.getStatusCode();
                        if (statusCode == 200) {
                            data = model.getData();
                            for (int i = 0; i < model.getData().size(); i++) {
                                vehiclearray.add(model.getData().get(i).getTrNumber());
                            }
                        } else {
                            Utility.displayToast(TodayTripActivity.this, model.getMessage());
                        }
                    }
                });

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, vehiclearray);
        dataAdapter.add("Select Vehicle");
        dataAdapter.setDropDownViewResource(R.layout.spinner_item);
        spn_vehicle.setAdapter(dataAdapter);
    }
}
