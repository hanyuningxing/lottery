package com.cai310.lottery.service.lottery.jclq;

import java.util.List;

import com.cai310.lottery.entity.lottery.jclq.JclqScheme;
import com.cai310.lottery.entity.lottery.jclq.JclqSchemeMatch;
import com.cai310.lottery.service.lottery.SchemeEntityManager;

public interface JclqSchemeEntityManager extends SchemeEntityManager<JclqScheme> {
	
	JclqSchemeMatch saveSchemeMatch(JclqSchemeMatch obj);
	
	List<Long> findCanHandleTransactionSchemeId(Long id);
	
	List<Long> findCanFrtchSpSchemeId();
}
