package com.saml.dox365.core.app.util;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.sniff.SniffOnFailureListener;
import org.elasticsearch.client.sniff.Sniffer;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.saml.dox365.core.app.config.ESProperties;

/**
 * 
 * @author ashish tuteja ESConnector connect to Elastic search This is generic
 *         class which will connect to any ES irrespective of any cloud
 */

@Component
public class ESConnector {

	Logger logger = Logger.getLogger(ESConnector.class);

	private final ESProperties esProperties;

	@Autowired
	public ESConnector(ESProperties esProperties) {
		this.esProperties = esProperties;
	}
	
	@Bean
	public RestHighLevelClient getESClient() {

		CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
		if (esProperties.isCheckCredentials()) {

			credentialsProvider.setCredentials(AuthScope.ANY,
					new UsernamePasswordCredentials(esProperties.getUserName(), esProperties.getPassword()));
		}

			SniffOnFailureListener snifferListener = new SniffOnFailureListener();
			RestHighLevelClient restHighLevelClient = new RestHighLevelClient(
					RestClient.builder(new HttpHost(esProperties.getHost(), esProperties.getPort(), esProperties.getProtocol()))
							.setRequestConfigCallback(new RestClientBuilder.RequestConfigCallback() {

								@Override
								public RequestConfig.Builder customizeRequestConfig(
										RequestConfig.Builder requestConfigBuilder) {
									return requestConfigBuilder.setConnectTimeout(esProperties.getConnectionTimeout())
											.setSocketTimeout(esProperties.getSocketTimeout());
								}
							}).setMaxRetryTimeoutMillis(esProperties.getRetryTimeout())
							.setHttpClientConfigCallback((HttpAsyncClientBuilder asyncBuilder) -> asyncBuilder
									.setDefaultCredentialsProvider(credentialsProvider))
							.setFailureListener(snifferListener));
			Sniffer sniffer = Sniffer.builder(restHighLevelClient.getLowLevelClient()).build();
			snifferListener.setSniffer(sniffer);
			
		return restHighLevelClient;
	}
}
