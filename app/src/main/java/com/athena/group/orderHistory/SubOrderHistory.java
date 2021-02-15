package com.athena.group.orderHistory;

import androidx.cardview.widget.CardView;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.athena.group.API.ApiConstants;
import com.athena.group.API.ApiInterface;
import com.athena.group.API.ApiServiceCreator;
import com.athena.group.FileUtils;
import com.athena.group.Model.OrderDetailsModel;
import com.athena.group.Model.OrderStatusModel;
import com.athena.group.Model.SpinnerModel;
import com.athena.group.Model.TimeLogModel;
import com.athena.group.R;
import com.athena.group.adapter.ImageAdapter;
import com.athena.group.adapter.MyAdapter;
import com.athena.group.application.SessionManager;
import com.athena.group.application.Utility;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;
import com.wang.avi.AVLoadingIndicatorView;

import java.io.File;
import java.net.SocketTimeoutException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

public class SubOrderHistory extends Activity {

    @BindView(R.id.toolbar_Title)
    TextView toolbar_Title;
    @BindView(R.id.back_icon)
    ImageView back_icon;
    @BindView(R.id.avi)
    AVLoadingIndicatorView avi;
    AVLoadingIndicatorView avi1;
    @BindView(R.id.txt_date)
    TextView txt_date;
    @BindView(R.id.txt_dl_date)
    TextView txt_dl_date;
    @BindView(R.id.txt_qty)
    TextView txt_qty;
    @BindView(R.id.txt_odr_no)
    TextView txt_odr_no;
    @BindView(R.id.txt_odrno)
    TextView txt_odrno;
    @BindView(R.id.txt_slump)
    TextView txt_slump;
    @BindView(R.id.txt_aggsize)
    TextView txt_aggsize;
    @BindView(R.id.txt_ordstatus)
    TextView txt_ordstatus;
    @BindView(R.id.txt_mix)
    TextView txt_mix;
    @BindView(R.id.txt_notes)
    TextView txt_notes;
    @BindView(R.id.txt_productname)
    TextView txt_productname;
    @BindView(R.id.txt_type)
    TextView txt_type;
    @BindView(R.id.txt_mesh)
    TextView txt_mesh;
    @BindView(R.id.txt_concrete_dringe)
    TextView txt_concrete_dringe;
    @BindView(R.id.txt_gnrlmtrl)
    TextView txt_gnrlmtrl;
    @BindView(R.id.txt_ppe)
    TextView txt_ppe;
    @BindView(R.id.txt_stools)
    TextView txt_stools;
    @BindView(R.id.txt_pdp)
    TextView txt_pdp;
    @BindView(R.id.txt_pdw)
    TextView txt_pdw;
    @BindView(R.id.txt_pr)
    TextView txt_pr;
    @BindView(R.id.txt_claydrinage)
    TextView txt_claydrinage;
    @BindView(R.id.txt_kerbs)
    TextView txt_kerbs;
    @BindView(R.id.txt_flags)
    TextView txt_flags;
    @BindView(R.id.txt_blockpaves)
    TextView txt_blockpaves;
    /*@BindView(R.id.spn_site)
    Spinner spn_site;
    @BindView(R.id.spn_orders)
    Spinner spn_orders;*/

    @BindView(R.id.card_type)
    CardView card_type;
    @BindView(R.id.card_aggre)
    CardView card_aggre;
    @BindView(R.id.card_slump)
    CardView card_slump;
    @BindView(R.id.card_mix)
    CardView card_mix;
    @BindView(R.id.card_concredringe)
    CardView card_concredringe;
    @BindView(R.id.card_mesh)
    CardView card_mesh;
    @BindView(R.id.card_ppe)
    CardView card_ppe;
    @BindView(R.id.card_gnrlmetrl)
    CardView card_gnrlmetrl;
    @BindView(R.id.card_stools)
    CardView card_stools;
    @BindView(R.id.card_pdp)
    CardView card_pdp;
    @BindView(R.id.card_pr)
    CardView card_pr;
    @BindView(R.id.card_pdw)
    CardView card_pdw;
    @BindView(R.id.card_kerbs)
    CardView card_kerbs;
    @BindView(R.id.card_claydrinage)
    CardView card_claydrinage;
    @BindView(R.id.card_flags)
    CardView card_flags;
    @BindView(R.id.card_blockpaves)
    CardView card_blockpaves;

    @BindView(R.id.btn_conform)
    Button btn_conform;
    @BindView(R.id.btn_pdf)
    Button btn_pdf;
    EditText edt_cmnt;
    GridView listView;
    SessionManager sessionManager;
    ApiInterface apiservice;
    List<OrderDetailsModel.Data> arraylist;
    int statusCode;
    List<SpinnerModel.Data> data;
    List<OrderStatusModel.Data> data1;
    String site = "", s_id = "", order = "", o_id = "";
    ArrayList<String> sitearray = new ArrayList<>();
    ArrayList<String> orderarray = new ArrayList<>();
    private Dialog dialog;
    private static final int REQUEST_CODE = 6384;
    List<MultipartBody.Part> parts;
    private ArrayList<Uri> imagesPathList;
    private static final int CAPTURE_IMAGE = 3;
    Uri mCapturedImageURI;
    @BindView(R.id.txt_comment)
    TextView txt_comment;
    @BindView(R.id.card)
    CardView card;
    @BindView(R.id.card1)
    CardView card1;

    @BindView(R.id.imagelist)
    RecyclerView imagelist;
    RecyclerView.LayoutManager layoutManager;

    List<String> image_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sub_order_history);

        ButterKnife.bind(this);

        sessionManager = new SessionManager(SubOrderHistory.this);
        apiservice = ApiServiceCreator.createService("latest");
        toolbar_Title.setText(getString(R.string.orderhistroy));

        back_icon.setOnClickListener(view -> finish());

        arraylist = Utility.getAppcon().getSession().arrayListOrderResponse;
        imagesPathList = new ArrayList<>();
