//package com.cai310.lottery.entity.football;
//
//import java.util.Date;
//
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.Table;
//
//import org.hibernate.annotations.Cache;
//import org.hibernate.annotations.CacheConcurrencyStrategy;
//@Entity
//@Table(name = "football_detail_result")
//@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
//public class DetailResult {
//	private Long id;
//	private Long scheduleID;
//	private Short happenTime;
//	private Long teamID;
//	private String playername;
//	private Long playerID;
//	private Short kind;
//	private Date modifyTime;
//	private String playername_e;
//	private String playernameTxt;
//	/**
//	 * @return the id
//	 */
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	@Column(name="ID")
//	public Long getId() {
//		return id;
//	}
//	/**
//	 * @param id the id to set
//	 */
//	public void setId(Long id) {
//		this.id = id;
//	}
//	/**
//	 * @return the scheduleID
//	 */
//	@Column(name="ScheduleID")
//	public Long getScheduleID() {
//		return scheduleID;
//	}
//	/**
//	 * @param scheduleID the scheduleID to set
//	 */
//	public void setScheduleID(Long scheduleID) {
//		this.scheduleID = scheduleID;
//	}
//	/**
//	 * @return the happenTime
//	 */
//	@Column(name="HappenTime")
//	public Short getHappenTime() {
//		return happenTime;
//	}
//	/**
//	 * @param happenTime the happenTime to set
//	 */
//	public void setHappenTime(Short happenTime) {
//		this.happenTime = happenTime;
//	}
//	/**
//	 * @return the teamID
//	 */
//	@Column(name="TeamID")
//	public Long getTeamID() {
//		return teamID;
//	}
//	/**
//	 * @param teamID the teamID to set
//	 */
//	public void setTeamID(Long teamID) {
//		this.teamID = teamID;
//	}
//	/**
//	 * @return the playername
//	 */
//	@Column(name="playername")
//	public String getPlayername() {
//		return playername;
//	}
//	/**
//	 * @param playername the playername to set
//	 */
//	public void setPlayername(String playername) {
//		this.playername = playername;
//	}
//	/**
//	 * @return the playerID
//	 */
//	@Column(name="PlayerID")
//	public Long getPlayerID() {
//		return playerID;
//	}
//	/**
//	 * @param playerID the playerID to set
//	 */
//	public void setPlayerID(Long playerID) {
//		this.playerID = playerID;
//	}
//	/**
//	 * @return the kind
//	 */
//	@Column(name="Kind")
//	public Short getKind() {
//		return kind;
//	}
//	/**
//	 * @param kind the kind to set
//	 */
//	public void setKind(Short kind) {
//		this.kind = kind;
//	}
//	/**
//	 * @return the modifyTime
//	 */
//	@Column(name="modifyTime")
//	public Date getModifyTime() {
//		return modifyTime;
//	}
//	/**
//	 * @param modifyTime the modifyTime to set
//	 */
//	public void setModifyTime(Date modifyTime) {
//		this.modifyTime = modifyTime;
//	}
//	/**
//	 * @return the playername_e
//	 */
//	@Column(name="playername_e")
//	public String getPlayername_e() {
//		return playername_e;
//	}
//	/**
//	 * @param playernameE the playername_e to set
//	 */
//	public void setPlayername_e(String playernameE) {
//		playername_e = playernameE;
//	}
//	/**
//	 * @return the playernameTxt
//	 */
//	@Column(name="playernameTxt")
//	public String getPlayernameTxt() {
//		return playernameTxt;
//	}
//	/**
//	 * @param playernameTxt the playernameTxt to set
//	 */
//	public void setPlayernameTxt(String playernameTxt) {
//		this.playernameTxt = playernameTxt;
//	}
//	@Override
//	public int hashCode() {
//		int returnValue = 17;
//		if(null!=scheduleID){
//			returnValue = 37 * returnValue + (int) (scheduleID ^ (scheduleID >>> 32));
//		}
//		if(null!=teamID){
//			returnValue = 37 * returnValue + (int) (teamID ^ (teamID >>> 32));
//		}
//		if(null!=kind){
//			returnValue = 37 * returnValue + (int) kind;
//		}
//		if(null!=happenTime){
//			returnValue = 37 * returnValue + (int) happenTime;
//		}
//		if(null!=playername){
//			returnValue = 37 * returnValue + playername.hashCode();
//		}
//        return returnValue;
//	}
//	@Override
//	public boolean equals(Object obj) {
//		if (obj instanceof DetailResult) {
//			DetailResult detailResult = (DetailResult)obj;
//			if(detailResult.hashCode()!=this.hashCode()){
//				return false;
//			}
//			return true;
//		}else{
//			return false;
//		}
//	}
//}
