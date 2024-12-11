package com.mockserver.mockserver;

import com.mockserver.mockserver.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

@SpringBootApplication
@RestController
public class MockserverApplication {
	@Autowired
	Config config;

//	@Autowired
//	EmployeeRepo employeeRepo;

	@Autowired
	WebClient webClient;

	@Value("testUrl")

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
	public String hello(@RequestParam String value){
		System.out.println("Hello");
		String response = webClient.get().uri("/test?value="+value).retrieve().bodyToMono(String.class).block();
		System.out.println(response);
		return "Hello world!";
	}

	@GetMapping("/test")
	public String test(@RequestParam String value){
		System.out.println("Hello from test method....."+value);
		return "Hello";
	}

//	@PostMapping("/add")
//	public Employee saveEmployee(@RequestBody Employee employee){
//		return employeeRepo.save(employee);
//	}



}
