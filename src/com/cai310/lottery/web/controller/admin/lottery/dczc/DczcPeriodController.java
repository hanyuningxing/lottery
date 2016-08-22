package com.cai310.lottery.web.controller.admin.lottery.dczc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.cai310.lottery.DczcConstant;
import com.cai310.lottery.common.EventLogType;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.entity.lottery.dczc.DczcMatch;
import com.cai310.lottery.entity.security.AdminUser;
import com.cai310.lottery.fetch.dczc.DczcKaijiang;
import com.cai310.lottery.fetch.dczc.DczcKaijiangFetchParser;
import com.cai310.lottery.fetch.dczc.DczcKaijiangOkoooFetchParser;
import com.cai310.lottery.fetch.dczc.DczcPassFetchParser;
import com.cai310.lottery.fetch.dczc.DczcWin310ResultFetchParser;
import com.cai310.lottery.service.ServiceException;
import com.cai310.lottery.service.lottery.dczc.DczcMatchEntityManager;
import com.cai310.lottery.utils.CommonUtil;
import com.cai310.lottery.web.controller.WebDataException;
import com.cai310.lottery.web.controller.admin.lottery.PeriodController;
import com.cai310.utils.RegexUtils;
import com.cai310.utils.Struts2Utils;
import com.google.common.collect.Lists;

@Namespace("/admin/lottery/" + DczcConstant.KEY)
@Action("period")
@Results( { @Result(name = "edit", location = "/WEB-INF/content/admin/lottery/dczc/period-edit.ftl") })
public class DczcPeriodController extends PeriodController {
	private static final long serialVersionUID = -4693805637373277840L;
	protected final transient Logger logger = LoggerFactory.getLogger(this.getClass());
	private List<DczcMatch> matchs;

	@Autowired
	private DczcMatchEntityManager matchEntityManager;
	
	@Override
	public Lottery getLotteryType() {
		return Lottery.DCZC;
	}

	@Override
	public String doEdit() {
		if (period != null)
			matchs = matchEntityManager.findMatchs(period.getId());

		return super.doEdit();
	}
	public String fetchMatch() throws Exception {
    	Map<String, Object> map = new HashMap<String, Object>();
		try {
		   AdminUser adminUser = getAdminUser();
		   if (null == adminUser) {
				throw new WebDataException("你还没有登录！");
		   }
		   if (this.id == null)
				throw new WebDataException("期ID不能为空.");
		   period = periodManager.getPeriod(id);
		   if (period == null)
				throw new WebDataException("销售期不存在.");
		   DczcPassFetchParser dczcPassFetchParser= new DczcPassFetchParser();;
		   
		   
		   List<DczcMatch> list =dczcPassFetchParser.fetch(period.getPeriodNumber());
		   Integer temp= 0;
		   for (DczcMatch dczcMatch : list) {
               if(dczcMatch.getLineId()>temp){
            	   temp = dczcMatch.getLineId();
               }
	       }
		   map.put("size", temp);
		   map.put("list", list);
		   map.put("success", true);
		} catch (WebDataException e) {
			e.printStackTrace();
			logger.warn(e.getMessage());
			map.put("success", false);
			map.put("msg", e.getMessage());
		 }catch (Exception e) {
			e.printStackTrace();
			logger.warn(e.getMessage());
			map.put("success", false);
			map.put("msg", "捉取发生异常！");
		}
		Struts2Utils.renderJson(map);
		return null;
	}
    public String dczcOdds() throws Exception {
    	final double rangeOfSp = 0.01d;//比对sp的误差范围
    	Map<String, Object> map = new HashMap<String, Object>();
		try {
		   AdminUser adminUser = getAdminUser();
		   if (null == adminUser) {
				throw new WebDataException("你还没有登录！");
		   }
    	   period = periodManager.getPeriod(period.getId());
    	   matchs = matchEntityManager.findMatchs(period.getId());
    	   List<String> matchKeysUnEnd = Lists.newArrayList();
    	   for(DczcMatch match : matchs){
    		   if(!match.isEnded()){
    			   matchKeysUnEnd.add(period.getPeriodNumber()+(match.getLineId()+1));
    		   }
    	   }
    	   
		   DczcWin310ResultFetchParser dczcWin310ResultFetchParser = new DczcWin310ResultFetchParser();
		   matchs=dczcWin310ResultFetchParser.fetch(period.getPeriodNumber().trim());
//		   DczcKaijiangFetchParser _500FetchParser = new DczcKaijiangFetchParser();
		   DczcKaijiangOkoooFetchParser _500FetchParser = new DczcKaijiangOkoooFetchParser();
		   Map matchMap = _500FetchParser.fetch(period.getPeriodNumber().trim());	
		   DczcKaijiang dk =null;
		   String key="";	 
			Integer lineNum =1;
			int biduiCount =0;
			List<DczcMatch> resultMatchList = Lists.newArrayList();
			if(matchs!=null){
			   for(DczcMatch dc:matchs){
				  lineNum=dc.getLineId()+1;
				  key=period.getPeriodNumber()+lineNum;
				  if(matchKeysUnEnd.contains(key)){
					  if(matchMap.containsKey(key)){
						  dk = (DczcKaijiang)matchMap.get(key);
						  biduiCount++;
						  if(dc.getBfResultSp()!=null ){		  
							  if(Math.abs(CommonUtil.floorPrize(dc.getBfResultSp())-CommonUtil.floorPrize(dk.getBifen()))>rangeOfSp){
								  dc.setBfResultSp(-dc.getBfResultSp());
								  logger.debug(dc.getBfResultSp()+" "+String.valueOf(dk.getBifen())); 
							  }else{
								  logger.debug(dc.getBfResultSp()+" "+String.valueOf(dk.getBifen()));
							  }
						  } 
						  if(dc.getSpfResultSp()!=null){
							  if(Math.abs(CommonUtil.floorPrize(dc.getSpfResultSp())-CommonUtil.floorPrize(dk.getRqspf()))>rangeOfSp){
								  dc.setSpfResultSp(-dc.getSpfResultSp());
								  logger.debug(dc.getSpfResultSp()+" "+String.valueOf(dk.getRqspf()));
							  }
						  }
						  if(dc.getSxdsResultSp()!=null){
							  if(Math.abs(CommonUtil.floorPrize(dc.getSxdsResultSp())-CommonUtil.floorPrize(dk.getSxds()))>rangeOfSp){
								  dc.setSxdsResultSp(-dc.getSxdsResultSp());
								  logger.debug(dc.getSxdsResultSp()+" "+String.valueOf(dk.getSxds()));					 
							  }
						  }
						  if(dc.getBqqspfResultSp()!=null){
							  if(Math.abs(CommonUtil.floorPrize(dc.getBqqspfResultSp())-CommonUtil.floorPrize(dk.getBqc()))>rangeOfSp){
								  dc.setBqqspfResultSp(-dc.getBqqspfResultSp());
								  logger.debug(dc.getBqqspfResultSp()+" "+String.valueOf(dk.getBqc()));
							  }
						  }
						  if(dc.getZjqsResultSp()!=null){
							  if(Math.abs(CommonUtil.floorPrize(dc.getZjqsResultSp())-CommonUtil.floorPrize(dk.getZjq()))>rangeOfSp){
								  dc.setZjqsResultSp(-dc.getZjqsResultSp());
								  logger.debug(dc.getZjqsResultSp()+" "+String.valueOf(dk.getZjq()));
							  }
						  }
						  resultMatchList.add(dc);						  
					  }else{
						  System.out.println("yyyyyyyyyyyyyy");
					  }	  
				  }
				  
			  }
		}
    	   if (matchs != null&&StringUtils.isNotBlank(period.getPeriodNumber())){		  
    		   map.put("dczcMatch_sp_bidui_result", "比对完成，应该比对"+matchs.size()+"场，实际比对"+biduiCount+"场");
			   map.put("success", true);
			   map.put("dczcMatch_sp", resultMatchList);
    	   }else{
    		   throw new WebDataException("读不到期数据信息！");
    	   }
		} catch (WebDataException e) {
			e.printStackTrace();
			logger.warn(e.getMessage());
			map.put("success", false);
			map.put("msg", e.getMessage());
		 }catch (Exception e) {
			e.printStackTrace();
			logger.warn(e.getMessage());
			map.put("success", false);
			map.put("msg", "更新sp发生异常！");
		}
		Struts2Utils.renderJson(map);
		return null;
	}
	public void prepareUpdateMatch() throws Exception {
		String id = Struts2Utils.getParameter("id");
		if (StringUtils.isNotBlank(id) && id.matches(RegexUtils.Number)) {
			period = periodManager.getPeriod(Long.valueOf(id));
			if (period != null) {
				String matchCountStr = Struts2Utils.getParameter("matchCount");
				if (StringUtils.isNotBlank(matchCountStr) && matchCountStr.matches(RegexUtils.Number)) {
					Integer matchCount = Integer.valueOf(matchCountStr);
					if (matchCount > 0) {
						matchs = matchEntityManager.findMatchs(period.getId());
						if (matchs != null && matchs.size() > matchCount) {
							matchs = matchs.subList(0, matchCount);
						}
					}
				}
			}
		}
	}
	
