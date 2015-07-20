package com.cai310.lottery.service.lottery.keno;

import java.util.Date;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.cai310.lottery.common.keno.IssueState;
import com.cai310.lottery.entity.lottery.ChasePlan;
import com.cai310.lottery.entity.lottery.NumberScheme;
import com.cai310.lottery.entity.lottery.keno.KenoPeriod;
import com.cai310.lottery.entity.lottery.keno.KenoSysConfig;
import com.cai310.lottery.entity.security.AdminUser;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.web.controller.lottery.keno.KenoSchemeDTO;
import com.cai310.orm.Pagination;
import com.cai310.orm.XDetachedCriteria;

public interface KenoService<I extends KenoPeriod, S extends NumberScheme> {

	/**
	 * 读取最后一个期号
	 * 
	 * @return
	 */
	public I getLastIssue();

	/**
	 * 据期号读取期号数据
	 * 
	 * @param issueNumber
	 *            期号
	 * @return
	 */
	public I findByIssueNumber(String issueNumber);

	/**
	 * 根据期号查询期数据
	 * 
	 * @param periodNumber
	 * @return
	 */
	public I loadPeriod(String periodNumber);
	/**
	 * 执行完成交易（交易成功或撤销）
	 * 
	 * @param schemeId 方案编号
	 */
	void commitOrCancelTransaction(Long schemeId);
	/**
	 * 保存期号数据
	 * 
	 * @param issueData
	 *            要保存的期号
	 * @return
	 */
	public I saveIssueData(I issueData);

	/**
	 * 据当前时间开售当前期
	 * 
	 * @param dateNow
	 *            当前系统时间
	 * @param beforeTime
	 *            提前开售和截止时间（单位：分钟）
	 */
	public void openCurrentIssue(Date dateNow, int beforeTime);

	/**
	 * 读取当前期数据
	 * 
	 * @param dateNow
	 *            当前系统时间
	 * @param beforeTime
	 *            提前开售和截止时间（单位：分钟）
	 * @return 前期数据
	 */
	public I getCurrIssueData(Date dateNow, int beforeTime);

	/**
	 * 读取最新开奖期数据
	 * 
	 * @return
	 */
	public I getLastResultIssueData();

	/**
	 * 读取可以更新中奖的期号列表
	 * 
	 * @param dateNow
	 *            当前系统时间
	 * @return 可以更新中奖的期号列表
	 */
	public List<I> getCanOpenPrize(Date dateNow);

	/**
	 * 据期号读取方案列表
	 * 
	 * @param issueNumber
	 *            期号
	 * @return 方案列表
	 */
	public List<S> getSchemeByIssueNumber(String issueNumber);
	/**
	 * 据读未统计方案列表
	 * 
	 * @param periodId
	 *            期号
	 * @return 方案列表
	 */
	public List<S> getUnAgentAnalyseScheme(Long periodId);
	
	/**
	 * 据期号读取出票失败方案列表
	 * 
	 * @param issueNumber
	 *            期号
	 * @return 出票失败方案列表
	 */
	public List<S> getPrintFailSchemeByIssueNumber(String issueNumber);

	/**
	 * 保存方案
	 * 
	 * @param scheme
	 *            需保存的方案
	 */
	public void saveScheme(S scheme);

	/**
	 * 取方案
	 * 
	 * @param scheme
	 */
	public S getScheme(Long id);

	/**
	 * 据方案号向用户派奖
	 * 
	 * @param scheme
	 *            需派奖的方案
	 */
	public void sendUserPrice(S scheme);
	/**
	 * 
	 * @param issueData
	 */
	public void endAll(I issueData);
	/**
	 * 更新期号状态
	 * 
	 * @param issueId
	 *            期号ID
	 * @param state
	 *            期号状态
	 */
	public void updateIssueState(Long issueId, IssueState state);
//	/**
//	 * 更新售后标识，售后标识使用二进制表示，进行【与】运算
//	 * 
//	 * @param periodId 销售期编号
//	 * @param afterSaleFlag 售后标识类型
//	 * @return 更新后的销售期对象
//	 */
//	I updateAfterSaleFlags(Long periodId, AfterSaleFlag afterSaleFlag);

	/**
	 * 读取需要派奖的期号列表
	 * 
	 * @param dateNow
	 *            当前系统时间
	 * @return 需要派奖的期号列表
	 */
	public List<I> getCanSendPrize(Date dateNow);

	/**
	 * 读取需要截止的期号列表
	 * 
	 * @param dateNow
	 *            当前系统时间
	 * @return 需要截止的期号列表
	 */
	public List<I> getCanCloseIssue(Date dateNow);

	/**
	 * 据键值读取配置信息项
	 * 
	 * @param key
	 *            键值
	 * @return 一条配置信息
	 */
	public KenoSysConfig getSysConfigByKey(String key);

	/**
	 * 得到可以追号的期数
	 * 
	 * @param dateNow
	 *            系统时间
	 * @param beforeTime
	 *            提前截至时间
	 * @param size
	 *            取的数
	 * @return List<I>
	 */
	public List<I> getCanChaseIssue(Date dateNow, Integer beforeTime, Integer size);
	


