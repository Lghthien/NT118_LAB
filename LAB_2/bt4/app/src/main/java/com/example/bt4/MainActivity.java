package com.example.bt4;

import static com.example.bt4.R.*;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText etFullName;
    private EditText etID;
    private CheckBox cbManager;
    private Button btnAdd;
    private TextView tvResult;
    private ListView lvName;
    private List<Employee> employeeslist;
    private EmployeeAdapter employeeadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            // Khởi tạo các thành phần giao diện
            etFullName = findViewById(R.id.et_name);
            etID = findViewById(R.id.et_code);
            cbManager = findViewById(R.id.cb_type);
            btnAdd = findViewById(R.id.btn_add);
            tvResult = findViewById(R.id.tv_result);
            // Khởi tạo ListView
            employeeslist = new ArrayList<>();
            // Khởi tạo Adapter
            employeeadapter = new EmployeeAdapter(this, R.layout.item_employee, employeeslist);
            // Khởi tạo ListView
            lvName = findViewById(R.id.lv_name);
            // Đặt Adapter cho ListView
            lvName.setAdapter(employeeadapter);

            btnAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addNewEmployee();
                }
            });

            lvName.setOnItemClickListener((parent, view, position, id) -> {
                Employee employee = employeeslist.get(position);
                tvResult.setText(employee.getFullName());
            });
            return insets;
        });
    }

    private void addNewEmployee() {
        String fullName = etFullName.getText().toString();
        String ID = etID.getText().toString();
        boolean isManager = cbManager.isChecked();

        if (!fullName.isEmpty() && !ID.isEmpty()) {
            Employee newEmployee = new Employee(ID, fullName, isManager);
            employeeslist.add(newEmployee);
            employeeadapter.notifyDataSetChanged();
            etFullName.setText("");
            etID.setText("");
            cbManager.setChecked(false);
        }
        else {
            tvResult.setText("Vui lòng nhập đầy đủ thông tin");
        }
    }

}