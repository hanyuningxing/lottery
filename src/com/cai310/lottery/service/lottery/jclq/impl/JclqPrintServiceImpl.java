package com.cai310.lottery.service.lottery.jclq.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.entity.lottery.PrintInterface;
import com.cai310.lottery.entity.lottery.jclq.JclqMatch;
import com.cai310.lottery.entity.lottery.jclq.JclqScheme;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.service.ServiceException;
import com.cai310.lottery.service.lottery.SchemeEntityManager;
import com.cai310.lottery.service.lottery.impl.PrintServiceImpl;
import com.cai310.lottery.service.lottery.jclq.JclqMatchEntityManager;
import com.cai310.lottery.support.jclq.JclqCompoundContent;
import com.cai310.lottery.support.jclq.JclqMatchItem;
import com.cai310.lottery.support.jclq.JclqPrintContent;
import com.cai310.lottery.support.jclq.JclqSingleContent;
import com.cai310.utils.DateUtil;
import com.cai310.utils.JsonUtil;

@Service("jclqPrintServiceImpl")
@Transactional
public class JclqPrintServiceImpl extends PrintServiceImpl<JclqScheme> {

	@Autowired
	private JclqSchemeEntityManagerImpl schemeEntityManager;
	@Autowired
	private JclqMatchEntityManager matchEntityManager;
	@Override
	protected SchemeEntityManager<JclqScheme> getSchemeEntityManager() {
		return schemeEntityManager;
	}

	public Lottery getLotteryType() {
		return Lottery.JCLQ;
	}

	@Override
	protected PrintInterface doSendToPrint(PrintInterface pi, JclqScheme scheme) {
		pi = super.doSendToPrint(pi, scheme);
		pi.setBetType((byte) scheme.getPlayType().ordinal());// 玩法

		// ----------------------- 重新组装方案内容 -----------------------
		JclqPrintContent printContentObj = new JclqPrintContent();
		printContentObj.setPassType(scheme.getPassType());
		printContentObj.setPassModeOrdinal(scheme.getPassMode().ordinal());
		printContentObj.setSchemeTypeOrdinal(scheme.getSchemeType().ordinal());
		printContentObj.setContent(scheme.getContent());
		printContentObj.setTicketContent(scheme.getTicketContent());
		List<String> matchKeyList = new ArrayList<String>();
		switch (scheme.getMode()) {
		case COMPOUND:
			JclqCompoundContent compoundContent = scheme.getCompoundContent();			
			for (JclqMatchItem item : compoundContent.getItems()) {
				matchKeyList.add(item.getMatchKey());
			}
			break;
		case SINGLE:
			JclqSingleContent singleContent= scheme.getSingleContent();
			matchKeyList = singleContent.getMatchkeys();
			break;
		default:
			throw new ServiceException("投注方式不正确.");
		}
		Date endTime = null;
		Date firstMatchTime = null;
		List<JclqMatch> matchList = matchEntityManager.findMatchs(matchKeyList);
		for (JclqMatch jclqMatch : matchList) {
			try {
				if (endTime == null || endTime.after(jclqMatch.getTicketOfficialEndTime()))
					endTime = jclqMatch.getTicketOfficialEndTime();
				
				if (firstMatchTime == null || firstMatchTime.after(jclqMatch.getMatchTime()))
					firstMatchTime = jclqMatch.getMatchTime();
			} catch (DataException e) {
				throw new ServiceException(jclqMatch.getMatchKey()+"获取截止时间错误.");
			}
		}
		
		if (endTime.before(pi.getOfficialEndTime()))
			pi.setOfficialEndTime(endTime);
		
		printContentObj.setFirstMatchTime(DateUtil.dateToStr(firstMatchTime, "yyyy-MM-dd HH:mm:ss"));
		String printContent = JsonUtil.getJsonString4JavaPOJO(printContentObj);
		pi.setContent(printContent);
		return pi;
	}
}