	/**
	 * 得到可以追号的期数 得到可以追号的期数
	 * 
	 * @param dateNow
	 *            系统时间
	 * @param beforeTime
	 *            提前截至时间
	 * @param size
	 *            取的数
	 * @return 一条配置信息
	 */
	public Integer getCanChaseIssueNum(Date dateNow, Integer beforeTime);

	/**
	 * 保存或更新配置信息
	 * 
	 * @param entity
	 *            一条配置信息
	 */
	public void getSysConfigByKey(KenoSysConfig entity);

	/**
	 * 保存追号内容
	 * 
	 * @param chasePlan
	 *            追号内容
	 */
	public void userChasePlan(ChasePlan chasePlan);

	/**
	 * 分页读取期数
	 * 
	 * @param criteria
	 * @param pagination
	 * @return
	 */
	public Pagination findByCriteriaAndPagination(XDetachedCriteria criteria, Pagination pagination);

	public I findIssueDataById(Long id);

//	public Class<C> getChasePlanClass();

	public Class<I> getIssueDataClass();

	public Class<S> getSchemeClass();

	public ChasePlan findChasePlanById(Long id);

	public S cancelScheme(Long id, AdminUser adminUser);
	public S commitTransactionScheme(Long id, AdminUser adminUser) ;
	/**
	 * 查询列表
	 * 
	 * @param criteria
	 * @return
	 */
	public List<I> findKenoPeriodByCriteria(DetachedCriteria criteria,Integer num);

	/**
	 * 发起方案
	 * 
	 * @param dto
	 * @return
	 */
	public S createScheme(KenoSchemeDTO schemeDTO);

	/**
	 * 查询列表
	 * 
	 * @param criteria
	 * @return
	 */
	public List<S> findByCriteria(XDetachedCriteria criteria);

	/**
	 * 获取下一期的ID
	 * 
	 * @param periodId
	 * @return
	 */
	public I getNextPeriod(long periodId);

	/**
	 * 停止追号
	 * 
	 * @param chasePlanId
	 * @param memo
	 */
	public void stopChasePlan(long chasePlanId, String memo);

	/**
	 * 更新开奖号码
	 * 
	 * @param issueData
	 */
	public void updateResults(I issueData);

	/**
	 * 暂停/启动销售
	 */
	public void pauseOrStartLottery();

	/**
	 * 暂停/启动销售状态值
	 * 
	 * @return
	 */
	public String findPauseOrStart();

	/**
	 * 追号最近的一个方案
	 * 
	 * @param chaseId
	 * @return
	 */
	public S getLastChaseScheme(long chaseId);

	/**
	 * 将旧方案对象里复制到全新的对象
	 * 
	 * @param oldScheme
	 * @return
	 */
	public S cloneNewScheme(S oldScheme);

	/**
	 * 追号处理
	 */
	public void handChasePlan(long chasePlanId);

	/**
	 * 据时间查询当前需要截止的期号数据
	 * 
	 * @param time
	 *            要询的时间
	 * @return 期号数据列表
	 */
	@SuppressWarnings("unchecked")
	public List<I> findCanCloseIssue(Date time);

	/**
	 * 查找可以执行开奖的期次
	 * 
	 * @return
	 */
	public List<I> getCanOpenResults();

	/**
	 * 查找当前期
	 * 
	 * @param dateNow
	 * @param beforeTime
	 * @return
	 */
	public I findCurrentData(Date dateNow, int beforeTime);

	/**
	 * 统计总方案数
	 * 
	 * @param issueDataId
	 * @return
	 */
	public Integer calAllSchemeCount(Long issueDataId);

	/**
	 * 统计总销量
	 * 
	 * @param issueDataId
	 * @return
	 */
	public Integer calAllSaleCount(Long issueDataId);

	/**
	 * 总中奖数
	 * 
	 * @param issueDataId
	 * @return
	 */
	public Integer calAllPrizeCount(Long issueDataId);

	/**
	 * 统计成功案数
	 * 
	 * @param issueDataId
	 * @return
	 */
	public Integer calSuccessSchemeCount(Long issueDataId);

	/**
	 * 设置期号时间
	 * 
	 * @param dateStar
	 *            开始时间
	 * @param dateEnd
	 *            结束时间
	 * @param dateMin
	 *            操作时间（可以是负数，action判断）
	 * @return 一条配置信息
	 */
	public void oprIssueTime(Date dateStar, Date dateEnd, Integer dateMin);

	/**
	 * 更新销售统计
	 * 
	 * @param issueData
	 * @throws DataException
	 */
	public void saleAnalye(I issueData) throws DataException;
	/**
	 * 销售金额跟中奖金额统计
	 * @param sql
	 * @return
	 */
	public List moneyAnalye(String startDate,String endDate);
	
	/**
	 * 强制出票
	 * 
	 * @param schemeId
	 */
	public S forcePrint(Long schemeId);
	
	/**
	 * 佣金统计
	 * 
	 * @param schemeId
	 */
	public S agentAnalyse(Long schemeId);

	public List<S> findUnSendPrize(Long id);
	
	public S createTicketScheme(KenoSchemeDTO schemeDTO);

	void doChasePlan(long curPeriodId, Boolean iswonStop);

	List<ChasePlan> getOutNumStopChasePlan(boolean outNumStop, I issueData);
}
