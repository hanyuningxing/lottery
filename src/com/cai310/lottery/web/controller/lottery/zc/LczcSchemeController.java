package com.cai310.lottery.web.controller.lottery.zc;

import java.util.Calendar;
import java.util.Date;

import javax.annotation.Resource;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.cai310.lottery.LczcConstant;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.common.SalesMode;
import com.cai310.lottery.common.ShareType;
import com.cai310.lottery.dto.lottery.zc.LczcSchemeDTO;
import com.cai310.lottery.dto.lottery.zc.SczcSchemeDTO;
import com.cai310.lottery.entity.lottery.Period;
import com.cai310.lottery.entity.lottery.PeriodSales;
import com.cai310.lottery.entity.lottery.PeriodSalesId;
import com.cai310.lottery.entity.lottery.zc.LczcMatch;
import com.cai310.lottery.entity.lottery.zc.LczcScheme;
import com.cai310.lottery.entity.lottery.zc.LczcSchemeTemp;
import com.cai310.lottery.entity.lottery.zc.SczcMatch;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.service.lottery.SchemeEntityManager;
import com.cai310.lottery.service.lottery.SchemeService;
import com.cai310.lottery.service.lottery.zc.ZcMatchEntityManager;
import com.cai310.lottery.service.lottery.zc.impl.LczcSchemeServiceImpl;
import com.cai310.lottery.service.lottery.zc.impl.LczcSchemeTempEntityManagerImpl;
import com.cai310.lottery.support.zc.LczcCompoundItem;
import com.cai310.lottery.support.zc.SfzcCompoundItem;
import com.cai310.lottery.web.controller.GlobalResults;
import com.cai310.lottery.web.controller.WebDataException;
import com.cai310.lottery.web.controller.lottery.SchemeBaseController;
import com.cai310.orm.XDetachedCriteria;
import com.cai310.utils.DateUtil;
import com.cai310.utils.Struts2Utils;

