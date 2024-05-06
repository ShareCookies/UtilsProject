package com.china.hcg.applications.chao_gu;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.lang.UUID;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.ParserConfig;
import com.china.hcg.applications.chao_gu.dao.stockdata.MinuteFundDirectionsStockDataFactory;
import com.china.hcg.applications.chao_gu.dao.stockdata.MinuteFundsStockDataFactory;
import com.china.hcg.applications.chao_gu.dao.stockdata.ThsMinuteFundsStockDataFactory;
import com.china.hcg.applications.chao_gu.dao.stockdata.StockDataTypes;
import com.china.hcg.applications.chao_gu.model.GuInfo;
import com.china.hcg.applications.chao_gu.utilscommon.StockThreadPoolUtil;
import com.china.hcg.applications.chao_gu.utilscommon.TextTableExpand;
import com.china.hcg.applications.chao_gu.utilsgu.GuMinuteDataNoticeUtils;
import com.china.hcg.http.HttpClientUtil;
import com.china.hcg.applications.chao_gu.utilscommon.TextTable;
import com.china.hcg.applications.chao_gu.utilsgu.GuMinuteDataUtils;
import com.china.hcg.io.file.FileUtils;
import com.china.hcg.utils.date.DateUtil;
import com.china.hcg.utils.logback.LogBackTest;
import com.china.hcg.utils.timer.JavaTimer;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.groovy.runtime.typehandling.BigDecimalMath;
import org.slf4j.LoggerFactory;

import javax.validation.constraints.NotNull;
import java.io.File;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.time.Duration;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * @autor hecaigui
 * @date 2022-9-15
 * @description
 */
@Slf4j
public class GuMinuteData {
    private static final org.slf4j.Logger logger= LoggerFactory.getLogger(LogBackTest.class);
    public List<GuInfo> list = new ArrayList();
    public GuMinuteData() {

    }
//        LocalDate startDate = LocalDate.parse("2023-03-28");
//        LocalDate endDate = LocalDate.parse("2023-03-28");
//        YearMonth currentMonth = YearMonth.from(startDate);
//        LocalDate monthStart = currentMonth.atDay(1);
//        LocalDate monthEnd = currentMonth.atEndOfMonth();
//        Date startDate1 = Date.from(monthStart.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
//
//        Date endDate1 = Date.from(monthEnd.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());

//        JSONObject jsonObject = JSONObject.parseObject("{\"SubmerContactCert\":\"350127197404115291\",\"CompanyCertType\":\"\",\"Address\":\"福建省福州市鼓楼区福州软件园a区17号楼\",\"Sex\":\"男\",\"SubMerSort\":\"1\",\"SubMerName\":\"个人商户_何国美\",\"PosAccount\":\"\",\"SubMerchantShortName\":\"个人商户_何国美\",\"Remark\":\"\",\"SubMerSignNo\":\"9023062823362599140\",\"Industry\":\"2005\",\"BusinessRange\":\"\",\"CompanyName\":\"\",\"EndCertificateValidity\":\"\",\"NewSubMerchantNo\":\"\",\"SubmerContactMail\":\"\",\"MerchantName\":\"福建省星云大数据应用服务有限公司\",\"SubMerchantType\":\"1\",\"NotifyUrl\":\"http://59.56.104.131:9001/api-abc/outside/applyNofity\",\"MerMobileNo\":\"13905029281\",\"Status\":\"1\",\"CertificateBegDate\":\"20060205\",\"TrxType\":\"QrySubMerchantInfo\",\"isPassed\":\"2\",\"ReturnCode\":\"9913\",\"CusNo\":\"6232111820001570589\",\"CertificateNo\":\"350127197404115291\",\"AccountRecords\":[],\"Nationality\":\"中国\",\"StatusMessage\":\"\",\"CompanyCertNo\":\"\",\"CertificateType\":\"110001\",\"SubmerContactType\":\"商户信息核实联系人\",\"ServicePhone\":\"13905029281\",\"FrCertEndDate\":\"20260205\",\"FrResidence\":\"福建省福州市鼓楼区福建省福清市三山镇虎邱村222号\",\"SubMerId\":\"1039913110A2253\",\"PosAccountName\":\"\"}");
//        System.err.println(jsonObject);
//        //提取门店汇总菜单的merchantNo
//        String string = FileUtils.readTxtContent(new File("D:/ordersync/info-region.txt"));
//        JSONArray t2 =    JSONObject.parseObject(string).getJSONArray("object");
//
//        String s = "";
//        for (Object o : t2) {
//            JSONObject o2 = (JSONObject)o;
//            s= s+"\""+o2.getString("merchantNo")+"\",";
//        }
//        System.err.println(s.substring(0,s.length()-1));
////report_store同步语句
//        String test = FileUtils.readTxtContent(new File("D:/ordersync/info-region.txt"));
//        String[] t2 = test.split(";");
//
//        String all = "";
//        for (String o : t2) {
//            if (o.length() < 10) continue;
//            if (o.startsWith("\n")){o = o.substring(1,o.length());}
//            String merchantNo = o.substring(o.indexOf("`merchantNo` = '") +16,o.indexOf("', `date` = '"));
//            String date = o.substring(o.indexOf("`date` = '") +10,o.indexOf(", `totalPayValue`")-1);
//            all += o.substring(0,o.indexOf("WHERE")) + " WHERE `merchantNo` = '"+ merchantNo + "' and `date` = '"+date + "';\n";
//        }
//        FileUtils.writeToFile(new File("D:/ordersync/test.txt"),all);

