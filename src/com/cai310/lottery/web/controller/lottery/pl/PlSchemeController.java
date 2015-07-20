package com.cai310.lottery.web.controller.lottery.pl;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import com.cai310.lottery.PlConstant;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.dto.lottery.SchemeQueryDTO;
import com.cai310.lottery.dto.lottery.pl.PlSchemeDTO;
import com.cai310.lottery.entity.lottery.pl.PlPeriodData;
import com.cai310.lottery.entity.lottery.pl.PlScheme;
import com.cai310.lottery.service.lottery.SchemeEntityManager;
import com.cai310.lottery.service.lottery.SchemeService;
import com.cai310.lottery.service.lottery.impl.PeriodDataEntityManagerImpl;
import com.cai310.lottery.service.lottery.pl.impl.PlPeriodDataEntityManagerImpl;
import com.cai310.lottery.service.lottery.pl.impl.PlSchemeEntityManagerImpl;
import com.cai310.lottery.service.lottery.pl.impl.PlSchemeServiceImpl;
import com.cai310.lottery.support.RandomContentGenerator;
import com.cai310.lottery.support.pl.PlPlayType;
import com.cai310.lottery.web.controller.WebDataException;
import com.cai310.lottery.web.controller.lottery.NumberSchemeBaseController;
import com.cai310.orm.XDetachedCriteria;
import com.cai310.utils.Struts2Utils;

