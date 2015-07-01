package com.chinadaas.spider.processor;

import java.util.List;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

import com.chinadaas.spider.dao.BaseDao;

/**
 * projectName: spider<br>
 * desc: 爬取江苏社会组织<br>
 * date: 2015年3月13日 下午2:23:19<br>
 * @author 开发者真实姓名[Andy]
 */
public class JsshzzPageProcessor implements PageProcessor {
	
	private Site site = Site.me().setTimeOut(10000).setRetryTimes(300).setSleepTime(100).setUserAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/40.0.2214.115 Safari/537.36");
	
	private BaseDao dao = new BaseDao();
	
	public JsshzzPageProcessor() {
	}

	public void process(Page page) {
		List<String> rs = page.getHtml().xpath("//input[@type='text']/@value").all();
		
		dao.update("insert into s_jsshzz values(?, ?, ?, ?, ?, ?, ?, ?, '')", 
				rs.get(0), rs.get(1), rs.get(2), rs.get(3), rs.get(4), rs.get(5), rs.get(6), rs.get(7));
		
	}

	public Site getSite() {
		return site;
	}
	
	public static void main(String[] args) {
		String[] urls = new String[1690];
		for (int i = 0; i < 1690; i++) {
			urls[i] = "http://mjzz.jsmz.gov.cn/manager/frmOnlineAppView.aspx?THREADNO="+(i+1);
		}
		Spider.create(new JsshzzPageProcessor()).addUrl(urls).thread(100).run();
		
	}

}

