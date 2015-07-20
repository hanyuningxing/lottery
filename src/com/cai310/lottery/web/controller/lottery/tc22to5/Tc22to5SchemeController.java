package com.cai310.lottery.web.controller.lottery.tc22to5;

import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.cai310.lottery.Tc22to5Constant;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.dto.lottery.tc22to5.Tc22to5SchemeDTO;
import com.cai310.lottery.entity.lottery.tc22to5.Tc22to5PeriodData;
import com.cai310.lottery.entity.lottery.tc22to5.Tc22to5Scheme;
import com.cai310.lottery.service.lottery.SchemeEntityManager;
import com.cai310.lottery.service.lottery.SchemeService;
import com.cai310.lottery.service.lottery.impl.PeriodDataEntityManagerImpl;
import com.cai310.lottery.service.lottery.tc22to5.impl.Tc22to5PeriodDataEntityManagerImpl;
import com.cai310.lottery.service.lottery.tc22to5.impl.Tc22to5SchemeEntityManagerImpl;
import com.cai310.lottery.service.lottery.tc22to5.impl.Tc22to5SchemeServiceImpl;
import com.cai310.lottery.support.RandomContentGenerator;
import com.cai310.lottery.support.shrink.Shrink;
import com.cai310.lottery.support.shrink.ShrinkBean;
import com.cai310.lottery.support.shrink.SpinmatrixProcessor;
import com.cai310.lottery.support.shrink.SpinmatrixType;
import com.cai310.lottery.support.tc22to5.Tc22to5RandomContentGenerator;
import com.cai310.lottery.web.controller.GlobalResults;
import com.cai310.lottery.web.controller.WebDataException;
import com.cai310.lottery.web.controller.lottery.NumberSchemeBaseController;
import com.cai310.utils.Struts2Utils;
import com.google.common.collect.Sets;

@Namespace("/" + Tc22to5Constant.KEY)
@Action(value = "scheme")
public class Tc22to5SchemeController extends
		NumberSchemeBaseController<Tc22to5Scheme, Tc22to5SchemeDTO, Tc22to5SchemeCreateForm, Tc22to5SchemeUploadForm, Tc22to5PeriodData> {
	
	private static final long serialVersionUID = -7505750959683148542L;


	@Override
	public PeriodDataEntityManagerImpl<Tc22to5PeriodData> getPeriodDataEntityManagerImpl() {
		return tc22to5PeriodDataEntityManagerImpl;
	}

	@Autowired
	private Tc22to5RandomContentGenerator randomContentGenerator;

	@Autowired
	private Tc22to5SchemeServiceImpl schemeService;

	@Autowired
	private Tc22to5SchemeEntityManagerImpl schemeEntityManager;
	@Autowired
	private Tc22to5PeriodDataEntityManagerImpl tc22to5PeriodDataEntityManagerImpl;
	
	private String shrinkNums;
	private ShrinkBean shrinkBean;
	
	@Autowired
	@Qualifier("tc22to5SpinmatrixShrink")
	private Shrink spinmatrixShrink;
	
	
	@Autowired
	private SpinmatrixProcessor spinmatrixProcessor;
	

	
	@Override
	protected SchemeService<Tc22to5Scheme, Tc22to5SchemeDTO> getSchemeService() {
		return schemeService;
	}

	@Override
	protected SchemeEntityManager<Tc22to5Scheme> getSchemeEntityManager() {
		return schemeEntityManager;
	}

	@Override
	protected RandomContentGenerator getRandomContentGenerator() {
		return randomContentGenerator;
	}

	private Tc22to5PeriodData periodData;

	public Tc22to5SchemeCreateForm getCreateForm() {
		return createForm;
	}

	public void setCreateForm(Tc22to5SchemeCreateForm createForm) {
		this.createForm = createForm;
	}

	public Tc22to5SchemeUploadForm getUploadForm() {
		return uploadForm;
	}

	public void setUploadForm(Tc22to5SchemeUploadForm uploadForm) {
		this.uploadForm = uploadForm;
	}

	private Integer playType = 0;

	@Override
	public Lottery getLotteryType() {
		return Lottery.TC22TO5;
	}

	@Override
	protected String doShow() throws WebDataException {
		// ///扩展方法。取开奖号码

		if (null != period) {
			this.setPeriodData(tc22to5PeriodDataEntityManagerImpl.getPeriodData(period.getId()));
		}
		return super.doShow();
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
				if (num < 1 || num > 33)
					throw new WebDataException("基号只能从01-22中选择.");
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
		return "";
	}

	public Tc22to5PeriodData getPeriodData() {
		return periodData;
	}

	public void setPeriodData(Tc22to5PeriodData periodData) {
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
	public Class<Tc22to5PeriodData> getPeriodDataClass() {
		return Tc22to5PeriodData.class;
	}
}
