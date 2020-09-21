package com.yincieryi.esapi.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//spring注入bean
@Configuration
public class ElasticSearchClientConfig {

	@Bean
	public RestHighLevelClient restHighLevelClient(){
		RestHighLevelClient restHighLevelClient = new RestHighLevelClient(
				RestClient.builder(new HttpHost("119.45.199.180", 9200, "http"))
		);
		return restHighLevelClient;
	}
}
