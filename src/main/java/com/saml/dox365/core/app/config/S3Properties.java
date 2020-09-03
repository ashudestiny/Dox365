package com.saml.dox365.core.app.config;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by vbaveja.
 */

@Configuration
@ConfigurationProperties(prefix="s3")
@Data
public class S3Properties {

    private String accessKeyId;
    
    private String accessSecretKey;
    
    private String bucketName;
    
    private String s3EndPoint;
    
    private String region;
    
    private String rootFolder;
  
    
}