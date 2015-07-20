package com.cai310.lottery.dao.lottery.jczq;

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import com.cai310.lottery.common.SchemeState;
import com.cai310.lottery.common.WinningUpdateStatus;
import com.cai310.lottery.entity.lottery.jczq.JczqMatch;
import com.cai310.lottery.entity.lottery.jczq.JczqScheme;
import com.cai310.lottery.entity.lottery.jczq.JczqSchemeMatch;
import com.cai310.orm.hibernate.HibernateDao;

@Repository
public class JczqSchemeMatchDao extends HibernateDao<JczqSchemeMatch, Long> {

	@SuppressWarnings("unchecked")
	public List<Long> findSchemeIdOfCanUpdateWon(final long periodId) {
		StringBuilder sb = new StringBuilder();
		sb.append(" select distinct a.schemeId as schemeId from ").append(JczqSchemeMatch.TABLE_NAME).append(" a ");

		sb.append(" inner join ")
				.append(JczqScheme.TABLE_NAME)
				.append(" bs on a.schemeId=bs.id and bs.periodId=:periodId and bs.uploaded=:uploaded and bs.winningUpdateStatus=:winningUpdateStatus ");
		sb.append(" and ( ");
		for (int i = 0; i < SchemeState.finalStatuss.length; i++) {
			if (i > 0)
				sb.append(" or ");
			sb.append(" bs.state=").append(SchemeState.finalStatuss[i].ordinal());
		}
		sb.append(" ) ");

		sb.append(" left join ")
				.append(JczqMatch.TABLE_NAME)
				.append(" m on a.matchKey=m.matchKey and (m.cancel=1 or (m.ended=1 and m.fullHomeScore is not null and m.fullGuestScore is not null))");

		sb.append(" group by a.schemeId having (sum(1)=sum(case when m.id is null then 0 else 1 end))");

		SQLQuery q = getSession().createSQLQuery(sb.toString());
		q.setLong("periodId", periodId);
		q.setBoolean("uploaded", true);
		q.setByte("winningUpdateStatus", (byte) WinningUpdateStatus.NONE.ordinal());
		q.addScalar("schemeId", Hibernate.LONG);

		return q.list();
	}
}
