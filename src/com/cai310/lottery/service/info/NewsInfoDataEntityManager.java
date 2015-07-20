package com.cai310.lottery.service.info;

import java.util.List;

import com.cai310.lottery.common.InfoSubType;
import com.cai310.lottery.common.InfoType;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.entity.info.MobileNewsData;
import com.cai310.lottery.entity.info.NewsInfoData;
import com.cai310.lottery.entity.info.Rule;
import com.cai310.lottery.entity.info.TagsInfoData;
import com.cai310.lottery.entity.security.AdminUser;
import com.cai310.orm.Pagination;

/**
 *  新闻相关实体管理接口
 * 
 */
@SuppressWarnings("unchecked") 
public interface NewsInfoDataEntityManager {
	public NewsInfoData getNewsInfoData(Long id);

	public NewsInfoData saveNewsInfoData(NewsInfoData entity);
	
	public NewsInfoData saveNewsInfoData(NewsInfoData entity,AdminUser operator);
	
	public List<NewsInfoData> getNewsInfoDataBy(InfoType type,Lottery lotteryType,Integer needNum,Boolean isIndex);
	
	public List<NewsInfoData> getNewsInfoDataBy(InfoType type,Lottery lotteryType,InfoSubType subType,Integer needNum,Boolean isIndex);
		
	public Pagination getNewsInfoDataBy(InfoType type,Lottery lotteryType,Pagination page);
	
	public List<NewsInfoData> getNewsInfoDataByArr(InfoType type,Lottery[] lotteryTypeArr,Integer needNum,Boolean isIndex);
	
	public MobileNewsData getMobileNewsData(long id);
	
	public MobileNewsData saveMobileNewsData(MobileNewsData entity,AdminUser operator);
	
	public void delMobileNewsData(long id);
	
	public TagsInfoData getTagsInfoData(long id);
	
	public TagsInfoData saveTagsInfoData(TagsInfoData entity,AdminUser operator);
	
	public Rule getRule(long id);
	
	public Rule saveRule(Rule entity,AdminUser operator);
	
	public void delTagsInfoData(long id);

	public List<TagsInfoData> loadTagsInfoDatas();

	public List<TagsInfoData> loadTagsInfoDatas(int level,Lottery lotterType);
	
	public List<NewsInfoData> loadTopics(InfoType type,InfoSubType[] subType,Integer needNum);

	
}
