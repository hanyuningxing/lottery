package com.cai310.lottery.service.lottery.dczc;

import java.util.List;

import com.cai310.lottery.entity.lottery.dczc.DczcScheme;
import com.cai310.lottery.service.lottery.SchemeEntityManager;

public interface DczcSchemeEntityManager extends SchemeEntityManager<DczcScheme> {

	/**
	 * 查询可以处理交易的方案ID集合
	 * 
	 * @param periodId 销售期ID
	 * @return 可以处理交易的方案ID集合
	 */
	List<Long> findCanHandleTransactionSchemeId(Long periodId);
}
