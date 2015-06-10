package edu.tongji.config;

import edu.tongji.Application;
import org.pegdown.PegDownProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = Application.class)
public class MarkdownConfig {

    @Bean
    public static PegDownProcessor pegDownProcessor() {
        return new PegDownProcessor();
    }

}
