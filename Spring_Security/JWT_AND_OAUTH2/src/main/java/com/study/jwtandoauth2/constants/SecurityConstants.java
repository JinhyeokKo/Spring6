package com.study.jwtandoauth2.constants;

// 일반적으로는 CI/CD 파이프라인을 통해 환경변수로 관리
public interface SecurityConstants {

    // 비밀 키 값을 가진 JWT 키
    public static final String JWT_KEY = "jxgEQeXHuPq8VdbyYFNkANdudQ53YUn4";
    // 헤더 이름 설정
    public static final String JWT_HEADER = "Authorization";

}
