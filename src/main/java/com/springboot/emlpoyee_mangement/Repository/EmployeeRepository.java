package com.springboot.emlpoyee_mangement.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springboot.emlpoyee_mangement.Models.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long>{

}


