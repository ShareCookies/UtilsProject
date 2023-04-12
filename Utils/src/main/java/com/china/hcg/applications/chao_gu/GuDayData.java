package com.china.hcg.applications.chao_gu;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.ParserConfig;
import com.china.hcg.applications.chao_gu.utilscommon.TextTable;
import com.china.hcg.applications.chao_gu.utilscommon.TextTableExpand;
import com.china.hcg.http.HttpClientUtil;

import javax.validation.constraints.NotNull;

/**
 * @autor hecaigui
 * @date 2023-4-10
 * @description
 */
public class GuDayData {
    public static void main(String[] args) {
        printDayGuInfo("600276","恒瑞医药");
    }
    public static void printDayGuInfo(String guCode,String guName){
        JSONArray minute_data_price = GuDayData.getDayData(guCode);
        System.err.println(guCode+guName);
        TextTable textTable = TextTableExpand.standardJsonArrayTextTable(minute_data_price);
        System.err.println(textTable.printTable());
        //System.err.println(guCode+guName);
    }
    private static JSONArray getDayData(@NotNull String stockCode){
        String r = HttpClientUtil.getForHttpsAndCookie("https://finance.pae.baidu.com/selfselect/getstockquotation?code="+stockCode+"&all=1&ktype=1&isIndex=false&isBk=false&isBlock=false&isFutures=false&stockType=ab&group=quotation_kline_ab&finClientType=pc");
        ParserConfig.getGlobalInstance().setAutoTypeSupport(true);
        JSONObject rj = JSONObject.parseObject(r);

        JSONArray minute_data_price = rj.getJSONArray("Result");
        JSONArray newData = new JSONArray();
        for (int i = 0; i < minute_data_price.size(); i++) {
            JSONObject jsonObject = minute_data_price.getJSONObject(i);
            JSONObject newJsonObject = jsonObject.getJSONObject("kline");
            newJsonObject.put("date",jsonObject.getString("date"));
            newData.add(newJsonObject);
        }
        return newData;
    }
}
