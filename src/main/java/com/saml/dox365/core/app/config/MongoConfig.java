package com.saml.dox365.core.app.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

import lombok.Data;

/**
 * 
 * @author ashish tuteja Mongo Db Connection details
 *
 */
@Configuration
@ConfigurationProperties(prefix = "mongo")
@Data
public class MongoConfig {

	@Value("${spring.data.mongodb.uri}")
	private String mongoUri;

	private String database;

	private String username;

	private String password;

	@Autowired
	private ApplicationContext appContext;

	@Bean
	public MongoClient mongo() {
		return new MongoClient(new MongoClientURI(mongoUri));
	}

	
	@Bean
	public MongoTemplate mongoTemplate() {
		final MongoMappingContext mongoMappingContext = new MongoMappingContext();
	    mongoMappingContext.setApplicationContext(appContext);

	    SimpleMongoDbFactory factory = new SimpleMongoDbFactory(mongo(), database);
	    final MappingMongoConverter converter = new MappingMongoConverter(new DefaultDbRefResolver(factory), mongoMappingContext);
	    
	    converter.setTypeMapper(new DefaultMongoTypeMapper(null));
	    return new MongoTemplate(factory, converter);
	    
		//return new MongoTemplate(mongo(), getDatabase());
	}
}
