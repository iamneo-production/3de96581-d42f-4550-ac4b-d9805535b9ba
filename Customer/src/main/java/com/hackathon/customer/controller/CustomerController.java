package com.hackathon.customer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.hackathon.customer.config.JwtAuthFilter;
import com.hackathon.customer.dto.JWTAuthResponse;
import com.hackathon.customer.dto.LoginDto;
import com.hackathon.customer.dto.Response;
import com.hackathon.customer.dto.UserInfoDTO;
import com.hackathon.customer.model.Customer;
import com.hackathon.customer.model.Users;
import com.hackathon.customer.service.CustomerService;
import com.hackathon.customer.service.JwtService;
import com.netflix.discovery.converters.Auto;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CustomerController {

    @Autowired
    private CustomerService service;
    @Autowired
    private JwtService jwtService;
    @Autowired PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping("/welcome")
    public String welcome() {
        return "Welcome this endpoint is not secure";
    }

    @PostMapping("/customer")
    public String addNewUser(@RequestBody UserInfoDTO userInfo) {
        return service.addUser(userInfo);
    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<Customer> getAllTheProducts() {
        return service.getCustomers();
    }

    @GetMapping("/accounts/{id}")
    @PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
    public Customer getProductById(@PathVariable int id) {
        return service.getCustomer(id);
    }


    @PostMapping("/authenticate")
    public ResponseEntity<Response<JWTAuthResponse>> authenticateAndGetToken(@RequestBody LoginDto authRequest) {
    	Response<JWTAuthResponse> response=null;
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getEmailId(), authRequest.getPassword()));
        if (authentication.isAuthenticated()) {
        	response=new Response<JWTAuthResponse>();
        	JWTAuthResponse jAuthResponse=new JWTAuthResponse();
        	jAuthResponse.setAccessToken(jwtService.generateToken(authRequest.getEmailId()));
        	jAuthResponse.setTokenType("Bearer");
        	response.setResponseCode(HttpStatus.OK.ordinal());
        	response.setMessage("Success");
        	response.setData(jAuthResponse);
            return new ResponseEntity<Response<JWTAuthResponse>>(response, HttpStatus.OK);
        } else {
            throw new UsernameNotFoundException("invalid user request !");
        }


    }
}