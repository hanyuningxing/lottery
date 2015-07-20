package com.cai310.lottery.service.rmi;

import java.util.List;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.entity.lottery.PrintInterface;
import com.cai310.lottery.entity.lottery.ticket.PrintInterfaceIndex;
import com.cai310.lottery.entity.lottery.ticket.Ticket;
import com.cai310.lottery.ticket.common.TicketQuery;
import com.cai310.lottery.ticket.common.TicketSupporter;

public interface TicketEntityManagerRMI {

	public Ticket getTicket(Long id);

	public Ticket saveTicket(Ticket entity);

	public Ticket updateTicket(Ticket entity);
	
	public void saveTickets(PrintInterfaceIndex printInterfaceIndex,List<Ticket> ticketList, TicketSupporter ticketSupporter);	

	/**
	 * 暂停发送
	 * @param entity
	 */
	public void pauseSend(Ticket entity,String adminName);
	
	/**
	 * 批量暂停发送
	 * @param ticketIds
	 */
	public void pauseSendOfBatch(List<Long> ticketIds,String adminName);
	
	/**
	 * 票重置出票商
	 * @param entity
	 * @param adminId
	 */
	public void resetTicketSupporter(Ticket entity,TicketSupporter ticketSupporter,String adminName);
	
	/**
	 * 批量重置出票商
	 * @param ticketIds
	 * @param ticketSupporter
	 * @param adminName
	 */
	public void resetTicketSupporterOfBatch(List<Long> ticketIds,TicketSupporter ticketSupporter,String adminName);

	/**
	 * 保存票，把出票接口改为已拆票
	 * @return
	 */
	public void saveTickets(PrintInterface printInterface,List<Ticket> ticketList, TicketSupporter ticketSupporter);

	/**
	 * 查找未发送的票
	 * @return
	 */
	public List<Ticket> findTicket(final List<TicketQuery> ticketQueryS,final TicketSupporter ticketSupporter);

	/**
	 * 查找某彩种单期未发送的票
	 * @return
	 */
	public List<Ticket> findTicket(final TicketQuery ticketQuery);

	/**
	 * 查找已发送未响应的票(一天前)
	 * @return
	 */
	public List<Ticket> findSendedNoResponseTicket(final TicketSupporter ticketSupporter);

	public List<Long> findSendedNoResponseTicketByLottery(final TicketSupporter ticketSupporter, final Lottery lottery);

	/**
	 * 查找未发送的票
	 * @return
	 */
	public List<Long> findTicket(final TicketSupporter ticketSupporter,final Lottery lottery);

	public List<Long> findNoTicketCodeTicketByLottery(final TicketSupporter ticketSupporter, final Lottery lottery);

	public List<Long> findTicket_synchronedTicketByLottery(final TicketSupporter ticketSupporter, final Lottery lottery);

	/**
	 * 查找所属方案号的记录总个数
	 * @return
	 */
	public Integer findCountByPrintinterfaceId(final Long printinterfaceId);

	/**
	 * 查找所属方案号的成功出票记录个数(成功记录数等于总个数,更新前台出票成功)
	 * @return
	 */
	public Integer findCountSuccessByPrintinterfaceId(final Long printinterfaceId);

	/**
	 * 查找所属方案号的委托中出票记录个数(成功记录数等于总个数,更新前台出票成功)
	 * @return
	 */
	public Integer findCountPrintByPrintinterfaceId(final Long printinterfaceId);

	/**
	 * 查找所属方案号的未发送出票记录个数,有未发送的不做处理
	 * @return
	 */
	public Integer findCountNoSendByPrintinterfaceId(final Long printinterfaceId);

	/**
	 * 查找所属方案号的出票失败的记录个数
	 * @return
	 */
	public Integer findCountFailedByPrintinterfaceId(final Long printinterfaceId);

	/**
	 * 查找近段时间内有状态变化的ticket记录,取方案接口表Id
	 * @return
	 */
	public List<Long> findPrintinterfaceIdByStateModifyTime();

	/**
	 * 根据接口表printInterfaceId查ticket记录
	 * @return
	 */
	public List<Long> findTicketByPrintInterfaceId(final Long printinterfaceId);

	/**
	 * 根据接口表printInterfaceId查ticketid记录
	 * @return
	 */
	public List<Long> findTicketIdByPrintInterfaceId(final Long printinterfaceId);

	/**
	 * 根据订单号查询相关票
	 * @return
	 */
	public List<Ticket> findTicketByOrderNo(final String orderNo);

	/**
	 * 根据订单id查询相关票
	 * @return
	 */
	public List<Ticket> findTicketByIds(final List<Long> ids);

}