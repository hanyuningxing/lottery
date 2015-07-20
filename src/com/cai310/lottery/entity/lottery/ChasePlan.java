package com.cai310.lottery.entity.lottery;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.Version;

import net.sf.json.JSONObject;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.hibernate.type.EnumType;

import com.cai310.entity.CreateMarkable;
import com.cai310.entity.IdEntity;
import com.cai310.entity.UpdateMarkable;
import com.cai310.lottery.Constant;
import com.cai310.lottery.common.ChaseState;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.common.PlatformInfo;
import com.cai310.lottery.common.SalesMode;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.support.CapacityChaseBean;
import com.cai310.utils.DateUtil;

/**
 * 追号计划实体
 * <p>
 * 倍数的存放规则 正整数表示该期追号  ； 0表示该期不追号； 负整数表示该期在追号过程中被停掉的 ；数字用这个"[]"包着的,表示该期已经做过相应处理；.
 * 例:[1],[0],-2,2 这个串表示追号共追3期;第二期在发起时选择了不追;追号过程中又把第三期给停掉了;目前第一期 第二期 已经处理过,下一期
 * 是追第三期
 * </p>
 */
@Table(name = com.cai310.lottery.Constant.LOTTERY_TABLE_PREFIX + "CHASE_PLAN")
@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ChasePlan extends IdEntity implements CreateMarkable, UpdateMarkable {
	private static final long serialVersionUID = -4650685530026841524L;

	/** 追号各期倍数的分隔符 */
	public static final String MULTIPLE_SEPARATOR = ",";

	public static final String CHASED_MULTIPLE_STARTSWITH = "[";
	public static final String CHASED_MULTIPLE_ENDSWITH = "]";

	public static final String MULTIPLE_REGEX = "\\[[^\\]]+\\]";
    public static void main(String[] args) throws ClassNotFoundException {
    	Class cls = Class.forName("com.cai310.lottery.entity.lottery.ChasePlan") ;
    	Field[] fieldList= cls.getDeclaredFields();
    	for (Field field : fieldList) {
			
		}
	}
	@Transient
	public BigDecimal getZoomTotalCost() {
		return BigDecimal.valueOf(totalCost);
	}
	@Transient
	public BigDecimal getZoomChasedCost() {
		return BigDecimal.valueOf(chasedCost);
	}
	
	/** 版本号 */
	protected Integer version;

	/** 创建时间 */
	private Date createTime;

	/** 最后更新时间 */
	private Date lastModifyTime;

	/**
	 * 彩种类型
	 * 
	 * @see com.cai310.lottery.common.Lottery
	 */
	private Lottery lotteryType;

	/** 当前期ID **/
	private Long curPeriodId;

	/** 追号总额 */
	private Integer totalCost;

	/** 已追金额 */
	private Integer chasedCost;

	/** 追号状态 **/
	private ChaseState state;

	/** 追号用户ID **/
	private Long userId;

	/*** 追号用户名 **/
	private String userName;

	/** 是否中奖后停追 */
	private boolean wonStop;

	/** 中奖金额大于该金额才停止追号 */
	private Integer prizeForWonStop;

	/** 是否机选追号 */
	private boolean random;

	/** 每期机选注数 */
	private Integer randomUnits;

	/** 是否有设置胆码 */
	private boolean hasDan;

	/** 机选胆码,使用json格式,如双色球:{red:['02','15'],blue:'06'} */
	private String dan;
	/** 各期追号的倍数,以逗号隔开倍数,0表示该期不进行追号,小括号表示该期手动停止追号,中括号表示该期已追号 */
	private String multiples;

	/** 交易ID */
	private Long transactionId;

	/** 预付款ID */
	private Long prepaymentId;

	/** 追号是否中奖 */
	private boolean won;

	/** 追号总奖金 */
	private BigDecimal totalPrize;
	
	//智能追号实体
	private String playType;
	private Integer units;
	private SalesMode mode;
	private String content;
	private Integer schemeCost;
	private boolean outNumStop;
	//收益方式
	private String capacityProfit;
	/** 来源*/
	private PlatformInfo platform;
	@Lob
	@Column(name = "capacityProfit")
	public String getCapacityProfit() {
		return capacityProfit;
	}
	public void setCapacityProfit(String capacityProfit) {
		this.capacityProfit = capacityProfit;
	}
	
	@SuppressWarnings("unchecked")
	@Transient
	public CapacityChaseBean getCapacityProfitContent() {
		return (CapacityChaseBean) JSONObject.toBean(JSONObject.fromObject(this.getCapacityProfit()),CapacityChaseBean.class);
	}
	
	
	
	@Column(nullable = true, updatable = false)
	public boolean isOutNumStop() {
		return outNumStop;
	}
	public void setOutNumStop(boolean outNumStop) {
		this.outNumStop = outNumStop;
	}
	
	
	@Column(nullable = false, updatable = false)
	public Integer getSchemeCost() {
		return schemeCost;
	}
	public void setSchemeCost(Integer schemeCost) {
		this.schemeCost = schemeCost;
	}
	@Lob
	@Column(name = "content")
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	@Type(type = "org.hibernate.type.EnumType", parameters = {
			@Parameter(name = EnumType.ENUM, value = "com.cai310.lottery.common.SalesMode"),
			@Parameter(name = EnumType.TYPE, value = SalesMode.SQL_TYPE) })
	@Column(name = "sales_mode", updatable = false)
	public SalesMode getMode() {
		return mode;
	}
	public void setMode(SalesMode mode) {
		this.mode = mode;
	}
	@Column
	public Integer getUnits() {
		return units;
	}
	public void setUnits(Integer units) {
		this.units = units;
	}
	
	@Column(nullable = true, updatable = false, length = 20)
	public String getPlayType() {
		return playType;
	}
	public void setPlayType(String playType) {
		this.playType = playType;
	}

	
	//智能追号实体

	/**
	 * @return the version
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

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false, updatable = false)
	public Date getCreateTime() {
		return createTime;
	}

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

	/**
	 * @return {@link #lotteryType}
	 */
	@Type(type = "org.hibernate.type.EnumType", parameters = {
			@Parameter(name = EnumType.ENUM, value = "com.cai310.lottery.common.Lottery"),
			@Parameter(name = EnumType.TYPE, value = Lottery.SQL_TYPE) })
	@Column(nullable = false, updatable = false)
	public Lottery getLotteryType() {
		return lotteryType;
	}

	/**
	 * @param lotteryType the {@link #lotteryType} to set
	 */
	public void setLotteryType(Lottery lotteryType) {
		this.lotteryType = lotteryType;
	}

	/**
	 * @return {@link #curPeriodId}
	 */
	@Column(nullable = false)
	public Long getCurPeriodId() {
		return curPeriodId;
	}

	/**
	 * @param curPeriodId the {@link #curPeriodId} to set
	 */
	public void setCurPeriodId(Long curPeriodId) {
		this.curPeriodId = curPeriodId;
	}

	/**
	 * @return {@link #totalCost}
	 */
	
	@Column(nullable = false, updatable = false)
	public Integer getTotalCost() {
		return totalCost;
	}

	/**
	 * @param totalCost the {@link #totalCost} to set
	 */
	public void setTotalCost(Integer totalCost) {
		this.totalCost = totalCost;
	}

	/**
	 * @return {@link #chasedCost}
	 */
	
	@Column
	public Integer getChasedCost() {
		return chasedCost;
	}

	/**
	 * @param chasedCost the {@link #chasedCost} to set
	 */
	public void setChasedCost(Integer chasedCost) {
		this.chasedCost = chasedCost;
	}

	/**
	 * @return {@link #state}
	 */
	@Type(type = "org.hibernate.type.EnumType", parameters = {
			@Parameter(name = EnumType.ENUM, value = "com.cai310.lottery.common.ChaseState"),
			@Parameter(name = EnumType.TYPE, value = ChaseState.SQL_TYPE) })
	@Column(nullable = false)
	public ChaseState getState() {
		return state;
	}

	/**
	 * @param state the {@link #state} to set
	 */
	public void setState(ChaseState state) {
		this.state = state;
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
	@Column(nullable = false, updatable = false, length = 20)
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
	 * @return {@link #wonStop}
	 */
	@Column(nullable = false, updatable = false)
	public boolean isWonStop() {
		return wonStop;
	}

	/**
	 * @param wonStop the {@link #wonStop} to set
	 */
	public void setWonStop(boolean wonStop) {
		this.wonStop = wonStop;
	}

	/**
	 * @return {@link #prizeForWonStop}
	 */
	@Column(updatable = false)
	public Integer getPrizeForWonStop() {
		return prizeForWonStop;
	}

	/**
	 * @param prizeForWonStop the {@link #prizeForWonStop} to set
	 */
	public void setPrizeForWonStop(Integer prizeForWonStop) {
		this.prizeForWonStop = prizeForWonStop;
	}

	/**
	 * @return {@link #random}
	 */
	@Column(nullable = false)
	public boolean isRandom() {
		return random;
	}

	/**
	 * @param random the {@link #random} to set
	 */
	public void setRandom(boolean random) {
		this.random = random;
	}

	/**
	 * @return {@link #randomUnits}
	 */
	@Column(updatable = false)
	public Integer getRandomUnits() {
		return randomUnits;
	}

	/**
	 * @param randomUnits the {@link #randomUnits} to set
	 */
	public void setRandomUnits(Integer randomUnits) {
		this.randomUnits = randomUnits;
	}

	/**
	 * @return {@link #hasDan}
	 */
	@Column(updatable = false, nullable = false)
	public boolean isHasDan() {
		return hasDan;
	}

	/**
	 * @param hasDan the {@link #hasDan} to set
	 */
	public void setHasDan(boolean hasDan) {
		this.hasDan = hasDan;
	}

	/**
	 * @return {@link #dan}
	 */
	@Column(updatable = false, length = 500)
	public String getDan() {
		return dan;
	}

	/**
	 * @param dan the {@link #dan} to set
	 */
	public void setDan(String dan) {
		this.dan = dan;
	}

	/**
	 * @return {@link #multiples}
	 */
	@Column(length = 1000, nullable = false)
	public String getMultiples() {
		return multiples;
	}

	/**
	 * @param multiples the {@link #multiples} to set
	 */
	public void setMultiples(String multiples) {
		this.multiples = multiples;
	}

	/**
	 * @return {@link #won}
	 */
	@Column(nullable = false)
	public boolean isWon() {
		return won;
	}

	/**
	 * @param won the {@link #won} to set
	 */
	public void setWon(boolean won) {
		this.won = won;
	}

	/**
	 * @return {@link #totalPrize}
	 */
	
	@Column
	public BigDecimal getTotalPrize() {
		return totalPrize;
	}

	/**
	 * @param totalPrize the {@link #totalPrize} to set
	 */
	public void setTotalPrize(BigDecimal totalPrize) {
		this.totalPrize = totalPrize;
	}

	/**
	 * @return {@link #prepaymentId}
	 */
	@Column(nullable = false, updatable = false)
	public Long getPrepaymentId() {
		return prepaymentId;
	}

	/**
	 * @param prepaymentId the {@link #prepaymentId} to set
	 */
	public void setPrepaymentId(Long prepaymentId) {
		this.prepaymentId = prepaymentId;
	}

	/**
	 * @return {@link #transactionId}
	 */
	@Column(nullable = false, updatable = false)
	public Long getTransactionId() {
		return transactionId;
	}

	/**
	 * @param transactionId the {@link #transactionId} to set
	 */
	public void setTransactionId(Long transactionId) {
		this.transactionId = transactionId;
	}

	/**
	 * 设置追号各期倍数
	 * 
	 * @param multipleList 追号各期倍数集合
	 */
	@Transient
	public void setMultiples(List<Integer> multipleList) throws DataException {
		if (multipleList == null)
			throw new DataException("追号各期倍数不能为空.");

		int chaseSize = 0;
		StringBuilder mutiles = new StringBuilder();
		for (int i = 0, l = multipleList.size(); i < l; i++) {
			Integer multiple = multipleList.get(i);
			if (multiple == null || multiple < 0)
				throw new DataException("倍数不能为空或者小于0.");
			else if (multiple == 0) {
				if (i == 0)
					throw new DataException("第一期的倍数不能为0.");
				else if (i == l - 1)
					throw new DataException("最后一期 的倍数不能为0.");
				else {
					mutiles.append(MULTIPLE_SEPARATOR);
					mutiles.append(multiple);
					continue;
				}
			} else if (multiple > Constant.MAX_MULTIPLE) {
				throw new DataException("倍数不能超过" + Constant.MAX_MULTIPLE + ".");
			} else {
				chaseSize++;
			}
			if (i > 0)
				mutiles.append(MULTIPLE_SEPARATOR);
			mutiles.append(multiple);
		}
		if (chaseSize < 2)
			throw new DataException("追号至少必须追两期.");

		this.setMultiples(mutiles.toString());
	}

	/**
	 * @return 追号倍数集合,不追期次的倍数为NULL.停追的期次返回负整数
	 */
	@Transient
	public List<Integer> getMultipleList() {
		
		List<Integer> multipleList = new ArrayList<Integer>();
		String[] multArr = getMultiples().split(MULTIPLE_SEPARATOR);
		for (String multipleStr : multArr) {
			if (isNotChase(multipleStr))
				multipleList.add(null);
			else {
				multipleStr = multipleStr.replaceAll(MULTIPLE_REGEX, "$1");
				multipleList.add(Integer.valueOf(multipleStr));
			}
		}
		
		return multipleList;
	}
	
	
	

	/**
	 * @return 总的实际追号期数
	 */
	@Transient
	public int getChaseTotalSize() {
		String[] multArr = getMultiples().split(MULTIPLE_SEPARATOR);
		int size = 0;
		for (String multipleStr : multArr) {
			if (!isNotChase(multipleStr))
				size++;
		}
		return size;
	}

	/**
	 * @return 实际已追的期数
	 */
	@Transient
	public int getChasedSize() {
		String[] multArr = getMultiples().split(MULTIPLE_SEPARATOR);
		int size = 0;
		for (String multipleStr : multArr) {
			if (multipleStr.matches("\\" + CHASED_MULTIPLE_STARTSWITH + "(?!0)\\d+" + "\\" +CHASED_MULTIPLE_ENDSWITH))
				size++;
		}
		return size;
	}

	/**
	 * @return 实际未追的期数
	 */
	@Transient
	public int getYetChaseSize() {
		String[] multArr = getMultiples().split(MULTIPLE_SEPARATOR);
		int size = 0;
		for (String multipleStr : multArr) {
			if (multipleStr.matches("\\(?!0)d+"))
				size++;
		}
		return size;
	}

	/**
	 * 判断该倍数是否不追号
	 * 
	 * @param multipleStr 追号倍数
	 * @return
	 */
	@Transient
	public boolean isNotChase(String multipleStr) {
		multipleStr = multipleStr.trim();
		return multipleStr.matches("\\" + CHASED_MULTIPLE_STARTSWITH + "*0\\" + CHASED_MULTIPLE_ENDSWITH + "*");
	}

	/**
	 * 判断该倍数是否已执行过追号处理
	 * 
	 * @param multipleStr
	 * @return
	 */
	@Transient
	public boolean isChased(String multipleStr) {
		multipleStr = multipleStr.trim();
		return multipleStr.startsWith(CHASED_MULTIPLE_STARTSWITH) && multipleStr.endsWith(CHASED_MULTIPLE_ENDSWITH);
	}

	/**
	 * 判断该倍数是否已停止追号
	 * 
	 * @param multipleStr
	 * @return
	 */
	@Transient
	public boolean isStopChased(String multipleStr) {
		multipleStr = multipleStr.trim();
		return multipleStr.matches("\\" + CHASED_MULTIPLE_STARTSWITH + "*\\-\\d+\\" + CHASED_MULTIPLE_ENDSWITH + "*");

	}

	/**
	 * 对倍数进行已追号处理
	 * 
	 * @param multiple 追号倍数
	 * @return 处理后的字符串
	 */
	protected String chasedMultiple(Integer multiple) {
		return CHASED_MULTIPLE_STARTSWITH + multiple + CHASED_MULTIPLE_ENDSWITH;
	}

	/**
	 * 对倍数进行停止追号处理
	 * 
	 * @param multiple 追号倍数
	 * @return 处理后的字符串
	 */
	protected String stopMultiple(Integer multiple) {
		return CHASED_MULTIPLE_STARTSWITH + multiple * -1 + CHASED_MULTIPLE_ENDSWITH;
	}

	/**
	 * 查找下一次倍数
	 * 
	 * @return
	 */
	@Transient
	public Integer getNextMultiple() {
		String[] multArr = getMultiples().split(MULTIPLE_SEPARATOR);
		for (String multipleStr : multArr) {
			if (!isChased(multipleStr)) {
				return Integer.valueOf(multipleStr);
			}
		}
		return 0;
	}

	/**
	 * 标识执行过追号处理.
	 */
	public void markChased() throws DataException {
		if (getState() != ChaseState.RUNNING)
			throw new DataException("追号不在运行状态,不能执行追号.");

		// 查找最近一个未追的，设置为已追
		String[] multArr = getMultiples().split(MULTIPLE_SEPARATOR);
		StringBuilder sb = new StringBuilder();
		boolean added = false;// 是否已增加
		boolean hasChase = false;// 是否还有未追的
		for (String multipleStr : multArr) {
			if (sb.length() > 0)
				sb.append(MULTIPLE_SEPARATOR);
			if (!isChased(multipleStr)) {
				if (!added) {
					sb.append(chasedMultiple(Integer.valueOf(multipleStr)));
					added = true;
				} else {
					sb.append(multipleStr);
					hasChase = true;
				}
			} else {
				sb.append(multipleStr);
			}
		}
		this.setMultiples(sb.toString());

		if (!hasChase)
			setState(ChaseState.STOPED);
	}
	
	public void skip(Long periodId) throws DataException {
		this.markChased();
		this.setCurPeriodId(periodId);
	}

	/**
	 * 执行追号
	 * 
	 * @param periodId 销售期ID
	 * @param schemeCost 方案金额
	 * @throws DataException
	 */
	public void chase(Long periodId, Integer schemeCost) throws DataException {
		this.markChased();

		if (schemeCost == null || schemeCost.doubleValue() <= 0)
			throw new DataException("方案金额不能为空、小于或等于0.");

		Integer chasedCost = (this.getChasedCost() == null) ? schemeCost : (this.getChasedCost() + schemeCost);

		this.setCurPeriodId(periodId);
		this.setChasedCost(chasedCost);
	}
	

	
	
	/**
	 * @return 是否可以停止追号
	 */
	@Transient
	public boolean isCanStop() {
		return this.getState() == ChaseState.RUNNING;
	}
	@Transient
	public String getCreateTimeStr(){
		return DateUtil.dateToStr(this.createTime,"yyyy-MM-dd HH:mm:ss");
	}
	/**
	 * 停止追号
	 * 
	 * @throws DataException
	 */
	public void stop() throws DataException {
		if (!this.isCanStop())
			throw new DataException("不能撤销该追号.");

		this.setState(ChaseState.STOPED);
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
