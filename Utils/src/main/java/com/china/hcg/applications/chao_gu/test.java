package com.china.hcg.applications.chao_gu;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.china.hcg.applications.chao_gu.model.GuInfo;
import com.china.hcg.applications.chao_gu.utilsgu.GuMinuteDataNoticeUtils;
import com.china.hcg.applications.chao_gu.utilsgu.GuMinuteDataUtils;
import com.china.hcg.http.HttpClientUtil;
import com.china.hcg.utils.StringUtils;
import com.china.hcg.utils.date.DateUtil;
import com.china.hcg.utils.timer.JavaTimer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;

import javax.validation.constraints.NotNull;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.*;

/**
 * @autor hecaigui
 * @date 2022-11-7
 * @description
 */
public class test {
    private static final Logger test= LoggerFactory.getLogger(test.class);
    //private static Log log = LogFactory.getLog(test.class);
    static boolean  testboolean;
    public static void main(String[] args) throws InterruptedException, ExecutionException, IOException {

        //?
//        executorService.submit
//        executorService.execute
//        callable原理？
//
//        //匿名内部类为什么可以访问到外部变量?
//        FlowOpenInterface2 flowOpenInterface2 = this;
//        Future<TodoAndToReadVo> urgerFlowResultFuture = mixUrgerTododuChaThreadPoolExecutor.submit(new Callable<TodoAndToReadVo>() {
//            @Override
//            public TodoAndToReadVo call() throws Exception {
//                TodoAndToReadVo urgerFlowResult = flowOpenInterface2.getTodoAndToRead(todoAndToReadDto);
//                return null;
//            }
//        });

//        JSONArray result = getMinuteFunds("sz","002241",500000);
//        System.err.println(result);
//        System.err.println(1000000000 * 2.2);
//        BigDecimal superBigAmountTotal = new BigDecimal("-75327617.000");
//        System.err.println(superBigAmountTotal.divide(new BigDecimal(GuMinuteDataUtils.tenThousand),1,BigDecimal.ROUND_HALF_UP) +"万;");

//        JavaTimer.loopTask(() ->{
//            try {
//
//                System.err.println("开始运行JavaTimer");
//                throw new RuntimeException("抛错了，线程什么状态");
//            }catch (Exception e){e.printStackTrace();}
//        },60L);
        List<String> sfIdList = Arrays.asList("DISPATCH","RECEIVAL","REQUEST" ,"approval");
        sfIdList = new ArrayList<>();
        sfIdList.add("6176");


        int size = sfIdList.size();
        int loopCount = size / 100 + 1;
        for (int i = 0; i < loopCount; i++) {
            int begin = i * 100;
            int end = Math.min((i + 1) * 100, sfIdList.size());
            List<String> list = sfIdList.subList(begin, end);
//            List<UserPxInfo> temp = orgService.getPxInfo(list);
//            if (temp != null) {
//                pxInfos.addAll(temp);
//            }
            System.err.println(111);
        }
    }
    static int num = 0;
    static ExecutorService  executorService = new ThreadPoolExecutor(1,100,5,TimeUnit.SECONDS,new SynchronousQueue<>(), new ThreadFactory() {
        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(r);
            t.setName("线程"+(num++));
            System.out.println("创建线程"+t.getName());
            return  t;
        }
    });
    
    private static JSONArray getMinuteFunds(@NotNull String stockArea,@NotNull String stockCode,@NotNull Integer amount){
        String nowDate = DateUtil.dateToString(new Date(),"yyyy-MM-dd");
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.USER_AGENT,"Mozilla/5.0 (Macintosh; Intel Mac OS X 10.6; rv:9.0) Gecko/20100101 Firefox/9.0");
        headers.set(HttpHeaders.REFERER,"https://vip.stock.finance.sina.com.cn/quotes_service/view/cn_bill.php?symbol="+stockArea+stockCode);
        String r = HttpClientUtil.getWithHeader("https://vip.stock.finance.sina.com.cn/quotes_service/api/json_v2.php/CN_Bill.GetBillList?symbol="+stockArea+stockCode+"&num=6000&page=1&sort=ticktime&asc=0&volume=0&amount="+amount+"&type=0&day="+nowDate,headers);
        JSONArray result = JSONArray.parseArray(r);
        return result;
    }

    //单独一个提示线程
    //todo：随着提醒越来越频繁 这个要渐渐提上日程
    void independentShow(){
//        InfoUtil test = new InfoUtil();
//        test.show(name+"    大额交易超过亿", "达到"+largeTotalAmount / 10000 + "万");
    }
    static class test1{
        String bb;

        public test1(String test) {
            this.bb = test;
        }

        public String getBb() {
            return bb;
        }

        public void setBb(String bb) {
            this.bb = bb;
        }
    }
}
