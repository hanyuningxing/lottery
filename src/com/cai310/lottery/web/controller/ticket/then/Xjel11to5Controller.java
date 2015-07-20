package com.cai310.lottery.web.controller.ticket.then;

import javax.annotation.Resource;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.entity.lottery.keno.xjel11to5.XjEl11to5IssueData;
import com.cai310.lottery.entity.lottery.keno.xjel11to5.XjEl11to5Scheme;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.service.lottery.keno.KenoManager;
import com.cai310.lottery.service.lottery.keno.KenoPlayer;
import com.cai310.lottery.service.lottery.keno.KenoService;
import com.cai310.lottery.support.ContentBean;
import com.cai310.lottery.support.xjel11to5.XjEl11to5ContentBeanBuilder;
import com.cai310.lottery.support.xjel11to5.XjEl11to5PlayType;
import com.cai310.lottery.web.controller.WebDataException;
import com.cai310.lottery.web.controller.lottery.keno.KenoSchemeDTO;

public class Xjel11to5Controller extends KenoController<XjEl11to5Scheme,KenoSchemeDTO,XjEl11to5IssueData>{

	private static final long serialVersionUID = 9092387820385267545L;
	protected XjEl11to5PlayType bulidPlayType() throws WebDataException{
        try{
        	return XjEl11to5PlayType.values()[playType];
        }catch(Exception e){
			logger.warn("玩法解析错误."+e.getMessage());
			throw new WebDataException("5-玩法解析错误.");
		}
    }
	@Resource(name = "xjel11to5KenoServiceImpl")
	@Override
	public void setKenoService(
			KenoService<XjEl11to5IssueData, XjEl11to5Scheme> kenoService) {
		this.kenoService=kenoService;
	}
	@Resource(name = "xjel11to5KenoManagerImpl")
	@Override
	public void setKenoManager(
			KenoManager<XjEl11to5IssueData, XjEl11to5Scheme> kenoManager) {
		this.kenoManager=kenoManager;
	}
	@Resource(name = "xjel11to5KenoPlayer")
	@Override
	public void setKenoPlayer(
			KenoPlayer<XjEl11to5IssueData, XjEl11to5Scheme> kenoPlayer) {
		this.kenoPlayer=kenoPlayer;
	}

	@Override
	protected Lottery getLottery() {
		return Lottery.XJEL11TO5;
	}

	@Override
	protected ContentBean buildCompoundContentBean() throws WebDataException {
		try {
			if(this.schemeValue.indexOf("+")!=-1){
				//多注
				return XjEl11to5ContentBeanBuilder.buildCompoundContentBean(schemeValue.replaceAll("\\+", "\r\n"),bulidPlayType());
			}else{
				//单注
				if(this.schemeValue.indexOf(":")!=-1){
					return XjEl11to5ContentBeanBuilder.buildCompoundContentBean(this.schemeValue,bulidPlayType());
				}else{
					return XjEl11to5ContentBeanBuilder.buildCompoundContentBean(this.units+":"+this.schemeValue,bulidPlayType());
				}
			}
		} catch (DataException e) {
			throw new WebDataException("5-投注内容计算分析错误.("+e.getMessage()+")");
		}
	}

	@Override
	protected ContentBean buildSingleContentBean() throws WebDataException {
		try {
			return XjEl11to5ContentBeanBuilder.buildSingleContentBean(schemeValue.replaceAll("M", "\r\n"),bulidPlayType());
		} catch (DataException e) {
			throw new WebDataException("5-投注内容计算分析错误.("+e.getMessage()+")");
		}
	}
	@Override
	protected KenoSchemeDTO buildSchemeDTO() throws WebDataException {
		KenoSchemeDTO kenoSchemeDTO = super.buildSchemeDTO();
		kenoSchemeDTO.setXjEl11to5PlayType(bulidPlayType());
		return kenoSchemeDTO;
	}
}
