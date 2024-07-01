//package com.study.keycloak.config;
//
//import com.study.keycloak.model.Authority;
//import com.study.keycloak.model.Customer;
//import com.study.keycloak.repository.CustomerRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Component;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Set;
//
//@Component
//public class EazyBankUsernamePwdAuthenticationProvider implements AuthenticationProvider {
//
//    private final CustomerRepository customerRepository;
//
//    private final PasswordEncoder passwordEncoder;
//
//    @Autowired
//    public EazyBankUsernamePwdAuthenticationProvider(CustomerRepository customerRepository, PasswordEncoder passwordEncoder) {
//        this.customerRepository = customerRepository;
//        this.passwordEncoder = passwordEncoder;
//    }
//
//    @Override
//    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//        String username = authentication.getName();
//        String pwd = authentication.getCredentials().toString();
//        List<Customer> customer = customerRepository.findByEmail(username);
//        if (!customer.isEmpty()) {
//            if (passwordEncoder.matches(pwd, customer.get(0).getPwd())) {
//                // 권한을 기존 role에서 가져오지 않음
////                List<GrantedAuthority> authorities = new ArrayList<>();
////                authorities.add(new SimpleGrantedAuthority(customer.get(0).getRole()));
//                return new UsernamePasswordAuthenticationToken(username, pwd, getGrantedAuthorities(customer.get(0).getAuthorities()));
//            } else {
//                throw new BadCredentialsException("Invalid password!");
//            }
//        }else {
//            throw new BadCredentialsException("No user registered with this details!");
//        }
//    }
//
//    // 권한 목록을 생성하는 메서드
//    private List<GrantedAuthority> getGrantedAuthorities(Set<Authority> authorities) {
//        // 권한 목록을 담을 리스트
//        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
//        // authorities를 순회하며 SimpleGrantedAuthority로 변환
//        for (Authority authority : authorities) {
//            // 권한을 SimpleGrantedAuthority로 변환하여 리스트에 추가
//            grantedAuthorities.add(new SimpleGrantedAuthority(authority.getName()));
//        }
//        return grantedAuthorities;
//    }
//
//    @Override
//    public boolean supports(Class<?> authentication) {
//        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
//    }
//}
