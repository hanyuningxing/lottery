package com.cai310.lottery.web.controller.lottery.sevenstar;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import com.cai310.lottery.SevenstarConstant;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.dto.lottery.sevenstar.SevenstarSchemeDTO;
import com.cai310.lottery.entity.lottery.sevenstar.SevenstarPeriodData;
import com.cai310.lottery.entity.lottery.sevenstar.SevenstarScheme;
import com.cai310.lottery.service.lottery.SchemeEntityManager;
import com.cai310.lottery.service.lottery.SchemeService;
import com.cai310.lottery.service.lottery.impl.PeriodDataEntityManagerImpl;
import com.cai310.lottery.service.lottery.seven.impl.SevenPeriodDataEntityManagerImpl;
import com.cai310.lottery.service.lottery.sevenstar.impl.SevenstarPeriodDataEntityManagerImpl;
import com.cai310.lottery.service.lottery.sevenstar.impl.SevenstarSchemeEntityManagerImpl;
import com.cai310.lottery.service.lottery.sevenstar.impl.SevenstarSchemeServiceImpl;
import com.cai310.lottery.support.RandomContentGenerator;
import com.cai310.lottery.web.controller.lottery.NumberSchemeBaseController;
import org.springframework.beans.factory.annotation.Autowired;

@Namespace("/" + SevenstarConstant.KEY)
@Action(value = "scheme")
public class SevenstarSchemeController
		extends
		NumberSchemeBaseController<SevenstarScheme, SevenstarSchemeDTO, SevenstarSchemeCreateForm, SevenstarSchemeUploadForm,SevenstarPeriodData> {
	private static final long serialVersionUID = 5783479221989581469L;

	// @Autowired
	// private SevenstarRandomContentGenerator randomContentGenerator;

	@Autowired
	private SevenstarSchemeServiceImpl schemeService;

	@Autowired
	private SevenstarSchemeEntityManagerImpl schemeEntityManager;
	@Autowired
	private SevenstarPeriodDataEntityManagerImpl sevenstarPeriodDataEntityManagerImpl;
	@Override
	protected SchemeService<SevenstarScheme, SevenstarSchemeDTO> getSchemeService() {
		return schemeService;
	}

	@Override
	protected SchemeEntityManager<SevenstarScheme> getSchemeEntityManager() {
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
	public SevenstarSchemeCreateForm getCreateForm() {
		return createForm;
	}

	/**
	 * @param createForm the {@link #createForm} to set
	 */
	public void setCreateForm(SevenstarSchemeCreateForm createForm) {
		this.createForm = createForm;
	}

	/**
	 * @return {@link #uploadForm}
	 */
	public SevenstarSchemeUploadForm getUploadForm() {
		return uploadForm;
	}

	/**
	 * @param uploadForm the {@link #uploadForm} to set
	 */
	public void setUploadForm(SevenstarSchemeUploadForm uploadForm) {
		this.uploadForm = uploadForm;
	}

	@Override
	public Lottery getLotteryType() {
		return Lottery.SEVENSTAR;
	}

	@Override
	public PeriodDataEntityManagerImpl<SevenstarPeriodData> getPeriodDataEntityManagerImpl() {
		return sevenstarPeriodDataEntityManagerImpl;
	}

	@Override
	public Class<SevenstarPeriodData> getPeriodDataClass() {
		return SevenstarPeriodData.class;
	}


}
