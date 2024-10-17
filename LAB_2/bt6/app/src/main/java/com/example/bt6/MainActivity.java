package com.example.bt6;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText etFullName;
    private EditText etID;
    private CheckBox cbManager;
    private Button btnAdd;
    private TextView tvResult;
    private RecyclerView rvEmployees;
    private EmployeeRecyclerAdapter employeeAdapter;
    private List<Employee> employeesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Khởi tạo các thành phần giao diện
        etFullName = findViewById(R.id.et_name);
        etID = findViewById(R.id.et_code);
        cbManager = findViewById(R.id.cb_type);
        btnAdd = findViewById(R.id.btn_add);
        tvResult = findViewById(R.id.tv_result);
        rvEmployees = findViewById(R.id.rv_employees);

        // Khởi tạo danh sách nhân viên và adapter
        employeesList = new ArrayList<>();
        employeeAdapter = new EmployeeRecyclerAdapter(employeesList, employee -> {
            // Hiển thị tên nhân viên được click vào TextView
            tvResult.setText(employee.getFullName());
        });

        // Cài đặt RecyclerView
        rvEmployees.setLayoutManager(new LinearLayoutManager(this));
        rvEmployees.setAdapter(employeeAdapter);

        // Thêm sự kiện cho nút Add
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewEmployee();
            }
        });
    }

    // Thêm nhân viên mới vào danh sách
    private void addNewEmployee() {
        String fullName = etFullName.getText().toString();
        String ID = etID.getText().toString();
        boolean isManager = cbManager.isChecked();

        if (!fullName.isEmpty() && !ID.isEmpty()) {
            Employee newEmployee = new Employee(ID, fullName, isManager);
            employeesList.add(newEmployee);
            employeeAdapter.notifyDataSetChanged(); // Cập nhật RecyclerView

            // Reset các trường input
            etFullName.setText("");
            etID.setText("");
            cbManager.setChecked(false);
        } else {
            tvResult.setText("Vui lòng nhập đầy đủ thông tin");
        }
    }
}
