package com.cai310.lottery.service.lottery.jczq.impl;
import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.cai310.lottery.dao.lottery.jczq.JczqChasePlanDetailDao;
import com.cai310.lottery.entity.lottery.jczq.JczqChasePlanDetail;
import com.cai310.lottery.service.lottery.jczq.JczqChasePlanDetailEntityManager;
import com.cai310.orm.Pagination;
import com.cai310.orm.XDetachedCriteria;

@Service("JczqChasePlanEntityManagerImpl")
@Transactional
public class JczqChasePlanDetailEntityManagerImpl implements
		JczqChasePlanDetailEntityManager {
	
	@Autowired
	private JczqChasePlanDetailDao jczqChasePlanDetailDao;
	
	@Override
	@Transactional(readOnly=true,  propagation = Propagation.SUPPORTS)
	public Pagination getJczqChasePlanDetailBy(
			Long jczqChasePlanId, Pagination pagination) {
		
		XDetachedCriteria criteria = new XDetachedCriteria(JczqChasePlanDetail.class, "m");
		criteria.add(Restrictions.eq("m.jczqChasePlanId", jczqChasePlanId));
		Pagination paginationNew = jczqChasePlanDetailDao.findByCriteriaAndPagination(criteria, pagination);
		
		return paginationNew;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly=true,  propagation = Propagation.SUPPORTS)
	public List<JczqChasePlanDetail> getJczqChasePlanDetailByJczqChasePlanId(
			Long jczqChasePlanId) {
		
		XDetachedCriteria criteria = new XDetachedCriteria(JczqChasePlanDetail.class, "m");
		criteria.add(Restrictions.eq("m.jczqChasePlanId", jczqChasePlanId));
		Pagination pagination = new Pagination(20);
		pagination = jczqChasePlanDetailDao.findByCriteriaAndPagination(criteria, pagination);
		
		return pagination.getResult();
	}

	@Override
	public void save(JczqChasePlanDetail detail) {		
		jczqChasePlanDetailDao.save(detail);
	}

	@Override
	public JczqChasePlanDetail getJczqChasePlanDetailBy(Long id) {
		JczqChasePlanDetail detail = jczqChasePlanDetailDao.get(id);
		return detail;
	}


}
