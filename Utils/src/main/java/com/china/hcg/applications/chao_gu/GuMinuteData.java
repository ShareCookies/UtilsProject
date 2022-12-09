package com.china.hcg.applications.chao_gu;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.ParserConfig;
import com.china.hcg.applications.chao_gu.dao.stockdata.MinuteFundDirectionsStockDataFactory;
import com.china.hcg.applications.chao_gu.dao.stockdata.MinuteFundsStockDataFactory;
import com.china.hcg.applications.chao_gu.dao.stockdata.StockDataTypes;
import com.china.hcg.applications.chao_gu.model.GuInfo;
import com.china.hcg.applications.chao_gu.utilscommon.StockThreadPoolUtil;
import com.china.hcg.applications.chao_gu.utilscommon.TextTableExpand;
import com.china.hcg.http.HttpClientUtil;
import com.china.hcg.applications.chao_gu.utilscommon.TextTable;
import com.china.hcg.applications.chao_gu.utilsgu.GuMinuteDataUtils;
import com.china.hcg.io.file.FileUtils;
import com.china.hcg.utils.date.DateUtil;
import org.apache.log4j.Logger;

import javax.validation.constraints.NotNull;
import java.io.File;
import java.util.*;
import java.util.concurrent.*;

/**
 * @autor hecaigui
 * @date 2022-9-15
 * @description
 */
public class GuMinuteData {
    private static Logger logger = Logger.getLogger(GuMinuteData.class);

    public static void main(String[] args) {
        List<GuInfo> list = new ArrayList();
//        list.add(new GuInfo("002241","歌尔股份","sz"));
//        list.add(new GuInfo("000792","盐湖股份"));
//        list.add(new GuInfo("002027","分众传媒","sz"));
//        list.add(new GuInfo("000776","广发证券","sz"));
//        list.add(new GuInfo("600383","金地集团","sh"));

//        list.add(new GuInfo("601456","国联证券","sz"));
//        list.add(new GuInfo("600383","金地集团","sz"));
//        list.add(new GuInfo("002241","歌尔股份","sz"));
//        list.add(new GuInfo("601377","兴业证券","sz"));
//        list.add(new GuInfo("601229","上海银行","sz"));
//        list.add(new GuInfo("002415","海康威视","sz"));
//        list.add(new GuInfo("600276","恒瑞医药","sh"));
//        list.add(new GuInfo("601318","中国平安","sz"));
//        list.add(new GuInfo("600741","华域汽车","sz"));
//        list.add(new GuInfo("002460","赣锋锂业","sz"));
//        list.add(new GuInfo("002466","天齐锂业","sz"));
//        list.add(new GuInfo("000012","南玻A","sz"));
//        list.add(new GuInfo("000725","京东方a","sz"));

        //printLatestMinuteGuInfo(list);


        //outGuInfo(list,"D:/chaogu/");

        //printMinuteGuInfo("0000001","上证指数");
        printMinuteGuInfo(new GuInfo("000792","盐湖股份","sz"));
//        printMinuteGuInfo(new GuInfo("002027","分众传媒","sz"));
//        printMinuteGuInfo(new GuInfo("000776","广发证券","sz"));
//        printMinuteGuInfo(new GuInfo("002241","歌尔股份","sz"));
//        printMinuteGuInfo(new GuInfo("601456","国联证券","sz"));
//        printMinuteGuInfo(new GuInfo("600383","金地集团","sh"));
//        printMinuteGuInfo(new GuInfo("600276","恒瑞医药","sh"));
//        printMinuteGuInfo(new GuInfo("600741","华域汽车","sz"));
//        printMinuteGuInfo(new GuInfo("601318","中国平安","sz"));
//        printMinuteGuInfo(new GuInfo("000725","京东方a","sz"));
//        printMinuteGuInfo(new GuInfo("000012","南玻A","sz"));
//        printMinuteGuInfo(new GuInfo("000725","京东方a","sz"));
        //自定义列表
    }
    /**
     * @description console打印股票分时详细信息table
     */
    public static void printMinuteGuInfo(GuInfo guInfo){
        System.out.println("大单：大于等于6万股或者30万元以上的成交单。\n" +
                "中单：大于等于1万股小于6万股或者大于等于5万元小于30万元的成交单。\n" +
                "小单：小于1万股或5万元的成交单。");
        MinuteData minuteData = minuteData(guInfo,new StockDataTypes[]{StockDataTypes.MinuteFunds,StockDataTypes.MinuteFundDirections});
        System.err.println(minuteData);

    }
    /**
     * @description console打印多只股票最新价格信息
     */
    public static void printLatestMinuteGuInfo(List<GuInfo> list){
        //数据的并发获取
        HashMap<String,MinuteData> allMinuteDatas = new HashMap<>();
        CompletableFuture<MinuteData>[] allMinuteDatasFuture = new CompletableFuture[list.size()];
        for (int i = 0; i < list.size(); i++) {
            GuInfo guInfo = list.get(i);
            CompletableFuture<MinuteData> future1 = CompletableFuture.supplyAsync(() -> {
                MinuteData minuteData = minuteData(guInfo,null);
                return minuteData;
            },StockThreadPoolUtil.executorService);
            allMinuteDatasFuture[i] = future1;
        }

        for (CompletableFuture completableFuture : allMinuteDatasFuture) {
            try {
                Object object = completableFuture.get();
                MinuteData minuteData = (MinuteData)object;
                allMinuteDatas.put(minuteData.getGuCode(),minuteData);
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            } catch (Exception e){
                e.printStackTrace();
            }
        }

        //对数据进行重新拼接
        List<Map<String,String>> minute_data_price = new ArrayList<>();
        for (GuInfo guInfo : list) {
            LinkedHashMap<String,String> latestMinuteGuInfoTreeMap = new LinkedHashMap<>();
            System.err.println(guInfo);
            MinuteData minuteData = allMinuteDatas.get(guInfo.code);
            //
            JSONObject latestMinuteGuInfoJson = null;
            if (minuteData.minutePriceData.size() > 0){
                latestMinuteGuInfoJson = minuteData.minutePriceData.getJSONObject(minuteData.minutePriceData.size() - 1);
            }
            if (latestMinuteGuInfoJson == null){
                continue;
            }
            latestMinuteGuInfoTreeMap.put("guName",guInfo.name);
            latestMinuteGuInfoTreeMap.put("datetime",latestMinuteGuInfoJson.getString("datetime"));
            latestMinuteGuInfoTreeMap.put("amount",latestMinuteGuInfoJson.getString("amount"));
            latestMinuteGuInfoTreeMap.put("局势",latestMinuteGuInfoJson.getString("局势"));
            latestMinuteGuInfoTreeMap.put("price",latestMinuteGuInfoJson.getString("price"));
            latestMinuteGuInfoTreeMap.put("avgPrice",latestMinuteGuInfoJson.getString("avgPrice"));
            latestMinuteGuInfoTreeMap.put("ratio",latestMinuteGuInfoJson.getString("ratio"));
            minute_data_price.add(latestMinuteGuInfoTreeMap);
        }
        TextTable textTable = TextTableExpand.standardJsonArrayTextTable(minute_data_price);
        System.err.println(textTable.printTable());
    }


