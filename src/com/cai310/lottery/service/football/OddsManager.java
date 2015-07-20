package com.cai310.lottery.service.football;

import java.util.List;
import java.util.Map;

import com.cai310.lottery.entity.football.Company;
import com.cai310.lottery.entity.lottery.jczq.JczqMatch;
import com.cai310.lottery.exception.DataException;
/**
 *  捉取赔率接口
 * 
 */
@SuppressWarnings("unchecked") 
public interface OddsManager {
	/**
	 * 
	 * @return
	 */
    Map<Long,JczqMatch> getJczqMatchMap();

	List<Company> getAllCompanyList();

	Company saveCompany(Company company);

	Company getCompany(Long id);

	Long getCompanyByWin310(Long win310Id) throws DataException;
}
