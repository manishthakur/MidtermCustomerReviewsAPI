package com.udacity.course3.reviews;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ReviewsApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReviewsApplication.class, args);
    }

    @Value("${spring.datasource.url}")
    private String dbBaseUrl;

    @Value("${spring.datasource.username}")
    private String userName;

    @Value("${spring.datasource.password}")
    private String password;

    @Bean
    public void flywayDbMigrate() {
        Flyway flyway = Flyway.configure().dataSource(dbBaseUrl, userName, password).load();
        flyway.migrate();
    }
}