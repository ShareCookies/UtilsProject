package com.china.hcg.AlgorithmAndStructure;

import java.util.ArrayList;
import java.util.List;

/**
 * @autor hecaigui
 * @date 2019-9-14
 * @description
 */
public class ListDivisionUtil {

    /**
     * list分割成若干个list.
     * n过大，则后续会有[]
     * <p>
     *         调用案例：
     *         List<List<EgovCommonContacts>> averageList= averageList(list,list.size()/200<1?1:list.size()/200);
     *         for (List<EgovCommonContacts> insertList: averageList) {
     *             result+=this.egovCommonContactDao.insertEgovCommonContactsByBatch(insertList);
     *         }
     * </p>
     * @param list 要切割的list
     * @param n 割成n个list
     * @return
     */
    public static <T> List<List<T>> averageList(List<T> list, int n){
        List<List<T>> result=new ArrayList<List<T>>();
        int remaider=list.size()%n;  //(先计算出余数)
        int number=list.size()/n;  //然后是商
        int offset=0;//偏移量
        for(int i=0;i<n;i++){
            List<T> value=null;
            if(remaider>0){
                value=list.subList(i*number+offset, (i+1)*number+offset+1);
                remaider--;
                offset++;
            }else{
                value=list.subList(i*number+offset, (i+1)*number+offset);
            }
            result.add(value);
        }
        return result;
    }

    /**
     * 根据list分割成若干个list
     * @param list 要切割的list
     * @param divisionStandard 例：50 以50为标准来切割list. 所以101会被切割成3个list
     * @return
     */
    public static <T> List<List<T>> divisionList(List<T> list, int divisionStandard){
        List<List<T>> result=new ArrayList<List<T>>();
        if (divisionStandard ==0 ){
            result.add(list);
            return result;
        }
        int n = list.size()/divisionStandard;
        if (list.size()%divisionStandard > 0){
            n++;
        }
        return  averageList(list,n);
    }

    public static void main(String[] args) {
        List<String> test = new ArrayList<>();
        test.add("1");

        List<List<String>> lists = divisionList(test,2);

        List<List<String>> lists2 = averageList(test,2);
        System.err.println(lists);
    }
}
