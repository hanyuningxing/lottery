package com.cai310.lottery.fetch.jczq.local;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.cai310.utils.JsonUtil;
import com.google.common.collect.Maps;

public class JczqMatchFetchParser {

 
	public String getCharset() {
		// TODO Auto-generated method stub
		return "gbk";
	}

 
	
	
//	public JczqMatchFetchResult fetch() {
//  
// 		return parserHTML(getCharset());
//	}

	
	public List<JczqMatchDto> parserHTML() {
		
		String url = getJsonUrl();
		String content ;	 
		Grabber g = new Grabber();
		content = g.grabberJson(url);
 
		Map obj =  JsonUtil.getMap4Json(content);
		String temp = String.valueOf(obj.get("data"));
		Map<String,String> map = JsonUtil.getMap4Json(temp);
		Map<String,String> matchMap;
		Map<Integer,JczqMatchDto> resultMap = Maps.newHashMap();
		Iterator it = map.entrySet().iterator();
		Map<String,String> oddsMap,hhadMap,hadMap,ttgMap,hafuMap,crsMap;
		String hhad,had,ttg,hafu,crs,odds;
		StringBuilder sb;
		JczqMatchDto dto;
		String mapValue;
		List<JczqMatchDto> resultList = new ArrayList<JczqMatchDto>();
		for(Map.Entry<String, String> entry : map.entrySet()){ 
			sb = new StringBuilder();
			mapValue= String.valueOf(entry.getValue()); 
			matchMap = JsonUtil.getMap4Json(mapValue);
			dto = new JczqMatchDto();
			dto.setGameColor(matchMap.get("l_background_color"));
			dto.setGameName(matchMap.get("l_cn"));
			dto.setHomeTeamName(matchMap.get("h_cn"));
			dto.setGuestTeamName(matchMap.get("a_cn"));
			dto.setMatchTime(matchMap.get("date")+" "+matchMap.get("time"));
			dto.setNum(matchMap.get("num"));
			dto.setMatchDate(matchMap.get("b_date"));
			if(matchMap.get("had")!=null){
			    hhadMap = JsonUtil.getMap4Json(String.valueOf(matchMap.get("hhad")));
				sb.append(hhadMap.get("a")+",").append(hhadMap.get("d")+",").append(hhadMap.get("h"));
				dto.setHandicap(hhadMap.get("fixedodds"));
			}
			if(matchMap.get("had")!=null){
				hadMap = JsonUtil.getMap4Json(String.valueOf(matchMap.get("had")));
				sb.append("|"+hadMap.get("a")+",").append(hadMap.get("d")+",").append(hadMap.get("h"));
			}else{
				sb.append("|");
			}
			if(matchMap.get("ttg")!=null){
				ttgMap = JsonUtil.getMap4Json(String.valueOf(matchMap.get("ttg")));
				sb.append("|"+ttgMap.get("s0")+",").append(ttgMap.get("s1")+",").append(ttgMap.get("s2")+",").append(ttgMap.get("s3")+",").
							append(ttgMap.get("s4")+",").append(ttgMap.get("s5")+",").append(ttgMap.get("s6")+",").append(ttgMap.get("s7"));
			}else{
				sb.append("|");
			}
			
			if(matchMap.get("hafu")!=null){
			hafuMap = JsonUtil.getMap4Json(String.valueOf(matchMap.get("hafu")));
			sb.append("|"+hafuMap.get("aa")+",").append(hafuMap.get("ad")+",").append(hafuMap.get("ah")+",").
			append(hafuMap.get("da")+",").append(hafuMap.get("dd")+",").append(hafuMap.get("dh")+",").
			append(hafuMap.get("ha")+",").append(hafuMap.get("hd")+",").append(hafuMap.get("hh"));
			}else{
				sb.append("|");
			}
			
			if(matchMap.get("crs")!=null){
			crsMap = JsonUtil.getMap4Json(String.valueOf(matchMap.get("crs")));
			sb.append("|"+crsMap.get("-1-a")+",").append(crsMap.get("-1-d")+",").append(crsMap.get("-1-h")+",").
						append(crsMap.get("0000")+",").append(crsMap.get("0001")+",").append(crsMap.get("0002")+",").append(crsMap.get("0003")+",").
						append(crsMap.get("0004")+",").append(crsMap.get("0005")+",").
						append(crsMap.get("0100")+",").append(crsMap.get("0101")+",").append(crsMap.get("0102")+",").append(crsMap.get("0103")+",").
						append(crsMap.get("0104")+",").append(crsMap.get("0105")+",").
						append(crsMap.get("0200")+",").append(crsMap.get("0201")+",").append(crsMap.get("0202")+",").append(crsMap.get("0203")+",").
						append(crsMap.get("0204")+",").append(crsMap.get("0205"));
			}else{
				sb.append("|");
			}
			
			dto.setOdds(sb.toString());
			//resultMap.put(Integer.valueOf(matchMap.get("id")),dto);
			resultList.add(dto);
		}
		return resultList;
	 
	}
	
	private String getJsonUrl(){
		return "http://i.sporttery.cn/odds_calculator/get_odds?i_format=json&poolcode[]=had";
	}
	
	public static void main(String[] args) {
		JczqMatchFetchParser parser = new JczqMatchFetchParser();
		List<JczqMatchDto> result = parser.parserHTML();

		for(JczqMatchDto dto:result){
 
			 System.out.println(dto.getGameColor()+"  "+dto.getGameName()+"  "+dto.getHomeTeamName()+"  "+dto.getGuestTeamName()+"  "+dto.getNum()+"  让球"+dto.getHandicap()
					 +"  "+dto.getMatchDate()+"  "+dto.getMatchTime()+"  "+dto.getOdds());
		}
		
	}
 

}