    /**
     * @description 输出股票详细分时信息到指定文件夹，当日分时json和分时table
     */
    static void outGuInfo(List<GuInfo> list,String outFilePath){
        for (GuInfo guInfo : list) {
            MinuteData minuteData = minuteData(guInfo,new StockDataTypes[]{StockDataTypes.MinuteFundDirections,StockDataTypes.MinuteFunds});
            FileUtils.writeToFile(new File(outFilePath), DateUtil.dateToString(new Date(),"yyyy-MM-dd")+guInfo.name+"json.txt",minuteData.minutePriceData.toJSONString());
            FileUtils.writeToFile(new File(outFilePath),DateUtil.dateToString(new Date(),"yyyy-MM-dd")+guInfo.name+".txt",minuteData.minutePriceDataTable);
        }
    }
//    public static void printDayGuInfo(String guCode,String guName){
//        JSONArray minute_data_price = GuMinuteData.getDayData(guCode);
//        System.err.println(guCode+guName);
//        TextTable textTable = GuMinuteData.standardJsonArrayPrint(minute_data_price);
//        System.err.println(textTable.printTable());
//        //System.err.println(guCode+guName);
//    }
    /**
     * @description 数据结构-存储分钟数据
     */
    //?内部类的危害等
    static class MinuteData{
        String guCode;
        JSONArray minutePriceData;
        String minutePriceDataTable;

        public MinuteData(String guCode, JSONArray minutePriceData, String minutePriceDataTable) {
            this.guCode = guCode;
            this.minutePriceData = minutePriceData;
            this.minutePriceDataTable = minutePriceDataTable;
        }

        public String getGuCode() {
            return guCode;
        }

        public void setGuCode(String guCode) {
            this.guCode = guCode;
        }

        public JSONArray getMinutePriceData() {
            return minutePriceData;
        }

        public void setMinutePriceData(JSONArray minutePriceData) {
            this.minutePriceData = minutePriceData;
        }

        public String getMinutePriceDataTable() {
            return minutePriceDataTable;
        }

