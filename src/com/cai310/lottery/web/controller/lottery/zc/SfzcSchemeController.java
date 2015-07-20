package com.cai310.lottery.web.controller.lottery.zc;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.cai310.lottery.SfzcConstant;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.common.SalesMode;
import com.cai310.lottery.common.ShareType;
import com.cai310.lottery.dto.lottery.SchemeQueryDTO;
import com.cai310.lottery.dto.lottery.zc.SfzcSchemeDTO;
import com.cai310.lottery.entity.lottery.Period;
import com.cai310.lottery.entity.lottery.PeriodSales;
import com.cai310.lottery.entity.lottery.PeriodSalesId;
import com.cai310.lottery.entity.lottery.zc.SfzcMatch;
import com.cai310.lottery.entity.lottery.zc.SfzcScheme;
import com.cai310.lottery.entity.lottery.zc.SfzcSchemeTemp;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.service.ServiceException;
import com.cai310.lottery.service.lottery.SchemeEntityManager;
import com.cai310.lottery.service.lottery.SchemeService;
import com.cai310.lottery.service.lottery.zc.ZcMatchEntityManager;
import com.cai310.lottery.service.lottery.zc.impl.SfzcSchemeServiceImpl;
import com.cai310.lottery.service.lottery.zc.impl.SfzcSchemeTempEntityManagerImpl;
import com.cai310.lottery.support.zc.CombinationBean;
import com.cai310.lottery.support.zc.PlayType;
import com.cai310.lottery.support.zc.SchemeConverWork;
import com.cai310.lottery.support.zc.SfzcCompoundItem;
import com.cai310.lottery.support.zc.ZcCompoundItem;
import com.cai310.lottery.support.zc.ZcUtils;
import com.cai310.lottery.web.controller.GlobalResults;
import com.cai310.lottery.web.controller.WebDataException;
import com.cai310.lottery.web.controller.lottery.SchemeBaseController;
import com.cai310.orm.XDetachedCriteria;
import com.cai310.utils.DateUtil;
import com.cai310.utils.Struts2Utils;

