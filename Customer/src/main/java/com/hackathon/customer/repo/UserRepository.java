package com.hackathon.customer.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hackathon.customer.model.Users;

public interface UserRepository extends JpaRepository<Users, Long> {


    Boolean existsByEmailId(String email);


	Optional<Users> findByEmailId(String username);
}