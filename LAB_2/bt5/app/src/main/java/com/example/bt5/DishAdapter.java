package com.example.bt5;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class DishAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Dish> dishes;
    private int layout;

    public DishAdapter(Context context, int layout, ArrayList<Dish> dishes) {
        this.context = context;
        this.layout = layout;
        this.dishes = dishes;
    }

    @Override
    public int getCount() {
        return dishes.size();
    }

    @Override
    public Object getItem(int position) {
        return dishes.get(position); // Trả về món ăn ở vị trí hiện tại
    }

    @Override
    public long getItemId(int position) {
        return position; // Trả về vị trí món ăn như là ID
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(this.layout, null);

            // Tạo ViewHolder để giữ các view
            holder = new ViewHolder();
            holder.txtName = convertView.findViewById(R.id.tv_namedisk);
            holder.imgThumbnail = convertView.findViewById(R.id.iv_disk);
            holder.imgStart = convertView.findViewById(R.id.iv_start);

            // Lưu ViewHolder vào convertView
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        // Lấy món ăn hiện tại
        Dish dish = dishes.get(position);

        // Gán giá trị cho các thành phần giao diện
        holder.txtName.setText(dish.getName());
        holder.imgThumbnail.setImageResource(dish.getThumbnailResource());

        // Hiển thị hoặc ẩn biểu tượng khuyến mãi
        if (dish.isPromotion()) {
            holder.imgStart.setVisibility(View.VISIBLE);
        } else {
            holder.imgStart.setVisibility(View.GONE);
        }

        return convertView;
    }

    // ViewHolder pattern để tái sử dụng view
    static class ViewHolder {
        TextView txtName;
        ImageView imgThumbnail;
        ImageView imgStart;
    }
}
