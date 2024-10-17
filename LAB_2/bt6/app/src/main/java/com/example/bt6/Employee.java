package com.example.bt6;

public class Employee {
    String ID;
    String fullName;
    Boolean isManager;

    public Employee(String ID,String fullName, boolean isManager){
        this.ID=ID;
        this.fullName=fullName;
        this.isManager=isManager;
    }

    public String getFullName(){
        return fullName;
    }

    public void setFullName(String fullName){
        this.fullName=fullName;
    }

    public boolean isManager() {
        return isManager;
    }

    public void setManager(boolean manager) {
        isManager = manager;
    }

    public void  setID(String ID){
        this.ID = ID;
    }
    public String getID(){
        return ID;
    }
}
