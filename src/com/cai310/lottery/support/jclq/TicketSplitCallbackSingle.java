package com.cai310.lottery.support.jclq;



public interface TicketSplitCallbackSingle {

	void handle(int index, PassType passType, int multiple);
}
