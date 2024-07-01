package com.study.keycloak.controller;

import com.study.keycloak.model.AccountTransactions;
import com.study.keycloak.model.Customer;
import com.study.keycloak.repository.AccountTransactionsRepository;
import com.study.keycloak.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BalanceController {

    @Autowired
    private AccountTransactionsRepository accountTransactionsRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping("/myBalance")
    public List<AccountTransactions> getBalanceDetails(@RequestParam String email) {
//        List<AccountTransactions> accountTransactions = accountTransactionsRepository.
//                findByCustomerIdOrderByTransactionDtDesc(id);
//        if (accountTransactions != null ) {
//            return accountTransactions;
//        }else {
//            return null;
//        }
        List<Customer> customers = customerRepository.findByEmail(email);
        if (customers != null && !customers.isEmpty()) {
            List<AccountTransactions> accountTransactions = accountTransactionsRepository.
                    findByCustomerIdOrderByTransactionDtDesc(customers.get(0).getId());
            if (accountTransactions != null ) {
                return accountTransactions;
            }
        }
        return null;
    }
}
