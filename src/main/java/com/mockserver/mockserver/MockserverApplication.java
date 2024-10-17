package com.mockserver.mockserver;

import com.mockserver.mockserver.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

@SpringBootApplication
@RestController
public class MockserverApplication {
	@Autowired
	Config config;

	public static void main(String[] args) {
		SpringApplication.run(MockserverApplication.class, args);
	}

	@GetMapping("/ratelimiter")
	public void test(){
		for(int i=0;i<200;i++){
			if(i%5==0){
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
			config.run();
		}
	}

	@Bean(name = "executor")
	public Executor taskExector(){
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(10);
		executor.setMaxPoolSize(10);
		executor.setQueueCapacity(500);
		return executor;
	}

	@GetMapping("/hello")
	public String hello(){
		return "Hello world!";
	}

}
