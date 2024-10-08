package io.company.brewcraft;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class BrewcraftApplication {
    private static final Logger log = LoggerFactory.getLogger(BrewcraftApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(BrewcraftApplication.class, args);
    }
}