package com.cai310.lottery.web.controller.admin.lottery;

import com.cai310.lottery.common.ChaseState;
import com.cai310.lottery.common.SalesMode;
import com.cai310.lottery.common.SchemePrintState;
import com.cai310.lottery.common.SchemeState;
import com.cai310.lottery.common.SecretType;
import com.cai310.lottery.common.ShareType;
import com.cai310.lottery.common.keno.IssueState;
import com.cai310.lottery.support.ahkuai3.AhKuai3PlayType;
import com.cai310.lottery.support.el11to5.El11to5PlayType;
import com.cai310.lottery.support.gdel11to5.GdEl11to5PlayType;
import com.cai310.lottery.support.klpk.KlpkPlayType;
import com.cai310.lottery.support.qyh.QyhPlayType;
import com.cai310.lottery.support.sdel11to5.SdEl11to5PlayType;
import com.cai310.lottery.support.ssc.SscPlayType;
import com.cai310.lottery.support.welfare36to7.Welfare36to7PlayType;
import com.cai310.lottery.support.xjel11to5.XjEl11to5PlayType;
import com.cai310.lottery.support.zc.PlayType;

/**
 * 后台方案查询表单
 * 
 */
public class AdminSchemeQueryForm {
	/**
	 * 方案投注的方式
	 * 
	 * @see com.cai310.lottery.common.SalesMode
	 */
	private SalesMode mode;
	
	/**
	 * 14场9场
	 * 
	 */
	private PlayType sfzcPlayType;
	
	private SscPlayType sscPlayType;
	
	private QyhPlayType qyhPlayType;
	
	private El11to5PlayType el11to5PlayType;
	
	private Welfare36to7PlayType welfare36to7playType;

	private GdEl11to5PlayType gdEl11to5PlayType;
	
	private KlpkPlayType klpkPlayType;
	
	private AhKuai3PlayType ahKuai3PlayType;
	
	private XjEl11to5PlayType xjEl11to5PlayType;


	public AhKuai3PlayType getAhKuai3PlayType() {
		return ahKuai3PlayType;
	}

	public void setAhKuai3PlayType(AhKuai3PlayType ahKuai3PlayType) {
		this.ahKuai3PlayType = ahKuai3PlayType;
	}

	public GdEl11to5PlayType getGdEl11to5PlayType() {
		return gdEl11to5PlayType;
	}

	public void setGdEl11to5PlayType(GdEl11to5PlayType gdEl11to5PlayType) {
		this.gdEl11to5PlayType = gdEl11to5PlayType;
	}


	private SdEl11to5PlayType sdEl11to5PlayType;
	
	public SscPlayType getSscPlayType() {
		return sscPlayType;
	}

	public void setSscPlayType(SscPlayType sscPlayType) {
		this.sscPlayType = sscPlayType;
	}

	public QyhPlayType getQyhPlayType() {
		return qyhPlayType;
	}

	public void setQyhPlayType(QyhPlayType qyhPlayType) {
		this.qyhPlayType = qyhPlayType;
	}

	public El11to5PlayType getEl11to5PlayType() {
		return el11to5PlayType;
	}

	public void setEl11to5PlayType(El11to5PlayType el11to5PlayType) {
		this.el11to5PlayType = el11to5PlayType;
	}

	public SdEl11to5PlayType getSdEl11to5PlayType() {
		return sdEl11to5PlayType;
	}

	public void setSdEl11to5PlayType(SdEl11to5PlayType sdEl11to5PlayType) {
		this.sdEl11to5PlayType = sdEl11to5PlayType;
	}

	
	public Welfare36to7PlayType getWelfare36to7playType() {
		return welfare36to7playType;
	}

	public void setWelfare36to7playType(Welfare36to7PlayType welfare36to7playType) {
		this.welfare36to7playType = welfare36to7playType;
	}

	
	/**
	 * 
	 * 排3排5
	 * 0代表排5，1是排三
	 */
	private Integer plPlayType;

	/** 是否有保底 */
	private Boolean hasBaodi;

	/** 是否中奖 */
	private Boolean won;

	/** 是否已派奖 */
	private Boolean prizeSended;

	/** 是否已更新中奖 */
	private Boolean updateWon;

	/** 是否保留 */
	private Boolean reserved;

	/**
	 * 方案分享类型
	 * 
	 * @see com.cai310.lottery.common.ShareType
	 */
	private ShareType shareType;

