package com.cai310.lottery.web.controller.admin.lottery.ssq;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.dom4j.DocumentException;

import com.cai310.lottery.Constant;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.entity.lottery.ssq.SsqPeriodData;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.service.lottery.PeriodDataEntityManager;
import com.cai310.lottery.utils.liangcai.LiangCaiUtil;
import com.cai310.lottery.utils.liangcai.LiangNumResultVisitor;
import com.cai310.lottery.web.controller.WebDataException;
import com.cai310.lottery.web.controller.admin.lottery.PeriodAndDataController;
import com.cai310.utils.NumUtils;

@Namespace("/admin/lottery/ssq")
@Action("period")
@Results( {
		@Result(name = "success", type = "redirectAction", params = { "actionName", "period", "method", "edit", "id",
				"${id}" }), @Result(name = "list", location = "/WEB-INF/content/admin/lottery/period-list.ftl"),
		@Result(name = "editNew", location = "/WEB-INF/content/admin/lottery/period-editNew.ftl"),
		@Result(name = "edit", location = "/WEB-INF/content/admin/lottery/ssq/period-edit.ftl") })
public class SsqPeriodController extends PeriodAndDataController {

	private static final long serialVersionUID = -4693805637373277840L;

	@Resource
	private PeriodDataEntityManager<SsqPeriodData> ssqPeriodDataEntityManager;

	@Override
	public Lottery getLotteryType() {
		return Lottery.SSQ;
	}

