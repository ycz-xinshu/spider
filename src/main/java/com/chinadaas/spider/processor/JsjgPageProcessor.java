package com.chinadaas.spider.processor;

import java.util.List;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

import com.chinadaas.spider.dao.BaseDao;

/**
 * projectName: spider<br>
 * desc: 爬取江苏企业<br>
 * date: 2015年3月13日 下午2:23:19<br>
 * @author 开发者真实姓名[Andy]
 */
public class JsjgPageProcessor implements PageProcessor {
	
	private Site site = Site.me().setTimeOut(10000).setRetryTimes(300).setSleepTime(100).setUserAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/40.0.2214.115 Safari/537.36");
	
	private BaseDao dao = new BaseDao();
	
	public JsjgPageProcessor() {
	}

	public void process(Page page) {
		List<String> rs = page.getHtml().xpath("//td[@width='55%']/text()").all();
		
		for (int i = 0; i < rs.size(); i += 6) {
			String line = rs.get(i);
			if(line != null && line.trim().length() > 0) {
				dao.update("insert into s_jsjg values(?)", line.trim());
			}
		}
		
	}

	public Site getSite() {
		return site;
	}
	
	public static void main(String[] args) {
		String[] urls = new String[10423];
		for (int i = 0; i < 10423; i++) {
			urls[i] = "http://www.jssi.org.cn:8080/demo1/gqsj_gg.jsp?dipage="+(i+1);
		}
		Spider.create(new JsjgPageProcessor()).addUrl(urls).thread(100).run();
		
	}

}

