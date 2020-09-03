/**
 * 
 */
package com.saml.dox365.core.app.config;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.AuthorizationCodeGrant;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.GrantType;
import springfox.documentation.service.OAuth;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.service.TokenEndpoint;
import springfox.documentation.service.TokenRequestEndpoint;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author vbaveja
 *
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
	
    @Value("${app.version}")
    private String version;
    
    @Value("${app.client.id}")
    private String clientId;
    @Value("${app.client.secret}")
    private String clientSecret;
    
    @Value("${app.version}")
    private String infoBuildName;

    public static final String securitySchemaOAuth2 = "oauth2";
    public static final String authorizationScopeGlobal = "global";
    public static final String authorizationScopeGlobalDesc = "accessEverything";

    /**
     * Create the Swagger details for this api.
     * 
     * @return Docket
     */
    @SuppressWarnings("deprecation")
	@Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(new ApiInfo("Dox365 REST Service for Uploading Document", "Dox365 REST API for Uploading Document to Object Store and Elastic Search", this.version, null, null, null, null))
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.saml.dox365.core.app"))
                .paths(PathSelectors.any())
                .build()
                .genericModelSubstitutes(Callable.class).apiInfo(metaData());
    }
    
    private ApiInfo metaData() {
    	ApiInfo apiInfo = new ApiInfo("Dox365 API","Dox 365 Document Management System", "v0.1", "Terms of Service", "Ashish Tuteja, Varun Baveja", "", "");
    	return apiInfo;
    }
    

	/*
	 * private OAuth securitySchema() {
	 * 
	 * List<AuthorizationScope> authorizationScopeList = new ArrayList();
	 * authorizationScopeList.add(new AuthorizationScope("global", "access all"));
	 * 
	 * List<GrantType> grantTypes = new ArrayList(); final TokenRequestEndpoint
	 * tokenRequestEndpoint = new
	 * TokenRequestEndpoint("http://server:port/oauth/token", clientId,
	 * clientSecret); final TokenEndpoint tokenEndpoint = new
	 * TokenEndpoint("http://server:port/oauth/token", "access_token");
	 * AuthorizationCodeGrant authorizationCodeGrant = new
	 * AuthorizationCodeGrant(tokenRequestEndpoint, tokenEndpoint);
	 * 
	 * grantTypes.add(authorizationCodeGrant);
	 * 
	 * OAuth oAuth = new OAuth("oauth", authorizationScopeList, grantTypes);
	 * 
	 * return oAuth; }
	 * 
	 * 
	 * private SecurityContext securityContext() { return
	 * SecurityContext.builder().securityReferences(defaultAuth())
	 * .forPaths(PathSelectors.ant("/api/**")).build(); }
	 * 
	 * private List<SecurityReference> defaultAuth() {
	 * 
	 * final AuthorizationScope authorizationScope = new
	 * AuthorizationScope(authorizationScopeGlobal, authorizationScopeGlobalDesc);
	 * final AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
	 * authorizationScopes[0] = authorizationScope; ArrayList<SecurityReference> a =
	 * new ArrayList(); a.add(new SecurityReference(securitySchemaOAuth2,
	 * authorizationScopes)); return a; }
	 */
}
