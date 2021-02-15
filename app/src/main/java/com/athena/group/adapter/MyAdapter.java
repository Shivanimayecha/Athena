package com.athena.group.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.athena.group.API.ApiConstants;
import com.athena.group.R;
import com.bumptech.glide.Glide;


import java.util.List;

/**
 * Created by Akshay Raj on 06/02/18.
 * akshay@snowcorp.org
 * www.snowcorp.org
 */

public class MyAdapter extends BaseAdapter {

    private Context context;
    private List<Uri> arrayList;

    public MyAdapter(Context context, List<Uri> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }


    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (mInflater != null) {
            convertView = mInflater.inflate(R.layout.list_items, null);
        }

        ImageView imageView = convertView.findViewById(R.id.imageView);


        ImageView button = convertView.findViewById(R.id.remove);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arrayList.remove(position);
                notifyDataSetChanged();
            }
        });
        Glide.with(context)
                .load(ApiConstants.IMAGE_URL + arrayList.get(position))
                .into(imageView);

        return convertView;
    }
}
