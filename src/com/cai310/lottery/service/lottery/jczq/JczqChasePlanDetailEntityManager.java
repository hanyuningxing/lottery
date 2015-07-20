package com.cai310.lottery.service.lottery.jczq;
import java.util.List;

import com.cai310.lottery.entity.lottery.jczq.JczqChasePlanDetail;
import com.cai310.orm.Pagination;

public interface JczqChasePlanDetailEntityManager {
	
	Pagination getJczqChasePlanDetailBy(Long jczqChasePlanId, Pagination pagination);
	
	JczqChasePlanDetail getJczqChasePlanDetailBy(Long id);
	
	void save(JczqChasePlanDetail detail);

	List<JczqChasePlanDetail> getJczqChasePlanDetailByJczqChasePlanId(
			Long jczqChasePlanId);
}
