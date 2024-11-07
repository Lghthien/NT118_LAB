package com.example.lab3_3;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class StudentRecyclerAdapter extends RecyclerView.Adapter<StudentRecyclerAdapter.StudentViewHolder> {

    private final List<Student> students;
    private final OnItemClickListener clickListener;
    private final OnItemLongClickListener longClickListener;

    public StudentRecyclerAdapter(List<Student> students, OnItemClickListener clickListener, OnItemLongClickListener longClickListener) {
        this.students = students;
        this.clickListener = clickListener;
        this.longClickListener = longClickListener;
    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.student_item, parent, false);
        return new StudentViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder holder, int position) {
        holder.bind(students.get(position), clickListener, longClickListener);
    }

    @Override
    public int getItemCount() {
        return students.size();
    }

    public static class StudentViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvStudentCode;
        private final TextView tvStudentName;

        public StudentViewHolder(@NonNull View itemView) {
            super(itemView);
            tvStudentCode = itemView.findViewById(R.id.tv_student_code);
            tvStudentName = itemView.findViewById(R.id.tv_student_name);
        }

        public void bind(Student student, OnItemClickListener clickListener, OnItemLongClickListener longClickListener) {
            tvStudentCode.setText(student.getCode());
            tvStudentName.setText(student.getName());

            itemView.setOnClickListener(v -> clickListener.onItemClick(student));
            itemView.setOnLongClickListener(v -> {
                longClickListener.onItemLongClick(student);
                return true;
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Student student);
    }

    public interface OnItemLongClickListener {
        void onItemLongClick(Student student);
    }
}
