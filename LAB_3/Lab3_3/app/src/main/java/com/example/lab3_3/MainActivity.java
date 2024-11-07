package com.example.lab3_3;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements StudentRecyclerAdapter.OnItemClickListener, StudentRecyclerAdapter.OnItemLongClickListener {

    private EditText etCode, etName, etAddress, etPhone;
    private Button btnAddStudent, btnSaveChanges;
    private TextView tvResult;
    private RecyclerView rvStudent;
    private StudentRecyclerAdapter adapter;
    private DatabaseHandler databaseHandler;

    private Student selectedStudent = null; // Lưu sinh viên đã chọn để chỉnh sửa

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Khởi tạo các view
        etCode = findViewById(R.id.et_code);
        etName = findViewById(R.id.et_name);
        etAddress = findViewById(R.id.et_address);
        etPhone = findViewById(R.id.et_phone);
        btnAddStudent = findViewById(R.id.btn_add_student);
        btnSaveChanges = findViewById(R.id.btn_save_changes);
        tvResult = findViewById(R.id.tv_result);
        rvStudent = findViewById(R.id.rv_student);

        // Khởi tạo cơ sở dữ liệu
        databaseHandler = new DatabaseHandler(this);
        rvStudent.setLayoutManager(new LinearLayoutManager(this));
        loadStudents();

        // Thiết lập sự kiện cho nút
        btnAddStudent.setOnClickListener(v -> addStudent());
        btnSaveChanges.setOnClickListener(v -> updateStudent());
    }

    // Tải danh sách sinh viên từ cơ sở dữ liệu
    private void loadStudents() {
        try {
            List<Student> students = databaseHandler.getAllStudents();
            adapter = new StudentRecyclerAdapter(students, this, this);
            rvStudent.setAdapter(adapter);
        } catch (Exception e) {
            tvResult.setText("Lỗi khi tải danh sách sinh viên: " + e.getMessage());
        }
    }

    // Thêm sinh viên mới
    private void addStudent() {
        String code = etCode.getText().toString().trim();
        String name = etName.getText().toString().trim();
        String address = etAddress.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();

        if (!code.isEmpty() && !name.isEmpty() && !address.isEmpty() && !phone.isEmpty()) {
            Student student = new Student(code, name, address, phone);
            try {
                databaseHandler.addStudent(student);
                loadStudents();
                clearFields();
                tvResult.setText("Thêm thành công: " + name);
            } catch (Exception e) {
                tvResult.setText("Lỗi khi thêm sinh viên: " + e.getMessage());
            }
        } else {
            tvResult.setText("Vui lòng nhập đầy đủ thông tin sinh viên.");
        }
    }

    // Làm sạch các trường nhập
    private void clearFields() {
        etCode.setText("");
        etName.setText("");
        etAddress.setText("");
        etPhone.setText("");
        selectedStudent = null; // Xóa sinh viên đã chọn
        btnSaveChanges.setVisibility(View.GONE); // Ẩn nút lưu
        btnAddStudent.setVisibility(View.VISIBLE); // Hiện nút thêm
    }

    @Override
    public void onItemClick(Student student) {
        loadStudentForEditing(student);
    }

    // Tải sinh viên để chỉnh sửa
    private void loadStudentForEditing(Student student) {
        if (student != null) {
            etCode.setText(student.getCode());
            etName.setText(student.getName());
            etAddress.setText(student.getAddress());
            etPhone.setText(student.getPhone());
            selectedStudent = student; // Đặt sinh viên đã chọn cho việc cập nhật

            btnSaveChanges.setVisibility(View.VISIBLE); // Hiện nút lưu
            btnAddStudent.setVisibility(View.GONE); // Ẩn nút thêm
        }
    }

    // Cập nhật thông tin sinh viên
    private void updateStudent() {
        if (selectedStudent != null) {
            String code = etCode.getText().toString().trim();
            String name = etName.getText().toString().trim();
            String address = etAddress.getText().toString().trim();
            String phone = etPhone.getText().toString().trim();

            if (!code.isEmpty() && !name.isEmpty() && !address.isEmpty() && !phone.isEmpty()) {
                selectedStudent.setCode(code);
                selectedStudent.setName(name);
                selectedStudent.setAddress(address);
                selectedStudent.setPhone(phone);

                try {
                    databaseHandler.updateStudent(selectedStudent);
                    loadStudents();
                    clearFields();
                    tvResult.setText("Cập nhật thành công: " + selectedStudent.getName());
                } catch (Exception e) {
                    tvResult.setText("");
                }
            } else {
                tvResult.setText("Vui lòng nhập đầy đủ thông tin để cập nhật.");
            }
        } else {
            tvResult.setText("Lỗi: Không có sinh viên nào được chọn để cập nhật.");
        }
    }

    @Override
    public void onItemLongClick(Student student) {
        if (student != null) {
            try {
                databaseHandler.deleteStudent(student);
                loadStudents();
                clearFields(); // Xóa thông tin nếu sinh viên bị xóa
                tvResult.setText("Đã xóa: " + student.getName());
            } catch (Exception e) {
                tvResult.setText("Lỗi khi xóa sinh viên: " + e.getMessage());
            }
        } else {
            tvResult.setText("Lỗi: Sinh viên không tồn tại.");
        }
    }
}
