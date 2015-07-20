package com.cai310.lottery.web.controller.lottery.dlt;

import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.cai310.lottery.DltConstant;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.dto.lottery.dlt.DltSchemeDTO;
import com.cai310.lottery.entity.lottery.dlt.DltPeriodData;
import com.cai310.lottery.entity.lottery.dlt.DltScheme;
import com.cai310.lottery.service.lottery.SchemeEntityManager;
import com.cai310.lottery.service.lottery.SchemeService;
import com.cai310.lottery.service.lottery.dlt.impl.DltPeriodDataEntityManagerImpl;
import com.cai310.lottery.service.lottery.dlt.impl.DltSchemeEntityManagerImpl;
import com.cai310.lottery.service.lottery.dlt.impl.DltSchemeServiceImpl;
import com.cai310.lottery.service.lottery.impl.PeriodDataEntityManagerImpl;
import com.cai310.lottery.support.RandomContentGenerator;
import com.cai310.lottery.support.dlt.DltPlayType;
import com.cai310.lottery.support.shrink.Shrink;
import com.cai310.lottery.support.shrink.ShrinkBean;
import com.cai310.lottery.support.shrink.SpinmatrixProcessor;
import com.cai310.lottery.support.shrink.SpinmatrixType;
import com.cai310.lottery.web.controller.GlobalResults;
import com.cai310.lottery.web.controller.WebDataException;
import com.cai310.lottery.web.controller.lottery.NumberSchemeBaseController;
import com.cai310.orm.XDetachedCriteria;
import com.cai310.utils.Struts2Utils;
import com.google.common.collect.Sets;

