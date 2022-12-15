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
