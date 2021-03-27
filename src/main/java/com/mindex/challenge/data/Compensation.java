package com.mindex.challenge.data;

public class Compensation {
    
    private Employee employee;
    private double salary;
    private String effectiveDate;

    public Compensation(){
    }

    public void setEmployee(Employee employee){
        this.employee = employee;
    }

    public Employee getEmployee(){
        return employee;
    }

    public void setSalary(double salary){
        this.salary = salary;
    }

    public double getSalary(){
        return salary;
    }

    public void setEffectiveDate(String effectiveDate){
        this.effectiveDate = effectiveDate;
    }

    public String getEffectiveDate(){
        return effectiveDate;
    }

}
