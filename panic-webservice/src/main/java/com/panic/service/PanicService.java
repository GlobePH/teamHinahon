package com.panic.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Created by tabs on 7/23/16.
 */

@SpringBootApplication
@EnableJpaRepositories
public class PanicService {

    public static void main(String[] args) {
        SpringApplication.run(PanicService.class, args);
    }
}
