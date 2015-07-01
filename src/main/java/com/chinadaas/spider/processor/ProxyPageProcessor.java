package com.chinadaas.spider.processor;

import java.util.List;

import com.chinadaas.spider.dao.BaseDao;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * projectName: spider<br>
 * desc: 爬取代理<br>
 * date: 2015年3月13日 下午2:23:19<br>
 * @author 开发者真实姓名[Andy]
 */
public class ProxyPageProcessor implements PageProcessor {
	
	private Site site = Site.me().setRetryTimes(3).setSleepTime(100);
	
	private BaseDao dao = new BaseDao();
	
	public ProxyPageProcessor() {
		dao.update("delete from s_proxy");
	}

	public void process(Page page) {
		List<String> rs = page.getHtml().xpath("//td[@valign='top']/font//td/text()").all();
		
		for (int i = 6; i < rs.size(); i += 5) {
			dao.update("insert into s_proxy values(?, ?)", rs.get(i), rs.get(i+1));
		}
		
		page.addTargetRequests(page.getHtml().xpath("//td[@bgcolor='#D3D3D3']").links().all());
	}

	public Site getSite() {
		return site;
	}
	
	public static void main(String[] args) {
		Spider.create(new ProxyPageProcessor()).addUrl("http://www.proxy.com.ru/").thread(5).run();
	}

}

