package com.tracejp.starnight.utils;

import com.alibaba.fastjson2.JSON;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p> restHighLevelClient elastic search 工具类 <p/>
 *
 * @author traceJP
 * @since 2023/7/20 11:23
 */
@Component
public class ElasticSearchUtils {

    @Autowired
    public RestHighLevelClient esClient;

    /**
     * 创建索引
     *
     * @param name    索引名
     * @param mapping 映射
     * @return 是否创建成功
     */
    public boolean createIndex(String name, String mapping) {
        CreateIndexRequest request = new CreateIndexRequest(name)
                .mapping(mapping, XContentType.JSON);
        try {
            CreateIndexResponse response = esClient.indices().create(request, RequestOptions.DEFAULT);
            return response.isAcknowledged();
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * 创建文档
     *
     * @param index 索引名
     * @param id    文档id
     * @param value 文档内容
     * @param <T>   文档类型
     * @return 是否创建成功
     */
    public <T> boolean createDocument(String index, String id, T value) {
        String doc = JSON.toJSONString(value);
        IndexRequest request = new IndexRequest().index(index).id(id).source(doc, XContentType.JSON);
        try {
            IndexResponse response = esClient.index(request, RequestOptions.DEFAULT);
            return response.getResult() == DocWriteResponse.Result.CREATED;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 创建文档
     *
     * @see ElasticSearchUtils#createDocument(String, String, Object)
     */
    public <T> boolean createDocument(String index, Long id, T value) {
        return createDocument(index, id.toString(), value);
    }

    /**
     * 更新文档
     *
     * @param index 索引名
     * @param id    文档id
     * @param value 文档内容
     * @param <T>   文档类型
     * @return 是否更新成功
     */
    public <T> boolean updateDocument(String index, String id, T value) {
        String doc = JSON.toJSONString(value);
        UpdateRequest request = new UpdateRequest().index(index).id(id).doc(doc, XContentType.JSON);
        try {
            UpdateResponse response = esClient.update(request, RequestOptions.DEFAULT);
            return response.getResult() == DocWriteResponse.Result.UPDATED;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 更新文档
     *
     * @see ElasticSearchUtils#updateDocument(String, String, Object)
     */
    public <T> boolean updateDocument(String index, Long id, T value) {
        return updateDocument(index, id.toString(), value);
    }

    /**
     * 批量删除文档
     *
     * @param index 索引名
     * @param ids   文档id列表
     * @return 是否删除成功
     */
    public boolean deleteBatchDocument(String index, List<Long> ids) {
        BulkRequest requests = new BulkRequest();
        ids.forEach(id -> requests.add(new DeleteRequest().index(index).id(id.toString())));
        try {
            BulkResponse response = esClient.bulk(requests, RequestOptions.DEFAULT);
            return !response.hasFailures();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 删除文档
     *
     * @param index 索引名
     * @param id    文档id
     * @return 是否删除成功
     */
    public boolean deleteDocument(String index, String id) {
        DeleteRequest request = new DeleteRequest().index(index).id(id);
        try {
            DeleteResponse response = esClient.delete(request, RequestOptions.DEFAULT);
            return response.getResult() == DocWriteResponse.Result.DELETED;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 删除文档
     *
     * @see ElasticSearchUtils#deleteDocument(String, String)
     */
    public boolean deleteDocument(String index, Long id) {
        return deleteDocument(index, id.toString());
    }

    /**
     * 查询文档
     * @param index 索引名
     * @param query 查询条件
     * @param resultClass 结果类型
     * @return 查询结果
     * @param <T> 结果类型
     */
    public <T> List<T> listDocument(String index, SearchSourceBuilder query, Class<T> resultClass) {
        SearchRequest request = new SearchRequest(index);
        request.source(query);
        try {
            SearchResponse response = esClient.search(request, RequestOptions.DEFAULT);
            SearchHit[] hits = response.getHits().getHits();
            if (hits == null || hits.length == 0) {
                return new ArrayList<>();
            }
            return Arrays.stream(hits).map(hit -> JSON.parseObject(hit.getSourceAsString(), resultClass))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

}
