package com.cai310.lottery.web.controller.lottery.seven;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

import com.cai310.lottery.SevenConstant;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.dto.lottery.seven.SevenSchemeDTO;
import com.cai310.lottery.entity.lottery.seven.SevenPeriodData;
import com.cai310.lottery.entity.lottery.seven.SevenScheme;
import com.cai310.lottery.service.lottery.SchemeEntityManager;
import com.cai310.lottery.service.lottery.SchemeService;
import com.cai310.lottery.service.lottery.impl.PeriodDataEntityManagerImpl;
import com.cai310.lottery.service.lottery.seven.impl.SevenPeriodDataEntityManagerImpl;
import com.cai310.lottery.service.lottery.seven.impl.SevenSchemeEntityManagerImpl;
import com.cai310.lottery.service.lottery.seven.impl.SevenSchemeServiceImpl;
import com.cai310.lottery.support.RandomContentGenerator;
import com.cai310.lottery.web.controller.WebDataException;
import com.cai310.lottery.web.controller.lottery.NumberSchemeBaseController;

@Namespace("/" + SevenConstant.KEY)
@Action(value = "scheme")
public class SevenSchemeController extends
		NumberSchemeBaseController<SevenScheme, SevenSchemeDTO, SevenSchemeCreateForm, SevenSchemeUploadForm, SevenPeriodData> {
	private static final long serialVersionUID = 5783479221989581469L;

	@Override
	public PeriodDataEntityManagerImpl<SevenPeriodData> getPeriodDataEntityManagerImpl() {
		return sevenPeriodDataEntityManagerImpl;
	}
	@Autowired
	private SevenSchemeServiceImpl schemeService;

	@Autowired
	private SevenSchemeEntityManagerImpl schemeEntityManager;
	@Autowired
	private SevenPeriodDataEntityManagerImpl sevenPeriodDataEntityManagerImpl;
	@Override
	protected SchemeService<SevenScheme, SevenSchemeDTO> getSchemeService() {
		return schemeService;
	}

	@Override
	protected SchemeEntityManager<SevenScheme> getSchemeEntityManager() {
		return schemeEntityManager;
	}
	private SevenPeriodData periodData;

	public SevenSchemeCreateForm getCreateForm() {
		return createForm;
	}

	public void setCreateForm(SevenSchemeCreateForm createForm) {
		this.createForm = createForm;
	}

	public SevenSchemeUploadForm getUploadForm() {
		return uploadForm;
	}

	public void setUploadForm(SevenSchemeUploadForm uploadForm) {
		this.uploadForm = uploadForm;
	}

	private Integer playType = 0;

	@Override
	public Lottery getLotteryType() {
		return Lottery.SEVEN;
	}

	@Override
	protected String doShow() throws WebDataException {
		// ///扩展方法。取开奖号码

		if (null != period) {
			this.setPeriodData(sevenPeriodDataEntityManagerImpl.getPeriodData(period.getId()));
		}
		return super.doShow();
	}

	public String editSpinmatrix() {
		return "editSpinmatrix";
	}

	public SevenPeriodData getPeriodData() {
		return periodData;
	}

	public void setPeriodData(SevenPeriodData periodData) {
		this.periodData = periodData;
	}

	public Integer getPlayType() {
		return playType;
	}

	public void setPlayType(Integer playType) {
		this.playType = playType;
	}

	@Override
	protected RandomContentGenerator getRandomContentGenerator() {
		return null;
	}

	@Override
	public Class<SevenPeriodData> getPeriodDataClass() {
		return SevenPeriodData.class;
	}

}
