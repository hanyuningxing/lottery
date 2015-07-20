package com.cai310.lottery.web.controller.admin.lottery.zc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import com.cai310.lottery.common.EventLogType;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.entity.lottery.zc.ZcMatch;
import com.cai310.lottery.entity.lottery.zc.ZcPeriodData;
import com.cai310.lottery.entity.security.AdminUser;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.fetch.zc.LczcPassFetchParser;
import com.cai310.lottery.fetch.zc.SczcPassFetchParser;
import com.cai310.lottery.fetch.zc.SfzcPassFetchParser;
import com.cai310.lottery.fetch.zc.ZcAbstractFetchParser;
import com.cai310.lottery.service.ServiceException;
import com.cai310.lottery.service.info.IndexInfoDataEntityManager;
import com.cai310.lottery.service.info.PasscountService;
import com.cai310.lottery.service.lottery.PeriodDataEntityManager;
import com.cai310.lottery.service.lottery.zc.ZcMatchEntityManager;
import com.cai310.lottery.web.controller.WebDataException;
import com.cai310.lottery.web.controller.admin.lottery.PeriodController;
import com.cai310.utils.RegexUtils;
import com.cai310.utils.Struts2Utils;

public abstract class ZcPeriodController<T extends ZcMatch, V extends ZcPeriodData> extends PeriodController {
	private static final long serialVersionUID = -4693805637373277840L;

	@Resource
	protected IndexInfoDataEntityManager indexInfoDataEntityManager;
	
	protected T[] matchs;

	protected V periodData;
	@Resource
	protected PasscountService passcountServiceImpl;
	protected abstract ZcMatchEntityManager<T> getZcMatchEntityManager();

	protected abstract PeriodDataEntityManager<V> getPeriodDataEntityManager();	

	protected abstract void instanceZcPeriodData();

	protected abstract void initZcMatchs();

	protected abstract String getMatchResult();
	
	protected abstract void passcount(long periodId);
	@Override
	public String doEdit() {
		this.loadZcPeriodInfo();
		return super.doEdit();
	}

	public void prepareUpdateMatch() throws Exception {
		String id = Struts2Utils.getParameter("id");
		if (StringUtils.isNotBlank(id) && id.matches(RegexUtils.Number)) {
			period = periodManager.getPeriod(Long.valueOf(id));
			if (period != null) {
				matchs = this.getZcMatchEntityManager().findMatchs(period.getId());
				if (matchs != null && matchs.length == 0) {
					this.initZcMatchs();
				}
			}
		}
	}

	public String updateMatch() {
		try {
			AdminUser adminUser = getAdminUser();
			if (null == adminUser) {
				throw new WebDataException("权限不足！");
			}
			if (this.id == null)
				throw new WebDataException("期ID不能为空.");

			period = periodManager.getPeriod(id);
			if (period == null)
				throw new WebDataException("销售期不存在.");
            int i=1;
			for (ZcMatch m : matchs) {
				m.setPeriodId(period.getId());
				m.setPeriodNumber(period.getPeriodNumber());
				if (StringUtils.isBlank(m.getGameName()))
					throw new WebDataException("第"+i+"场联赛名不能为空.");
				if (null==m.getMatchTime())
					throw new WebDataException("第"+i+"场比赛时间不能为空.");
				if (StringUtils.isBlank(m.getHomeTeamName()))
					throw new WebDataException("第"+i+"场主队不能为空.");
				if (StringUtils.isBlank(m.getGuestTeamName()))
					throw new WebDataException("第"+i+"场客队不能为空.");
				i++;
				
			}
			StringBuffer sb = new StringBuffer();
			for (T match : matchs) {
				sb.append(match.toString());
			}
			this.getZcMatchEntityManager().saveMatchs(matchs);
			eventLogManager.saveSimpleEventLog(period,getLotteryType(), adminUser, EventLogType.UpdateMatch, "期Id:【"+id+"】,期实体："+period.toString()+"对阵实体："+sb.toString());
			addActionMessage("更新对阵成功.");
			//执行过关统计
			if(StringUtils.isNotBlank(Struts2Utils.getParameter("isPasscount"))){
				this.passcount(period.getId());
				period.setPasscount(Boolean.TRUE);
				periodManager.savePeriod(period);
				this.passcountServiceImpl.createPasscountXml(period);
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
		   ZcAbstractFetchParser zcAbstractFetchParser=null;
		   if(period.getLotteryType().equals(Lottery.SFZC)){
			   zcAbstractFetchParser =  new SfzcPassFetchParser();
		   }else if(period.getLotteryType().equals(Lottery.LCZC)){
			   zcAbstractFetchParser =  new LczcPassFetchParser();
		   }else if(period.getLotteryType().equals(Lottery.SCZC)){
			   zcAbstractFetchParser =  new SczcPassFetchParser();
		   }else{
			   throw new WebDataException("彩种错误.");
		   }
		   
		   List<ZcMatch> list =zcAbstractFetchParser.fetch(period.getPeriodNumber());
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

	public void prepareUpdatePeriodData() {
		String periodId = Struts2Utils.getRequest().getParameter("id");
		if (StringUtils.isNotBlank(periodId) && periodId.matches(RegexUtils.Number)) {
			periodData = this.getPeriodDataEntityManager().getPeriodData(Long.valueOf(periodId));
			if (periodData == null) {
				this.instanceZcPeriodData();
				periodData.setPeriodId(Long.valueOf(periodId));
			}
		}
	}

	public String updatePeriodData() {
		try {
			AdminUser adminUser = getAdminUser();
			if (null == adminUser) {
				throw new WebDataException("权限不足！");
			}
			if (this.periodData != null) {
				period = periodManager.getPeriod(periodData.getPeriodId());
				if (period == null) {
					throw new DataException("期次不存在!");
				}
				matchs = this.getZcMatchEntityManager().findMatchs(periodData.getPeriodId());
				periodData.setResult(this.getMatchResult());
				StringBuffer sb = new StringBuffer();
				for (T match : matchs) {
					sb.append(match.toString());
				}
				eventLogManager.saveSimpleEventLog(period,getLotteryType(), adminUser, EventLogType.UpdateResult, "开奖号码为"+this.getMatchResult()+"期Id:【"+id+"】,期实体："+period.toString()+"对阵实体："+sb.toString());
				this.periodData = this.getPeriodDataEntityManager().save(periodData);
				indexInfoDataEntityManager.makeZcNewResult();
			}
		} catch (Exception e) {
			String errMsg = "加载期数据出错！";
			logger.warn(errMsg, e);
			return "error";
		}
		addActionMessage("更新期开奖数据成功.");
		return success();
	}

	public String update() throws Exception {
		this.loadZcPeriodInfo();
		return super.update();
	}

	/**
	 * 加载或初始化足彩相关信息，对阵及期配置
	 */
	private void loadZcPeriodInfo() {
		if (period != null) {
			matchs = this.getZcMatchEntityManager().findMatchs(period.getId());
			periodData = this.getPeriodDataEntityManager().getPeriodData(period.getId());
			if (periodData == null)
				this.instanceZcPeriodData();
		}
	}

	public V getPeriodData() {
		return periodData;
	}

	public void setPeriodData(V periodData) {
		this.periodData = periodData;
	}

}
