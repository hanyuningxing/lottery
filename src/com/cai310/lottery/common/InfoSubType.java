package com.cai310.lottery.common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.cai310.lottery.Constant;


/**
 * 信息子类别 
 *
 */
public enum InfoSubType {
	NONE("无"), 
	///预测
	ZIMI("字迷"), 
	TUMI("图迷"), 
	SIMI("诗迷"), 
	DANSHI("单式"), 
	FUSHI("复式"), 
	LANQIU("蓝球"), 
	DANTUO("胆拖"), 
	SHAMA("杀码"), 
	SHENGXIAOLE("生肖乐"), 
	ZHIXUAN("直选"), 
	ZUXUAN("组选"), 
	HEZHI("和值"), 
	KUADU("跨度"), 
	DANMA("胆码"), 
	RENWU("任五"),
	QIANSAN("前三"),
	RENXUAN("任选"),
	WEIXUAN("围选"),
	SHUNXUAN("顺选"),
	SANXING("三星"),
	XINGTAI("形态"),
	WUXING("形态"),
	XINGSHIJIEDU("形势解读"),
	PANKOUPEILV("盘口赔率"),
	JIAODIANDUIZHEN("焦点对阵"),
	XINSHUIFENSHI("心水分析"),
	ZHUANJIAYUCE("专家预测"),
	ZHONGXINJINGXUAN("重心精选"),
	
	///技巧	
	RUMEN("入门"),
	JINJIE("进阶"),
	GAOSHOU("高手"),
	SHENGRUYANJIU("深入研究"),
	///资讯
	ZHONGJIANG("中奖"),
	GONGGAO("公告"),
	REDIAN("热点"),
	GUANGZHU("关注"),
	TOUZHU("投注"),
	XIXUN("喜讯"),
	HELP("帮助"),
	///
	KAIJIANG("开奖"),
	YILOU("遗漏"),
	ZHUIHAO("追号"),
	
	SHUZI("数字"),
	SHENGXIAO("生肖"),
	JIJIE("季节"),
	FANGWEI("方位"),
	XIANHAO("限号"),
	
	//专题
	JGZT("加奖专题"),
	HDZT("活动专题"),
	SXZT("上线专题"),
	LTZT("论坛专题"),

	
	ZJJH("专家荐号"),
	HCSC(Constant.WEB_NAME+"说彩"),
	GFTZ("官方通知"),
	XWZX("新闻咨询"),
	MEIYU("竟赢天下"),
	ZHUANYU("竞彩PK王");
	
	private String typeName;

	private InfoSubType(String typeName) {
		this.typeName = typeName;
	}
	
