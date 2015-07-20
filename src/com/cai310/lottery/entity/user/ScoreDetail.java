package com.cai310.lottery.entity.user;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.Version;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.http.client.ClientProtocolException;

import com.cai310.entity.CreateMarkable;
import com.cai310.entity.IdEntity;
import com.cai310.entity.UpdateMarkable;
import com.cai310.lottery.common.FundDetailType;
import com.cai310.lottery.common.FundMode;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.common.LotteryCategory;
import com.cai310.lottery.common.PlatformInfo;
import com.cai310.lottery.common.SalesMode;
import com.cai310.lottery.common.ScoreDetailType;
import com.cai310.lottery.ticket.common.HttpclientUtil;
import com.cai310.utils.DateUtil;
import com.cai310.utils.JsonUtil;
import com.cai310.utils.Struts2Utils;

import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.hibernate.type.EnumType;

/**
 * <b>资金明细.</b>
 * <p>
 * 资金明细是不能更新的, 所有字段都设置了[updatable = false].
 * </p>
 * 
 */
@Table(name =com.cai310.lottery.Constant.LOTTERY_TABLE_PREFIX + "SCORE_DETAIL")
@Entity
public class ScoreDetail extends IdEntity implements CreateMarkable, UpdateMarkable, Serializable {
	private static final long serialVersionUID = -5229478943870811148L;
	/** 版本号,用于实现乐观锁 */
	private Integer version;
	/** 用户ID */
	private Long userId;

	/** 用户名 */
	private String userName;

	/** 此次操作涉及的金额 */
	private BigDecimal score;

	/** 操作后用户的账户余额 */
	private BigDecimal resultScore;

	/** 备注 */
	private String remark;

	/**
	 * 表示资金是进帐还是出帐
	 * 
	 * @see com.cai310.lottery.common.FundMode
	 */
	private FundMode mode;

	/**
	 * 积分明细类型
	 * 
	 * @see com.cai310.lottery.common.FundDetailType
	 */
//	private FundDetailType type;
	private ScoreDetailType type;
//	/** 创建时间 *
	private Date createTime;

	/** 最后更新时间 */
	private Date lastModifyTime;
	/** 来源*/
	private PlatformInfo platform;
	
	public ScoreDetail() {

	}

