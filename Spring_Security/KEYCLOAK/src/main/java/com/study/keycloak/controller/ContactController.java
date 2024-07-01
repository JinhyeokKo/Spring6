package com.study.keycloak.controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.study.keycloak.model.Contact;
import com.study.keycloak.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class ContactController {

    @Autowired
    private ContactRepository contactRepository;

    @PostMapping("/contact")
    // pre는 요청을 처리하기 전에, post는 요청을 처리한 후에 필터링을 수행한다.
    // 그래서 post는 db에 기록이 남는다
    //@PreFilter("filterObject.contactName != 'test'")
    @PostFilter("filterObject.contactName != 'test'")
    public List<Contact> saveContactInquiryDetails(@RequestBody List<Contact> contacts) {
        Contact contact = contacts.get(0);
        contact.setContactId(getServiceReqNumber());
        contact.setCreateDt(new Date(System.currentTimeMillis()));
        contact = contactRepository.save(contact);
        List<Contact> returnContacts = new ArrayList<>();
        returnContacts.add(contact);
        return returnContacts;
//        contact.setContactId(getServiceReqNumber());
//        contact.setCreateDt(new Date(System.currentTimeMillis()));
//        return contactRepository.save(contact);
    }

    public String getServiceReqNumber() {
        Random random = new Random();
        int ranNum = random.nextInt(999999999 - 9999) + 9999;
        return "SR"+ranNum;
    }
}
