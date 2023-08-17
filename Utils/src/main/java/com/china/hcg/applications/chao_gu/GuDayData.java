package com.china.hcg.applications.chao_gu;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.ParserConfig;
import com.china.hcg.applications.chao_gu.dao.stockdata.StockDataTypes;
import com.china.hcg.applications.chao_gu.model.GuInfo;
import com.china.hcg.applications.chao_gu.utilscommon.TextTable;
import com.china.hcg.applications.chao_gu.utilscommon.TextTableExpand;
import com.china.hcg.applications.chao_gu.utilsgu.GuMinuteDataUtils;
import com.china.hcg.http.HttpClientUtil;
import com.china.hcg.io.file.FileUtils;
import org.apache.commons.lang3.StringUtils;

import javax.validation.constraints.NotNull;
import java.io.File;
import java.math.BigDecimal;
import java.util.*;

import static com.china.hcg.applications.chao_gu.OthersData.getSZZS;

/**
 * @autor hecaigui
 * @date 2023-4-10
 * @description
 */
public class GuDayData {
    public static void main(String[] args) {
//        printDayGuInfo("000625","000625");
//        getSZZS();

    }

    public static  Set<GuInfo> printLatestTwoDay(List<GuInfo> list){
        StringBuilder stringBuilder = new StringBuilder();
        Set<GuInfo> console = new HashSet<>();
        for (GuInfo o : list) {
            JSONArray minute_data_price = GuDayData.getDayData(o.getCode());
            List l15 = minute_data_price.subList(minute_data_price.size() - 15 ,minute_data_price.size());
            JSONArray minute_data_price2 = new JSONArray();
            minute_data_price2.addAll(l15);
            float lowestPrice10 = minute_data_price2.getJSONObject(0).getFloat("avgPrice");
            float bigestPrice10 = minute_data_price2.getJSONObject(0).getFloat("avgPrice");
            float price10 = minute_data_price2.getJSONObject(minute_data_price2.size() - 6).getFloat("avgPrice");
            for (int i = 0; i < minute_data_price2.size() - 5; i++) {
                float p = minute_data_price2.getJSONObject(i).getFloat("avgPrice");
                if (lowestPrice10 > p) lowestPrice10 = p;
                if (bigestPrice10 < p) bigestPrice10 = p;
            }
            //10天内卖走势跳过
//            if (lowestPrice10 == price10) continue;
            if ((price10 - bigestPrice10) < 0) {
                float c = Math.abs(price10 - bigestPrice10);
                //最新价格与最高价格差价超过2个点跳过
                if (bigestPrice10 * 0.02 < c) continue;
            }

            boolean outFor = false;
            float increase5 = 0f;
            for (int i = minute_data_price2.size() - 5; i < minute_data_price2.size(); i++) {
//                System.err.println(minute_data_price2.getJSONObject(i).getFloat("increase"));
                Float increase = minute_data_price2.getJSONObject(i).getFloat("increase");
                Float avgPrice = minute_data_price2.getJSONObject(i).getFloat("avgPrice");
                increase5+=increase;
                //回落超过最近最低价 跳过
                if (avgPrice < lowestPrice10) outFor = true;
                //todo：走势抖动太大跳过
            }
            if (outFor) continue;
//            System.err.println(increase5);
//            System.err.println(Math.abs(increase5));
            if (Math.abs(increase5) > 3 )continue;
            //System.err.println(lowestPrice10);
            //System.err.println(price10);

//            JSONObject last1day = minute_data_price.getJSONObject(minute_data_price.size()-1);
//            JSONObject last2day = minute_data_price.getJSONObject(minute_data_price.size()-2);
//            Float csa = last2day.getFloat("csa");
//            if (last1day.getFloat("open") > last2day.getFloat("close") && (csa != null && csa > 0)){
            //if (csa != null && csa > 0){
            TextTable textTable = TextTableExpand.standardJsonArrayTextTable(jsonarrToListLinkedMap(minute_data_price2));
            console.add(o);
            System.err.println(o.getCode()+o.getName());
            System.err.println(textTable.printTable());
            //}
        }

        return console;
    }
    public static String printMinuteData(List<GuInfo> list){
        StringBuilder stringBuilder = new StringBuilder();
        for (GuInfo o : list) {
//            System.err.println(o);
//            System.err.println(console.get(o));

            GuMinuteData.MinuteData minuteData = GuMinuteData.minuteData(o,null);

//            System.err.println(minuteData.toString());
            boolean add = false;
            for (int i = 0; i < minuteData.getMinutePriceData().size(); i++) {
                Integer oriAmount = minuteData.getMinutePriceData().getJSONObject(i).getInteger("oriAmount");
                if (oriAmount > 3000000){
                    add = true;
                }
            }
            if (add) stringBuilder.append(o).append("\n").append("\n").append(minuteData.toString()).append("\n");
        }
        return stringBuilder.toString();
    }
    public static void printDayGuInfo(String guCode,String guName){
        JSONArray minute_data_price = GuDayData.getDayData(guCode);
        System.err.println(guCode+guName);
        TextTable textTable = TextTableExpand.standardJsonArrayTextTable(jsonarrToListLinkedMap(minute_data_price));
        System.err.println(textTable.printTable());
        //System.err.println(guCode+guName);
    }
    private static JSONArray getDayData(@NotNull String stockCode){
        String r = HttpClientUtil.getForHttps("https://finance.pae.baidu.com/selfselect/getstockquotation?code="+stockCode+"&all=1&ktype=1&isIndex=false&isBk=false&isBlock=false&isFutures=false&stockType=ab&group=quotation_kline_ab&finClientType=pc");
        ParserConfig.getGlobalInstance().setAutoTypeSupport(true);
        JSONObject rj = JSONObject.parseObject(r);

        JSONArray minute_data_price = rj.getJSONArray("Result");
        JSONArray newData = new JSONArray();
        for (int i = 0; i < minute_data_price.size(); i++) {
            JSONObject jsonObject = minute_data_price.getJSONObject(i);
            JSONObject newJsonObject = jsonObject.getJSONObject("kline");
            newJsonObject.put("date",jsonObject.getString("date"));

            newJsonObject = GuDayData.addAvgPrice(newJsonObject);
            newJsonObject = GuDayData.addAmountValue(newJsonObject);
            newJsonObject = GuDayData.addCloseSubtractAvg(newJsonObject);
            newJsonObject = GuDayData.addOpenSubtractFrontClose(newJsonObject,i>0 ?minute_data_price.getJSONObject(i-1).getJSONObject("kline") : null);
            newData.add(newJsonObject);
        }
        return newData;
    }


