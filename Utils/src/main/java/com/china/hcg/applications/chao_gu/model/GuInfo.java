package com.china.hcg.applications.chao_gu.model;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.china.hcg.utils.windows.InfoUtil;

/**
 * @autor hecaigui
 * @date 2022-10-27
 * @description
 */
public class GuInfo {
    public String code;
    public String name;

    //总量提醒
    public double totalAmountReference;
    //价格涨到提醒
    Float noticePriceRise;
    //价格跌到提醒
    Float noticePriceFall;
    //todo:突然量提醒-提醒涨量


    public GuInfo(String code, String name, double totalAmountReference) {
        this.code = code;
        this.name = name;
        this.totalAmountReference = totalAmountReference;
    }
    public GuInfo(String code, String name, double totalAmountReference,Float noticePriceRise,Float noticePriceFall) {
        this.code = code;
        this.name = name;
        this.totalAmountReference = totalAmountReference;
        this.noticePriceRise = noticePriceRise;
        this.noticePriceFall = noticePriceFall;
    }
    public void priceNotice(JSONArray minuteData){
        if (noticePriceRise == null && noticePriceFall == null){
            return;
        }
        JSONObject minute_data_price = minuteData.getJSONObject(minuteData.size()-1);
        if(minute_data_price.getFloat("price") >= noticePriceRise && noticePriceRise != null){
            InfoUtil test = new InfoUtil();
            test.show(name+"价格提醒","涨价提醒"+noticePriceRise+InfoUtil.breakLine+"当前价格"+minute_data_price.getFloat("price"));
        }
        if (minute_data_price.getFloat("price") <= noticePriceFall && noticePriceFall != null){
            InfoUtil test = new InfoUtil();
            test.show(name+"价格提醒","跌价提醒"+noticePriceFall+InfoUtil.breakLine+"当前价格"+minute_data_price.getFloat("price"));
        }
    }
}
