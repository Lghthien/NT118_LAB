package com.example.bt2;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Điều chỉnh giao diện để bao gồm các thanh hệ thống (status bar, navigation bar)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Tìm các thành phần giao diện
        TextView tv_result = findViewById(R.id.tv_result);
        ListView lv_name = findViewById(R.id.lv_name);
        Button btn_name = findViewById(R.id.btn_name);
        EditText et_name = findViewById(R.id.et_name);

        ArrayList<String> nameList = new ArrayList<>();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, nameList);
        lv_name.setAdapter(adapter);

        btn_name.setOnClickListener(v -> {
            String name = et_name.getText().toString();

            if(!name.isEmpty()){
                nameList.add(name);
                adapter.notifyDataSetChanged();
                et_name.setText("");
            }
        });
        lv_name.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                tv_result.setText("Họ và tên: " + nameList.get(position));
            }
        });

        lv_name.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                nameList.remove(position);
                adapter.notifyDataSetChanged();
                tv_result.setText("");
                return true;
        }
    });}
}
