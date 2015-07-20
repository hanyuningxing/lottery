package com.cai310.lottery.web.controller.ticket.then;



import javax.annotation.Resource;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.entity.lottery.keno.klpk.KlpkIssueData;
import com.cai310.lottery.entity.lottery.keno.klpk.KlpkScheme;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.service.lottery.keno.KenoManager;
import com.cai310.lottery.service.lottery.keno.KenoPlayer;
import com.cai310.lottery.service.lottery.keno.KenoService;
import com.cai310.lottery.support.ContentBean;
import com.cai310.lottery.support.klpk.KlpkContentBeanBuilder;
import com.cai310.lottery.support.klpk.KlpkPlayType;
import com.cai310.lottery.web.controller.WebDataException;
import com.cai310.lottery.web.controller.lottery.keno.KenoSchemeDTO;


public class KlpkController extends
                    KenoController<KlpkScheme,KenoSchemeDTO,KlpkIssueData> {
	private static final long serialVersionUID = 5783479221989581469L;
	

	protected KlpkPlayType bulidPlayType() throws WebDataException{
        try{
        	return KlpkPlayType.values()[playType];
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
				return KlpkContentBeanBuilder.buildCompoundContentBean(schemeValue.replaceAll("M", "\r\n"),bulidPlayType());
			}else{
				//单注
				if(this.schemeValue.indexOf(":")!=-1){
					return KlpkContentBeanBuilder.buildCompoundContentBean(this.schemeValue,bulidPlayType());
				}else{
					return KlpkContentBeanBuilder.buildCompoundContentBean(this.units+":"+this.schemeValue,bulidPlayType());
				}
			}
		} catch (DataException e) {
			throw new WebDataException("5-投注内容计算分析错误.("+e.getMessage()+")");
		}
	}

	@Override
	protected ContentBean buildSingleContentBean() throws WebDataException{
		try {
			return KlpkContentBeanBuilder.buildSingleContentBean(schemeValue.replaceAll("\\+", "\r\n"),bulidPlayType());
		} catch (DataException e) {
			throw new WebDataException("5-投注内容计算分析错误.("+e.getMessage()+")");
		}
	}
	@Override
	protected KenoSchemeDTO buildSchemeDTO() throws WebDataException {
		KenoSchemeDTO kenoSchemeDTO = super.buildSchemeDTO();
		kenoSchemeDTO.setKlpkPlayType(bulidPlayType());
		return kenoSchemeDTO;
	}
	@Override
	protected Lottery getLottery() {
		return Lottery.KLPK;
	}
	
	@Resource(name = "klpkKenoManagerImpl")
	@Override
	public void setKenoManager(KenoManager<KlpkIssueData, KlpkScheme> kenoManager) {
		this.kenoManager = kenoManager;
	}

	@Resource(name = "klpkKenoPlayer")
	@Override
	public void setKenoPlayer(KenoPlayer<KlpkIssueData, KlpkScheme> kenoPlayer) {
		this.kenoPlayer = kenoPlayer;
	}

	@Resource(name = "klpkKenoServiceImpl")
	@Override
	public void setKenoService(KenoService<KlpkIssueData, KlpkScheme> kenoService) {
		this.kenoService = kenoService;
	}
    public static void main(String[] args) {
    	StringBuilder sb = new StringBuilder();
    	sb.append("+");
    	System.out.print(sb.toString());
	}
}
