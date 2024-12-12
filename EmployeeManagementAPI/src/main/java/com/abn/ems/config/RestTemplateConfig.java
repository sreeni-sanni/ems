package com.abn.ems.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {
    @Bean
    public RestTemplate restTemplate() {
       /* Can be configured for SSL further ensuring secure communication with downstream api*/
        return new RestTemplate();
    }

}
