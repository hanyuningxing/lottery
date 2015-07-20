package com.cai310.lottery.web.controller.ticket;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;


import net.sf.ehcache.Cache;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.cai310.lottery.Constant;
import com.cai310.lottery.JclqConstant;
import com.cai310.lottery.common.FundMode;
import com.cai310.lottery.common.LuckDetailType;
import com.cai310.lottery.common.PlatformInfo;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.common.LotteryCategory;
import com.cai310.lottery.common.SalesMode;
import com.cai310.lottery.common.SchemePrintState;
import com.cai310.lottery.common.SchemeState;
import com.cai310.lottery.common.SecretType;
import com.cai310.lottery.common.ShareType;
import com.cai310.lottery.common.SubscriptionLicenseType;
import com.cai310.lottery.common.SubscriptionWay;
import com.cai310.lottery.common.TicketSchemeState;
import com.cai310.lottery.common.UserWay;
import com.cai310.lottery.dao.ticket.TicketPlatformInfoDao;
import com.cai310.lottery.dto.lottery.PeriodDataDTO;
import com.cai310.lottery.dto.lottery.PeriodDataInfoDTO;
import com.cai310.lottery.dto.lottery.SchemeDTO;
import com.cai310.lottery.dto.lottery.SchemeInfoDTO;
import com.cai310.lottery.dto.lottery.SubscribeDTO;
import com.cai310.lottery.dto.lottery.SubsriptionDTO;
import com.cai310.lottery.entity.lottery.Period;
import com.cai310.lottery.entity.lottery.PeriodData;
import com.cai310.lottery.entity.lottery.Scheme;
import com.cai310.lottery.entity.lottery.jclq.JclqScheme;
import com.cai310.lottery.entity.lottery.jczq.JczqScheme;
import com.cai310.lottery.entity.lottery.dczc.DczcScheme;
import com.cai310.lottery.entity.lottery.dlt.DltPeriodData;
import com.cai310.lottery.entity.lottery.dlt.DltScheme;
import com.cai310.lottery.entity.lottery.pl.PlPeriodData;
import com.cai310.lottery.entity.lottery.pl.PlScheme;
import com.cai310.lottery.entity.lottery.seven.SevenPeriodData;
import com.cai310.lottery.entity.lottery.sevenstar.SevenstarPeriodData;
import com.cai310.lottery.entity.lottery.ssq.SsqPeriodData;
import com.cai310.lottery.entity.lottery.tc22to5.Tc22to5PeriodData;
import com.cai310.lottery.entity.lottery.ticket.Ticket;
import com.cai310.lottery.entity.lottery.ticket.TicketLogger;
import com.cai310.lottery.entity.lottery.welfare3d.Welfare3dPeriodData;
import com.cai310.lottery.entity.lottery.welfare3d.Welfare3dScheme;
import com.cai310.lottery.entity.lottery.zc.SfzcPeriodData;
import com.cai310.lottery.entity.lottery.zc.SfzcScheme;
import com.cai310.lottery.entity.ticket.TicketDatail;
import com.cai310.lottery.entity.ticket.TicketPlatformInfo;
import com.cai310.lottery.entity.ticket.TicketThen;
import com.cai310.lottery.entity.user.LuckDetail;
import com.cai310.lottery.entity.user.User;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.service.QueryService;
import com.cai310.lottery.service.ServiceException;
import com.cai310.lottery.service.lottery.PeriodDataEntityManager;
import com.cai310.lottery.service.lottery.PeriodEntityManager;
import com.cai310.lottery.service.lottery.SchemeEntityManager;
import com.cai310.lottery.service.lottery.SchemeService;
import com.cai310.lottery.service.lottery.ticket.impl.TicketEntityManager;
import com.cai310.lottery.service.ticket.TicketThenEntityManager;

import com.cai310.lottery.support.ContentBean;
import com.cai310.lottery.support.Executable;
import com.cai310.lottery.support.ExecuteException;
import com.cai310.lottery.support.ExecutorUtils;
import com.cai310.lottery.support.Item;
import com.cai310.lottery.support.dczc.ItemBF;
import com.cai310.lottery.support.jclq.JclqMatchItem;
import com.cai310.lottery.support.jczq.JczqMatchItem;

import com.cai310.lottery.support.pl.PlPlayType;
import com.cai310.lottery.ticket.common.SecurityUtil;
import com.cai310.lottery.ticket.protocol.response.dto.JcMatchOdds;
import com.cai310.lottery.ticket.protocol.response.dto.JcMatchOddsList;
import com.cai310.lottery.ticket.protocol.response.dto.JcTicketOddsList;
import com.cai310.lottery.utils.BigDecimalUtil;
import com.cai310.lottery.web.controller.WebDataException;
import com.cai310.lottery.web.controller.lottery.LotteryBaseController;
import com.cai310.lottery.web.controller.lottery.OrderType;
import com.cai310.lottery.web.controller.ticket.then.ReqParamVisitor;
import com.cai310.lottery.web.sso.SsoLoginHolder;
import com.cai310.orm.XDetachedCriteria;
import com.cai310.utils.JsonUtil;
import com.cai310.utils.DateUtil;
import com.cai310.utils.ReflectionUtils;
import com.cai310.utils.Struts2Utils;
import com.cai310.utils.WriteHTMLUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
@Results( {
	   @Result(name = "create", location = "/WEB-INF/content/ticket/create.jsp"),
	   @Result(name = "common", location = "/WEB-INF/content/ticket/common.jsp")
	}) 
public abstract class TicketBaseController<T extends Scheme,E extends SchemeDTO> extends LotteryBaseController {
    /**
	 * 
	 */
	@Resource
	TicketEntityManager ticketEntityManager;
	@Resource(name="schemeQueryCache")
	protected Cache schemeQueryCache;
	
	@Autowired
	protected QueryService queryService;
	@Autowired
	protected TicketThenEntityManager ticketThenEntityManager;
	@Resource(name="commonQueryCache")
	protected Cache commonQueryCache;
	@Autowired
	protected PeriodEntityManager periodManager;
	protected static final long serialVersionUID = 5741625897396579892L;
	protected List<ReqParamVisitor> ticketList = Lists.newArrayList();
	
	protected String wAction;
	
	protected String wLotteryId;
	
	protected String wParam;
	
	protected String wSign;
	
	protected String wAgent;

    protected String start;
    protected String count;
	
	protected User user;
	
	protected Class<E> schemeDTOClass;
	/** 期*/
	protected String periodNumber;
	
	protected String matchDate;

	/** 方案描述 */
	protected String description;

	/** 方案注数（单倍注数） */
	protected Integer units;

	/** 方案倍数 */
	protected Integer multiple;

	/** 方案金额 */
	protected Integer schemeCost;
	/** 方案订单号 */
	protected String orderId;
	
	protected TicketPlatformInfo ticketPlatformInfo;
	/**
	 * 方案投注的方式
	 * 
	 * @see com.cai310.lottery.common.SalesMode
	 * 0=复式
	 * 1=单式
	 */
	protected Integer mode;
	
	/**
	 * 方案分享的方式
	 * 
	 * @see com.cai310.lottery.common.ShareType
	 * 0=合买
	 * 1=自购
	 * 默认1
	 */
	protected Integer shareType=1;//分享类型
	protected BigDecimal subscriptionCost; //加入金额
	protected BigDecimal baodiCost;//保底金额
	protected Float commissionRate=0F;//佣金
	protected BigDecimal minSubscriptionCost=BigDecimal.valueOf(1);//最小认购
	/**
	 * 方案分享的方式
	 * 
	 * @see com.cai310.lottery.common.SecretType
	 *0="完全公开"
	 *1="开奖后公开"
	 *2="完全保密"
	 *合买默认0,自购=2
	 */
	protected Integer secretType=0;//分享类型

	protected Integer unitsMoney=2;

	protected T scheme;
	
	protected Long userId;
	
	protected String userPwd;
	
	protected String specialFlag;
	
	protected PlatformInfo platformInfo; 
	@SuppressWarnings("rawtypes")
	private Map<Lottery, PeriodDataEntityManager> periodDataEntityManagerMap = Maps.newHashMap();
	@SuppressWarnings("rawtypes")
	private PeriodDataEntityManager getPeriodDataEntityManager(Lottery lotteryType) {
		return periodDataEntityManagerMap.get(lotteryType);
	}
	@SuppressWarnings("rawtypes")
	@Autowired
	public void setPeriodDataEntityManagerList(List<PeriodDataEntityManager> periodDataEntityManagerList) {
		for (PeriodDataEntityManager manager : periodDataEntityManagerList) {
			periodDataEntityManagerMap.put(manager.getLottery(), manager);
		}
	}
	
	@SuppressWarnings("rawtypes")
	private Map<Lottery, SchemeEntityManager> schemeEntityManagerMap = Maps.newHashMap();
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
	
	protected abstract SchemeService<T, E> getSchemeService();
	
    protected SalesMode buildSalesMode() throws WebDataException{
    	 try{
    		 return SalesMode.values()[mode];
         }catch(Exception e){
 			logger.warn("投注方式解析错误."+e.getMessage());
 			throw new WebDataException("5-投注方式错误.");
 		}
    }
    protected ShareType buildShareType() throws WebDataException{
   	 try{
   		 if(null==shareType)return ShareType.SELF;
   		 return ShareType.values()[shareType];
        }catch(Exception e){
			logger.warn("分享方式解析错误."+e.getMessage());
			throw new WebDataException("5-分享方式错误.");
		}
   }
    protected SecretType buildSecretType() throws WebDataException{
      	 try{
      		 if(null==secretType)return SecretType.FULL_PUBLIC;
      		 return SecretType.values()[secretType];
           }catch(Exception e){
   			logger.warn("保密方式解析错误."+e.getMessage());
   			throw new WebDataException("5-保密方式错误.");
   		 }
      }
    @SuppressWarnings("unchecked")
	public TicketBaseController() {
		this.schemeDTOClass = ReflectionUtils.getSuperClassGenricType(getClass(), 1);
	}
	public void check() throws WebDataException, UnsupportedEncodingException {
		this.wAction=Struts2Utils.getParameter("wAction");
		this.wParam=Struts2Utils.getParameter("wParam");
		this.wSign=Struts2Utils.getParameter("wSign");
		this.wAgent=Struts2Utils.getParameter("wAgent");
		if(StringUtils.isBlank(wAction)){
			throw new WebDataException("4-请求Id为空");
		}
		if(StringUtils.isBlank(wParam)){
			throw new WebDataException("5-请求参数为空");
		}
		if(StringUtils.isBlank(wSign)){
			throw new WebDataException("1-请求密钥为空");
		}
		if(StringUtils.isBlank(wAgent)){
			throw new WebDataException("1-请求平台号为空");
		}
		try{
			if(StringUtils.isNotBlank(wParam)){
				Map<String, Object> map = JsonUtil.getMap4Json(wParam);
				if(null!=map){
					 wLotteryId = String.valueOf(map.get("wLotteryId"));
					 if(null!=map.get("ticket")){
						 String[] items = JsonUtil.getStringArray4Json(String.valueOf(map.get("ticket")));
							final List<ReqParamVisitor> correctList = new ArrayList<ReqParamVisitor>();
							for (String itemStr : items) {
								ReqParamVisitor reqParamVisitor = new ReqParamVisitor();
								reqParamVisitor.visit(itemStr);
								ticketList.add(reqParamVisitor);
						 }
					 }
					 start = null==map.get("start")?null:String.valueOf(map.get("start"));
					 count = null==map.get("count")?null:String.valueOf(map.get("count"));
					 
				}
				
			}
		}catch(Exception e){
			logger.warn(e.getMessage(), e);
			throw new WebDataException("5-ReqParam参数解析错误");
		}
		try{
			Lottery lottery = Lottery.values()[Integer.valueOf(wLotteryId)];
			if(null==lottery){
				throw new WebDataException("3-彩种为空");
			}
		}catch(Exception e){
			logger.warn(e.getMessage(), e);
			throw new WebDataException("3-彩种错误");
		}
		try{
			for (PlatformInfo platformInfoTemp : PlatformInfo.values()) {
				if(this.wAgent.trim().equals(platformInfoTemp.getId().trim()))platformInfo = platformInfoTemp;
			}
			if (platformInfo == null)
				throw new WebDataException("1-平台ID不能为空.");
			
			String param = wAction+wParam+wAgent+platformInfo.getPassword();
			String pwd = SecurityUtil.md5(param.getBytes("UTF-8")).toUpperCase().trim();
			if(!pwd.equals(wSign.trim())){
				throw new WebDataException("1-请求密钥验证错误");
			}			
		}catch(WebDataException e){
			logger.warn(e.getMessage(), e);
			throw new WebDataException(e.getMessage());
		}
	}
	protected  abstract void buildReqParamVisitor(ReqParamVisitor reqParamVisitor)throws WebDataException;
	protected  abstract List buildMatchList()throws WebDataException;
	protected  abstract List buildMatchResultList()throws WebDataException;
	protected String buildTicketResultXML(Map<Long,Map<String,String>> flag){
		 	StringBuffer sb = new StringBuffer();
		    for (Long orderid : flag.keySet()) {
		    	Map<String,String> temp = flag.get(orderid);
		   		sb.append("<ticket>");
		   	    sb.append("<process>"+temp.get("process")+"</process>");
		   	    sb.append("<orderId>"+temp.get("orderId")+"</orderId>");
		   	    if(null!=temp.get("errorMsg")){
		   	       sb.append("<errorMsg>"+temp.get("errorMsg")+"</errorMsg>");
		   	    }
		   	    if(null!=temp.get("ticketCode")){
		   	       sb.append("<ticketCode>"+temp.get("ticketCode")+"</ticketCode>");
		   	    }
		   	    if(null!=temp.get("awards")){
		   	       sb.append("<awards>"+temp.get("awards")+"</awards>");
		   	    }
		   	    if(null!=temp.get("operateTime")){
		   	       sb.append("<operateTime>"+temp.get("operateTime")+"</operateTime>");
		   	    }
		   	    sb.append("</ticket>");
		   	    
			}
	  	    return sb.toString();
	}
	public void checkUser(String userPwd,User user) throws WebDataException{
		String loginUserPwd = SsoLoginHolder.getPhoneLoginUserPwd(user);
		if(StringUtils.isBlank(loginUserPwd))throw new WebDataException("1-用户验证密码为空.");
		if(StringUtils.isBlank(userPwd))throw new WebDataException("1-验证密码为空.");
		if (!userPwd.trim().equalsIgnoreCase(loginUserPwd.trim())) {
			throw new WebDataException("1-验证密码错误.");
		} 
	}
    /**
	 * 保存新方案
	 */
	public String create() {
		Map map = Maps.newHashMap();
       	JsonConfig jsonConfig = new JsonConfig();  
		
		TicketLogger ticketLogger =null;
		List<Map<String,String>> flag = Lists.newArrayList();
		ReqParamVisitor reqParamVisitor_flag;
		try {
			check();
			E schemeDTO;
			Map<String,String> temp = null;
			Long time_all =System.currentTimeMillis();
			for (ReqParamVisitor reqParamVisitor : ticketList) {
				reqParamVisitor_flag = reqParamVisitor;
				System.out.println(reqParamVisitor.getOrderId()+"-----------------------开始");
				Long time =System.currentTimeMillis();
				try {
					user = userManager.getUser(Long.valueOf(reqParamVisitor.getUserId()));
					if (user == null)
						throw new WebDataException("1-找不到用户ID对应的用户.");
					if(user.isLocked())throw new WebDataException("2-帐号被冻结");
					////验证用户
					checkUser(reqParamVisitor.getUserPwd(),user);
					buildReqParamVisitor(reqParamVisitor);
					System.out.println("-----------------------解析"+(System.currentTimeMillis()-time));
					time=System.currentTimeMillis();
					schemeDTO = buildSchemeDTO();
					System.out.println("-----------------------构建"+(System.currentTimeMillis()-time));
					time=System.currentTimeMillis();
					////////////////////////////////////////
					synchronized (Constant.THENLOCK) {
						Boolean isRepeatOrder = getSchemeService().isRepeatOrder(orderId,schemeDTO.getSponsorId());
						System.out.println("-----------------------检查"+(System.currentTimeMillis()-time));
						time=System.currentTimeMillis();
						if(isRepeatOrder){
							throw new WebDataException("2-已有此订单");
						}
						checkUser(user,schemeDTO);
						schemeDTO.setIsTicket(true);
						if(null!=this.platformInfo){
							schemeDTO.setPlatform(platformInfo);
						}
						final E schemeDTOTemp = schemeDTO;
						ExecutorUtils.exec(new Executable() {
							public void run() throws ExecuteException {
								scheme = getSchemeService().createScheme(schemeDTOTemp);
							}
						}, 3);
						System.out.println("-----------------------方案"+(System.currentTimeMillis()-time));
						time=System.currentTimeMillis();
					}
					temp = Maps.newHashMap();
					temp.put("process", "0");
					temp.put("orderId", scheme.getId()+"");
					flag.add(temp);
					System.out.println("-----------------------完毕"+(System.currentTimeMillis()-time));
					time=System.currentTimeMillis();
				} catch (WebDataException e) {
					temp = Maps.newHashMap();
					String process ="4";
					if(null!=e.getMessage()&&e.getMessage().indexOf("-")!=-1){
						String tempStr = e.getMessage().split("-")[0];
						try {process = ""+Integer.valueOf(tempStr);}catch (Exception ex) {}///如果报错报系统错误
						temp.put("errorMsg", e.getMessage());
					}else{
						logger.warn(e.getMessage(), e);
						temp.put("errorMsg", "系统内部错误");
					}
					temp.put("process", process);
					flag.add(temp);
					continue;
				} catch (ServiceException e) {
					temp = Maps.newHashMap();
					String process ="4";
					if(null!=e.getMessage()&&e.getMessage().indexOf("-")!=-1){
						String tempStr = e.getMessage().split("-")[0];
						try {process = ""+Integer.valueOf(tempStr);}catch (Exception ex) {}///如果报错报系统错误
						temp.put("errorMsg", e.getMessage());
					}else{
						logger.warn(e.getMessage(), e);
						temp.put("errorMsg", "系统内部错误");
					}
					temp.put("process", process);
					flag.add(temp);
					continue;
				}catch (Exception e) {
					temp = Maps.newHashMap();
					String process ="4";
					if(null!=e.getMessage()&&e.getMessage().indexOf("-")!=-1){
						String tempStr = e.getMessage().split("-")[0];
						try {process = ""+Integer.valueOf(tempStr);}catch (Exception ex) {}///如果报错报系统错误
						temp.put("errorMsg", e.getMessage());
					}else{
						logger.warn(e.getMessage(), e);
						temp.put("errorMsg", "系统内部错误");
					}
					temp.put("process", process);
					flag.add(temp);
					continue;
				}catch (Throwable e) {
					temp = Maps.newHashMap();
					String process ="4";
					if(null!=e.getMessage()&&e.getMessage().indexOf("-")!=-1){
						String tempStr = e.getMessage().split("-")[0];
						try {process = ""+Integer.valueOf(tempStr);}catch (Exception ex) {}///如果报错报系统错误
						temp.put("errorMsg", e.getMessage());
					}else{
						logger.warn(e.getMessage(), e);
						temp.put("errorMsg", "系统内部错误");
					}
					temp.put("process", process);
					flag.add(temp);
					continue;
				}
			}
			System.out.println("-----------------------完毕"+(System.currentTimeMillis()-time_all));
			map.put("processId", "0");
			map.put("ticket", flag);
		} catch (WebDataException e) {
			String processId ="7";
			if(null!=e.getMessage()&&e.getMessage().indexOf("-")!=-1){
				String temp = e.getMessage().split("-")[0];
				try {processId = ""+Integer.valueOf(temp);}catch (Exception ex) {}///如果报错报系统错误
			}else{
				logger.warn(e.getMessage(), e);
			}
			map.put("processId", processId);
		 	
			map.put("errorMsg", e.getMessage());
		} catch (ServiceException e) {
			String processId ="7";
			if(null!=e.getMessage()&&e.getMessage().indexOf("-")!=-1){
				String temp = e.getMessage().split("-")[0];
				try {processId = ""+Integer.valueOf(temp);}catch (Exception ex) {}///如果报错报系统错误
			}else{
				logger.warn(e.getMessage(), e);
			}
			map.put("processId", processId);
		 	
			map.put("errorMsg", e.getMessage());
		} catch (Exception e) {
			String processId ="7";
			if(null!=e.getMessage()&&e.getMessage().indexOf("-")!=-1){
				String temp = e.getMessage().split("-")[0];
				try {processId = ""+Integer.valueOf(temp);}catch (Exception ex) {}///如果报错报系统错误
			}else{
				logger.warn(e.getMessage(), e);
			}	
			map.put("processId", processId);
		 	
			map.put("errorMsg", e.getMessage());
		}catch (Throwable e) {
			String processId ="7";
			if(null!=e.getMessage()&&e.getMessage().indexOf("-")!=-1){
				String temp = e.getMessage().split("-")[0];
				try {processId = ""+Integer.valueOf(temp);}catch (Exception ex) {}///如果报错报系统错误
			}else{
				logger.warn(e.getMessage(), e);
			}		
			map.put("processId", processId);
		 	
			map.put("errorMsg", e.getMessage());
		}
		renderJson(map,jsonConfig);
		return null;
	}
	
