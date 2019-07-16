package com.yg0r2.kinesis.client.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy(proxyTargetClass = true)
@ComponentScan(basePackages = "com.yg0r2.kinesis.client.example.config")
public class SampleApplication {

    public static void main(String[] args) {
        System.setProperty("aws.cborEnabled", "false");
        System.setProperty("aws.disableEc2Metadata", "true");

        SpringApplication.run(SampleApplication.class, args);
    }

}
