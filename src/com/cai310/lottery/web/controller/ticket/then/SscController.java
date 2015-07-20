package com.cai310.lottery.web.controller.ticket.then;



import javax.annotation.Resource;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.entity.lottery.keno.ssc.SscIssueData;
import com.cai310.lottery.entity.lottery.keno.ssc.SscScheme;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.service.lottery.keno.KenoManager;
import com.cai310.lottery.service.lottery.keno.KenoPlayer;
import com.cai310.lottery.service.lottery.keno.KenoService;
import com.cai310.lottery.support.ContentBean;
import com.cai310.lottery.support.sdel11to5.SdEl11to5ContentBeanBuilder;
import com.cai310.lottery.support.ssc.SscContentBeanBuilder;
import com.cai310.lottery.support.ssc.SscPlayType;
import com.cai310.lottery.web.controller.WebDataException;
import com.cai310.lottery.web.controller.lottery.keno.KenoSchemeDTO;


public class SscController extends
                    KenoController< SscScheme,KenoSchemeDTO,SscIssueData> {
	private static final long serialVersionUID = 5783479221989581469L;
	

	protected SscPlayType bulidPlayType() throws WebDataException{
        try{
        	return SscPlayType.values()[playType];
        }catch(Exception e){
			logger.warn("玩法解析错误."+e.getMessage());
			throw new WebDataException("5-玩法解析错误.");
		}
    }
	
	@Override
	protected ContentBean buildCompoundContentBean() throws WebDataException{
		try {
			if(this.schemeValue.indexOf("+")!=-1){
				//多注
				return SscContentBeanBuilder.buildCompoundContentBean(schemeValue.replaceAll("\\+", "\r\n"),bulidPlayType());
			}else{
				//单注
				if(this.schemeValue.indexOf(":")!=-1){
					return SscContentBeanBuilder.buildCompoundContentBean(this.schemeValue,bulidPlayType());
				}else{
					return SscContentBeanBuilder.buildCompoundContentBean(this.units+":"+this.schemeValue,bulidPlayType());
				}
			}
		} catch (DataException e) {
			throw new WebDataException("5-投注内容计算分析错误.("+e.getMessage()+")");
		}
	}

	@Override
	protected ContentBean buildSingleContentBean() throws WebDataException{
		try {
			return SscContentBeanBuilder.buildSingleContentBean(schemeValue.replaceAll("\\+", "\r\n"),bulidPlayType());
		} catch (DataException e) {
			throw new WebDataException("5-投注内容计算分析错误.("+e.getMessage()+")");
		}
	}
	@Override
	protected KenoSchemeDTO buildSchemeDTO() throws WebDataException {
		KenoSchemeDTO kenoSchemeDTO = super.buildSchemeDTO();
		kenoSchemeDTO.setSscPlayType(bulidPlayType());
		return kenoSchemeDTO;
	}
	@Override
	protected Lottery getLottery() {
		return Lottery.SSC;
	}
	
	@Resource(name = "sscKenoManagerImpl")
	@Override
	public void setKenoManager(KenoManager<SscIssueData, SscScheme> kenoManager) {
		this.kenoManager = kenoManager;
	}

	@Resource(name = "sscKenoPlayer")
	@Override
	public void setKenoPlayer(KenoPlayer<SscIssueData, SscScheme> kenoPlayer) {
		this.kenoPlayer = kenoPlayer;
	}

	@Resource(name = "sscKenoServiceImpl")
	@Override
	public void setKenoService(KenoService<SscIssueData, SscScheme> kenoService) {
		this.kenoService = kenoService;
	}
}
