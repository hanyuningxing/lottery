package com.cai310.lottery.service.info;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.entity.info.AdImages;
import com.cai310.lottery.entity.lottery.Period;
import com.cai310.lottery.exception.DataException;

/**
 *  新闻相关实体管理接口
 * 
 */
@SuppressWarnings("unchecked") 
public interface IndexInfoDataEntityManager {
	public void makeZcMatch(Period period)throws DataException;
	
	public void makeShuTopWon(Lottery lottery) throws DataException;
	
	public void makeShuNewResult() throws DataException;
	
	public void makeZcNewResult() throws DataException;
	
	public void uploadAdPic(AdImages adImages)throws DataException;
	
	public void deleteAdPic(Long id)throws DataException;
}
