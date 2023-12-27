package com.mindex.challenge.service.impl;

import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.service.ReportingStructureService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReportingStructureServiceImplTest {

    private String employeeUrl;
    private String reportingStructureIdUrl;

    @Autowired
    private ReportingStructureService reportingStructureService;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Before
    public void setup() {
        employeeUrl = "http://localhost:" + port + "/employee";
        reportingStructureIdUrl = "http://localhost:" + port + "/reportingStructure/{id}";
    }

    @Test
    public void testRead() {
        Employee testEmployee1 = new Employee();
        testEmployee1.setFirstName("John");
        testEmployee1.setLastName("Doe");
        testEmployee1.setDepartment("Engineering");
        testEmployee1.setPosition("Developer");
        Employee createdEmployee1 = restTemplate.postForEntity(employeeUrl, testEmployee1, Employee.class).getBody();
        

        Employee testEmployee2 = new Employee();
        testEmployee2.setFirstName("John");
        testEmployee2.setLastName("Doe");
        testEmployee2.setDepartment("Engineering");
        testEmployee2.setPosition("Developer");
        Employee createdEmployee2 = restTemplate.postForEntity(employeeUrl, testEmployee2, Employee.class).getBody();
        List<Employee> directReports = new ArrayList<Employee>();
        directReports.add(createdEmployee1);
        directReports.add(createdEmployee2);

        Employee testEmployeeManager = new Employee();
        testEmployeeManager.setFirstName("John");
        testEmployeeManager.setLastName("Doe");
        testEmployeeManager.setDepartment("Engineering");
        testEmployeeManager.setPosition("Manager");
        testEmployeeManager.setDirectReports(directReports);

        // Create checks
        Employee createdEmployeeManager = restTemplate.postForEntity(employeeUrl, testEmployeeManager, Employee.class).getBody();
        ReportingStructure reportingStructure = new ReportingStructure(createdEmployeeManager);

        assertNotNull(createdEmployeeManager.getEmployeeId());
        assertEmployeeEquivalence(testEmployeeManager, createdEmployeeManager);


        // Read checks
        ReportingStructure readReportingStructure = restTemplate.getForEntity(reportingStructureIdUrl, ReportingStructure.class, createdEmployeeManager.getEmployeeId()).getBody();
        assertEquals(createdEmployeeManager.getEmployeeId(), readReportingStructure.getEmployee().getEmployeeId());
        assertReportingStructureEquivalence(reportingStructure, readReportingStructure);
    }

    private static void assertEmployeeEquivalence(Employee expected, Employee actual) {
        assertEquals(expected.getFirstName(), actual.getFirstName());
        assertEquals(expected.getLastName(), actual.getLastName());
        assertEquals(expected.getDepartment(), actual.getDepartment());
        assertEquals(expected.getPosition(), actual.getPosition());
    }

    private static void assertReportingStructureEquivalence(ReportingStructure expected, ReportingStructure actual) {
        assertEmployeeEquivalence(expected.getEmployee(), actual.getEmployee());
        assertEquals(expected.getNumReports(), actual.getNumReports());
    }
}
