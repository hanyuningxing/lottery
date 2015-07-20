package com.cai310.lottery.web.controller.ticket.then;


import org.springframework.beans.factory.annotation.Autowired;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.dto.lottery.ssq.SsqSchemeDTO;
import com.cai310.lottery.entity.lottery.ssq.SsqScheme;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.service.lottery.SchemeService;
import com.cai310.lottery.service.lottery.ssq.impl.SsqSchemeServiceImpl;
import com.cai310.lottery.support.ContentBean;
import com.cai310.lottery.support.dlt.DltContentBeanBuilder;
import com.cai310.lottery.support.ssq.SsqContentBeanBuilder;
import com.cai310.lottery.web.controller.WebDataException;
import com.cai310.lottery.web.controller.ticket.TicketBaseController;


public class SsqController extends
                    NumberController<SsqScheme, SsqSchemeDTO> {
	private static final long serialVersionUID = 5783479221989581469L;
	/**
	 * 
	 *  单式： 红球（6）+篮球（1） 例如：01,02,03,04,05,06|01
		复式：红球（大于6）+篮球（大于1） 例如：01,02,03,04,05,06,07|01,02

	 */
	@Autowired
	private SsqSchemeServiceImpl schemeService;

	@Override
	protected SchemeService<SsqScheme, SsqSchemeDTO> getSchemeService() {
		return schemeService;
	}
	
	
	@Override
	protected SsqSchemeDTO buildSchemeDTO() throws WebDataException {
		SsqSchemeDTO dto = super.buildSchemeDTO();
		return dto;
	}
	//01,02,03,04,05,06,07|01,02
	@Override
	protected ContentBean buildCompoundContentBean() throws WebDataException {
		try {
			//要求格式1(注数):04,13,14,15,28,31|01(方案内容)
			if(this.schemeValue.indexOf("+")!=-1){
				//多注
				return SsqContentBeanBuilder.buildCompoundContentBean(schemeValue.replaceAll("\\+", "\r\n"));
			}else{
				//单注
				if(this.schemeValue.indexOf(":")!=-1){
					return SsqContentBeanBuilder.buildCompoundContentBean(this.schemeValue);
				}else{
					return SsqContentBeanBuilder.buildCompoundContentBean(this.units+":"+this.schemeValue);
				}
			}
		} catch (DataException e) {
			throw new WebDataException("5-投注内容计算分析错误.("+e.getMessage()+")");
		}
	}
	///01,02,03,04,05,06|01
	@Override
	protected ContentBean buildSingleContentBean() throws WebDataException {
		try {
			return SsqContentBeanBuilder.buildSingleContentBean(schemeValue.replaceAll("\\|", ",").replaceAll("\\+", "\r\n"));
		} catch (DataException e) {
			throw new WebDataException("5-投注内容计算分析错误.("+e.getMessage()+")");
		}
	}
	@Override
	protected Lottery getLottery() {
		return Lottery.SSQ;
	}


}
