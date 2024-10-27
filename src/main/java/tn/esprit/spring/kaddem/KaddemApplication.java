package tn.esprit.spring.kaddem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;



@EnableScheduling // Enables scheduling capabilities in the application
@SpringBootApplication // Indicates this is a Spring Boot application
public class KaddemApplication {

    public static void main(String[] args) {
        // Launches the Spring Boot application
        SpringApplication.run(KaddemApplication.class, args);
    }
}

