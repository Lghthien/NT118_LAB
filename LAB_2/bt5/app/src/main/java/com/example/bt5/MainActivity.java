package com.example.bt5;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText etName;
    Spinner spThumbnail;
    CheckBox cbPromotion;
    GridView gvFood;
    Button btnAdd;
    List<Thumbnail> thumbnailList = new ArrayList<>();
    List<Dish> dishList = new ArrayList<>();
    DishAdapter dishAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etName = findViewById(R.id.et_name);
        spThumbnail = findViewById(R.id.sp_thumbnail);
        cbPromotion = findViewById(R.id.cb_promotion);
        gvFood = findViewById(R.id.gv_food);
        btnAdd = findViewById(R.id.btn_add);

        // Khởi tạo danh sách thumbnail
        thumbnailList.add(Thumbnail.ThumbailL1);
        thumbnailList.add(Thumbnail.ThumbailL2);
        thumbnailList.add(Thumbnail.ThumbailL3);
        thumbnailList.add(Thumbnail.ThumbailL4);

        // Khởi tạo adapter cho Spinner
        ThumbnailAdapter thumbnailAdapter = new ThumbnailAdapter(this, R.layout.item_selected_thumbnail, thumbnailList);
        spThumbnail.setAdapter(thumbnailAdapter);

        // Khởi tạo adapter cho GridView
        dishAdapter = new DishAdapter((Context) this, R.layout.item_dish, (ArrayList<Dish>) dishList);
        gvFood.setAdapter(dishAdapter);

        // Sự kiện nhấn nút thêm
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewDish();
            }
        });
    }

    private void addNewDish() {
        // Lấy tên món ăn từ EditText
        String dishName = etName.getText().toString().trim();

        // Lấy hình ảnh từ Spinner
        Thumbnail selectedThumbnail = (Thumbnail) spThumbnail.getSelectedItem();
        int thumbnailResource = selectedThumbnail != null ? selectedThumbnail.getImageResource() : -1;

        // Lấy trạng thái khuyến mãi từ CheckBox
        boolean isPromotion = cbPromotion.isChecked();

        // Kiểm tra tên món ăn và hình ảnh có hợp lệ không
        if (!dishName.isEmpty() && thumbnailResource != -1) {
            // Tạo đối tượng Dish mới
            Dish newDish = new Dish(dishName, thumbnailResource, isPromotion);

            // Thêm món ăn vào danh sách
            dishList.add(newDish);
            dishAdapter.notifyDataSetChanged();

            // Hiển thị Toast thông báo
            Toast.makeText(this, "Added successfully", Toast.LENGTH_SHORT).show();

            // Reset các trường dữ liệu
            etName.setText("");
            spThumbnail.setSelection(0); // Đặt về lựa chọn mặc định
            cbPromotion.setChecked(false); // Reset trạng thái khuyến mãi
        } else {
            // Thông báo nếu có vấn đề
            Toast.makeText(this, "Please enter a dish name and select a thumbnail", Toast.LENGTH_SHORT).show();
        }
    }
}
