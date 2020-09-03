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
 * 
 * @author ashish tuteja
 *
 */

@Configuration
@ConfigurationProperties(prefix="flow")
@Data
public class FlowProperties {

    private String s3DeleteMaxCount;
    
    private String templateDownloadLocation;
    
    private String templateDownloadFormat;
}