package com.cai310.lottery.web.controller.lottery;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cai310.lottery.dto.lottery.SchemeDTO;
import com.cai310.lottery.entity.lottery.Scheme;
import com.cai310.lottery.entity.lottery.SchemeTemp;
import com.cai310.lottery.support.Item;
import com.cai310.lottery.support.RateItem;
import com.cai310.lottery.support.jczq.JczqMatchItem;
import com.cai310.lottery.support.jczq.OptimizeType;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * 奖金优化控制器
 * @author jack
 *
 */
public abstract class OptimizeController<T extends Scheme, E extends SchemeDTO, CF extends SchemeCreateForm> extends SchemeBaseController<T, E, CF, SchemeUploadForm, SchemeTemp>{

	private static final long serialVersionUID = -8429328400456666809L;

	protected transient Logger logger = LoggerFactory.getLogger(getClass());	
			
	protected static final int MAX_UNITS=10000;//最大优化注数
	
	protected OptimizeType optimizeType;
	
	protected List<Double> itemPrizes;//已经优化后的单注单倍奖金值
	
	/**是否执行奖金预测*/
	protected boolean prizeForecast;
		
	/**取赔率(key=场次+玩法)*/
	protected Map<String,Map<String,Map<String,RateItem>>> matchKey_RateMap_map = Maps.newHashMap();
		
		
	/**
	 * 混合投注赔率Mapkey
	 * @param matchKey
	 * @param playType
	 * @return
	 */
	protected String buildMixSpMapKey(String matchKey,String playType){
		return matchKey+playType;
	}
		
	
	/**
	 * 为拆单后的内容进行倍投优化操作--按照奖金分配注数（平均、保守、搏冷）
	 * @param resultList
	 * @param totalMultiple
	 */
	protected void optimizeMultiple(List<Object[]> resultList,int totalMultiple){
		
		//拆分注数
		int resultCount = resultList.size();
		//默认为平均优化
		if(this.optimizeType==null)this.optimizeType=OptimizeType.PINGJUN;
		
		if(this.optimizeType==OptimizeType.PINGJUN || this.optimizeType==OptimizeType.BOLENG || this.optimizeType==OptimizeType.BAOSHOU)
			Collections.sort(resultList,new ContentComparator());
		
		//优化内容等于总倍投数,说明是1倍投
		if(resultList.size()==totalMultiple){
			for(Object[] item : resultList){
				item[1]=1;
			}
			return;
		}
		
		//方案总金额
		int totalCost = totalMultiple * createForm.getUnitsMoney();
		
		switch(this.optimizeType){
		case PINGJUN:
			Double baseSp = (Double)((Object[])resultList.get(0))[2];
			Double percentC = 0.0;
			Double percent = 0.0;
			List<Double> percents = Lists.newArrayList();
			for(Object[] item : resultList){
				percent = baseSp/(Double)item[2];
				percentC+=percent;
				percents.add(percent);
			}			
			Double basePercent = totalMultiple/percentC;				
			int baseMultiple = (int) Math.round(basePercent);
			resultList.get(0)[1]=baseMultiple==0?1:baseMultiple;
			
			int multiple = 0;
			for(int i=1;i<resultCount;i++){
				multiple = (int) Math.round(basePercent*percents.get(i));
				if(multiple==0){
					resultList.get(i)[1] = 1;
				}else{
					resultList.get(i)[1] = multiple;
				}
			}
			
			int realMultiple = 0;//实际分配后的倍数
			for(Object[] item : resultList){
				realMultiple+=(Integer)item[1];
			}
									
			logger.debug("optimize befor======="+totalMultiple+" "+realMultiple);
			//需要有一个补足注数的浮动问题（Math.round导致有0注情况）
			if(totalMultiple!=realMultiple){
				this.adjustMultiple(resultList, realMultiple-totalMultiple);
			}
			realMultiple=0;
			for(Object[] item : resultList){
				realMultiple+=(Integer)item[1];
			}
			logger.debug("optimize after======="+totalMultiple+" "+realMultiple);
			
			break;
		case BAOSHOU:
			//赔率最小的，即为保守
			for(int i=resultCount-1;i>0;i--){
				Double cost = (Double)((Object[])resultList.get(i))[2];
				int multiple_bs = (int)Math.ceil(totalCost/cost);
				resultList.get(i)[1] = multiple_bs;
				totalMultiple -= multiple_bs;
			}
			resultList.get(0)[1] = totalMultiple;
			break;
		case BOLENG:
			//赔率最大的，即为搏冷
			for(int i=0;i<resultCount-1;i++){
				Double cost = (Double)((Object[])resultList.get(i))[2];
				int multiple_bs = (int)Math.ceil(totalCost/cost);
				resultList.get(i)[1] = multiple_bs;
				totalMultiple -= multiple_bs;
			}
			resultList.get(resultCount-1)[1] = totalMultiple;
			break;
		
		//代码有待优化
		case RANG_LING_OR_ONE:
			
			for(int k=0; k<resultCount;k+=4) {	
				Object[] win_winObject = null;
				Object[] lose_loseObject = null;
				Object[] lose_winObject = null;
				Object[] win_loseObject = null;
				int win_drawMutiple = 0;
				int lose_drawMutiple = 0;
				int win_winMutiple = 0;
				int lose_winMutiple = 0;
				int lose_loseMutiple = 0;
				int win_loseMutiple = 0;
				
				for(int i=k; i<k+4; i++) {
					String selectedItemStr = resultList.get(i)[3].toString();
					
					//单式的胜平
					if(selectedItemStr.contains("0") && selectedItemStr.contains("1")) {
						win_drawMutiple = (int) ((totalCost/(resultCount/4))/((Double)resultList.get(i)[2]));
					}				
					//单式的负平
					if(selectedItemStr.contains("2") && selectedItemStr.contains("1")) {
						lose_drawMutiple = (int) ((totalCost/(resultCount/4))/((Double)resultList.get(i)[2]));
					}
					//单式的胜胜
					if(selectedItemStr.contains("0") && !selectedItemStr.contains("1") && !selectedItemStr.contains("2")) {
						win_winObject = resultList.get(i);
					}
					//单式的负负
					if(selectedItemStr.contains("2") && !selectedItemStr.contains("1") && !selectedItemStr.contains("0")) {
						lose_loseObject = resultList.get(i);
					}
					//单式的胜负
					if(selectedItemStr.contains("2") && selectedItemStr.contains("0")) {
						lose_winObject = resultList.get(i);
						win_loseObject = resultList.get(i);
					}
				}
				
				if(win_winObject != null){				
					win_winMutiple = (int) (((Double)lose_winObject[2]/((Double)lose_winObject[2] + (Double)win_winObject[2])) * (totalMultiple/(resultCount/4)-win_drawMutiple-lose_drawMutiple));
					lose_winMutiple = totalMultiple/(resultCount/4)-win_drawMutiple-lose_drawMutiple-win_winMutiple;								
				}
				
				if(lose_loseObject != null) {
					lose_loseMutiple = (int) (((Double)win_loseObject[2]/((Double)win_loseObject[2]+(Double)lose_loseObject[2])) * (totalMultiple/(resultCount/4)-win_drawMutiple-lose_drawMutiple));
					win_loseMutiple =  totalMultiple/(resultCount/4)-win_drawMutiple-lose_drawMutiple-lose_loseMutiple;
				}
				
				for(int i=k; i<k+4; i++) {	
					
					String selectedItemStr = resultList.get(i)[3].toString();
					
					//分配单式胜负的注数
					if(selectedItemStr.contains("0") && selectedItemStr.contains("1")) {
						resultList.get(i)[1] = win_drawMutiple;
					}				
					//分配单式负平的注数
					if(selectedItemStr.contains("2") && selectedItemStr.contains("1")) {
						resultList.get(i)[1] = lose_drawMutiple;
					}
					//分配单式胜胜的注数
					if(selectedItemStr.contains("0") && !selectedItemStr.contains("1") && !selectedItemStr.contains("2")) {
						resultList.get(i)[1] = win_winMutiple;
						
					}
					//分配单式负负的注数
					if(selectedItemStr.contains("2") && !selectedItemStr.contains("1") && !selectedItemStr.contains("0")) {
						resultList.get(i)[1] = lose_loseMutiple;
					}
					//分配单式胜负的注数
					if(selectedItemStr.contains("2") && selectedItemStr.contains("0")) {
						
						if(lose_winMutiple == 0){
							resultList.get(i)[1] = win_loseMutiple;
						} else {
							resultList.get(i)[1] = lose_winMutiple;
						}
					}
				}
				
			}
			break;
		//代码有待优化
		case RANG_ER_WU:
			for(int k=0; k<resultCount;k+=4) {	
				Boolean isWinWin = false;
				Boolean isLoseLose = false;
				
				//通过查看单式含有胜胜组合的投注还是负负组合的投注，判断投注的是胜平，还是平负
				for(int i=k; i<k+4; i++) {
					String selectedItemStr = resultList.get(i)[3].toString();
				
					if(selectedItemStr.contains("0") && !selectedItemStr.contains("1") && !selectedItemStr.contains("2")) {
						isWinWin = true;
					}
					
					if(selectedItemStr.contains("2") && !selectedItemStr.contains("1") && !selectedItemStr.contains("0")) {
						isLoseLose = true;
					}
				}
				
				if(isWinWin) {
					Object[] win_winObject = null;
					Object[] lose_loseObject = null;
					Object[] lose_winObject = null;
					Object[] win_loseObject = null;
					int win_drawMutiple = 0;
					int lose_drawMutiple = 0;
					int win_winMutiple = 0;
					int lose_winMutiple = 0;
					int lose_loseMutiple = 0;
					int win_loseMutiple = 0;
					for(int i=k; i<k+4; i++) {	
						String selectedItemStr = resultList.get(i)[3].toString();
						//单式的胜平
						if(resultList.get(i)[3].toString().contains("0") && selectedItemStr.contains("1")) {
							win_drawMutiple = (int) ((totalCost/(resultCount/4)/2)/((Double)resultList.get(i)[2]));
						}				
						//单式的负平
						if(selectedItemStr.contains("2") && selectedItemStr.contains("1")) {
							lose_drawMutiple = (int) ((totalCost/(resultCount/4)/2)/((Double)resultList.get(i)[2]));
						}
						//单式的胜胜
						if(selectedItemStr.contains("0") && !selectedItemStr.contains("1") && !selectedItemStr.contains("2")) {
							win_winObject = resultList.get(i);
						}
						//单式的负负
						if(selectedItemStr.contains("2") && !selectedItemStr.contains("1") && !selectedItemStr.contains("0")) {
							lose_loseObject = resultList.get(i);
						}
						//单式的胜负
						if(selectedItemStr.contains("2") && selectedItemStr.contains("0")) {
							lose_winObject = resultList.get(i);
							win_loseObject = resultList.get(i);
						}
					}
					if(win_winObject != null){				
						win_winMutiple = (int) (((Double)lose_winObject[2]/((Double)lose_winObject[2] + (Double)win_winObject[2])) * (totalMultiple/(resultCount/4)-win_drawMutiple-lose_drawMutiple));
						lose_winMutiple = totalMultiple/(resultCount/4)-win_drawMutiple-lose_drawMutiple-win_winMutiple;								
					}
					
					if(lose_loseObject != null) {
						lose_loseMutiple = (int) (((Double)win_loseObject[2]/((Double)win_loseObject[2]+(Double)lose_loseObject[2])) * (totalMultiple/(resultCount/4)-win_drawMutiple-lose_drawMutiple));
						win_loseMutiple =  totalMultiple/(resultCount/4)-win_drawMutiple-lose_drawMutiple-lose_loseMutiple;
					}
					
					for(int i=k; i<k+4; i++) {	
						String selectedItemStr = resultList.get(i)[3].toString();
						//分配单式胜平的注数
						if(selectedItemStr.contains("0") && selectedItemStr.contains("1")) {
							resultList.get(i)[1] = win_drawMutiple;
						}				
						//分配单式负平的注数
						if(selectedItemStr.contains("2") && selectedItemStr.contains("1")) {
							resultList.get(i)[1] = lose_drawMutiple;
						}
						//分配单式胜胜的注数
						if(selectedItemStr.contains("0") && !selectedItemStr.contains("1") && !selectedItemStr.contains("2")) {
							resultList.get(i)[1] = win_winMutiple;
							
						}
						//分配单式负负的注数
						if(selectedItemStr.contains("2") && !selectedItemStr.contains("1") && !selectedItemStr.contains("0")) {
							resultList.get(i)[1] = lose_loseMutiple;
						}
						//分配单式胜负的注数
						if(selectedItemStr.contains("2") && selectedItemStr.contains("0")) {
							
							if(lose_winMutiple == 0){
								resultList.get(i)[1] = win_loseMutiple;
							} else {
								resultList.get(i)[1] = lose_winMutiple;
							}
						}
					}
					
				} else if(isLoseLose){
					Double winLoseSP = null;
					Double winDrawSP = null;
					Double loseLoseSP = null; 
					Double loseDrawSp = null;
					
					Double winLosePercent = null;
					Double winDrawPercent = null;
					Double loseLosePercent = null; 
					Double loseDrawPercent = null;
					
					for(int i=k; i<k+4; i++) {	
						String selectedItemStr = resultList.get(i)[3].toString();
						if(selectedItemStr.contains("2") && selectedItemStr.contains("0")) {
							winLoseSP = (Double) resultList.get(i)[2];
						}
						if(selectedItemStr.contains("1") && selectedItemStr.contains("0")) {
							winDrawSP = (Double) resultList.get(i)[2];
						}	
						if(selectedItemStr.contains("2") && !selectedItemStr.contains("1") && !selectedItemStr.contains("0")) {
							loseLoseSP = (Double) resultList.get(i)[2];
						}
						if(selectedItemStr.contains("2") && selectedItemStr.contains("1")) {
							loseDrawSp = (Double) resultList.get(i)[2];
						}
					}
					
					winLosePercent = winLoseSP/(2*winLoseSP+winDrawSP+2*loseLoseSP+loseDrawSp);
					winDrawPercent = winDrawSP/(2*winLoseSP+winDrawSP+2*loseLoseSP+loseDrawSp);
					loseLosePercent = loseLoseSP/(2*winLoseSP+winDrawSP+2*loseLoseSP+loseDrawSp);
					loseDrawPercent = loseDrawSp/(2*winLoseSP+winDrawSP+2*loseLoseSP+loseDrawSp);
					
					Double[] percentArr = {winLosePercent, winDrawPercent, loseLosePercent, loseDrawPercent};
					
					Arrays.sort(percentArr);
					Map<Integer, Integer> positionMap = new HashMap<Integer, Integer>();
					positionMap.put(0, 3);
					positionMap.put(1, 2);
					positionMap.put(3, 0);
					positionMap.put(2, 1);
					
					Integer winLoseBaoBenMutiple = (int) (totalCost/(resultCount/4)/winLoseSP);
					Integer winDrawBaoBenMutiple = (int) (totalCost/(resultCount/4)/winDrawSP);
					Integer loseLoseBaoBenMutiple = (int) (totalCost/(resultCount/4)/loseLoseSP);
					Integer loseDrawBaoBenMutiple = (int) (totalCost/(resultCount/4)/loseDrawSp);
					
					Integer allBaoBenMutiple = winLoseBaoBenMutiple + winDrawBaoBenMutiple + loseLoseBaoBenMutiple + loseDrawBaoBenMutiple;
					Integer surplusMutiple = (totalMultiple)/(resultCount/4);
					
					Integer winLoseMutipleWithoutBaoben = null;
					Integer winDrawMutipleWithoutBaoben = null;
					Integer loseLoseMutipleWithoutBaoben = null;
					Integer loseDrawMutipleWithoutBaoben = null;
					
					for(int i=k; i<k+4; i++) {	
						String selectedItemStr = resultList.get(i)[3].toString();
						if(selectedItemStr.contains("2") && selectedItemStr.contains("0")) {
							int winLosePosition = 0;
							if(winLoseSP > winDrawSP) {
								winLosePosition = winLosePosition + 1;
							}
							
							if(winLoseSP > loseLoseSP) {
								winLosePosition = winLosePosition + 1;
							}
							
							if(winLoseSP > loseDrawSp) {
								winLosePosition = winLosePosition + 1;
							}
							resultList.get(i)[1] =  (int)(((totalMultiple)/(resultCount/4)-allBaoBenMutiple)*percentArr[positionMap.get(winLosePosition)]*2) + winLoseBaoBenMutiple;
							winLoseMutipleWithoutBaoben = (int)(((totalMultiple)/(resultCount/4)-allBaoBenMutiple)*percentArr[positionMap.get(winLosePosition)]*2);
							surplusMutiple = surplusMutiple - (int)(((totalMultiple)/(resultCount/4)-allBaoBenMutiple)*percentArr[positionMap.get(winLosePosition)]*2) - winLoseBaoBenMutiple;
						}
						if(selectedItemStr.contains("1") && selectedItemStr.contains("0")) {
							int winDrawPosition = 0;
							if(winDrawSP>winLoseSP) {
								winDrawPosition = winDrawPosition + 1;
							}
							if(winDrawSP>loseLoseSP) {
								winDrawPosition = winDrawPosition + 1;
							}
							if(winDrawSP>loseDrawSp) {
								winDrawPosition = winDrawPosition + 1;
							}
							resultList.get(i)[1] =  (int)(((totalMultiple)/(resultCount/4)-allBaoBenMutiple)*percentArr[positionMap.get(winDrawPosition)]) + winDrawBaoBenMutiple;
							winDrawMutipleWithoutBaoben = (int)(((totalMultiple)/(resultCount/4)-allBaoBenMutiple)*percentArr[positionMap.get(winDrawPosition)]);
							surplusMutiple = surplusMutiple - (int)(((totalMultiple)/(resultCount/4)-allBaoBenMutiple)*percentArr[positionMap.get(winDrawPosition)]) - winDrawBaoBenMutiple;
						}	
						if(selectedItemStr.contains("2") && !selectedItemStr.contains("1") && !selectedItemStr.contains("0")) {
							int loseLosePosition = 0;
							if(loseLoseSP>winLoseSP) {
								loseLosePosition = loseLosePosition + 1;
							}
							if(loseLoseSP>winDrawSP) {
								loseLosePosition = loseLosePosition + 1;
							}
							if(loseLoseSP>loseDrawSp) {
								loseLosePosition = loseLosePosition + 1;
							}
							resultList.get(i)[1] =  (int)(((totalMultiple)/(resultCount/4)-allBaoBenMutiple)*percentArr[positionMap.get(loseLosePosition)]*2) + loseLoseBaoBenMutiple;
							loseLoseMutipleWithoutBaoben = (int)(((totalMultiple)/(resultCount/4)-allBaoBenMutiple)*percentArr[positionMap.get(loseLosePosition)]*2);
							surplusMutiple = surplusMutiple - (int)(((totalMultiple)/(resultCount/4)-allBaoBenMutiple)*percentArr[positionMap.get(loseLosePosition)]*2) - loseLoseBaoBenMutiple;
						}
						if(selectedItemStr.contains("2") && selectedItemStr.contains("1")) {
							int loseDrawPosition = 0;
							if(loseDrawSp>winLoseSP) {
								loseDrawPosition = loseDrawPosition + 1;
							}
							if(loseDrawSp>winDrawSP) {
								loseDrawPosition = loseDrawPosition + 1;
							}
							if(loseDrawSp>loseLoseSP) {
								loseDrawPosition = loseDrawPosition + 1;
							}
							resultList.get(i)[1] =  (int)(((totalMultiple)/(resultCount/4)-allBaoBenMutiple)*percentArr[positionMap.get(loseDrawPosition)]) + loseDrawBaoBenMutiple;
							loseDrawMutipleWithoutBaoben =  (int)(((totalMultiple)/(resultCount/4)-allBaoBenMutiple)*percentArr[positionMap.get(loseDrawPosition)]);
							surplusMutiple = surplusMutiple - (int)(((totalMultiple)/(resultCount/4)-allBaoBenMutiple)*percentArr[positionMap.get(loseDrawPosition)]) - loseDrawBaoBenMutiple;
						}						
					}
					Integer allMutipleWithoutBaoben = winLoseMutipleWithoutBaoben + winDrawMutipleWithoutBaoben + loseLoseMutipleWithoutBaoben + loseDrawMutipleWithoutBaoben;
					//当剩余注数大于0时，继续分配
					while(surplusMutiple > 0) {
						for(int i=k; i<k+4; i++) {		
							String selectedItemStr = resultList.get(i)[3].toString();
							if(selectedItemStr.contains("2") && selectedItemStr.contains("0")) {
								
								if((int)(((double)winLoseMutipleWithoutBaoben/(double)allMutipleWithoutBaoben)*surplusMutiple) < 1) {
									if(surplusMutiple <= 0)
										break;
									resultList.get(i)[1] = (Integer)resultList.get(i)[1] + 1;
									surplusMutiple = surplusMutiple - 1;
								} else {
									if(surplusMutiple <= 0)
										break;
									resultList.get(i)[1] = (Integer)resultList.get(i)[1] + (int)(((double)winLoseMutipleWithoutBaoben/(double)allMutipleWithoutBaoben)*surplusMutiple);
									surplusMutiple = surplusMutiple - (int)(((double)winLoseMutipleWithoutBaoben/(double)allMutipleWithoutBaoben)*surplusMutiple);
								}
							}
							if(selectedItemStr.contains("1") && selectedItemStr.contains("0")) {																
								if((int)(((double)winDrawMutipleWithoutBaoben/(double)allMutipleWithoutBaoben)*surplusMutiple) < 1) {
									if(surplusMutiple <= 0)
										break;
									resultList.get(i)[1] = (Integer)resultList.get(i)[1] + 1;
									surplusMutiple = surplusMutiple - 1;
								} else {
									if(surplusMutiple <= 0)
										break;
									resultList.get(i)[1] = (Integer)resultList.get(i)[1] + (int)(((double)winDrawMutipleWithoutBaoben/(double)allMutipleWithoutBaoben)*surplusMutiple);
									surplusMutiple = surplusMutiple - (int)(((double)winDrawMutipleWithoutBaoben/(double)allMutipleWithoutBaoben)*surplusMutiple);
								}
								
							}	
							if(selectedItemStr.contains("2") && !selectedItemStr.contains("1") && !selectedItemStr.contains("0")) {																
								if((int)(((double)loseLoseMutipleWithoutBaoben/(double)allMutipleWithoutBaoben)*surplusMutiple) < 1) {
									if(surplusMutiple <= 0)
										break;
									resultList.get(i)[1] = (Integer)resultList.get(i)[1] + 1;
									surplusMutiple = surplusMutiple - 1;
								} else {
									if(surplusMutiple <= 0)
										break;
									resultList.get(i)[1] = (Integer)resultList.get(i)[1] + (int)(((double)loseLoseMutipleWithoutBaoben/(double)allMutipleWithoutBaoben)*surplusMutiple);
									surplusMutiple = surplusMutiple - (int)(((double)loseLoseMutipleWithoutBaoben/(double)allMutipleWithoutBaoben)*surplusMutiple);
								}
								
							}
							if(selectedItemStr.contains("2") && selectedItemStr.contains("1")) {						
								
								if((int)(((double)loseDrawMutipleWithoutBaoben/(double)allMutipleWithoutBaoben)*surplusMutiple) < 1) {
									if(surplusMutiple <= 0)
										break;
									resultList.get(i)[1] = (Integer)resultList.get(i)[1] + 1;
									surplusMutiple = surplusMutiple - 1;
								} else {
									if(surplusMutiple <= 0)
										break;
									resultList.get(i)[1] = (Integer)resultList.get(i)[1] + (int)(((double)loseDrawMutipleWithoutBaoben/(double)allMutipleWithoutBaoben)*surplusMutiple);
									surplusMutiple = surplusMutiple - (int)(((double)loseDrawMutipleWithoutBaoben/(double)allMutipleWithoutBaoben)*surplusMutiple);
								}
								
							}						
						}
					}
					
				}
				
			}
			break;
		//代码有待优化
		case RANG_QI_WU:
			for(int k=0; k<resultCount;k+=4) {	
				Boolean isWinWin = false;
				Boolean isLoseLose = false;
				
				for(int i=k; i<k+4; i++) {
					String selectedItemStr = resultList.get(i)[3].toString();
					if(selectedItemStr.contains("0") && !selectedItemStr.contains("1") && !selectedItemStr.contains("2")) {
						isWinWin = true;
					}
					
					if(selectedItemStr.contains("2") && !selectedItemStr.contains("1") && !selectedItemStr.contains("0")) {
						isLoseLose = true;
					}
				}
				if(isLoseLose) {
					Object[] win_winObject = null;
					Object[] lose_loseObject = null;
					Object[] lose_winObject = null;
					Object[] win_loseObject = null;
					int win_drawMutiple = 0;
					int lose_drawMutiple = 0;
					int win_winMutiple = 0;
					int lose_winMutiple = 0;
					int lose_loseMutiple = 0;
					int win_loseMutiple = 0;
					for(int i=k; i<k+4; i++) {	
						String selectedItemStr = resultList.get(i)[3].toString();
						if(selectedItemStr.contains("0") && selectedItemStr.contains("1")) {
							win_drawMutiple = (int) ((totalCost/(resultCount/4)/2)/((Double)resultList.get(i)[2]));
						}				
						
						if(selectedItemStr.contains("2") && selectedItemStr.contains("1")) {
							lose_drawMutiple = (int) ((totalCost/(resultCount/4)/2)/((Double)resultList.get(i)[2]));
						}
						
						if(selectedItemStr.contains("0") && !selectedItemStr.contains("1") && !selectedItemStr.contains("2")) {
							win_winObject = resultList.get(i);
						}
						
						if(selectedItemStr.contains("2") && !selectedItemStr.contains("1") && !selectedItemStr.contains("0")) {
							lose_loseObject = resultList.get(i);
						}
						
						if(selectedItemStr.contains("2") && selectedItemStr.contains("0")) {
							lose_winObject = resultList.get(i);
							win_loseObject = resultList.get(i);
						}
					}
					if(win_winObject != null){				
						win_winMutiple = (int) (((Double)lose_winObject[2]/((Double)lose_winObject[2] + (Double)win_winObject[2])) * (totalMultiple/(resultCount/4)-win_drawMutiple-lose_drawMutiple));
						lose_winMutiple = totalMultiple/(resultCount/4)-win_drawMutiple-lose_drawMutiple-win_winMutiple;								
					}
					
					if(lose_loseObject != null) {
						lose_loseMutiple = (int) (((Double)win_loseObject[2]/((Double)win_loseObject[2]+(Double)lose_loseObject[2])) * (totalMultiple/(resultCount/4)-win_drawMutiple-lose_drawMutiple));
						win_loseMutiple =  totalMultiple/(resultCount/4)-win_drawMutiple-lose_drawMutiple-lose_loseMutiple;
					}
					
					for(int i=k; i<k+4; i++) {		
						String selectedItemStr = resultList.get(i)[3].toString();
						if(selectedItemStr.contains("0") && selectedItemStr.contains("1")) {
							resultList.get(i)[1] = win_drawMutiple;
						}				
						
						if(selectedItemStr.contains("2") && selectedItemStr.contains("1")) {
							resultList.get(i)[1] = lose_drawMutiple;
						}
						
						if(selectedItemStr.contains("0") && !selectedItemStr.contains("1") && !selectedItemStr.contains("2")) {
							resultList.get(i)[1] = win_winMutiple;
							
						}
						
						if(selectedItemStr.contains("2") && !selectedItemStr.contains("1") && !selectedItemStr.contains("0")) {
							resultList.get(i)[1] = lose_loseMutiple;
						}
						
						if(selectedItemStr.contains("2") && selectedItemStr.contains("0")) {
							
							if(lose_winMutiple == 0){
								resultList.get(i)[1] = win_loseMutiple;
							} else {
								resultList.get(i)[1] = lose_winMutiple;
							}
						}
					}
					
				} else if(isWinWin){
					Double winWinSP = null;
					Double winDrawSP = null;
					Double loseWinSP = null; 
					Double loseDrawSp = null;
					
					Double winWinPercent = null;
					Double winDrawPercent = null;
					Double loseWInPercent = null; 
					Double loseDrawPercent = null;
					
					for(int i=k; i<k+4; i++) {	
						String selectedItemStr = resultList.get(i)[3].toString();
						if(!selectedItemStr.contains("2") && !selectedItemStr.contains("1") && selectedItemStr.contains("0")) {
							winWinSP = (Double) resultList.get(i)[2];
						}
						if(selectedItemStr.contains("1") && selectedItemStr.contains("0")) {
							winDrawSP = (Double) resultList.get(i)[2];
						}	
						if(selectedItemStr.contains("2") && selectedItemStr.contains("0")) {
							loseWinSP = (Double) resultList.get(i)[2];
						}
						if(selectedItemStr.contains("2") && selectedItemStr.contains("1")) {
							loseDrawSp = (Double) resultList.get(i)[2];
						}
					}
					
					winWinPercent = winWinSP/(2*winWinSP+winDrawSP+2*loseWinSP+loseDrawSp);
					winDrawPercent = winDrawSP/(2*winWinSP+winDrawSP+2*loseWinSP+loseDrawSp);
					loseWInPercent = loseWinSP/(2*winWinSP+winDrawSP+2*loseWinSP+loseDrawSp);
					loseDrawPercent = loseDrawSp/(2*winWinSP+winDrawSP+2*loseWinSP+loseDrawSp);
					
					Double[] percentArr = {winWinPercent, winDrawPercent, loseWInPercent, loseDrawPercent};
					
					Arrays.sort(percentArr);
					Map<Integer, Integer> positionMap = new HashMap<Integer, Integer>();
					positionMap.put(0, 3);
					positionMap.put(1, 2);
					positionMap.put(3, 0);
					positionMap.put(2, 1);
					
					Integer winWInBaoBenMutiple = (int) (totalCost/(resultCount/4)/winWinSP);
					Integer winDrawBaoBenMutiple = (int) (totalCost/(resultCount/4)/winDrawSP);
					Integer loseWinBaoBenMutiple = (int) (totalCost/(resultCount/4)/loseWinSP);
					Integer loseDrawBaoBenMutiple = (int) (totalCost/(resultCount/4)/loseDrawSp);
					
					Integer allBaoBenMutiple = winWInBaoBenMutiple + winDrawBaoBenMutiple + loseWinBaoBenMutiple + loseDrawBaoBenMutiple;
					Integer surplusMutiple = (totalMultiple)/(resultCount/4);
						
					Integer winWInMutipleWithoutBaoben = null;
					Integer winDrawMutipleWithoutBaoben = null;
					Integer loseWinMutipleWithoutBaoben = null;
					Integer loseDrawMutipleWithoutBaoben = null;
					
					for(int i=k; i<k+4; i++) {		
						String selectedItemStr = resultList.get(i)[3].toString();
						if(!selectedItemStr.contains("2") && !selectedItemStr.contains("1") && selectedItemStr.contains("0")) {
							int winWInPosition = 0;
							if(winWinSP > winDrawSP) {
								winWInPosition = winWInPosition + 1;
							}
							
							if(winWinSP > loseWinSP) {
								winWInPosition = winWInPosition + 1;
							}
							
							if(winWinSP > loseDrawSp) {
								winWInPosition = winWInPosition + 1;
							}
							resultList.get(i)[1] =  (int)(((totalMultiple)/(resultCount/4)-allBaoBenMutiple)*percentArr[positionMap.get(winWInPosition)]*2) + winWInBaoBenMutiple;
							winWInMutipleWithoutBaoben = (int)(((totalMultiple)/(resultCount/4)-allBaoBenMutiple)*percentArr[positionMap.get(winWInPosition)]*2);
							surplusMutiple = surplusMutiple - (int)(((totalMultiple)/(resultCount/4)-allBaoBenMutiple)*percentArr[positionMap.get(winWInPosition)]*2) - winWInBaoBenMutiple;
						}
						if(selectedItemStr.contains("1") && selectedItemStr.contains("0")) {
							int winDrawPosition = 0;
							if(winDrawSP>winWinSP) {
								winDrawPosition = winDrawPosition + 1;
							}
							if(winDrawSP>loseWinSP) {
								winDrawPosition = winDrawPosition + 1;
							}
							if(winDrawSP>loseDrawSp) {
								winDrawPosition = winDrawPosition + 1;
							}
							resultList.get(i)[1] =  (int)(((totalMultiple)/(resultCount/4)-allBaoBenMutiple)*percentArr[positionMap.get(winDrawPosition)]) + winDrawBaoBenMutiple;
							winDrawMutipleWithoutBaoben = (int)(((totalMultiple)/(resultCount/4)-allBaoBenMutiple)*percentArr[positionMap.get(winDrawPosition)]);
							surplusMutiple = surplusMutiple - (int)(((totalMultiple)/(resultCount/4)-allBaoBenMutiple)*percentArr[positionMap.get(winDrawPosition)]) - winDrawBaoBenMutiple;
						}	
						if(selectedItemStr.contains("2")&& selectedItemStr.contains("0")) {
							int loseWinPosition = 0;
							if(loseWinSP>winWinSP) {
								loseWinPosition = loseWinPosition + 1;
							}
							if(loseWinSP>winDrawSP) {
								loseWinPosition = loseWinPosition + 1;
							}
							if(loseWinSP>loseDrawSp) {
								loseWinPosition = loseWinPosition + 1;
							}
							resultList.get(i)[1] =  (int)(((totalMultiple)/(resultCount/4)-allBaoBenMutiple)*percentArr[positionMap.get(loseWinPosition)]*2) + loseWinBaoBenMutiple;
							loseWinMutipleWithoutBaoben = (int)(((totalMultiple)/(resultCount/4)-allBaoBenMutiple)*percentArr[positionMap.get(loseWinPosition)]*2);
							surplusMutiple = surplusMutiple - (int)(((totalMultiple)/(resultCount/4)-allBaoBenMutiple)*percentArr[positionMap.get(loseWinPosition)]*2) - loseWinBaoBenMutiple;
						}
						if(selectedItemStr.contains("2") && selectedItemStr.contains("1")) {
							int loseDrawPosition = 0;
							if(loseDrawSp>winWinSP) {
								loseDrawPosition = loseDrawPosition + 1;
							}
							if(loseDrawSp>winDrawSP) {
								loseDrawPosition = loseDrawPosition + 1;
							}
							if(loseDrawSp>loseWinSP) {
								loseDrawPosition = loseDrawPosition + 1;
							}
							resultList.get(i)[1] =  (int)(((totalMultiple)/(resultCount/4)-allBaoBenMutiple)*percentArr[positionMap.get(loseDrawPosition)]) + loseDrawBaoBenMutiple;
							loseDrawMutipleWithoutBaoben =  (int)(((totalMultiple)/(resultCount/4)-allBaoBenMutiple)*percentArr[positionMap.get(loseDrawPosition)]);
							surplusMutiple = surplusMutiple - (int)(((totalMultiple)/(resultCount/4)-allBaoBenMutiple)*percentArr[positionMap.get(loseDrawPosition)]) - loseDrawBaoBenMutiple;
						}						
					}
					Integer allMutipleWithoutBaoben = winWInMutipleWithoutBaoben + winDrawMutipleWithoutBaoben + loseWinMutipleWithoutBaoben + loseDrawMutipleWithoutBaoben;
					//当剩余注数大于0时，继续分配
					while(surplusMutiple > 0) {
						for(int i=k; i<k+4; i++) {	
							String selectedItemStr = resultList.get(i)[3].toString();
							if(!selectedItemStr.contains("1") && !selectedItemStr.contains("2") && selectedItemStr.contains("0")) {
								
								if((int)(((double)winWInMutipleWithoutBaoben/(double)allMutipleWithoutBaoben)*surplusMutiple) < 1) {
									if(surplusMutiple <= 0)
										break;
									resultList.get(i)[1] = (Integer)resultList.get(i)[1] + 1;
									surplusMutiple = surplusMutiple - 1;
								} else {
									if(surplusMutiple <= 0)
										break;
									resultList.get(i)[1] = (Integer)resultList.get(i)[1] + (int)(((double)winWInMutipleWithoutBaoben/(double)allMutipleWithoutBaoben)*surplusMutiple);
									surplusMutiple = surplusMutiple - (int)(((double)winWInMutipleWithoutBaoben/(double)allMutipleWithoutBaoben)*surplusMutiple);
								}
							}
							if(selectedItemStr.contains("1") && selectedItemStr.contains("0")) {																
								if((int)(((double)winDrawMutipleWithoutBaoben/(double)allMutipleWithoutBaoben)*surplusMutiple) < 1) {
									if(surplusMutiple <= 0)
										break;
									resultList.get(i)[1] = (Integer)resultList.get(i)[1] + 1;
									surplusMutiple = surplusMutiple - 1;
								} else {
									if(surplusMutiple <= 0)
										break;
									resultList.get(i)[1] = (Integer)resultList.get(i)[1] + (int)(((double)winDrawMutipleWithoutBaoben/(double)allMutipleWithoutBaoben)*surplusMutiple);
									surplusMutiple = surplusMutiple - (int)(((double)winDrawMutipleWithoutBaoben/(double)allMutipleWithoutBaoben)*surplusMutiple);
								}
								
							}	
							if(selectedItemStr.contains("2")&& selectedItemStr.contains("0")) {																
								if((int)(((double)loseWinMutipleWithoutBaoben/(double)allMutipleWithoutBaoben)*surplusMutiple) < 1) {
									if(surplusMutiple <= 0)
										break;
									resultList.get(i)[1] = (Integer)resultList.get(i)[1] + 1;
									surplusMutiple = surplusMutiple - 1;
								} else {
									if(surplusMutiple <= 0)
										break;
									resultList.get(i)[1] = (Integer)resultList.get(i)[1] + (int)(((double)loseWinMutipleWithoutBaoben/(double)allMutipleWithoutBaoben)*surplusMutiple);
									surplusMutiple = surplusMutiple - (int)(((double)loseWinMutipleWithoutBaoben/(double)allMutipleWithoutBaoben)*surplusMutiple);
								}
								
							}
							if(selectedItemStr.contains("2") && selectedItemStr.contains("1")) {						
								
								if((int)(((double)loseDrawMutipleWithoutBaoben/(double)allMutipleWithoutBaoben)*surplusMutiple) < 1) {
									if(surplusMutiple <= 0)
										break;
									resultList.get(i)[1] = (Integer)resultList.get(i)[1] + 1;
									surplusMutiple = surplusMutiple - 1;
								} else {
									if(surplusMutiple <= 0)
										break;
									resultList.get(i)[1] = (Integer)resultList.get(i)[1] + (int)(((double)loseDrawMutipleWithoutBaoben/(double)allMutipleWithoutBaoben)*surplusMutiple);
									surplusMutiple = surplusMutiple - (int)(((double)loseDrawMutipleWithoutBaoben/(double)allMutipleWithoutBaoben)*surplusMutiple);
								}
								
							}						
						}
					}
					//当剩余注数小于于0时，说明分配注数过多，按比例减少已分配的注数
					while(surplusMutiple < 0) {
						for(int i=k; i<k+4; i++) {		
							String selectedItemStr = resultList.get(i)[3].toString();
							if(!selectedItemStr.contains("1") && !selectedItemStr.contains("2") && selectedItemStr.contains("0")) {
								
								if((int)(((double)winWInMutipleWithoutBaoben/(double)allMutipleWithoutBaoben)*surplusMutiple) > -1) {
									if(surplusMutiple >= 0)
										break;
									resultList.get(i)[1] = (Integer)resultList.get(i)[1] - 1;
									surplusMutiple = surplusMutiple + 1;
								} else {
									if(surplusMutiple >= 0)
										break;
									resultList.get(i)[1] = (Integer)resultList.get(i)[1] + (int)(((double)winWInMutipleWithoutBaoben/(double)allMutipleWithoutBaoben)*surplusMutiple);
									surplusMutiple = surplusMutiple - (int)(((double)winWInMutipleWithoutBaoben/(double)allMutipleWithoutBaoben)*surplusMutiple);
								}
							}
							if(selectedItemStr.contains("1") && selectedItemStr.contains("0")) {																
								if((int)(((double)winDrawMutipleWithoutBaoben/(double)allMutipleWithoutBaoben)*surplusMutiple) > -1) {
									if(surplusMutiple >= 0)
										break;
									resultList.get(i)[1] = (Integer)resultList.get(i)[1] - 1;
									surplusMutiple = surplusMutiple + 1;
								} else {
									if(surplusMutiple >= 0)
										break;
									resultList.get(i)[1] = (Integer)resultList.get(i)[1] + (int)(((double)winDrawMutipleWithoutBaoben/(double)allMutipleWithoutBaoben)*surplusMutiple);
									surplusMutiple = surplusMutiple - (int)(((double)winDrawMutipleWithoutBaoben/(double)allMutipleWithoutBaoben)*surplusMutiple);
								}
								
							}	
							if(resultList.get(i)[3].toString().contains("2")&& resultList.get(i)[3].toString().contains("0")) {																
								if((int)(((double)loseWinMutipleWithoutBaoben/(double)allMutipleWithoutBaoben)*surplusMutiple) > -1) {
									if(surplusMutiple >= 0)
										break;
									resultList.get(i)[1] = (Integer)resultList.get(i)[1] - 1;
									surplusMutiple = surplusMutiple + 1;
								} else {
									if(surplusMutiple >= 0)
										break;
									resultList.get(i)[1] = (Integer)resultList.get(i)[1] + (int)(((double)loseWinMutipleWithoutBaoben/(double)allMutipleWithoutBaoben)*surplusMutiple);
									surplusMutiple = surplusMutiple - (int)(((double)loseWinMutipleWithoutBaoben/(double)allMutipleWithoutBaoben)*surplusMutiple);
								}
								
							}
							if(resultList.get(i)[3].toString().contains("2") && selectedItemStr.contains("1")) {						
								
								if((int)(((double)loseDrawMutipleWithoutBaoben/(double)allMutipleWithoutBaoben)*surplusMutiple) > -1) {
									if(surplusMutiple >= 0)
										break;
									resultList.get(i)[1] = (Integer)resultList.get(i)[1] - 1;
									surplusMutiple = surplusMutiple + 1;
								} else {
									if(surplusMutiple >= 0)
										break;
									resultList.get(i)[1] = (Integer)resultList.get(i)[1] + (int)(((double)loseDrawMutipleWithoutBaoben/(double)allMutipleWithoutBaoben)*surplusMutiple);
									surplusMutiple = surplusMutiple - (int)(((double)loseDrawMutipleWithoutBaoben/(double)allMutipleWithoutBaoben)*surplusMutiple);
								}
								
							}						
						}
					}
					
				}
				
			}
			break;
		}
	}
	
	
	
