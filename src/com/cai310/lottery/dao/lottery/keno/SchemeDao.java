package com.cai310.lottery.dao.lottery.keno;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import com.cai310.lottery.common.AgentAnalyseState;
import com.cai310.lottery.common.SchemePrintState;
import com.cai310.lottery.common.SchemeState;
import com.cai310.lottery.entity.lottery.NumberScheme;

public abstract class SchemeDao<T extends NumberScheme> extends com.cai310.lottery.dao.lottery.SchemeDao<T> {
	/**
	 * 据期号读取方案列表
	 * 
	 * @param issueNumber 期号
	 * @return 方案列表
	 */
	@SuppressWarnings("unchecked")
	public List<T> findByIssueNumber(String issueNumber) {
		Criteria criteria = getSession().createCriteria(getEntityClass());
		criteria.add(Restrictions.eq("periodNumber", issueNumber));
		return criteria.list();
	}
	//cyy-c 2011-04-14
	@SuppressWarnings("unchecked")
	public List<T> findPrintFailByIssueNumber(String issueNumber) {
		Criteria criteria = getSession().createCriteria(getEntityClass());
		criteria.add(Restrictions.eq("periodNumber", issueNumber));
		criteria.add(Restrictions.ne("schemePrintState", SchemePrintState.SUCCESS));
		return criteria.list();
	}
	//cyy-c 2013-04-14
	@SuppressWarnings("unchecked")
	public List<T> findUnAnalyes(Long periodId) {
			Criteria criteria = getSession().createCriteria(getEntityClass());
			criteria.add(Restrictions.eq("periodId", periodId));
			criteria.add(Restrictions.eq("state", SchemeState.SUCCESS));
			criteria.add(Restrictions.eq("agentAnalyseState", AgentAnalyseState.NONE));
			return criteria.list();
	}
	//cyy-c 2013-04-14
	@SuppressWarnings("unchecked")
	public List<T> findUnSendPrize(Long periodId) {
				Criteria criteria = getSession().createCriteria(getEntityClass());
				criteria.add(Restrictions.eq("periodId", periodId));
				criteria.add(Restrictions.eq("won", true));
				criteria.add(Restrictions.eq("prizeSended", false));
				return criteria.list();
	}
	@SuppressWarnings("unchecked")
	public Class<T> getEntityClass() {
		if (entityClass == null) {
			entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
			logger.debug("T class = " + entityClass.getName());
		}
		return entityClass;
	}
}
