package com.chinadaas.spider.processor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.http.HttpHost;
import org.springframework.jdbc.core.RowMapper;

import com.chinadaas.spider.dao.BaseDao;
import com.chinadaas.spider.downloader.ProxyDownloader;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * projectName: spider<br>
 * desc: 爬取江苏企业<br>
 * date: 2015年3月13日 下午2:23:19<br>
 * @author 开发者真实姓名[Andy]
 */
@Deprecated
public class JsentPageProcessor implements PageProcessor {
	
	private Site site = Site.me().setRetryTimes(300000000).setSleepTime(10);
	
	private BaseDao dao = new BaseDao();
	
	public BaseDao getDao() {
		return dao;
	}

	public JsentPageProcessor() {
		this.site.setUserAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/40.0.2214.115 Safari/537.36");
		List<String[]> proxy_list = (List<String[]>) dao.query("select ip, port from s_proxy", new Object[]{}, new RowMapper<String[]>() {

			public String[] mapRow(ResultSet rs, int rowNum) throws SQLException {
				String[] record = {rs.getString("ip"), rs.getString("port")};
				return record;
			}
		});
//		site.setHttpProxyPool(proxy_list);
		site.setHttpProxy(new HttpHost("61.152.102.40", 8080));
	}

	public void process(Page page) {
		List<String> rs = page.getHtml().xpath("//h4/a/text()").all();
		
		for (String ent_name : rs) {
			dao.update("insert into s_ent values(?)", ent_name);
		}
		
		page.addTargetRequests(page.getHtml().xpath("//div[@class='page_tag Baidu_paging_indicator']").links().all());
	}

	public Site getSite() {
		return site;
	}
	
	public static void main(String[] args) {
		JsentPageProcessor pp = new JsentPageProcessor();
		List<String> tag_url_list = (List<String>) pp.getDao().query("select url from s_tag",
				new Object[]{}, new RowMapper<String>() {

			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getString("url");
			}
		});
		String[] tags = new String[tag_url_list.size()];
		
		Spider.create(pp).addUrl(tag_url_list.toArray(tags)).setDownloader(new ProxyDownloader()).thread(tag_url_list.size()).run();
	}

}

