package com.china.hcg.applications.chao_gu.utilscommon;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.*;

/**
 * @autor hecaigui
 * @date 2022-12-6
 * @description
 */
public class TextTableExpand {
    /**
     * @description 标准json数组打印
     * 标准：所有的列数量要相同
     */
    public static TextTable standardJsonArrayTextTable(JSONArray minute_data_price){
        //列名
        Set<String> sets = minute_data_price.getJSONObject(0).keySet();
        List<String> columnNameList = new ArrayList<String>(sets);

        //列值
        List<List<String>> allValues = new ArrayList<>();
        for (Object js : minute_data_price) {
            JSONObject jsonObject = (JSONObject)js;
            Collection values = jsonObject.values();
            List<String> valueList = new ArrayList(values);
            allValues.add(valueList);
        }
        TextTable textTable = new TextTable(columnNameList,allValues);
        return textTable;
    }
//    public static TextTable standardJsonArrayTextTable(List<Map<String,String>> minute_data_price){
    public static TextTable standardJsonArrayTextTable(List<Map<String,String>> minute_data_price){
        //列名
        Set<String> sets = minute_data_price.get(0).keySet();
        List<String> columnNameList = new ArrayList<String>(sets);

        //列值
        List<List<String>> allValues = new ArrayList<>();
        for (Object js : minute_data_price) {
            Map jsonObject = (Map)js;
            Collection values = jsonObject.values();
            List<String> valueList = new ArrayList(values);
            allValues.add(valueList);
        }
        TextTable textTable = new TextTable(columnNameList,allValues);
        return textTable;
    }




}
