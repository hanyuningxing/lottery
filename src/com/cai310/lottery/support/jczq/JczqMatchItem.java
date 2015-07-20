package com.cai310.lottery.support.jczq;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import com.cai310.lottery.support.Item;
import com.cai310.lottery.support.SelectedCount;
import com.cai310.lottery.utils.StringUtil;
import com.google.common.collect.Lists;

public class JczqMatchItem implements SelectedCount, Serializable, Comparable<JczqMatchItem> {
	private static final long serialVersionUID = -2542287020185175888L;

	/** 场次标识 */
	private String matchKey;

	/** 选择的内容 */
	private int value;

	/** 是否设胆 */
	private boolean dan;
	
	/** 混合配 */
	private PlayType playType;
	
	/** 记录投注场次的赔率*/
	private List<String> sps;
	/**
	 * @return the matchKey
	 */
	public String getMatchKey() {
		return matchKey;
	}

	/**
	 * @param matchKey the matchKey to set
	 */
	public void setMatchKey(String matchKey) {
		this.matchKey = matchKey;
	}

	/**
	 * @return {@link #value}
	 */
	public int getValue() {
		return value;
	}

	/**
	 * @param value the {@link #value} to set
	 */
	public void setValue(int value) {
		this.value = value;
	}

	/**
	 * @return {@link #dan}
	 */
	public boolean isDan() {
		return dan;
	}

	/**
	 * @param dan the {@link #dan} to set
	 */
	public void setDan(boolean dan) {
		this.dan = dan;
	}

	public int compareTo(JczqMatchItem o) {
		if(this.matchKey.equals(o.getMatchKey())){
			if(this.playType.ordinal()==o.getPlayType().ordinal()){
				return 0;
			}else if(this.playType.ordinal()>o.getPlayType().ordinal()){
				return 1;
			}else{
				return -1;
			}
		}
		return this.matchKey.compareTo(o.matchKey);
	}

	/**
	 * 计算选择的选项个数
	 * 
	 * @return 选择的选项个数
	 */
	public int selectedCount() {
		int v = value;
		int i = 0;
		int count = 0;
		while (v > 0) {
			int a = 1 << i;
			if ((v & a) > 0) {
				count++;
				v ^= a;
			}
			i++;
			if (i > 31)
				throw new RuntimeException("数据异常！");
		}
		return count;
	}
	

	/**
	 * 是否选择了某个选项
	 * 
	 * @param item 某个选项
	 * @return 是否有选择
	 */
	public boolean hasSelect(Item item) {
		return hasSelect(item.ordinal());
	}

	/**
	 * 是否有选择某个选项
	 * 
	 * @param itemOrdinal 选项的序号
	 * @return 是否有选择
	 */
	public boolean hasSelect(int itemOrdinal) {
		return (this.value & (1 << itemOrdinal)) > 0;
	}

	public PlayType getPlayType() {
		return playType;
	}

	public void setPlayType(PlayType playType) {
		this.playType = playType;
	}

	public List<String> getSps() {
		return sps;
	}

	public void setSps(List<String> sps) {
		this.sps = sps;
	}

	public void setSpStr(String spStr) {
		if(!StringUtil.isEmpty(spStr)){
			this.sps = Arrays.asList(spStr.split(","));
		}
	}
	
	public String buildSpStr(){
		StringBuffer sb = new StringBuffer();
		for(String sp : sps){
			sb.append(sp).append(",");
		}
		sb.deleteCharAt(sb.length()-1);
		return sb.toString();
	}
	
	/**
	 * 场次玩法选中项的最小最大赔率
	 * @return
	 */
	public double[] findSelectedMinMaxSp(List<Item> exculdeItems){
		Item[] allItem = playType.getAllItems();
		int itemLength = allItem.length;
		double[] minMaxSp = new double[]{0d,0d};
		double sp;
		for(int i=0;i<itemLength;i++){
			try{
				sp = Double.parseDouble(sps.get(i));
			}catch(NumberFormatException e){
				sp=0.0d;
			}
			if(hasSelect(allItem[i])){
				if(minMaxSp[0]==0 || minMaxSp[0]>sp)minMaxSp[0] = sp;
				boolean needExculde = false;
				if(exculdeItems!=null){
					for(Item item : exculdeItems){
						if(item.ordinal()==allItem[i].ordinal()){
							needExculde = true;
							break;
						}
					}
				}				
				if((exculdeItems==null || !needExculde) && (minMaxSp[1]==0 || minMaxSp[1]<sp)){
					minMaxSp[1] = sp;
				}
			}
		}
		return minMaxSp;
	}
	