    public static void main(String[] args) {
       start2();
//        start();
    }
    static ScheduledExecutorService yanchi = new ScheduledThreadPoolExecutor(1, r -> {
        Thread t = new Thread(r);
        t.setName("yanchi线程池");
        t.setDaemon(true);
        return  t;
    });
    public static void start2() {

        List<GuInfo> list = new ArrayList<>();
//////mai
////
        list.add(new GuInfo("000017","000017","sz"));
        list.add(new GuInfo("000017","000017","sz"));
        list.add(new GuInfo("000017","000017","sz"));
        list.add(new GuInfo("000017","000017","sz"));
        list.add(new GuInfo("000017","000017","sz"));
        list.add(new GuInfo("000017","000017","sz"));
        list.add(new GuInfo("000017","000017","sz"));
        list.add(new GuInfo("000017","000017","sz"));
        list.add(new GuInfo("000017","000017","sz"));
        list.add(new GuInfo("000017","000017","sz"));
        list.add(new GuInfo("000017","000017","sz"));
        list.add(new GuInfo("000017","000017","sz"));
        list.add(new GuInfo("000017","000017","sz"));
        list.add(new GuInfo("000017","000017","sz"));
        list.add(new GuInfo("000017","000017","sz"));
        list.add(new GuInfo("000017","000017","sz"));
        list.add(new GuInfo("000017","000017","sz"));
        list.add(new GuInfo("000017","000017","sz"));
        list.add(new GuInfo("000017","000017","sz"));
        list.add(new GuInfo("000017","000017","sz"));
        list.add(new GuInfo("000017","000017","sz"));
        list.add(new GuInfo("000017","000017","sz"));
        list.add(new GuInfo("000017","000017","sz"));
        list.add(new GuInfo("000017","000017","sz"));
        list.add(new GuInfo("000017","000017","sz"));
        list.add(new GuInfo("000017","000017","sz"));
        list.add(new GuInfo("000017","000017","sz"));
        list.add(new GuInfo("000017","000017","sz"));
        list.add(new GuInfo("000017","000017","sz"));
        list.add(new GuInfo("000017","000017","sz"));
        list.add(new GuInfo("000017","000017","sz"));
        list.add(new GuInfo("000017","000017","sz"));
        list.add(new GuInfo("000017","000017","sz"));

//
        for (GuInfo guInfo : list) {
            printMinuteGuInfo(guInfo);
        }
    }
    public static void start() {
//        printMinuteGuInfo(new GuInfo("600887","伊利","sz"));
    }
    /**
     * @description console打印股票分时详细信息table
     */
    public static void printMinuteGuInfo(GuInfo guInfo){
        System.out.println("大单：大于等于6万股或者30万元以上的成交单。\n" +
                "中单：大于等于1万股小于6万股或者大于等于5万元小于30万元的成交单。\n" +
                "小单：小于1万股或5万元的成交单。");
        MinuteData minuteData = minuteData(guInfo,new StockDataTypes[]{StockDataTypes.MinuteFundDirections,StockDataTypes.ThsMinuteFunds,StockDataTypes.MinuteFunds});
//        MinuteData minuteData = minuteData(guInfo,null);
        logger.error(minuteData.toString());
//        FileUtils.writeToFile(new File("C:\\Users\\98279\\Desktop\\gu收集\\成功案例\\"+));
    }
    /**
     * @description console打印多只股票最新价格信息
     */
    public static List<Map<String, String>> printLatestMinuteGuInfo(List<GuInfo> list) {
        //数据的并发获取
        HashMap<String,MinuteData> allMinuteDatas = new HashMap<>();
        CompletableFuture<MinuteData>[] allMinuteDatasFuture = new CompletableFuture[list.size()];
        for (int i = 0; i < list.size(); i++) {
            GuInfo guInfo = list.get(i);
            CompletableFuture<MinuteData> future1 = CompletableFuture.supplyAsync(() -> {
                MinuteData minuteData = minuteData(guInfo,new StockDataTypes[]{});
                return minuteData;
            },StockThreadPoolUtil.executorService);
            allMinuteDatasFuture[i] = future1;
        }

        for (CompletableFuture completableFuture : allMinuteDatasFuture) {
            try {
                Object object = completableFuture.get(10,TimeUnit.SECONDS);
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
            if (minuteData == null){
                continue;
            }
            //
            JSONObject latestMinuteGuInfoJson = null;
            if (minuteData.minutePriceData.size() > 0){
                latestMinuteGuInfoJson = minuteData.minutePriceData.getJSONObject(minuteData.minutePriceData.size() - 1);
            }
            if (latestMinuteGuInfoJson == null){
                continue;
            }
            latestMinuteGuInfoTreeMap.put("guCode", guInfo.getCode());
            latestMinuteGuInfoTreeMap.put("guName",guInfo.getArea()+guInfo.getCode()+guInfo.getName());
            latestMinuteGuInfoTreeMap.put("datetime",latestMinuteGuInfoJson.getString("datetime"));
            latestMinuteGuInfoTreeMap.put("amount",latestMinuteGuInfoJson.getString("amount"));
            latestMinuteGuInfoTreeMap.put("局势",latestMinuteGuInfoJson.getString("局势"));
            latestMinuteGuInfoTreeMap.put("price",latestMinuteGuInfoJson.getString("price"));
            latestMinuteGuInfoTreeMap.put("avgPrice",latestMinuteGuInfoJson.getString("avgPrice"));
            latestMinuteGuInfoTreeMap.put("ratio",latestMinuteGuInfoJson.getString("ratio"));
            latestMinuteGuInfoTreeMap.put("minuteData",latestMinuteGuInfoJson.getString("ratio"));

            GuMinuteDataNoticeUtils guMinuteDataNoticeUtils = new GuMinuteDataNoticeUtils(guInfo);
            guMinuteDataNoticeUtils.largeFundsFlowNotice(minuteData.minutePriceData);
            minute_data_price.add(latestMinuteGuInfoTreeMap);
        }
        TextTable textTable = TextTableExpand.standardJsonArrayTextTable(minute_data_price);
        System.err.println(textTable.printTable());
        return minute_data_price;
    }


    /**
     * @description 输出股票详细分时信息到指定文件夹，当日分时json和分时table
     */
    static void outGuInfo(List<GuInfo> list,String outFilePath){
        for (GuInfo guInfo : list) {
            MinuteData minuteData = minuteData(guInfo,new StockDataTypes[]{StockDataTypes.MinuteFundDirections,StockDataTypes.ThsMinuteFunds,StockDataTypes.MinuteFunds});
            FileUtils.writeToFile(new File(outFilePath), DateUtil.dateToString(new Date(),"yyyy-MM-dd")+guInfo.name+"json.txt",minuteData.minutePriceData.toJSONString());
            FileUtils.writeToFile(new File(outFilePath),DateUtil.dateToString(new Date(),"yyyy-MM-dd")+guInfo.name+".txt",minuteData.minutePriceDataTable);
        }
    }

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
            // 1. 获取请求
            Future<JSONArray> minutePrice = GuMinuteData.asyncGetMinuteData(guInfo.getCode());
            //1.1 根据需求，自定义发起异步请求获取不同的分钟扩展数据
            //1.2 自定义-分钟资金量
            ThsMinuteFundsStockDataFactory thsMinuteFundsStockDataFactory = null;
            if (ThsMinuteFundsStockDataFactory.isSupport(wantGetStockDatas)){
                 thsMinuteFundsStockDataFactory = new ThsMinuteFundsStockDataFactory(guInfo);
            }
            //1.3 自定义-分钟资金流势
            MinuteFundDirectionsStockDataFactory minuteFundDirectionsStockDataFactory = null;
            if (MinuteFundDirectionsStockDataFactory.isSupport(wantGetStockDatas)){
                 minuteFundDirectionsStockDataFactory = new MinuteFundDirectionsStockDataFactory(guInfo);
            }
            //1.4 自定义-分钟大单资金量
            MinuteFundsStockDataFactory minuteFundsStockDataFactory = null;
            if (MinuteFundsStockDataFactory.isSupport(wantGetStockDatas)){
                minuteFundsStockDataFactory = new MinuteFundsStockDataFactory(guInfo);
            }
            //2. 分钟数据
            JSONArray minutePriceData = minutePrice.get();
            if (thsMinuteFundsStockDataFactory != null) {
                thsMinuteFundsStockDataFactory.getAsyncRequestResult();
            }
            //2.2
            if (minuteFundDirectionsStockDataFactory != null) {
                minuteFundDirectionsStockDataFactory.getAsyncRequestResult();
            }
            //2.4
            if (minuteFundsStockDataFactory != null) {
                minuteFundsStockDataFactory.getAsyncRequestResult();
            }
            //3.
            GuMinuteDataUtils.minuteDataCustomZhangDieLiang(minutePriceData);
            //3.1 自定义-分钟资金量
            if (thsMinuteFundsStockDataFactory != null) {
                thsMinuteFundsStockDataFactory.decoratorData(minutePriceData);
            }
            //3.4 自定义-分钟大单资金量
            if (minuteFundsStockDataFactory != null) {
                minuteFundsStockDataFactory.decoratorData(minutePriceData);
            }

            //4. 分钟数据table
            TextTable textTable = TextTableExpand.standardJsonArrayTextTable(minutePriceData);
            stringBuilder.append(textTable.printTable());
            stringBuilder.append(System.getProperty("line.separator"));
            //4.1 自定义-分钟资金量
            if (thsMinuteFundsStockDataFactory != null) {
                thsMinuteFundsStockDataFactory.decoratorDataTableString(stringBuilder);
            }
            //4.2 自定义-分钟资金流势
            if (minuteFundDirectionsStockDataFactory != null) {
                minuteFundDirectionsStockDataFactory.decoratorDataTableString(stringBuilder);
            }
            //4.4 自定义-大单分钟资金量
            if (minuteFundsStockDataFactory != null) {
                minuteFundsStockDataFactory.decoratorDataTableString(stringBuilder);
            }
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


}
