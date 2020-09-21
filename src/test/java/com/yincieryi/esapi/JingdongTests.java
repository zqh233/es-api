package com.yincieryi.esapi;

import com.alibaba.fastjson.JSON;
import com.yincieryi.esapi.pojo.PojoContext;
import com.yincieryi.esapi.pojo.User;
import com.yincieryi.esapi.utils.HtmlPraseUtils;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;

@SpringBootTest
public class JingdongTests {
	@Autowired
	private RestHighLevelClient restHighLevelClient;
	@Test
	void testAddAllDocument() throws IOException {
		BulkRequest bulkRequest = new BulkRequest();
		bulkRequest.timeout("10s");
		for(int x=1;x<50;x++) {
			
			List<PojoContext> list = HtmlPraseUtils.parseId("java&page="+x);
			for (int i = 0; i < list.size(); i++) {
				int num = i+x*50;
				bulkRequest.add(
						new IndexRequest("jingdong")
								.id(num + "")
								.source(JSON.toJSONString(list.get(i)), XContentType.JSON)
				);
			}
			
			BulkResponse bulk = restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
			System.out.println(bulk.status());
		}
		
		for(int x=1;x<50;x++) {
			
			List<PojoContext> list = HtmlPraseUtils.parseId("vue&page="+x);
			for (int i = 0; i < list.size(); i++) {
				int num = i+x*50 + 2500;
				bulkRequest.add(
						new IndexRequest("jingdong")
								.id(num + "")
								.source(JSON.toJSONString(list.get(i)), XContentType.JSON)
				);
			}
			
			BulkResponse bulk = restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
			System.out.println(bulk.status());
		}
		
		for(int x=1;x<50;x++) {
			
			List<PojoContext> list = HtmlPraseUtils.parseId("C++&page="+x);
			for (int i = 0; i < list.size(); i++) {
				int num = i+x*50 + 5000;
				bulkRequest.add(
						new IndexRequest("jingdong")
								.id(num + "")
								.source(JSON.toJSONString(list.get(i)), XContentType.JSON)
				);
			}
			
			BulkResponse bulk = restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
			System.out.println(bulk.status());
		}
		
		for(int x=1;x<50;x++) {
			
			List<PojoContext> list = HtmlPraseUtils.parseId("衣服&page="+x);
			for (int i = 0; i < list.size(); i++) {
				int num = i+x*50 + 7500;
				bulkRequest.add(
						new IndexRequest("jingdong")
								.id(num + "")
								.source(JSON.toJSONString(list.get(i)), XContentType.JSON)
				);
			}
			
			BulkResponse bulk = restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
			System.out.println(bulk.status());
		}
		
		for(int x=1;x<50;x++) {
			
			List<PojoContext> list = HtmlPraseUtils.parseId("裤子&page="+x);
			for (int i = 0; i < list.size(); i++) {
				int num = i+x*50 + 10000;
				bulkRequest.add(
						new IndexRequest("jingdong")
								.id(num + "")
								.source(JSON.toJSONString(list.get(i)), XContentType.JSON)
				);
			}
			
			BulkResponse bulk = restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
			System.out.println(bulk.status());
		}
		
	}
}
