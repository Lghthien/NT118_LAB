package com.example.bt5;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ThumbnailAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private List<Thumbnail> thumbnailList;

    public ThumbnailAdapter(Context context, int layout, List<Thumbnail> thumbnailList) {
        this.context = context;
        this.layout = layout;
        this.thumbnailList = thumbnailList;
    }

    @Override
    public int getCount() {
        return thumbnailList.size();
    }

    @Override
    public Object getItem(int position) {
        return thumbnailList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_selected_thumbnail, null);
        }

        TextView tvThumbnailName = convertView.findViewById(R.id.tv_thumbnail_name);
        Thumbnail thumbnail = thumbnailList.get(position);
        tvThumbnailName.setText(thumbnail.getName());
        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_thumbnail, null);
        }

        ImageView ivThumbnail = convertView.findViewById(R.id.iv_thumbnail);
        TextView tvThumbnailName = convertView.findViewById(R.id.tv_thumbnail_name);

        Thumbnail thumbnail = thumbnailList.get(position);
        ivThumbnail.setImageResource(thumbnail.getImageResource());
        tvThumbnailName.setText(thumbnail.getName());

        return convertView;
    }
}
