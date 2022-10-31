package com.china.hcg.http.applications.chao_gu;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * @autor hecaigui
 * @date 2022-10-27
 * @description
 */
public class GuDataUtils {
    //1亿
    final static int oneHundredMillion = 100000000;
    //1千万
    final static int tenMillion = 10000000;
    //1百万
    final static int oneMillion = 1000000;

    static void minuteDataCustom(JSONArray minuteData){
        for (int i = 0; i < minuteData.size(); i++) {
            GuDataUtils.minuteDataCustom(minuteData,i);
        }
    }
    static void minuteDataCustom(JSONArray minuteData, int i ){
        // 自定义-跌涨放量
        JSONObject minute_data_price = minuteData.getJSONObject(i);
        minute_data_price.put("局势","");
        if (i>0){
            JSONObject pre_minute_data_price = minuteData.getJSONObject(i-1);
            float amountValue = GuDataUtils.minuteComputeAmountValue(minute_data_price);
            if (amountValue > oneMillion * 4 & amountValue < oneMillion * 9){
                minute_data_price.put("局势","跌放量");
                if (minute_data_price.getFloat("increase") >= pre_minute_data_price.getFloat("increase")){
                    //minute_data_price.put("局势","涨放小量");
                    minute_data_price.put("局势","");
                }
            } else if ( amountValue > oneMillion * 9){
                minute_data_price.put("局势","跌放大量");
                if (minute_data_price.getFloat("increase") >= pre_minute_data_price.getFloat("increase")){
                    minute_data_price.put("局势","涨放大量");
                }
            }
        }
        minuteData.set(i,minute_data_price);
    }
    //计算交易总价
    static float minuteComputeAmountValue(JSONObject minute_data_price){
        int  volume = minute_data_price.getInteger("volume");
        float  avgPrice = minute_data_price.getFloat("avgPrice");
        float amountValue = volume * avgPrice * 100;
        return amountValue;
    }
}
