package com.cai310.lottery.web.controller.lottery.jczq;

 

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.cai310.lottery.JczqConstant;
import com.cai310.lottery.dto.lottery.jczq.OddsDto;
import com.cai310.lottery.web.controller.BaseController;
import com.cai310.utils.HttpClientUtil;
import com.cai310.utils.JsonUtil;
import com.cai310.utils.Struts2Utils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.Gson;

@Results({
		@Result(name = "all-odds", location = "/WEB-INF/content/jczq/match-odds.ftl")
	})
@Namespace("/" + JczqConstant.KEY)
@Action(value = "matchhistory")
public class JczqMatchHistoryController extends BaseController{
	
	private Map<Integer,String> companyMap = Maps.newHashMap();
 
	private List<OddsDto> allOddsList; 
	
	@Autowired
	@Qualifier("matchHisCache")
	private Cache matchHisCache;
 
	
	public JczqMatchHistoryController(){
		companyMap.put(24,"99家平均");
		companyMap.put(14,"威廉希尔");
		companyMap.put(82,"立博");
		companyMap.put(27,"bet36");
		companyMap.put(84,"澳门");
	}
	
	private  DecimalFormat df = new  DecimalFormat("000");
	
	public String history(){
		String html ="";
		String period =  Struts2Utils.getRequest().getParameter("period");
		String matchLine =  Struts2Utils.getRequest().getParameter("matchLine");
		String pageNo =Struts2Utils.getRequest().getParameter("pageNo");
		String t = Struts2Utils.getRequest().getParameter("t");
		String op = Struts2Utils.getRequest().getParameter("op");
		matchLine = df.format(Integer.valueOf(matchLine));
		Element el = matchHisCache.get("mh_"+period+matchLine+pageNo+t+op);
		if (el != null){
			html =  ((String) el.getValue()).toString();
		}else{
			String url ="http://61.147.127.238:8090/odds/matchHistoryFinal/?p="+period+"-"+matchLine+"&t="+t+"&pageNo="+pageNo;
			if(StringUtils.isNotBlank(op)){
				url ="http://61.147.127.238:8090/odds/matchHistoryFinal/?p="+period+"-"+matchLine+"&t="+t+"&pageNo="+pageNo+"&op="+op;
			}	
			html= HttpClientUtil.getRemoteSource(url,"utf-8");
			if(StringUtils.isNotBlank(html)){
				matchHisCache.put(new Element("mh_"+period+matchLine+pageNo+t+op,html));
			}
		}	
		this.jsonMap.put("matchHisResult",html);
		return this.success();
	}
	
	public String lishi(){
		String html ="";
		String key =  Struts2Utils.getRequest().getParameter("key");
		String matchType = Struts2Utils.getRequest().getParameter("matchType");
		Element el = matchHisCache.get("lishi_key"+key);
		if (el != null){
			html =  ((String) el.getValue()).toString();
		}else{
			String url ="http://192.168.1.102:8084/bet007/matchHis?key="+key+"&matchType="+matchType;
			html= HttpClientUtil.getRemoteSource(url,"utf-8");
			if(StringUtils.isNotBlank(html)){
				matchHisCache.put(new Element("lishi_key"+key,html));
			}
		}	
		Gson gson = new Gson();
		Map<String,String> map = gson.fromJson(html,Map.class);
		this.jsonMap.put("matchHisResult",map);
		return this.success();
	}
	
	public String lishiPeilv(){
		String html ="";
		String cId =  Struts2Utils.getRequest().getParameter("cId");
		String matchType = Struts2Utils.getRequest().getParameter("matchType");
		String type =  Struts2Utils.getRequest().getParameter("type");
		String key = Struts2Utils.getRequest().getParameter("key");
		Element el = matchHisCache.get("lspl_"+key+"_"+cId+"_"+type);
		if (el != null){
			html =  ((String) el.getValue()).toString();
		}else{
			String url ="http://192.168.1.102:8084/bet007/matchHisAj?key="+key+"&matchType="+matchType+"&type="+type+"&cId="+cId;
			html= HttpClientUtil.getRemoteSource(url,"utf-8");
			if(StringUtils.isNotBlank(html)){
				matchHisCache.put(new Element("lspl_"+key+"_"+cId+"_"+type,html));
			}
		}	
		Gson gson = new Gson();
		Map<String,String> map = gson.fromJson(html,Map.class);
		this.jsonMap.put("lishipeilvResult",map);
		return this.success();
	}
	
	public String allOdds(){
		String html ="";
		String period =  Struts2Utils.getRequest().getParameter("period");
		String matchLine =  Struts2Utils.getRequest().getParameter("matchLine");
		String t = Struts2Utils.getRequest().getParameter("t");
		matchLine = df.format(Integer.valueOf(matchLine));
 
		String url;
		if(t==null){
			url ="http://61.147.79.109:8889/odds/allCompanyOdds?period="+period+"&matchKey="+matchLine;
		}else{
			url ="http://61.147.79.109:8889/odds/allCompanyAsiaOdds?period="+period+"&matchKey="+matchLine;
		}
		allOddsList = Lists.newArrayList();
		html= HttpClientUtil.getRemoteSource(url,"utf-8");
		OddsDto odds ;
		String allOddsArr[] = JsonUtil.getStringArray4Json(html);
		String oddsArr[],firstOdds[],currentOddsArr[],currentOdds[],oddsTemp;
		try{
		for(String allOdds:allOddsArr){
			odds= new OddsDto(); 
			oddsArr = JsonUtil.getStringArray4Json(allOdds);
			odds.setCompany(oddsArr[0]);
			firstOdds = JsonUtil.getStringArray4Json(oddsArr[1]);
			odds.setFirstOdds(firstOdds);
			odds.setOdds(oddsArr[2]);
			currentOdds =JsonUtil.getStringArray4Json(oddsArr[2]);
			oddsTemp = currentOdds[currentOdds.length-1];
			currentOddsArr = JsonUtil.getStringArray4Json(oddsTemp);
			odds.setCurrentOdds(currentOddsArr);
			allOddsList.add(odds);
			
		}	
		}catch(Exception e){
			logger.warn(e.getMessage());
		}
		//Struts2Utils.getRequest().setAttribute("allOddsList",allOddsList);
		return "all-odds";
	}
	
	public String allAisaOdds(){
		String html ="";
		String period =  Struts2Utils.getRequest().getParameter("period");
		String matchLine =  Struts2Utils.getRequest().getParameter("matchLine");
		matchLine = df.format(Integer.valueOf(matchLine)); 
		String url ="http://61.147.79.109:8889/odds/allCompanyAsiaOdds?period="+period+"&matchKey="+matchLine; 
		html= HttpClientUtil.getRemoteSource(url,"utf-8");	 
		this.jsonMap.put("allOdds",html);
		return this.success();
	}

	public List<OddsDto> getAllOddsList() {
		return allOddsList;
	}

	public void setAllOddsList(List<OddsDto> allOddsList) {
		this.allOddsList = allOddsList;
	}
	
	
	
 
}
