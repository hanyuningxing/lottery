package com.cai310.lottery.web.controller.lottery.ssq;

import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.cai310.lottery.SsqConstant;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.dto.lottery.ssq.SsqSchemeDTO;
import com.cai310.lottery.entity.lottery.ssq.SsqPeriodData;
import com.cai310.lottery.entity.lottery.ssq.SsqScheme;
import com.cai310.lottery.service.lottery.SchemeEntityManager;
import com.cai310.lottery.service.lottery.SchemeService;
import com.cai310.lottery.service.lottery.impl.PeriodDataEntityManagerImpl;
import com.cai310.lottery.service.lottery.ssq.impl.SsqPeriodDataEntityManagerImpl;
import com.cai310.lottery.service.lottery.ssq.impl.SsqSchemeEntityManagerImpl;
import com.cai310.lottery.service.lottery.ssq.impl.SsqSchemeServiceImpl;
import com.cai310.lottery.support.RandomContentGenerator;
import com.cai310.lottery.support.shrink.Shrink;
import com.cai310.lottery.support.shrink.ShrinkBean;
import com.cai310.lottery.support.shrink.SpinmatrixProcessor;
import com.cai310.lottery.support.shrink.SpinmatrixType;
import com.cai310.lottery.support.ssq.SsqRandomContentGenerator;
import com.cai310.lottery.web.controller.GlobalResults;
import com.cai310.lottery.web.controller.WebDataException;
import com.cai310.lottery.web.controller.lottery.NumberSchemeBaseController;
import com.cai310.utils.Struts2Utils;
import com.google.common.collect.Sets;

@Namespace("/" + SsqConstant.KEY)
@Action(value = "scheme")
public class SsqSchemeController extends
		NumberSchemeBaseController<SsqScheme, SsqSchemeDTO, SsqSchemeCreateForm, SsqSchemeUploadForm, SsqPeriodData> {
	private static final long serialVersionUID = 5783479221989581469L;

	@Override
	public PeriodDataEntityManagerImpl<SsqPeriodData> getPeriodDataEntityManagerImpl() {
		return ssqPeriodDataEntityManagerImpl;
	}

	@Autowired
	private SsqRandomContentGenerator randomContentGenerator;

	@Autowired
	private SsqSchemeServiceImpl schemeService;

	@Autowired
	private SsqSchemeEntityManagerImpl schemeEntityManager;
	@Autowired
	private SsqPeriodDataEntityManagerImpl ssqPeriodDataEntityManagerImpl;

	@Autowired
	private SpinmatrixProcessor spinmatrixProcessor;

	@Autowired
	@Qualifier("ssqSpinmatrixShrink")
	private Shrink spinmatrixShrink;

	@Autowired
	@Qualifier("ssqDantuoShrink")
	private Shrink dantuoShrink;

	private String shrinkNums;
	private ShrinkBean shrinkBean;

	@Override
	protected SchemeService<SsqScheme, SsqSchemeDTO> getSchemeService() {
		return schemeService;
	}

	@Override
	protected SchemeEntityManager<SsqScheme> getSchemeEntityManager() {
		return schemeEntityManager;
	}

	@Override
	protected RandomContentGenerator getRandomContentGenerator() {
		return randomContentGenerator;
	}

	private SsqPeriodData periodData;

	public SsqSchemeCreateForm getCreateForm() {
		return createForm;
	}

	public void setCreateForm(SsqSchemeCreateForm createForm) {
		this.createForm = createForm;
	}

	public SsqSchemeUploadForm getUploadForm() {
		return uploadForm;
	}

	public void setUploadForm(SsqSchemeUploadForm uploadForm) {
		this.uploadForm = uploadForm;
	}

	private Integer playType = 0;

	@Override
	public Lottery getLotteryType() {
		return Lottery.SSQ;
	}

	@Override
	protected String doShow() throws WebDataException {
		// ///扩展方法。取开奖号码

		if (null != period) {
			this.setPeriodData(ssqPeriodDataEntityManagerImpl.getPeriodData(period.getId()));
		}
		return super.doShow();
	}

	public String editSpinmatrix() {
		return "editSpinmatrix";
	}

	public String spinmatrix() {
		try {
			if (StringUtils.isBlank(this.shrinkNums))
				throw new WebDataException("请选定8至20个基号.");
			String[] arr = this.shrinkNums.trim().split(",");
			int[] numArr = new int[arr.length];
			int i = 0;
			Set<Integer> set = Sets.newHashSet();
			for (String str : arr) {
				if (!str.matches("\\d{1,2}"))
					throw new WebDataException("选择的基号错误.");
				int num = Integer.parseInt(str);
				if (num < 1 || num > 33)
					throw new WebDataException("基号只能从01-33中选择.");
				if (set.contains(num))
					throw new WebDataException("选择的基号不能重复.");
				set.add(num);
				numArr[i] = num;
				i++;
			}
			if (numArr.length < 8 || numArr.length > 20)
				throw new WebDataException("基号个数必须是8至20个.");

			String result = spinmatrixProcessor.spinmatrix(SpinmatrixType.ST6_5, numArr);
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
			return "editSpinmatrixShrink";
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

	public String editFilter() {
		return "editFilter";
	}

	public String filter() {
		try {
			if (this.shrinkBean == null)
				throw new WebDataException("过滤参数错误.");
			if (StringUtils.isBlank(this.shrinkBean.getContent()))
				throw new WebDataException("原始内容不能为空.");

			String result = dantuoShrink.shrink(this.shrinkBean);
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

	public SsqPeriodData getPeriodData() {
		return periodData;
	}

	public void setPeriodData(SsqPeriodData periodData) {
		this.periodData = periodData;
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

	@Override
	public Class<SsqPeriodData> getPeriodDataClass() {
		return SsqPeriodData.class;
	}
}
