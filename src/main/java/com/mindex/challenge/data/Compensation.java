package com.mindex.challenge.data;

import java.util.Date;

public class Compensation {
    private Employee employee;
    private float salary;
    private Date effectiveDate;

    public Compensation() {
        this.employee = null;
        this.salary = 0;
        this.effectiveDate = null;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee newEmployee) {
        this.employee = newEmployee;
    }

    public float getSalary() {
        return salary;
    }

    public void setSalary(float newSalary) {
        this.salary = newSalary;
    }

    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Date newEffectiveDate) {
        this.effectiveDate = newEffectiveDate;
    }

}
