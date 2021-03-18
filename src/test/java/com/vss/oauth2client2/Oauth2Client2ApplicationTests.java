package com.vss.oauth2client2;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@SpringBootTest
class Oauth2Client2ApplicationTests {

    @Autowired
    WebClient webClient;

    @Autowired
    WebClient webClient2;

    @Test
    void testConfiguracaoWebClientDeprecated() {
        Integer id=1;
        Mono<Resource> mono = this.webClient
                            .method(HttpMethod.GET)
                            .uri("http://localhost:8080/myapp/resource/{id}",id)
                            .retrieve()
                            .bodyToMono(Resource.class);

        Resource resource = mono.block();

        System.out.println(resource.getName());

    }

    @Test
    void testConfiguracaoWeb2() {
        Integer id=2;
        Mono<Resource> mono = this.webClient2
                .method(HttpMethod.GET)
                .uri("/resource/{id}",id)
                .retrieve()
                .bodyToMono(Resource.class);

        Resource resource = mono.block();

        System.out.println(resource.getName());

    }


}
