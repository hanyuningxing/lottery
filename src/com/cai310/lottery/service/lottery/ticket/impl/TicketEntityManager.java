package com.cai310.lottery.service.lottery.ticket.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cai310.lottery.common.PrintInterfaceState;
import com.cai310.lottery.dao.lottery.ticket.TicketDao;
import com.cai310.lottery.dao.lottery.ticket.TicketLoggerDao;
import com.cai310.lottery.dao.lottery.ticket.TicketProcessDao;
import com.cai310.lottery.entity.lottery.PrintInterface;
import com.cai310.lottery.entity.lottery.ticket.Ticket;
import com.cai310.lottery.entity.lottery.ticket.TicketLogger;
import com.cai310.lottery.service.lottery.PrintEntityManager;
import com.cai310.lottery.ticket.common.TicketSupporter;
import com.cai310.orm.hibernate.CriteriaExecuteCallBack;
import com.cai310.utils.DateUtil;

/**
 * 标准出票格式的ticket实体操作类,本地操作-需要多数据源事务支持
 * 
 * @author jack
 * 
 */
@Service("ticketEntityManager")
@Transactional
public class TicketEntityManager {
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	protected TicketDao ticketDao;
	@Autowired
	protected TicketLoggerDao ticketLoggerDao;
	@Autowired
	protected TicketProcessDao ticketProcessDao;
	
	@Autowired
	protected PrintEntityManager printEntityManager;
	
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Ticket getTicket(Long id) {
		return this.ticketDao.get(id);
	}

	public Ticket saveTicket(Ticket entity) {
		return this.ticketDao.save(entity);
	}
	public TicketLogger saveTicketLogger(TicketLogger entity) {
		return this.ticketLoggerDao.save(entity);
	}
	/**
	 * 保存票，把出票接口改为已拆票
	 * @return
	 */
	public void saveTickets(PrintInterface printInterface, List<Ticket> ticketList, TicketSupporter ticketSupporter) {
		for (Ticket ticket : ticketList) {
			ticket.setTicketSupporter(ticketSupporter);
			this.ticketDao.save(ticket);
		}
		// 操作成功需要将当前操作的接口表设为已拆票记录回数据库
		printInterface.setPrintState(PrintInterfaceState.DISASSEMBLED);
		printEntityManager.savePrintInterface(printInterface);
	}
	
		
	
	/**
	 * 查找所属方案号的记录总个数
	 * @return
	 */
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Integer findCountByPrintinterfaceId(final Long printinterfaceId){
		return (Integer) ticketDao.execute(new CriteriaExecuteCallBack() {
			public Object execute(Criteria criteria) {
				criteria.add(Restrictions.eq("printinterfaceId", printinterfaceId));
				criteria.setProjection(Projections.rowCount());
				Integer total = (Integer) criteria.uniqueResult();
				return total;
			}
		});
	}
	
	/**
	 * 查找所属方案号的成功出票记录个数(成功记录数等于总个数,更新前台出票成功)
	 * @return
	 */
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Integer findCountSuccessByPrintinterfaceId(final Long printinterfaceId){
		return (Integer) ticketDao.execute(new CriteriaExecuteCallBack() {
			public Object execute(Criteria criteria) {
				criteria.add(Restrictions.eq("printinterfaceId", printinterfaceId));
				criteria.add(Restrictions.eq("sended", Boolean.TRUE));
				criteria.add(Restrictions.eq("stateCode", "1"));//成功出票状态
				criteria.setProjection(Projections.rowCount());
				Integer total = (Integer) criteria.uniqueResult();
				return total;
			}
		});
	}
	
	
	/**
	 * 查找所属方案号的委托中出票记录个数(成功记录数等于总个数,更新前台出票成功)
	 * @return
	 */
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Integer findCountPrintByPrintinterfaceId(final Long printinterfaceId){
		return (Integer) ticketDao.execute(new CriteriaExecuteCallBack() {
			public Object execute(Criteria criteria) {
				criteria.add(Restrictions.eq("printinterfaceId", printinterfaceId));
				criteria.add(Restrictions.eq("sended", Boolean.TRUE));
				criteria.add(Restrictions.eq("stateCode", "0"));//委托出票状态
				criteria.setProjection(Projections.rowCount());
				Integer total = (Integer) criteria.uniqueResult();
				return total;
			}
		});
	}
	
	
	/**
	 * 查找所属方案号的出票失败的记录个数
	 * @return
	 */
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Integer findCountFailedByPrintinterfaceId(final Long printinterfaceId){
		return (Integer) ticketDao.execute(new CriteriaExecuteCallBack() {
			public Object execute(Criteria criteria) {
				criteria.add(Restrictions.eq("printinterfaceId", printinterfaceId));
				criteria.add(Restrictions.eq("sended", Boolean.TRUE));
				criteria.add(Restrictions.eq("stateCode", "2"));//失败出票状态
				criteria.setProjection(Projections.rowCount());
				Integer total = (Integer) criteria.uniqueResult();
				return total;
			}
		});
	}
	
	
	/**
	 * 查找近段时间内有状态变化的ticket记录,取方案接口表Id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<Long> findPrintinterfaceIdByStateModifyTime(){
		return (List<Long>) ticketDao.execute(new CriteriaExecuteCallBack() {
			public Object execute(Criteria criteria) {
				ProjectionList propList = Projections.projectionList();
				propList.add(Projections.property("printinterfaceId"), "printinterfaceId");
				criteria.setProjection(propList);
				criteria.setProjection(Projections.distinct(propList));
				criteria.add(Restrictions.eq("synchroned", Boolean.FALSE));//未同步
				criteria.add(Restrictions.gt("stateModifyTime", DateUtil.getdecDateOfMinute(new Date(),24*60)));//前6小时
				criteria.setMaxResults(200);
				return criteria.list();
			}
		});
	}
	
	/**
	 * 根据接口表printInterfaceId查ticket记录
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<Long> findTicketByPrintInterfaceId(final Long printinterfaceId){
		return (List<Long>)ticketDao.execute(new CriteriaExecuteCallBack() {
			public Object execute(Criteria criteria) {
				criteria.setProjection(Projections.property("id"));
				criteria.add(Restrictions.eq("printinterfaceId", printinterfaceId));
				return criteria.list();
			}
		});		
	}
	/**
	 * 根据接口表printInterfaceId查ticketid记录
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<Long> findTicketIdByPrintInterfaceId(final Long printinterfaceId){
		return (List<Long>)ticketDao.execute(new CriteriaExecuteCallBack() {
			public Object execute(Criteria criteria) {
				criteria.add(Restrictions.eq("printinterfaceId", printinterfaceId));
				ProjectionList propList = Projections.projectionList();
				propList.add(Projections.property("id"), "id");
				criteria.setProjection(propList);
				criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
				return criteria.list();
			}
		});		
	}
	
}
