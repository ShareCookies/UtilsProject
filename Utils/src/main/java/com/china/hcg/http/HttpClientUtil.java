package com.china.hcg.http;

//import com.alibaba.fastjson.JSONObject;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONObject;
import com.china.hcg.http.utils.HttpUtil;
import com.china.hcg.http.utils.HttpsClientRequestFactory;
import com.china.hcg.http.utils.RestTemplateByteArrayResource;
import com.china.hcg.utils.poi.excel.ExcelReader;
import com.sun.org.apache.bcel.internal.generic.NEW;
import org.apache.log4j.Logger;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

/**
 * @autor hecaigui
 * @date 2020-1-19
 * @description HttpClientUtil.java 实现了get，post简易封装，便于快速发起请求。
 * <p>
 *     HttpClientUtil.java基于spring的RestTemplate实现的。
 * </p>
 */
public class HttpClientUtil {
    private static Logger logger = Logger.getLogger(HttpClientUtil.class);

    private static  RestTemplate rtSimpleFactory(){
        RestTemplate restTemplate = new RestTemplate();
        StringHttpMessageConverterDefaultUtf8Charset(restTemplate);
        return restTemplate;
    }
    /**
     * @description 连接超时、读取超时、
     * @author hecaigui
     * @date 2022-7-26
     * @param
     * @return
     */
    private static  RestTemplate rtHttpsSimpleFactory(){
        HttpsClientRequestFactory httpRequestFactory = new  HttpsClientRequestFactory();
//        connect timeout 是建立连接的超时时间；
//        read timeout， 是传递数据的超时时间。
//        ConnectTimeout只有在网络正常的情况下才有效，而当网络不正常时，ReadTimeout才真正的起作用，?
        httpRequestFactory.setConnectTimeout(2 * 60 * 1000);
        httpRequestFactory.setReadTimeout(10 * 60 * 1000);

        RestTemplate restTemplate = new RestTemplate(httpRequestFactory);
        StringHttpMessageConverterDefaultUtf8Charset(restTemplate);
        return restTemplate;
    }
    /**
     * @description 字符串消息转换器的编码设置为UTF-8
     * 修正返回乱码https://blog.csdn.net/myhAini/article/details/103276726
     */
    private static  void StringHttpMessageConverterDefaultUtf8Charset(RestTemplate restTemplate){
        List<HttpMessageConverter<?>> httpMessageConverters = restTemplate.getMessageConverters();
        httpMessageConverters.stream().forEach(httpMessageConverter -> {
            //字符串消息转换器的编码设置为UTF-8
            if(httpMessageConverter instanceof StringHttpMessageConverter){
                StringHttpMessageConverter messageConverter = (StringHttpMessageConverter) httpMessageConverter;
                messageConverter.setDefaultCharset(Charset.forName("UTF-8"));
            }
        });
    }
    /**
     * @description 发起get请求
     * @author hecaigui
     * @date 2020-8-15
     * @param   url
     * @return
     */
    public static String get(String url){
        RestTemplate restTemplate = HttpClientUtil.rtSimpleFactory();
        ResponseEntity<String> entity = restTemplate.getForEntity(url, String.class);
        HttpStatus statusCode = entity.getStatusCode();
        logger.debug("get请求状态："+statusCode.value());
        String body = entity.getBody();
        logger.debug("get请求结果："+body);
        return body;
    }
    /**
     * @description 发起https类型的get请求
     * @author hecaigui
     * @date 2020-8-15
     * @param   url
     * @return
     */
    public static String getForHttps(String url){
        RestTemplate restTemplate = HttpClientUtil.rtHttpsSimpleFactory();
        ResponseEntity<String> entity = restTemplate.getForEntity(url, String.class);
        HttpStatus statusCode = entity.getStatusCode();
        logger.debug("get请求状态："+statusCode.value());
        String body = entity.getBody();
        logger.debug("get请求结果："+body);
        return body;
    }
    /**
     * @description 发起get请求，该请求中携带了USER_AGENT请求头，来伪装成浏览器
     * @author hecaigui
     * @date 2020-8-15
     * @param   url
     * @return
     */
    public static String getForHttpsAndCookie(String url){
        RestTemplate restTemplate = HttpClientUtil.rtHttpsSimpleFactory();

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.USER_AGENT,"Mozilla/5.0 (Macintosh; Intel Mac OS X 10.6; rv:9.0) Gecko/20100101 Firefox/9.0");
        HttpEntity<String> httpEntity = new HttpEntity<>(null, headers);

        ResponseEntity<String> entity = restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class);

        HttpStatus statusCode = entity.getStatusCode();
//        logger.debug("get请求状态："+statusCode.value());
        String body = entity.getBody();
