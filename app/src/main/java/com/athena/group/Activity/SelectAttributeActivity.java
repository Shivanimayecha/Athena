package com.athena.group.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.athena.group.API.ApiInterface;
import com.athena.group.API.ApiServiceCreator;
import com.athena.group.Model.AttributeValueModel;
import com.athena.group.R;
import com.athena.group.application.SessionManager;
import com.wang.avi.AVLoadingIndicatorView;

import java.net.SocketTimeoutException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Observer;
import rx.schedulers.Schedulers;

public class SelectAttributeActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    @BindView(R.id.back_icon)
    ImageView back_icon;
    @BindView(R.id.toolbar_Title)
    TextView toolbar_Title;
    @BindView(R.id.list_attribute)
    ListView list_attribute;
    @BindView(R.id.avi)
    AVLoadingIndicatorView avi;
    @BindView(R.id.simpleSearchView)
    SearchView simpleSearchView;
    ApiInterface apiservice;
    SessionManager sessionManager;
    String pid = "", ca_label;
    int statusCode;
    String[] array;
    ArrayAdapter<String> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_attribute);
        ButterKnife.bind(this);
        sessionManager = new SessionManager(SelectAttributeActivity.this);
        apiservice = ApiServiceCreator.createService("latest");
        toolbar_Title.setText("Place Order");

        initViews();
    }

    private void initViews() {

        back_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        pid = getIntent().getStringExtra("p_id");
        ca_label = getIntent().getStringExtra("label");

        getAttributeValue();

        list_attribute.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String str = (String) adapterView.getItemAtPosition(i);
                Intent intent = new Intent();
                intent.putExtra("data", str);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        simpleSearchView.setOnQueryTextListener(this);
    }

    private void getAttributeValue() {


        ArrayList<String> AttributeArray = new ArrayList<>();
        avi.show();

        Observable<AttributeValueModel> responseObservable = apiservice.getAttributeValues(pid, ca_label);

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
                .subscribe(new Observer<AttributeValueModel>() {
                    @Override
                    public void onCompleted() {
                       avi.hide();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("error", "" + e.getMessage());
                    }

                    @Override
                    public void onNext(AttributeValueModel model) {
                        avi.hide();

                        statusCode = model.getStatusCode();
                        if (statusCode == 200) {
                            //List<AttributeModel.Data> data = model.getData();
                            for (int i = 0; i < model.getData().size(); i++) {
                                AttributeArray.add(model.getData().get(i).getCa_value());
                            }
                            array = AttributeArray.toArray(new String[0]);
                            adapter = new ArrayAdapter<String>(SelectAttributeActivity.this, android.R.layout.simple_list_item_1, array);
                            //  country.setDropDownViewResource(R.layout.spinner_item);
                            list_attribute.setAdapter(adapter);
                        }
                    }
                });

    }

    @Override
    public void onBackPressed() {
        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_CANCELED, returnIntent);
        finish();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        String text = newText;
        adapter.getFilter().filter(newText);
        return false;
    }

}
