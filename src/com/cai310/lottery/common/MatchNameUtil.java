package com.cai310.lottery.common;

import java.util.Map;

import com.cai310.lottery.Constant;
import com.google.common.collect.Maps;

/**
 * 
 */
public class MatchNameUtil {
	private static Map<String,String> matchNameMap = Maps.newHashMap();
    static{
    	matchNameMap.put("美国职业篮球联盟", "美职篮");
    	matchNameMap.put("欧洲篮球联赛", "欧洲联");
    	matchNameMap.put("美国女子篮球联盟", "美女篮");
    	matchNameMap.put("奥运会女篮落选赛", "奥女篮");
    	matchNameMap.put("奥运会男篮落选赛", "奥男落选");
    	matchNameMap.put("奥运会女篮", "奥女篮");
    	matchNameMap.put("奥运会男篮", "奥女篮");
    	matchNameMap.put("美职篮联盟季前赛", "美季前");
    	matchNameMap.put("欧洲篮球联盟杯", "欧联杯");
    	
        matchNameMap.put("英格兰冠军联赛","英冠");
	    matchNameMap.put("法国甲级联赛","法甲");
	    matchNameMap.put("瑞典超级联赛","瑞典超");
	    matchNameMap.put("挪威超级联赛","挪超");
	    matchNameMap.put("法国乙级联赛","法乙");
	    matchNameMap.put("苏格兰超级联赛","苏超");
	    matchNameMap.put("英格兰超级联赛","英超");
	    matchNameMap.put("阿根廷甲级联赛", "阿甲");
	    matchNameMap.put("南美解放者杯","南美杯");
	    matchNameMap.put("欧罗巴联赛","欧联杯");
	    matchNameMap.put("巴西杯","巴西杯");
	    matchNameMap.put("美国职业大联盟","美职业");
	    matchNameMap.put("荷兰乙级联赛","荷乙");
	    matchNameMap.put("德国甲级联赛","德甲");
	    matchNameMap.put("荷兰甲级联赛","荷甲");
	    matchNameMap.put("德国乙级联赛","德乙");
	    matchNameMap.put("英格兰甲级联赛","英甲");
	    matchNameMap.put("日本职业联赛","日职业");
	    matchNameMap.put("日本乙级联赛","日职乙");
	    matchNameMap.put("英格兰乙级联赛","英乙");
	    matchNameMap.put("葡萄牙超级联赛","葡超");
	    matchNameMap.put("德国杯","德国杯");
	    matchNameMap.put("西班牙甲级联赛","西甲");
	    matchNameMap.put("意大利甲级联赛","意甲");
	    matchNameMap.put("亚洲冠军联赛","亚冠");
	    matchNameMap.put("日本联赛杯","日联杯");
	    matchNameMap.put("苏格兰足总杯","苏足总杯");
	    matchNameMap.put("欧洲冠军联赛", "欧冠");
	    matchNameMap.put("葡萄牙杯","葡萄杯");
	    matchNameMap.put("意大利杯","意杯");
	    matchNameMap.put("国际赛","国际赛");
	    matchNameMap.put("西班牙国王杯","国王杯");
	    matchNameMap.put("巴西甲级联赛","巴甲");
	    matchNameMap.put("美国公开赛杯","美公开赛");
	    matchNameMap.put("欧洲U21预选赛","欧U21预");
	    matchNameMap.put("世界杯预选赛","世预赛");
	    matchNameMap.put("欧洲杯","欧洲杯");
	    matchNameMap.put("挪威杯","挪威杯");
	    matchNameMap.put("俱乐部友谊赛","友谊赛");
	    matchNameMap.put("南美俱乐部杯","南俱杯");
	    matchNameMap.put("奥运会女足","奥女足");
	    matchNameMap.put("奥运会男足","奥男足");
	    matchNameMap.put("法国超级杯","法超杯");
	    matchNameMap.put("中北美冠军联赛","中北美冠");
	    matchNameMap.put("荷兰超级杯","荷超杯");
	    matchNameMap.put("法国联赛杯","法联杯");
	    matchNameMap.put("意大利超级杯","意超杯");
	    matchNameMap.put("英格兰联赛杯","英联杯");
	    matchNameMap.put("英格兰社区盾杯","社区盾");
	    matchNameMap.put("葡萄牙超级杯","葡超杯");
	    matchNameMap.put("德国超级杯","德超杯");
	    matchNameMap.put("苏格兰联赛杯","苏联杯");
	    matchNameMap.put("南美优胜者杯","南美优胜杯");
	    matchNameMap.put("西班牙超级杯","西超杯");
	    matchNameMap.put("欧洲超级杯","欧超杯");
	    matchNameMap.put("英格兰锦标赛","英锦赛");
	    matchNameMap.put("日本天皇杯","天皇杯");
	    matchNameMap.put("葡萄牙联赛杯","葡联杯");
	    matchNameMap.put("荷兰杯","荷兰杯");
	    matchNameMap.put("澳大利亚超级联赛","澳A赛");
	    matchNameMap.put("英格兰足总杯","英足总杯");
	    matchNameMap.put("英格兰足总杯","英足总杯");
	    matchNameMap.put("东南亚锦标赛","东南亚锦");
	    
    }

	public static String getShotName(String longName) {
		String shortName = matchNameMap.get(longName);
		if(null!=shortName){
			return shortName; 
		}
		return longName;
	}
}
