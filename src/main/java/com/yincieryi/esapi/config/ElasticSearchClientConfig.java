package com.yincieryi.esapi.config;

import java.time.Duration;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;

//spring注入bean
@Configuration
public class ElasticSearchClientConfig {
	@Value("${es.address}")
	private String hostAndPort;

	@Value("${es.socketTimeout}")
	private long socketTimeout;

	@Bean
	public RestHighLevelClient restHighLevelClient(){

		final ClientConfiguration clientConfiguration = ClientConfiguration.builder()
				.connectedTo(hostAndPort)
				.withSocketTimeout(Duration.ofSeconds(socketTimeout))
				.build();
		return RestClients.create(clientConfiguration).rest();

//		RestHighLevelClient restHighLevelClient = new RestHighLevelClient(
//				RestClient.builder(new HttpHost("119.45.199.180", 9200, "http"))
//		);
//		return restHighLevelClient;
	}

	@Bean
	public ElasticsearchRestTemplate restTemplate(){
		return new ElasticsearchRestTemplate(restHighLevelClient());
	}
}
