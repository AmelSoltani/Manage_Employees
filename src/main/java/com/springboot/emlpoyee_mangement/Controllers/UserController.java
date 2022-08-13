package com.springboot.emlpoyee_mangement.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.springboot.emlpoyee_mangement.Models.Employee;
import com.springboot.emlpoyee_mangement.Models.ServiceEmp;
import com.springboot.emlpoyee_mangement.Models.User;
import com.springboot.emlpoyee_mangement.Repository.EmployeeRepository;
import com.springboot.emlpoyee_mangement.Repository.UserRepository;
import com.springboot.emlpoyee_mangement.Services.EmployeeService;
import com.springboot.emlpoyee_mangement.Services.ServiceEmpService;

@Controller
public class UserController {

	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private EmployeeRepository employeeRepo;
	
	@GetMapping("/login")
	public String viewHomePage() {
		return "index";
	}
	@GetMapping("/homepage")
	public String viewHomePage(Model model) {
		return "home";		
	}
	
	@GetMapping("/register")
	public String showRegistrationForm(Model model) {
		model.addAttribute("user", new User());
		
		return "signup_form";
	}
	
	@PostMapping("/process_register")
	public String processRegister(User user) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encodedPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
		
		userRepo.save(user);
		
		return "register_success";
	}
	
	@GetMapping("/users")
	public String listUsers(Model model) {
		List<User> listUsers = userRepo.findAll();
		model.addAttribute("listUsers", listUsers);
		
		return "users";
	}
	
	@Autowired
	private EmployeeService employeeService;
	
	// display list of employees
	
	
	@GetMapping("/showNewEmployeeForm")
	public String showNewEmployeeForm(Model model) {
		// create model attribute to bind form data
		Employee employee = new Employee();
		model.addAttribute("employee", employee);
		return "new_employee";
	}
	
	@PostMapping("/saveEmployee")
	public String saveEmployee(@ModelAttribute("employee") Employee employee) {
		// save employee to database
		employeeService.saveEmployee(employee);
		return "redirect:/homepage";
	}
	
	@GetMapping("/showFormForUpdate/{id}")
	public String showFormForUpdate(@PathVariable ( value = "id") long id, Model model) {
		
		// get employee from the service
		Employee employee = employeeService.getEmployeeById(id);
		
		// set employee as a model attribute to pre-populate the form
		model.addAttribute("employee", employee);
		return "update_employee";
	}
	
	@GetMapping("/deleteEmployee/{id}")
	public String deleteEmployee(@PathVariable (value = "id") long id) {
		
		// call delete employee method 
		this.employeeService.deleteEmployeeById(id);
		return "redirect:/homepage";
	}
	@GetMapping("/page/{pageNo}")
	public String findPaginated(@PathVariable (value = "pageNo") int pageNo, 
			@RequestParam("sortField") String sortField,
			@RequestParam("sortDir") String sortDir,
			Model model) {
		int pageSize = 5;
		
		Page<Employee> page = employeeService.findPaginated(pageNo, pageSize, sortField, sortDir);
		List<Employee> listEmployees = page.getContent();
		
		model.addAttribute("currentPage", pageNo);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("totalItems", page.getTotalElements());
		
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
		
		model.addAttribute("listEmployees", listEmployees);
		return "home";
	}
	
	@Autowired
	private ServiceEmpService service;
	
	// display list of employees
	
	@GetMapping("/services")
	public String listServices(Model model) {
		List<ServiceEmp> listServices = service.findAll();
		model.addAttribute("listServices", listServices);
		return "Service";
	}
	@GetMapping("/showNewServiceForm")
	public String showNewServiceForm(Model model) {
		// create model attribute to bind form data
		ServiceEmp serv = new ServiceEmp();
		model.addAttribute("service", serv);
		return "new_service";
	}
	
	@PostMapping("/saveService")
	public String saveService(@ModelAttribute("service") ServiceEmp serv) {
		// save service to database
		service.save(serv);
		return "redirect:/homepage";
	}
	
	@GetMapping("/deleteService/{id}")
	public String delete(@PathVariable (value = "id") long id) {
		
		// call delete service method 
		service.delete(id);
		return "redirect:/homepage";
	}
}
