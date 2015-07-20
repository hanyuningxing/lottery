package com.cai310.lottery.service.lottery.jczq;

import java.util.List;

import com.cai310.lottery.entity.lottery.jczq.JczqScheme;
import com.cai310.lottery.entity.lottery.jczq.JczqSchemeMatch;
import com.cai310.lottery.service.lottery.SchemeEntityManager;

public interface JczqSchemeEntityManager extends SchemeEntityManager<JczqScheme> {
	
	JczqSchemeMatch saveSchemeMatch(JczqSchemeMatch obj);

	List<Long> findCanHandleTransactionSchemeId(Long id);
	
	List<Long> findCanFrtchSpSchemeId();
	
	
	/**
	 * @param periodId期号ID
	 * @return 
	 */
	List<JczqScheme> findTopTogetherScheme(int size);
	
}
