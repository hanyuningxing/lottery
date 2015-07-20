package com.cai310.lottery.web.controller.lottery.dczc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.support.ContentBean;
import com.cai310.lottery.utils.BigDecimalUtil;
import com.cai310.lottery.web.controller.lottery.SchemeCreateForm;

public class DczcFilterForm extends SchemeCreateForm{
	private List<String> sps = new ArrayList<String>();
	private List<String> condition = new ArrayList<String>();
	private List<String> spfs = new ArrayList<String>();
	private List<String> rcCondtion = new ArrayList<String>();
	private List<String> sp = new ArrayList<String>();
	private List<String> jhCondtion = new ArrayList<String>();
	
	//指数最小和
	private double indexMinSum = 0;
	//指数最大和
	private double indexMaxSum = 0;
	//指数最小积
	private double indexMinPlot = 0;
	//指数最大积
	private double indexMaxPlot = 0;
	//奖金预计最小值
	private double minPrize = 0;
	//奖金预计最大值
	private double maxPrize = 0;
	private String params;
	private Integer matchCount = 0;
	
	private boolean current;
	
	@Override
	protected ContentBean buildCompoundContentBean() throws DataException {
		return null;
	}

	@Override
	protected ContentBean buildSingleContentBean() throws DataException {
		return null;
	}

	public List<String> getSps() {
		ArrayList<Double> datas = new ArrayList<Double>();
		ArrayList<Double> everyMatchSp = null;
		String[] param = params.split("#");
		double[] minSps = new double[param.length]; 
		double[] maxSps = new double[param.length]; 
		double indexMinSum = 0;
		double indexMaxSum = 0;
		double indexMinPlot = 1;
		double indexMaxPlot = 1;
		for(int i=0;i<param.length;i++){
			everyMatchSp = new ArrayList<Double>();
			sps.add(param[i]);
			String[] tempValues = param[i].split(",");
			for(String temp:tempValues){
				everyMatchSp.add(new Double(temp));
				datas.add(new Double(temp));
			}	
			Collections.sort(everyMatchSp);
			minSps[i] = everyMatchSp.get(0);
			maxSps[i] = everyMatchSp.get(everyMatchSp.size()-1);
			indexMinSum+=minSps[i];
			indexMaxSum+=maxSps[i];
			indexMinPlot*=minSps[i];
			indexMaxPlot*=maxSps[i];
		}
		this.setIndexMinSum(indexMinSum);
		this.setIndexMaxSum(indexMaxSum);
		BigDecimal minPlot=new BigDecimal(indexMinPlot);  
		this.setIndexMinPlot(new Double(minPlot.setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue()));
		BigDecimal maxPlot=new BigDecimal(indexMaxPlot);  
		this.setIndexMaxPlot(new Double(maxPlot.setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue()));

		Collections.sort(datas);
		if(datas.size()>0){
			double minSpsPlot = 1;
			double maxSpsPlot = 1;
			for(int i=0;i<minSps.length;i++){
				minSpsPlot*=minSps[i];
				maxSpsPlot*=maxSps[i];
			}
			this.setMinPrize(minSpsPlot*1.3);
			this.setMaxPrize(maxSpsPlot*1.3);
		}
		this.setMatchCount(param.length);
		return sps;
	}

	public void setSps(List<String> sps) {
		this.sps = sps;
	}

	public double getIndexMinSum() {
		return indexMinSum;
	}

	public void setIndexMinSum(double indexMinSum) {
		this.indexMinSum = indexMinSum;
	}

	public double getIndexMaxSum() {
		return indexMaxSum;
	}

	public void setIndexMaxSum(double indexMaxSum) {
		this.indexMaxSum = indexMaxSum;
	}

	public double getIndexMinPlot() {
		return indexMinPlot;
	}

	public void setIndexMinPlot(double indexMinPlot) {
		this.indexMinPlot = indexMinPlot;
	}

	public double getIndexMaxPlot() {
		return indexMaxPlot;
	}

	public void setIndexMaxPlot(double indexMaxPlot) {
		this.indexMaxPlot = indexMaxPlot;
	}

	public double getMinPrize() {
		return minPrize;
	}

	public void setMinPrize(double minPrize) {
		this.minPrize = minPrize;
	}

	public double getMaxPrize() {
		return maxPrize;
	}

	public void setMaxPrize(double maxPrize) {
		this.maxPrize = maxPrize;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public Integer getMatchCount() {
		return matchCount;
	}

	public void setMatchCount(Integer matchCount) {
		this.matchCount = matchCount;
	}

	public List<String> getCondition() {
		return condition;
	}

	public void setCondition(List<String> condition) {
		this.condition = condition;
	}

	public List<String> getSpfs() {
		return spfs;
	}

	public void setSpfs(List<String> spfs) {
		this.spfs = spfs;
	}

	public boolean isCurrent() {
		return current;
	}

	public void setCurrent(boolean current) {
		this.current = current;
	}

	public List<String> getRcCondtion() {
		return rcCondtion;
	}

	public void setRcCondtion(List<String> rcCondtion) {
		this.rcCondtion = rcCondtion;
	}

	public List<String> getSp() {
		return sp;
	}

	public void setSp(List<String> sp) {
		this.sp = sp;
	}

	public List<String> getJhCondtion() {
		return jhCondtion;
	}

	public void setJhCondtion(List<String> jhCondtion) {
		this.jhCondtion = jhCondtion;
	}

}
