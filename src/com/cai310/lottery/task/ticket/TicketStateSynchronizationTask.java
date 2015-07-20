package com.cai310.lottery.task.ticket;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.entity.lottery.PrintInterface;
import com.cai310.lottery.service.lottery.PrintEntityManager;
import com.cai310.lottery.service.lottery.ticket.impl.SynchronizedTicketStateManager;
import com.cai310.lottery.service.lottery.ticket.impl.TicketEntityManager;

/**
 * 票状态同步工作任务
 * @author jack
 *
 */
public class TicketStateSynchronizationTask {
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	
	@Autowired
	protected TicketEntityManager ticketEntityManager;
	
	@Autowired
	protected PrintEntityManager printEntityManager;
	
	@Autowired
	protected SynchronizedTicketStateManager synchronizedTicketStateManager;
	
	/**
	 * 同步任务
	 */
	public void runTask(){
		List<Long> printInterfaceIds = ticketEntityManager.findPrintinterfaceIdByStateModifyTime();
		PrintInterface printInterface = null;
		for(Long id:printInterfaceIds){
			try{
				printInterface = printEntityManager.getPrintInterfaceById(id);
				if(printInterface==null){
					logger.warn("接口表中找不到序列号:"+id+"的记录！");
				}else{
					if(printInterface.getLotteryType().equals(Lottery.JCZQ)||printInterface.getLotteryType().equals(Lottery.JCLQ)){
						
						//？取得最后的ticket状态更新时间+30分=作为即时撤单的依据，避免一失败就撤单，来不及人工切换出票(待完善)，目前全部失败算失败，出票处现在还没有及时写失败状态，所以暂缓
						
						synchronizedTicketStateManager.synchronizationState_jc(printInterface,true);
					}else{
						synchronizedTicketStateManager.synchronizationState(printInterface,true);
					}
				}
			}catch(Exception e){
				logger.warn(e.getMessage());
				continue;
			}
		}
		try {
			Thread.sleep(1);
		} catch (InterruptedException e) {
			logger.warn(""+Thread.currentThread()+"sleep出错！");
		}
	}
}
