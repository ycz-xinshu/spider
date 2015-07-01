package com.chinadaas.spider.processor;

import java.util.List;

import com.chinadaas.spider.dao.BaseDao;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * projectName: spider<br>
 * desc: 爬取上海事业单位<br>
 * date: 2015年3月13日 下午2:23:19<br>
 * @author 开发者真实姓名[Andy]
 */
public class ShsydwPageProcessor implements PageProcessor {
	
	private Site site = Site.me().setRetryTimes(3).setSleepTime(100);
	
	private BaseDao dao = new BaseDao();
	
	public ShsydwPageProcessor() {
	}

	public void process(Page page) {
		List<String> rs = page.getHtml().xpath("//tr[@class='cursor']/td/text()").all();
		
		for (int i = 0; i < rs.size(); i += 6) {
			dao.update("insert into s_sydw values(?, ?, ?, ?, ?)", rs.get(i), rs.get(i+1), rs.get(i+2), rs.get(i+3), rs.get(i+5));
		}
		
	}

	public Site getSite() {
		return site;
	}
	
	public static void main(String[] args) {
		Spider.create(new ShsydwPageProcessor()).addUrl("http://www.sydjsh.cn/ndbg.do?type=4&pageSize=7000").thread(1).run();
	}

}

