package com.cai310.lottery.task.dczc;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.SortedMap;


import javax.annotation.PostConstruct;
import org.apache.http.impl.cookie.DateUtils;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.output.XMLOutputter;
import org.mortbay.log.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;


import com.cai310.lottery.Constant;
import com.cai310.lottery.FetchConstant;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.common.TaskType;
import com.cai310.lottery.entity.lottery.Period;
import com.cai310.lottery.entity.lottery.dczc.DczcSpInfo;
import com.cai310.lottery.fetch.DataModel;
import com.cai310.lottery.fetch.FetchDataBean;
import com.cai310.lottery.fetch.Item;
import com.cai310.lottery.fetch.ValueItem;
import com.cai310.lottery.fetch.dczc.DczcAbstractFetchParser;
import com.cai310.lottery.fetch.dczc.DczcBFOkoooFetchParser;
import com.cai310.lottery.fetch.dczc.DczcBQQSPFOkoooFetchParser;
import com.cai310.lottery.fetch.dczc.DczcContextHolder;
import com.cai310.lottery.fetch.dczc.DczcFetchResult;
import com.cai310.lottery.fetch.dczc.DczcSPFOkoooFetchParser;
import com.cai310.lottery.fetch.dczc.DczcSXDSOkoooFetchParser;
import com.cai310.lottery.fetch.dczc.DczcZJQSOkoooFetchParser;
import com.cai310.lottery.service.lottery.PeriodEntityManager;
import com.cai310.lottery.service.lottery.dczc.DczcMatchEntityManager;
import com.cai310.lottery.support.dczc.PlayType;
import com.cai310.lottery.task.BaseFetchTask;
import com.cai310.lottery.utils.HttpUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class DczcXYOFetchTask{
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	static Pattern p1 = Pattern.compile(FetchConstant.DCZC_XYO);
	static Pattern p2 = Pattern.compile(FetchConstant.OYP);

	public void fetchData() {
		this.logger.info("[{}]线程开始执行,析亚欧...");

		String content;
		try {
			content = HttpUtil.getContentByURL(FetchConstant.XYO_URL, false,"utf-8");
			ArrayList datas = fiterContent(content);
			BuildXMLDoc(datas,"xyo.xml");
			
			content = HttpUtil.getContentByURL(FetchConstant.SFCURL, false,"utf-8");
			ArrayList sfcDatas = fiterContent(content);
			BuildXMLDoc(sfcDatas,"sfc_xyo.xml");
			
			/*content = HttpUtil.getContentByURL(FetchConstant.BQCURL, false,"utf-8");
			ArrayList bqcDatas = fiterContent(content);
			BuildXMLDoc(sfcDatas,"bqc_xyo.xml");*/
		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.logger.info("[{}]线程开始执行结束,析亚欧...");
	}

	public void fetchOYPData(){
		this.logger.info("[{}]线程开始执行,欧亚盘指数...");
		String year = DateUtils.formatDate(new Date(), "yyyy");
		String content;
		try {
			String url = "http://www.cailele.com/lottery/dcspf/spfhomeAction.php?term=";
			String pContent = HttpUtil.getContentByURL(FetchConstant.CURPERIOD_URL,false);			
			Pattern p4 = Pattern.compile(FetchConstant.CUR_PERIOD);			
			Matcher m = p4.matcher(pContent); 
			if(m.find()){
				String period = year.substring(2, year.length())+m.group(1).substring(1, m.group(1).length());
				url = "http://www.cailele.com/lottery/dcspf/spfhomeAction.php?term="+period;
				Log.info("==============【"+url+"】============");	
			}else{
				url = FetchConstant.OYP_URL;
			}
			content = HttpUtil.getContentByURL(url, false);
			
			//ArrayList datas = fiterOYPContent(content);
			//BuildOYPXMLDoc(datas);
		//} catch (JDOMException e) {
		//	e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.logger.info("[{}]线程开始执行结束,欧亚盘指数...");
	}
	public ArrayList fiterContent(String content){
		//http://info.hclotto/index.php?controller=analysis&mid=429363&lotyid=5
		//http://odds.hclotto/index.php?controller=detail&mid=429363&sit=1&lotyid=5
		ArrayList result = new ArrayList();
		Matcher m = p1.matcher(content);
		
		while(m.find()){
			ArrayList line = new ArrayList();
			String mid = m.group(2);
			String[] oParams = analyseParams(m.group(6));
			String[] yParams = analyseParams(m.group(7));
			String xUrl = "http://odds.hclotto/index.php?controller=danalysis&mid="+mid+"&lotyid="+yParams[2];
			String yUrl = "http://odds.hclotto/index.php?controller=detail&mid="+mid+"&sit="+yParams[1]+"&lotyid="+yParams[2];
			String oUrl = "http://odds.hclotto/index.php?controller=detail&mid="+mid+"&sit="+oParams[1]+"&lotyid="+oParams[2];

			line.add(m.group(1));
			line.add(mid);
			line.add(yParams[1]);
			line.add(yParams[2]);
			line.add(oParams[1]);
			line.add(oParams[2]);
			result.add(line);
		}		
		
		return result;		
	}

	private String[] analyseParams(String datas){
		String data = datas.substring(1, datas.length()-1);
		return data.split(",");
	}

	
	public void BuildXMLDoc(ArrayList result,String fileName) throws IOException, JDOMException {
		//创建根节点 root;
       Element root = new Element("root");
       
       //根节点添加到文档中；
       Document Doc = new Document(root);
        
       //创建节点 item;
        Element elements = new Element("items");
        //给 items 节点添加子节点并赋值；
        for(Iterator iter = (Iterator) result.iterator();iter.hasNext();){
     	   Element ele = new Element("item");
     	   ArrayList list = (ArrayList) iter.next();
     	   ele.setAttribute("id",((String) list.get(0)).trim());
     	   ele.setAttribute("mid",(String) list.get(1));
     	   ele.setAttribute("xSit",(String) list.get(2));
     	   ele.setAttribute("xLotyid",(String) list.get(3));
     	   ele.setAttribute("ySit",(String) list.get(4));
    	   ele.setAttribute("yLotyid",(String) list.get(5));
     	   elements.addContent(ele);
        }
       
	    root.addContent(elements);

	    XMLOutputter XMLOut = new XMLOutputter();
	    XMLOut.output(Doc, new FileOutputStream(new File(Constant.ROOTPATH+FetchConstant.DCZC_CUR_PATH+fileName)));

	}
	
	
	public ArrayList fiterOYPContent(String content){
		ArrayList result = new ArrayList();
		Matcher m = p2.matcher(content);
Log.info(content);		
		while(m.find()){
			ArrayList line = new ArrayList();
			String no = m.group(1);
			String oz = m.group(4);
			String oh = m.group(7);
			String ok = m.group(11);
			
			String yz = m.group(2);
			String yh = m.group(6);
			String yk = m.group(9);
			line.add(no);
			line.add(oz);
			line.add(oh);
			line.add(ok);
			line.add(yz);
			line.add(yh);
			line.add(yk);
			result.add(line);
		}		
		
		return result;		
	}
	
	
	public void BuildOYPXMLDoc(ArrayList result) throws IOException, JDOMException {
		//创建根节点 root;
       Element root = new Element("root");
       
       //根节点添加到文档中；
       Document Doc = new Document(root);
        
       //创建节点 item;
        Element elements = new Element("items");
        //给 items 节点添加子节点并赋值；
        for(Iterator iter = (Iterator) result.iterator();iter.hasNext();){
     	   Element ele = new Element("item");
     	   ArrayList list = (ArrayList) iter.next();
     	   
     	   ele.setAttribute("no",((String) list.get(0)).trim());
     	   ele.setAttribute("oz",((String) list.get(1)).trim());
     	   ele.setAttribute("oh",((String) list.get(2)).trim());
     	   ele.setAttribute("ok",((String) list.get(3)).trim());
     	   ele.setAttribute("yz",((String) list.get(4)).trim());
    	   ele.setAttribute("yh",((String) list.get(5)).trim());
    	   ele.setAttribute("yk",((String) list.get(6)).trim());

     	   elements.addContent(ele);
        }
       
	    root.addContent(elements);

	    XMLOutputter XMLOut = new XMLOutputter();
	    XMLOut.output(Doc, new FileOutputStream(new File(Constant.ROOTPATH+FetchConstant.DCZC_CUR_PATH+"oyp.xml")));

	}
	
	public static void main(String[] args) throws IOException {
		DczcXYOFetchTask task = new DczcXYOFetchTask();
		task.fetchData();
	}
}