/*        Log.e("Comment",arraylist.get(0).getCocComment());
        Log.e("Comment",arraylist.get(0).getCocComment());*/
        btn_pdf.setOnClickListener(view -> {
            String pdf_url = arraylist.get(0).getOrdPdfLink();
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(ApiConstants.PDF_URL + Uri.parse(pdf_url)));
            startActivity(browserIntent);
        });
        card.setVisibility(View.VISIBLE);

        btn_conform.setOnClickListener(view -> {
            /*txt_ordstatus.setText("Completed");
            callOrderConfromApi();*/
            if (arraylist.get(0).getOrderStatusName().equals("Delivered")) {
                openDialog();
            } else if (arraylist.get(0).getOrderStatusName().equals("Order assigned to third party")) {
                openDialog();
            } else {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                alertDialogBuilder.setMessage("Order delivery yet not confrim by assigned truck driver.");
                alertDialogBuilder.setPositiveButton("Ok",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                            }
                        });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });

        getData();
    }

    private void getData() {

        if (arraylist.get(0).getOrderStatusName().equals("Delivered")) {
            btn_conform.setEnabled(true);
        } else if (arraylist.get(0).getOrderStatusName().equals("Order assigned to third party")) {
            btn_conform.setEnabled(true);
        } else if (arraylist.get(0).getOrderStatusName().equals("Completed")) {
            btn_conform.setVisibility(View.GONE);
        } /*else if (arraylist.get(0).getOrderStatusName().equals("Order Confirmed")) {
            btn_conform.setText("Confirmed");
            //btn_conform.setEnabled(false);
            btn_conform.setBackground(getDrawable(R.drawable.grayback));
            btn_conform.setTextColor(Color.parseColor("#ffffff"));
        }*/ else {
            // btn_conform.setEnabled(false);
            btn_conform.setBackground(getDrawable(R.drawable.grayback));
            //btn_conform.setBackgroundColor(Color.parseColor("#7D7F81"));
            btn_conform.setTextColor(Color.parseColor("#ffffff"));
        }
        try {
            if (arraylist.get(0).getCocComment().equals("")) {
                card.setVisibility(View.GONE);
            } else {
                txt_comment.setText(arraylist.get(0).getCocComment());
                card.setVisibility(View.VISIBLE);
            }
            if (arraylist.get(0).getCocImages().equals("")) {
                card1.setVisibility(View.GONE);
            } else {
                layoutManager = new LinearLayoutManager(this);
                imagelist.setLayoutManager(layoutManager);
                image_list = new ArrayList<>();
                card1.setVisibility(View.VISIBLE);
                String[] separated = arraylist.get(0).getCocImages().split(",");
                for (int i = 0; i < separated.length; i++) {
                    image_list.add(separated[i]);
                    Log.e("Image is", image_list.get(i));

                }

                ImageAdapter adapter = new ImageAdapter(getApplicationContext(), image_list);
                imagelist.setAdapter(adapter);

            }

        } catch (Exception e) {

        }


        DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy");
        String inputDateStr = arraylist.get(0).getOrdDate();
        Date date = null;
        try {
            date = inputFormat.parse(inputDateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String outputDateStr = outputFormat.format(date);
        txt_date.setText(outputDateStr);

        txt_dl_date.setText(arraylist.get(0).getOrdDeliveryDate());

        //txt_date.setText(arraylist.get(0).getOrdDate());

        txt_odrno.setText("#" + arraylist.get(0).getMs_ord_id());
        txt_odr_no.setText("Order #" + arraylist.get(0).getMs_ord_id() + " Invoice ");
        txt_qty.setText(arraylist.get(0).getOrdQty() + " Qty");

        if (arraylist.get(0).getOrderStatusName().equals("Delivered")) {
            // txt_ordstatus.setText("Received");
            txt_ordstatus.setText("Delivered");

        } else {
            txt_ordstatus.setText(arraylist.get(0).getOrderStatusName());
        }

        txt_notes.setText(arraylist.get(0).getOrdNotes());
        txt_productname.setText(arraylist.get(0).getProductName());

        if (arraylist.get(0).getProductName().equals("Concrete")) {
            card_type.setVisibility(View.GONE);
            card_concredringe.setVisibility(View.GONE);
            card_gnrlmetrl.setVisibility(View.GONE);
            card_mesh.setVisibility(View.GONE);
            card_ppe.setVisibility(View.GONE);
            card_stools.setVisibility(View.GONE);
            card_pdp.setVisibility(View.GONE);
            card_pdw.setVisibility(View.GONE);
            card_pr.setVisibility(View.GONE);
            card_claydrinage.setVisibility(View.GONE);
            card_kerbs.setVisibility(View.GONE);
            card_flags.setVisibility(View.GONE);
            card_blockpaves.setVisibility(View.GONE);

            card_aggre.setVisibility(View.VISIBLE);
            card_slump.setVisibility(View.VISIBLE);
            card_mix.setVisibility(View.VISIBLE);

            txt_mix.setText("Mix / " + arraylist.get(0).getOrdAttributes().getMix());
            txt_aggsize.setText("Aggregate Size / " + arraylist.get(0).getOrdAttributes().getAggregateSize());
            txt_slump.setText("Slump / " + arraylist.get(0).getOrdAttributes().getSlump());

        } else if (arraylist.get(0).getProductName().equals("Aggregates")) {
            card_type.setVisibility(View.VISIBLE);
            card_aggre.setVisibility(View.GONE);
            card_slump.setVisibility(View.GONE);
            card_mix.setVisibility(View.GONE);
            card_concredringe.setVisibility(View.GONE);
            card_gnrlmetrl.setVisibility(View.GONE);
            card_mesh.setVisibility(View.GONE);
            card_ppe.setVisibility(View.GONE);
            card_stools.setVisibility(View.GONE);
            card_pdp.setVisibility(View.GONE);
            card_pdw.setVisibility(View.GONE);
            card_pr.setVisibility(View.GONE);
            card_claydrinage.setVisibility(View.GONE);
            card_kerbs.setVisibility(View.GONE);
            card_flags.setVisibility(View.GONE);
            card_blockpaves.setVisibility(View.GONE);

            txt_type.setText("Type / " + arraylist.get(0).getOrdAttributes().getType());

        } else if (arraylist.get(0).getProductName().equals("Materials")) {

            if (arraylist.get(0).getOrdAttributes().getOrdProductLabel().equals("Concrete drainage")) {
                card_concredringe.setVisibility(View.VISIBLE);
                card_gnrlmetrl.setVisibility(View.GONE);
                card_mesh.setVisibility(View.GONE);
                card_ppe.setVisibility(View.GONE);
                card_stools.setVisibility(View.GONE);
                card_pdp.setVisibility(View.GONE);
                card_pdw.setVisibility(View.GONE);
                card_pr.setVisibility(View.GONE);
                card_claydrinage.setVisibility(View.GONE);
                card_kerbs.setVisibility(View.GONE);
                card_flags.setVisibility(View.GONE);
                card_blockpaves.setVisibility(View.GONE);

                txt_concrete_dringe.setText("Concrete Drainage :- " + arraylist.get(0).getOrdAttributes().getOrdLabel() + " : " + arraylist.get(0).getOrdAttributes().getOrdConcrete());
            } else if (arraylist.get(0).getOrdAttributes().getOrdProductLabel().equals("Mesh and accessories")) {
                card_mesh.setVisibility(View.VISIBLE);
                card_gnrlmetrl.setVisibility(View.GONE);
                card_concredringe.setVisibility(View.GONE);
                card_ppe.setVisibility(View.GONE);
                card_stools.setVisibility(View.GONE);
                card_pdp.setVisibility(View.GONE);
                card_pdw.setVisibility(View.GONE);
                card_pr.setVisibility(View.GONE);
                card_claydrinage.setVisibility(View.GONE);
                card_kerbs.setVisibility(View.GONE);
                card_flags.setVisibility(View.GONE);
                card_blockpaves.setVisibility(View.GONE);
                txt_mesh.setText("Mesh :- " + arraylist.get(0).getOrdAttributes().getOrdMesh());
            } else if (arraylist.get(0).getOrdAttributes().getOrdProductLabel().equals("General building materials")) {
                card_gnrlmetrl.setVisibility(View.VISIBLE);
                card_ppe.setVisibility(View.GONE);
                card_mesh.setVisibility(View.GONE);
                card_concredringe.setVisibility(View.GONE);
                card_stools.setVisibility(View.GONE);
                card_pdp.setVisibility(View.GONE);
                card_pdw.setVisibility(View.GONE);
                card_pr.setVisibility(View.GONE);
                card_claydrinage.setVisibility(View.GONE);
                card_kerbs.setVisibility(View.GONE);
                card_flags.setVisibility(View.GONE);
                card_blockpaves.setVisibility(View.GONE);
                txt_gnrlmtrl.setText("GeneralBuilding Material :- " + arraylist.get(0).getOrdAttributes().getGeneralbuldingMaterial());
            } else if (arraylist.get(0).getOrdAttributes().getOrdProductLabel().equals("PPE")) {
                card_ppe.setVisibility(View.VISIBLE);
                card_gnrlmetrl.setVisibility(View.GONE);
                card_mesh.setVisibility(View.GONE);
                card_concredringe.setVisibility(View.GONE);
                card_stools.setVisibility(View.GONE);
                card_pdp.setVisibility(View.GONE);
                card_pdw.setVisibility(View.GONE);
                card_pr.setVisibility(View.GONE);
                card_claydrinage.setVisibility(View.GONE);
                card_kerbs.setVisibility(View.GONE);
                card_flags.setVisibility(View.GONE);
                card_blockpaves.setVisibility(View.GONE);
                txt_ppe.setText("PPE :- " + arraylist.get(0).getOrdAttributes().getOrdPpe());
            } else if (arraylist.get(0).getOrdAttributes().getOrdProductLabel().equals("Small tools")) {
                card_stools.setVisibility(View.VISIBLE);
                card_ppe.setVisibility(View.GONE);
                card_gnrlmetrl.setVisibility(View.GONE);
                card_mesh.setVisibility(View.GONE);
                card_concredringe.setVisibility(View.GONE);
                card_pdp.setVisibility(View.GONE);
                card_pdw.setVisibility(View.GONE);
                card_pr.setVisibility(View.GONE);
                card_claydrinage.setVisibility(View.GONE);
                card_kerbs.setVisibility(View.GONE);
                card_flags.setVisibility(View.GONE);
                card_blockpaves.setVisibility(View.GONE);

                txt_stools.setText("Small tools :- " + arraylist.get(0).getOrdAttributes().getOrdSmalltools());
            } else if (arraylist.get(0).getOrdAttributes().getOrdProductLabel().equals("Plastic Drainage Polypipe")) {

                card_ppe.setVisibility(View.GONE);
                card_gnrlmetrl.setVisibility(View.GONE);
                card_mesh.setVisibility(View.GONE);
                card_concredringe.setVisibility(View.GONE);
                card_stools.setVisibility(View.GONE);
                card_pdp.setVisibility(View.VISIBLE);
                card_pdw.setVisibility(View.GONE);
                card_pr.setVisibility(View.GONE);
                card_claydrinage.setVisibility(View.GONE);
                card_kerbs.setVisibility(View.GONE);
                card_flags.setVisibility(View.GONE);
                card_blockpaves.setVisibility(View.GONE);
                txt_pdp.setText("Plastic Drainage Polypipe :- " + arraylist.get(0).getOrdAttributes().getOrdPlasticDrainagePolypipe());
            } else if (arraylist.get(0).getOrdAttributes().getOrdProductLabel().equals("Polysewer / Ridgidrain")) {

                card_ppe.setVisibility(View.GONE);
                card_gnrlmetrl.setVisibility(View.GONE);
                card_mesh.setVisibility(View.GONE);
                card_concredringe.setVisibility(View.GONE);
                card_stools.setVisibility(View.GONE);
                card_pdp.setVisibility(View.GONE);
                card_pdw.setVisibility(View.GONE);
                card_pr.setVisibility(View.VISIBLE);
                card_claydrinage.setVisibility(View.GONE);
                card_kerbs.setVisibility(View.GONE);
                card_flags.setVisibility(View.GONE);
                card_blockpaves.setVisibility(View.GONE);
                txt_pr.setText("Polysewer / Ridgidrain :- " + arraylist.get(0).getOrdAttributes().getOrdPolysewer());
            } else if (arraylist.get(0).getOrdAttributes().getOrdProductLabel().equals("Plastic Drainage Wavin")) {
                card_ppe.setVisibility(View.GONE);
                card_gnrlmetrl.setVisibility(View.GONE);
                card_mesh.setVisibility(View.GONE);
                card_concredringe.setVisibility(View.GONE);
                card_stools.setVisibility(View.GONE);
                card_pdp.setVisibility(View.GONE);
                card_pdw.setVisibility(View.VISIBLE);
                card_pr.setVisibility(View.GONE);
                card_claydrinage.setVisibility(View.GONE);
                card_kerbs.setVisibility(View.GONE);
                card_flags.setVisibility(View.GONE);
                card_blockpaves.setVisibility(View.GONE);
                txt_pdw.setText("Plastic Drainage Wavin :- " + arraylist.get(0).getOrdAttributes().getOrdPlasticDrainageWavin());
            } else if (arraylist.get(0).getOrdAttributes().getOrdProductLabel().equals("Clay Drainage")) {

                card_ppe.setVisibility(View.GONE);
                card_gnrlmetrl.setVisibility(View.GONE);
                card_mesh.setVisibility(View.GONE);
                card_concredringe.setVisibility(View.GONE);
                card_stools.setVisibility(View.GONE);
                card_pdp.setVisibility(View.GONE);
                card_pdw.setVisibility(View.GONE);
                card_pr.setVisibility(View.GONE);
                card_claydrinage.setVisibility(View.VISIBLE);
                card_kerbs.setVisibility(View.GONE);
                card_flags.setVisibility(View.GONE);
                card_blockpaves.setVisibility(View.GONE);
                txt_claydrinage.setText("Clay Drainage :- " + arraylist.get(0).getOrdAttributes().getOrdClayDrainage());
            } else if (arraylist.get(0).getOrdAttributes().getOrdProductLabel().equals("Kerbs")) {
                card_ppe.setVisibility(View.GONE);
                card_gnrlmetrl.setVisibility(View.GONE);
                card_mesh.setVisibility(View.GONE);
                card_concredringe.setVisibility(View.GONE);
                card_stools.setVisibility(View.GONE);
                card_pdp.setVisibility(View.GONE);
                card_pdw.setVisibility(View.GONE);
                card_pr.setVisibility(View.GONE);
                card_claydrinage.setVisibility(View.GONE);
                card_kerbs.setVisibility(View.GONE);
                card_flags.setVisibility(View.VISIBLE);
                card_blockpaves.setVisibility(View.GONE);
                txt_kerbs.setText("Kerbs :- " + arraylist.get(0).getOrdAttributes().getOrdKerbs());
            } else if (arraylist.get(0).getOrdAttributes().getOrdProductLabel().equals("Flags")) {
                card_ppe.setVisibility(View.GONE);
                card_gnrlmetrl.setVisibility(View.GONE);
                card_mesh.setVisibility(View.GONE);
                card_concredringe.setVisibility(View.GONE);
                card_stools.setVisibility(View.GONE);
                card_pdp.setVisibility(View.GONE);
                card_pdw.setVisibility(View.GONE);
                card_pr.setVisibility(View.GONE);
                card_claydrinage.setVisibility(View.GONE);
                card_kerbs.setVisibility(View.GONE);
                card_flags.setVisibility(View.VISIBLE);
                card_blockpaves.setVisibility(View.GONE);
                txt_flags.setText("Flags :- " + arraylist.get(0).getOrdAttributes().getOrdFlags());
            } else if (arraylist.get(0).getOrdAttributes().getOrdProductLabel().equals("Block Paves")) {
                card_ppe.setVisibility(View.GONE);
                card_gnrlmetrl.setVisibility(View.GONE);
                card_mesh.setVisibility(View.GONE);
                card_concredringe.setVisibility(View.GONE);
                card_stools.setVisibility(View.GONE);
                card_pdp.setVisibility(View.GONE);
                card_pdw.setVisibility(View.GONE);
                card_pr.setVisibility(View.GONE);
                card_claydrinage.setVisibility(View.GONE);
                card_kerbs.setVisibility(View.GONE);
                card_flags.setVisibility(View.GONE);
                card_blockpaves.setVisibility(View.VISIBLE);
                txt_blockpaves.setText("Block Paves :- " + arraylist.get(0).getOrdAttributes().getOrdBlockpaves());
            }
        }
    }

    private void openDialog() {

        dialog = new Dialog(SubOrderHistory.this);
        dialog.getWindow();
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_ordrconfrm);
        dialog.setCancelable(true);

        Button btn_submt = dialog.findViewById(R.id.btn_submit);
        ImageView txt_cancel = dialog.findViewById(R.id.img_close);
        edt_cmnt = dialog.findViewById(R.id.edt_cmmnt);
        listView = dialog.findViewById(R.id.listView);
        avi1 = dialog.findViewById(R.id.avi);
        LinearLayout imgpic = dialog.findViewById(R.id.img_picibutton);

        txt_cancel.setOnClickListener(view -> dialog.dismiss());
        imgpic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
                //showChooser();
            }
        });

        edt_cmnt.setOnTouchListener(new View.OnTouchListener() {
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

        btn_submt.setOnClickListener(view -> {
            callSubmitApi();
        });


        dialog.show();
        Window window = dialog.getWindow();
        assert window != null;
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    private void callSubmitApi() {

        try {
            parts = new ArrayList<>();

            if (imagesPathList != null) {
                for (int i = 0; i < imagesPathList.size(); i++) {
                    parts.add(prepareFilePart("coc_images[]", imagesPathList.get(i)));
                }
            }
            RequestBody u_id = RequestBody.create(MediaType.parse("text/plain"), sessionManager.getKeyId());
            RequestBody msid = RequestBody.create(MediaType.parse("text/plain"), arraylist.get(0).getMs_ord_id());
            RequestBody cmnt = RequestBody.create(MediaType.parse("text/plain"), edt_cmnt.getText().toString());

            avi1.show();
            Observable<TimeLogModel> responseObservable = apiservice.confirmOrder(u_id, msid, cmnt, parts);

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
                    .subscribe(new Observer<TimeLogModel>() {
                        @Override
                        public void onCompleted() {
                            avi1.hide();
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.e("error", "" + e.getMessage());
                        }

                        @Override
                        public void onNext(TimeLogModel model) {
                            statusCode = model.getStatusCode();
                            if (statusCode == 200) {
                                Utility.displayToast(SubOrderHistory.this, model.getMessage());
                                txt_ordstatus.setText("Order Delivered");
                                btn_conform.setText("Material Received");
                                btn_conform.setEnabled(false);
                                btn_conform.setBackground(getDrawable(R.drawable.grayback));
                                btn_conform.setTextColor(Color.parseColor("#ffffff"));
                                dialog.dismiss();
                            } else {
                                Utility.displayToast(SubOrderHistory.this, model.getMessage());
                            }
                        }
                    });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void selectImage() {

        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(SubOrderHistory.this);
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (options[item].equals("Take Photo")) {

                    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                        String fileName = "temp.jpg";
                        ContentValues values = new ContentValues();
                        values.put(MediaStore.Images.Media.TITLE, fileName);
                        mCapturedImageURI = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
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

    /*
        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @NonNull
        private MultipartBody.Part prepareFilePart(String partName, Uri fileUri) {
            Log.e("All File detail is ", String.valueOf(fileUri));
            File file = FileUtils.getFile(getApplicationContext(), fileUri);
            RequestBody requestFile = RequestBody.create(MediaType.parse(Objects.requireNonNull(getApplicationContext().getContentResolver().getType(fileUri))), file);
            return MultipartBody.Part.createFormData(partName, file.getName(), requestFile);
        }*/
    @NonNull
    private MultipartBody.Part prepareFilePart(String partName, Uri fileUri) {
        RequestBody requestFile;
        File file = FileUtils.getFile(this, fileUri);
        requestFile = RequestBody.create(MediaType.parse("image/*"), file);
        return MultipartBody.Part.createFormData(partName, file.getName(), requestFile);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case CAPTURE_IMAGE:
                ImageCropFunctionCustom(mCapturedImageURI);

                break;
            case CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE:

                CropImage.ActivityResult result = CropImage.getActivityResult(data);
                Compressor compressedImageFile = new Compressor(this);
                compressedImageFile.setQuality(60);

                try {
                    // Get the file path from the URI
                    File file = compressedImageFile.compressToFile(new File(result.getUri().getPath()));
                    Uri uri = Uri.fromFile(file);

                    Log.e("TAG", "capture uri:-- " + uri);
                    final String path = FileUtils.getPath(getApplicationContext(), uri);
                    Log.e("Single File Selected", path);

                    imagesPathList.add(uri);
                    Log.e("Path is ", path);

                    MyAdapter mAdapter = new MyAdapter(getApplicationContext(), imagesPathList);
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
                                MyAdapter mAdapter = new MyAdapter(getApplicationContext(), imagesPathList);
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
                            final String path = FileUtils.getPath(getApplicationContext(), uri);
                            Log.e("Single File Selected", path);

                            imagesPathList.add(uri);
                            Log.e("Path is ", path);
                            MyAdapter mAdapter = new MyAdapter(getApplicationContext(), imagesPathList);
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
                .getIntent(this);
        startActivityForResult(intent, CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE);
    }

/*
    private void callOrderConfromApi() {

        avi.show();
        Observable<TimeLogModel> responseObservable = apiservice.confirmOrder(arraylist.get(0).getMs_ord_id());

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
                .subscribe(new Observer<TimeLogModel>() {
                    @Override
                    public void onCompleted() {

                        avi.hide();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("error", "" + e.getMessage());
                    }

                    @Override
                    public void onNext(TimeLogModel model) {
                        statusCode = model.getStatusCode();
                        if (statusCode == 200) {
                            Utility.displayToast(SubOrderHistory.this, model.getMessage());
                            btn_conform.setEnabled(false);
                            btn_conform.setBackground(getDrawable(R.drawable.grayback));
                            btn_conform.setTextColor(Color.parseColor("#ffffff"));

                        } else {
                            Utility.displayToast(SubOrderHistory.this, model.getMessage());
                        }
                    }
                });

    }
*/

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        if (Utility.getAppcon().getSession().screen_name.equals("OderHistoryList")) {
            callHandler();
        } else if (Utility.getAppcon().getSession().screen_name.equals("openOderHistoryList")) {
            callHandler2();
        }
    }

    private void callHandler() {
        if (OrderHistoryList.handler != null) {
            Message message = Message.obtain();
            message.what = 100;
            if (OrderHistoryList.handler != null) {
                OrderHistoryList.handler.sendMessage(message);
            }

        }
    }

    private void callHandler2() {
        if (OpenOrderHistoryList.handler != null) {
            Message message = Message.obtain();
            message.what = 100;
            if (OpenOrderHistoryList.handler != null) {
                OpenOrderHistoryList.handler.sendMessage(message);
            }

        }
    }
}

/*getSiteName();
        spn_site.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (spn_site.getSelectedItem() == "Select site") {
                    site = "";
                    //Do nothing.
                } else {
                    site = spn_site.getSelectedItem().toString();
                    SpinnerModel.Data datalist = data.get(i - 1);
                    s_id = datalist.getSiteId();

                    callOrderHistoryApi();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        getOrderStatus();
        spn_orders.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (spn_orders.getSelectedItem() == "Select Order Status") {
                    order = "";
                    //Do nothing.
                } else {
                    order = spn_orders.getSelectedItem().toString();
                    OrderStatusModel.Data datalist = data1.get(i - 1);
                    o_id = datalist.getOsId();

                    if (s_id.equals("")) {
                        Utility.displayToast(getApplicationContext(), "Please select site");
                    } else {
                        getStatusOrderHistoryApi();

                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
*/

    /*private void getOrderStatus() {

        avi.show();
        Observable<OrderStatusModel> responseObservable = apiservice.get_allOrderStatus();

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
                .subscribe(new Observer<OrderStatusModel>() {
                    @Override
                    public void onCompleted() {

                        avi.hide();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("error", "" + e.getMessage());
                    }

                    @Override
                    public void onNext(OrderStatusModel model) {
                        statusCode = model.getStatusCode();
                        if (statusCode == 200) {
                            data1 = model.getData();
                            for (int i = 0; i < model.getData().size(); i++) {
                                orderarray.add(model.getData().get(i).getOsName());
                            }
                        }
                    }
                });

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, orderarray);
        dataAdapter.add("Select Order Status");
        dataAdapter.setDropDownViewResource(R.layout.spinner_item);
        //spn_orders.setAdapter(dataAdapter);

    }

    private void getSiteName() {

        avi.show();
        Observable<SpinnerModel> responseObservable = apiservice.get_userSite(sessionManager.getKeyId());

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
                                sitearray.add(model.getData().get(i).getSiteName());
                            }
                        }
                    }
                });

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, sitearray);
        dataAdapter.add("Select site");
        dataAdapter.setDropDownViewResource(R.layout.spinner_item);
        //spn_site.setAdapter(dataAdapter);

    }*/

    /*private void getStatusOrderHistoryApi() {

        avi.show();

        Observable<OrderDetailsModel> responseObservable = apiservice.get_statusOrderHistory(
                s_id,
                o_id
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
                        //getAttributename1(p_id);
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

                            DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
                            DateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy");
                            String inputDateStr = model.getData().get(0).getOrdDate();
                            Date date = null;
                            try {
                                date = inputFormat.parse(inputDateStr);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            String outputDateStr = outputFormat.format(date);
                            txt_date.setText(outputDateStr);
                            //txt_date.setText(model.getData().get(0).getOrdDate());
                            txt_odrno.setText("Order ##XX" + model.getData().get(0).getOrdId());
                            txt_odr_no.setText("Order ##XX" + model.getData().get(0).getOrdId() + " Invoice");
                            txt_qty.setText(model.getData().get(0).getOrdQty() + " Qty");
                            txt_ordstatus.setText(model.getData().get(0).getOrderStatusName());
                            txt_notes.setText(model.getData().get(0).getOrdNotes());
                            txt_productname.setText(model.getData().get(0).getProductName());

                            if (model.getData().get(0).getProductName().equals("Concrete")) {
                                card_type.setVisibility(View.GONE);
                                card_aggre.setVisibility(View.VISIBLE);
                                card_slump.setVisibility(View.VISIBLE);
                                card_mix.setVisibility(View.VISIBLE);

                                txt_mix.setText("Mix / " + model.getData().get(0).getOrdAttributes().getMix());
                                txt_aggsize.setText("Aggregate Size / " + model.getData().get(0).getOrdAttributes().getAggregateSize());
                                txt_slump.setText("Slump / " + model.getData().get(0).getOrdAttributes().getSlump());

                            } else if (model.getData().get(0).getProductName().equals("Aggregates")) {
                                card_type.setVisibility(View.VISIBLE);
                                card_aggre.setVisibility(View.GONE);
                                card_slump.setVisibility(View.GONE);
                                card_mix.setVisibility(View.GONE);

                                txt_type.setText("Type / " + model.getData().get(0).getOrdAttributes().getType());

                            } else if (model.getData().get(0).getProductName().equals("Materials")) {


                                if (model.getData().get(0).getOrdAttributes().getOrdProductLabel().equals("Concrete drainage")) {
                                    card_concredringe.setVisibility(View.VISIBLE);
                                    card_gnrlmetrl.setVisibility(View.GONE);
                                    card_mesh.setVisibility(View.GONE);
                                    card_ppe.setVisibility(View.GONE);
                                    card_stools.setVisibility(View.GONE);
                                    card_pdp.setVisibility(View.GONE);
                                    card_pdw.setVisibility(View.GONE);
                                    card_pr.setVisibility(View.GONE);
                                    card_claydrinage.setVisibility(View.GONE);
                                    card_kerbs.setVisibility(View.GONE);
                                    card_flags.setVisibility(View.GONE);
                                    card_blockpaves.setVisibility(View.GONE);
                                    txt_concrete_dringe.setText("Concrete Drainage :- " + model.getData().get(0).getOrdAttributes().getOrdLabel() + " : " + model.getData().get(0).getOrdAttributes().getOrdConcrete());
                                } else if (model.getData().get(0).getOrdAttributes().getOrdProductLabel().equals("Mesh and accessories")) {
                                    card_mesh.setVisibility(View.VISIBLE);
                                    card_gnrlmetrl.setVisibility(View.GONE);
                                    card_concredringe.setVisibility(View.GONE);
                                    card_ppe.setVisibility(View.GONE);
                                    card_stools.setVisibility(View.GONE);
                                    card_pdp.setVisibility(View.GONE);
                                    card_pdw.setVisibility(View.GONE);
                                    card_pr.setVisibility(View.GONE);
                                    card_claydrinage.setVisibility(View.GONE);
                                    card_kerbs.setVisibility(View.GONE);
                                    card_flags.setVisibility(View.GONE);
                                    card_blockpaves.setVisibility(View.GONE);
                                    txt_mesh.setText("Mesh :- " + model.getData().get(0).getOrdAttributes().getOrdMesh());
                                } else if (model.getData().get(0).getOrdAttributes().getOrdProductLabel().equals("General building materials")) {
                                    card_gnrlmetrl.setVisibility(View.VISIBLE);
                                    card_ppe.setVisibility(View.GONE);
                                    card_mesh.setVisibility(View.GONE);
                                    card_concredringe.setVisibility(View.GONE);
                                    card_stools.setVisibility(View.GONE);
                                    card_pdp.setVisibility(View.GONE);
                                    card_pdw.setVisibility(View.GONE);
                                    card_pr.setVisibility(View.GONE);
                                    card_claydrinage.setVisibility(View.GONE);
                                    card_kerbs.setVisibility(View.GONE);
                                    card_flags.setVisibility(View.GONE);
                                    card_blockpaves.setVisibility(View.GONE);
                                    txt_gnrlmtrl.setText("GeneralBuilding Material :- " + model.getData().get(0).getOrdAttributes().getGeneralbuldingMaterial());
                                } else if (model.getData().get(0).getOrdAttributes().getOrdProductLabel().equals("PPE")) {
                                    card_ppe.setVisibility(View.VISIBLE);
                                    card_gnrlmetrl.setVisibility(View.GONE);
                                    card_mesh.setVisibility(View.GONE);
                                    card_concredringe.setVisibility(View.GONE);
                                    card_stools.setVisibility(View.GONE);
                                    card_pdp.setVisibility(View.GONE);
                                    card_pdw.setVisibility(View.GONE);
                                    card_pr.setVisibility(View.GONE);
                                    card_claydrinage.setVisibility(View.GONE);
                                    card_kerbs.setVisibility(View.GONE);
                                    card_flags.setVisibility(View.GONE);
                                    card_blockpaves.setVisibility(View.GONE);
                                    txt_ppe.setText("PPE :- " + model.getData().get(0).getOrdAttributes().getOrdPpe());
                                } else if (model.getData().get(0).getOrdAttributes().getOrdProductLabel().equals("Small tools")) {
                                    card_stools.setVisibility(View.VISIBLE);
                                    card_ppe.setVisibility(View.GONE);
                                    card_gnrlmetrl.setVisibility(View.GONE);
                                    card_mesh.setVisibility(View.GONE);
                                    card_concredringe.setVisibility(View.GONE);
                                    card_pdp.setVisibility(View.GONE);
                                    card_pdw.setVisibility(View.GONE);
                                    card_pr.setVisibility(View.GONE);
                                    card_claydrinage.setVisibility(View.GONE);
                                    card_kerbs.setVisibility(View.GONE);
                                    card_flags.setVisibility(View.GONE);
                                    card_blockpaves.setVisibility(View.GONE);
                                    txt_stools.setText("Small tools :- " + model.getData().get(0).getOrdAttributes().getOrdSmalltools());
                                } else if (model.getData().get(0).getOrdAttributes().getOrdProductLabel().equals("Plastic Drainage Polypipe")) {
                                    card_ppe.setVisibility(View.GONE);
                                    card_gnrlmetrl.setVisibility(View.GONE);
                                    card_mesh.setVisibility(View.GONE);
                                    card_concredringe.setVisibility(View.GONE);
                                    card_stools.setVisibility(View.GONE);
                                    card_pdp.setVisibility(View.VISIBLE);
                                    card_pdw.setVisibility(View.GONE);
                                    card_pr.setVisibility(View.GONE);
                                    card_claydrinage.setVisibility(View.GONE);
                                    card_kerbs.setVisibility(View.GONE);
                                    card_flags.setVisibility(View.GONE);
                                    card_blockpaves.setVisibility(View.GONE);
                                    txt_pdp.setText("Plastic Drainage Polypipe :- " + model.getData().get(0).getOrdAttributes().getOrdPlasticDrainagePolypipe());
                                } else if (model.getData().get(0).getOrdAttributes().getOrdProductLabel().equals("Polysewer / Ridgidrain")) {
                                    card_ppe.setVisibility(View.GONE);
                                    card_gnrlmetrl.setVisibility(View.GONE);
                                    card_mesh.setVisibility(View.GONE);
                                    card_concredringe.setVisibility(View.GONE);
                                    card_stools.setVisibility(View.GONE);
                                    card_pdp.setVisibility(View.GONE);
                                    card_pdw.setVisibility(View.GONE);
                                    card_pr.setVisibility(View.VISIBLE);
                                    card_claydrinage.setVisibility(View.GONE);
                                    card_kerbs.setVisibility(View.GONE);
                                    card_flags.setVisibility(View.GONE);
                                    card_blockpaves.setVisibility(View.GONE);
                                    txt_pr.setText("Polysewer / Ridgidrain :- " + model.getData().get(0).getOrdAttributes().getOrdPolysewer());
                                } else if (model.getData().get(0).getOrdAttributes().getOrdProductLabel().equals("Plastic Drainage Wavin")) {
                                    card_ppe.setVisibility(View.GONE);
                                    card_gnrlmetrl.setVisibility(View.GONE);
                                    card_mesh.setVisibility(View.GONE);
                                    card_concredringe.setVisibility(View.GONE);
                                    card_stools.setVisibility(View.GONE);
                                    card_pdp.setVisibility(View.GONE);
                                    card_pdw.setVisibility(View.VISIBLE);
                                    card_pr.setVisibility(View.GONE);
                                    card_claydrinage.setVisibility(View.GONE);
                                    card_kerbs.setVisibility(View.GONE);
                                    card_flags.setVisibility(View.GONE);
                                    card_blockpaves.setVisibility(View.GONE);
                                    txt_pdw.setText("Plastic Drainage Wavin :- " + model.getData().get(0).getOrdAttributes().getOrdPlasticDrainageWavin());
                                } else if (model.getData().get(0).getOrdAttributes().getOrdProductLabel().equals("Clay Drainage")) {
                                    card_ppe.setVisibility(View.GONE);
                                    card_gnrlmetrl.setVisibility(View.GONE);
                                    card_mesh.setVisibility(View.GONE);
                                    card_concredringe.setVisibility(View.GONE);
                                    card_stools.setVisibility(View.GONE);
                                    card_pdp.setVisibility(View.GONE);
                                    card_pdw.setVisibility(View.GONE);
                                    card_pr.setVisibility(View.GONE);
                                    card_claydrinage.setVisibility(View.VISIBLE);
                                    card_kerbs.setVisibility(View.GONE);
                                    card_flags.setVisibility(View.GONE);
                                    card_blockpaves.setVisibility(View.GONE);
                                    txt_claydrinage.setText("Clay Drainage :- " + model.getData().get(0).getOrdAttributes().getOrdClayDrainage());
                                } else if (model.getData().get(0).getOrdAttributes().getOrdProductLabel().equals("Kerbs")) {
                                    card_ppe.setVisibility(View.GONE);
                                    card_gnrlmetrl.setVisibility(View.GONE);
                                    card_mesh.setVisibility(View.GONE);
                                    card_concredringe.setVisibility(View.GONE);
                                    card_stools.setVisibility(View.GONE);
                                    card_pdp.setVisibility(View.GONE);
                                    card_pdw.setVisibility(View.GONE);
                                    card_pr.setVisibility(View.GONE);
                                    card_claydrinage.setVisibility(View.GONE);
                                    card_kerbs.setVisibility(View.GONE);
                                    card_flags.setVisibility(View.VISIBLE);
                                    card_blockpaves.setVisibility(View.GONE);
                                    txt_kerbs.setText("Kerbs :- " + model.getData().get(0).getOrdAttributes().getOrdKerbs());
                                } else if (model.getData().get(0).getOrdAttributes().getOrdProductLabel().equals("Flags")) {
                                    card_ppe.setVisibility(View.GONE);
                                    card_gnrlmetrl.setVisibility(View.GONE);
                                    card_mesh.setVisibility(View.GONE);
                                    card_concredringe.setVisibility(View.GONE);
                                    card_stools.setVisibility(View.GONE);
                                    card_pdp.setVisibility(View.GONE);
                                    card_pdw.setVisibility(View.GONE);
                                    card_pr.setVisibility(View.GONE);
                                    card_claydrinage.setVisibility(View.GONE);
                                    card_kerbs.setVisibility(View.GONE);
                                    card_flags.setVisibility(View.VISIBLE);
                                    card_blockpaves.setVisibility(View.GONE);
                                    txt_flags.setText("Flags :- " + model.getData().get(0).getOrdAttributes().getOrdFlags());
                                } else if (model.getData().get(0).getOrdAttributes().getOrdProductLabel().equals("Block Paves")) {
                                    card_ppe.setVisibility(View.GONE);
                                    card_gnrlmetrl.setVisibility(View.GONE);
                                    card_mesh.setVisibility(View.GONE);
                                    card_concredringe.setVisibility(View.GONE);
                                    card_stools.setVisibility(View.GONE);
                                    card_pdp.setVisibility(View.GONE);
                                    card_pdw.setVisibility(View.GONE);
                                    card_pr.setVisibility(View.GONE);
                                    card_claydrinage.setVisibility(View.GONE);
                                    card_kerbs.setVisibility(View.GONE);
                                    card_flags.setVisibility(View.GONE);
                                    card_blockpaves.setVisibility(View.VISIBLE);
                                    txt_blockpaves.setText("Block Paves :- " + model.getData().get(0).getOrdAttributes().getOrdBlockpaves());
                                }

                            }
                        }
                    }
                });

    }

    private void callOrderHistoryApi() {

        avi.show();

        Observable<OrderDetailsModel> responseObservable = apiservice.get_allOrderHistory(
                s_id
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
                        //getAttributename1(p_id);
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

                            DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
                            DateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy");
                            String inputDateStr = model.getData().get(0).getOrdDate();
                            Date date = null;
                            try {
                                date = inputFormat.parse(inputDateStr);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            String outputDateStr = outputFormat.format(date);
                            txt_date.setText(outputDateStr);

                            //txt_date.setText(model.getData().get(0).getOrdDate());
                            txt_odrno.setText("Order ##XX" + model.getData().get(0).getOrdId());
                            txt_odr_no.setText("Order ##XX" + model.getData().get(0).getOrdId() + " Invoice");
                            txt_qty.setText(model.getData().get(0).getOrdQty() + " Qty");
                            txt_ordstatus.setText(model.getData().get(0).getOrderStatusName());
                            txt_notes.setText(model.getData().get(0).getOrdNotes());
                            txt_productname.setText(model.getData().get(0).getProductName());

                            if (model.getData().get(0).getProductName().equals("Concrete")) {
                                card_type.setVisibility(View.GONE);
                                card_aggre.setVisibility(View.VISIBLE);
                                card_slump.setVisibility(View.VISIBLE);
                                card_mix.setVisibility(View.VISIBLE);

                                txt_mix.setText("Mix / " + model.getData().get(0).getOrdAttributes().getMix());
                                txt_aggsize.setText("Aggregate Size / " + model.getData().get(0).getOrdAttributes().getAggregateSize());
                                txt_slump.setText("Slump / " + model.getData().get(0).getOrdAttributes().getSlump());

                            } else if (model.getData().get(0).getProductName().equals("Aggregates")) {
                                card_type.setVisibility(View.VISIBLE);
                                card_aggre.setVisibility(View.GONE);
                                card_slump.setVisibility(View.GONE);
                                card_mix.setVisibility(View.GONE);

                                txt_type.setText("Type / " + model.getData().get(0).getOrdAttributes().getType());

                            } else if (model.getData().get(0).getProductName().equals("Materials")) {

                                if (model.getData().get(0).getOrdAttributes().getOrdProductLabel().equals("Concrete drainage")) {
                                    card_concredringe.setVisibility(View.VISIBLE);
                                    card_gnrlmetrl.setVisibility(View.GONE);
                                    card_mesh.setVisibility(View.GONE);
                                    card_ppe.setVisibility(View.GONE);
                                    card_stools.setVisibility(View.GONE);
                                    card_pdp.setVisibility(View.GONE);
                                    card_pdw.setVisibility(View.GONE);
                                    card_pr.setVisibility(View.GONE);
                                    card_claydrinage.setVisibility(View.GONE);
                                    card_kerbs.setVisibility(View.GONE);
                                    card_flags.setVisibility(View.GONE);
                                    card_blockpaves.setVisibility(View.GONE);
                                    txt_concrete_dringe.setText("Concrete Drainage :- " + model.getData().get(0).getOrdAttributes().getOrdLabel() + " : " + model.getData().get(0).getOrdAttributes().getOrdConcrete());
                                } else if (model.getData().get(0).getOrdAttributes().getOrdProductLabel().equals("Mesh and accessories")) {
                                    card_mesh.setVisibility(View.VISIBLE);
                                    card_gnrlmetrl.setVisibility(View.GONE);
                                    card_concredringe.setVisibility(View.GONE);
                                    card_ppe.setVisibility(View.GONE);
                                    card_stools.setVisibility(View.GONE);
                                    card_pdp.setVisibility(View.GONE);
                                    card_pdw.setVisibility(View.GONE);
                                    card_pr.setVisibility(View.GONE);
                                    card_claydrinage.setVisibility(View.GONE);
                                    card_kerbs.setVisibility(View.GONE);
                                    card_flags.setVisibility(View.GONE);
                                    card_blockpaves.setVisibility(View.GONE);
                                    txt_mesh.setText("Mesh :- " + model.getData().get(0).getOrdAttributes().getOrdMesh());
                                } else if (model.getData().get(0).getOrdAttributes().getOrdProductLabel().equals("General building materials")) {
                                    card_gnrlmetrl.setVisibility(View.VISIBLE);
                                    card_ppe.setVisibility(View.GONE);
                                    card_mesh.setVisibility(View.GONE);
                                    card_concredringe.setVisibility(View.GONE);
                                    card_stools.setVisibility(View.GONE);
                                    card_pdp.setVisibility(View.GONE);
                                    card_pdw.setVisibility(View.GONE);
                                    card_pr.setVisibility(View.GONE);
                                    card_claydrinage.setVisibility(View.GONE);
                                    card_kerbs.setVisibility(View.GONE);
                                    card_flags.setVisibility(View.GONE);
                                    card_blockpaves.setVisibility(View.GONE);
                                    txt_gnrlmtrl.setText("GeneralBuilding Material :- " + model.getData().get(0).getOrdAttributes().getGeneralbuldingMaterial());
                                } else if (model.getData().get(0).getOrdAttributes().getOrdProductLabel().equals("PPE")) {
                                    card_ppe.setVisibility(View.VISIBLE);
                                    card_gnrlmetrl.setVisibility(View.GONE);
                                    card_mesh.setVisibility(View.GONE);
                                    card_concredringe.setVisibility(View.GONE);
                                    card_stools.setVisibility(View.GONE);
                                    card_pdp.setVisibility(View.GONE);
                                    card_pdw.setVisibility(View.GONE);
                                    card_pr.setVisibility(View.GONE);
                                    card_claydrinage.setVisibility(View.GONE);
                                    card_kerbs.setVisibility(View.GONE);
                                    card_flags.setVisibility(View.GONE);
                                    card_blockpaves.setVisibility(View.GONE);
                                    txt_ppe.setText("PPE :- " + model.getData().get(0).getOrdAttributes().getOrdPpe());
                                } else if (model.getData().get(0).getOrdAttributes().getOrdProductLabel().equals("Small tools")) {
                                    card_stools.setVisibility(View.VISIBLE);
                                    card_ppe.setVisibility(View.GONE);
                                    card_gnrlmetrl.setVisibility(View.GONE);
                                    card_mesh.setVisibility(View.GONE);
                                    card_concredringe.setVisibility(View.GONE);
                                    card_pdp.setVisibility(View.GONE);
                                    card_pdw.setVisibility(View.GONE);
                                    card_pr.setVisibility(View.GONE);
                                    card_claydrinage.setVisibility(View.GONE);
                                    card_kerbs.setVisibility(View.GONE);
                                    card_flags.setVisibility(View.GONE);
                                    card_blockpaves.setVisibility(View.GONE);
                                    txt_stools.setText("Small tools :- " + model.getData().get(0).getOrdAttributes().getOrdSmalltools());
                                } else if (model.getData().get(0).getOrdAttributes().getOrdProductLabel().equals("Plastic Drainage Polypipe")) {
                                    card_ppe.setVisibility(View.GONE);
                                    card_gnrlmetrl.setVisibility(View.GONE);
                                    card_mesh.setVisibility(View.GONE);
                                    card_concredringe.setVisibility(View.GONE);
                                    card_stools.setVisibility(View.GONE);
                                    card_pdp.setVisibility(View.VISIBLE);
                                    card_pdw.setVisibility(View.GONE);
                                    card_pr.setVisibility(View.GONE);
                                    card_claydrinage.setVisibility(View.GONE);
                                    card_kerbs.setVisibility(View.GONE);
                                    card_flags.setVisibility(View.GONE);
                                    card_blockpaves.setVisibility(View.GONE);
                                    txt_pdp.setText("Plastic Drainage Polypipe :- " + model.getData().get(0).getOrdAttributes().getOrdPlasticDrainagePolypipe());
                                } else if (model.getData().get(0).getOrdAttributes().getOrdProductLabel().equals("Polysewer / Ridgidrain")) {
                                    card_ppe.setVisibility(View.GONE);
                                    card_gnrlmetrl.setVisibility(View.GONE);
                                    card_mesh.setVisibility(View.GONE);
                                    card_concredringe.setVisibility(View.GONE);
                                    card_stools.setVisibility(View.GONE);
                                    card_pdp.setVisibility(View.GONE);
                                    card_pdw.setVisibility(View.GONE);
                                    card_pr.setVisibility(View.VISIBLE);
                                    card_claydrinage.setVisibility(View.GONE);
                                    card_kerbs.setVisibility(View.GONE);
                                    card_flags.setVisibility(View.GONE);
                                    card_blockpaves.setVisibility(View.GONE);
                                    txt_pr.setText("Polysewer / Ridgidrain :- " + model.getData().get(0).getOrdAttributes().getOrdPolysewer());
                                } else if (model.getData().get(0).getOrdAttributes().getOrdProductLabel().equals("Plastic Drainage Wavin")) {
                                    card_ppe.setVisibility(View.GONE);
                                    card_gnrlmetrl.setVisibility(View.GONE);
                                    card_mesh.setVisibility(View.GONE);
                                    card_concredringe.setVisibility(View.GONE);
                                    card_stools.setVisibility(View.GONE);
                                    card_pdp.setVisibility(View.GONE);
                                    card_pdw.setVisibility(View.VISIBLE);
                                    card_pr.setVisibility(View.GONE);
                                    card_claydrinage.setVisibility(View.GONE);
                                    card_kerbs.setVisibility(View.GONE);
                                    card_flags.setVisibility(View.GONE);
                                    card_blockpaves.setVisibility(View.GONE);
                                    txt_pdw.setText("Plastic Drainage Wavin :- " + model.getData().get(0).getOrdAttributes().getOrdPlasticDrainageWavin());
                                } else if (model.getData().get(0).getOrdAttributes().getOrdProductLabel().equals("Clay Drainage")) {
                                    card_ppe.setVisibility(View.GONE);
                                    card_gnrlmetrl.setVisibility(View.GONE);
                                    card_mesh.setVisibility(View.GONE);
                                    card_concredringe.setVisibility(View.GONE);
                                    card_stools.setVisibility(View.GONE);
                                    card_pdp.setVisibility(View.GONE);
                                    card_pdw.setVisibility(View.GONE);
                                    card_pr.setVisibility(View.GONE);
                                    card_claydrinage.setVisibility(View.VISIBLE);
                                    card_kerbs.setVisibility(View.GONE);
                                    card_flags.setVisibility(View.GONE);
                                    card_blockpaves.setVisibility(View.GONE);
                                    txt_claydrinage.setText("Clay Drainage :- " + model.getData().get(0).getOrdAttributes().getOrdClayDrainage());
                                } else if (model.getData().get(0).getOrdAttributes().getOrdProductLabel().equals("Kerbs")) {
                                    card_ppe.setVisibility(View.GONE);
                                    card_gnrlmetrl.setVisibility(View.GONE);
                                    card_mesh.setVisibility(View.GONE);
                                    card_concredringe.setVisibility(View.GONE);
                                    card_stools.setVisibility(View.GONE);
                                    card_pdp.setVisibility(View.GONE);
                                    card_pdw.setVisibility(View.GONE);
                                    card_pr.setVisibility(View.GONE);
                                    card_claydrinage.setVisibility(View.GONE);
                                    card_kerbs.setVisibility(View.GONE);
                                    card_flags.setVisibility(View.VISIBLE);
                                    card_blockpaves.setVisibility(View.GONE);
                                    txt_kerbs.setText("Kerbs :- " + model.getData().get(0).getOrdAttributes().getOrdKerbs());
                                } else if (model.getData().get(0).getOrdAttributes().getOrdProductLabel().equals("Flags")) {
                                    card_ppe.setVisibility(View.GONE);
                                    card_gnrlmetrl.setVisibility(View.GONE);
                                    card_mesh.setVisibility(View.GONE);
                                    card_concredringe.setVisibility(View.GONE);
                                    card_stools.setVisibility(View.GONE);
                                    card_pdp.setVisibility(View.GONE);
                                    card_pdw.setVisibility(View.GONE);
                                    card_pr.setVisibility(View.GONE);
                                    card_claydrinage.setVisibility(View.GONE);
                                    card_kerbs.setVisibility(View.GONE);
                                    card_flags.setVisibility(View.VISIBLE);
                                    card_blockpaves.setVisibility(View.GONE);
                                    txt_flags.setText("Flags :- " + model.getData().get(0).getOrdAttributes().getOrdFlags());
                                } else if (model.getData().get(0).getOrdAttributes().getOrdProductLabel().equals("Block Paves")) {
                                    card_ppe.setVisibility(View.GONE);
                                    card_gnrlmetrl.setVisibility(View.GONE);
                                    card_mesh.setVisibility(View.GONE);
                                    card_concredringe.setVisibility(View.GONE);
                                    card_stools.setVisibility(View.GONE);
                                    card_pdp.setVisibility(View.GONE);
                                    card_pdw.setVisibility(View.GONE);
                                    card_pr.setVisibility(View.GONE);
                                    card_claydrinage.setVisibility(View.GONE);
                                    card_kerbs.setVisibility(View.GONE);
                                    card_flags.setVisibility(View.GONE);
                                    card_blockpaves.setVisibility(View.VISIBLE);
                                    txt_blockpaves.setText("Block Paves :- " + model.getData().get(0).getOrdAttributes().getOrdBlockpaves());
                                }
                            }
                        }
                    }
                });


    }*/