	/**
	 * 获取场次选中项的最大赔率及项
	 * @return object[] item double
	 */
	public Object[] findSelectedMaxItemAndSp(){
		Object[] maxSpInfo = new Object[2]; 
		Item[] allItem = playType.getAllItems();
		int itemLength = allItem.length;
		double maxSp = 0.0d;
		Item item = null;
		for(int i=0;i<itemLength;i++){
			double sp = Double.parseDouble(sps.get(i));
			if(hasSelect(allItem[i])){
				if(maxSp==0 || maxSp<sp){
					item = allItem[i];
					maxSp = sp;
				}
			}
		}
		maxSpInfo[0] = item;
		maxSpInfo[1] = maxSp;
		return maxSpInfo;
	}
	
	
	/**
	 * 获取投注选项详细字符串
	 * @return
	 */
	public String buildSelectedItemStr(Item result){
		Item[] items = playType.getAllItems();
		StringBuffer sb = new StringBuffer();
		for(Item item : items){
			if((this.value & 1<<item.ordinal())>0){
				if(item == result) {
					if((this.value & 1<<item.ordinal())>0){
						if(null!=this.getSps()&&!this.getSps().isEmpty()){
							sb.append("<span class='rc1'>" + item.getText()).append('(').append(this.getSps().get(item.ordinal())).append(')').append("</span>").append(',');
						}else{
							///兼容手机没有上传sp
							sb.append("<span class='rc1'>" + item.getText()).append("</span>").append(',');
						}
					}
				} else {
					if((this.value & 1<<item.ordinal())>0){
						if(null!=this.getSps()&&!this.getSps().isEmpty()){
							sb.append(item.getText()).append('(').append(this.getSps().get(item.ordinal())).append(')').append(',');	
						}else{
							///兼容手机没有上传sp
							sb.append(item.getText()).append(',');	
						}
					}
				}
			}
		}
		sb.deleteCharAt(sb.length()-1);
		return sb.toString();
	}
	
	/**
	 * 获取投注选项详细字符串
	 * @return
	 */
	public String buildSelectedItemStr(){
		Item[] items = playType.getAllItems();
		StringBuffer sb = new StringBuffer();
		for(Item item : items){
			if((this.value & 1<<item.ordinal())>0){
				if(null!=this.getSps()&&!this.getSps().isEmpty()){
					sb.append(item.getText()).append('(').append(this.getSps().get(item.ordinal())).append(')').append(',');	
				}else{
					///兼容手机没有上传sp
					sb.append(item.getText()).append(',');	
				}
			}
		}
		sb.deleteCharAt(sb.length()-1);
		return sb.toString();
	}
	
	
	/**
	 * 获取投注选项详细字符串
	 * @return
	 */
	public String buildSelectedItemStrOfEXY(){
		Item[] items = playType.getAllItems();
		StringBuffer sb = new StringBuffer();
		for(Item item : items){
			if((this.value & 1<<item.ordinal())>0){
				if((this.value & 1<<item.ordinal())>0){
					if(null!=this.getSps()&&!this.getSps().isEmpty()){
						sb.append('(').append(this.getSps().get(item.ordinal())).append(')').append(',');	
					}else{
						///兼容手机没有上传sp
						sb.append(item.getText()).append(',');	
					}
				}
			}
		}
		sb.deleteCharAt(sb.length()-1);
		return sb.toString();
	}
	
	/**
	 * 获取投注选项详细列表
	 * @return
	 */
	public List<String> buildSelectedItemList(){
		Item[] items = playType.getAllItems();
		List<String> selectedItemList = Lists.newArrayList();
		for(Item item : items){
			if((this.value & 1<<item.ordinal())>0){
				if(null!=this.getSps()&&!this.getSps().isEmpty()){
					selectedItemList.add(playType.getText() + ":" + item.getText()+" "+ this.getSps().get(item.ordinal()));
				}else{
					///兼容手机没有上传sp
					selectedItemList.add(item.getText()+" ");
				}
				
			}
		}
		return selectedItemList;
	}
	
	
	
}
