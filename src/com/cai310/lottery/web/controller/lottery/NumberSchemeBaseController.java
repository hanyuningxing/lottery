package com.cai310.lottery.web.controller.lottery;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.cai310.lottery.Constant;
import com.cai310.lottery.common.MobileInfoType;
import com.cai310.lottery.common.SalesMode;
import com.cai310.lottery.common.SecretType;
import com.cai310.lottery.common.ShareType;
import com.cai310.lottery.common.SubscriptionLicenseType;
import com.cai310.lottery.dto.lottery.NumberSchemeDTO;
import com.cai310.lottery.entity.info.MobileNewsData;
import com.cai310.lottery.entity.lottery.NumberScheme;
import com.cai310.lottery.entity.lottery.Period;
import com.cai310.lottery.entity.lottery.PeriodData;
import com.cai310.lottery.entity.lottery.SchemeTemp;
import com.cai310.lottery.entity.user.User;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.service.ServiceException;
import com.cai310.lottery.service.lottery.impl.PeriodDataEntityManagerImpl;
import com.cai310.lottery.support.CapacityChaseBean;
import com.cai310.lottery.support.ContentBean;
import com.cai310.lottery.support.Executable;
import com.cai310.lottery.support.ExecuteException;
import com.cai310.lottery.support.ExecutorUtils;
import com.cai310.lottery.support.RandomContentGenerator;
import com.cai310.lottery.support.RandomException;
import com.cai310.lottery.web.controller.GlobalResults;
import com.cai310.lottery.web.controller.WebDataException;
import com.cai310.utils.JsonUtil;
import com.cai310.utils.Struts2Utils;
import com.cai310.utils.WriteHTMLUtil;

