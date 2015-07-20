package com.cai310.lottery.service.lottery;

public interface DoAnalysisService {
	/**
	 * 处理【消费统计】
	 * 
	 * @param betIssueId 销售期编号
	 * @param refresh 是否删除之前的统计信息，全部重新统计
	 */
	void saleAnalyseWork(long periodId, boolean refresh);

	/**
	 * 处理【战绩统计】
	 * 
	 * @param betIssueId 销售期编号
	 */
	void userScoreWork(long periodId);

	/**
	 * 处理【销量统计】
	 * 
	 * @param betIssueId 销售期编号
	 */
	void statuteWork(long periodId);
}
