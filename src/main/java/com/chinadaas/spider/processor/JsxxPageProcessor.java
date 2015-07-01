package com.chinadaas.spider.processor;

import java.util.List;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

import com.chinadaas.spider.dao.BaseDao;

/**
 * projectName: spider<br>
 * desc: 爬取江苏学校<br>
 * date: 2015年3月13日 下午2:23:19<br>
 * @author 开发者真实姓名[Andy]
 */
public class JsxxPageProcessor implements PageProcessor {
	
	private Site site = Site.me().setTimeOut(10000).setRetryTimes(300).setSleepTime(100).setUserAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/40.0.2214.115 Safari/537.36");
	
	private BaseDao dao = new BaseDao();
	
	public JsxxPageProcessor() {
	}

	public void process(Page page) {
		List<String> rs = page.getHtml().xpath("//h4/a/text()").all();
		
		for (String name : rs) {
			dao.update("insert into s_jsxx values(?)", name);
		}
		
	}

	public Site getSite() {
		return site;
	}
	
	public static void main(String[] args) {
		String[] urls = new String[636];
		for (int i = 0; i < 636; i++) {
			urls[i] = "http://www.ruyile.com/xxlb.aspx?id=10&p="+(i+1);
		}
		Spider.create(new JsxxPageProcessor()).addUrl(urls).thread(100).run();
		
	}

}

