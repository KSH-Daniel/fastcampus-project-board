package com.fastcampus.projectboard.config;

import com.fastcampus.projectboard.dto.security.BoardPrincipal;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@EnableJpaAuditing
@Configuration
public class JpaConfig {

    @Bean
    public AuditorAware<String> auditorAware() {
        return () -> Optional.ofNullable(SecurityContextHolder.getContext()) // security 정보를 모두들고있는 클래스호출 후, security context 를 불러온다
                .map(SecurityContext::getAuthentication) // 인증 정보를 가져오고
                .filter(Authentication::isAuthenticated) // 인증이 되었는지 (로그인 상태인지) 확안
                .map(Authentication::getPrincipal) // principal 정보를 가져온다
                .map(BoardPrincipal.class::cast) // custom 객체로 형변환 (UserDetails)
                .map(BoardPrincipal::getUsername); // user name 을 가져와 실제 유저 정보를 불러온다
    }
}