        public void setMinutePriceDataTable(String minutePriceDataTable) {
            this.minutePriceDataTable = minutePriceDataTable;
        }

        @Override
        public String toString() {
            return minutePriceDataTable;
        }

    }
    /**
     * @description 获取分钟数据-外观模式，整合了所有分钟数据相关接口
     * @author hecaigui
     * @date 2022-12-6
     * @param
     * @return
     */
    public static MinuteData minuteData(GuInfo guInfo, StockDataTypes[] wantGetStockDatas){
        //好处?
        StringBuilder stringBuilder = new StringBuilder();

        try {
            Future<JSONArray> minutePrice = GuMinuteData.asyncGetMinuteData(guInfo.getCode());

            MinuteFundsStockDataFactory minuteFundsStockDataFactory = null;
            MinuteFundDirectionsStockDataFactory minuteFundDirectionsStockDataFactory = null;
            if (MinuteFundsStockDataFactory.isSupport(wantGetStockDatas)){
                 minuteFundsStockDataFactory = new MinuteFundsStockDataFactory(guInfo);
            }
            if (MinuteFundDirectionsStockDataFactory.isSupport(wantGetStockDatas)){
                 minuteFundDirectionsStockDataFactory = new MinuteFundDirectionsStockDataFactory(guInfo);
            }

            JSONArray minutePriceData = minutePrice.get();
            if (minuteFundsStockDataFactory != null) minuteFundsStockDataFactory.getAsyncRequestResult();
            if (minuteFundDirectionsStockDataFactory != null) minuteFundDirectionsStockDataFactory.getAsyncRequestResult();


            GuMinuteDataUtils.minuteDataCustomZhangDieLiang(minutePriceData);
            //分钟资金量
            if (minuteFundsStockDataFactory != null) minuteFundsStockDataFactory.decoratorData(minutePriceData);
            TextTable textTable = TextTableExpand.standardJsonArrayTextTable(minutePriceData);


            stringBuilder.append(textTable.printTable());
            stringBuilder.append(System.getProperty("line.separator"));
            //分钟资金量
            if (minuteFundsStockDataFactory != null) minuteFundsStockDataFactory.decoratorDataTableString(stringBuilder);
            //资金流势
            if (minuteFundDirectionsStockDataFactory != null) minuteFundDirectionsStockDataFactory.decoratorDataTableString(stringBuilder);

            return new MinuteData(guInfo.getCode(),minutePriceData,stringBuilder.toString());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * @description 获取分时数据
     * @return
     */
    private static JSONArray getMinuteData(@NotNull String stockCode){
        String r = HttpClientUtil.getForHttpsAndCookie("https://gushitong.baidu.com/opendata?openapi=1&dspName=iphone&tn=tangram&client=app&query="+stockCode+"&code="+stockCode+"&word="+stockCode+"&resource_id=5429&ma_ver=4&finClientType=pc");
        //?
        //https://blog.csdn.net/adsl624153/article/details/79562282
        ParserConfig.getGlobalInstance().setAutoTypeSupport(true);
        JSONObject rj = JSONObject.parseObject(r);

        JSONArray minute_data_price = rj.getJSONArray("Result").getJSONObject(1).getJSONObject("DisplayData").getJSONObject("resultData").getJSONObject("tplData").getJSONObject("result").getJSONObject("minute_data").getJSONArray("priceinfo");
        return minute_data_price;
    }
    public static Future<JSONArray> asyncGetMinuteData(@NotNull String stockCode){
        Future<JSONArray> future = StockThreadPoolUtil.executorService.submit(new Callable<JSONArray>() {
            @Override
            public JSONArray call() throws Exception {
                return getMinuteData(stockCode);
            }
        });
        return future;
    }
//    private static JSONArray getDayData(@NotNull String stockCode){
//        String r = HttpClientUtil.getForHttpsAndCookie("https://finance.pae.baidu.com/selfselect/getstockquotation?code="+stockCode+"&all=1&ktype=1&isIndex=false&isBk=false&isBlock=false&isFutures=false&stockType=ab&group=quotation_kline_ab&finClientType=pc");
//        ParserConfig.getGlobalInstance().setAutoTypeSupport(true);
//        JSONObject rj = JSONObject.parseObject(r);
//
//        JSONArray minute_data_price = rj.getJSONArray("Result");
//        JSONArray newData = new JSONArray();
//        for (int i = 0; i < minute_data_price.size(); i++) {
//            JSONObject jsonObject = minute_data_price.getJSONObject(i);
//            JSONObject newJsonObject = jsonObject.getJSONObject("kline");
//            newJsonObject.put("date",jsonObject.getString("date"));
//            newData.add(newJsonObject);
//        }
//        return newData;
//    }

}
