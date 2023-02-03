package com.shantishinbora.devtest.services;

import com.shantishinbora.devtest.dto.response.BaseResponse;
import com.shantishinbora.devtest.dto.request.RegisterRequest;
import com.shantishinbora.devtest.entities.Customer;
import com.shantishinbora.devtest.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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

    public Customer findCustomerByName(String name){
        return customerRepository.findByName(name);
    }

    @Transactional
    public ResponseEntity<?> deleteCustomer(String name){
        boolean customerExists = customerRepository.existsByName(name);
        if(!customerExists){
            throw new IllegalStateException("Customer name " + name + " doesn't exist");
        }
        customerRepository.deleteByName(name);
        return ResponseEntity.ok(new BaseResponse("User deleted successfully!"));
    }

    @Transactional
    public void updateCustomer(String name, String email, String phone){
        Customer customerExists = customerRepository.findByName(name);
        if(null == customerExists){
            throw new IllegalStateException("Customer name " + name + " doesn't exists");
        }else {
            if (email != null && email.length() > 0 && !Objects.equals(customerExists.getEmail(), email)) {
                customerExists.setEmail(email);
            }

            if (phone != null && phone.length() > 0 && !Objects.equals(customerExists.getPhone(), phone)) {
                customerExists.setName(name);
            }
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
