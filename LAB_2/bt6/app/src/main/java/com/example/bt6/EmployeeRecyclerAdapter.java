package com.example.bt6;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class EmployeeRecyclerAdapter extends RecyclerView.Adapter<EmployeeRecyclerAdapter.EmployeeViewHolder> {

    private List<Employee> employees;
    private OnItemClickListener listener;

    public EmployeeRecyclerAdapter(List<Employee> employees, OnItemClickListener listener) {
        this.employees = employees;
        this.listener = listener;
    }

    @NonNull
    @Override
    public EmployeeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_employee, parent, false);
        return new EmployeeViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull EmployeeViewHolder holder, int position) {
        Employee employee = employees.get(position);
        holder.bind(employee, listener, position);
    }

    @Override
    public int getItemCount() {
        return employees.size();
    }

    public static class EmployeeViewHolder extends RecyclerView.ViewHolder {
        private TextView tvFullName;
        private TextView tvPosition;
        private ImageView ivManager;
        private LinearLayout llParent;

        public EmployeeViewHolder(@NonNull View itemView) {
            super(itemView);
            tvFullName = itemView.findViewById(R.id.item_employee_tv_fullname);
            tvPosition = itemView.findViewById(R.id.item_employee_tv_position);
            ivManager = itemView.findViewById(R.id.item_employee_iv_manager);
            llParent = itemView.findViewById(R.id.item_employee_ll_parent);
        }

        public void bind(Employee employee, OnItemClickListener listener, int position) {
            tvFullName.setText(employee.getFullName());

            if (employee.isManager()) {
                ivManager.setVisibility(View.VISIBLE);
                tvPosition.setVisibility(View.GONE);
            } else {
                ivManager.setVisibility(View.GONE);
                tvPosition.setVisibility(View.VISIBLE);
                tvPosition.setText("Staff");
            }

            if (position % 2 == 0) {
                llParent.setBackgroundResource(R.color.White);
            } else {
                llParent.setBackgroundResource(R.color.LightBlue);
            }

            itemView.setOnClickListener(v -> listener.onItemClick(employee));
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Employee employee);
    }
}
