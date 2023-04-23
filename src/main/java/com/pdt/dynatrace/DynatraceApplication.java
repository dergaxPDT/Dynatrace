package com.pdt.dynatrace;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnabledSwagger2
public class DynatraceApplication {

    public static void main(String[] args) {
        SpringApplication.run(DynatraceApplication.class, args);
    }

}
