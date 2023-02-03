package com.shantishinbora.devtest.controllers;

import com.shantishinbora.devtest.utils.JwtUtils;
import com.shantishinbora.devtest.dto.request.LoginRequest;
import com.shantishinbora.devtest.dto.request.RegisterRequest;
import com.shantishinbora.devtest.dto.response.LoginResponse;
import com.shantishinbora.devtest.entities.Customer;
import com.shantishinbora.devtest.repositories.CustomerRepository;
import com.shantishinbora.devtest.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerControllers {
    @Autowired
    CustomerService customerService;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    CustomerRepository customerRepository;

    @GetMapping("/myProfile")
    public Customer findCustomerByName(Authentication authentication){
        String name = authentication.getName();
        return customerService.findCustomerByName(name);
    }

    @GetMapping("/getAllCust")
    public List<Customer> getCustomer(){
        return customerService.getCustomer();
    }

    @PostMapping("/add")
    public void addCustomer(@RequestBody Customer customer){
        customerService.addCustomer(customer);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteCustomer(Authentication authentication){
        String name = authentication.getName();
        return customerService.deleteCustomer(name);
    }

    @PutMapping("/update")
    public void updateCustomer(Authentication authentication, @RequestBody Customer customer){
        String name = authentication.getName();
        String email = customer.getEmail();
        String phone = customer.getPhone();
        customerService.updateCustomer(name, email, phone);
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        String jwt = jwtUtils.generateJwtToken(loginRequest.getName());
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(jwt);
        return ResponseEntity.ok(loginResponse);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest registerRequest) {
        return customerService.register(registerRequest);
    }
}