	/**
	 * 方案保密类型
	 * 
	 * @see com.cai310.lottery.common.SecretType
	 */
	private SecretType secretType;

	/**
	 * 方案状态
	 * 
	 * @see com.cai310.lottery.common.SchemeState
	 */
	private SchemeState state;
	/**
	 * 出票状态
	 * @see com.cai310.lottery.common.SchemePrintState
	 */
	private SchemePrintState ticketState;

	/** 方案排序优先级 */
	private Byte orderPriority;

	/** 期号 */
	private String periodNumber;
	
	/** 期号ID */
	private long periodId;
	
	/** 是否中奖后停追 */
	private Boolean wonStop;

	/** 发起人，以逗号隔开多个发起人 */
	private String sponsorNames;

	/** 方案号，以逗号隔开多个方案号 */
	private String schemeNumbers;

	private SchemeQueryOrder order;

	/** 是否倒序 */
	private boolean descOrder;
	
	/**追号首期期号**/
	private String firstPeriodNumber;
	
	/**追号当前期**/
	private Long curPeriodId;
	
	/** 期状态 */
	private IssueState issueState;
	
	/** 追号状态 **/
	private ChaseState chaseState;
	
	/**开始时间**/
	private String startDate;
	
	/**截止时间**/
	private String endDate;
	/**
	 * @return the {@link #mode}
	 */
	public SalesMode getMode() {
		return mode;
	}

	/**
	 * @param mode the {@link #mode} to set
	 */
	public void setMode(SalesMode mode) {
		this.mode = mode;
	}

	/**
	 * @return the {@link #hasBaodi}
	 */
	public Boolean getHasBaodi() {
		return hasBaodi;
	}

	/**
	 * @param hasBaodi the {@link #hasBaodi} to set
	 */
	public void setHasBaodi(String hasBaodi) {
		if ("true".equalsIgnoreCase(hasBaodi))
			this.hasBaodi = Boolean.TRUE;
		else if ("false".equalsIgnoreCase(hasBaodi))
			this.hasBaodi = Boolean.FALSE;
	}

	/**
	 * @return the {@link #won}
	 */
	public Boolean getWon() {
		return won;
	}

	/**
	 * @param won the {@link #won} to set
	 */
	public void setWon(String won) {
		if ("true".equalsIgnoreCase(won))
			this.won = Boolean.TRUE;
		else if ("false".equalsIgnoreCase(won))
			this.won = Boolean.FALSE;
	}

	/**
	 * @return the {@link #prizeSended}
	 */
	public Boolean getPrizeSended() {
		return prizeSended;
	}

	/**
	 * @param prizeSended the {@link #prizeSended} to set
	 */
	public void setPrizeSended(String prizeSended) {
		if ("true".equalsIgnoreCase(prizeSended))
			this.prizeSended = Boolean.TRUE;
		else if ("false".equalsIgnoreCase(prizeSended))
			this.prizeSended = Boolean.FALSE;
	}

	/**
	 * @return the {@link #updateWon}
	 */
	public Boolean getUpdateWon() {
		return updateWon;
	}

	/**
	 * @param updateWon the {@link #updateWon} to set
	 */
	public void setUpdateWon(String updateWon) {
		if ("true".equalsIgnoreCase(updateWon))
			this.updateWon = Boolean.TRUE;
		else if ("false".equalsIgnoreCase(updateWon))
			this.updateWon = Boolean.FALSE;
	}

	/**
	 * @return the {@link #reserved}
	 */
	public Boolean getReserved() {
		return reserved;
	}

	/**
	 * @param reserved the {@link #reserved} to set
	 */
	public void setReserved(String reserved) {
		if ("true".equalsIgnoreCase(reserved))
			this.reserved = Boolean.TRUE;
		else if ("false".equalsIgnoreCase(reserved))
			this.reserved = Boolean.FALSE;
	}

	/**
	 * @return the {@link #shareType}
	 */
	public ShareType getShareType() {
		return shareType;
	}

	/**
	 * @param shareType the {@link #shareType} to set
	 */
	public void setShareType(ShareType shareType) {
		this.shareType = shareType;
	}

	/**
	 * @return the {@link #secretType}
	 */
	public SecretType getSecretType() {
		return secretType;
	}

	/**
	 * @param secretType the {@link #secretType} to set
	 */
	public void setSecretType(SecretType secretType) {
		this.secretType = secretType;
	}

	/**
	 * @return the {@link #state}
	 */
	public SchemeState getState() {
		return state;
	}

