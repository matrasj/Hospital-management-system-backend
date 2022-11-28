package com.example.hospitalmanagementsystembackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableAspectJAutoProxy
@EnableTransactionManagement
@EnableAsync
public class HospitalManagementSystemBackendApplication  {
    public static void main(String[] args) {
        SpringApplication.run(HospitalManagementSystemBackendApplication.class, args);
    }

}
