package br.com.letscode.starwarsnetwork.adapters.http;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Star Wars Resistence Social Network")
                .description("Controle dos rebeldes")
                .license("MIT")
                .licenseUrl("https://raw.githubusercontent.com/git/git-scm.com/main/MIT-LICENSE.txt")
                .termsOfServiceUrl("")
                .version("1.0.0")
                .contact(new Contact("Oliveira, Gabriel", "https://linkedin.com/in/gabrielbo1", "barbosa.olivera1@gmail.com"))
                .build();
    }


    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());
    }
}
