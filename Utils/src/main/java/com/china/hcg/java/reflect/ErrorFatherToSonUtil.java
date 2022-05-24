package com.china.hcg.java.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @autor hecaigui
 * @date 2021-3-24
 * @description
 */
public class ErrorFatherToSonUtil {
    /**
     * 通过get方法把父类的属性值赋值给子类
     *
     * @param father 父类
     * @param child  子类
     * @param <T>
     * @throws Exception
     */
    public static <T> void fatherToChild(T father, T child) throws Exception {
        if (child.getClass().getSuperclass() != father.getClass()) {
            throw new Error("child 不是 father 的子类");
        }
        Class<?> fatherClass = father.getClass();
        Field[] declaredFields = fatherClass.getDeclaredFields();
        //祖父类所有属性
        Class<?> supClass = fatherClass;
        while (supClass.getSuperclass() !=null){
            supClass = supClass.getSuperclass();
            if ("java.lang.Object".equals(supClass.getName())){
                break;
            } else{
                Field[] supClassDeclaredFields = supClass.getDeclaredFields();
                declaredFields = addAll(declaredFields,supClassDeclaredFields);
            }
        }
        for (int i = 0; i < declaredFields.length; i++) {
            Field field = declaredFields[i];
            Method method = fatherClass.getDeclaredMethod("get" + upperHeadChar(field.getName()));
            //？这里又缺失了祖父类的方法
            Object obj = method.invoke(father);
            field.setAccessible(true);
            field.set(child, obj);
        }
    }
    /**
     * 首字母修改为大写，例如：in:deleteDate --> out:DeleteDate
     */
    public static String upperHeadChar(String in) {
        String head = in.substring(0, 1);
        String out = head.toUpperCase() + in.substring(1, in.length());
        return out;
    }
    //todo:静态类空间的释放？
    //？java.lang.ArrayIndexOutOfBoundsException: 56
    private static  Field[] addAll(Field[] declaredFields1,Field[] declaredFields2){
        int leng1 = declaredFields1.length;
        int leng2 = declaredFields2.length;
        int leng = leng1 + leng2;
        Field[] newFields = new Field[leng];
        for (int i = 0;i < leng;i++){
            for (Field field : declaredFields1){
                newFields[i] = field;
                i++;
            }

            for (Field field : declaredFields2){
                newFields[i] = field;
                i++;
            }
        }
        return newFields;
    }
}
