package com.china.hcg.applications.chao_gu;

import com.alibaba.fastjson.JSONArray;
import com.china.hcg.applications.chao_gu.dao.stockdata.StockDataTypes;
import com.china.hcg.applications.chao_gu.model.GuInfo;
import com.china.hcg.applications.chao_gu.utilsgu.GuMinuteDataNoticeUtils;
import com.china.hcg.applications.chao_gu.utilsgu.GuMinuteDataUtils;
import com.china.hcg.utils.date.DateUtil;
import com.china.hcg.utils.timer.JavaTimer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * @autor hecaigui
 * @date 2022-10-21
 * @description
 */
public class AutoGuMinuteData {
    private static final Logger logger= LoggerFactory.getLogger(AutoGuMinuteData.class);

    public static void main(String[] args) throws Exception{
        JavaTimer.loopTask(() ->{
            Calendar rightCalendar = Calendar.getInstance();//获取当前地区的日期信息
            int hour = rightCalendar.get(Calendar.HOUR_OF_DAY);
//            if (!(hour >= 9 && hour < 15)){
//                System.err.println("退出");
//                return;
//            }
            try {
                Map<String,GuInfo> guInfoMap = new HashMap();
                guInfoMap.put("600551",new GuInfo("600551","600551","sz",null,null));//36.725*0.04  35.26
                guInfoMap.put("603386",new GuInfo("603386","603386","sz",null,null));//36.725*0.04  35.26
                guInfoMap.put("002055",new GuInfo("002055","002055","sz",10.4f,null));//36.725*0.04  35.26
                guInfoMap.put("605289",new GuInfo("605289","605289","sz",null,null));//36.725*0.04  35.26
                guInfoMap.put("605287",new GuInfo("605287","605287","sz",null,null));//36.725*0.04  35.26
                guInfoMap.put("002849",new GuInfo("002849","002849","sz",18.32f,null));//36.725*0.04  35.26


//                guInfoMap.put("000776",new GuInfo("000776","广发证券","sz",null,null));//36.725*0.04  35.26
//
//                guInfoMap.put("002761",new GuInfo("002761","    ","sz",null,null));//36.725*0.04  35.26
//                guInfoMap.put("600691",new GuInfo("600691","阳煤化工","sz",null,null));//36.725*0.04  35.26
//                guInfoMap.put("600229",new GuInfo("600229","广发证券·","sz",null,null));//36.725*0.04  35.26
//                guInfoMap.put("600547",new GuInfo("600547","山东黄金","sz",null,null));//36.725*0.04  35.26
//                guInfoMap.put("300207",new GuInfo("300207","欣旺达","sz",null,null));//36.725*0.04  35.26
//                guInfoMap.put("600988",new GuInfo("600988","赤峰黄金","sz",null,null));//36.725*0.04  35.26
//                guInfoMap.put("600276",new GuInfo("600276","恒瑞医药","sh",null,null));
//                guInfoMap.put("601933",new GuInfo("601933","永辉超市","sz",null,null));
//                guInfoMap.put("000651",new GuInfo("000651","格力电器","sh",null,null));
//                guInfoMap.put("600741",new GuInfo("600741","华域汽车","sz",null,9.21f));
//                guInfoMap.put("600988",new GuInfo("600988","赤峰黄金","sh",null,null));
//                guInfoMap.put("601456",new GuInfo("601456","国联证券","sz",null,null));
//                guInfoMap.put("601318",new GuInfo("601318","中国平安","sz",null,null));
//                guInfoMap.put("002149",new GuInfo("002149","西部材料","sz",null,null));
//                guInfoMap.put("002027",new GuInfo("002027","分众传媒","sz",null,null));//6.78 2w
//                guInfoMap.put("002241",new GuInfo("002241","歌尔股份","sz",null,null));//3.85 (20.8+20.73)-20.56 //20.67 3%  20.07 //20.22

                List<GuInfo> list = new ArrayList();
                for (String s : guInfoMap.keySet()) {
                    list.add(guInfoMap.get(s));
                }
                List<Map<String,String>> minute_data_price = GuMinuteData.printLatestMinuteGuInfo(list);
                for (Map<String, String> map : minute_data_price) {
                    //价格提醒
                    GuMinuteDataNoticeUtils guMinuteDataNoticeUtils = new GuMinuteDataNoticeUtils(guInfoMap.get(map.get("guCode")));
                    guMinuteDataNoticeUtils.priceNotice(Float.valueOf(map.get("price")));
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        },60L);
    }
    void autoNotice() throws Exception {
        List<GuInfo> list = new ArrayList();
//        list.add(new GuInfo("000725","京东方a",5 * GuDataUtils.oneHundredMillion));
        //list.add(new GuInfo("002027","分众传媒",5 * GuMinuteDataUtils.oneHundredMillion,6.3F,6.18F));
        //list.add(new GuInfo("000792","盐湖股份",5 * GuMinuteDataUtils.oneHundredMillion,25.2F,24.95F));
        list.add(new GuInfo("002241","歌尔股份",10 * GuMinuteDataUtils.oneHundredMillion,18.6F,18.2F));
        //list.add(new GuInfo("000776","广发证券",18 * GuMinuteDataUtils.oneHundredMillion,null,null));
        //list.add(new GuInfo("600383","金地集团",15 * GuMinuteDataUtils.oneHundredMillion,10.75F,10.5F));
        for (GuInfo guInfo : list) {
            minuteGuInfoLoop(guInfo);
        }
    }



    static void minuteGuInfoLoop(GuInfo guInfo) throws Exception{
        JavaTimer.loopTask(new Runnable() {
            @Override
            public void run() {
                //开始条件判断
//                Calendar rightCalendar = Calendar.getInstance();
//                int hour = rightCalendar.get(Calendar.HOUR_OF_DAY);
//                if (hour < 9 || hour > 15){
//                    return;
//                }
                //todo：有报错要照样打印啊
                try {
                    logger.error("定时器"+DateUtil.dateToString(new Date(System.currentTimeMillis()),"yyyy-MM-dd HH:mm:ss"));
                    GuMinuteData.MinuteData minuteDataObj = GuMinuteData.minuteData(guInfo,new StockDataTypes[]{StockDataTypes.MinuteFundDirections,StockDataTypes.ThsMinuteFunds});
                    JSONArray minuteData = minuteDataObj.minutePriceData;
                    GuMinuteDataNoticeUtils guMinuteDataNoticeUtils = new GuMinuteDataNoticeUtils(guInfo);
                    guMinuteDataNoticeUtils.priceNotice(minuteData);
                    guMinuteDataNoticeUtils.totalAmountNotice(minuteData);
                    guMinuteDataNoticeUtils.largeFundsFlowNotice(minuteData);
                } catch (Exception | Error e){
                    e.printStackTrace();
                }
            }
        },60L);
    }


}
