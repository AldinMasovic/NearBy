package com.example.demo.configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.utils.SpringDocUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Configuration
public class OpenApiConfig {
    static {
        var schema = new Schema<LocalTime>();
        schema.example(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"))).type("string");
        SpringDocUtils.getConfig().replaceWithSchema(LocalTime.class, schema);
    }

    @Bean
    public OpenAPI customApi() {
        var securitySchemeName = "bearerAuth";
        var schema = new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT");
        var component = new Components().addSecuritySchemes(securitySchemeName, schema);
        return new OpenAPI()
                .components(component)
                .security(List.of(new SecurityRequirement().addList(securitySchemeName)))
                .info(new Info().title("Aldin Server").version("v1"));
    }
}
