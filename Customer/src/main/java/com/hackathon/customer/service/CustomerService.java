package com.hackathon.customer.service;

import java.util.List;

import com.hackathon.customer.dto.UserInfoDTO;
import com.hackathon.customer.model.Customer;
import com.hackathon.customer.model.Users;

public interface CustomerService {

	String addUser(UserInfoDTO userInfo);

	List<Customer> getCustomers();

	Customer getCustomer(int id);

	String addEmployee(Users emp);

	List<Users> getAllEmployee();

	Users viewEmployee(Long id);


}
