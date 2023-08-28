package com.china.hcg.applications.chao_gu.utilsgu;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.china.hcg.applications.chao_gu.model.GuInfo;
import com.china.hcg.utils.windows.InfoUtil;

/**
 * @autor hecaigui
 * @date 2022-11-14
 * @description
 */
public class GuMinuteDataNoticeUtils {
    private GuInfo guInfo;

    public GuMinuteDataNoticeUtils(GuInfo guInfo) {
        this.guInfo = guInfo;
    }

    /**
     * @description 价格提醒
     * @author hecaigui
     * @date 2022-11-10
     * @param minuteData
     * @return
     */
    public void priceNotice(JSONArray minuteData){
        JSONObject minute_data_price = minuteData.getJSONObject(minuteData.size()-1);
        priceNotice(minute_data_price.getFloat("price"));
    }
    public void priceNotice(Float price){
        if (guInfo.noticePriceRise == null && guInfo.noticePriceFall == null){
            return;
        }

        if(guInfo.noticePriceRise != null && price >= guInfo.noticePriceRise){
            InfoUtil test = new InfoUtil();
            test.show(guInfo.code+"价格提醒","涨价提醒"+guInfo.noticePriceRise+InfoUtil.breakLine+"当前价格"+price);
        }
        if (guInfo.noticePriceFall != null && price <= guInfo.noticePriceFall ){
            InfoUtil test = new InfoUtil();
            test.show(guInfo.code+"价格提醒","跌价提醒" + guInfo.noticePriceFall+InfoUtil.breakLine+"当前价格"+price);
        }
    }

    /**
     * @description 大额资金出逃提醒
     * @author hecaigui
     * @date 2022-11-10
     * @param minuteData
     * @return
     */
    public void largeFundsFlowNotice(JSONArray minuteData){
        if (minuteData.size() < 1){
            return;
        }

        try {
            int newPosition = minuteData.size()-1;
            try {
                while (newPosition >= 1 && minuteData.getJSONObject(newPosition).getString("资金净额趋势(大单、中单、小单)").length() < 15 ){
                    --newPosition;
                }
            }catch (NullPointerException e){
                //？todo：线程异常了会怎样
                throw new Error("请在调用该方法前，提前调用minuteDataCustomZhangDieLiang，自定义数据");
            }

            JSONObject newMinute_data_price = minuteData.getJSONObject(newPosition);
            JSONObject preMinute_data_price = minuteData.getJSONObject(newPosition-1);
            String[] newData = newMinute_data_price.getString("资金净额趋势(大单、中单、小单)").split(";");
            String[] preData = preMinute_data_price.getString("资金净额趋势(大单、中单、小单)").split(";");
            double newLargeFundsnewData = Double.valueOf(newData[1]);
            double preLargeFundsnewData = Double.valueOf(preData[1]);

            double largeFundsFlow = newLargeFundsnewData - preLargeFundsnewData;

            // 大额资金出逃超过500w
            //-500
            if (guInfo.noticeFundsInstantRun !=null && largeFundsFlow < guInfo.noticeFundsInstantRun){
                //"当前价格"+minute_data_price.getFloat("price")

                StringBuilder notice = new StringBuilder();
                notice.append("大额资金卖出"+largeFundsFlow);
                notice.append(InfoUtil.breakLine);
                notice.append(InfoUtil.breakLine);

                notice.append(preData[0]+"资金净额"+preMinute_data_price.getString("资金净额趋势(大单、中单、小单)"));
                notice.append(InfoUtil.breakLine);
                notice.append(newData[0]+"资金净额"+newMinute_data_price.getString("资金净额趋势(大单、中单、小单)"));
                notice.append(InfoUtil.breakLine);


                InfoUtil test = new InfoUtil();
                test.show(guInfo.name+"大额资金出逃提醒",notice.toString());
            }
            // 大额资金买入超过100w
            if (guInfo.noticeFundsInstantEnter != null && largeFundsFlow > guInfo.noticeFundsInstantEnter){
                StringBuilder notice = new StringBuilder();
                notice.append("大额资金买入"+largeFundsFlow);
                notice.append(InfoUtil.breakLine);
                notice.append(InfoUtil.breakLine);

                notice.append(preData[0]+"资金净额"+preMinute_data_price.getString("资金净额趋势(大单、中单、小单)"));
                notice.append(InfoUtil.breakLine);
                notice.append(newData[0]+"资金净额"+newMinute_data_price.getString("资金净额趋势(大单、中单、小单)"));
                notice.append(InfoUtil.breakLine);
                InfoUtil test = new InfoUtil();
                test.show(guInfo.name+"大额资金买入提醒",notice.toString());
            }
        } catch (Exception e){
            e.printStackTrace();
            System.err.println("largeFundsFlowNotice报错");
        }
    }

    /**
     * @description 交易总金额提醒
     * @author hecaigui
     * @date 2022-11-14
     * @param minuteData
     * @return
     */
    public void totalAmountNotice(JSONArray minuteData){
        int largeTotalAmount = 0;
        int totalAmount = 0;
        //提醒参照值
        for (int i = 0; i < minuteData.size(); i++) {
            JSONObject minute_data_price = minuteData.getJSONObject(i);

            //
            float amountValue = GuMinuteDataUtils.minuteComputeAmountValue(minute_data_price);
            totalAmount += amountValue;
            if (amountValue > 5000000){
                largeTotalAmount += amountValue;
                //大额提醒

                if (largeTotalAmount > guInfo.getTotalAmountReference()){
                    //每提醒一次，参照值自动新增5千万
                    guInfo.setTotalAmountReference(guInfo.getTotalAmountReference() + 5 * GuMinuteDataUtils.tenMillion);
                    InfoUtil test = new InfoUtil();
                    test.show(guInfo.getName()+"当前大额放量交易量达到", (double) largeTotalAmount / GuMinuteDataUtils.oneHundredMillion + "亿;"
                            +InfoUtil.breakLine+""+"当前交易总额量"+ totalAmount / GuMinuteDataUtils.oneHundredMillion + "亿");
                }
            }
        }
    }
}
