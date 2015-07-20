package com.cai310.lottery.entity.football;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
@Entity
@Table(name = "football_sclass")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Sclass {
   private Long id;
   private String color;
   private String name_J;
   private String name_F;
   private String name_E;
   private String name_JS;
   private String name_FS;
   private String name_ES;
   private String name_S;
   private Short kind;
   private Short mode;
   private Short count_round;
   private Short curr_round;
   private String curr_matchSeason;
   private String sclass_pic;
   private Byte ifstop;
   private Integer sclass_order;
   private Byte sclass_type;
   private Byte ifindex;
   private Short count_group;
   private String sclass_rule;
   private Byte bf_simply_disp;
   private Short sclass_sequence;
   private Short infoID;
   private Integer msrepl_tran_version;
   private Byte ifShow;
   private Byte bf_IfDisp;
   private String infoUrl;
   private Date modifyTime;
   private String beginSeason;
   private String sclass_ruleEn;
   private Boolean nowGoalShow;
   private Boolean getEspn;
   private Long subSclassID;
   private Boolean ifHaveSub;
	/**
	 * @return the id
	 */
    @Id
	@Column(name="sclassID")
	public Long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * @return the color
	 */
	@Column(name="Color",length=30)
	public String getColor() {
		return color;
	}
	/**
	 * @param color the color to set
	 */
	public void setColor(String color) {
		this.color = color;
	}
	/**
	 * @return the name_J
	 */
	@Column(name="Name_J",length=50)
	public String getName_J() {
		return name_J;
	}
	/**
	 * @param nameJ the name_J to set
	 */
	public void setName_J(String nameJ) {
		name_J = nameJ;
	}
	/**
	 * @return the name_F
	 */
	@Column(name="Name_F",length=50)
	public String getName_F() {
		return name_F;
	}
	/**
	 * @param nameF the name_F to set
	 */
	public void setName_F(String nameF) {
		name_F = nameF;
	}
	/**
	 * @return the name_E
	 */
	@Column(name="Name_E",length=50)
	public String getName_E() {
		return name_E;
	}
	/**
	 * @param nameE the name_E to set
	 */
	public void setName_E(String nameE) {
		name_E = nameE;
	}
	/**
	 * @return the name_JS
	 */
	@Column(name="Name_JS",length=50)
	public String getName_JS() {
		return name_JS;
	}
	/**
	 * @param nameJS the name_JS to set
	 */
	public void setName_JS(String nameJS) {
		name_JS = nameJS;
	}
	/**
	 * @return the name_FS
	 */
	@Column(name="Name_FS",length=50)
	public String getName_FS() {
		return name_FS;
	}
	/**
	 * @param nameFS the name_FS to set
	 */
	public void setName_FS(String nameFS) {
		name_FS = nameFS;
	}
	/**
	 * @return the name_ES
	 */
	@Column(name="Name_ES",length=50)
	public String getName_ES() {
		return name_ES;
	}
	/**
	 * @param nameES the name_ES to set
	 */
	public void setName_ES(String nameES) {
		name_ES = nameES;
	}
	/**
	 * @return the name_S
	 */
	@Column(name="Name_S",length=50)
	public String getName_S() {
		return name_S;
	}
	/**
	 * @param nameS the name_S to set
	 */
	public void setName_S(String nameS) {
		name_S = nameS;
	}
	/**
	 * @return the kind
	 */
	@Column(name="Kind")
	public Short getKind() {
		return kind;
	}
	/**
	 * @param kind the kind to set
	 */
	public void setKind(Short kind) {
		this.kind = kind;
	}
	/**
	 * @return the mode
	 */
	@Column(name="Mode")
	public Short getMode() {
		return mode;
	}
	/**
	 * @param mode the mode to set
	 */
	public void setMode(Short mode) {
		this.mode = mode;
	}
	/**
	 * @return the count_round
	 */
	@Column(name="count_round")
	public Short getCount_round() {
		return count_round;
	}
	/**
	 * @param countRound the count_round to set
	 */
	public void setCount_round(Short countRound) {
		count_round = countRound;
	}
	/**
	 * @return the curr_round
	 */
	@Column(name="curr_round")
	public Short getCurr_round() {
		return curr_round;
	}
	/**
	 * @param currRound the curr_round to set
	 */
	public void setCurr_round(Short currRound) {
		curr_round = currRound;
	}
	/**
	 * @return the curr_matchSeason
	 */
	@Column(name="Curr_matchSeason")
	public String getCurr_matchSeason() {
		return curr_matchSeason;
	}
	/**
	 * @param currMatchSeason the curr_matchSeason to set
	 */
	public void setCurr_matchSeason(String currMatchSeason) {
		curr_matchSeason = currMatchSeason;
	}
	/**
	 * @return the sclass_pic
	 */
	@Column(name="Sclass_pic")
	public String getSclass_pic() {
		return sclass_pic;
	}
	/**
	 * @param sclassPic the sclass_pic to set
	 */
	public void setSclass_pic(String sclassPic) {
		sclass_pic = sclassPic;
	}
	/**
	 * @return the ifstop
	 */
	@Column(name="ifstop")
	public Byte getIfstop() {
		return ifstop;
	}
	/**
	 * @param ifstop the ifstop to set
	 */
	public void setIfstop(Byte ifstop) {
		this.ifstop = ifstop;
	}
	/**
	 * @return the sclass_order
	 */
	@Column(name="sclass_order")
	public Integer getSclass_order() {
		return sclass_order;
	}
	/**
	 * @param sclassOrder the sclass_order to set
	 */
	public void setSclass_order(Integer sclassOrder) {
		sclass_order = sclassOrder;
	}
	/**
	 * @return the sclass_type
	 */
	@Column(name="Sclass_type")
	public Byte getSclass_type() {
		return sclass_type;
	}
	/**
	 * @param sclassType the sclass_type to set
	 */
	public void setSclass_type(Byte sclassType) {
		sclass_type = sclassType;
	}
	/**
	 * @return the ifindex
	 */
	@Column(name="ifindex")
	public Byte getIfindex() {
		return ifindex;
	}
	/**
	 * @param ifindex the ifindex to set
	 */
	public void setIfindex(Byte ifindex) {
		this.ifindex = ifindex;
	}
	/**
	 * @return the count_group
	 */
	@Column(name="count_group")
	public Short getCount_group() {
		return count_group;
	}
	/**
	 * @param countGroup the count_group to set
	 */
	public void setCount_group(Short countGroup) {
		count_group = countGroup;
	}
	/**
	 * @return the sclass_rule
	 */
	@Column(name="sclass_rule",columnDefinition="TEXT")
	public String getSclass_rule() {
		return sclass_rule;
	}
	/**
	 * @param sclassRule the sclass_rule to set
	 */
	public void setSclass_rule(String sclassRule) {
		sclass_rule = sclassRule;
	}
	/**
	 * @return the bf_simply_disp
	 */
	@Column(name="Bf_simply_disp")
	public Byte getBf_simply_disp() {
		return bf_simply_disp;
	}
	/**
	 * @param bfSimplyDisp the bf_simply_disp to set
	 */
	public void setBf_simply_disp(Byte bfSimplyDisp) {
		bf_simply_disp = bfSimplyDisp;
	}
	/**
	 * @return the sclass_sequence
	 */
	@Column(name="sclass_sequence")
	public Short getSclass_sequence() {
		return sclass_sequence;
	}
	/**
	 * @param sclassSequence the sclass_sequence to set
	 */
	public void setSclass_sequence(Short sclassSequence) {
		sclass_sequence = sclassSequence;
	}
	/**
	 * @return the infoID
	 */
	@Column(name="InfoID")
	public Short getInfoID() {
		return infoID;
	}
	/**
	 * @param infoID the infoID to set
	 */
	public void setInfoID(Short infoID) {
		this.infoID = infoID;
	}
	/**
	 * @return the msrepl_tran_version
	 */
	@Column(name="msrepl_tran_version")
	public Integer getMsrepl_tran_version() {
		return msrepl_tran_version;
	}
	/**
	 * @param msreplTranVersion the msrepl_tran_version to set
	 */
	public void setMsrepl_tran_version(Integer msreplTranVersion) {
		msrepl_tran_version = msreplTranVersion;
	}
	/**
	 * @return the ifShow
	 */
	@Column(name="IfShow")
	public Byte getIfShow() {
		return ifShow;
	}
	/**
	 * @param ifShow the ifShow to set
	 */
	public void setIfShow(Byte ifShow) {
		this.ifShow = ifShow;
	}
	/**
	 * @return the bf_IfDisp
	 */
	@Column(name="Bf_IfDisp")
	public Byte getBf_IfDisp() {
		return bf_IfDisp;
	}
	/**
	 * @param bfIfDisp the bf_IfDisp to set
	 */
	public void setBf_IfDisp(Byte bfIfDisp) {
		bf_IfDisp = bfIfDisp;
	}
	/**
	 * @return the infoUrl
	 */
	@Column(name="InfoUrl")
	public String getInfoUrl() {
		return infoUrl;
	}
	/**
	 * @param infoUrl the infoUrl to set
	 */
	public void setInfoUrl(String infoUrl) {
		this.infoUrl = infoUrl;
	}
	/**
	 * @return the modifyTime
	 */
	@Column(name="ModifyTime")
	public Date getModifyTime() {
		return modifyTime;
	}
	/**
	 * @param modifyTime the modifyTime to set
	 */
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	/**
	 * @return the beginSeason
	 */
	@Column(name="BeginSeason")
	public String getBeginSeason() {
		return beginSeason;
	}
	/**
	 * @param beginSeason the beginSeason to set
	 */
	public void setBeginSeason(String beginSeason) {
		this.beginSeason = beginSeason;
	}
	/**
	 * @return the sclass_ruleEn
	 */
	@Column(name="sclass_ruleEn",columnDefinition="TEXT")
	public String getSclass_ruleEn() {
		return sclass_ruleEn;
	}
	/**
	 * @param sclassRuleEn the sclass_ruleEn to set
	 */
	public void setSclass_ruleEn(String sclassRuleEn) {
		sclass_ruleEn = sclassRuleEn;
	}
	/**
	 * @return the nowGoalShow
	 */
	@Column(name="NowGoalShow")
	public Boolean getNowGoalShow() {
		return nowGoalShow;
	}
	/**
	 * @param nowGoalShow the nowGoalShow to set
	 */
	public void setNowGoalShow(Boolean nowGoalShow) {
		this.nowGoalShow = nowGoalShow;
	}
	/**
	 * @return the getEspn
	 */
	@Column(name="GetEspn")
	public Boolean getGetEspn() {
		return getEspn;
	}
	/**
	 * @param getEspn the getEspn to set
	 */
	public void setGetEspn(Boolean getEspn) {
		this.getEspn = getEspn;
	}
	/**
	 * @return the subSclassID
	 */
	@Column(name="subSclassID")
	public Long getSubSclassID() {
		return subSclassID;
	}
	/**
	 * @param subSclassID the subSclassID to set
	 */
	public void setSubSclassID(Long subSclassID) {
		this.subSclassID = subSclassID;
	}
	/**
	 * @return the ifHaveSub
	 */
	@Column(name="ifHaveSub")
	public Boolean getIfHaveSub() {
		return ifHaveSub;
	}
	/**
	 * @param ifHaveSub the ifHaveSub to set
	 */
	public void setIfHaveSub(Boolean ifHaveSub) {
		this.ifHaveSub = ifHaveSub;
	}
	@Override
	public int hashCode() {
		int returnValue = 17;
		if(null!=id){
			returnValue = 37 * returnValue + (int) (id ^ (id >>> 32));
		}
		if(null!=color){
			returnValue = 37 * returnValue + color.hashCode();
		}
		if(null!=name_J){
			returnValue = 37 * returnValue + name_J.hashCode();
		}
		if(null!=name_F){
			returnValue = 37 * returnValue + name_F.hashCode();
		}
		if(null!=name_E){
			returnValue = 37 * returnValue + name_E.hashCode();
		}
		if(null!=name_JS){
			returnValue = 37 * returnValue + name_JS.hashCode();
		}
		if(null!=name_FS){
			returnValue = 37 * returnValue + name_FS.hashCode();
		}
		if(null!=name_ES){
			returnValue = 37 * returnValue + name_ES.hashCode();
		}
		if(null!=kind){
			returnValue = 37 * returnValue + (int)kind;
		}
		if(null!=count_round){
			returnValue = 37 * returnValue + (int)count_round;
		}
		if(null!=curr_round){
			returnValue = 37 * returnValue + (int)curr_round;
		}
		if(null!=curr_matchSeason){
			returnValue = 37 * returnValue + curr_matchSeason.hashCode();
		}
        return returnValue;
	}

	@Override
	public boolean equals(Object obj) {
		
		if (obj instanceof Sclass) {
			Sclass sclass = (Sclass)obj;
			if(sclass.hashCode()!=this.hashCode()){
				return false;
			}
			return true;
		}else{
			return false;
		}

	}
}
