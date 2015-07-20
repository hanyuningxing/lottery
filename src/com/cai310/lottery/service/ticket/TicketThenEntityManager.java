package com.cai310.lottery.service.ticket;

import java.math.BigDecimal;
import java.util.List;

import com.cai310.lottery.common.FundMode;
import com.cai310.lottery.entity.lottery.Scheme;
import com.cai310.lottery.entity.security.AdminUser;
import com.cai310.lottery.entity.ticket.TicketDatail;
import com.cai310.lottery.entity.ticket.TicketPlatformInfo;
import com.cai310.lottery.entity.ticket.TicketThen;
import com.cai310.lottery.entity.user.User;



/**
 * 
 */
public interface TicketThenEntityManager {
	TicketPlatformInfo getTicketPlatformInfo(Long id);
	List<Long> getAllTicketPlatformInfo();
	TicketPlatformInfo saveTicketPlatformInfo(TicketPlatformInfo ticketPlatformInfo);
	TicketThen saveTicketThen(TicketThen ticketThen);
	TicketThen getTicketThenByScheme(Scheme scheme);
	TicketDatail getTicketDatailByTicketThen(TicketThen ticketThen);
	/**
	 * 接票是否重复
	 * 
	 * @param orderId 订单编号
	 * @param platformInfoId 平台编号
	 * @return 是否已有该订单
	 */
	
	boolean isRepeatOrder(String orderId,Long platformInfoId);
	TicketDatail saveTicketDatail(TicketDatail ticketDatail);
	void UserTranMoneyToTicketPlatformInfo(TicketPlatformInfo ticketPlatformInfo,User user);
	void updateTicketPlatformInfoPrize();
	void tranUserToTicketPlatformInfo(Long userId);
	void synchronizationFailTicket(Scheme scheme);
	void synchronizationWonTicket(Scheme scheme);
	void adminOperUserMoney(Long userId,FundMode mode,BigDecimal money,String remark,AdminUser adminUser);
	TicketPlatformInfo getTicketPlatformInfoByUserId(Long userId);
}
