package com.cai310.lottery.service.lottery.dczc.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.common.SchemeState;
import com.cai310.lottery.dao.lottery.SchemeDao;
import com.cai310.lottery.dao.lottery.dczc.DczcSchemeDao;
import com.cai310.lottery.dao.lottery.dczc.DczcSchemeWonInfoDao;
import com.cai310.lottery.entity.lottery.dczc.DczcScheme;
import com.cai310.lottery.entity.lottery.dczc.DczcPasscount;
import com.cai310.lottery.service.lottery.dczc.DczcSchemeEntityManager;
import com.cai310.lottery.service.lottery.impl.SchemeEntityManagerImpl;
import com.cai310.orm.hibernate.CriteriaExecuteCallBack;

/**
 * 单场足彩方案相关实体管理实现类.
 * 
 */
@Service("dczcSchemeEntityManagerImpl")
@Transactional
public class DczcSchemeEntityManagerImpl extends SchemeEntityManagerImpl<DczcScheme> implements DczcSchemeEntityManager {

	@Autowired
	private DczcSchemeDao schemeDao;
	
	@Autowired
	private DczcSchemeWonInfoDao dczcSchemeWonInfoDao;

	@Override
	protected SchemeDao<DczcScheme> getSchemeDao() {
		return schemeDao;
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Lottery getLottery() {
		return Lottery.DCZC;
	}

	@SuppressWarnings("unchecked")
	public List<Long> findCanHandleTransactionSchemeId(final Long periodId) {
		return (List<Long>) schemeDao.execute(new CriteriaExecuteCallBack() {
			public Object execute(Criteria criteria) {
				criteria.setProjection(Projections.property("id"));
				criteria.add(Restrictions.eq("periodId", periodId));
				criteria.add(Restrictions.eq("prizeSended", false));
				criteria.add(Restrictions.or(Restrictions.eq("state", SchemeState.UNFULL), Restrictions.eq("state",
						SchemeState.FULL)));
				return criteria.list();
			}
		});
	}
	
	public DczcPasscount saveDczcSchemeWonInfo(DczcPasscount entity) {
		return dczcSchemeWonInfoDao.save(entity);
	}
	
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public DczcPasscount getDczcSchemeWonInfo(long id) {
		return dczcSchemeWonInfoDao.get(id);
	}
	
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public void deleteDczcSchemeWonInfo(DczcPasscount entity ) {
		dczcSchemeWonInfoDao.delete(entity);
	}
}
