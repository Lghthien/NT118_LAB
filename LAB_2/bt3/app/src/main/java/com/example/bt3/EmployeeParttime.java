package com.example.bt3;

// Lớp con cho nhân viên thời vụ
public class EmployeeParttime extends Employee {
    public EmployeeParttime(String ID, String name) {
        super(ID, name, "Thời vụ", 150);  // Gán lương 150 cho nhân viên thời vụ
    }
}