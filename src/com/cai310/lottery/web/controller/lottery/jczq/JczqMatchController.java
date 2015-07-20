package com.cai310.lottery.web.controller.lottery.jczq;

 
 
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

import com.cai310.lottery.JczqConstant;
import com.cai310.lottery.cache.JczqLocalCache;
import com.cai310.lottery.entity.lottery.jczq.JczqMatch;
import com.cai310.lottery.service.lottery.jczq.JczqMatchEntityManager;
import com.cai310.lottery.support.Item;
import com.cai310.lottery.support.RateItem;
import com.cai310.lottery.support.jczq.ItemBF;
import com.cai310.lottery.support.jczq.PassMode;
import com.cai310.lottery.support.jczq.PlayType;
import com.cai310.lottery.support.jczq.PlayTypeWeb;
import com.cai310.lottery.web.controller.BaseController;
import com.cai310.utils.Struts2Utils;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
 

 
@Namespace("/" + JczqConstant.KEY)
@Action(value = "matchRemote")
public class JczqMatchController extends BaseController{
	
	private PlayType playType;
	private PassMode passMode;
	
	@Autowired
	private JczqMatchEntityManager matchEntityManager;
 
	@Autowired
	private JczqLocalCache localCache;
	
	public String matchList(){
		String matchDate =  Struts2Utils.getRequest().getParameter("matchDate");
		List<JczqMatch> jczqMatchList =  matchEntityManager.findMatchs(Integer.valueOf(matchDate));
		Gson gson = new Gson();
		String jczqMatchListGson = gson.toJson(jczqMatchList);
		this.jsonMap.put("jczqMatchListJson", jczqMatchListGson);
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.putAll(jsonMap);
	
		Struts2Utils.renderJson(map);
		return null;
	}
	public String matchSp(){
		Map<String, Map<String, RateItem>>  rate = localCache.getRateData(playType,passMode);
//		JsonConfig jsonConfig = new JsonConfig();
//		jsonConfig.setExcludes(new String[] {"chg"});
//		String jsonString = JSONObject.fromObject(rate, jsonConfig).toString();
//		Struts2Utils.renderJson(jsonString);
		Map<String, RateItem> map = null;
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		RateItem rateItem= null;
		for(Map.Entry<String,Map<String, RateItem>>entry:rate.entrySet()){
		 
			sb.append("'"+entry.getKey()+"':");
			map = entry.getValue();
			rateItem = map.get("WIN");		
			sb.append("["+rateItem.getValue()+",");
			rateItem = map.get("DRAW");	
			sb.append(rateItem.getValue()+",");
			rateItem = map.get("LOSE");	
			sb.append(rateItem.getValue()+"],");
		}
		if(sb.toString().endsWith(",")){
			sb.delete(sb.length()-1,sb.length());
		}
		sb.append("}");
		Struts2Utils.renderJson(playType+"Sp("+sb.toString()+")");
		return null;
	}
	
	
	public PlayType getPlayType() {
		return playType;
	}
	public void setPlayType(PlayType playType) {
		this.playType = playType;
	}
	public PassMode getPassMode() {
		return passMode;
	}
	public void setPassMode(PassMode passMode) {
		this.passMode = passMode;
	}
	
	
	
	
}
