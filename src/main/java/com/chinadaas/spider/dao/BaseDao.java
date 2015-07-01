package com.chinadaas.spider.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.chinadaas.spider.bean.BeanFactory;


/**
 * projectName: tools<br>
 * desc: TODO<br>
 * date: 2014年10月10日 下午4:43:33<br>
 * @author 开发者真实姓名[Andy]
 */
public class BaseDao {
	
	private static Logger logger = Logger.getLogger(BaseDao.class);
	
	/**
	 * desc: 执行数据库更新语句<br>
	 * date: 2014年10月30日 上午10:08:47<br>
	 * @author 开发者真实姓名[Andy]
	 * @param sql
	 * @return
	 * @throws RunnerException
	 */
	public int update(String sql, Object... params) {
		JdbcTemplate jdbcTemplate = BeanFactory.getJdbcTemplate();
		return jdbcTemplate.update(sql, params);
	}
	
	/**
	 * desc: 执行数据库查询语句,返回map列表<br>
	 * date: 2015年3月13日 下午5:13:16<br>
	 * @author 开发者真实姓名[Andy]
	 * @param sql
	 * @param params
	 * @return
	 */
	public List<Map<String, Object>> query(String sql, Object[] params) {
		JdbcTemplate jdbcTemplate = BeanFactory.getJdbcTemplate();
		return jdbcTemplate.queryForList(sql, params);
	}
	
	/**
	 * desc: 执行数据库查询语句，返回对象列表<br>
	 * date: 2015年3月13日 下午5:44:25<br>
	 * @author 开发者真实姓名[Andy]
	 * @param sql
	 * @param params
	 * @param rowMapper
	 * @return
	 */
	public List<?> query(String sql, Object[] params, RowMapper<?> rowMapper) {
		JdbcTemplate jdbcTemplate = BeanFactory.getJdbcTemplate();
		return jdbcTemplate.query(sql, params, rowMapper);
	}
	
	public static void main(String[] args) {
		List<String> a = new ArrayList<String>();
		a.add("123");
		
		List<String> b = new ArrayList<String>();
		b.addAll(a);
		a.clear();
		System.out.println(b.size());
 	}
	
}