	/**
	 * @param state the {@link #state} to set
	 */
	public void setState(SchemeState state) {
		this.state = state;
	}

	/**
	 * @return the {@link #orderPriority}
	 */
	public Byte getOrderPriority() {
		return orderPriority;
	}

	/**
	 * @param orderPriority the {@link #orderPriority} to set
	 */
	public void setOrderPriority(Byte orderPriority) {
		this.orderPriority = orderPriority;
	}

	/**
	 * @return the {@link #periodNumber}
	 */
	public String getPeriodNumber() {
		return periodNumber;
	}

	/**
	 * @param periodNumber the {@link #periodNumber} to set
	 */
	public void setPeriodNumber(String periodNumber) {
		this.periodNumber = periodNumber;
	}

	/**
	 * @return the {@link #sponsorNames}
	 */
	public String getSponsorNames() {
		return sponsorNames;
	}

	/**
	 * @param sponsorNames the {@link #sponsorNames} to set
	 */
	public void setSponsorNames(String sponsorNames) {
		this.sponsorNames = sponsorNames;
	}

	/**
	 * @return the {@link #schemeNumbers}
	 */
	public String getSchemeNumbers() {
		return schemeNumbers;
	}

	/**
	 * @param schemeNumbers the {@link #schemeNumbers} to set
	 */
	public void setSchemeNumbers(String schemeNumbers) {
		this.schemeNumbers = schemeNumbers;
	}

	/**
	 * @return the {@link #order}
	 */
	public SchemeQueryOrder getOrder() {
		return order;
	}

	/**
	 * @param order the {@link #order} to set
	 */
	public void setOrder(SchemeQueryOrder order) {
		this.order = order;
	}

	/**
	 * @return the {@link #descOrder}
	 */
	public boolean isDescOrder() {
		return descOrder;
	}

	/**
	 * @param descOrder the {@link #descOrder} to set
	 */
	public void setDescOrder(boolean descOrder) {
		this.descOrder = descOrder;
	}

	public long getPeriodId() {
		return periodId;
	}

	public void setPeriodId(long periodId) {
		this.periodId = periodId;
	}

	public Boolean getWonStop() {
		return wonStop;
	}

	public void setWonStop(String wonStop) {
		if ("true".equalsIgnoreCase(wonStop))
			this.wonStop = Boolean.TRUE;
		else if ("false".equalsIgnoreCase(wonStop))
			this.wonStop = Boolean.FALSE;
	}

	public String getFirstPeriodNumber() {
		return firstPeriodNumber;
	}

	public void setFirstPeriodNumber(String firstPeriodNumber) {
		this.firstPeriodNumber = firstPeriodNumber;
	}

	public Long getCurPeriodId() {
		return curPeriodId;
	}

	public void setCurPeriodId(Long curPeriodId) {
		this.curPeriodId = curPeriodId;
	}

	public IssueState getIssueState() {
		return issueState;
	}

	public void setIssueState(IssueState issueState) {
		this.issueState = issueState;
	}

	public ChaseState getChaseState() {
		return chaseState;
	}

	public void setChaseState(ChaseState chaseState) {
		this.chaseState = chaseState;
	}

	/**
	 * @return the sfzcPlayType
	 */
	public PlayType getSfzcPlayType() {
		return sfzcPlayType;
	}

	/**
	 * @param sfzcPlayType the sfzcPlayType to set
	 */
	public void setSfzcPlayType(PlayType sfzcPlayType) {
		this.sfzcPlayType = sfzcPlayType;
	}

	/**
	 * @return the plPlayType
	 */
	public Integer getPlPlayType() {
		return plPlayType;
	}

	/**
	 * @param plPlayType the plPlayType to set
	 */
	public void setPlPlayType(Integer plPlayType) {
		this.plPlayType = plPlayType;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public KlpkPlayType getKlpkPlayType() {
		return klpkPlayType;
	}

	public void setKlpkPlayType(KlpkPlayType klpkPlayType) {
		this.klpkPlayType = klpkPlayType;
	}

	public XjEl11to5PlayType getXjEl11to5PlayType() {
		return xjEl11to5PlayType;
	}

	public void setXjEl11to5PlayType(XjEl11to5PlayType xjEl11to5PlayType) {
		this.xjEl11to5PlayType = xjEl11to5PlayType;
	}

	public SchemePrintState getTicketState() {
		return ticketState;
	}

	public void setTicketState(SchemePrintState ticketState) {
		this.ticketState = ticketState;
	}

	
	
	
}
