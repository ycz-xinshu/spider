package com.chinadaas.spider.processor;

import java.util.List;

import com.chinadaas.spider.dao.BaseDao;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * projectName: spider<br>
 * desc: 爬取江苏分类链接<br>
 * date: 2015年3月13日 下午2:23:19<br>
 * @author 开发者真实姓名[Andy]
 */
@Deprecated
public class JstagPageProcessor implements PageProcessor {
	
	private Site site = Site.me().setRetryTimes(3).setSleepTime(100);
	
	private BaseDao dao = new BaseDao();
	
	public JstagPageProcessor() {
		dao.update("delete from s_tag");
		this.site.setUserAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/40.0.2214.115 Safari/537.36");
	}

	public void process(Page page) {
		List<String> rs = page.getHtml().regex("<li><a href=\"(http://b2b.huangye88.com/jiangsu/.*?)\"").all();
		
		for (String url : rs) {
			dao.update("insert into s_tag values(?)", url);
		}
	}

	public Site getSite() {
		return site;
	}
	
	public static void main(String[] args) {
		Spider.create(new JstagPageProcessor()).addUrl("http://b2b.huangye88.com/jiangsu/").thread(1).run();
	}

}

