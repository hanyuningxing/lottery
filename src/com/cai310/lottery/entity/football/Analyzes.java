//package com.cai310.lottery.entity.football;
//
//import java.util.Date;
//
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.Table;
//
//import org.hibernate.annotations.Cache;
//import org.hibernate.annotations.CacheConcurrencyStrategy;
//@Entity
//@Table(name = "football_analyzes")
//@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
//public class Analyzes extends FootballIdEntity{
//	/**
//	 * 
//	 */
//	private static final long serialVersionUID = -6273987548489011353L;
//	private Long scheduleId;
//	private String homeArray;
//	private String guestArray;
//	private String homeLineup;
//	private String guestLineup;
//	private String homeBackup;
//	private String guestBackup;
//	private String homeLineup_J;
//	private String guestLineup_J;
//	private String homeBackup_J;
//	private String guestBackup_J;
//	private String homeLineup2 ;
//	private String guestLineup2;
//	private String homeBackup2;
//	private String guestBackup2;
//	private String homeLineup_J2;
//	private String guestLineup_J2;
//	private String homeBackup_J2;
//	
//	
//	private String guestBackup_J2;
//	private String media_Win;
//	private String media_Zc;
//	private String media_Cb;
//	private String media_Sun;
//	private String media_East;
//	private String media_Apple;
//	private String briefing;
//	private String comment_XS;
//	
//	private String vs_Past;
//	private Integer h_order;
//	private Integer g_order;
//	private String h_StopMatch;
//	private String g_StopMatch;
//	private String h_Injure;
//	private String g_Injure;
//	private String score;
//	private Date modifyTime;
//	private String media_Mb;
//	private String technicCount;
//	private boolean created;
//	/**
//	 * @return the scheduleId
//	 */
//	@Column(name="ScheduleID")
//	public Long getScheduleId() {
//		return scheduleId;
//	}
//	/**
//	 * @param scheduleId the scheduleId to set
//	 */
//	public void setScheduleId(Long scheduleId) {
//		this.scheduleId = scheduleId;
//	}
//	/**
//	 * @return the homeArray
//	 */
//	@Column(name="homeArray",length=50)
//	public String getHomeArray() {
//		return homeArray;
//	}
//	/**
//	 * @param homeArray the homeArray to set
//	 */
//	public void setHomeArray(String homeArray) {
//		this.homeArray = homeArray;
//	}
//	/**
//	 * @return the guestArray
//	 */
//	@Column(name="guestArray",length=50)
//	public String getGuestArray() {
//		return guestArray;
//	}
//	/**
//	 * @param guestArray the guestArray to set
//	 */
//	public void setGuestArray(String guestArray) {
//		this.guestArray = guestArray;
//	}
//	/**
//	 * @return the homeLineup
//	 */
//	@Column(name="HomeLineup", columnDefinition = "TEXT")
//	public String getHomeLineup() {
//		return homeLineup;
//	}
//	/**
//	 * @param homeLineup the homeLineup to set
//	 */
//	public void setHomeLineup(String homeLineup) {
//		this.homeLineup = homeLineup;
//	}
//	/**
//	 * @return the guestLineup
//	 */
//	@Column(name="GuestLineup", columnDefinition = "TEXT")
//	public String getGuestLineup() {
//		return guestLineup;
//	}
//	/**
//	 * @param guestLineup the guestLineup to set
//	 */
//	public void setGuestLineup(String guestLineup) {
//		this.guestLineup = guestLineup;
//	}
//	/**
//	 * @return the homeBackup
//	 */
//	@Column(name="HomeBackup", columnDefinition = "TEXT")
//	public String getHomeBackup() {
//		return homeBackup;
//	}
//	/**
//	 * @param homeBackup the homeBackup to set
//	 */
//	public void setHomeBackup(String homeBackup) {
//		this.homeBackup = homeBackup;
//	}
//	/**
//	 * @return the guestBackup
//	 */
//	@Column(name="GuestBackup", columnDefinition = "TEXT")
//	public String getGuestBackup() {
//		return guestBackup;
//	}
//	/**
//	 * @param guestBackup the guestBackup to set
//	 */
//	public void setGuestBackup(String guestBackup) {
//		this.guestBackup = guestBackup;
//	}
//	/**
//	 * @return the homeLineup_J
//	 */
//	@Column(name="HomeLineup_J", columnDefinition = "TEXT")
//	public String getHomeLineup_J() {
//		return homeLineup_J;
//	}
//	/**
//	 * @param homeLineupJ the homeLineup_J to set
//	 */
//	public void setHomeLineup_J(String homeLineupJ) {
//		homeLineup_J = homeLineupJ;
//	}
//	/**
//	 * @return the guestLineup_J
//	 */
//	@Column(name="GuestLineup_J", columnDefinition = "TEXT")
//	public String getGuestLineup_J() {
//		return guestLineup_J;
//	}
//	/**
//	 * @param guestLineupJ the guestLineup_J to set
//	 */
//	public void setGuestLineup_J(String guestLineupJ) {
//		guestLineup_J = guestLineupJ;
//	}
//	/**
//	 * @return the homeBackup_J
//	 */
//	@Column(name="HomeBackup_J", columnDefinition = "TEXT")
//	public String getHomeBackup_J() {
//		return homeBackup_J;
//	}
//	/**
//	 * @param homeBackupJ the homeBackup_J to set
//	 */
//	public void setHomeBackup_J(String homeBackupJ) {
//		homeBackup_J = homeBackupJ;
//	}
//	/**
//	 * @return the guestBackup_J
//	 */
//	@Column(name="GuestBackup_J", columnDefinition = "TEXT")
//	public String getGuestBackup_J() {
//		return guestBackup_J;
//	}
//	/**
//	 * @param guestBackupJ the guestBackup_J to set
//	 */
//	public void setGuestBackup_J(String guestBackupJ) {
//		guestBackup_J = guestBackupJ;
//	}
//	/**
//	 * @return the homeLineup2
//	 */
//	@Column(name="HomeLineup2", columnDefinition = "TEXT")
//	public String getHomeLineup2() {
//		return homeLineup2;
//	}
//	/**
//	 * @param homeLineup2 the homeLineup2 to set
//	 */
//	public void setHomeLineup2(String homeLineup2) {
//		this.homeLineup2 = homeLineup2;
//	}
//	/**
//	 * @return the guestLineup2
//	 */
//	@Column(name="GuestLineup2", columnDefinition = "TEXT")
//	public String getGuestLineup2() {
//		return guestLineup2;
//	}
//	/**
//	 * @param guestLineup2 the guestLineup2 to set
//	 */
//	public void setGuestLineup2(String guestLineup2) {
//		this.guestLineup2 = guestLineup2;
//	}
//	/**
//	 * @return the homeBackup2
//	 */
//	@Column(name="HomeBackup2", columnDefinition = "TEXT")
//	public String getHomeBackup2() {
//		return homeBackup2;
//	}
//	/**
//	 * @param homeBackup2 the homeBackup2 to set
//	 */
//	public void setHomeBackup2(String homeBackup2) {
//		this.homeBackup2 = homeBackup2;
//	}
//	/**
//	 * @return the guestBackup2
//	 */
//	@Column(name="GuestBackup2", columnDefinition = "TEXT")
//	public String getGuestBackup2() {
//		return guestBackup2;
//	}
//	/**
//	 * @param guestBackup2 the guestBackup2 to set
//	 */
//	public void setGuestBackup2(String guestBackup2) {
//		this.guestBackup2 = guestBackup2;
//	}
//	/**
//	 * @return the homeLineup_J2
//	 */
//	@Column(name="HomeLineup_J2", columnDefinition = "TEXT")
//	public String getHomeLineup_J2() {
//		return homeLineup_J2;
//	}
//	/**
//	 * @param homeLineupJ2 the homeLineup_J2 to set
//	 */
//	public void setHomeLineup_J2(String homeLineupJ2) {
//		homeLineup_J2 = homeLineupJ2;
//	}
//	/**
//	 * @return the guestLineup_J2
//	 */
//	@Column(name="GuestLineup_J2", columnDefinition = "TEXT")
//	public String getGuestLineup_J2() {
//		return guestLineup_J2;
//	}
//	/**
//	 * @param guestLineupJ2 the guestLineup_J2 to set
//	 */
//	public void setGuestLineup_J2(String guestLineupJ2) {
//		guestLineup_J2 = guestLineupJ2;
//	}
//	/**
//	 * @return the homeBackup_J2
//	 */
//	@Column(name="HomeBackup_J2", columnDefinition = "TEXT")
//	public String getHomeBackup_J2() {
//		return homeBackup_J2;
//	}
//	/**
//	 * @param homeBackupJ2 the homeBackup_J2 to set
//	 */
//	public void setHomeBackup_J2(String homeBackupJ2) {
//		homeBackup_J2 = homeBackupJ2;
//	}
//	/**
//	 * @return the guestBackup_J2
//	 */
//	@Column(name="GuestBackup_J2", columnDefinition = "TEXT")
//	public String getGuestBackup_J2() {
//		return guestBackup_J2;
//	}
//	/**
//	 * @param guestBackupJ2 the guestBackup_J2 to set
//	 */
//	public void setGuestBackup_J2(String guestBackupJ2) {
//		guestBackup_J2 = guestBackupJ2;
//	}
//	/**
//	 * @return the media_Win
//	 */
//	@Column(name="Media_Win", length=50)
//	public String getMedia_Win() {
//		return media_Win;
//	}
//	/**
//	 * @param mediaWin the media_Win to set
//	 */
//	public void setMedia_Win(String mediaWin) {
//		media_Win = mediaWin;
//	}
//	/**
//	 * @return the media_Zc
//	 */
//	@Column(name="Media_Zc", length=50)
//	public String getMedia_Zc() {
//		return media_Zc;
//	}
//	/**
//	 * @param mediaZc the media_Zc to set
//	 */
//	public void setMedia_Zc(String mediaZc) {
//		media_Zc = mediaZc;
//	}
//	/**
//	 * @return the media_Cb
//	 */
//	@Column(name="Media_Cb", length=50)
//	public String getMedia_Cb() {
//		return media_Cb;
//	}
//	/**
//	 * @param mediaCb the media_Cb to set
//	 */
//	public void setMedia_Cb(String mediaCb) {
//		media_Cb = mediaCb;
//	}
//	/**
//	 * @return the media_Sun
//	 */
//	@Column(name="Media_Sun", length=50)
//	public String getMedia_Sun() {
//		return media_Sun;
//	}
//	/**
//	 * @param mediaSun the media_Sun to set
//	 */
//	public void setMedia_Sun(String mediaSun) {
//		media_Sun = mediaSun;
//	}
//	/**
//	 * @return the media_East
//	 */
//	@Column(name="Media_East", length=50)
//	public String getMedia_East() {
//		return media_East;
//	}
//	/**
//	 * @param mediaEast the media_East to set
//	 */
//	public void setMedia_East(String mediaEast) {
//		media_East = mediaEast;
//	}
//	/**
//	 * @return the media_Apple
//	 */
//	@Column(name="Media_Apple", length=50)
//	public String getMedia_Apple() {
//		return media_Apple;
//	}
//	/**
//	 * @param mediaApple the media_Apple to set
//	 */
//	public void setMedia_Apple(String mediaApple) {
//		media_Apple = mediaApple;
//	}
//	/**
//	 * @return the briefing
//	 */
//	@Column(name="Briefing", columnDefinition = "TEXT")
//	public String getBriefing() {
//		return briefing;
//	}
//	/**
//	 * @param briefing the briefing to set
//	 */
//	public void setBriefing(String briefing) {
//		this.briefing = briefing;
//	}
//	/**
//	 * @return the comment_XS
//	 */
//	@Column(name="Comment_XS", columnDefinition = "TEXT")
//	public String getComment_XS() {
//		return comment_XS;
//	}
//	/**
//	 * @param commentXS the comment_XS to set
//	 */
//	public void setComment_XS(String commentXS) {
//		comment_XS = commentXS;
//	}
//	/**
//	 * @return the vs_Past
//	 */
//	@Column(name="Vs_Past", columnDefinition = "TEXT")
//	public String getVs_Past() {
//		return vs_Past;
//	}
//	/**
//	 * @param vsPast the vs_Past to set
//	 */
//	public void setVs_Past(String vsPast) {
//		vs_Past = vsPast;
//	}
//	/**
//	 * @return the h_order
//	 */
//	@Column(name="H_order")
//	public Integer getH_order() {
//		return h_order;
//	}
//	/**
//	 * @param hOrder the h_order to set
//	 */
//	public void setH_order(Integer hOrder) {
//		h_order = hOrder;
//	}
//	/**
//	 * @return the g_order
//	 */
//	@Column(name="G_order")
//	public Integer getG_order() {
//		return g_order;
//	}
//	/**
//	 * @param gOrder the g_order to set
//	 */
//	public void setG_order(Integer gOrder) {
//		g_order = gOrder;
//	}
//	/**
//	 * @return the h_StopMatch
//	 */
//	@Column(name="H_StopMatch", columnDefinition = "TEXT")
//	public String getH_StopMatch() {
//		return h_StopMatch;
//	}
//	/**
//	 * @param hStopMatch the h_StopMatch to set
//	 */
//	public void setH_StopMatch(String hStopMatch) {
//		h_StopMatch = hStopMatch;
//	}
//	/**
//	 * @return the g_StopMatch
//	 */
//	@Column(name="G_StopMatch", columnDefinition = "TEXT")
//	public String getG_StopMatch() {
//		return g_StopMatch;
//	}
//	/**
//	 * @param gStopMatch the g_StopMatch to set
//	 */
//	public void setG_StopMatch(String gStopMatch) {
//		g_StopMatch = gStopMatch;
//	}
//	/**
//	 * @return the h_Injure
//	 */
//	@Column(name="H_Injure", columnDefinition = "TEXT")
//	public String getH_Injure() {
//		return h_Injure;
//	}
//	/**
//	 * @param hInjure the h_Injure to set
//	 */
//	public void setH_Injure(String hInjure) {
//		h_Injure = hInjure;
//	}
//	/**
//	 * @return the g_Injure
//	 */
//	@Column(name="G_Injure", columnDefinition = "TEXT")
//	public String getG_Injure() {
//		return g_Injure;
//	}
//	/**
//	 * @param gInjure the g_Injure to set
//	 */
//	public void setG_Injure(String gInjure) {
//		g_Injure = gInjure;
//	}
//	/**
//	 * @return the score
//	 */
//	@Column(name="Score", columnDefinition = "TEXT")
//	public String getScore() {
//		return score;
//	}
//	/**
//	 * @param score the score to set
//	 */
//	public void setScore(String score) {
//		this.score = score;
//	}
//	/**
//	 * @return the modifyTime
//	 */
//	@Column
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
//	 * @return the media_Mb
//	 */
//	@Column(name="Media_Mb", length=50)
//	public String getMedia_Mb() {
//		return media_Mb;
//	}
//	/**
//	 * @param mediaMb the media_Mb to set
//	 */
//	public void setMedia_Mb(String mediaMb) {
//		media_Mb = mediaMb;
//	}
//	/**
//	 * @return the technicCount
//	 */
//	@Column(name="TechnicCount", columnDefinition = "TEXT")
//	public String getTechnicCount() {
//		return technicCount;
//	}
//	/**
//	 * @param technicCount the technicCount to set
//	 */
//	public void setTechnicCount(String technicCount) {
//		this.technicCount = technicCount;
//	}
//	/**
//	 * @return the created
//	 */
//	@Column(name="Created", columnDefinition ="bit(1) default 0")
//	public boolean isCreated() {
//		return created;
//	}
//	/**
//	 * @param created the created to set
//	 */
//	public void setCreated(boolean created) {
//		this.created = created;
//	}
//}
