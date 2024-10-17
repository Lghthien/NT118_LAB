package com.example.bt4;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.bt4.Employee;
import com.example.bt4.R;

import java.util.List;

/**
 * EmployeeAdapter tùy chỉnh ArrayAdapter để hiển thị danh sách nhân viên trong ListView.
 */
public class EmployeeAdapter extends ArrayAdapter<Employee> {
    private final Activity context;
    private final int resource;
    private final List<Employee> employees;

    /**
     * Constructor của EmployeeAdapter.
     * @param context Context của Activity.
     * @param resource ID của layout cho từng item.
     * @param employees Danh sách nhân viên.
     */
    public EmployeeAdapter(@NonNull Activity context, int resource, @NonNull List<Employee> employees) {
        super(context, resource, employees);
        this.context = context;
        this.resource = resource;
        this.employees = employees;
    }

    /**
     * Phương thức getView được gọi để tạo và cập nhật view cho từng item trong ListView.
     * @param position Vị trí của item trong danh sách.
     * @param convertView View hiện tại để tái sử dụng.
     * @param parent ViewGroup cha.
     * @return View đã được cập nhật.
     */
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Inflate layout nếu convertView là null
        if (convertView == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            convertView = inflater.inflate(resource, null);
        }

        // Lấy nhân viên tại vị trí hiện tại
        Employee employee = employees.get(position);

        // Tham chiếu các view trong item_employee.xml
        TextView tvFullName = convertView.findViewById(R.id.item_employee_tv_fullname);
        TextView tvPosition = convertView.findViewById(R.id.item_employee_tv_position);
        ImageView ivManager = convertView.findViewById(R.id.item_employee_iv_manager);
        LinearLayout llParent = convertView.findViewById(R.id.item_employee_ll_parent);

        // Đặt tên nhân viên
        tvFullName.setText(employee.getFullName());

        // Kiểm tra loại nhân viên và hiển thị icon hoặc chữ "Staff"
        if (employee.isManager()) {
            ivManager.setVisibility(View.VISIBLE);    // Hiển thị icon Manager
            tvPosition.setVisibility(View.GONE);     // Ẩn TextView "Staff"
        } else {
            ivManager.setVisibility(View.GONE);       // Ẩn icon Manager
            tvPosition.setVisibility(View.VISIBLE);  // Hiển thị TextView "Staff"
//            tvPosition.setText("Staff");
        }

        // Đổi màu nền cho các dòng khác nhau để dễ nhìn
        if (position % 2 == 0) {
            llParent.setBackgroundResource(R.color.White); // Dòng chẵn: màu trắng
        } else {
            llParent.setBackgroundResource(R.color.LightBlue); // Dòng lẻ: màu xanh nhạt
        }

        return convertView;
    }
}