	public ScoreDetail(User user, BigDecimal score, BigDecimal resultScore, String remark, FundMode mode,
			ScoreDetailType type) {
		this.setUserId(user.getId());
		this.setUserName(user.getUserName());
		this.setCreateTime(new Date());
		this.setMode(mode);
//		this.setMoney(money);
//		this.setResultMoney(resultMoney);
		this.setScore(score);
		this.setResultScore(resultScore);
		this.setRemark(remark);
		this.setType(type);
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false, updatable = false)
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * @return {@link #userId}
	 */
	@Column(nullable = false, updatable = false)
	public Long getUserId() {
		return userId;
	}

	/**
	 * @param userId the {@link #userId} to set
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	/**
	 * @return {@link #userName}
	 */
	@Column(nullable = false, updatable = false,length=20)
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName the {@link #userName} to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return {@link #money}
	 */
	@Column(nullable = false, updatable = false, precision = 18, scale = 4)
		public BigDecimal getScore() {
		return score;
	}

	public void setScore(BigDecimal score) {
		this.score = score;
	}
	/**
	 * @return {@link #resultMoney}
	 */
	@Column(nullable = false, updatable = false, precision = 18, scale = 4)
	public BigDecimal getResultScore() {
		return resultScore;
	}
	
	public void setResultScore(BigDecimal resultScore) {
		this.resultScore = resultScore;
	}
	

	/**
	 * @return {@link #remark}
	 */
	@Column(nullable = false, updatable = false, length = 500)
	public String getRemark() {
		return remark;
	}


	/**
	 * @param remark the {@link #remark} to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * @return {@link #mode}
	 */
	@Type(type = "org.hibernate.type.EnumType", parameters = {
			@Parameter(name = EnumType.ENUM, value = "com.cai310.lottery.common.FundMode"),
			@Parameter(name = EnumType.TYPE, value = FundMode.SQL_TYPE) })
	@Column(nullable = false, updatable = false)
	public FundMode getMode() {
		return mode;
	}

	/**
	 * @param mode the {@link #mode} to set
	 */
	public void setMode(FundMode mode) {
		this.mode = mode;
	}

	/**
	 * @return {@link #type}
	 */
//	@Type(type = "org.hibernate.type.EnumType", parameters = {
//			@Parameter(name = EnumType.ENUM, value = "com.cai310.lottery.common.FundDetailType"),
//			@Parameter(name = EnumType.TYPE, value = FundDetailType.SQL_TYPE) })
//	@Column(nullable = false, updatable = false)
//	public FundDetailType getType() {
//		return type;
//	}
//
//	/**
//	 * @param type the {@link #type} to set
//	 */
//	public void setType(FundDetailType type) {
//		this.type = type;
//	}
	
	/**
	 * @return {@link #type}
	 */
	@Type(type = "org.hibernate.type.EnumType", parameters = {
			@Parameter(name = EnumType.ENUM, value = "com.cai310.lottery.common.ScoreDetailType"),
			@Parameter(name = EnumType.TYPE, value = ScoreDetailType.SQL_TYPE) })
	@Column(nullable = false, updatable = false)
	public ScoreDetailType getType() {
		return type;
	}

	/**
	 * @param type the {@link #type} to set
	 */
	public void setType(ScoreDetailType type) {
		this.type = type;
	}
	
	@Transient
	public Lottery getLotteryType(){
		if(StringUtils.isBlank(this.getRemark()))return null;
		String remarkString=this.getRemark().trim();
		Pattern patt = Pattern.compile("([a-zA-Z]+[0-9]+)");
		try{
			Matcher matcher = patt.matcher(remarkString);
			if(matcher.find()){
				String schemeNumber = matcher.group(1);
				if(StringUtils.isNotBlank(schemeNumber)){
					   Lottery lottery=Lottery.valueOfSchemeNumber(schemeNumber.trim());
					   return lottery;
				}
			}
		}catch(Exception e){
			
		}
		return null;
	}
	@Transient
	public String getRemarkString(){
		if(StringUtils.isBlank(this.getRemark()))return null;
		String remarkString=this.getRemark().trim();
		Pattern patt = Pattern.compile("([a-zA-Z]+[0-9]+)");
		Matcher matcher = patt.matcher(remarkString);
		if(matcher.find()){
			if(StringUtils.isNotBlank(matcher.group(1))){
				    Lottery lottery=Lottery.valueOfSchemeNumber(matcher.group(1).trim());
				    if(null!=lottery){
						String url="/"+lottery.getKey()+"/scheme!show.action?schemeNumber="+matcher.group(1).trim();
						if(LotteryCategory.FREQUENT.equals(lottery.getCategory())){
							///高频彩
						     url="/"+lottery.getKey()+"/scheme!show.action?schemeNumber="+matcher.group(1).trim();
						}
						String aUrl="<a href=\""+url+"\">"+matcher.group(1)+"</a>";
						remarkString=remarkString.replaceAll(matcher.group(1), aUrl);
					}
			}
		}
		return remarkString;
	}
	
	@Transient
	public String getRemarkSchemeNumber(){
		if(StringUtils.isBlank(this.getRemark()))return null;
		String remarkString=this.getRemark().trim();
		Pattern patt = Pattern.compile("([a-zA-Z]+[0-9]+)");
		Matcher matcher = patt.matcher(remarkString);
		if(matcher.find()){
			if(StringUtils.isNotBlank(matcher.group(1))){
				  return matcher.group(1).trim();
			}
		}
		return null;
	}
	@Transient
	public String getRemarkTicketIds(){
		StringBuffer sb = new StringBuffer();
		try {
			HttpServletRequest httpServletRequest = Struts2Utils.getRequest();
			String host = "http://"+httpServletRequest.getServerName()+":"+httpServletRequest.getServerPort();
			String schemeNumber = this.getRemarkSchemeNumber();
			Map<String,String> ParamMap=new LinkedHashMap<String,String>();
			ParamMap.put("schemeNumber", schemeNumber);
			String returnString = HttpclientUtil.Utf8HttpClientUtils(host+"/ticket/query!getTicketCodeBySchemeNumber.action",ParamMap);
			if(StringUtils.isNotBlank(returnString)){
				Map<String, Object> map = JsonUtil.getMap4Json(returnString);
				String[] items = JsonUtil.getStringArray4Json( String.valueOf(map.get("idList")));
				if(items.length>0){
					for (String str : items) {
						sb.append(str+",");
					}
					sb.delete(sb.length()-1, sb.length());
				}
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}
	@Transient
	public String getTypeString(){
		return this.type.getTypeName();
	}
	@Transient
	public String getModeString(){
		return this.mode.getTypeName();
	}
	@Transient
	public String getCreateTimeStr(){
		return DateUtil.dateToStr(this.createTime,"yyyy-MM-dd HH:mm:ss");
	}
	@Temporal(TemporalType.TIMESTAMP)
	@Column(insertable = false)
	public Date getLastModifyTime() {
		return lastModifyTime;
	}

	public void setLastModifyTime(Date lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}
	/**
	 * @return {@link #version}
	 */
	@Version
	@Column(nullable = false)
	public Integer getVersion() {
		return version;
	}

	/**
	 * @param version the version to set
	 */
	public void setVersion(Integer version) {
		this.version = version;
	}
	@Type(type = "org.hibernate.type.EnumType", parameters = {
			@Parameter(name = EnumType.ENUM, value = "com.cai310.lottery.common.PlatformInfo"),
			@Parameter(name = EnumType.TYPE, value = SalesMode.SQL_TYPE) })
	@Column(updatable = false)
	public PlatformInfo getPlatform() {
		return platform;
	}

	public void setPlatform(PlatformInfo platform) {
		this.platform = platform;
	}

}
