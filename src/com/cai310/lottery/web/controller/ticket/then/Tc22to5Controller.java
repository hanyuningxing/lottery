package com.cai310.lottery.web.controller.ticket.then;


import org.springframework.beans.factory.annotation.Autowired;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.dto.lottery.tc22to5.Tc22to5SchemeDTO;
import com.cai310.lottery.entity.lottery.tc22to5.Tc22to5Scheme;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.service.lottery.SchemeService;
import com.cai310.lottery.service.lottery.tc22to5.impl.Tc22to5SchemeServiceImpl;
import com.cai310.lottery.support.ContentBean;
import com.cai310.lottery.support.sevenstar.SevenstarContentBeanBuilder;
import com.cai310.lottery.support.tc22to5.Tc22to5ContentBeanBuilder;
import com.cai310.lottery.web.controller.WebDataException;
import com.cai310.lottery.web.controller.ticket.TicketBaseController;


public class Tc22to5Controller extends
                    NumberController<Tc22to5Scheme, Tc22to5SchemeDTO> {
	private static final long serialVersionUID = 5783479221989581469L;
	/**
	 * 
		复式	彩球（大于7） 例如：01,02,03,04,05,06,07,08,09
		单式	彩球（7）例如：01,02,03,04,05


	 */
	@Autowired
	private Tc22to5SchemeServiceImpl schemeService;

	@Override
	protected SchemeService<Tc22to5Scheme, Tc22to5SchemeDTO> getSchemeService() {
		return schemeService;
	}
	
	
	@Override
	protected Tc22to5SchemeDTO buildSchemeDTO() throws WebDataException {
		Tc22to5SchemeDTO dto = super.buildSchemeDTO();
		return dto;
	}
	@Override
	protected ContentBean buildCompoundContentBean() throws WebDataException {
		try {
			if(this.schemeValue.indexOf("+")!=-1){
				//多注
				return Tc22to5ContentBeanBuilder.buildCompoundContentBean(schemeValue.replaceAll("\\+", "\r\n"));
			}else{
				//单注
				if(this.schemeValue.indexOf(":")!=-1){
					return Tc22to5ContentBeanBuilder.buildCompoundContentBean(this.schemeValue);
				}else{
					return Tc22to5ContentBeanBuilder.buildCompoundContentBean(this.units+":"+this.schemeValue);
				}
			}
		} catch (DataException e) {
			throw new WebDataException("5-投注内容计算分析错误.("+e.getMessage()+")");
		}
	}
	@Override
	protected ContentBean buildSingleContentBean() throws WebDataException {
		try {
			return Tc22to5ContentBeanBuilder.buildSingleContentBean(schemeValue.replaceAll("\\|", ",").replaceAll("\\+", "\r\n"));
		} catch (DataException e) {
			throw new WebDataException("5-投注内容计算分析错误.("+e.getMessage()+")");
		}
	}
	@Override
	protected Lottery getLottery() {
		return Lottery.TC22TO5;
	}


}
