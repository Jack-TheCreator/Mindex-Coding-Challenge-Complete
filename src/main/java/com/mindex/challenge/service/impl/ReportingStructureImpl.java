package com.mindex.challenge.service.impl;

import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mindex.challenge.data.Employee;
import com.mindex.challenge.service.EmployeeService;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.ReportingStructureService;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.List;

@Service
public class ReportingStructureImpl implements ReportingStructureService{
    private static final Logger LOG = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    @Autowired
    private EmployeeService employeeService;
    
    private int numberOfReports;

    @Override
    public ReportingStructure create(String EmployeeID){

        LOG.debug("Creating Report Structure For Employee With ID [{}]", EmployeeID);
        numberOfReports = 0;

        Employee employee = employeeService.read(EmployeeID);
        ReportingStructure report = new ReportingStructure();

        report.setEmployee(employee);
        findNumberOfReports(employee.getDirectReports());
        report.setNumberOfReports(numberOfReports);
        return report;
    }


    //Recursive Funciton To Get Number Of Reports For Employee
    public void findNumberOfReports(List<Employee> directReports){
        if(directReports != null){
            for(Employee reportEmployee : directReports){
                Employee loadEmployee = employeeService.read(reportEmployee.getEmployeeId());
                reportEmployee.setFirstName(loadEmployee.getFirstName());
                reportEmployee.setLastName(loadEmployee.getLastName());
                reportEmployee.setPosition(loadEmployee.getPosition());
                reportEmployee.setDepartment(loadEmployee.getDepartment());
                reportEmployee.setDirectReports(loadEmployee.getDirectReports());

                numberOfReports++;
                findNumberOfReports(reportEmployee.getDirectReports());
            }
        }
        return;
    }

}
