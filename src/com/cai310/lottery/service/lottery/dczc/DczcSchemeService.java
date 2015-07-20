package com.cai310.lottery.service.lottery.dczc;

import com.cai310.lottery.dto.lottery.dczc.DczcSchemeDTO;
import com.cai310.lottery.entity.lottery.dczc.DczcScheme;
import com.cai310.lottery.service.lottery.SchemeService;

public interface DczcSchemeService extends SchemeService<DczcScheme, DczcSchemeDTO> {

	/**
	 * <strong>处理交易</strong>
	 * <p>
	 * 检查方案选择的比赛是否都已截止，已截止则进行完成或取消交易的操作
	 * </p>
	 * 
	 * @param schemeId 方案ID
	 * @return 是否已处理
	 */
	boolean handleTransaction(Long schemeId);
	
    DczcScheme newSchemeInstance(DczcSchemeDTO schemeDTO);

}
