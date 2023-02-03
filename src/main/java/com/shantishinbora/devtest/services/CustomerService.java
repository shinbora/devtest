package com.shantishinbora.devtest.services;

import com.shantishinbora.devtest.BaseResponse;
import com.shantishinbora.devtest.dto.request.RegisterRequest;
import com.shantishinbora.devtest.entities.Customer;
import com.shantishinbora.devtest.repositories.CustomerRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CustomerService {
    @Autowired
    CustomerRepository customerRepository;

    public List<Customer> getCustomer(){
        return customerRepository.findAll();
    }

    public void addCustomer(Customer customer){
        Optional<Customer> customerOptional = customerRepository.findCustomerByEmail(customer.getEmail());
        if(customerOptional.isPresent()){
            throw new IllegalStateException("Email is already registered");
        }
        customerRepository.save(customer);
    }

    public ResponseEntity<?> deleteCustomer(BigInteger id){
        boolean customerExists = customerRepository.existsById(id);
        if(!customerExists){
            throw new IllegalStateException("Customer ID " + id + " doesn't exist");
        }
        customerRepository.deleteById(id);
        return ResponseEntity.ok(new BaseResponse("User deleted successfully!"));
    }

    @Transactional
    public void updateCustomer(BigInteger id, String name, String email){
        Customer customerExists = customerRepository.findById(id).orElseThrow(() -> new IllegalStateException("Customer ID " + id + "doesn't exists"));
        if(name != null && name.length() > 0 && !Objects.equals(customerExists.getName(), name)){
            customerExists.setName(name);
        }
        if(email != null && email.length() > 0 && !Objects.equals(customerExists.getEmail(), email)){
            Optional<Customer> customerOptional = customerRepository.findCustomerByEmail(customerExists.getEmail());
            if(customerOptional.isPresent()){
                throw new IllegalStateException("Email is already registered");
            }
            customerExists.setEmail(email);
        }
    }

    public ResponseEntity<?> register(RegisterRequest registerRequest){
        if (customerRepository.existsByName(registerRequest.getName())) {
            return ResponseEntity
                    .badRequest()
                    .body(new BaseResponse("Error: Name is already taken!"));
        }
        Customer customer = new Customer(registerRequest.getName(),
                registerRequest.getEmail(), registerRequest.getPhone());
        customerRepository.save(customer);
        return ResponseEntity.ok(new BaseResponse("User registered successfully!"));
    }
}
