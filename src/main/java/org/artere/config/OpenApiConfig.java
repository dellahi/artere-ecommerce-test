package org.artere.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI artereOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Artere Test API")
                        .description("REST API for managing categories and products with hierarchical structure")
                        .version("1.0.0"));
    }
}

