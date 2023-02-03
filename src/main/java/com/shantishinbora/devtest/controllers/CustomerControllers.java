package com.shantishinbora.devtest.controllers;

import com.shantishinbora.devtest.JwtUtils;
import com.shantishinbora.devtest.dto.request.LoginRequest;
import com.shantishinbora.devtest.dto.request.RegisterRequest;
import com.shantishinbora.devtest.entities.Customer;
import com.shantishinbora.devtest.repositories.CustomerRepository;
import com.shantishinbora.devtest.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;

@Controller
@RequestMapping("/customer")
public class CustomerControllers {
    @Autowired
    CustomerService customerService;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    CustomerRepository customerRepository;

    @GetMapping
    public String homePage(){
        return "index";
    }

    @GetMapping("/get")
    public List<Customer> getCustomer(){
        return customerService.getCustomer();
    }

    @PostMapping("/add")
    public void addCustomer(@RequestBody Customer customer){
        customerService.addCustomer(customer);
    }

    @DeleteMapping(path="{customerId}")
    public ResponseEntity<?> deleteCustomer(@PathVariable("customerId") BigInteger id){
        return customerService.deleteCustomer(id);
    }

    @PutMapping(path="{customerId}")
    public void updateCustomer(@PathVariable("customerId") BigInteger id, @RequestParam(required = false) String name, @RequestParam(required = false) String email){
        customerService.updateCustomer(id, name, email);
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        String jwt = jwtUtils.generateJwtToken(loginRequest.getName());
        return ResponseEntity.ok(jwt);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest registerRequest) {
        return customerService.register(registerRequest);
    }
}
