package com.cai310.lottery.web.controller.ticket.then;


import org.springframework.beans.factory.annotation.Autowired;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.dto.lottery.dlt.DltSchemeDTO;
import com.cai310.lottery.entity.lottery.dlt.DltScheme;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.service.lottery.SchemeService;
import com.cai310.lottery.service.lottery.dlt.impl.DltSchemeServiceImpl;
import com.cai310.lottery.support.ContentBean;
import com.cai310.lottery.support.dlt.DltContentBeanBuilder;
import com.cai310.lottery.support.dlt.DltPlayType;
import com.cai310.lottery.support.jclq.PlayType;
import com.cai310.lottery.support.ssc.SscContentBeanBuilder;
import com.cai310.lottery.web.controller.WebDataException;
import com.cai310.lottery.web.controller.ticket.TicketBaseController;


public class DltController extends
                    NumberController<DltScheme, DltSchemeDTO> {
	private static final long serialVersionUID = 5783479221989581469L;
	/**
	 * 
	 *  单式： 红球（5）+篮球（2） 例如：01,02,03,04,05|06,01
		复式：红球（大于5）+篮球（大于2） 例如：01,02,03,04,05|06,01

	 */
	
	/** 常规 0 */

	/** 12选2  1*/

	/** 常规追加  2*/
	protected DltPlayType bulidPlayType() throws WebDataException{
        try{
        	return DltPlayType.values()[playType];
        }catch(Exception e){
			logger.warn("玩法解析错误."+e.getMessage());
			throw new WebDataException("5-玩法解析错误.");
		}
    }
	@Autowired
	private DltSchemeServiceImpl schemeService;

	@Override
	protected SchemeService<DltScheme, DltSchemeDTO> getSchemeService() {
		return schemeService;
	}
	
	
	@Override
	protected DltSchemeDTO buildSchemeDTO() throws WebDataException {
		if(null!=bulidPlayType()){
			if(DltPlayType.GeneralAdditional.equals(bulidPlayType())){
				this.unitsMoney =3;
			}else{
				this.unitsMoney =2;
			}
		}else{
			throw new WebDataException("5-玩法解析错误.");
		}
		DltSchemeDTO dto = super.buildSchemeDTO();
		dto.setPlayType(bulidPlayType());
		return dto;
	}
	//01,02,03,04,05,06,07|01,02
	@Override
	protected ContentBean buildCompoundContentBean() throws WebDataException {
		try {
			//要求格式1(注数):04,13,14,15,28,30:01(方案内容)
			if(this.schemeValue.indexOf("+")!=-1){
				//多注
				return DltContentBeanBuilder.buildCompoundContentBean(schemeValue.replaceAll("\\+", "\r\n"),bulidPlayType());
			}else{
				//单注
				if(this.schemeValue.indexOf(":")!=-1){
					return DltContentBeanBuilder.buildCompoundContentBean(this.schemeValue,bulidPlayType());
				}else{
					return DltContentBeanBuilder.buildCompoundContentBean(this.units+":"+this.schemeValue,bulidPlayType());
				}
			}
		} catch (DataException e) {
			throw new WebDataException("5-投注内容计算分析错误.("+e.getMessage()+")");
		}
	}
	///01,02,03,04,05|06,01
	@Override
	protected ContentBean buildSingleContentBean() throws WebDataException {
		try {
			return DltContentBeanBuilder.buildSingleContentBean(schemeValue.replaceAll("\\|", ",").replaceAll("\\+", "\r\n"),bulidPlayType());
		} catch (DataException e) {
			throw new WebDataException("5-投注内容计算分析错误.("+e.getMessage()+")");
		}
	}
	@Override
	protected Lottery getLottery() {
		return Lottery.DLT;
	}

}
