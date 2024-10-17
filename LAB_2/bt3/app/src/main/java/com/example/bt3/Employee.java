package com.example.bt3;

// Class Employee cơ sở
public class Employee {
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