	/**
	 * 调整倍数计算缺失
	 * @param resultList
	 * @param multile 浮动注数(正为多，负为少)
	 */
	private void adjustMultiple(List<Object[]> resultList,int multiple){
		int total = resultList.size();
		
		/**
		 * 可根据优化后的注数来定
		 * 如果注数大则将其设置小，否则可设置大些
		 * 根据实际情况来做调整
		 */
		int maxAdjustNum = 10;//精细调整的倍投数(大-精)
		if(total>5000){
			maxAdjustNum = 0;
		}else if(total>3000){
			maxAdjustNum = 3;
		}else if(total>2000){
			maxAdjustNum = 5;
		}
		
		int operIndex = 0;
		int operNum = 0;
		if(multiple>0){
			operNum = -1;
			if(multiple>maxAdjustNum){
				int dimOperNum = multiple-maxAdjustNum;
				for(int i=0;i<dimOperNum;i++){
					resultList.get(i)[1] = (Integer)resultList.get(i)[1]+operNum;
					multiple--;
				}
			}
			if(multiple!=0){
				List<Object[]> compareList = Lists.newArrayList();
				for(int i=0;i<total;i++){
					Object[] item = (Object[])resultList.get(i);
					if((Integer)item[1]>1){
						compareList.add(item);
					}	
				}
				operIndex = findOperIndex(operNum,compareList);
				compareList.get(operIndex)[1] = (Integer)compareList.get(operIndex)[1]+operNum;
				multiple=multiple+operNum;
			}			
		}else{
			operNum = 1;
			if(multiple+maxAdjustNum<0){
				int dimOperNum = Math.abs(multiple+maxAdjustNum);
				for(int i=0;i<dimOperNum;i++){
					resultList.get(i)[1] = (Integer)resultList.get(i)[1]+operNum;
					multiple++;
				}
			}
			if(multiple!=0){
				operIndex = findOperIndex(operNum,resultList);
				resultList.get(operIndex)[1] = (Integer)resultList.get(operIndex)[1]+operNum;
				multiple=multiple+operNum;
			}
		}
		
		if(multiple!=0){
			adjustMultiple(resultList,multiple);
		}
	}
	
