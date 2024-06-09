package com.study.jwtandoauth2.repository;

import com.study.jwtandoauth2.model.Contact;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ContactRepository extends CrudRepository<Contact, Long> {
	
	
}
