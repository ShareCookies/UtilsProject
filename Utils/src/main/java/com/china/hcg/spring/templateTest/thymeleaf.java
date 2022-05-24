package com.china.hcg.spring.templateTest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;


/**
 * @autor hecaigui
 * @date 2021-5-12
 * @description
 */
@RunWith(SpringRunner.class)
//@SpringBootTest(classes = ApplicationConfiguration.class)
@SpringBootTest
public class thymeleaf {
	@Autowired
	private TemplateEngine templateEngine;

	@Test
	public void thymeleaf() {
		Context context = new Context();
		context.setVariable("name", "xxxxxxxxx");
		String result = templateEngine.process("test", context);
		System.err.println("==============");
		System.err.println(result);
		System.err.println("==============");
	}
}
