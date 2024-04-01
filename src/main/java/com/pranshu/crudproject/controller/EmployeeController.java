package com.pranshu.crudproject.controller;


import com.pranshu.crudproject.exception.EmployeeNotFoundException;
import com.pranshu.crudproject.model.Employee;
import com.pranshu.crudproject.repository.EmployeeRepository;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://frontend.route53testdemo.fun")
public class EmployeeController {
 
    private EmployeeRepository employeeRepository;
    public EmployeeController(EmployeeRepository employeeRepository) {
    	this.employeeRepository=employeeRepository;
    	
    }
    

	@PostMapping("/employee")
    @PreAuthorize("hasRole('admin')")
    Employee newEmployee(@RequestBody Employee newEmployee){
        return employeeRepository.save(newEmployee);
    }

    @GetMapping("/employees")
//    @PreAuthorize("hasRole('employee')")
    List<Employee>getAllEmployees(){
        return employeeRepository.findAll();
    }
    
    
    @GetMapping("/employee/{id}")
    Employee getEmployeeById(@PathVariable Long id){
        return employeeRepository.findById(id)
                .orElseThrow(()-> new EmployeeNotFoundException(id));
    }

    @PutMapping("/employee/{id}")
    @PreAuthorize("hasRole('admin')")
    Employee updateEmployee(@RequestBody Employee newEmployee, @PathVariable Long id){
        return employeeRepository.findById(id)
                .map(employee ->{
                    employee.setName(newEmployee.getName());
                    employee.setDept(newEmployee.getDept());
                    employee.setIncome(newEmployee.getIncome());
                    employee.setGender(newEmployee.getGender());
                    employee.setAddress(newEmployee.getAddress());
                    employee.setDesignation(newEmployee.getDesignation());
                    employee.setEmail(newEmployee.getEmail());
                    return employeeRepository.save(employee);
                }).orElseThrow(()->new EmployeeNotFoundException(id));
    }

    @DeleteMapping("/employee/{id}")
    @PreAuthorize("hasRole('admin')")
    String deleteEmployee(@PathVariable Long id){
        if(!employeeRepository.existsById(id)){
            throw new EmployeeNotFoundException(id);
        }
        employeeRepository.deleteById(id);
        return "User with id "+id+" has been deleted successfully";
    }
    
    
    

}
