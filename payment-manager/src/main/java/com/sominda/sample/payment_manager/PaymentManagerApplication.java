package com.sominda.sample.payment_manager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class PaymentManagerApplication {

    public static void main(String[] args) {

        SpringApplication.run(PaymentManagerApplication.class, args);
    }
}
