package com.example.highgrade;

import com.example.highgrade.config.AwsS3Config;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class HighgradeApplication {

    public static void main(String[] args) {
        SpringApplication.run(HighgradeApplication.class, args);
    }

}
