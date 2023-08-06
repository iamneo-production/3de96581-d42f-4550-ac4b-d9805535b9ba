package com.hackathon.customer.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.hackathon.customer.dto.UserInfoDTO;
import com.hackathon.customer.model.Customer;
import com.hackathon.customer.model.Role;
import com.hackathon.customer.model.Users;
import com.hackathon.customer.repo.CustomerRepository;
import com.hackathon.customer.repo.RoleRepository;
import com.hackathon.customer.repo.UserRepository;

import jakarta.annotation.PostConstruct;

@Service
@Repository
public class CustomerServiceImpl implements CustomerService{

    List<Customer> productList = null;

    @Autowired
    private UserRepository repository;
    
    @Autowired
    private CustomerRepository customer;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired 
    private UserRepository userRepository;
    
    @Autowired
    private RoleRepository roleRepo;

    @PostConstruct
    public void loadProductsFromDB() {
        productList = customer.findAll();
    }


    public List<Customer> getCustomers() {
        return productList;
    }

    public Customer getCustomer(int id) {
        return customer.getCustomerByUserId(id);
    }


    public String addUser(UserInfoDTO userInfoDto) {
    	Users user=new Users();
    	user.setEmailId(userInfoDto.getEmailId());
    	user.setFirstName(userInfoDto.getFirstName());
    	user.setLastName(userInfoDto.getFirstName());
    	user.setPassword(passwordEncoder.encode(userInfoDto.getPassword()));
    	Optional<Role> role=roleRepo.findByName(userInfoDto.getRole());
    	if(role.isPresent()) {
    		Set<Role> rol = new HashSet<Role>();
    		rol.add(role.get());
    		user.setRoles(rol);
    	}
    	Users u=repository.save(user);
    			Customer cust=new Customer();
    			cust.setAccountNo(userInfoDto.getAccountNo());
    			cust.setBalance(0);
    			cust.setName(userInfoDto.getFirstName()+" "+userInfoDto.getLastName());
    			cust.setUserId(u.getId());
    			customer.save(cust);
        return "user added to system ";
    }


	@Override
	public String addEmployee(Users emp) {
		return null;
	}


	@Override
	public List<Users> getAllEmployee() {
		return userRepository.findAll();
	}


	@Override
	public Users viewEmployee(Long id) {
		return userRepository.findById(id).get();
	}





}