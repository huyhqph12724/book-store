package com.mshop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "customAuditorAware")
public class AuditorAwareConfig {
    @Bean("customAuditorAware")
    public CustomAuditorAware customAuditorAware() {
        return new CustomAuditorAware();
    }
}
