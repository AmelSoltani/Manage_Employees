package com.springboot.emlpoyee_mangement.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.springboot.emlpoyee_mangement.Models.ServiceEmp;

@Repository
public interface ServiceRepository extends JpaRepository<ServiceEmp,Long>{

}