	public void checkTicket() throws WebDataException, UnsupportedEncodingException {
		this.wAction=Struts2Utils.getParameter("wAction");
		this.wParam=Struts2Utils.getParameter("wParam");
		this.wSign=Struts2Utils.getParameter("wSign");
		this.wAgent=Struts2Utils.getParameter("wAgent");
		if(StringUtils.isBlank(wAction)){
			throw new WebDataException("4-请求Id为空");
		}
		if(StringUtils.isBlank(wParam)){
			throw new WebDataException("5-请求参数为空");
		}
		if(StringUtils.isBlank(wSign)){
			throw new WebDataException("1-请求密钥为空");
		}
		if(StringUtils.isBlank(wAgent)){
			throw new WebDataException("1-请求平台号为空");
		}
		
		if (wParam.contains("%")){
			wParam = URLDecoder.decode(wParam,"UTF-8");
		}
		
		try{
			if(StringUtils.isNotBlank(wParam)){
				Map<String, Object> map = JsonUtil.getMap4Json(wParam);
				if(null!=map){
					 wLotteryId = String.valueOf(map.get("wLotteryId"));
					 if(null!=map.get("ticket")){
						 String[] items = JsonUtil.getStringArray4Json(String.valueOf(map.get("ticket")));
							final List<ReqParamVisitor> correctList = new ArrayList<ReqParamVisitor>();
							for (String itemStr : items) {
								ReqParamVisitor reqParamVisitor = new ReqParamVisitor();
								reqParamVisitor.visit(itemStr);
								if(StringUtils.isBlank(reqParamVisitor.getMode())){
									reqParamVisitor.setMode("0");
								}
								ticketList.add(reqParamVisitor);
						 }
					 }
//					 start = null==map.get("start")?null:String.valueOf(map.get("start"));
//					 count = null==map.get("count")?null:String.valueOf(map.get("count"));
					 periodNumber = null==map.get("periodNumber")?"":String.valueOf(map.get("periodNumber"));
				}
				
			}
		}catch(Exception e){
			logger.warn(e.getMessage(), e);
			throw new WebDataException("5-ReqParam参数解析错误");
		}
		try{
			Lottery lottery = Lottery.values()[Integer.valueOf(wLotteryId)];
			if(null==lottery){
				throw new WebDataException("3-彩种为空");
			}
		}catch(Exception e){
			logger.warn(e.getMessage(), e);
			throw new WebDataException("3-彩种错误");
		}
		
		try{
			try{
				ticketPlatformInfo = ticketThenEntityManager.getTicketPlatformInfo(Long.valueOf(this.wAgent.trim()));
				if(null==ticketPlatformInfo){
					throw new WebDataException("1-平台号为空");
				}
			}catch(Exception e){
				logger.warn(e.getMessage(), e);
				throw new WebDataException("1-平台号错误");
			}
			String param = wAction+wParam+wAgent+ticketPlatformInfo.getPassword();
			String pwd = SecurityUtil.md5(param.getBytes("UTF-8")).toUpperCase().trim();
			if(!pwd.equals(wSign.trim())){
				throw new WebDataException("1-请求密钥验证错误");
			}
			///增加ip检查
			String addr = Struts2Utils.getRemoteAddr();
			if(StringUtils.isNotBlank(ticketPlatformInfo.getLimitIp())){
				if(ticketPlatformInfo.getLimitIp().indexOf("|")!=-1){
					String[] arr = ticketPlatformInfo.getLimitIp().split("\\|");
					boolean flag = false;
					for (String ip : arr) {
						if(addr.indexOf(ip)!=-1){
							flag = true;
							break;
						}
					}
					if(!flag){
						throw new WebDataException("1-帐号绑定IP段与发票IP不符");
					}
				}else{
					if(addr.indexOf(ticketPlatformInfo.getLimitIp())==-1)throw new WebDataException("1-帐号绑定IP段与发票IP不符");
				}
			}else{
				//暂时不处理。没有设置的所有ip都可以发票
			}
		}catch(WebDataException e){
			logger.warn(e.getMessage(), e);
			throw new WebDataException(e.getMessage());
		}
	}
	    /**
		 * 保存新方案_接票
		 */
		public String createTicket() {
			Map map = Maps.newHashMap();
	       	JsonConfig jsonConfig = new JsonConfig();  
			
			TicketLogger ticketLogger =null;
			List<Map<String,String>> flag = Lists.newArrayList();
			ReqParamVisitor reqParamVisitor_flag;
			try {
				ticketLogger = ticketLog(null,null);
				checkTicket();
				E schemeDTO;
				Map<String,String> temp = null;
				Long time_all =System.currentTimeMillis();
				for (ReqParamVisitor reqParamVisitor : ticketList) {
					reqParamVisitor_flag = reqParamVisitor;
					System.out.println(reqParamVisitor.getOrderId()+"-----------------------开始");
					Long time =System.currentTimeMillis();
					try {
						if (ticketPlatformInfo == null)
							throw new WebDataException("1-找不到用户ID对应的用户.");
						if(ticketPlatformInfo.isLocked())throw new WebDataException("2-帐号被冻结");
						////验证用户
						buildReqParamVisitor(reqParamVisitor);
						System.out.println("-----------------------解析"+(System.currentTimeMillis()-time));
						time=System.currentTimeMillis();
						orderId = reqParamVisitor.getOrderId();
						schemeDTO = buildSchemeDTO();
						System.out.println("-----------------------构建"+(System.currentTimeMillis()-time));
						time=System.currentTimeMillis();
						////////////////////////////////////////
						synchronized (ticketPlatformInfo.getId()) {
							Boolean isRepeatOrder = ticketThenEntityManager.isRepeatOrder(schemeDTO.getOrderId(),ticketPlatformInfo.getId());
							System.out.println("-----------------------检查"+(System.currentTimeMillis()-time));
							time=System.currentTimeMillis();
							if(isRepeatOrder){
								throw new WebDataException("2-已有此订单");
							}
							checkTicketPlatformInfo(ticketPlatformInfo,schemeDTO);
							schemeDTO.setIsTicket(true);
							schemeDTO.setOutOrderNumber(reqParamVisitor.getOutOrderNumber());
							if(null!=this.platformInfo){
								schemeDTO.setPlatform(platformInfo);
							}
							final E schemeDTOTemp = schemeDTO;
							ExecutorUtils.exec(new Executable() {
								public void run() throws ExecuteException {
									scheme = getSchemeService().createTicketScheme(schemeDTOTemp);
								}
							}, 3);
							System.out.println("-----------------------方案"+(System.currentTimeMillis()-time));
							time=System.currentTimeMillis();
						}
						temp = Maps.newHashMap();
						temp.put("process", "0");
						temp.put("orderId", orderId+"");
						flag.add(temp);
						System.out.println("-----------------------完毕"+(System.currentTimeMillis()-time));
						time=System.currentTimeMillis();
					} catch (WebDataException e) {
						temp = Maps.newHashMap();
						temp.put("orderId", orderId + "");
						String process ="4";
						if(null!=e.getMessage()&&e.getMessage().indexOf("-")!=-1){
							String tempStr = e.getMessage().split("-")[0];
							try {process = ""+Integer.valueOf(tempStr);}catch (Exception ex) {}///如果报错报系统错误
							temp.put("errorMsg", e.getMessage());
						}else{
							logger.warn(e.getMessage(), e);
							temp.put("errorMsg", "系统内部错误");
						}
						temp.put("process", process);
						flag.add(temp);
						continue;
					} catch (ServiceException e) {
						temp = Maps.newHashMap();
						temp.put("orderId", orderId + "");
						String process ="4";
						if(null!=e.getMessage()&&e.getMessage().indexOf("-")!=-1){
							String tempStr = e.getMessage().split("-")[0];
							try {process = ""+Integer.valueOf(tempStr);}catch (Exception ex) {}///如果报错报系统错误
							temp.put("errorMsg", e.getMessage());
						}else{
							logger.warn(e.getMessage(), e);
							temp.put("errorMsg", "系统内部错误");
						}
						temp.put("process", process);
						flag.add(temp);
						continue;
					}catch (Exception e) {
						temp = Maps.newHashMap();
						temp.put("orderId", orderId + "");
						String process ="4";
						if(null!=e.getMessage()&&e.getMessage().indexOf("-")!=-1){
							String tempStr = e.getMessage().split("-")[0];
							try {process = ""+Integer.valueOf(tempStr);}catch (Exception ex) {}///如果报错报系统错误
							temp.put("errorMsg", e.getMessage());
						}else{
							logger.warn(e.getMessage(), e);
							temp.put("errorMsg", "系统内部错误");
						}
						temp.put("process", process);
						flag.add(temp);
						continue;
					}catch (Throwable e) {
						temp = Maps.newHashMap();
						temp.put("orderId", orderId + "");
						String process ="4";
						if(null!=e.getMessage()&&e.getMessage().indexOf("-")!=-1){
							String tempStr = e.getMessage().split("-")[0];
							try {process = ""+Integer.valueOf(tempStr);}catch (Exception ex) {}///如果报错报系统错误
							temp.put("errorMsg", e.getMessage());
						}else{
							logger.warn(e.getMessage(), e);
							temp.put("errorMsg", "系统内部错误");
						}
						temp.put("process", process);
						flag.add(temp);
						continue;
					}
				}
				System.out.println("-----------------------完毕"+(System.currentTimeMillis()-time_all));
				map.put("processId", "0");
				map.put("ticket", flag);
			} catch (WebDataException e) {
				String processId ="7";
				if(null!=e.getMessage()&&e.getMessage().indexOf("-")!=-1){
					String temp = e.getMessage().split("-")[0];
					try {processId = ""+Integer.valueOf(temp);}catch (Exception ex) {}///如果报错报系统错误
				}else{
					logger.warn(e.getMessage(), e);
				}
				map.put("processId", processId);
			 	
				map.put("errorMsg", e.getMessage());
			} catch (ServiceException e) {
				String processId ="7";
				if(null!=e.getMessage()&&e.getMessage().indexOf("-")!=-1){
					String temp = e.getMessage().split("-")[0];
					try {processId = ""+Integer.valueOf(temp);}catch (Exception ex) {}///如果报错报系统错误
				}else{
					logger.warn(e.getMessage(), e);
				}
				map.put("processId", processId);
			 	
				map.put("errorMsg", e.getMessage());
			} catch (Exception e) {
				String processId ="7";
				if(null!=e.getMessage()&&e.getMessage().indexOf("-")!=-1){
					String temp = e.getMessage().split("-")[0];
					try {processId = ""+Integer.valueOf(temp);}catch (Exception ex) {}///如果报错报系统错误
				}else{
					logger.warn(e.getMessage(), e);
				}	
				map.put("processId", processId);
			 	
				map.put("errorMsg", e.getMessage());
			}catch (Throwable e) {
				String processId ="7";
				if(null!=e.getMessage()&&e.getMessage().indexOf("-")!=-1){
					String temp = e.getMessage().split("-")[0];
					try {processId = ""+Integer.valueOf(temp);}catch (Exception ex) {}///如果报错报系统错误
				}else{
					logger.warn(e.getMessage(), e);
				}		
				map.put("processId", processId);
			 	
				map.put("errorMsg", e.getMessage());
			}
			renderJson(map,jsonConfig);
			ticketLogger = ticketLog(flag.toString(),ticketLogger);
			return null;
		}
	 public void renderJson(Map map,JsonConfig jsonConfig){
		 try{
	    	String jsonString =JSONObject.fromObject(map,jsonConfig).toString();
	    	Struts2Utils.render("application/json", jsonString);
		 }catch(Exception e){
			 e.printStackTrace();
			 logger.warn(e.getMessage());
		 }
	 }
	/**
	 * 验证用户是否符合发起方案的相关条件
	 * 
	 * @param user
	 *            用户对象
	 * @param schemeDTO
	 *            发起方案的数据传输对象
	 * @throws WebDataException 
	 */
	protected void checkTicketPlatformInfo(TicketPlatformInfo ticketPlatformInfo, E schemeDTO) throws WebDataException {
		if (ticketPlatformInfo.isLocked())
			throw new ServiceException("1-您的帐户已经被锁定,不能发起方案.如有什么疑问,请联系我们的客服人员.");

		BigDecimal totalCost = BigDecimal.ZERO;
		if (schemeDTO.getSponsorSubscriptionCost() != null && schemeDTO.getSponsorSubscriptionCost().doubleValue() > 0)
			totalCost = totalCost.add(schemeDTO.getSponsorSubscriptionCost());
		if (schemeDTO.getSponsorBaodiCost() != null && schemeDTO.getSponsorBaodiCost().doubleValue() > 0)
			totalCost = totalCost.add(schemeDTO.getSponsorBaodiCost());
		if (ticketPlatformInfo == null) {
			throw new ServiceException("1-用户[#" + ticketPlatformInfo.getId() + "]不存在.");
		} 
		BigDecimal allAccountBalance = ticketPlatformInfo.getRemainMoney();

		BigDecimal remainMoney = (allAccountBalance != null) ? allAccountBalance : BigDecimal.ZERO;
		if (remainMoney.doubleValue() < totalCost.doubleValue())
			throw new ServiceException("1-对不起,完成此次交易共需要[" + Constant.MONEY_FORMAT.format(totalCost) + "]元,您的账户余额只有["
					+ Constant.MONEY_FORMAT.format(remainMoney) + "]元,不够支付此次交易.");
		try {
			ticketPlatformInfo.subtractMoney(totalCost);
		} catch (DataException e) {
			throw new WebDataException("1-用户帐号异常");
		}
	}
	/**
	 * 验证用户是否符合发起方案的相关条件
	 * 
	 * @param user
	 *            用户对象
	 * @param schemeDTO
	 *            发起方案的数据传输对象
	 * @throws WebDataException 
	 */
	protected void checkUser(User user, E schemeDTO) throws WebDataException {
		if (user.isLocked())
			throw new ServiceException("1-您的帐户已经被锁定,不能发起方案.如有什么疑问,请联系我们的客服人员.");

		BigDecimal totalCost = BigDecimal.ZERO;
		if (schemeDTO.getSponsorSubscriptionCost() != null && schemeDTO.getSponsorSubscriptionCost().doubleValue() > 0)
			totalCost = totalCost.add(schemeDTO.getSponsorSubscriptionCost());
		if (schemeDTO.getSponsorBaodiCost() != null && schemeDTO.getSponsorBaodiCost().doubleValue() > 0)
			totalCost = totalCost.add(schemeDTO.getSponsorBaodiCost());
		if (user == null) {
			throw new ServiceException("1-用户[#" + user.getId() + "]不存在.");
		} 
		BigDecimal allAccountBalance = user.getRemainMoney();

		BigDecimal remainMoney = (allAccountBalance != null) ? allAccountBalance : BigDecimal.ZERO;
		if (remainMoney.doubleValue() < totalCost.doubleValue())
			throw new ServiceException("1-对不起,完成此次交易共需要[" + Constant.MONEY_FORMAT.format(totalCost) + "]元,您的账户余额只有["
					+ Constant.MONEY_FORMAT.format(remainMoney) + "]元,不够支付此次交易.");
		try {
			user.subtractMoney(totalCost);
		} catch (DataException e) {
			throw new WebDataException("1-用户帐号异常");
		}
	}
	private JcTicketOddsList getJczqOdds(JczqScheme jczqScheme){
		String awardString = jczqScheme.getPrintAwards();
		com.cai310.lottery.support.jczq.PlayType playType = jczqScheme.getPlayType();
		if(StringUtils.isNotBlank(awardString)){
			@SuppressWarnings("rawtypes")
			Map classMap = new HashMap();
			classMap.put("awardList", JcMatchOddsList.class);
			classMap.put("jcMatchOdds", JcMatchOdds.class);
			JcTicketOddsList jcTicketOddsList = JsonUtil.getObject4JsonString(awardString, JcTicketOddsList.class, classMap);
			Map<String,com.cai310.lottery.support.jczq.PlayType> playTypeMap  =  Maps.newHashMap();
			if(com.cai310.lottery.support.jczq.PlayType.MIX.equals(jczqScheme.getPlayType())){
				for (JczqMatchItem matchItem : jczqScheme.getCompoundContent().getItems()) {
					playTypeMap.put(matchItem.getMatchKey(), matchItem.getPlayType());
				}
			}
			if(null!=jcTicketOddsList){
				List<JcMatchOddsList> awardList = jcTicketOddsList.getAwardList();
				List<JcMatchOddsList> awardList_new = Lists.newArrayList();
				for (int i = 0; i < awardList.size(); i++) {
					JcMatchOddsList jcMatchOddsList = awardList.get(i);
					////修改赔率显示开始
					List<JcMatchOdds> JcMatchOddsList = jcMatchOddsList.getJcMatchOdds();
					List<JcMatchOdds> JcMatchOddsList_new = Lists.newArrayList();
					for (JcMatchOdds jcMatchOdds : JcMatchOddsList) {
						Map<String, Double> map = jcMatchOdds.getOptions();
						JcMatchOdds jcMatchOdds_new = new JcMatchOdds();
						jcMatchOdds_new.setMatchKey(jcMatchOdds.getMatchKey());
						Map<String, Double> map_new = Maps.newHashMap();
						for (String  key : map.keySet()) {
							if(com.cai310.lottery.support.jczq.PlayType.MIX.equals(jczqScheme.getPlayType()))playType = playTypeMap.get(jcMatchOdds.getMatchKey());
							
							Item item =playType.getItemByItemValue(key);
							Double value = Double.valueOf(""+map.get(key));
							map_new.put(item.toString(), value);
						}
						jcMatchOdds_new.setOptions(map_new);
						JcMatchOddsList_new.add(jcMatchOdds_new);
					}
					jcMatchOddsList.setJcMatchOdds(JcMatchOddsList_new);
					//修改赔率显示结束
					awardList_new.add(jcMatchOddsList);
				}
				jcTicketOddsList.setAwardList(awardList_new);
			}
//		    return JsonUtil.getJsonString4JavaPOJO(jcTicketOddsList);
			return jcTicketOddsList;
		}
		return null;
	}
	private JcTicketOddsList getJclqOdds(JclqScheme jclqScheme){
		String awardString = jclqScheme.getPrintAwards();
		com.cai310.lottery.support.jclq.PlayType playType = jclqScheme.getPlayType();
		if(StringUtils.isNotBlank(awardString)){
			@SuppressWarnings("rawtypes")
			Map classMap = new HashMap();
			classMap.put("awardList", JcMatchOddsList.class);
			classMap.put("jcMatchOdds", JcMatchOdds.class);
			JcTicketOddsList jcTicketOddsList = JsonUtil.getObject4JsonString(awardString, JcTicketOddsList.class, classMap);
			Map<String,com.cai310.lottery.support.jclq.PlayType> playTypeMap  =  Maps.newHashMap();
			if(com.cai310.lottery.support.jclq.PlayType.MIX.equals(jclqScheme.getPlayType())){
				for (JclqMatchItem matchItem : jclqScheme.getCompoundContent().getItems()) {
					playTypeMap.put(matchItem.getMatchKey(), matchItem.getPlayType());
				}
			}
			if(null!=jcTicketOddsList){
				List<JcMatchOddsList> awardList = jcTicketOddsList.getAwardList();
				List<JcMatchOddsList> awardList_new = Lists.newArrayList();
				for (int i = 0; i < awardList.size(); i++) {
					JcMatchOddsList jcMatchOddsList = awardList.get(i);
					////修改赔率显示开始
					List<JcMatchOdds> JcMatchOddsList = jcMatchOddsList.getJcMatchOdds();
					List<JcMatchOdds> JcMatchOddsList_new = Lists.newArrayList();
					for (JcMatchOdds jcMatchOdds : JcMatchOddsList) {
						Map<String, Double> map = jcMatchOdds.getOptions();
						JcMatchOdds jcMatchOdds_new = new JcMatchOdds();
						jcMatchOdds_new.setMatchKey(jcMatchOdds.getMatchKey());
						Map<String, Double> map_new = Maps.newHashMap();
						for (String  key : map.keySet()) {
							if(JclqConstant.REFERENCE_VALUE_KEY.equalsIgnoreCase(key)){
								Double value = Double.valueOf(""+map.get(key));
								map_new.put(JclqConstant.REFERENCE_VALUE_KEY, value);
							}else{
								if(com.cai310.lottery.support.jclq.PlayType.MIX.equals(jclqScheme.getPlayType()))playType = playTypeMap.get(jcMatchOdds.getMatchKey());
								Item item =playType.getItemByItemValue(key);
								Double value = Double.valueOf(""+map.get(key));
								map_new.put(item.toString(), value);
							}
						}
						jcMatchOdds_new.setOptions(map_new);
						JcMatchOddsList_new.add(jcMatchOdds_new);
					}
					jcMatchOddsList.setJcMatchOdds(JcMatchOddsList_new);
					//修改赔率显示结束
					awardList_new.add(jcMatchOddsList);
				}
				jcTicketOddsList.setAwardList(awardList_new);
			}
		    return jcTicketOddsList;
		}
		return null;
	}
	public static void main(String[] args) {
		System.out.print(ItemBF.DRAW00.toString());
	}
	private String getJczqTicketCode(JczqScheme jczqScheme){
		String awardString = jczqScheme.getPrintAwards();
		if(StringUtils.isNotBlank(awardString)){
			@SuppressWarnings("rawtypes")
			Map classMap = new HashMap();
			classMap.put("awardList", JcMatchOddsList.class);
			classMap.put("jcMatchOdds", JcMatchOdds.class);
			JcTicketOddsList jcTicketOddsList = JsonUtil.getObject4JsonString(awardString, JcTicketOddsList.class, classMap);
			List<JcMatchOddsList> jcMatchOdds = jcTicketOddsList.getAwardList();
			if(jcMatchOdds.size()==1){
				JcMatchOddsList jcMatchOddsList = jcMatchOdds.get(0);
				return jcMatchOddsList.getTicketCode();
			}
		}
		return null;
	}
	private String getJclqTicketCode(JclqScheme jclqScheme){
		String awardString = jclqScheme.getPrintAwards();
		if(StringUtils.isNotBlank(awardString)){
			@SuppressWarnings("rawtypes")
			Map classMap = new HashMap();
			classMap.put("awardList", JcMatchOddsList.class);
			classMap.put("jcMatchOdds", JcMatchOdds.class);
			JcTicketOddsList jcTicketOddsList = JsonUtil.getObject4JsonString(awardString, JcTicketOddsList.class, classMap);
			List<JcMatchOddsList> jcMatchOdds = jcTicketOddsList.getAwardList();
			if(jcMatchOdds.size()==1){
				JcMatchOddsList jcMatchOddsList = jcMatchOdds.get(0);
				return jcMatchOddsList.getTicketCode();
			}
		}
		return null;
	}
	public String matchTicket() {
		Map map = Maps.newHashMap();
       	JsonConfig jsonConfig = new JsonConfig();  
		ReqParamVisitor reqParamVisitor = new ReqParamVisitor();
		try {
			checkTicket();
			if(StringUtils.isNotBlank(wParam)){
				 reqParamVisitor.visit(wParam);
			}
			this.buildReqParamVisitor(reqParamVisitor);
			//彩钟平台，玩法，期号必须不为空。竞彩buildReqParamVisitor已经有赋予期号
			if(null==this.getwLotteryId()){
				throw new WebDataException("10-彩种为空");
			}
			if(null==this.getPeriodNumber()){
				throw new WebDataException("11-期号为空");
			}
			List matchList = this.buildMatchList();
			map.put("processId", "0");
			map.put("match", matchList);
		} catch (WebDataException e) {
			String processId ="7";
			if(null!=e.getMessage()&&e.getMessage().indexOf("-")!=-1){
				String temp = e.getMessage().split("-")[0];
				try {processId = ""+Integer.valueOf(temp);}catch (Exception ex) {}///如果报错报系统错误
				Struts2Utils.setAttribute("errorMsg", e.getMessage());
			}else{
				Struts2Utils.setAttribute("errorMsg", "系统内部错误");
			}		
			map.put("processId", processId);
		 	
			map.put("errorMsg", e.getMessage());
		} catch (ServiceException e) {
			logger.warn(e.getMessage(), e);
			String processId ="7";
			if(null!=e.getMessage()&&e.getMessage().indexOf("-")!=-1){
				String temp = e.getMessage().split("-")[0];
				try {processId = ""+Integer.valueOf(temp);}catch (Exception ex) {}///如果报错报系统错误
				Struts2Utils.setAttribute("errorMsg", e.getMessage());
			}else{
				Struts2Utils.setAttribute("errorMsg", "系统内部错误");
			}		
			map.put("processId", processId);
		 	
			map.put("errorMsg", e.getMessage());
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			String processId ="7";
			if(null!=e.getMessage()&&e.getMessage().indexOf("-")!=-1){
				String temp = e.getMessage().split("-")[0];
				try {processId = ""+Integer.valueOf(temp);}catch (Exception ex) {}///如果报错报系统错误
				Struts2Utils.setAttribute("errorMsg", e.getMessage());
			}else{
				Struts2Utils.setAttribute("errorMsg", "系统内部错误");
			}		
			map.put("processId", processId);
		 	
			map.put("errorMsg", e.getMessage());
		}
		renderJson(map,jsonConfig);
		return null;
	}
	
