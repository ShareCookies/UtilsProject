package com.china.hcg.applications.chao_gu;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.china.hcg.http.HttpClientUtil;
import com.china.hcg.utils.StringUtils;
import com.china.hcg.utils.date.DateUtil;
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

//        JSONArray result = getMinuteFunds("sz","300274",500000);
//        System.err.println(result);
        BlockingQueue  q = new LinkedBlockingDeque();
        q.add("1");
        q.add("2");
        q.take();
        q.take();
        System.out.println("test");
        executorService.submit(()->{System.err.println(1);try {Thread.sleep(10000);} catch (InterruptedException e) {e.printStackTrace();} });
        executorService.submit(()->{System.err.println(2);try {Thread.sleep(10000);} catch (InterruptedException e) {e.printStackTrace();} });
        executorService.submit(()->{System.err.println(3);try {Thread.sleep(10000);} catch (InterruptedException e) {e.printStackTrace();} });
        executorService.submit(()->{System.err.println(4);try {Thread.sleep(10000);} catch (InterruptedException e) {e.printStackTrace();} });
//        executorService.submit(()->{System.err.println(1);try {Thread.sleep(10000);} catch (InterruptedException e) {e.printStackTrace();} });
//        executorService.submit(()->{System.err.println(1);try {Thread.sleep(10000);} catch (InterruptedException e) {e.printStackTrace();} });
//        executorService.submit(()->{System.err.println(1);try {Thread.sleep(10000);} catch (InterruptedException e) {e.printStackTrace();} });

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
        String r = HttpClientUtil.getWithHeader("https://vip.stock.finance.sina.com.cn/quotes_service/api/json_v2.php/CN_Bill.GetBillList?symbol="+stockArea+stockCode+"&num=60&page=1&sort=ticktime&asc=0&volume=0&amount="+amount+"&type=0&day="+nowDate,headers);
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
