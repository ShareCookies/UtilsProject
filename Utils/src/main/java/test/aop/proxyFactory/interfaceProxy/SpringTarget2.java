package test.aop.proxyFactory.interfaceProxy;

/**
 * @author Administrator
 *
 */
public class SpringTarget2 implements interfaceTarget{

	/**
	 * 
	 */
	public SpringTarget2() {
		// TODO Auto-generated constructor stub
	}
	@Override
	public void execute() {
		System.out.println("execute");
	}
}
