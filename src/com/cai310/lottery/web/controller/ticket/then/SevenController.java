package com.cai310.lottery.web.controller.ticket.then;


import org.springframework.beans.factory.annotation.Autowired;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.dto.lottery.seven.SevenSchemeDTO;
import com.cai310.lottery.entity.lottery.seven.SevenScheme;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.service.lottery.SchemeService;
import com.cai310.lottery.service.lottery.seven.impl.SevenSchemeServiceImpl;
import com.cai310.lottery.support.ContentBean;
import com.cai310.lottery.support.seven.SevenContentBeanBuilder;
import com.cai310.lottery.support.ssq.SsqContentBeanBuilder;
import com.cai310.lottery.web.controller.WebDataException;
import com.cai310.lottery.web.controller.ticket.TicketBaseController;


public class SevenController extends
                    NumberController<SevenScheme, SevenSchemeDTO> {
	private static final long serialVersionUID = 5783479221989581469L;
	/**
	 * 
		复式	彩球（大于7） 例如：01,02,03,04,05,06,07,08,09
		单式	彩球（7）例如：01,02,03,04,05,06,07
		胆拖	彩球胆码（大于1）+ 彩球拖码    (加起来大于7)
		例如：01,02,03;04,05,06,07,08 
		注：红色为彩球胆码，橙色为彩球拖码


	 */
	@Autowired
	private SevenSchemeServiceImpl schemeService;

	@Override
	protected SchemeService<SevenScheme, SevenSchemeDTO> getSchemeService() {
		return schemeService;
	}
	
	
	@Override
	protected SevenSchemeDTO buildSchemeDTO() throws WebDataException {
		SevenSchemeDTO dto = super.buildSchemeDTO();
		return dto;
	}
	//01,02,03,04,05,06,07,08,09
	@Override
	protected ContentBean buildCompoundContentBean() throws WebDataException {
		try {
			//要求格式1(注数):01,02,03,04,05,06,07,08,09(方案内容)
			if(this.schemeValue.indexOf("+")!=-1){
				//多注
				return SevenContentBeanBuilder.buildCompoundContentBean(schemeValue.replaceAll("\\+", "\r\n"));
			}else{
				//单注
				if(this.schemeValue.indexOf(":")!=-1){
					return SevenContentBeanBuilder.buildCompoundContentBean(this.schemeValue);
				}else{
					return SevenContentBeanBuilder.buildCompoundContentBean(this.units+":"+this.schemeValue);
				}
			}
		} catch (DataException e) {
			throw new WebDataException("5-投注内容计算分析错误.("+e.getMessage()+")");
		}
	}
	///01,02,03,04,05,06,07
	@Override
	protected ContentBean buildSingleContentBean() throws WebDataException {
		try {
			return SevenContentBeanBuilder.buildSingleContentBean(schemeValue.replaceAll("\\|", ",").replaceAll("\\+", "\r\n"));
		} catch (DataException e) {
			throw new WebDataException("5-投注内容计算分析错误.("+e.getMessage()+")");
		}
	}
	@Override
	protected Lottery getLottery() {
		return Lottery.SEVEN;
	}


}