	public String resultTicket() {
		Map map = Maps.newHashMap();
		ReqParamVisitor reqParamVisitor = new ReqParamVisitor();
		JsonConfig jsonConfig = new JsonConfig();  
		try {
			checkTicket();
			if(StringUtils.isNotBlank(wParam)){
				Map<String, Object> wParam_map = JsonUtil.getMap4Json(wParam);
				if (null != wParam_map) {
					String wLotteryId = null == wParam_map.get("wLotteryId") ? null : String
							.valueOf(wParam_map.get("wLotteryId"));
					if(StringUtils.isNotBlank(wLotteryId)){
						this.setwLotteryId(wLotteryId);
					}
					String periodNumber = null == wParam_map.get("periodNumber") ? null : String
							.valueOf(wParam_map.get("periodNumber"));
					if(StringUtils.isNotBlank(periodNumber)){
						this.setPeriodNumber(periodNumber);
					}
					if(null==this.getwLotteryId()){
						throw new WebDataException("10-彩种为空");
					}
					if(null==this.getPeriodNumber()){
						throw new WebDataException("11-期号为空");
					}
					List matchList = this.buildMatchResultList();
					map.put("processId", "0");
					map.put("match", matchList);
				}
			}
		} catch (WebDataException e) {
			String processId ="7";
			if(null!=e.getMessage()&&e.getMessage().indexOf("-")!=-1){
				String temp = e.getMessage().split("-")[0];
				try {processId = ""+Integer.valueOf(temp);}catch (Exception ex) {}///如果报错报系统错误
				Struts2Utils.setAttribute("errorMsg", e.getMessage());
			}else{
				Struts2Utils.setAttribute("errorMsg", "系统内部错误");
			}		
			Struts2Utils.setAttribute("processId", processId);
		} catch (ServiceException e) {
			logger.warn(e.getMessage(), e);
			String processId ="7";
			if(null!=e.getMessage()&&e.getMessage().indexOf("-")!=-1){
				String temp = e.getMessage().split("-")[0];
				try {processId = ""+Integer.valueOf(temp);}catch (Exception ex) {}///如果报错报系统错误
				Struts2Utils.setAttribute("errorMsg", e.getMessage());
			}else{
				Struts2Utils.setAttribute("errorMsg", "系统内部错误");
			}		
			Struts2Utils.setAttribute("processId", processId);
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			String processId ="7";
			if(null!=e.getMessage()&&e.getMessage().indexOf("-")!=-1){
				String temp = e.getMessage().split("-")[0];
				try {processId = ""+Integer.valueOf(temp);}catch (Exception ex) {}///如果报错报系统错误
				Struts2Utils.setAttribute("errorMsg", e.getMessage());
			}else{
				Struts2Utils.setAttribute("errorMsg", "系统内部错误");
			}		
			Struts2Utils.setAttribute("processId", processId);
		}
		renderJson(map,jsonConfig);
		return null;
	}
	protected TicketLogger ticketLog(String respContent,TicketLogger ticketLogger){
    	try{
	    	if(null!=ticketLogger&&null!=ticketLogger.getId()){
	        	ticketLogger.setRespContent(respContent);
	        	ticketLogger.setRespTime(new Date());
	        	return ticketEntityManager.saveTicketLogger(ticketLogger);
	    	}else{
	    		this.wAction=Struts2Utils.getParameter("wAction");
	    		this.wLotteryId=Struts2Utils.getParameter("wLotteryId");
	    		this.wParam=Struts2Utils.getParameter("wParam");
	    		this.wSign=Struts2Utils.getParameter("wSign");
	    		this.wAgent=Struts2Utils.getParameter("wAgent");
	    		
	        	ticketLogger = new TicketLogger();
	        	ticketLogger.setCreateTime(new Date());
	        	StringBuffer sb = new StringBuffer();
	        	sb.append("wAction="+this.wAction).append("wLotteryId="+wLotteryId).append("wParam="+wParam).append("wSign="+wSign).append("wAgent="+wAgent).append("ip="+Struts2Utils.getRemoteAddr());
	        	ticketLogger.setReqContent(sb.toString());
	        	if(StringUtils.isNotBlank(wAgent)){
	        		try{
	        			ticketLogger.setUserId(Long.valueOf(wAgent));
	        		}catch(Exception e){
	        			logger.warn(e.getMessage());
	        		}
	        	}
	        	if(StringUtils.isNotBlank(wLotteryId)){
	        		try{
	        			Lottery lottery = Lottery.values()[Integer.valueOf(wLotteryId)];
	        			ticketLogger.setLotteryType(lottery);
	        		}catch(Exception e){
	        			logger.warn(e.getMessage());
	        		}
	        	}
	        	return ticketEntityManager.saveTicketLogger(ticketLogger);
	    	}
    	}catch(Exception e){
			logger.warn(e.getMessage());
			return null;
		}
    	
    }
	/**
	 * 
	 */
	public String query() {
		Map<String, Object> map = Maps.newHashMap();
       	JsonConfig jsonConfig = new JsonConfig();  
       	
		TicketLogger ticketLogger =null;
		StringBuffer sb = new StringBuffer();
		List<Map<String,Object>> flag = Lists.newArrayList();
		try {
			ticketLogger = ticketLog(null,null);
			checkTicket();
			String orderId =null;
			if(StringUtils.isNotBlank(wParam)){
				Map<String, Object> wParamMap = JsonUtil.getMap4Json(wParam);
				if(null!=wParamMap){
					orderId = String.valueOf(wParamMap.get("orderId"));
				}
			}
			String orderIdTemp;
			List<T> schemeList;
			if(StringUtils.isBlank(orderId))
				throw new WebDataException("5-orderId参数解析错误");
			String[] arr = orderId.split(",");
			List<String> orderList = Lists.newArrayList();
			for (String order : arr) {
				orderList.add(order.trim());
			}
			Map<String,Object> temp = null;
			for (String id : orderList) {
					Scheme scheme = null;
					try{
						user = this.userManager.getUser(ticketPlatformInfo.getUserId());
						scheme = getSchemeEntityManager(this.getLottery()).getTicketSchemeBy(id,user.getId());
						if(null==scheme){
								temp = Maps.newHashMap();
								temp.put("process", "4");
								temp.put("orderId", ""+id);
								temp.put("errorMsg", "无此订单");
								flag.add(temp);
								continue;
						}
						temp = Maps.newHashMap();
//						if(SchemeState.REFUNDMENT.equals(scheme.getState())||SchemeState.CANCEL.equals(scheme.getState())||SchemeState.UNFULL.equals(scheme.getState())){
//							temp.put("process", "" +TicketSchemeState.FAILD.ordinal() );
//						}else if(scheme.getState().equals(SchemeState.FULL)){
//							if(scheme.getSchemePrintState().equals(SchemePrintState.SUCCESS)){
//								temp.put("process", "" +TicketSchemeState.SUCCESS.ordinal() );
//							}else if(scheme.getSchemePrintState().equals(SchemePrintState.PRINT)||scheme.getSchemePrintState().equals(SchemePrintState.UNPRINT)){
//								temp.put("process", "" +TicketSchemeState.FULL.ordinal() );
//							}else if(scheme.getSchemePrintState().equals(SchemePrintState.FAILED)){
//								temp.put("process", "" +TicketSchemeState.FAILD.ordinal() );
//							}
//						}else if(scheme.getState().equals(SchemeState.SUCCESS)){
//							temp.put("process", "" +TicketSchemeState.SUCCESS.ordinal() );
//						}else{
//							temp.put("process", "" +TicketSchemeState.FULL.ordinal() );
//						}
						if (scheme.getLotteryType().getCategory().equals(LotteryCategory.FREQUENT)) {
							temp.put("process",
									"" + TicketSchemeState.SUCCESS.ordinal());
						}else{
							if (SchemeState.REFUNDMENT.equals(scheme.getState())
									|| SchemeState.CANCEL.equals(scheme.getState())
									|| SchemeState.UNFULL.equals(scheme.getState())) {
								temp.put("process",
										"" + TicketSchemeState.FAILD.ordinal());
							} else if (scheme.getState().equals(SchemeState.FULL)) {
								if (scheme.getSchemePrintState().equals(
										SchemePrintState.SUCCESS)) {
									temp.put("process",
											"" + TicketSchemeState.SUCCESS.ordinal());
								} else if (scheme.getSchemePrintState().equals(
										SchemePrintState.PRINT)
										|| scheme.getSchemePrintState().equals(
												SchemePrintState.UNPRINT)) {
									temp.put("process",
											"" + TicketSchemeState.FULL.ordinal());
								} else if (scheme.getSchemePrintState().equals(
										SchemePrintState.FAILED)) {
									temp.put("process",
											"" + TicketSchemeState.FAILD.ordinal());
								}
							} else if (scheme.getState().equals(SchemeState.SUCCESS)) {
								temp.put("process",
										"" + TicketSchemeState.SUCCESS.ordinal());
							} else {
								temp.put("process",
										"" + TicketSchemeState.FULL.ordinal());
							}
						}
						temp.put("orderId", ""+id);
						if(scheme instanceof JclqScheme){
							JclqScheme jclqScheme = (JclqScheme) scheme;
							temp.put("awards", this.getJclqOdds(jclqScheme));
							temp.put("ticketCode", getJclqTicketCode(jclqScheme));
						}else if(scheme instanceof JczqScheme){
							JczqScheme jczqScheme = (JczqScheme) scheme;
							temp.put("awards", this.getJczqOdds(jczqScheme));
							temp.put("ticketCode", getJczqTicketCode(jczqScheme));
						}else {
							/*
							String ticketCode = getKenoTicketCode(scheme.getId());
							if (ticketCode == null) {
								ticketCode = "";
							}
							*/
							temp.put("awards", null);
							temp.put("ticketCode", "");
						}
						temp.put("operateTime", com.cai310.utils.DateUtil.dateToStr(scheme.getCreateTime()));
						flag.add(temp);
					}catch(Exception e){
						temp = Maps.newHashMap();
						temp.put("process", "6");
						temp.put("orderId", ""+id);
						temp.put("errorMsg", "订单错误");
						flag.add(temp);
						logger.warn(e.getMessage(), e);
						continue;
					}
			}
			map.put("processId", "0");
			
			map.put("ticket", flag);
			
			for (Map<String,Object> order : flag) {
				sb.append("orderId="+order.get("orderId")+";");
				sb.append("process="+order.get("process")+";");
			}
		} catch (WebDataException e) {
			String processId ="7";
			if(null!=e.getMessage()&&e.getMessage().indexOf("-")!=-1){
				String temp = e.getMessage().split("-")[0];
				try {processId = ""+Integer.valueOf(temp);}catch (Exception ex) {}///如果报错报系统错误
				Struts2Utils.setAttribute("errorMsg", e.getMessage());
			}else{
				Struts2Utils.setAttribute("errorMsg", "系统内部错误");
			}		
			map.put("processId", processId);
		 	
			map.put("errorMsg", e.getMessage());
		} catch (ServiceException e) {
			logger.warn(e.getMessage(), e);
			String processId ="7";
			if(null!=e.getMessage()&&e.getMessage().indexOf("-")!=-1){
				String temp = e.getMessage().split("-")[0];
				try {processId = ""+Integer.valueOf(temp);}catch (Exception ex) {}///如果报错报系统错误
				Struts2Utils.setAttribute("errorMsg", e.getMessage());
			}else{
				Struts2Utils.setAttribute("errorMsg", "系统内部错误");
			}		
			map.put("processId", processId);
		 	
			map.put("errorMsg", e.getMessage());
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			String processId ="7";
			if(null!=e.getMessage()&&e.getMessage().indexOf("-")!=-1){
				String temp = e.getMessage().split("-")[0];
				try {processId = ""+Integer.valueOf(temp);}catch (Exception ex) {}///如果报错报系统错误
				Struts2Utils.setAttribute("errorMsg", e.getMessage());
			}else{
				Struts2Utils.setAttribute("errorMsg", "系统内部错误");
			}		
			map.put("processId", processId);
		 	
			map.put("errorMsg", e.getMessage());
		}
		renderJson(map,jsonConfig);
		ticketLogger = ticketLog(flag.toString(),ticketLogger);
		return null;
	}
	