	/**
	 * 查操作的下标
	 * @param operNum
	 * @param compareList
	 * @return
	 */
	private int findOperIndex(int operNum,List<Object[]> compareList){
		int operIndex = 0;//操作的下标
		int total = compareList.size();
		List<Double[]> index_Sums = Lists.newArrayList();
		Double[] sums = null;
		for(int i=0;i<total;i++){
			sums = new Double[total];
			int j=0;
			for(Object[] item : compareList){
				if(i==j){
					sums[j] = ((Integer)item[1]+operNum)*(Double)item[2];
				}else{
					sums[j] = (Integer)item[1]*(Double)item[2];
				}
				j++;
			}
			index_Sums.add(sums);
		}
		//对计算结果查差值最小的就是需要填补的位置
		double mixNum = 0;
		double currNum = 0;
		for(int i=0;i<index_Sums.size();i++){				
			currNum = this.getMaxMin(index_Sums.get(i));
			if (mixNum==0){
				mixNum = currNum;
			}			
			if(mixNum >= currNum){
				mixNum = currNum;
				operIndex=i;
			}
		}
		return operIndex;
	}
	
	
	/**
	 * 获取数组的最大的差值
	 * @param nums
	 * @return
	 */
	private double getMaxMin(Double... nums) {
		if (nums == null || nums.length == 0) {
			return 0;
		}
		double max = nums[0];
		double min = nums[0];
		for (double d : nums) {
			if (d - max > 10e-6) {
				max = d;
			}
			if (min - d > 10e-6) {
				min = d;
			}
		}
		return (max - min);
	}

