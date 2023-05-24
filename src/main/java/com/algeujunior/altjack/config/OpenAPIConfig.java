package com.algeujunior.altjack.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI myOpenAPI() {


        Info info = new Info()
                .title("Altjack Game API")
                .version("1.0")
                .description("An API to play Blackjack Game.");

        return new OpenAPI().info(info);
    }
}
