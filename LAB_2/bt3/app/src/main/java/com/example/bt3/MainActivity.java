package com.example.bt3;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Áp dụng insets để đảm bảo không bị che bởi thanh trạng thái
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Tìm các view
        EditText et_code = findViewById(R.id.et_code);
        EditText et_name = findViewById(R.id.et_name);
        RadioGroup rg_type = findViewById(R.id.rg_type);
        Button btn_name = findViewById(R.id.btn_name);
        TextView tv_result = findViewById(R.id.tv_result);  // TextView để hiển thị thông tin
        ListView lv_name = findViewById(R.id.lv_name);

        // Khai báo mảng để lưu nhân viên
        ArrayList<Employee> employees = new ArrayList<>();
        ArrayAdapter<Employee> adapter;

        // Thiết lập adapter
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, employees);
        lv_name.setAdapter(adapter);

        // Xử lý khi nhấn nút thêm nhân viên
        btn_name.setOnClickListener(view -> {
            String id = et_code.getText().toString().trim();
            String name = et_name.getText().toString().trim();
            int selectedTypeId = rg_type.getCheckedRadioButtonId();

            if (!id.isEmpty() && !name.isEmpty()) {
                Employee employee;
                if (selectedTypeId == R.id.rd_type_1) {
                    employee = new EmployeeFulltime(id, name);  // Tạo nhân viên chính thức
                } else if (selectedTypeId == R.id.rd_type_2) {
                    employee = new EmployeeParttime(id, name);  // Tạo nhân viên thời vụ
                } else {
                    tv_result.setText("Vui lòng chọn loại nhân viên");
                    return; // Thoát nếu không chọn loại nhân viên
                }

                // Thêm nhân viên vào danh sách và cập nhật giao diện
                employees.add(employee);
                adapter.notifyDataSetChanged();

                // Xóa dữ liệu nhập sau khi thêm
                et_code.setText("");
                et_name.setText("");
            } else {
                tv_result.setText("Vui lòng nhập đầy đủ thông tin");
            }
        });

        // Xử lý sự kiện nhấn vào mục trong ListView
        lv_name.setOnItemClickListener((parent, view, position, id) -> {
            // Lấy nhân viên tại vị trí được nhấn
            Employee selectedEmployee = employees.get(position);
            // Hiển thị thông tin nhân viên lên TextView
            tv_result.setText(selectedEmployee.toString());
        });
    }

    // Class Employee cơ sở
    class Employee {
        String ID;
        String Name;
        String Type;  // Thuộc tính dùng để phân biệt loại nhân viên
        double Salary; // Thuộc tính lương

        public Employee(String ID, String name, String type, double salary) {
            this.ID = ID;
            this.Name = name;
            this.Type = type;
            this.Salary = salary; // Gán lương khi khởi tạo
        }

        @Override
        public String toString() {
            return "Mã NV: " + ID + ", Tên NV: " + Name + ", Loại NV: " + Type + ", Lương: " + Salary;
        }
    }

    // Lớp con cho nhân viên chính thức
    class EmployeeFulltime extends Employee {
        public EmployeeFulltime(String ID, String name) {
            super(ID, name, "Chính thức", 500);  // Gán lương 500 cho nhân viên chính thức
        }
    }

    // Lớp con cho nhân viên thời vụ
    class EmployeeParttime extends Employee {
        public EmployeeParttime(String ID, String name) {
            super(ID, name, "Thời vụ", 150);  // Gán lương 150 cho nhân viên thời vụ
        }
    }
}
