package com.cgi.ordermanagement.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.*;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.*;

@Slf4j
@Configuration
@EnableSwagger2
//@Profile({"test", "dev", "local"})
public class SwaggerConfiguration {

    //    @Value("${app.client.id}")
    private String clientId = "sampleClientId";

    //    @Value("${app.client.secret}")
    private String clientSecret = "secret";

    //    @Value("${info.build.name}")
    private String infoBuildName;

    //    @Value("${host.full.dns.auth.link}")
    private String authLink = "http://localhost:8080";

    @Bean
    public Docket apiDocket1() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("admin")
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.ant("/api/admin/**"))
                .build()
                .apiInfo(getApiInfo())
                .securitySchemes(Collections.singletonList(new ApiKey("Authorization", "Authorization", "header")))
                .useDefaultResponseMessages(false);
    }

    @Bean
    public Docket apiDocket2() {

        List<ResponseMessage> list = new java.util.ArrayList<>();
        list.add(new ResponseMessageBuilder().code(500).message("500 message")
                .responseModel(new ModelRef("Result")).build());
        list.add(new ResponseMessageBuilder().code(401).message("Unauthorized")
                .responseModel(new ModelRef("Result")).build());
        list.add(new ResponseMessageBuilder().code(406).message("Not Acceptable")
                .responseModel(new ModelRef("Result")).build());

        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("orders")
                .tags(new Tag("orders", "orders related"),
                         new Tag("screenMessages", "screenMessages related"))
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.ant("/api/v1/**"))
                .build()
                .securitySchemes(Collections.singletonList(securitySchema()))
                .securityContexts(Collections.singletonList(securityContext()))
                .pathMapping("/")
                .useDefaultResponseMessages(false)
                .apiInfo(getApiInfo())
//                .globalResponseMessage(RequestMethod.GET, list)
//                .globalResponseMessage(RequestMethod.POST, list)
//                .globalResponseMessage(RequestMethod.PATCH, list)
//                .globalResponseMessage(RequestMethod.DELETE, list)
                ;
    }

    private OAuth securitySchema() {

        List<AuthorizationScope> authorizationScopeList = new ArrayList<>();
        authorizationScopeList.add(new AuthorizationScope("read", "read all"));
        authorizationScopeList.add(new AuthorizationScope("trust", "trust all"));
        authorizationScopeList.add(new AuthorizationScope("write", "access all"));

        List<GrantType> grantTypes = new ArrayList<>();
        GrantType creGrant = new ResourceOwnerPasswordCredentialsGrant( "/oauth/token");

        grantTypes.add(creGrant);

        return new OAuth("oauth2schema", authorizationScopeList, grantTypes);

    }

    private SecurityContext securityContext() {
        return SecurityContext.builder().securityReferences(defaultAuth()).forPaths(PathSelectors.ant("/api/v1/**"))
                .build();
    }

    private List<SecurityReference> defaultAuth() {

        final AuthorizationScope[] authorizationScopes = new AuthorizationScope[3];
        authorizationScopes[0] = new AuthorizationScope("read", "read all");
        authorizationScopes[1] = new AuthorizationScope("trust", "trust all");
        authorizationScopes[2] = new AuthorizationScope("write", "write all");

        return Collections.singletonList(new SecurityReference("oauth2schema", authorizationScopes));
    }

    private ApiInfo getApiInfo() {
        return new ApiInfo(
                "PusulaIT API DOCS",
                "PusulaIT API DOCS FOR TESTS",
                "v1.0",
                "Apache 2.0",
                new Contact("Benan Aktaş", "https://www.pusulait.com", "benan.aktas@pusulait.com"),
                "Apache 2.0",
                "Apache 2.0",
                Collections.emptyList()

        );
    }

    @Bean
    public SecurityConfiguration securityInfo() {
        Map<String, Object> addParams = new HashMap<>();
        addParams.put("grant_type", "password");
        addParams.put("scope", "read write");

        return SecurityConfigurationBuilder.builder()
                .clientId(clientId)
                .clientSecret(clientSecret)
                .additionalQueryStringParams(addParams)
                .realm(null)
                .appName(null)
                .scopeSeparator("")
                .build();
    }

    @Bean
    UiConfiguration uiConfig() {
        return UiConfigurationBuilder.builder()
                .deepLinking(true)
                .displayOperationId(false)
                .defaultModelsExpandDepth(1)
                .defaultModelExpandDepth(1)
                .defaultModelRendering(ModelRendering.EXAMPLE)
                .displayRequestDuration(false)
                .docExpansion(DocExpansion.NONE)
                .filter(false)
                .maxDisplayedTags(null)
                .operationsSorter(OperationsSorter.ALPHA)
                .showExtensions(false)
                .tagsSorter(TagsSorter.ALPHA)
                .supportedSubmitMethods(UiConfiguration.Constants.DEFAULT_SUBMIT_METHODS)
                .validatorUrl(null)
                .build();
    }
}