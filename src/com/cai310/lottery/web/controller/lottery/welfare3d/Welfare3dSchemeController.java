package com.cai310.lottery.web.controller.lottery.welfare3d;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

import com.cai310.lottery.Welfare3dConstant;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.dto.lottery.welfare3d.Welfare3dSchemeDTO;
import com.cai310.lottery.entity.lottery.welfare3d.Welfare3dPeriodData;
import com.cai310.lottery.entity.lottery.welfare3d.Welfare3dScheme;
import com.cai310.lottery.service.lottery.SchemeEntityManager;
import com.cai310.lottery.service.lottery.SchemeService;
import com.cai310.lottery.service.lottery.impl.PeriodDataEntityManagerImpl;
import com.cai310.lottery.service.lottery.welfare3d.impl.Welfare3dPeriodDataEntityManagerImpl;
import com.cai310.lottery.service.lottery.welfare3d.impl.Welfare3dSchemeEntityManagerImpl;
import com.cai310.lottery.service.lottery.welfare3d.impl.Welfare3dSchemeServiceImpl;
import com.cai310.lottery.support.RandomContentGenerator;
import com.cai310.lottery.support.welfare3d.Welfare3dPlayType;
import com.cai310.lottery.web.controller.WebDataException;
import com.cai310.lottery.web.controller.lottery.NumberSchemeBaseController;
import com.cai310.orm.XDetachedCriteria;

@Namespace("/" + Welfare3dConstant.KEY)
@Action(value = "scheme")
public class Welfare3dSchemeController
		extends
		NumberSchemeBaseController<Welfare3dScheme, Welfare3dSchemeDTO, Welfare3dSchemeCreateForm, Welfare3dSchemeUploadForm,Welfare3dPeriodData> {
	private static final long serialVersionUID = 5783479221989581469L;
	@Autowired
	private Welfare3dPeriodDataEntityManagerImpl welfare3dPeriodDataEntityManagerImpl;
	
	@Override
	public PeriodDataEntityManagerImpl<Welfare3dPeriodData> getPeriodDataEntityManagerImpl() {
		return welfare3dPeriodDataEntityManagerImpl;
	}
	// @Autowired
	// private Welfare3dRandomContentGenerator randomContentGenerator;

	@Autowired
	private Welfare3dSchemeServiceImpl schemeService;

	@Autowired
	private Welfare3dSchemeEntityManagerImpl schemeEntityManager;

	// -------------------------------------------------------
	@Override
	protected XDetachedCriteria buildListDetachedCriteria() {
		XDetachedCriteria c = super.buildListDetachedCriteria();
		if (null == this.getCreateForm()) {
			this.setCreateForm(new Welfare3dSchemeCreateForm());
		}
		if (null == this.getCreateForm().getPlayType()) {
			this.getCreateForm().setPlayType(Welfare3dPlayType.Direct);
		}
		return c;
	}

	@Override
	protected String doEditNew() throws Exception {
		if (null == this.getCreateForm()) {
			this.setCreateForm(new Welfare3dSchemeCreateForm());
		}
		if (null == this.getCreateForm().getPlayType()) {
			this.getCreateForm().setPlayType(Welfare3dPlayType.Direct);
		}
		return super.doEditNew();
	}
	@Override
	protected Welfare3dSchemeDTO quick_buildSchemeDTO() throws WebDataException {
		this.createForm.setPlayType(Welfare3dPlayType.Direct);
		Welfare3dSchemeDTO welfare3dSchemeDTO = super.quick_buildSchemeDTO();
		welfare3dSchemeDTO.setPlayType(Welfare3dPlayType.Direct);
		return welfare3dSchemeDTO;
	}
	@Override
	protected Welfare3dSchemeDTO buildSchemeDTO() throws WebDataException {
		Welfare3dSchemeDTO welfare3dSchemeDTO = super.buildSchemeDTO();
		welfare3dSchemeDTO.setPlayType(this.getCreateForm().getPlayType());
		return welfare3dSchemeDTO;
	}
	
	@Override
	protected SchemeService<Welfare3dScheme, Welfare3dSchemeDTO> getSchemeService() {
		return schemeService;
	}

	@Override
	protected SchemeEntityManager<Welfare3dScheme> getSchemeEntityManager() {
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
	public Welfare3dSchemeCreateForm getCreateForm() {
		return createForm;
	}

	/**
	 * @param createForm the {@link #createForm} to set
	 */
	public void setCreateForm(Welfare3dSchemeCreateForm createForm) {
		this.createForm = createForm;
	}

	/**
	 * @return {@link #uploadForm}
	 */
	public Welfare3dSchemeUploadForm getUploadForm() {
		return uploadForm;
	}

	/**
	 * @param uploadForm the {@link #uploadForm} to set
	 */
	public void setUploadForm(Welfare3dSchemeUploadForm uploadForm) {
		this.uploadForm = uploadForm;
	}

	@Override
	public Lottery getLotteryType() {
		return Lottery.WELFARE3D;
	}
	@Override
	public Class<Welfare3dPeriodData> getPeriodDataClass() {
		return Welfare3dPeriodData.class;
	}

}
