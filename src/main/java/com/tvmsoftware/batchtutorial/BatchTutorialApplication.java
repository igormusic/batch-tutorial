package com.tvmsoftware.batchtutorial;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.task.configuration.EnableTask;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
@EnableTask
@EnableBatchProcessing
public class BatchTutorialApplication {
    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(BatchTutorialApplication.class, args);
        System.exit(SpringApplication.exit(applicationContext));
    }
}