    private static JSONObject addAvgPrice(JSONObject newJsonObject){
        BigDecimal volume = newJsonObject.getBigDecimal("volume");
        BigDecimal amount = newJsonObject.getBigDecimal("amount");
        if (amount.intValue() == 0 || volume.intValue() == 0) {
             newJsonObject.put("avgPrice","");
            return newJsonObject;
        }
        BigDecimal shou = new BigDecimal(100);
        newJsonObject.put("avgPrice",volume.divide(amount,2,BigDecimal.ROUND_HALF_UP).divide(shou,2,BigDecimal.ROUND_HALF_UP).toPlainString());
        return newJsonObject;
    }
    //交易量
    private static JSONObject addAmountValue(JSONObject newJsonObject){
        BigDecimal volume = newJsonObject.getBigDecimal("volume");
        BigDecimal oneHundredMillion  = new BigDecimal(GuMinuteDataUtils.oneHundredMillion);
        newJsonObject.put("amountValue",volume.divide(oneHundredMillion,3,BigDecimal.ROUND_HALF_UP).toPlainString());
        return newJsonObject;
    }
    private static JSONObject addCloseSubtractAvg(JSONObject newJsonObject){
        newJsonObject.put("csa","");
        Float close = newJsonObject.getFloat("close");
        Float avgPrice = newJsonObject.getFloat("avgPrice");
        if (avgPrice == null){
            return newJsonObject;
        }
        String he = String.valueOf(close - avgPrice);
        if (close - avgPrice > 0.01) {
            newJsonObject.put("csa",he.length() >=4 ? he.substring(0,4) : he);
        } else {
            newJsonObject.put("csa","");
        }

        return newJsonObject;
    }
    private static JSONObject addOpenSubtractFrontClose(JSONObject newJsonObject,JSONObject frontJsonObject){
        newJsonObject.put("osfc","");
        if (frontJsonObject == null){
            return newJsonObject;
        }
        if (StringUtils.isBlank(frontJsonObject.getString("csa"))){
            return newJsonObject;
        }
        Float open = newJsonObject.getFloat("open");
        Float frontClose = frontJsonObject.getFloat("close");
        if (open - frontClose > 0) {
            newJsonObject.put("osfc",String.valueOf(open - frontClose));
        }
        return newJsonObject;
    }
    static LinkedList<String> descSort = new LinkedList();
    public static List<Map<String,String>> jsonarrToListLinkedMap(JSONArray jsonArray){
        //排序
        descSort.add("high");
        descSort.add("low");
        descSort.add("amountValue");
        descSort.add("open");
        descSort.add("close");
        descSort.add("avgPrice");
        descSort.add("csa");
        //
        List<Map<String,String>> r = new ArrayList<>();
        for (Object js : jsonArray) {
            JSONObject jsonObject = (JSONObject)js;
            Map  stringObjectMap = jsonObject.getInnerMap();
            r.add(mapSort(stringObjectMap,descSort));
        }
        return r;
    }
    public static LinkedHashMap<String, String> mapSort(Map<String,String>  stringObjectMap,LinkedList<String> descSort){
        LinkedHashMap<String, String> treeMap = new LinkedHashMap<>();
        treeMap.putAll(stringObjectMap);

        for (String o : descSort) {
            treeMap.remove(o);
            treeMap.put(o,stringObjectMap.get(o));
        }
        return treeMap;
    }

}
