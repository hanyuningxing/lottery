package com.cai310.lottery.web.controller.ticket.then;


import org.springframework.beans.factory.annotation.Autowired;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.dto.lottery.sevenstar.SevenstarSchemeDTO;
import com.cai310.lottery.entity.lottery.sevenstar.SevenstarScheme;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.service.lottery.SchemeService;
import com.cai310.lottery.service.lottery.sevenstar.impl.SevenstarSchemeServiceImpl;
import com.cai310.lottery.support.ContentBean;
import com.cai310.lottery.support.seven.SevenContentBeanBuilder;
import com.cai310.lottery.support.sevenstar.SevenstarContentBeanBuilder;
import com.cai310.lottery.web.controller.WebDataException;
import com.cai310.lottery.web.controller.ticket.TicketBaseController;


public class SevenstarController extends
                    NumberController<SevenstarScheme, SevenstarSchemeDTO> {
	private static final long serialVersionUID = 5783479221989581469L;
	/**
	 * 
		复式	彩球（大于7） 例如：1,2,3,4-1,2,3,4-1,2,3,4-1,2,3,4-1,2,3,4-1,2,3,4-1,2,3,4
		单式	彩球（7）例如：01,02,03,04,05,06,07
	 */
	@Autowired
	private SevenstarSchemeServiceImpl schemeService;

	@Override
	protected SchemeService<SevenstarScheme, SevenstarSchemeDTO> getSchemeService() {
		return schemeService;
	}
	
	
	@Override
	protected SevenstarSchemeDTO buildSchemeDTO() throws WebDataException {
		SevenstarSchemeDTO dto = super.buildSchemeDTO();
		return dto;
	}
	@Override
	protected ContentBean buildCompoundContentBean() throws WebDataException {
		try {
			if(this.schemeValue.indexOf("+")!=-1){
				//多注
				return SevenstarContentBeanBuilder.buildCompoundContentBean(schemeValue.replaceAll("\\+", "\r\n"));
			}else{
				//单注
				if(this.schemeValue.indexOf(":")!=-1){
					return SevenstarContentBeanBuilder.buildCompoundContentBean(this.schemeValue);
				}else{
					return SevenstarContentBeanBuilder.buildCompoundContentBean(this.units+":"+this.schemeValue);
				}
			}
		} catch (DataException e) {
			throw new WebDataException("5-投注内容计算分析错误.("+e.getMessage()+")");
		}
	}
	@Override
	protected ContentBean buildSingleContentBean() throws WebDataException {
		try {
			return SevenstarContentBeanBuilder.buildSingleContentBean(schemeValue.replaceAll("\\|", ",").replaceAll("\\+", "\r\n"));
		} catch (DataException e) {
			throw new WebDataException("5-投注内容计算分析错误.("+e.getMessage()+")");
		}
	}
	@Override
	protected Lottery getLottery() {
		return Lottery.SEVENSTAR;
	}


}
