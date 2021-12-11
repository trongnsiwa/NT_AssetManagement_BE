// package com.nashtech.rootkies.config;

// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import springfox.documentation.builders.PathSelectors;
// import springfox.documentation.builders.RequestHandlerSelectors;
// import springfox.documentation.service.*;
// import springfox.documentation.spi.DocumentationType;
// import springfox.documentation.spi.service.contexts.SecurityContext;
// import springfox.documentation.spring.web.plugins.Docket;
// import springfox.documentation.swagger.web.*;

// import java.util.Arrays;
// import java.util.Collections;
// import java.util.List;

// import java.util.Collections;

// @Configuration
// public class SwaggerConfig {

//     public static final String AUTHORIZATION_HEADER = "Authorization";

//     private ApiInfo apiInfo() {
//         return new ApiInfo(
//                 "Shop Service Api",
//                 "Shop service provides apis relate to : product, category, cart, item.",
//                 "v1.0",
//                 "Terms of service",
//                 new Contact("Nguyen Nhan", "www.google.com", "leonguyencm1984@gmail.com"),
//                 "License of API",
//                 "API license URL",
//                 Collections.emptyList());
//     }

//     @Bean
//     public Docket api() {
//         return new Docket(DocumentationType.SWAGGER_2)
//                 .apiInfo(apiInfo())
//                 .select()
//                 .apis(RequestHandlerSelectors.any())
//                 .paths(PathSelectors.any())
//                 .build();
//     }

//     /**
//      * SwaggerUI information
//      */

//     @Bean
//     UiConfiguration uiConfig() {
//         return UiConfigurationBuilder.builder()
//                 .deepLinking(true)
//                 .displayOperationId(false)
//                 .defaultModelsExpandDepth(1)
//                 .defaultModelExpandDepth(1)
//                 .defaultModelRendering(ModelRendering.EXAMPLE)
//                 .displayRequestDuration(false)
//                 .docExpansion(DocExpansion.NONE)
//                 .filter(false)
//                 .maxDisplayedTags(null)
//                 .operationsSorter(OperationsSorter.ALPHA)
//                 .showExtensions(false)
//                 .tagsSorter(TagsSorter.ALPHA)
//                 .supportedSubmitMethods(UiConfiguration.Constants.DEFAULT_SUBMIT_METHODS)
//                 .validatorUrl(null)
//                 .build();
//     }

//     private ApiKey apiKey() {
//         return new ApiKey("JWT", AUTHORIZATION_HEADER, "header");
//     }

//     private SecurityContext securityContext() {
//         return SecurityContext.builder()
//                 .securityReferences(defaultAuth())
//                 .build();
//     }

//     List<SecurityReference> defaultAuth() {
//         AuthorizationScope authorizationScope
//                 = new AuthorizationScope("global", "accessEverything");
//         AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
//         authorizationScopes[0] = authorizationScope;
//         return Arrays.asList(new SecurityReference("JWT", authorizationScopes));
//     }
// }