//        logger.debug("get请求结果："+body);
        return body;
    }
    /**
     * @description 发起get请求，该请求中携带了请求头
     * @author hecaigui
     * @date 2020-8-15
     * @param   url
     * @return
     */
    public static String getWithHeader(String url,HttpHeaders headers){
        RestTemplate restTemplate = HttpClientUtil.rtHttpsSimpleFactory();

//        HttpHeaders headers = new HttpHeaders();
//        headers.set(HttpHeaders.USER_AGENT,"Mozilla/5.0 (Macintosh; Intel Mac OS X 10.6; rv:9.0) Gecko/20100101 Firefox/9.0");
        HttpEntity<String> httpEntity = new HttpEntity<>(null, headers);

        ResponseEntity<String> entity = restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class);

        HttpStatus statusCode = entity.getStatusCode();
        //logger.debug("get请求状态："+statusCode.value());
        String body = entity.getBody();
//        logger.debug("get请求结果："+body);
        return body;
    }
    /**
     * @description 发起json格式的post请求
     * <p>
     *     请求头：application/json;charset=UTF-8
     *     请求体：json字符串
     * </p>
     * @author hecaigui
     * @date 2020/6/13
     * @param  url
     * @param postObj  请求参数
     *                 例：JSONObject postObj = new JSONObject();
     *                 postObj.put("tokenid","11");
     *                 ArrayList toread = new ArrayList<>(); toread.add("toread");postObj.put("stateList",toread);
     * @return
     */
    public static String postOfJson(String url, JSONObject postObj){
        RestTemplate restTemplate = HttpClientUtil.rtSimpleFactory();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity request = new HttpEntity<>(postObj, headers);
        ResponseEntity<String> entity = restTemplate.postForEntity(url,request, String.class);
        HttpStatus statusCode = entity.getStatusCode();
        //logger.debug("post请求状态："+statusCode.value());
        String body = entity.getBody();
        //logger.debug("post请求结果："+body);
        return body;
    }
    /**
     * @description 同上，仅返回多了请求状态。
     * @author hecaigui
     * @date 2020/6/13
     * @param  url
     * @param postObj
     * @return {"status":"请求状态","body":"请求响应内容"}
     */
    public static JSONObject postOfJson2(String url, JSONObject postObj){
        RestTemplate restTemplate = HttpClientUtil.rtSimpleFactory();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity request = new HttpEntity<>(postObj, headers);
        ResponseEntity<String> entity = restTemplate.postForEntity(url,request, String.class);
        HttpStatus statusCode = entity.getStatusCode();
        //logger.debug("post请求状态："+statusCode.value());
        String body = entity.getBody();
        //logger.debug("post请求结果："+body);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status",statusCode.value());
        jsonObject.put("body",body);
        return jsonObject;
    }
    /**
     * @description 发起post请求，数据传输格式为x-www-form-urlencoded，且带有重定向功能
     * <p>
     *     https://blog.csdn.net/u013827143/article/details/86222486
     *     https://www.jianshu.com/p/53b5bd0f1d44
     * </p>
     * @author hecaigui
     * @date 2020/6/13
     * @param  url
     * @param requestMap 请求参数  MultiValueMap<String, String> requestMap = new LinkedMultiValueMap<>();
     * @return
     */
    public static String postOfFormUrlencode(String url, MultiValueMap<String, String> requestMap){
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity request = new HttpEntity<>(requestMap, headers);
        ResponseEntity<String> entity = restTemplate.postForEntity(url,request, String.class);
        HttpStatus statusCode = entity.getStatusCode();
        logger.debug("post请求状态："+statusCode.value());
        String body = entity.getBody();
        // 网页重定向自动跳转
        if (statusCode.is3xxRedirection()){
            String url2 = HttpUtil.getPrefix(url)+entity.getHeaders().getLocation();
            body = HttpClientUtil.get(url2);
        }
        logger.debug("post请求结果："+body);

        return body;
    }
    /**
     * @description restTemplate以post形式传输文件 。
     * <p>
     *     文件接收案例:./attachs/
     * </p>
     * @author hecaigui
     * @date 2020-8-15
     * @param  postUrl 必须参数 POST请求地址
     * @param bytes 文件字节
     * @param fileName 文件名，可以为空但要传
     * @param otherHeadParams 其他参数
     * @return
     */
    public static String postOfFile(String postUrl, byte[] bytes, String fileName, Map<String,Object> otherHeadParams){
        RestTemplate restTemplate =  new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("multipart/form-data");
        headers.setContentType(type);

        MultiValueMap<String, Object> form = new LinkedMultiValueMap<>();
        ByteArrayResource arrayResource = new RestTemplateByteArrayResource(bytes,fileName);
        form.add("multipartFile", arrayResource);
        for (String key : otherHeadParams.keySet()){
            form.add(key,otherHeadParams.get(key));
        }

        HttpEntity request = new HttpEntity<>(form, headers);
        ResponseEntity<String> entity = restTemplate.postForEntity(postUrl,request, String.class);
        HttpStatus statusCode = entity.getStatusCode();
        logger.debug("post请求状态："+statusCode.value());
        String body = entity.getBody();
        logger.debug("post请求结果："+body);
        return body;
    }
    public static String post(String url,HttpHeaders headers, Object requestBody){
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity request = new HttpEntity<>(requestBody, headers);
        ResponseEntity<String> entity = restTemplate.postForEntity(url,request, String.class);
        HttpStatus statusCode = entity.getStatusCode();
        logger.debug("post请求状态："+statusCode.value());
        String body = entity.getBody();
        // 网页重定向自动跳转
        if (statusCode.is3xxRedirection()){
            String url2 = HttpUtil.getPrefix(url)+entity.getHeaders().getLocation();
            body = HttpClientUtil.get(url2);
        }
        logger.debug("post请求结果："+body);

        return body;
    }
    /**
     * @description 发起post,get,put,delete请求，数据传输格式为x-www-form-urlencoded

     * @author hecaigui
     * @date 2020/6/13
     * @param  url
     * @param requestMap 请求参数  MultiValueMap<String, String> requestMap = new LinkedMultiValueMap<>();
     * @return String 字符串结果
     */
    public static String exchangeOfFormUrlencode(HttpMethod httpMethod,String url, MultiValueMap<String, String> requestMap){
        RestTemplate restTemplate = new RestTemplate();

        String resultJsonStr = "";
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
// 这样也行啊。什么样的接收请求头方式可以？为什么？，resttemplate原理是什么了？
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("clientId","11");
//        HttpEntity<String> requestEntity = new HttpEntity<>(jsonObject.toJSONString(), requestHeaders);

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(requestMap, requestHeaders);
        ResponseEntity<String> responseEntity=restTemplate.exchange(url, httpMethod,requestEntity, String.class);


        HttpStatus statusCode = responseEntity.getStatusCode();
        logger.debug("请求状态："+statusCode.value());
        String body = responseEntity.getBody();
        logger.debug("请求结果："+body);
        return body;
    }
    public static void main(String[] args){
        HttpClientUtil.get("https://www.amazon.com/");
        //HttpClientUtil.getForHttps("https://www.amazon.com/robots.txt");
//        HttpClientUtil.getForHttpsAndCookie("https://www.amazon.com/");
        //HttpClientUtil.getForHttps("https://tcc.taobao.com/cc/json/mobile_tel_segment.htm?tel=15180411152");
//        MultiValueMap<String, String> requestMap = new LinkedMultiValueMap<>();
//        requestMap.set("test","testt11");
//        HttpClientUtil.exchangeOfFormUrlencode(HttpMethod.POST,"http://localhost:8888/openApi/wflow/getTodoAndToRead",requestMap);

        double m = 2573550 / 1024.0 / 1024.0;
        MultiValueMap<String, String> requestMap = new LinkedMultiValueMap<>();
    }
}
// 以下为一些resttemplate原生demo
//exchange:
//        String resultJsonStr = "";
//        HttpHeaders requestHeaders = new HttpHeaders();
//        requestHeaders.add("Content-Type", "application/json;charset=UTF-8");
//        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(null, requestHeaders);
//
//        ResponseEntity<String> responseEntity=restTemplate.exchange(url, HttpMethod.GET,requestEntity, String.class);
//        resultJsonStr=responseEntity.getBody();
//get:
//        RestTemplate restTemplate = new RestTemplate();
//        ResponseEntity<String> entity = restTemplate.getForEntity("http://www.baidu.com/s?ie=UTF-8&wd=1", String.class);
//        HttpStatus statusCode = entity.getStatusCode();
//        logger.debug("statusCode.is2xxSuccessful()"+statusCode.is2xxSuccessful());
//        String body = entity.getBody();
//        logger.debug("entity.getBody()"+body);
//post：
//		private JSONObject  postForObj (String postUrl , JSONObject postObj){
//			RestTemplate restTemplate = new RestTemplate();
//			String remoteHost = ExCommon.getRequestUrl(this.rmsParamDao, "remoteHost");
//			String url = remoteHost+postUrl;
//			HttpHeaders headers = new HttpHeaders();
//			headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
//			HttpEntity request = new HttpEntity<>(postObj, headers);
//			ResponseEntity<String> response = restTemplate.postForEntity( url, request , String.class );
//			logger.debug(response.getBody());
//			JSONObject jsonResult = JSON.parseObject(response.getBody());
//			return jsonResult;
//		}
//		请求参数格式：
//		post headers{Content-Type：application/json}
//		参数为对象：
//			{
//				"fileUrl":"http://192.168.210.171:6051/egovAtt/downloadEgovAttFile?id=Xp1CAeD_o7WsO1N9",
//				"fileName":"417督办类型数据修改",
//				"ext":"doc",
//				"docId":"XpaEO4SuklmVoZFO"
//			}