package com.athena.group.adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.athena.group.API.ApiInterface;
import com.athena.group.API.ApiServiceCreator;
import com.athena.group.Model.CommanModel;
import com.athena.group.Model.HSInspectionModel;
import com.athena.group.Model.PercFaildModel;
import com.athena.group.Model.SpinnerModel;
import com.athena.group.R;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Observer;
import rx.schedulers.Schedulers;

public class HSInspectnQuesAdapter extends RecyclerView.Adapter<HSInspectnQuesAdapter.MyViewHolder> {

    public interface OnItemClickListener {
        //void onItemClick(Float item, int item1, String validation);
        void onItemClick(Double item, int item1, String validation);
    }

    OnItemClickListener listener;
    int total_failed = 0;
    double totale_point = 0.00, totale_point_same = 0.00;
    int question_totale_point = 0;
    Double question_percentage = 0.00;
    double per = 0.00;
    Context mContext;
    List<CommanModel> arraylist;
    private String fragment_name;
    public static List<HSInspectionModel.questiondata_Hs> hsquestiondataList = new ArrayList<>();
    private Dialog dialog;
    int statusCode;
    ApiInterface apiservice;
    ArrayList<String> arrayList_status = new ArrayList<>();
    String[] array;
    String hs_status = "";
    HSInspectionModel.questiondata_Hs hsism;
    HSInspectionModel model;
    PercFaildModel pfmodel;
    List<PercFaildModel.Dataa> dataaList = new ArrayList<>();
    String TAG = "TAG";
    int quesum, pos, scorgood = 40, scormi = 20, scorir = 1, scoriar = 10, scorna = 40;
    List<String> queslist;
    double totlscor = 0.0;
    PercFaildModel.Dataa dataa123;
    int position1;


    public HSInspectnQuesAdapter(Context mContext, List<CommanModel> arraylist, String fragment_name, OnItemClickListener listener) {
        this.mContext = mContext;
        this.arraylist = arraylist;
        this.fragment_name = fragment_name;
        this.listener = listener;
        apiservice = ApiServiceCreator.createService("latest");
    }

