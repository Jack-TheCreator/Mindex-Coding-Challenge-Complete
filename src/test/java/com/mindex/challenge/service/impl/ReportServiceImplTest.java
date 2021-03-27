package com.mindex.challenge.service.impl;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.EmployeeService;
import com.mindex.challenge.service.ReportingStructureService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReportServiceImplTest {
    
    private String reportIdUrl;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private ReportingStructureService reportStructureService;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private Employee testEmployee;
    private Employee directReport1;
    private Employee directReport2;
    private Employee indirectReport; 

    @Before
    public void setup(){
        reportIdUrl = "http://localhost:" + port + "/reportstructure/{id}";
        testEmployee = new Employee();
        testEmployee.setEmployeeId(UUID.randomUUID().toString());
        directReport1 = new Employee();
        directReport1.setEmployeeId(UUID.randomUUID().toString());
        directReport2 = employeeService.create(new Employee());
        indirectReport = employeeService.create(new Employee());
        
        List<Employee> DirectReports = new ArrayList<>();
        List<Employee> IndirectReports = new ArrayList<>();

        DirectReports.add(directReport1);
        DirectReports.add(directReport2);
        IndirectReports.add(indirectReport);

        testEmployee.setDirectReports(DirectReports);
        directReport1.setDirectReports(IndirectReports);
        
        testEmployee = employeeService.update(testEmployee);
        directReport1 = employeeService.update(directReport1);

    }
    @Test
    public void testCreate(){
        
        int expectedReports = 3;
        ReportingStructure report = restTemplate.getForEntity(reportIdUrl, ReportingStructure.class, testEmployee.getEmployeeId()).getBody();
        assertEquals(expectedReports, report.getNumberOfReports());
    }

}
