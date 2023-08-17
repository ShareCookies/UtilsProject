package com.china.hcg.applications.chao_gu;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.china.hcg.http.HttpClientUtil;
import com.china.hcg.io.file.FileUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.io.File;

/**
 * @autor hecaigui
 * @date 2023/8/11
 * @description
 */
public class OthersData {
    public static void main(String[] args) {
        outLt();
    }
    /**
     * @description 获取上证指数
     */
    public static void getSZZS(){
        String r = HttpClientUtil.get("https://finance.pae.baidu.com/selfselect/getstockquotation?all=1&code=000001&isIndex=true&isBk=false&isBlock=false&isFutures=false&isStock=false&newFormat=1&market_type=ab&group=quotation_index_fiveday&finClientType=pc");
        JSONObject rj = JSONObject.parseObject(r);
        JSONObject minute_data_price = rj.getJSONObject("Result");
        JSONArray leastDay = minute_data_price.getJSONArray("fivedays").getJSONObject(4).getJSONArray("priceinfos");
        for (Object o : leastDay) {
            JSONObject jsonObject = (JSONObject) o;
            System.err.println(jsonObject);
        }
    }
    /**
     * @description 龙头导出
     */
    public static void outLt(){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        String body = "";
        JSONArray allData = new JSONArray();
        for (int i = 1; i <= 41; i++) {
            String page = String.valueOf(i);
//            System.err.println(i);
            body = "query=%E9%9D%9E%E7%A7%91%E5%88%9B%EF%BC%8C%E9%9D%9E%E5%88%9B%E4%B8%9A%EF%BC%8C%E9%9D%9E%E5%8C%97%E4%BA%A4%2C%E9%9D%9E%E6%B8%AF%E8%82%A1%E9%80%9A%EF%BC%8C%E9%9D%9EST%EF%BC%8C%E8%B5%84%E9%87%91%E6%B5%81%E5%85%A5%EF%BC%8C%E8%B5%84%E9%87%91%E6%B5%81%E5%85%A5&urp_sort_way=asc&urp_sort_index=a%E8%82%A1%E5%B8%82%E5%80%BC%28%E4%B8%8D%E5%90%AB%E9%99%90%E5%94%AE%E8%82%A1%29%5B20230817%5D&page=2&perpage=50&addheaderindexes=&condition=%5B%7B%22chunkedResult%22%3A%22%E9%9D%9E%E7%A7%91%E5%88%9B%2C_%26_%E9%9D%9E%E5%88%9B%E4%B8%9A%2C_%26_%E9%9D%9E%E5%8C%97%E4%BA%A4%2C_%26_%E9%9D%9E%E6%B8%AF%E8%82%A1%E9%80%9A%2C_%26_%E9%9D%9Est%2C_%26_%E8%B5%84%E9%87%91%E6%B5%81%E5%85%A5%2C_%26_%E8%B5%84%E9%87%91%E6%B5%81%E5%85%A5%22%2C%22opName%22%3A%22and%22%2C%22opProperty%22%3A%22%22%2C%22sonSize%22%3A12%2C%22relatedSize%22%3A0%7D%2C%7B%22indexName%22%3A%22%E4%B8%8A%E5%B8%82%E6%9D%BF%E5%9D%97%22%2C%22indexProperties%22%3A%5B%22%E4%B8%8D%E5%8C%85%E5%90%AB%E7%A7%91%E5%88%9B%22%5D%2C%22source%22%3A%22new_parser%22%2C%22type%22%3A%22index%22%2C%22indexPropertiesMap%22%3A%7B%22%E4%B8%8D%E5%8C%85%E5%90%AB%22%3A%22%E7%A7%91%E5%88%9B%22%7D%2C%22reportType%22%3A%22null%22%2C%22valueType%22%3A%22_%E4%B8%8A%E5%B8%82%E6%9D%BF%E5%9D%97%22%2C%22domain%22%3A%22abs_%E8%82%A1%E7%A5%A8%E9%A2%86%E5%9F%9F%22%2C%22uiText%22%3A%22%E4%B8%8A%E5%B8%82%E6%9D%BF%E5%9D%97%E4%B8%8D%E5%8C%85%E5%90%AB%E7%A7%91%E5%88%9B%22%2C%22sonSize%22%3A0%2C%22queryText%22%3A%22%E4%B8%8A%E5%B8%82%E6%9D%BF%E5%9D%97%E4%B8%8D%E5%8C%85%E5%90%AB%E7%A7%91%E5%88%9B%22%2C%22relatedSize%22%3A0%2C%22tag%22%3A%22%E4%B8%8A%E5%B8%82%E6%9D%BF%E5%9D%97%22%7D%2C%7B%22opName%22%3A%22and%22%2C%22opProperty%22%3A%22%22%2C%22sonSize%22%3A10%2C%22relatedSize%22%3A0%7D%2C%7B%22indexName%22%3A%22%E4%B8%8A%E5%B8%82%E6%9D%BF%E5%9D%97%22%2C%22indexProperties%22%3A%5B%22%E4%B8%8D%E5%8C%85%E5%90%AB%E5%88%9B%E4%B8%9A%E6%9D%BF%22%5D%2C%22source%22%3A%22new_parser%22%2C%22type%22%3A%22index%22%2C%22indexPropertiesMap%22%3A%7B%22%E4%B8%8D%E5%8C%85%E5%90%AB%22%3A%22%E5%88%9B%E4%B8%9A%E6%9D%BF%22%7D%2C%22reportType%22%3A%22null%22%2C%22valueType%22%3A%22_%E4%B8%8A%E5%B8%82%E6%9D%BF%E5%9D%97%22%2C%22domain%22%3A%22abs_%E8%82%A1%E7%A5%A8%E9%A2%86%E5%9F%9F%22%2C%22uiText%22%3A%22%E4%B8%8A%E5%B8%82%E6%9D%BF%E5%9D%97%E4%B8%8D%E5%8C%85%E5%90%AB%E5%88%9B%E4%B8%9A%E6%9D%BF%22%2C%22sonSize%22%3A0%2C%22queryText%22%3A%22%E4%B8%8A%E5%B8%82%E6%9D%BF%E5%9D%97%E4%B8%8D%E5%8C%85%E5%90%AB%E5%88%9B%E4%B8%9A%E6%9D%BF%22%2C%22relatedSize%22%3A0%2C%22tag%22%3A%22%E4%B8%8A%E5%B8%82%E6%9D%BF%E5%9D%97%22%7D%2C%7B%22opName%22%3A%22and%22%2C%22opProperty%22%3A%22%22%2C%22sonSize%22%3A8%2C%22relatedSize%22%3A0%7D%2C%7B%22indexName%22%3A%22%E8%82%A1%E7%A5%A8%E5%B8%82%E5%9C%BA%E7%B1%BB%E5%9E%8B%22%2C%22indexProperties%22%3A%5B%22%E4%B8%8D%E5%8C%85%E5%90%AB%E5%8C%97%E8%AF%81a%E8%82%A1%22%5D%2C%22source%22%3A%22new_parser%22%2C%22type%22%3A%22index%22%2C%22indexPropertiesMap%22%3A%7B%22%E4%B8%8D%E5%8C%85%E5%90%AB%22%3A%22%E5%8C%97%E8%AF%81a%E8%82%A1%22%7D%2C%22reportType%22%3A%22null%22%2C%22valueType%22%3A%22_%E8%82%A1%E7%A5%A8%E5%B8%82%E5%9C%BA%E7%B1%BB%E5%9E%8B%22%2C%22domain%22%3A%22abs_%E8%82%A1%E7%A5%A8%E9%A2%86%E5%9F%9F%22%2C%22uiText%22%3A%22%E8%82%A1%E7%A5%A8%E5%B8%82%E5%9C%BA%E7%B1%BB%E5%9E%8B%E4%B8%8D%E5%8C%85%E5%90%AB%E5%8C%97%E4%BA%A4%22%2C%22sonSize%22%3A0%2C%22queryText%22%3A%22%E8%82%A1%E7%A5%A8%E5%B8%82%E5%9C%BA%E7%B1%BB%E5%9E%8B%E4%B8%8D%E5%8C%85%E5%90%AB%E5%8C%97%E4%BA%A4%22%2C%22relatedSize%22%3A0%2C%22tag%22%3A%22%E8%82%A1%E7%A5%A8%E5%B8%82%E5%9C%BA%E7%B1%BB%E5%9E%8B%22%7D%2C%7B%22opName%22%3A%22and%22%2C%22opProperty%22%3A%22%22%2C%22sonSize%22%3A6%2C%22relatedSize%22%3A0%7D%2C%7B%22indexName%22%3A%22%E6%89%80%E5%B1%9E%E6%A6%82%E5%BF%B5%22%2C%22indexProperties%22%3A%5B%22%E6%A6%82%E5%BF%B5id%20301490%22%2C%22%E4%B8%8D%E5%8C%85%E5%90%AB%E6%B8%AF%E8%82%A1%E9%80%9A%22%5D%2C%22source%22%3A%22new_parser%22%2C%22type%22%3A%22index%22%2C%22indexPropertiesMap%22%3A%7B%22%E6%A6%82%E5%BF%B5id%22%3A%22301490%22%2C%22%E4%B8%8D%E5%8C%85%E5%90%AB%22%3A%22%E6%B8%AF%E8%82%A1%E9%80%9A%22%7D%2C%22reportType%22%3A%22null%22%2C%22valueType%22%3A%22_%E6%89%80%E5%B1%9E%E6%A6%82%E5%BF%B5%22%2C%22domain%22%3A%22abs_%E8%82%A1%E7%A5%A8%E9%A2%86%E5%9F%9F%22%2C%22uiText%22%3A%22%E6%89%80%E5%B1%9E%E6%A6%82%E5%BF%B5%E4%B8%8D%E5%8C%85%E5%90%AB%E6%B8%AF%E8%82%A1%E9%80%9A%22%2C%22sonSize%22%3A0%2C%22queryText%22%3A%22%E6%89%80%E5%B1%9E%E6%A6%82%E5%BF%B5%E4%B8%8D%E5%8C%85%E5%90%AB%E6%B8%AF%E8%82%A1%E9%80%9A%22%2C%22relatedSize%22%3A0%2C%22tag%22%3A%22%E6%89%80%E5%B1%9E%E6%A6%82%E5%BF%B5%22%7D%2C%7B%22opName%22%3A%22and%22%2C%22opProperty%22%3A%22%22%2C%22sonSize%22%3A4%2C%22relatedSize%22%3A0%7D%2C%7B%22indexName%22%3A%22%E8%82%A1%E7%A5%A8%E7%AE%80%E7%A7%B0%22%2C%22indexProperties%22%3A%5B%22%E4%B8%8D%E5%8C%85%E5%90%ABst%22%5D%2C%22source%22%3A%22new_parser%22%2C%22type%22%3A%22index%22%2C%22indexPropertiesMap%22%3A%7B%22%E4%B8%8D%E5%8C%85%E5%90%AB%22%3A%22st%22%7D%2C%22reportType%22%3A%22null%22%2C%22valueType%22%3A%22_%E8%82%A1%E7%A5%A8%E7%AE%80%E7%A7%B0%22%2C%22domain%22%3A%22abs_%E8%82%A1%E7%A5%A8%E9%A2%86%E5%9F%9F%22%2C%22uiText%22%3A%22%E8%82%A1%E7%A5%A8%E7%AE%80%E7%A7%B0%E4%B8%8D%E5%8C%85%E5%90%ABst%22%2C%22sonSize%22%3A0%2C%22queryText%22%3A%22%E8%82%A1%E7%A5%A8%E7%AE%80%E7%A7%B0%E4%B8%8D%E5%8C%85%E5%90%ABst%22%2C%22relatedSize%22%3A0%2C%22tag%22%3A%22%E8%82%A1%E7%A5%A8%E7%AE%80%E7%A7%B0%22%7D%2C%7B%22opName%22%3A%22and%22%2C%22opProperty%22%3A%22%22%2C%22sonSize%22%3A2%2C%22relatedSize%22%3A0%7D%2C%7B%22indexName%22%3A%22%E8%B5%84%E9%87%91%E6%B5%81%E5%90%91%22%2C%22indexProperties%22%3A%5B%22nodate%201%22%2C%22%E4%BA%A4%E6%98%93%E6%97%A5%E6%9C%9F%2020230817%22%2C%22%280%22%5D%2C%22source%22%3A%22new_parser%22%2C%22type%22%3A%22index%22%2C%22indexPropertiesMap%22%3A%7B%22%E4%BA%A4%E6%98%93%E6%97%A5%E6%9C%9F%22%3A%2220230817%22%2C%22%28%22%3A%220%22%2C%22nodate%22%3A%221%22%7D%2C%22reportType%22%3A%22TRADE_DAILY%22%2C%22dateType%22%3A%22%E4%BA%A4%E6%98%93%E6%97%A5%E6%9C%9F%22%2C%22valueType%22%3A%22_%E6%B5%AE%E7%82%B9%E5%9E%8B%E6%95%B0%E5%80%BC%28%E5%85%83%29%22%2C%22domain%22%3A%22abs_%E8%82%A1%E7%A5%A8%E9%A2%86%E5%9F%9F%22%2C%22uiText%22%3A%22%E8%B5%84%E9%87%91%E6%B5%81%E5%90%91%3E0%22%2C%22sonSize%22%3A0%2C%22queryText%22%3A%22%E8%B5%84%E9%87%91%E6%B5%81%E5%90%91%3E0%22%2C%22relatedSize%22%3A0%2C%22tag%22%3A%22%E8%B5%84%E9%87%91%E6%B5%81%E5%90%91%22%7D%2C%7B%22indexName%22%3A%22%E8%B5%84%E9%87%91%E6%B5%81%E5%90%91%22%2C%22indexProperties%22%3A%5B%22nodate%201%22%2C%22%E4%BA%A4%E6%98%93%E6%97%A5%E6%9C%9F%2020230817%22%2C%22%280%22%5D%2C%22source%22%3A%22new_parser%22%2C%22type%22%3A%22index%22%2C%22indexPropertiesMap%22%3A%7B%22%E4%BA%A4%E6%98%93%E6%97%A5%E6%9C%9F%22%3A%2220230817%22%2C%22%28%22%3A%220%22%2C%22nodate%22%3A%221%22%7D%2C%22reportType%22%3A%22TRADE_DAILY%22%2C%22dateType%22%3A%22%E4%BA%A4%E6%98%93%E6%97%A5%E6%9C%9F%22%2C%22valueType%22%3A%22_%E6%B5%AE%E7%82%B9%E5%9E%8B%E6%95%B0%E5%80%BC%28%E5%85%83%29%22%2C%22domain%22%3A%22abs_%E8%82%A1%E7%A5%A8%E9%A2%86%E5%9F%9F%22%2C%22uiText%22%3A%22%E8%B5%84%E9%87%91%E6%B5%81%E5%90%91%3E0%22%2C%22sonSize%22%3A0%2C%22queryText%22%3A%22%E8%B5%84%E9%87%91%E6%B5%81%E5%90%91%3E0%22%2C%22relatedSize%22%3A0%2C%22tag%22%3A%22%E8%B5%84%E9%87%91%E6%B5%81%E5%90%91%22%7D%5D&codelist=&indexnamelimit=&logid=8cbcf66a303f3d4896030ec7f76b8502&ret=json_all&sessionid=8cbcf66a303f3d4896030ec7f76b8502&source=Ths_iwencai_Xuangu&date_range%5B0%5D=20230817&date_range%5B1%5D=20230817&iwc_token=0ac952ac16922871181936374&urp_use_sort=1&user_id=Ths_iwencai_Xuangu_596n94e1jiz74z4tzdc5xu6pw0wdqz9e&uuids%5B0%5D=24087&query_type=stock&comp_id=6836372&business_cat=soniu&uuid=24087";
            body = body.replace("page=2","page="+page);
            String r = HttpClientUtil.post("http://www.iwencai.com/gateway/urp/v7/landing/getDataList",headers,body);
            JSONObject rj = JSONObject.parseObject(r);
            JSONArray array = rj.getJSONObject("answer").getJSONArray("components").getJSONObject(0).getJSONObject("data").getJSONArray("datas");
            allData.addAll(array);
        }
        FileUtils.writeToFile(new File("D:\\heSpace\\gu\\lt.txt"),allData.toJSONString());
//        for (int i = 0; i < array.size(); i++) {
//
//        }
    }
}
