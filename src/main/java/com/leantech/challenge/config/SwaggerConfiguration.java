package com.leantech.challenge.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.springdoc.core.Constants.SPRINGDOC_SWAGGER_UI_ENABLED;

@Configuration
@ConditionalOnProperty(value = SPRINGDOC_SWAGGER_UI_ENABLED, matchIfMissing = true)
public class SwaggerConfiguration {

    @Value("${api.swagger.version}")
    private String swaggerVersion;

    @Bean
    public OpenAPI configureOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .version(swaggerVersion)
                        .title("Lean Tech Challenge API's")
                        .description("Lean Tech Challenge API's"));
    }

}