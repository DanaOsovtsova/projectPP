package bsu.labs.ArithmeticsApp.configuration;

import bsu.labs.ArithmeticsApp.encryptor.FileEncryptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public FileEncryptor customBean() {
        return new FileEncryptor("dana1234keys4321");
    }
}