	private String getKenoTicketCode(Long schemeId) {
		XDetachedCriteria criteria = new XDetachedCriteria(Ticket.class, "t");
		criteria.add(Restrictions.like("schemeNumber",
				"%" + schemeId.toString()));
		List<Ticket> tickets = queryService.findByDetachedCriteria(criteria);
		String ticketCode = null;
		for (Ticket ticket : tickets) {
			String ticketStr = ticket.getSchemeNumber();
			ticketStr = ticketStr.substring(ticketStr.length() - 10);
			if (schemeId.equals(Long.valueOf(ticketStr))) {
				ticketCode = ticket.getRemoteTicketId();
			}
		}
		if (ticketCode == null) {
			ticketCode = "";
		} 
		return ticketCode;
	}
	
	/**
	 * 
	 */
	public String resultList() {
		Map map = Maps.newHashMap();
       	JsonConfig jsonConfig = new JsonConfig();  
		StringBuffer sb = new StringBuffer();
		try {
			check();
			Lottery lotteryType = null;
			if (StringUtils.isNotBlank(wLotteryId)) {
				try{
					 lotteryType = Lottery.values()[Integer.valueOf(wLotteryId.trim())];
				} catch (Exception e) {
					throw new WebDataException("12-彩种错误");
				}
				if(null==lotteryType){
					throw new WebDataException("12-彩种错误");
				}
			}
			if (StringUtils.isBlank(start)){
				throw new WebDataException("9-起始标志为空");
			}
			try{
				Integer.valueOf(start);
			} catch (Exception e) {
				throw new WebDataException("9-起始标志错误");
			}
			if (StringUtils.isBlank(count)){
				throw new WebDataException("10-条数标志为空");
			}
			try{
				Integer.valueOf(count);
			} catch (Exception e) {
				throw new WebDataException("10-条数标志错误");
			}
			PeriodDataEntityManager periodDataEntityManager = this.getPeriodDataEntityManager(lotteryType);
			StringBuffer buf = new StringBuffer();
			buf.append("Select p as period,d as periodData ");
			buf.append("From Period p," + periodDataEntityManager.PeriodDataClass().getName() + " d ");
			buf.append("Where p.drawed=:drawed And p.id = d.periodId ");
			buf.append("Order by p.periodNumber desc");
			Map<String, Object> pmap = new HashMap<String, Object>();
			pmap.put("drawed", true);
			List list = queryService.findByHql(buf.toString(), pmap, Integer.valueOf(start), Integer.valueOf(count), Criteria.ALIAS_TO_ENTITY_MAP);
			List<PeriodDataDTO> resultList = Lists.newArrayList();
			for (Object object : list) {
				Period period = (Period) ((Map<String, Object>)object).get("period");
				PeriodData periodData = (PeriodData) ((Map<String, Object>)object).get("periodData");

				if(null!=periodData&&null!=period&&StringUtils.isNotBlank(periodData.getResult())){
					PeriodDataDTO periodDataDTO = new PeriodDataDTO();
					periodDataDTO.setLotteryType(period.getLotteryType());
					periodDataDTO.setPeriodId(period.getId());
					periodDataDTO.setPeriodNumber(period.getPeriodNumber());
					periodDataDTO.setPeriodTitle(period.getLotteryType().getTitle());
					periodDataDTO.setPrizeTime(DateUtil.dateToStr(period.getPrizeTime(),"yyyy-MM-dd HH:mm:ss"));
					periodDataDTO.setResult(periodData.getResult());
					resultList.add(periodDataDTO);
				}
				
			}
			map.put("processId", "0");
			
			map.put("resultList", resultList);
		} catch (WebDataException e) {
			String processId ="7";
			if(null!=e.getMessage()&&e.getMessage().indexOf("-")!=-1){
				String temp = e.getMessage().split("-")[0];
				try {processId = ""+Integer.valueOf(temp);}catch (Exception ex) {}///如果报错报系统错误
				Struts2Utils.setAttribute("errorMsg", e.getMessage());
			}else{
				Struts2Utils.setAttribute("errorMsg", "系统内部错误");
			}		
			map.put("processId", processId);
		 	
			map.put("errorMsg", e.getMessage());
		} catch (ServiceException e) {
			logger.warn(e.getMessage(), e);
			String processId ="7";
			if(null!=e.getMessage()&&e.getMessage().indexOf("-")!=-1){
				String temp = e.getMessage().split("-")[0];
				try {processId = ""+Integer.valueOf(temp);}catch (Exception ex) {}///如果报错报系统错误
				Struts2Utils.setAttribute("errorMsg", e.getMessage());
			}else{
				Struts2Utils.setAttribute("errorMsg", "系统内部错误");
			}		
			map.put("processId", processId);
		 	
			map.put("errorMsg", e.getMessage());
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			String processId ="7";
			if(null!=e.getMessage()&&e.getMessage().indexOf("-")!=-1){
				String temp = e.getMessage().split("-")[0];
				try {processId = ""+Integer.valueOf(temp);}catch (Exception ex) {}///如果报错报系统错误
				Struts2Utils.setAttribute("errorMsg", e.getMessage());
			}else{
				Struts2Utils.setAttribute("errorMsg", "系统内部错误");
			}		
			map.put("processId", processId);
		 	
			map.put("errorMsg", e.getMessage());
		}
		renderJson(map,jsonConfig);
		return null;
	}
	
	/**
	 * 
	 */
	protected  abstract SchemeInfoDTO getSchemeMatchDTO(Scheme scheme)throws WebDataException;
	public String schemeInfo() {
		Map map = Maps.newHashMap();
       	JsonConfig jsonConfig = new JsonConfig();  
		StringBuffer sb = new StringBuffer();
		List<Map<String,String>> flag = Lists.newArrayList();
		try {
			check();
			String id =null;
			if(StringUtils.isNotBlank(wParam)){
				Map<String, Object> wParamMap = JsonUtil.getMap4Json(wParam);
				if(null!=wParamMap){
					id = String.valueOf(wParamMap.get("id"));
				}
			}
			if(StringUtils.isBlank(id))
				throw new WebDataException("5-orderId参数解析错误");
			Lottery lottery = Lottery.values()[Integer.valueOf(wLotteryId)];
			Scheme scheme = getSchemeEntityManager(lottery).getScheme(Long.valueOf(id));
			scheme = (T)scheme;
			scheme.setSubscriptions(null);
			if(scheme.getLotteryType().getCategory().equals(LotteryCategory.JC)||scheme.getLotteryType().getCategory().equals(LotteryCategory.DCZC)){
				//返回开奖详情
				String content =JSONObject.fromObject(this.getSchemeMatchDTO(scheme)).toString();
				scheme.setContent(content);
			}else if(scheme.getLotteryType().getCategory().equals(LotteryCategory.ZC)){
				if (scheme.getMode() == SalesMode.COMPOUND) {
					String content =JSONObject.fromObject(this.getSchemeMatchDTO(scheme)).toString();
					scheme.setContent(content);
				}
			}else{
				PeriodData periodData = this.getPeriodDataEntityManager(scheme.getLotteryType()).getPeriodData(scheme.getPeriodId());
				if(null!=periodData&&StringUtils.isNotBlank(periodData.getResult())){
					scheme.setRemark(periodData.getResult());
					map.put("result", periodData.getResult());
				}
				if(null==scheme.getPrizeSendTime()){
					Period period = this.periodManager.getPeriod(scheme.getPeriodId());
					if(null!=period&&null!=period.getPrizeTime()){
						scheme.setPrizeSendTime(period.getPrizeTime());
					}
				}
			}
//			if(lottery.equals(Lottery.SSQ)){
//				scheme = (com.cai310.lottery.entity.lottery.ssq.SsqScheme) scheme;
//			}else if(lottery.equals(Lottery.JCLQ)){
//				scheme = (JclqScheme) scheme;
//			}else if(lottery.equals(Lottery.JCZQ)){
//				scheme = (JczqScheme) scheme;
//			}else if(lottery.equals(Lottery.SSC)){
//				scheme = (SscScheme) scheme;
//			}else if(lottery.equals(Lottery.SDEL11TO5)){
//				scheme = (SdEl11to5Scheme) scheme;
//			}else if(lottery.equals(Lottery.DLT)){
//				scheme = (DltScheme) scheme;
//			}else if(lottery.equals(Lottery.WELFARE3D)){
//				scheme = (Welfare3dScheme) scheme;
//			}else if(lottery.equals(Lottery.PL)){
//				scheme = (PlScheme) scheme;
//			}else if(lottery.equals(Lottery.TC22TO5)){
//				scheme = (Tc22to5Scheme) scheme;
//			}else if(lottery.equals(Lottery.SEVEN)){
//				scheme = (SevenScheme) scheme;
//			}else if(lottery.equals(Lottery.SEVENSTAR)){
//				scheme = (SevenstarScheme) scheme;
//			}
			map.put("processId", "0");
			
			map.put("scheme", scheme);
			jsonConfig.setExcludes(new String[] {"selectedMatchKeys","contentText","contentString","singleContent","passTypes","extraPrizeMsg","passMode","selectedLineIds","wonCombinationMap","hasCancelMatch","betType","playType","createTime","prizeSendTime","userBetCost","uploaded","updateWon","updatePrize","unSuccessWon","unFullState","transactionId","totalProgressRate","successWon","together","spareBaodiCost","lotteryPlayType","keepTop","keepBottom","compoundMode","compoundContent","baodiProgressRate","subscriptions","ticketSchemeState","compoundContentText","compoundContentTextForList","commitTime","uploadTime","orderPriorityText","lastModifyTime","wonStatusHtml"});  
			
		} catch (WebDataException e) {
			String processId ="7";
			if(null!=e.getMessage()&&e.getMessage().indexOf("-")!=-1){
				String temp = e.getMessage().split("-")[0];
				try {processId = ""+Integer.valueOf(temp);}catch (Exception ex) {}///如果报错报系统错误
				Struts2Utils.setAttribute("errorMsg", e.getMessage());
			}else{
				Struts2Utils.setAttribute("errorMsg", "系统内部错误");
			}		
			map.put("processId", processId);
		 	
			map.put("errorMsg", e.getMessage());
		} catch (ServiceException e) {
			logger.warn(e.getMessage(), e);
			String processId ="7";
			if(null!=e.getMessage()&&e.getMessage().indexOf("-")!=-1){
				String temp = e.getMessage().split("-")[0];
				try {processId = ""+Integer.valueOf(temp);}catch (Exception ex) {}///如果报错报系统错误
				Struts2Utils.setAttribute("errorMsg", e.getMessage());
			}else{
				Struts2Utils.setAttribute("errorMsg", "系统内部错误");
			}		
			map.put("processId", processId);
		 	
			map.put("errorMsg", e.getMessage());
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			String processId ="7";
			if(null!=e.getMessage()&&e.getMessage().indexOf("-")!=-1){
				String temp = e.getMessage().split("-")[0];
				try {processId = ""+Integer.valueOf(temp);}catch (Exception ex) {}///如果报错报系统错误
				Struts2Utils.setAttribute("errorMsg", e.getMessage());
			}else{
				Struts2Utils.setAttribute("errorMsg", "系统内部错误");
			}		
			map.put("processId", processId);
		 	
			map.put("errorMsg", e.getMessage());
		}
		renderJson(map,jsonConfig);
		return null;
	}

	
	/**
	 * 
	 */
	
