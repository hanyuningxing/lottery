package com.cai310.lottery.web.controller.info;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;

import com.cai310.lottery.common.DrawingOrderState;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.common.LotteryUtil;
import com.cai310.lottery.common.SchemePrintState;
import com.cai310.lottery.common.SchemeState;
import com.cai310.lottery.entity.info.TaskInfoData;
import com.cai310.lottery.entity.lottery.Scheme;
import com.cai310.lottery.entity.lottery.jclq.JclqMatch;
import com.cai310.lottery.entity.lottery.jczq.JczqMatch;
import com.cai310.lottery.entity.user.DrawingOrder;
import com.cai310.lottery.entity.user.Ipsorder;
import com.cai310.lottery.fetch.jclq.JclqContextHolder;
import com.cai310.lottery.fetch.jczq.JczqContextHolder;
import com.cai310.lottery.service.QueryService;
import com.cai310.lottery.service.lottery.SchemeEntityManager;
import com.cai310.lottery.service.lottery.jclq.JclqMatchEntityManager;
import com.cai310.lottery.service.lottery.jczq.JczqMatchEntityManager;
import com.cai310.lottery.service.user.UserEntityManager;
import com.cai310.lottery.ticket.common.TicketConstant;
import com.cai310.lottery.web.controller.BaseController;
import com.cai310.orm.XDetachedCriteria;
import com.cai310.utils.DateUtil;
import com.cai310.utils.Struts2Utils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
public class TaskController extends BaseController {
	private static final long serialVersionUID = -3004288085663395253L;
	private List<TaskInfoData> taskInfoDataList;
	@Resource
	private QueryService queryService;
	@SuppressWarnings("rawtypes")
	private Map<Lottery, SchemeEntityManager> schemeEntityManagerMap = Maps.newHashMap();
	@Autowired
	private JczqMatchEntityManager jczqMatchEntityManager;
	@Autowired
	private JclqMatchEntityManager jclqMatchEntityManager;
	@Autowired
	private UserEntityManager userEntityManager;
	@SuppressWarnings("rawtypes")
	private SchemeEntityManager getSchemeEntityManager(Lottery lotteryType) {
		return schemeEntityManagerMap.get(lotteryType);
	}

	@SuppressWarnings("rawtypes")
	@Autowired
	public void setSchemeEntityManagerList(List<SchemeEntityManager> schemeEntityManagerList) {
		for (SchemeEntityManager manager : schemeEntityManagerList) {
			schemeEntityManagerMap.put(manager.getLottery(), manager);
		}
	}
	public String index(){
		XDetachedCriteria criteria = new XDetachedCriteria(TaskInfoData.class, "m");
		taskInfoDataList = queryService.findByDetachedCriteria(criteria);
		return "task";
	}
	/**
	 * @return the taskInfoDataList
	 */
	public List<TaskInfoData> getTaskInfoDataList() {
		return taskInfoDataList;
	}
	/**
	 * @param taskInfoDataList the taskInfoDataList to set
	 */
	public void setTaskInfoDataList(List<TaskInfoData> taskInfoDataList) {
		this.taskInfoDataList = taskInfoDataList;
	}
	private Integer lastJclqMatchSize;
	private Integer lastJczqMatchSize;
	private Integer jclqMatchSize;
	private Integer jczqMatchSize;
	private Map<Lottery,List> unprint=Maps.newHashMap();
	private Map<Lottery,List> print=Maps.newHashMap();
	private Map<Lottery,List> failed=Maps.newHashMap();
	private Map<Lottery,List> upprint_success=Maps.newHashMap();
	private Map<Lottery,List> printed_canecl=Maps.newHashMap();
	private List<Ipsorder> ipsorderList = Lists.newArrayList();
	private List<DrawingOrder> drawingOrderList = Lists.newArrayList();
	private Lottery[] lotteryArr;
	public Lottery[] getLotteryArr() {
		return LotteryUtil.getWebLotteryList();
	}

