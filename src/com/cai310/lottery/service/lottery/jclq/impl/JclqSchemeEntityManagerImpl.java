package com.cai310.lottery.service.lottery.jclq.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.common.SchemePrintState;
import com.cai310.lottery.common.SchemeState;
import com.cai310.lottery.common.WinningUpdateStatus;
import com.cai310.lottery.dao.lottery.SchemeDao;
import com.cai310.lottery.dao.lottery.jclq.JclqSchemeDao;
import com.cai310.lottery.dao.lottery.jclq.JclqSchemeMatchDao;
import com.cai310.lottery.entity.lottery.jclq.JclqScheme;
import com.cai310.lottery.entity.lottery.jclq.JclqSchemeMatch;
import com.cai310.lottery.service.lottery.impl.SchemeEntityManagerImpl;
import com.cai310.lottery.service.lottery.jclq.JclqSchemeEntityManager;
import com.cai310.lottery.support.jclq.PassMode;
import com.cai310.orm.hibernate.CriteriaExecuteCallBack;
import com.cai310.utils.DateUtil;

/**
 * 竞彩足球方案相关实体管理实现类.
 * 
 */
@Service("jclqSchemeEntityManagerImpl")
@Transactional
public class JclqSchemeEntityManagerImpl extends SchemeEntityManagerImpl<JclqScheme> implements JclqSchemeEntityManager {
	
	@Autowired
	private JclqSchemeDao schemeDao;

	@Autowired
	private JclqSchemeMatchDao schemeMatchDao;
	
	@Override
	protected SchemeDao<JclqScheme> getSchemeDao() {
		return schemeDao;
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	@Override
	public Lottery getLottery() {
		return Lottery.JCLQ;
	}

	@Override
	public JclqSchemeMatch saveSchemeMatch(JclqSchemeMatch obj) {
		return schemeMatchDao.save(obj);
	}

	@Override
	public List<Long> findSchemeIdOfCanUpdateWon(long periodId) {
		return schemeMatchDao.findSchemeIdOfCanUpdateWon(periodId);
	}
	@SuppressWarnings("unchecked")
	public List<Long> findCanHandleTransactionSchemeId(final Long periodId) {
		return (List<Long>) schemeDao.execute(new CriteriaExecuteCallBack() {
			public Object execute(Criteria criteria) {
				criteria.setProjection(Projections.property("id"));
				criteria.add(Restrictions.eq("lotteryType", getLottery()));
				criteria.add(Restrictions.eq("periodId", periodId));
				criteria.add(Restrictions.eq("prizeSended", false));
				criteria.add(Restrictions.or(Restrictions.eq("state", SchemeState.UNFULL), Restrictions.eq("state",
						SchemeState.FULL)));
				return criteria.list();
			}
		});
	}
	@SuppressWarnings("unchecked")
	public List<Long> findCanFrtchSpSchemeId() {
		return (List<Long>) schemeDao.execute(new CriteriaExecuteCallBack() {
			public Object execute(Criteria criteria) {
				criteria.setProjection(Projections.property("id"));
				criteria.add(Restrictions.eq("lotteryType", getLottery()));
				criteria.add(Restrictions.eq("passMode", PassMode.PASS));
				criteria.add(Restrictions.gt("lastModifyTime", DateUtil.getdecDateOfMinute(new Date(),60)));
				criteria.add(Restrictions.or(Restrictions.eq("state", SchemeState.FULL), Restrictions.eq("state",
						SchemeState.SUCCESS)));
				criteria.add(Restrictions.eq("schemePrintState", SchemePrintState.SUCCESS));
				return criteria.list();
			}
		});
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<JclqScheme> findWonOfUnRank(final Integer currDateToInt, final Date startDate, final Date endDate,final int maxSize){
		return (List<JclqScheme>) getSchemeDao().execute(new CriteriaExecuteCallBack() {
			public Object execute(Criteria criteria) {
				criteria.add(Restrictions.eq("lotteryType", getLottery()));
				criteria.add(Restrictions.eq("won", true));
				criteria.add(Restrictions.ge("createTime", startDate));
				criteria.add(Restrictions.lt("createTime", endDate));
				criteria.add(Restrictions.gt("prize", new BigDecimal(0)));
				criteria.add(Restrictions.or(Restrictions.isNull("rankFlag"),Restrictions.ne("rankFlag", currDateToInt)));
				criteria.add(
						Restrictions.or(
							Restrictions.or(
								Restrictions.eq("state", SchemeState.SUCCESS),
								Restrictions.eq("state", SchemeState.CANCEL)
							), 
							Restrictions.eq("state", SchemeState.REFUNDMENT)
						)
				);
				criteria.setMaxResults(maxSize);
				return criteria.list();
			}
		});
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<JclqScheme> findWinUpdateOfUnGrade(final int maxSize){
		return (List<JclqScheme>) getSchemeDao().execute(new CriteriaExecuteCallBack() {
			public Object execute(Criteria criteria) {
				criteria.add(Restrictions.eq("lotteryType", getLottery()));
				criteria.add(
						Restrictions.or(
							Restrictions.or(
								Restrictions.eq("state", SchemeState.SUCCESS),
								Restrictions.eq("state", SchemeState.CANCEL)
							), 
							Restrictions.eq("state", SchemeState.REFUNDMENT)
						)
				);
				criteria.add(Restrictions.or(
						Restrictions.eq("winningUpdateStatus", WinningUpdateStatus.WINNING_UPDATED),
						Restrictions.eq("winningUpdateStatus", WinningUpdateStatus.WINNING_UPDATED)
						)
			    );
				criteria.add(Restrictions.eq("gradeFlag",false));
				criteria.setMaxResults(maxSize);
				return criteria.list();
			}
		});
	}
}
