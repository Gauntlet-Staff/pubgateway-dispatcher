package com.pubgateway.dispatcher.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.tags.Tag;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("PubGateway Dispatcher API")
                        .version("1.0.0")
                        .description("""
                                Unified dispatcher service for publisher gateway plugins.
                                
                                This service acts as a gateway that dispatches requests to various 
                                publisher-specific services (Google Ads, Meta Ads, etc.) based on 
                                the publisher parameter in the URL.
                                
                                ## Supported Publishers
                                - **GOOGLE**: Google Ads integration
                                - **META**: Meta (Facebook) Ads integration (coming soon)
                                
                                ## Authentication
                                Authentication is handled by the downstream publisher services.
                                
                                ## Service Discovery
                                This service uses Eureka for service discovery and Feign for HTTP communication.
                                """)
                        .contact(new Contact()
                                .name("PubGateway Team")
                                .email("support@pubgateway.com")))
                .servers(List.of(
                        new Server()
                                .url("http://localhost:8082")
                                .description("Local Development Server"),
                        new Server()
                                .url("http://ec2-54-226-128-157.compute-1.amazonaws.com:8082")
                                .description("Production Server (if deployed)")
                ))
                .tags(List.of(
                        new Tag()
                                .name("Accounts")
                                .description("Account management operations for publisher platforms"),
                        new Tag()
                                .name("Campaigns")
                                .description("Campaign management operations for publisher platforms"),
                        new Tag()
                                .name("Groups")
                                .description("Ad group management operations for publisher platforms"),
                        new Tag()
                                .name("Ads")
                                .description("Ad management operations for publisher platforms"),
                        new Tag()
                                .name("Keywords")
                                .description("Keyword (ad group criteria) management operations for Google Ads"),
                        new Tag()
                                .name("Test")
                                .description("Test endpoints for verifying service connectivity")
                ));
    }
}