	public String getTypeName(){
		return this.typeName;
	}
	public static List<InfoSubType> getInfoSubTypeBy(InfoType infoType,Lottery lottery){
		List<InfoSubType> infoSubTypeList = new ArrayList<InfoSubType>();
		if(null!=infoType&&infoType.equals(InfoType.PHONE)){
			infoSubTypeList.add(InfoSubType.ZJJH);
			infoSubTypeList.add(InfoSubType.HCSC);
			infoSubTypeList.add(InfoSubType.GFTZ);
			infoSubTypeList.add(InfoSubType.XWZX);
			return infoSubTypeList;
		}
		if(null!=infoType&&infoType.equals(InfoType.TOPICS)){
			infoSubTypeList.add(InfoSubType.JGZT);
			infoSubTypeList.add(InfoSubType.HDZT);
			infoSubTypeList.add(InfoSubType.SXZT);
			infoSubTypeList.add(InfoSubType.LTZT);
			return infoSubTypeList;
		}
		if(null!=infoType&&infoType.equals(InfoType.INFO)){
			infoSubTypeList.add(InfoSubType.ZHONGJIANG);
			infoSubTypeList.add(InfoSubType.GONGGAO);
			infoSubTypeList.add(InfoSubType.REDIAN);
			infoSubTypeList.add(InfoSubType.GUANGZHU);
			infoSubTypeList.add(InfoSubType.TOUZHU);
			infoSubTypeList.add(InfoSubType.XIXUN);
			infoSubTypeList.add(InfoSubType.HELP);
			return infoSubTypeList;
		}
		if(null==infoType||null==lottery){
			infoSubTypeList.add(InfoSubType.NONE);
			return infoSubTypeList;
		}
		if(infoType.equals(InfoType.SKILLS)&&(LotteryCategory.NUMBER.equals(lottery.getCategory())||LotteryCategory.FREQUENT.equals(lottery.getCategory()))){
			infoSubTypeList.add(InfoSubType.JINJIE);
			infoSubTypeList.add(InfoSubType.RUMEN);
			infoSubTypeList.add(InfoSubType.GAOSHOU);
			infoSubTypeList.add(InfoSubType.SHENGRUYANJIU);
			return infoSubTypeList;
		}
		if(infoType.equals(InfoType.NOTICE)){
			infoSubTypeList.add(InfoSubType.KAIJIANG);
			return infoSubTypeList;
		}
		if(infoType.equals(InfoType.FORECAST)){
			if(LotteryCategory.NUMBER.equals(lottery.getCategory())){
				infoSubTypeList.add(InfoSubType.ZIMI);
				infoSubTypeList.add(InfoSubType.TUMI);
				infoSubTypeList.add(InfoSubType.SIMI);
				infoSubTypeList.add(InfoSubType.SHAMA);
			}
			if(Lottery.SSQ.equals(lottery)){
				infoSubTypeList.add(InfoSubType.DANSHI);
				infoSubTypeList.add(InfoSubType.LANQIU);
				infoSubTypeList.add(InfoSubType.DANTUO);
				infoSubTypeList.add(InfoSubType.FUSHI);
				
				Collections.reverse(infoSubTypeList);
				return infoSubTypeList;
			}
			
			if(Lottery.TC22TO5.equals(lottery)){
				infoSubTypeList.add(InfoSubType.DANSHI);
				infoSubTypeList.add(InfoSubType.DANTUO);
				infoSubTypeList.add(InfoSubType.FUSHI);
				Collections.reverse(infoSubTypeList);
				return infoSubTypeList;
			}
			
			if(Lottery.WELFARE36To7.equals(lottery)){
				infoSubTypeList.add(InfoSubType.SHUZI);
				infoSubTypeList.add(InfoSubType.SHENGXIAO);
				infoSubTypeList.add(InfoSubType.JIJIE);
				infoSubTypeList.add(InfoSubType.FANGWEI);
				infoSubTypeList.add(InfoSubType.ZHUIHAO);
				infoSubTypeList.add(InfoSubType.XIANHAO);
				Collections.reverse(infoSubTypeList);
				return infoSubTypeList;
			}
			
			if(Lottery.DLT.equals(lottery)){
				infoSubTypeList.add(InfoSubType.DANSHI);
				infoSubTypeList.add(InfoSubType.SHENGXIAOLE);
				infoSubTypeList.add(InfoSubType.DANTUO);
				infoSubTypeList.add(InfoSubType.FUSHI);

				Collections.reverse(infoSubTypeList);
				return infoSubTypeList;
			}
			if(Lottery.WELFARE3D.equals(lottery)||Lottery.PL.equals(lottery)){
				infoSubTypeList.add(InfoSubType.ZHIXUAN);
				infoSubTypeList.add(InfoSubType.ZUXUAN);
				infoSubTypeList.add(InfoSubType.HEZHI);
				infoSubTypeList.add(InfoSubType.KUADU);
				infoSubTypeList.add(InfoSubType.DANMA);
				return infoSubTypeList;
			}
			if(Lottery.EL11TO5.equals(lottery)||Lottery.SDEL11TO5.equals(lottery)){
				infoSubTypeList.add(InfoSubType.RENWU);
				infoSubTypeList.add(InfoSubType.QIANSAN);
				infoSubTypeList.add(InfoSubType.RENXUAN);
				infoSubTypeList.add(InfoSubType.HEZHI);
				infoSubTypeList.add(InfoSubType.KUADU);
				infoSubTypeList.add(InfoSubType.YILOU);
				infoSubTypeList.add(InfoSubType.ZHUIHAO);
				
				Collections.reverse(infoSubTypeList);
				return infoSubTypeList;
			}
			if(Lottery.QYH.equals(lottery)){
				infoSubTypeList.add(InfoSubType.DANTUO);
				infoSubTypeList.add(InfoSubType.RENXUAN);
				infoSubTypeList.add(InfoSubType.WEIXUAN);
				infoSubTypeList.add(InfoSubType.SHUNXUAN);
				infoSubTypeList.add(InfoSubType.HEZHI);
				infoSubTypeList.add(InfoSubType.KUADU);
				infoSubTypeList.add(InfoSubType.YILOU);
				infoSubTypeList.add(InfoSubType.ZHUIHAO);
				
				Collections.reverse(infoSubTypeList);
				return infoSubTypeList;
			}
			if(Lottery.SEVEN.equals(lottery)){
				infoSubTypeList.add(InfoSubType.DANSHI);
				infoSubTypeList.add(InfoSubType.DANMA);
				infoSubTypeList.add(InfoSubType.FUSHI);

				Collections.reverse(infoSubTypeList);
				return infoSubTypeList;
			}
			if(Lottery.SSC.equals(lottery)){
				infoSubTypeList.add(InfoSubType.SANXING);
				infoSubTypeList.add(InfoSubType.DANMA);
				infoSubTypeList.add(InfoSubType.ZUXUAN);
				infoSubTypeList.add(InfoSubType.XINGTAI);
				infoSubTypeList.add(InfoSubType.WUXING);
				return infoSubTypeList;
			}
			if(LotteryCategory.ZC.equals(lottery.getCategory())||LotteryCategory.DCZC.equals(lottery.getCategory())){
				infoSubTypeList.add(InfoSubType.XINGSHIJIEDU);
				infoSubTypeList.add(InfoSubType.PANKOUPEILV);
				infoSubTypeList.add(InfoSubType.JIAODIANDUIZHEN);
				infoSubTypeList.add(InfoSubType.XINSHUIFENSHI);
				infoSubTypeList.add(InfoSubType.ZHUANJIAYUCE);
				infoSubTypeList.add(InfoSubType.ZHONGXINJINGXUAN);
				return infoSubTypeList;
			}
			if(Lottery.JCZQ.equals(lottery)){
				infoSubTypeList.add(InfoSubType.MEIYU);
				infoSubTypeList.add(InfoSubType.ZHUANYU);
				return infoSubTypeList;
			}
			infoSubTypeList.add(InfoSubType.NONE);
			return infoSubTypeList;
		}
		infoSubTypeList.add(InfoSubType.NONE);
		return infoSubTypeList;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
}
