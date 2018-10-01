package com.wigo.server.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.Arrays;
import java.util.List;

@Configuration
@ComponentScan({"com.wigo.server.controllers", "com.wigo.server.config","com.wigo.server.service"})
public class WigoConfiguration extends WebMvcConfigurerAdapter {

    @Bean
    public RestOperations restOperations(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Bean
    public ObjectMapper objectMapper(){
        return getObjectMapper();
    }

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.stream().filter(converter-> converter instanceof MappingJackson2HttpMessageConverter)
                .findFirst().ifPresent(converter-> ((MappingJackson2HttpMessageConverter)converter).setObjectMapper(getObjectMapper()));
    }

    private ObjectMapper getObjectMapper(){
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper;
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        String[] allVerbs = Arrays.stream(HttpMethod.values()).map(HttpMethod::toString).toArray(String[]::new);
        registry.addMapping("/**").allowedMethods(allVerbs);
    }
}
