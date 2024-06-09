package com.study.keycloak.controller;

import com.study.keycloak.model.Accounts;
import com.study.keycloak.model.Customer;
import com.study.keycloak.repository.AccountsRepository;
import com.study.keycloak.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AccountController {

    @Autowired
    private AccountsRepository accountsRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping("/myAccount")
    public Accounts getAccountDetails(@RequestParam String email) {
//        Accounts accounts = accountsRepository.findByCustomerId(id);
//        if (accounts != null ) {
//            return accounts;
//        }else {
//            return null;
//        }
        List<Customer> customers = customerRepository.findByEmail(email);
        if (customers != null && !customers.isEmpty()) {
            Accounts accounts = accountsRepository.findByCustomerId(customers.get(0).getId());
            if (accounts != null) {
                return accounts;
            }
        }
        return null;
    }
}