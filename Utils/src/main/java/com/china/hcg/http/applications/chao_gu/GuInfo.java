package com.china.hcg.http.applications.chao_gu;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.china.hcg.utils.windows.InfoUtil;

/**
 * @autor hecaigui
 * @date 2022-10-27
 * @description
 */
public class GuInfo {
    String code;
    String name;
    //总量提醒
    double totalAmountReference;
    //todo:价格涨到提醒
    Float noticePrice;
    //todo:价格跌到提醒
    //todo:突然量提醒-提醒涨量


    public GuInfo(String code, String name, double totalAmountReference) {
        this.code = code;
        this.name = name;
        this.totalAmountReference = totalAmountReference;
    }
    public GuInfo(String code, String name, double totalAmountReference,float noticePrice) {
        this.code = code;
        this.name = name;
        this.totalAmountReference = totalAmountReference;
        this.noticePrice = noticePrice;
    }

    public GuInfo(String code, String name, double totalAmountReference, Float noticePrice) {
        this.code = code;
        this.name = name;
        this.totalAmountReference = totalAmountReference;
        this.noticePrice = noticePrice;
    }

    void priceNotice(JSONArray minuteData){
        if (noticePrice == null){
            return;
        }
        JSONObject minute_data_price = minuteData.getJSONObject(minuteData.size()-1);
        if(minute_data_price.getFloat("price") >= noticePrice){
            InfoUtil test = new InfoUtil();
            test.show(name+"价格提醒","提醒价格"+noticePrice+InfoUtil.breakLine+"当前价格"+minute_data_price.getFloat("price"));
        }
    }
}
