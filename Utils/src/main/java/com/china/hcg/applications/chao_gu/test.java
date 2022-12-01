package com.china.hcg.applications.chao_gu;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.china.hcg.http.HttpClientUtil;
import com.china.hcg.utils.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

        //* ？日志
        List<test1> users = new ArrayList<>();
        test1 t = new test1("111");users.add(t);
        System.err.println(JSON.toJSONString(users));
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
