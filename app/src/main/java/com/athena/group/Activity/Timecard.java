package com.athena.group.Activity;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;

import com.athena.group.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Timecard extends AppCompatActivity {

    private int mYear, mMonth, mDay, mHour, mMinute;
    RelativeLayout btnDatePicker;
    AppCompatButton submit;
    TextView calender;
    CardView timeclock, timeclock1, timeclock2, timeclock3;
    TextView timetxt, timetxt1, timetxt2, timetxt3;
    String stime = "", etime = "", bstime = "", betime = "", txtdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.timecard);
        timetxt = findViewById(R.id.timetxt);
        timetxt1 = findViewById(R.id.timetxt1);
        timetxt2 = findViewById(R.id.timetxt2);
        timetxt3 = findViewById(R.id.timetxt3);
        ImageView img_back = findViewById(R.id.branding_icon);


        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.branding_icon:
                        finish();
                        break;
                    default:
                        break;
                }
            }
        });

        timeclock = findViewById(R.id.timeclock);
        timeclock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(Timecard.this, R.style.DialogTheme,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {


                                stime = String.format("%02d:%02d", hourOfDay, minute);
                                timetxt.setText(stime);
                                Log.e("timestartddd", stime);


                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();
            }
        });

        timeclock1 = findViewById(R.id.timeclock1);
        timeclock1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(Timecard.this, R.style.DialogTheme,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {

                                etime = String.format("%02d:%02d", hourOfDay, minute);
                                timetxt1.setText(etime);
                                Log.e("timestartddd", etime);


//                                timetxt1.setText(hourOfDay + ":" + minute);
//                                etime = hourOfDay + ":" + minute;
                                //Log.e("end_time",etime);
                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();
            }
        });
        timeclock2 = findViewById(R.id.timeclock2);
        timeclock2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(Timecard.this, R.style.DialogTheme,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {

                                bstime = String.format("%02d:%02d", hourOfDay, minute);
                                timetxt2.setText(bstime);
                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();
            }
        });
        timeclock3 = findViewById(R.id.timeclock3);
        timeclock3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(Timecard.this, R.style.DialogTheme,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {


                                betime = String.format("%02d:%02d", hourOfDay, minute);
                                timetxt3.setText(betime);
                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();
            }
        });

        //  btnDatePicker = findViewById(R.id.btnDatePicker);

/*
time        btnDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(Timecard.this, R.style.DialogTheme,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                calender.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                                txtdate = calender.toString();
                                // day.setText(dayOfMonth);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
*/


        submit = findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
                Date startDate = null, endDate = null, breakStart = null, breakEnd = null;

                if (stime != null) {
                    try {
                        startDate = simpleDateFormat.parse(stime);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                if (etime != null) {
                    try {
                        endDate = simpleDateFormat.parse(etime);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                if (bstime != null) {
                    try {
                        breakStart = simpleDateFormat.parse(etime);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                if (betime != null) {
                    try {
                        breakEnd = simpleDateFormat.parse(etime);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }

                if (TimeDiff(startDate, endDate, simpleDateFormat)) {
                    Toast.makeText(Timecard.this, "sacho Time", Toast.LENGTH_SHORT).show();
                    if (TimeDiff(breakStart, breakEnd, simpleDateFormat)) {
                        Toast.makeText(Timecard.this, "sacho Time", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(getApplicationContext(), TimeLog.class);
                        i.putExtra("startime", stime);
                        i.putExtra("endtime", etime);
                        i.putExtra("bstartime", bstime);
                        i.putExtra("estartime", betime);
                        startActivity(i);
                        finish();
                    } else {
                        Toast.makeText(Timecard.this, "khoto Time", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(Timecard.this, "khoto Time", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public boolean TimeDiff(Date startDate, Date endDate, SimpleDateFormat simpleDateFormat) {
        boolean isSuccess = false;
        if (endDate != null && startDate != null) {
            long difference = endDate.getTime() - startDate.getTime();
            if (difference < 0) {
                isSuccess = false;
            } else {
                isSuccess = true;
            }
        }
        return isSuccess;
    }

}