	/**
	 * 优化方案的比较器，按金额小到大
	 *
	 */
	private class ContentComparator implements Comparator<Object> {
		public int compare(Object o1, Object o2) {
			Object[] c1 = (Object[]) o1;
			Object[] c2 = (Object[]) o2;
			if ((Double) c1[2] > (Double) c2[2]) {
				return 1;
			} else {
				if ((Double) c1[2] == (Double) c2[2]) {
					return 0;
				} else {
					return -1;
				}
			}
		}
	}
	
	/**
	 * 构建奖金预测的相关数据
	 * @param resultList
	 * @param matchKeyList
	 */
	protected Map<String,double[]> buildPrizeForecast(final List<Object[]> resultList,final List<String> matchKeyList,final Map<String,List<Item>> excludeItems){
		
		/**用于提供方案优化预测数据*/
		Map<String,Map<String,Double>> currentForecastDataMap = Maps.newHashMap();
		/**优化方案预测最小最大*/
		Map<String,double[]> forcastMinMaxPrizeMap = Maps.newHashMap();
		
		List<MapKeyItem> mapKeyItems=Lists.newArrayList();
		boolean maxExcludeFlag;
		for(int index =0;index<resultList.size();index++){
			Object[] result = resultList.get(index);
			String values = String.valueOf(result[3]);
			String playTypes = String.valueOf(result[4]);
			String[] valueArr = values.split(",");
			String[] playTypeArr = playTypes.split(",");
			
			mapKeyItems.clear();
			maxExcludeFlag = false;
			for(int i=0;i<valueArr.length;i++){
				String value = valueArr[i];
				if("#".equals(value)){
					continue;
				}
				String matchKey = matchKeyList.get(i);
				String playType = playTypeArr[i];
				mapKeyItems.add(new MapKeyItem(matchKey,playType));
				
				if(excludeItems!=null && !maxExcludeFlag){//排它项判断
					List<Item> excludeItemList = excludeItems.get(matchKey+playType);
					if(excludeItemList!=null){
						for(Item item : excludeItemList){
							if(item.ordinal()==Integer.parseInt(value)){
								maxExcludeFlag = true;
								break;
							}
						}
					}
				}
			}
			
			MapKeyBuilder mapKeyBuilder = new MapKeyBuilder(mapKeyItems);
			String mapKeyOfMatch = mapKeyBuilder.getMapKeyOfMatch();
			String mapKeyOfPlayType = mapKeyBuilder.getMapKeyMatchOfPlayType();
			double prize = Double.valueOf(result[2].toString())*Integer.valueOf(result[1].toString());
			double maxPrize = maxExcludeFlag?0.0d:prize;
			
			Map<String,Double> mapPlayTypePrize = currentForecastDataMap.get(mapKeyOfMatch);//用于最大奖金统计
			
			if(mapPlayTypePrize==null){
				mapPlayTypePrize = new HashMap<String,Double>();
				mapPlayTypePrize.put(mapKeyOfPlayType, maxPrize);
				currentForecastDataMap.put(mapKeyOfMatch,mapPlayTypePrize);
				forcastMinMaxPrizeMap.put(mapKeyOfMatch, new double[]{prize,maxPrize});
			}else{
				//记录最小奖金
				double minPrizeOfMatch = forcastMinMaxPrizeMap.get(mapKeyOfMatch)[0];
				if(minPrizeOfMatch>prize){
					forcastMinMaxPrizeMap.get(mapKeyOfMatch)[0] = prize;
				}
				
				//分玩法记录最大奖金
				if(maxExcludeFlag)continue;
				if(mapPlayTypePrize.containsKey(mapKeyOfPlayType)){
					Double prizeOfMap = mapPlayTypePrize.get(mapKeyOfPlayType);
					if(prizeOfMap==0 || prize>prizeOfMap){
						mapPlayTypePrize.put(mapKeyOfPlayType, prize);
					}
				}else{
					mapPlayTypePrize.put(mapKeyOfPlayType, prize);
				}
			}
		}
		//以场次为标识记录最大奖金		
		Iterator<Entry<String,Map<String,Double>>> itorOfMatch = currentForecastDataMap.entrySet().iterator();
		while(itorOfMatch.hasNext()){
			Entry<String, Map<String,Double>> itemOfMatch = itorOfMatch.next();
			Map<String,Double> mapPlayType = itemOfMatch.getValue();
			Iterator<Entry<String,Double>> itorOfPlayType = mapPlayType.entrySet().iterator();
			Double maxPrizeOfMatch = 0.0D;
			while(itorOfPlayType.hasNext()){
				Entry<String,Double> itemOfPlayType = itorOfPlayType.next();
				maxPrizeOfMatch += itemOfPlayType.getValue();
			}
			forcastMinMaxPrizeMap.get(itemOfMatch.getKey())[1]=maxPrizeOfMatch;
			//System.out.println("运行:"+maxPrizeOfMatch);
		}		
		//System.out.println("运行结束");
		return forcastMinMaxPrizeMap;
	}
	
	
	
