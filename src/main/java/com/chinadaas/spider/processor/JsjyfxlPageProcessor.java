package com.chinadaas.spider.processor;

import java.util.List;

import com.chinadaas.spider.dao.BaseDao;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * projectName: spider<br>
 * desc: 爬取江苏非学历教育<br>
 * date: 2015年3月13日 下午2:23:19<br>
 * @author 开发者真实姓名[Andy]
 */
public class JsjyfxlPageProcessor implements PageProcessor {
	
	private Site site = Site.me().setRetryTimes(3).setSleepTime(100);
	
	private BaseDao dao = new BaseDao();
	
	public JsjyfxlPageProcessor() {
	}

	public void process(Page page) {
		List<String> rs = page.getHtml().xpath("//span[@title]/text()").all();
		
		for (int i = 0; i < rs.size(); i += 6) {
			dao.update("insert into s_jyfxl values(?, ?, ?, ?, ?, ?)", 
					rs.get(i+2), rs.get(i), rs.get(i+1), rs.get(i+3), rs.get(i+4), rs.get(i+5));
		}
		
	}

	public Site getSite() {
		return site;
	}
	
	public static void main(String[] args) {
		String[] urls = new String[260];
		for (int i = 0; i < 260; i++) {
			urls[i] = "http://www.js-study.cn/course/management/management.action?page.pageNo="+(i+1);
		}
		Spider.create(new JsjyfxlPageProcessor()).addUrl("http://www.js-study.cn/course/management/management.action?page.pageSize=2600").thread(100).run();
	}

}

