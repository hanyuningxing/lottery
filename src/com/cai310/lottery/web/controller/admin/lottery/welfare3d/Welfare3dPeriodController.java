package com.cai310.lottery.web.controller.admin.lottery.welfare3d;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.dom4j.DocumentException;

import com.cai310.lottery.Constant;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.entity.lottery.welfare3d.Welfare3dPeriodData;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.service.lottery.PeriodDataEntityManager;
import com.cai310.lottery.utils.liangcai.LiangCaiUtil;
import com.cai310.lottery.utils.liangcai.LiangNumResultVisitor;
import com.cai310.lottery.web.controller.WebDataException;
import com.cai310.lottery.web.controller.admin.lottery.PeriodAndDataController;
import com.cai310.utils.NumUtils;

@Namespace("/admin/lottery/welfare3d")
@Action("period")
@Results( {
		@Result(name = "success", type = "redirectAction", params = { "actionName", "period", "method", "edit", "id",
				"${id}" }), @Result(name = "list", location = "/WEB-INF/content/admin/lottery/period-list.ftl"),
		@Result(name = "editNew", location = "/WEB-INF/content/admin/lottery/period-editNew.ftl"),
		@Result(name = "edit", location = "/WEB-INF/content/admin/lottery/welfare3d/period-edit.ftl") })
public class Welfare3dPeriodController extends PeriodAndDataController {
	private static final long serialVersionUID = -4693805637373277840L;

	@Resource(name = "welfare3dPeriodDataEntityManagerImpl")
	private PeriodDataEntityManager<Welfare3dPeriodData> welfare3dPeriodDataEntityManager;

	@Override
	public Lottery getLotteryType() {
		return Lottery.WELFARE3D;
	}

	@Override
	protected PeriodDataEntityManager<Welfare3dPeriodData> getPeriodDataEntityManager() {
		return welfare3dPeriodDataEntityManager;
	}
	@SuppressWarnings("unchecked")
	@Override
	protected void getResultNum() throws Exception {
		if(null==this.getNum1())throw new WebDataException("开奖号码1为空.");
		if (this.getNum1() < 0 || this.getNum1() > 9)
			throw new DataException("开奖号码不正确,号码1在[0-9]之间.");
		
		if(null==this.getNum2())throw new WebDataException("开奖号码2为空.");
		if (this.getNum2() < 0 || this.getNum2() > 9)
			throw new DataException("开奖号码不正确,号码2在[0-9]之间.");
		
		if(null==this.getNum3())throw new WebDataException("开奖号码3为空.");
		if (this.getNum3() < 0 || this.getNum3() > 9)
			throw new DataException("开奖号码不正确,号码3在[0-9]之间.");
		
		StringBuilder result = new StringBuilder(25);
		List listTemp=new ArrayList();
		listTemp.add(this.getNum1());
		listTemp.add(this.getNum2());
		listTemp.add(this.getNum3());
		if(listTemp.size()!=3)throw new WebDataException("开奖号码数目出错.");
		result.append(StringUtils.join(listTemp, Constant.RESULT_SEPARATOR_FOR_NUMBER));
		if(null==this.getPeriodData())throw new DataException("开奖号码数据为空");
		Welfare3dPeriodData welfare3dPeriodData = (Welfare3dPeriodData) this.getPeriodData();
		welfare3dPeriodData.setResult(result.toString().trim());
		this.setPeriodData(welfare3dPeriodData);
		
	}
	@SuppressWarnings("unchecked")
	@Override
	protected void setResultNum() throws Exception {
		if(null!=this.getPeriodData()){
			Welfare3dPeriodData welfare3dPeriodData = (Welfare3dPeriodData) this.getPeriodData();
			if(StringUtils.isNotBlank(welfare3dPeriodData.getResult())){
				if(welfare3dPeriodData.getResult().indexOf(Constant.RESULT_SEPARATOR_FOR_NUMBER)!=-1){
					Integer[] resultArr=NumUtils.toIntegerArr(welfare3dPeriodData.getResult().split(Constant.RESULT_SEPARATOR_FOR_NUMBER));
					if(resultArr.length!=3)throw new WebDataException("开奖号码数目出错.");
					this.setNum1(resultArr[0]);
					this.setNum2(resultArr[1]);
					this.setNum3(resultArr[2]);
					List listTemp=new ArrayList();
					listTemp.add(this.getNum1());
					listTemp.add(this.getNum2());
					listTemp.add(this.getNum3());
					if(listTemp.size()!=3)throw new WebDataException("开奖号码数目出错.");
					if(null==this.getNum1())throw new WebDataException("开奖号码1为空.");
					if (this.getNum1() < 0 || this.getNum1() > 9)
						throw new DataException("开奖号码不正确,号码1在[0-9]之间.");
					
					if(null==this.getNum2())throw new WebDataException("开奖号码2为空.");
					if (this.getNum2() < 0 || this.getNum2() > 9)
						throw new DataException("开奖号码不正确,号码2在[0-9]之间.");
					
					if(null==this.getNum3())throw new WebDataException("开奖号码3为空.");
					if (this.getNum3() < 0 || this.getNum3() > 9)
						throw new DataException("开奖号码不正确,号码3在[0-9]之间.");
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
