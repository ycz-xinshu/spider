package com.chinadaas.spider.bean;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * projectName: tools<br>
 * desc: TODO<br>
 * date: 2014年10月10日 下午5:26:13<br>
 * @author 开发者真实姓名[Andy]
 */
public class BeanVo {

	static ApplicationContext context;
	
	static {
		if (context == null) {
			context = new ClassPathXmlApplicationContext("applicationContext.xml");
		}
	}
	
	public static Object getBean(String beanName) {
		return context.getBean(beanName);
	}
	
}

