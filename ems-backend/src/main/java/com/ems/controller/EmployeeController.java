package com.ems.controller;


import com.ems.exception.ResourceNotFoundException;
import com.ems.model.Employee;
import com.ems.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping("/employees")
    public List<Employee> getAllEmployees(){
        return this.employeeRepository.findAll();
    }

    @GetMapping("/employees/{id}")
    public ResponseEntity<Employee> getEmployeeByID(@PathVariable long id) throws ResourceNotFoundException {
        Employee employee = this.employeeRepository.findById(id)
                            .orElseThrow( () -> new ResourceNotFoundException("Employee not Found for this ID ::" +id));

        return ResponseEntity.ok().body(employee);
    }

    @PostMapping("/employees")
    public Employee createEmployee(@RequestBody Employee employee){
        return this.employeeRepository.save(employee);
    }

    @PutMapping("/employees/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable("id") long id,
                                                   @RequestBody Employee employeeDetails) throws ResourceNotFoundException{

        Employee employee = this.employeeRepository.findById(id)
                            .orElseThrow(() -> new ResourceNotFoundException("Employee not Found for this ID::" +id) );

        employee.setEmailId(employeeDetails.getEmailId());
        employee.setFirstName(employeeDetails.getFirstName());
        employee.setLastName(employeeDetails.getLastName());

        final Employee updatedEmployee = employeeRepository.save(employee);
        return ResponseEntity.ok().body(updatedEmployee);

    }

    @DeleteMapping("/employees/{id}")
    public Map<String, Boolean> deleteEmployee(@PathVariable("id") long id) throws ResourceNotFoundException{
        Employee employee = this.employeeRepository.findById(id)
                            .orElseThrow(() -> new ResourceNotFoundException("Employee not Found for this ID::"+id));

        employeeRepository.delete(employee);

        Map<String , Boolean> response = new HashMap<>();
                response.put("Deleted",Boolean.TRUE);

                return response;
    }

}
