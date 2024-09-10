package com.boot.api.globals.configuration;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;

@Configuration
@EnableJpaAuditing
public class JpaConfiguration {
    @Bean
    JPAQueryFactory jpaQueryFactory(EntityManager em) { return new JPAQueryFactory(em); }

    @Bean
    public AuditorAware<Integer> auditorProvider() { return () -> Optional.of(1); }
}
