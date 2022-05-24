package com.china.hcg.http.test;

import org.apache.http.HttpConnection;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.net.HttpURLConnection;

/**
 * @autor hecaigui
 * @date 2021-11-12
 * @description
 */
public class HttpClientsTest {
    static String test(String url){
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
            return resultStr;
        } catch (
                IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