public abstract class NumberSchemeBaseController<T extends NumberScheme, E extends NumberSchemeDTO, CF extends NumberSchemeCreateForm, UF extends SchemeUploadForm, P extends PeriodData>
		extends SchemeBaseController<T, E, CF, UF, SchemeTemp> {
	private static final long serialVersionUID = -5786839962202996759L;

	protected abstract RandomContentGenerator getRandomContentGenerator();

	public abstract PeriodDataEntityManagerImpl<P> getPeriodDataEntityManagerImpl();

	@Override
	protected SalesMode getDefaultSalesModeForList() {
		return SalesMode.COMPOUND;
	}

	protected Period newestPeriod;
	protected P newestPeriodData;
	protected P periodData;
	protected String compoundContentDetail;
	protected String commonSingleAnalyse;//普通号码单式推介
	protected String commonAnalyse;//普通号码复式推介
	protected String singleAnalyse;//特别号码复式推介
	protected String analyse;//特别号码复式推介
	/**
	 * 最新开奖号码和信息
	 */
	protected void searchNewestData() throws WebDataException {
		//读js文件
		String dir = "/html/js/data/" + getLotteryType().getKey() + "/";
		String period_data = WriteHTMLUtil.readFile(dir, "period_data.js");
		if(StringUtils.isNotBlank(period_data)){
			try{
					Map<String, Object> map = JsonUtil.getMap4Json(period_data);
					String period = String.valueOf(map.get("period"));
					newestPeriod  = JsonUtil.getObject4JsonString(period,Period.class);
					String periodData = String.valueOf(map.get("periodData"));
					newestPeriodData = JsonUtil.getObject4JsonString(periodData,getPeriodDataClass());
			}catch(Exception e){
					logger.warn(this.getLotteryType().getLotteryName()+"最新开奖文件解析错误");
			}
		}
		if(null==newestPeriodData||null==newestPeriod){
			newestPeriodData = getPeriodDataEntityManagerImpl().getNewestResultPeriodData();
			if (null != newestPeriodData) {
				newestPeriod = periodEntityManager.getPeriod(newestPeriodData.getPeriodId());
			}
		}
	}
	protected Integer getLotteryPlayType(){
		return 0;
	}
	protected void periodAnalyse(){
		if(null!=this.period){
			DetachedCriteria criteria  = DetachedCriteria.forClass(MobileNewsData.class, "m");
			criteria.add(Restrictions.eq("m.periodId", this.period.getId()));		
			criteria.add(Restrictions.eq("m.lotteryType", this.period.getLotteryType()));	
			criteria.add(Restrictions.eq("m.lotteryPlayType", getLotteryPlayType()));	
			criteria.add(Restrictions.eq("m.mobileInfoType", MobileInfoType.ZJJH));		
			List<MobileNewsData> list = this.queryService.findByDetachedCriteria(criteria);
			for (MobileNewsData mobileNewsData : list) {
				if(null!=mobileNewsData.getAnalyse()){
					if(mobileNewsData.getAnalyse().indexOf("|")!=-1){
						this.commonAnalyse=mobileNewsData.getAnalyse().split("\\|")[0];
						this.analyse=mobileNewsData.getAnalyse().split("\\|")[1];
					}else{
						this.commonAnalyse=mobileNewsData.getAnalyse();
					}
				}
				if(null!=mobileNewsData.getSingleAnalyse()){
					if(mobileNewsData.getSingleAnalyse().indexOf("|")!=-1){
						this.commonSingleAnalyse=mobileNewsData.getSingleAnalyse().split("\\|")[0];
						this.singleAnalyse=mobileNewsData.getSingleAnalyse().split("\\|")[1];
					}else{
						this.commonSingleAnalyse=mobileNewsData.getSingleAnalyse();
					}
				}
			}
		}
	}
    public abstract Class<P> getPeriodDataClass();
	@Override
	public String editNew() {
		try {
			this.preparePeriods(true);
			searchNewestData();
			periodAnalyse();
			return super.editNew();
		} catch (WebDataException e) {
			addActionError(e.getMessage());
		} catch (ServiceException e) {
			addActionError(e.getMessage());
		} catch (Exception e) {
			addActionError(e.getMessage());
			logger.warn(e.getMessage(), e);
		}

		return GlobalResults.FWD_ERROR;

	}
	/**
	 * 保存新方案_首页或者其他地方发起
	 */
	public String quick_create() {
		try {
			checkRepeatRequest();

			User loginUser = getLoginUser();
			if (loginUser == null) {
				throw new WebDataException("您还未登录，请先登录.");
			}
			
			final E schemeDTO = quick_buildSchemeDTO();

			ExecutorUtils.exec(new Executable() {
				public void run() throws ExecuteException {
					scheme = getSchemeService().createScheme(schemeDTO);
				}
			}, 3);

			addActionMessage("发起方案成功！");

			if (Struts2Utils.isAjaxRequest())
				this.jsonMap.put(KEY_REDIRECT_URL, getSchemeUrl(this.scheme));
			else {
				this.id = scheme.getId();
				this.jsonMap.put(KEY_REDIRECT_URL, getSchemeUrl(this.scheme));
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
	protected E quick_buildSchemeDTO() throws WebDataException {
		if (this.createForm == null)
			throw new WebDataException("表单数据为空.");
		E schemeDTO;
		try {
			schemeDTO = this.schemeDTOClass.newInstance();
		} catch (InstantiationException e) {
			this.logger.warn("创建数据传输对象发生异常.", e);
			throw new WebDataException("创建数据传输对象发生异常.");
		} catch (IllegalAccessException e) {
			this.logger.warn("创建数据传输对象发生异常.", e);
			throw new WebDataException("创建数据传输对象发生异常.");
		}
		User user = getLoginUser();
		if (user == null)
			throw new WebDataException("您还未登录,请登录后再操作.");
		schemeDTO.setSponsorId(user.getId());

		if (this.createForm.getPeriodId() == null)
			throw new WebDataException("期ID为空.");
		schemeDTO.setPeriodId(this.createForm.getPeriodId());

		schemeDTO.setMode(SalesMode.COMPOUND);
		this.createForm.setMode(SalesMode.COMPOUND);///匹配方案需要的参数
		
		schemeDTO.setSecretType(SecretType.FULL_SECRET);
		
		schemeDTO.setSubscriptionLicenseType(SubscriptionLicenseType.PUBLIC_LICENSE);
		
		schemeDTO.setSchemeCost(2);

		schemeDTO.setMultiple(1);

		schemeDTO.setUnits(1);
		this.createForm.setUnits(1);///匹配方案需要的参数

		int cost = schemeDTO.getUnits() * schemeDTO.getMultiple() * this.createForm.getUnitsMoney();
		if (cost != schemeDTO.getSchemeCost())
			throw new WebDataException("根据注数计算出来的金额与提交的方案金额不一致.");
		
		schemeDTO.setShareType(ShareType.SELF);
		
		this.createForm.setContent(this.createForm.buildContent());
		
		ContentBean contentBean;
		try {
				contentBean = this.createForm.buildContentBean();
		} catch (DataException e) {
				throw new WebDataException(e.getMessage());
		}
		if (contentBean == null || StringUtils.isBlank(contentBean.getContent()))
				throw new WebDataException("方案内容为空.");
		else if (!schemeDTO.getUnits().equals(contentBean.getUnits()))
				throw new WebDataException("根据方案内容计算出来的注数与提交的注数不一致.");
		schemeDTO.setContent(contentBean.getContent());
		
		return schemeDTO;
	}
	public String show() {
		try {
			searchNewestData();
			if (this.id != null)
				this.scheme = getSchemeEntityManager().getScheme(this.id);
			else {
				String schemeNumber = Struts2Utils.getRequest().getParameter("schemeNumber");
				if (StringUtils.isBlank(schemeNumber))
					throw new WebDataException("方案号为空.");
				this.scheme = getSchemeEntityManager().getSchemeBy(schemeNumber);
			}
			if (this.scheme == null)
				throw new WebDataException("方案不存在.");

			this.period = this.periodEntityManager.getPeriod(scheme.getPeriodId());
			if (this.period == null)
				throw new WebDataException("销售期不存在.");
			this.periodData = getPeriodDataEntityManagerImpl().getPeriodData(this.period.getId());
			if (this.scheme.getMode().equals(SalesMode.COMPOUND) && null != this.periodData
					&& StringUtils.isNotBlank(this.periodData.getResult())) {
				compoundContentDetail = this.scheme.getCompoundContentDetail(this.periodData.getResult());
			}
			return super.show();
		} catch (WebDataException e) {
			addActionError(e.getMessage());
		} catch (ServiceException e) {
			addActionError(e.getMessage());
		} catch (Exception e) {
			addActionError(e.getMessage());
			logger.warn(e.getMessage(), e);
		}

		return GlobalResults.FWD_ERROR;
	}

	/**
	 * 进入方案列表
	 */
	public String list() {
		try {
			searchNewestData();
			return super.list();
		} catch (WebDataException e) {
			addActionError(e.getMessage());
		} catch (ServiceException e) {
			addActionError(e.getMessage());
		} catch (Exception e) {
			addActionError(e.getMessage());
			logger.warn(e.getMessage(), e);
		}
		return GlobalResults.FWD_ERROR;
	}

	@Override
	public String myList() {
		try {
			searchNewestData();
			return super.myList();
		} catch (WebDataException e) {
			addActionError(e.getMessage());
		} catch (ServiceException e) {
			addActionError(e.getMessage());
		} catch (Exception e) {
			addActionError(e.getMessage());
			logger.warn(e.getMessage(), e);
		}
		return GlobalResults.FWD_ERROR;
	}

	@Override
	protected E buildSchemeDTO() throws WebDataException {
		if (this.createForm == null)
			throw new WebDataException("表单数据为空.");

		if (!this.createForm.isChase())
			return super.buildSchemeDTO();

		if (this.createForm.getShareType() != ShareType.SELF)
			throw new WebDataException("非自购方案不能追号.");

		if (this.createForm.isAheadOfUploadContent())
			throw new WebDataException("追号不允许先发起后上传.");

		if (this.createForm.getPeriodSizeOfChase() == null)
			throw new WebDataException("追号期数不能为空.");
		else if (this.createForm.getPeriodSizeOfChase() < 2)
			throw new WebDataException("追号期数不能小于2.");
		else if (this.createForm.getPeriodSizeOfChase() > Constant.CHASE_MAX_PERIOD_SIZE)
			throw new WebDataException("最多只允许追" + Constant.CHASE_MAX_PERIOD_SIZE + "期.");

		List<Integer> chaseMultiples = this.createForm.getMultiplesOfChase();
		if (chaseMultiples == null) {
			chaseMultiples = new ArrayList<Integer>(this.createForm.getPeriodSizeOfChase());
			for (int i = 0; i < this.createForm.getPeriodSizeOfChase(); i++) {
				chaseMultiples.add(this.createForm.getMultiple());
			}
		} else {
			int size = chaseMultiples.size();
			for (int i = chaseMultiples.size() - 1; i >= 0; i--) {
				Integer mul = chaseMultiples.get(i);
				if (mul != null && mul > 0) {
					size = i + 1;
					break;
				}
			}
			chaseMultiples = chaseMultiples.subList(0, size);
			if (chaseMultiples.size() < 2)
				throw new WebDataException("追号期数不能小于2.");
		}
		while (chaseMultiples.get(chaseMultiples.size() - 1) == 0) {
			chaseMultiples.remove(chaseMultiples.size() - 1);
		}

		Map<String, String> danMap = null;
		if (this.createForm.isRandomOfChase()) {
			this.createForm.setMode(SalesMode.SINGLE);

			if (this.createForm.getRandomUnitsOfChase() == null || this.createForm.getRandomUnitsOfChase() <= 0)
				throw new WebDataException("机选注数不能为空、小于或等于0.");
			else if (this.createForm.getRandomUnitsOfChase() > Constant.MAX_UNITS)
				throw new WebDataException("机选注数不能大于" + Constant.MAX_UNITS + "注.");

			if (this.createForm.isHasDanOfChase()) {
				try {
					danMap = this.createForm.getDanMap();
				} catch (DataException e) {
					throw new WebDataException(e.getMessage());
				}
			}

			List<String> contentList;
			try {
				contentList = getRandomContentGenerator().generate(this.createForm.getRandomUnitsOfChase(), danMap);
			} catch (RandomException e) {
				throw new WebDataException(e.getMessage());
			}

			this.createForm.setMultiple(chaseMultiples.get(0));
			this.createForm.setUnitsAndContent(this.createForm.getRandomUnitsOfChase(),
					StringUtils.join(contentList, "\r\n"));
			int schemeCost = this.createForm.getUnits() * this.createForm.getMultiple()
					* this.createForm.getUnitsMoney();
			this.createForm.setSchemeCost(schemeCost);

		}
		E schemeDTO = super.buildSchemeDTO();
		schemeDTO.setChase(true);
		schemeDTO.setMultiplesOfChase(chaseMultiples);
		schemeDTO.setRandomOfChase(this.createForm.isRandomOfChase());
		if (danMap != null) {
			schemeDTO.setHasDanOfChase(true);
			schemeDTO.setDanOfChase(JSONObject.fromObject(danMap).toString());
		}

		if (this.createForm.isWonStopOfChase()) {
			schemeDTO.setWonStopOfChase(true);
			if (this.createForm.getPrizeForWonStopOfChase() != null && this.createForm.getPrizeForWonStopOfChase() >= 0) {
				schemeDTO.setPrizeForWonStopOfChase(this.createForm.getPrizeForWonStopOfChase());
			} else {
				schemeDTO.setPrizeForWonStopOfChase(0);
			}
		}

		if (this.createForm.getTotalCostOfChase() == null) {
			throw new WebDataException("追号总金额不能为空.");
		}
		int costPerMult = schemeDTO.getUnits() * this.createForm.getUnitsMoney();// 单倍方案金额
		int totalCost = 0;
		for (Integer multiple : schemeDTO.getMultiplesOfChase()) {
			if (multiple != null) {
				if (multiple < 0)
					throw new WebDataException("追号倍数不能小于0.");
				if (multiple > Constant.MAX_MULTIPLE)
					throw new WebDataException("追号倍数不能大于" + Constant.MAX_MULTIPLE + ".");

				totalCost += costPerMult * multiple;
			}
		}
		if (totalCost != this.createForm.getTotalCostOfChase()) {
			throw new WebDataException("系统计算的追号总金额与提交的追号总金额不一致.");
		}
		schemeDTO.setTotalCostOfChase(totalCost);

		// TODO 设置追号收益信息
		CapacityChaseBean capacityChaseBean = new CapacityChaseBean();
		capacityChaseBean.setStartChasePeriodNum(this.createForm.getStartChasePeriodNum());
		capacityChaseBean.setPeriodSizeOfChase(String.valueOf(this.createForm.getPeriodSizeOfChase()));
		capacityChaseBean.setStartMultiple(this.createForm.getStartMultiple());
		capacityChaseBean.setHasInvested(this.createForm.getHasInvested());
		capacityChaseBean.setExpectedHit(this.createForm.getExpectedHit());
		if ("0".equals(Struts2Utils.getRequest().getParameter("lucreradio"))) {
			capacityChaseBean.setAllafterlucre(this.createForm.getAllafterlucre());
		} 
		if ("1".equals(Struts2Utils.getRequest().getParameter("lucreradio"))) {
			capacityChaseBean.setBefortermmember(this.createForm.getBefortermmember());
			capacityChaseBean.setBeforelc(this.createForm.getBeforelc());
			capacityChaseBean.setAferlc(this.createForm.getAferlc());
		}
		if ("0".equals(Struts2Utils.getRequest().getParameter("lucrepradio"))) {
			capacityChaseBean.setAll_lucrep_select(this.createForm.getAll_lucrep_select());
		} 
		if ("1".equals(Struts2Utils.getRequest().getParameter("lucrepradio"))) {
			capacityChaseBean.setBefortermmemberp(this.createForm.getBefortermmemberp());
			capacityChaseBean.setBefore_lcp_select(this.createForm.getBefore_lcp_select());
			capacityChaseBean.setAferlcp_select(this.createForm.getAferlcp_select());
		}
		schemeDTO.setCapacityProfit(JSONObject.fromObject(capacityChaseBean).toString());

		return schemeDTO;
	}

	public Period getNewestPeriod() {
		return newestPeriod;
	}

	public void setNewestPeriod(Period newestPeriod) {
		this.newestPeriod = newestPeriod;
	}

	public P getNewestPeriodData() {
		return newestPeriodData;
	}

	public void setNewestPeriodData(P newestPeriodData) {
		this.newestPeriodData = newestPeriodData;
	}

	public P getPeriodData() {
		return periodData;
	}

	public void setPeriodData(P periodData) {
		this.periodData = periodData;
	}

	public String getCompoundContentDetail() {
		return compoundContentDetail;
	}

	public void setCompoundContentDetail(String compoundContentDetail) {
		this.compoundContentDetail = compoundContentDetail;
	}

	public String getCommonSingleAnalyse() {
		return commonSingleAnalyse;
	}

	public void setCommonSingleAnalyse(String commonSingleAnalyse) {
		this.commonSingleAnalyse = commonSingleAnalyse;
	}

	public String getCommonAnalyse() {
		return commonAnalyse;
	}

	public void setCommonAnalyse(String commonAnalyse) {
		this.commonAnalyse = commonAnalyse;
	}

	public String getSingleAnalyse() {
		return singleAnalyse;
	}

	public void setSingleAnalyse(String singleAnalyse) {
		this.singleAnalyse = singleAnalyse;
	}

	public String getAnalyse() {
		return analyse;
	}

	public void setAnalyse(String analyse) {
		this.analyse = analyse;
	}
}