	public void setLotteryArr(Lottery[] lotteryArr) {
		this.lotteryArr = lotteryArr;
	}
	public String jcmatch(){
		jclqMatchSize = null==JclqContextHolder.getMatchList()?0:JclqContextHolder.getMatchList().size();
		lastJclqMatchSize = null==findJclqMatchsOfCacheable()?0:findJclqMatchsOfCacheable().size();
		if(lastJclqMatchSize!=jclqMatchSize){
			Struts2Utils.setAttribute("jclqErr", true);
		}
		jczqMatchSize = null==JczqContextHolder.getMatchList()?0:JczqContextHolder.getMatchList().size();
		lastJczqMatchSize = null==findJczqMatchsOfCacheable()?0:findJczqMatchsOfCacheable().size();
		if(lastJczqMatchSize!=jczqMatchSize){
			Struts2Utils.setAttribute("jczqErr", true);
		}
		for (Lottery lottery : getLotteryArr()) {
			List<Criterion> restrictions = Lists.newArrayList();
			//restrictions.add(Restrictions.ne("schemePrintState", SchemePrintState.SUCCESS));
			restrictions.add(Restrictions.eq("state", SchemeState.FULL));
			restrictions.add(Restrictions.eq("sendToPrint",true));
			if(lottery.equals(Lottery.JCLQ)||lottery.equals(Lottery.JCZQ)){
				restrictions.add(Restrictions.eq("lotteryType",lottery));
			}

			ProjectionList prop = Projections.projectionList();
			prop.add(Projections.count("id"), "units");
			prop.add(Projections.groupProperty("commitTime"), "commitTime");
			prop.add(Projections.groupProperty("schemePrintState"),"schemePrintState");
			
			List<Scheme> unprint_list = Lists.newArrayList();
			List<Scheme> print_list = Lists.newArrayList();
			List<Scheme> failed_list = Lists.newArrayList();
			List<Scheme> list = getSchemeEntityManager(lottery).countSchemeByCriterion(restrictions,prop);
			for (Scheme scheme : list) {
				if(SchemePrintState.UNPRINT.equals(scheme.getSchemePrintState())){
					unprint_list.add(scheme);
				}else if(SchemePrintState.PRINT.equals(scheme.getSchemePrintState())){
					print_list.add(scheme);
				}else if(SchemePrintState.FAILED.equals(scheme.getSchemePrintState())){
					failed_list.add(scheme);
				}
			}
			unprint.put(lottery,unprint_list);//未出票
			print.put(lottery,print_list); //出票中
			failed.put(lottery,failed_list); //出票失败 
			
			prop = Projections.projectionList();
			prop.add(Projections.count("id"), "units");
			prop.add(Projections.groupProperty("commitTime"), "commitTime");
				
			restrictions = Lists.newArrayList();
			restrictions.add(Restrictions.ne("schemePrintState", SchemePrintState.SUCCESS));
			restrictions.add(Restrictions.eq("state", SchemeState.SUCCESS));
			restrictions.add(Restrictions.eq("sendToPrint",true));
			restrictions.add(Restrictions.gt("createTime",DateUtil.getdecDateOfMinute(new Date(),TicketConstant.QUERY_MIN)));
			upprint_success.put(lottery,getSchemeEntityManager(lottery).countSchemeByCriterion(restrictions,prop));//出票异常
			
			
			restrictions = Lists.newArrayList();
			restrictions.add(Restrictions.eq("schemePrintState", SchemePrintState.SUCCESS));
			restrictions.add(Restrictions.in("state", SchemeState.UNSUCCESS));
			restrictions.add(Restrictions.eq("sendToPrint",true));
			restrictions.add(Restrictions.gt("createTime",DateUtil.getdecDateOfMinute(new Date(),TicketConstant.QUERY_MIN)));

			printed_canecl.put(lottery,getSchemeEntityManager(lottery).countSchemeByCriterion(restrictions,prop));//出票异常
		}
		ProjectionList prop = Projections.projectionList();
		prop.add(Projections.count("id"), "version");
		prop.add(Projections.groupProperty("payWay"), "payWay");
			
		List<Criterion> restrictions = Lists.newArrayList();
		restrictions.add(Restrictions.eq("ifcheck",false));
		restrictions.add(Restrictions.gt("createTime",DateUtil.getdecDateOfDate(new Date(),7)));
		ipsorderList = userEntityManager.countIpsorder(restrictions, prop, Transformers.aliasToBean(Ipsorder.class));
		
		
		prop = Projections.projectionList();
		prop.add(Projections.count("id"), "version");
		prop.add(Projections.groupProperty("state"), "state");
			
		restrictions = Lists.newArrayList();
		restrictions.add(Restrictions.gt("createTime",DateUtil.getdecDateOfDate(new Date(),7)));
		List<DrawingOrder> list = userEntityManager.countDrawingOrder(restrictions, prop, Transformers.aliasToBean(DrawingOrder.class));
		for (DrawingOrder drawingOrder : list) {
			if(DrawingOrderState.CHECKING.equals(drawingOrder.getState())||DrawingOrderState.PASSCHECK.equals(drawingOrder.getState())){
				drawingOrderList.add(drawingOrder);
			}
		}
		
		return "jcmatch";
	}
	private Lottery lottery;
	private List<Scheme> schemeList;
	private Date commitTime;
	public String findScheme(){
		Integer type = Integer.valueOf(Struts2Utils.getParameter("type"));
		List<Criterion> restrictions = Lists.newArrayList();
		if(null!=commitTime){
			restrictions.add(Restrictions.eq("commitTime", commitTime));
		}
		restrictions.add(Restrictions.eq("sendToPrint",true));
		if(Integer.valueOf(0).equals(type)){
			restrictions.add(Restrictions.eq("schemePrintState", SchemePrintState.UNPRINT));
			restrictions.add(Restrictions.eq("state", SchemeState.FULL));
			schemeList= getSchemeEntityManager(lottery).findSchemeByCriterion(restrictions);
		}else if(Integer.valueOf(1).equals(type)){
			restrictions.add(Restrictions.eq("schemePrintState", SchemePrintState.PRINT));
			restrictions.add(Restrictions.eq("state", SchemeState.FULL));
			schemeList= getSchemeEntityManager(lottery).findSchemeByCriterion(restrictions);
		}else if(Integer.valueOf(2).equals(type)){
			restrictions.add(Restrictions.eq("schemePrintState", SchemePrintState.FAILED));
			restrictions.add(Restrictions.eq("state", SchemeState.FULL));
			schemeList= getSchemeEntityManager(lottery).findSchemeByCriterion(restrictions);
		}else if(Integer.valueOf(3).equals(type)){
			restrictions.add(Restrictions.ne("schemePrintState", SchemePrintState.SUCCESS));
			restrictions.add(Restrictions.eq("state", SchemeState.SUCCESS));
			restrictions.add(Restrictions.gt("createTime",DateUtil.getdecDateOfMinute(new Date(),TicketConstant.QUERY_MIN)));
			schemeList= getSchemeEntityManager(lottery).findSchemeByCriterion(restrictions);//出票异常
		}else if(Integer.valueOf(4).equals(type)){
			restrictions.add(Restrictions.eq("schemePrintState", SchemePrintState.SUCCESS));
			restrictions.add(Restrictions.in("state", SchemeState.UNSUCCESS));
			restrictions.add(Restrictions.gt("createTime",DateUtil.getdecDateOfMinute(new Date(),TicketConstant.QUERY_MIN)));
			schemeList= getSchemeEntityManager(lottery).findSchemeByCriterion(restrictions);//出票异常
		}
		return "scheme";
	}
	protected List<JczqMatch> findJczqMatchsOfCacheable() {
		List<JczqMatch> matchList  = null;
		if (matchList == null || matchList.isEmpty()) {
			matchList = jczqMatchEntityManager.findMatchsOfTicketUnEnd();
		}
		return matchList;
	}
	protected List<JclqMatch> findJclqMatchsOfCacheable() {
		List<JclqMatch> matchList  = null;
		if (matchList == null || matchList.isEmpty()) {
			matchList = jclqMatchEntityManager.findMatchsOfTicketUnEnd();
		}
		return matchList;
	}
	public Integer getJclqMatchSize() {
		return jclqMatchSize;
	}
	public void setJclqMatchSize(Integer jclqMatchSize) {
		this.jclqMatchSize = jclqMatchSize;
	}
	public Integer getJczqMatchSize() {
		return jczqMatchSize;
	}
	public void setJczqMatchSize(Integer jczqMatchSize) {
		this.jczqMatchSize = jczqMatchSize;
	}
	public Integer getLastJclqMatchSize() {
		return lastJclqMatchSize;
	}
	public void setLastJclqMatchSize(Integer lastJclqMatchSize) {
		this.lastJclqMatchSize = lastJclqMatchSize;
	}
	public Integer getLastJczqMatchSize() {
		return lastJczqMatchSize;
	}
	public void setLastJczqMatchSize(Integer lastJczqMatchSize) {
		this.lastJczqMatchSize = lastJczqMatchSize;
	}

