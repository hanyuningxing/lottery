package com.cai310.lottery.web.controller.admin.lottery.dlt;

import java.io.IOException;
import java.util.LinkedHashSet;
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
import com.cai310.lottery.entity.lottery.dlt.DltPeriodData;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.service.lottery.PeriodDataEntityManager;
import com.cai310.lottery.utils.liangcai.LiangCaiUtil;
import com.cai310.lottery.utils.liangcai.LiangNumResultVisitor;
import com.cai310.lottery.web.controller.WebDataException;
import com.cai310.lottery.web.controller.admin.lottery.PeriodAndDataController;
import com.cai310.utils.NumUtils;

@Namespace("/admin/lottery/dlt")
@Action("period")
@Results( {
		@Result(name = "success", type = "redirectAction", params = { "actionName", "period", "method", "edit", "id",
				"${id}" }), @Result(name = "list", location = "/WEB-INF/content/admin/lottery/period-list.ftl"),
		@Result(name = "editNew", location = "/WEB-INF/content/admin/lottery/period-editNew.ftl"),
		@Result(name = "edit", location = "/WEB-INF/content/admin/lottery/dlt/period-edit.ftl") })
public class DltPeriodController extends PeriodAndDataController {

	private static final long serialVersionUID = -4693805637373277840L;

	@Resource
	private PeriodDataEntityManager<DltPeriodData> dltPeriodDataEntityManager;

	@Override
	public Lottery getLotteryType() {
		return Lottery.DLT;
	}

