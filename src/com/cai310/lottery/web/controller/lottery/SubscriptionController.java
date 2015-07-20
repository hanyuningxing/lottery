package com.cai310.lottery.web.controller.lottery;

public class SubscriptionController extends LotteryBaseController {
	private static final long serialVersionUID = 6742886053251640063L;

	public String index() {
		return list();
	}

	public String list() {

		return "list";
	}
}
