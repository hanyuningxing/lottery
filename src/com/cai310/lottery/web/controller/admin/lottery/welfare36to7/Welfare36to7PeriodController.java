package com.cai310.lottery.web.controller.admin.lottery.welfare36to7;

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
import com.cai310.lottery.entity.lottery.welfare36to7.Welfare36to7PeriodData;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.service.lottery.PeriodDataEntityManager;
import com.cai310.lottery.utils.liangcai.LiangCaiUtil;
import com.cai310.lottery.utils.liangcai.LiangNumResultVisitor;
import com.cai310.lottery.web.controller.WebDataException;
import com.cai310.lottery.web.controller.admin.lottery.PeriodAndDataController;
import com.cai310.utils.NumUtils;

@Namespace("/admin/lottery/welfare36to7")
@Action("period")
@Results( {
		@Result(name = "success", type = "redirectAction", params = { "actionName", "period", "method", "edit", "id",
				"${id}" }), @Result(name = "list", location = "/WEB-INF/content/admin/lottery/period-list.ftl"),
		@Result(name = "editNew", location = "/WEB-INF/content/admin/lottery/period-editNew.ftl"),
		@Result(name = "edit", location = "/WEB-INF/content/admin/lottery/welfare36to7/period-edit.ftl") })
public class Welfare36to7PeriodController extends PeriodAndDataController {

	private static final long serialVersionUID = -4693805637373277840L;

	@Resource(name = "welfare36to7PeriodDataEntityManagerImpl")
	private PeriodDataEntityManager<Welfare36to7PeriodData> welfare36to7PeriodDataEntityManager;

	@Override
	public Lottery getLotteryType() {
		return Lottery.WELFARE36To7;
	}

	@Override
	protected PeriodDataEntityManager<Welfare36to7PeriodData> getPeriodDataEntityManager() {
		return welfare36to7PeriodDataEntityManager;
	}
	@SuppressWarnings("unchecked")
	@Override
	protected void getResultNum() throws Exception {
		if(null==this.getNum1())throw new WebDataException("基本开奖号码1为空.");
		if (this.getNum1() < 1 || this.getNum1() > 36)
			throw new DataException("开奖号码不正确,基本号码1在[01-36]之间.");
		if(null==this.getNum2())throw new WebDataException("基本开奖号码2为空.");
		if (this.getNum2() < 1 || this.getNum2() > 36)
			throw new DataException("开奖号码不正确,基本号码1在[01-36]之间.");
		if(null==this.getNum3())throw new WebDataException("基本开奖号码3为空.");
		if (this.getNum3() < 1 || this.getNum3() > 36)
			throw new DataException("开奖号码不正确,基本号码1在[01-36]之间.");
		if(null==this.getNum4())throw new WebDataException("基本开奖号码4为空.");
		if (this.getNum4() < 1 || this.getNum4() > 36)
			throw new DataException("开奖号码不正确,基本号码1在[01-36]之间.");
		if(null==this.getNum5())throw new WebDataException("基本开奖号码5为空.");
		if (this.getNum5() < 1 || this.getNum5() > 36)
			throw new DataException("开奖号码不正确,基本号码1在[01-36]之间.");
		if(null==this.getNum6())throw new WebDataException("基本开奖号码6为空.");
		if (this.getNum6() < 1 || this.getNum6() > 36)
			throw new DataException("开奖号码不正确,基本号码1在[01-36]之间.");
		if(null==this.getNum7())throw new WebDataException("篮球开奖号码为空.");
		if (this.getNum7() < 1 || this.getNum7() > 36)
			throw new DataException("开奖号码不正确,特别号码1在[01-36]之间.");
		
		StringBuilder result = new StringBuilder(25);
		Set setTemp=new LinkedHashSet();
		setTemp.add(this.getNum1());
		setTemp.add(this.getNum2());
		setTemp.add(this.getNum3());
		setTemp.add(this.getNum4());
		setTemp.add(this.getNum5());
		setTemp.add(this.getNum6());
		setTemp.add(this.getNum7());
		if(setTemp.size()!=7)throw new WebDataException("开奖号码重复.");
		
		result.append(StringUtils.join(setTemp, Constant.RESULT_SEPARATOR_FOR_NUMBER));
		if(null==this.getPeriodData())throw new DataException("开奖号码数据为空");
		Welfare36to7PeriodData welfare36to7PeriodData = (Welfare36to7PeriodData) this.getPeriodData();
		welfare36to7PeriodData.setResult(result.toString().trim());
		this.setPeriodData(welfare36to7PeriodData);
		
	}
	@SuppressWarnings("unchecked")
	@Override
	protected void setResultNum() throws Exception {
		if(null!=this.getPeriodData()){
			Welfare36to7PeriodData welfare36to7PeriodData = (Welfare36to7PeriodData) this.getPeriodData();
			if(StringUtils.isNotBlank(welfare36to7PeriodData.getResult())){
				if(welfare36to7PeriodData.getResult().indexOf(Constant.RESULT_SEPARATOR_FOR_NUMBER)!=-1){
					Integer[] resultArr=NumUtils.toIntegerArr(welfare36to7PeriodData.getResult().split(Constant.RESULT_SEPARATOR_FOR_NUMBER));
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
					setTemp.add(this.getNum6());
					setTemp.add(this.getNum7());
					if(setTemp.size()!=7)throw new WebDataException("开奖号码重复.");
					
					if(null==this.getNum1())throw new WebDataException("基本开奖号码1为空.");
					if (this.getNum1() < 1 || this.getNum1() > 36)
						throw new DataException("开奖号码不正确,基本号码1在[01-36]之间.");
					if(null==this.getNum2())throw new WebDataException("基本开奖号码2为空.");
					if (this.getNum2() < 1 || this.getNum2() > 36)
						throw new DataException("开奖号码不正确,基本号码1在[01-36]之间.");
					if(null==this.getNum3())throw new WebDataException("基本开奖号码3为空.");
					if (this.getNum3() < 1 || this.getNum3() > 36)
						throw new DataException("开奖号码不正确,基本号码1在[01-36]之间.");
					if(null==this.getNum4())throw new WebDataException("基本开奖号码4为空.");
					if (this.getNum4() < 1 || this.getNum4() > 36)
						throw new DataException("开奖号码不正确,基本号码1在[01-36]之间.");
					if(null==this.getNum5())throw new WebDataException("基本开奖号码5为空.");
					if (this.getNum5() < 1 || this.getNum5() > 36)
						throw new DataException("开奖号码不正确,基本号码1在[01-36]之间.");
					if(null==this.getNum6())throw new WebDataException("基本开奖号码6为空.");
					if (this.getNum6() < 1 || this.getNum6() > 36)
						throw new DataException("开奖号码不正确,基本号码1在[01-36]之间.");
					if(null==this.getNum7())throw new WebDataException("篮球开奖号码为空.");
					if (this.getNum7() < 1 || this.getNum7() > 36)
						throw new DataException("开奖号码不正确,特别号码1在[01-36]之间.");
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
