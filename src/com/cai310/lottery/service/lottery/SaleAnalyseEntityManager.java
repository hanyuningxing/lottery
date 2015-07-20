package com.cai310.lottery.service.lottery;
import java.util.List;
import java.util.Set;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.entity.lottery.SaleAnalyse;

public interface SaleAnalyseEntityManager {
	void updateSaleAnalyse(List<SaleAnalyse> updateSaleAnalyse,Set<Long> updateSchemeSet,Lottery lottery);
	List<SaleAnalyse> getTop10SaleAnalyse(Lottery lottery, Integer monthNum);
	List<SaleAnalyse> getTop10SaleAnalyse();
	SaleAnalyse getJcSaleAnalyse(Long userId,Lottery lottery,Integer playType,Integer week);
	SaleAnalyse saveJcSaleAnalyse(SaleAnalyse saleAnalyse);
}
