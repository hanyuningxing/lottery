package com.cai310.lottery.entity.user;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.cai310.entity.CreateMarkable;
import com.cai310.entity.UpdateMarkable;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.support.user.GradeMedal;
import com.cai310.lottery.utils.StringUtil;
import com.cai310.utils.JsonUtil;
import com.google.common.collect.Maps;

/**
 * 用户战绩信息
 * @author jack
 *
 */
@Entity
@Table(name = com.cai310.lottery.Constant.LOTTERY_TABLE_PREFIX + "USER_GRADE")
public class UserGrade implements CreateMarkable, UpdateMarkable,Serializable{
	
	private static final long serialVersionUID = 4706894518187439525L;
	
	private Long id;
		
	/**总奖金*/
	private BigDecimal totalPrize;
	
	/**中奖次数*/
	private int wonTimes;
	
	/**万元中奖次数*/
	private int wonTimes_wan;
	
	/**千元中奖次数*/
	private int wonTimes_qian;
	
	/**发单数*/
	private int fadanNums;
	
	/**发单成功数*/
	private int fadanSuccessNums;
	
	/**分玩法奖金*/
	private String prizes_play;
	
	/**分玩法奖金Map封装*/
	private Map<String,BigDecimal> prize_play_result;
	
	/**分玩法奖牌*/
	private String medals_play;
	
	/**分玩法奖牌Map*/
	private Map<String,GradeMedal> medalsMap;
	
	/**足球类总奖牌*/
	private GradeMedal medal_zq;
	
	/**篮球类总奖牌*/
	private GradeMedal medal_lq;	
		
	/** 购彩账户 */
	private User user;
	
	/** 创建时间 */
	private Date createTime;

	/** 最后更新时间 */
	private Date lastModifyTime;

