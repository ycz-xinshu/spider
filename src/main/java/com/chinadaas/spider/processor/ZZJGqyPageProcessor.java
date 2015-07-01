package com.chinadaas.spider.processor;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

import com.chinadaas.spider.dao.BaseDao;

/**
 * projectName: spider<br>
 * desc: 爬取企业型组织机构代码<br>
 * date: 2015年3月13日 下午2:23:19<br>
 * @author 开发者真实姓名[Andy]
 */
public class ZZJGqyPageProcessor implements PageProcessor {
	
	private Site site = Site.me().setRetryTimes(30).setSleepTime(100);
	
	private BaseDao dao = new BaseDao();
	
	public BaseDao getDao() {
		return dao;
	}

	public ZZJGqyPageProcessor() {
	}

	public void process(Page page) {
		List<String> rs = page.getHtml().xpath("//td/text()").all();
		//4,8,12,16,19,21,27,32
		if(rs.size() >= 41) {
			dao.update("insert into s_zzjg values(?, ?, ?, ?, ?, ?, ?, ?, '企业')", rs.get(4), rs.get(8), 
				rs.get(12), rs.get(16), rs.get(19), rs.get(21), rs.get(27), rs.get(32));
		}
		try {
			String name = URLDecoder.decode(page.getRequest().getUrl().split("=")[1], "gbk");
			dao.update("insert into s_zzjg values(?, ?, ?, ?, ?, ?, ?, ?, '企业')", "", name, 
					"", "", "", "", "", "");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public Site getSite() {
		return site;
	}
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader("xx.txt"));
		String line = null;
		List<String> urls = new ArrayList<String>();
		do {
			line = br.readLine();
			try {
				urls.add("http://www.jsdm.org.cn:8080/demo1/CX-1.jsp?jgmc=" + URLEncoder.encode(line, "gbk"));
			} catch (Exception e) {
				System.out.println(line);
				continue;
			}
		} while(line != null && line.length() > 0);
		
		String[] ss = new String[urls.size()];
		Spider.create(new ZZJGqyPageProcessor()).addUrl(urls.toArray(ss)).thread(500).run();
	}

}

