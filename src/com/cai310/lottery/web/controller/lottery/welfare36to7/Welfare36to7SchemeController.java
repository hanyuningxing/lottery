package com.cai310.lottery.web.controller.lottery.welfare36to7;

import java.util.HashMap;
import java.util.Map;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

import com.cai310.lottery.Welfare36to7Constant;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.dto.lottery.welfare36to7.Welfare36to7SchemeDTO;
import com.cai310.lottery.entity.lottery.welfare36to7.Welfare36to7PeriodData;
import com.cai310.lottery.entity.lottery.welfare36to7.Welfare36to7Prize;
import com.cai310.lottery.entity.lottery.welfare36to7.Welfare36to7Scheme;
import com.cai310.lottery.service.lottery.SchemeEntityManager;
import com.cai310.lottery.service.lottery.SchemeService;
import com.cai310.lottery.service.lottery.impl.PeriodDataEntityManagerImpl;
import com.cai310.lottery.service.lottery.welfare36to7.impl.Welfare36to7PeriodDataEntityManagerImpl;
import com.cai310.lottery.service.lottery.welfare36to7.impl.Welfare36to7SchemeEntityManagerImpl;
import com.cai310.lottery.service.lottery.welfare36to7.impl.Welfare36to7SchemeServiceImpl;
import com.cai310.lottery.support.RandomContentGenerator;
import com.cai310.lottery.support.welfare36to7.Welfare36to7PlayType;
import com.cai310.lottery.web.controller.WebDataException;
import com.cai310.lottery.web.controller.lottery.NumberSchemeBaseController;
import com.cai310.orm.XDetachedCriteria;
import com.cai310.utils.Struts2Utils;

@Namespace("/" + Welfare36to7Constant.KEY)
@Action(value = "scheme")
public class Welfare36to7SchemeController
		extends
		NumberSchemeBaseController<Welfare36to7Scheme, Welfare36to7SchemeDTO, Welfare36to7SchemeCreateForm, Welfare36to7SchemeUploadForm,Welfare36to7PeriodData> {
	private static final long serialVersionUID = 5783479221989581469L;

	@Autowired
	private Welfare36to7SchemeServiceImpl schemeService;

	@Autowired
	private Welfare36to7SchemeEntityManagerImpl schemeEntityManager;
	
	@Autowired
	private Welfare36to7PeriodDataEntityManagerImpl welfare36to7PeriodDataEntityManagerImpl;

	// -------------------------------------------------------
	@Override
	protected XDetachedCriteria buildListDetachedCriteria() {
		XDetachedCriteria c = super.buildListDetachedCriteria();
		if (null == this.getCreateForm()) {
			this.setCreateForm(new Welfare36to7SchemeCreateForm());
		}
		if (null == this.getCreateForm().getPlayType()) {
			this.getCreateForm().setPlayType(Welfare36to7PlayType.Haocai1);
		}
		return c;
	}

	@Override
	protected String doEditNew() throws Exception {
		if (null == this.getCreateForm()) {
			this.setCreateForm(new Welfare36to7SchemeCreateForm());
		}
		if (null == this.getCreateForm().getPlayType()) {
			this.getCreateForm().setPlayType(Welfare36to7PlayType.Haocai1);
		}
		return super.doEditNew();
	}

	@Override
	protected Welfare36to7SchemeDTO buildSchemeDTO() throws WebDataException {
		Welfare36to7SchemeDTO welfare36to7SchemeDTO = super.buildSchemeDTO();
		welfare36to7SchemeDTO.setPlayType(this.getCreateForm().getPlayType());
		return welfare36to7SchemeDTO;
	}

	@Override
	protected SchemeService<Welfare36to7Scheme, Welfare36to7SchemeDTO> getSchemeService() {
		return schemeService;
	}

	@Override
	protected SchemeEntityManager<Welfare36to7Scheme> getSchemeEntityManager() {
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
	public Welfare36to7SchemeCreateForm getCreateForm() {
		return createForm;
	}

	/**
	 * @param createForm the {@link #createForm} to set
	 */
	public void setCreateForm(Welfare36to7SchemeCreateForm createForm) {
		this.createForm = createForm;
	}

	/**
	 * @return {@link #uploadForm}
	 */
	public Welfare36to7SchemeUploadForm getUploadForm() {
		return uploadForm;
	}

	/**
	 * @param uploadForm the {@link #uploadForm} to set
	 */
	public void setUploadForm(Welfare36to7SchemeUploadForm uploadForm) {
		this.uploadForm = uploadForm;
	}

	@Override
	public Lottery getLotteryType() {
		return Lottery.WELFARE36To7;
	}

	@Override
	public PeriodDataEntityManagerImpl<Welfare36to7PeriodData> getPeriodDataEntityManagerImpl() {
		return welfare36to7PeriodDataEntityManagerImpl;
	}

	public String getBetTypePrize() {
		Welfare36to7Prize welfare36to7Prize =new Welfare36to7Prize();
		int prize=0;
		Map<String, Object> data = new HashMap<String, Object>();
		 switch (this.createForm.getPlayType()) {
			case Haocai1:	
				prize = welfare36to7Prize.getHaocai1Prize();
				break;
			case Zodiac:
				prize = welfare36to7Prize.getZodiacPrize();
				break;
			case Season:
				prize = welfare36to7Prize.getSeasonPrize();
				break;
			case Azimuth:
				prize = welfare36to7Prize.getAzimuthPrize();
				break;
			default:
				prize = 0;
			}
		    data.put("prize", prize);
			Struts2Utils.renderJson(data);
			return null;
	}
	@Override
	public Class<Welfare36to7PeriodData> getPeriodDataClass() {
		return Welfare36to7PeriodData.class;
	}
}