	protected void passcount(long periodId){
		////执行过关统计
	}
	public String updateMatch() {
		try {
			AdminUser adminUser = getAdminUser();
			if (null == adminUser) {
				throw new WebDataException("权限不足！");
			}
			if (this.id == null)
				throw new WebDataException("期ID不能为空.");

			String matchCountStr = Struts2Utils.getParameter("matchCount");
			if (StringUtils.isBlank(matchCountStr) || !matchCountStr.matches(RegexUtils.Number))
				throw new WebDataException("场次数目为空或不是数字.");

			period = periodManager.getPeriod(id);
			if (period == null)
				throw new WebDataException("销售期不存在.");

			for (DczcMatch m : matchs) {
				m.setPeriodId(period.getId());
				m.setPeriodNumber(period.getPeriodNumber());
			}
			matchEntityManager.saveMatchs(matchs);
			eventLogManager.saveSimpleEventLog(period,getLotteryType(), adminUser, EventLogType.UpdateMatch, "期Id:【"+id+"】,期实体："+period.toString()+"对阵实体："+matchs.toString());
			
			addActionMessage("更新对阵成功.");
			//执行过关统计
			if(StringUtils.isNotBlank(Struts2Utils.getParameter("isPasscount"))){
				this.passcount(period.getId());
				period.setPasscount(Boolean.TRUE);
				periodManager.savePeriod(period);
				addActionMessage("过关统计操作成功.");
			}
			return success();
		} catch (WebDataException e) {
			addActionError(e.getMessage());
		} catch (ServiceException e) {
			addActionError(e.getMessage());
		} catch (Exception e) {
			addActionError(e.getMessage());
			logger.warn(e.getMessage(), e);
		}

		return error();
	}

	/**
	 * @return {@link #matchs}
	 */
	public List<DczcMatch> getMatchs() {
		return matchs;
	}

	/**
	 * @param matchs the {@link #matchs} to set
	 */
	public void setMatchs(List<DczcMatch> matchs) {
		this.matchs = matchs;
	}
 

}
