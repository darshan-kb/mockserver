package com.mockserver.mockserver.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class Config {
    @Autowired
    WebClient webClient;
    @Async("executor")
    public void run(){
        webClient.get().uri("http://localhost:9001/testroute1/get").retrieve().bodyToMono(String.class)
                .doOnNext(res -> {
                    System.out.println(res);
                }).subscribe();
    }
}
