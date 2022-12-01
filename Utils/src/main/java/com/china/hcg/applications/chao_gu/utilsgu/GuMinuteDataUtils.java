package com.china.hcg.applications.chao_gu.utilsgu;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * @autor hecaigui
 * @date 2022-10-27
 * @description 分钟数据重构-追加数据
 */
public class GuMinuteDataUtils {
    //1亿
    public final static int oneHundredMillion = 100000000;
    //1千万
    public final static int tenMillion = 10000000;
    //1百万
    public final static int oneMillion = 1000000;

    public static void minuteDataCustomZhangDieLiang(JSONArray minuteData){
        for (int i = 0; i < minuteData.size(); i++) {
            GuMinuteDataUtils.minuteDataCustomZhangDieLiang(minuteData,i);
        }
    }
    public static void minuteDataCustomZhangDieLiang(JSONArray minuteData, int i ){
        // 自定义-跌涨放量
        JSONObject minute_data_price = minuteData.getJSONObject(i);
        minute_data_price.put("局势","");
        if (i>0){
            JSONObject pre_minute_data_price = minuteData.getJSONObject(i-1);
            float amountValue = GuMinuteDataUtils.minuteComputeAmountValue(minute_data_price);
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
    public static float minuteComputeAmountValue(JSONObject minute_data_price){
        int  volume = minute_data_price.getInteger("volume");
        float  avgPrice = minute_data_price.getFloat("avgPrice");
        float amountValue = volume * avgPrice * 100;
        return amountValue;
    }
    public static void minuteDataCustomFunds(JSONArray minuteData,String[] minuteFundsData){
        for (int i = 0; i < minuteData.size(); i++) {
            JSONObject minute_data_price = minuteData.getJSONObject(i);
            try {
                minute_data_price.put("资金净额趋势(大单、中单、小单)",minuteFundsData[i]);
            }catch (ArrayIndexOutOfBoundsException e){
                minute_data_price.put("资金趋势","");
//                e.printStackTrace();
//                System.err.println(minuteFundsData.length);
//                System.err.println(minuteData.size());
            }
        }
    }

}
