package com.china.hcg.applications.chao_gu.dao.stockdata;

import com.alibaba.fastjson.JSONArray;
import com.china.hcg.applications.chao_gu.model.GuInfo;

import javax.validation.constraints.NotNull;

/**
 * @autor hecaigui
 * @date 2022-12-8
 * @description
 */
public abstract class StockDataFactory<T> {
    GuInfo guInfo;

    public StockDataFactory(GuInfo guInfo) {
        this.guInfo = guInfo;
    }
    public  static  boolean isSupport(StockDataTypes[] wantGetStockDatas){
        return false;
    }

    public  abstract void asyncRequest(@NotNull String stockCode);
    public abstract T getAsyncRequestResult();

    public StringBuilder decoratorDataTableString(StringBuilder tableString){
        return tableString;
    }

    public JSONArray decoratorData(JSONArray minutePriceData){
        return minutePriceData;
    }
}
