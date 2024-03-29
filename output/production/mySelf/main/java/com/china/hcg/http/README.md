> HttpClientUtil.java 实现了get，post简易封装，基于spring的RestTemplate实现的。

## spring发起http请求：
>	https://www.cnblogs.com/javazhiyin/p/9851775.html
	
###	RestTemplate介绍：
```
    RestTemplate是Spring提供的用于访问Rest服务的客户端.
    RestTemplate提供了多种便捷访问远程Http服务的方法,能够大大提高客户端的编写效率。
```
###	RestTemplate使用：
```	
    RestTemplate具有的方法主要是根据HTTP的六个方法制定的。
    get：
        getForObject()：
            自动将响应封装成对象。
        getForEntity()方法：
            将响应存入ResponseEntity对象，并返回。
            ResponseEntity对象：
                ResponseEntity中包含了HttpStatus和BodyBuilder的信息，这更方便我们处理response原生的东西。
        附：
            getForObject()其实比getForEntity()多包含了将HTTP转成POJO的功能，但是getForObject没有处理response的能力。
            因为它拿到手的就是成型的pojo。省略了很多response的信息。

    post：
        postForObject()：
            
        postForEntity()：
            例子
                private JSONObject  postForObj (String postUrl , JSONObject postObj){
                    RestTemplate restTemplate = new RestTemplate();
                    String remoteHost = ExCommon.getRequestUrl(this.rmsParamDao, "remoteHost");
                    String url = remoteHost+postUrl;
                    HttpHeaders headers = new HttpHeaders();
                    headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
                    HttpEntity request = new HttpEntity<>(postObj, headers);
                    ResponseEntity<String> response = restTemplate.postForEntity( url, request , String.class );
                    System.out.println(response.getBody());
                    JSONObject jsonResult = JSON.parseObject(response.getBody());
                    return jsonResult;
                }
                请求参数格式：
                    post headers{Content-Type：application/json}
                参数为对象：
                    {
                        "fileUrl":"http://192.168.210.171:6051/egovAtt/downloadEgovAttFile?id=Xp1CAeD_o7WsO1N9",
                        "fileName":"417督办类型数据修改",
                        "ext":"doc",
                        "docId":"XpaEO4SuklmVoZFO"
                    }
        附：
            请求参数：
                post请求参数用HttpEntity<MultiValueMap<String, String>> request 封装。
                    MultiValueMap是Map的一个子类，它的一个key可以存储多个value。
                @Nullable Object request类型，request是用HttpEntity来解析，而HttpEntity接受的request类型是MultiValueMap。
    exchange:
        String resultJsonStr = "";
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Content-Type", "application/json;charset=UTF-8");
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(null, requestHeaders);

        ResponseEntity<String> responseEntity=restTemplate.exchange(url, HttpMethod.GET,requestEntity, String.class);
        resultJsonStr=responseEntity.getBody();
    execute:
        
```
```
附：
    postForEntity方法发送带有xml类型的数据：
        https://ask.csdn.net/questions/771239

    Spring RestTemplete支持Https安全请求：
        https://blog.csdn.net/weixin_33835103/article/details/86278376
        https://blog.csdn.net/justry_deng/article/details/82531306#commentBox
        
        附：
        HttpClient 关于 https：	
            https://blog.csdn.net/xiaoxian8023/article/details/49866397
            https://blog.csdn.net/xiaoxian8023/article/details/49865335


    超时设置：
        https://blog.csdn.net/truelove12358/article/details/88838644?utm_medium=distribute.pc_relevant.none-task-blog-BlogCommendFromBaidu-3.control&depth_1-utm_source=distribute.pc_relevant.none-task-blog-BlogCommendFromBaidu-3.control
        http://cn.voidcc.com/question/p-gjlkwpxr-gy.html
	
附：
	1.字符串编码
		URLEncoder.encode("测试.xls", "UTF-8")
	2.爬虫如何解决503错误：
		https://www.zhihu.com/question/34505058
		503表示服务器暂时无法处理某一请求。
		这既有可能是服务器过载导致的，也有可能是服务器屏蔽了你的请求。
		
		RestTemplate 设置 USER-AGENT
			https://blog.csdn.net/weixin_34292287/article/details/91894828
				'MSIE (MSIE 6.0; X11; Linux; i686) Opera 7.23',
				'Opera/9.20 (Macintosh; Intel Mac OS X; U; en)',
				'Opera/9.0 (Macintosh; PPC Mac OS X; U; en)',
				'iTunes/9.0.3 (Macintosh; U; Intel Mac OS X 10_6_2; en-ca)',
				'Mozilla/4.76 [en_jp] (X11; U; SunOS 5.8 sun4u)',
				'iTunes/4.2 (Macintosh; U; PPC Mac OS X 10.2)',
				'Mozilla/5.0 (Macintosh; Intel Mac OS X 10.6; rv:5.0) Gecko/20100101 Firefox/5.0',
				'Mozilla/5.0 (Macintosh; Intel Mac OS X 10.6; rv:9.0) Gecko/20100101 Firefox/9.0',
				'Mozilla/5.0 (Macintosh; Intel Mac OS X 10.8; rv:16.0) Gecko/20120813 Firefox/16.0',
				'Mozilla/4.77 [en] (X11; I; IRIX;64 6.5 IP30)',
				'Mozilla/4.8 [en] (X11; U; SunOS; 5.7 sun4u)'
	3.uri与url
		https://www.cnblogs.com/throwable/p/9740425.html
		https://blog.csdn.net/qq_32595453/article/details/80563142
```

```
HttpClient:
    https://blog.csdn.net/qianyiyiding/article/details/86558140
```
### RestTemplate分析
#### RestTemplate类关系图
![RestTemplate类关系图](http://qiniu.58xuejia.cn/RestTemplate.png)
#### RestTemplate架构分析
```text
1. RestTemplate应用了模板模式，
```