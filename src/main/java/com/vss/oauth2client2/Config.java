package com.vss.oauth2client2;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.AuthorizedClientServiceReactiveOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.InMemoryReactiveOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.*;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.security.oauth2.client.web.server.UnAuthenticatedServerOAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.web.reactive.function.client.WebClient;


@Configuration
public class Config {

    @Value("${spring.security.oauth2.client.registration.my-platform.client-id}")
    private String clientID;

    @Value("${spring.security.oauth2.client.registration.my-platform.client-secret}")
    private String clientSecret;

    @Value("${spring.security.oauth2.client.registration.my-platform.scope}")
    private String grantType;

    @Value("${spring.security.oauth2.client.provider.my-platform.token-uri}")
    private String tokenURI;

    @Value("${baseUrlResource}")
    private String baseUrlResource;


    //TODO: Implementar a configuração do BEAN de forma antiga e de forma atualizada usando webClient
    @Bean
    WebClient webClient() {

        ClientRegistration clientRegistration = ClientRegistration
                .withRegistrationId("my-platform")
                .clientId(clientID)
                .clientSecret(clientSecret)
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                .tokenUri(tokenURI)
                .build();

        ReactiveClientRegistrationRepository clientRegistrations =
                new InMemoryReactiveClientRegistrationRepository(clientRegistration);

        ServerOAuth2AuthorizedClientExchangeFilterFunction oauth =
                new ServerOAuth2AuthorizedClientExchangeFilterFunction(
                        new InMemoryReactiveClientRegistrationRepository(clientRegistration),
                        new UnAuthenticatedServerOAuth2AuthorizedClientRepository()
                );
        oauth.setDefaultClientRegistrationId("my-platform");
        return WebClient.builder()
                .filter(oauth)
                .build();
    }

    @Bean
    WebClient webClient2(){
        ClientRegistration clientRegistration = ClientRegistration
                .withRegistrationId("my-platform")
                .clientId(clientID)
                .clientSecret(clientSecret)
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                .tokenUri(tokenURI)
                .build();

        ReactiveClientRegistrationRepository clientRegistrations =
                new InMemoryReactiveClientRegistrationRepository(clientRegistration);


        AuthorizedClientServiceReactiveOAuth2AuthorizedClientManager authorizedClientManager =
                new AuthorizedClientServiceReactiveOAuth2AuthorizedClientManager(
                        new InMemoryReactiveClientRegistrationRepository(clientRegistration),
                        new InMemoryReactiveOAuth2AuthorizedClientService(clientRegistrations));

        ServerOAuth2AuthorizedClientExchangeFilterFunction oauth2 =
                new ServerOAuth2AuthorizedClientExchangeFilterFunction(authorizedClientManager);

        oauth2.setDefaultClientRegistrationId("my-platform");

        return WebClient.builder()
                .baseUrl(baseUrlResource)
                .filter(oauth2)
                .build();

    }

}

