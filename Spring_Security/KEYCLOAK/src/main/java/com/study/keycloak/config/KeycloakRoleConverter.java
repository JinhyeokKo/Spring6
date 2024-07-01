package com.study.keycloak.config;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class KeycloakRoleConverter implements Converter<Jwt, Collection<GrantedAuthority>> {
    @Override
    public Collection<GrantedAuthority> convert(Jwt source) {
        // realm_access에 역할이 저장되어 있음 --> map 객체를 추출
        Map<String, Object> realmAccess = (Map<String, Object>) source.getClaims().get("realm_access");

        // 비어있다면 빈 리스트 반환
        if (realmAccess == null || realmAccess.isEmpty()) {
            return new ArrayList<>();
        }

        // realmAccess map에 존재하는 모든 역할 정보를 가져오기 위해 roles 인 객체 반환
        Collection<GrantedAuthority> returnValue = ((List<String>) realmAccess.get("roles"))
                // map 메소드 사용하면 Lambda 식을 작성해서 stream list에 존재하는 역할 하나하나에 로직 실행 가능
                // ROLE_ + 역할명으로 변환
                .stream().map(roleName -> "ROLE_" + roleName)
                // SimpleGrantedAuthority 객체로 변환
                .map(SimpleGrantedAuthority::new)
                // collect 메소드로 List 객체로 변환
                .collect(Collectors.toList());

        return returnValue;
    }
}
