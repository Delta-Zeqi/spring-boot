package com.zzq.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Boot05Web01Application {

    public static void main(String[] args) {
        SpringApplication.run(Boot05Web01Application.class, args);
        System.out.println("test git");
        System.out.println("test git2");
        System.out.println("test hot-fix merge");
        System.out.println("make master conflict!!");
    }

}