    @NonNull
    @Override
    public HSInspectnQuesAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hsinspctnquestion_details, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HSInspectnQuesAdapter.MyViewHolder holder, int position) {

        //pos = position;
        hsism = new HSInspectionModel.questiondata_Hs();
        model = new HSInspectionModel();
        pfmodel = new PercFaildModel();


        queslist = new ArrayList<>();
        queslist.add(arraylist.get(position).getHsQues());
        pos = 40;
        position1 = position;
        dataa123 = new PercFaildModel.Dataa();
        for (int j = 0; j <= queslist.size(); j++) {
            dataa123.setId("0");
            dataa123.setAns_ques_points("0");
            dataa123.setPoistion(String.valueOf(j));
        }

        dataaList.add(dataa123);
        pfmodel.setDataa(dataaList);
        if (fragment_name.equals("hsincpcnreportng")) {
            holder.txt_queId.setText(Integer.toString(position + 1));
            holder.txt_queTitle.setText(arraylist.get(position).getHsQues());

            for (int i = 0; i < queslist.size(); i++) {
                quesum = quesum + pos;
            }
            Log.e(TAG, "quesum 1-- " + quesum);


        } else if (fragment_name.equals("hsdocumnt")) {
            holder.txt_queId.setText(Integer.toString(position + 1));
            holder.txt_queTitle.setText(arraylist.get(position).getHsQues());

            for (int i = 0; i < queslist.size(); i++) {
                quesum = quesum + pos;
            }
            Log.e(TAG, "quesum 2-- " + quesum);

        } else if (fragment_name.equals("hsccct")) {
            holder.txt_queId.setText(Integer.toString(position + 1));
            holder.txt_queTitle.setText(arraylist.get(position).getHsQues());
            for (int i = 0; i < queslist.size(); i++) {
                quesum = quesum + pos;
            }
            Log.e(TAG, "quesum 3-- " + quesum);

        } else if (fragment_name.equals("hsemrgncywlfr")) {
            holder.txt_queId.setText(Integer.toString(position + 1));
            holder.txt_queTitle.setText(arraylist.get(position).getHsQues());

            for (int i = 0; i < queslist.size(); i++) {
                quesum = quesum + pos;
            }
            Log.e(TAG, "quesum 4-- " + quesum);

        } else if (fragment_name.equals("hsrams")) {
            holder.txt_queId.setText(Integer.toString(position + 1));
            holder.txt_queTitle.setText(arraylist.get(position).getHsQues());
            for (int i = 0; i < queslist.size(); i++) {
                quesum = quesum + pos;
            }
            Log.e(TAG, "quesum 4-- " + quesum);
        } else if (fragment_name.equals("hsptw")) {
            holder.txt_queId.setText(Integer.toString(position + 1));
            holder.txt_queTitle.setText(arraylist.get(position).getHsQues());
            for (int i = 0; i < queslist.size(); i++) {
                quesum = quesum + pos;
            }
            Log.e(TAG, "quesum 4-- " + quesum);
        } else if (fragment_name.equals("hstmpae")) {
            holder.txt_queId.setText(Integer.toString(position + 1));
            holder.txt_queTitle.setText(arraylist.get(position).getHsQues());
            for (int i = 0; i < queslist.size(); i++) {
                quesum = quesum + pos;
            }
            Log.e(TAG, "quesum 4-- " + quesum);
        } else if (fragment_name.equals("hsmatral")) {
            holder.txt_queId.setText(Integer.toString(position + 1));
            holder.txt_queTitle.setText(arraylist.get(position).getHsQues());
            for (int i = 0; i < queslist.size(); i++) {
                quesum = quesum + pos;
            }
            Log.e(TAG, "quesum 4-- " + quesum);
        } else if (fragment_name.equals("hsnvd")) {
            holder.txt_queId.setText(Integer.toString(position + 1));
            holder.txt_queTitle.setText(arraylist.get(position).getHsQues());
            for (int i = 0; i < queslist.size(); i++) {
                quesum = quesum + pos;
            }
            Log.e(TAG, "quesum 4-- " + quesum);
        } else if (fragment_name.equals("hsvvmpm")) {
            holder.txt_queId.setText(Integer.toString(position + 1));
            holder.txt_queTitle.setText(arraylist.get(position).getHsQues());

            for (int i = 0; i < queslist.size(); i++) {
                quesum = quesum + pos;
            }
            Log.e(TAG, "quesum 4-- " + quesum);
        } else if (fragment_name.equals("hsleo")) {
            holder.txt_queId.setText(Integer.toString(position + 1));
            holder.txt_queTitle.setText(arraylist.get(position).getHsQues());
            for (int i = 0; i < queslist.size(); i++) {
                quesum = quesum + pos;
            }
            Log.e(TAG, "quesum 4-- " + quesum);
        } else if (fragment_name.equals("hshkae")) {
            holder.txt_queId.setText(Integer.toString(position + 1));
            holder.txt_queTitle.setText(arraylist.get(position).getHsQues());
            for (int i = 0; i < queslist.size(); i++) {
                quesum = quesum + pos;
            }
            Log.e(TAG, "quesum 4-- " + quesum);
        } else if (fragment_name.equals("hsppss")) {
            holder.txt_queId.setText(Integer.toString(position + 1));
            holder.txt_queTitle.setText(arraylist.get(position).getHsQues());
            for (int i = 0; i < queslist.size(); i++) {
                quesum = quesum + pos;
            }
            Log.e(TAG, "quesum 4-- " + quesum);
        } else if (fragment_name.equals("hsath")) {
            holder.txt_queId.setText(Integer.toString(position + 1));
            holder.txt_queTitle.setText(arraylist.get(position).getHsQues());
            for (int i = 0; i < queslist.size(); i++) {
                quesum = quesum + pos;
            }
            Log.e(TAG, "quesum 4-- " + quesum);
        } else if (fragment_name.equals("hspae")) {
            holder.txt_queId.setText(Integer.toString(position + 1));
            holder.txt_queTitle.setText(arraylist.get(position).getHsQues());

            for (int i = 0; i < queslist.size(); i++) {
                quesum = quesum + pos;
            }
            Log.e(TAG, "quesum 4-- " + quesum);
        } else if (fragment_name.equals("hsexcavtn")) {
            holder.txt_queId.setText(Integer.toString(position + 1));
            holder.txt_queTitle.setText(arraylist.get(position).getHsQues());
            for (int i = 0; i < queslist.size(); i++) {
                quesum = quesum + pos;
            }
            Log.e(TAG, "quesum 4-- " + quesum);
        } else if (fragment_name.equals("hsprtbltools")) {
            holder.txt_queId.setText(Integer.toString(position + 1));
            holder.txt_queTitle.setText(arraylist.get(position).getHsQues());
            for (int i = 0; i < queslist.size(); i++) {
                quesum = quesum + pos;
            }
            Log.e(TAG, "quesum 4-- " + quesum);
        } else if (fragment_name.equals("hscoshh")) {
            holder.txt_queId.setText(Integer.toString(position + 1));
            holder.txt_queTitle.setText(arraylist.get(position).getHsQues());
            for (int i = 0; i < queslist.size(); i++) {
                quesum = quesum + pos;
            }
            Log.e(TAG, "quesum 4-- " + quesum);
        } else if (fragment_name.equals("hsmh")) {
            holder.txt_queId.setText(Integer.toString(position + 1));
            holder.txt_queTitle.setText(arraylist.get(position).getHsQues());
            for (int i = 0; i < queslist.size(); i++) {
                quesum = quesum + pos;
            }
            Log.e(TAG, "quesum 4-- " + quesum);
        } else if (fragment_name.equals("hsconfspce")) {
            holder.txt_queId.setText(Integer.toString(position + 1));
            holder.txt_queTitle.setText(arraylist.get(position).getHsQues());
            for (int i = 0; i < queslist.size(); i++) {
                quesum = quesum + pos;
            }
            Log.e(TAG, "quesum 4-- " + quesum);
        } else if (fragment_name.equals("hsmprywrk")) {
            holder.txt_queId.setText(Integer.toString(position + 1));
            holder.txt_queTitle.setText(arraylist.get(position).getHsQues());
            for (int i = 0; i < queslist.size(); i++) {
                quesum = quesum + pos;
            }
            Log.e(TAG, "quesum 4-- " + quesum);
        } else if (fragment_name.equals("hscofsubcon")) {
            holder.txt_queId.setText(Integer.toString(position + 1));
            holder.txt_queTitle.setText(arraylist.get(position).getHsQues());
            for (int i = 0; i < queslist.size(); i++) {
                quesum = quesum + pos;
            }
            Log.e(TAG, "quesum 4-- " + quesum);
        } else if (fragment_name.equals("hsppe")) {
            holder.txt_queId.setText(Integer.toString(position + 1));
            holder.txt_queTitle.setText(arraylist.get(position).getHsQues());
            for (int i = 0; i < queslist.size(); i++) {
                quesum = quesum + pos;
            }
            Log.e(TAG, "quesum 4-- " + quesum);
        }

        hsism.setAns_label(arraylist.get(position).getHsLabel());
        hsism.setAns_ques(arraylist.get(position).getHsQues());
        hsism.setAns_ques_status("");
        hsism.setAns_comment("");
        hsquestiondataList.add(hsism);


        holder.ll_status.setOnClickListener(view -> {
            totale_point_same = totale_point;
            if (arraylist.size() == 1) {
                totale_point = 0;
                question_percentage = 0.00;
                total_failed = 0;
                MyAllData(holder, position);

            } else {
                MyAllData(holder, position);
            }

        });

        holder.edt_cmtHSEQ.setOnTouchListener(new View.OnTouchListener() {
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

    }

    public void MyAllData(HSInspectnQuesAdapter.MyViewHolder holder, int pos) {
        dialog = new Dialog(mContext);
        dialog.getWindow();
        dialog.setTitle("Address");
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_hs_statuslist);
        dialog.setCancelable(true);
        ImageView btnclos = (ImageView) dialog.findViewById(R.id.img_close);
        ListView listView = (ListView) dialog.findViewById(R.id.lv_hslist);
        btnclos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        arrayList_status.clear();
        Observable<SpinnerModel> responseObservable = apiservice.get_allInspetionStatus();

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
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("error", "" + e.getMessage());
                    }

                    @Override
                    public void onNext(SpinnerModel model) {//
                        statusCode = model.getStatusCode();
                        if (statusCode == 200) {
                            List<SpinnerModel.Data> res = model.getData();
                            for (int i = 0; i < res.size(); i++) {
                                arrayList_status.add(res.get(i).getInsName());
                            }
                            array = arrayList_status.toArray(new String[0]);
                            ArrayAdapter arrayAdapter = new ArrayAdapter(mContext, R.layout.list_item, R.id.tv, array);
                            listView.setAdapter(arrayAdapter);
                        }
                    }
                });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view1, int position, long id) {


                if (array[position].equals("Good")) {

                    holder.ll_hsstatus.setBackgroundColor(Color.parseColor("#4CAF50"));
                    holder.txt_hs_status.setTextColor(Color.parseColor("#FFFFFF"));
                    holder.txt_hs_status.setText(array[position]);
                    hs_status = array[position];
                    try {
                        hsquestiondataList.get(pos).setAns_ques_status(array[position]);
                        dataaList.get(pos).setAns_ques_points("40");
                        dataaList.get(pos).setAns_failed("0");

                    } catch (Exception e) {

                    }

                } else if (array[position].equals("Immediate action required")) {
                    holder.ll_hsstatus.setBackgroundColor(Color.parseColor("#FFFC2424"));
                    holder.txt_hs_status.setTextColor(Color.parseColor("#FFFFFF"));
                    holder.txt_hs_status.setText(array[position]);
                    hs_status = array[position];

                    try {

                        hsquestiondataList.get(pos).setAns_ques_status(array[position]);
                        dataaList.get(pos).setAns_ques_points("-10");
                        dataaList.get(pos).setAns_failed("0");


                    } catch (Exception e) {

                    }


                } else if (array[position].equals("Improvement Required")) {
                    holder.ll_hsstatus.setBackgroundColor(Color.parseColor("#FD9832"));
                    holder.txt_hs_status.setTextColor(Color.parseColor("#FFFFFF"));
                    holder.txt_hs_status.setText(array[position]);
                    hs_status = array[position];

                    try {

                        hsquestiondataList.get(pos).setAns_ques_status(array[position]);
                        dataaList.get(pos).setAns_failed("1");
                        dataaList.get(pos).setAns_ques_points("1");

                    } catch (Exception e) {

                    }

                } else if (array[position].equals("Minor Infringement")) {
                    holder.ll_hsstatus.setBackgroundColor(Color.parseColor("#FFFF56"));
                    holder.txt_hs_status.setTextColor(Color.parseColor("#000000"));
                    holder.txt_hs_status.setText(array[position]);
                    hs_status = array[position];
                    try {

                        hsquestiondataList.get(pos).setAns_ques_status(array[position]);
                        dataaList.get(pos).setAns_ques_points("20");
                        dataaList.get(pos).setAns_failed("0");

                    } catch (Exception e) {

                    }

                } else if (array[position].equals("N/A")) {

                    holder.ll_hsstatus.setBackgroundColor(Color.parseColor("#777777"));
                    holder.txt_hs_status.setTextColor(Color.parseColor("#FFFFFF"));
                    holder.txt_hs_status.setText(array[position]);
                    hs_status = array[position];

                    try {

                        hsquestiondataList.get(pos).setAns_ques_status(array[position]);
                        dataaList.get(pos).setAns_ques_points("40");
                        dataaList.get(pos).setAns_failed("0");

                    } catch (Exception e) {

                    }

                } else {
                    holder.ll_hsstatus.setBackgroundColor(Color.parseColor("#4CAF50"));
                    holder.txt_hs_status.setTextColor(Color.parseColor("#FFFFFF"));
                    holder.txt_hs_status.setText(array[position]);
                    hs_status = array[position];

                }

                dialog.dismiss();
                totale_point = 0;
                total_failed = 0;
                getdata(pos);
                String lok = "No";
                for (int i = 0; i < dataaList.size(); i++) {
                    if (dataaList.get(i).getAns_ques_points().equals("0")) {
                        lok = "No";
                        Log.e("Validataion is ", lok);
                    } else {
                        lok = "Yes";
                        Log.e("Validataion is ", lok);
                    }
                }
                listener.onItemClick(question_percentage, total_failed, lok);
            }
        });
        dialog.show();

    }

    //Shirish
    public void getdata(int pos) {

        totale_point = totale_point;
        for (int i = 0; i <= dataaList.size(); i++) {

            Log.e("item is ", String.valueOf(i));
            //quesum = quesum + pos;
            try {
                totale_point += Double.parseDouble(dataaList.get(i).getAns_ques_points());
                question_percentage = (totale_point * 100) / quesum;
                total_failed += Integer.parseInt(dataaList.get(i).getAns_failed());
                Log.e(TAG, "totale is " + String.valueOf(totale_point));
                Log.e(TAG, "question_percentage " + question_percentage);
                //model.setAns_ques_points_percentage(String.valueOf(question_percentage));

            } catch (Exception e) {

            }
        }
    }


    @Override
    public int getItemCount() {
        return arraylist.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.txt_queId)
        TextView txt_queId;
        @BindView(R.id.txt_queTitle)
        TextView txt_queTitle;
        @BindView(R.id.txt_hs_status)
        TextView txt_hs_status;
        @BindView(R.id.ll_hsstatus)
        LinearLayout ll_hsstatus;
        @BindView(R.id.ll_status)
        LinearLayout ll_status;
        @BindView(R.id.edt_cmtHSEQ)
        EditText edt_cmtHSEQ;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            edt_cmtHSEQ.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    hsquestiondataList.get(getAdapterPosition()).setAns_comment(edt_cmtHSEQ.getText().toString());
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
        }
    }
}