@Namespace("/" + LczcConstant.KEY)
@Action(value = "scheme")
public class LczcSchemeController extends
		SchemeBaseController<LczcScheme, LczcSchemeDTO, LczcSchemeCreateForm, LczcSchemeUploadForm, LczcSchemeTemp> {
	private static final long serialVersionUID = 5783479221989581469L;

	
	@Autowired
	private LczcSchemeServiceImpl schemeService;

	@Resource
	private SchemeEntityManager<LczcScheme> lczcSchemeEntityManagerImpl;

	@Resource
	private ZcMatchEntityManager<LczcMatch> lczcMatchEntityManagerImpl;
	
	@Autowired
	private LczcSchemeTempEntityManagerImpl lczcSchemeTempEntityManagerImpl;

	@Autowired
	@Qualifier("lczcMatchCache")
	private Cache matchCache;

	@Override
	protected SchemeService<LczcScheme, LczcSchemeDTO> getSchemeService() {
		return schemeService;
	}

	@Override
	protected SchemeEntityManager<LczcScheme> getSchemeEntityManager() {
		return lczcSchemeEntityManagerImpl;
	}
	
	@Override
	protected LczcSchemeTempEntityManagerImpl getSchemeTempEntityManager() {
		return lczcSchemeTempEntityManagerImpl;
	}
	
	private String playType;
	// -------------------------------------------------------
	@Override
	protected XDetachedCriteria buildFilterListDetachedCriteria() {
		XDetachedCriteria c = super.buildFilterListDetachedCriteria();
		return c;
	}
	@Override
	protected String doEditNew() throws Exception {
		LczcMatch[] matchs = findMatchsOfCacheable(this.period.getId());
		if (matchs == null)
			throw new WebDataException("对阵不存在.");
		Struts2Utils.setAttribute("matchs", matchs);
		
		PeriodSales periodSales_SINGLE = this.periodEntityManager.getPeriodSales(new PeriodSalesId(this.period.getId(), SalesMode.SINGLE));
		PeriodSales periodSales_COMPOUND = this.periodEntityManager.getPeriodSales(new PeriodSalesId(this.period.getId(), SalesMode.COMPOUND));
		Struts2Utils.setAttribute("singleEndTime", periodSales_SINGLE.getSaleEndTime(ShareType.TOGETHER));
		Struts2Utils.setAttribute("compoundEndTime", periodSales_COMPOUND.getSaleEndTime(ShareType.TOGETHER));
		return super.doEditNew();
	}

	protected LczcMatch[] findMatchsOfCacheable(Long periodId) {
		Element el = matchCache.get(periodId);
		if (el == null) {
			LczcMatch[] matchs = lczcMatchEntityManagerImpl.findMatchs(periodId);
			if (matchs != null)
				matchCache.put(new Element(periodId, matchs));

			return matchs;
		} else {
			return (LczcMatch[]) el.getValue();
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
	
	@Override
	protected LczcSchemeCreateForm supplementCreateFormData() throws WebDataException{
		this.createForm = super.supplementCreateFormData();
		String schemeIdStr = Struts2Utils.getParameter("tempSchemeId");
		if(schemeIdStr==null){
			throw new WebDataException("操作的方案标识为空.");
		}
		Long schemeId = Long.valueOf(schemeIdStr);
		this.schemeTemp = lczcSchemeTempEntityManagerImpl.getScheme(schemeId);
		if(schemeTemp==null){
			throw new WebDataException("保存的方案 id["+schemeIdStr+"]未能找到.");
		}
		try{
			switch(this.createForm.getMode()){
			case COMPOUND:
				LczcCompoundItem[] items= schemeTemp.getCompoundContent();
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
					LczcCompoundItem[] betItems = schemeTemp.getCompoundContent();
					Struts2Utils.setAttribute("items", betItems);
					endTime = periodSales.getShareEndInitTime();
					break;
				case SINGLE:
					endTime = periodSales.getSelfEndInitTime();
					break;
				default:
					throw new DataException("投注方式不合法！");
			}
			LczcMatch[] matchs = findMatchsOfCacheable(schemeTemp.getPeriodId());
			Struts2Utils.setAttribute("matchs", matchs);
			Struts2Utils.setAttribute("firstMatchTime", endTime);
			return super.doShowTemp();
		} catch (DataException e) {
			addActionError(e.getMessage());
			return GlobalResults.FWD_ERROR;
		}		
	}

	@Override
	protected LczcSchemeDTO buildSchemeDTO() throws WebDataException {
		if (createForm == null)
			throw new WebDataException("表单数据为空.");

		if (createForm.getItems() == null || createForm.getItems().length == 0)
			throw new WebDataException("投注内容不能为空.");

		LczcSchemeDTO dto = super.buildSchemeDTO();

		return dto;
	}

	@Override
	protected String doShow() throws WebDataException {
		if (scheme != null) {
			LczcMatch[] matchs = findMatchsOfCacheable(scheme.getPeriodId());
			Struts2Utils.setAttribute("matchs", matchs);
			try {
				if(scheme.getMode() == SalesMode.COMPOUND)
				   Struts2Utils.setAttribute("items", scheme.getCompoundContent());
			} catch (DataException e) {
				e.printStackTrace();
			}
		}
		return super.doShow();
	}

	// -------------------------------------------------------

	/**
	 * @return {@link #createForm}
	 */
	public LczcSchemeCreateForm getCreateForm() {
		return createForm;
	}

	/**
	 * @param createForm the {@link #createForm} to set
	 */
	public void setCreateForm(LczcSchemeCreateForm createForm) {
		this.createForm = createForm;
	}

	/**
	 * @return {@link #uploadForm}
	 */
	public LczcSchemeUploadForm getUploadForm() {
		return uploadForm;
	}

	/**
	 * @param uploadForm the {@link #uploadForm} to set
	 */
	public void setUploadForm(LczcSchemeUploadForm uploadForm) {
		this.uploadForm = uploadForm;
	}

	@Override
	public Lottery getLotteryType() {
		return Lottery.LCZC;
	}

	public String getPlayType() {
		return playType;
	}

	public void setPlayType(String playType) {
		this.playType = playType;
	}


}
