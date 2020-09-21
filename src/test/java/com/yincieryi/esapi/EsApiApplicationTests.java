package com.yincieryi.esapi;

import com.alibaba.fastjson.JSON;
import com.yincieryi.esapi.pojo.User;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.AsyncSearchClient;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateDataStreamRequest;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.GetIndexResponse;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

@SpringBootTest
class EsApiApplicationTests {
	@Autowired
	private RestHighLevelClient restHighLevelClient;
	
	@Test
	void contextLoads() {
		AsyncSearchClient asyncSearchClient = restHighLevelClient.asyncSearch();
	}
	
	//索引的创建
	@Test
	void testCreateIndex() throws IOException {
		//创建索引请求
		CreateIndexRequest index = new CreateIndexRequest("zqh_index");
		//执行请求
		CreateIndexResponse createIndexResponse = restHighLevelClient.indices().create(index, RequestOptions.DEFAULT);
		//请求后获得响应
		System.out.println(createIndexResponse);
	}
	
	//索引的获取
	@Test
	void testGetIndex() throws IOException {
		//创建索引请求
		GetIndexRequest index = new GetIndexRequest("zqh_index");
		//执行请求
		boolean exists = restHighLevelClient.indices().exists(index, RequestOptions.DEFAULT);
		//请求后获得响应
		System.out.println(exists);
	}
	
	
	//索引的删除
	@Test
	void testDeleteIndex() throws IOException {
		//创建索引请求
		DeleteIndexRequest index = new DeleteIndexRequest("zqh_index");
		//执行请求
		AcknowledgedResponse delete = restHighLevelClient.indices().delete(index, RequestOptions.DEFAULT);
		//请求后获得响应
		System.out.println(delete.isAcknowledged());
	}
	
	//判断文档是否存在
	@Test
	void testExistDocument() throws IOException {
		GetRequest getRequest = new GetRequest("zqh_index", "1");
		//不获取_source的上下文
		getRequest.fetchSourceContext(new FetchSourceContext(false));
		getRequest.storedFields("_name_");
		
		boolean exists = restHighLevelClient.exists(getRequest, RequestOptions.DEFAULT);
		System.out.println(exists);
	}
	
	//获取文档是否存在
	@Test
	void testGetDocument() throws IOException {
		GetRequest getRequest = new GetRequest("zqh_index", "1");
		//不获取_source的上下文
//		getRequest.fetchSourceContext(new FetchSourceContext(false));
//		getRequest.storedFields("_name_");
		
		GetResponse documentFields = restHighLevelClient.get(getRequest, RequestOptions.DEFAULT);
		System.out.println(documentFields.getSourceAsString());
		System.out.println(documentFields);
		
		
	}
	
	//添加文档
	@Test
	void testAddDocument() throws IOException {
		//创建对象
		User user = new User("zqh", 18);
		//创建请求
		IndexRequest index = new IndexRequest("zqh_index");
		index.id("1");
		index.timeout(TimeValue.timeValueSeconds(1));
		
		//将数据放入请求
		index.source(JSON.toJSONString(user), XContentType.JSON);
		
		//客户端发送请求
		IndexResponse response = restHighLevelClient.index(index, RequestOptions.DEFAULT);
		System.out.println(response.toString());
		System.out.println(response.status());
		
	}
	
	//更新文档
	@Test
	void testUpdateDocument() throws IOException {
		//创建对象
		User user = new User("zqhzs", 18);
		//创建请求
		UpdateRequest updateRequest = new UpdateRequest("zqh_index", "1");
		updateRequest.timeout("1s");
		
		//将数据放入请求
		updateRequest.doc(JSON.toJSONString(user), XContentType.JSON);
		
		//客户端发送请求
		UpdateResponse update = restHighLevelClient.update(updateRequest, RequestOptions.DEFAULT);
		System.out.println(update.toString());
		System.out.println(update.status());
		
	}
	
	//删除文档
	@Test
	void testDeleteDocument() throws IOException {
		//创建请求
		DeleteRequest updateRequest = new DeleteRequest("zqh_index", "1");
		
		//客户端发送请求
		DeleteResponse response = restHighLevelClient.delete(updateRequest, RequestOptions.DEFAULT);
		System.out.println(response.toString());
		System.out.println(response.status());
	}
	
	//大批量导入
	@Test
	void testAddAllDocument() throws IOException {
		BulkRequest bulkRequest = new BulkRequest();
		bulkRequest.timeout("10s");
		for (int i = 0; i < 20; i++) {
			User user = new User("zqhzs", i);
			bulkRequest.add(
					new IndexRequest("zqh_index")
							.id(i + "")
							.source(JSON.toJSONString(user), XContentType.JSON)
			);
		}
		
		BulkResponse bulk = restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
		System.out.println(bulk.status());
		
	}
	
	//批量查询
	@Test
	void testGetAllDocument() throws IOException {
		SearchRequest searchRequest = new SearchRequest();
		
		//构建搜索条件
		SearchSourceBuilder builder = new SearchSourceBuilder();
		
		//使用QueryBuilders快速匹配
		//termQuery精确查询
		//matchAllQuery匹配所有，其他api自己再去看
		TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("name", "zqhzs");
		//QueryBuilders.matchAllQuery();
		
		builder.query(termQueryBuilder);
		builder.from(1);
		builder.size(2);
		builder.timeout(new TimeValue(60, TimeUnit.SECONDS));
		
		searchRequest.source(builder);
		
		SearchResponse search = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
		
		System.out.println(JSON.toJSONString(search.getHits()));
	}
	
}
