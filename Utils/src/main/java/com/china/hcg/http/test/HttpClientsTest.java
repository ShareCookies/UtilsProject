package com.china.hcg.http.test;

import com.china.hcg.http.HttpClientUtil;
import com.china.hcg.spring.SpringEventListener.HelloEvent;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.http.HeaderElement;
import org.apache.http.HttpConnection;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @autor hecaigui
 * @date 2021-11-12
 * @description
 * Java HTTP连接客户端，选 HttpClient 还是 OkHttp ：
 *  https://blog.csdn.net/weixin_42096792/article/details/127656486?spm=1001.2101.3001.6650.4&utm_medium=distribute.pc_relevant.none-task-blog-2%7Edefault%7EYuanLiJiHua%7EPosition-4-127656486-blog-80883189.pc_relevant_recovery_v2&depth_1-utm_source=distribute.pc_relevant.none-task-blog-2%7Edefault%7EYuanLiJiHua%7EPosition-4-127656486-blog-80883189.pc_relevant_recovery_v2&utm_relevant_index=8
 */
@RestController
public class HttpClientsTest {
    public static void main(String[] args) {
        HttpClientsTest.getTest("http://www.baidu.com/");

//        http://localhost:8888/openApi/wflow/getTodoAndToRead

//        Map<String,String> h = new HashMap<>();
//        h.put("Content-Type","application/json");
//        String resultStr= postTest("http://localhost:8888/openApi/getMixUrgerTodo",h,"{\n" +
//                "\t\"stateList\": [\n" +
//                "\t\t\"todo\"\n" +
//                "\t],\n" +
//                "\t\"tokenid\": \"dXNlcm5hbWU9c3dhZG1pbiZkdD0yMDIyMDIxNzE4MDYxNSZjb2RlPWY2ZmY4ZjA0MTgxNzVhYjNlNGYxYTk4NzE2NGJlNTVl\",\n" +
//                "\t\"pageNum\": \"1\",\n" +
//                "\t\"pageSize\": \"100\"\n" +
//                "}");
//        System.err.println(resultStr);
    }
    @PostMapping("/HttpClientsTest")
    public void checkTokenHsf(@RequestBody String body,@RequestParam(value = "url") String url ,@RequestParam Map<String,String> headers) {
        headers.remove("url");
        String resultStr= postTest(url,headers,body);
        System.err.println(resultStr);
    }

    static String getTest(String url){
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet httpPost = new HttpGet(url);

//        StringEntity entity = new StringEntity(json, "utf-8");
//        httpPost.setEntity(entity);
        // httpPost.setHeader("Accept", "application/json");
        //httpPost.setHeader("token", token);
        CloseableHttpResponse response = null;
        try {
            response = client.execute(httpPost);
            org.apache.http.HttpEntity result = response.getEntity();

            String resultStr= EntityUtils.toString(result);
            System.err.println(Arrays.asList(response.getAllHeaders()));
            System.err.println(response.getProtocolVersion());
            System.err.println(resultStr);
            return resultStr;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    static String postTest(String url, Map<String,String> headers,String body){
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);

        if (headers != null){
            for (String s : headers.keySet()) {
                httpPost.setHeader(s, headers.get(s));
            }
        }

        httpPost.setEntity(new StringEntity(body, "UTF-8"));
//        StringEntity entity = new StringEntity(json, "utf-8");
//        httpPost.setEntity(entity);
        // httpPost.setHeader("Accept", "application/json");
        //httpPost.setHeader("token", token);
        CloseableHttpResponse response = null;
        try {
            response = client.execute(httpPost);
            org.apache.http.HttpEntity result = response.getEntity();

            String resultStr= EntityUtils.toString(result);
            System.err.println(Arrays.asList(response.getAllHeaders()));
            System.err.println(response.getProtocolVersion());
            return resultStr;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
