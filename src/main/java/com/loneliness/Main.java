package com.loneliness;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


@SpringBootApplication(scanBasePackages = {"com.loneliness", "com.loneliness.entity.domain"})
@EnableWebMvc
public class Main extends SpringBootServletInitializer {



    public static void main(String[] args) {


        SpringApplication.run(Main.class,args);



    }



}
