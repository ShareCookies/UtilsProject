package com.china.hcg.applications.chao_gu.model;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.china.hcg.applications.chao_gu.utilsgu.GuMinuteDataUtils;
import com.china.hcg.utils.windows.InfoUtil;

/**
 * @autor hecaigui
 * @date 2022-10-27
 * @description
 */
public class GuInfo {
    public String code;
    public String name;
    //sz、sh
    public String area = "sz";

    //总量提醒
    public double totalAmountReference;
    //价格涨到提醒
    public Float noticePriceRise;
    //价格跌到提醒
    public Float noticePriceFall;
    //todo:突然量提醒-提醒涨量
    //大资金突然离场提醒
    public Double noticeFundsInstantRun = -500d;
    //大资金突然入场提醒
    public Double noticeFundsInstantEnter = 100d;
    //todo：大资金买入量达到多少提醒
    //todo：大资金卖入量达到多少提醒


    public GuInfo(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public GuInfo(String code, String name, String area) {
        this.code = code;
        this.name = name;
        this.area = area;
    }

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

    public GuInfo(String code, String name, double totalAmountReference, Float noticePriceRise, Float noticePriceFall, Double noticeFundsInstantRun, Double noticeFundsInstantEnter) {
        this.code = code;
        this.name = name;
        this.totalAmountReference = totalAmountReference;
        this.noticePriceRise = noticePriceRise;
        this.noticePriceFall = noticePriceFall;
        this.noticeFundsInstantRun = noticeFundsInstantRun;
        this.noticeFundsInstantEnter = noticeFundsInstantEnter;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public double getTotalAmountReference() {
        return totalAmountReference;
    }

    public void setTotalAmountReference(double totalAmountReference) {
        this.totalAmountReference = totalAmountReference;
    }

    public Float getNoticePriceRise() {
        return noticePriceRise;
    }

    public void setNoticePriceRise(Float noticePriceRise) {
        this.noticePriceRise = noticePriceRise;
    }

    public Float getNoticePriceFall() {
        return noticePriceFall;
    }

    public void setNoticePriceFall(Float noticePriceFall) {
        this.noticePriceFall = noticePriceFall;
    }
}
