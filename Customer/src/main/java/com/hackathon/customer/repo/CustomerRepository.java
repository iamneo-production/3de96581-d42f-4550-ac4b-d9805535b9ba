package com.hackathon.customer.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.hackathon.customer.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

	@Query(name = "select c.name ,c.account_no, c.balance From customer c join users u on u.id=c.user_id where u.id=?1", nativeQuery = true)
	Customer getCustomerByUserId(int id);

}