	@Id
	@Column(name = "id")
	@GenericGenerator(name = "generator", strategy = "foreign", parameters = { @Parameter(name = "property", value = "user") })
	@GeneratedValue(generator = "generator")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return {@link #user}
	 */
	@OneToOne(optional = false)
	@PrimaryKeyJoinColumn
	public User getUser() {
		return user;
	}

	/**
	 * @param user the {@link #user} to set
	 */
	public void setUser(User user) {
		this.user = user;
	}

	@Column
	public BigDecimal getTotalPrize() {
		return totalPrize;
	}

	public void setTotalPrize(BigDecimal totalPrize) {
		this.totalPrize = totalPrize;
	}

	@Column
	public int getWonTimes() {
		return wonTimes;
	}

	public void setWonTimes(int wonTimes) {
		this.wonTimes = wonTimes;
	}

	@Column
	public int getWonTimes_wan() {
		return wonTimes_wan;
	}

	public void setWonTimes_wan(int wonTimes_wan) {
		this.wonTimes_wan = wonTimes_wan;
	}

	@Column
	public int getWonTimes_qian() {
		return wonTimes_qian;
	}

	public void setWonTimes_qian(int wonTimes_qian) {
		this.wonTimes_qian = wonTimes_qian;
	}

	@Column
	public int getFadanNums() {
		return fadanNums;
	}

	public void setFadanNums(int fadanNums) {
		this.fadanNums = fadanNums;
	}

	@Column
	public int getFadanSuccessNums() {
		return fadanSuccessNums;
	}

	public void setFadanSuccessNums(int fadanSuccessNums) {
		this.fadanSuccessNums = fadanSuccessNums;
	}

	@Column
	public String getPrizes_play() {
		return prizes_play;
	}

	public void setPrizes_play(String prizes_play) {
		this.prizes_play = prizes_play;
	}
	
	/**
	 * 获取玩法奖金的Map
	 * @return
	 */
	@Transient
	public Map<String,BigDecimal> getPrizeMap(){
		if(this.prize_play_result==null){
			return this.converPrizeMap();
		}
		return this.prize_play_result;
	}
	
	/**
	 * 从json中解析玩法奖金Map
	 * @return
	 */
	private Map<String,BigDecimal> converPrizeMap(){
		Map<String,BigDecimal> resultMap = Maps.newHashMap();
		if(StringUtil.isEmpty(this.prizes_play)){
			return null;
		}
		Map<String,Object> jsonMap = JsonUtil.getMap4Json(this.prizes_play);
		Set<String> keys = jsonMap.keySet();
		Object value = null;
		for(String key : keys){
			value = jsonMap.get(key);
			resultMap.put(key, BigDecimal.valueOf(Double.valueOf(String.valueOf(value))));
		}
		this.prize_play_result = resultMap;
		return resultMap;
	}
	
	/**
	 * 按彩种与玩法获取相关奖金
	 * @param lottery
	 * @param playTypeStr
	 * @return
	 */
	@Transient
	public BigDecimal getPrizeOfLotteryAndPlay(Lottery lottery,String playTypeStr){
		String key=this.bulidMapKey(lottery, playTypeStr);
		BigDecimal prize = this.getPrizeMap().get(key);
		if(prize==null){
			prize = new BigDecimal(0);
		}
		return prize;
	}
	
	/**
	 * 获取玩法分类的Map主键
	 * @param lottery
	 * @param playTypeStr
	 * @return
	 */
	private String bulidMapKey(Lottery lottery,String playTypeStr){
		StringBuffer key = new StringBuffer();
		key.append(lottery);
		if(!StringUtil.isEmpty(playTypeStr)){
			key.append("_").append(playTypeStr);
		}
		return key.toString();
	}
	
	/**
	 * 根据彩种玩法获取相关的类型及将key转为中文显示
	 * @return resultArr[2] 
	 */
	@Transient
	public String[] getTypeAndKey2Text(String key){
		if(key==null)return null;
		String[] resultArr=new String[2];
		resultArr[0] = "zq";
		StringBuffer sb = new StringBuffer();
		String[] keyArr = key.split("_");
		Lottery lottery = Lottery.valueOf(keyArr[0]);
		sb.append(lottery.getLotteryName());
		switch(lottery){
		case JCZQ:
			if(keyArr.length>1)sb.append(com.cai310.lottery.support.jczq.PlayType.valueOfName(keyArr[1]).getText());
			break;
		case JCLQ:
			resultArr[0] = "lq";
			if(keyArr.length>1)sb.append(com.cai310.lottery.support.jclq.PlayType.valueOfName(keyArr[1]).getText());
			break;
		case DCZC:
			if(keyArr.length>1)sb.append(com.cai310.lottery.support.dczc.PlayType.valueOfName(keyArr[1]).getText());
			break;
		case SFZC:
			if(keyArr.length>1)sb.append(com.cai310.lottery.support.zc.PlayType.valueOfName(keyArr[1]).getText());
			break;
		}
		resultArr[1] = sb.toString();
		return resultArr;
	}

	@Column
	public String getMedals_play() {
		return medals_play;
	}

	public void setMedals_play(String medals_play) {
		this.medals_play = medals_play;
	}
	
	@Transient
	public Map<String,GradeMedal> getMedalMap(){
		if(medalsMap!=null)return medalsMap;
		if(StringUtil.isEmpty(this.medals_play)){
			return null;
		}
		medalsMap = JsonUtil.getMap4Json(this.medals_play,GradeMedal.class);
		return medalsMap;
	}
	
	/**
	 * 按彩种与玩法查奖牌对象
	 * @param lottery
	 * @param playTypeStr
	 * @return
	 */
	@Transient
	public GradeMedal getMedalOfLotteryAndPlay(Lottery lottery,String playTypeStr){
		String key = this.bulidMapKey(lottery, playTypeStr);
		Map<String,GradeMedal> medalMap = this.getMedalMap();
		if(medalMap==null)return null;
		return medalMap.get(key);
	}
	
	/**
	 * 统计足球类奖牌
	 * @return
	 */
	@Transient
	public GradeMedal getMedal_zq() {
		if(medal_zq!=null)return this.medal_zq;
		Map<String,GradeMedal> medalMap = this.getMedalMap();
		if(medalMap==null)return null;
		medal_zq = new GradeMedal();
		com.cai310.lottery.support.jczq.PlayType[] jcPlayTypes = com.cai310.lottery.support.jczq.PlayType.values();
		for(com.cai310.lottery.support.jczq.PlayType playType : jcPlayTypes){
			this.statGradeMedalOfTotal(medal_zq, Lottery.JCZQ, playType.toString(),medalMap);
		}
		com.cai310.lottery.support.dczc.PlayType[] dcPlayTypes = com.cai310.lottery.support.dczc.PlayType.values();
		for(com.cai310.lottery.support.dczc.PlayType playType : dcPlayTypes){
			this.statGradeMedalOfTotal(medal_zq, Lottery.DCZC, playType.toString(),medalMap);
		}
		com.cai310.lottery.support.zc.PlayType[] zcPlayTypes = com.cai310.lottery.support.zc.PlayType.values();
		for(com.cai310.lottery.support.zc.PlayType playType : zcPlayTypes){
			this.statGradeMedalOfTotal(medal_zq, Lottery.SFZC, playType.toString(),medalMap);
		}
		this.statGradeMedalOfTotal(medal_zq, Lottery.LCZC, null,medalMap);
		this.statGradeMedalOfTotal(medal_zq, Lottery.SCZC, null,medalMap);
		return medal_zq;
	}

	/**
	 * 统计篮球类奖牌
	 * @return
	 */
	@Transient
	public GradeMedal getMedal_lq() {
		if(medal_lq!=null)return this.medal_lq;
		Map<String,GradeMedal> medalMap = this.getMedalMap();
		if(medalMap==null)return null;
		medal_lq = new GradeMedal();
		com.cai310.lottery.support.jclq.PlayType[] playTypes = com.cai310.lottery.support.jclq.PlayType.values();
		for(com.cai310.lottery.support.jclq.PlayType playType : playTypes){
			this.statGradeMedalOfTotal(medal_lq, Lottery.JCLQ, playType.toString(),medalMap);
		}
		return medal_lq;
	}
	
	/**
	 * 统计动作
	 * @param gradeMedal
	 * @param lottery
	 * @param playTypeStr
	 * @param medalMap
	 */
	private void statGradeMedalOfTotal(GradeMedal gradeMedal,Lottery lottery,String playTypeStr,Map<String,GradeMedal> medalMap){
		String key = this.bulidMapKey(Lottery.JCZQ, playTypeStr);
		GradeMedal gm = medalMap.get(key);
		if(gm!=null){
			medal_zq.setGoldenNums(medal_zq.getGoldenNums()+medalMap.get(key).getGoldenNums());
			medal_zq.setSilveryNums(medal_zq.getSilveryNums()+medalMap.get(key).getSilveryNums());
		}		
	}

	/**
	 * @return the createTime
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false, updatable = false)
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(insertable = false)
	public Date getLastModifyTime() {
		return lastModifyTime;
	}

	public void setLastModifyTime(Date lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}
	
	
}
