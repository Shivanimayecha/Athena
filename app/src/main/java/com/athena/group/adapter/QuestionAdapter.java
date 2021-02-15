package com.athena.group.adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.athena.group.API.ApiInterface;
import com.athena.group.API.ApiServiceCreator;
import com.athena.group.Model.CommanModel;
import com.athena.group.Model.Example;
import com.athena.group.Model.FrgmentModel;
import com.athena.group.Model.SpinnerModel;
import com.athena.group.R;
import com.athena.group.application.Utility;
import com.google.gson.Gson;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Observer;
import rx.schedulers.Schedulers;

import static java.lang.String.valueOf;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.MyViewHolder> {

    FrgmentModel frgmentModel;
    ArrayAdapter arrayAdapter;
    ListView listView;

    public interface OnItemClickListener {
        //void onItemClick(Float item, int item1, String validation);
        void onItemClick(String validation);
    }

    OnItemClickListener listener;
    Context mContext;
    List<CommanModel> arraylist;
    private String fragment_name;
    String ans_ans = "";
    public static List<Example.questiondata> questiondataList = new ArrayList<>();
    List<String> queslist;
    public static String lok = "No";
    Example.questiondata e;
    private Dialog dialog;
    int statusCode;
    ApiInterface apiservice;
    ArrayList<String> arrayList_status = new ArrayList<>();
    String[] array;
    String hs_status = "";

    public QuestionAdapter(Context mContext, List<CommanModel> arraylist, String fragment_name, OnItemClickListener listener) {
        this.mContext = mContext;
        this.arraylist = arraylist;
        this.fragment_name = fragment_name;
        this.listener = listener;
        apiservice = ApiServiceCreator.createService("latest");
    }

    @NonNull
    @Override
    public QuestionAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.question_details, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionAdapter.MyViewHolder holder, int position) {

        queslist = new ArrayList<>();

        queslist.add(arraylist.get(position).getHsQues());
        e = new Example.questiondata();
        e.setAns_label(arraylist.get(position).getHsLabel());
        e.setAns_ques(arraylist.get(position).getHsQues());
        e.setAns_ques_value("");
        e.setAns_ques_value_no("");
        questiondataList.add(e);
        holder.ll_hsstatus.setVisibility(View.GONE);

        //Log.e("Position ", valueOf(position));
        //  Log.e("question label",arraylist.get(position).getHsLabel());
        //   Log.e("question name ",arraylist.get(position).getHsQues());

        try {
            if (fragment_name.equals("hseqinspection")) {
                holder.txt_queId.setText(Integer.toString(position + 1));
                holder.txt_queTitle.setText(arraylist.get(position).getHsQues());

                List<Example> arrayListQuestion1 = Utility.getAppcon().getSession().questiondataList1;
                Log.e("Qusetion 1234",arrayListQuestion1.get(0).getQuestions_hs().get(position).getAns_ques());
                Log.e("Qusetion Ans 1234",arrayListQuestion1.get(0).getQuestions_hs().get(position).getAns_ques_value());

                if (arrayListQuestion1.size() == 0) {

                } else {
                    if (arrayListQuestion1.get(0).getQuestions_hs().get(position).getAns_ques_value().equals("Yes")) {
                        for (int i = 0; i < questiondataList.size(); i++) {
                            holder.ll_hsstatus.setVisibility(View.GONE);
                            holder.rd1.setChecked(true);
                            questiondataList.get(position).setAns_ques(arrayListQuestion1.get(0).getQuestions_hs().get(i).getAns_ques());

                            questiondataList.get(position).setAns_ques_value(arrayListQuestion1.get(0).getQuestions_hs().get(i).getAns_ques_value());
                            // questiondataList.get(0).setAns_ques_value_no(arrayListQuestion2.get(0).getQuestions_hs().get(position).getAns_ques_value_no());

                            questiondataList.get(i).setAns_ques_value_no("");
                            //Log.e("Value is 1",arrayListQuestion1.get(0).getQuestions_hs().get(i).getAns_ques_value());

                        }

                    } else if (arrayListQuestion1.get(0).getQuestions_hs().get(position).getAns_ques_value().equals("N/A")) {
                        for (int i = 0; i < questiondataList.size(); i++) {
                            holder.ll_hsstatus.setVisibility(View.GONE);
                            holder.rd3.setChecked(true);
                            questiondataList.get(position).setAns_ques(arrayListQuestion1.get(0).getQuestions_hs().get(i).getAns_ques());

                            questiondataList.get(position).setAns_ques_value(arrayListQuestion1.get(0).getQuestions_hs().get(i).getAns_ques_value());
                            // questiondataList.get(0).setAns_ques_value_no(arrayListQuestion2.get(0).getQuestions_hs().get(position).getAns_ques_value_no());

                            questiondataList.get(i).setAns_ques_value_no("");
                            //Log.e("Value is 1",arrayListQuestion1.get(0).getQuestions_hs().get(i).getAns_ques_value());

                        }
                    } else if (arrayListQuestion1.get(0).getQuestions_hs().get(position).getAns_ques_value().equals("No")) {
                        for (int i = 0; i < questiondataList.size(); i++) {
                            holder.ll_hsstatus.setVisibility(View.VISIBLE);
                            holder.rd2.setChecked(true);

                            questiondataList.get(position).setAns_ques(arrayListQuestion1.get(0).getQuestions_hs().get(i).getAns_ques());

                            questiondataList.get(position).setAns_ques_value(arrayListQuestion1.get(0).getQuestions_hs().get(position).getAns_ques_value());

                            String q_value = arrayListQuestion1.get(0).getQuestions_hs().get(i).getAns_ques_value_no();
                            if (q_value.equals("Serious/Imminent danger")) {

                                holder.ll_hsstatus.setBackgroundColor(Color.parseColor("#FE7D5D"));
                                holder.txt_hs_status.setTextColor(Color.parseColor("#FFFFFF"));
                                holder.txt_hs_status.setText("Serious/Imminent danger");
                                questiondataList.get(i).setAns_ques_value_no("Serious/Imminent danger");
                            } else if (q_value.equals("Breach of leglslation")) {
                                holder.ll_hsstatus.setBackgroundColor(Color.parseColor("#FFD352"));
                                holder.txt_hs_status.setTextColor(Color.parseColor("#FFFFFF"));
                                ;
                                holder.txt_hs_status.setText("Breach of leglslation");
                                questiondataList.get(i).setAns_ques_value_no("Breach of leglslation");
                            } else if (q_value.equals("Does not meet HSE guidance or procedures")) {
                                holder.ll_hsstatus.setBackgroundColor(Color.parseColor("#41CDB3"));
                                holder.txt_hs_status.setTextColor(Color.parseColor("#FFFFFF"));
                                holder.txt_hs_status.setText("Does not meet HSE guidance or procedures");
                                questiondataList.get(i).setAns_ques_value_no("Does not meet HSE guidance or procedures");
                            } else if (q_value.equals("Discussed/Advice")) {
                                holder.ll_hsstatus.setBackgroundColor(Color.parseColor("#004B68"));
                                holder.txt_hs_status.setTextColor(Color.parseColor("#FFFFFF"));
                                holder.txt_hs_status.setText("Discussed/Advice");
                                questiondataList.get(i).setAns_ques_value_no("Discussed/Advice");
                            }


                            //Listdataset(q_value,holder.ll_hsstatus,holder.txt_hs_status);
                            //Log.e("Value is 2",arrayListQuestion1.get(0).getQuestions_hs().get(i).getAns_ques_value());
                        }
                    }
                }

            } else if (fragment_name.equals("inspctnreporting")) {
                holder.txt_queId.setText(Integer.toString(position + 1));
                holder.txt_queTitle.setText(arraylist.get(position).getHsQues());
                List<Example> arrayListQuestion = Utility.getAppcon().getSession().questiondataList2;
                if (arrayListQuestion.size() == 0) {

                } else {
                    if (arrayListQuestion.get(0).getQuestions_hs().get(position).getAns_ques_value().equals("Yes")) {
                        for (int i = 0; i < questiondataList.size(); i++) {
                            holder.ll_hsstatus.setVisibility(View.GONE);
                            holder.rd1.setChecked(true);
                            questiondataList.get(position).setAns_ques(arrayListQuestion.get(0).getQuestions_hs().get(i).getAns_ques());

                            questiondataList.get(position).setAns_ques_value(arrayListQuestion.get(0).getQuestions_hs().get(i).getAns_ques_value());
                            // questiondataList.get(0).setAns_ques_value_no(arrayListQuestion2.get(0).getQuestions_hs().get(position).getAns_ques_value_no());

                            questiondataList.get(position).setAns_ques_value_no("");
                            //Log.e("Value is 1",arrayListQuestion1.get(0).getQuestions_hs().get(i).getAns_ques_value());

                        }

                    } else if (arrayListQuestion.get(0).getQuestions_hs().get(position).getAns_ques_value().equals("N/A")) {
                        for (int i = 0; i < questiondataList.size(); i++) {
                            holder.ll_hsstatus.setVisibility(View.GONE);
                            holder.rd3.setChecked(true);
                            questiondataList.get(position).setAns_ques(arrayListQuestion.get(0).getQuestions_hs().get(i).getAns_ques());

                            questiondataList.get(position).setAns_ques_value(arrayListQuestion.get(0).getQuestions_hs().get(i).getAns_ques_value());
                            // questiondataList.get(0).setAns_ques_value_no(arrayListQuestion2.get(0).getQuestions_hs().get(position).getAns_ques_value_no());

                            questiondataList.get(position).setAns_ques_value_no("");
                            //Log.e("Value is 1",arrayListQuestion1.get(0).getQuestions_hs().get(i).getAns_ques_value());

                        }
                    } else if (arrayListQuestion.get(0).getQuestions_hs().get(position).getAns_ques_value().equals("No")) {
                        for (int i = 0; i < questiondataList.size(); i++) {
                            holder.ll_hsstatus.setVisibility(View.VISIBLE);
                            holder.rd2.setChecked(true);

                            questiondataList.get(position).setAns_ques(arrayListQuestion.get(0).getQuestions_hs().get(i).getAns_ques());

                            questiondataList.get(position).setAns_ques_value(arrayListQuestion.get(0).getQuestions_hs().get(position).getAns_ques_value());

                            String q_value = arrayListQuestion.get(0).getQuestions_hs().get(i).getAns_ques_value_no();
                            if (q_value.equals("Serious/Imminent danger")) {

                                holder.ll_hsstatus.setBackgroundColor(Color.parseColor("#FE7D5D"));
                                holder.txt_hs_status.setTextColor(Color.parseColor("#FFFFFF"));
                                holder.txt_hs_status.setText("Serious/Imminent danger");
                                questiondataList.get(i).setAns_ques_value_no("Serious/Imminent danger");
                            } else if (q_value.equals("Breach of leglslation")) {
                                holder.ll_hsstatus.setBackgroundColor(Color.parseColor("#FFD352"));
                                holder.txt_hs_status.setTextColor(Color.parseColor("#FFFFFF"));
                                ;
                                holder.txt_hs_status.setText("Breach of leglslation");
                                questiondataList.get(i).setAns_ques_value_no("Breach of leglslation");
                            } else if (q_value.equals("Does not meet HSE guidance or procedures")) {
                                holder.ll_hsstatus.setBackgroundColor(Color.parseColor("#41CDB3"));
                                holder.txt_hs_status.setTextColor(Color.parseColor("#FFFFFF"));
                                holder.txt_hs_status.setText("Does not meet HSE guidance or procedures");
                                questiondataList.get(i).setAns_ques_value_no("Does not meet HSE guidance or procedures");
                            } else if (q_value.equals("Discussed/Advice")) {
                                holder.ll_hsstatus.setBackgroundColor(Color.parseColor("#004B68"));
                                holder.txt_hs_status.setTextColor(Color.parseColor("#FFFFFF"));
                                holder.txt_hs_status.setText("Discussed/Advice");
                                questiondataList.get(i).setAns_ques_value_no("Discussed/Advice");
                            }


                            //Listdataset(q_value,holder.ll_hsstatus,holder.txt_hs_status);
                            //Log.e("Value is 2",arrayListQuestion1.get(0).getQuestions_hs().get(i).getAns_ques_value());
                        }
                    }
                }

            } else if (fragment_name.equals("documentation")) {
                holder.txt_queId.setText(Integer.toString(position + 1));
                holder.txt_queTitle.setText(arraylist.get(position).getHsQues());
                List<Example> arrayListQuestion2 = Utility.getAppcon().getSession().questiondataList3;
                if (arrayListQuestion2.size() == 0) {

                } else {
                    if (arrayListQuestion2.get(0).getQuestions_hs().get(position).getAns_ques_value().equals("Yes")) {
                        for (int i = 0; i < questiondataList.size(); i++) {
                            holder.ll_hsstatus.setVisibility(View.GONE);
                            holder.rd1.setChecked(true);
                            questiondataList.get(position).setAns_ques(arrayListQuestion2.get(0).getQuestions_hs().get(i).getAns_ques());

                            questiondataList.get(position).setAns_ques_value(arrayListQuestion2.get(0).getQuestions_hs().get(i).getAns_ques_value());
                            // questiondataList.get(0).setAns_ques_value_no(arrayListQuestion2.get(0).getQuestions_hs().get(position).getAns_ques_value_no());

                            questiondataList.get(position).setAns_ques_value_no("");
                            //Log.e("Value is 1",arrayListQuestion1.get(0).getQuestions_hs().get(i).getAns_ques_value());

                        }

                    } else if (arrayListQuestion2.get(0).getQuestions_hs().get(position).getAns_ques_value().equals("N/A")) {
                        for (int i = 0; i < questiondataList.size(); i++) {
                            holder.ll_hsstatus.setVisibility(View.GONE);
                            holder.rd3.setChecked(true);
                            questiondataList.get(position).setAns_ques(arrayListQuestion2.get(0).getQuestions_hs().get(i).getAns_ques());

                            questiondataList.get(position).setAns_ques_value(arrayListQuestion2.get(0).getQuestions_hs().get(i).getAns_ques_value());
                            // questiondataList.get(0).setAns_ques_value_no(arrayListQuestion2.get(0).getQuestions_hs().get(position).getAns_ques_value_no());

                            questiondataList.get(position).setAns_ques_value_no("");
                            //Log.e("Value is 1",arrayListQuestion1.get(0).getQuestions_hs().get(i).getAns_ques_value());

                        }
                    } else if (arrayListQuestion2.get(0).getQuestions_hs().get(position).getAns_ques_value().equals("No")) {
                        for (int i = 0; i < questiondataList.size(); i++) {
                            holder.ll_hsstatus.setVisibility(View.VISIBLE);
                            holder.rd2.setChecked(true);

                            questiondataList.get(position).setAns_ques(arrayListQuestion2.get(0).getQuestions_hs().get(i).getAns_ques());

                            questiondataList.get(position).setAns_ques_value(arrayListQuestion2.get(0).getQuestions_hs().get(position).getAns_ques_value());

                            String q_value = arrayListQuestion2.get(0).getQuestions_hs().get(i).getAns_ques_value_no();
                            if (q_value.equals("Serious/Imminent danger")) {

                                holder.ll_hsstatus.setBackgroundColor(Color.parseColor("#FE7D5D"));
                                holder.txt_hs_status.setTextColor(Color.parseColor("#FFFFFF"));
                                holder.txt_hs_status.setText("Serious/Imminent danger");
                                questiondataList.get(i).setAns_ques_value_no("Serious/Imminent danger");
                            } else if (q_value.equals("Breach of leglslation")) {
                                holder.ll_hsstatus.setBackgroundColor(Color.parseColor("#FFD352"));
                                holder.txt_hs_status.setTextColor(Color.parseColor("#FFFFFF"));
                                holder.txt_hs_status.setText("Breach of leglslation");
                                questiondataList.get(i).setAns_ques_value_no("Breach of leglslation");
                            } else if (q_value.equals("Does not meet HSE guidance or procedures")) {
                                holder.ll_hsstatus.setBackgroundColor(Color.parseColor("#41CDB3"));
                                holder.txt_hs_status.setTextColor(Color.parseColor("#FFFFFF"));
                                holder.txt_hs_status.setText("Does not meet HSE guidance or procedures");
                                questiondataList.get(i).setAns_ques_value_no("Does not meet HSE guidance or procedures");
                            } else if (q_value.equals("Discussed/Advice")) {
                                holder.ll_hsstatus.setBackgroundColor(Color.parseColor("#004B68"));
                                holder.txt_hs_status.setTextColor(Color.parseColor("#FFFFFF"));
                                holder.txt_hs_status.setText("Discussed/Advice");
                                questiondataList.get(i).setAns_ques_value_no("Discussed/Advice");
                            }


                            //Listdataset(q_value,holder.ll_hsstatus,holder.txt_hs_status);
                            //Log.e("Value is 2",arrayListQuestion1.get(0).getQuestions_hs().get(i).getAns_ques_value());
                        }
                    }
                }

            } else if (fragment_name.equals("competencetraning")) {
                holder.txt_queId.setText(Integer.toString(position + 1));
                holder.txt_queTitle.setText(arraylist.get(position).getHsQues());
                List<Example> arrayListQuestion3 = Utility.getAppcon().getSession().questiondataList4;

                if (arrayListQuestion3.size() == 0) {

                } else {
                    if (arrayListQuestion3.get(0).getQuestions_hs().get(position).getAns_ques_value().equals("Yes")) {
                        for (int i = 0; i < questiondataList.size(); i++) {
                            holder.ll_hsstatus.setVisibility(View.GONE);
                            holder.rd1.setChecked(true);
                            questiondataList.get(position).setAns_ques(arrayListQuestion3.get(0).getQuestions_hs().get(i).getAns_ques());

                            questiondataList.get(position).setAns_ques_value(arrayListQuestion3.get(0).getQuestions_hs().get(i).getAns_ques_value());
                            // questiondataList.get(0).setAns_ques_value_no(arrayListQuestion2.get(0).getQuestions_hs().get(position).getAns_ques_value_no());

                            questiondataList.get(position).setAns_ques_value_no("");
                            //Log.e("Value is 1",arrayListQuestion1.get(0).getQuestions_hs().get(i).getAns_ques_value());

                        }

                    } else if (arrayListQuestion3.get(0).getQuestions_hs().get(position).getAns_ques_value().equals("N/A")) {
                        for (int i = 0; i < questiondataList.size(); i++) {
                            holder.ll_hsstatus.setVisibility(View.GONE);
                            holder.rd3.setChecked(true);
                            questiondataList.get(position).setAns_ques(arrayListQuestion3.get(0).getQuestions_hs().get(i).getAns_ques());

                            questiondataList.get(position).setAns_ques_value(arrayListQuestion3.get(0).getQuestions_hs().get(i).getAns_ques_value());
                            // questiondataList.get(0).setAns_ques_value_no(arrayListQuestion2.get(0).getQuestions_hs().get(position).getAns_ques_value_no());

                            questiondataList.get(position).setAns_ques_value_no("");
                            //Log.e("Value is 1",arrayListQuestion1.get(0).getQuestions_hs().get(i).getAns_ques_value());

                        }
                    } else if (arrayListQuestion3.get(0).getQuestions_hs().get(position).getAns_ques_value().equals("No")) {
                        for (int i = 0; i < questiondataList.size(); i++) {
                            holder.ll_hsstatus.setVisibility(View.VISIBLE);
                            holder.rd2.setChecked(true);
                            Log.e("Question is ",arrayListQuestion3.get(0).getQuestions_hs().get(i).getAns_ques());
                            questiondataList.get(position).setAns_ques(arrayListQuestion3.get(0).getQuestions_hs().get(i).getAns_ques());

                            questiondataList.get(position).setAns_ques_value(arrayListQuestion3.get(0).getQuestions_hs().get(position).getAns_ques_value());

                            String q_value = arrayListQuestion3.get(0).getQuestions_hs().get(i).getAns_ques_value_no();
                            if (q_value.equals("Serious/Imminent danger")) {

                                holder.ll_hsstatus.setBackgroundColor(Color.parseColor("#FE7D5D"));
                                holder.txt_hs_status.setTextColor(Color.parseColor("#FFFFFF"));
                                holder.txt_hs_status.setText("Serious/Imminent danger");
                                questiondataList.get(i).setAns_ques_value_no("Serious/Imminent danger");
                            } else if (q_value.equals("Breach of leglslation")) {
                                holder.ll_hsstatus.setBackgroundColor(Color.parseColor("#FFD352"));
                                holder.txt_hs_status.setTextColor(Color.parseColor("#FFFFFF"));
                                ;
                                holder.txt_hs_status.setText("Breach of leglslation");
                                questiondataList.get(i).setAns_ques_value_no("Breach of leglslation");
                            } else if (q_value.equals("Does not meet HSE guidance or procedures")) {
                                holder.ll_hsstatus.setBackgroundColor(Color.parseColor("#41CDB3"));
                                holder.txt_hs_status.setTextColor(Color.parseColor("#FFFFFF"));
                                holder.txt_hs_status.setText("Does not meet HSE guidance or procedures");
                                questiondataList.get(i).setAns_ques_value_no("Does not meet HSE guidance or procedures");
                            } else if (q_value.equals("Discussed/Advice")) {
                                holder.ll_hsstatus.setBackgroundColor(Color.parseColor("#004B68"));
                                holder.txt_hs_status.setTextColor(Color.parseColor("#FFFFFF"));
                                holder.txt_hs_status.setText("Discussed/Advice");
                                questiondataList.get(i).setAns_ques_value_no("Discussed/Advice");
                            }


                            //Listdataset(q_value,holder.ll_hsstatus,holder.txt_hs_status);
                            //Log.e("Value is 2",arrayListQuestion1.get(0).getQuestions_hs().get(i).getAns_ques_value());
                        }
                    }
                }

            } else if (fragment_name.equals("welfare")) {
                holder.txt_queId.setText(Integer.toString(position + 1));
                holder.txt_queTitle.setText(arraylist.get(position).getHsQues());

                List<Example> arrayListQuestionWalfr = Utility.getAppcon().getSession().questiondataList5;
                if (arrayListQuestionWalfr.size() == 0) {

                } else {
                    if (arrayListQuestionWalfr.get(0).getQuestions_hs().get(position).getAns_ques_value().equals("Yes")) {
                        for (int i = 0; i < questiondataList.size(); i++) {
                            holder.ll_hsstatus.setVisibility(View.GONE);
                            holder.rd1.setChecked(true);
                            questiondataList.get(position).setAns_ques(arrayListQuestionWalfr.get(0).getQuestions_hs().get(i).getAns_ques());

                            questiondataList.get(position).setAns_ques_value(arrayListQuestionWalfr.get(0).getQuestions_hs().get(i).getAns_ques_value());
                            // questiondataList.get(0).setAns_ques_value_no(arrayListQuestion2.get(0).getQuestions_hs().get(position).getAns_ques_value_no());

                            questiondataList.get(position).setAns_ques_value_no("");
                            //Log.e("Value is 1",arrayListQuestion1.get(0).getQuestions_hs().get(i).getAns_ques_value());

                        }

                    } else if (arrayListQuestionWalfr.get(0).getQuestions_hs().get(position).getAns_ques_value().equals("N/A")) {
                        for (int i = 0; i < questiondataList.size(); i++) {
                            holder.ll_hsstatus.setVisibility(View.GONE);
                            holder.rd3.setChecked(true);
                            questiondataList.get(position).setAns_ques(arrayListQuestionWalfr.get(0).getQuestions_hs().get(i).getAns_ques());

                            questiondataList.get(position).setAns_ques_value(arrayListQuestionWalfr.get(0).getQuestions_hs().get(i).getAns_ques_value());
                            // questiondataList.get(0).setAns_ques_value_no(arrayListQuestion2.get(0).getQuestions_hs().get(position).getAns_ques_value_no());

                            questiondataList.get(position).setAns_ques_value_no("");
                            //Log.e("Value is 1",arrayListQuestion1.get(0).getQuestions_hs().get(i).getAns_ques_value());

                        }
                    } else if (arrayListQuestionWalfr.get(0).getQuestions_hs().get(position).getAns_ques_value().equals("No")) {
                        for (int i = 0; i < questiondataList.size(); i++) {
                            holder.ll_hsstatus.setVisibility(View.VISIBLE);
                            holder.rd2.setChecked(true);

                            questiondataList.get(position).setAns_ques(arrayListQuestionWalfr.get(0).getQuestions_hs().get(i).getAns_ques());

                            questiondataList.get(position).setAns_ques_value(arrayListQuestionWalfr.get(0).getQuestions_hs().get(position).getAns_ques_value());

                            String q_value = arrayListQuestionWalfr.get(0).getQuestions_hs().get(i).getAns_ques_value_no();
                            if (q_value.equals("Serious/Imminent danger")) {

                                holder.ll_hsstatus.setBackgroundColor(Color.parseColor("#FE7D5D"));
                                holder.txt_hs_status.setTextColor(Color.parseColor("#FFFFFF"));
                                holder.txt_hs_status.setText("Serious/Imminent danger");
                                questiondataList.get(i).setAns_ques_value_no("Serious/Imminent danger");
                            } else if (q_value.equals("Breach of leglslation")) {
                                holder.ll_hsstatus.setBackgroundColor(Color.parseColor("#FFD352"));
                                holder.txt_hs_status.setTextColor(Color.parseColor("#FFFFFF"));
                                ;
                                holder.txt_hs_status.setText("Breach of leglslation");
                                questiondataList.get(i).setAns_ques_value_no("Breach of leglslation");
                            } else if (q_value.equals("Does not meet HSE guidance or procedures")) {
                                holder.ll_hsstatus.setBackgroundColor(Color.parseColor("#41CDB3"));
                                holder.txt_hs_status.setTextColor(Color.parseColor("#FFFFFF"));
                                holder.txt_hs_status.setText("Does not meet HSE guidance or procedures");
                                questiondataList.get(i).setAns_ques_value_no("Does not meet HSE guidance or procedures");
                            } else if (q_value.equals("Discussed/Advice")) {
                                holder.ll_hsstatus.setBackgroundColor(Color.parseColor("#004B68"));
                                holder.txt_hs_status.setTextColor(Color.parseColor("#FFFFFF"));
                                holder.txt_hs_status.setText("Discussed/Advice");
                                questiondataList.get(i).setAns_ques_value_no("Discussed/Advice");
                            }


                            //Listdataset(q_value,holder.ll_hsstatus,holder.txt_hs_status);
                            //Log.e("Value is 2",arrayListQuestion1.get(0).getQuestions_hs().get(i).getAns_ques_value());
                        }
                    }
                }
            } else if (fragment_name.equals("emergencypro")) {
                holder.txt_queId.setText(Integer.toString(position + 1));
                holder.txt_queTitle.setText(arraylist.get(position).getHsQues());

                List<Example> arrayListQuestionEmrpro = Utility.getAppcon().getSession().questiondataList6;
                if (arrayListQuestionEmrpro.size() == 0) {

                } else {
                    if (arrayListQuestionEmrpro.get(0).getQuestions_hs().get(position).getAns_ques_value().equals("Yes")) {
                        for (int i = 0; i < questiondataList.size(); i++) {
                            holder.ll_hsstatus.setVisibility(View.GONE);
                            holder.rd1.setChecked(true);
                            questiondataList.get(position).setAns_ques(arrayListQuestionEmrpro.get(0).getQuestions_hs().get(i).getAns_ques());

                            questiondataList.get(position).setAns_ques_value(arrayListQuestionEmrpro.get(0).getQuestions_hs().get(i).getAns_ques_value());
                            // questiondataList.get(0).setAns_ques_value_no(arrayListQuestion2.get(0).getQuestions_hs().get(position).getAns_ques_value_no());

                            questiondataList.get(position).setAns_ques_value_no("");
                            //Log.e("Value is 1",arrayListQuestion1.get(0).getQuestions_hs().get(i).getAns_ques_value());

                        }

                    } else if (arrayListQuestionEmrpro.get(0).getQuestions_hs().get(position).getAns_ques_value().equals("N/A")) {
                        for (int i = 0; i < questiondataList.size(); i++) {
                            holder.ll_hsstatus.setVisibility(View.GONE);
                            holder.rd3.setChecked(true);
                            Log.e("arrayListQuestionEmrpro--", new Gson().toJson(arrayListQuestionEmrpro));

                            questiondataList.get(position).setAns_ques(arrayListQuestionEmrpro.get(0).getQuestions_hs().get(i).getAns_ques());

                            questiondataList.get(position).setAns_ques_value(arrayListQuestionEmrpro.get(0).getQuestions_hs().get(i).getAns_ques_value());
                            // questiondataList.get(0).setAns_ques_value_no(arrayListQuestion2.get(0).getQuestions_hs().get(position).getAns_ques_value_no());

                            questiondataList.get(position).setAns_ques_value_no("");
                            //Log.e("Value is 1",arrayListQuestion1.get(0).getQuestions_hs().get(i).getAns_ques_value());

                        }
                    } else if (arrayListQuestionEmrpro.get(0).getQuestions_hs().get(position).getAns_ques_value().equals("No")) {
                        for (int i = 0; i < questiondataList.size(); i++) {
                            holder.ll_hsstatus.setVisibility(View.VISIBLE);
                            holder.rd2.setChecked(true);

                            questiondataList.get(position).setAns_ques(arrayListQuestionEmrpro.get(0).getQuestions_hs().get(i).getAns_ques());

                            questiondataList.get(position).setAns_ques_value(arrayListQuestionEmrpro.get(0).getQuestions_hs().get(position).getAns_ques_value());

                            String q_value = arrayListQuestionEmrpro.get(0).getQuestions_hs().get(i).getAns_ques_value_no();
                            if (q_value.equals("Serious/Imminent danger")) {

                                holder.ll_hsstatus.setBackgroundColor(Color.parseColor("#FE7D5D"));
                                holder.txt_hs_status.setTextColor(Color.parseColor("#FFFFFF"));
                                holder.txt_hs_status.setText("Serious/Imminent danger");
                                questiondataList.get(i).setAns_ques_value_no("Serious/Imminent danger");
                            } else if (q_value.equals("Breach of leglslation")) {
                                holder.ll_hsstatus.setBackgroundColor(Color.parseColor("#FFD352"));
                                holder.txt_hs_status.setTextColor(Color.parseColor("#FFFFFF"));
                                ;
                                holder.txt_hs_status.setText("Breach of leglslation");
                                questiondataList.get(i).setAns_ques_value_no("Breach of leglslation");
                            } else if (q_value.equals("Does not meet HSE guidance or procedures")) {
                                holder.ll_hsstatus.setBackgroundColor(Color.parseColor("#41CDB3"));
                                holder.txt_hs_status.setTextColor(Color.parseColor("#FFFFFF"));
                                holder.txt_hs_status.setText("Does not meet HSE guidance or procedures");
                                questiondataList.get(i).setAns_ques_value_no("Does not meet HSE guidance or procedures");
                            } else if (q_value.equals("Discussed/Advice")) {
                                holder.ll_hsstatus.setBackgroundColor(Color.parseColor("#004B68"));
                                holder.txt_hs_status.setTextColor(Color.parseColor("#FFFFFF"));
                                holder.txt_hs_status.setText("Discussed/Advice");
                                questiondataList.get(i).setAns_ques_value_no("Discussed/Advice");
                            }


                            //Listdataset(q_value,holder.ll_hsstatus,holder.txt_hs_status);
                            //Log.e("Value is 2",arrayListQuestion1.get(0).getQuestions_hs().get(i).getAns_ques_value());
                        }
                    }
                }
            } else if (fragment_name.equals("rams")) {
                holder.txt_queId.setText(Integer.toString(position + 1));
                holder.txt_queTitle.setText(arraylist.get(position).getHsQues());

                List<Example> arrayListQuestionRams = Utility.getAppcon().getSession().questiondataList7;
                if (arrayListQuestionRams.size() == 0) {

                } else {
                    if (arrayListQuestionRams.get(0).getQuestions_hs().get(position).getAns_ques_value().equals("Yes")) {
                        for (int i = 0; i < questiondataList.size(); i++) {
                            holder.ll_hsstatus.setVisibility(View.GONE);
                            holder.rd1.setChecked(true);
                            questiondataList.get(position).setAns_ques(arrayListQuestionRams.get(0).getQuestions_hs().get(i).getAns_ques());

                            questiondataList.get(position).setAns_ques_value(arrayListQuestionRams.get(0).getQuestions_hs().get(i).getAns_ques_value());
                            // questiondataList.get(0).setAns_ques_value_no(arrayListQuestion2.get(0).getQuestions_hs().get(position).getAns_ques_value_no());

                            questiondataList.get(position).setAns_ques_value_no("");
                            //Log.e("Value is 1",arrayListQuestion1.get(0).getQuestions_hs().get(i).getAns_ques_value());

                        }

                    } else if (arrayListQuestionRams.get(0).getQuestions_hs().get(position).getAns_ques_value().equals("N/A")) {
                        for (int i = 0; i < questiondataList.size(); i++) {
                            holder.ll_hsstatus.setVisibility(View.GONE);
                            holder.rd3.setChecked(true);
                            questiondataList.get(position).setAns_ques(arrayListQuestionRams.get(0).getQuestions_hs().get(i).getAns_ques());

                            questiondataList.get(position).setAns_ques_value(arrayListQuestionRams.get(0).getQuestions_hs().get(i).getAns_ques_value());
                            // questiondataList.get(0).setAns_ques_value_no(arrayListQuestion2.get(0).getQuestions_hs().get(position).getAns_ques_value_no());

                            questiondataList.get(position).setAns_ques_value_no("");
                            //Log.e("Value is 1",arrayListQuestion1.get(0).getQuestions_hs().get(i).getAns_ques_value());

                        }
                    } else if (arrayListQuestionRams.get(0).getQuestions_hs().get(position).getAns_ques_value().equals("No")) {
                        for (int i = 0; i < questiondataList.size(); i++) {
                            holder.ll_hsstatus.setVisibility(View.VISIBLE);
                            holder.rd2.setChecked(true);

                            questiondataList.get(position).setAns_ques(arrayListQuestionRams.get(0).getQuestions_hs().get(i).getAns_ques());

                            questiondataList.get(position).setAns_ques_value(arrayListQuestionRams.get(0).getQuestions_hs().get(position).getAns_ques_value());

                            String q_value = arrayListQuestionRams.get(0).getQuestions_hs().get(i).getAns_ques_value_no();
                            if (q_value.equals("Serious/Imminent danger")) {

                                holder.ll_hsstatus.setBackgroundColor(Color.parseColor("#FE7D5D"));
                                holder.txt_hs_status.setTextColor(Color.parseColor("#FFFFFF"));
                                holder.txt_hs_status.setText("Serious/Imminent danger");
                                questiondataList.get(i).setAns_ques_value_no("Serious/Imminent danger");
                            } else if (q_value.equals("Breach of leglslation")) {
                                holder.ll_hsstatus.setBackgroundColor(Color.parseColor("#FFD352"));
                                holder.txt_hs_status.setTextColor(Color.parseColor("#FFFFFF"));
                                ;
                                holder.txt_hs_status.setText("Breach of leglslation");
                                questiondataList.get(i).setAns_ques_value_no("Breach of leglslation");
                            } else if (q_value.equals("Does not meet HSE guidance or procedures")) {
                                holder.ll_hsstatus.setBackgroundColor(Color.parseColor("#41CDB3"));
                                holder.txt_hs_status.setTextColor(Color.parseColor("#FFFFFF"));
                                holder.txt_hs_status.setText("Does not meet HSE guidance or procedures");
                                questiondataList.get(i).setAns_ques_value_no("Does not meet HSE guidance or procedures");
                            } else if (q_value.equals("Discussed/Advice")) {
                                holder.ll_hsstatus.setBackgroundColor(Color.parseColor("#004B68"));
                                holder.txt_hs_status.setTextColor(Color.parseColor("#FFFFFF"));
                                holder.txt_hs_status.setText("Discussed/Advice");
                                questiondataList.get(i).setAns_ques_value_no("Discussed/Advice");
                            }


                            //Listdataset(q_value,holder.ll_hsstatus,holder.txt_hs_status);
                            //Log.e("Value is 2",arrayListQuestion1.get(0).getQuestions_hs().get(i).getAns_ques_value());
                        }
                    }
                }
            } else if (fragment_name.equals("nb")) {
                holder.txt_queId.setText(Integer.toString(position + 1));
                holder.txt_queTitle.setText(arraylist.get(position).getHsQues());

                List<Example> arrayListQuestionNb = Utility.getAppcon().getSession().questiondataList8;
                if (arrayListQuestionNb.size() == 0) {

                } else {
                    if (arrayListQuestionNb.get(0).getQuestions_hs().get(position).getAns_ques_value().equals("Yes")) {
                        for (int i = 0; i < questiondataList.size(); i++) {
                            holder.ll_hsstatus.setVisibility(View.GONE);
                            holder.rd1.setChecked(true);
                            questiondataList.get(position).setAns_ques(arrayListQuestionNb.get(0).getQuestions_hs().get(i).getAns_ques());

                            questiondataList.get(position).setAns_ques_value(arrayListQuestionNb.get(0).getQuestions_hs().get(i).getAns_ques_value());
                            // questiondataList.get(0).setAns_ques_value_no(arrayListQuestion2.get(0).getQuestions_hs().get(position).getAns_ques_value_no());

                            questiondataList.get(position).setAns_ques_value_no("");
                            //Log.e("Value is 1",arrayListQuestion1.get(0).getQuestions_hs().get(i).getAns_ques_value());

                        }

                    } else if (arrayListQuestionNb.get(0).getQuestions_hs().get(position).getAns_ques_value().equals("N/A")) {
                        for (int i = 0; i < questiondataList.size(); i++) {
                            holder.ll_hsstatus.setVisibility(View.GONE);
                            holder.rd3.setChecked(true);
                            questiondataList.get(position).setAns_ques(arrayListQuestionNb.get(0).getQuestions_hs().get(i).getAns_ques());

                            questiondataList.get(position).setAns_ques_value(arrayListQuestionNb.get(0).getQuestions_hs().get(i).getAns_ques_value());
                            // questiondataList.get(0).setAns_ques_value_no(arrayListQuestion2.get(0).getQuestions_hs().get(position).getAns_ques_value_no());

                            questiondataList.get(position).setAns_ques_value_no("");
                            //Log.e("Value is 1",arrayListQuestion1.get(0).getQuestions_hs().get(i).getAns_ques_value());

                        }
                    } else if (arrayListQuestionNb.get(0).getQuestions_hs().get(position).getAns_ques_value().equals("No")) {
                        for (int i = 0; i < questiondataList.size(); i++) {
                            holder.ll_hsstatus.setVisibility(View.VISIBLE);
                            holder.rd2.setChecked(true);

                            questiondataList.get(position).setAns_ques(arrayListQuestionNb.get(0).getQuestions_hs().get(i).getAns_ques());

                            questiondataList.get(position).setAns_ques_value(arrayListQuestionNb.get(0).getQuestions_hs().get(position).getAns_ques_value());

                            String q_value = arrayListQuestionNb.get(0).getQuestions_hs().get(i).getAns_ques_value_no();
                            if (q_value.equals("Serious/Imminent danger")) {

                                holder.ll_hsstatus.setBackgroundColor(Color.parseColor("#FE7D5D"));
                                holder.txt_hs_status.setTextColor(Color.parseColor("#FFFFFF"));
                                holder.txt_hs_status.setText("Serious/Imminent danger");
                                questiondataList.get(i).setAns_ques_value_no("Serious/Imminent danger");
                            } else if (q_value.equals("Breach of leglslation")) {
                                holder.ll_hsstatus.setBackgroundColor(Color.parseColor("#FFD352"));
                                holder.txt_hs_status.setTextColor(Color.parseColor("#FFFFFF"));
                                ;
                                holder.txt_hs_status.setText("Breach of leglslation");
                                questiondataList.get(i).setAns_ques_value_no("Breach of leglslation");
                            } else if (q_value.equals("Does not meet HSE guidance or procedures")) {
                                holder.ll_hsstatus.setBackgroundColor(Color.parseColor("#41CDB3"));
                                holder.txt_hs_status.setTextColor(Color.parseColor("#FFFFFF"));
                                holder.txt_hs_status.setText("Does not meet HSE guidance or procedures");
                                questiondataList.get(i).setAns_ques_value_no("Does not meet HSE guidance or procedures");
                            } else if (q_value.equals("Discussed/Advice")) {
                                holder.ll_hsstatus.setBackgroundColor(Color.parseColor("#004B68"));
                                holder.txt_hs_status.setTextColor(Color.parseColor("#FFFFFF"));
                                holder.txt_hs_status.setText("Discussed/Advice");
                                questiondataList.get(i).setAns_ques_value_no("Discussed/Advice");
                            }


                            //Listdataset(q_value,holder.ll_hsstatus,holder.txt_hs_status);
                            //Log.e("Value is 2",arrayListQuestion1.get(0).getQuestions_hs().get(i).getAns_ques_value());
                        }
                    }
                }
            } else if (fragment_name.equals("PermitsToWork")) {
                holder.txt_queId.setText(Integer.toString(position + 1));
                holder.txt_queTitle.setText(arraylist.get(position).getHsQues());

                List<Example> arrayListQuestionPTW = Utility.getAppcon().getSession().questiondataList9;
                if (arrayListQuestionPTW.size() == 0) {

                } else {
                    if (arrayListQuestionPTW.get(0).getQuestions_hs().get(position).getAns_ques_value().equals("Yes")) {
                        for (int i = 0; i < questiondataList.size(); i++) {
                            holder.ll_hsstatus.setVisibility(View.GONE);
                            holder.rd1.setChecked(true);
                            questiondataList.get(position).setAns_ques(arrayListQuestionPTW.get(0).getQuestions_hs().get(i).getAns_ques());

                            questiondataList.get(position).setAns_ques_value(arrayListQuestionPTW.get(0).getQuestions_hs().get(i).getAns_ques_value());
                            // questiondataList.get(0).setAns_ques_value_no(arrayListQuestion2.get(0).getQuestions_hs().get(position).getAns_ques_value_no());

                            questiondataList.get(position).setAns_ques_value_no("");
                            //Log.e("Value is 1",arrayListQuestion1.get(0).getQuestions_hs().get(i).getAns_ques_value());

                        }

                    } else if (arrayListQuestionPTW.get(0).getQuestions_hs().get(position).getAns_ques_value().equals("N/A")) {
                        for (int i = 0; i < questiondataList.size(); i++) {
                            holder.ll_hsstatus.setVisibility(View.GONE);
                            holder.rd3.setChecked(true);
                            questiondataList.get(position).setAns_ques(arrayListQuestionPTW.get(0).getQuestions_hs().get(i).getAns_ques());

                            questiondataList.get(position).setAns_ques_value(arrayListQuestionPTW.get(0).getQuestions_hs().get(i).getAns_ques_value());
                            // questiondataList.get(0).setAns_ques_value_no(arrayListQuestion2.get(0).getQuestions_hs().get(position).getAns_ques_value_no());

                            questiondataList.get(position).setAns_ques_value_no("");
                            //Log.e("Value is 1",arrayListQuestion1.get(0).getQuestions_hs().get(i).getAns_ques_value());

                        }
                    } else if (arrayListQuestionPTW.get(0).getQuestions_hs().get(position).getAns_ques_value().equals("No")) {
                        for (int i = 0; i < questiondataList.size(); i++) {
                            holder.ll_hsstatus.setVisibility(View.VISIBLE);
                            holder.rd2.setChecked(true);

                            questiondataList.get(position).setAns_ques(arrayListQuestionPTW.get(0).getQuestions_hs().get(i).getAns_ques());

                            questiondataList.get(position).setAns_ques_value(arrayListQuestionPTW.get(0).getQuestions_hs().get(position).getAns_ques_value());

                            String q_value = arrayListQuestionPTW.get(0).getQuestions_hs().get(i).getAns_ques_value_no();
                            if (q_value.equals("Serious/Imminent danger")) {

                                holder.ll_hsstatus.setBackgroundColor(Color.parseColor("#FE7D5D"));
                                holder.txt_hs_status.setTextColor(Color.parseColor("#FFFFFF"));
                                holder.txt_hs_status.setText("Serious/Imminent danger");
                                questiondataList.get(i).setAns_ques_value_no("Serious/Imminent danger");
                            } else if (q_value.equals("Breach of leglslation")) {
                                holder.ll_hsstatus.setBackgroundColor(Color.parseColor("#FFD352"));
                                holder.txt_hs_status.setTextColor(Color.parseColor("#FFFFFF"));
                                ;
                                holder.txt_hs_status.setText("Breach of leglslation");
                                questiondataList.get(i).setAns_ques_value_no("Breach of leglslation");
                            } else if (q_value.equals("Does not meet HSE guidance or procedures")) {
                                holder.ll_hsstatus.setBackgroundColor(Color.parseColor("#41CDB3"));
                                holder.txt_hs_status.setTextColor(Color.parseColor("#FFFFFF"));
                                holder.txt_hs_status.setText("Does not meet HSE guidance or procedures");
                                questiondataList.get(i).setAns_ques_value_no("Does not meet HSE guidance or procedures");
                            } else if (q_value.equals("Discussed/Advice")) {
                                holder.ll_hsstatus.setBackgroundColor(Color.parseColor("#004B68"));
                                holder.txt_hs_status.setTextColor(Color.parseColor("#FFFFFF"));
                                holder.txt_hs_status.setText("Discussed/Advice");
                                questiondataList.get(i).setAns_ques_value_no("Discussed/Advice");
                            }


                            //Listdataset(q_value,holder.ll_hsstatus,holder.txt_hs_status);
                            //Log.e("Value is 2",arrayListQuestion1.get(0).getQuestions_hs().get(i).getAns_ques_value());
                        }
                    }
                }
            } else if (fragment_name.equals("TrafficManagement")) {
                holder.txt_queId.setText(Integer.toString(position + 1));
                holder.txt_queTitle.setText(arraylist.get(position).getHsQues());

                List<Example> arrayListQuestionTrfcmng = Utility.getAppcon().getSession().questiondataList10;
                if (arrayListQuestionTrfcmng.size() == 0) {

                } else {
                    if (arrayListQuestionTrfcmng.get(0).getQuestions_hs().get(position).getAns_ques_value().equals("Yes")) {
                        for (int i = 0; i < questiondataList.size(); i++) {
                            holder.ll_hsstatus.setVisibility(View.GONE);
                            holder.rd1.setChecked(true);
                            questiondataList.get(position).setAns_ques(arrayListQuestionTrfcmng.get(0).getQuestions_hs().get(i).getAns_ques());

                            questiondataList.get(position).setAns_ques_value(arrayListQuestionTrfcmng.get(0).getQuestions_hs().get(i).getAns_ques_value());
                            // questiondataList.get(0).setAns_ques_value_no(arrayListQuestion2.get(0).getQuestions_hs().get(position).getAns_ques_value_no());

                            questiondataList.get(position).setAns_ques_value_no("");
                            //Log.e("Value is 1",arrayListQuestion1.get(0).getQuestions_hs().get(i).getAns_ques_value());

                        }

                    } else if (arrayListQuestionTrfcmng.get(0).getQuestions_hs().get(position).getAns_ques_value().equals("N/A")) {
                        for (int i = 0; i < questiondataList.size(); i++) {
                            holder.ll_hsstatus.setVisibility(View.GONE);
                            holder.rd3.setChecked(true);
                            questiondataList.get(position).setAns_ques(arrayListQuestionTrfcmng.get(0).getQuestions_hs().get(i).getAns_ques());

                            questiondataList.get(position).setAns_ques_value(arrayListQuestionTrfcmng.get(0).getQuestions_hs().get(i).getAns_ques_value());
                            // questiondataList.get(0).setAns_ques_value_no(arrayListQuestion2.get(0).getQuestions_hs().get(position).getAns_ques_value_no());

                            questiondataList.get(position).setAns_ques_value_no("");
                            //Log.e("Value is 1",arrayListQuestion1.get(0).getQuestions_hs().get(i).getAns_ques_value());

                        }
                    } else if (arrayListQuestionTrfcmng.get(0).getQuestions_hs().get(position).getAns_ques_value().equals("No")) {
                        for (int i = 0; i < questiondataList.size(); i++) {
                            holder.ll_hsstatus.setVisibility(View.VISIBLE);
                            holder.rd2.setChecked(true);

                            questiondataList.get(position).setAns_ques(arrayListQuestionTrfcmng.get(0).getQuestions_hs().get(i).getAns_ques());

                            questiondataList.get(position).setAns_ques_value(arrayListQuestionTrfcmng.get(0).getQuestions_hs().get(position).getAns_ques_value());

                            String q_value = arrayListQuestionTrfcmng.get(0).getQuestions_hs().get(i).getAns_ques_value_no();
                            if (q_value.equals("Serious/Imminent danger")) {

                                holder.ll_hsstatus.setBackgroundColor(Color.parseColor("#FE7D5D"));
                                holder.txt_hs_status.setTextColor(Color.parseColor("#FFFFFF"));
                                holder.txt_hs_status.setText("Serious/Imminent danger");
                                questiondataList.get(i).setAns_ques_value_no("Serious/Imminent danger");
                            } else if (q_value.equals("Breach of leglslation")) {
                                holder.ll_hsstatus.setBackgroundColor(Color.parseColor("#FFD352"));
                                holder.txt_hs_status.setTextColor(Color.parseColor("#FFFFFF"));
                                ;
                                holder.txt_hs_status.setText("Breach of leglslation");
                                questiondataList.get(i).setAns_ques_value_no("Breach of leglslation");
                            } else if (q_value.equals("Does not meet HSE guidance or procedures")) {
                                holder.ll_hsstatus.setBackgroundColor(Color.parseColor("#41CDB3"));
                                holder.txt_hs_status.setTextColor(Color.parseColor("#FFFFFF"));
                                holder.txt_hs_status.setText("Does not meet HSE guidance or procedures");
                                questiondataList.get(i).setAns_ques_value_no("Does not meet HSE guidance or procedures");
                            } else if (q_value.equals("Discussed/Advice")) {
                                holder.ll_hsstatus.setBackgroundColor(Color.parseColor("#004B68"));
                                holder.txt_hs_status.setTextColor(Color.parseColor("#FFFFFF"));
                                holder.txt_hs_status.setText("Discussed/Advice");
                                questiondataList.get(i).setAns_ques_value_no("Discussed/Advice");
                            }


                            //Listdataset(q_value,holder.ll_hsstatus,holder.txt_hs_status);
                            //Log.e("Value is 2",arrayListQuestion1.get(0).getQuestions_hs().get(i).getAns_ques_value());
                        }
                    }
                }
            } else if (fragment_name.equals("VehicalVMPM")) {
                holder.txt_queId.setText(Integer.toString(position + 1));
                holder.txt_queTitle.setText(arraylist.get(position).getHsQues());

                List<Example> arrayListQuestionVVmpm = Utility.getAppcon().getSession().questiondataList11;
                if (arrayListQuestionVVmpm.size() == 0) {

                } else {
                    if (arrayListQuestionVVmpm.get(0).getQuestions_hs().get(position).getAns_ques_value().equals("Yes")) {
                        for (int i = 0; i < questiondataList.size(); i++) {
                            holder.ll_hsstatus.setVisibility(View.GONE);
                            holder.rd1.setChecked(true);
                            questiondataList.get(position).setAns_ques(arrayListQuestionVVmpm.get(0).getQuestions_hs().get(i).getAns_ques());

                            questiondataList.get(position).setAns_ques_value(arrayListQuestionVVmpm.get(0).getQuestions_hs().get(i).getAns_ques_value());
                            // questiondataList.get(0).setAns_ques_value_no(arrayListQuestion2.get(0).getQuestions_hs().get(position).getAns_ques_value_no());

                            questiondataList.get(position).setAns_ques_value_no("");
                            //Log.e("Value is 1",arrayListQuestion1.get(0).getQuestions_hs().get(i).getAns_ques_value());

                        }

                    } else if (arrayListQuestionVVmpm.get(0).getQuestions_hs().get(position).getAns_ques_value().equals("N/A")) {
                        for (int i = 0; i < questiondataList.size(); i++) {
                            holder.ll_hsstatus.setVisibility(View.GONE);
                            holder.rd3.setChecked(true);
                            questiondataList.get(position).setAns_ques(arrayListQuestionVVmpm.get(0).getQuestions_hs().get(i).getAns_ques());

                            questiondataList.get(position).setAns_ques_value(arrayListQuestionVVmpm.get(0).getQuestions_hs().get(i).getAns_ques_value());
                            // questiondataList.get(0).setAns_ques_value_no(arrayListQuestion2.get(0).getQuestions_hs().get(position).getAns_ques_value_no());

                            questiondataList.get(position).setAns_ques_value_no("");
                            //Log.e("Value is 1",arrayListQuestion1.get(0).getQuestions_hs().get(i).getAns_ques_value());

                        }
                    } else if (arrayListQuestionVVmpm.get(0).getQuestions_hs().get(position).getAns_ques_value().equals("No")) {
                        for (int i = 0; i < questiondataList.size(); i++) {
                            holder.ll_hsstatus.setVisibility(View.VISIBLE);
                            holder.rd2.setChecked(true);

                            questiondataList.get(position).setAns_ques(arrayListQuestionVVmpm.get(0).getQuestions_hs().get(i).getAns_ques());

                            questiondataList.get(position).setAns_ques_value(arrayListQuestionVVmpm.get(0).getQuestions_hs().get(position).getAns_ques_value());

                            String q_value = arrayListQuestionVVmpm.get(0).getQuestions_hs().get(i).getAns_ques_value_no();
                            if (q_value.equals("Serious/Imminent danger")) {

                                holder.ll_hsstatus.setBackgroundColor(Color.parseColor("#FE7D5D"));
                                holder.txt_hs_status.setTextColor(Color.parseColor("#FFFFFF"));
                                holder.txt_hs_status.setText("Serious/Imminent danger");
                                questiondataList.get(i).setAns_ques_value_no("Serious/Imminent danger");
                            } else if (q_value.equals("Breach of leglslation")) {
                                holder.ll_hsstatus.setBackgroundColor(Color.parseColor("#FFD352"));
                                holder.txt_hs_status.setTextColor(Color.parseColor("#FFFFFF"));
                                ;
                                holder.txt_hs_status.setText("Breach of leglslation");
                                questiondataList.get(i).setAns_ques_value_no("Breach of leglslation");
                            } else if (q_value.equals("Does not meet HSE guidance or procedures")) {
                                holder.ll_hsstatus.setBackgroundColor(Color.parseColor("#41CDB3"));
                                holder.txt_hs_status.setTextColor(Color.parseColor("#FFFFFF"));
                                holder.txt_hs_status.setText("Does not meet HSE guidance or procedures");
                                questiondataList.get(i).setAns_ques_value_no("Does not meet HSE guidance or procedures");
                            } else if (q_value.equals("Discussed/Advice")) {
                                holder.ll_hsstatus.setBackgroundColor(Color.parseColor("#004B68"));
                                holder.txt_hs_status.setTextColor(Color.parseColor("#FFFFFF"));
                                holder.txt_hs_status.setText("Discussed/Advice");
                                questiondataList.get(i).setAns_ques_value_no("Discussed/Advice");
                            }


                            //Listdataset(q_value,holder.ll_hsstatus,holder.txt_hs_status);
                            //Log.e("Value is 2",arrayListQuestion1.get(0).getQuestions_hs().get(i).getAns_ques_value());
                        }
                    }
                }
            } else if (fragment_name.equals("PortableTools")) {
                holder.txt_queId.setText(Integer.toString(position + 1));
                holder.txt_queTitle.setText(arraylist.get(position).getHsQues());

                List<Example> arrayListQuestionPT = Utility.getAppcon().getSession().questiondataList12;
                if (arrayListQuestionPT.size() == 0) {

                } else {
                    if (arrayListQuestionPT.get(0).getQuestions_hs().get(position).getAns_ques_value().equals("Yes")) {
                        for (int i = 0; i < questiondataList.size(); i++) {
                            holder.ll_hsstatus.setVisibility(View.GONE);
                            holder.rd1.setChecked(true);
                            questiondataList.get(position).setAns_ques(arrayListQuestionPT.get(0).getQuestions_hs().get(i).getAns_ques());

                            questiondataList.get(position).setAns_ques_value(arrayListQuestionPT.get(0).getQuestions_hs().get(i).getAns_ques_value());
                            // questiondataList.get(0).setAns_ques_value_no(arrayListQuestion2.get(0).getQuestions_hs().get(position).getAns_ques_value_no());

                            questiondataList.get(position).setAns_ques_value_no("");
                            //Log.e("Value is 1",arrayListQuestion1.get(0).getQuestions_hs().get(i).getAns_ques_value());

                        }

                    } else if (arrayListQuestionPT.get(0).getQuestions_hs().get(position).getAns_ques_value().equals("N/A")) {
                        for (int i = 0; i < questiondataList.size(); i++) {
                            holder.ll_hsstatus.setVisibility(View.GONE);
                            holder.rd3.setChecked(true);
                            questiondataList.get(position).setAns_ques(arrayListQuestionPT.get(0).getQuestions_hs().get(i).getAns_ques());

                            questiondataList.get(position).setAns_ques_value(arrayListQuestionPT.get(0).getQuestions_hs().get(i).getAns_ques_value());
                            // questiondataList.get(0).setAns_ques_value_no(arrayListQuestion2.get(0).getQuestions_hs().get(position).getAns_ques_value_no());

                            questiondataList.get(position).setAns_ques_value_no("");
                            //Log.e("Value is 1",arrayListQuestion1.get(0).getQuestions_hs().get(i).getAns_ques_value());

                        }
                    } else if (arrayListQuestionPT.get(0).getQuestions_hs().get(position).getAns_ques_value().equals("No")) {
                        for (int i = 0; i < questiondataList.size(); i++) {
                            holder.ll_hsstatus.setVisibility(View.VISIBLE);
                            holder.rd2.setChecked(true);

                            questiondataList.get(position).setAns_ques(arrayListQuestionPT.get(0).getQuestions_hs().get(i).getAns_ques());

                            questiondataList.get(position).setAns_ques_value(arrayListQuestionPT.get(0).getQuestions_hs().get(position).getAns_ques_value());

                            String q_value = arrayListQuestionPT.get(0).getQuestions_hs().get(i).getAns_ques_value_no();
                            if (q_value.equals("Serious/Imminent danger")) {

                                holder.ll_hsstatus.setBackgroundColor(Color.parseColor("#FE7D5D"));
                                holder.txt_hs_status.setTextColor(Color.parseColor("#FFFFFF"));
                                holder.txt_hs_status.setText("Serious/Imminent danger");
                                questiondataList.get(i).setAns_ques_value_no("Serious/Imminent danger");
                            } else if (q_value.equals("Breach of leglslation")) {
                                holder.ll_hsstatus.setBackgroundColor(Color.parseColor("#FFD352"));
                                holder.txt_hs_status.setTextColor(Color.parseColor("#FFFFFF"));
                                ;
                                holder.txt_hs_status.setText("Breach of leglslation");
                                questiondataList.get(i).setAns_ques_value_no("Breach of leglslation");
                            } else if (q_value.equals("Does not meet HSE guidance or procedures")) {
                                holder.ll_hsstatus.setBackgroundColor(Color.parseColor("#41CDB3"));
                                holder.txt_hs_status.setTextColor(Color.parseColor("#FFFFFF"));
                                holder.txt_hs_status.setText("Does not meet HSE guidance or procedures");
                                questiondataList.get(i).setAns_ques_value_no("Does not meet HSE guidance or procedures");
                            } else if (q_value.equals("Discussed/Advice")) {
                                holder.ll_hsstatus.setBackgroundColor(Color.parseColor("#004B68"));
                                holder.txt_hs_status.setTextColor(Color.parseColor("#FFFFFF"));
                                holder.txt_hs_status.setText("Discussed/Advice");
                                questiondataList.get(i).setAns_ques_value_no("Discussed/Advice");
                            }


                            //Listdataset(q_value,holder.ll_hsstatus,holder.txt_hs_status);
                            //Log.e("Value is 2",arrayListQuestion1.get(0).getQuestions_hs().get(i).getAns_ques_value());
                        }
                    }
                }
            } else if (fragment_name.equals("NoiseVibrationAndDust")) {
                holder.txt_queId.setText(Integer.toString(position + 1));
                holder.txt_queTitle.setText(arraylist.get(position).getHsQues());

                List<Example> arrayListQuestionNVD = Utility.getAppcon().getSession().questiondataList13;
                if (arrayListQuestionNVD.size() == 0) {

                } else {
                    if (arrayListQuestionNVD.get(0).getQuestions_hs().get(position).getAns_ques_value().equals("Yes")) {
                        for (int i = 0; i < questiondataList.size(); i++) {
                            holder.ll_hsstatus.setVisibility(View.GONE);
                            holder.rd1.setChecked(true);
                            questiondataList.get(position).setAns_ques(arrayListQuestionNVD.get(0).getQuestions_hs().get(i).getAns_ques());

                            questiondataList.get(position).setAns_ques_value(arrayListQuestionNVD.get(0).getQuestions_hs().get(i).getAns_ques_value());
                            // questiondataList.get(0).setAns_ques_value_no(arrayListQuestion2.get(0).getQuestions_hs().get(position).getAns_ques_value_no());

                            questiondataList.get(position).setAns_ques_value_no("");
                            //Log.e("Value is 1",arrayListQuestion1.get(0).getQuestions_hs().get(i).getAns_ques_value());

                        }

                    } else if (arrayListQuestionNVD.get(0).getQuestions_hs().get(position).getAns_ques_value().equals("N/A")) {
                        for (int i = 0; i < questiondataList.size(); i++) {
                            holder.ll_hsstatus.setVisibility(View.GONE);
                            holder.rd3.setChecked(true);
                            questiondataList.get(position).setAns_ques(arrayListQuestionNVD.get(0).getQuestions_hs().get(i).getAns_ques());

                            questiondataList.get(position).setAns_ques_value(arrayListQuestionNVD.get(0).getQuestions_hs().get(i).getAns_ques_value());
                            // questiondataList.get(0).setAns_ques_value_no(arrayListQuestion2.get(0).getQuestions_hs().get(position).getAns_ques_value_no());

                            questiondataList.get(position).setAns_ques_value_no("");
                            //Log.e("Value is 1",arrayListQuestion1.get(0).getQuestions_hs().get(i).getAns_ques_value());

                        }
                    } else if (arrayListQuestionNVD.get(0).getQuestions_hs().get(position).getAns_ques_value().equals("No")) {
                        for (int i = 0; i < questiondataList.size(); i++) {
                            holder.ll_hsstatus.setVisibility(View.VISIBLE);
                            holder.rd2.setChecked(true);

                            questiondataList.get(position).setAns_ques(arrayListQuestionNVD.get(0).getQuestions_hs().get(i).getAns_ques());

                            questiondataList.get(position).setAns_ques_value(arrayListQuestionNVD.get(0).getQuestions_hs().get(position).getAns_ques_value());

                            String q_value = arrayListQuestionNVD.get(0).getQuestions_hs().get(i).getAns_ques_value_no();
                            if (q_value.equals("Serious/Imminent danger")) {

                                holder.ll_hsstatus.setBackgroundColor(Color.parseColor("#FE7D5D"));
                                holder.txt_hs_status.setTextColor(Color.parseColor("#FFFFFF"));
                                holder.txt_hs_status.setText("Serious/Imminent danger");
                                questiondataList.get(i).setAns_ques_value_no("Serious/Imminent danger");
                            } else if (q_value.equals("Breach of leglslation")) {
                                holder.ll_hsstatus.setBackgroundColor(Color.parseColor("#FFD352"));
                                holder.txt_hs_status.setTextColor(Color.parseColor("#FFFFFF"));
                                ;
                                holder.txt_hs_status.setText("Breach of leglslation");
                                questiondataList.get(i).setAns_ques_value_no("Breach of leglslation");
                            } else if (q_value.equals("Does not meet HSE guidance or procedures")) {
                                holder.ll_hsstatus.setBackgroundColor(Color.parseColor("#41CDB3"));
                                holder.txt_hs_status.setTextColor(Color.parseColor("#FFFFFF"));
                                holder.txt_hs_status.setText("Does not meet HSE guidance or procedures");
                                questiondataList.get(i).setAns_ques_value_no("Does not meet HSE guidance or procedures");
                            } else if (q_value.equals("Discussed/Advice")) {
                                holder.ll_hsstatus.setBackgroundColor(Color.parseColor("#004B68"));
                                holder.txt_hs_status.setTextColor(Color.parseColor("#FFFFFF"));
                                holder.txt_hs_status.setText("Discussed/Advice");
                                questiondataList.get(i).setAns_ques_value_no("Discussed/Advice");
                            }


                            //Listdataset(q_value,holder.ll_hsstatus,holder.txt_hs_status);
                            //Log.e("Value is 2",arrayListQuestion1.get(0).getQuestions_hs().get(i).getAns_ques_value());
                        }
                    }
                }
            } else if (fragment_name.equals("excavations")) {
                holder.txt_queId.setText(Integer.toString(position + 1));
                holder.txt_queTitle.setText(arraylist.get(position).getHsQues());

                List<Example> arrayListQuestionExca = Utility.getAppcon().getSession().questiondataList14;
                if (arrayListQuestionExca.size() == 0) {

                } else {
                    if (arrayListQuestionExca.get(0).getQuestions_hs().get(position).getAns_ques_value().equals("Yes")) {
                        for (int i = 0; i < questiondataList.size(); i++) {
                            holder.ll_hsstatus.setVisibility(View.GONE);
                            holder.rd1.setChecked(true);
                            questiondataList.get(position).setAns_ques(arrayListQuestionExca.get(0).getQuestions_hs().get(i).getAns_ques());

                            questiondataList.get(position).setAns_ques_value(arrayListQuestionExca.get(0).getQuestions_hs().get(i).getAns_ques_value());
                            // questiondataList.get(0).setAns_ques_value_no(arrayListQuestion2.get(0).getQuestions_hs().get(position).getAns_ques_value_no());

                            questiondataList.get(position).setAns_ques_value_no("");
                            //Log.e("Value is 1",arrayListQuestion1.get(0).getQuestions_hs().get(i).getAns_ques_value());

                        }

                    } else if (arrayListQuestionExca.get(0).getQuestions_hs().get(position).getAns_ques_value().equals("N/A")) {
                        for (int i = 0; i < questiondataList.size(); i++) {
                            holder.ll_hsstatus.setVisibility(View.GONE);
                            holder.rd3.setChecked(true);
                            questiondataList.get(position).setAns_ques(arrayListQuestionExca.get(0).getQuestions_hs().get(i).getAns_ques());

                            questiondataList.get(position).setAns_ques_value(arrayListQuestionExca.get(0).getQuestions_hs().get(i).getAns_ques_value());
                            // questiondataList.get(0).setAns_ques_value_no(arrayListQuestion2.get(0).getQuestions_hs().get(position).getAns_ques_value_no());

                            questiondataList.get(position).setAns_ques_value_no("");
                            //Log.e("Value is 1",arrayListQuestion1.get(0).getQuestions_hs().get(i).getAns_ques_value());

                        }
                    } else if (arrayListQuestionExca.get(0).getQuestions_hs().get(position).getAns_ques_value().equals("No")) {
                        for (int i = 0; i < questiondataList.size(); i++) {
                            holder.ll_hsstatus.setVisibility(View.VISIBLE);
                            holder.rd2.setChecked(true);

                            questiondataList.get(position).setAns_ques(arrayListQuestionExca.get(0).getQuestions_hs().get(i).getAns_ques());

                            questiondataList.get(position).setAns_ques_value(arrayListQuestionExca.get(0).getQuestions_hs().get(position).getAns_ques_value());

                            String q_value = arrayListQuestionExca.get(0).getQuestions_hs().get(i).getAns_ques_value_no();
                            if (q_value.equals("Serious/Imminent danger")) {

                                holder.ll_hsstatus.setBackgroundColor(Color.parseColor("#FE7D5D"));
                                holder.txt_hs_status.setTextColor(Color.parseColor("#FFFFFF"));
                                holder.txt_hs_status.setText("Serious/Imminent danger");
                                questiondataList.get(i).setAns_ques_value_no("Serious/Imminent danger");
                            } else if (q_value.equals("Breach of leglslation")) {
                                holder.ll_hsstatus.setBackgroundColor(Color.parseColor("#FFD352"));
                                holder.txt_hs_status.setTextColor(Color.parseColor("#FFFFFF"));
                                ;
                                holder.txt_hs_status.setText("Breach of leglslation");
                                questiondataList.get(i).setAns_ques_value_no("Breach of leglslation");
                            } else if (q_value.equals("Does not meet HSE guidance or procedures")) {
                                holder.ll_hsstatus.setBackgroundColor(Color.parseColor("#41CDB3"));
                                holder.txt_hs_status.setTextColor(Color.parseColor("#FFFFFF"));
                                holder.txt_hs_status.setText("Does not meet HSE guidance or procedures");
                                questiondataList.get(i).setAns_ques_value_no("Does not meet HSE guidance or procedures");
                            } else if (q_value.equals("Discussed/Advice")) {
                                holder.ll_hsstatus.setBackgroundColor(Color.parseColor("#004B68"));
                                holder.txt_hs_status.setTextColor(Color.parseColor("#FFFFFF"));
                                holder.txt_hs_status.setText("Discussed/Advice");
                                questiondataList.get(i).setAns_ques_value_no("Discussed/Advice");
                            }


                            //Listdataset(q_value,holder.ll_hsstatus,holder.txt_hs_status);
                            //Log.e("Value is 2",arrayListQuestion1.get(0).getQuestions_hs().get(i).getAns_ques_value());
                        }
                    }
                }
            } else if (fragment_name.equals("prtctngpblcscrty")) {
                holder.txt_queId.setText(Integer.toString(position + 1));
                holder.txt_queTitle.setText(arraylist.get(position).getHsQues());

                List<Example> arrayListQuestionPPS = Utility.getAppcon().getSession().questiondataList15;
                if (arrayListQuestionPPS.size() == 0) {

                } else {
                    if (arrayListQuestionPPS.get(0).getQuestions_hs().get(position).getAns_ques_value().equals("Yes")) {
                        for (int i = 0; i < questiondataList.size(); i++) {
                            holder.ll_hsstatus.setVisibility(View.GONE);
                            holder.rd1.setChecked(true);
                            questiondataList.get(position).setAns_ques(arrayListQuestionPPS.get(0).getQuestions_hs().get(i).getAns_ques());

                            questiondataList.get(position).setAns_ques_value(arrayListQuestionPPS.get(0).getQuestions_hs().get(i).getAns_ques_value());
                            // questiondataList.get(0).setAns_ques_value_no(arrayListQuestion2.get(0).getQuestions_hs().get(position).getAns_ques_value_no());

                            questiondataList.get(position).setAns_ques_value_no("");
                            //Log.e("Value is 1",arrayListQuestion1.get(0).getQuestions_hs().get(i).getAns_ques_value());

                        }

                    } else if (arrayListQuestionPPS.get(0).getQuestions_hs().get(position).getAns_ques_value().equals("N/A")) {
                        for (int i = 0; i < questiondataList.size(); i++) {
                            holder.ll_hsstatus.setVisibility(View.GONE);
                            holder.rd3.setChecked(true);
                            questiondataList.get(position).setAns_ques(arrayListQuestionPPS.get(0).getQuestions_hs().get(i).getAns_ques());

                            questiondataList.get(position).setAns_ques_value(arrayListQuestionPPS.get(0).getQuestions_hs().get(i).getAns_ques_value());
                            // questiondataList.get(0).setAns_ques_value_no(arrayListQuestion2.get(0).getQuestions_hs().get(position).getAns_ques_value_no());

                            questiondataList.get(position).setAns_ques_value_no("");
                            //Log.e("Value is 1",arrayListQuestion1.get(0).getQuestions_hs().get(i).getAns_ques_value());

                        }
                    } else if (arrayListQuestionPPS.get(0).getQuestions_hs().get(position).getAns_ques_value().equals("No")) {
                        for (int i = 0; i < questiondataList.size(); i++) {
                            holder.ll_hsstatus.setVisibility(View.VISIBLE);
                            holder.rd2.setChecked(true);

                            questiondataList.get(position).setAns_ques(arrayListQuestionPPS.get(0).getQuestions_hs().get(i).getAns_ques());

                            questiondataList.get(position).setAns_ques_value(arrayListQuestionPPS.get(0).getQuestions_hs().get(position).getAns_ques_value());

                            String q_value = arrayListQuestionPPS.get(0).getQuestions_hs().get(i).getAns_ques_value_no();
                            if (q_value.equals("Serious/Imminent danger")) {

                                holder.ll_hsstatus.setBackgroundColor(Color.parseColor("#FE7D5D"));
                                holder.txt_hs_status.setTextColor(Color.parseColor("#FFFFFF"));
                                holder.txt_hs_status.setText("Serious/Imminent danger");
                                questiondataList.get(i).setAns_ques_value_no("Serious/Imminent danger");
                            } else if (q_value.equals("Breach of leglslation")) {
                                holder.ll_hsstatus.setBackgroundColor(Color.parseColor("#FFD352"));
                                holder.txt_hs_status.setTextColor(Color.parseColor("#FFFFFF"));
                                ;
                                holder.txt_hs_status.setText("Breach of leglslation");
                                questiondataList.get(i).setAns_ques_value_no("Breach of leglslation");
                            } else if (q_value.equals("Does not meet HSE guidance or procedures")) {
                                holder.ll_hsstatus.setBackgroundColor(Color.parseColor("#41CDB3"));
                                holder.txt_hs_status.setTextColor(Color.parseColor("#FFFFFF"));
                                holder.txt_hs_status.setText("Does not meet HSE guidance or procedures");
                                questiondataList.get(i).setAns_ques_value_no("Does not meet HSE guidance or procedures");
                            } else if (q_value.equals("Discussed/Advice")) {
                                holder.ll_hsstatus.setBackgroundColor(Color.parseColor("#004B68"));
                                holder.txt_hs_status.setTextColor(Color.parseColor("#FFFFFF"));
                                holder.txt_hs_status.setText("Discussed/Advice");
                                questiondataList.get(i).setAns_ques_value_no("Discussed/Advice");
                            }


                            //Listdataset(q_value,holder.ll_hsstatus,holder.txt_hs_status);
                            //Log.e("Value is 2",arrayListQuestion1.get(0).getQuestions_hs().get(i).getAns_ques_value());
                        }
                    }
                }
            } else if (fragment_name.equals("plant")) {
                holder.txt_queId.setText(Integer.toString(position + 1));
                holder.txt_queTitle.setText(arraylist.get(position).getHsQues());

                List<Example> arrayListQuestionPlant = Utility.getAppcon().getSession().questiondataList16;
                if (arrayListQuestionPlant.size() == 0) {

                } else {
                    if (arrayListQuestionPlant.get(0).getQuestions_hs().get(position).getAns_ques_value().equals("Yes")) {
                        for (int i = 0; i < questiondataList.size(); i++) {
                            holder.ll_hsstatus.setVisibility(View.GONE);
                            holder.rd1.setChecked(true);
                            questiondataList.get(position).setAns_ques(arrayListQuestionPlant.get(0).getQuestions_hs().get(i).getAns_ques());

                            questiondataList.get(position).setAns_ques_value(arrayListQuestionPlant.get(0).getQuestions_hs().get(i).getAns_ques_value());
                            // questiondataList.get(0).setAns_ques_value_no(arrayListQuestion2.get(0).getQuestions_hs().get(position).getAns_ques_value_no());

                            questiondataList.get(position).setAns_ques_value_no("");
                            //Log.e("Value is 1",arrayListQuestion1.get(0).getQuestions_hs().get(i).getAns_ques_value());

                        }

                    } else if (arrayListQuestionPlant.get(0).getQuestions_hs().get(position).getAns_ques_value().equals("N/A")) {
                        for (int i = 0; i < questiondataList.size(); i++) {
                            holder.ll_hsstatus.setVisibility(View.GONE);
                            holder.rd3.setChecked(true);
                            questiondataList.get(position).setAns_ques(arrayListQuestionPlant.get(0).getQuestions_hs().get(i).getAns_ques());

                            questiondataList.get(position).setAns_ques_value(arrayListQuestionPlant.get(0).getQuestions_hs().get(i).getAns_ques_value());
                            // questiondataList.get(0).setAns_ques_value_no(arrayListQuestion2.get(0).getQuestions_hs().get(position).getAns_ques_value_no());

                            questiondataList.get(position).setAns_ques_value_no("");
                            //Log.e("Value is 1",arrayListQuestion1.get(0).getQuestions_hs().get(i).getAns_ques_value());

                        }
                    } else if (arrayListQuestionPlant.get(0).getQuestions_hs().get(position).getAns_ques_value().equals("No")) {
                        for (int i = 0; i < questiondataList.size(); i++) {
                            holder.ll_hsstatus.setVisibility(View.VISIBLE);
                            holder.rd2.setChecked(true);

                            questiondataList.get(position).setAns_ques(arrayListQuestionPlant.get(0).getQuestions_hs().get(i).getAns_ques());

                            questiondataList.get(position).setAns_ques_value(arrayListQuestionPlant.get(0).getQuestions_hs().get(position).getAns_ques_value());

                            String q_value = arrayListQuestionPlant.get(0).getQuestions_hs().get(i).getAns_ques_value_no();
                            if (q_value.equals("Serious/Imminent danger")) {

                                holder.ll_hsstatus.setBackgroundColor(Color.parseColor("#FE7D5D"));
                                holder.txt_hs_status.setTextColor(Color.parseColor("#FFFFFF"));
                                holder.txt_hs_status.setText("Serious/Imminent danger");
                                questiondataList.get(i).setAns_ques_value_no("Serious/Imminent danger");
                            } else if (q_value.equals("Breach of leglslation")) {
                                holder.ll_hsstatus.setBackgroundColor(Color.parseColor("#FFD352"));
                                holder.txt_hs_status.setTextColor(Color.parseColor("#FFFFFF"));
                                ;
                                holder.txt_hs_status.setText("Breach of leglslation");
                                questiondataList.get(i).setAns_ques_value_no("Breach of leglslation");
                            } else if (q_value.equals("Does not meet HSE guidance or procedures")) {
                                holder.ll_hsstatus.setBackgroundColor(Color.parseColor("#41CDB3"));
                                holder.txt_hs_status.setTextColor(Color.parseColor("#FFFFFF"));
                                holder.txt_hs_status.setText("Does not meet HSE guidance or procedures");
                                questiondataList.get(i).setAns_ques_value_no("Does not meet HSE guidance or procedures");
                            } else if (q_value.equals("Discussed/Advice")) {
                                holder.ll_hsstatus.setBackgroundColor(Color.parseColor("#004B68"));
                                holder.txt_hs_status.setTextColor(Color.parseColor("#FFFFFF"));
                                holder.txt_hs_status.setText("Discussed/Advice");
                                questiondataList.get(i).setAns_ques_value_no("Discussed/Advice");
                            }


                            //Listdataset(q_value,holder.ll_hsstatus,holder.txt_hs_status);
                            //Log.e("Value is 2",arrayListQuestion1.get(0).getQuestions_hs().get(i).getAns_ques_value());
                        }
                    }
                }
            } else if (fragment_name.equals("materials")) {
                holder.txt_queId.setText(Integer.toString(position + 1));
                holder.txt_queTitle.setText(arraylist.get(position).getHsQues());

                List<Example> arrayListQuestionMatrl = Utility.getAppcon().getSession().questiondataList17;
                if (arrayListQuestionMatrl.size() == 0) {

                } else {
                    if (arrayListQuestionMatrl.get(0).getQuestions_hs().get(position).getAns_ques_value().equals("Yes")) {
                        for (int i = 0; i < questiondataList.size(); i++) {
                            holder.ll_hsstatus.setVisibility(View.GONE);
                            holder.rd1.setChecked(true);
                            questiondataList.get(position).setAns_ques(arrayListQuestionMatrl.get(0).getQuestions_hs().get(i).getAns_ques());

                            questiondataList.get(position).setAns_ques_value(arrayListQuestionMatrl.get(0).getQuestions_hs().get(i).getAns_ques_value());
                            // questiondataList.get(0).setAns_ques_value_no(arrayListQuestion2.get(0).getQuestions_hs().get(position).getAns_ques_value_no());

                            questiondataList.get(position).setAns_ques_value_no("");
                            //Log.e("Value is 1",arrayListQuestion1.get(0).getQuestions_hs().get(i).getAns_ques_value());

                        }

                    } else if (arrayListQuestionMatrl.get(0).getQuestions_hs().get(position).getAns_ques_value().equals("N/A")) {
                        for (int i = 0; i < questiondataList.size(); i++) {
                            holder.ll_hsstatus.setVisibility(View.GONE);
                            holder.rd3.setChecked(true);
                            questiondataList.get(position).setAns_ques(arrayListQuestionMatrl.get(0).getQuestions_hs().get(i).getAns_ques());

                            questiondataList.get(position).setAns_ques_value(arrayListQuestionMatrl.get(0).getQuestions_hs().get(i).getAns_ques_value());
                            // questiondataList.get(0).setAns_ques_value_no(arrayListQuestion2.get(0).getQuestions_hs().get(position).getAns_ques_value_no());

                            questiondataList.get(position).setAns_ques_value_no("");
                            //Log.e("Value is 1",arrayListQuestion1.get(0).getQuestions_hs().get(i).getAns_ques_value());

                        }
                    } else if (arrayListQuestionMatrl.get(0).getQuestions_hs().get(position).getAns_ques_value().equals("No")) {
                        for (int i = 0; i < questiondataList.size(); i++) {
                            holder.ll_hsstatus.setVisibility(View.VISIBLE);
                            holder.rd2.setChecked(true);

                            questiondataList.get(position).setAns_ques(arrayListQuestionMatrl.get(0).getQuestions_hs().get(i).getAns_ques());

                            questiondataList.get(position).setAns_ques_value(arrayListQuestionMatrl.get(0).getQuestions_hs().get(position).getAns_ques_value());

                            String q_value = arrayListQuestionMatrl.get(0).getQuestions_hs().get(i).getAns_ques_value_no();
                            if (q_value.equals("Serious/Imminent danger")) {

                                holder.ll_hsstatus.setBackgroundColor(Color.parseColor("#FE7D5D"));
                                holder.txt_hs_status.setTextColor(Color.parseColor("#FFFFFF"));
                                holder.txt_hs_status.setText("Serious/Imminent danger");
                                questiondataList.get(i).setAns_ques_value_no("Serious/Imminent danger");
                            } else if (q_value.equals("Breach of leglslation")) {
                                holder.ll_hsstatus.setBackgroundColor(Color.parseColor("#FFD352"));
                                holder.txt_hs_status.setTextColor(Color.parseColor("#FFFFFF"));
                                ;
                                holder.txt_hs_status.setText("Breach of leglslation");
                                questiondataList.get(i).setAns_ques_value_no("Breach of leglslation");
                            } else if (q_value.equals("Does not meet HSE guidance or procedures")) {
                                holder.ll_hsstatus.setBackgroundColor(Color.parseColor("#41CDB3"));
                                holder.txt_hs_status.setTextColor(Color.parseColor("#FFFFFF"));
                                holder.txt_hs_status.setText("Does not meet HSE guidance or procedures");
                                questiondataList.get(i).setAns_ques_value_no("Does not meet HSE guidance or procedures");
                            } else if (q_value.equals("Discussed/Advice")) {
                                holder.ll_hsstatus.setBackgroundColor(Color.parseColor("#004B68"));
                                holder.txt_hs_status.setTextColor(Color.parseColor("#FFFFFF"));
                                holder.txt_hs_status.setText("Discussed/Advice");
                                questiondataList.get(i).setAns_ques_value_no("Discussed/Advice");
                            }


                            //Listdataset(q_value,holder.ll_hsstatus,holder.txt_hs_status);
                            //Log.e("Value is 2",arrayListQuestion1.get(0).getQuestions_hs().get(i).getAns_ques_value());
                        }
                    }
                }
            } else if (fragment_name.equals("VisualChecks")) {
                holder.txt_queId.setText(Integer.toString(position + 1));
                holder.txt_queTitle.setText(arraylist.get(position).getHsQues());

                List<Example> arrayListQuestionVisulChck = Utility.getAppcon().getSession().questiondataList18;
                if (arrayListQuestionVisulChck.size() == 0) {

                } else {
                    if (arrayListQuestionVisulChck.get(0).getQuestions_hs().get(position).getAns_ques_value().equals("Yes")) {
                        for (int i = 0; i < questiondataList.size(); i++) {
                            holder.ll_hsstatus.setVisibility(View.GONE);
                            holder.rd1.setChecked(true);
                            questiondataList.get(position).setAns_ques(arrayListQuestionVisulChck.get(0).getQuestions_hs().get(i).getAns_ques());

                            questiondataList.get(position).setAns_ques_value(arrayListQuestionVisulChck.get(0).getQuestions_hs().get(i).getAns_ques_value());
                            // questiondataList.get(0).setAns_ques_value_no(arrayListQuestion2.get(0).getQuestions_hs().get(position).getAns_ques_value_no());

                            questiondataList.get(position).setAns_ques_value_no("");
                            //Log.e("Value is 1",arrayListQuestion1.get(0).getQuestions_hs().get(i).getAns_ques_value());

                        }

                    } else if (arrayListQuestionVisulChck.get(0).getQuestions_hs().get(position).getAns_ques_value().equals("N/A")) {
                        for (int i = 0; i < questiondataList.size(); i++) {
                            holder.ll_hsstatus.setVisibility(View.GONE);
                            holder.rd3.setChecked(true);
                            questiondataList.get(position).setAns_ques(arrayListQuestionVisulChck.get(0).getQuestions_hs().get(i).getAns_ques());

                            questiondataList.get(position).setAns_ques_value(arrayListQuestionVisulChck.get(0).getQuestions_hs().get(i).getAns_ques_value());
                            // questiondataList.get(0).setAns_ques_value_no(arrayListQuestion2.get(0).getQuestions_hs().get(position).getAns_ques_value_no());

                            questiondataList.get(position).setAns_ques_value_no("");
                            //Log.e("Value is 1",arrayListQuestion1.get(0).getQuestions_hs().get(i).getAns_ques_value());

                        }
                    } else if (arrayListQuestionVisulChck.get(0).getQuestions_hs().get(position).getAns_ques_value().equals("No")) {
                        for (int i = 0; i < questiondataList.size(); i++) {
                            holder.ll_hsstatus.setVisibility(View.VISIBLE);
                            holder.rd2.setChecked(true);

                            questiondataList.get(position).setAns_ques(arrayListQuestionVisulChck.get(0).getQuestions_hs().get(i).getAns_ques());

                            questiondataList.get(position).setAns_ques_value(arrayListQuestionVisulChck.get(0).getQuestions_hs().get(position).getAns_ques_value());

                            String q_value = arrayListQuestionVisulChck.get(0).getQuestions_hs().get(i).getAns_ques_value_no();
                            if (q_value.equals("Serious/Imminent danger")) {

                                holder.ll_hsstatus.setBackgroundColor(Color.parseColor("#FE7D5D"));
                                holder.txt_hs_status.setTextColor(Color.parseColor("#FFFFFF"));
                                holder.txt_hs_status.setText("Serious/Imminent danger");
                                questiondataList.get(i).setAns_ques_value_no("Serious/Imminent danger");
                            } else if (q_value.equals("Breach of leglslation")) {
                                holder.ll_hsstatus.setBackgroundColor(Color.parseColor("#FFD352"));
                                holder.txt_hs_status.setTextColor(Color.parseColor("#FFFFFF"));
                                ;
                                holder.txt_hs_status.setText("Breach of leglslation");
                                questiondataList.get(i).setAns_ques_value_no("Breach of leglslation");
                            } else if (q_value.equals("Does not meet HSE guidance or procedures")) {
                                holder.ll_hsstatus.setBackgroundColor(Color.parseColor("#41CDB3"));
                                holder.txt_hs_status.setTextColor(Color.parseColor("#FFFFFF"));
                                holder.txt_hs_status.setText("Does not meet HSE guidance or procedures");
                                questiondataList.get(i).setAns_ques_value_no("Does not meet HSE guidance or procedures");
                            } else if (q_value.equals("Discussed/Advice")) {
                                holder.ll_hsstatus.setBackgroundColor(Color.parseColor("#004B68"));
                                holder.txt_hs_status.setTextColor(Color.parseColor("#FFFFFF"));
                                holder.txt_hs_status.setText("Discussed/Advice");
                                questiondataList.get(i).setAns_ques_value_no("Discussed/Advice");
                            }


                            //Listdataset(q_value,holder.ll_hsstatus,holder.txt_hs_status);
                            //Log.e("Value is 2",arrayListQuestion1.get(0).getQuestions_hs().get(i).getAns_ques_value());
                        }
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        holder.rd1.setOnClickListener(view ->
        {
            holder.ll_hsstatus.setVisibility(View.GONE);
            questiondataList.get(position).setAns_ques_value("Yes");
            for (int i = 0; i < questiondataList.size(); i++) {
                if (questiondataList.get(i).getAns_ques_value().equals("")) {
                    lok = "No";
                    //  Log.e("Validataion is1 ", lok);
                } else {
                    lok = "Yes";
                    /// Log.e("Validataion is1 ", lok);
                }
            }
            listener.onItemClick(lok);
        });
        holder.rd2.setOnClickListener(view ->
        {
            // Log.e("No","Ok");
            holder.ll_hsstatus.setVisibility(View.VISIBLE);
            questiondataList.get(position).setAns_ques_value("No");
            // questiondataList.get(position).setAns_ques_value_no("No");
            for (int i = 0; i < questiondataList.size(); i++) {
                if (questiondataList.get(i).getAns_ques_value().equals("")) {
                    lok = "No";
                    //  Log.e("Validataion is2 ", lok);
                } else {
                    lok = "Yes";
                    //  Log.e("Validataion is 2", lok);
                }

            }
            listener.onItemClick(lok);
        });
        holder.rd3.setOnClickListener(view ->
        {
            // Log.e("No","Ok");
            holder.ll_hsstatus.setVisibility(View.GONE);
            questiondataList.get(position).setAns_ques_value("N/A");
            // questiondataList.get(position).setAns_ques_value_no("No");
            for (int i = 0; i < questiondataList.size(); i++) {
                if (questiondataList.get(i).getAns_ques_value().equals("")) {
                    lok = "No";
                    //  Log.e("Validataion is2 ", lok);
                } else {
                    lok = "Yes";
                    //  Log.e("Validataion is 2", lok);
                }
            }
            listener.onItemClick(lok);
        });

        holder.ll_status.setOnClickListener(view -> {
            bindStatus(holder, position);

        });
    }

    private void bindStatus(MyViewHolder holder, int pos) {
        dialog = new Dialog(mContext);
        dialog.getWindow();
        dialog.setTitle("Address");
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_hs_statuslist);
        dialog.setCancelable(true);
        ImageView btnclos = (ImageView) dialog.findViewById(R.id.img_close);
        listView = (ListView) dialog.findViewById(R.id.lv_hslist);
        btnclos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        arrayList_status.clear();
        Observable<SpinnerModel> responseObservable = apiservice.get_all_hs_QuestionStatus();

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
                                arrayList_status.add(res.get(i).getHsn_name());
                            }

                            array = arrayList_status.toArray(new String[0]);
                            arrayAdapter = new ArrayAdapter(mContext, R.layout.list_item, R.id.tv, array) {

                                @Override
                                public View getView(int position, View convertView, ViewGroup parent) {
                                    // Get the current item from ListView
                                    View view = super.getView(position, convertView, parent);
                                    TextView text = view.findViewById(R.id.tv);
                                    text.setTextColor(Color.parseColor("#FFFFFF"));
                                    if (array[position].equals("Serious/Imminent danger")) {
                                        view.setBackgroundColor(Color.parseColor("#FE7D5D"));
                                    } else if (array[position].equals("Breach of leglslation")) {
                                        view.setBackgroundColor(Color.parseColor("#FFD352"));
                                    } else if (array[position].equals("Does not meet HSE guidance or procedures")) {
                                        view.setBackgroundColor(Color.parseColor("#41CDB3"));
                                    } else if (array[position].equals("Discussed/Advice")) {
                                        view.setBackgroundColor(Color.parseColor("#004B68"));
                                    }
                                    return view;
                                }
                            };
                            listView.setAdapter(arrayAdapter);
                        }
                    }
                });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view1, int position, long id) {

                if (array[position].equals("Serious/Imminent danger")) {

                    holder.ll_hsstatus.setBackgroundColor(Color.parseColor("#FE7D5D"));
                    holder.txt_hs_status.setTextColor(Color.parseColor("#FFFFFF"));
                    holder.txt_hs_status.setText(array[position]);
                    hs_status = array[position];
                    try {
                        questiondataList.get(pos).setAns_ques_value_no(array[position]);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else if (array[position].equals("Breach of leglslation")) {
                    holder.ll_hsstatus.setBackgroundColor(Color.parseColor("#FFD352"));
                    holder.txt_hs_status.setTextColor(Color.parseColor("#FFFFFF"));
                    holder.txt_hs_status.setText(array[position]);
                    hs_status = array[position];
                    try {
                        questiondataList.get(pos).setAns_ques_value_no(array[position]);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else if (array[position].equals("Does not meet HSE guidance or procedures")) {
                    holder.ll_hsstatus.setBackgroundColor(Color.parseColor("#41CDB3"));
                    holder.txt_hs_status.setTextColor(Color.parseColor("#FFFFFF"));
                    holder.txt_hs_status.setText(array[position]);
                    hs_status = array[position];
                    try {
                        questiondataList.get(pos).setAns_ques_value_no(array[position]);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else if (array[position].equals("Discussed/Advice")) {
                    holder.ll_hsstatus.setBackgroundColor(Color.parseColor("#004B68"));
                    holder.txt_hs_status.setTextColor(Color.parseColor("#FFFFFF"));
                    holder.txt_hs_status.setText(array[position]);
                    hs_status = array[position];
                    try {
                        questiondataList.get(pos).setAns_ques_value_no(array[position]);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else {
                    holder.ll_hsstatus.setBackgroundColor(Color.parseColor("#4CAF50"));
                    holder.txt_hs_status.setTextColor(Color.parseColor("#FFFFFF"));
                    holder.txt_hs_status.setText(array[position]);
                    hs_status = array[position];

                }
                dialog.dismiss();
            }

        });
        dialog.show();
    }

    @Override
    public int getItemCount() {
        return arraylist.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.txt_hs_status)
        TextView txt_hs_status;
        @BindView(R.id.ll_hsstatus)
        LinearLayout ll_hsstatus;
        @BindView(R.id.ll_status)
        LinearLayout ll_status;
        @BindView(R.id.txt_queId)
        TextView txt_queId;
        @BindView(R.id.txt_queTitle)
        TextView txt_queTitle;
        @BindView(R.id.rd1)
        RadioButton rd1;
        @BindView(R.id.rd2)
        RadioButton rd2;
        @BindView(R.id.rd3)
        RadioButton rd3;

        @BindView(R.id.radiog)
        RadioGroup radiog;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }

    public void onBackPressed() {
        Log.e("On Back Space clla", "Yes");
    }


    public void Listdataset(String value, LinearLayout ll_hsstatus, TextView txt_hs_status) {

    }

}

