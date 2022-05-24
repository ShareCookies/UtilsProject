/**
 * 
 */
package test.aop.proxyFactory;

import org.springframework.aop.framework.ProxyFactory;
import test.aop.proxyFactory.classProxy.SpringAddvice;
import test.aop.proxyFactory.classProxy.SpringTarget;
import test.aop.proxyFactory.interfaceProxy.SpringTarget2;
import test.aop.proxyFactory.interfaceProxy.interfaceTarget;

import java.lang.reflect.Proxy;

/**
 * @author Administrator
 *
 */
public class Test {


	/**
	 * 
	 */
	public Test() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//classProxy();

	}
	//代理对象未指定接口，则spring使用CGLIB生成代理类
	static void  classProxy(){
		SpringTarget target=new SpringTarget();//创建目标对象

		ProxyFactory di=new ProxyFactory();//创建代理工厂
		di.addAdvice(new SpringAddvice());//添加建议即添加通知，
		di.setTarget(target);//添加目标对象

		SpringTarget targetProxy=(SpringTarget)di.getProxy();//获得代理器，即获得已经被添加了通知的目标对象
		//targetProxy.execute();//执行修改过目标对象方法
		System.err.println(targetProxy.getClass().getName());
	}
	//代理对象指定接口，目标类为实现接口的类，则spring使用JDK proxy生成代理类
	static void interfaceProxy() {
		ProxyFactory factory = new ProxyFactory();
		factory.setInterfaces(new Class[] { interfaceTarget.class });
		factory.addAdvice(new SpringAddvice());
		factory.setTarget(new SpringTarget2());
		interfaceTarget peopleProxy = (interfaceTarget) factory.getProxy();
		peopleProxy.execute();
		System.err.println(peopleProxy.getClass().getName());
	}
}