	/**
	* 
	*/
	public String resultInfo() {
			Map map = Maps.newHashMap();
	       	JsonConfig jsonConfig = new JsonConfig();  
			StringBuffer sb = new StringBuffer();
			try {
				check();
				if(StringUtils.isNotBlank(this.wParam)){
					Map<String, Object> wParam_map = JsonUtil.getMap4Json(wParam);
					if(null!=wParam_map){
						String periodId = null==wParam_map.get("periodId")?null:String.valueOf(wParam_map.get("periodId"));
						if(null!=periodId){
							try{
								Long.valueOf(periodId);
							} catch (Exception e) {
								throw new WebDataException("7-期号Id错误");
							}
							Period period = this.periodManager.getPeriod(Long.valueOf(periodId));
							if(null==period){
								throw new WebDataException("7-期号Id错误");
							}
							PeriodData periodDataCom = this.getPeriodDataEntityManager(this.getLottery()).getPeriodData(Long.valueOf(periodId));
							if(null==periodDataCom){
								throw new WebDataException("8-期数据为空");
							}
							PeriodDataInfoDTO periodDataInfoDTO = new PeriodDataInfoDTO();
							periodDataInfoDTO.setLotteryType(period.getLotteryType());
							periodDataInfoDTO.setPeriodId(period.getId());
							periodDataInfoDTO.setResult(periodDataCom.getResult());
							periodDataInfoDTO.setPeriodNumber(period.getPeriodNumber());
							periodDataInfoDTO.setPeriodTitle(period.getLotteryType().getTitle());
							periodDataInfoDTO.setPrizeTime(DateUtil.dateToStr(period.getPrizeTime(),"yyyy-MM-dd HH:mm:ss"));
							periodDataInfoDTO.setEndCashTime(DateUtil.dateToStr(DateUtils.addDays(period.getPrizeTime(), 59),"yyyy-MM-dd"));
							if(period.getLotteryType().equals(Lottery.SSQ)){
								SsqPeriodData periodData = (SsqPeriodData)periodDataCom;
								periodDataInfoDTO.setPrizePool(periodData.getPrizePool());
								periodDataInfoDTO.setTotalSales(periodData.getTotalSales());
								periodDataInfoDTO.setWinUnit(JsonUtil.getJSONArray4Json("["+JsonUtil.getJsonString4JavaPOJO(periodData.getWinUnit())+"]"));
								periodDataInfoDTO.setPrize(JsonUtil.getJSONArray4Json("["+JsonUtil.getJsonString4JavaPOJO(periodData.getPrize())+"]"));
							}
							if(period.getLotteryType().equals(Lottery.DLT)){
								DltPeriodData periodData = (DltPeriodData)periodDataCom;
								periodDataInfoDTO.setPrizePool(periodData.getPrizePool());
								periodDataInfoDTO.setTotalSales(periodData.getTotalSales());
								periodDataInfoDTO.setWinUnit(JsonUtil.getJSONArray4Json("["+JsonUtil.getJsonString4JavaPOJO(periodData.getWinUnit())+"]"));
								periodDataInfoDTO.setPrize(JsonUtil.getJSONArray4Json("["+JsonUtil.getJsonString4JavaPOJO(periodData.getPrize())+"]"));
							}
							if(period.getLotteryType().equals(Lottery.SEVEN)){
								SevenPeriodData periodData = (SevenPeriodData)periodDataCom;
								periodDataInfoDTO.setPrizePool(periodData.getPrizePool());
								periodDataInfoDTO.setTotalSales(periodData.getTotalSales());
								periodDataInfoDTO.setWinUnit(JsonUtil.getJSONArray4Json("["+JsonUtil.getJsonString4JavaPOJO(periodData.getWinUnit())+"]"));
								periodDataInfoDTO.setPrize(JsonUtil.getJSONArray4Json("["+JsonUtil.getJsonString4JavaPOJO(periodData.getPrize())+"]"));
							}
							if(period.getLotteryType().equals(Lottery.SEVENSTAR)){
								SevenstarPeriodData periodData = (SevenstarPeriodData)periodDataCom;
								periodDataInfoDTO.setPrizePool(periodData.getPrizePool());
								periodDataInfoDTO.setTotalSales(periodData.getTotalSales());
								periodDataInfoDTO.setWinUnit(JsonUtil.getJSONArray4Json("["+JsonUtil.getJsonString4JavaPOJO(periodData.getWinUnit())+"]"));
								periodDataInfoDTO.setPrize(JsonUtil.getJSONArray4Json("["+JsonUtil.getJsonString4JavaPOJO(periodData.getPrize())+"]"));
							}
							if(period.getLotteryType().equals(Lottery.TC22TO5)){
								Tc22to5PeriodData periodData = (Tc22to5PeriodData)periodDataCom;
								periodDataInfoDTO.setPrizePool(periodData.getPrizePool());
								periodDataInfoDTO.setTotalSales(periodData.getTotalSales());
								periodDataInfoDTO.setWinUnit(JsonUtil.getJSONArray4Json("["+JsonUtil.getJsonString4JavaPOJO(periodData.getWinUnit())+"]"));
								periodDataInfoDTO.setPrize(JsonUtil.getJSONArray4Json("["+JsonUtil.getJsonString4JavaPOJO(periodData.getPrize())+"]"));
							}
							if(period.getLotteryType().equals(Lottery.PL)){
								PlPeriodData periodData = (PlPeriodData)periodDataCom;
								periodDataInfoDTO.setPrizePool(null);
								periodDataInfoDTO.setTotalSales(periodData.getTotalSales());
								periodDataInfoDTO.setWinUnit(JsonUtil.getJSONArray4Json("["+JsonUtil.getJsonString4JavaPOJO(periodData.getWinUnit())+"]"));
								periodDataInfoDTO.setPrize(JsonUtil.getJSONArray4Json("["+JsonUtil.getJsonString4JavaPOJO(periodData.getPrize())+"]"));
							}
							if(period.getLotteryType().equals(Lottery.WELFARE3D)){
								Welfare3dPeriodData periodData = (Welfare3dPeriodData)periodDataCom;
								periodDataInfoDTO.setPrizePool(null);
								periodDataInfoDTO.setTotalSales(periodData.getTotalSales());
								periodDataInfoDTO.setWinUnit(JsonUtil.getJSONArray4Json("["+JsonUtil.getJsonString4JavaPOJO(periodData.getWinUnit())+"]"));
								periodDataInfoDTO.setPrize(JsonUtil.getJSONArray4Json("["+JsonUtil.getJsonString4JavaPOJO(periodData.getPrize())+"]"));
							}
							if(period.getLotteryType().equals(Lottery.SFZC)){
								SfzcPeriodData periodData = (SfzcPeriodData)periodDataCom;
								periodDataInfoDTO.setPrizePool(periodData.getPrizePool());
								periodDataInfoDTO.setTotalSales(periodData.getTotalSales());
								Map<String,Object> unit = Maps.newHashMap();
								Map<String,Object> prize = Maps.newHashMap();
								unit.put("firstWinUnits_14", periodData.getFirstWinUnits_14());
								prize.put("firstPrize_14", periodData.getFirstPrize_14());
								periodDataInfoDTO.setWinUnit(JsonUtil.getJSONArray4Json("["+JsonUtil.getJsonString4JavaPOJO(unit)+"]"));
								periodDataInfoDTO.setPrize(JsonUtil.getJSONArray4Json("["+JsonUtil.getJsonString4JavaPOJO(prize)+"]"));
							}
							map.put("periodData", periodDataInfoDTO);
							jsonConfig.setExcludes(new String[] {"winItemList","won","generalAdditional","maxHit"});  
							
						}
					}
				}
				map.put("processId", "0");
			} catch (WebDataException e) {
				String processId ="7";
				if(null!=e.getMessage()&&e.getMessage().indexOf("-")!=-1){
					String temp = e.getMessage().split("-")[0];
					try {processId = ""+Integer.valueOf(temp);}catch (Exception ex) {}///如果报错报系统错误
					Struts2Utils.setAttribute("errorMsg", e.getMessage());
				}else{
					Struts2Utils.setAttribute("errorMsg", "系统内部错误");
				}		
				map.put("processId", processId);
			 	
				map.put("errorMsg", e.getMessage());
			} catch (ServiceException e) {
				logger.warn(e.getMessage(), e);
				String processId ="7";
				if(null!=e.getMessage()&&e.getMessage().indexOf("-")!=-1){
					String temp = e.getMessage().split("-")[0];
					try {processId = ""+Integer.valueOf(temp);}catch (Exception ex) {}///如果报错报系统错误
					Struts2Utils.setAttribute("errorMsg", e.getMessage());
				}else{
					Struts2Utils.setAttribute("errorMsg", "系统内部错误");
				}		
				map.put("processId", processId);
			 	
				map.put("errorMsg", e.getMessage());
			} catch (Exception e) {
				logger.warn(e.getMessage(), e);
				String processId ="7";
				if(null!=e.getMessage()&&e.getMessage().indexOf("-")!=-1){
					String temp = e.getMessage().split("-")[0];
					try {processId = ""+Integer.valueOf(temp);}catch (Exception ex) {}///如果报错报系统错误
					Struts2Utils.setAttribute("errorMsg", e.getMessage());
				}else{
					Struts2Utils.setAttribute("errorMsg", "系统内部错误");
				}		
				map.put("processId", processId);
			 	
				map.put("errorMsg", e.getMessage());
			}
			renderJson(map,jsonConfig);
			return null;
		}

	/**
	 * 返回查询所有的查询彩票信息.北单的是整一期返回。竞彩是根据订单号来查询
	 */
	public String prizeTicket() {
		Map map = Maps.newHashMap();
       	JsonConfig jsonConfig = new JsonConfig();  
		StringBuffer sb = new StringBuffer();
		try {
			checkTicket();
			String orderId =null;
			user = this.userManager.getUser(ticketPlatformInfo.getUserId());
			if(null==user)	throw new WebDataException("1-平台ID错误");
			try{
				if(StringUtils.isNotBlank(wParam)){
					Map<String, Object> wParamMap = JsonUtil.getMap4Json(wParam);
					if(null!=wParamMap){
						orderId = String.valueOf(wParamMap.get("orderId"));
					}
				}

			}catch(Exception e){
				logger.warn(e.getMessage(), e);
				throw new WebDataException("5-参数解析错误");
			}
				Lottery lottery = Lottery.values()[Integer.valueOf(wLotteryId)];
				if(null==lottery)
					throw new WebDataException("5-lottery参数解析错误");
				String[] arr = orderId.split(",");
				List<String> orderList = Lists.newArrayList();
				for (String order : arr) {
					orderList.add(order.trim());
				}
				if(orderList.isEmpty())
					throw new WebDataException("5-订单总数为0");
				if(orderList.size()>100)
					throw new WebDataException("5-订单总数不能超过100");
				List<T> list = this.getSchemeEntityManager(lottery).findSchemeByOrderId(orderList);
				List<TicketState> ticketList = Lists.newArrayList();
				for (T t : list) {
					ticketList.add(new TicketState(t)) ;
				}
				map.put("processId", "0");
				map.put("ticket", ticketList);
		} catch (WebDataException e) {
			String processId ="7";
			if(null!=e.getMessage()&&e.getMessage().indexOf("-")!=-1){
				String temp = e.getMessage().split("-")[0];
				try {processId = ""+Integer.valueOf(temp);}catch (Exception ex) {}///如果报错报系统错误
				Struts2Utils.setAttribute("errorMsg", e.getMessage());
			}else{
				Struts2Utils.setAttribute("errorMsg", "系统内部错误");
			}		
			map.put("processId", processId);
		 	
			map.put("errorMsg", e.getMessage());
		} catch (ServiceException e) {
			logger.warn(e.getMessage(), e);
			String processId ="7";
			if(null!=e.getMessage()&&e.getMessage().indexOf("-")!=-1){
				String temp = e.getMessage().split("-")[0];
				try {processId = ""+Integer.valueOf(temp);}catch (Exception ex) {}///如果报错报系统错误
				Struts2Utils.setAttribute("errorMsg", e.getMessage());
			}else{
				Struts2Utils.setAttribute("errorMsg", "系统内部错误");
			}		
			map.put("processId", processId);
		 	
			map.put("errorMsg", e.getMessage());
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			String processId ="7";
			if(null!=e.getMessage()&&e.getMessage().indexOf("-")!=-1){
				String temp = e.getMessage().split("-")[0];
				try {processId = ""+Integer.valueOf(temp);}catch (Exception ex) {}///如果报错报系统错误
				Struts2Utils.setAttribute("errorMsg", e.getMessage());
			}else{
				Struts2Utils.setAttribute("errorMsg", "系统内部错误");
			}		
			map.put("processId", processId);
		 	
			map.put("errorMsg", e.getMessage());
		}
		renderJson(map,jsonConfig);
		return null;
	}
	/**
	 * 
	 */
	public String award() {
		Map map = Maps.newHashMap();
       	JsonConfig jsonConfig = new JsonConfig();  
		StringBuffer sb = new StringBuffer();
		try {
			checkTicket();
			String createDate =null;
			String periodNumber = null;
			Date endDate = null;
			try{
				if(StringUtils.isNotBlank(wParam)){
					Map<String, Object> wParamMap = JsonUtil.getMap4Json(wParam);
					if(null!=wParamMap){
						createDate = String.valueOf(wParamMap.get("createDate"));
						periodNumber = String.valueOf(wParamMap.get("periodNumber"));
					}
				}
				Lottery lottery = Lottery.values()[Integer.valueOf(wLotteryId)];
				if(null==lottery)
					throw new WebDataException("5-lottery参数解析错误");
				if(lottery.getCategory().equals(LotteryCategory.JC)){
					if(StringUtils.isBlank(createDate))
						throw new WebDataException("5-createDate参数解析错误");
					Date startDate = DateUtil.strToDate(createDate,"yyyyMMdd");
					if(null==startDate)throw new WebDataException("5-日期解析错误");
				    endDate = DateUtils.addDays(startDate, 1);
					if(null==endDate)throw new WebDataException("5-日期解析错误");
				}else{
					if(StringUtils.isBlank(periodNumber))
						throw new WebDataException("5-periodNumber参数解析错误"); 
				}
				
			}catch(Exception e){
				logger.warn(e.getMessage(), e);
				throw new WebDataException("5-参数解析错误");
			}
			
			user = this.userManager.getUser(ticketPlatformInfo.getUserId());
			if(null==user)	throw new WebDataException("1-平台ID错误");
			
		   ////写文件前先要读文件
			Long sponsorId=user.getId();
			String time=createDate;
			///读票
			String dir = "/html/js/data/award/" + sponsorId+ "/"+this.getLottery().getKey()+"/";
			String fileName = time+".js";
			String data = WriteHTMLUtil.readFile(dir, fileName);
			if(StringUtils.isNotBlank(data)){
				Map<String, Object> returnMap = JsonUtil.getMap4Json(data);
				String[] items = JsonUtil.getStringArray4Json(String.valueOf(returnMap.get("list")));
				final List<WonScheme> correctList = new ArrayList<WonScheme>();
				for (String itemStr : items) {
					returnMap = JsonUtil.getMap4Json(itemStr);
					Long id = Long.valueOf(String.valueOf(returnMap.get("id")));
					WonScheme item = new WonScheme();
					item.setId(id);
					item.setOrderId(String.valueOf(returnMap.get("orderId")).trim());
					item.setPrizeAfterTax(BigDecimal.valueOf(Double.valueOf(String.valueOf(returnMap.get("prizeAfterTax")))));
					correctList.add(item);
				}
				if(null!=correctList&&!correctList.isEmpty()){
					map.put("processId", "0");
					String wonStr="";
					BigDecimal total = BigDecimal.valueOf(0);
					for (WonScheme t : correctList) {
						wonStr = wonStr+t.getOrderId()+":"+Constant.numFmt.format(t.getPrizeAfterTax())+";";
						total = total.add(t.getPrizeAfterTax());
					}
					map.put("total", Constant.numFmt.format(total));
					map.put("award", wonStr);
				}else{
					map.put("processId", "1");
					map.put("award", "");
					map.put("total","");
				}
			}else{
				map.put("processId", "1");
				map.put("award", "");
				map.put("total","");
			}
		} catch (WebDataException e) {
			String processId ="7";
			if(null!=e.getMessage()&&e.getMessage().indexOf("-")!=-1){
				String temp = e.getMessage().split("-")[0];
				try {processId = ""+Integer.valueOf(temp);}catch (Exception ex) {}///如果报错报系统错误
				Struts2Utils.setAttribute("errorMsg", e.getMessage());
			}else{
				Struts2Utils.setAttribute("errorMsg", "系统内部错误");
			}		
			map.put("processId", processId);
		 	
			map.put("errorMsg", e.getMessage());
		} catch (ServiceException e) {
			logger.warn(e.getMessage(), e);
			String processId ="7";
			if(null!=e.getMessage()&&e.getMessage().indexOf("-")!=-1){
				String temp = e.getMessage().split("-")[0];
				try {processId = ""+Integer.valueOf(temp);}catch (Exception ex) {}///如果报错报系统错误
				Struts2Utils.setAttribute("errorMsg", e.getMessage());
			}else{
				Struts2Utils.setAttribute("errorMsg", "系统内部错误");
			}		
			map.put("processId", processId);
		 	
			map.put("errorMsg", e.getMessage());
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			String processId ="7";
			if(null!=e.getMessage()&&e.getMessage().indexOf("-")!=-1){
				String temp = e.getMessage().split("-")[0];
				try {processId = ""+Integer.valueOf(temp);}catch (Exception ex) {}///如果报错报系统错误
				Struts2Utils.setAttribute("errorMsg", e.getMessage());
			}else{
				Struts2Utils.setAttribute("errorMsg", "系统内部错误");
			}		
			map.put("processId", processId);
		 	
			map.put("errorMsg", e.getMessage());
		}
		renderJson(map,jsonConfig);
		return null;
	}
	public String match() {
		Map map = Maps.newHashMap();
       	JsonConfig jsonConfig = new JsonConfig();  
		ReqParamVisitor reqParamVisitor = new ReqParamVisitor();
		try {
			check();
			if(StringUtils.isNotBlank(wParam)){
				 reqParamVisitor.visit(wParam);
			}
			this.buildReqParamVisitor(reqParamVisitor);
			//彩钟平台，玩法，期号必须不为空。竞彩buildReqParamVisitor已经有赋予期号
			if(null==this.getwLotteryId()){
				throw new WebDataException("10-彩种为空");
			}
			if(null==this.getPeriodNumber()){
				throw new WebDataException("11-期号为空");
			}
			List matchList = this.buildMatchList();
			map.put("processId", "0");
			map.put("match", matchList);
		} catch (WebDataException e) {
			String processId ="7";
			if(null!=e.getMessage()&&e.getMessage().indexOf("-")!=-1){
				String temp = e.getMessage().split("-")[0];
				try {processId = ""+Integer.valueOf(temp);}catch (Exception ex) {}///如果报错报系统错误
				Struts2Utils.setAttribute("errorMsg", e.getMessage());
			}else{
				Struts2Utils.setAttribute("errorMsg", "系统内部错误");
			}		
			map.put("processId", processId);
		 	
			map.put("errorMsg", e.getMessage());
		} catch (ServiceException e) {
			logger.warn(e.getMessage(), e);
			String processId ="7";
			if(null!=e.getMessage()&&e.getMessage().indexOf("-")!=-1){
				String temp = e.getMessage().split("-")[0];
				try {processId = ""+Integer.valueOf(temp);}catch (Exception ex) {}///如果报错报系统错误
				Struts2Utils.setAttribute("errorMsg", e.getMessage());
			}else{
				Struts2Utils.setAttribute("errorMsg", "系统内部错误");
			}		
			map.put("processId", processId);
		 	
			map.put("errorMsg", e.getMessage());
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			String processId ="7";
			if(null!=e.getMessage()&&e.getMessage().indexOf("-")!=-1){
				String temp = e.getMessage().split("-")[0];
				try {processId = ""+Integer.valueOf(temp);}catch (Exception ex) {}///如果报错报系统错误
				Struts2Utils.setAttribute("errorMsg", e.getMessage());
			}else{
				Struts2Utils.setAttribute("errorMsg", "系统内部错误");
			}		
			map.put("processId", processId);
		 	
			map.put("errorMsg", e.getMessage());
		}
		renderJson(map,jsonConfig);
		return null;
	}
	
