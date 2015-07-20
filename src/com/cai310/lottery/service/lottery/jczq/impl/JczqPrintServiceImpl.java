package com.cai310.lottery.service.lottery.jczq.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.entity.lottery.PrintInterface;
import com.cai310.lottery.entity.lottery.jczq.JczqMatch;
import com.cai310.lottery.entity.lottery.jczq.JczqScheme;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.service.ServiceException;
import com.cai310.lottery.service.lottery.SchemeEntityManager;
import com.cai310.lottery.service.lottery.impl.PrintServiceImpl;
import com.cai310.lottery.service.lottery.jczq.JczqMatchEntityManager;
import com.cai310.lottery.support.jczq.JczqCompoundContent;
import com.cai310.lottery.support.jczq.JczqMatchItem;
import com.cai310.lottery.support.jczq.JczqPrintContent;
import com.cai310.lottery.support.jczq.JczqSingleContent;
import com.cai310.utils.DateUtil;
import com.cai310.utils.JsonUtil;

@Service("jczqPrintServiceImpl")
@Transactional
public class JczqPrintServiceImpl extends PrintServiceImpl<JczqScheme> {

	@Autowired
	private JczqSchemeEntityManagerImpl schemeEntityManager;
	@Autowired
	private JczqMatchEntityManager matchEntityManager;
	@Override
	protected SchemeEntityManager<JczqScheme> getSchemeEntityManager() {
		return schemeEntityManager;
	}

	public Lottery getLotteryType() {
		return Lottery.JCZQ;
	}

	@Override
	protected PrintInterface doSendToPrint(PrintInterface pi, JczqScheme scheme) {
		pi = super.doSendToPrint(pi, scheme);
		pi.setBetType((byte) scheme.getPlayType().ordinal());// 玩法

		// ----------------------- 重新组装方案内容 -----------------------
		
		JczqPrintContent printContentObj = new JczqPrintContent();
		printContentObj.setPassType(scheme.getPassType());
		printContentObj.setPassModeOrdinal(scheme.getPassMode().ordinal());
		printContentObj.setSchemeTypeOrdinal(scheme.getSchemeType().ordinal());
		printContentObj.setContent(scheme.getContent());
		printContentObj.setTicketContent(scheme.getTicketContent());
		List<String> matchKeyList = new ArrayList<String>();
		switch (scheme.getMode()) {
		case COMPOUND:
			JczqCompoundContent compoundContent = scheme.getCompoundContent();			
			for (JczqMatchItem item : compoundContent.getItems()) {
				matchKeyList.add(item.getMatchKey());
			}
			break;
		case SINGLE:
			JczqSingleContent singleContent= scheme.getSingleContent();
			matchKeyList = singleContent.getMatchkeys();
			break;
		default:
			throw new ServiceException("投注方式不正确.");
		}
		Date endTime = null;
		Date firstMatchTime = null;
		List<JczqMatch> matchList = matchEntityManager.findMatchs(matchKeyList);
		for (JczqMatch jczqMatch : matchList) {
			try {
				if (endTime == null || endTime.after(jczqMatch.getTicketOfficialEndTime()))
					endTime = jczqMatch.getTicketOfficialEndTime();
				
				if (firstMatchTime == null || firstMatchTime.after(jczqMatch.getMatchTime()))
					firstMatchTime = jczqMatch.getMatchTime();
			} catch (DataException e) {
				throw new ServiceException(jczqMatch.getMatchKey()+"获取截止时间错误.");
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
