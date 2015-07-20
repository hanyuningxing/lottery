package com.cai310.lottery.service.lottery.jclq;

import java.util.List;

import com.cai310.lottery.dto.lottery.jclq.JclqSchemeDTO;
import com.cai310.lottery.entity.lottery.jclq.JclqScheme;
import com.cai310.lottery.service.lottery.SchemeService;

public interface JclqSchemeService extends SchemeService<JclqScheme, JclqSchemeDTO> {
	public boolean handleTransaction(JclqScheme scheme,List<String> endLineIds);
}
