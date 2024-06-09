package com.study.keycloak.repository;

import com.study.keycloak.model.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Long> {

    // findBy 는 Jpa에서 제공하는 메소드명 규칙에 따라 동작
    // email 필드를 이용하여 조회 --> jpa가 자동으로 비즈니스로직 생성
    List<Customer> findByEmail(String email);
}
