package com.billingcounter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Entry point of the BillingCounter Spring Boot application.
 * Interview Tip: This is where the application is launched.
 */
@SpringBootApplication
public class BillingCounterApplication {
    public static void main(String[] args) {
        SpringApplication.run(BillingCounterApplication.class, args);
    }
}

// @EnableMethodSecurity(prePostEnabled = true)
// @SpringBootApplication
// public class BillingCounterApplication {
//     public static void main(String[] args) {
//         SpringApplication.run(BillingCounterApplication.class, args);
//     }
// }