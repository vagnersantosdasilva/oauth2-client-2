package com.vss.oauth2client2.service;

import com.vss.oauth2client2.model.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class ClientService {

    @Autowired
    WebClient webClient2;

    public Resource getResource(Integer id){
        Mono<Resource> mono = this.webClient2
                .method(HttpMethod.GET)
                .uri("/resource/{id}",id)
                .retrieve()
                .bodyToMono(Resource.class);

        Resource resource = mono.block();
        return  resource;
    }
}
