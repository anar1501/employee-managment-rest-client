package com.company;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
public class EmployeeManagmentRestApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmployeeManagmentRestApiApplication.class, args);
    }

}
