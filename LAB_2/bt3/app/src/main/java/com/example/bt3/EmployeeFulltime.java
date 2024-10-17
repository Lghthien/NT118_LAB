package com.example.bt3;

// Lớp con cho nhân viên chính thức
public class EmployeeFulltime extends Employee {
    public EmployeeFulltime(String ID, String name) {
        super(ID, name, "Chính thức", 500);  // Gán lương 500 cho nhân viên chính thức
    }
}
