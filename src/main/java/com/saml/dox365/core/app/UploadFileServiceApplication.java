package com.saml.dox365.core.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.saml.dox365.core.app.config.S3Properties;
import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@ComponentScan({"com.saml.dox365.*"})
@EnableEncryptableProperties
@EnableAsync
@EnableCaching
@EnableConfigurationProperties(S3Properties.class)
@EnableSwagger2
@EnableScheduling
public class UploadFileServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UploadFileServiceApplication.class, args);
	}

}
