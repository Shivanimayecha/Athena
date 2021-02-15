package com.athena.group.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.athena.group.API.ApiConstants;
import com.athena.group.API.ApiInterface;
import com.athena.group.API.ApiServiceCreator;
import com.athena.group.R;
import com.athena.group.application.SessionManager;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.MyViewHolder> {

    Context mContext;
    List<String> arraylist;
    SessionManager sessionManager;
    ApiInterface apiservice;
    int statusCode;


    public ImageAdapter(Context mContext, List<String> arraylist) {
        this.mContext = mContext;
        this.arraylist = arraylist;
        sessionManager = new SessionManager(mContext);
        apiservice = ApiServiceCreator.createService("latest");
    }

    @NonNull
    @Override
    public ImageAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_view_image_select1, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageAdapter.MyViewHolder holder, int position) {

        Glide.with(mContext)
                .load(ApiConstants.IMAGE_URL + arraylist.get(position))
                .transform(new RoundedCorners(10))
                .placeholder(R.color.gray)
                .into(holder.image_view_image_select);

    }


    @Override
    public int getItemCount() {
        return arraylist.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.image_view_image_select)
        ImageView image_view_image_select;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);


        }
    }
}
