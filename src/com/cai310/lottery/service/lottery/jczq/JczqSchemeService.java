package com.cai310.lottery.service.lottery.jczq;

import java.util.List;

import com.cai310.lottery.dto.lottery.jczq.JczqSchemeDTO;
import com.cai310.lottery.entity.lottery.jczq.JczqScheme;
import com.cai310.lottery.service.lottery.SchemeService;

public interface JczqSchemeService extends SchemeService<JczqScheme, JczqSchemeDTO> {
	public boolean handleTransaction(JczqScheme scheme,List<String> endLineIds);
	public JczqScheme destineScheme(JczqSchemeDTO schemeDTO);
}
