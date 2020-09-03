package com.saml.dox365.core.app.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

/**
 * Created by ashish tuteja
 * Connection details for Elastic search
 */

@Configuration
@ConfigurationProperties(prefix="es")
@Data
public class ESProperties {

    private String host;
    
    private String userName;
    
    private String password;
    
    private int port;
    
    private String protocol;
    
    private String rootFolder;
    
    private int connectionTimeout;
    
    private int socketTimeout;
    
    private int retryTimeout;
    
    private boolean checkCredentials;
  
    
}