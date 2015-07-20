package com.cai310.lottery.dao.lottery.keno;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.cai310.lottery.common.keno.IssueState;
import com.cai310.lottery.entity.lottery.keno.KenoPeriod;

public abstract class IssueDataDao<T extends KenoPeriod> extends KenoGenericDao<T> {

	public final static IssueState[] issueStateArray = { IssueState.ISSUE_SATE_READY, IssueState.ISSUE_SATE_CURRENT };

	/**
	 * 据期号加载数据
	 * 
	 * @param issueNum 要读取数据的期号
	 * @return 查询期号的期号数据
	 */
	@SuppressWarnings("unchecked")
	public T findDataByNumber(String issueNum) {
		Criteria criteria = getSession().createCriteria(getEntityClass());
		criteria.setCacheable(true);
		criteria.setMaxResults(1);
		criteria.add(Restrictions.eq("periodNumber", issueNum));
		return (T) criteria.uniqueResult();
	}

	/**
	 * 读取当前期的期号数据
	 * 
	 * @param bfTime 一期销售的时间，以分钟计算
	 * @return 返回当前销售期数据
	 */
	@SuppressWarnings("unchecked")
	public T findCurrentData(Date data, int beforeTime) {
		Criteria criteria = getSession().createCriteria(getEntityClass());
		criteria.setCacheable(true);
		criteria.setMaxResults(1);
		criteria.add(Restrictions.gt("endedTime", DateUtils.addMinutes(data, beforeTime)));
		criteria.addOrder(Order.asc("endedTime"));
		return (T) criteria.uniqueResult();
	}

	/**
	 * 据期号ID查询下一期期号数据
	 * 
	 * @param id 要查询下一期的期号ID
	 * @return 下一期的期号数据
	 */
	@SuppressWarnings("unchecked")
	public T findNextIssueData(long id) {
		Criteria criteria = getSession().createCriteria(getEntityClass());
		criteria.setCacheable(true);
		criteria.setMaxResults(1);
		criteria.add(Restrictions.eq("prevPreriodId", id));
		return (T) criteria.uniqueResult();
	}

	/**
	 * 据时间查询当前需要截止的期号数据
	 * 
	 * @param time 要询的时间
	 * @return 期号数据列表
	 */
	@SuppressWarnings("unchecked")
	public List<T> findCanCloseIssue(Date time) {
		Criteria criteria = getSession().createCriteria(getEntityClass());
		criteria.setCacheable(true);
		criteria.add(Restrictions.in("state", issueStateArray));
		criteria.add(Restrictions.le("endedTime", time));
		return criteria.list();
	}

	/**
	 * 读取最后一期的期号数据
	 * 
	 * @return 最后一期的数据
	 */
	@SuppressWarnings("unchecked")
	public T findLastIssue() {
		Criteria criteria = getSession().createCriteria(getEntityClass());
		criteria.setCacheable(true);
		criteria.setMaxResults(1);
		criteria.addOrder(Order.desc("id"));
		return (T) criteria.uniqueResult();
	}

	/**
	 * 更新期号状态
	 * 
	 * @param issueId 要更新的期号
	 * @param state 新的状态
	 */
	public void updateIssueState(long issueId, IssueState state) {
		T entity = super.get(issueId);
		entity.setState(state);
		super.save(entity);
	}


	/**
	 * 读取最后一期开奖期号数据
	 * 
	 * @return 最后一期开奖期号数据
	 */
	@SuppressWarnings("unchecked")
	public T findLastResultIssueData() {
		Criteria criteria = getSession().createCriteria(getEntityClass());
		criteria.setCacheable(true);
		criteria.setMaxResults(1);
		criteria.add(Restrictions.isNotNull("results"));
		criteria.addOrder(Order.desc("endedTime"));
		return (T) criteria.uniqueResult();
	}

	/**
	 * 读取可以更新中奖的期号列表
	 * 
	 * @param dateNow 当前系统时间
	 * @return 可以更新中奖的期号列表
	 */
	@SuppressWarnings("unchecked")
	public List<T> findIssueDataList(Date dateNow, IssueState state) {
		Criteria criteria = getSession().createCriteria(getEntityClass());
		criteria.setCacheable(true);
		criteria.add(Restrictions.eq("state", state));
		criteria.add(Restrictions.le("prizeTime", dateNow));
		return criteria.list();
	}
}
