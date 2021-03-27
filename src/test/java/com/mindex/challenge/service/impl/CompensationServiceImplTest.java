package com.mindex.challenge.service;

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

import com.mindex.challenge.data.Compensation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;




@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CompensationServiceImplTest {
    
    private String compensationUrl;
    private String compensationReadUrl;
    private Employee testEmployee;

    @Autowired
    private EmployeeService employeeService;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;
    
    @Before
    public void setup() {
        compensationUrl = "http://localhost:" + port + "/compensation";
        compensationReadUrl = "http://localhost:" + port + "/compensation/{id}";
        testEmployee = employeeService.create(new Employee());
    }

    @Test
    public void testCreateRead(){
        //Create Test Variables
        double salary = 86753.09;
        String date = "2020-03-03";
        Compensation testCopmensation = new Compensation();
        testCopmensation.setEffectiveDate(date);
        testCopmensation.setSalary(salary);
        testCopmensation.setEmployee(testEmployee);

        //Check Create
        Compensation createdCompensation = restTemplate.postForEntity(compensationUrl, testCopmensation, Compensation.class).getBody();
        assertCompensationEquivalence(createdCompensation, testCopmensation);
        assertEmployeeEquivalence(createdCompensation.getEmployee(), createdCompensation.getEmployee());

        //Check Read
        Compensation readCompensation = restTemplate.getForEntity(compensationReadUrl, Compensation.class, testEmployee.getEmployeeId()).getBody();
        assertCompensationEquivalence(readCompensation, testCopmensation);
        assertEmployeeEquivalence(readCompensation.getEmployee(), testEmployee);

        
    }

    private static void assertCompensationEquivalence(Compensation expected, Compensation actual){
        assertEquals(expected.getEffectiveDate(), actual.getEffectiveDate());
        assertTrue(expected.getSalary() == actual.getSalary());
    }
    private static void assertEmployeeEquivalence(Employee expected, Employee actual) {
        assertEquals(expected.getFirstName(), actual.getFirstName());
        assertEquals(expected.getLastName(), actual.getLastName());
        assertEquals(expected.getDepartment(), actual.getDepartment());
        assertEquals(expected.getPosition(), actual.getPosition());
    }

}
