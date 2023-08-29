package com.china.hcg.applications.chao_gu;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.china.hcg.applications.chao_gu.model.GuInfo;
import com.china.hcg.applications.chao_gu.utilscommon.StockThreadPoolUtil;
import com.china.hcg.applications.chao_gu.utilsgu.GuMinuteDataNoticeUtils;
import com.china.hcg.applications.chao_gu.utilsgu.ggzj;
import com.china.hcg.io.file.FileUtils;
import com.china.hcg.utils.timer.JavaTimer;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.*;
import java.util.concurrent.*;

/**
 * @autor hecaigui
 * @date 2023/7/13
 * @description
 */
public class GuDayData2 {
    //默认情况下，如果项目中集成了Logback等日志框架，在执行main方法时通过其进行日志打印，那么默认的日志级别是debug的。
    //http://www.choupangxia.com/2022/05/05/java-main-log-level/
    static {
        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        List<ch.qos.logback.classic.Logger> loggerList = loggerContext.getLoggerList();
        loggerList.forEach(logger -> {
            logger.setLevel(Level.INFO);
        });
    }
    public static void main(String[] args) throws InterruptedException {
//        //跑龙头
//        String arr = FileUtils.readTxtContent(new File("D:\\heSpace\\gu\\lt.txt"));
//        JSONArray jsonArray = JSONArray.parseArray(arr);
//        List<GuInfo> list = new LinkedList<>();
//        for (Object o : jsonArray) {
//            JSONObject jsonObject = (JSONObject) o;
//            GuInfo guInfo = new GuInfo(jsonObject.getString("code"),jsonObject.toJSONString());
//            list.add(guInfo);
//        }
//        GuDayData.printLatestTwoDay(list);

        //跑资金榜单
//        List<GuInfo> list = ggzj.start("3");
//        concurrencyPrint(list);

        //跑固定
        Set<GuInfo> list = new HashSet<>();
        list.add(new GuInfo("601158"));
        list.add(new GuInfo("601288"));
        list.add(new GuInfo("600919"));
        list.add(new GuInfo("601598"));
        list.add(new GuInfo("603979"));
        list.add(new GuInfo("002179"));
        list.add(new GuInfo("600028"));

        GuDayData2.Gucodes = list;
        autoRun();
    }
    public static List<GuInfo> getLt(){
        //OthersData.outLt();
        String arr = FileUtils.readTxtContent(new File("D:\\heSpace\\gu\\lt.txt"));
        JSONArray jsonArray = JSONArray.parseArray(arr);
        List<GuInfo> list = new LinkedList<>();
        for (Object o : jsonArray) {
            JSONObject jsonObject = (JSONObject) o;
            String name = "";
            for (String s : jsonObject.keySet()) {
                if (s.contains("龙头")) name += jsonObject.getString(s);
            }
            if (StringUtils.isBlank(name)) continue;

            name+=";"+jsonObject.getString("股票简称");
            GuInfo guInfo = new GuInfo(jsonObject.getString("code"),name);
            list.add(guInfo);
        }
        return list;
    }
    public static Set<GuInfo> Gucodes = new LinkedHashSet<GuInfo>();
    public static void autoRun(){
        JavaTimer.loopTask(() ->{
            try {
                List<GuInfo> guInfos = new ArrayList<>();
                guInfos.addAll(Gucodes);
                List<Map<String,String>> minute_data_price = GuMinuteData.printLatestMinuteGuInfo(guInfos);
            }catch (Exception e){

            }

        },5L);
    }

    static List<GuInfo> test(){
        List<GuInfo> list = new LinkedList<>();
//        list.add(new GuInfo("002905"));
        list.add(new GuInfo("002422"));
        System.err.println(GuDayData.printLatestTwoDay(list));
        return list;
    }
    public static ExecutorService executorService = new ThreadPoolExecutor(1, 200, 5, TimeUnit.SECONDS, new LinkedBlockingQueue<>(), new ThreadFactory() {
        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r,"Stock-concurrencyPrint");
        }
    });
    static void concurrencyPrint(List<GuInfo> list){
        for (GuInfo guInfo : list) {
            List<GuInfo>  l = new LinkedList<>();
            l.add(guInfo);
            GuDayData2.executorService.submit(() -> {
//                System.err.println("id"+Thread.currentThread().getId());
                String s = GuDayData.printMinuteData(l);
                System.err.println(s);
                if (StringUtils.isNotBlank(s)){
                    Gucodes.addAll(l);
                }
            });
        }

//        CompletableFuture[] tasks = new CompletableFuture[list.size()];
//        for (int i = 0; i < list.size(); i++) {
//            List  l = new LinkedList<>();
//            l.add(list.get(i));
//            CompletableFuture future1 = CompletableFuture.supplyAsync(() -> {
////                String s = GuDayData.printLatestTwoDay(l);
////                return s;
//                try {
//                    Thread.sleep(10);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                return String.valueOf(Thread.currentThread().getId());
//            }, StockThreadPoolUtil.executorService);
//            tasks[i] = future1;
//        }
//        for (CompletableFuture completableFuture : tasks) {
//            try {
//                Object object = completableFuture.get(60, TimeUnit.SECONDS);
//                System.err.println((String) object);
//            } catch (InterruptedException | ExecutionException e) {
//                e.printStackTrace();
//            } catch (Exception e){
//                e.printStackTrace();
//            }
//        }
    }
}
