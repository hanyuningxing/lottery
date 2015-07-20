package com.cai310.lottery.web.controller.ticket.then;



import javax.annotation.Resource;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.entity.lottery.keno.el11to5.El11to5IssueData;
import com.cai310.lottery.entity.lottery.keno.el11to5.El11to5Scheme;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.service.lottery.keno.KenoManager;
import com.cai310.lottery.service.lottery.keno.KenoPlayer;
import com.cai310.lottery.service.lottery.keno.KenoService;
import com.cai310.lottery.support.ContentBean;
import com.cai310.lottery.support.el11to5.El11to5ContentBeanBuilder;
import com.cai310.lottery.support.el11to5.El11to5PlayType;
import com.cai310.lottery.web.controller.WebDataException;
import com.cai310.lottery.web.controller.lottery.keno.KenoSchemeDTO;


public class El11to5Controller extends
                    KenoController<El11to5Scheme,KenoSchemeDTO,El11to5IssueData> {
	private static final long serialVersionUID = 5783479221989581469L;
	

	protected El11to5PlayType bulidPlayType() throws WebDataException{
        try{
        	return El11to5PlayType.values()[playType];
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
				return El11to5ContentBeanBuilder.buildCompoundContentBean(schemeValue.replaceAll("\\+", "\r\n"),bulidPlayType());
			}else{
				//单注
				if(this.schemeValue.indexOf(":")!=-1){
					return El11to5ContentBeanBuilder.buildCompoundContentBean(this.schemeValue,bulidPlayType());
				}else{
					return El11to5ContentBeanBuilder.buildCompoundContentBean(this.units+":"+this.schemeValue,bulidPlayType());
				}
			}
		} catch (DataException e) {
			throw new WebDataException("5-投注内容计算分析错误.("+e.getMessage()+")");
		}
	}

	@Override
	protected ContentBean buildSingleContentBean() throws WebDataException{
		try {
			return El11to5ContentBeanBuilder.buildSingleContentBean(schemeValue.replaceAll("\\+", "\r\n"),bulidPlayType());
		} catch (DataException e) {
			throw new WebDataException("5-投注内容计算分析错误.("+e.getMessage()+")");
		}
	}
	@Override
	protected KenoSchemeDTO buildSchemeDTO() throws WebDataException {
		KenoSchemeDTO kenoSchemeDTO = super.buildSchemeDTO();
		kenoSchemeDTO.setEl11to5PlayType(bulidPlayType());
		return kenoSchemeDTO;
	}
	@Override
	protected Lottery getLottery() {
		return Lottery.EL11TO5;
	}
	
	@Resource(name = "el11to5KenoManagerImpl")
	@Override
	public void setKenoManager(KenoManager<El11to5IssueData, El11to5Scheme> kenoManager) {
		this.kenoManager = kenoManager;
	}

	@Resource(name = "el11to5KenoPlayer")
	@Override
	public void setKenoPlayer(KenoPlayer<El11to5IssueData, El11to5Scheme> kenoPlayer) {
		this.kenoPlayer = kenoPlayer;
	}

	@Resource(name = "el11to5KenoServiceImpl")
	@Override
	public void setKenoService(KenoService<El11to5IssueData, El11to5Scheme> kenoService) {
		this.kenoService = kenoService;
	}
    public static void main(String[] args) {
    	StringBuilder sb = new StringBuilder();
    	sb.append("+");
    	System.out.print(sb.toString());
	}
}
