package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Configuration
public class RestClientConfig {
    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate(clientHttpRequestFactory());
        restTemplate.setInterceptors(Collections.singletonList(new CustomClientHttpRequestInterceptor()));
        return restTemplate;
    }

    private ClientHttpRequestFactory clientHttpRequestFactory() {
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        factory.setReadTimeout(35 * 1000);
        factory.setConnectTimeout(5 * 1000);
        return factory;
    }
}
