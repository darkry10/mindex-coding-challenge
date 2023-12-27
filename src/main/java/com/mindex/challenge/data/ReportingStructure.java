package com.mindex.challenge.data;

import java.util.List;

public class ReportingStructure {
    private Employee employee;
    private int numberOfReports;

    public ReportingStructure() {
        this.employee = null;
        this.numberOfReports = 0;
    }

    public ReportingStructure(Employee employee) {
        this.employee = employee;
        
        this.numberOfReports = updateNumReports(employee.getDirectReports());
    }
    
    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee newEmployee) {
        this.employee = newEmployee;
    }

    public int getNumReports() {
        return numberOfReports;
    }

    public void setNumReports(int numReports) {
        this.numberOfReports = numReports;
    }

    public int updateNumReports(List<Employee> directReports) {
        if (directReports == null || directReports.isEmpty()) {
            return 0;
        }

        int numReports = directReports.size();
        for (int i = 0; i < directReports.size(); i++) {
            numReports += updateNumReports(directReports.get(i).getDirectReports());
        }

        return numReports;
    }

    public String toString() {
        return employeeInfo(this.employee);
    }

    //Helper function to show a detailed list of the reporting structure for the employee.
    public String employeeInfo(Employee employee) {
        StringBuilder builder = new StringBuilder();
        builder.append(employee.getFirstName() + " " + employee.getLastName() + " - " + employee.getPosition() + "\n");
        if (employee.getDirectReports() == null || employee.getDirectReports().isEmpty()) {
            return builder.toString();
        }

        for (int i = 0; i < employee.getDirectReports().size(); i++) {
            builder.append("\t" + employeeInfo(employee.getDirectReports().get(i)));
        }

        return builder.toString();
    }
}
