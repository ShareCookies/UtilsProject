package test;

import java.util.List;

/**
 * @autor hecaigui
 * @date 2021-11-22
 * @description
 * 泛型:泛型可用于类、接口、方法的定义 //泛型指明这里将只能用一个类型，但这里类型还未定义。
 * 泛型通配符：通配符是配合泛型实例化用的。通过变量(或形参)接收，实例化泛型类等时，可在变量的<>中通过通配符为泛型指明实例化的泛型范围 //用来为泛型指明范围的。
 */
public class GenericTest {
    class Father{}
    class son1 extends Father{}
    class son2 extends Father{}
    class Test{}


    //泛型类
    public class Generic<T>{
        private T key;//key这个成员变量的类型为T,T的类型由外部指定
        public Generic(T key) { //泛型构造方法形参key的类型也为T，T的类型由外部指定
            this.key = key;
        }
        public T getKey(){ //泛型方法getKey的返回值类型为T，T的类型由外部指定
            return key;
        }
    }
    //泛型上下边界通配符
    //为泛型添加上下边界，即传入的类型实参必须是指定类型的子类型
    Generic<? extends  Father> generic = new Generic<Father>(new Father());
    //无限定通配符
    void setFirst(Generic<?> test){}
    public void processElements(List<?> elements){
        for(Object a : elements){

        }
    }
}