@Namespace("/" + PlConstant.KEY)
@Action(value = "scheme")
public class PlSchemeController extends
		NumberSchemeBaseController<PlScheme, PlSchemeDTO, PlSchemeCreateForm, PlSchemeUploadForm,PlPeriodData> {
	private static final long serialVersionUID = 5783479221989581469L;
	@Autowired
	private PlPeriodDataEntityManagerImpl plPeriodDataEntityManagerImpl;
	
	@Override
	public PeriodDataEntityManagerImpl<PlPeriodData> getPeriodDataEntityManagerImpl() {
		return plPeriodDataEntityManagerImpl;
	}
	// @Autowired
	// private PlRandomContentGenerator randomContentGenerator;

	@Autowired
	private PlSchemeServiceImpl schemeService;
	private Integer playType;///1是排五。0是排三
	@Autowired
	private PlSchemeEntityManagerImpl schemeEntityManager;
	@Override
	protected String doShow() throws WebDataException {
		PlScheme plScheme=this.getScheme();
		if(PlPlayType.P5Direct.equals(plScheme.getPlayType())){
			this.setPlayType(Integer.valueOf("1"));
		}else{
			this.setPlayType(Integer.valueOf("0"));
		}
		return "show";
	}
	protected Integer getLotteryPlayType(){
		return playType;
	}
	/**
	 * 玩法介绍
	 */
	@Override
	public String introduction() throws WebDataException {
		if(Integer.valueOf(0).equals(playType)){
			///排三
			return "introduction_pl3";
		}else{
			///排五
			return "introduction_pl5";
		}
		
	}
	@Override
	protected SchemeQueryDTO buildMySchemeQueryDTO() {
		SchemeQueryDTO dto = super.buildMySchemeQueryDTO();
		if (playType != null) {
			if(Integer.valueOf(0).equals(playType)){
				///排三
				dto.setPlayType("0");
			}else{
				///排五
				dto.setPlayType("1");
			}
		}
		return dto;
	}
	/**
	 * 规则
	 */
	@Override
	public String protocol() throws WebDataException {
		if(Integer.valueOf(0).equals(playType)){
			///排三
			return "protocol_pl3";
		}else{
			///排五
			return "protocol_pl5";
		}
		
	}
	// -------------------------------------------------------
	
	protected XDetachedCriteria buildListDetachedCriteria() {
		XDetachedCriteria c = super.buildListDetachedCriteria();
		
		if(Integer.valueOf("1").equals(this.getPlayType())){
			c.add(Restrictions.eq("m.playType", PlPlayType.P5Direct));
			if (null == this.getCreateForm()) {
				this.setCreateForm(new PlSchemeCreateForm());
				this.createForm.setPlayType(PlPlayType.P5Direct);
			}
		}else if(Integer.valueOf("0").equals(this.getPlayType())){
			if (null == this.getCreateForm()) {
				this.setCreateForm(new PlSchemeCreateForm());
				this.createForm.setPlayType(PlPlayType.P3Direct);
			}
			c.add(Restrictions.not(Restrictions.eq("m.playType", PlPlayType.P5Direct)));
		}
		return c;
	}
	@Override
	protected String getSchemeUrl(PlScheme scheme) {
		if (scheme.getPlayType().equals(PlPlayType.P5Direct)) {
			this.setPlayType(1);
		}else{
			this.setPlayType(0);
		}
		return Struts2Utils.getRequest().getContextPath() + "/" + scheme.getLotteryType().getKey()
				+ "/scheme!show.action?schemeNumber=" + scheme.getSchemeNumber()+"&playType="+this.getPlayType();
	} 
	@Override
	protected String doEditNew() throws Exception {
		if(Integer.valueOf("1").equals(this.getPlayType())){
			if (null == this.getCreateForm()) {
				this.setCreateForm(new PlSchemeCreateForm());
				this.createForm.setPlayType(PlPlayType.P5Direct);
			}
		}else if(Integer.valueOf("0").equals(this.getPlayType())){
			if (null == this.getCreateForm()) {
				this.setCreateForm(new PlSchemeCreateForm());
				this.createForm.setPlayType(PlPlayType.P3Direct);
			}
		}
		return super.doEditNew();
	}
	/**
	 * 重写该方法，过滤玩法
	 */
	protected XDetachedCriteria buildFilterListDetachedCriteria() {
		XDetachedCriteria c = super.buildFilterListDetachedCriteria();
		
		if(Integer.valueOf("1").equals(this.getPlayType())){
			c.add(Restrictions.eq("m.playType", PlPlayType.P5Direct));
			if (null == this.getCreateForm()) {
				this.setCreateForm(new PlSchemeCreateForm());
				this.createForm.setPlayType(PlPlayType.P5Direct);
			}
		}else if(Integer.valueOf("0").equals(this.getPlayType())){
			if (null == this.getCreateForm()) {
				this.setCreateForm(new PlSchemeCreateForm());
				this.createForm.setPlayType(PlPlayType.P3Direct);
			}
			c.add(Restrictions.not(Restrictions.eq("m.playType", PlPlayType.P5Direct)));
		}
		return c;
	}	
	@Override
	protected PlSchemeDTO buildSchemeDTO() throws WebDataException {
		PlSchemeDTO plSchemeDTO = super.buildSchemeDTO();
		plSchemeDTO.setPlayType(this.getCreateForm().getPlayType());
		return plSchemeDTO;
	}

	@Override
	protected SchemeService<PlScheme, PlSchemeDTO> getSchemeService() {
		return schemeService;
	}

	@Override
	protected SchemeEntityManager<PlScheme> getSchemeEntityManager() {
		return schemeEntityManager;
	}

	@Override
	protected RandomContentGenerator getRandomContentGenerator() {
		// return randomContentGenerator;
		return null;
	}

	/**
	 * @return {@link #createForm}
	 */
	public PlSchemeCreateForm getCreateForm() {
		return createForm;
	}

	/**
	 * @param createForm the {@link #createForm} to set
	 */
	public void setCreateForm(PlSchemeCreateForm createForm) {
		this.createForm = createForm;
	}

	/**
	 * @return {@link #uploadForm}
	 */
	public PlSchemeUploadForm getUploadForm() {
		return uploadForm;
	}

	/**
	 * @param uploadForm the {@link #uploadForm} to set
	 */
	public void setUploadForm(PlSchemeUploadForm uploadForm) {
		this.uploadForm = uploadForm;
	}

	@Override
	public Lottery getLotteryType() {
		return Lottery.PL;
	}

	/**
	 * @return the playType
	 */
	public Integer getPlayType() {
		return playType;
	}

	/**
	 * @param playType the playType to set
	 */
	public void setPlayType(Integer playType) {
		this.playType = playType;
	}
	@Override
	public Class<PlPeriodData> getPeriodDataClass() {
		return PlPeriodData.class;
	}
	
	
}