	/**
	 * 优化内容的Map主键构造
	 * @author jack
	 *
	 */
	class MapKeyBuilder{
		public MapKeyBuilder() {
			super();
		}
		public MapKeyBuilder(List<MapKeyItem> keys) {
			super();
			this.keys = keys;
			Collections.sort(this.keys, new Comparator<MapKeyItem>() {
				public int compare(MapKeyItem o1, MapKeyItem o2) {
					return o1.getMatchKey().compareTo(o2.getMatchKey());			
				}
			});
		}
		private List<MapKeyItem> keys;
		
		public String getMapKeyOfMatch(){			
			StringBuffer sb = new StringBuffer();
			for(MapKeyItem keyItem : this.keys){
				sb.append(keyItem.getMatchKey());
			}
			return sb.toString();
		}
		public String getMapKeyMatchOfPlayType(){			
			StringBuffer sb = new StringBuffer();
			for(MapKeyItem keyItem : this.keys){
				sb.append(keyItem.getPlayType());
			}
			return sb.toString();
		}		
	}
	
	/**
	 * 优化内容Map主键项
	 * @author jack
	 *
	 */
	class MapKeyItem{
		public MapKeyItem(String matchKey, String playType) {
			super();
			this.matchKey = matchKey;
			this.playType = playType;
		}
		private String matchKey;
		private String playType;
		public String getMatchKey() {
			return matchKey;
		}
		public void setMatchKey(String matchKey) {
			this.matchKey = matchKey;
		}
		public String getPlayType() {
			return playType;
		}
		public void setPlayType(String playType) {
			this.playType = playType;
		}		
	}	
	
	public OptimizeType getOptimizeType() {
		return optimizeType;
	}

	public void setOptimizeType(OptimizeType optimizeType) {
		this.optimizeType = optimizeType;
	}

	public void setPrizeForecast(boolean prizeForecast) {
		this.prizeForecast = prizeForecast;
	}

	public List<Double> getItemPrizes() {
		return itemPrizes;
	}

	public void setItemPrizes(List<Double> itemPrizes) {
		this.itemPrizes = itemPrizes;
	}

	
//	public void addActionError(String anErrorMessage) {
//	String s = anErrorMessage;
//	System.out.println(s);
//}
//
//public void addActionMessage(String aMessage) {
//	String s = aMessage;
//	System.out.println(s);
//
//}
//
//public void addFieldError(String fieldName, String errorMessage) {
//	String s = errorMessage;
//	String f = fieldName;
//	System.out.println(s);
//	System.out.println(f);
//
//}
	
}
