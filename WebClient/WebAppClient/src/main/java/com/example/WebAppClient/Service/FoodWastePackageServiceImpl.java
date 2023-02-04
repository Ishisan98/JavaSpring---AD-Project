package com.example.WebAppClient.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.WebAppClient.Model.FoodWastePackage;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;

@Service
public class FoodWastePackageServiceImpl implements FoodWastePackageService {


    @Autowired
    WebClient webClient;

    public FoodWastePackageServiceImpl(@Value("${content-service}") String baseURL) {
        this.webClient = WebClient.builder()
                .baseUrl(baseURL)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }



    @Override
    public List<FoodWastePackage> getPackageList() {
        Flux<FoodWastePackage> retrievedPackageList = webClient.get()
                .uri("/foodwastepackage")
                .accept(MediaType.APPLICATION_JSON)
                .exchangeToFlux(response -> {
                    if (response.statusCode().equals(HttpStatus.OK)) {
                        return response.bodyToFlux(FoodWastePackage.class);
                    } else {
                        return response.createException().flatMapMany(Flux::error);
                    }
                });
        return retrievedPackageList.collectList().block();
    }


    @Override 
    public FoodWastePackage createPackage (FoodWastePackage foodwastepackages){
        Mono<FoodWastePackage> createdPackage = webClient.post()
                                                  .uri("/foodwastepackage")                                        
                                                  .body(Mono.just(foodwastepackages), FoodWastePackage.class)
                                                  .retrieve().bodyToMono(FoodWastePackage.class)
                                                  .timeout(Duration.ofMillis(10_000));
        return createdPackage.block();
    }

}