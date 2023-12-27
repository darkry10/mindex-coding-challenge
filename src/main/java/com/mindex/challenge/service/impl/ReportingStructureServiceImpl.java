package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.ReportingStructureService;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReportingStructureServiceImpl implements ReportingStructureService {

    private static final Logger LOG = LoggerFactory.getLogger(ReportingStructureServiceImpl.class);

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public ReportingStructure read(String id) {
        LOG.debug("Creating Reporting Structure with id [{}]", id);

        //Fetch desired employee from mongo DB
        Employee employee = employeeRepository.findByEmployeeId(id);

        if (employee == null) {
            throw new RuntimeException("Invalid employeeId: " + id);
        }

        employee.setDirectReports(getDirectReports(employee));

        //Instantiateing new reporting structure with an employee will calculate the number of reports.
        ReportingStructure reportingStructure = new ReportingStructure(employee);


        return reportingStructure;
    }

    public List<Employee> getDirectReports(Employee employee) {
        if (employee.getDirectReports() == null || employee.getDirectReports().isEmpty()) {
            return employee.getDirectReports();
        }

        List<Employee> directReports = new ArrayList<Employee>();
        for (int i = 0; i < employee.getDirectReports().size(); i++) {
            Employee detailedEmployee = employeeRepository.findByEmployeeId(employee.getDirectReports().get(i).getEmployeeId());
            detailedEmployee.setDirectReports(getDirectReports(detailedEmployee));
            directReports.add(detailedEmployee);
        }

        return directReports;
    }
}