@Namespace("/" + SfzcConstant.KEY)
@Action(value = "scheme")
public class SfzcSchemeController extends
		SchemeBaseController<SfzcScheme, SfzcSchemeDTO, SfzcSchemeCreateForm, SfzcSchemeUploadForm, SfzcSchemeTemp> {
	private static final long serialVersionUID = 5783479221989581469L;

	private PlayType playType;
	
	@Autowired
	private SfzcSchemeServiceImpl schemeService;

	@Resource
	private SchemeEntityManager<SfzcScheme> sfzcSchemeEntityManagerImpl;

	@Resource
	private ZcMatchEntityManager<SfzcMatch> sfzcMatchEntityManagerImpl;
	
	@Resource
	private SfzcSchemeTempEntityManagerImpl sfzcSchemeTempEntityManagerImpl;

	@Autowired
	@Qualifier("sfzcMatchCache")
	private Cache matchCache;

	@Override
	protected SchemeService<SfzcScheme, SfzcSchemeDTO> getSchemeService() {
		return schemeService;
	}

	@Override
	protected SchemeEntityManager<SfzcScheme> getSchemeEntityManager() {
		return sfzcSchemeEntityManagerImpl;
	}
	
	@Override
	protected SfzcSchemeTempEntityManagerImpl getSchemeTempEntityManager() {
		return sfzcSchemeTempEntityManagerImpl;
	}

	// -------------------------------------------------------
	@Override
	public String list() {
		if (playType == null)
			playType = PlayType.SFZC14;
		return super.list();
	}

	@Override
	protected XDetachedCriteria buildListDetachedCriteria() {
		XDetachedCriteria c = super.buildListDetachedCriteria();
		if (playType == null)
			playType = PlayType.SFZC14;

		c.add(Restrictions.eq("m.playType", playType));

		return c;
	}
	@Override
	protected XDetachedCriteria buildFilterListDetachedCriteria() {
		XDetachedCriteria c = super.buildFilterListDetachedCriteria();
		if (playType == null)
			playType = PlayType.SFZC14;

		c.add(Restrictions.eq("m.playType", playType));

		return c;
	}
	@Override
	protected String doEditNew() throws Exception {
		if (playType == null)
			playType = PlayType.SFZC14;

		SfzcMatch[] matchs = findMatchsOfCacheable(this.period.getId());
		if (matchs == null)
			throw new WebDataException("对阵不存在.");
		Struts2Utils.setAttribute("matchs", matchs);
		
		PeriodSales periodSales_SINGLE = this.periodEntityManager.getPeriodSales(new PeriodSalesId(this.period.getId(), SalesMode.SINGLE));
		PeriodSales periodSales_COMPOUND = this.periodEntityManager.getPeriodSales(new PeriodSalesId(this.period.getId(), SalesMode.COMPOUND));
		Struts2Utils.setAttribute("singleEndTime", periodSales_SINGLE.getSaleEndTime(ShareType.TOGETHER));
		Struts2Utils.setAttribute("compoundEndTime", periodSales_COMPOUND.getSaleEndTime(ShareType.TOGETHER));
		return super.doEditNew();
	}

	protected SfzcMatch[] findMatchsOfCacheable(Long periodId) {
		Element el = matchCache.get(periodId);
		if (el == null) {
			SfzcMatch[] matchs = sfzcMatchEntityManagerImpl.findMatchs(periodId);
			if (matchs != null)
				matchCache.put(new Element(periodId, matchs));

			return matchs;
		} else {
			return (SfzcMatch[]) el.getValue();
		}
	}
	
	public String wonListTable() {				
		XDetachedCriteria criteria = super.buildWonListQueryCriteria();

		if(this.getPlayType()!=null)
		criteria.add(Restrictions.eq("m.playType", this.getPlayType()));
			
		this.pagination = queryService.findByCriteriaAndPagination(criteria, this.pagination);
		
		return "wonListTable";
	}
	
	protected String getKey(Date time) {
		Calendar c = Calendar.getInstance();
		c.setTime(time);
		c.add(Calendar.HOUR_OF_DAY, -10);
		String dateStr = DateUtil.dateToStr(c.getTime(), "yyyy-MM-dd");
		String weedStr = com.cai310.utils.DateUtil.getWeekStr(c);
		StringBuilder sb = new StringBuilder();
		sb.append(dateStr).append(" ").append(weedStr);
		return sb.toString();
	}
	
	/**
	 * 构建免费保存方案实体
	 */
	@Override
	protected SfzcSchemeTemp buildSchemeTemp(SfzcSchemeDTO schemeDTO) throws WebDataException{
		SfzcSchemeTemp schemeTemp = super.buildSchemeTemp(schemeDTO);		
		schemeTemp.setPlayType(schemeDTO.getPlayType());
		return schemeTemp;
	}
	
	@Override
	protected SfzcSchemeCreateForm supplementCreateFormData() throws WebDataException{		
		this.createForm = super.supplementCreateFormData();
		String schemeIdStr = Struts2Utils.getParameter("tempSchemeId");
		if(schemeIdStr==null){
			throw new WebDataException("操作的方案标识为空.");
		}
		Long schemeId = Long.valueOf(schemeIdStr);
		this.schemeTemp = sfzcSchemeTempEntityManagerImpl.getScheme(schemeId);
		if(schemeTemp==null){
			throw new WebDataException("保存的方案 id["+schemeIdStr+"]未能找到.");
		}
		try{
			switch(this.createForm.getMode()){
			case COMPOUND:
				SfzcCompoundItem[] items= schemeTemp.getCompoundContent();
				this.createForm.setItems(items);
				break;
			case SINGLE:
				this.createForm.setContent(schemeTemp.getContent());
				break;
			default:
				throw new DataException("方案投注方式不合法！");
			}
			return this.createForm;
		}catch (DataException e) {
			throw new WebDataException(e.getMessage());
		}
	}
	
	@Override
	protected String doShowTemp() throws WebDataException {		
		try {
			if (schemeTemp == null) {
				throw new DataException("没有找到您保存的方案！");
			}
			Date endTime = null;
			this.period = this.periodEntityManager.getPeriod(schemeTemp.getPeriodId());
			PeriodSales periodSales = this.periodEntityManager.getPeriodSales(new PeriodSalesId(this.period.getId(),this.schemeTemp.getMode()));
			switch(schemeTemp.getMode()){
				case COMPOUND:
					SfzcCompoundItem[] betItems = schemeTemp.getCompoundContent();
					if (schemeTemp.getPlayType() == PlayType.SFZC9) {
						int dans = 0, unDans = 0;
						for (SfzcCompoundItem item : betItems) {
							if (item.selectedCount() > 0) {
								if (item.isShedan()) {
									dans++;
								} else {
									unDans++;
								}
							}
						}
						Struts2Utils.setAttribute("dans", dans);
						Struts2Utils.setAttribute("unDans", unDans);
						Struts2Utils.setAttribute("danMinHit", scheme.getDanMinHit());
					}
					Struts2Utils.setAttribute("items", betItems);
					endTime = periodSales.getShareEndInitTime();
					break;
				case SINGLE:
					endTime = periodSales.getSelfEndInitTime();
					break;
				default:
					throw new DataException("投注方式不合法！");
			}
			SfzcMatch[] matchs = findMatchsOfCacheable(schemeTemp.getPeriodId());
			Struts2Utils.setAttribute("matchs", matchs);
			Struts2Utils.setAttribute("firstMatchTime", endTime);
			return super.doShowTemp();
		} catch (DataException e) {
			addActionError(e.getMessage());
			return GlobalResults.FWD_ERROR;
		}		
	}

	@Override
	protected SfzcSchemeDTO buildSchemeDTO() throws WebDataException {
		if (createForm == null)
			throw new WebDataException("表单数据为空.");

		if (playType == null)
			throw new WebDataException("玩法类型不能为空.");

		if (createForm.getItems() == null || createForm.getItems().length == 0)
			throw new WebDataException("投注内容不能为空.");

		SfzcSchemeDTO dto = super.buildSchemeDTO();
		dto.setPlayType(playType);
		return dto;
	}

	@Override
	public String myList() {
		if (playType == null)
			playType = PlayType.SFZC14;
		return super.myList();
	}

	@Override
	protected SchemeQueryDTO buildMySchemeQueryDTO() {
		SchemeQueryDTO dto = super.buildMySchemeQueryDTO();
		if (playType != null) {
			dto.setPlayType(playType.name());
		}
		return dto;
	}

	@Override
	protected String doShow() throws WebDataException {
		try {
			if (scheme == null) {
				throw new DataException("没有找到该方案！");
			}
			Date endTime = null;
			this.period = this.periodEntityManager.getPeriod(scheme.getPeriodId());
			PeriodSales periodSales = this.periodEntityManager.getPeriodSales(new PeriodSalesId(this.period.getId(),this.scheme.getMode()));
			
			switch(scheme.getMode()){
				case COMPOUND:
					SfzcCompoundItem[] betItems = scheme.getCompoundContent();
					if (scheme.getPlayType() == PlayType.SFZC9) {
						int dans = 0, unDans = 0;
						for (SfzcCompoundItem item : betItems) {
							if (item.selectedCount() > 0) {
								if (item.isShedan()) {
									dans++;
								} else {
									unDans++;
								}
							}
						}
						Struts2Utils.setAttribute("dans", dans);
						Struts2Utils.setAttribute("unDans", unDans);
						Struts2Utils.setAttribute("danMinHit", scheme.getDanMinHit());
					}
					Struts2Utils.setAttribute("items", betItems);
					endTime = periodSales.getShareEndInitTime();
					break;
				case SINGLE:
					endTime = periodSales.getSelfEndInitTime();
					break;
				default:
					throw new DataException("投注方式不合法！");
			}
			SfzcMatch[] matchs = findMatchsOfCacheable(scheme.getPeriodId());
			Struts2Utils.setAttribute("matchs", matchs);
			String timeStr = com.cai310.lottery.utils.DateUtil.remainTime(endTime);
			Struts2Utils.setAttribute("remainTime", timeStr);
			super.remainTimeOfScheme(endTime);
			Struts2Utils.setAttribute("firstMatchTime", endTime);
			return super.doShow();
		} catch (DataException e) {
			addActionError(e.getMessage());
			return GlobalResults.FWD_ERROR;
		}
	}

	public String combinations() {
		try {
			if (id == null)
				throw new WebDataException("方案ID不能为空.");

			scheme = getSchemeEntityManager().getScheme(id);
			if (scheme == null)
				throw new WebDataException("方案不存在.");

			SfzcCompoundItem[] betItems = scheme.getCompoundContent();
			List<SfzcCompoundItem> danList = new ArrayList<SfzcCompoundItem>();
			List<SfzcCompoundItem> unDanList = new ArrayList<SfzcCompoundItem>();
			for (SfzcCompoundItem item : betItems) {
				if (item.selectedCount() > 0) {
					if (item.isShedan()) {
						danList.add(item);
					} else {
						unDanList.add(item);
					}
				}
			}
			SchemeConverWork<SfzcCompoundItem> work = new SchemeConverWork<SfzcCompoundItem>(
					ZcUtils.SFZC9_MATCH_MINSELECT_COUNT, danList, unDanList, scheme.getDanMinHit(), -1);
			List<CombinationBean> combinations = new ArrayList<CombinationBean>();
			ZcCompoundItem[] itemStandard = null;

			CombinationBean combBean = null;// 组合方案
			Map<String, CombinationBean> wonCombinationMap = scheme.getWonCombinationMap();// 中奖组合

			for (List<SfzcCompoundItem> items : work.getResultList()) {
				itemStandard = ZcUtils.getStandardSfItems(items);
				combBean = new CombinationBean();
				SfzcCompoundItem[] sfItems = new SfzcCompoundItem[itemStandard.length];
				for (int i = 0; i < itemStandard.length; i++) {
					sfItems[i] = (SfzcCompoundItem) itemStandard[i];
				}
				combBean.setItems(sfItems);
				if (wonCombinationMap != null && wonCombinationMap.containsKey(combBean.generateKey())) {
					combinations.add(wonCombinationMap.get(combBean.generateKey()));
				} else {
					combinations.add(combBean);
				}
			}

			// 分页组合详情
			pagination.setPageSize(20);
			pagination.setTotalCount(combinations.size());
			int formIndex = pagination.getFirst();
			int toIndex = formIndex + pagination.getPageSize();
			if (toIndex > combinations.size())
				toIndex = combinations.size();
			pagination.setResult(combinations.subList(formIndex, toIndex));

			return "combinations";
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
	 * 玩法介绍
	 */
	@Override
	public String introduction() throws WebDataException {
		if (PlayType.SFZC14.equals(playType)) {
			// /14
			return "introduction-SFZC14";
		} else {
			// /9
			return "introduction-SFZC9";
		}

	}

	@Override
	public String protocol() throws WebDataException {
		if (PlayType.SFZC14.equals(playType)) {
			// /14
			return "protocol-SFZC14";
		} else {
			// /9
			return "protocol-SFZC9";
		}

	}

	// -------------------------------------------------------

	/**
	 * @return {@link #createForm}
	 */
	public SfzcSchemeCreateForm getCreateForm() {
		return createForm;
	}

	/**
	 * @param createForm the {@link #createForm} to set
	 */
	public void setCreateForm(SfzcSchemeCreateForm createForm) {
		this.createForm = createForm;
	}

	/**
	 * @return {@link #uploadForm}
	 */
	public SfzcSchemeUploadForm getUploadForm() {
		return uploadForm;
	}

	/**
	 * @param uploadForm the {@link #uploadForm} to set
	 */
	public void setUploadForm(SfzcSchemeUploadForm uploadForm) {
		this.uploadForm = uploadForm;
	}

	@Override
	public Lottery getLotteryType() {
		return Lottery.SFZC;
	}

	/**
	 * @return {@link #playType}
	 */
	public PlayType getPlayType() {
		return playType;
	}

	/**
	 * @param playType the {@link #playType} to set
	 */
	public void setPlayType(PlayType playType) {
		this.playType = playType;
	}


}
