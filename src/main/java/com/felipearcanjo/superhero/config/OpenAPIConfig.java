package com.felipearcanjo.superhero.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenAPIConfig {

    @Value("${felipearc.openapi.dev-url}")
    private String devUrl;

    @Value("${felipearc.openapi.prd-url}")
    private String prodUrl;

    @Bean
    public OpenAPI openApiConfig(){
        Server devServer = new Server();
        devServer.setUrl(devUrl);
        devServer.setDescription("Server URL in Development environment");

        Server prodServer = new Server();
        prodServer.setUrl(prodUrl);
        prodServer.setDescription("Server URL in Production environment");

        Contact contact = new Contact();
        contact.setEmail("tf.arcanjo@gmail.com");
        contact.setName("Felipe Arcanjo");

        Info info = new Info()
                .title("SuperHero API")
                .version("1.0")
                .contact(contact)
                .description("This API exposes endpoints to catalogue superheroes characters");

        return new OpenAPI().info(info).servers(List.of(devServer, prodServer));
    }
}
