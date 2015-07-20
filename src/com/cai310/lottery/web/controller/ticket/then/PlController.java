package com.cai310.lottery.web.controller.ticket.then;


import org.springframework.beans.factory.annotation.Autowired;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.dto.lottery.pl.PlSchemeDTO;
import com.cai310.lottery.entity.lottery.pl.PlScheme;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.service.lottery.SchemeService;
import com.cai310.lottery.service.lottery.pl.impl.PlSchemeServiceImpl;
import com.cai310.lottery.support.ContentBean;
import com.cai310.lottery.support.pl.PlContentBeanBuilder;
import com.cai310.lottery.support.pl.PlPlayType;
import com.cai310.lottery.support.dlt.DltContentBeanBuilder;
import com.cai310.lottery.support.jclq.PlayType;
import com.cai310.lottery.web.controller.WebDataException;
import com.cai310.lottery.web.controller.ticket.TicketBaseController;


public class PlController extends
                    NumberController<PlScheme, PlSchemeDTO> {
	private static final long serialVersionUID = 5783479221989581469L;

	/** 排列五直选 0 --  单式 ：01,02,03 复式： 01,02,03-01-01-01-01 */
	/** 排列三直选 1 --  单式 ：01,02,03 复式： 01,02,03-01,02,03-01 */
	/** 组三 2 --  单式 ：01,01,03 复式：01,02,03,04 */
	/** 组六  3 -- 单式 ：01,02,03 复式：01,02,03,04 */
	/** 直选和值  4 -- 复式：0-27 暂时不支持*/
	/** 组选和值 5 -- 复式：1-26 */
	/** 包串 6 -- 复式：01,02,03,04  暂时不支持*/
	/** 直选跨度 7 -- 复式：0-9*/
	/** 组选3跨度 8 -- 复式：1-9*/
	/** 组选6跨度  9 -- 复式：2-9*/
	protected PlPlayType bulidPlayType() throws WebDataException{
        try{
        	return PlPlayType.values()[playType];
        }catch(Exception e){
			logger.warn("玩法解析错误."+e.getMessage());
			throw new WebDataException("5-玩法解析错误.");
		}
    }
	@Autowired
	private PlSchemeServiceImpl schemeService;

	@Override
	protected SchemeService<PlScheme, PlSchemeDTO> getSchemeService() {
		return schemeService;
	}
	
	
	@Override
	protected PlSchemeDTO buildSchemeDTO() throws WebDataException {
		if(null==bulidPlayType()){
			throw new WebDataException("5-玩法解析错误.");
		}
		PlSchemeDTO dto = super.buildSchemeDTO();
		dto.setPlayType(bulidPlayType());
		return dto;
	}
	@Override
	protected ContentBean buildCompoundContentBean() throws WebDataException {
		try {
			if(this.schemeValue.indexOf("+")!=-1){
				//多注
				return PlContentBeanBuilder.buildCompoundContentBean(schemeValue.replaceAll("\\+", "\r\n"),bulidPlayType());
			}else{
				//单注
				if(this.schemeValue.indexOf(":")!=-1){
					return PlContentBeanBuilder.buildCompoundContentBean(this.schemeValue,bulidPlayType());
				}else{
					return PlContentBeanBuilder.buildCompoundContentBean(this.units+":"+this.schemeValue,bulidPlayType());
				}
			}
		} catch (DataException e) {
			throw new WebDataException("5-投注内容计算分析错误.("+e.getMessage()+")");
		}
	}
	@Override
	protected ContentBean buildSingleContentBean() throws WebDataException {
		try {
			return PlContentBeanBuilder.buildSingleContentBean(schemeValue.replaceAll("\\|", ",").replaceAll("\\+", "\r\n"),bulidPlayType());
		} catch (DataException e) {
			throw new WebDataException("5-投注内容计算分析错误.("+e.getMessage()+")");
		}
	}
	@Override
	protected Lottery getLottery() {
		return Lottery.PL;
	}

}