	@Override
	protected PeriodDataEntityManager<DltPeriodData> getPeriodDataEntityManager() {
		return dltPeriodDataEntityManager;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void getResultNum() throws Exception {
		if(null==this.getNum1())throw new WebDataException("前区开奖号码1为空.");
		if (this.getNum1() < 1 || this.getNum1() > 35)
			throw new DataException("开奖号码不正确,前区号码1在[01-35]之间.");
		if(null==this.getNum2())throw new WebDataException("前区开奖号码2为空.");
		if (this.getNum2() < 1 || this.getNum2() > 35)
			throw new DataException("开奖号码不正确,前区号码2在[01-35]之间.");
		if(null==this.getNum3())throw new WebDataException("前区开奖号码3为空.");
		if (this.getNum3() < 1 || this.getNum3() > 35)
			throw new DataException("开奖号码不正确,前区号码3在[01-35]之间.");
		if(null==this.getNum4())throw new WebDataException("前区开奖号码4为空.");
		if (this.getNum4() < 1 || this.getNum4() > 35)
			throw new DataException("开奖号码不正确,前区号码4在[01-35]之间.");
		if(null==this.getNum5())throw new WebDataException("前区开奖号码5为空.");
		if (this.getNum5() < 1 || this.getNum5() > 35)
			throw new DataException("开奖号码不正确,前区号码5在[01-35]之间.");
		if(null==this.getNum6())throw new WebDataException("后区开奖号码1为空.");
		if (this.getNum6() < 1 || this.getNum6() > 12)
			throw new DataException("方案内容不正确,后区号码1在[01-12]之间.");
		if(null==this.getNum7())throw new WebDataException("后区开奖号码2为空.");
		if (this.getNum7() < 1 || this.getNum7() > 12)
			throw new DataException("方案内容不正确,后区号码2在[01-12]之间.");
		
		StringBuilder result = new StringBuilder(25);
		Set setTemp=new LinkedHashSet();
		setTemp.add(this.getNum1());
		setTemp.add(this.getNum2());
		setTemp.add(this.getNum3());
		setTemp.add(this.getNum4());
		setTemp.add(this.getNum5());
		if(setTemp.size()!=5)throw new WebDataException("前区开奖号码重复.");
		result.append(StringUtils.join(setTemp, Constant.RESULT_SEPARATOR_FOR_NUMBER));
		setTemp=new LinkedHashSet();
		setTemp.add(this.getNum6());
		setTemp.add(this.getNum7());
		if(setTemp.size()!=2)throw new WebDataException("后区开奖号码重复.");
		
		
		result.append(Constant.RESULT_SEPARATOR_FOR_NUMBER).append(StringUtils.join(setTemp, Constant.RESULT_SEPARATOR_FOR_NUMBER));
		if(null==this.getPeriodData())throw new DataException("开奖号码数据为空");
		DltPeriodData dltPeriodData = (DltPeriodData) this.getPeriodData();
		dltPeriodData.setResult(result.toString().trim());
		this.setPeriodData(dltPeriodData);
		
	}
	@SuppressWarnings("unchecked")
	@Override
	protected void setResultNum() throws Exception {
		if(null!=this.getPeriodData()){
			DltPeriodData dltPeriodData = (DltPeriodData) this.getPeriodData();
			if(StringUtils.isNotBlank(dltPeriodData.getResult())){
				if(dltPeriodData.getResult().indexOf(Constant.RESULT_SEPARATOR_FOR_NUMBER)!=-1){
					Integer[] resultArr=NumUtils.toIntegerArr(dltPeriodData.getResult().split(Constant.RESULT_SEPARATOR_FOR_NUMBER));
					if(resultArr.length!=7)throw new WebDataException("开奖号码数目出错.");
					this.setNum1(resultArr[0]);
					this.setNum2(resultArr[1]);
					this.setNum3(resultArr[2]);
					this.setNum4(resultArr[3]);
					this.setNum5(resultArr[4]);
					this.setNum6(resultArr[5]);
					this.setNum7(resultArr[6]);
					Set setTemp=new LinkedHashSet();
					setTemp.add(this.getNum1());
					setTemp.add(this.getNum2());
					setTemp.add(this.getNum3());
					setTemp.add(this.getNum4());
					setTemp.add(this.getNum5());
					if(setTemp.size()!=5)throw new WebDataException("前区开奖号码重复.");
					setTemp=new LinkedHashSet();
					setTemp.add(this.getNum6());
					setTemp.add(this.getNum7());
					if(setTemp.size()!=2)throw new WebDataException("后区开奖号码重复.");
					if (this.getNum1() < 1 || this.getNum1() > 35)
						throw new DataException("开奖号码不正确,前区号码1在[01-35]之间.");
					if(null==this.getNum2())throw new WebDataException("前区开奖号码2为空.");
					if (this.getNum2() < 1 || this.getNum2() > 35)
						throw new DataException("开奖号码不正确,前区号码2在[01-35]之间.");
					if(null==this.getNum3())throw new WebDataException("前区开奖号码3为空.");
					if (this.getNum3() < 1 || this.getNum3() > 35)
						throw new DataException("开奖号码不正确,前区号码3在[01-35]之间.");
					if(null==this.getNum4())throw new WebDataException("前区开奖号码4为空.");
					if (this.getNum4() < 1 || this.getNum4() > 35)
						throw new DataException("开奖号码不正确,前区号码4在[01-35]之间.");
					if(null==this.getNum5())throw new WebDataException("前区开奖号码5为空.");
					if (this.getNum5() < 1 || this.getNum5() > 35)
						throw new DataException("开奖号码不正确,前区号码5在[01-35]之间.");
					if(null==this.getNum6())throw new WebDataException("后区开奖号码1为空.");
					if (this.getNum6() < 1 || this.getNum6() > 12)
						throw new DataException("方案内容不正确,后区号码1在[01-12]之间.");
					if(null==this.getNum7())throw new WebDataException("后区开奖号码2为空.");
					if (this.getNum7() < 1 || this.getNum7() > 12)
						throw new DataException("方案内容不正确,后区号码2在[01-12]之间.");
				}
			}
		}
		
	}
	/**
	 * 获取开奖号码
	 */
	protected String getResultNumber() throws DataException, IOException, DocumentException{
		String periodNumber_="20"+periodNumber;
		LiangNumResultVisitor liangNumResultVisitor=LiangCaiUtil.getResult(getLotteryType(),Byte.valueOf("0"),periodNumber_);
		if(liangNumResultVisitor.getIsSuccess()){
			return liangNumResultVisitor.getResult();

		}
		return null;
	}
}