	public Map<Lottery, List> getUnprint() {
		return unprint;
	}

	public void setUnprint(Map<Lottery, List> unprint) {
		this.unprint = unprint;
	}

	public Map<Lottery, List> getPrint() {
		return print;
	}

	public void setPrint(Map<Lottery, List> print) {
		this.print = print;
	}

	public Map<Lottery, List> getFailed() {
		return failed;
	}

	public void setFailed(Map<Lottery, List> failed) {
		this.failed = failed;
	}

	public Map<Lottery, List> getUpprint_success() {
		return upprint_success;
	}

	public void setUpprint_success(Map<Lottery, List> upprint_success) {
		this.upprint_success = upprint_success;
	}

	public Map<Lottery, List> getPrinted_canecl() {
		return printed_canecl;
	}

	public void setPrinted_canecl(Map<Lottery, List> printed_canecl) {
		this.printed_canecl = printed_canecl;
	}

	public Lottery getLottery() {
		return lottery;
	}

	public void setLottery(Lottery lottery) {
		this.lottery = lottery;
	}

	public List<Scheme> getSchemeList() {
		return schemeList;
	}

	public void setSchemeList(List<Scheme> schemeList) {
		this.schemeList = schemeList;
	}

	public Date getCommitTime() {
		return commitTime;
	}

	public void setCommitTime(Date commitTime) {
		this.commitTime = commitTime;
	}

	public List<Ipsorder> getIpsorderList() {
		return ipsorderList;
	}

	public void setIpsorderList(List<Ipsorder> ipsorderList) {
		this.ipsorderList = ipsorderList;
	}

	public List<DrawingOrder> getDrawingOrderList() {
		return drawingOrderList;
	}

	public void setDrawingOrderList(List<DrawingOrder> drawingOrderList) {
		this.drawingOrderList = drawingOrderList;
	}

	
}