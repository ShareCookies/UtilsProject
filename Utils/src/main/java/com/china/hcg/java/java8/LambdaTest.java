package com.china.hcg.java.java8;

/**
 * @autor hecaigui
 * @date 2021-4-22
 * @description
 */
public class LambdaTest {
	public static void main(String args[]){
		LambdaTest tester = new LambdaTest();

		// 1.
		MathOperation addition = (int a, int b) -> a + b;

		System.out.println("10 + 5 = " + tester.operate(10, 5, addition));

		// 2.
		GreetingService greetService1 = message ->
				System.out.println("Hello " + message);

		greetService1.sayMessage("Runoob");


	}

	interface MathOperation {
		int operation(int a, int b);
	}

	interface GreetingService {
		void sayMessage(String message);
	}

	private int operate(int a, int b, MathOperation mathOperation){
		return mathOperation.operation(a, b);
	}
}
