package com.szht;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
public class UapApplication {
    public static void main(String[] args) {
        SpringApplication.run(UapApplication.class);
    }
}
