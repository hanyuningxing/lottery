package com.cai310.lottery.service.lottery;

import java.util.List;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.dto.lottery.SchemeTempQueryDTO;
import com.cai310.lottery.entity.lottery.SchemeTemp;
import com.cai310.orm.Pagination;

/**
 * 免费保存方案实体管理类.
 * 
 * @param <T>
 *            方案对象的类型
 */
public interface SchemeTempEntityManager<TT extends SchemeTemp> {
	
	/**
	 * 获取方案
	 * 
	 * @param id
	 *            方案编号
	 * @return 方案对象
	 */
	TT getScheme(Long id);


	/**
	 * 保存方案
	 * 
	 * @param scheme
	 *            方案对象
	 * @return 保存后的方案对象
	 */
	TT saveScheme(TT scheme);

	/**
	 * @return 所管理彩种类型
	 * @see com.cai310.lottery.common.Lottery
	 */
	Lottery gerLottery();
	/**
	 * 查询方案
	 * 
	 * @param userId
	 *            用户编号
	 * @param dto
	 *            查询参数
	 * @return
	 */
	Pagination findMyScheme(SchemeTempQueryDTO dto, Pagination pagination);

	
	/**
	 * @param periodId
	 *            销售期编号
	 * @return
	 */
	List<Long> findSchemeIdOfSaleAnalyse(long periodId);

	
}
