package com.china.hcg.applications.chao_gu;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.ParserConfig;
import com.china.hcg.applications.chao_gu.model.GuInfo;
import com.china.hcg.applications.chao_gu.utilscommon.TextTable;
import com.china.hcg.applications.chao_gu.utilscommon.TextTableExpand;
import com.china.hcg.applications.chao_gu.utilsgu.GuMinuteDataUtils;
import com.china.hcg.http.HttpClientUtil;
import org.apache.commons.lang3.StringUtils;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.*;

/**
 * @autor hecaigui
 * @date 2023-4-10
 * @description
 */
public class GuDayData {
    public static void main(String[] args) {
        printDayGuInfo("002055","002055");
    }
    public static  void printLatestTwoDay(List<GuInfo> list){
        int z = 0;
        int f = 0;
        Map<String,String> console = new HashMap<>();
        Map<String,String> zconsole = new HashMap<>();
        Map<String,String> fconsole = new HashMap<>();
        for (GuInfo o : list) {
            JSONArray minute_data_price = GuDayData.getDayData(o.getCode());
            //System.err.println(o.getCode()+o.getName());
            if (minute_data_price.size() <= 6) continue;
            JSONArray minute_data_price2 = new JSONArray();
            minute_data_price2.add(minute_data_price.get(minute_data_price.size()-6) );
            minute_data_price2.add(minute_data_price.get(minute_data_price.size()-5) );
            minute_data_price2.add(minute_data_price.get(minute_data_price.size()-4) );
            minute_data_price2.add(minute_data_price.get(minute_data_price.size()-3) );
            minute_data_price2.add(minute_data_price.get(minute_data_price.size()-2) );
            minute_data_price2.add(minute_data_price.get(minute_data_price.size()-1) );
            TextTable textTable = TextTableExpand.standardJsonArrayTextTable(jsonarrToListLinkedMap(minute_data_price2));


            JSONObject last1day = minute_data_price.getJSONObject(minute_data_price.size()-1);
            JSONObject last2day = minute_data_price.getJSONObject(minute_data_price.size()-2);
            Float csa = last2day.getFloat("csa");
            if (last1day.getFloat("open") > last2day.getFloat("close") && (csa != null && csa > 0)){
                console.put(o.getCode()+o.getName(),textTable.printTable());
//                if (last1day.getString("netChangeRatio").contains("+")){
//                    z++;
//                } else f++;
                if (last1day.getFloat("close") > last1day.getFloat("open")){
                    z++;
                    zconsole.put(o.getCode()+o.getName(),textTable.printTable());
                } else {
                    f++;
                    fconsole.put(o.getCode()+o.getName(),textTable.printTable());
                }
            }
            //System.err.println(textTable.printTable());
        }
        for (String o : console.keySet()) {
            System.err.println(o);
            System.err.println(console.get(o));
        }
        for (String o : zconsole.keySet()) {
            System.err.println(o);
            System.err.println(console.get(o));
        }
        System.err.println(z);
        System.err.println(f);
        for (String o : fconsole.keySet()) {
            System.err.println(o);
            System.err.println(console.get(o));
        }
        System.err.println(z);
        System.err.println(f);
    }
    public static void printDayGuInfo(String guCode,String guName){
        JSONArray minute_data_price = GuDayData.getDayData(guCode);
        System.err.println(guCode+guName);
        TextTable textTable = TextTableExpand.standardJsonArrayTextTable(jsonarrToListLinkedMap(minute_data_price));
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
