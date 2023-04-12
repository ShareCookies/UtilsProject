package com.china.hcg.http.test;

import com.china.hcg.http.HttpClientUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.converter.*;
import org.springframework.http.converter.support.AllEncompassingFormHttpMessageConverter;
import org.springframework.http.converter.xml.SourceHttpMessageConverter;
import org.springframework.web.client.HttpMessageConverterExtractor;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestTemplate;

import javax.xml.transform.Source;
import java.io.Console;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * @autor hecaigui
 * @date 2022-12-20
 * @description
 */
public class testExecute {

    @Autowired
    private TestRestTemplate template;
    public static void main(String[] args) {

        RestTemplate restTemplate = new RestTemplate();



//         final ResponseExtractor<HttpHeaders> headersExtractor = new HeadersExtractor();
          ResponseExtractor<ResponseEntity<String>> headersExtractor = new ResponseEntityResponseExtractor<>(String.class);
        ResponseEntity<String> entity = restTemplate.execute("http://www.baidu.com/", HttpMethod.GET,null,headersExtractor);
        //ResponseEntity<String> entity = restTemplate.getForEntity("http://www.baidu.com/",String.class);


        System.err.println(entity.getBody());
    }
    /**
     * Response extractor that extracts the response {@link HttpHeaders}.
     */
    private static class HeadersExtractor implements ResponseExtractor<HttpHeaders> {

        @Override
        public HttpHeaders extractData(ClientHttpResponse response) throws IOException {
            return response.getHeaders();
        }
    }
    /**
     * Response extractor for {@link HttpEntity}.
     */
    private static class ResponseEntityResponseExtractor<T> implements ResponseExtractor<ResponseEntity<T>> {

        private final HttpMessageConverterExtractor<T> delegate;

        public ResponseEntityResponseExtractor(Type responseType) {
             List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
            messageConverters.add(new ByteArrayHttpMessageConverter());
            messageConverters.add(new StringHttpMessageConverter());
            messageConverters.add(new ResourceHttpMessageConverter());
            messageConverters.add(new SourceHttpMessageConverter<Source>());
            messageConverters.add(new AllEncompassingFormHttpMessageConverter());

            if (responseType != null && Void.class != responseType) {
                this.delegate = new HttpMessageConverterExtractor<T>(responseType,messageConverters);
            }
            else {
                this.delegate = null;
            }
        }
        @Override
        public ResponseEntity<T> extractData(ClientHttpResponse response) throws IOException {
            if (this.delegate != null) {
                T body = this.delegate.extractData(response);
                System.err.println(response.getClass());
                return ResponseEntity.status(response.getRawStatusCode()).headers(response.getHeaders()).body(body);
            }
            else {
                return ResponseEntity.status(response.getRawStatusCode()).headers(response.getHeaders()).build();
            }

        }
    }
}
