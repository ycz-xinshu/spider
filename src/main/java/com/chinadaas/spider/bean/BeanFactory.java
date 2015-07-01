package com.chinadaas.spider.bean;

import org.springframework.jdbc.core.JdbcTemplate;

/**
 * projectName: tools<br>
 * desc: TODO<br>
 * date: 2014年10月10日 下午5:21:43<br>
 * 
 * @author 开发者真实姓名[Andy]
 */
public class BeanFactory {

	public static JdbcTemplate getJdbcTemplate() {
		return (JdbcTemplate) BeanVo.getBean("jdbcTemplate");
	}

}
