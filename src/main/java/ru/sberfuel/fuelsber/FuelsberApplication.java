package ru.sberfuel.fuelsber;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = {"ru.sberfuel.fuelsber.*"})
public class FuelsberApplication {

    public static void main(String[] args) {
        SpringApplication.run(FuelsberApplication.class, args);
    }

}
