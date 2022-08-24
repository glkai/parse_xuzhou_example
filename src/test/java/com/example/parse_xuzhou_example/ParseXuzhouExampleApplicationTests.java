package com.example.parse_xuzhou_example;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.parse_xuzhou_example.utils.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.xmlbeans.impl.piccolo.io.FileFormatException;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
class ParseDemoApplicationTests {

    @Autowired
    private RestHighLevelClient restHighLevelClient;


    @Value("${directory.path}")
    private String doxDirectoryPath;

    @Value("${controller.url}")
    private String url;

    // case_parse_32
    @Value("${elasticsearch.devIndexName}")
    private String devIndexName;

    // case_parse_temp
    @Value("${elasticsearch.myIndexName}")
    private String myIndexName;

    // case_parse_tmp
    @Value("${elasticsearch.myIndexName2}")
    private String myIndexName2;

    // case_parse_3
    @Value("${elasticsearch.myIndexName3}")
    private String myIndexName3;


    @Test
    public void testPost() throws Exception {

        elasticsearchUtil.addIndexWithMappingAndSettings(restHighLevelClient,myIndexName3,ElasticSearchIndexProperties.mappings,ElasticSearchIndexProperties.indexSettingsMap);

    }

    @Test
    public void testB() throws IOException {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        System.out.println(elasticsearchUtil.countDocument(restHighLevelClient, searchSourceBuilder,myIndexName));
    }

    @Test
    public void Test3() throws IOException {
        SimpleDateFormat formatter = new SimpleDateFormat(myFileUtils.DATE_FORMAT);
        List<JSONObject> jsonObjects = myFileUtils.readTxt("C:\\Users\\17566\\Desktop\\徐州案由端口解析结果TXT文档\\侵害商标权纠纷.txt");
        List<JSONObject> list = new ArrayList<>();

        for(JSONObject jsonObject:jsonObjects){
            if("徐州市".equals(jsonObject.getString("city")) && "江苏省".equals(jsonObject.getString("province"))){
                formatter.format(System.currentTimeMillis());
                list.add(jsonObject);
            }
        }
        System.out.println(list.size());
        elasticsearchUtil.bulkAddDoc(restHighLevelClient,myIndexName,list);

    }

    @Test
    public void Test4(){
        String path = "C:\\Users\\17566\\Desktop\\22222\\176-林紫嫣、林宪文等与乔立民、董宜洋等生命权、健康权、身体权纠纷一审民事案67588534.docx";
        String s = myFileUtils.readWord(path);
        JSONObject text = PostOtherController.post(url, new HashMap<>() {
            {
                put("text", s);
            }
        });
        text.put("ids","100");
        elasticsearchUtil.bulkAddDoc(restHighLevelClient,myIndexName3,List.of(text));
    }

    @Test
    public void Test6(){
        String txt = "C:\\Users\\17566\\Desktop\\侵害作品信息网络传播权纠纷.txt";
        Set<String> ids = new HashSet<>();
        List<JSONObject> jsonObjects = myFileUtils.readTxtFiles(txt);
        for(JSONObject object : jsonObjects){
            ids.add(object.getString("causes"));
            System.out.println(object.getString("insertTime"));
        }

        System.out.println(devIndexName);
        elasticsearchUtil.bulkAddDoc(restHighLevelClient,devIndexName,jsonObjects);
        System.out.println(ids);
        System.out.println(jsonObjects.size());
    }

}