@Namespace("/" + DltConstant.KEY)
@Action(value = "scheme")
public class DltSchemeController extends
		NumberSchemeBaseController<DltScheme, DltSchemeDTO, DltSchemeCreateForm, DltSchemeUploadForm, DltPeriodData> {
	private static final long serialVersionUID = 5783479221989581469L;
	@Autowired
	private DltPeriodDataEntityManagerImpl dltPeriodDataEntityManagerImpl;

	@Override
	public PeriodDataEntityManagerImpl<DltPeriodData> getPeriodDataEntityManagerImpl() {
		return dltPeriodDataEntityManagerImpl;
	}

	// @Autowired
	// private DltRandomContentGenerator randomContentGenerator;

	@Autowired
	private DltSchemeServiceImpl schemeService;

	@Autowired
	private DltSchemeEntityManagerImpl schemeEntityManager;
	private Integer playType = 0;

	@Autowired
	private SpinmatrixProcessor spinmatrixProcessor;

	@Autowired
	@Qualifier("dltSpinmatrixShrink")
	private Shrink spinmatrixShrink;

	@Autowired
	@Qualifier("dlt35to5Shrink")
	private Shrink dlt35to5Shrink;

	@Autowired
	@Qualifier("dlt12to2Shrink")
	private Shrink dlt12to2Shrink;

	private String shrinkNums;
	private ShrinkBean shrinkBean;

	private ShrinkBean dlt35to5ShrinkBean;
	private ShrinkBean dlt12to2ShrinkBean;

	// -------------------------------------------------------
	@Override
	protected XDetachedCriteria buildListDetachedCriteria() {
		XDetachedCriteria c = super.buildListDetachedCriteria();
		if (null == this.getCreateForm()) {
			this.setCreateForm(new DltSchemeCreateForm());
		}
		if (null == this.getCreateForm().getPlayType()) {
			this.getCreateForm().setPlayType(DltPlayType.General);
		}
		// c.add(Restrictions.eq("m.playType",
		// this.getCreateForm().getPlayType()));
		return c;
	}
	@Override
	protected DltSchemeDTO quick_buildSchemeDTO() throws WebDataException {
		this.createForm.setPlayType(DltPlayType.General);
		DltSchemeDTO dltSchemeDTO = super.quick_buildSchemeDTO();
		dltSchemeDTO.setPlayType(DltPlayType.General);
		return dltSchemeDTO;
	}
	protected XDetachedCriteria buildFilterListDetachedCriteria() {
		XDetachedCriteria c = super.buildFilterListDetachedCriteria();
		if (null == this.getCreateForm()) {
			this.setCreateForm(new DltSchemeCreateForm());
		}
		if (null == this.getCreateForm().getPlayType()) {
			this.getCreateForm().setPlayType(DltPlayType.General);
		}
		// c.add(Restrictions.eq("m.playType",
		// this.getCreateForm().getPlayType()));
		return c;
	}

	@Override
	protected String doEditNew() throws Exception {
		if (null == this.getCreateForm()) {
			this.setCreateForm(new DltSchemeCreateForm());
		}
		if (null == this.getCreateForm().getPlayType()) {
			this.getCreateForm().setPlayType(DltPlayType.General);
		}
		return super.doEditNew();
	}

	@Override
	protected DltSchemeDTO buildSchemeDTO() throws WebDataException {
		DltSchemeDTO dltSchemeDTO;
		if (DltPlayType.GeneralAdditional.equals(this.getCreateForm().getPlayType())) {
			throw new WebDataException("大乐透玩法选择出错.");
		} else if (DltPlayType.General.equals(this.getCreateForm().getPlayType())) {
			if (null != this.getCreateForm().getDltAdditional() && this.getCreateForm().getDltAdditional()) {
				this.getCreateForm().setUnitsMoney(Integer.valueOf("3"));
				dltSchemeDTO = super.buildSchemeDTO();
				dltSchemeDTO.setPlayType(DltPlayType.GeneralAdditional);
			} else {
				dltSchemeDTO = super.buildSchemeDTO();
				dltSchemeDTO.setPlayType(this.getCreateForm().getPlayType());
			}

		} else if (DltPlayType.Select12to2.equals(this.getCreateForm().getPlayType())) {
			dltSchemeDTO = super.buildSchemeDTO();
			dltSchemeDTO.setPlayType(this.getCreateForm().getPlayType());
		} else {
			throw new WebDataException("大乐透玩法选择出错.");
		}
		return dltSchemeDTO;
	}

	public String editSpinmatrix() {
		return "editSpinmatrix";
	}

	public String spinmatrix() {
		try {
			if (StringUtils.isBlank(this.shrinkNums))
				throw new WebDataException("请选定7至18个基号.");
			String[] arr = this.shrinkNums.trim().split(",");
			int[] numArr = new int[arr.length];
			int i = 0;
			Set<Integer> set = Sets.newHashSet();
			for (String str : arr) {
				if (!str.matches("\\d{1,2}"))
					throw new WebDataException("选择的基号错误.");
				int num = Integer.parseInt(str);
				if (num < 1 || num > 35)
					throw new WebDataException("基号只能从01-35中选择.");
				if (set.contains(num))
					throw new WebDataException("选择的基号不能重复.");
				set.add(num);
				numArr[i] = num;
				i++;
			}
			if (numArr.length < 7 || numArr.length > 18)
				throw new WebDataException("基号个数必须是7至18个.");

			String result = spinmatrixProcessor.spinmatrix(SpinmatrixType.ST5_4, numArr);
			String[] contents;
			int units;
			if (StringUtils.isBlank(result)) {
				contents = new String[0];
				units = 0;
			} else {
				contents = result.trim().split("\r\n");
				units = contents.length;
			}
			Struts2Utils.setAttribute("numArr", numArr);
			Struts2Utils.setAttribute("result", result);
			Struts2Utils.setAttribute("contents", contents);
			Struts2Utils.setAttribute("units", units);
			return "spinmatrixResult";
		} catch (WebDataException e) {
			addActionMessage(e.getMessage());
			return "editSpinmatrix";
		} catch (Exception e) {
			addActionError(e.getMessage());
			logger.warn(e.getMessage(), e);
			return GlobalResults.FWD_ERROR;
		}
	}

	public String shrink() {
		try {
			if (this.shrinkBean == null)
				throw new WebDataException("缩水参数错误.");
			if (StringUtils.isBlank(this.shrinkBean.getContent()))
				throw new WebDataException("原始内容不能为空.");

			String result = spinmatrixShrink.shrink(this.shrinkBean);
			String[] contents;
			int units;
			if (StringUtils.isBlank(result)) {
				contents = new String[0];
				units = 0;
			} else {
				contents = result.trim().split("\r\n");
				units = result.trim().split("\r\n").length;
			}
			Struts2Utils.setAttribute("result", result);
			Struts2Utils.setAttribute("contents", contents);
			Struts2Utils.setAttribute("units", units);
			return "shrinkResult";
		} catch (WebDataException e) {
			addActionMessage(e.getMessage());
			return GlobalResults.FWD_ERROR;
		} catch (Exception e) {
			addActionError(e.getMessage());
			logger.warn(e.getMessage(), e);
			return GlobalResults.FWD_ERROR;
		}
	}

	public String filter() {
		try {
			if (dlt35to5ShrinkBean == null || dlt12to2ShrinkBean == null)
				throw new WebDataException("过滤参数错误.");
			if (StringUtils.isBlank(dlt35to5ShrinkBean.getContent())
					|| StringUtils.isBlank(dlt12to2ShrinkBean.getContent()))
				throw new WebDataException("原始内容不能为空.");

			String dlt35to5Result = dlt35to5Shrink.shrink(dlt35to5ShrinkBean);
			String dlt12to2Result = dlt12to2Shrink.shrink(dlt12to2ShrinkBean);

			String[] contents;
			int units;
			if (StringUtils.isBlank(dlt35to5Result) || StringUtils.isBlank(dlt12to2Result)) {
				contents = new String[0];
				units = 0;
			} else {
				String[] dlt35to5Arr = dlt35to5Result.trim().split("\r\n");
				String[] dlt12to2Arr = dlt12to2Result.trim().split("\r\n");
				units = dlt35to5Arr.length * dlt12to2Arr.length;
				contents = new String[units];
				int i = 0;
				for (String str35to5 : dlt35to5Arr) {
					for (String str12to2 : dlt12to2Arr) {
						String line = str35to5 + "," + str12to2;
						contents[i] = line;
						i++;
					}
				}
			}
			Struts2Utils.setAttribute("result", StringUtils.join(contents, "\r\n"));
			Struts2Utils.setAttribute("contents", contents);
			Struts2Utils.setAttribute("units", units);
			return "filterResult";
		} catch (WebDataException e) {
			addActionMessage(e.getMessage());
			return GlobalResults.FWD_ERROR;
		} catch (Exception e) {
			addActionError(e.getMessage());
			logger.warn(e.getMessage(), e);
			return GlobalResults.FWD_ERROR;
		}
	}

	public String editFilter() {
		return "editFilter";
	}

	@Override
	protected SchemeService<DltScheme, DltSchemeDTO> getSchemeService() {
		return schemeService;
	}

	@Override
	protected SchemeEntityManager<DltScheme> getSchemeEntityManager() {
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
	public DltSchemeCreateForm getCreateForm() {
		return createForm;
	}

	/**
	 * @param createForm the {@link #createForm} to set
	 */
	public void setCreateForm(DltSchemeCreateForm createForm) {
		this.createForm = createForm;
	}

	/**
	 * @return {@link #uploadForm}
	 */
	public DltSchemeUploadForm getUploadForm() {
		return uploadForm;
	}

	/**
	 * @param uploadForm the {@link #uploadForm} to set
	 */
	public void setUploadForm(DltSchemeUploadForm uploadForm) {
		this.uploadForm = uploadForm;
	}

	@Override
	public Lottery getLotteryType() {
		return Lottery.DLT;
	}

	public Integer getPlayType() {
		return playType;
	}

	public void setPlayType(Integer playType) {
		this.playType = playType;
	}

	public String getShrinkNums() {
		return shrinkNums;
	}

	public void setShrinkNums(String shrinkNums) {
		this.shrinkNums = shrinkNums;
	}

	public ShrinkBean getShrinkBean() {
		return shrinkBean;
	}

	public void setShrinkBean(ShrinkBean shrinkBean) {
		this.shrinkBean = shrinkBean;
	}

	public ShrinkBean getDlt35to5ShrinkBean() {
		return dlt35to5ShrinkBean;
	}

	public void setDlt35to5ShrinkBean(ShrinkBean dlt35to5ShrinkBean) {
		this.dlt35to5ShrinkBean = dlt35to5ShrinkBean;
	}

	public ShrinkBean getDlt12to2ShrinkBean() {
		return dlt12to2ShrinkBean;
	}

	public void setDlt12to2ShrinkBean(ShrinkBean dlt12to2ShrinkBean) {
		this.dlt12to2ShrinkBean = dlt12to2ShrinkBean;
	}
	@Override
	public Class<DltPeriodData> getPeriodDataClass() {
		return DltPeriodData.class;
	}
}
