package com.springboot.emlpoyee_mangement.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.springboot.emlpoyee_mangement.Models.ServiceEmp;

import com.springboot.emlpoyee_mangement.Repository.ServiceRepository;
@Service
public class ServiceEmpService {
	@Autowired
	private ServiceRepository serviceRepository;
	
	//Get All Countrys
	public List<ServiceEmp> findAll(){
		return serviceRepository.findAll();
	}	
	
	//Delete Country
	public void delete(long id) {
		serviceRepository.deleteById(id);
	}
	
	//Update Country
	public void save( ServiceEmp service) {
		serviceRepository.save(service);
	}
}