	public String result() {
		Map map = Maps.newHashMap();
		ReqParamVisitor reqParamVisitor = new ReqParamVisitor();
		JsonConfig jsonConfig = new JsonConfig();  
		try {
			check();
			if(StringUtils.isNotBlank(wParam)){
				Map<String, Object> wParam_map = JsonUtil.getMap4Json(wParam);
				if (null != wParam_map) {
					String wLotteryId = null == wParam_map.get("wLotteryId") ? null : String
							.valueOf(wParam_map.get("wLotteryId"));
					if(StringUtils.isNotBlank(wLotteryId)){
						this.setwLotteryId(wLotteryId);
					}
					String matchDate = null == wParam_map.get("matchDate") ? null : String
							.valueOf(wParam_map.get("matchDate"));
					if(StringUtils.isNotBlank(matchDate)){
						this.setMatchDate(matchDate);
					}
					if(null==this.getwLotteryId()){
						throw new WebDataException("10-彩种为空");
					}
					if(null==this.getMatchDate()){
						throw new WebDataException("11-日期为空");
					}
					List matchList = this.buildMatchResultList();
					Map<String,List> infoMap = Maps.newHashMap();
					if(getLottery().equals(Lottery.DCZC)||getLottery().equals(Lottery.SFZC)){
						List<Period> oldPeriods = periodManager.findOldPeriods(getLottery(), 10, true);
						List<String> periods = Lists.newArrayList();
						for (Period period : oldPeriods) {
							periods.add(period.getPeriodNumber());
						}
						infoMap.put("oldPeriods", periods);
					}
					map.put("processId", "0");
					infoMap.put("match", matchList);
					map.put("result", infoMap);
				}
			}
		} catch (WebDataException e) {
			String processId ="7";
			if(null!=e.getMessage()&&e.getMessage().indexOf("-")!=-1){
				String temp = e.getMessage().split("-")[0];
				try {processId = ""+Integer.valueOf(temp);}catch (Exception ex) {}///如果报错报系统错误
				Struts2Utils.setAttribute("errorMsg", e.getMessage());
			}else{
				Struts2Utils.setAttribute("errorMsg", "系统内部错误");
			}		
			Struts2Utils.setAttribute("processId", processId);
		} catch (ServiceException e) {
			logger.warn(e.getMessage(), e);
			String processId ="7";
			if(null!=e.getMessage()&&e.getMessage().indexOf("-")!=-1){
				String temp = e.getMessage().split("-")[0];
				try {processId = ""+Integer.valueOf(temp);}catch (Exception ex) {}///如果报错报系统错误
				Struts2Utils.setAttribute("errorMsg", e.getMessage());
			}else{
				Struts2Utils.setAttribute("errorMsg", "系统内部错误");
			}		
			Struts2Utils.setAttribute("processId", processId);
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			String processId ="7";
			if(null!=e.getMessage()&&e.getMessage().indexOf("-")!=-1){
				String temp = e.getMessage().split("-")[0];
				try {processId = ""+Integer.valueOf(temp);}catch (Exception ex) {}///如果报错报系统错误
				Struts2Utils.setAttribute("errorMsg", e.getMessage());
			}else{
				Struts2Utils.setAttribute("errorMsg", "系统内部错误");
			}		
			Struts2Utils.setAttribute("processId", processId);
		}
		renderJson(map,jsonConfig);
		return null;
	}
	public String subsriptionList() {
		Map map = Maps.newHashMap();
		ReqParamVisitor reqParamVisitor = new ReqParamVisitor();
		JsonConfig jsonConfig = new JsonConfig();  
		try {
			check();
			if(StringUtils.isNotBlank(wParam)){
				Map<String, Object> wParam_map = JsonUtil.getMap4Json(wParam);
				if (null != wParam_map) {
					Lottery lotteryType = null;
					if (StringUtils.isNotBlank(wLotteryId)) {
						try{
							 lotteryType = Lottery.values()[Integer.valueOf(wLotteryId.trim())];
						} catch (Exception e) {
							throw new WebDataException("12-彩种错误");
						}
						if(null==lotteryType){
							throw new WebDataException("12-彩种错误");
						}
					}
					if (StringUtils.isBlank(start)){
						throw new WebDataException("9-起始标志为空");
					}
					try{
						Integer.valueOf(start);
					} catch (Exception e) {
						throw new WebDataException("9-起始标志错误");
					}
					if (StringUtils.isBlank(count)){
						throw new WebDataException("10-条数标志为空");
					}
					try{
						Integer.valueOf(count);
					} catch (Exception e) {
						throw new WebDataException("10-条数标志错误");
					}
					String orderTypeStr = null == wParam_map.get("orderType") ? null : String
							.valueOf(wParam_map.get("orderType"));
					OrderType orderType = null;
					if(StringUtils.isNotBlank(orderTypeStr)){
						try{
							orderType = OrderType.values()[Integer.valueOf(orderTypeStr)];
						} catch (Exception e) {
							throw new WebDataException("11-排序标志错误");
						}
						
					}
					String playTypeOrdinal = null == wParam_map.get("playTypeOrdinal") ? null : String
							.valueOf(wParam_map.get("playTypeOrdinal"));
					String key = wLotteryId+"-"+start+"-"+count+"-"+orderTypeStr+"-"+playTypeOrdinal;
					///
					net.sf.ehcache.Element el = schemeQueryCache.get(key);
					if (el == null) {
						List<Criterion> restrictions = Lists.newArrayList();
						restrictions.add(Restrictions.eq("shareType", ShareType.TOGETHER));
						restrictions.add(Restrictions.eq("state",SchemeState.UNFULL));
						switch (getLottery()) {
						case PL:
							if(StringUtils.isBlank(playTypeOrdinal)){
								throw new WebDataException("12-玩法错误");
							}
							try{
								if (Integer.valueOf(0).equals(Integer.valueOf(playTypeOrdinal))) {
									restrictions.add(Restrictions.eq("playType", PlPlayType.P5Direct));
								} else{
									restrictions.add(Restrictions.ne("playType", PlPlayType.P5Direct));
								}
							} catch (Exception e) {
								throw new WebDataException("12-玩法错误");
							}
							break;
						case SFZC:
							if(StringUtils.isBlank(playTypeOrdinal)){
								throw new WebDataException("12-玩法错误");
							}
							try{
								com.cai310.lottery.support.zc.PlayType zcPlayType = com.cai310.lottery.support.zc.PlayType.values()[Integer.valueOf(playTypeOrdinal)];
								restrictions.add(Restrictions.eq("playType", zcPlayType));
							} catch (Exception e) {
								throw new WebDataException("12-玩法错误");
							}
							break;
						}
						List<Order> orders = Lists.newArrayList();
						if (orderType != null) {
							switch (orderType) {
							case CREATE_TIME_DESC:
								orders.add(Order.desc("id"));
								break;
							case CREATE_TIME_ASC:
								orders.add(Order.asc("id"));
								break;
							case PROCESS_RATE_DESC:
								orders.add(Order.desc("progressRate"));
								break;
							case PROCESS_RATE_ASC:
								orders.add(Order.asc("progressRate"));
								break;
							case SCHEME_COST_DESC:
								orders.add(Order.desc("schemeCost"));
								break;
							case SCHEME_COST_ASC:
								orders.add(Order.asc("schemeCost"));
								break;
							}
						} else {
							orders.add(Order.desc("orderPriority"));
							orders.add(Order.asc("state"));
							orders.add(Order.desc("progressRate"));
						}
						orders.add(Order.desc("id"));
						List<Scheme> list = this.getSchemeEntityManager(lotteryType).findSubsriptionByCriterion(restrictions, orders, Integer.valueOf(start), Integer.valueOf(count));
						List<SubsriptionDTO> subsriptionList = Lists.newArrayList();
						for (Scheme s : list) {
							s.setSubscriptions(null);
							SubsriptionDTO subsriptionDTO = new SubsriptionDTO();
							subsriptionDTO.setSchemeId(s.getId());
							subsriptionDTO.setLotteryType(lotteryType);
							subsriptionDTO.setSchemeState(s.getState());
							switch (lotteryType) {
								case PL:
									PlScheme plScheme = (PlScheme)s;
									subsriptionDTO.setPlayTypeOrdinal(plScheme.getPlayTypeOrdinal());
									break;
								case SFZC:
									SfzcScheme sfzcScheme = (SfzcScheme)s;
									subsriptionDTO.setPlayTypeOrdinal(sfzcScheme.getPlayTypeOrdinal());
									break;
								case WELFARE3D:
									Welfare3dScheme welfare3dScheme = (Welfare3dScheme)s;
									subsriptionDTO.setPlayTypeOrdinal(welfare3dScheme.getPlayTypeOrdinal());
									break;
								case DLT:
									DltScheme dltScheme = (DltScheme)s;
									subsriptionDTO.setPlayTypeOrdinal(dltScheme.getPlayTypeOrdinal());
									break;
								case DCZC:
									DczcScheme dczcScheme = (DczcScheme)s;
									subsriptionDTO.setPlayTypeOrdinal(dczcScheme.getPlayTypeOrdinal());
									break;
								case JCLQ:
									JclqScheme jclqScheme = (JclqScheme)s;
									subsriptionDTO.setPlayTypeOrdinal(jclqScheme.getPlayTypeOrdinal());
									break;
								case JCZQ:
									JczqScheme jczqScheme = (JczqScheme)s;
									subsriptionDTO.setPlayTypeOrdinal(jczqScheme.getPlayTypeOrdinal());
									break;
							}
							PropertyUtils.copyProperties(subsriptionDTO, s);
							subsriptionList.add(subsriptionDTO);
						}
                        el = new net.sf.ehcache.Element(key, subsriptionList);
						this.schemeQueryCache.put(el);
						map.put("processId", "0");
						map.put("totalCount", subsriptionList.size());
						map.put("list", subsriptionList);
					} else {
						List list  = (List) el.getValue();
						map.put("processId", "0");
						map.put("totalCount", list.size());
						map.put("list", list);
					}
				}
				
			}
		} catch (WebDataException e) {
			String processId ="7";
			if(null!=e.getMessage()&&e.getMessage().indexOf("-")!=-1){
				String temp = e.getMessage().split("-")[0];
				try {processId = ""+Integer.valueOf(temp);}catch (Exception ex) {}///如果报错报系统错误
				Struts2Utils.setAttribute("errorMsg", e.getMessage());
			}else{
				Struts2Utils.setAttribute("errorMsg", "系统内部错误");
			}		
			map.put("processId", processId);
		 	
			map.put("errorMsg", e.getMessage());
		} catch (ServiceException e) {
			logger.warn(e.getMessage(), e);
			String processId ="7";
			if(null!=e.getMessage()&&e.getMessage().indexOf("-")!=-1){
				String temp = e.getMessage().split("-")[0];
				try {processId = ""+Integer.valueOf(temp);}catch (Exception ex) {}///如果报错报系统错误
				Struts2Utils.setAttribute("errorMsg", e.getMessage());
			}else{
				Struts2Utils.setAttribute("errorMsg", "系统内部错误");
			}		
			map.put("processId", processId);
		 	
			map.put("errorMsg", e.getMessage());
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			String processId ="7";
			if(null!=e.getMessage()&&e.getMessage().indexOf("-")!=-1){
				String temp = e.getMessage().split("-")[0];
				try {processId = ""+Integer.valueOf(temp);}catch (Exception ex) {}///如果报错报系统错误
				Struts2Utils.setAttribute("errorMsg", e.getMessage());
			}else{
				Struts2Utils.setAttribute("errorMsg", "系统内部错误");
			}		
			map.put("processId", processId);
		 	
			map.put("errorMsg", e.getMessage());
		}
		renderJson(map,jsonConfig);
		return null;
	}
	public String subsription() {
		Map map = Maps.newHashMap();
		JsonConfig jsonConfig = new JsonConfig();  
		try {
			check();
			Map<String, Object> wParam_map = JsonUtil.getMap4Json(wParam);
			Lottery lotteryType = null;
			try{
				lotteryType = Lottery.values()[Integer.valueOf(wLotteryId.trim())];
			} catch (Exception e) {
				throw new WebDataException("8-彩种错误");
			}
			if(null==lotteryType){
				throw new WebDataException("8-彩种错误");
			}
			
			
			String userId = null==wParam_map.get("userId")?null:String.valueOf(wParam_map.get("userId")).trim();
			if(StringUtils.isBlank(userId))throw new WebDataException("1-用户ID为空.");
			String userPwd = null==wParam_map.get("userPwd")?null:String.valueOf(wParam_map.get("userPwd")).trim();
			if(StringUtils.isBlank(userPwd))throw new WebDataException("1-用户交易密码为空.");
			user = userManager.getUser(Long.valueOf(userId));
			if (user == null)throw new WebDataException("1-找不到用户ID对应的用户.");
			if(user.isLocked())throw new WebDataException("2-帐号被冻结");
			String schemeId = null==wParam_map.get("schemeId")?null:String.valueOf(wParam_map.get("schemeId")).trim();
			if(StringUtils.isBlank(schemeId))throw new WebDataException("9-方案ID为空.");
			String subscriptionCostStr = null==wParam_map.get("subscriptionCost")?null:String.valueOf(wParam_map.get("subscriptionCost")).trim();
			if(StringUtils.isBlank(subscriptionCostStr))throw new WebDataException("10-加入金额为空.");
			BigDecimal subscriptionCost = null;
			try{
				subscriptionCost =  BigDecimal.valueOf(Integer.valueOf(subscriptionCostStr));
			} catch (Exception e) {
				throw new WebDataException("10-加入金额错误");
			}
			if(null==subscriptionCost)throw new WebDataException("10-加入金额错误");
			String baodiCostStr = null==wParam_map.get("baodiCost")?null:String.valueOf(wParam_map.get("baodiCost")).trim();
			BigDecimal baodiCost = null;
			if(StringUtils.isNotBlank(baodiCostStr)){
				try{
					baodiCost = BigDecimal.valueOf(Integer.valueOf(baodiCostStr));
				} catch (Exception e) {
					throw new WebDataException("11-加入金额错误");
				}
			}
			checkUser(userPwd,user);
			final SubscribeDTO dto = buildSubscribeDTO(Long.valueOf(schemeId),subscriptionCost,baodiCost,null);
			////////////////////////////////////////
			ExecutorUtils.exec(new Executable() {
				public void run() throws ExecuteException {
					scheme = getSchemeService().subscribe(dto);
				}
			}, 3);
			map.put("processId", "0");
			map.put("schemeId", scheme.getId()+"");
			map.put("subscriptionCost", subscriptionCost+"");
			map.put("baodiCost", baodiCost+"");
		} catch (WebDataException e) {
			String processId ="7";
			if(null!=e.getMessage()&&e.getMessage().indexOf("-")!=-1){
				String temp = e.getMessage().split("-")[0];
				try {processId = ""+Integer.valueOf(temp);}catch (Exception ex) {}///如果报错报系统错误
				Struts2Utils.setAttribute("errorMsg", e.getMessage());
			}else{
				Struts2Utils.setAttribute("errorMsg", "系统内部错误");
			}		
			map.put("processId", processId);
		 	
			map.put("errorMsg", e.getMessage());
		} catch (ServiceException e) {
			logger.warn(e.getMessage(), e);
			String processId ="7";
			if(null!=e.getMessage()&&e.getMessage().indexOf("-")!=-1){
				String temp = e.getMessage().split("-")[0];
				try {processId = ""+Integer.valueOf(temp);}catch (Exception ex) {}///如果报错报系统错误
				Struts2Utils.setAttribute("errorMsg", e.getMessage());
			}else{
				Struts2Utils.setAttribute("errorMsg", "系统内部错误");
			}		
			map.put("processId", processId);
		 	
			map.put("errorMsg", e.getMessage());
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			String processId ="7";
			if(null!=e.getMessage()&&e.getMessage().indexOf("-")!=-1){
				String temp = e.getMessage().split("-")[0];
				try {processId = ""+Integer.valueOf(temp);}catch (Exception ex) {}///如果报错报系统错误
				Struts2Utils.setAttribute("errorMsg", e.getMessage());
			}else{
				Struts2Utils.setAttribute("errorMsg", "系统内部错误");
			}		
			map.put("processId", processId);
		 	
			map.put("errorMsg", e.getMessage());
		}
		renderJson(map,jsonConfig);
		return null;
	}
	/**
	 * 构建认购数据传输对象
	 * 
	 * @return 认购数据传输对象
	 * @throws WebDataException
	 */
	protected SubscribeDTO buildSubscribeDTO(Long schemeId,BigDecimal subscriptionCostTemp,BigDecimal baodiCostTemp,String password) throws WebDataException {
		SubscribeDTO dto = new SubscribeDTO();
		dto.setUserId(user.getId());
        
		if (schemeId == null)
			throw new WebDataException("9-方案ID为空.");
		Scheme scheme = this.getSchemeEntityManager(this.getLottery()).getScheme(schemeId);
		if (scheme == null)
			throw new WebDataException("9-方案为空.");
		if (!scheme.isCanSubscribe())
			throw new WebDataException("9-方案不允许加入.");
		dto.setSchemeId(schemeId);

		if (subscriptionCostTemp== null && baodiCostTemp == null)
			throw new WebDataException("10-认购金额和保底金额都为空.");

		if (subscriptionCostTemp!= null) {
			BigDecimal subscriptionCost = subscriptionCostTemp.setScale(
					Constant.COST_MIN_UNITS.getScale(), BigDecimal.ROUND_DOWN);
			if (subscriptionCost.doubleValue() != subscriptionCostTemp.doubleValue())
				throw new WebDataException("10-认购金额不合法,金额的最小单位为" + Constant.COST_MIN_UNITS.getTypeName() + ".");

			dto.setSubscriptionCost(subscriptionCost);
		}

		if (baodiCostTemp != null) {
			BigDecimal baodiCost = baodiCostTemp.setScale(Constant.COST_MIN_UNITS.getScale(),
					BigDecimal.ROUND_DOWN);
			if (baodiCost.doubleValue() != baodiCostTemp.doubleValue())
				throw new WebDataException("11-保底金额不合法,金额的最小单位为" + Constant.COST_MIN_UNITS.getTypeName() + ".");

			dto.setBaodiCost(baodiCost);
		}

		dto.setPassword(password);
		dto.setWay(SubscriptionWay.NORMAL);
		return dto;
	}
	public void createAward(){
		try {
			String createDate = Struts2Utils.getParameter("date");
			Date startDate = DateUtil.strToDate(createDate,"yyyyMMdd");
			if(null==startDate)throw new WebDataException("5-日期解析错误");
			Date endDate = DateUtils.addDays(startDate, 1);
			if(null==endDate)throw new WebDataException("5-日期解析错误");
			XDetachedCriteria userCriteria = new XDetachedCriteria(User.class, "u");
			userCriteria.add(Restrictions.eq("u.ticketUser", true));
			List<User> ticketUserList = queryService.findByDetachedCriteria(userCriteria);
			for (User user : ticketUserList) {
				List<T> schemeList = getSchemeService().findWonSchemeBySponsorId(user.getId(), startDate, endDate);
				WonScheme wonScheme;
				List<WonScheme> wonSchemeList = Lists.newArrayList();
				for (T scheme : schemeList) {
					if(null!=scheme.getOrderId()&&scheme.isSuccessWon()){
						wonScheme = new WonScheme();
						wonScheme.setId(scheme.getId());
						wonScheme.setOrderId(scheme.getOrderId());
						wonScheme.setPrizeAfterTax(scheme.getPrizeAfterTax());
						wonScheme.setSponsorId(scheme.getSponsorId());
						wonScheme.setCreateTime(scheme.getCreateTime());
						wonSchemeList.add(wonScheme);
					}
				}
				Map<String, Object> contents = new HashMap<String, Object>();
				String dir = "/html/js/data/award/" + user.getId()+ "/"+this.getLottery().getKey()+"/";
				String fileName = createDate+".js";
				contents.put("list", wonSchemeList);
				JsonConfig jsonConfig = new JsonConfig();  
				jsonConfig.setExcludes(new String[] { "createTime"});  
				String value =JSONObject.fromObject(contents,jsonConfig).toString();
				com.cai310.utils.WriteHTMLUtil.writeHtm(dir, fileName, value, "UTF-8");
			}
		}catch (Exception e) {
			Struts2Utils.setAttribute("processId", 9);
		}
	}
	protected String buildAwardResultXML(Map<Long,Map<String,String>> flag){
	 	StringBuffer sb = new StringBuffer();
	    for (Long orderid : flag.keySet()) {
	    	Map<String,String> temp = flag.get(orderid);
	   		sb.append("<ticket>");
	   	    sb.append("<process>"+temp.get("process")+"</process>");
	   	    sb.append("<createDate>"+temp.get("createDate")+"</createDate>");
	   	    if(null!=temp.get("total")){
	   	       sb.append("<total>"+temp.get("total")+"</total>");
	   	    }
	   	    if(null!=temp.get("award")){
	   	       sb.append("<award>"+temp.get("award")+"</award>");
	   	    }
	   	    sb.append("</ticket>");
	   	    
		}
  	    return sb.toString();
}
	
	
    /**
	 * 构建方案创建数据传输对象
	 * 
	 * @return 方案创建数据传输对象
	 * @throws WebDataException
	 */
	protected  abstract Lottery getLottery();
	protected E buildSchemeDTO() throws WebDataException {
		E schemeDTO;
		try {
			schemeDTO = this.schemeDTOClass.newInstance();
		} catch (InstantiationException e) {
			this.logger.warn("创建数据传输对象发生异常.", e);
			throw new WebDataException("6-创建数据传输对象发生异常.");
		} catch (IllegalAccessException e) {
			this.logger.warn("创建数据传输对象发生异常.", e);
			throw new WebDataException("6-创建数据传输对象发生异常.");
		}
		if (this.ticketPlatformInfo != null){
			if (orderId == null)
				throw new WebDataException("6-订单号为空.");
			schemeDTO.setSponsorId(ticketPlatformInfo.getId());
			this.shareType =  ShareType.SELF.ordinal();
		}else{
			if (user == null)
				throw new WebDataException("6-您还未登录,请登录后再操作.");

			schemeDTO.setSponsorId(user.getId());
		}

		schemeDTO.setOrderId(orderId);
		if (StringUtils.isBlank(this.getPeriodNumber()))
			throw new WebDataException("6-期号为空.");
		Long periodId = null;
		List<Period> periodList = periodManager.findCurrentPeriods(getLottery());
		for (Period period : periodList) {
			if(period.getPeriodNumber().trim().equals(this.getPeriodNumber().trim())){
				periodId = period.getId();
			}
		}
		if (periodId == null)
			throw new WebDataException("6-期号对应期Id为空.");
		schemeDTO.setPeriodId(periodId);

		if (getMode() == null)
			throw new WebDataException("6-方案投注方式为空.");
		SalesMode salesMode = buildSalesMode();
		schemeDTO.setMode(salesMode);

		if (getSchemeCost() == null || getSchemeCost() < 2)
			throw new WebDataException("6-方案金额为空或不合法.");
		schemeDTO.setSchemeCost(getSchemeCost());
		schemeDTO.setSponsorSubscriptionCost(BigDecimal.valueOf(getSchemeCost()));
		if (getMultiple() == null || getMultiple() < 1)
			throw new WebDataException("6-方案倍数为空或不合法.");
		schemeDTO.setMultiple(getMultiple());

		if (getUnits() == null || getUnits() < 1)
			throw new WebDataException("6-方案注数为空或不合法.");
		else {
			if (salesMode == SalesMode.SINGLE && getUnits() > Constant.MAX_SINGLE_UNITS) {
				throw new WebDataException("6-单式方案注数不能大于" + Constant.MAX_SINGLE_UNITS + "注.");
			}
			if (salesMode == SalesMode.COMPOUND && getUnits() > Constant.MAX_UNITS)
				throw new WebDataException("6-复式方案注数不能大于" + Constant.MAX_UNITS + "注.");
		}
		schemeDTO.setUnits(getUnits());

		int cost = schemeDTO.getUnits() * schemeDTO.getMultiple() * getUnitsMoney();
		if (cost != schemeDTO.getSchemeCost())
			throw new WebDataException("6-根据注数计算出来的金额与提交的方案金额不一致.");
		
		ContentBean contentBean = buildContentBean();
			if (contentBean == null || StringUtils.isBlank(contentBean.getContent()))
				throw new WebDataException("6-方案内容为空.");
			else if (!schemeDTO.getUnits().equals(contentBean.getUnits()))
				throw new WebDataException("6-根据方案内容计算出来的注数与提交的注数不一致.");
		schemeDTO.setContent(contentBean.getContent());
		switch (buildShareType()) {
		case TOGETHER:
			if (this.getCommissionRate() != null) {
				if (this.getCommissionRate() < 0
						|| this.getCommissionRate() > Constant.COMMISSION_MAX_RATE * 100)
					throw new WebDataException("6-佣金率不能小于0或者大于" + (Constant.COMMISSION_MAX_RATE * 100) + "%.");

				schemeDTO.setCommissionRate(this.getCommissionRate()/100);
			}
			if (this.getMinSubscriptionCost() != null) {
				BigDecimal minSubscriptionCost = this.getMinSubscriptionCost().setScale(
						Constant.COST_MIN_UNITS.getScale(), BigDecimal.ROUND_DOWN);
				if (minSubscriptionCost.doubleValue() != this.getMinSubscriptionCost().doubleValue())
					throw new WebDataException("6-最低认购金额不合法,金额的最小单位为" + Constant.COST_MIN_UNITS.getTypeName() + ".");

				schemeDTO.setMinSubscriptionCost(minSubscriptionCost);
			}

			BigDecimal totalCost = BigDecimal.ZERO;
			if (this.getSubscriptionCost() == null)
				throw new WebDataException("6-方案认购金额为空.");
			else {
				BigDecimal subscriptionCost = this.getSubscriptionCost().setScale(
						Constant.COST_MIN_UNITS.getScale(), BigDecimal.ROUND_DOWN);
				if (subscriptionCost.doubleValue() != this.getSubscriptionCost().doubleValue())
					throw new WebDataException("6-认购金额不合法,金额的最小单位为" + Constant.COST_MIN_UNITS.getTypeName() + ".");

				if (subscriptionCost.doubleValue() > schemeDTO.getSchemeCost())
					throw new WebDataException("6-认购金额不能大于方案金额.");

				BigDecimal sponsorMinSubscriptionCost = BigDecimalUtil.valueOf(schemeDTO.getSchemeCost()
						* Constant.SPONSOR_MIN_SUBSCRIPTION_PERCENT);
				if (subscriptionCost.compareTo(sponsorMinSubscriptionCost) < 0)
					throw new WebDataException("6-发起人必须认购方案金额的" + Constant.SPONSOR_MIN_SUBSCRIPTION_PERCENT * 100
							+ "%(即" + Constant.MONEY_FORMAT.format(sponsorMinSubscriptionCost) + "元)以上.");
				if (schemeDTO.getMinSubscriptionCost() != null
						&& schemeDTO.getSchemeCost() < schemeDTO.getMinSubscriptionCost().doubleValue())
					throw new WebDataException("6-方案金额不能小于设置的最小认购金额.");
				if (schemeDTO.getMinSubscriptionCost() != null
						&& subscriptionCost.doubleValue() < schemeDTO.getMinSubscriptionCost().doubleValue())
					throw new WebDataException("6-认购方案金额不能小于设置的最小认购金额.");

				schemeDTO.setSponsorSubscriptionCost(subscriptionCost);
				totalCost = totalCost.add(subscriptionCost);
			}

			if (this.getBaodiCost() != null&&this.getBaodiCost().compareTo(BigDecimal.ZERO)>0) {
				BigDecimal baodiCost = this.getBaodiCost().setScale(Constant.COST_MIN_UNITS.getScale(),
						BigDecimal.ROUND_DOWN);
				if (baodiCost.doubleValue() != this.getBaodiCost().doubleValue())
					throw new WebDataException("6-保底金额不合法,金额的最小单位为" + Constant.COST_MIN_UNITS.getTypeName() + ".");

				if (baodiCost.doubleValue() > schemeDTO.getSchemeCost())
					throw new WebDataException("6-保底金额不能大于方案金额.");

				if (schemeDTO.getMinSubscriptionCost() != null
						&& baodiCost.doubleValue() < schemeDTO.getMinSubscriptionCost().doubleValue())
					throw new WebDataException("6-保底方案金额不能小于设置的最小认购金额.");

				schemeDTO.setSponsorBaodiCost(baodiCost);
				totalCost = totalCost.add(baodiCost);
			}

			if (totalCost.doubleValue() > schemeDTO.getSchemeCost())
				throw new WebDataException("6-认购金额与保底金额之和不能大于方案金额.");
			schemeDTO.setSecretType(this.buildSecretType());///合买
			break;
		case SELF:
			schemeDTO.setSecretType(SecretType.FULL_SECRET);///接票的都设为保密
			break;
		default:
			throw new WebDataException("6-方案分享方式不合法.");
		}
		schemeDTO.setSubscriptionLicenseType(SubscriptionLicenseType.PUBLIC_LICENSE);///加入方式都设为公共加入
		schemeDTO.setShareType(buildShareType());
		schemeDTO.setDescription("ticket");
		if (StringUtils.isNotBlank(getDescription())) {
			String desc = getDescription().trim();
			if (desc.getBytes().length > Scheme.DESCRIPTION_MAX_LENGTH)
				throw new WebDataException("6-方案描述内容长度不能超过" + Scheme.DESCRIPTION_MAX_LENGTH + "个字符,一个汉字占两个字符.");
			// 这里不过滤内容，在显示的时候进行过滤后再显示
			schemeDTO.setDescription(desc);
		}
		return schemeDTO;
	}
	public String data(){
		  ///写入接票表
		List<Criterion> restrictions = Lists.newArrayList();
		restrictions.add(Restrictions.eq("sponsorId", 370L));
		restrictions.add(Restrictions.gt("createTime",DateUtil.strToDate("2013-11-07 00:00")));
		List<T> t = this.getSchemeEntityManager(this.getLottery()).findSchemeByCriterion(restrictions);
		for (T t2 : t) {
			TicketThen ticketThen = this.getSchemeEntityManager(this.getLottery()).findTicketThenByOrderId(t2.getOrderId());
			if(null==ticketThen){
				DczcScheme scheme = (DczcScheme) t2;
				ticketThen = new TicketThen();
				ticketThen.setOutOrderNumber(t2.getOrderId());
						ticketThen.setOrderId(scheme.getOrderId());
						ticketThen.setOfficialEndTime(scheme.getCommitTime());
						ticketThen.setPlatformInfoId(250L);
						ticketThen.setUserId(370L);
						ticketThen.setLotteryType(scheme.getLotteryType());
						ticketThen.setPeriodNumber(scheme.getPeriodNumber());
						ticketThen.setSchemeNumber(scheme.getSchemeNumber());
						ticketThen.setUnits(scheme.getUnits());
						ticketThen.setMultiple(scheme.getMultiple());
						ticketThen.setSchemeCost(scheme.getSchemeCost());
						ticketThen.setState(TicketSchemeState.SUCCESS);
						ticketThen.setMode(scheme.getMode());
						ticketThen.setPlayType(scheme.getPlayTypeOrdinal());
				ticketThen=this.ticketThenEntityManager.saveTicketThen(ticketThen);
				TicketDatail ticketDatail = new TicketDatail();
				ticketDatail.setTicketId(ticketThen.getId());
				ticketDatail.setContent(scheme.getContentText());
				this.ticketThenEntityManager.saveTicketDatail(ticketDatail);
			}
		}
		return null;
	}
	/**
	* 
	*/
	public String resultInfoTicket() {
			Map map = Maps.newHashMap();
	       	JsonConfig jsonConfig = new JsonConfig();  
			StringBuffer sb = new StringBuffer();
			try {
				checkTicket();
				if(StringUtils.isNotBlank(this.wParam)){
					Map<String, Object> wParam_map = JsonUtil.getMap4Json(wParam);
					if(null!=wParam_map){
						String periodNumber = null == wParam_map
								.get("periodNumber") ? null : String
								.valueOf(wParam_map.get("periodNumber"));
						if (StringUtils.isNotBlank(periodNumber)) {
							this.setPeriodNumber(periodNumber);
						}
						if (null == this.getwLotteryId()) {
							throw new WebDataException("10-彩种为空");
						}
						if (null == this.getPeriodNumber()) {
							throw new WebDataException("11-期号为空");
						}
						if(null!=this.getPeriodNumber()){
							Period period = this.periodManager.loadPeriod(this.getLottery(),this.getPeriodNumber().trim());
							if(null==period){
								throw new WebDataException("7-期号Id错误");
							}
							PeriodData periodDataCom = this.getPeriodDataEntityManager(this.getLottery()).getPeriodData(period.getId());
							if(null==periodDataCom){
								throw new WebDataException("8-期数据为空");
							}
							PeriodDataInfoDTO periodDataInfoDTO = new PeriodDataInfoDTO();
							periodDataInfoDTO.setLotteryType(period.getLotteryType());
							periodDataInfoDTO.setPeriodId(period.getId());
							periodDataInfoDTO.setResult(periodDataCom.getResult());
							periodDataInfoDTO.setPeriodNumber(period.getPeriodNumber());
							periodDataInfoDTO.setPeriodTitle(period.getLotteryType().getTitle());
							periodDataInfoDTO.setPrizeTime(DateUtil.dateToStr(period.getPrizeTime(),"yyyy-MM-dd HH:mm:ss"));
							periodDataInfoDTO.setEndCashTime(DateUtil.dateToStr(DateUtils.addDays(period.getPrizeTime(), 59),"yyyy-MM-dd"));
							if(period.getLotteryType().equals(Lottery.SSQ)){
								SsqPeriodData periodData = (SsqPeriodData)periodDataCom;
								periodDataInfoDTO.setPrizePool(periodData.getPrizePool());
								periodDataInfoDTO.setTotalSales(periodData.getTotalSales());
								periodDataInfoDTO.setWinUnit(JsonUtil.getJSONArray4Json("["+JsonUtil.getJsonString4JavaPOJO(periodData.getWinUnit())+"]"));
								periodDataInfoDTO.setPrize(JsonUtil.getJSONArray4Json("["+JsonUtil.getJsonString4JavaPOJO(periodData.getPrize())+"]"));
							}
							if(period.getLotteryType().equals(Lottery.DLT)){
								DltPeriodData periodData = (DltPeriodData)periodDataCom;
								periodDataInfoDTO.setPrizePool(periodData.getPrizePool());
								periodDataInfoDTO.setTotalSales(periodData.getTotalSales());
								periodDataInfoDTO.setWinUnit(JsonUtil.getJSONArray4Json("["+JsonUtil.getJsonString4JavaPOJO(periodData.getWinUnit())+"]"));
								periodDataInfoDTO.setPrize(JsonUtil.getJSONArray4Json("["+JsonUtil.getJsonString4JavaPOJO(periodData.getPrize())+"]"));
							}
							if(period.getLotteryType().equals(Lottery.SEVEN)){
								SevenPeriodData periodData = (SevenPeriodData)periodDataCom;
								periodDataInfoDTO.setPrizePool(periodData.getPrizePool());
								periodDataInfoDTO.setTotalSales(periodData.getTotalSales());
								periodDataInfoDTO.setWinUnit(JsonUtil.getJSONArray4Json("["+JsonUtil.getJsonString4JavaPOJO(periodData.getWinUnit())+"]"));
								periodDataInfoDTO.setPrize(JsonUtil.getJSONArray4Json("["+JsonUtil.getJsonString4JavaPOJO(periodData.getPrize())+"]"));
							}
							if(period.getLotteryType().equals(Lottery.SEVENSTAR)){
								SevenstarPeriodData periodData = (SevenstarPeriodData)periodDataCom;
								periodDataInfoDTO.setPrizePool(periodData.getPrizePool());
								periodDataInfoDTO.setTotalSales(periodData.getTotalSales());
								periodDataInfoDTO.setWinUnit(JsonUtil.getJSONArray4Json("["+JsonUtil.getJsonString4JavaPOJO(periodData.getWinUnit())+"]"));
								periodDataInfoDTO.setPrize(JsonUtil.getJSONArray4Json("["+JsonUtil.getJsonString4JavaPOJO(periodData.getPrize())+"]"));
							}
							if(period.getLotteryType().equals(Lottery.TC22TO5)){
								Tc22to5PeriodData periodData = (Tc22to5PeriodData)periodDataCom;
								periodDataInfoDTO.setPrizePool(periodData.getPrizePool());
								periodDataInfoDTO.setTotalSales(periodData.getTotalSales());
								periodDataInfoDTO.setWinUnit(JsonUtil.getJSONArray4Json("["+JsonUtil.getJsonString4JavaPOJO(periodData.getWinUnit())+"]"));
								periodDataInfoDTO.setPrize(JsonUtil.getJSONArray4Json("["+JsonUtil.getJsonString4JavaPOJO(periodData.getPrize())+"]"));
							}
							if(period.getLotteryType().equals(Lottery.PL)){
								PlPeriodData periodData = (PlPeriodData)periodDataCom;
								periodDataInfoDTO.setPrizePool(null);
								periodDataInfoDTO.setTotalSales(periodData.getTotalSales());
								periodDataInfoDTO.setWinUnit(JsonUtil.getJSONArray4Json("["+JsonUtil.getJsonString4JavaPOJO(periodData.getWinUnit())+"]"));
								periodDataInfoDTO.setPrize(JsonUtil.getJSONArray4Json("["+JsonUtil.getJsonString4JavaPOJO(periodData.getPrize())+"]"));
							}
							if(period.getLotteryType().equals(Lottery.WELFARE3D)){
								Welfare3dPeriodData periodData = (Welfare3dPeriodData)periodDataCom;
								periodDataInfoDTO.setPrizePool(null);
								periodDataInfoDTO.setTotalSales(periodData.getTotalSales());
								periodDataInfoDTO.setWinUnit(JsonUtil.getJSONArray4Json("["+JsonUtil.getJsonString4JavaPOJO(periodData.getWinUnit())+"]"));
								periodDataInfoDTO.setPrize(JsonUtil.getJSONArray4Json("["+JsonUtil.getJsonString4JavaPOJO(periodData.getPrize())+"]"));
							}
							if(period.getLotteryType().equals(Lottery.SFZC)){
								SfzcPeriodData periodData = (SfzcPeriodData)periodDataCom;
								periodDataInfoDTO.setPrizePool(periodData.getPrizePool());
								periodDataInfoDTO.setTotalSales(periodData.getTotalSales());
								Map<String,Object> unit = Maps.newHashMap();
								Map<String,Object> prize = Maps.newHashMap();
								unit.put("firstWinUnits_14", periodData.getFirstWinUnits_14());
								prize.put("firstPrize_14", periodData.getFirstPrize_14());
								periodDataInfoDTO.setWinUnit(JsonUtil.getJSONArray4Json("["+JsonUtil.getJsonString4JavaPOJO(unit)+"]"));
								periodDataInfoDTO.setPrize(JsonUtil.getJSONArray4Json("["+JsonUtil.getJsonString4JavaPOJO(prize)+"]"));
							}
							map.put("periodData", periodDataInfoDTO);
							jsonConfig.setExcludes(new String[] {"winItemList","won","generalAdditional","maxHit","periodId","lotteryType"});  
							
						}
					}
				}
				map.put("processId", "0");
			} catch (WebDataException e) {
				String processId ="7";
				if(null!=e.getMessage()&&e.getMessage().indexOf("-")!=-1){
					String temp = e.getMessage().split("-")[0];
					try {processId = ""+Integer.valueOf(temp);}catch (Exception ex) {}///如果报错报系统错误
					Struts2Utils.setAttribute("errorMsg", e.getMessage());
				}else{
					Struts2Utils.setAttribute("errorMsg", "系统内部错误");
				}		
				map.put("processId", processId);
			 	
				map.put("errorMsg", e.getMessage());
			} catch (ServiceException e) {
				logger.warn(e.getMessage(), e);
				String processId ="7";
				if(null!=e.getMessage()&&e.getMessage().indexOf("-")!=-1){
					String temp = e.getMessage().split("-")[0];
					try {processId = ""+Integer.valueOf(temp);}catch (Exception ex) {}///如果报错报系统错误
					Struts2Utils.setAttribute("errorMsg", e.getMessage());
				}else{
					Struts2Utils.setAttribute("errorMsg", "系统内部错误");
				}		
				map.put("processId", processId);
			 	
				map.put("errorMsg", e.getMessage());
			} catch (Exception e) {
				logger.warn(e.getMessage(), e);
				String processId ="7";
				if(null!=e.getMessage()&&e.getMessage().indexOf("-")!=-1){
					String temp = e.getMessage().split("-")[0];
					try {processId = ""+Integer.valueOf(temp);}catch (Exception ex) {}///如果报错报系统错误
					Struts2Utils.setAttribute("errorMsg", e.getMessage());
				}else{
					Struts2Utils.setAttribute("errorMsg", "系统内部错误");
				}		
				map.put("processId", processId);
			 	
				map.put("errorMsg", e.getMessage());
			}
			renderJson(map,jsonConfig);
			return null;
		}
	