	@Override
	protected PeriodDataEntityManager<SsqPeriodData> getPeriodDataEntityManager() {
		return ssqPeriodDataEntityManager;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void getResultNum() throws Exception {
		if (null == this.getNum1())
			throw new WebDataException("红球开奖号码1为空.");
		if (this.getNum1() < 1 || this.getNum1() > 33)
			throw new WebDataException("开奖号码不正确,红球号码1在[01-35]之间.");
		if (null == this.getNum2())
			throw new WebDataException("红球开奖号码2为空.");
		if (this.getNum2() < 1 || this.getNum2() > 33)
			throw new WebDataException("开奖号码不正确,红球号码2在[01-35]之间.");
		if (null == this.getNum3())
			throw new WebDataException("红球开奖号码3为空.");
		if (this.getNum3() < 1 || this.getNum3() > 33)
			throw new WebDataException("开奖号码不正确,红球号码3在[01-35]之间.");
		if (null == this.getNum4())
			throw new WebDataException("红球开奖号码4为空.");
		if (this.getNum4() < 1 || this.getNum4() > 33)
			throw new WebDataException("开奖号码不正确,红球号码4在[01-35]之间.");
		if (null == this.getNum5())
			throw new WebDataException("红球开奖号码5为空.");
		if (this.getNum5() < 1 || this.getNum5() > 33)
			throw new WebDataException("开奖号码不正确,红球号码5在[01-35]之间.");
		if (null == this.getNum6())
			throw new WebDataException("红球开奖号码6为空.");
		if (this.getNum6() < 1 || this.getNum6() > 33)
			throw new WebDataException("方案内容不正确,红球号码6在[01-33]之间.");
		if (null == this.getNum7())
			throw new WebDataException("篮球开奖号码为空.");
		if (this.getNum7() < 1 || this.getNum7() > 16)
			throw new WebDataException("方案内容不正确,篮球号码在[01-16]之间.");

		StringBuilder result = new StringBuilder(25);
		Set setTemp = new LinkedHashSet();
		setTemp.add(this.getNum1());
		setTemp.add(this.getNum2());
		setTemp.add(this.getNum3());
		setTemp.add(this.getNum4());
		setTemp.add(this.getNum5());
		setTemp.add(this.getNum6());
		if (setTemp.size() != 6)
			throw new WebDataException("红球开奖号码重复.");
		List list = new ArrayList(setTemp);
		list.add(this.getNum7());
		result.append(StringUtils.join(list, Constant.RESULT_SEPARATOR_FOR_NUMBER));
		if (null == this.getPeriodData())
			throw new DataException("开奖号码数据为空");
		SsqPeriodData ssqPeriodData = (SsqPeriodData) this.getPeriodData();
		ssqPeriodData.setResult(result.toString().trim());
		this.setPeriodData(ssqPeriodData);

	}

	@SuppressWarnings("unchecked")
	@Override
	protected void setResultNum() throws Exception {
		if (null != this.getPeriodData()) {
			SsqPeriodData ssqPeriodData = (SsqPeriodData) this.getPeriodData();
			if (StringUtils.isNotBlank(ssqPeriodData.getResult())) {
				if (ssqPeriodData.getResult().indexOf(Constant.RESULT_SEPARATOR_FOR_NUMBER) != -1) {
					Integer[] resultArr = NumUtils.toIntegerArr(ssqPeriodData.getResult().split(
							Constant.RESULT_SEPARATOR_FOR_NUMBER));
					if (resultArr.length != 7)
						throw new WebDataException("开奖号码数目出错.");
					this.setNum1(resultArr[0]);
					this.setNum2(resultArr[1]);
					this.setNum3(resultArr[2]);
					this.setNum4(resultArr[3]);
					this.setNum5(resultArr[4]);
					this.setNum6(resultArr[5]);
					this.setNum7(resultArr[6]);
					Set setTemp = new LinkedHashSet();
					setTemp.add(this.getNum1());
					setTemp.add(this.getNum2());
					setTemp.add(this.getNum3());
					setTemp.add(this.getNum4());
					setTemp.add(this.getNum5());
					setTemp.add(this.getNum6());
					if (setTemp.size() != 6)
						throw new WebDataException("红球开奖号码重复.");
					if (null == this.getNum1())
						throw new WebDataException("红球开奖号码1为空.");
					if (this.getNum1() < 1 || this.getNum1() > 33)
						throw new WebDataException("开奖号码不正确,红球号码1在[01-35]之间.");
					if (null == this.getNum2())
						throw new WebDataException("红球开奖号码2为空.");
					if (this.getNum2() < 1 || this.getNum2() > 33)
						throw new WebDataException("开奖号码不正确,红球号码2在[01-35]之间.");
					if (null == this.getNum3())
						throw new WebDataException("红球开奖号码3为空.");
					if (this.getNum3() < 1 || this.getNum3() > 33)
						throw new WebDataException("开奖号码不正确,红球号码3在[01-35]之间.");
					if (null == this.getNum4())
						throw new WebDataException("红球开奖号码4为空.");
					if (this.getNum4() < 1 || this.getNum4() > 33)
						throw new WebDataException("开奖号码不正确,红球号码4在[01-35]之间.");
					if (null == this.getNum5())
						throw new WebDataException("红球开奖号码5为空.");
					if (this.getNum5() < 1 || this.getNum5() > 33)
						throw new WebDataException("开奖号码不正确,红球号码5在[01-35]之间.");
					if (null == this.getNum6())
						throw new WebDataException("红球开奖号码6为空.");
					if (this.getNum6() < 1 || this.getNum6() > 33)
						throw new WebDataException("方案内容不正确,红球号码6在[01-33]之间.");
					if (null == this.getNum7())
						throw new WebDataException("篮球开奖号码为空.");
					if (this.getNum7() < 1 || this.getNum7() > 16)
						throw new WebDataException("方案内容不正确,篮球号码在[01-16]之间.");
				}
			}
		}

	}
	/**
	 * 获取开奖号码
	 */
	protected String getResultNumber() throws DataException, IOException, DocumentException{
		LiangNumResultVisitor liangNumResultVisitor=LiangCaiUtil.getResult(getLotteryType(),Byte.valueOf("0"),periodNumber);
		if(liangNumResultVisitor.getIsSuccess()){
			return liangNumResultVisitor.getResult();

		}
		return null;
	}
}
