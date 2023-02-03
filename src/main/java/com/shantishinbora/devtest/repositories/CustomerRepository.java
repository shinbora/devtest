package com.shantishinbora.devtest.repositories;

import com.shantishinbora.devtest.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, BigInteger> {
    Optional<Customer> findCustomerByEmail(String email);
    Boolean existsByName(String name);
    Customer findByName(String name);
    void deleteByName(String name);
}