	/**
	 * 构建格式化好的方案内容和根据内容计算出来的注数
	 */
	public ContentBean buildContentBean() throws WebDataException {
		switch (buildSalesMode()) {
		case COMPOUND:
			return buildCompoundContentBean();
		case SINGLE:
			return buildSingleContentBean();
		default:
			throw new WebDataException("6-方案投注方式不合法.");
		}
	}

	/**
	 * 构造复式方案内容和计算注数
	 * 
	 */
	protected abstract ContentBean buildCompoundContentBean() throws WebDataException;

	/**
	 * 构造单式方案内容和计算注数
	 * 
	 */
	protected abstract ContentBean buildSingleContentBean() throws WebDataException;

	public Class<E> getSchemeDTOClass() {
		return schemeDTOClass;
	}

	public void setSchemeDTOClass(Class<E> schemeDTOClass) {
		this.schemeDTOClass = schemeDTOClass;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getUnits() {
		return units;
	}

	public void setUnits(Integer units) {
		this.units = units;
	}

	public Integer getMultiple() {
		return multiple;
	}

	public void setMultiple(Integer multiple) {
		this.multiple = multiple;
	}

	public Integer getSchemeCost() {
		return schemeCost;
	}

	public void setSchemeCost(Integer schemeCost) {
		this.schemeCost = schemeCost;
	}

	public Integer getMode() {
		return mode;
	}

	public void setMode(Integer mode) {
		this.mode = mode;
	}

	public Integer getUnitsMoney() {
		return unitsMoney;
	}

	public void setUnitsMoney(Integer unitsMoney) {
		this.unitsMoney = unitsMoney;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserPwd() {
		return userPwd;
	}

	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}

	public T getScheme() {
		return scheme;
	}

	public void setScheme(T scheme) {
		this.scheme = scheme;
	}

	public String getPeriodNumber() {
		return periodNumber;
	}

	public void setPeriodNumber(String periodNumber) {
		this.periodNumber = periodNumber;
	}
	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<ReqParamVisitor> getTicketList() {
		return ticketList;
	}

	public void setTicketList(List<ReqParamVisitor> ticketList) {
		this.ticketList = ticketList;
	}

	public String getwAction() {
		return wAction;
	}

	public void setwAction(String wAction) {
		this.wAction = wAction;
	}

	public String getwLotteryId() {
		return wLotteryId;
	}

	public void setwLotteryId(String wLotteryId) {
		this.wLotteryId = wLotteryId;
	}

	public String getwParam() {
		return wParam;
	}

	public void setwParam(String wParam) {
		this.wParam = wParam;
	}

	public String getwSign() {
		return wSign;
	}

	public void setwSign(String wSign) {
		this.wSign = wSign;
	}

	public String getwAgent() {
		return wAgent;
	}

	public void setwAgent(String wAgent) {
		this.wAgent = wAgent;
	}
	public String getStart() {
		return start;
	}
	public void setStart(String start) {
		this.start = start;
	}
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}
	public PlatformInfo getPlatformInfo() {
		return platformInfo;
	}
	public void setPlatformInfo(PlatformInfo platformInfo) {
		this.platformInfo = platformInfo;
	}
	public BigDecimal getSubscriptionCost() {
		return subscriptionCost;
	}
	public void setSubscriptionCost(BigDecimal subscriptionCost) {
		this.subscriptionCost = subscriptionCost;
	}
	public BigDecimal getBaodiCost() {
		return baodiCost;
	}
	public void setBaodiCost(BigDecimal baodiCost) {
		this.baodiCost = baodiCost;
	}
	public Float getCommissionRate() {
		return commissionRate;
	}
	public void setCommissionRate(Float commissionRate) {
		this.commissionRate = commissionRate;
	}
	public BigDecimal getMinSubscriptionCost() {
		return minSubscriptionCost;
	}
	public void setMinSubscriptionCost(BigDecimal minSubscriptionCost) {
		this.minSubscriptionCost = minSubscriptionCost;
	}
	public TicketPlatformInfo getTicketPlatformInfo() {
		return ticketPlatformInfo;
	}
	public void setTicketPlatformInfo(TicketPlatformInfo ticketPlatformInfo) {
		this.ticketPlatformInfo = ticketPlatformInfo;
	}
	public String getMatchDate() {
		return matchDate;
	}
	public void setMatchDate(String matchDate) {
		this.matchDate = matchDate;
	}
    
}