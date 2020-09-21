package com.yincieryi.esapi.manager;

import com.yincieryi.esapi.enums.BusinessError;
import com.yincieryi.esapi.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Description: es服务封装
 * @Author: chen qiang
 * @Date: 2020/9/21 20:08
 */
@Component
public class ElasticSearchManager {

    private static final Logger logger = LoggerFactory.getLogger(ElasticSearchManager.class);

    @Resource
    ElasticsearchRestTemplate restTemplate;

    /**
     * 删除索引
     *
     * @param indexName
     * @return
     * @throws Exception
     */
    public boolean deleteIndex(String indexName) throws BusinessException {
        IndexCoordinates indexCoordinates = IndexCoordinates.of(indexName);
        boolean exists = restTemplate.indexOps(indexCoordinates).exists();
        if (exists) {
            boolean result = restTemplate.indexOps(indexCoordinates).delete();

            if (result == true) {
                logger.info("索引{}删除成功", indexName);
                return true;
            }

            throw new BusinessException(BusinessError.ES_INDEX_DELETE_FAILED.getCode(), BusinessError.ES_INDEX_DELETE_FAILED.getMsg());
        }
        return true;
    }

    /**
     * 创建索引
     * @param indexName
     * @param delete 是否删除已有索引
     * @return
     * @throws Exception
     */
    public boolean createIndex(String indexName, boolean delete) throws BusinessException {
        IndexCoordinates indexCoordinates = IndexCoordinates.of(indexName);
        if(delete){
            //创建前先调用上面的删除索引方法
            deleteIndex(indexName);
        } else if(restTemplate.indexOps(indexCoordinates).exists()){
            throw new BusinessException(BusinessError.ES_INDEX_EXIST.getCode(), BusinessError.ES_INDEX_EXIST.getMsg());
        }

        // 创建索引
        boolean result = restTemplate.indexOps(indexCoordinates).create();

        if (!result){
            throw new BusinessException(BusinessError.ES_INDEX_CREATE_FAILED.getCode(),BusinessError.ES_INDEX_CREATE_FAILED.getMsg());
        }

        return true;
    }

    /**
     * 列表查询
     * @param query
     * @param clazz
     * @param indexName
     * @param <T>
     * @return
     */
    public <T> SearchHits<T> searchList(Query query, Class<T> clazz, String indexName){
        return restTemplate.search(query, clazz, IndexCoordinates.of(indexName));
    }

    public ElasticsearchRestTemplate getRestTemplate(){
        return restTemplate;
    }
}
