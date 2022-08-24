package com.example.parse_xuzhou_example.utils;


import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.ElasticsearchStatusException;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.IndicesClient;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.core.CountRequest;
import org.elasticsearch.client.core.CountResponse;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.GetIndexResponse;
import org.elasticsearch.cluster.metadata.MappingMetadata;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.xcontent.XContentType;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author GaoLiuKai
 * @date 2022/8/5 11:15
 */
@Slf4j
public class elasticsearchUtil {

    /**
     * 创建索引，无映射
     */
    public static void createIndex(RestHighLevelClient client,String index){
        try {
            if (existIndex(client, index)) {
                log.info("索引已经存在，不能重复创建");
                return;
            }
            CreateIndexRequest request = new CreateIndexRequest(index);
            CreateIndexResponse createIndexResponse = client.indices().create(request, RequestOptions.DEFAULT);
            if (createIndexResponse.isAcknowledged()) {
                log.info(new HashMap<String, String>() {
                    {
                        put("acknowledged", "true");
                    }
                }.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 向索引中添加映射
     */
    public static void putMapping(RestHighLevelClient client,String index,String mappings){
        try {
            if(elasticsearchUtil.existIndex(client,index)){
                log.info(index+" 索引不存在");
            }else{
                PutMappingRequest request = new PutMappingRequest(index);
                request.type("_doc").source(mappings,XContentType.JSON);
                AcknowledgedResponse putMapping = client.indices().putMapping(request, RequestOptions.DEFAULT);
                if(putMapping.isAcknowledged()){
                    log.info(new HashMap<String, String>() {
                        {
                            put("acknowledged", "true");
                        }
                    }.toString());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * 添加索引，并添加映射和设置
     */
    public static void addIndexWithMappingAndSettings(RestHighLevelClient client,String indexName,String mapping,Map<String, Object> settings) throws Exception {
        IndicesClient indices = client.indices();
        CreateIndexRequest createIndexRequest = new CreateIndexRequest(indexName);

        log.info("开始初始化索引......");
        createIndexRequest.settings(settings);
        createIndexRequest.mapping(mapping, XContentType.JSON);
        log.info("索引初始化完毕");

        try {
            CreateIndexResponse createIndexResponse = indices.create(createIndexRequest, RequestOptions.DEFAULT);
            if (createIndexResponse.isAcknowledged()) {
                log.info(new HashMap<String, String>() {
                    {
                        put("acknowledged", "true");
                    }
                }.toString());
            }
        } catch (ElasticsearchStatusException e) {
            log.error(e.getMessage());
        }
    }

    /**
     * 查询索引
     */
    public static  void queryIndex(RestHighLevelClient client,String indexName) throws Exception {
        IndicesClient indices = client.indices();
        GetIndexRequest getRequest=new GetIndexRequest(indexName);
        GetIndexResponse response = indices.get(getRequest, RequestOptions.DEFAULT);
        Map<String,MappingMetadata> mappings = response.getMappings();
        for (String key : mappings.keySet()) {
            System.out.println(key+"==="+mappings.get(key).getSourceAsMap());
        }
    }

    /**
     * 索引是否存在
     */
    public static boolean existIndex(RestHighLevelClient client,String indexName) throws IOException {
        IndicesClient indices = client.indices();
        GetIndexRequest getIndexRequest=new GetIndexRequest(indexName);
        return indices.exists(getIndexRequest, RequestOptions.DEFAULT);
    }

    /**
     * 添加一条文档
     */
    public static void addDoc(RestHighLevelClient client, String indexName, JSONObject document)  {
        IndexRequest request= new IndexRequest(indexName).source(document);
        try {
            client.index(request, RequestOptions.DEFAULT);
            log.info("文档添加成功");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 批量添加文档,设置 id 为文档中的 uniqueId
     */
    public static void bulkAddDoc(RestHighLevelClient client, String indexName, List<JSONObject> docs){

        BulkRequest bulkRequest = new BulkRequest(indexName);
        for(JSONObject doc : docs){
            bulkRequest.add(new IndexRequest(indexName).source(doc).id(doc.getString("uniqueId")));
        }
        try {
            client.bulk(bulkRequest, RequestOptions.DEFAULT);
            log.info("向elasticsearch索引："+indexName+" 共添加 "+docs.size()+" 条文档数据");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * 统计数据数量
     */
    public static long countDocument(RestHighLevelClient client,SearchSourceBuilder searchSourceBuilder, String index) throws IOException {
        CountRequest countRequest = new CountRequest(index);

//        BoolQueryBuilder builder = new BoolQueryBuilder();
//        builder.filter(QueryBuilders.termQuery("province","江苏省"));
//        builder.filter(QueryBuilders.termQuery("city","徐州市"));
////        builder.filter(QueryBuilders.termQuery(myFileUtils.CAUSES_IS_MATCHED,true));
////        builder.filter(QueryBuilders.termQuery(myFileUtils.IS_INSERTED_DEV_ES,true));
//        builder.filter(QueryBuilders.termQuery("causes","抚养费纠纷"));
//        searchSourceBuilder.query(builder);
        countRequest.source(searchSourceBuilder);
        CountResponse countResponse = client.count(countRequest, RequestOptions.DEFAULT);
        return countResponse.getCount();
    }

    public static void main(String[] args) {

    }

}


