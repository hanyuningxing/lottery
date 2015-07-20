package com.cai310.lottery.service.lottery.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cai310.lottery.dao.lottery.SchemeTempDao;
import com.cai310.lottery.dto.lottery.SchemeTempQueryDTO;
import com.cai310.lottery.entity.lottery.SchemeTemp;
import com.cai310.lottery.service.lottery.SchemeTempEntityManager;
import com.cai310.orm.Pagination;
import com.cai310.orm.hibernate.ExecuteCallBack;
import com.cai310.utils.ReflectionUtils;

/**
 * 免费保存方案管理实现类
 * @author jack
 *
 */
@Transactional
public abstract class SchemeTempEntityManagerImpl<TT extends SchemeTemp> implements SchemeTempEntityManager<TT> {

	
	protected abstract SchemeTempDao<TT> getSchemeTempDao();
	
	protected final Class<TT> schemeTempClass;
	
	public SchemeTempEntityManagerImpl() {
		this.schemeTempClass = ReflectionUtils.getSuperClassGenricType(this.getClass());
	}
	
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public TT getScheme(Long id) {
		return getSchemeTempDao().get(id);
	}


	@Override
	public TT saveScheme(TT scheme) {
		return getSchemeTempDao().save(scheme);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Pagination findMyScheme(final SchemeTempQueryDTO dto, final Pagination pagination) {
		return (Pagination) getSchemeTempDao().execute(new ExecuteCallBack() {
			public Object execute(Session session) {
				Criteria criteria = session.createCriteria(schemeTempClass, "m");
				if(dto != null) {
					if(dto.getSponsorId() != null) {
						criteria.add(Restrictions.eq("m.sponsorId", dto.getSponsorId()));
					}
					if(dto.getLotteryType() != null) {
						criteria.add(Restrictions.eq("m.lotteryType", dto.getLotteryType()));
					}
					if(dto.getStartTime() != null){
						criteria.add(Restrictions.ge("m.createTime", dto.getStartTime()));
					}
					if(dto.getEndTime() != null){
						criteria.add(Restrictions.le("m.createTime", dto.getEndTime()));
					}
		
					if (pagination.getFirst() >= 0) {
						criteria.setFirstResult(pagination.getFirst());
					}
					if (pagination.getPageSize() > 0) {
						criteria.setMaxResults(pagination.getPageSize());
					}
					
					pagination.setResult(criteria.list());
				}else {
					pagination.setResult(null);
					pagination.setTotalCount(0);
				}
				
				return pagination;
			}
		});
	}

	@Override
	public List<Long> findSchemeIdOfSaleAnalyse(long periodId) {
		return null;
	}

}
