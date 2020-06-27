package com.example.starter.configuration;

import com.example.starter.HLTemplate;
import com.example.starter.format.FormatProcessor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Import(com.example.starter.configuration.FormatAutoConfiguration.class)
@EnableConfigurationProperties(com.example.starter.configuration.HelloProperties.class)
@Configuration
public class HLAutoConfiguration {

    @Bean
    public HLTemplate getHLTemplate(FormatProcessor formatProcessor, HelloProperties properties) {
        return new HLTemplate(properties, formatProcessor);
    }
}
