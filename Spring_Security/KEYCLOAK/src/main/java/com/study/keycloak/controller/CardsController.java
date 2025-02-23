package com.study.keycloak.controller;

import com.study.keycloak.model.Cards;
import com.study.keycloak.model.Customer;
import com.study.keycloak.repository.CardsRepository;
import com.study.keycloak.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CardsController {

    @Autowired
    private CardsRepository cardsRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping("/myCards")
    public List<Cards> getCardDetails(@RequestParam String email) {
//        List<Cards> cards = cardsRepository.findByCustomerId(id);
//        if (cards != null ) {
//            return cards;
//        }else {
//            return null;
//        }
        List<Customer> customers = customerRepository.findByEmail(email);
        if (customers != null && !customers.isEmpty()) {
            List<Cards> cards = cardsRepository.findByCustomerId(customers.get(0).getId());
            if (cards != null ) {
                return cards;
            }
        }
        return null;
    }

}
